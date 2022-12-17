package src;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * This class defines the methods and attributes associated with a player.
 * This class also contains a run() that starts a threaded process within a player
 */
public class Player extends FileOutput implements Runnable {
    int playerID;
    CardDeck LDeck;
    CardDeck RDeck;
    ArrayList<Card> hand = new ArrayList<>();
    File playerFile;
    boolean won = false;


    /**
     * This is the constructor for the player object
     * Output file is generated within the constructor
     * @param playerID
     */
    public Player(int playerID) {
        this.playerID = playerID;
        try{
            this.playerFile = new File("player"+playerID+"_output");
            playerFile.createNewFile();
        }catch (IOException e){
            System.out.println(e);
        }
    }

    /**
     * This is the threaded process within player
     * Process consists of taking a card from the top of the deck to the players left and adding it to their hand
     * Evaluating their hand
     * Removing a card from their deck and placing it on the bottom of the deck to their right
     */
    public void run() {
        try{
            //Outputs the initial hand of the player to a file
            FileOutput.describeInitialHand(playerID, cardsToStringArray(), playerFile);
            while(! CardGame.gameOver.get()){   //Threaded process continues until game is over
                if(! isWinningHand()){
                    if(!LDeck.isDeckEmpty()){
                        Card card = LDeck.returnTopCard(); //Removes a card from left deck
                        FileOutput.drawsCard(playerID, card, LDeck.getDeckId(), playerFile);
                        addToHand(card);    //adds to its hand
                        Card removedCard = removeCard();    //Evaluates card to remove
                        FileOutput.discardsCard(playerID, removedCard, RDeck.getDeckId(), playerFile);
                        RDeck.addToCardDeck(removedCard); //removes the card
                        FileOutput.currentHand(playerID, cardsToStringArray(), playerFile);
                    }
                }
            }
        }catch (InterruptedException e){

        }
        if(!won){
            FileOutput.loses(CardGame.winnerId , playerID,cardsToStringArray(), playerFile);
        }
    }

    /**
     * This is process is synchronised so only one thread can call it at a time
     * This method checks whether the hand of the player is a winning hand and returns true or false
     * outputs the winner to textfile and console
     * Notifies other players of the game ending
     */
    public synchronized boolean isWinningHand() throws InterruptedException {
        Set<String> set = new HashSet<String>(cardsToStringArray());
        if (set.size() == 1){
            if(CardGame.tryGameOver(playerID)){
                CardGame.endGame();
                System.out.println("player "+ playerID + " won");
                won = true;
                FileOutput.wins(playerID, cardsToStringArray(), playerFile);
            }else{
                this.wait();
            }
            return true;
        }
        return false;
    }

    /**
     * This method adds a Card object to the players hand
     */
    public void addToHand(Card card){
        hand.add(card);
    }

    /**
     *This method removes a card from the players hand that is not the players preferred card denomination
     * @return Card
     */
    public Card removeCard(){
        for (int i = 0; i < hand.size(); i++) {
            if(! hand.get(i).returnFaceValue().equals(String.valueOf(getPlayerID()))){
                return hand.remove(i);
            }
        }
        return new Card("");
    }

    /**
     * This method returns an arraylist consisting of the face value of the cards in the players hand
     * @return handValue
     */
    public ArrayList<String> cardsToStringArray(){
        ArrayList<String> handsValue = new ArrayList<>();
        for (Card card: hand) {
            handsValue.add(card.returnFaceValue());
        }
        return handsValue;
    }

    /**
     * Returns a playersID
     * @return playerID
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Returns the left deck of the player
     * @return LDeck
     */
    public CardDeck getLDeck() {
        return LDeck;
    }

    /**
     * Sets the left deck of the player
     */
    public void setLDeck(CardDeck LDeck) {
        this.LDeck = LDeck;
    }

    /**
     * Returns the right deck of the player
     * @return RDeck
     */
    public CardDeck getRDeck() {
        return RDeck;
    }

    /**
     * Sets the right deck of the player
     * @return RDeck
     */
    public void setRDeck(CardDeck RDeck) {
        this.RDeck = RDeck;
    }

    /**
     * Returns a players hand
     * @return hand
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * Sets the hand of a player
     */
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    /**
     * Returns the output file of a player
     * @return playerFile
     */
    public File getPlayerFile() {
        return playerFile;
    }
}
