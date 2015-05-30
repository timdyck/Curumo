package movement;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import board.Board;
import board.PieceType;
import board.movement.Move;
import board.movement.sliding.BishopMovement;
import board.movement.sliding.QueenMovement;
import board.movement.sliding.RookMovement;

/**
 * Tests that verify potential pawn move validity.
 */
public class SlidingMovementTest {

    @Test
    public void RookMovement() {
        /* @formatter:off */
        String[][] boardMatrix = {{"  ", "BN", "BB", "BQ", "BK", "BB", "BN", "  "},
                                  {"BP", "  ", "BP", "BP", "BP", "BP", "  ", "BP"},
                                  {"BR", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                  {"  ", "  ", "  ", "WP", "  ", "WR", "BP", "BR"},
                                  {"WP", "WR", "  ", "  ", "  ", "  ", "  ", "  "},
                                  {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                  {"  ", "WP", "WP", "  ", "WP", "  ", "WP", "WP"},
                                  {"  ", "WN", "WB", "WQ", "WK", "WB", "WN", "  "}};
        /* @formatter:on */
        Board board = Board.arrayToBoard(boardMatrix);
        RookMovement movement = new RookMovement(board);

        // White rooks
        List<Move> moves = movement.getWhiteMoves();
        List<Move> expectedMoves = new ArrayList<Move>();

        expectedMoves.add(new Move(PieceType.WR, 1, 3, 1, 2));
        expectedMoves.add(new Move(PieceType.WR, 1, 3, 1, 4));
        expectedMoves.add(new Move(PieceType.WR, 1, 3, 1, 5));
        expectedMoves.add(new Move(PieceType.WR, 1, 3, 1, 6));
        expectedMoves.add(new Move(PieceType.WR, 1, 3, 1, 7, true));
        expectedMoves.add(new Move(PieceType.WR, 1, 3, 2, 3));
        expectedMoves.add(new Move(PieceType.WR, 1, 3, 3, 3));
        expectedMoves.add(new Move(PieceType.WR, 1, 3, 4, 3));
        expectedMoves.add(new Move(PieceType.WR, 1, 3, 5, 3));
        expectedMoves.add(new Move(PieceType.WR, 1, 3, 6, 3));
        expectedMoves.add(new Move(PieceType.WR, 1, 3, 7, 3));

        expectedMoves.add(new Move(PieceType.WR, 5, 4, 6, 4, true));
        expectedMoves.add(new Move(PieceType.WR, 5, 4, 5, 6, true));
        expectedMoves.add(new Move(PieceType.WR, 5, 4, 5, 5));
        expectedMoves.add(new Move(PieceType.WR, 5, 4, 4, 4));
        expectedMoves.add(new Move(PieceType.WR, 5, 4, 5, 3));
        expectedMoves.add(new Move(PieceType.WR, 5, 4, 5, 2));
        expectedMoves.add(new Move(PieceType.WR, 5, 4, 5, 1));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));

        // Black rooks
        moves = movement.getBlackMoves();
        expectedMoves.clear();

        expectedMoves.add(new Move(PieceType.BR, 0, 5, 0, 4));
        expectedMoves.add(new Move(PieceType.BR, 0, 5, 0, 3, true));
        expectedMoves.add(new Move(PieceType.BR, 0, 5, 1, 5));
        expectedMoves.add(new Move(PieceType.BR, 0, 5, 2, 5));
        expectedMoves.add(new Move(PieceType.BR, 0, 5, 3, 5));
        expectedMoves.add(new Move(PieceType.BR, 0, 5, 4, 5));
        expectedMoves.add(new Move(PieceType.BR, 0, 5, 5, 5));
        expectedMoves.add(new Move(PieceType.BR, 0, 5, 6, 5));
        expectedMoves.add(new Move(PieceType.BR, 0, 5, 7, 5));

        expectedMoves.add(new Move(PieceType.BR, 7, 4, 7, 5));
        expectedMoves.add(new Move(PieceType.BR, 7, 4, 7, 3));
        expectedMoves.add(new Move(PieceType.BR, 7, 4, 7, 2));
        expectedMoves.add(new Move(PieceType.BR, 7, 4, 7, 1, true));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));
    }

    @Test
    public void BishopMovement() {
        /* @formatter:off */
        String[][] boardMatrix = {{"BR", "BN", "  ", "BQ", "BK", "  ", "BN", "BR"},
                                  {"BP", "BP", "BP", "BP", "BP", "BP", "BP", "BP"},
                                  {"  ", "  ", "  ", "  ", "  ", "BB", "  ", "  "},
                                  {"  ", "  ", "  ", "BB", "  ", "  ", "  ", "  "},
                                  {"  ", "WB", "  ", "  ", "  ", "  ", "  ", "  "},
                                  {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "WB"},
                                  {"WP", "WP", "WP", "WP", "WP", "WP", "WP", "WP"},
                                  {"WR", "WN", "  ", "WQ", "WK", "  ", "WN", "WR"}};
        /* @formatter:on */
        Board board = Board.arrayToBoard(boardMatrix);
        BishopMovement movement = new BishopMovement(board);

        // White bishops
        List<Move> moves = movement.getWhiteMoves();
        List<Move> expectedMoves = new ArrayList<Move>();

        expectedMoves.add(new Move(PieceType.WB, 1, 3, 0, 2));
        expectedMoves.add(new Move(PieceType.WB, 1, 3, 0, 4));
        expectedMoves.add(new Move(PieceType.WB, 1, 3, 2, 2));
        expectedMoves.add(new Move(PieceType.WB, 1, 3, 2, 4));
        expectedMoves.add(new Move(PieceType.WB, 1, 3, 3, 5));
        expectedMoves.add(new Move(PieceType.WB, 1, 3, 4, 6, true));

        expectedMoves.add(new Move(PieceType.WB, 7, 2, 6, 3));
        expectedMoves.add(new Move(PieceType.WB, 7, 2, 5, 4));
        expectedMoves.add(new Move(PieceType.WB, 7, 2, 4, 5));
        expectedMoves.add(new Move(PieceType.WB, 7, 2, 3, 6, true));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));

        // Black bishops
        moves = movement.getBlackMoves();
        expectedMoves.clear();

        expectedMoves.add(new Move(PieceType.BB, 3, 4, 2, 5));
        expectedMoves.add(new Move(PieceType.BB, 3, 4, 2, 3));
        expectedMoves.add(new Move(PieceType.BB, 3, 4, 1, 2));
        expectedMoves.add(new Move(PieceType.BB, 3, 4, 0, 1, true));
        expectedMoves.add(new Move(PieceType.BB, 3, 4, 4, 5));
        expectedMoves.add(new Move(PieceType.BB, 3, 4, 4, 3));
        expectedMoves.add(new Move(PieceType.BB, 3, 4, 5, 2));
        expectedMoves.add(new Move(PieceType.BB, 3, 4, 6, 1, true));

        expectedMoves.add(new Move(PieceType.BB, 5, 5, 4, 4));
        expectedMoves.add(new Move(PieceType.BB, 5, 5, 3, 3));
        expectedMoves.add(new Move(PieceType.BB, 5, 5, 2, 2));
        expectedMoves.add(new Move(PieceType.BB, 5, 5, 1, 1, true));
        expectedMoves.add(new Move(PieceType.BB, 5, 5, 6, 4));
        expectedMoves.add(new Move(PieceType.BB, 5, 5, 7, 3));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));
    }

    @Test
    public void QueenMovement() {
        /* @formatter:off */
        String[][] boardMatrix = {{"BR", "BN", "BB", "  ", "BK", "BB", "BN", "BR"},
                                  {"BP", "BP", "BP", "BP", "BP", "  ", "BP", "BP"},
                                  {"  ", "  ", "  ", "  ", "  ", "BP", "  ", "  "},
                                  {"  ", "  ", "  ", "  ", "  ", "  ", "BQ", "  "},
                                  {"  ", "  ", "WQ", "WP", "  ", "  ", "  ", "  "},
                                  {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                  {"WP", "WP", "WP", "  ", "WP", "WP", "WP", "WP"},
                                  {"WR", "WN", "WB", "  ", "WK", "WB", "WN", "WR"}};
        /* @formatter:on */
        Board board = Board.arrayToBoard(boardMatrix);
        QueenMovement movement = new QueenMovement(board);

        // White queen
        List<Move> moves = movement.getWhiteMoves();
        List<Move> expectedMoves = new ArrayList<Move>();

        expectedMoves.add(new Move(PieceType.WQ, 2, 3, 1, 2));
        expectedMoves.add(new Move(PieceType.WQ, 2, 3, 3, 2));
        expectedMoves.add(new Move(PieceType.WQ, 2, 3, 1, 4));
        expectedMoves.add(new Move(PieceType.WQ, 2, 3, 0, 5));
        expectedMoves.add(new Move(PieceType.WQ, 2, 3, 3, 4));
        expectedMoves.add(new Move(PieceType.WQ, 2, 3, 4, 5));
        expectedMoves.add(new Move(PieceType.WQ, 2, 3, 5, 6));
        expectedMoves.add(new Move(PieceType.WQ, 2, 3, 6, 7, true));

        expectedMoves.add(new Move(PieceType.WQ, 2, 3, 1, 3));
        expectedMoves.add(new Move(PieceType.WQ, 2, 3, 0, 3));
        expectedMoves.add(new Move(PieceType.WQ, 2, 3, 2, 2));
        expectedMoves.add(new Move(PieceType.WQ, 2, 3, 2, 4));
        expectedMoves.add(new Move(PieceType.WQ, 2, 3, 2, 5));
        expectedMoves.add(new Move(PieceType.WQ, 2, 3, 2, 6, true));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));

        // Black queen
        moves = movement.getBlackMoves();
        expectedMoves.clear();

        expectedMoves.add(new Move(PieceType.BQ, 6, 4, 7, 5));
        expectedMoves.add(new Move(PieceType.BQ, 6, 4, 7, 3));
        expectedMoves.add(new Move(PieceType.BQ, 6, 4, 5, 3));
        expectedMoves.add(new Move(PieceType.BQ, 6, 4, 4, 2));
        expectedMoves.add(new Move(PieceType.BQ, 6, 4, 3, 1));
        expectedMoves.add(new Move(PieceType.BQ, 6, 4, 2, 0, true));

        expectedMoves.add(new Move(PieceType.BQ, 6, 4, 6, 5));
        expectedMoves.add(new Move(PieceType.BQ, 6, 4, 7, 4));
        expectedMoves.add(new Move(PieceType.BQ, 6, 4, 5, 4));
        expectedMoves.add(new Move(PieceType.BQ, 6, 4, 4, 4));
        expectedMoves.add(new Move(PieceType.BQ, 6, 4, 3, 4));
        expectedMoves.add(new Move(PieceType.BQ, 6, 4, 2, 4));
        expectedMoves.add(new Move(PieceType.BQ, 6, 4, 1, 4));
        expectedMoves.add(new Move(PieceType.BQ, 6, 4, 0, 4));
        expectedMoves.add(new Move(PieceType.BQ, 6, 4, 6, 3));
        expectedMoves.add(new Move(PieceType.BQ, 6, 4, 6, 2));
        expectedMoves.add(new Move(PieceType.BQ, 6, 4, 6, 1, true));

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
