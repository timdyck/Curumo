package gameplay;

import junit.framework.Assert;

import org.junit.Test;

import perft.Perft;
import board.Board;
import board.BoardUtils;
import board.FEN;
import board.PieceType;

public class PerftTest {

    public long[] perftDepthNodesCount = { 1, 20, 400, 8902, 197281, 4865609, 119060324, 3195901860L, 84998978956L,
            2439530234167L, 69352859712417L, 2097651003696806L, 62854969236701747L, 1981066775000396239L };

    @Test
    public void ChessProgramming1() {
        Gameplay fenGame = FEN.fromFenString("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");
        Board board = fenGame.getBoard();
        BoardUtils.printBoard(board);
        Gameplay game = new Gameplay(board, PieceType.Colour.WHITE);
        BoardUtils.printBoard(board);

        int nodes = Perft.countLeafNodes(game, 5);
        Assert.assertEquals(nodes, 193690690);
    }

    @Test
    public void ChessProgramming2() {
        runPerft("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -", 7, 178633661);
    }

    @Test
    public void ChessProgramming3() {
        runPerft("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1", 7, 706045033);
    }

    @Test
    public void IllegalEnPassant1() {
        runPerft("3k4/3p4/8/K1P4r/8/8/8/8 b - - 0 1", 6, 1134888);
    }

    @Test
    public void IllegalEnPassant2() {
        runPerft("8/8/4k3/8/2p5/8/B2P2K1/8 w - - 0 1", 6, 1015133);
    }

    @Test
    public void EnPassantCaptureAndCheck() {
        runPerft("8/8/1k6/2b5/2pP4/8/5K2/8 b - d3 0 1", 6, 1440467);
    }

    @Test
    public void ShortCastlingCheck() {
        runPerft("5k2/8/8/8/8/8/8/4K2R w K - 0 1", 6, 661072);
    }

    @Test
    public void LongCastlingCheck() {
        runPerft("3k4/8/8/8/8/8/8/R3K3 w Q - 0 1", 6, 803711);
    }

    @Test
    public void CastleRights() {
        runPerft("r3k2r/1b4bq/8/8/8/8/7B/R3K2R w KQkq - 0 1", 4, 1274206);
    }

    @Test
    public void CastlePrevented() {
        runPerft("r3k2r/8/3Q4/8/8/5q2/8/R3K2R b KQkq - 0 1", 4, 1720476);
    }

    @Test
    public void PromoteOutOfCheck() {
        runPerft("2K2r2/4P3/8/8/8/8/8/3k4 w - - 0 1", 6, 3821001);
    }

    @Test
    public void DiscoveredCheck() {
        runPerft("8/8/1P2K3/8/2n5/1q6/8/5k2 b - - 0 1", 5, 1004658);
    }

    @Test
    public void PromoteToCheck() {
        runPerft("4k3/1P6/8/8/8/8/K7/8 w - - 0 1", 6, 217342);
    }

    @Test
    public void UnderPromoteToCheck() {
        runPerft("8/P1k5/K7/8/8/8/8/8 w - - 0 1", 6, 92683);
    }

    @Test
    public void SelfStalemate() {
        runPerft("K1k5/8/P7/8/8/8/8/8 w - - 0 1", 6, 2217);
    }

    @Test
    public void StalemateAndCheckmate1() {
        runPerft("8/k1P5/8/1K6/8/8/8/8 w - - 0 1", 7, 567584);
    }

    @Test
    public void StalemateAndCheckmate2() {
        runPerft("8/8/2k5/5q2/5n2/8/5K2/8 b - - 0 1", 4, 23527);
    }

    private void runPerft(String fenString, int maxDepth, long expectedNodes) {
        Gameplay fenGame = FEN.fromFenString(fenString);
        Board board = fenGame.getBoard();
        BoardUtils.printBoard(board);
        Gameplay game = new Gameplay(board, PieceType.Colour.WHITE);

        long startTime = System.currentTimeMillis();
        int nodes = Perft.countLeafNodes(game, maxDepth);
        long endTime = System.currentTimeMillis();
        long time = (endTime - startTime) / 1000;

        System.out.println("Total: " + nodes);
        System.out.println("Total time: " + time);
        if (time != 0) {
            System.out.println("Nodes per second: " + nodes / time);
        }
        Assert.assertEquals(nodes, expectedNodes);
    }

    @Test
    public void PerftFiveDeep() {
        Gameplay game = new Gameplay();

        int maxDepth = 5;
        long startTime = System.currentTimeMillis();
        int leafNodes = Perft.countLeafNodes(game, maxDepth);
        long endTime = System.currentTimeMillis();
        long time = (endTime - startTime) / 1000;

        System.out.println("Total: " + leafNodes);
        System.out.println("Total time: " + time);
        System.out.println("Nodes per second: " + leafNodes / time);
        Assert.assertEquals(perftDepthNodesCount[maxDepth], leafNodes);

    }
}
