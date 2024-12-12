package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.Movement;
import engine.movements.MovementRestriction;

public abstract class Piece {
    private final PlayerColor color;
    private Coordinates coordinates;
    private final Movement[] pieceMovements;
    private final MovementRestriction[] pieceMovementRestrictions;

    protected Piece(PlayerColor color, Coordinates coordinates, Movement[] pieceMovements, MovementRestriction[] pieceMovementRestrictions) {
        this.color = color;
        this.coordinates = coordinates;
        this.pieceMovements = pieceMovements;
        this.pieceMovementRestrictions = pieceMovementRestrictions;
    }

    public abstract PieceType getGraphicalType();

    public boolean canMoveTo(Coordinates destination) {
        for (Movement movement : pieceMovements) {
            //movement.can
        }
        return false;
    }


}
