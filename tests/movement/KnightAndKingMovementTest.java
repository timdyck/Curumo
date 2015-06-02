package movement;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import board.Board;
import board.PieceType;
import board.movement.KingMovement;
import board.movement.KnightMovement;
import board.movement.Move;
import board.movement.Movement;

/**
 * Tests that verify potential knight and king move validity.
 */
public class KnightAndKingMovementTest {

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

    @Test
    public void KingMovement() {
        /* @formatter:off */
        String[][] boardMatrix = {{"BR", "BN", "BB", "BQ", "  ", "  ", "BN", "BR"},
                                  {"BP", "  ", "  ", "BP", "BP", "BP", "BP", "BP"},
                                  {"  ", "  ", "BP", "BB", "  ", "  ", "  ", "  "},
                                  {"  ", "  ", "  ", "  ", "WP", "  ", "  ", "  "},
                                  {"  ", "  ", "WK", "  ", "BK", "  ", "  ", "  "},
                                  {"  ", "BP", "  ", "  ", "  ", "  ", "  ", "BN"},
                                  {"WP", "WP", "WP", "WP", "  ", "WP", "  ", "WP"},
                                  {"WR", "WN", "WB", "WQ", "  ", "  ", "  ", "WR"}};
        /* @formatter:on */
        Board board = Board.arrayToBoard(boardMatrix);
        Movement movement = new Movement(board);
        KingMovement kingMovement = new KingMovement(board, movement.getUnsafeForWhite(), movement.getUnsafeForBlack());

        // White king
        List<Move> moves = kingMovement.getWhiteMoves();
        List<Move> expectedMoves = new ArrayList<Move>();

        expectedMoves.add(new Move(PieceType.WK, 2, 3, 2, 2));
        expectedMoves.add(new Move(PieceType.WK, 2, 3, 1, 2, true));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));

        // Black knight
        moves = kingMovement.getBlackMoves();
        expectedMoves.clear();

        expectedMoves.add(new Move(PieceType.BK, 4, 3, 5, 4));
        expectedMoves.add(new Move(PieceType.BK, 4, 3, 5, 3));
        expectedMoves.add(new Move(PieceType.BK, 4, 3, 4, 4, true));

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