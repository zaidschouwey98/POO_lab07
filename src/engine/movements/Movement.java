package engine.movements;

import engine.Coordinates;

public interface Movement {
	boolean canMove(Coordinates from, Coordinates to);
}
