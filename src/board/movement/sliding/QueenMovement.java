package board.movement.sliding;

import board.Board;
import board.PieceType;

public class QueenMovement extends SlidingMovement {

    public QueenMovement(Board board) {
        super(board);
        initializeMoves(PieceType.WQ);
        initializeMoves(PieceType.BQ);
    }
}
