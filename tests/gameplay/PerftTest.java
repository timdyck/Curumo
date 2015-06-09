package gameplay;

import gameplay.Gameplay;
import junit.framework.Assert;

import org.junit.Test;

import perft.Perft;

public class PerftTest {

    public long[] perftDepthNodesCount = { 1, 20, 400, 8902, 197281, 4865609, 119060324, 3195901860L, 84998978956L,
            2439530234167L, 69352859712417L, 2097651003696806L, 62854969236701747L, 1981066775000396239L };

    @Test
    public void PerftFiveDeep() {
        Gameplay game = new Gameplay();

        int maxDepth = 5;
        int leafNodes = Perft.countLeafNodes(game, maxDepth);
        System.out.println("Total: " + leafNodes);
        Assert.assertEquals(perftDepthNodesCount[maxDepth], leafNodes);

    }
}
