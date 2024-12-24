package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.Movement;
import engine.movements.MovementRestriction;

import java.util.Arrays;
import java.util.LinkedList;

public abstract class Piece {
	private final PlayerColor color;
	protected Coordinates<Integer> coordinates;
	private final Movement[] pieceMovements;

	// TODO need something mutable, so that we can change it for the pawn
	//protected final MovementRestriction[] pieceMovementRestrictions;
	protected LinkedList<MovementRestriction> pieceMovementRestrictions;

	protected Piece(PlayerColor color, Coordinates<Integer> coordinates, Movement[] pieceMovements, MovementRestriction[] pieceMovementRestrictions) {
		this.color = color;
		this.coordinates = coordinates;
		this.pieceMovements = pieceMovements;

		// this.pieceMovementRestrictions = pieceMovementRestrictions;
		this.pieceMovementRestrictions = new LinkedList<>();
		if (pieceMovementRestrictions != null) this.pieceMovementRestrictions.addAll(Arrays.asList(pieceMovementRestrictions));
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
