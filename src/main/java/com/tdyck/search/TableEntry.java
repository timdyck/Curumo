package com.tdyck.search;

import com.tdyck.board.movement.Move;

/**
 * Value for the transposition table.
 */
public class TableEntry {

    private Move bestMove;
    private int depth;
    private double score;

    public TableEntry(Move bestMove, int depth, double score) {
        this.bestMove = bestMove;
        this.depth = depth;
        this.score = score;
    }

    public Move getBestMove() {
        return bestMove;
    }

    public int getDepth() {
        return depth;
    }

    public double getScore() {
        return score;
    }

}
