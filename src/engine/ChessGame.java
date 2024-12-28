package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.piece.*;

public class ChessGame implements ChessController {
	static class PieceMemory implements ChessView.UserChoice{
		private final PieceType p;
		PieceMemory(PieceType piece){
			this.p = piece;
		}

		public PieceType getPieceType() {
			return p;
		}
		@Override
		public String textValue() {
			return p.toString();
		}
	}

	private ChessView view;
	private boolean whiteTurn = true;
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
		if(movingPiece == null || pieceNotInPlayingTeam(movingPiece)) {
			return false;
		}
		boolean moveWasDone = board.move(from, to, whiteTurn);
		if (moveWasDone) {
			// vérifier que la destination était la première ou la dernière rangée
			if (toY == 0 || toY == 7){
				if (movingPiece instanceof Pawn) {
					PieceMemory choice = view.askUser("Promotion", "Promotion choice",
                            new PieceMemory(PieceType.KNIGHT),
                            new PieceMemory(PieceType.BISHOP),
                            new PieceMemory(PieceType.ROOK),
                            new PieceMemory(PieceType.QUEEN)
							);
					Piece newPiece;
					switch(choice.getPieceType()){
						case PieceType.KNIGHT:
							newPiece = new Knight(movingPiece.getColor(), new Coordinates<>(toX, toY));
							break;
						case PieceType.BISHOP:
							newPiece = new Bishop(movingPiece.getColor(), new Coordinates<>(toX, toY));
							break;
						case PieceType.ROOK:
							// TODO remove castling rights to this piece
							newPiece = new Rook(movingPiece.getColor(), new Coordinates<>(toX, toY));
							break;
						case PieceType.QUEEN:
							newPiece = new Queen(movingPiece.getColor(), new Coordinates<>(toX, toY));
							break;
						default:
							newPiece = null;
					}
					board.removePiece(movingPiece);
					board.addPiece(newPiece);
				}
			}
			whiteTurn = !whiteTurn;
		}
		board.updateView(view);

		// this print is for dev purposes only

		return moveWasDone;
	}

	@Override
	public void newGame() {
		// TODO remettre du vide sur les cases où il ne doit pas y avoir de pièces au départ
		whiteTurn = true;
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

			for (int i = 0 ; i < 8 ; ++i){
				board.addPiece(new Pawn(color, new Coordinates<>(i, pawnStartRow)));
			}
		}
		board.updateView(view);
	}

	/**
	 * Verifies that a piece is in the currently playing team
	 * @param piece the piece to check
	 * @return boolean representing if the piece is in the playing team
	 */
	private boolean pieceNotInPlayingTeam(Piece piece) {
		return whiteTurn && piece.getColor() != PlayerColor.WHITE || !whiteTurn && piece.getColor() != PlayerColor.BLACK;
	}

	private ChessView.UserChoice pieceTypeToUserChoice(PieceType piece) {
		return new ChessView.UserChoice() {
			private final PieceType p = piece;

			public PieceType getPieceType() {
				return p;
			}
			@Override
			public String textValue() {
				return p.toString();
			}
		};
	}
}
