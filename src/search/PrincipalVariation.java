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

    public int depth;
    public Move bestMove;
    public double bestScore;

    public PrincipalVariation() {
        this.depth = DEFAULT_DEPTH;
    }

    public PrincipalVariation(int depth) {
        this.depth = depth;
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
        List<Move> moves = game.getSafeMoves();

        if (currentDepth == 0 || moves.isEmpty()) {
            return Evaluate.eval(game);
        }

        boolean firstMove = true;
        // TODO: sort moves, or make sure best is at the front?
        for (Move move : moves) {
            double score;

            Gameplay gameAfterMove = new Gameplay(game);
            gameAfterMove.executeMove(move);

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

            // TODO: If score is highest possible (checkmate), return

            if (currentDepth == this.depth && score > this.bestScore) {
                this.bestScore = score;
                this.bestMove = move;
                System.out.println("NEW SCORE OF " + score);
            }

            alpha = Math.max(alpha, score);
            if (alpha >= beta) {
                break;
            }
        }

        return alpha;
    }

    private double nullWindowSearch(Gameplay game, int depth, double beta) {
        List<Move> moves = game.getSafeMoves();

        if (depth == 0 || moves.isEmpty()) {
            return Evaluate.eval(game);
        }

        for (Move move : moves) {
            Gameplay gameAfterMove = new Gameplay(game);
            gameAfterMove.executeMove(move);

            double score = -nullWindowSearch(gameAfterMove, depth - 1, 1 - beta);

            if (score >= beta) {
                // fail-hard, beta cutoff
                return beta;
            }
        }

        // fail-hard, return alpha
        return beta - 1;
    }
}
