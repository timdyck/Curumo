package com.tdyck.board.movement.sliding;

import com.tdyck.board.Board;
import com.tdyck.board.PieceType;

public class RookMovement extends SlidingMovement {

    public RookMovement(Board board) {
        super(board);
        initializeMoves(PieceType.WR);
        initializeMoves(PieceType.BR);
    }

}
