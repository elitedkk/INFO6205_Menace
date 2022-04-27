package test;

import menace.Tree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {

    @Test
    void testClone() {
        char[][] field = {{'O','X','X'},{'-','O','-'},{'-','-','O'}};
        Tree tree = new Tree(field,false,null);
        char[][] clone = tree.clone();

       for(int i = 0; i< 3; i++)
        {
            for(int j = 0; j<3; j++)
            {
                assertTrue(field[i][j] == clone[i][j]);
            }
        }
        field[1][1] ='x';
       assertFalse(field[1][1] == clone[1][1]);



    }

    @Test
    void testReturnScore() {
        Tree tree = new Tree(null,false,null);
        assertTrue(1== tree.retScore(true));
        assertTrue(-1== tree.retScore(false));
    }

}