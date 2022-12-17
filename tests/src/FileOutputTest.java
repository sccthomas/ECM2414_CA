package tests.src;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import src.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static junit.framework.TestCase.assertEquals;


public class FileOutputTest {
    File testFile;
    Player player;

    @Before
    public void setUp(){
        player = new Player(1);
        for (int i = 1; i < 5; i++) {
            player.addToHand(new Card(String.valueOf(i)));
        }

        player.setLDeck(new CardDeck(1));
        player.setRDeck(new CardDeck(2));

        try{
            this.testFile = new File("player"+testFile+"_output");
            testFile.createNewFile();
        }catch (IOException e){
            System.out.println(e);
        }
    }
    @After
    public void tearDown(){
        player = null;
    }

    @Test
    public void testFileWrite(){
        FileOutput.writeToFile("WORK", testFile);

        File myObj = testFile;
        Scanner myReader = null;
        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String data = "";
        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
        }
        myReader.close();
        assertEquals("WORK ", data);

    }



}