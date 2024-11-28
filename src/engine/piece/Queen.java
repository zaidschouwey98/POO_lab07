package engine.piece;

import chess.PlayerColor;
import engine.Coordinates;

public class Queen extends Piece {
    public Queen (PlayerColor color, Coordinates coordinates){
        super(color, coordinates, null, null);
    }
}