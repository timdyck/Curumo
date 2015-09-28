package com.tdyck.board;

import java.util.HashMap;
import java.util.Map;

import com.tdyck.board.movement.Move;

public class Board {

    public final static int DIMENSION = 8;
    public final static int NUM_SQUARES = DIMENSION * DIMENSION;
    public final static int NUM_BOARDS = 12;

    private Map<PieceType, Long> bitBoards;

    public Board(Map<PieceType, Long> bitBoards) {
        this.bitBoards = bitBoards;
    }

    /**
     * Copy constructor
     * 
     * @param board
     */
    public Board(Board board) {
        this.bitBoards = new HashMap<PieceType, Long>();
        this.bitBoards.putAll(board.getBitBoards());
    }

    public static Board StandardBoard() {
        Map<PieceType, Long> bitBoards = new HashMap<PieceType, Long>();
        bitBoards.put(PieceType.WP, BoardUtils.WP_INITIAL);
        bitBoards.put(PieceType.WR, BoardUtils.WR_INITIAL);
        bitBoards.put(PieceType.WN, BoardUtils.WN_INITIAL);
        bitBoards.put(PieceType.WB, BoardUtils.WB_INITIAL);
        bitBoards.put(PieceType.WQ, BoardUtils.WQ_INITIAL);
        bitBoards.put(PieceType.WK, BoardUtils.WK_INITIAL);
        bitBoards.put(PieceType.BP, BoardUtils.BP_INITIAL);
        bitBoards.put(PieceType.BR, BoardUtils.BR_INITIAL);
        bitBoards.put(PieceType.BN, BoardUtils.BN_INITIAL);
        bitBoards.put(PieceType.BB, BoardUtils.BB_INITIAL);
        bitBoards.put(PieceType.BQ, BoardUtils.BQ_INITIAL);
        bitBoards.put(PieceType.BK, BoardUtils.BK_INITIAL);
        return new Board(bitBoards);
    }

    public Map<PieceType, Long> getBitBoards() {
        return bitBoards;
    }

    public long getBitBoard(PieceType type) {
        return bitBoards.get(type);
    }

    public void setBitBoard(PieceType piece, long newBitBoard) {
        bitBoards.put(piece, newBitBoard);
    }

    /**
     * Updates the board after the given move is executed.
     * 
     * @param move
     */
    public void updateBoardAfterMove(Move move) {
        long fromBitBoard = getBitBoard(move.getX1(), move.getY1());
        long toBitBoard = getBitBoard(move.getX2(), move.getY2());

        switch (move.getType()) {
        case CAPTURE:
            updateAfterCapture(move, fromBitBoard, toBitBoard);
            break;
        case CAPTURE_AND_PROMOTION:
            updateAfterCaptureAndPromotion(move, fromBitBoard, toBitBoard);
            break;
        case CASTLE:
            updateAfterCastle(move, fromBitBoard, toBitBoard);
            break;
        case EN_PASSANT:
            updateAfterEnPassant(move, fromBitBoard, toBitBoard);
            break;
        case PROMOTION:
            updateAfterPromotion(move, fromBitBoard, toBitBoard);
            break;
        case TYPICAL:
            updateTypicalMove(move, fromBitBoard, toBitBoard);
            break;
        default:
            break;
        }
    }

    /**
     * Updates the board to the given move was executed.
     * 
     * @param previousMove
     */
    public void updateBoardAfterMoveUndo(Move previousMove) {
        long fromBitBoard = getBitBoard(previousMove.getX2(), previousMove.getY2());
        long toBitBoard = getBitBoard(previousMove.getX1(), previousMove.getY1());

        switch (previousMove.getType()) {
        case CAPTURE:
            undoCapture(previousMove, fromBitBoard, toBitBoard);
            break;
        case CAPTURE_AND_PROMOTION:
            undoCaptureAndPromotion(previousMove, fromBitBoard, toBitBoard);
            break;
        case CASTLE:
            undoCastle(previousMove, fromBitBoard, toBitBoard);
            break;
        case EN_PASSANT:
            undoEnPassant(previousMove, fromBitBoard, toBitBoard);
            break;
        case PROMOTION:
            undoPromotion(previousMove, fromBitBoard, toBitBoard);
            break;
        case TYPICAL:
            updateTypicalMove(previousMove, fromBitBoard, toBitBoard);
            break;
        default:
            break;
        }
    }

    private void updateTypicalMove(Move move, long fromBitBoard, long toBitBoard) {
        long pieceBitBoard = getBitBoard(move.getPiece());
        pieceBitBoard &= ~fromBitBoard;
        pieceBitBoard |= toBitBoard;
        setBitBoard(move.getPiece(), pieceBitBoard);
    }

