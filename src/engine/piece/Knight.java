package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Coordinates;
import engine.movements.KnightMovement;
import engine.movements.Movement;

/**
 * Represents the Knight piece in chess.
 * Knights move in L shape and can jump over other pieces.
 */
public class Knight extends Piece {
	/**
	 * Constructor for the Knight class
	 *
	 * @param color       color of the Knight
	 * @param coordinates initial coordinate of the Knight
	 */
	public Knight(PlayerColor color, Coordinates coordinates) {
		super(color, coordinates,
			new Movement[]{
				new KnightMovement()
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
		return PieceType.KNIGHT;
	}

	/**
	 * toString value for the class
	 * @return the text value for the class ("Knight" here)
	 */
	public String toString(){
		return "Knight";
	}
}
