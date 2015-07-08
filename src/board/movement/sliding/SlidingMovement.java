package board.movement.sliding;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.PieceType;
import board.movement.Move;
import board.movement.PieceMovement;

public class SlidingMovement extends PieceMovement {

    public SlidingMovement(Board board) {
        super(board);
    }

    /**
     * @param piece
     * @return List of Moves depending on the type of sliding piece (rook,
     *         bishop or queen)
     */
    protected void initializeMoves(PieceType piece) {
        List<Move> moves = new ArrayList<Move>();
        long currentBitBoard = board.getBitBoard(piece);
        long allPossibleMovesBitBoard = 0L;

        for (int i : getIndices(currentBitBoard)) {
            int x = getX(i);
            int y = getY(i);

            long possibleMovesBitBoard = 0L;
            if (piece.equals(PieceType.WR) || piece.equals(PieceType.BR)) {
                // Vertical and horizontal for rooks
                possibleMovesBitBoard |= getLevelMovesBoard(x, y);
            } else if (piece.equals(PieceType.WB) || piece.equals(PieceType.BB)) {
                // Diagonal and Anti-diagonal for bishops
                possibleMovesBitBoard |= getDiagonalMovesBoard(x, y);
            } else if (piece.equals(PieceType.WQ) || piece.equals(PieceType.BQ)) {
                // Both for queen
                possibleMovesBitBoard |= getLevelMovesBoard(x, y);
                possibleMovesBitBoard |= getDiagonalMovesBoard(x, y);
            } else {
                throw new IllegalStateException(piece.name() + " is not a sliding piece!");
            }

            moves.addAll(getMoves(possibleMovesBitBoard, piece, x, y));
            allPossibleMovesBitBoard |= possibleMovesBitBoard;
        }

        setMoves(piece, moves, allPossibleMovesBitBoard);
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
    protected long getDiagonalMovesBoard(int x, int y) {
        long bitBoardS = getBitBoard(x, y);
        int s = (Board.DIMENSION * y) + x;

        long diagonalMask = DIAGONAL_MASKS[7 - (s / 8) + (s % 8)];
        long occupiedD = occupied & diagonalMask;
        long antiDiagonalMask = ANTIDIAGONAL_MASKS[(s / 8) + (s % 8)];
        long occupiedAD = occupied & antiDiagonalMask;

        long diagonalMoves = (occupiedD - 2 * bitBoardS) ^ reverse(reverse(occupiedD) - 2 * reverse(bitBoardS));
        long antiDiagonalMoves = (occupiedAD - 2 * bitBoardS) ^ reverse(reverse(occupiedAD) - 2 * reverse(bitBoardS));

        return (diagonalMoves & diagonalMask) | (antiDiagonalMoves & antiDiagonalMask);
    }

    protected long reverse(long num) {
        return Long.reverse(num);
    }
}
