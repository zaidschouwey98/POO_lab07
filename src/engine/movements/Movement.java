package engine.movements;

import engine.Coordinates;

public interface Movement {
	boolean canMove(Coordinates<Integer> from, Coordinates<Integer> to);
}
