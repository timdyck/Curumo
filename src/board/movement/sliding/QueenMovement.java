package board.movement.sliding;

import java.util.List;

import board.Board;
import board.PieceType;
import board.movement.Move;

public class QueenMovement extends SlidingMovement {

    public QueenMovement(Board board) {
        super(board);
    }

    public List<Move> getWhiteMoves() {
        return getMoves(PieceType.WQ);
    }

    public List<Move> getBlackMoves() {
        return getMoves(PieceType.BQ);
    }
}
