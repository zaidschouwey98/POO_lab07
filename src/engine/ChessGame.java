package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import engine.piece.*;

public class ChessGame implements ChessController {

	private ChessView view;
	private PlayerColor colorPlaying = PlayerColor.WHITE;
	private Board board;

	@Override
	public void start(ChessView view) {
		this.board = new Board();

		this.view = view;
		view.startView();

		updateView(board);
	}

	@Override
	public boolean move(int fromX, int fromY, int toX, int toY) {
		Coordinates<Integer> from = new Coordinates<>(fromX, fromY);
		Coordinates<Integer> to = new Coordinates<>(toX, toY);

		Piece movingPiece = board.getPieceAt(from);
		if (movingPiece == null || !movingPiece.getColor().equals(colorPlaying)) {
			return false;
		}
		boolean moveWasValid = board.move(from, to, colorPlaying);
		if (moveWasValid) {
			colorPlaying = colorPlaying.toggle();
		}
		updateView(board);

		return moveWasValid;
	}

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

			board.addPiece(new Rook(color, new Coordinates<>(0, pieceStartRow)));
			board.addPiece(new Rook(color, new Coordinates<>(7, pieceStartRow)));
			board.addPiece(new Knight(color, new Coordinates<>(6, pieceStartRow)));
			board.addPiece(new Knight(color, new Coordinates<>(1, pieceStartRow)));
			board.addPiece(new Bishop(color, new Coordinates<>(2, pieceStartRow)));
			board.addPiece(new Bishop(color, new Coordinates<>(5, pieceStartRow)));
			board.addPiece(new Queen(color, new Coordinates<>(3, pieceStartRow)));
			board.addPiece(new King(color, new Coordinates<>(4, pieceStartRow)));

			for (int i = 0 ; i < 8 ; ++i) {
				board.addPiece(new Pawn(color, new Coordinates<>(i, pawnStartRow)));
			}
		}

		updateView(board);
	}

	private void updateView(Board board) {
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {
				Piece p = board.getPieceAt(new Coordinates<>(i, j));
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
}
