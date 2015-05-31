package board.movement;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.PieceType;

public class KnightMovement extends Movement {

    private final long KNIGHT_RANGE = 0x0000000A1100110AL;
    private final int CENTRE = 18; // 18 Right shifts of KNIGHT_RANGE
                                   // puts the centre into the last bit

    public KnightMovement(Board board) {
        super(board);
    }

    public List<Move> getWhiteMoves() {
        return getMoves(PieceType.WN);
    }

    public List<Move> getBlackMoves() {
        return getMoves(PieceType.BN);
    }

    public List<Move> getMoves(PieceType piece) {
        List<Move> moves = new ArrayList<Move>();
        long currentKnights = board.getBitBoard(piece);

        for (int i = initialIndex(currentKnights); i < finalIndex(currentKnights); i++) {
            if (((currentKnights >> i) & 1) == 1) {
                long possibleMoves = i < CENTRE ? KNIGHT_RANGE >> (CENTRE - i) : KNIGHT_RANGE << (i - CENTRE);

                long sameColorPieces = piece.isWhitePiece() ? whitePieces : blackPieces;
                possibleMoves &= ~sameColorPieces;

                // Eliminate wrap around and wrong colour
                if (i % 8 < 4) {
                    possibleMoves &= ~FILE_A & ~FILE_B;
                } else {
                    possibleMoves &= ~FILE_H & ~FILE_G;
                }

                moves.addAll(getMoves(possibleMoves, piece, getX(i), getY(i)));
            }
        }

        return moves;
    }
}
