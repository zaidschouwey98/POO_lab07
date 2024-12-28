package engine;

import chess.PlayerColor;
import engine.piece.King;
import engine.piece.Knight;
import engine.piece.Piece;

import java.util.LinkedList;
import java.util.List;

public class Board {
	private static final int WHITE = PlayerColor.WHITE.ordinal();
	private static final int BLACK = PlayerColor.BLACK.ordinal();
	private static final int DEFAULT_WIDTH = 8;
	private static final int DEFAULT_HEIGHT = 8;

	private final int width;
	private final int height;

	private final King[] kings = new King[2];
	private boolean check = false;

	private final List<List<Piece>> pieces = List.of(
			new LinkedList<>(), // white pieces
			new LinkedList<>()	// black pieces
	);

	public Board(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public Board() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public void addPiece(Piece piece) {
		pieces.get(piece.getColor().ordinal()).add(piece);
		if (piece instanceof King)
			kings[piece.getColor().ordinal()] = (King) piece;
	}

	public void removePiece(Piece piece) {
		if(piece instanceof King)
			System.out.println("WARNING : YOU SHOULDN'T REMOVE A KING FROM THE GAME");
		pieces.get(piece.getColor().ordinal()).remove(piece);
	}

	/**
	 * Tries to move a piece from "from" to "dest"
	 * @param from start coordinates
	 * @param to destination coordinates
	 * @param colorPlaying boolean representing if white is to play
	 * @return boolean representing whether the piece was moved or not
	 */
	public boolean move(Coordinates<Integer> from, Coordinates<Integer> to, PlayerColor colorPlaying) {
		Piece p = getPieceAt(from);
		Piece target = getPieceAt(to);

		if (!isMovementValid(p, target, from, to, colorPlaying)) return false;

		if (target != null)
			target.moveTo(new Coordinates<>(-1, -1));
		p.moveTo(to);

		// Control if any opponent piece can capture the king
		Coordinates<Integer> playingKingCoordinates = kings[colorPlaying.ordinal()].getCoordinates();
		if (verifyCheck(colorPlaying.toggle(), playingKingCoordinates)) {
			if (target != null)
				target.moveTo(to);
			p.moveTo(from);

			return false;
		}
		check = false;

		// Control if opponent King is checked or not
		Coordinates<Integer> opponentKingCoordinates = kings[colorPlaying.toggle().ordinal()].getCoordinates();
		check = verifyCheck(colorPlaying, opponentKingCoordinates);
		if (target != null) {
			pieces.get(target.getColor().ordinal()).remove(target);
		}

		return true;
	}

	/**
	 * Verifies that path between a coordinate to another is obstructed
	 * @param from initial coordinates
	 * @param dest destination coordinates
	 * @return boolean that shows is the path is obstructed
	 *
	 * @throws ArrayIndexOutOfBoundsException when the given position is out of the board
	 */
	private boolean isPathObstructed(Coordinates<Integer> from, Coordinates<Integer> dest) {
		if (from == null || dest == null) throw new NullPointerException();
		if (from.equals(dest)) return false;

		int dx = (int) Math.signum(dest.x() - from.x());
		int dy = (int) Math.signum(dest.y() - from.y());

		// * infinite loop here
		for (Coordinates<Integer> it = from.move(dx, dy); isInBoundaries(it) && !it.equals(dest); it = it.move(dx, dy)) {
			if (getPieceAt(it) != null) return true;
		}

		return false;
	}

	public Piece getPieceAt(Coordinates<Integer> pos) {
		if (pos == null) throw new NullPointerException("Coordinates cannot be null");
		if (!isInBoundaries(pos)) throw new IllegalArgumentException(String.format("Invalid coordinates (%d,%d)", pos.x(), pos.y()));
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

	public boolean isChecked() {
		return check;
	}

	private boolean verifyCheck(PlayerColor opponentColor, Coordinates<Integer> position) {
		for (Piece oppenentPiece : pieces.get(opponentColor.ordinal())) {
			boolean isOnPath = oppenentPiece.canCaptureAt(position);
			boolean isReachable = oppenentPiece instanceof Knight || !isPathObstructed(oppenentPiece.getCoordinates(), position);

			if (isOnPath && isReachable) return true;
		}

		return false;
	}

	private boolean isInBoundaries(Coordinates<Integer> position) {
		return position.x() >= 0 && position.x() < width && position.y() >= 0 && position.y() < height;
	}

	private boolean isMovementValid(Piece p, Piece target, Coordinates<Integer> from, Coordinates<Integer> to, PlayerColor colorPlaying) {
		// General invalid movement cases
		if (
			p == null ||
				!p.getColor().equals(colorPlaying) ||
				!(p instanceof Knight) && isPathObstructed(from, to)
		) return false;
		// Invalid movement cases depending on the destination
		if (target == null) {
			return p.canMoveTo(to);
		} else {
			if (target.getColor() == p.getColor()) return false;
			else return p.canCaptureAt(target.getCoordinates());
		}
	}
}
