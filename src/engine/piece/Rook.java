package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.AxialMovement;
import engine.movements.Movement;

/**
 * Represents the Rook piece in chess.
 * Rooks move any number of squares in a line.
 */
public class Rook extends FirstMovePiece {
	public Rook(PlayerColor color, Coordinates coordinates) {
		super(color, coordinates,
			new Movement[]{
				new AxialMovement()
			},
			new Movement[]{}
		);
	}

	@Override
	public PieceType getGraphicalType() {
		return PieceType.ROOK;
	}
}