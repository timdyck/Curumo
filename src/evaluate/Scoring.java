package evaluate;

import gameplay.Gameplay;
import board.Board;
import board.PieceType;
import board.PieceType.Colour;
import board.movement.PawnMovement;
import board.movement.PieceMovement;

public class Scoring {

    public final int pawnValue = 100;
    public final int knightValue = 300;
    public final int bishopValue = 300;
    public final int rookValue = 500;
    public final int queenValue = 900;
    public final int kingValue = 20000;

    /* @formatter:off */
    public final int[] whitePawnPosVals = new int[]
    {
        0,  0,  0,  0,  0,  0,  0,  0,
        5, 10, 10,-20,-20, 10, 10,  5,
        5, -5,-10,  0,  0,-10, -5,  5,
        0,  0,  0, 20, 20,  0,  0,  0,
        5,  5, 10, 25, 35, 10,  5,  5,
        10, 10, 20, 25, 25, 20, 10, 10, 
        50, 50, 50, 50, 50, 50, 50, 50, 
        0,  0,  0,  0,  0,  0,  0,  0
    };

    public final int[] blackPawnPosVals = new int[]
    {
        0,  0,  0,  0,  0,  0,  0,  0,
        50, 50, 50, 50, 50, 50, 50, 50,
        10, 10, 20, 25, 25, 20, 10, 10,
        5,  5, 10, 25, 35, 10,  5,  5,
        0,  0,  0, 20, 20,  0,  0,  0,
        5, -5,-10,  0,  0,-10, -5,  5,
        5, 10, 10,-20,-20, 10, 10,  5,
        0,  0,  0,  0,  0,  0,  0,  0
    };

    public final int[] whiteKnightPosVals = new int[]
    {
        -50,-40,-30,-30,-30,-30,-40,-50,
        -40,-20,  0,  5,  5,  0,-20,-40,
        -30,  5, 10, 15, 15, 10,  5,-30,
        -30,  0, 15, 20, 20, 15,  0,-30,
        -30,  5, 15, 20, 20, 15,  5,-30,
        -30,  0, 10, 15, 15, 10,  0,-30,
        -40,-20,  0,  0,  0,  0,-20,-40,
        -50,-40,-30,-30,-30,-30,-40,-50
    };

    public final int[] blackKnightPosVals = new int[]
    {
        -50,-40,-30,-30,-30,-30,-40,-50,
        -40,-20,  0,  0,  0,  0,-20,-40,
        -30,  0, 10, 15, 15, 10,  0,-30,
        -30,  5, 15, 20, 20, 15,  5,-30,
        -30,  0, 15, 20, 20, 15,  0,-30,
        -30,  5, 10, 15, 15, 10,  5,-30,
        -40,-20,  0,  5,  5,  0,-20,-40,
        -50,-40,-30,-30,-30,-30,-40,-50
    };

    public final int[] whiteBishopPosVals = new int[]
    {
        -20,-10,-10,-10,-10,-10,-10,-20,
        -10,  5,  0,  0,  0,  0,  5,-10,
        -10, 10, 10, 10, 10, 10, 10,-10,
        -10,  0, 10, 10, 10, 10,  0,-10,
        -10,  5,  5, 10, 10,  5,  5,-10,
        -10,  0,  5, 10, 10,  5,  0,-10,
        -10,  0,  0,  0,  0,  0,  0,-10,
        -20,-10,-10,-10,-10,-10,-10,-20
    };

    public final int[] blackBishopPosVals = new int[]
    {
        -20,-10,-10,-10,-10,-10,-10,-20,
        -10,  0,  0,  0,  0,  0,  0,-10,
        -10,  0,  5, 10, 10,  5,  0,-10,
        -10,  5,  5, 10, 10,  5,  5,-10,
        -10,  0, 10, 10, 10, 10,  0,-10,
        -10, 10, 10, 10, 10, 10, 10,-10,
        -10,  5,  0,  0,  0,  0,  5,-10,
        -20,-10,-10,-10,-10,-10,-10,-20
    };

