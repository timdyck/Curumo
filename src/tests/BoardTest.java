package tests;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import board.Board;
import board.Move;
import board.Movement;
import board.PieceType;

public class BoardTest {

    /**
     * Ensure that each piece in the inital board is in the right place,
     * simultaneously testing out board to array functionality.
     */
    /**
     * 
     */
    @Test
    public void InitialBoard() {
        Board board = new Board();
        Map<PieceType, Long> bitBoards = board.getBitBoards();

        Assert.assertEquals(bitBoards.size(), Board.NUM_BOARDS);

        String[][] boardArray = board.getBoardArray();
        Assert.assertEquals(PieceType.BR.name(), boardArray[0][0]);
        Assert.assertEquals(PieceType.BR.name(), boardArray[0][7]);
        Assert.assertEquals(PieceType.WR.name(), boardArray[7][0]);
        Assert.assertEquals(PieceType.WR.name(), boardArray[7][7]);

        Assert.assertEquals(PieceType.BN.name(), boardArray[0][1]);
        Assert.assertEquals(PieceType.BN.name(), boardArray[0][6]);
        Assert.assertEquals(PieceType.WN.name(), boardArray[7][1]);
        Assert.assertEquals(PieceType.WN.name(), boardArray[7][6]);

        Assert.assertEquals(PieceType.BB.name(), boardArray[0][2]);
        Assert.assertEquals(PieceType.BB.name(), boardArray[0][5]);
        Assert.assertEquals(PieceType.WB.name(), boardArray[7][2]);
        Assert.assertEquals(PieceType.WB.name(), boardArray[7][5]);

        Assert.assertEquals(PieceType.BQ.name(), boardArray[0][3]);
        Assert.assertEquals(PieceType.WQ.name(), boardArray[7][3]);

        Assert.assertEquals(PieceType.BK.name(), boardArray[0][4]);
        Assert.assertEquals(PieceType.WK.name(), boardArray[7][4]);

        for (int i = 0; i < Board.DIMENSION; i++) {
            Assert.assertEquals(PieceType.BP.name(), boardArray[1][i]);
            Assert.assertEquals(PieceType.WP.name(), boardArray[Board.DIMENSION - 2][i]);
        }

        for (int i = 2; i < Board.DIMENSION - 2; i++) {
            for (int j = 0; j < Board.DIMENSION; j++) {
                Assert.assertNull(boardArray[i][j]);
            }
        }

        board.printBoard();
    }

    /**
     * Ensure the initial bit boards for white pieces, black pieces, and empty
     * pieces, are correct.
     */
    @Test
    public void InitialBoardMovementConstants() {
        Board board = new Board();
        Movement movement = new Movement(board);

        String blackPiece = "BP";
        String[][] blackBoardArray = Board.getBitBoardArray(movement.BLACK_PIECES, blackPiece);
        for (int i = 0; i < Board.DIMENSION; i++) {
            Assert.assertEquals(blackPiece, blackBoardArray[0][i]);
            Assert.assertEquals(blackPiece, blackBoardArray[1][i]);
        }
        for (int i = 2; i < Board.DIMENSION; i++) {
            for (int j = 0; j < Board.DIMENSION; j++) {
                Assert.assertNull(blackBoardArray[i][j]);
            }
        }

        String whitePiece = "WP";
        String[][] whiteBoardArray = Board.getBitBoardArray(movement.WHITE_PIECES, whitePiece);
        for (int i = 0; i < Board.DIMENSION; i++) {
            Assert.assertEquals(whitePiece, whiteBoardArray[Board.DIMENSION - 2][i]);
            Assert.assertEquals(whitePiece, whiteBoardArray[Board.DIMENSION - 1][i]);
        }
        for (int i = 0; i < Board.DIMENSION - 2; i++) {
            for (int j = 0; j < Board.DIMENSION; j++) {
                Assert.assertNull(whiteBoardArray[i][j]);
            }
        }

        String emptyPiece = "EE";
        String[][] emptyBoardArray = Board.getBitBoardArray(movement.EMPTY, emptyPiece);
        for (int i = 0; i < Board.DIMENSION; i++) {
            Assert.assertNull(emptyPiece, emptyBoardArray[0][i]);
            Assert.assertNull(emptyPiece, emptyBoardArray[1][i]);
            Assert.assertNull(emptyPiece, emptyBoardArray[Board.DIMENSION - 2][i]);
            Assert.assertNull(emptyPiece, emptyBoardArray[Board.DIMENSION - 1][i]);
        }
        for (int i = 2; i < Board.DIMENSION - 2; i++) {
            for (int j = 0; j < Board.DIMENSION; j++) {
                Assert.assertEquals(emptyPiece, emptyBoardArray[i][j]);
            }
        }

        List<Move> moves = movement.getWhiteMoves(Move.getFirstPreviousMove());
        for (Move move : moves) {
            move.printMove();
        }

    }
}
