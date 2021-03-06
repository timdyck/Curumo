package com.tdyck.perft;

import java.util.List;

import com.tdyck.board.movement.Move;
import com.tdyck.gameplay.Gameplay;

/**
 * Algorithm to perform Performance Test, Move Path Enumeration
 */
public class Perft {

    /**
     * Recursive method to get the number of leaf nodes in the possible moves
     * tree for the current game. Prints out the number of leaf nodes off each
     * initial move.
     * 
     * @param game
     * @param maxDepth
     *            > currentDepth
     * @return count of lead nodes in the moves tree
     */
    public static int countLeafNodes(Gameplay game, int maxDepth) {
        return countLeafNodes(game, 1, maxDepth);
    }

    private static int countLeafNodes(Gameplay game, int currentDepth, int maxDepth) {
        List<Move> possibleMoves = game.getSafeMoves();
        if (currentDepth == maxDepth) {
            for (Move move : possibleMoves) {
                if (currentDepth == 1) {
                    System.out.println(move.toUciForm() + " 1");
                }
            }

            return possibleMoves.size();
        }

        int leafNodesCount = 0;
        for (Move move : possibleMoves) {
            Gameplay newGame = new Gameplay(game);
            newGame.executeMove(move);

            int leafCount = countLeafNodes(newGame, currentDepth + 1, maxDepth);
            leafNodesCount += leafCount;

            if (currentDepth == 1) {
                System.out.println(move.toUciForm() + " " + leafCount);
            }
        }

        return leafNodesCount;
    }
}
