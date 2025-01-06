package engine.movements;

import engine.Coordinates;

/**
 * Allows movement diagonally on the board.
 */
public class DiagonalMovement implements Movement {
	@Override
	public boolean canMove(Coordinates from, Coordinates to) {
		return Math.abs(from.x() - to.x()) == Math.abs(from.y() - to.y());
	}
}
