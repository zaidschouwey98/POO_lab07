package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import engine.piece.*;

public class ChessGame implements ChessController {

	private ChessView view;

	private Board board;

	@Override
	public void start(ChessView view) {
		this.board = new Board(8, 8);

		this.view = view;
		view.startView();

		board.updateView(view);
	}

	@Override
	public boolean move(int fromX, int fromY, int toX, int toY) {
		System.out.printf("TO REMOVE : from (%d, %d) to (%d, %d)\n", fromX, fromY, toX, toY); // TODO remove
		return false; // TODO
	}

	@Override
	public void newGame() {
		view.displayMessage("new game (TO REMOVE)"); // TODO
		// view.putPiece(PieceType.KING, PlayerColor.BLACK, 3, 4); // TODO exemple uniquement
		int pieceStartRow;
		int pawnStartRow;
		for (PlayerColor color : PlayerColor.values()) {
			pieceStartRow = color.ordinal() == PlayerColor.WHITE.ordinal() ? 0 : 7;
			pawnStartRow = color.ordinal() == PlayerColor.WHITE.ordinal() ? 1 : 6;
			board.setPiece(0, pieceStartRow, new Rook(color, new Coordinates<>(0, pieceStartRow)));
			board.setPiece(7, pieceStartRow, new Rook(color,new Coordinates<>(7,pieceStartRow)));
			board.setPiece(6, pieceStartRow, new Knight(color,new Coordinates<>(6,pieceStartRow)));
			board.setPiece(1, pieceStartRow, new Knight(color,new Coordinates<>(1,pieceStartRow)));
			board.setPiece(2, pieceStartRow,  new Bishop(color,new Coordinates<>(2,pieceStartRow)));
			board.setPiece(5, pieceStartRow, new Bishop(color,new Coordinates<>(5,pieceStartRow)));
			board.setPiece(3, pieceStartRow, new Queen(color,new Coordinates<>(3,pieceStartRow)));
			board.setPiece(4, pieceStartRow, new King(color,new Coordinates<>(4,pieceStartRow)));

			for (int i = 0 ; i < 8 ; ++i){
				board.setPiece(i, pawnStartRow, new Pawn(color,new Coordinates<>(i,pieceStartRow)));
			}
		}
		board.updateView(view);
	}
}
