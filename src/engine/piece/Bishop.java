package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.DiagonalMovement;
import engine.movements.Movement;

public class Bishop extends Piece {
	public Bishop(PlayerColor color, Coordinates coordinates) {
		super(color, coordinates,
			new Movement[]{new DiagonalMovement()},
			new Movement[]{}
		);
	}

	@Override
	public PieceType getGraphicalType() {
		return PieceType.BISHOP;
	}
}

