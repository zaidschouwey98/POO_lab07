package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;

public class King extends Piece {
    private boolean hasMooved;
    public King (PlayerColor color, Coordinates coordinates){
        super(color, coordinates, null, null);
    }
    @Override
    public PieceType getGraphicalType() {
        return PieceType.KING;
    }
}
