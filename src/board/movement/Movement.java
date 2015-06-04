package board.movement;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.movement.sliding.BishopMovement;
import board.movement.sliding.QueenMovement;
import board.movement.sliding.RookMovement;

public class Movement {

    private PawnMovement pawnMovement;
    private RookMovement rookMovement;
    private KnightMovement knightMovement;
    private BishopMovement bishopMovement;
    private QueenMovement queenMovement;
    private KingMovement kingMovement;

    public Movement(Board board) {
        updateBoard(board, new ArrayList<Move>());
    }

    public Movement(Board board, List<Move> previousMoves) {
        updateBoard(board, previousMoves);
    }

    public void updateBoard(Board newBoard, List<Move> previousMoves) {
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
    }

    public List<Move> getAllWhiteMoves() {
        List<Move> possibleWhiteMoves = new ArrayList<Move>();

        possibleWhiteMoves.addAll(pawnMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(rookMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(knightMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(bishopMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(queenMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(kingMovement.getWhiteMoves());

        return possibleWhiteMoves;
    }

    public List<Move> getAllBlackMoves() {
        List<Move> possibleBlackMoves = new ArrayList<Move>();

        possibleBlackMoves.addAll(pawnMovement.getBlackMoves());
        possibleBlackMoves.addAll(rookMovement.getBlackMoves());
        possibleBlackMoves.addAll(knightMovement.getBlackMoves());
        possibleBlackMoves.addAll(bishopMovement.getBlackMoves());
        possibleBlackMoves.addAll(queenMovement.getBlackMoves());
        possibleBlackMoves.addAll(kingMovement.getBlackMoves());

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

    public boolean isLegalMove(Move move) {
        if (move.getPiece().isWhitePiece()) {
            return getAllWhiteMoves().contains(move);
        } else {
            return getAllBlackMoves().contains(move);
        }
    }
}
