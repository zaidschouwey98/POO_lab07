package engine;

import chess.PlayerColor;
import engine.piece.King;
import engine.piece.Knight;
import engine.piece.Pawn;
import engine.piece.Piece;
import engine.piece.Rook;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Board {
	private static final int WHITE = PlayerColor.WHITE.ordinal();
	private static final int BLACK = PlayerColor.BLACK.ordinal();
	private static final int DEFAULT_WIDTH = 8;
	private static final int DEFAULT_HEIGHT = 8;
	private static final int CASTLE_DIST = 2;

	private final int width;
	private final int height;

	private final King[] kings = new King[2];
	private final Rook[][] rooks = new Rook[2][2];
	private boolean check = false;

	private final List<List<Piece>> pieces = List.of(
		new LinkedList<>(), // white pieces
		new LinkedList<>()    // black pieces
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
		if (piece instanceof Rook)
			add(rooks[piece.getColor().ordinal()], piece);
	}

	public void removePiece(Piece piece) {
		pieces.get(piece.getColor().ordinal()).remove(piece);
	}

	/**
	 * Tries to move a piece from "from" to "dest"
	 *
	 * @param from         start coordinates
	 * @param to           destination coordinates
	 * @param colorPlaying boolean representing if white is to play
	 * @return boolean representing whether the piece was moved or not
	 */
	public boolean move(Coordinates from, Coordinates to, PlayerColor colorPlaying) {
		Piece p = getPieceAt(from);
		Piece target = getPieceAt(to);

		boolean movementWasValid = isMovementValid(p, target, from, to, colorPlaying);
		if (!movementWasValid) return false;

		// Castle
		if (!check && p instanceof King king && (to.equals(from.move(CASTLE_DIST, 0)) || to.equals(from.move(-CASTLE_DIST, 0)))) {
			int rookId = to.x() < king.getCoordinates().x() ? 0 : 1;
			Rook rook = rooks[colorPlaying.ordinal()][rookId];

			if (!castle(king, rook)) {
				// Cancel move
				if (target != null)
					target.moveTo(to);
				p.moveTo(from);

				return false;
			}

			return true;
		} else {
			if (p instanceof Pawn && target == null && !Objects.equals(from.x(), to.x())) {
				Coordinates enPassantCapturePos = new Coordinates(to.x(), from.y());
				Piece enPassantTarget = getPieceAt(enPassantCapturePos);
				if (enPassantTarget instanceof Pawn && ((Pawn) enPassantTarget).isCapturableByEnpassant()) {
					removePiece(enPassantTarget);
				}
			}

			// Normal move
			if (target != null)
				target.moveTo(new Coordinates(-1, -1));
			p.moveTo(to);

			// Control if any opponent piece can capture the king
			Coordinates playingKingCoordinates = kings[colorPlaying.ordinal()].getCoordinates();
			if (verifyCheck(colorPlaying.toggle(), playingKingCoordinates)) {
				// Cancel move
				if (target != null)
					target.moveTo(to);
				p.moveTo(from);

				return false;
			}

			// Remove targeted piece, if any
			if (target != null) {
				pieces.get(target.getColor().ordinal()).remove(target);
			}

			check = false;
		}

		// Control if opponent King is checked or not
		Coordinates opponentKingCoordinates = kings[colorPlaying.toggle().ordinal()].getCoordinates();
		check = verifyCheck(colorPlaying, opponentKingCoordinates);

		if (p instanceof Pawn) {
			((Pawn) p).setCapturableByEnpassant(Math.abs(from.y() - to.y()) == 2);
		}
		return true;
	}

	/**
	 * Verifies that path between a coordinate to another is obstructed
	 *
	 * @param from initial coordinates
	 * @param dest destination coordinates
	 * @return boolean that shows is the path is obstructed
	 * @throws ArrayIndexOutOfBoundsException when the given position is out of the board
	 */
	private boolean isPathObstructed(Coordinates from, Coordinates dest) {
		if (from == null || dest == null) throw new NullPointerException();
		if (from.equals(dest)) return false;

		int dx = (int) Math.signum(dest.x() - from.x());
		int dy = (int) Math.signum(dest.y() - from.y());

		// * infinite loop here
		for (Coordinates it = from.move(dx, dy); isInBoundaries(it) && !it.equals(dest); it = it.move(dx, dy)) {
			if (getPieceAt(it) != null) return true;
		}

		return false;
	}

	public Piece getPieceAt(Coordinates pos) {
		if (pos == null) throw new NullPointerException("Coordinates cannot be null");
		if (!isInBoundaries(pos))
			throw new IllegalArgumentException(String.format("Invalid coordinates %s.", pos));
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

	private boolean verifyCheck(PlayerColor opponentColor, Coordinates position) {
		for (Piece oppenentPiece : pieces.get(opponentColor.ordinal())) {
			boolean isOnPath = oppenentPiece.canCaptureAt(position);
			boolean isReachable = oppenentPiece instanceof Knight || !isPathObstructed(oppenentPiece.getCoordinates(), position);

			if (isOnPath && isReachable) return true;
		}

		return false;
	}

	private boolean isInBoundaries(Coordinates position) {
		return position.x() >= 0 && position.x() < width && position.y() >= 0 && position.y() < height;
	}

	private boolean isMovementValid(Piece p, Piece target, Coordinates from, Coordinates to, PlayerColor colorPlaying) {
		// General invalid movement cases
		if (p == null || !p.getColor().equals(colorPlaying) || !(p instanceof Knight) && isPathObstructed(from, to)) {
			return false;
		}
		// Invalid movement cases depending on the destination
		if (target == null) {
			if (p instanceof Pawn && from.x() != to.x()) {
				// Tries to enpassant
				Coordinates enPassantCapturePos = new Coordinates(to.x(), from.y());
				Piece enPassantTarget = getPieceAt(enPassantCapturePos);
				return enPassantTarget instanceof Pawn && ((Pawn) enPassantTarget).isCapturableByEnpassant();
			}
			return p.canMoveTo(to);
		} else {
			if (target.getColor() == p.getColor()) return false;
			else return p.canCaptureAt(target.getCoordinates());
		}
	}

	private void add(Piece[] array, Piece p) {
		int i = 0;
		while (i < array.length && array[i] != null) ++i;

		array[i] = p;
	}

	private boolean castle(King king, Rook rook) {
		if (king.hasMoved() || rook.hasMoved()) return false;

		int d = rook.getCoordinates().x() < king.getCoordinates().x() ? -1 : 1;
		var to = king.getCoordinates().move(d * CASTLE_DIST, 0);
		// The rook is not going to `to`, but the path has to be clear anyway
		if (isPathObstructed(rook.getCoordinates(), king.getCoordinates())) return false;

		// Check if an opponent can reach one of the squares on the path
		for (var it = king.getCoordinates().move(d, 0); !it.equals(to); it = it.move(d, 0)) {
			if (verifyCheck(king.getColor().toggle(), it)) return false;
		}

		rook.moveTo(king.getCoordinates().move(d, 0));
		king.moveTo(king.getCoordinates().move(d * CASTLE_DIST, 0));

		return true;
	}
}
