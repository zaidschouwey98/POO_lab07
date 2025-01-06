package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.KnightMovement;
import engine.movements.Movement;

/**
 * Represents the Knight piece in chess.
 * Knights move in L shape and can jump over other pieces.
 */
public class Knight extends Piece {
	public Knight(PlayerColor color, Coordinates coordinates) {
		super(color, coordinates,
			new Movement[]{
				new KnightMovement()
			},
			new Movement[]{}
		);
	}

	@Override
	public PieceType getGraphicalType() {
		return PieceType.KNIGHT;
	}
}
