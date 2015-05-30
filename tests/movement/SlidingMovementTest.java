package movement;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import board.Board;
import board.PieceType;
import board.movement.Move;
import board.movement.sliding.RookMovement;

/**
 * Tests that verify potential pawn move validity.
 */
public class SlidingMovementTest {

    @Test
    public void WhiteRookMovement() {
        /* @formatter:off */
        String[][] boardMatrix = {{"BR", "BN", "BB", "BQ", "BK", "BB", "BN", "BR"},
                                  {"BP", "  ", "BP", "BP", "BP", "BP", "  ", "  "},
                                  {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                  {"  ", "  ", "  ", "WP", "  ", "WR", "BP", "BP"},
                                  {"WP", "WR", "  ", "  ", "  ", "  ", "  ", "  "},
                                  {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                  {"  ", "WP", "WP", "  ", "WP", "  ", "WP", "WP"},
                                  {"  ", "WN", "WB", "WQ", "WK", "WB", "WN", "  "}};
        /* @formatter:on */
        Board board = Board.arrayToBoard(boardMatrix);
        RookMovement movement = new RookMovement(board);
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
    }

    private boolean equalMoveList(List<Move> moves, List<Move> expectedMoves) {
        Assert.assertEquals(moves.size(), expectedMoves.size());

        for (Move move : expectedMoves) {
            if (!moves.contains(move)) {
                return false;
            }
        }

        return true;
    }
}
