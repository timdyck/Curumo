package board.movement.sliding;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.PieceType;
import board.movement.Move;
import board.movement.Movement;

public class SlidingMovement extends Movement {

    public SlidingMovement(Board board) {
        super(board);
    }

    /**
     * @param bitBoard
     * @param type
     *            {@link PieceType} of the piece we want to find the moves of
     * @param x
     *            position of white piece we want to find the moves of
     * @param y
     *            position of white piece we want to find the moves of
     * @return list of all potential horizontal and vertical moves
     */
    protected List<Move> getSlideMoves(long possibleMovesBitBoard, PieceType type, int x, int y) {
        List<Move> moves = new ArrayList<Move>();

        // Remove moves that are not proper captures
        if (type.isWhitePiece()) {
            possibleMovesBitBoard &= ~whitePieces;
        } else if (type.isBlackPiece()) {
            possibleMovesBitBoard &= ~blackPieces;
        }

        long bitBoardS = getBitBoard(x, y);

        for (int i = initialIndex(possibleMovesBitBoard); i < finalIndex(possibleMovesBitBoard); i++) {
            if (((possibleMovesBitBoard >> i) & 1) == bitBoardS) {
                // Observing the piece in question
                continue;
            }
            if (((possibleMovesBitBoard >> i) & 1) == 1) {
                int moveX = getX(i);
                int moveY = getY(i);

                long oppositeColorPieces = type.isWhitePiece() ? blackPieces : whitePieces;
                if (((oppositeColorPieces >> i) & 1) == 1) {
                    // Capture
                    moves.add(new Move(type, x, y, moveX, moveY, true));
                } else {
                    moves.add(new Move(type, x, y, moveX, moveY));
                }
            }
        }

        return moves;
    }

    /**
     * @param x
     * @param y
     * @return bit board of potential horizontal and vertical moves
     */
    protected long getLevelMovesBoard(int x, int y) {
        long bitBoardS = getBitBoard(x, y);

        long horizontalMask = RANK_MASKS[y];
        long verticalMask = FILE_MASKS[x];
        long occupiedV = occupied & verticalMask;

        long horizontalMoves = (occupied - 2 * bitBoardS) ^ reverse(reverse(occupied) - 2 * reverse(bitBoardS));
        long verticalMoves = (occupiedV - 2 * bitBoardS) ^ reverse(reverse(occupiedV) - 2 * reverse(bitBoardS));

        return (horizontalMoves & horizontalMask) | (verticalMoves & verticalMask);
    }

    /**
     * @param x
     * @param y
     * @return bit board of potential diagonal and anti-diagonal moves
     */
    protected long getDiagonalMoves(int x, int y) {
        long bitBoardS = getBitBoard(x, y);
        int s = (Board.DIMENSION * y) + (7 - x);

        long diagonalMask = DIAGONAL_MASKS[7 - (s / 8) + (s % 8)];
        long occupiedD = occupied & diagonalMask;
        long antiDiagonalMask = ANTIDIAGONAL_MASKS[(s / 8) + (s % 8)];
        long occupiedAD = occupied & antiDiagonalMask;

        long diagonalMoves = (occupiedD - 2 * bitBoardS) ^ reverse(reverse(occupiedD) - 2 * reverse(bitBoardS));
        long antiDiagonalMoves = (occupiedAD - 2 * bitBoardS) ^ reverse(reverse(occupiedAD) - 2 * reverse(bitBoardS));

        return (diagonalMoves & diagonalMask) | (antiDiagonalMoves & antiDiagonalMask);
    }

    /**
     * Bottom left=0, bottom right=7, top left=56, top right= 63
     * 
     * @param x
     * @param y
     * @return
     */
    protected long getBitBoard(int x, int y) {
        int s = (Board.DIMENSION * y) + (7 - x);
        return 1L << s;
    }

    protected long reverse(long num) {
        return Long.reverse(num);
    }
}
