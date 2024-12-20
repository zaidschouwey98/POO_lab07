package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.DiagonalMovement;
import engine.movements.Movement;
import engine.movements.MovementRestriction;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Bishop extends Piece {
	public Bishop (PlayerColor color, Coordinates<Integer> coordinates){
		super(color, coordinates,
			new Movement[] { new DiagonalMovement() },
			new MovementRestriction[] { }
		);
	}

	@Override
	public PieceType getGraphicalType() {
		return PieceType.BISHOP;
	}
}

