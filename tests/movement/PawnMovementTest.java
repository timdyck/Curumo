package movement;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import board.Board;
import board.PieceType;
import board.movement.Move;
import board.movement.PawnMovement;

/**
 * Tests that verify potential pawn move validity.
 */
public class PawnMovementTest {

    /*
     * WHITE MOVES
     */

    @Test
    public void WhitePawnMovementAndCapture() {
        /* @formatter:off */
        String[][] boardMatrix = {{"BR", "  ", "BB", "BQ", "BK", "BB", "BN", "BR"},
                                  {"  ", "BP", "BP", "  ", "  ", "BP", "  ", "  "},
                                  {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                  {"  ", "  ", "  ", "  ", "BP", "  ", "BP", "  "},
                                  {"BP", "  ", "  ", "BP", "  ", "WP", "  ", "BP"},
                                  {"WP", "WP", "BN", "WP", "WP", "  ", "  ", "WP"},
                                  {"  ", "  ", "WP", "  ", "  ", "  ", "WP", "  "},
                                  {"WR", "WN", "WB", "WQ", "WK", "WB", "WN", "WR"}};
        /* @formatter:on */
        Board board = Board.arrayToBoard(boardMatrix);
        PawnMovement movement = new PawnMovement(board);
        List<Move> moves = movement.getWhiteMoves();

        List<Move> expectedMoves = new ArrayList<Move>();
        expectedMoves.add(new Move(PieceType.WP, 1, 2, 0, 3, true));
        expectedMoves.add(new Move(PieceType.WP, 1, 2, 1, 3));
        expectedMoves.add(new Move(PieceType.WP, 4, 2, 3, 3, true));
        expectedMoves.add(new Move(PieceType.WP, 4, 2, 4, 3));
        expectedMoves.add(new Move(PieceType.WP, 5, 3, 4, 4, true));
        expectedMoves.add(new Move(PieceType.WP, 5, 3, 6, 4, true));
        expectedMoves.add(new Move(PieceType.WP, 5, 3, 5, 4));
        expectedMoves.add(new Move(PieceType.WP, 6, 1, 6, 2));
        expectedMoves.add(new Move(PieceType.WP, 6, 1, 6, 3));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));

    }

    @Test
    public void WhitePawnEnPassant() {
        /* @formatter:off */
        String[][] boardMatrix = {{"BR", "  ", "BB", "BQ", "BK", "BB", "BN", "BR"},
                                  {"  ", "  ", "BP", "  ", "  ", "BP", "  ", "  "},
                                  {"  ", "BP", "WP", "  ", "  ", "  ", "  ", "BP"},
                                  {"BP", "  ", "  ", "  ", "BP", "WP", "BP", "WP"},
                                  {"  ", "WP", "  ", "BP", "WP", "  ", "  ", "  "},
                                  {"WP", "  ", "BN", "WP", "  ", "  ", "  ", "  "},
                                  {"  ", "  ", "  ", "  ", "  ", "  ", "WP", "  "},
                                  {"WR", "WN", "WB", "WQ", "WK", "WB", "WN", "WR"}};
        /* @formatter:on */
        Board board = Board.arrayToBoard(boardMatrix);
        PawnMovement movement = new PawnMovement(board);

        List<Move> expectedMoves = new ArrayList<Move>();
        expectedMoves.add(new Move(PieceType.WP, 0, 2, 0, 3));
        expectedMoves.add(new Move(PieceType.WP, 1, 3, 1, 4));
        expectedMoves.add(new Move(PieceType.WP, 1, 3, 0, 4, true));
        expectedMoves.add(new Move(PieceType.WP, 5, 4, 5, 5));
        expectedMoves.add(new Move(PieceType.WP, 6, 1, 6, 2));
        expectedMoves.add(new Move(PieceType.WP, 6, 1, 6, 3));

        // One opportunity
        List<Move> moves = movement.getWhiteMoves(new Move(PieceType.BP, 4, 6, 4, 4));
        expectedMoves.add(new Move(PieceType.WP, 5, 4, 4, 5, true));
        Assert.assertTrue(equalMoveList(moves, expectedMoves));
        expectedMoves.remove(new Move(PieceType.WP, 5, 4, 4, 5, true));

        // Two opportunities
        moves = movement.getWhiteMoves(new Move(PieceType.BP, 6, 6, 6, 4));
        expectedMoves.add(new Move(PieceType.WP, 5, 4, 6, 5, true));
        expectedMoves.add(new Move(PieceType.WP, 7, 4, 6, 5, true));
        Assert.assertTrue(equalMoveList(moves, expectedMoves));
        expectedMoves.remove(new Move(PieceType.WP, 5, 4, 6, 5, true));
        expectedMoves.remove(new Move(PieceType.WP, 7, 4, 6, 5, true));

        // No opportunites
        moves = movement.getWhiteMoves(new Move(PieceType.BP, 0, 6, 0, 4));
        Assert.assertTrue(equalMoveList(moves, expectedMoves));

        moves = movement.getWhiteMoves(new Move(PieceType.BP, 1, 6, 1, 5));
        Assert.assertTrue(equalMoveList(moves, expectedMoves));

        moves = movement.getWhiteMoves(new Move(PieceType.BP, 3, 4, 3, 3));
        Assert.assertTrue(equalMoveList(moves, expectedMoves));
    }

    @Test
    public void WhitePawnPromotion() {
        /* @formatter:off */
        String[][] boardMatrix = {{"BR", "  ", "BB", "BQ", "BK", "BB", "BN", "  "},
                                  {"WP", "WP", "BP", "  ", "  ", "BP", "  ", "WP"},
                                  {"  ", "BP", "WP", "  ", "  ", "WP", "  ", "BP"},
                                  {"BP", "  ", "  ", "  ", "BP", "  ", "BP", "  "},
                                  {"  ", "  ", "  ", "BP", "WP", "  ", "WP", "  "},
                                  {"  ", "  ", "BN", "WP", "  ", "  ", "  ", "  "},
                                  {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                  {"WR", "WN", "WB", "WQ", "WK", "WB", "WN", "WR"}};
        /* @formatter:on */
        Board board = Board.arrayToBoard(boardMatrix);
        PawnMovement movement = new PawnMovement(board);
        List<Move> moves = movement.getWhiteMoves();

        List<Move> expectedMoves = new ArrayList<Move>();
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 1, 7, PieceType.WR));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 1, 7, PieceType.WN));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 1, 7, PieceType.WB));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 1, 7, PieceType.WQ));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 0, 7, true, PieceType.WR));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 0, 7, true, PieceType.WN));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 0, 7, true, PieceType.WB));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 0, 7, true, PieceType.WQ));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 2, 7, true, PieceType.WR));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 2, 7, true, PieceType.WN));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 2, 7, true, PieceType.WB));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 2, 7, true, PieceType.WQ));
        expectedMoves.add(new Move(PieceType.WP, 7, 6, 6, 7, true, PieceType.WR));
        expectedMoves.add(new Move(PieceType.WP, 7, 6, 6, 7, true, PieceType.WN));
        expectedMoves.add(new Move(PieceType.WP, 7, 6, 6, 7, true, PieceType.WB));
        expectedMoves.add(new Move(PieceType.WP, 7, 6, 6, 7, true, PieceType.WQ));
        expectedMoves.add(new Move(PieceType.WP, 7, 6, 7, 7, PieceType.WR));
        expectedMoves.add(new Move(PieceType.WP, 7, 6, 7, 7, PieceType.WN));
        expectedMoves.add(new Move(PieceType.WP, 7, 6, 7, 7, PieceType.WB));
        expectedMoves.add(new Move(PieceType.WP, 7, 6, 7, 7, PieceType.WQ));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));
    }

    /*
     * BLACK MOVES
     */

    @Test
    public void BlackPawnMovementAndCapture() {
        /* @formatter:off */
        String[][] boardMatrix = {{"BR", "BN", "BB", "BQ", "BK", "BB", "BN", "BR"},
                                  {"  ", "BP", "  ", "  ", "  ", "BP", "  ", "  "},
                                  {"BP", "  ", "  ", "BP", "BP", "WP", "BP", "BP"},
                                  {"WP", "  ", "BP", "  ", "WP", "  ", "  ", "WP"},
                                  {"  ", "WP", "  ", "WP", "  ", "  ", "  ", "  "},
                                  {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                  {"  ", "  ", "WP", "  ", "  ", "  ", "WP", "  "},
                                  {"WR", "WN", "WB", "WQ", "WK", "WB", "WN", "WR"}};
        /* @formatter:on */
        Board board = Board.arrayToBoard(boardMatrix);
        PawnMovement movement = new PawnMovement(board);
        List<Move> moves = movement.getBlackMoves();

        List<Move> expectedMoves = new ArrayList<Move>();
        expectedMoves.add(new Move(PieceType.BP, 1, 6, 1, 5));
        expectedMoves.add(new Move(PieceType.BP, 1, 6, 1, 4));
        expectedMoves.add(new Move(PieceType.BP, 2, 4, 2, 3));
        expectedMoves.add(new Move(PieceType.BP, 2, 4, 1, 3, true));
        expectedMoves.add(new Move(PieceType.BP, 2, 4, 3, 3, true));
        expectedMoves.add(new Move(PieceType.BP, 3, 5, 3, 4));
        expectedMoves.add(new Move(PieceType.BP, 3, 5, 4, 4, true));
        expectedMoves.add(new Move(PieceType.BP, 6, 5, 6, 4));
        expectedMoves.add(new Move(PieceType.BP, 6, 5, 7, 4, true));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));

    }

    @Test
    public void BlackPawnEnPassant() {
        /* @formatter:off */
        String[][] boardMatrix = {{"BR", "BN", "BB", "BQ", "BK", "BB", "BN", "BR"},
                                  {"  ", "BP", "  ", "  ", "  ", "BP", "  ", "  "},
                                  {"  ", "  ", "  ", "  ", "BP", "WN", "  ", "BP"},
                                  {"  ", "  ", "  ", "BP", "WP", "  ", "BP", "  "},
                                  {"BP", "WP", "BP", "WP", "  ", "  ", "  ", "WP"},
                                  {"WP", "  ", "  ", "  ", "  ", "BP", "WP", "  "},
                                  {"  ", "  ", "WP", "  ", "  ", "WP", "  ", "  "},
                                  {"WR", "WN", "WB", "WQ", "WK", "WB", "  ", "WR"}};
        /* @formatter:on */
        Board board = Board.arrayToBoard(boardMatrix);
        PawnMovement movement = new PawnMovement(board);

        List<Move> expectedMoves = new ArrayList<Move>();
        expectedMoves.add(new Move(PieceType.BP, 1, 6, 1, 5));
        expectedMoves.add(new Move(PieceType.BP, 1, 6, 1, 4));
        expectedMoves.add(new Move(PieceType.BP, 2, 3, 2, 2));
        expectedMoves.add(new Move(PieceType.BP, 6, 4, 6, 3));
        expectedMoves.add(new Move(PieceType.BP, 6, 4, 7, 3, true));
        expectedMoves.add(new Move(PieceType.BP, 7, 5, 7, 4));

        // One opportunity
        List<Move> moves = movement.getBlackMoves(new Move(PieceType.WP, 3, 1, 3, 3));
        expectedMoves.add(new Move(PieceType.BP, 2, 3, 3, 2, true));
        Assert.assertTrue(equalMoveList(moves, expectedMoves));
        expectedMoves.remove(new Move(PieceType.BP, 2, 3, 3, 2, true));

        // Two opportunities
        moves = movement.getBlackMoves(new Move(PieceType.WP, 1, 1, 1, 3));
        expectedMoves.add(new Move(PieceType.BP, 0, 3, 1, 2, true));
        expectedMoves.add(new Move(PieceType.BP, 2, 3, 1, 2, true));
        Assert.assertTrue(equalMoveList(moves, expectedMoves));
        expectedMoves.remove(new Move(PieceType.BP, 0, 3, 1, 2, true));
        expectedMoves.remove(new Move(PieceType.BP, 2, 3, 1, 2, true));

        // No opportunites
        moves = movement.getBlackMoves(new Move(PieceType.WP, 7, 1, 7, 3));
        Assert.assertTrue(equalMoveList(moves, expectedMoves));

        moves = movement.getBlackMoves(new Move(PieceType.WP, 6, 1, 6, 2));
        Assert.assertTrue(equalMoveList(moves, expectedMoves));

        moves = movement.getBlackMoves(new Move(PieceType.WP, 4, 3, 4, 4));
        Assert.assertTrue(equalMoveList(moves, expectedMoves));
    }

    @Test
    public void BlackPawnPromotion() {
        /* @formatter:off */
        String[][] boardMatrix = {{"BR", "BN", "BB", "BQ", "BK", "BB", "BN", "BR"},
                                  {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                  {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                  {"  ", "BP", "  ", "  ", "BP", "WN", "  ", "  "},
                                  {"  ", "WP", "  ", "BP", "WP", "  ", "  ", "WP"},
                                  {"WP", "  ", "BP", "WP", "  ", "BP", "WP", "  "},
                                  {"BP", "  ", "WP", "  ", "  ", "WP", "BP", "BP"},
                                  {"  ", "WN", "WB", "WQ", "WK", "WB", "  ", "WR"}};
        /* @formatter:on */
        Board board = Board.arrayToBoard(boardMatrix);
        PawnMovement movement = new PawnMovement(board);
        List<Move> moves = movement.getBlackMoves();

        List<Move> expectedMoves = new ArrayList<Move>();
        expectedMoves.add(new Move(PieceType.BP, 0, 1, 0, 0, PieceType.BR));
        expectedMoves.add(new Move(PieceType.BP, 0, 1, 0, 0, PieceType.BN));
        expectedMoves.add(new Move(PieceType.BP, 0, 1, 0, 0, PieceType.BB));
        expectedMoves.add(new Move(PieceType.BP, 0, 1, 0, 0, PieceType.BQ));
        expectedMoves.add(new Move(PieceType.BP, 0, 1, 1, 0, true, PieceType.BR));
        expectedMoves.add(new Move(PieceType.BP, 0, 1, 1, 0, true, PieceType.BN));
        expectedMoves.add(new Move(PieceType.BP, 0, 1, 1, 0, true, PieceType.BB));
        expectedMoves.add(new Move(PieceType.BP, 0, 1, 1, 0, true, PieceType.BQ));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 5, 0, true, PieceType.BR));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 5, 0, true, PieceType.BN));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 5, 0, true, PieceType.BB));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 5, 0, true, PieceType.BQ));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 7, 0, true, PieceType.BR));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 7, 0, true, PieceType.BN));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 7, 0, true, PieceType.BB));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 7, 0, true, PieceType.BQ));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 6, 0, PieceType.BR));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 6, 0, PieceType.BN));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 6, 0, PieceType.BB));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 6, 0, PieceType.BQ));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));
    }

    private boolean equalMoveList(List<Move> moves, List<Move> expectedMoves) {
        Assert.assertEquals(moves.size(), expectedMoves.size());

        for (Move move : expectedMoves) {
            if (!moves.contains(move)) {
                System.out.println("Can't find this move:");
                move.printMove();
                return false;
            }
        }

        return true;
    }
}
