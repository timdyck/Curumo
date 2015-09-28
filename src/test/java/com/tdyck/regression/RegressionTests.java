package com.tdyck.regression;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.tdyck.board.BoardTest;
import com.tdyck.board.BoardUpdateTest;
import com.tdyck.gameplay.GameplayTest;
import com.tdyck.movement.KnightAndKingMovementTest;
import com.tdyck.movement.PawnMovementTest;
import com.tdyck.movement.SlidingMovementTest;

@RunWith(Suite.class)
@SuiteClasses({ BoardTest.class, PawnMovementTest.class, SlidingMovementTest.class, KnightAndKingMovementTest.class,
        BoardUpdateTest.class, GameplayTest.class })
public class RegressionTests {
}
