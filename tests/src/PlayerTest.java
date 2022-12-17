package tests.src;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import src.Card;
import src.CardDeck;
import src.CardGame;
import src.Player;

import java.io.File;
import java.security.cert.TrustAnchor;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {
    Player player;
    @Before
    public void setUp() throws Exception {
        //Setup player
        this.player = new Player(1);
        

        //Setup players decks
        CardDeck leftDeck = new CardDeck(1);
        CardDeck rightDeck = new CardDeck(2);
        this.player.setLDeck(leftDeck);
        this.player.setRDeck(rightDeck);

    }

    @After
    public void tearDown() throws Exception {
        this.player = null;
    }

    @Test
    public void testGetDeckId(){
        assertEquals(1, this.player.getLDeck().getDeckId());
        assertEquals(2, this.player.getRDeck().getDeckId());

    }

    @Test
    public void testRun(){
        CardGame.gameOver.set(false);
        for (int i = 0; i < 4; i++) {
            this.player.addToHand(new Card("1"));
        }
        System.out.println(this.player.cardsToStringArray());
        this.player.run();
        assertTrue(CardGame.gameOver.get());
        //////////////////////////////////////////
        CardGame.gameOver.set(true);
        this.player.setHand(new ArrayList<Card>());
        for (int i = 0; i < 4; i++) {
            this.player.addToHand(new Card("1"));
        }
        System.out.println(this.player.cardsToStringArray());
        this.player.run();
        assertTrue(CardGame.gameOver.get());
        //////////////////////////////////////////
        CardGame.gameOver.set(false);
        this.player.setHand(new ArrayList<Card>());
        for (int i = 0; i < 3; i++) {
            this.player.addToHand(new Card("1"));
        }
        this.player.addToHand(new Card("4"));
        CardDeck ldeck = new CardDeck(1);
        for (int i = 0; i < 4; i++) {
            ldeck.addToCardDeck(new Card(Integer.toString(1)));
        }
        this.player.setLDeck(ldeck);
        System.out.println("Left decks initial cards: "+ldeck.getCardDeck());
        System.out.println("The players initial cards: "+this.player.cardsToStringArray());
        this.player.run();
        assertTrue(CardGame.gameOver.get());
    }

    @Test
    public void testIsWinningHand() throws InterruptedException{
        CardGame.gameOver.set(false);
        for (int i = 1; i < 5; i++) {
            this.player.addToHand(new Card("11"));
        }
        System.out.println(this.player.cardsToStringArray());
        assertTrue(this.player.isWinningHand());

        this.player = new Player(11);
        this.player.setHand(new ArrayList<>());
        for (int i = 1; i < 5; i++) {
            this.player.addToHand(new Card(Integer.toString(i)));
        }
        System.out.println(this.player.cardsToStringArray());
        assertFalse(this.player.isWinningHand());
    }

    @Test
    public void testAddToHand() {
        this.player.addToHand(new Card("1"));
        assertEquals(1, this.player.getHand().size());
        this.player.addToHand(new Card("2"));
        assertEquals(2, this.player.getHand().size());
    }

    @Test
    public void testRemoveCard() {
        player = this.player;
        player.addToHand(new Card("10"));
        player.addToHand(new Card("20"));
        player.addToHand(new Card("30"));
        assertEquals(3, player.getHand().size());
        player.removeCard();
        assertEquals(2, player.getHand().size());
    }

    @Test
    public void testCardsToArray() {
        ArrayList<String> cardsList = new ArrayList<>();
        cardsList.add("1");
        cardsList.add("2");
        cardsList.add("3");
        cardsList.add("4");
        for (int i = 1; i < 5; i++) {
            this.player.addToHand(new Card(Integer.toString(i)));
        }
        assertEquals(cardsList,this.player.cardsToStringArray());

    }
    @Test
    public void testFileCreation() {
        File filename = this.player.getPlayerFile();
        assertTrue(filename.exists() && !filename.isDirectory());
    }

}