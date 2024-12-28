package engine;

import chess.ChessView;
import chess.PlayerColor;
import engine.piece.King;
import engine.piece.Knight;
import engine.piece.Piece;

import java.util.LinkedList;
import java.util.List;

public class Board {
	private static final int WHITE = PlayerColor.WHITE.ordinal();
	private static final int BLACK = PlayerColor.BLACK.ordinal();
	private final King[] kings = new King[2];
	private boolean check = false;

	private final List<List<Piece>> pieces = List.of(
			new LinkedList<>(), // white pieces
			new LinkedList<>()	// black pieces
	);




	public void addPiece(Piece piece) {
		pieces.get(piece.getColor().ordinal()).add(piece);
		if (piece instanceof King)
			kings[piece.getColor().ordinal()] = (King) piece;
	}

	/**
	 * Tries to move a piece from "from" to "dest"
	 * @param from start coordinates
	 * @param dest destination coordinates
	 * @param colorPlaying boolean representing if white is to play
	 * @return boolean representing whether the piece was moved or not
	 */
	public boolean move(Coordinates<Integer> from, Coordinates<Integer> dest, PlayerColor colorPlaying) {
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
		// check that our King would not be checked after the move

		if (target != null)
			target.moveTo(new Coordinates<>(-1, -1));
		p.moveTo(dest);

		Coordinates<Integer> playingKingCoordinates = kings[colorPlaying.ordinal()].getCoordinates();
		for (Piece oppenentPiece : pieces.get(colorPlaying.toggle().ordinal())) {
			// Control if any opponent piece can capture the king
			boolean opponentCanCapture = oppenentPiece.canCaptureAt(playingKingCoordinates);
			if (opponentCanCapture && !isPathObstructed(oppenentPiece.getCoordinates(), playingKingCoordinates)) {
				// Cancel the move
				if (target != null)
					target.moveTo(dest);
				p.moveTo(from);

				return false;
			}
		}

		// Control if opponent King is checked or not
		Coordinates<Integer> enemyKingCord = kings[colorPlaying.toggle().ordinal()].getCoordinates();
		for (Piece oppenentPiece : pieces.get(colorPlaying.ordinal())) {
			if (oppenentPiece.canCaptureAt(enemyKingCord)){
				check = true;
			}
		}
		if (target != null) {
			pieces.get(target.getColor().ordinal()).remove(target);
		}
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


	/**
	 * Verifies that path between a coordinate to another is obstructed
	 * @param from initial coordinates
	 * @param dest destination coordinates
	 * @return boolean that shows is the path is obstructed
	 *
	 * @throws ArrayIndexOutOfBoundsException when the given position is out of the board
	 */
	private boolean isPathObstructed(Coordinates<Integer> from, Coordinates<Integer> dest) throws ArrayIndexOutOfBoundsException {
		int dx = (int) Math.signum(dest.x() - from.x());
		int dy = (int) Math.signum(dest.y() - from.y());

		int x = from.x() + dx;
		int y = from.y() + dy;

		// * infinite loop here
		while (x != dest.x() || y != dest.y()) {
			if(getPieceAt(new Coordinates<>(x, y)) != null) return true;
			x += dx;
			y += dy;
		}
		return false;
	}

	public Piece getPieceAt(Coordinates<Integer> pos){
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