    private void updateAfterCapture(Move move, long fromBitBoard, long toBitBoard) {
        // Remove taken piece
        long capturedPieceBitBoard = getBitBoard(move.getCapturedPiece());
        capturedPieceBitBoard &= ~toBitBoard;
        setBitBoard(move.getCapturedPiece(), capturedPieceBitBoard);

        // Move piece to new location
        long pieceBitBoard = getBitBoard(move.getPiece());
        pieceBitBoard &= ~fromBitBoard;
        pieceBitBoard |= toBitBoard;
        setBitBoard(move.getPiece(), pieceBitBoard);
    }

    private void undoCapture(Move move, long fromBitBoard, long toBitBoard) {
        // Re-add taken piece
        long capturedPieceBitBoard = getBitBoard(move.getCapturedPiece());
        capturedPieceBitBoard |= fromBitBoard;
        setBitBoard(move.getCapturedPiece(), capturedPieceBitBoard);

        // Move piece to old location
        long pieceBitBoard = getBitBoard(move.getPiece());
        pieceBitBoard |= toBitBoard;
        pieceBitBoard &= ~fromBitBoard;
        setBitBoard(move.getPiece(), pieceBitBoard);
    }

    private void updateAfterPromotion(Move move, long fromBitBoard, long toBitBoard) {
        // Add new piece
        long promotionPieceBitBoard = getBitBoard(move.getPromotionPiece());
        promotionPieceBitBoard |= toBitBoard;
        setBitBoard(move.getPromotionPiece(), promotionPieceBitBoard);

        // Remove old pawn
        long pieceBitBoard = getBitBoard(move.getPiece());
        pieceBitBoard &= ~fromBitBoard;
        setBitBoard(move.getPiece(), pieceBitBoard);
    }

    private void undoPromotion(Move move, long fromBitBoard, long toBitBoard) {
        // Remove new piece
        long promotionPieceBitBoard = getBitBoard(move.getPromotionPiece());
        promotionPieceBitBoard &= ~fromBitBoard;
        setBitBoard(move.getPromotionPiece(), promotionPieceBitBoard);

        // Re-add old pawn
        long pieceBitBoard = getBitBoard(move.getPiece());
        pieceBitBoard |= toBitBoard;
        setBitBoard(move.getPiece(), pieceBitBoard);
    }

    private void updateAfterCaptureAndPromotion(Move move, long fromBitBoard, long toBitBoard) {
        // Remove taken piece
        long capturedPieceBitBoard = getBitBoard(move.getCapturedPiece());
        capturedPieceBitBoard &= ~toBitBoard;
        setBitBoard(move.getCapturedPiece(), capturedPieceBitBoard);

        // Add new piece
        long promotionPieceBitBoard = getBitBoard(move.getPromotionPiece());
        promotionPieceBitBoard |= toBitBoard;
        setBitBoard(move.getPromotionPiece(), promotionPieceBitBoard);

        // Remove old pawn
        long pieceBitBoard = getBitBoard(move.getPiece());
        pieceBitBoard &= ~fromBitBoard;
        setBitBoard(move.getPiece(), pieceBitBoard);
    }

    private void undoCaptureAndPromotion(Move move, long fromBitBoard, long toBitBoard) {
        // Re-add taken piece
        long capturedPieceBitBoard = getBitBoard(move.getCapturedPiece());
        capturedPieceBitBoard |= fromBitBoard;
        setBitBoard(move.getCapturedPiece(), capturedPieceBitBoard);

        // Remove new piece
        long promotionPieceBitBoard = getBitBoard(move.getPromotionPiece());
        promotionPieceBitBoard &= ~fromBitBoard;
        setBitBoard(move.getPromotionPiece(), promotionPieceBitBoard);

        // Re-add old pawn
        long pieceBitBoard = getBitBoard(move.getPiece());
        pieceBitBoard |= toBitBoard;
        setBitBoard(move.getPiece(), pieceBitBoard);
    }

    private void updateAfterEnPassant(Move move, long fromBitBoard, long toBitBoard) {
        PieceType capturedPiece;
        if (move.getPiece().isWhitePiece()) {
            capturedPiece = PieceType.BP;
        } else {
            capturedPiece = PieceType.WP;
        }

        // Remove taken piece
        long capturedPieceBitBoard = getBitBoard(capturedPiece);
        long capturedPieceLocation = getBitBoard(move.getX2(), move.getY1());
        capturedPieceBitBoard &= ~capturedPieceLocation;
        setBitBoard(capturedPiece, capturedPieceBitBoard);

        // Move piece to new location
        long pieceBitBoard = getBitBoard(move.getPiece());
        pieceBitBoard |= toBitBoard;
        pieceBitBoard &= ~fromBitBoard;
        setBitBoard(move.getPiece(), pieceBitBoard);
    }

