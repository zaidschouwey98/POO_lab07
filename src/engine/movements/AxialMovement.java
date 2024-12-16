package engine.movements;

import engine.Coordinates;

public class AxialMovement implements Movement {
	@Override
	public boolean canMove(Coordinates<Integer> from, Coordinates<Integer> to) {
		return from.x() == to.x() ^ from.y() == to.y();
	}
}
