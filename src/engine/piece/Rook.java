package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;

public class Rook extends Piece {
    public Rook (PlayerColor color, Coordinates coordinates){
        super(color, coordinates, null, null);
    }
    @Override
    public PieceType getGraphicalType() {
        return PieceType.ROOK;
    }
}