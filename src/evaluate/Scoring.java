package evaluate;

import gameplay.Gameplay;

import java.util.List;

import board.PieceType;
import board.movement.Move;

public class Scoring {

    public static final int MATE_SCORE = Short.MIN_VALUE / 2;

    Gameplay game;

    public Scoring(Gameplay game) {
        this.game = game;
    }

    public double materialScore() {
        /* @formatter:off */
        double score = 200 * (getNumOf(PieceType.WK) - getNumOf(PieceType.BK))
                    + 9 * (getNumOf(PieceType.WQ) - getNumOf(PieceType.BQ))
                    + 5 * (getNumOf(PieceType.WR) - getNumOf(PieceType.BR))
                    + 3 * (getNumOf(PieceType.WB) - getNumOf(PieceType.BB))
                    + 3 * (getNumOf(PieceType.WN) - getNumOf(PieceType.BN))
                    + 1 * (getNumOf(PieceType.WP) - getNumOf(PieceType.BP));
        /* @formatter:on */

        return game.getTurn().equals(PieceType.Colour.WHITE) ? score : -score;
    }

    public double positionalScore() {
        double mateScore = 0;

        List<Move> moves = game.getSafeMoves();
        if (moves.size() == 0) {
            System.out.println("RUH-ROH");
            mateScore = MATE_SCORE;
            System.out.println(mateScore);
        }

        List<Move> otherMoves = game.getTurn().equals(PieceType.Colour.WHITE) ? game.getSafeBlackMoves() : game
                .getSafeWhiteMoves();
        double mobilityScore = 0.1 * (moves.size() - otherMoves.size());

        return mateScore + mobilityScore;
    }

    private int getNumOf(PieceType piece) {
        return Long.bitCount(game.getBoard().getBitBoard(piece));
    }
}
