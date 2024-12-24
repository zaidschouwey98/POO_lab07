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
		for (MovementRestriction restriction : pieceMovementRestrictions)
            if (!restriction.canMove(this.coordinates, destination)) {
				return false;
			}
        for (Movement movement : pieceMovements)
            if (movement.canMove(this.coordinates, destination)) {
				return true;
			}
		return false;
	}

	public boolean canCaptureAt(Coordinates<Integer> destination) {
		return canMoveTo(destination);
	}

	/**
	 * Definitely moves to piece to a destination
	 * @param destination the destination
	 */
	public void moveTo(Coordinates<Integer> destination) {
		this.coordinates = destination;
	}

	public PlayerColor getColor() {
		return color;
	}

	public Coordinates<Integer> getCoordinates() {
		return coordinates;
	}
}
