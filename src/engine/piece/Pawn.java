package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.*;

/**
 * Represents the Pawn piece in chess.
 * Pawns move forward one square, and two square if it's their first move.
 * They can capture diagonally, and en passant.
 */
public class Pawn extends FirstMovePiece {

    private static final int LONG_JUMP_DIST = 2;
    private final Movement[] captureRestrictions;
    private boolean capturableByEnpassant = false;

    /**
     * Constructor for the Pawn class
     *
     * @param color       color of the Pawn
     * @param coordinates initial coordinate of the Pawn
     */
    public Pawn(PlayerColor color, Coordinates coordinates) {
        super(color, coordinates,
                new Movement[]{
                        new AxialMovement()
                },
                new Movement[]{
                        new DirectionMovementRestriction(color),
                        new RadiusMovementRestriction(1)  // Radius changed to 1 after first move
                }
        );
        captureRestrictions = new Movement[]{
                new DirectionMovementRestriction(color),
                new RadiusMovementRestriction(1),
                new DiagonalMovement()
        };
    }

    /**
     * Gets the capturableByEnpassant attribute of the pawn.
     *
     * @return the value
     */
    public boolean isCapturableByEnpassant() {
        return this.capturableByEnpassant;
    }

    /**
     * Sets the capturableByEnpassant attribute of the pawn.
     *
     * @param capturableByEnpassant the value to set
     */
    public void setCapturableByEnpassant(boolean capturableByEnpassant) {
        this.capturableByEnpassant = capturableByEnpassant;
    }

    /**
     * This method is used to implement special moves that don't fall in the piece's default moveset. In the pawn's case,
     * it will try to move to squares forward.
     *
     * @param dest the target position
     * @return true if the move is allowed
     */
    @Override
    public boolean isExceptionalMoveAllowed(Coordinates dest) {
        int jumpDistance = LONG_JUMP_DIST;
        if (getColor() == PlayerColor.BLACK) jumpDistance *= -1;

        return !hasMoved() && dest.equals(getCoordinates().move(0, jumpDistance));
    }

    /**
     * Check if the pawn could capture at the destination. Does not cover en passant.
     *
     * @param destination the target of the capture
     * @return boolean that represents if the piece can capture at dest
     */
    @Override
    public boolean canCaptureAt(Coordinates destination) {
        for (var movement : captureRestrictions) {
            if (!movement.canMove(getCoordinates(), destination)) return false;
        }
        return true;
    }

    /**
     * Gets the visual type of the piece.
     *
     * @return the type of the piece
     */
    @Override
    public PieceType getGraphicalType() {
        return PieceType.PAWN;
    }

    public void moveTo(Coordinates destination) {
        if (!hasMoved && Math.abs(this.getCoordinates().y() - destination.y()) == 2) {
            ((Pawn) this).setCapturableByEnpassant(true);
        }
        super.moveTo(destination);
    }

    /**
     * toString value for the class
     *
     * @return the text value for the class ("Pawn" here)
     */
    public String toString() {
        return "Pawn";
    }
}