package gameplay;

import board.Board;
import board.BoardUtilities;
import board.movement.Move;
import board.movement.Movement;

public class Gameplay {

    private Board board;
    private Move previousMove;
    private Movement movement;

    public Gameplay() {
        this.board = Board.StandardBoard();
        this.previousMove = Move.getFirstPreviousMove();
        this.movement = new Movement(board);
    }

    public Gameplay(Board board) {
        this.board = board;
        this.previousMove = Move.getFirstPreviousMove();
        this.movement = new Movement(board, this.previousMove);
    }

    public Gameplay(Board board, Move previousMove) {
        this.board = board;
        this.previousMove = previousMove;
        this.movement = new Movement(board, previousMove);
    }

    /**
     * Executes the given move, updating the board and movement members.
     * 
     * @param move
     */
    public void executeMove(Move move) {
        if (!movement.isLegalMove(move)) {
            BoardUtilities.printBoard(board);
            throw new IllegalArgumentException(move + " is not a legal move!");
        }

        board.updateBoard(move);
        movement.updateBoard(board, previousMove);

        previousMove = move;
    }

    public Board getBoard() {
        return board;
    }

    public Movement getMovement() {
        return movement;
    }

}
