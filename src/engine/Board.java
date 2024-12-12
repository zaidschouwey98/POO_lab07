package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.piece.Piece;

public class Board {
	private final Piece[][] board;

	public Board(int width, int height) {
		if (width < 1 || height < 1)
			throw new IllegalArgumentException("Width and height must be greater than 0.");

		board = new Piece[height][width];
	}

	public boolean move(Coordinates<Integer> from, Coordinates<Integer> dest) {
		Piece p;
		try {
			p = board[from.x()][from.y()];
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}

		return p == null || p.moveTo(dest);
	}

	public void updateView(ChessView view) {
		for (int i = 0 ; i < board.length ; ++i){
			for(int j = 0 ; j < board[0].length ; ++j){
				if(board[i][j] != null){
					PieceType p = board[i][j].getGraphicalType();
					view.putPiece(p, PlayerColor.BLACK,i,j);
				}
			}
		}
	}
}
