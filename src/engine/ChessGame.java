package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.piece.King;
import engine.piece.Piece;

public class ChessGame implements ChessController {

  private ChessView view;

  private Piece[][] board;

  @Override
  public void start(ChessView view) {
    this.board = new Piece[8][8];
    for(int x = 0; x < 8; x++){
      for(int y = 0; y < 8; y++){
        if(y <= 1){
          //White

        } else if(y >= 6){
          //Black

        }
        this.board[4][7] = new King(PlayerColor.BLACK, new Coordinates(5,8));
      }
    }


    this.view = view;
    view.startView();

    boardToView();
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
