package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.*;

/**
 * Represents the king piece in chess.
 * The King can move one square in any direction.
 */
public class King extends FirstMovePiece {
	private static final int CASTLE_DIST = 2;

	/**
	 * Constructor for the King Class
	 * @param color color of the King
	 * @param coordinates initial coordinate of the King
	 */
	public King(PlayerColor color, Coordinates coordinates) {
		super(color, coordinates,
			new Movement[]{
				new AxialMovement(),
				new DiagonalMovement()
			},
			new Movement[]{
				new RadiusMovementRestriction(1)
			}
		);
	}

	/**
	 * Gets the visual type of the piece.
	 *
	 * @return the type of the piece
	 */
	@Override
	public PieceType getGraphicalType() {
		return PieceType.KING;
	}

	/**
	 * This method is used to implement special moves that don't fall in the piece's default moveset. In the king's
	 * case, it will verify if a castle is tried.
	 *
	 * @param dest the target position
	 * @return true if the move is allowed
	 */
	@Override
	public boolean isExceptionalMoveAllowed(Coordinates dest) {
		if (hasMoved()) return false;

		boolean isLeftRook = dest.equals(getCoordinates().move(-CASTLE_DIST, 0));
		boolean isRightRook = dest.equals(getCoordinates().move(CASTLE_DIST, 0));

		return isLeftRook || isRightRook;
	}

	/**
	 * toString value for the class
	 * @return the text value for the class ("King" here)
	 */
	public String toString(){
		return "King";
	}
}
