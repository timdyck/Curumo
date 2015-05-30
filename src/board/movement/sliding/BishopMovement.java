package board.movement.sliding;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.movement.Move;

public class BishopMovement extends SlidingMovement {

    public BishopMovement(Board board) {
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
