package src;

/**
 *Class for creating a Card object
 */
public class Card {
    String faceValue;

    /**
     * Constructor for creating a Card object
     * @param faceValue
     */
    public Card(String faceValue) {
        this.faceValue = faceValue;
    }

    /**
     * Function that returns the faceValue of a card
     * @return faceValue
     */
    public synchronized String returnFaceValue(){
        return this.faceValue;
    }

}
