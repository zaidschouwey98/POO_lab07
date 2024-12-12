package engine.piece;

import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.AxialMovement;
import engine.movements.Movement;
import engine.movements.MovementRestriction;

public class Rook extends Piece {
    public Rook (PlayerColor color, Coordinates coordinates){
        super(color, coordinates,
            new Movement[] {
                new AxialMovement()
            },
            new MovementRestriction[] { }
        );
    }
}