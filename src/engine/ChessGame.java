package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.piece.*;

public class ChessGame implements ChessController {

  private ChessView view;

  private Piece[][] board;

  @Override
  public void start(ChessView view) {
    this.board = new Piece[8][8];

    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        if (y <= 1){
          //White
          System.out.println("The whites play.");
        } else if(y >= 6) {
          //Black
          System.out.println("The blacks play.");
        }
      }
    }


    this.view = view;
    view.startView();

    boardToView();
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
      board[0][pieceStartRow] = new Rook(color, new Coordinates<>(0, pieceStartRow));
      board[7][pieceStartRow] = new Rook(color,new Coordinates<>(7,pieceStartRow));
      board[6][pieceStartRow] = new Knight(color,new Coordinates<>(6,pieceStartRow));
      board[1][pieceStartRow] = new Knight(color,new Coordinates<>(1,pieceStartRow));
      board[2][pieceStartRow] = new Bishop(color,new Coordinates<>(2,pieceStartRow));
      board[5][pieceStartRow] = new Bishop(color,new Coordinates<>(5,pieceStartRow));
      board[3][pieceStartRow] = new Queen(color,new Coordinates<>(3,pieceStartRow));
      board[4][pieceStartRow] = new King(color,new Coordinates<>(4,pieceStartRow));

      for (int i = 0 ; i < 8 ; ++i){
        board[i][pawnStartRow] = new Pawn(color,new Coordinates<>(i,pieceStartRow));
      }
    }
    boardToView();
  }

  public void boardToView(){
    for(int i = 0 ; i < 8 ; ++i){
      for(int j = 0 ; j < 8 ; ++j){
        if(board[i][j] != null){
          PieceType p = board[i][j].getGraphicalType();
          view.putPiece(p,PlayerColor.BLACK,i,j);
        }
      }
    }
  }

}
