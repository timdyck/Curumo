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
        this.pawnMovement = new PawnMovement(board);
        this.rookMovement = new RookMovement(board);
        this.knightMovement = new KnightMovement(board);
        this.bishopMovement = new BishopMovement(board);
        this.queenMovement = new QueenMovement(board);
        this.kingMovement = new KingMovement(board);

        // Now that king is initialized, update for unsafe moves
        kingMovement.updateUnsafeMoves(getUnsafeForWhite(), getUnsafeForBlack());
    }

    public List<Move> getAllWhiteMoves(Move previousMove) {
        List<Move> possibleWhiteMoves = new ArrayList<Move>();

        possibleWhiteMoves.addAll(pawnMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(rookMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(knightMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(bishopMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(queenMovement.getWhiteMoves());
        possibleWhiteMoves.addAll(kingMovement.getWhiteMoves());

        return possibleWhiteMoves;
    }

    public List<Move> getAllBlackMoves(Move previousMove) {
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
}
