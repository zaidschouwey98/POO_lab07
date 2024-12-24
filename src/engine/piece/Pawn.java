package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.*;

public class Pawn extends Piece {
	private final Movement[] captureRestrictions;
	private boolean hasMoved;
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
		hasMoved = false;
	}
	public boolean hasMoved () {
		return hasMoved;
	}

	/**
	 * Setter to set the hasMoved variable
	 * @param hasMoved the value to set
	 */
	private void setHasMoved (boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	public void moveTo(Coordinates<Integer> destination) {
		if (!hasMoved()) {
			setHasMoved(false);

			// removing the RadiusMovement restriction and assigning a new one
			// assuming there is only one RadiusMovementRestriction assigned to the pawn
			for (int i = 0 ; i < this.pieceMovementRestrictions.size() ; ++i){
				if (this.pieceMovementRestrictions.get(i) instanceof RadiusMovementRestriction) {
					this.pieceMovementRestrictions.remove(i);
					break;
				}
			}
			this.pieceMovementRestrictions.add(new RadiusMovementRestriction(1));
		}
		super.moveTo(destination);
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