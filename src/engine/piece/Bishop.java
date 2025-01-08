package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.DiagonalMovement;
import engine.movements.Movement;

/**
 * Represents the Bishop piece in chess.
 * Bishops can move diagonally any number of squares.
 */
public class Bishop extends Piece {
	/**
	 * Constructor for the Bishop class
	 * @param color color of the Bishop
	 * @param coordinates initial coordinate of the Bishop
	 */
	public Bishop(PlayerColor color, Coordinates coordinates) {
		super(color, coordinates,
			new Movement[]{new DiagonalMovement()},
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
		return PieceType.BISHOP;
	}

	/**
	 * toString value for the class
	 * @return the text value for the class ("Bishop" here)
	 */
	public String toString(){
		return "Bishop";
	}
}

