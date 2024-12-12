package engine.movements;

import engine.Coordinates;

public class DiagonalMovement implements Movement {
	@Override
	public boolean canMove(Coordinates from, Coordinates to) {
		return from.x() - to.x() == from.y() - to.y();
	}
}
