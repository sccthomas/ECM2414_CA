package tests.src;

import org.junit.Before;
import org.junit.Test;
import src.Card;

import static org.junit.Assert.assertEquals;

public class CardTest {
    Card card;

    @Before
    public void setUp(){
        card = new Card("10");
    }

    @Test
    public void returnFaceValue() {
        assertEquals(card.returnFaceValue(), "10");
    }
}