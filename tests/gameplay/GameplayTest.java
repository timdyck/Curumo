package gameplay;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import board.Board;
import board.BoardUtils;
import board.PieceType;
import board.movement.Move;
import board.movement.MoveType;

public class GameplayTest {

    @Test
    public void SafeMoves() {
        /* @formatter:off */
        String[][] boardMatrix = {{"BR", "BN", "BB", "BQ", "BK", "BB", "BN", "BR"},
                                  {"  ", "BP", "BP", "BP", "BP", "BP", "BP", "BP"},
                                  {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                  {"BP", "WB", "  ", "  ", "  ", "  ", "  ", "WQ"},
                                  {"  ", "  ", "  ", "  ", "WP", "  ", "  ", "  "},
                                  {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                  {"WP", "WP", "WP", "WP", "  ", "WP", "WP", "WP"},
                                  {"WR", "WN", "WB", "  ", "WK", "  ", "WN", "WR"}};
        /* @formatter:on */
        Board board = BoardUtils.arrayToBoard(boardMatrix);
        Gameplay game = new Gameplay(board, PieceType.Colour.BLACK);

        // Moves that will put black in check
        List<Move> moves = game.getSafeBlackMoves();
        List<Move> impossibleMoves = new ArrayList<Move>();

        impossibleMoves.add(new Move(PieceType.BP, 3, 6, 3, 5));
        impossibleMoves.add(new Move(PieceType.BP, 3, 6, 3, 4));
        impossibleMoves.add(new Move(PieceType.BP, 5, 6, 5, 5));
        impossibleMoves.add(new Move(PieceType.BP, 5, 6, 5, 4));

        Assert.assertTrue(impossibleMoveList(moves, impossibleMoves));

        /* @formatter:off */
        String[][] boardMatrix2 = {{"BR", "BN", "BB", "BQ", "BK", "BB", "BN", "  "},
                                   {"BP", "BP", "BP", "  ", "BP", "  ", "BP", "BP"},
                                   {"  ", "  ", "  ", "  ", "BR", "  ", "  ", "  "},
                                   {"  ", "  ", "  ", "BP", "  ", "BP", "  ", "  "},
                                   {"  ", "  ", "  ", "  ", "WP", "  ", "  ", "  "},
                                   {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                   {"WP", "WP", "WP", "WP", "  ", "WP", "WP", "WP"},
                                   {"WR", "WN", "WB", "WQ", "WK", "WB", "WN", "WR"}};
        /* @formatter:on */
        board = BoardUtils.arrayToBoard(boardMatrix2);
        game = new Gameplay(board, PieceType.Colour.WHITE);

        // Moves that will put white in check
        moves = game.getSafeWhiteMoves();
        impossibleMoves = new ArrayList<Move>();

        impossibleMoves.add(new Move(PieceType.WP, 4, 3, 3, 4, MoveType.CAPTURE, PieceType.BP));
        impossibleMoves.add(new Move(PieceType.WP, 4, 3, 5, 4, MoveType.CAPTURE, PieceType.BP));

        Assert.assertTrue(impossibleMoveList(moves, impossibleMoves));
    }

    private boolean impossibleMoveList(List<Move> moves, List<Move> impossibleMoves) {
        for (Move move : impossibleMoves) {
            if (moves.contains(move)) {
                System.out.println("This move puts the king in check:");
                System.out.println(move);
                return false;
            }
        }

        return true;
    }

}