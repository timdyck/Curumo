package movement;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import board.Board;
import board.PieceType;
import board.movement.Move;
import board.movement.MoveType;
import board.movement.PawnMovement;

/**
 * Tests that verify potential pawn move validity.
 */
public class PawnMovementTest {

    /**************
     * WHITE MOVES
     **************/

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
        expectedMoves.add(new Move(PieceType.WP, 1, 2, 0, 3, MoveType.CAPTURE, PieceType.BP));
        expectedMoves.add(new Move(PieceType.WP, 1, 2, 1, 3));
        expectedMoves.add(new Move(PieceType.WP, 4, 2, 3, 3, MoveType.CAPTURE, PieceType.BP));
        expectedMoves.add(new Move(PieceType.WP, 4, 2, 4, 3));
        expectedMoves.add(new Move(PieceType.WP, 5, 3, 4, 4, MoveType.CAPTURE, PieceType.BP));
        expectedMoves.add(new Move(PieceType.WP, 5, 3, 6, 4, MoveType.CAPTURE, PieceType.BP));
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

        List<Move> expectedMoves = new ArrayList<Move>();
        expectedMoves.add(new Move(PieceType.WP, 0, 2, 0, 3));
        expectedMoves.add(new Move(PieceType.WP, 1, 3, 1, 4));
        expectedMoves.add(new Move(PieceType.WP, 1, 3, 0, 4, MoveType.CAPTURE, PieceType.BP));
        expectedMoves.add(new Move(PieceType.WP, 5, 4, 5, 5));
        expectedMoves.add(new Move(PieceType.WP, 6, 1, 6, 2));
        expectedMoves.add(new Move(PieceType.WP, 6, 1, 6, 3));

        // One opportunity
        PawnMovement movement = new PawnMovement(board, new Move(PieceType.BP, 4, 6, 4, 4));
        List<Move> moves = movement.getWhiteMoves();

        expectedMoves.add(new Move(PieceType.WP, 5, 4, 4, 5, MoveType.EN_PASSANT));
        Assert.assertTrue(equalMoveList(moves, expectedMoves));
        expectedMoves.remove(new Move(PieceType.WP, 5, 4, 4, 5, MoveType.EN_PASSANT));

        // Two opportunities
        movement = new PawnMovement(board, new Move(PieceType.BP, 6, 6, 6, 4));
        moves = movement.getWhiteMoves();

        expectedMoves.add(new Move(PieceType.WP, 5, 4, 6, 5, MoveType.EN_PASSANT));
        expectedMoves.add(new Move(PieceType.WP, 7, 4, 6, 5, MoveType.EN_PASSANT));
        Assert.assertTrue(equalMoveList(moves, expectedMoves));
        expectedMoves.remove(new Move(PieceType.WP, 5, 4, 6, 5, MoveType.EN_PASSANT));
        expectedMoves.remove(new Move(PieceType.WP, 7, 4, 6, 5, MoveType.EN_PASSANT));

        // No opportunites
        movement = new PawnMovement(board, new Move(PieceType.BP, 0, 6, 0, 4));
        moves = movement.getWhiteMoves();
        Assert.assertTrue(equalMoveList(moves, expectedMoves));

        movement = new PawnMovement(board, new Move(PieceType.BP, 1, 6, 1, 5));
        moves = movement.getWhiteMoves();
        Assert.assertTrue(equalMoveList(moves, expectedMoves));

