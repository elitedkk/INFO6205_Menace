package test;
import menace.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class BeadsDistributionTest {
    private static  Logger logger = LoggerFactory.getLogger(BeadsDistributionTest.class);

    private static Timestamp getTimestamp()
    {
        return new Timestamp(System.currentTimeMillis());
    }

    @Test
    void getByProbabilityTest() {
        BeadsDistribution beadsDistribution = new BeadsDistribution();
        beadsDistribution.beads = new int[]{10,20,30,40,50,60,70,80,90};
        assertEquals(3,beadsDistribution.getByProbability(80));
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName()+"() Passed at "+getTimestamp());
    }
}