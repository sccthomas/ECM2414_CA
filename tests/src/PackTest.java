package tests.src;

import org.junit.Test;
import src.Pack;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PackTest {

    @Test
    public void testGeneratePack() {
        Pack.generatePack(4,"tests/test_pack0");
        assertTrue(new File("tests/test_pack0").isFile());
    }
    @Test
    public void testGetPackValid() {
        Pack.generatePack(4,"tests/test_pack1");
        assertTrue(Pack.checkPack(Pack.fileContents("tests/test_pack1"),4));
    }
    @Test
    public  void testGetPackInValidPlayerCount(){
        for (int i = 0; i < 10; i++) {
            boolean valid = Pack.checkPack(Pack.fileContents("tests/test_pack2"),i);
            if(i == 5){
                assertTrue(valid);
            }else{
                assertFalse(valid);
            }
        }
    }

    @Test
    public void testWordPack(){
        assertFalse(Pack.checkPack(Pack.fileContents("tests/test_pack3"),5));
    }

    @Test
    public void testGetPackInvalidZero(){
        assertFalse(Pack.checkPack(Pack.fileContents("tests/test_pack4"),5));
    }
    @Test
    public void testGetPackInvalidName(){
        assertFalse(Pack.getPack("qwerty"));

    }
}