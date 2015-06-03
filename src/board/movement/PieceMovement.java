package board.movement;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.PieceType;

public class PieceMovement {

    /* @formatter:off */
    protected static long FILE_MASKS[] = { 0x8080808080808080L, 0x4040404040404040L, 0x2020202020202020L, 0x1010101010101010L,
                                           0x0808080808080808L, 0x0404040404040404L, 0x0202020202020202L, 0x0101010101010101L };

    protected static long RANK_MASKS[] = { 0x00000000000000ffL, 0x000000000000ff00L, 0x0000000000ff0000L, 0x00000000ff000000L,
                                          0x000000ff00000000L, 0x0000ff0000000000L, 0x00ff000000000000L, 0xff00000000000000L };

    // Top left to bottom right, positive slope
    protected static long DIAGONAL_MASKS[] = { 0x8000000000000000L, 0x4080000000000000L, 0x2040800000000000L, 0x1020408000000000L,
                                               0x0810204080000000L, 0x0408102040800000L, 0x0204081020408000L, 0x0102040810204080L,
                                               0x0001020408102040L, 0x0000010204081020L, 0x0000000102040810L, 0x0000000001020408L,
                                               0x0000000000010204L, 0x0000000000000102L, 0x0000000000000001L };
    
    // Bottom left to top right, negative slope
    protected static long ANTIDIAGONAL_MASKS[] = { 0x0000000000000080L, 0x0000000000008040L, 0x0000000000804020L, 0x0000000080402010L,
                                                   0x0000008040201008L, 0x0000804020100804L, 0x0080402010080402L, 0x8040201008040201L,
                                                   0x4020100804020100L, 0x2010080402010000L, 0x1008040201000000L, 0x0804020100000000L,
                                                   0x0402010000000000L, 0x0201000000000000L, 0x0100000000000000L };
    /* @formatter:on */

    protected final long FILE_A = FILE_MASKS[0]; // First Column
    protected final long FILE_B = FILE_MASKS[1];
    protected final long FILE_G = FILE_MASKS[6];
    protected final long FILE_H = FILE_MASKS[7]; // Last Column
    protected final long RANK_1 = RANK_MASKS[0]; // Bottom Row
    protected final long RANK_4 = RANK_MASKS[3];
    protected final long RANK_5 = RANK_MASKS[4];
    protected final long RANK_8 = RANK_MASKS[7]; // Top Row

    // Store board and board matrix since matrix gives quick indexing
    protected Board board;
    protected String[][] boardMatrix;

    // Piece locations
    public long whitePieces;
    public long blackPieces;
    public long occupied;
    public long empty;

    // Possible moves
    protected long possibleWhiteAttacksBitBoard = 0L;
    protected List<Move> whiteMoves;
    protected long possibleBlackAttacksBitBoard = 0L;
    protected List<Move> blackMoves;

    public PieceMovement(Board board) {
        this.board = board;
        this.boardMatrix = board.boardToArray();

        /* @formatter:off */
        whitePieces =  board.getBitBoard(PieceType.WP) |
                       board.getBitBoard(PieceType.WR) | 
                       board.getBitBoard(PieceType.WN) |
                       board.getBitBoard(PieceType.WB) |
                       board.getBitBoard(PieceType.WQ) |
                       board.getBitBoard(PieceType.WK);
        blackPieces =  board.getBitBoard(PieceType.BP) |
                       board.getBitBoard(PieceType.BR) | 
                       board.getBitBoard(PieceType.BN) |
                       board.getBitBoard(PieceType.BB) |
                       board.getBitBoard(PieceType.BQ) |
                       board.getBitBoard(PieceType.BK);
        /* @formatter:on */

        occupied = blackPieces | whitePieces;
        empty = ~(occupied);

    }

    /*
     * Package private getters for possible moves
     */

    public long getWhitePossibleMoves() {
        return possibleWhiteAttacksBitBoard;
    }

    public List<Move> getWhiteMoves() {
        return whiteMoves;
    }

    public long getBlackPossibleMoves() {
        return possibleBlackAttacksBitBoard;
    }

    public List<Move> getBlackMoves() {
        return blackMoves;
    }

    protected PieceType getPieceAt(int x, int y) {
        String str = boardMatrix[7 - y][x];
        return str == null ? null : PieceType.valueOf(boardMatrix[7 - y][x]);
    }

    protected void setMoves(PieceType piece, List<Move> moves, long possibleMoves) {
        if (piece.isWhitePiece()) {
            this.possibleWhiteAttacksBitBoard = possibleMoves;
            this.whiteMoves = moves;
        } else {
            this.possibleBlackAttacksBitBoard = possibleMoves;
            this.blackMoves = moves;
        }
    }

    /**
     * @param bitBoard
     * @param piece
     *            {@link PieceType} of the piece we want to find the moves of
     * @param x
     *            position of white piece we want to find the moves of
     * @param y
     *            position of white piece we want to find the moves of
     * @return list of all potential moves from the given bit board
     */
    protected List<Move> getMoves(long possibleMovesBitBoard, PieceType piece, int x, int y) {
        List<Move> moves = new ArrayList<Move>();

        // Remove moves that are not proper captures
        if (piece.isWhitePiece()) {
            possibleMovesBitBoard &= ~whitePieces;
        } else if (piece.isBlackPiece()) {
            possibleMovesBitBoard &= ~blackPieces;
        }

        long bitBoardS = getBitBoard(x, y);

        for (int i = initialIndex(possibleMovesBitBoard); i < finalIndex(possibleMovesBitBoard); i++) {
            if (((possibleMovesBitBoard >> i) & 1) == bitBoardS) {
                // Observing the piece in question
                continue;
            }
            if (((possibleMovesBitBoard >> i) & 1) == 1) {
                int moveX = getX(i);
                int moveY = getY(i);

                long oppositeColorPieces = piece.isWhitePiece() ? blackPieces : whitePieces;
                if (((oppositeColorPieces >> i) & 1) == 1) {
                    // Capture
                    moves.add(new Move(piece, x, y, moveX, moveY, MoveType.CAPTURE, getPieceAt(moveX, moveY)));
                } else {
                    moves.add(new Move(piece, x, y, moveX, moveY));
                }
            }
        }

        return moves;
    }

    protected int initialIndex(long num) {
        return Long.numberOfTrailingZeros(num);
    }

    protected int finalIndex(long num) {
        return Board.NUM_SQUARES - Long.numberOfLeadingZeros(num);
    }

    protected int getX(int i) {
        return 7 - (i % 8);
    }

    protected int getY(int i) {
        return i / 8;
    }

    /**
     * Bottom left=0, bottom right=7, top left=56, top right= 63
     * 
     * @param x
     * @param y
     * @return a bitboard with a 1 at position (x,y)
     */
    protected long getBitBoard(int x, int y) {
        int s = (Board.DIMENSION * y) + (7 - x);
        return 1L << s;
    }

}
