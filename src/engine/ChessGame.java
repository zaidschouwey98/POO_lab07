package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

public class ChessGame implements ChessController {

  private ChessView view;

  @Override
  public void start(ChessView view) {
    this.view = view;
    view.startView();
  }

  @Override
  public boolean move(int fromX, int fromY, int toX, int toY) {
    System.out.println(String.format("TO REMOVE : from (%d, %d) to (%d, %d)", fromX, fromY, toX, toY)); // TODO remove
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
      view.putPiece(PieceType.ROOK, color, 0, pieceStartRow);
      view.putPiece(PieceType.ROOK, color, 7, pieceStartRow);
      view.putPiece(PieceType.KNIGHT, color, 1, pieceStartRow);
      view.putPiece(PieceType.KNIGHT, color, 6, pieceStartRow);
      view.putPiece(PieceType.BISHOP, color, 2, pieceStartRow);
      view.putPiece(PieceType.BISHOP, color, 5, pieceStartRow);
      view.putPiece(PieceType.QUEEN, color, 3, pieceStartRow);
      view.putPiece(PieceType.KING, color, 4, pieceStartRow);

      for (int i = 0 ; i < 8 ; ++i){
        view.putPiece(PieceType.PAWN, color, i, pawnStartRow);
      }
    }
  }
}
