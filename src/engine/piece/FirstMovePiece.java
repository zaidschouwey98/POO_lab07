package engine.piece;

import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.Movement;

/**
 * This abstract class represents a piece that tracks if it has already moved since its creation.
 */
public abstract class FirstMovePiece extends Piece {
	private boolean hasMoved = false;

	protected FirstMovePiece(PlayerColor color, Coordinates coordinates, Movement[] pieceMovements, Movement[] pieceMovementRestrictions) {
		super(color, coordinates, pieceMovements, pieceMovementRestrictions);
	}

	@Override
	public void moveTo(Coordinates destination) {
		super.moveTo(destination);

		hasMoved = true;
	}

	/**
	 * Checks if the piece has moved before.
	 *
	 * @return true if the piece has already moved, false otherwise
	 */
	public boolean hasMoved() {
		return hasMoved;
	}
}
