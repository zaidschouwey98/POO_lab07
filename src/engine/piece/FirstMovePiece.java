package engine.piece;

import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.Movement;

/**
 * This abstract class represents a piece that tracks if it has already moved since its creation.
 */
public abstract class FirstMovePiece extends Piece {
    private boolean hasMoved = false;

    /**
     * Constructor for the FirstMovePiece class
     *
     * @param color                     color of the piece
     * @param coordinates               initial coordinates of the piece
     * @param pieceMovements            allowed kind of movements for the piece
     * @param pieceMovementRestrictions movement restrictions for the piece
     */
    protected FirstMovePiece(PlayerColor color, Coordinates coordinates, Movement[] pieceMovements,
                             Movement[] pieceMovementRestrictions) {
        super(color, coordinates, pieceMovements, pieceMovementRestrictions);
    }

    /**
     * Definitely moves the piece to a destination
     *
     * @param destination the destination
     */
    @Override
    public void moveTo(Coordinates destination) {
        super.moveTo(destination);
        if (this instanceof Pawn && !hasMoved) {
            ((Pawn) this).setCapturableByEnpassant(true);
        }
        hasMoved = true;
    }

    /**
     * Checks if the piece has moved before.
     *
     * @return true if the piece has already moved, false otherwise
     */
    public boolean hasMoved() {
        return hasMoved;
    }
}
