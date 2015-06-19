package board.movement;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.BoardUtils;
import board.PieceType;
import board.movement.sliding.BishopMovement;
import board.movement.sliding.QueenMovement;
import board.movement.sliding.RookMovement;

/**
 * Encapsulates every type of {@link PieceMovement} possible
 */
public final class Movement {

    private Board board;

    private PawnMovement pawnMovement;
    private RookMovement rookMovement;
    private KnightMovement knightMovement;
    private BishopMovement bishopMovement;
    private QueenMovement queenMovement;
    private KingMovement kingMovement;

    // Castling flags and initial positions
    private boolean wKCastle = true;
    private boolean wQCastle = true;
    private boolean bKCastle = true;
    private boolean bQCastle = true;
    private final long wQRookPos = 0x0000000000000080L;
    private final long wKRookPos = 0x0000000000000001L;
    private final long bQRookPos = 0x8000000000000000L;
    private final long bKRookPos = 0x0100000000000000L;

    public Movement(Board board) {
        initializeMovement(board, new ArrayList<Move>());
    }

    public Movement(Board board, List<Move> previousMoves) {
        initializeMovement(board, previousMoves);
    }

    public Movement(Board board, Move previousMove) {
        List<Move> previousMoves = new ArrayList<Move>();
        previousMoves.add(previousMove);
        initializeMovement(board, previousMoves);
    }

    /**
     * Updates the board, movement, and castle flags.
     * 
     * @param newBoard
     * @param previousMoves
     */
    public void initializeMovement(Board newBoard, List<Move> previousMoves) {
        this.board = newBoard;

        if (previousMoves.isEmpty()) {
            this.pawnMovement = new PawnMovement(newBoard, Move.getFirstPreviousMove());
        } else {
            this.pawnMovement = new PawnMovement(newBoard, previousMoves.get(previousMoves.size() - 1));
        }
        this.rookMovement = new RookMovement(newBoard);
        this.knightMovement = new KnightMovement(newBoard);
        this.bishopMovement = new BishopMovement(newBoard);
        this.queenMovement = new QueenMovement(newBoard);
        this.kingMovement = new KingMovement(newBoard);

        // Now that king is initialized, update for unsafe moves
        this.kingMovement.updateUnsafeMoves(getUnsafeForWhite(), getUnsafeForBlack());

        updateCastleFlags();
    }

    public List<Move> getAllMoves(PieceType.Colour turn) {
        if (turn.equals(PieceType.Colour.WHITE)) {
            return getAllWhiteMoves();
        } else {
            return getAllBlackMoves();
        }
    }

