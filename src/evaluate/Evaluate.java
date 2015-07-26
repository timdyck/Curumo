package evaluate;

import gameplay.Gameplay;

public class Evaluate {

    public static final int EARLY_GAME = 7;

    public static double eval(Gameplay game) {
        Scoring scoring = new Scoring(game);

        int materialWeight = 1;
        int positionWeight = 1;

        if (isEarlyGame(game)) {
            positionWeight = 3;
        }

        double score = materialWeight * scoring.materialScore() + positionWeight * scoring.positionalScore();
        return score;
    }

    private static boolean isEarlyGame(Gameplay game) {
        return game.getNumberOfMoves() <= EARLY_GAME;
    }
}
