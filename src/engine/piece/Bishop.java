package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.DiagonalMovement;
import engine.movements.Movement;

/**
 * Represents the Bishop piece in chess.
 * Bishops can move diagonally any number of squares.
 */
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

	public String toString(){
		return "Bishop";
	}
}

