package engine.piece;

import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.KnightMovement;
import engine.movements.Movement;
import engine.movements.MovementRestriction;

public class Knight extends Piece{
    public Knight(PlayerColor color, Coordinates coordinates){
        super(color,coordinates,
            new Movement[] {
                new KnightMovement()
            },
            new MovementRestriction[] { }
        );
    }
}
