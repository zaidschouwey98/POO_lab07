package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.Movement;
import engine.movements.MovementRestriction;

public abstract class Piece {
	private final PlayerColor color;
	protected Coordinates<Integer> coordinates;
	private final Movement[] pieceMovements;

	private final MovementRestriction[] pieceMovementRestrictions;

	protected Piece(PlayerColor color, Coordinates<Integer> coordinates, Movement[] pieceMovements, MovementRestriction[] pieceMovementRestrictions) {
		this.color = color;
		this.coordinates = coordinates;

		this.pieceMovements = pieceMovements;
		this.pieceMovementRestrictions = pieceMovementRestrictions;
	}

	public abstract PieceType getGraphicalType();

	/**
	 * Does nothing by default.
	 * Can be re-defined in order to perform actions at move time.
	 */
	public void performMoveActions() {}

	public boolean canMoveTo(Coordinates<Integer> destination) {
		if (isExceptionalMoveAllowed(destination)) return true;
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

	public boolean isExceptionalMoveAllowed(Coordinates<Integer> dest) {
		return false;
	}

	/**
	 * Check if the piece could capture at the destination REGARDLESS OF THE FACT THAT THE PATH
	 * IS CLEAR OR NOT
	 * @param destination the target of the capture
	 * @return boolean that represents if the piece can capture at dest
	 */
	public boolean canCaptureAt(Coordinates<Integer> destination) {
		return canMoveTo(destination);
	}

	/**
	 * Definitely moves to piece to a destination
	 * @param destination the destination
	 */
	public final void moveTo(Coordinates<Integer> destination) {
		this.coordinates = destination;
		performMoveActions();
	}

	/**
	 * Gets the color of the piece
	 * @return the color of the piece
	 */
	public PlayerColor getColor() {
		return color;
	}

	public Coordinates<Integer> getCoordinates() {
		return coordinates;
	}
}
