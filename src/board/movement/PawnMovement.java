package board.movement;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.PieceType;

public class PawnMovement extends PieceMovement {

    public PawnMovement(Board board) {
        super(board);
        initializeWhiteMoves();
        initializeBlackMoves();
    }

    public PawnMovement(Board board, Move previousMove) {
        super(board);
        initializeWhiteMoves(previousMove);
        initializeBlackMoves(previousMove);
    }

    public void initializeWhiteMoves() {
        initializeWhiteMoves(Move.getFirstPreviousMove());
    }

    public void initializeWhiteMoves(Move previousMove) {
        List<Move> moves = new ArrayList<Move>();
        PieceType type = PieceType.WP;
        long currentPawns = board.getBitBoard(type);

        // Right captures
        long possibleWhiteCaptures = (currentPawns << 7) & ~FILE_A;
        long captureRight = (currentPawns << 7) & blackPieces & ~FILE_A;
        for (int i = initialIndex(captureRight); i < finalIndex(captureRight); i++) {
            int x = getX(i);
            int y = getY(i);

            PieceType capturedPiece = getPieceAt(x, y);

            if ((((captureRight & RANK_8) >> i) & 1) == 1) {
                // Promotion
                moves.add(new Move(type, x - 1, y - 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.WN));
                moves.add(new Move(type, x - 1, y - 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.WB));
                moves.add(new Move(type, x - 1, y - 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.WR));
                moves.add(new Move(type, x - 1, y - 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.WQ));
            } else if (((captureRight >> i) & 1) == 1) {
                moves.add(new Move(type, x - 1, y - 1, x, y, MoveType.CAPTURE, capturedPiece));
            }
        }

        // Left captures
        possibleWhiteCaptures |= (currentPawns << 9) & ~FILE_H;
        long captureLeft = (currentPawns << 9) & blackPieces & ~FILE_H;
        for (int i = initialIndex(captureLeft); i < finalIndex(captureLeft); i++) {
            int x = getX(i);
            int y = getY(i);

            PieceType capturedPiece = getPieceAt(x, y);

            if ((((captureLeft & RANK_8) >> i) & 1) == 1) {
                // Promotion
                moves.add(new Move(type, x + 1, y - 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.WN));
                moves.add(new Move(type, x + 1, y - 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.WB));
                moves.add(new Move(type, x + 1, y - 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.WR));
                moves.add(new Move(type, x + 1, y - 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.WQ));
            } else if (((captureLeft >> i) & 1) == 1) {
                moves.add(new Move(type, x + 1, y - 1, x, y, MoveType.CAPTURE, capturedPiece));
            }
        }

        // Set all possible attacks
        this.possibleWhiteAttacksBitBoard = possibleWhiteCaptures;

        // One forward
        long oneForward = (currentPawns << 8) & empty;
        for (int i = initialIndex(oneForward); i < finalIndex(oneForward); i++) {
            int x = getX(i);
            int y = getY(i);

            if ((((oneForward & RANK_8) >> i) & 1) == 1) {
                // Promotion
                moves.add(new Move(type, x, y - 1, x, y, MoveType.PROMOTION, PieceType.WN));
                moves.add(new Move(type, x, y - 1, x, y, MoveType.PROMOTION, PieceType.WB));
                moves.add(new Move(type, x, y - 1, x, y, MoveType.PROMOTION, PieceType.WR));
                moves.add(new Move(type, x, y - 1, x, y, MoveType.PROMOTION, PieceType.WQ));
            } else if (((oneForward >> i) & 1) == 1) {
                moves.add(new Move(type, x, y - 1, x, y));
            }
        }

        // Two forward
        long twoForward = (currentPawns << 16) & empty & (empty << 8) & RANK_4;
        for (int i = initialIndex(twoForward); i < finalIndex(twoForward); i++) {
            int x = getX(i);
            int y = getY(i);

            if (((twoForward >> i) & 1) == 1) {
                moves.add(new Move(type, x, y - 2, x, y));
            }
        }

        // En passant
        if (!Move.isFirstMove(previousMove) && previousMove.getPiece().equals(PieceType.BP) && previousMove.getY1() == 6
                && previousMove.getY2() == 4 && previousMove.getX1() == previousMove.getX2()) {
            int col = previousMove.getX1();

            // Right capture
            long rightCapture = (currentPawns >> 1) & blackPieces & RANK_5 & ~FILE_A & FILE_MASKS[col];
            if (rightCapture != 0) {
                int i = Long.numberOfTrailingZeros(rightCapture);
                int x = getX(i);
                int y = getY(i);
                moves.add(new Move(type, x - 1, y, x, y + 1, MoveType.EN_PASSANT));
            }

            // Left capture
            long leftCapture = (currentPawns << 1) & blackPieces & RANK_5 & ~FILE_H & FILE_MASKS[col];
            if (leftCapture != 0) {
                int i = Long.numberOfTrailingZeros(leftCapture);
                int x = getX(i);
                int y = getY(i);
                moves.add(new Move(type, x + 1, y, x, y + 1, MoveType.EN_PASSANT));
            }

        }

        this.whiteMoves = moves;

    }

