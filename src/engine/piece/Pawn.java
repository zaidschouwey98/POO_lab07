package engine.piece;

import chess.PlayerColor;
import engine.Coordinates;

public class Pawn extends Piece {
    public Pawn (PlayerColor color, Coordinates coordinates){
        super(color, coordinates, null, null);
    }
}