import movement.KnightMovementTest;
import movement.PawnMovementTest;
import movement.SlidingMovementTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import board.BoardTest;

@RunWith(Suite.class)
@SuiteClasses({ BoardTest.class, PawnMovementTest.class, SlidingMovementTest.class, KnightMovementTest.class })
public class AllTests {
}