    public void initializeBlackMoves() {
        initializeBlackMoves(Move.getFirstPreviousMove());
    }

    public void initializeBlackMoves(Move previousMove) {
        List<Move> moves = new ArrayList<Move>();
        PieceType type = PieceType.BP;
        long currentPawns = board.getBitBoard(PieceType.BP);

        // Right captures
        long possibleBlackCaptures = (currentPawns >> 7) & ~FILE_H;
        long captureRight = (currentPawns >> 7) & whitePieces & ~FILE_H;
        for (int i = initialIndex(captureRight); i < finalIndex(captureRight); i++) {
            int x = getX(i);
            int y = getY(i);

            PieceType capturedPiece = getPieceAt(x, y);

            if ((((captureRight & RANK_1) >> i) & 1) == 1) {
                // Promotion
                moves.add(new Move(type, x + 1, y + 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.BN));
                moves.add(new Move(type, x + 1, y + 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.BB));
                moves.add(new Move(type, x + 1, y + 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.BR));
                moves.add(new Move(type, x + 1, y + 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.BQ));
            } else if (((captureRight >> i) & 1) == 1) {
                moves.add(new Move(type, x + 1, y + 1, x, y, MoveType.CAPTURE, capturedPiece));
            }
        }

        // Left captures
        possibleBlackCaptures |= (currentPawns >> 9) & ~FILE_A;
        long captureLeft = (currentPawns >> 9) & whitePieces & ~FILE_A;
        for (int i = initialIndex(captureLeft); i < finalIndex(captureLeft); i++) {
            int x = getX(i);
            int y = getY(i);

            PieceType capturedPiece = getPieceAt(x, y);

            if ((((captureLeft & RANK_1) >> i) & 1) == 1) {
                // Promotion
                moves.add(new Move(type, x - 1, y + 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.BN));
                moves.add(new Move(type, x - 1, y + 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.BB));
                moves.add(new Move(type, x - 1, y + 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.BR));
                moves.add(new Move(type, x - 1, y + 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.BQ));
            } else if (((captureLeft >> i) & 1) == 1) {
                moves.add(new Move(type, x - 1, y + 1, x, y, MoveType.CAPTURE, capturedPiece));
            }
        }

        // Set all possible attacks
        this.possibleBlackAttacksBitBoard = possibleBlackCaptures;

        // One forward
        long oneForward = (currentPawns >> 8) & empty;
        for (int i = initialIndex(oneForward); i < finalIndex(oneForward); i++) {
            int x = getX(i);
            int y = getY(i);

            if ((((oneForward & RANK_1) >> i) & 1) == 1) {
                // Promotion
                moves.add(new Move(type, x, y + 1, x, y, MoveType.PROMOTION, PieceType.BN));
                moves.add(new Move(type, x, y + 1, x, y, MoveType.PROMOTION, PieceType.BB));
                moves.add(new Move(type, x, y + 1, x, y, MoveType.PROMOTION, PieceType.BR));
                moves.add(new Move(type, x, y + 1, x, y, MoveType.PROMOTION, PieceType.BQ));
            } else if (((oneForward >> i) & 1) == 1) {
                moves.add(new Move(type, x, y + 1, x, y));
            }
        }

        // Two forward
        long twoForward = (currentPawns >> 16) & empty & (empty >> 8) & RANK_5;
        for (int i = initialIndex(twoForward); i < finalIndex(twoForward); i++) {
            int x = getX(i);
            int y = getY(i);

            if (((twoForward >> i) & 1) == 1) {
                moves.add(new Move(type, x, y + 2, x, y));
            }
        }

        // En passant
        if (!Move.isFirstMove(previousMove) && previousMove.getPiece().equals(PieceType.WP) && previousMove.getY1() == 1
                && previousMove.getY2() == 3 && previousMove.getX1() == previousMove.getX2()) {
            int col = previousMove.getX1();

            // Right capture
            long rightCapture = (currentPawns << 1) & whitePieces & RANK_4 & ~FILE_H & FILE_MASKS[col];
            if (rightCapture != 0) {
                int i = Long.numberOfTrailingZeros(rightCapture);
                int x = getX(i);
                int y = getY(i);
                moves.add(new Move(type, x + 1, y, x, y - 1, MoveType.EN_PASSANT));
            }

            // Left capture
            long leftCapture = (currentPawns >> 1) & whitePieces & RANK_4 & ~FILE_A & FILE_MASKS[col];
            if (leftCapture != 0) {
                int i = Long.numberOfTrailingZeros(leftCapture);
                int x = getX(i);
                int y = getY(i);
                moves.add(new Move(type, x - 1, y, x, y - 1, MoveType.EN_PASSANT));
            }

        }

        this.blackMoves = moves;
    }
}
