package board.movement.sliding;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.movement.Move;

public class QueenMovement extends SlidingMovement {

    public QueenMovement(Board board) {
        super(board);
    }

    public List<Move> getWhiteMoves() {
        List<Move> moves = new ArrayList<Move>();

        return moves;
    }

    public List<Move> getBlackMoves() {
        List<Move> moves = new ArrayList<Move>();

        return moves;
    }
}
