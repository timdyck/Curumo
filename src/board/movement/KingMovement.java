package board.movement;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.PieceType;

public class KingMovement extends PieceMovement {

    private final long KING_RANGE = 0x0000000000070507L;
    private final int CENTRE = 9; // 9 Right shifts of KING_RANGE
                                  // puts the centre into the last bit

    private final long DEFAULT_UNSAFE_MOVES = 0x0L;

    public KingMovement(Board board) {
        super(board);
        initializeMoves(PieceType.WK, DEFAULT_UNSAFE_MOVES);
        initializeMoves(PieceType.BK, DEFAULT_UNSAFE_MOVES);
    }

    public KingMovement(Board board, long unsafeForWhite, long unsafeForBlack) {
        super(board);
        initializeMoves(PieceType.WK, unsafeForWhite);
        initializeMoves(PieceType.BK, unsafeForBlack);
    }

    public void initializeMoves(PieceType piece, long unsafeMoves) {
        List<Move> moves = new ArrayList<Move>();
        long currentKnights = board.getBitBoard(piece);

        for (int i = initialIndex(currentKnights); i < finalIndex(currentKnights); i++) {
            if (((currentKnights >> i) & 1) == 1) {
                long possibleMoves = i < CENTRE ? KING_RANGE >> (CENTRE - i) : KING_RANGE << (i - CENTRE);

                long sameColorPieces = piece.isWhitePiece() ? whitePieces : blackPieces;
                possibleMoves &= ~sameColorPieces;

                // Eliminate wrap around
                if (getX(i) == 7) {
                    possibleMoves &= ~FILE_A;
                } else if (getX(i) == 0) {
                    possibleMoves &= ~FILE_H;
                }

                // Eliminate moves putting king in check
                possibleMoves &= ~unsafeMoves;

                moves.addAll(getMoves(possibleMoves, piece, getX(i), getY(i)));

                setMoves(piece, moves, possibleMoves);
            }
        }
    }

    public void updateUnsafeMoves(long unsafeForWhite, long unsafeForBlack) {
        initializeMoves(PieceType.WK, DEFAULT_UNSAFE_MOVES);
        initializeMoves(PieceType.BK, DEFAULT_UNSAFE_MOVES);
    }
}
