package engine.piece;

import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.Movement;
import engine.movements.MovementRestriction;

public abstract class Piece {
    private PlayerColor color;
    private Coordinates coordinates;
    private Movement[] pieceMovements;
    private MovementRestriction[] pieceMovementRestrictions;
    protected Piece(){

    }
}




