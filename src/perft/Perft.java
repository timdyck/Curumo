package perft;

import gameplay.Gameplay;

import java.util.List;

import board.movement.Move;

public class Perft {

    /**
     * Recursive method to get the number of leaf nodes in the possible moves
     * tree for the current game.
     * 
     * @param game
     * @param currentDepth
     *            > 0
     * @param maxDepth
     *            > currentDepth
     * @return count of lead nodes in the moves tree
     */
    public static int countLeafNodes(Gameplay game, int currentDepth, int maxDepth) {
        if (currentDepth <= 0 || maxDepth <= 0 || maxDepth < currentDepth) {
            throw new IllegalStateException("Don't be an idiot. " + currentDepth + " layers deep? With a max depth " + maxDepth
                    + "? Good one.");
        }

        List<Move> possibleMoves = game.getMovement().getAllMoves(game.getTurn());
        if (currentDepth == maxDepth) {
            return possibleMoves.size();
        }

        int leafNodesCount = 0;
        for (Move move : possibleMoves) {
            Gameplay newGame = new Gameplay(game);
            newGame.executeMove(move);

            leafNodesCount += countLeafNodes(newGame, currentDepth + 1, maxDepth);
        }
        return leafNodesCount;
    }

}
