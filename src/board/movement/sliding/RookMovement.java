package board.movement.sliding;

import board.Board;
import board.PieceType;

public class RookMovement extends SlidingMovement {

    public RookMovement(Board board) {
        super(board);
        initializeMoves(PieceType.WR);
        initializeMoves(PieceType.BR);
    }

}
