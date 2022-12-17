package tests.src;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import src.Card;
import src.CardDeck;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CardDeckTest {

    CardDeck deck;
    @Before
    public void setUp() throws Exception {
        this.deck = new CardDeck(1);
        for (int i = 1; i < 5; i++) {
            this.deck.addToCardDeck(new Card(Integer.toString(i)));
        }
    }

    @After
    public void tearDown() throws Exception {
        this.deck = null;
    }

    @Test
    public void testReturnTopCard() {
        //In the setup the first card added had an ID of 1 therefore it should be the top card
        assertEquals("1",this.deck.returnTopCard().returnFaceValue());
    }

    @Test
    public void testIsDeckEmpty() {
        this.deck.getDeck().clear();
        assertTrue(this.deck.isDeckEmpty());
    }

    @Test
    public void testGetCardDeck() {
        Queue<String> cardsExpected = new LinkedList<String>();
        cardsExpected.add("1");
        cardsExpected.add("2");
        cardsExpected.add("3");
        cardsExpected.add("4");
        assertEquals(cardsExpected,this.deck.getCardDeck());
    }

    @Test
    public void testAddToCardDeck() {
        //When we add a card it should be at the back of the queue. Therefore, the last card to be removed 
        Card cardRemoved = null;
        for (int i = 0; i < 4; i++) {
            cardRemoved = this.deck.returnTopCard();
        }
        assertEquals("4",cardRemoved.returnFaceValue());
    }

    @Test
    public void testFileCreation() {
        File filename = this.deck.getFileName();
        assertTrue(filename.exists() && !filename.isDirectory());
    }
}