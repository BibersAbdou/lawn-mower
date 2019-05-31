package lawnmower.domain;

public enum Direction {
  N,E,S,W;

  public static Direction nextOf(Direction direction, int offset) {
    //in java % returns the remainder
    int index = (((direction.ordinal() + offset) % 4)+4 )%4 ;
    return Direction.values()[index];
  }
}
