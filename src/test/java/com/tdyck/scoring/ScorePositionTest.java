package com.tdyck.scoring;

import org.junit.Test;

import com.tdyck.board.Board;
import com.tdyck.board.BoardUtils;
import com.tdyck.board.FEN;
import com.tdyck.board.movement.Move;
import com.tdyck.gameplay.Gameplay;
import com.tdyck.search.PrincipalVariation;

public class ScorePositionTest {

    @Test
    public void scorePos() {
        // Replace the FEN string to evaluate a position
        Gameplay game = FEN.fromFenString("r1bqkbnr/pppppppp/2n5/8/4P3/2N5/PPPP1PPP/R1BQKBNR b KQkq - 2 2 ");
        Board board = game.getBoard();

        BoardUtils.printBoard(board);
        PrincipalVariation search = new PrincipalVariation(4);

        Move move = search.search(game);
        System.out.println("Best move is " + move.toUciForm());
    }
}
