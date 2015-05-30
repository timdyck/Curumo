package board.movement.sliding;

import java.util.List;

import board.Board;
import board.PieceType;
import board.movement.Move;

public class BishopMovement extends SlidingMovement {

    public BishopMovement(Board board) {
        super(board);
    }

    public List<Move> getWhiteMoves() {
        return getMoves(PieceType.WB);
    }

    public List<Move> getBlackMoves() {
        return getMoves(PieceType.BB);
    }

}
