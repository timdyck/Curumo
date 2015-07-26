package search;

import evaluate.Evaluate;
import gameplay.Gameplay;

import java.util.List;

import board.movement.Move;

/**
 * Implementation of the Principal Variation Search algorithm.
 */
public class PrincipalVariation {

    public static final double ALPHA = Integer.MIN_VALUE;
    public static final double BETA = Integer.MAX_VALUE;
    public static final int DEFAULT_DEPTH = 5;

    private int depth;
    private Move bestMove;
    private double bestScore;

    private TranspositionTable table;

    public PrincipalVariation() {
        this.depth = DEFAULT_DEPTH;
        this.table = new TranspositionTable();
    }

    public PrincipalVariation(int depth) {
        this.depth = depth;
        this.table = new TranspositionTable();
    }

    public Move search(Gameplay game) {
        this.bestMove = null;
        this.bestScore = Integer.MIN_VALUE;

        System.out.println("Best overall: " + principalVariationSearch(game));
        return this.bestMove;
    }

    public double principalVariationSearch(Gameplay game) {
        return principalVariationSearch(game, depth, ALPHA, BETA);
    }

    public double principalVariationSearch(Gameplay game, int currentDepth, double alpha, double beta) {
        List<Move> moves = game.getAllMoves();

        if (currentDepth == 0 || moves.isEmpty()) {
            return Evaluate.eval(game);
        }

        boolean firstMove = true;
        // TODO: sort moves, or make sure best is at the front?
        for (Move move : moves) {
            double score;

            Gameplay gameAfterMove = new Gameplay(game);
            gameAfterMove.executeMove(move);

            // Only evaluate positions that have not been seen
            if (!table.containsEntry(gameAfterMove)) {
                if (!firstMove) {
                    // Not first/best move, so search with null window
                    score = -nullWindowSearch(gameAfterMove, currentDepth - 1, -alpha);

                    if (alpha < score && score < beta) {
                        // Better move, do full re-search
                        score = -principalVariationSearch(gameAfterMove, currentDepth - 1, -beta, -score);
                    }
                } else {
                    // First (best?) move, so do full search
                    score = -principalVariationSearch(gameAfterMove, currentDepth - 1, -beta, -alpha);
                    firstMove = false;
                }

                // Add this board position to the transposition table
                table.addEntry(gameAfterMove, move, this.depth - currentDepth, score);
            } else {
                score = table.getEntryScore(gameAfterMove);
            }

            // Update potentially best move
            if (game.isLegalMove(move)) {
                if (currentDepth == this.depth && score > this.bestScore) {
                    this.bestScore = score;
                    this.bestMove = move;
                    System.out.println("NEW SCORE OF " + score);
                    System.out.println("MOVE: " + move.toUciForm());
                }

                alpha = Math.max(alpha, score);
                if (alpha >= beta) {
                    break;
                }
            }
        }

        return alpha;
    }

    private double nullWindowSearch(Gameplay game, int depth, double beta) {
        List<Move> moves = game.getAllMoves();

        if (depth == 0 || moves.isEmpty()) {
            return Evaluate.eval(game);
        }

        for (Move move : moves) {
            double score;

            Gameplay gameAfterMove = new Gameplay(game);
            gameAfterMove.executeMove(move);

            // Only evaluate positions that have not been seen
            if (!table.containsEntry(gameAfterMove)) {
                score = -nullWindowSearch(gameAfterMove, depth - 1, 1 - beta);
            } else {
                score = table.getEntryScore(gameAfterMove);
            }

            if (score >= beta) {
                // fail-hard, beta cutoff
                return beta;
            }
        }

        // fail-hard, return alpha
        return beta - 1;
    }
}
