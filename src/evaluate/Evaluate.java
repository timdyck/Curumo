package evaluate;

import gameplay.Gameplay;

public class Evaluate {

    public static double eval(Gameplay game) {
        Scoring scoring = new Scoring(game);

        double score = scoring.materialScore() + scoring.positionalScore();

        return score;
    }
}
