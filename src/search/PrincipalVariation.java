package search;

import evaluate.Evaluate;
import gameplay.Gameplay;

import java.util.List;

import board.movement.Move;

/**
 * Implementation of the Principal Variation Search algorithm.
 */
public class PrincipalVariation {

    public static final int DEPTH = 5;

    public int principalVariationSearch(Gameplay game, int depth, int alpha, int beta) {
        List<Move> moves = game.getSafeMoves();

        if (depth == 0 || moves.isEmpty()) {
            return Evaluate.eval(game);
        }

        boolean firstMove = true;
        // TODO: sort moves, or make sure best is at the front?
        for (Move move : moves) {
            int score;

            Gameplay gameAfterMove = new Gameplay(game);
            gameAfterMove.executeMove(move);

            if (!firstMove) {
                // Not first/best move, so search with null window
                score = -nullWindowSearch(gameAfterMove, depth - 1, -alpha);

                if (alpha < score && score < beta) {
                    // Better move, do full re-search
                    score = -principalVariationSearch(gameAfterMove, depth - 1, -beta, -score);
                }
            } else {
                // First (best?) move, so do full search
                score = -principalVariationSearch(gameAfterMove, depth - 1, -beta, -alpha);
                firstMove = false;
            }

            // TODO: If score is highest possible (checkmate), return

            alpha = Math.max(alpha, score);
            if (alpha >= beta) {
                break;
            }
        }

        return alpha;
    }

    private int nullWindowSearch(Gameplay game, int depth, int beta) {
        List<Move> moves = game.getSafeMoves();

        if (depth == 0 || moves.isEmpty()) {
            return Evaluate.eval(game);
        }

        for (Move move : moves) {
            Gameplay gameAfterMove = new Gameplay(game);
            gameAfterMove.executeMove(move);

            int score = -nullWindowSearch(gameAfterMove, depth - 1, 1 - beta);

            if (score >= beta) {
                // fail-hard, beta cutoff
                return beta;
            }
        }

        // fail-hard, return alpha
        return beta - 1;
    }
}
