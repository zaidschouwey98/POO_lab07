package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;

public class Knight extends Piece{
    public Knight(PlayerColor color, Coordinates coordinates){
        super(color,coordinates,null,null);
    }
    @Override
    public PieceType getGraphicalType() {
        return PieceType.KNIGHT;
    }
}
