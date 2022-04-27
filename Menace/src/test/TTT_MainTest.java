package test;

import menace.Players;
import menace.TTT_Main;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TTT_MainTest {

    @org.junit.jupiter.api.Test
    void checkifWintest() {
        TTT_Main ttt_main = new TTT_Main();
        Players human  = new Players("Human", 'O', false,false);
        char[][] field = {{'O','X','X'},{'-','O','-'},{'-','-','O'}};
        boolean h = ttt_main.CheckifWin(human,2,2,field);
        assertTrue(true == h);
    }
    @org.junit.jupiter.api.Test
    void checkifWindiagtest() {
        TTT_Main ttt_main = new TTT_Main();
        Players human  = new Players("Human", 'O', false,false);
        char[][] field = {{'O','X','X'},{'-','O','-'},{'-','-','O'}};
        boolean h = ttt_main.WinCondition_Diag(human,field);
        assertTrue(true == h);
    }
    @org.junit.jupiter.api.Test
    void checkifWinrowtest() {
        TTT_Main ttt_main = new TTT_Main();
        Players human  = new Players("Human", 'O', false,false);
        char[][] field = {{'X','X','O'},{'-','X','O'},{'-','-','O'}};
        boolean h = ttt_main.WinCondition_Row(human,2,2,field);
        assertTrue(true == h);
    }

    @org.junit.jupiter.api.Test
    void checkifWincoltest() {
        TTT_Main ttt_main = new TTT_Main();
        Players human  = new Players("Human", 'O', false,false);
        char[][] field = {{'O','X','X'},{'O','O','O'},{'X','X','O'}};
        boolean h = ttt_main.WinCondition_Column(human,1,2,field);
        assertTrue(true == h);
    }
}