package engine;

import chess.PlayerColor;
import engine.piece.King;
import engine.piece.Knight;
import engine.piece.Pawn;
import engine.piece.Piece;
import engine.piece.Rook;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents the chessboard, manages the state, pieces, and movement.
 * Handles castling, en passant, and checking for checks.
 */
public class Board {
    private static final int WHITE = PlayerColor.WHITE.ordinal();
    private static final int BLACK = PlayerColor.BLACK.ordinal();
    private static final int DEFAULT_WIDTH = 8;
    private static final int DEFAULT_HEIGHT = 8;
    private static final int CASTLE_DIST = 2;

    private final int width;
    private final int height;

    private final King[] kings = new King[2];    // to quickly get the positions of the kings when needed
    private final Rook[][] castlableRooks = new Rook[2][2];


    private boolean check = false;

    private final List<List<Piece>> pieces = List.of(
            new LinkedList<>(),   // white pieces
            new LinkedList<>()    // black pieces
    );

    /**
     * Creates a chessboard with a width and height.
     *
     * @param width  the width of the board
     * @param height the height of the board
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Creates a chessboard with the default dimensions of 8x8.
     */
    public Board() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Adds a piece to the board and updates the board state accordingly.
     *
     * @param piece the piece to be added to the board
     */
    public void addPiece(Piece piece) {
        addPiece(piece, false);
    }

    /**
     * Adds a piece to the board and updates the board state accordingly.
     *
     * @param piece       the piece to be added to the board
     * @param gameStarted will indicate if the game started or not, so that board knows if piece is added due to promotion
     *                    or not
     */
    public void addPiece(Piece piece, boolean gameStarted) {
        pieces.get(piece.getColor().ordinal()).add(piece);
        if (gameStarted) {
            return;
        }
        if (piece instanceof King) kings[piece.getColor().ordinal()] = (King) piece;
        if (piece instanceof Rook) add(castlableRooks[piece.getColor().ordinal()], piece);
    }

    /**
     * Removes a piece from the board.
     *
     * @param piece the piece to be removed from the board
     */
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
            Rook rook = castlableRooks[colorPlaying.ordinal()][rookId];

            if (!castle(king, rook)) {
                // Cancel move
                if (target != null) target.moveTo(to);
                p.moveTo(from);

                return false;
            }
            return true;
        } else {
            // Handle en passant for pawns
            if (isEnPassantCapture(from, to)) {
                Coordinates enPassantCapturePos = new Coordinates(to.x(), from.y());
                Piece enPassantTarget = getPieceAt(enPassantCapturePos);
                removePiece(enPassantTarget);
            } else {
                resetEnPassantFlags(colorPlaying.ordinal() == WHITE ? BLACK : WHITE);
            }

            // Normal move
            if (target != null) target.moveTo(new Coordinates(-1, -1));
            p.moveTo(to);

            // Control if any opponent piece can capture the king
            Coordinates playingKingCoordinates = kings[colorPlaying.ordinal()].getCoordinates();
            if (verifyCheck(colorPlaying.toggle(), playingKingCoordinates)) {
                // Cancel move
                if (target != null) target.moveTo(to);
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

    /**
     * Get the piece located at the specified position.
     *
     * @param pos the coordinates of the position
     * @return the piece at the specified position, or null if no piece is present
     * @throws IllegalArgumentException if the position is out of the board boundaries
     */
    public Piece getPieceAt(Coordinates pos) {
        if (pos == null) throw new NullPointerException("Coordinates cannot be null");
        if (!isInBoundaries(pos)) throw new IllegalArgumentException(String.format("Invalid coordinates %s.", pos));
        for (Piece p : pieces.get(WHITE)) {
            if (pos.equals(p.getCoordinates())) return p;
        }
        for (Piece p : pieces.get(BLACK)) {
            if (pos.equals(p.getCoordinates())) return p;
        }

        return null;
    }

    /**
     * Returns whether there is an ongoing check or not
     *
     * @return whether a check is on going or not
     */
    public boolean isChecked() {
        return check;
    }

    /**
     * Verifies if the king is in check.
     *
     * @param opponentColor the color of the opponent pieces
     * @param position      the coordinates of the king
     * @return true if the king is in check and false otherwise
     */
    private boolean verifyCheck(PlayerColor opponentColor, Coordinates position) {
        for (Piece oppenentPiece : pieces.get(opponentColor.ordinal())) {
            boolean isOnPath = oppenentPiece.canCaptureAt(position);
            boolean isReachable = oppenentPiece instanceof Knight || !isPathObstructed(oppenentPiece.getCoordinates(), position);

            if (isOnPath && isReachable) return true;
        }

        return false;
    }

    /**
     * Checks if a position is within the boundaries of the board.
     *
     * @param position the coordinates to check
     * @return true if the position is within boundaries and false otherwise
     */
    private boolean isInBoundaries(Coordinates position) {
        return position.x() >= 0 && position.x() < width && position.y() >= 0 && position.y() < height;
    }

    /**
     * Validates if a movement is allowed for a piece.
     *
     * @param p            the piece to be moved
     * @param target       the target piece at the destination, if there's one
     * @param from         the starting coordinates
     * @param to           the destination coordinates
     * @param colorPlaying the color of the player making the move
     * @return true if the movement is valid and false otherwise
     */
    private boolean isMovementValid(Piece p, Piece target, Coordinates from, Coordinates to, PlayerColor colorPlaying) {
        // General invalid movement cases
        if (p == null || !p.getColor().equals(colorPlaying) || !(p instanceof Knight) && isPathObstructed(from, to)) {
            return false;
        }
        // Invalid movement cases depending on the destination
        if (target == null) {
            if (p instanceof Pawn && isEnPassantCapture(from, to)) {
                // Tries to enpassant
                return true;
            }
            return p.canMoveTo(to);
        } else {
            if (target.getColor() == p.getColor()) return false;
            else return p.canCaptureAt(target.getCoordinates());
        }
    }

    /**
     * Adds a piece to an array of pieces.
     *
     * @param array the array to add the piece to
     * @param p     the piece to be added
     */
    private void add(Piece[] array, Piece p) {
        int i = 0;
        while (i < array.length && array[i] != null) ++i;

        array[i] = p;
    }

    /**
     * Handles the castling move for a king and a rook.
     *
     * @param king the king involved in castling
     * @param rook the rook involved in castling
     * @return true if the castling move was successful and false otherwise
     */
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

    /**
     * Reset the en passant flags for a given color
     *
     * @param playerColor the color to reset the en passant flags
     */
    private void resetEnPassantFlags(int playerColor) {
        for (Piece piece : pieces.get(playerColor)) {
            if (piece instanceof Pawn) {
                ((Pawn) piece).setCapturableByEnpassant(false);
            }
        }
    }

    /**
     * Verifies is a capture is an en passant capture
     *
     * @param from start coordinates
     * @param to   destination coordinates
     * @return boolean value indicating the result
     */
    private boolean isEnPassantCapture(Coordinates from, Coordinates to) {
        Piece p = getPieceAt(from);
        if (!(p instanceof Pawn)) return false;

        if (from.x() != to.x() && getPieceAt(to) == null) {
            Coordinates enPassantCapturePos = new Coordinates(to.x(), from.y());
            Piece enPassantTarget = getPieceAt(enPassantCapturePos);
            return enPassantTarget instanceof Pawn && ((Pawn) enPassantTarget).isCapturableByEnpassant();
        }
        return false;
    }
}
