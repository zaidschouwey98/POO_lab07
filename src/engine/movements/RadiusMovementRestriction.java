package engine.movements;

import engine.Coordinates;

/**
 * Limits movement to a certain radius from the starting position.
 */
public class RadiusMovementRestriction implements Movement {

    private final int movementRadius;

    /**
     * Sets the maximum distance the piece can move.
     *
     * @param movementRadius the maximum number of squares
     */
    public RadiusMovementRestriction(int movementRadius) {
        this.movementRadius = movementRadius;
    }

    /**
     * Checks that a piece does not move more that the allowed radius.
     *
     * @param from the starting position
     * @param to   the target position
     * @return true if the move is allowed and false otherwise
     */
    @Override
    public boolean canMove(Coordinates from, Coordinates to) {
        int xDiff = Math.abs(from.x() - to.x());
        int yDiff = Math.abs(from.y() - to.y());

        return xDiff <= movementRadius && yDiff <= movementRadius;
    }
}
