package board.movement.sliding;

import board.Board;
import board.PieceType;

public class BishopMovement extends SlidingMovement {

    public BishopMovement(Board board) {
        super(board);
        initializeMoves(PieceType.WB);
        initializeMoves(PieceType.BB);
    }

}
