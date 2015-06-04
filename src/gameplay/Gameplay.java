package gameplay;

import board.Board;
import board.movement.Move;
import board.movement.Movement;

public class Gameplay {

    private Board board;
    private Movement movement;

    public Gameplay() {
        this.board = Board.StandardBoard();
        this.movement = new Movement(board);
    }

    public Gameplay(Board board) {
        this.board = board;
        this.movement = new Movement(board);
    }

    /**
     * Executes the given move, updating the board and movement members.
     * 
     * @param move
     */
    public void executeMove(Move move) {
        if (!movement.isLegalMove(move)) {
            board.printBoard();
            throw new IllegalArgumentException(move + " is not a legal move!");
        }

        board.updateBoard(move);
        movement.updateBoard(board);
    }

    public Board getBoard() {
        return board;
    }

    public Movement getMovement() {
        return movement;
    }

}
