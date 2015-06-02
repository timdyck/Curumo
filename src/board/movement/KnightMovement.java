package board.movement;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.PieceType;

public class KnightMovement extends PieceMovement {

    private final long KNIGHT_RANGE = 0x0000000A1100110AL;
    private final int CENTRE = 18; // 18 Right shifts of KNIGHT_RANGE
                                   // puts the centre into the last bit

    public KnightMovement(Board board) {
        super(board);
        initializeMoves(PieceType.WN);
        initializeMoves(PieceType.BN);
    }

    private void initializeMoves(PieceType piece) {
        List<Move> moves = new ArrayList<Move>();
        long currentKnights = board.getBitBoard(piece);
        long allPossibleMovesBitBoard = 0L;

        for (int i = initialIndex(currentKnights); i < finalIndex(currentKnights); i++) {
            if (((currentKnights >> i) & 1) == 1) {
                long possibleMoves = i < CENTRE ? KNIGHT_RANGE >> (CENTRE - i) : KNIGHT_RANGE << (i - CENTRE);

                long sameColorPieces = piece.isWhitePiece() ? whitePieces : blackPieces;
                possibleMoves &= ~sameColorPieces;

                // Eliminate wrap around
                if (getX(i) > 5) {
                    possibleMoves &= ~FILE_A & ~FILE_B;
                } else if (getX(i) < 2) {
                    possibleMoves &= ~FILE_H & ~FILE_G;
                }

                moves.addAll(getMoves(possibleMoves, piece, getX(i), getY(i)));
                allPossibleMovesBitBoard |= possibleMoves;
            }
        }

        setMoves(piece, moves, allPossibleMovesBitBoard);
    }
}
