package engine;

import chess.ChessView;
import engine.piece.Knight;
import engine.piece.Piece;

public class Board {
	private final Piece[][] board;

	public Board(int width, int height) {
		if (width < 1 || height < 1)
			throw new IllegalArgumentException("Width and height must be greater than 0.");

		board = new Piece[height][width];
	}

	public void setPiece(int x, int y, Piece piece) {
		try {
			board[x][y] = piece;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new RuntimeException("Invalid position.");
		}
	}

	public boolean move(Coordinates<Integer> from, Coordinates<Integer> dest) {
		Piece p;

		try {
			p = board[from.x()][from.y()];

			if (p.moveTo(dest)) {
				if (!(p instanceof Knight) && isPathObstructed(from, dest)) {
					p.moveTo(from);

					return false;
				}
				Piece p2 = board[dest.x()][dest.y()];
				if (p2 != null && p2.getColor() == p.getColor()) {
					p.moveTo(from);

					return false;
				}

				board[dest.x()][dest.y()] = p;
				board[from.x()][from.y()] = null;
			}
		} catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
			return false;
		}

		return true;
	}

	public void updateView(ChessView view) {
		for (int i = 0; i < board.length; ++i) {
			for (int j = 0; j < board[0].length; ++j) {
				Piece p = board[i][j];
				if (p == null) {
					view.removePiece(i, j);
				} else {
					view.putPiece(p.getGraphicalType(), p.getColor(), i, j);
				}
			}
		}
	}



	private boolean isPathObstructed(Coordinates<Integer> from, Coordinates<Integer> dest) throws ArrayIndexOutOfBoundsException {
		int dx = (int) Math.signum(dest.x() - from.x());
		int dy = (int) Math.signum(dest.y() - from.y());
		System.out.println("dx is " + dx + " dy is " + dy);

		int x = from.x() + dx;
		int y = from.y() + dy;

		System.out.println("x is : " + x + ", y is : " + y);

		while (!(x == dest.x() && y == dest.y())) {
			System.out.println("Nouveau tour de boucle");
			if (board[x][y] != null) return true;

			x += dx;
			y += dy;
		}

		return false;
	}
}
