package perft;

import gameplay.Gameplay;

import java.util.List;

import board.movement.Move;

/**
 * Algorithm to perform Performance Test, Move Path Enumeration
 */
public class Perft {

    public static int countLeafNodes(Gameplay game, int maxDepth) {
        return countLeafNodes(game, 1, maxDepth);
    }

    /**
     * Recursive method to get the number of leaf nodes in the possible moves
     * tree for the current game. Prints out the number of leaf nodes off each
     * initial move.
     * 
     * @param game
     * @param currentDepth
     *            > 0
     * @param maxDepth
     *            > currentDepth
     * @param printDepth
     *            depth to print current results
     * @return count of lead nodes in the moves tree
     */
    private static int countLeafNodes(Gameplay game, int currentDepth, int maxDepth) {
        if (currentDepth <= 0 || maxDepth <= 0 || maxDepth < currentDepth) {
            throw new IllegalStateException("Don't be an idiot. " + currentDepth + " layers deep? With a max depth " + maxDepth
                    + "? Good one.");
        }

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