    public final int[] whiteRookPosVals = new int[]
    {
        0,  0,  0,  0,  0,  0,  0,  0,
        5, 10, 10, 10, 10, 10, 10,  5,
       -5,  0,  0,  0,  0,  0,  0, -5,
       -5,  0,  0,  0,  0,  0,  0, -5,
       -5,  0,  0,  0,  0,  0,  0, -5,
       -5,  0,  0,  0,  0,  0,  0, -5,
       -5,  0,  0,  0,  0,  0,  0, -5,
        0,  0,  0,  5,  5,  0,  0,  0
    };

    public final int[] blackRookPosVals = new int[]
    {
         0,  0,  0,  5,  5,  0,  0,  0,
        -5,  0,  0,  0,  0,  0,  0, -5,
        -5,  0,  0,  0,  0,  0,  0, -5,
        -5,  0,  0,  0,  0,  0,  0, -5,
        -5,  0,  0,  0,  0,  0,  0, -5,
        -5,  0,  0,  0,  0,  0,  0, -5,
         5, 10, 10, 10, 10, 10, 10,  5,
         0,  0,  0,  0,  0,  0,  0,  0
    };

    public final int[] whiteQueenPosVals = new int[]
    {
        -20,-10,-10, -5, -5,-10,-10,-20,
        -10,  0,  5,  0,  0,  0,  0,-10,
        -10,  5,  5,  5,  5,  5,  0,-10,
         0,  0,  5,  5,  5,  5,  0, -5,
        -5,  0,  5,  5,  5,  5,  0, -5,
        -10,  0,  5,  5,  5,  5,  0,-10,
        -10,  0,  0,  0,  0,  0,  0,-10,
        -20,-10,-10, -5, -5,-10,-10,-20
    };

    public final int[] blackQueenPosVals = new int[]
    {
        -20,-10,-10, -5, -5,-10,-10,-20,
        -10,  0,  0,  0,  0,  0,  0,-10,
        -10,  0,  5,  5,  5,  5,  0,-10,
         -5,  0,  5,  5,  5,  5,  0, -5,
          0,  0,  5,  5,  5,  5,  0, -5,
        -10,  5,  5,  5,  5,  5,  0,-10,
        -10,  0,  5,  0,  0,  0,  0,-10,
        -20,-10,-10, -5, -5,-10,-10,-20
    };

    public final int[] whiteKingPosVals = new int[]
    {
        -30,-40,-40,-50,-50,-40,-40,-30,
        -30,-40,-40,-50,-50,-40,-40,-30,
        -30,-40,-40,-50,-50,-40,-40,-30,
        -30,-40,-40,-50,-50,-40,-40,-30,
        -20,-30,-30,-40,-40,-30,-30,-20,
        -10,-20,-20,-20,-20,-20,-20,-10,
         20, 20,  0,  0,  0,  0, 20, 20,
         20, 30, 10,  0,  0, 10, 30, 20
    };

    public final int[] blackKingPosVals = new int[]
    {
        20, 30, 10,  0,  0, 10, 30, 20,
        20, 20,  0,  0,  0,  0, 20, 20,
        -10,-20,-20,-20,-20,-20,-20,-10,
        -20,-30,-30,-40,-40,-30,-30,-20,
        -30,-40,-40,-50,-50,-40,-40,-30,
        -30,-40,-40,-50,-50,-40,-40,-30,
        -30,-40,-40,-50,-50,-40,-40,-30,
        -30,-40,-40,-50,-50,-40,-40,-30
    };

    public final int[] kingEndGamePosVals = new int[]
    {
        -50,-30,-30,-30,-30,-30,-30,-50,
        -30,-30,  0,  0,  0,  0,-30,-30,
        -30,-10, 20, 30, 30, 20,-10,-30,
        -30,-10, 30, 40, 40, 30,-10,-30,
        -30,-10, 30, 40, 40, 30,-10,-30,
        -30,-10, 20, 30, 30, 20,-10,-30,
        -30,-20,-10,  0,  0,-10,-20,-30,
        -50,-40,-30,-20,-20,-30,-40,-50
    };

