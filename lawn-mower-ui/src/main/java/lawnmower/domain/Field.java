package lawnmower.domain;

public class Field {


  private int height ;
  private int width ;


  public Field(int height, int width) {
    this.height = height;
    this.width = width;


  }


  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public boolean outside(int x, int y) {
    return x>width || x <0 || y> height || y<0;
  }





  @Override
  public String toString() {
    return "{" +
        "height=" + height +
        ", width=" + width +
        '}';
  }
}
