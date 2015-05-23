package tests;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import board.Board;
import board.BoardType;

public class BoardTest {

    @Test
    public void InitializeBoard() {
        Board board = new Board();
        Map<BoardType, Long> bitBoards = board.getBitBoards();

        Assert.assertEquals(bitBoards.size(), Board.NUM_BOARDS);

        // Check that all pieces are in their initial positions
        String[][] boardArray = board.getBoardArray();
        Assert.assertEquals(BoardType.BR.name(), boardArray[0][0]);
        Assert.assertEquals(BoardType.BR.name(), boardArray[0][7]);
        Assert.assertEquals(BoardType.WR.name(), boardArray[7][0]);
        Assert.assertEquals(BoardType.WR.name(), boardArray[7][7]);

        Assert.assertEquals(BoardType.BN.name(), boardArray[0][1]);
        Assert.assertEquals(BoardType.BN.name(), boardArray[0][6]);
        Assert.assertEquals(BoardType.WN.name(), boardArray[7][1]);
        Assert.assertEquals(BoardType.WN.name(), boardArray[7][6]);

        Assert.assertEquals(BoardType.BB.name(), boardArray[0][2]);
        Assert.assertEquals(BoardType.BB.name(), boardArray[0][5]);
        Assert.assertEquals(BoardType.WB.name(), boardArray[7][2]);
        Assert.assertEquals(BoardType.WB.name(), boardArray[7][5]);

        Assert.assertEquals(BoardType.BQ.name(), boardArray[0][3]);
        Assert.assertEquals(BoardType.WQ.name(), boardArray[7][3]);

        Assert.assertEquals(BoardType.BK.name(), boardArray[0][4]);
        Assert.assertEquals(BoardType.WK.name(), boardArray[7][4]);

        for (int i = 0; i < Board.DIMENSION; i++) {
            Assert.assertEquals(BoardType.BP.name(), boardArray[1][i]);
            Assert.assertEquals(BoardType.WP.name(), boardArray[Board.DIMENSION - 2][i]);
        }

        for (int i = 2; i < Board.DIMENSION - 2; i++) {
            for (int j = 0; j < Board.DIMENSION; j++) {
                Assert.assertEquals(null, boardArray[i][j]);
            }
        }

        // board.printBoard();
    }

}
