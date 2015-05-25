package board;

import java.util.ArrayList;
import java.util.List;

public class Movement {

    private final long FILE_A = 0x8080808080808080L; // First Column
    private final long FILE_H = 0x0101010101010101L; // Last Column
    private final long RANK_1 = 0x00000000000000ffL; // Bottom Row
    private final long RANK_4 = 0x00000000ff000000L;
    private final long RANK_5 = 0x000000ff00000000L;
    private final long RANK_8 = 0xff00000000000000L; // Top Row

    private Board board;

    public final long WHITE_PIECES;
    public final long BLACK_PIECES;
    public final long EMPTY;

    public Movement(Board board) {
        this.board = board;

        /* @formatter:off */
        WHITE_PIECES =  board.getBitBoard(PieceType.WP) |
                        board.getBitBoard(PieceType.WR) | 
                        board.getBitBoard(PieceType.WN) |
                        board.getBitBoard(PieceType.WB) |
                        board.getBitBoard(PieceType.WQ) |
                        board.getBitBoard(PieceType.WK);
        BLACK_PIECES =  board.getBitBoard(PieceType.BP) |
                        board.getBitBoard(PieceType.BR) | 
                        board.getBitBoard(PieceType.BN) |
                        board.getBitBoard(PieceType.BB) |
                        board.getBitBoard(PieceType.BQ) |
                        board.getBitBoard(PieceType.BK);
        /* @formatter:on */

        EMPTY = ~(WHITE_PIECES | BLACK_PIECES);
    }

    public List<Move> getWhiteMoves() {
        List<Move> possibleWhiteMoves = new ArrayList<Move>();

        possibleWhiteMoves.addAll(getWhitePawnMoves());
        possibleWhiteMoves.addAll(getWhiteRookMoves());
        possibleWhiteMoves.addAll(getWhiteKnightMoves());
        possibleWhiteMoves.addAll(getWhiteBishopMoves());
        possibleWhiteMoves.addAll(getWhiteQueenMoves());
        possibleWhiteMoves.addAll(getWhiteKingMoves());

        return possibleWhiteMoves;
    }

    public List<Move> getWhitePawnMoves() {
        List<Move> moves = new ArrayList<Move>();
        long currentPawns = board.getBitBoard(PieceType.WP);
        PieceType type = PieceType.WP;

        // Right captures
        long captureRight = (currentPawns << 7) & BLACK_PIECES & ~FILE_A;
        for (int i = initialIndex(captureRight); i < finalIndex(captureRight); i++) {
            if ((((captureRight & RANK_8) >> i) & 1) == 1) {
                // Promotion
                moves.add(new Move(type, i / 8 - 1, i % 8 - 1, i / 8, i % 8, true, PieceType.WN));
                moves.add(new Move(type, i / 8 - 1, i % 8 - 1, i / 8, i % 8, true, PieceType.WB));
                moves.add(new Move(type, i / 8 - 1, i % 8 - 1, i / 8, i % 8, true, PieceType.WR));
                moves.add(new Move(type, i / 8 - 1, i % 8 - 1, i / 8, i % 8, true, PieceType.WQ));
            } else if (((captureRight >> i) & 1) == 1) {
                moves.add(new Move(type, i / 8 - 1, i % 8 - 1, i / 8, i % 8, true));
            }
        }

        // Left captures
        long captureLeft = (currentPawns << 9) & BLACK_PIECES & ~FILE_H;
        for (int i = initialIndex(captureLeft); i < finalIndex(captureLeft); i++) {
            if ((((captureLeft & RANK_8) >> i) & 1) == 1) {
                // Promotion
                moves.add(new Move(type, i / 8 - 1, i % 8 + 1, i / 8, i % 8, true, PieceType.WN));
                moves.add(new Move(type, i / 8 - 1, i % 8 + 1, i / 8, i % 8, true, PieceType.WB));
                moves.add(new Move(type, i / 8 - 1, i % 8 + 1, i / 8, i % 8, true, PieceType.WR));
                moves.add(new Move(type, i / 8 - 1, i % 8 + 1, i / 8, i % 8, true, PieceType.WQ));
            } else if (((captureLeft >> i) & 1) == 1) {
                moves.add(new Move(type, i / 8 - 1, i % 8 + 1, i / 8, i % 8, true));
            }
        }

        // One forward
        long oneForward = (currentPawns << 8) & EMPTY;
        for (int i = initialIndex(oneForward); i < finalIndex(oneForward); i++) {
            if ((((oneForward & RANK_8) >> i) & 1) == 1) {
                // Promotion
                moves.add(new Move(type, i / 8 - 1, i % 8, i / 8, i % 8, PieceType.WN));
                moves.add(new Move(type, i / 8 - 1, i % 8, i / 8, i % 8, PieceType.WB));
                moves.add(new Move(type, i / 8 - 1, i % 8, i / 8, i % 8, PieceType.WR));
                moves.add(new Move(type, i / 8 - 1, i % 8, i / 8, i % 8, PieceType.WQ));
            } else if (((oneForward >> i) & 1) == 1) {
                moves.add(new Move(type, i / 8 - 1, i % 8, i / 8, i % 8));
            }
        }

        // Two forward
        long twoForward = (currentPawns << 16) & EMPTY & (EMPTY << 8) & RANK_4;
        for (int i = initialIndex(twoForward); i < finalIndex(twoForward); i++) {
            if (((twoForward >> i) & 1) == 1) {
                moves.add(new Move(type, i / 8 - 2, i % 8, i / 8, i % 8));
            }
        }

        return moves;
    }

    public List<Move> getWhiteRookMoves() {
        List<Move> moves = new ArrayList<Move>();

        return moves;
    }

    public List<Move> getWhiteKnightMoves() {
        List<Move> moves = new ArrayList<Move>();

        return moves;
    }

    public List<Move> getWhiteBishopMoves() {
        List<Move> moves = new ArrayList<Move>();

        return moves;
    }

    public List<Move> getWhiteQueenMoves() {
        List<Move> moves = new ArrayList<Move>();

        return moves;
    }

    public List<Move> getWhiteKingMoves() {
        List<Move> moves = new ArrayList<Move>();

        return moves;
    }

    private int initialIndex(long num) {
        return Long.numberOfTrailingZeros(num);
    }

    private int finalIndex(long num) {
        return Board.NUM_SQUARES - Long.numberOfLeadingZeros(num);
    }

}
