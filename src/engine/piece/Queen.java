package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.AxialMovement;
import engine.movements.DiagonalMovement;
import engine.movements.Movement;

/**
 * Represents the Queen piece in chess.
 * Queens can move any number of squares in a line or diagonally.
 */
public class Queen extends Piece {
	public Queen(PlayerColor color, Coordinates coordinates) {
		super(color, coordinates,
			new Movement[]{
				new AxialMovement(),
				new DiagonalMovement()
			},
			new Movement[]{}
		);
	}

	@Override
	public PieceType getGraphicalType() {
		return PieceType.QUEEN;
	}
}