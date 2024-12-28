package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.*;

public class King extends FirstMovePiece {
	private static final int CASTLE_DIST = 2;

	public King(PlayerColor color, Coordinates coordinates) {
		super(color, coordinates,
			new Movement[]{
				new AxialMovement(),
				new DiagonalMovement()
			},
			new Movement[]{
				new RadiusMovementRestriction(1)
			}
		);
	}

	@Override
	public PieceType getGraphicalType() {
		return PieceType.KING;
	}

	@Override
	public boolean isExceptionalMoveAllowed(Coordinates dest) {
		if (hasMoved()) return false;

		boolean isLeftRook = dest.equals(getCoordinates().move(-CASTLE_DIST, 0));
		boolean isRightRook = dest.equals(getCoordinates().move(CASTLE_DIST, 0));

		return isLeftRook || isRightRook;
	}
}
