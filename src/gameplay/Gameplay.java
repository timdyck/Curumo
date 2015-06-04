package gameplay;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.BoardUtils;
import board.movement.Move;
import board.movement.Movement;

public class Gameplay {

    private Board board;
    private List<Move> previousMoves;
    private Movement movement;

    public Gameplay() {
        this.board = Board.StandardBoard();
        this.previousMoves = new ArrayList<Move>();
        this.movement = new Movement(board);
    }

    public Gameplay(Board board) {
        this.board = board;
        this.previousMoves = new ArrayList<Move>();
        this.movement = new Movement(board, this.previousMoves);
    }

    public Gameplay(Board board, Move previousMove) {
        this.board = board;
        this.previousMoves = new ArrayList<Move>();
        this.previousMoves.add(previousMove);
        this.movement = new Movement(board, previousMoves);
    }

    /**
     * Executes the given move, updating the board and movement members.
     * 
     * @param move
     */
    public void executeMove(Move move) {
        if (!movement.isLegalMove(move)) {
            BoardUtils.printBoard(board);
            throw new IllegalArgumentException(move + " is not a legal move!");
        }

        board.updateBoardAfterMove(move);
        previousMoves.add(move);
        movement.updateBoard(board, previousMoves);
    }

    /**
     * Executes the given move, updating the board and movement members.
     * 
     * @param move
     */
    public void undoMove() {
        if (previousMoves.isEmpty()) {
            BoardUtils.printBoard(board);
            throw new IllegalArgumentException("No moves to undo!");
        }

        Move previousMove = previousMoves.get(previousMoves.size() - 1);

        board.updateBoardAfterMoveUndo(previousMove);
        previousMoves.add(previousMove);
        movement.updateBoard(board, previousMoves);
    }

    public Board getBoard() {
        return board;
    }

    public Movement getMovement() {
        return movement;
    }

}
