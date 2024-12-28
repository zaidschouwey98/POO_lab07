package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.*;

public class Pawn extends Piece {

	private static final int LONG_JUMP_DIST = 2;
	private final Movement[] captureRestrictions;
	private boolean canLongJump = true;
	private boolean capturableByEnpassant = false;

	public Pawn (PlayerColor color, Coordinates<Integer> coordinates) {
		super(color, coordinates,
			new Movement[] {
				new AxialMovement()
			},
			new MovementRestriction[] {
				new DirectionMovementRestriction(color),
				new RadiusMovementRestriction(1)  // Radius changed to 1 after first move
			}
		);
		captureRestrictions = new Movement[] {
				new DirectionMovementRestriction(color),
				new RadiusMovementRestriction(1),
				new DiagonalMovement()
		};
	}

	public boolean isCapturableByEnpassant() {
		return this.capturableByEnpassant;
	}

	public void setCapturableByEnpassant(boolean capturableByEnpassant) {
		this.capturableByEnpassant = capturableByEnpassant;
	}

	@Override
	public boolean isExceptionalMoveAllowed(Coordinates<Integer> dest) {
		int jumpDistance = LONG_JUMP_DIST;
		if (getColor() == PlayerColor.BLACK) jumpDistance *= -1;

		return canLongJump && dest.equals(getCoordinates().move(0, jumpDistance));
	}

	@Override
	public void performMoveActions() {
		canLongJump = false;
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