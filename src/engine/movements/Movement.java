package engine.movements;

import engine.Coordinates;

/**
 * Defines how a chess piece can move on the board.
 */
public interface Movement {
	/**
	 * Checks if a piece can move from one position to another.
	 *
	 * @param from the starting position
	 * @param to   the target position
	 * @return true if the move is allowed and false otherwise
	 */
	boolean canMove(Coordinates from, Coordinates to);
}
