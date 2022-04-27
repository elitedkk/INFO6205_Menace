package menace;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TreeTest {
    private static Logger logger = LoggerFactory.getLogger(TreeTest.class);
    private static Timestamp getTimestamp()
    {
        return new Timestamp(System.currentTimeMillis());
    }
    @Test
    void testClone() {
        char[][] field = {{'O','X','X'},{'-','O','-'},{'-','-','O'}};
        TTT_Main ttt = new TTT_Main();
        Tree tree = new Tree(field,false,ttt);
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
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName()+"() Passed at "+getTimestamp());



    }

    @Test
    void testReturnScore() {
        TTT_Main ttt = new TTT_Main();
        Tree tree = new Tree(null,false,ttt);
        assertTrue(1== tree.retScore(true));
        assertTrue(-1== tree.retScore(false));
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName()+"() Passed at "+getTimestamp());
    }

}