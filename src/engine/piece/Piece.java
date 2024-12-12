package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.Movement;
import engine.movements.MovementRestriction;

public abstract class Piece {
	private final PlayerColor color;
	private Coordinates<Integer> coordinates;
	private final Movement[] pieceMovements;
	private final MovementRestriction[] pieceMovementRestrictions;

	protected Piece(PlayerColor color, Coordinates<Integer> coordinates, Movement[] pieceMovements, MovementRestriction[] pieceMovementRestrictions) {
		this.color = color;
		this.coordinates = coordinates;
		this.pieceMovements = pieceMovements;
		this.pieceMovementRestrictions = pieceMovementRestrictions;
	}

	public abstract PieceType getGraphicalType();

	public boolean canMoveTo(Coordinates<Integer> destination) {
		for (Movement movement : pieceMovements) {
			//movement.can
		}
		return false;
	}

	public boolean moveTo(Coordinates<Integer> destination) {
		if (canMoveTo(destination)) {
			this.coordinates = destination;
			return true;
		} else {
			return false;
		}
	}
}
