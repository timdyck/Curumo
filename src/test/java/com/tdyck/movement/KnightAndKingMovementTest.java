package com.tdyck.movement;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.tdyck.board.Board;
import com.tdyck.board.BoardUtils;
import com.tdyck.board.FEN;
import com.tdyck.board.PieceType;
import com.tdyck.board.movement.KingMovement;
import com.tdyck.board.movement.KnightMovement;
import com.tdyck.board.movement.Move;
import com.tdyck.board.movement.MoveType;
import com.tdyck.board.movement.Movement;
import com.tdyck.gameplay.Gameplay;

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
        Board board = BoardUtils.arrayToBoard(boardMatrix);
        KnightMovement movement = new KnightMovement(board);

        // White knights
        List<Move> moves = movement.getWhiteMoves();
        List<Move> expectedMoves = new ArrayList<Move>();

        expectedMoves.add(new Move(PieceType.WN, 5, 2, 6, 0));
        expectedMoves.add(new Move(PieceType.WN, 5, 2, 3, 3));
        expectedMoves.add(new Move(PieceType.WN, 5, 2, 4, 4));
        expectedMoves.add(new Move(PieceType.WN, 5, 2, 7, 3));

        expectedMoves.add(new Move(PieceType.WN, 6, 4, 5, 6, MoveType.CAPTURE, PieceType.BP));
        expectedMoves.add(new Move(PieceType.WN, 6, 4, 7, 6, MoveType.CAPTURE, PieceType.BP));
        expectedMoves.add(new Move(PieceType.WN, 6, 4, 4, 5));
        expectedMoves.add(new Move(PieceType.WN, 6, 4, 4, 3));
        expectedMoves.add(new Move(PieceType.WN, 6, 4, 7, 2));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));

        // Black knight
        moves = movement.getBlackMoves();
        expectedMoves.clear();

        expectedMoves.add(new Move(PieceType.BN, 0, 2, 1, 0));
        expectedMoves.add(new Move(PieceType.BN, 0, 2, 1, 4));
        expectedMoves.add(new Move(PieceType.BN, 0, 2, 2, 1, MoveType.CAPTURE, PieceType.WP));
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
        Board board = BoardUtils.arrayToBoard(boardMatrix);
        Movement movement = new Movement(board);
        KingMovement kingMovement = new KingMovement(board, movement.getUnsafeForWhite(), movement.getUnsafeForBlack());

        // White king
        List<Move> moves = kingMovement.getWhiteMoves();
        List<Move> expectedMoves = new ArrayList<Move>();

        expectedMoves.add(new Move(PieceType.WK, 2, 3, 2, 2));
        expectedMoves.add(new Move(PieceType.WK, 2, 3, 1, 2, MoveType.CAPTURE, PieceType.BP));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));

        // Black knight
        moves = kingMovement.getBlackMoves();
        expectedMoves.clear();

        expectedMoves.add(new Move(PieceType.BK, 4, 3, 5, 4));
        expectedMoves.add(new Move(PieceType.BK, 4, 3, 5, 3));
        expectedMoves.add(new Move(PieceType.BK, 4, 3, 4, 4, MoveType.CAPTURE, PieceType.WP));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));
    }

    @Test
    public void Castling() {
        /* @formatter:off */
        String[][] boardMatrix = {{"BR", "  ", "  ", "  ", "BK", "  ", "  ", "BR"},
                                  {"BP", "BP", "BP", "BP", "BP", "BP", "BP", "BP"},
                                  {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                  {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                  {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                  {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                  {"WP", "WP", "WP", "WP", "WP", "WP", "WP", "WP"},
                                  {"WR", "  ", "  ", "  ", "WK", "  ", "  ", "WR"}};
        /* @formatter:on */
        Board board = BoardUtils.arrayToBoard(boardMatrix);
        Movement movement = new Movement(board);
        KingMovement kingMovement = new KingMovement(board, movement.getUnsafeForWhite(), movement.getUnsafeForBlack());

        // White king
        List<Move> moves = kingMovement.getWhiteMoves();
        List<Move> expectedMoves = new ArrayList<Move>();

        expectedMoves.add(new Move(PieceType.WK, 4, 0, 5, 0));
        expectedMoves.add(new Move(PieceType.WK, 4, 0, 6, 0, MoveType.CASTLE));
        expectedMoves.add(new Move(PieceType.WK, 4, 0, 3, 0));
        expectedMoves.add(new Move(PieceType.WK, 4, 0, 2, 0, MoveType.CASTLE));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));

        // Black knight
        moves = kingMovement.getBlackMoves();
        expectedMoves.clear();

        expectedMoves.add(new Move(PieceType.BK, 4, 7, 5, 7));
        expectedMoves.add(new Move(PieceType.BK, 4, 7, 6, 7, MoveType.CASTLE));
        expectedMoves.add(new Move(PieceType.BK, 4, 7, 3, 7));
        expectedMoves.add(new Move(PieceType.BK, 4, 7, 2, 7, MoveType.CASTLE));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));

        /* @formatter:off */
        String[][] boardMatrix2 = {{"BR", "  ", "BB", "  ", "BK", "  ", "  ", "BR"},
                                   {"BP", "BP", "BP", "BP", "BP", "BP", "BP", "BP"},
                                   {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                   {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                   {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                   {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "BN"},
                                   {"WP", "WP", "WP", "WP", "WP", "WP", "  ", "WP"},
                                   {"WR", "  ", "  ", "  ", "WK", "WB", "  ", "WR"}};
        /* @formatter:on */
        board = BoardUtils.arrayToBoard(boardMatrix2);
        movement = new Movement(board);
        kingMovement = new KingMovement(board, movement.getUnsafeForWhite(), movement.getUnsafeForBlack());

        // White king
        moves = kingMovement.getWhiteMoves();
        expectedMoves = new ArrayList<Move>();

        expectedMoves.add(new Move(PieceType.WK, 4, 0, 3, 0));
        expectedMoves.add(new Move(PieceType.WK, 4, 0, 2, 0, MoveType.CASTLE));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));

        // Black knight
        moves = kingMovement.getBlackMoves();
        expectedMoves.clear();

        expectedMoves.add(new Move(PieceType.BK, 4, 7, 5, 7));
        expectedMoves.add(new Move(PieceType.BK, 4, 7, 6, 7, MoveType.CASTLE));
        expectedMoves.add(new Move(PieceType.BK, 4, 7, 3, 7));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));
    }

    @Test
    public void CaptureIntoCheck() {
        // King causes check
        Gameplay game = FEN.fromFenString("8/K1kp4/1r4b1/wP5/8/8/8/8 w - - 0 1");

        List<Move> moves = game.getMovement().getKingMovement().getWhiteMoves();
        List<Move> expectedMoves = new ArrayList<Move>();
        expectedMoves.add(new Move(PieceType.WK, 0, 6, 0, 7));

        Assert.assertTrue(equalMoveList(moves, expectedMoves));

        // Knight causes check
        game = FEN.fromFenString("8/K2n4/1r4b1/wP5/8/8/8/7k w - - 0 1");

        moves = game.getMovement().getKingMovement().getWhiteMoves();
        expectedMoves = new ArrayList<Move>();
        expectedMoves.add(new Move(PieceType.WK, 0, 6, 0, 7));

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
