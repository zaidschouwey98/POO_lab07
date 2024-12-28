package engine.movements;

import engine.Coordinates;

public class RadiusMovementRestriction implements Movement {

	private final int movementRadius;

	public RadiusMovementRestriction(int movementRadius) {
		this.movementRadius = movementRadius;
	}

	@Override
	public boolean canMove(Coordinates from, Coordinates to) {
		int xDiff = Math.abs(from.x() - to.x());
		int yDiff = Math.abs(from.y() - to.y());

		return xDiff <= movementRadius && yDiff <= movementRadius;
	}
}
