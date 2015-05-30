package board.movement.sliding;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.PieceType;
import board.movement.Move;

public class RookMovement extends SlidingMovement {

    PieceType whitePiece = PieceType.WR;
    PieceType blackPiece = PieceType.BR;

    public RookMovement(Board board) {
        super(board);
    }

    public List<Move> getWhiteMoves() {
        List<Move> moves = new ArrayList<Move>();
        long currentRooks = board.getBitBoard(whitePiece);

        for (int rookIndex = initialIndex(currentRooks); rookIndex < finalIndex(currentRooks); rookIndex++) {
            if (((currentRooks >> rookIndex) & 1) == 1) {
                int x = getX(rookIndex);
                int y = getY(rookIndex);
                long possibleMovesBitBoard = getLevelMovesBoard(x, y);

                moves.addAll(getSlideMoves(possibleMovesBitBoard, whitePiece, x, y));
            }
        }

        return moves;
    }

    public List<Move> getBlackMoves() {
        List<Move> moves = new ArrayList<Move>();

        return moves;
    }
}
