package engine.movements;

import engine.Coordinates;

/**
 * Allows movement diagonally on the board.
 */
public class DiagonalMovement implements Movement {
	/**
	 * Checks that a piece's movement is strictly diagonal.
	 *
	 * @param from the starting position
	 * @param to   the target position
	 * @return true if the move is allowed and false otherwise
	 */
	@Override
	public boolean canMove(Coordinates from, Coordinates to) {
		return Math.abs(from.x() - to.x()) == Math.abs(from.y() - to.y());
	}
}
