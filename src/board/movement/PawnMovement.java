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
        for (int i : getIndices(captureRight)) {
            int x = getX(i);
            int y = getY(i);

            PieceType capturedPiece = getPieceAt(x, y);

            if ((((captureRight & RANK_8) >> i) & 1) == 1) {
                // Promotion
                moves.add(new Move(type, x - 1, y - 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.WN));
                moves.add(new Move(type, x - 1, y - 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.WB));
                moves.add(new Move(type, x - 1, y - 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.WR));
                moves.add(new Move(type, x - 1, y - 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.WQ));
            } else {
                moves.add(new Move(type, x - 1, y - 1, x, y, MoveType.CAPTURE, capturedPiece));
            }
        }

        // Left captures
        possibleWhiteCaptures |= (currentPawns << 9) & ~FILE_H;
        long captureLeft = (currentPawns << 9) & blackPieces & ~FILE_H;
        for (int i : getIndices(captureLeft)) {
            int x = getX(i);
            int y = getY(i);

            PieceType capturedPiece = getPieceAt(x, y);

            if ((((captureLeft & RANK_8) >> i) & 1) == 1) {
                // Promotion
                moves.add(new Move(type, x + 1, y - 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.WN));
                moves.add(new Move(type, x + 1, y - 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.WB));
                moves.add(new Move(type, x + 1, y - 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.WR));
                moves.add(new Move(type, x + 1, y - 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.WQ));
            } else {
                moves.add(new Move(type, x + 1, y - 1, x, y, MoveType.CAPTURE, capturedPiece));
            }
        }

        // Set all possible attacks
        this.possibleWhiteAttacksBitBoard = possibleWhiteCaptures;

        // One forward
        long oneForward = (currentPawns << 8) & empty;
        for (int i : getIndices(oneForward)) {
            int x = getX(i);
            int y = getY(i);

            if ((((oneForward & RANK_8) >> i) & 1) == 1) {
                // Promotion
                moves.add(new Move(type, x, y - 1, x, y, MoveType.PROMOTION, PieceType.WN));
                moves.add(new Move(type, x, y - 1, x, y, MoveType.PROMOTION, PieceType.WB));
                moves.add(new Move(type, x, y - 1, x, y, MoveType.PROMOTION, PieceType.WR));
                moves.add(new Move(type, x, y - 1, x, y, MoveType.PROMOTION, PieceType.WQ));
            } else {
                moves.add(new Move(type, x, y - 1, x, y));
            }
        }

        // Two forward
        long twoForward = (currentPawns << 16) & empty & (empty << 8) & RANK_4;
        for (int i : getIndices(twoForward)) {
            int x = getX(i);
            int y = getY(i);
            moves.add(new Move(type, x, y - 2, x, y));
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
        for (int i : getIndices(captureRight)) {
            int x = getX(i);
            int y = getY(i);

            PieceType capturedPiece = getPieceAt(x, y);

            if ((((captureRight & RANK_1) >> i) & 1) == 1) {
                // Promotion
                moves.add(new Move(type, x + 1, y + 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.BN));
                moves.add(new Move(type, x + 1, y + 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.BB));
                moves.add(new Move(type, x + 1, y + 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.BR));
                moves.add(new Move(type, x + 1, y + 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.BQ));
            } else {
                moves.add(new Move(type, x + 1, y + 1, x, y, MoveType.CAPTURE, capturedPiece));
            }
        }

        // Left captures
        possibleBlackCaptures |= (currentPawns >> 9) & ~FILE_A;
        long captureLeft = (currentPawns >> 9) & whitePieces & ~FILE_A;
        for (int i : getIndices(captureLeft)) {
            int x = getX(i);
            int y = getY(i);

            PieceType capturedPiece = getPieceAt(x, y);

            if ((((captureLeft & RANK_1) >> i) & 1) == 1) {
                // Promotion
                moves.add(new Move(type, x - 1, y + 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.BN));
                moves.add(new Move(type, x - 1, y + 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.BB));
                moves.add(new Move(type, x - 1, y + 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.BR));
                moves.add(new Move(type, x - 1, y + 1, x, y, MoveType.CAPTURE_AND_PROMOTION, capturedPiece, PieceType.BQ));
            } else {
                moves.add(new Move(type, x - 1, y + 1, x, y, MoveType.CAPTURE, capturedPiece));
            }
        }

        // Set all possible attacks
        this.possibleBlackAttacksBitBoard = possibleBlackCaptures;

        // One forward
        long oneForward = (currentPawns >> 8) & empty;
        for (int i : getIndices(oneForward)) {
            int x = getX(i);
            int y = getY(i);

            if ((((oneForward & RANK_1) >> i) & 1) == 1) {
                // Promotion
                moves.add(new Move(type, x, y + 1, x, y, MoveType.PROMOTION, PieceType.BN));
                moves.add(new Move(type, x, y + 1, x, y, MoveType.PROMOTION, PieceType.BB));
                moves.add(new Move(type, x, y + 1, x, y, MoveType.PROMOTION, PieceType.BR));
                moves.add(new Move(type, x, y + 1, x, y, MoveType.PROMOTION, PieceType.BQ));
            } else {
                moves.add(new Move(type, x, y + 1, x, y));
            }
        }

        // Two forward
        long twoForward = (currentPawns >> 16) & empty & (empty >> 8) & RANK_5;
        for (int i : getIndices(twoForward)) {
            int x = getX(i);
            int y = getY(i);
            moves.add(new Move(type, x, y + 2, x, y));
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

    /**
     * @param turn
     *            {@link PieceType.Colour} representing whose turn it is
     * @return the number of doubled pawns (1 if 2 pawns on the same file, 2 if
     *         3 pawns, etc, for all files)
     */
    public int countDoubledPawns(PieceType.Colour turn) {
        long bitBoard = turn.equals(PieceType.Colour.WHITE) ? board.getBitBoard(PieceType.WP) : board.getBitBoard(PieceType.BP);
        int count = 0;

        for (int i = 0; i < Board.DIMENSION; i++) {
            long file = bitBoard & FILE_MASKS[i];
            int numPawnsInFile = Long.bitCount(file);

            if (numPawnsInFile >= 2) {
                count += numPawnsInFile - 1;
            }
        }

        return count;
    }

    /**
     * @param turn
     *            {@link PieceType.Colour} representing whose turn it is
     * @return the number of isolated pawns, i.e. the number of pawns with no
     *         same-coloured pawn on an adjacent file
     */
    public int countIsolatedPawns(PieceType.Colour turn) {
        long bitBoard = turn.equals(PieceType.Colour.WHITE) ? board.getBitBoard(PieceType.WP) : board.getBitBoard(PieceType.BP);
        int count = 0;

        for (int i : getIndices(bitBoard)) {
            int x = getX(i);
            int y = getY(i);
            long pieceBitBoard = getBitBoard(x, y);

            if (turn.equals(PieceType.Colour.WHITE)) {
                if (((pieceBitBoard << 8) & occupied) == pieceBitBoard << 8) {
                    count += 1;
                }
            } else {
                if (((pieceBitBoard >> 8) & occupied) == pieceBitBoard >> 8) {
                    count += 1;
                }
            }
        }

        return count;
    }

    /**
     * @param turn
     *            {@link PieceType.Colour} representing whose turn it is
     * @return the number of blocked pawns, i.e. the number of pawns that can
     *         not move directly forward
     */
    public int countBlockedPawns(PieceType.Colour turn) {
        long bitBoard = turn.equals(PieceType.Colour.WHITE) ? board.getBitBoard(PieceType.WP) : board.getBitBoard(PieceType.BP);
        int count = 0;

        for (int i = 0; i < Board.DIMENSION; i++) {
            long file = bitBoard & FILE_MASKS[i];
            int numPawnsInFile = Long.bitCount(file);

            long prevFile = i == 0 ? 0 : bitBoard & FILE_MASKS[i - 1];
            int numPawnsInPrevFile = Long.bitCount(prevFile);

            long nextFile = i == Board.DIMENSION - 1 ? 0 : bitBoard & FILE_MASKS[i + 1];
            int numPawnsInNextFile = Long.bitCount(nextFile);

            if (numPawnsInPrevFile == 0 && numPawnsInNextFile == 0) {
                count += numPawnsInFile;
            }
        }

        return count;
    }

}
