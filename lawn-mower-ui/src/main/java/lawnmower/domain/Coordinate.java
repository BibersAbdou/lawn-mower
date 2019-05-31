package lawnmower.domain;

import java.util.Objects;

public class Coordinate {

 private int x;
 private int y;
  public static Coordinate of(int x, int y)
  {
    return new Coordinate(x,y);
  }
  public Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Coordinate() {
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void update(int x, int y) {
    this.x= x;
    this.y=y;
  }

  @Override
  public String toString() {
    return "{" +
        "x=" + x +
        ", y=" + y +
        '}';
  }

  @Override
  public int hashCode() {
    return Objects.hash(x,y);
  }

  @Override
  public boolean equals(Object obj) {
    if(obj instanceof Coordinate)
    {
      return ((Coordinate) obj).x == x && ((Coordinate) obj).y == y;
    }
    return super.equals(obj);
  }
}
