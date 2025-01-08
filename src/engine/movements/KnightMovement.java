package engine.movements;

import engine.Coordinates;

/**
 * Allows movement knight movements, which is complex in shape.
 */
public class KnightMovement implements Movement {
	/**
	 * Checks that a piece's is a knight move.
	 *
	 * @param from the starting position
	 * @param to   the target position
	 * @return true if the move is allowed and false otherwise
	 */
	@Override
	public boolean canMove(Coordinates from, Coordinates to) {
		int xDiff = Math.abs(from.x() - to.x());
		int yDiff = Math.abs(from.y() - to.y());

		return xDiff == 2 && yDiff == 1 || xDiff == 1 && yDiff == 2;
	}
}
