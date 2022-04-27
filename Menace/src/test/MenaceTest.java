package test;

import menace.Menace;
import menace.TTT_Main;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

class MenaceTest {

    @Test
    void createStringFromField() {
        TTT_Main ttt_main = new TTT_Main();
        Menace menace = new Menace(ttt_main);

        char[][] field = {{'O','X','X'},{0,'O',0},{0,0,'O'}};
        assertTrue("oxx0o000o".equals(menace.createStringFromField(field)));

    }


    @Test
    void getIandJ() {
        TTT_Main ttt_main = new TTT_Main();
        Menace menace = new Menace(ttt_main);
        assertArrayEquals(new int[]{0,0},menace.getIandJ(0));
        assertArrayEquals(new int[]{0,1},menace.getIandJ(1));
        assertArrayEquals(new int[]{1,0},menace.getIandJ(3));
        assertArrayEquals(new int[]{1,1},menace.getIandJ(4));
        assertArrayEquals(new int[]{2,2},menace.getIandJ(8));
    }

    @Test
    void getIndex(){
        TTT_Main ttt_main = new TTT_Main();
        Menace menace = new Menace(ttt_main);
        assertTrue(menace.getIndex(0,0)==0);
        assertTrue(menace.getIndex(0,1)==1);
        assertTrue(menace.getIndex(1,1)==4);
        assertTrue(menace.getIndex(2,1)==7);
        assertTrue(menace.getIndex(2,2)==8);
    }
}