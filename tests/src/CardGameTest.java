package tests.src;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import src.*;

import java.util.ArrayList;
import java.util.Stack;

import static org.junit.Assert.*;

public class CardGameTest{
    ArrayList<Player> players;
    ArrayList<CardDeck> decks;
    @Before
    public void setUp() throws Exception {
        this.players = CardGame.generatePlayers(4);
        this.decks = CardGame.generateDecks(4);
    }

    @After
    public void tearDown() throws Exception {
        this.players = null;
        this.decks = null;


    }

    @Test
    public void canInterrupt(){
        ArrayList<Integer> check = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            Thread thread = new Thread(CardGame.tg1, "thread" + i){
                public void run() {
                    synchronized (this) {
                        System.out.println("Thread will now wait");
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            System.out.println("Out");
                            check.add(1);
                        }
                    }
                }
            };
            thread.start();
        }
        CardGame.endGame();
        while(true){
            if(check.size() == 3){
                System.out.println("All threads were interrupted");
                break;
            }
        }


    }

    @Test
    public void testGetPlayerCount() {
        for (int i = -5; i < 5; i++) {
            if(i<=1){
                assertFalse(CardGame.checkPlayerCount(Integer.toString(i)));
            }else{
                assertTrue(CardGame.checkPlayerCount(Integer.toString(i)));
            }
        }
        assertFalse(CardGame.checkPlayerCount("a"));
        assertFalse(CardGame.checkPlayerCount("1.5"));
        assertFalse(CardGame.checkPlayerCount("ab"));
        assertFalse(CardGame.checkPlayerCount("abc"));
    }

    @Test
    public void testDistributeCard() {
        //We have the distributecard function take in objects so that
        // we can test different amount of players rather than have the function
        // update attributes of cardgame
        //We can test for if its round-robin
        // We have not shuffled the cards in the distribution the cards function as we assume they are shuffled
        // before and passed in
        // For this case we are using an non-shuffled pack so that we can check for correct distribution

        //ArrayList<Player> players = CardGame.generatePlayers(4);
        //ArrayList<CardDeck> decks = CardGame.generateDecks(4);
        Stack<Card> cards = Pack.fileContents("tests/test_pack1"); // a pack for size 4
        Stack<Card> cardsCopy = Pack.fileContents("tests/test_pack1");

        //Test the players hands are empty
        for (Player player: this.players){
            assertEquals(0, player.getHand().size());
        }

        CardGame.distributeCard(players,decks,cards);

        //test players have been assigned cards after function call
        for (Player player: this.players) {
            assertEquals(4,player.getHand().size());
            //For each player it takes the first 4 cards of the pack and checks that the players hand doesn't equal this
            ArrayList<Card> fourCards = new ArrayList<>();
            for (int i = 0; i < players.size(); i++) {
                fourCards.add(cardsCopy.pop());
            }

            assertNotEquals(player.getHand(),fourCards);
        }
        for (CardDeck deck: this.decks) {
            assertNotEquals(0, deck.getCardDeck().size());
            ArrayList<Card> fourCards = new ArrayList<>();
            for (int i = 0; i < players.size(); i++) {
                fourCards.add(cardsCopy.pop());
            }
            assertNotEquals(new ArrayList<>(deck.getCardDeck()),fourCards);
        }
    }

    @Test
    public void testAssignPlayerDecks() {
        CardGame.assignPlayerDecks(this.players,this.decks);
        for (Player player: players) {
            if(player.getPlayerID() == 1){
                assertEquals(player.getPlayerID(),player.getLDeck().getDeckId());
                assertEquals(decks.size(),player.getRDeck().getDeckId());
            }else{
                assertEquals(player.getPlayerID(),player.getLDeck().getDeckId());
                assertEquals(player.getPlayerID()-1,player.getRDeck().getDeckId());
            }
        }
    }

}