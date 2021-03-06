package com.tdyck.board.movement;

import java.util.ArrayList;
import java.util.List;

import com.tdyck.board.Board;
import com.tdyck.board.PieceType;
import com.tdyck.board.movement.sliding.BishopMovement;
import com.tdyck.board.movement.sliding.QueenMovement;
import com.tdyck.board.movement.sliding.RookMovement;

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

    public Movement(Board board) {
        initializeMovement(board, Move.getFirstPreviousMove());
    }

    public Movement(Board board, Move previousMove) {
        initializeMovement(board, previousMove);
    }

    public Movement(Board board, Move previousMove, CastleFlags flags) {
        initializeMovement(board, previousMove, flags);
    }

    private void initializeMovement(Board newBoard, Move previousMove) {
        initializeMovement(newBoard, previousMove, new CastleFlags());
    }

    public void initializeMovement(Board newBoard, Move previousMove, CastleFlags flags) {
        this.board = newBoard;

        this.pawnMovement = new PawnMovement(newBoard, previousMove);
        this.rookMovement = new RookMovement(newBoard);
        this.knightMovement = new KnightMovement(newBoard);
        this.bishopMovement = new BishopMovement(newBoard);
        this.queenMovement = new QueenMovement(newBoard);
        this.kingMovement = new KingMovement(newBoard, flags);

        // Now that king is initialized, update for unsafe moves
        this.kingMovement.updateUnsafeMoves(getUnsafeForWhite(), getUnsafeForBlack());
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
        possibleWhiteMoves.addAll(knightMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(bishopMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(rookMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(queenMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(kingMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(pawnMovement.getWhiteMoves());

        return possibleWhiteMoves;
    }

    private List<Move> getAllBlackMoves() {
        List<Move> possibleBlackMoves = new ArrayList<Move>();

        possibleBlackMoves.addAll(knightMovement.getBlackMoves());
        possibleBlackMoves.addAll(bishopMovement.getBlackMoves());
        possibleBlackMoves.addAll(rookMovement.getBlackMoves());
        possibleBlackMoves.addAll(queenMovement.getBlackMoves());
        possibleBlackMoves.addAll(kingMovement.getBlackMoves());
        possibleBlackMoves.addAll(pawnMovement.getBlackMoves());

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

}
