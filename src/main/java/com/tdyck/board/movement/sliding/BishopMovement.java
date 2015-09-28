package com.tdyck.board.movement.sliding;

import com.tdyck.board.Board;
import com.tdyck.board.PieceType;

public class BishopMovement extends SlidingMovement {

    public BishopMovement(Board board) {
        super(board);
        initializeMoves(PieceType.WB);
        initializeMoves(PieceType.BB);
    }

}
