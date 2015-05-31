package movement;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import board.Board;
import board.PieceType;
import board.movement.KnightMovement;
import board.movement.Move;

/**
 * Tests that verify potential pawn move validity.
 */
public class KnightMovementTest {

    @Test
    public void KnightMovement() {
        /* @formatter:off */
        String[][] boardMatrix = {{"BR", "  ", "BB", "BQ", "BK", "BB", "  ", "BR"},
                                  {"BP", "BP", "BP", "BP", "BP", "BP", "BP", "BP"},
                                  {"  ", "  ", "  ", "BN", "  ", "  ", "  ", "  "},
                                  {"  ", "  ", "  ", "  ", "  ", "  ", "WN", "  "},
                                  {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                  {"BN", "  ", "  ", "  ", "  ", "WN", "  ", "  "},
                                  {"WP", "WP", "WP", "WP", "WP", "WP", "WP", "WP"},
                                  {"WR", "  ", "WB", "WQ", "WK", "WB", "  ", "WR"}};
        /* @formatter:on */
        Board board = Board.arrayToBoard(boardMatrix);
        KnightMovement movement = new KnightMovement(board);

        // White knights
        List<Move> moves = movement.getWhiteMoves();
        List<Move> expectedMoves = new ArrayList<Move>();

        expectedMoves.add(new Move(PieceType.WN, 5, 2, 6, 0));
        expectedMoves.add(new Move(PieceType.WN, 5, 2, 3, 3));
        expectedMoves.add(new Move(PieceType.WN, 5, 2, 4, 4));
        expectedMoves.add(new Move(PieceType.WN, 5, 2, 7, 3));

        expectedMoves.add(new Move(PieceType.WN, 6, 4, 5, 6, true));
        expectedMoves.add(new Move(PieceType.WN, 6, 4, 7, 6, true));
        expectedMoves.add(new Move(PieceType.WN, 6, 4, 4, 5));
        expectedMoves.add(new Move(PieceType.WN, 6, 4, 4, 3));
        expectedMoves.add(new Move(PieceType.WN, 6, 4, 7, 2));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));

        // Black knight
        moves = movement.getBlackMoves();
        expectedMoves.clear();

        expectedMoves.add(new Move(PieceType.BN, 0, 2, 1, 0));
        expectedMoves.add(new Move(PieceType.BN, 0, 2, 1, 4));
        expectedMoves.add(new Move(PieceType.BN, 0, 2, 2, 1, true));
        expectedMoves.add(new Move(PieceType.BN, 0, 2, 2, 3));

        expectedMoves.add(new Move(PieceType.BN, 3, 5, 1, 4));
        expectedMoves.add(new Move(PieceType.BN, 3, 5, 5, 4));
        expectedMoves.add(new Move(PieceType.BN, 3, 5, 2, 3));
        expectedMoves.add(new Move(PieceType.BN, 3, 5, 4, 3));

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
