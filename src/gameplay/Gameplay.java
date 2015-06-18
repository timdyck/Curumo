package gameplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Gameplay(Board board, PieceType.Colour turn) {
        this.board = board;
        this.previousMoves = new ArrayList<Move>();
        this.movement = new Movement(board, this.previousMoves);

        this.turn = turn;
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
        // Check if right color is moving
        if (!turn.equals(move.getPiece().getColour())) {
            throw new IllegalArgumentException(move + " is not a legal move, as it is " + turn.name() + "'s turn!");
        }

        // Update board and compute new movement options
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

    public boolean isLegalMove(Move move) {
        if (move.getPiece().isWhitePiece()) {
            return getSafeWhiteMoves().contains(move);
        } else {
            return getSafeBlackMoves().contains(move);
        }
    }

    /**
     * @return list of moves that won't put the white king in check
     */
    public List<Move> getSafeWhiteMoves() {
        List<Move> safeMoves = new ArrayList<Move>();
        List<Move> potentialMoves = movement.getAllMoves(PieceType.Colour.WHITE);

        for (Move potentialMove : potentialMoves) {
            Board newBoard = new Board(board);
            newBoard.updateBoardAfterMove(potentialMove);
            Movement newMovement = new Movement(newBoard, potentialMove);

            long kingBitBoard = newBoard.getBitBoard(PieceType.WK);
            if ((newMovement.getUnsafeForWhite() & kingBitBoard) != kingBitBoard) {
                safeMoves.add(potentialMove);
            }
        }

        return safeMoves;
    }

    /**
     * @return list of moves that won't put the black king in check
     */
    public List<Move> getSafeBlackMoves() {
        List<Move> safeMoves = new ArrayList<Move>();
        List<Move> potentialMoves = movement.getAllMoves(PieceType.Colour.BLACK);

        for (Move potentialMove : potentialMoves) {
            Board newBoard = new Board(board);
            newBoard.updateBoardAfterMove(potentialMove);
            Movement newMovement = new Movement(newBoard, potentialMove);

            long kingBitBoard = newBoard.getBitBoard(PieceType.BK);
            if ((newMovement.getUnsafeForBlack() & kingBitBoard) != kingBitBoard) {
                safeMoves.add(potentialMove);
            }
        }

        return safeMoves;
    }

    public List<Move> getSafeMoves() {
        // TODO: Yuck. So inefficient. Must be a better way. Find it.
        if (turn.equals(PieceType.Colour.WHITE)) {
            return getSafeWhiteMoves();
        } else {
            return getSafeBlackMoves();
        }
    }

    /**
     * @return map of UCI string move to {@link Move}
     */
    public Map<String, Move> getMovesMap() {
        Map<String, Move> movesMap = new HashMap<String, Move>();
        for (Move move : getSafeMoves()) {
            movesMap.put(move.toUciForm(), move);
        }
        return movesMap;
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
