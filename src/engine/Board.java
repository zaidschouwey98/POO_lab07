package engine;

import chess.ChessView;
import chess.PlayerColor;
import engine.piece.Knight;
import engine.piece.Piece;

import java.util.LinkedList;
import java.util.List;

public class Board {
	private static final int WHITE = PlayerColor.WHITE.ordinal();
	private static final int BLACK = PlayerColor.BLACK.ordinal();

	private final List<List<Piece>> pieces = List.of(
		new LinkedList<>(), // white pieces
		new LinkedList<>()	// black pieces
	);

	public void addPiece(Piece piece) {
		pieces.get(piece.getColor().ordinal()).add(piece);
	}

	public boolean move(Coordinates<Integer> from, Coordinates<Integer> dest) {
		Piece p = getPieceAt(from);
		Piece target = getPieceAt(dest);

		if (p == null) return false;
		if (!(p instanceof Knight) && isPathObstructed(from, dest)) return false;
		if (target == null) {
			if (!p.canMoveTo(dest)) return false;
		} else {
			if (target.getColor() == p.getColor()) return false;
			else if (!p.canCaptureAt(target.getCoordinates())) return false;
		}

		p.moveTo(dest);

		// vérifier que bouger la pièce ne mettrait pas son roi en échec
		// si la pièce il y a des vérifications à faire en plus (voir google docs)

		if (target != null)
			pieces.get(target.getColor().ordinal()).remove(target);

		return true;
	}

	public void updateView(ChessView view) {
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {
				Piece p = getPieceAt(new Coordinates<>(i, j));
				if (p == null) {
					view.removePiece(i, j);
				} else {
					view.putPiece(p.getGraphicalType(), p.getColor(), i, j);
				}
			}
		}
	}



	private boolean isPathObstructed(Coordinates<Integer> from, Coordinates<Integer> dest) throws ArrayIndexOutOfBoundsException {
		/*int dx = (int) Math.signum(dest.x() - from.x());
		int dy = (int) Math.signum(dest.y() - from.y());

		int x = from.x() + dx;
		int y = from.y() + dy;

		for (Piece p : whitePieces) {
			if ()
		}

		while (!(x == dest.x() && y == dest.y())) {
			if (board[x][y] != null) return true;

			x += dx;
			y += dy;
		}

		return false;*/

		return false;
	}

	private Piece getPieceAt(Coordinates<Integer> pos){
		for (Piece p : pieces.get(WHITE)) {
			if (pos.equals(p.getCoordinates()))
				return p;
		}
		for (Piece p : pieces.get(BLACK)) {
			if (pos.equals(p.getCoordinates()))
				return p;
		}
		return null;
	}
}
