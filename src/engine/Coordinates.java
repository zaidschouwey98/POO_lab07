package engine;

/**
 * Represents a specific location on the chessboard.
 * Helps calculate movement and shows the position as text.
 */
public record Coordinates(int x, int y) {

	/**
	 * Moves the position by adding the given values to x and y.
	 *
	 * @param dx the change in the x
	 * @param dy the change in the y
	 * @return a new Coordinates with the updated position
	 */
	public Coordinates move(int dx, int dy) {
		int rx = this.x() + dx;
		int ry = this.y() + dy;

		return new Coordinates(rx, ry);
	}

	@Override
	public String toString() {
		return String.format("(%s, %s)", x(), y());
	}
}
