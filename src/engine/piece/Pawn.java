package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.*;

public class Pawn extends Piece {
	private final Movement[] captureRestrictions;

	public Pawn (PlayerColor color, Coordinates<Integer> coordinates) {
		super(color, coordinates,
			new Movement[] {
				new AxialMovement()
			},
			new MovementRestriction[] {
				new DirectionMovementRestriction(color),
				new RadiusMovementRestriction(2)  // Radius changed to 1 after first move
			}
		);

		captureRestrictions = new Movement[] {
			new DirectionMovementRestriction(color),
			new RadiusMovementRestriction(1),
			new DiagonalMovement()
		};
	}

	@Override
	public boolean canCaptureAt(Coordinates<Integer> at) {
		for (var movement : captureRestrictions) {
			if (!movement.canMove(getCoordinates(), at)) return false;
		}
		return true;
	}

	@Override
	public PieceType getGraphicalType() {
		return PieceType.PAWN;
	}
}