package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.Movement;

public abstract class Piece {
	private final PlayerColor color;
	protected Coordinates coordinates;
	private final Movement[] pieceMovements;

	private final Movement[] pieceMovementRestrictions;

	protected Piece(PlayerColor color, Coordinates coordinates, Movement[] pieceMovements, Movement[] pieceMovementRestrictions) {
		this.color = color;
		this.coordinates = coordinates;

		this.pieceMovements = pieceMovements;
		this.pieceMovementRestrictions = pieceMovementRestrictions;
	}

	public abstract PieceType getGraphicalType();

	public boolean canMoveTo(Coordinates destination) {
		if (isExceptionalMoveAllowed(destination)) return true;
		for (Movement restriction : pieceMovementRestrictions)
            if (!restriction.canMove(this.coordinates, destination)) {
				return false;
			}
        for (Movement movement : pieceMovements)
            if (movement.canMove(this.coordinates, destination)) {
				return true;
			}

		return false;
	}

	public boolean isExceptionalMoveAllowed(Coordinates dest) {
		return false;
	}

	/**
	 * Check if the piece could capture at the destination REGARDLESS OF THE FACT THAT THE PATH
	 * IS CLEAR OR NOT
	 * @param destination the target of the capture
	 * @return boolean that represents if the piece can capture at dest
	 */
	public boolean canCaptureAt(Coordinates destination) {
		return canMoveTo(destination);
	}

	/**
	 * Definitely moves to piece to a destination
	 * @param destination the destination
	 */
	public void moveTo(Coordinates destination) {
		this.coordinates = destination;
	}

	/**
	 * Gets the color of the piece
	 * @return the color of the piece
	 */
	public PlayerColor getColor() {
		return color;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}
}
