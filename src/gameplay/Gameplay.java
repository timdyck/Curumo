package gameplay;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.BoardUtils;
import board.PieceType;
import board.movement.Move;
import board.movement.Movement;

public class Gameplay {

    private Board board;
    private List<Move> previousMoves;
    private Movement movement;

    private PieceType.Colour turn;

    public Gameplay() {
        this.board = Board.StandardBoard();
        this.previousMoves = new ArrayList<Move>();
        this.movement = new Movement(board);

        this.turn = PieceType.Colour.WHITE;
    }

    public Gameplay(Board board) {
        this.board = board;
        this.previousMoves = new ArrayList<Move>();
        this.movement = new Movement(board, this.previousMoves);

        this.turn = PieceType.Colour.WHITE;
    }

    public Gameplay(Board board, Move previousMove) {
        this.board = board;
        this.previousMoves = new ArrayList<Move>();
        this.previousMoves.add(previousMove);
        this.movement = new Movement(board, previousMoves);

        if (previousMove.getPiece().isWhitePiece()) {
            this.turn = PieceType.Colour.BLACK;
        } else {
            this.turn = PieceType.Colour.WHITE;
        }
    }

    /**
     * Copy constructor
     * 
     * @param game
     */
    public Gameplay(Gameplay game) {
        this.board = new Board(game.getBoard());
        this.previousMoves = new ArrayList<Move>();
        this.previousMoves.addAll(game.getPreviousMoves());
        this.movement = new Movement(game.getBoard());
        this.turn = game.turn;
    }

    /**
     * Executes the given move, updating the board and movement members.
     * 
     * @param move
     */
    public void executeMove(Move move) {
        if (!turn.equals(move.getPiece().getColour())) {
            throw new IllegalArgumentException(move + " is not a legal move, as it is " + turn.name() + "'s turn!");
        }

        if (!movement.isLegalMove(move)) {
            BoardUtils.printBoard(board);
            throw new IllegalArgumentException(move + " is not a legal move!");
        }

        board.updateBoardAfterMove(move);
        previousMoves.add(move);
        movement.initializeMovement(board, previousMoves);

        turn = turn.getOppositeColour();
    }

    /**
     * Undoes the last move, updating the board and movement members.
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
        movement.initializeMovement(board, previousMoves);

        turn = turn.getOppositeColour();
    }

    public Board getBoard() {
        return board;
    }

    public List<Move> getPreviousMoves() {
        return previousMoves;
    }

    public Movement getMovement() {
        return movement;
    }

    public PieceType.Colour getTurn() {
        return turn;
    }

}
