package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

public class CardGame extends FileOutput{

    public static ThreadGroup tg1 = new ThreadGroup("Parent"); //Group that holds all threads for the players
    public static ArrayList<Thread> threads = new ArrayList<>(); //An arraylist that contains the thread for each player
    public static volatile AtomicBoolean gameOver = new AtomicBoolean(false); //Defines whether the game is over
    public static int winnerId; //The ID of the player who has won the game

    /**
     * Main function sets up the card game and starts the game
     * It asks for the amount of players
     * It generates pack from a given file and checks its validility
     * Generates the players for the game
     * Distributes the cards to both the players and the decks
     * Starts the player threads
     * @param args
     */
    public static void main(String[] args){
        int playerCount = getPlayerCount();
        Stack<Card> cardPack = inputPack(playerCount);
        ArrayList<Player> players = generatePlayers(playerCount);
        ArrayList<CardDeck> decks = generateDecks(playerCount);
        distributeCard(players ,decks ,cardPack);
        assignPlayerDecks(players, decks);
        System.out.println("---------------");
        startGameThreads(players);

        Thread waitingForGameOver = new Thread(tg1,"deckfinal"){
            public void run(){
                synchronized (this){
                    try {
                        this.wait();//Thread waits for the game to end
                    } catch (InterruptedException e) {
                        finalDecks(decks);//Collect the final decks of the game
                    }
                }
            }
        };
        waitingForGameOver.start();

    }



    /**
     * This method retrieves and calls check on the inputted playercount
     * @return playercount
     */
    public static int getPlayerCount(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of players...");
        String playerCount = input.next();
        if (checkPlayerCount(playerCount)){
            return Integer.parseInt(playerCount);
        }else{
            return getPlayerCount();
        }
    }

    /**
     * This method checks if the player count inputted is valid
     * @return playercount
     */
    public static boolean checkPlayerCount(String playerCount){
        try{
            int playerCountInt = Integer.parseInt(playerCount);
            if(playerCountInt <= 1){ //Check if the player count is positive
                System.out.println("Input must be greater than 1");
                return false;
            }
        }catch (Exception e){ //Check if the input is an integer
            System.out.println("Input must be a valid integer");
            return false;
        }
        return true;
    }

    /**
     * This method will read a designated input pack and checks for both its existance and validility
     * @param playerCount
     * @return a valid pack of cards
     */
    public static Stack<Card> inputPack(int playerCount){
        Scanner input2 = new Scanner(System.in);
        Stack<Card> cards = null; 
        System.out.println("Enter the file containing the pack...");
        String fileName = input2.next();
        if (Pack.getPack(fileName)){
            cards = Pack.fileContents(fileName);
        }else{
            return inputPack(playerCount);
        }
        if(Pack.checkPack(cards, playerCount)){
            Collections.shuffle(cards);
            return cards;
        }else{
            return inputPack(playerCount);
        }
    }


    /**
     * This function will add all threads to a common group and start the player threads
     * We are shuffling the threads so that it is a more fair game, your position won't determine if you win
     * @param players
     */
    public static void startGameThreads(ArrayList<Player> players){
        for (Player p: players) {
            Thread x = new Thread(tg1,p);
            threads.add(x);
        }
        Collections.shuffle(threads);
        for (Thread thread: threads) {
            thread.start();
        }
    }


    /**
     * This function will create 'playerCount' amount of player objects
     * and return them in an arrayList
     * @param playerCount
     * @return the player objects
     */
    public static ArrayList<Player> generatePlayers(int playerCount){
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 1; i < playerCount + 1; i++) {
            players.add(new Player(i));
        }
        return players;
    }


    /**
     * This function will create n amount of empty deck objects that will later be filled with cards
     * @param playerCount
     * @return ArrayList containing the card decks
     */
    public static ArrayList<CardDeck> generateDecks(int playerCount){
        ArrayList<CardDeck> decks = new ArrayList<>();
        for (int i = 1; i < playerCount + 1; i++) {
            decks.add(new CardDeck(i));
        }
        return decks;
    }


    /**
     * This function distributes 4 cards to each player in a round-robin fashion
     * and then distributes the remaining cards between the decks
     * using the same round-robin technique
     * @param players
     * @param cardDecks
     * @param cards
     */
    public static void distributeCard(ArrayList<Player> players, ArrayList<CardDeck> cardDecks, Stack<Card> cards){
        int totalPlayers = players.size();
        for (int i = 0; i < 4; i++) {
            int playersCount = 0;
            while(playersCount < totalPlayers){
                players.get(playersCount).addToHand(cards.pop());
                playersCount++;
            }
        }
        while(!cards.isEmpty()){
            int deckCount = 0;
            while(deckCount < totalPlayers){
                cardDecks.get(deckCount).addToCardDeck(cards.pop());
                deckCount++;
            }
        }
    }


    /**
     * This function iterates over the players arraylist and assigns them two decks (the left and right) according to their playerID
     * @param players
     * @param decks
     */
    public static void assignPlayerDecks(ArrayList<Player> players, ArrayList<CardDeck> decks) {
        for (Player player : players) {
            if(player.getPlayerID() == 1){//If the player is the first player, (right deck is the last deck)
                player.setLDeck(decks.get(player.getPlayerID()-1));
                player.setRDeck(decks.get(decks.size()-1));
            }else{ //If the player is not the first
                player.setLDeck(decks.get(player.getPlayerID()-1));
                player.setRDeck(decks.get(player.getPlayerID()-2));
            }
        }
    }

    /**
     * This function will interrupt the thread group when called
     */
    public static synchronized void endGame() {
        tg1.interrupt();
    }

    /**
     * This function will check attempt to end the game,
     * if there is already a winner when it is called it will simply return false
     * If there is no winner when it is called it will designate the parameter playerId as winner
     * and return true
     * @param playerId
     * @return
     */
    public static boolean tryGameOver(int playerId) {
        if(gameOver.compareAndSet(false, true)){
            winnerId = playerId;
            return true;
        }else{
            return false;
        }

    }

    /**
     * Iterates over the arraylist of cardDeck objects and outputs their deck to an output file
     * @param cardDecks
     */
    public static void finalDecks(ArrayList<CardDeck> cardDecks){
        for (CardDeck deck:cardDecks) {
            FileOutput.writeFinalDeck(deck.getDeckId(), deck.getCardDeck(),deck.getFileName());
        }
    }


}
