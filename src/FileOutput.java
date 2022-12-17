package src;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Queue;

/**
 * Abstract class for outputting game information to the associated TextFile
 */
public abstract class FileOutput {

    /**
     * This method writes to the passed in parameter: file, the passed in parameter: str.
     * @param str
     * @param file
     */
    public static void writeToFile(String str, File file){
        try {
            FileWriter fw = new FileWriter(file, true); //the true will append the new data
            fw.write(str+" \n");//appends the string to the file
            fw.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * This method iterates through an array and returns it as a concatenated string
     * @param hand
     * @return returnString
     */
    private static String handToString(ArrayList<String> hand){
        String returnString = "";
        for (String card: hand) {
            returnString = returnString + card + " ";
        }
        return returnString;
    }

    /**
     * This method iterates through a queue and returns it as a concatenated string
     * @param deck
     * @return returnString
     */
    private static String handToString(Queue<String> deck) {
        String returnString = "";
        for (String card : deck) {
            returnString = returnString + card + " ";
        }
        return returnString;
    }

    /**
     * This method calls the writeToFile method and passes through a string describing the final state of a deck
     * @param deck
     */
    static void writeFinalDeck(int Id, Queue<String> deck, File file) {
        writeToFile("deck" + Id + " contents: " + handToString(deck), file);
    }

    /**
     * This method calls the writeToFile method and passes through a string describing the initial hand.
     * @param Id
     * @param hand
     * @param file
     */
    static void describeInitialHand(int Id, ArrayList<String> hand,  File file){
        writeToFile("Player "+Id+" initial hand is "+ handToString(hand), file);
    }
    /**
     * This method calls the writeToFile method and passes through a string describing the player drawing a card from a deck
     * @param Id
     * @param card
     * @param deck
     * @param file
     */
    static void drawsCard(int Id, Card card, int deck, File file) {
        writeToFile("Player "+Id+" draws a "+card.returnFaceValue()+" from deck "+deck, file);
    }

    /**
     * This method calls the writeToFile method and passes through a string describing the player discarding a card to a deck
     * @param Id
     * @param card
     * @param deck
     * @param file
     */
    static void discardsCard(int Id, Card card, int deck, File file) {
        writeToFile("Player "+Id+" discards a "+card.returnFaceValue()+" to deck "+deck, file);
    }

    /**
     * This method calls the writeToFile method and passes through a string describing the player's current hand
     * @param Id
     * @param hand
     * @param file
     */
    static void currentHand(int Id, ArrayList<String> hand, File file) {
        writeToFile("Player "+Id+" current hand is " +handToString(hand) , file);
    }

    /**
     * This method calls the writeToFile method and passes through a string describing the winning player
     * @param Id
     * @param hand
     * @param file
     */
    static void wins(int Id, ArrayList<String> hand, File file) {
        writeToFile("Player "+Id+" wins",file);
        writeToFile("Player "+Id+" final hand : " +handToString(hand), file);
    }

    /**
     * This method calls the writeToFile method and passes through a string indicating to the other players who has won and the game is over
     * @param Id
     * @param hand
     * @param file
     */
    static void loses(int WinId,int Id, ArrayList<String> hand, File file) {
        writeToFile("Player "+WinId+" has informed player " + Id + " that player " + WinId + " has won\nplayer " + Id + " exits\nPlayer " +Id+ " final hand : "  +handToString(hand) ,file);
    }

}
