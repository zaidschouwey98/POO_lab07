package engine.movements;

import chess.PlayerColor;
import engine.Coordinates;

/**
 * Restricts movement to a specific direction, useful for pawns.
 */
public class DirectionMovementRestriction implements Movement {

	private final PlayerColor color;

	/**
	 * Sets the movement direction based on the player  color.
	 *
	 * @param color the player color
	 */
	public DirectionMovementRestriction(PlayerColor color) {
		this.color = color;
	}

	@Override
	public boolean canMove(Coordinates from, Coordinates to) {
		return switch (color) {
			case WHITE -> from.y() < to.y();
			case BLACK -> from.y() > to.y();
		};
	}
}
