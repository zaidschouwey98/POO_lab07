package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.AxialMovement;
import engine.movements.Movement;

/**
 * Represents the Rook piece in chess.
 * Rooks move any number of squares in a line.
 */
public class Rook extends FirstMovePiece {
    /**
     * Constructor for the Rook class
     *
     * @param color       color of the Rook
     * @param coordinates initial coordinate of the Queen
     */
    public Rook(PlayerColor color, Coordinates coordinates) {
        super(color, coordinates,
                new Movement[]{
                        new AxialMovement()
                },
                new Movement[]{}
        );
    }

    /**
     * Gets the visual type of the piece.
     *
     * @return the type of the piece
     */
    @Override
    public PieceType getGraphicalType() {
        return PieceType.ROOK;
    }

    /**
     * toString value for the class
     *
     * @return the text value for the class ("Rook" here)
     */
    public String toString() {
        return "Rook";
    }
}