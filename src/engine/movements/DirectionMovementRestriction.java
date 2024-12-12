package engine.movements;

import chess.PlayerColor;
import engine.Coordinates;

public class DirectionMovementRestriction implements MovementRestriction {

	private final PlayerColor color;

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
