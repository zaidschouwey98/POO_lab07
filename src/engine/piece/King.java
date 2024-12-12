package engine.piece;

import chess.PieceType;
import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.*;

public class King extends Piece {
	private boolean hasMoved;
	public King (PlayerColor color, Coordinates coordinates){
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
}
