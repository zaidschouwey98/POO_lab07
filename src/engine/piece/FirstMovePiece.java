package engine.piece;

import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.Movement;

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

	public boolean hasMoved() {
		return hasMoved;
	}
}
