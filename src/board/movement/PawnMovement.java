package board.movement;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.PieceType;

public class PawnMovement extends Movement {

    public PawnMovement(Board board) {
        super(board);
    }

    public List<Move> getWhiteMoves(Move previousMove) {
        List<Move> moves = new ArrayList<Move>();
        long currentPawns = board.getBitBoard(PieceType.WP);
        PieceType type = PieceType.WP;

        // Right captures
        long captureRight = (currentPawns << 7) & blackPieces & ~FILE_A;
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
        long captureLeft = (currentPawns << 9) & blackPieces & ~FILE_H;
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
        long oneForward = (currentPawns << 8) & empty;
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
        long twoForward = (currentPawns << 16) & empty & (empty << 8) & RANK_4;
        for (int i = initialIndex(twoForward); i < finalIndex(twoForward); i++) {
            if (((twoForward >> i) & 1) == 1) {
                moves.add(new Move(type, i / 8 - 2, i % 8, i / 8, i % 8));
            }
        }

        // En passant
        if (!Move.isFirstMove(previousMove) && previousMove.type.equals(PieceType.BP) && previousMove.row1 == 6
                && previousMove.row2 == 4 && previousMove.col1 == previousMove.col2) {
            int col = previousMove.col1;

            // Right capture
            long rightCapture = (currentPawns >> 1) & blackPieces & RANK_5 & ~FILE_A & FILE_MASKS[col];
            if (rightCapture != 0) {
                int i = Long.numberOfTrailingZeros(rightCapture);
                moves.add(new Move(type, i / 8, i % 8 - 1, i / 8 + 1, i % 8, true));
            }

            // Left capture
            long leftCapture = (currentPawns << 1) & blackPieces & RANK_5 & ~FILE_H & FILE_MASKS[col];
            if (leftCapture != 0) {
                int i = Long.numberOfTrailingZeros(leftCapture);
                moves.add(new Move(type, i / 8, i % 8 + 1, i / 8 + 1, i % 8, true));
            }

        }

        return moves;
    }

    public List<Move> getBlackMoves(Move previousMove) {
        List<Move> moves = new ArrayList<Move>();

        return moves;
    }
}
