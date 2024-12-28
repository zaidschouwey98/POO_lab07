package engine.movements;

import engine.Coordinates;

public class DiagonalMovement implements Movement {
	@Override
	public boolean canMove(Coordinates from, Coordinates to) {
		return Math.abs(from.x() - to.x()) == Math.abs(from.y() - to.y());
	}
}
