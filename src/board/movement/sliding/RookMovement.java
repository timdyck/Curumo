package board.movement.sliding;

import java.util.List;

import board.Board;
import board.PieceType;
import board.movement.Move;

public class RookMovement extends SlidingMovement {

    public RookMovement(Board board) {
        super(board);
    }

    public List<Move> getWhiteMoves() {
        return getMoves(PieceType.WR);
    }

    public List<Move> getBlackMoves() {
        return getMoves(PieceType.BR);
    }

}
