package board;

public class Movement {

    private final long FILE_A = 0x8080808080808080L;
    private final long RANK_1 = 0x00000000000000ffL;

    private Board board;

    public final long WHITE_PIECES;
    public final long BLACK_PIECES;
    public final long EMPTY;

    public Movement(Board board) {
        this.board = board;

        /* @formatter:off */
        WHITE_PIECES =  board.getBitBoard(BoardType.WP) |
                        board.getBitBoard(BoardType.WR) | 
                        board.getBitBoard(BoardType.WN) |
                        board.getBitBoard(BoardType.WB) |
                        board.getBitBoard(BoardType.WQ) |
                        board.getBitBoard(BoardType.WK);
        BLACK_PIECES =  board.getBitBoard(BoardType.BP) |
                        board.getBitBoard(BoardType.BR) | 
                        board.getBitBoard(BoardType.BN) |
                        board.getBitBoard(BoardType.BB) |
                        board.getBitBoard(BoardType.BQ) |
                        board.getBitBoard(BoardType.BK);
        /* @formatter:on */

        EMPTY = ~(WHITE_PIECES | BLACK_PIECES);
    }

}
