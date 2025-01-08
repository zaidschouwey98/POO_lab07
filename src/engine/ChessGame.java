package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import engine.piece.*;

/**
 * Manages the chess game, starting the game, handling moves,
 * and updating the view.
 */
public class ChessGame implements ChessController {
	private ChessView view;
	private PlayerColor colorPlaying = PlayerColor.WHITE;
	private Board board;

	/**
	 * Sets up the chess game and shows the chessboard on the screen.
	 *
	 * @param view the screen display for the chess game
	 */
	@Override
	public void start(ChessView view) {
		this.board = new Board();

		this.view = view;
		view.startView();

		updateView();
	}

	/**
	 * Moves a piece on the board from one position to another.
	 *
	 * @param fromX the starting column
	 * @param fromY the starting row
	 * @param toX   the target column
	 * @param toY   the target row
	 * @return true if the move is valid and false otherwise
	 */
	@Override
	public boolean move(int fromX, int fromY, int toX, int toY) {
		Coordinates from = new Coordinates(fromX, fromY);
		Coordinates to = new Coordinates(toX, toY);

		Piece movingPiece = board.getPieceAt(from);
		if (!board.move(from, to, colorPlaying)) return false;
		colorPlaying = colorPlaying.toggle();

		// Pawn promotion
		if (movingPiece instanceof Pawn && (to.y() == 0 || to.y() == 7)) {
			PieceUserChoice choice = view.askUser("Promotion", "Promotion choice",
				new PieceUserChoice(new Knight(movingPiece.getColor(), new Coordinates(toX, toY))),
				new PieceUserChoice(new Bishop(movingPiece.getColor(), new Coordinates(toX, toY))),
				new PieceUserChoice(new Rook(movingPiece.getColor(), new Coordinates(toX, toY))),
				new PieceUserChoice(new Queen(movingPiece.getColor(), new Coordinates(toX, toY)))
			);

			board.removePiece(movingPiece);
			board.addPiece(choice.piece());
		}
		updateView();

		return true;
	}

	/**
	 * Starts a new game by resetting the board and adding all pieces to their starting positions.
	 */
	@Override
	public void newGame() {
		board = new Board();
		colorPlaying = PlayerColor.WHITE;

		int pieceStartRow;
		int pawnStartRow;
		for (PlayerColor color : PlayerColor.values()) {
			if (color == PlayerColor.WHITE) {
				pieceStartRow = 0;
				pawnStartRow = 1;
			} else {
				pieceStartRow = 7;
				pawnStartRow = 6;
			}

			board.addPiece(new Rook(color, new Coordinates(0, pieceStartRow)));
			board.addPiece(new Rook(color, new Coordinates(7, pieceStartRow)));
			board.addPiece(new Knight(color, new Coordinates(6, pieceStartRow)));
			board.addPiece(new Knight(color, new Coordinates(1, pieceStartRow)));
			board.addPiece(new Bishop(color, new Coordinates(2, pieceStartRow)));
			board.addPiece(new Bishop(color, new Coordinates(5, pieceStartRow)));
			board.addPiece(new Queen(color, new Coordinates(3, pieceStartRow)));
			board.addPiece(new King(color, new Coordinates(4, pieceStartRow)));

			for (int i = 0; i < 8; ++i) {
				board.addPiece(new Pawn(color, new Coordinates(i, pawnStartRow)));
			}
		}

		updateView();
	}

	/**
	 * Updates the chessboard display to show the current state of the game.
	 *
	 */
	private void updateView() {
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {
				Piece p = board.getPieceAt(new Coordinates(i, j));
				if (p == null) {
					this.view.removePiece(i, j);
				} else {
					this.view.putPiece(p.getGraphicalType(), p.getColor(), i, j);
				}
			}
		}

		if (board.isChecked()) view.displayMessage("Check !");
		else view.displayMessage("");
	}

	/**
	 * Represents the player's choice of pieces when promoting a pawn.
	 */
	record PieceUserChoice(Piece piece) implements ChessView.UserChoice {

		@Override
		public String textValue() {
			return piece.toString();
		}
	}
}
