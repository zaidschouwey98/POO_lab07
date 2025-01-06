package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.Movement;

/**
 * Represents a chess piece. All pieces inherit from this abstract class.
 */
public abstract class Piece {
	private final PlayerColor color;
	private Coordinates coordinates;
	private final Movement[] pieceMovements;

	private final Movement[] pieceMovementRestrictions;

	public Piece(PlayerColor color, Coordinates coordinates, Movement[] pieceMovements, Movement[] pieceMovementRestrictions) {
		this.color = color;
		this.coordinates = coordinates;

		this.pieceMovements = pieceMovements;
		this.pieceMovementRestrictions = pieceMovementRestrictions;
	}

	/**
	 * Gets the visual type of the piece.
	 *
	 * @return the type of the piece
	 */
	public abstract PieceType getGraphicalType();

	/**
	 * Checks if the piece can move to the target position.
	 *
	 * @param destination the target position
	 * @return true if the move is valid and false otherwise
	 */
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

	/**
	 * This method is used to implement special moves that don't fall in the piece's default moveset.
	 *
	 * @param dest the target position
	 * @return true if the move is allowed
	 */
	public boolean isExceptionalMoveAllowed(Coordinates dest) {
		return false;
	}

	/**
	 * Check if the piece could capture at the destination REGARDLESS OF THE FACT THAT THE PATH
	 * IS CLEAR OR NOT
	 *
	 * @param destination the target of the capture
	 * @return boolean that represents if the piece can capture at dest
	 */
	public boolean canCaptureAt(Coordinates destination) {
		return canMoveTo(destination);
	}

	/**
	 * Definitely moves to piece to a destination
	 *
	 * @param destination the destination
	 */
	public void moveTo(Coordinates destination) {
		this.coordinates = destination;
	}

	/**
	 * Returns the color of the piece
	 *
	 * @return the color of the piece
	 */
	public PlayerColor getColor() {
		return color;
	}

	/**
	 * Returns the piece's coordinates.
	 *
	 * @return the piece's coordinates
	 */
	public Coordinates getCoordinates() {
		return coordinates;
	}
}
