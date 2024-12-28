package chess;

public enum PlayerColor {
  WHITE, BLACK;

  /**
   * @return the color opposite to the current color
   */
  public PlayerColor toggle() {
    if (this == BLACK) return WHITE;
    else return BLACK;
  }
}
