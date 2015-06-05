package perft;

import gameplay.Gameplay;

import org.junit.Assert;
import org.junit.Test;

public class PerftTest {

    public long[] perftDepthNodesCount = { 1, 20, 400, 8092, 197281, 4865609, 119060324, 3195901860L, 84998978956L,
            2439530234167L, 69352859712417L, 2097651003696806L, 62854969236701747L, 1981066775000396239L };

    @Test
    public void PerftFiveDeep() {
        Gameplay game = new Gameplay();

        for (int maxDepth = 1; maxDepth <= 5; maxDepth++) {
            Assert.assertEquals(perftDepthNodesCount[maxDepth], Perft.countLeafNodes(game, 1, maxDepth));
        }
    }
}
