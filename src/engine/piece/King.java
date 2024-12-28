package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.*;

public class King extends FirstMovePiece {
	private static final int CASTLE_DIST = 2;

	public King (PlayerColor color, Coordinates<Integer> coordinates){
		super(color, coordinates,
			new Movement[] {
				new AxialMovement(),
				new DiagonalMovement()
			},
			new MovementRestriction[] {
				new RadiusMovementRestriction(1)
			}
		);
	}

	@Override
	public PieceType getGraphicalType() {
        return PieceType.KING;
    }

	public void castle(Rook rook) {
		if (hasMoved()) return;

		int direction = (rook.getCoordinates().x() < this.getCoordinates().x() ? -1 : 1);
		int movement = direction * CASTLE_DIST;

		moveTo(this.coordinates.move(movement, 0));
		rook.moveTo(this.coordinates.move(-direction, 0));
	}
}
