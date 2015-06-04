package board;

import gameplay.Gameplay;
import junit.framework.Assert;

import org.junit.Test;

import board.movement.Move;
import board.movement.MoveType;

public class BoardUpdateTest {

    @Test
    public void TypicalMoves() {
        Gameplay game = new Gameplay();

        // Execute the Open Sicilian Defense (Wing Gambit Deferred)
        game.executeMove(new Move(PieceType.WP, 4, 1, 4, 3));
        game.executeMove(new Move(PieceType.BP, 2, 6, 2, 4));
        game.executeMove(new Move(PieceType.WN, 6, 0, 5, 2));
        game.executeMove(new Move(PieceType.BP, 3, 6, 3, 5));
        game.executeMove(new Move(PieceType.WP, 1, 1, 1, 3));

        /* @formatter:off */
        String[][] boardMatrix = {{"BR", "BN", "BB", "BQ", "BK", "BB", "BN", "BR"},
                                  {"BP", "BP", "  ", "  ", "BP", "BP", "BP", "BP"},
                                  {"  ", "  ", "  ", "BP", "  ", "  ", "  ", "  "},
                                  {"  ", "  ", "BP", "  ", "  ", "  ", "  ", "  "},
                                  {"  ", "WP", "  ", "  ", "WP", "  ", "  ", "  "},
                                  {"  ", "  ", "  ", "  ", "  ", "WN", "  ", "  "},
                                  {"WP", "  ", "WP", "WP", "  ", "WP", "WP", "WP"},
                                  {"WR", "WN", "WB", "WQ", "WK", "WB", "  ", "WR"}};
        /* @formatter:on */
        Board expectedBoard = Board.arrayToBoard(boardMatrix);
        Assert.assertEquals(game.getBoard(), expectedBoard);
    }

    @Test
    public void Captures() {

        /* @formatter:off */
        String[][] boardMatrix = {{"BR", "  ", "BB", "BQ", "BK", "  ", "BN", "  "},
                                  {"BP", "BP", "  ", "  ", "  ", "BP", "BP", "BP"},
                                  {"  ", "  ", "BN", "  ", "  ", "BB", "  ", "  "},
                                  {"  ", "  ", "BP", "  ", "BP", "  ", "  ", "  "},
                                  {"WR", "  ", "  ", "WP", "  ", "  ", "  ", "BR"},
                                  {"  ", "  ", "WB", "  ", "WP", "WN", "  ", "  "},
                                  {"WP", "WP", "WP", "  ", "  ", "WP", "WP", "WP"},
                                  {"  ", "WN", "  ", "WQ", "WK", "WB", "  ", "WR"}};
        /* @formatter:on */
        Board initialBoard = Board.arrayToBoard(boardMatrix);
        Gameplay game = new Gameplay(initialBoard);

        game.executeMove(new Move(PieceType.BP, 4, 4, 3, 3, MoveType.CAPTURE, PieceType.WP));
        game.executeMove(new Move(PieceType.WP, 4, 2, 3, 3, MoveType.CAPTURE, PieceType.BP));
        game.executeMove(new Move(PieceType.BP, 2, 4, 3, 3, MoveType.CAPTURE, PieceType.WP));
        game.executeMove(new Move(PieceType.WB, 2, 2, 3, 3, MoveType.CAPTURE, PieceType.BP));
        game.executeMove(new Move(PieceType.BB, 5, 5, 3, 3, MoveType.CAPTURE, PieceType.WB));
        game.executeMove(new Move(PieceType.WN, 5, 2, 3, 3, MoveType.CAPTURE, PieceType.BB));
        game.executeMove(new Move(PieceType.BN, 2, 5, 3, 3, MoveType.CAPTURE, PieceType.WN));
        game.executeMove(new Move(PieceType.WR, 0, 3, 3, 3, MoveType.CAPTURE, PieceType.BN));
        game.executeMove(new Move(PieceType.BR, 7, 3, 3, 3, MoveType.CAPTURE, PieceType.WR));
        game.executeMove(new Move(PieceType.WQ, 3, 0, 3, 3, MoveType.CAPTURE, PieceType.BR));
        game.executeMove(new Move(PieceType.BQ, 3, 7, 3, 3, MoveType.CAPTURE, PieceType.WQ));

        /* @formatter:off */
        String[][] expectedBoardMatrix = {{"BR", "  ", "BB", "  ", "BK", "  ", "BN", "  "},
                                          {"BP", "BP", "  ", "  ", "  ", "BP", "BP", "BP"},
                                          {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                          {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                          {"  ", "  ", "  ", "BQ", "  ", "  ", "  ", "  "},
                                          {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
                                          {"WP", "WP", "WP", "  ", "  ", "WP", "WP", "WP"},
                                          {"  ", "WN", "  ", "  ", "WK", "WB", "  ", "WR"}};
        /* @formatter:on */
        Board expectedBoard = Board.arrayToBoard(expectedBoardMatrix);
        Assert.assertEquals(game.getBoard(), expectedBoard);
    }
}
