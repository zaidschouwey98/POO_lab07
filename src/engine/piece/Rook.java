package engine.piece;

import chess.PlayerColor;
import engine.Coordinates;

public class Rook extends Piece {
    public Rook (PlayerColor color, Coordinates coordinates){
        super(color, coordinates, null, null);
    }
}