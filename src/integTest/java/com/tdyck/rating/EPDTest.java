package com.tdyck.rating;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tdyck.board.FEN;
import com.tdyck.board.movement.Move;
import com.tdyck.gameplay.Gameplay;
import com.tdyck.rating.EPDMapper.EPD;
import com.tdyck.search.PrincipalVariation;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class EPDTest {
    
    private static final PrincipalVariation PV = new PrincipalVariation(4);

    @Test
    @FileParameters(value = "src/test/resources/arasan-18.epd", mapper = EPDMapper.class)
    public void passArasanTest(EPD epd) {
        runTest(epd);
    }
    
    @Test
    @FileParameters(value = "src/test/resources/bratko-kopec.epd", mapper = EPDMapper.class)
    public void passBratkoKopecTest(EPD epd) {
        runTest(epd);
    }
    
    private void runTest(EPD epd) {
        Gameplay gameplay = FEN.fromFenString(epd.getFENString());
        Move move = PV.search(gameplay);
        String expected = sanitize(epd.getBestMove());
        
        String actual = simplify(expected, move.toEpdForm());
        System.out.println(String.format("Expected: %s, Actual: %s", expected, actual));
        Assert.assertEquals(expected, actual);
    }
    
    private static String simplify(String expected, String actual) {
        if (expected.length() == 2) {
            return actual.substring(1, 3);
        }
        return actual;
    }
    
    private static String sanitize(String move) {
        move = move.replace("x", "");
        move = move.replace("+", "");
        return move;
    }

}