    private List<Move> getAllWhiteMoves() {
        List<Move> possibleWhiteMoves = new ArrayList<Move>();

        possibleWhiteMoves.addAll(pawnMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(rookMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(knightMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(bishopMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(queenMovement.getWhiteMoves());

        // Remove illegal castles
        List<Move> kingMoves = kingMovement.getWhiteMoves();
        for (Move kingMove : kingMovement.getWhiteMoves()) {
            if (!isWKCastle() && kingMove.getX1() == 4 && kingMove.getX2() == 6) {
                kingMoves.remove(kingMove);
            } else if (!isWQCastle() && kingMove.getX1() == 4 && kingMove.getX2() == 2) {
                kingMoves.remove(kingMove);
            }
        }
        possibleWhiteMoves.addAll(kingMoves);

        return possibleWhiteMoves;
    }

    private List<Move> getAllBlackMoves() {
        List<Move> possibleBlackMoves = new ArrayList<Move>();

        possibleBlackMoves.addAll(pawnMovement.getBlackMoves());
        possibleBlackMoves.addAll(rookMovement.getBlackMoves());
        possibleBlackMoves.addAll(knightMovement.getBlackMoves());
        possibleBlackMoves.addAll(bishopMovement.getBlackMoves());
        possibleBlackMoves.addAll(queenMovement.getBlackMoves());

        // Remove illegal castles
        List<Move> kingMoves = kingMovement.getBlackMoves();
        for (Move kingMove : kingMovement.getBlackMoves()) {
            if (!isBKCastle() && kingMove.getX1() == 4 && kingMove.getX2() == 6) {
                kingMoves.remove(kingMove);
            } else if (!isBQCastle() && kingMove.getX1() == 4 && kingMove.getX2() == 2) {
                kingMoves.remove(kingMove);
            }
        }
        possibleBlackMoves.addAll(kingMoves);

        return possibleBlackMoves;
    }

    public long getUnsafeForWhite() {
        long unsafeMoves = 0L;
        unsafeMoves |= pawnMovement.getBlackPossibleMoves();
        unsafeMoves |= rookMovement.getBlackPossibleMoves();
        unsafeMoves |= knightMovement.getBlackPossibleMoves();
        unsafeMoves |= bishopMovement.getBlackPossibleMoves();
        unsafeMoves |= queenMovement.getBlackPossibleMoves();
        unsafeMoves |= kingMovement.getBlackPossibleMoves();

        return unsafeMoves;
    }

    public long getUnsafeForBlack() {
        long unsafeMoves = 0L;

        unsafeMoves |= pawnMovement.getWhitePossibleMoves();
        unsafeMoves |= rookMovement.getWhitePossibleMoves();
        unsafeMoves |= knightMovement.getWhitePossibleMoves();
        unsafeMoves |= bishopMovement.getWhitePossibleMoves();
        unsafeMoves |= queenMovement.getWhitePossibleMoves();
        unsafeMoves |= kingMovement.getWhitePossibleMoves();

        return unsafeMoves;
    }

    public boolean isKingInCheck(PieceType.Colour kingColour) {
        if (kingColour.equals(PieceType.Colour.WHITE)) {
            long kingBitBoard = board.getBitBoard(PieceType.WK);
            return (kingBitBoard & getUnsafeForWhite()) == kingBitBoard;
        } else {
            long kingBitBoard = board.getBitBoard(PieceType.BK);
            return (kingBitBoard & getUnsafeForBlack()) == kingBitBoard;
        }
    }

    public void updateCastleFlags() {
        if (this.wKCastle || this.wQCastle) {
            // Check if king has moved
            if (board.getBitBoard(PieceType.WK) != BoardUtils.WK_INITIAL) {
                this.wKCastle = false;
                this.wQCastle = false;
            }

            // Check if either rook has moved
            long rookBoard = board.getBitBoard(PieceType.WR);
            if ((rookBoard & wKRookPos) != wKRookPos) {
                wKCastle = false;
            } else if ((rookBoard & wQRookPos) != wQRookPos) {
                wQCastle = false;
            }
        }

        if (this.bKCastle || this.bQCastle) {
            // Check if king has moved
            if (board.getBitBoard(PieceType.BK) != BoardUtils.BK_INITIAL) {
                this.bKCastle = false;
                this.bQCastle = false;
            }

            // Check if either rook has moved
            long rookBoard = board.getBitBoard(PieceType.BR);
            if ((rookBoard & bKRookPos) != bKRookPos) {
                bKCastle = false;
            } else if ((rookBoard & bQRookPos) != bQRookPos) {
                bQCastle = false;
            }
        }
    }

    /**********************
     * Getters and Setters
     **********************/

    public PawnMovement getPawnMovement() {
        return pawnMovement;
    }

    public RookMovement getRookMovement() {
        return rookMovement;
    }

    public KnightMovement getKnightMovement() {
        return knightMovement;
    }

    public BishopMovement getBishopMovement() {
        return bishopMovement;
    }

    public QueenMovement getQueenMovement() {
        return queenMovement;
    }

    public KingMovement getKingMovement() {
        return kingMovement;
    }

    public boolean isWKCastle() {
        return wKCastle;
    }

    public void noWKCastle() {
        wKCastle = false;
    }

    public boolean isWQCastle() {
        return wQCastle;
    }

    public void noWQCastle() {
        wQCastle = false;
    }

    public boolean isBKCastle() {
        return bKCastle;
    }

    public void noBKCastle() {
        bKCastle = false;
    }

    public boolean isBQCastle() {
        return bQCastle;
    }

    public void noBQCastle() {
        bQCastle = false;
    }
}
