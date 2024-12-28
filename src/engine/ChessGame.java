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

		board.updateView(view);
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
		board.updateView(view);

		return moveWasValid;
	}

	@Override
	public void newGame() {
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

		// TODO remettre du vide sur les cases où il ne doit pas y avoir de pièces au départ
		// TODO déplacer updateView dans ChessGame
		board.updateView(view);
	}
}
