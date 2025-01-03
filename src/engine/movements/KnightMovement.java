package engine.movements;

import engine.Coordinates;

public class KnightMovement implements Movement {
	@Override
	public boolean canMove(Coordinates from, Coordinates to) {
		int xDiff = Math.abs(from.x() - to.x());
		int yDiff = Math.abs(from.y() - to.y());

		return xDiff == 2 && yDiff == 1 || xDiff == 1 && yDiff == 2;
	}
}
