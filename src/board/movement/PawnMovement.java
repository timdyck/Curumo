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
            int x = i / 8;
            int y = 7 - (i % 8);

            if ((((captureRight & RANK_8) >> i) & 1) == 1) {
                // Promotion
                moves.add(new Move(type, x - 1, y - 1, x, y, true, PieceType.WN));
                moves.add(new Move(type, x - 1, y - 1, x, y, true, PieceType.WB));
                moves.add(new Move(type, x - 1, y - 1, x, y, true, PieceType.WR));
                moves.add(new Move(type, x - 1, y - 1, x, y, true, PieceType.WQ));
            } else if (((captureRight >> i) & 1) == 1) {
                moves.add(new Move(type, x - 1, y - 1, x, y, true));
            }
        }

        // Left captures
        long captureLeft = (currentPawns << 9) & blackPieces & ~FILE_H;
        for (int i = initialIndex(captureLeft); i < finalIndex(captureLeft); i++) {
            int x = i / 8;
            int y = 7 - (i % 8);

            if ((((captureLeft & RANK_8) >> i) & 1) == 1) {
                // Promotion
                moves.add(new Move(type, x - 1, y + 1, x, y, true, PieceType.WN));
                moves.add(new Move(type, x - 1, y + 1, x, y, true, PieceType.WB));
                moves.add(new Move(type, x - 1, y + 1, x, y, true, PieceType.WR));
                moves.add(new Move(type, x - 1, y + 1, x, y, true, PieceType.WQ));
            } else if (((captureLeft >> i) & 1) == 1) {
                moves.add(new Move(type, x - 1, y + 1, x, y, true));
            }
        }

        // One forward
        long oneForward = (currentPawns << 8) & empty;
        for (int i = initialIndex(oneForward); i < finalIndex(oneForward); i++) {
            int x = i / 8;
            int y = 7 - (i % 8);

            if ((((oneForward & RANK_8) >> i) & 1) == 1) {
                // Promotion
                moves.add(new Move(type, x - 1, y, x, y, PieceType.WN));
                moves.add(new Move(type, x - 1, y, x, y, PieceType.WB));
                moves.add(new Move(type, x - 1, y, x, y, PieceType.WR));
                moves.add(new Move(type, x - 1, y, x, y, PieceType.WQ));
            } else if (((oneForward >> i) & 1) == 1) {
                moves.add(new Move(type, x - 1, y, x, y));
            }
        }

        // Two forward
        long twoForward = (currentPawns << 16) & empty & (empty << 8) & RANK_4;
        for (int i = initialIndex(twoForward); i < finalIndex(twoForward); i++) {
            int x = i / 8;
            int y = 7 - (i % 8);

            if (((twoForward >> i) & 1) == 1) {
                moves.add(new Move(type, x - 2, y, x, y));
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
                int x = i / 8;
                int y = 7 - (i % 8);
                moves.add(new Move(type, x, y - 1, x + 1, y, true));
            }

            // Left capture
            long leftCapture = (currentPawns << 1) & blackPieces & RANK_5 & ~FILE_H & FILE_MASKS[col];
            if (leftCapture != 0) {
                int i = Long.numberOfTrailingZeros(leftCapture);
                int x = i / 8;
                int y = 7 - (i % 8);
                moves.add(new Move(type, x, y + 1, x + 1, y, true));
            }

        }

        return moves;
    }

    public List<Move> getBlackMoves(Move previousMove) {
        List<Move> moves = new ArrayList<Move>();

        return moves;
    }
}
