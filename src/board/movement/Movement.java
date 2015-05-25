package board.movement;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.PieceType;

public class Movement {

    protected static long FILE_MASKS[] = { 0x8080808080808080L, 0x4040404040404040L, 0x2020202020202020L, 0x1010101010101010L,
            0x0808080808080808L, 0x0404040404040404L, 0x0202020202020202L, 0x0101010101010101L };

    protected static long ROW_MASKS[] = { 0x00000000000000ffL, 0x000000000000ff00L, 0x0000000000ff0000L, 0x00000000ff000000L,
            0x000000ff00000000L, 0x0000ff0000000000L, 0x00ff000000000000L, 0xff00000000000000L };

    protected final long FILE_A = FILE_MASKS[0]; // First Column
    protected final long FILE_H = FILE_MASKS[7]; // Last Column
    protected final long RANK_1 = ROW_MASKS[0]; // Bottom Row
    protected final long RANK_4 = ROW_MASKS[3];
    protected final long RANK_5 = ROW_MASKS[4];
    protected final long RANK_8 = ROW_MASKS[7]; // Top Row

    protected Board board;

    public long whitePieces;
    public long blackPieces;
    public long empty;

    public Movement(Board board) {
        this.board = board;

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

        empty = ~(whitePieces | blackPieces);
    }

    public List<Move> getAllWhiteMoves(Move previousMove) {
        List<Move> possibleWhiteMoves = new ArrayList<Move>();

        PawnMovement pawnMovement = new PawnMovement(board);
        RookMovement rookMovement = new RookMovement(board);
        KnightMovement knightMovement = new KnightMovement(board);
        BishopMovement bishopMovement = new BishopMovement(board);
        QueenMovement queenMovement = new QueenMovement(board);
        KingMovement kingMovement = new KingMovement(board);

        possibleWhiteMoves.addAll(pawnMovement.getWhiteMoves(previousMove));
        possibleWhiteMoves.addAll(rookMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(knightMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(bishopMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(queenMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(kingMovement.getWhiteMoves());

        return possibleWhiteMoves;
    }

    public List<Move> getAllBlackMoves(Move previousMove) {
        List<Move> possibleBlackMoves = new ArrayList<Move>();

        PawnMovement pawnMovement = new PawnMovement(board);
        RookMovement rookMovement = new RookMovement(board);
        KnightMovement knightMovement = new KnightMovement(board);
        BishopMovement bishopMovement = new BishopMovement(board);
        QueenMovement queenMovement = new QueenMovement(board);
        KingMovement kingMovement = new KingMovement(board);

        possibleBlackMoves.addAll(pawnMovement.getBlackMoves(previousMove));
        possibleBlackMoves.addAll(rookMovement.getBlackMoves());
        possibleBlackMoves.addAll(knightMovement.getBlackMoves());
        possibleBlackMoves.addAll(bishopMovement.getBlackMoves());
        possibleBlackMoves.addAll(queenMovement.getBlackMoves());
        possibleBlackMoves.addAll(kingMovement.getBlackMoves());

        return possibleBlackMoves;
    }

    protected int initialIndex(long num) {
        return Long.numberOfTrailingZeros(num);
    }

    protected int finalIndex(long num) {
        return Board.NUM_SQUARES - Long.numberOfLeadingZeros(num);
    }

}