    public final int[] blackEndGamePosVals = new int[]
    {
        -50,-40,-30,-20,-20,-30,-40,-50,
        -30,-20,-10,  0,  0,-10,-20,-30,
        -30,-10, 20, 30, 30, 20,-10,-30,
        -30,-10, 30, 40, 40, 30,-10,-30,
        -30,-10, 30, 40, 40, 30,-10,-30,
        -30,-10, 20, 30, 30, 20,-10,-30,
        -30,-30,  0,  0,  0,  0,-30,-30,
        -50,-30,-30,-30,-30,-30,-30,-50
    };

    /* @formatter:on */

    private Gameplay game;

    public Scoring(Gameplay game) {
        this.game = game;
    }

    public double materialScore() {
        PawnMovement pawnMovement = game.getMovement().getPawnMovement();
        /* @formatter:off */
        double score = kingValue * (getNumOf(PieceType.WK) - getNumOf(PieceType.BK))
                    + queenValue * (getNumOf(PieceType.WQ) - getNumOf(PieceType.BQ))
                    + rookValue * (getNumOf(PieceType.WR) - getNumOf(PieceType.BR))
                    + bishopValue * (getNumOf(PieceType.WB) - getNumOf(PieceType.BB))
                    + knightValue * (getNumOf(PieceType.WN) - getNumOf(PieceType.BN))
                    + pawnValue * (getNumOf(PieceType.WP) - getNumOf(PieceType.BP))
                    - (pawnValue / 2) * (pawnMovement.countDoubledPawns(Colour.WHITE) - pawnMovement.countDoubledPawns(Colour.BLACK))
                    - (pawnValue / 2) * (pawnMovement.countIsolatedPawns(Colour.WHITE) - pawnMovement.countIsolatedPawns(Colour.BLACK))
                    - (pawnValue / 2) * (pawnMovement.countBlockedPawns(Colour.WHITE) - pawnMovement.countBlockedPawns(Colour.BLACK));
        /* @formatter:on */

        return game.getTurn().equals(PieceType.Colour.WHITE) ? score : -score;
    }

    public double positionalScore() {
        double score = 0;

        Colour turn = game.getTurn();
        Board board = game.getBoard();

        if (turn.equals(Colour.WHITE)) {
            score += calculatePositionalScore(board.getBitBoard(PieceType.WP), whitePawnPosVals);
            score += calculatePositionalScore(board.getBitBoard(PieceType.WN), whiteKnightPosVals);
            score += calculatePositionalScore(board.getBitBoard(PieceType.WB), blackBishopPosVals);
            score += calculatePositionalScore(board.getBitBoard(PieceType.WR), whiteRookPosVals);
            score += calculatePositionalScore(board.getBitBoard(PieceType.WQ), whiteQueenPosVals);
            score += calculatePositionalScore(board.getBitBoard(PieceType.WK), whiteKingPosVals);
        } else {
            score += calculatePositionalScore(board.getBitBoard(PieceType.BP), blackPawnPosVals);
            score += calculatePositionalScore(board.getBitBoard(PieceType.BN), blackKnightPosVals);
            score += calculatePositionalScore(board.getBitBoard(PieceType.BB), blackBishopPosVals);
            score += calculatePositionalScore(board.getBitBoard(PieceType.BR), blackRookPosVals);
            score += calculatePositionalScore(board.getBitBoard(PieceType.BQ), blackQueenPosVals);
            score += calculatePositionalScore(board.getBitBoard(PieceType.BK), blackKingPosVals);
        }

        return score;
    }

    private double calculatePositionalScore(long bitBoard, int[] valsTable) {
        int score = 0;

        for (int i : PieceMovement.getIndices(bitBoard)) {
            score += valsTable[63 - i];
        }

        return score;
    }

    private int getNumOf(PieceType piece) {
        return Long.bitCount(game.getBoard().getBitBoard(piece));
    }
}
