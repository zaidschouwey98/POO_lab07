package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.*;

public class Pawn extends Piece {
    public Pawn (PlayerColor color, Coordinates coordinates) {
        super(color, coordinates,
            new Movement[] {
                new AxialMovement()
            },
            new MovementRestriction[] {
                new DirectionMovementRestriction(color),
                new RadiusMovementRestriction(2)  // Radius changed to 1 after first move
            }
        );
    }
    @Override
    public PieceType getGraphicalType() {
        return PieceType.PAWN;
    }
}