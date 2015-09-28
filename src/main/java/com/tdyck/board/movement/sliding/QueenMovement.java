package com.tdyck.board.movement.sliding;

import com.tdyck.board.Board;
import com.tdyck.board.PieceType;

public class QueenMovement extends SlidingMovement {

    public QueenMovement(Board board) {
        super(board);
        initializeMoves(PieceType.WQ);
        initializeMoves(PieceType.BQ);
    }
}
