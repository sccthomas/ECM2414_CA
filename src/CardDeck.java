package src;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class creates cardDeck objects which stores cards that aren't currently in a players hand
 * */
public class CardDeck {
    int deckId;
    File deckFile;
    Queue<Card> cardDeck;

    /**
     * This is the constructor for a CardDeck object
     * Within the constructor a new file is created for
     * outputting the state of the deck at the end of the game
     * @param deckId
     */
    public CardDeck(int deckId) {
        this.cardDeck =  new LinkedList<>();
        this.deckId = deckId;
        try{
            this.deckFile = new File("Deck"+deckId+"_output.txt");
            deckFile.createNewFile();
        }catch (IOException e){
            System.out.println(e);
        }
    }

    /**
     * Returns the deckId of the CardDeck object this method is called on
     * @return deckId
     */
    public int getDeckId() {
        return deckId;
    }

    /**
     * Removes and returns the top card of a deck
     */
    public Card returnTopCard() {
            return cardDeck.remove();

    }

    /**
     * Returns True or False depending on whether the cardDeck queue within this object is empty
     */
    public boolean isDeckEmpty(){
        return cardDeck.isEmpty();
    }


    /**
     * Returns the CardDeck as a Queue of strings. The strings representing the Card objects face value.
     * @return cardsValues
     */
    public  Queue<String> getCardDeck() {
        Queue<String> cardsValues = new LinkedList();
        for (Card card: cardDeck) {
            cardsValues.add(card.returnFaceValue());
        }
        return cardsValues;
    }

    /**
     * Adds a Card object to the end of the queue 'cardDeck'.
     * @param card
     */
    public void addToCardDeck(Card card) {
        this.cardDeck.add(card);

    }

    /**
     * Returns the outputFile name of the CardDeck this method is called on
     * @return deckId
     */
    public File getFileName(){
        return deckFile;
    }
    /**
     * Returns the deck as a queue
     * @return Queue
     */
    public Queue<Card> getDeck() {
        return cardDeck;
    }
}
