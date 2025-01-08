package engine.movements;

import engine.Coordinates;

/**
 * Allows movement in straight lines.
 */
public class AxialMovement implements Movement {
	/**
	 * Checks that a piece's movement is strictly axial.
	 *
	 * @param from the starting position
	 * @param to   the target position
	 * @return true if the move is allowed and false otherwise
	 */
	@Override
	public boolean canMove(Coordinates from, Coordinates to) {
		return from.x() == to.x() ^ from.y() == to.y();
	}
}
