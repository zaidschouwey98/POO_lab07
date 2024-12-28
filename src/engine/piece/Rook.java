package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.AxialMovement;
import engine.movements.Movement;
import engine.movements.MovementRestriction;

public class Rook extends FirstMovePiece {
	public Rook (PlayerColor color, Coordinates<Integer> coordinates){
		super(color, coordinates,
			new Movement[] {
				new AxialMovement()
			},
			new MovementRestriction[] { }
		);
	}

	@Override
	public PieceType getGraphicalType() {
		return PieceType.ROOK;
	}
}