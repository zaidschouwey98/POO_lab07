package engine.piece;

import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.Movement;
import engine.movements.MovementRestriction;

public abstract class FirstMovePiece extends Piece {
	private boolean hasMoved = false;

	protected FirstMovePiece(PlayerColor color, Coordinates<Integer> coordinates, Movement[] pieceMovements, MovementRestriction[] pieceMovementRestrictions) {
		super(color, coordinates, pieceMovements, pieceMovementRestrictions);
	}

	@Override
	public void moveTo(Coordinates<Integer> destination) {
		super.moveTo(destination);

		hasMoved = true;
	}

	public boolean hasMoved() {
		return hasMoved;
	}
}
