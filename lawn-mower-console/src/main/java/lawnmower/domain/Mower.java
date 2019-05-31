package lawnmower.domain;

public class Mower {


  /**
   * current position of mower
   */
  private Coordinate coordinate;
  /**
   * direction reference of position of this mower
   */
  private Direction direction;
  private String id;

  public Mower( Coordinate coordinate, Direction direction) {

    this.coordinate = coordinate;
    this.direction = direction;
  }

  public Mower() {
  }

  public static Mower of( int x, int y, Direction direction) {
    return new Mower(Coordinate.of(x,y),direction);
  }



  public Coordinate getCoordinate() {
    return coordinate;
  }

  public void setCoordinate(Coordinate coordinate) {
    this.coordinate = coordinate;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  @Override
  public String toString() {
    return coordinate.getX() +" "+coordinate.getY() +" "+ direction;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