    private void undoEnPassant(Move move, long fromBitBoard, long toBitBoard) {
        PieceType capturedPiece;
        if (move.getPiece().isWhitePiece()) {
            capturedPiece = PieceType.BP;
        } else {
            capturedPiece = PieceType.WP;
        }

        // Re-add taken piece
        long capturedPieceBitBoard = getBitBoard(capturedPiece);
        long capturedPieceLocation = getBitBoard(move.getX2(), move.getY1());
        capturedPieceBitBoard |= capturedPieceLocation;
        setBitBoard(capturedPiece, capturedPieceBitBoard);

        // Move piece to old location
        long pieceBitBoard = getBitBoard(move.getPiece());
        pieceBitBoard |= toBitBoard;
        pieceBitBoard &= ~fromBitBoard;
        setBitBoard(move.getPiece(), pieceBitBoard);
    }

    private void updateAfterCastle(Move move, long fromBitBoard, long toBitBoard) {
        PieceType castledRook;
        long rookOldLocation;
        long rookNewLocation;

        // Get appropriate rook
        if (move.getPiece().isWhitePiece()) {
            castledRook = PieceType.WR;
        } else {
            castledRook = PieceType.BR;
        }

        // Get rook start and end positions
        if (move.getX2() - move.getX1() > 0) {
            // King-side Castle
            rookOldLocation = getBitBoard(move.getX1() + 3, move.getY1());
            rookNewLocation = getBitBoard(move.getX1() + 1, move.getY1());
        } else {
            // Queen-side Castle
            rookOldLocation = getBitBoard(move.getX1() - 4, move.getY1());
            rookNewLocation = getBitBoard(move.getX1() - 1, move.getY1());
        }

        // Move rook to new location
        long rookBitBoard = getBitBoard(castledRook);
        rookBitBoard |= rookNewLocation;

        // Remove rook's old position
        rookBitBoard &= ~rookOldLocation;
        setBitBoard(castledRook, rookBitBoard);

        // Move piece to new location
        long pieceBitBoard = getBitBoard(move.getPiece());
        pieceBitBoard |= toBitBoard;
        pieceBitBoard &= ~fromBitBoard;
        setBitBoard(move.getPiece(), pieceBitBoard);
    }

    private void undoCastle(Move move, long fromBitBoard, long toBitBoard) {
        PieceType castledRook;
        long rookOldLocation;
        long rookNewLocation;

        // Get appropriate rook
        if (move.getPiece().isWhitePiece()) {
            castledRook = PieceType.WR;
        } else {
            castledRook = PieceType.BR;
        }

        // Get rook start and end positions
        if (move.getX2() - move.getX1() > 0) {
            // King-side Castle
            rookOldLocation = getBitBoard(move.getX1() + 3, move.getY1());
            rookNewLocation = getBitBoard(move.getX1() + 1, move.getY1());
        } else {
            // Queen-side Castle
            rookOldLocation = getBitBoard(move.getX1() - 4, move.getY1());
            rookNewLocation = getBitBoard(move.getX1() - 1, move.getY1());
        }

        // Remove rook's to new location
        long rookBitBoard = getBitBoard(castledRook);
        rookBitBoard &= ~rookNewLocation;

        // Add rook's old position
        rookBitBoard |= rookOldLocation;
        setBitBoard(castledRook, rookBitBoard);

        // Move piece to old location
        long pieceBitBoard = getBitBoard(move.getPiece());
        pieceBitBoard |= toBitBoard;
        pieceBitBoard &= ~fromBitBoard;
        setBitBoard(move.getPiece(), pieceBitBoard);
    }

    /**
     * Bottom left=0, bottom right=7, top left=56, top right= 63
     * 
     * @param x
     * @param y
     * @return a bitboard with a 1 at position (x,y)
     */
    private long getBitBoard(int x, int y) {
        int s = (Board.DIMENSION * y) + (DIMENSION - 1 - x);
        return 1L << s;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bitBoards == null) ? 0 : bitBoards.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Board other = (Board) obj;
        if (bitBoards == null) {
            if (other.bitBoards != null)
                return false;
        } else if (!bitBoards.equals(other.bitBoards))
            return false;
        return true;
    }

}
