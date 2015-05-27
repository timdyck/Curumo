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
 * Tests that verify potential piece move validity.
 */
public class MovementTest {

    @Test
    public void PawnMovementAndCapture() {
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
        List<Move> moves = movement.getWhiteMoves(Move.getFirstPreviousMove());
        board.printBoard();

        List<Move> expectedMoves = new ArrayList<Move>();
        expectedMoves.add(new Move(PieceType.WP, 2, 1, 3, 0, true));
        expectedMoves.add(new Move(PieceType.WP, 2, 1, 3, 1));
        expectedMoves.add(new Move(PieceType.WP, 2, 4, 3, 3, true));
        expectedMoves.add(new Move(PieceType.WP, 2, 4, 3, 4));
        expectedMoves.add(new Move(PieceType.WP, 3, 5, 4, 4, true));
        expectedMoves.add(new Move(PieceType.WP, 3, 5, 4, 6, true));
        expectedMoves.add(new Move(PieceType.WP, 3, 5, 4, 5));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 2, 6));
        expectedMoves.add(new Move(PieceType.WP, 1, 6, 3, 6));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));

    }

    @Test
    public void PawnEnPassant() {
    }

    @Test
    public void PawnPromotion() {
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
