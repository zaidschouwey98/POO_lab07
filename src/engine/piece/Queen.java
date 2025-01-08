package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.AxialMovement;
import engine.movements.DiagonalMovement;
import engine.movements.Movement;

/**
 * Represents the Queen piece in chess.
 * Queens can move any number of squares in a line or diagonally.
 */
public class Queen extends Piece {
	/**
	 * Constructor for the Queen class
	 *
	 * @param color       color of the Queen
	 * @param coordinates initial coordinate of the Queen
	 */
	public Queen(PlayerColor color, Coordinates coordinates) {
		super(color, coordinates,
			new Movement[]{
				new AxialMovement(),
				new DiagonalMovement()
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
		return PieceType.QUEEN;
	}

	/**
	 * toString value for the class
	 * @return the text value for the class ("Queen" here)
	 */
	public String toString(){
		return "Queen";
	}
}