        movement = new PawnMovement(board, new Move(PieceType.BP, 3, 4, 3, 3));
        moves = movement.getWhiteMoves();
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
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 1, 7, MoveType.PROMOTION, PieceType.WR));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 1, 7, MoveType.PROMOTION, PieceType.WN));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 1, 7, MoveType.PROMOTION, PieceType.WB));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 1, 7, MoveType.PROMOTION, PieceType.WQ));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 0, 7, MoveType.CAPTURE_AND_PROMOTION, PieceType.BR, PieceType.WR));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 0, 7, MoveType.CAPTURE_AND_PROMOTION, PieceType.BR, PieceType.WN));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 0, 7, MoveType.CAPTURE_AND_PROMOTION, PieceType.BR, PieceType.WB));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 0, 7, MoveType.CAPTURE_AND_PROMOTION, PieceType.BR, PieceType.WQ));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 2, 7, MoveType.CAPTURE_AND_PROMOTION, PieceType.BB, PieceType.WR));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 2, 7, MoveType.CAPTURE_AND_PROMOTION, PieceType.BB, PieceType.WN));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 2, 7, MoveType.CAPTURE_AND_PROMOTION, PieceType.BB, PieceType.WB));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 2, 7, MoveType.CAPTURE_AND_PROMOTION, PieceType.BB, PieceType.WQ));
        expectedMoves.add(new Move(PieceType.WP, 7, 6, 6, 7, MoveType.CAPTURE_AND_PROMOTION, PieceType.BN, PieceType.WR));
        expectedMoves.add(new Move(PieceType.WP, 7, 6, 6, 7, MoveType.CAPTURE_AND_PROMOTION, PieceType.BN, PieceType.WN));
        expectedMoves.add(new Move(PieceType.WP, 7, 6, 6, 7, MoveType.CAPTURE_AND_PROMOTION, PieceType.BN, PieceType.WB));
        expectedMoves.add(new Move(PieceType.WP, 7, 6, 6, 7, MoveType.CAPTURE_AND_PROMOTION, PieceType.BN, PieceType.WQ));
        expectedMoves.add(new Move(PieceType.WP, 7, 6, 7, 7, MoveType.PROMOTION, PieceType.WR));
        expectedMoves.add(new Move(PieceType.WP, 7, 6, 7, 7, MoveType.PROMOTION, PieceType.WN));
        expectedMoves.add(new Move(PieceType.WP, 7, 6, 7, 7, MoveType.PROMOTION, PieceType.WB));
        expectedMoves.add(new Move(PieceType.WP, 7, 6, 7, 7, MoveType.PROMOTION, PieceType.WQ));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));
    }

    /**************
     * BLACK MOVES
     **************/

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
        expectedMoves.add(new Move(PieceType.BP, 2, 4, 1, 3, MoveType.CAPTURE, PieceType.WP));
        expectedMoves.add(new Move(PieceType.BP, 2, 4, 3, 3, MoveType.CAPTURE, PieceType.WP));
        expectedMoves.add(new Move(PieceType.BP, 3, 5, 3, 4));
        expectedMoves.add(new Move(PieceType.BP, 3, 5, 4, 4, MoveType.CAPTURE, PieceType.WP));
        expectedMoves.add(new Move(PieceType.BP, 6, 5, 6, 4));
        expectedMoves.add(new Move(PieceType.BP, 6, 5, 7, 4, MoveType.CAPTURE, PieceType.WP));

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

        List<Move> expectedMoves = new ArrayList<Move>();
        expectedMoves.add(new Move(PieceType.BP, 1, 6, 1, 5));
        expectedMoves.add(new Move(PieceType.BP, 1, 6, 1, 4));
        expectedMoves.add(new Move(PieceType.BP, 2, 3, 2, 2));
        expectedMoves.add(new Move(PieceType.BP, 6, 4, 6, 3));
        expectedMoves.add(new Move(PieceType.BP, 6, 4, 7, 3, MoveType.CAPTURE, PieceType.WP));
        expectedMoves.add(new Move(PieceType.BP, 7, 5, 7, 4));

        // One opportunity
        PawnMovement movement = new PawnMovement(board, new Move(PieceType.WP, 3, 1, 3, 3));
        List<Move> moves = movement.getBlackMoves();

        expectedMoves.add(new Move(PieceType.BP, 2, 3, 3, 2, MoveType.EN_PASSANT));
        Assert.assertTrue(equalMoveList(moves, expectedMoves));
        expectedMoves.remove(new Move(PieceType.BP, 2, 3, 3, 2, MoveType.EN_PASSANT));

        // Two opportunities
        movement = new PawnMovement(board, new Move(PieceType.WP, 1, 1, 1, 3));

        moves = movement.getBlackMoves();
        expectedMoves.add(new Move(PieceType.BP, 0, 3, 1, 2, MoveType.EN_PASSANT));
        expectedMoves.add(new Move(PieceType.BP, 2, 3, 1, 2, MoveType.EN_PASSANT));
        Assert.assertTrue(equalMoveList(moves, expectedMoves));
        expectedMoves.remove(new Move(PieceType.BP, 0, 3, 1, 2, MoveType.EN_PASSANT));
        expectedMoves.remove(new Move(PieceType.BP, 2, 3, 1, 2, MoveType.EN_PASSANT));

        // No opportunites
        movement = new PawnMovement(board, new Move(PieceType.WP, 7, 1, 7, 3));
        moves = movement.getBlackMoves();
        Assert.assertTrue(equalMoveList(moves, expectedMoves));

        movement = new PawnMovement(board, new Move(PieceType.WP, 6, 1, 6, 2));
        moves = movement.getBlackMoves();
        Assert.assertTrue(equalMoveList(moves, expectedMoves));

        movement = new PawnMovement(board, new Move(PieceType.WP, 4, 3, 4, 4));
        moves = movement.getBlackMoves();
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
        expectedMoves.add(new Move(PieceType.BP, 0, 1, 0, 0, MoveType.PROMOTION, PieceType.BR));
        expectedMoves.add(new Move(PieceType.BP, 0, 1, 0, 0, MoveType.PROMOTION, PieceType.BN));
        expectedMoves.add(new Move(PieceType.BP, 0, 1, 0, 0, MoveType.PROMOTION, PieceType.BB));
        expectedMoves.add(new Move(PieceType.BP, 0, 1, 0, 0, MoveType.PROMOTION, PieceType.BQ));
        expectedMoves.add(new Move(PieceType.BP, 0, 1, 1, 0, MoveType.CAPTURE_AND_PROMOTION, PieceType.WN, PieceType.BR));
        expectedMoves.add(new Move(PieceType.BP, 0, 1, 1, 0, MoveType.CAPTURE_AND_PROMOTION, PieceType.WN, PieceType.BN));
        expectedMoves.add(new Move(PieceType.BP, 0, 1, 1, 0, MoveType.CAPTURE_AND_PROMOTION, PieceType.WN, PieceType.BB));
        expectedMoves.add(new Move(PieceType.BP, 0, 1, 1, 0, MoveType.CAPTURE_AND_PROMOTION, PieceType.WN, PieceType.BQ));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 5, 0, MoveType.CAPTURE_AND_PROMOTION, PieceType.WB, PieceType.BR));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 5, 0, MoveType.CAPTURE_AND_PROMOTION, PieceType.WB, PieceType.BN));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 5, 0, MoveType.CAPTURE_AND_PROMOTION, PieceType.WB, PieceType.BB));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 5, 0, MoveType.CAPTURE_AND_PROMOTION, PieceType.WB, PieceType.BQ));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 7, 0, MoveType.CAPTURE_AND_PROMOTION, PieceType.WR, PieceType.BR));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 7, 0, MoveType.CAPTURE_AND_PROMOTION, PieceType.WR, PieceType.BN));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 7, 0, MoveType.CAPTURE_AND_PROMOTION, PieceType.WR, PieceType.BB));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 7, 0, MoveType.CAPTURE_AND_PROMOTION, PieceType.WR, PieceType.BQ));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 6, 0, MoveType.PROMOTION, PieceType.BR));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 6, 0, MoveType.PROMOTION, PieceType.BN));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 6, 0, MoveType.PROMOTION, PieceType.BB));
        expectedMoves.add(new Move(PieceType.BP, 6, 1, 6, 0, MoveType.PROMOTION, PieceType.BQ));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));
    }

    private boolean equalMoveList(List<Move> moves, List<Move> expectedMoves) {
        Assert.assertEquals(moves.size(), expectedMoves.size());

        for (Move move : expectedMoves) {
            if (!moves.contains(move)) {
                System.out.println("Can't find this move:");
                System.out.println(move);
                return false;
            }
        }

        return true;
    }
}
