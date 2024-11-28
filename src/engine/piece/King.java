package engine.piece;

import chess.PlayerColor;
import engine.Coordinates;

public class King extends Piece {
    private boolean hasMooved;
    public King (PlayerColor color, Coordinates coordinates){
        super(color, coordinates, null, null);
    }
}
