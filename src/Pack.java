package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

/**
 * Class that has methods for generating, validating and returning a pack
 */
public class Pack {


    /**
     * This method generates a valid pack for the player count
     * @param playerCount
     * @param filename
     */
    public static void generatePack(int playerCount, String filename) {
        ArrayList<Integer> pack = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            for (int j = 1; j < 9; j++) {
                pack.add(j);
            }
        }
        Collections.shuffle(pack);

        try {
            File myObj = new File(filename);
            myObj.createNewFile();
            FileWriter fileWriter = new FileWriter(filename);
            for (int value : pack) {
                fileWriter.write(value + "\n");
            }
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("File exception");
        }
    }


    /**
     * This method reads the pack file and returns a stack consisting of Card objects
     * @param filename
     * @return cards
     */
    public static boolean getPack(String filename) {
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
        } catch (FileNotFoundException f) {
            System.out.println("The file specified does not exist.");
            return false;
        }
        return true;

    }

    /**
     * A function that will return the content of a file, and will catch a filenotfound exception if no file is present.
     * @param filename
     * @return cards
     */
    public static Stack<Card> fileContents(String filename){
        File myObj = new File(filename);
        Scanner myReader = null;
        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Stack<Card> cards = new Stack<>();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            cards.push(new Card(data));
        }
        myReader.close();
        return cards;
    }

    /**
     * This method checks that a pack entered has a valid size and that each card has a face value greater than zero
     * @param cardStack
     * @param playerCount
     */
    public static boolean checkPack(Stack<Card> cardStack, int playerCount){
        ArrayList<Card> cards = new ArrayList<>(cardStack);
        if (cards.size()/8 == playerCount){ //Checks if the pack is 8n, where n is the player count
            for (Card card:cards) {
                try{
                    if(Integer.parseInt(card.returnFaceValue()) <= 0 ){   //Checks that a card is greater than 0 and an integer
                        System.out.println("Pack does not match required format, it contains an 0 value or negative value.");
                        return false;
                    }
                }catch (Exception e) {
                    System.out.println("Pack does not match required format, the pack is not all integers.");
                    return false;
                }
            }
        }else {
            System.out.println("Pack does not match required format, it is not suitable for the player count.");
            return false;
        }
        return true;
    }


}
