import gameplay.GameplayTest;
import movement.KnightAndKingMovementTest;
import movement.PawnMovementTest;
import movement.SlidingMovementTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import board.BoardTest;
import board.BoardUpdateTest;

@RunWith(Suite.class)
@SuiteClasses({ BoardTest.class, PawnMovementTest.class, SlidingMovementTest.class, KnightAndKingMovementTest.class,
        BoardUpdateTest.class, GameplayTest.class })
public class AllTests {
}
