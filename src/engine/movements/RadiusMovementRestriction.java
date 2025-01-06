package engine.movements;

import engine.Coordinates;

/**
 * Limits movement to a certain radius from the starting position.
 */
public class RadiusMovementRestriction implements Movement {

	private final int movementRadius;

	/**
	 * Sets the maximum distance the piece can move.
	 *
	 * @param movementRadius the maximum number of squares
	 */
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
