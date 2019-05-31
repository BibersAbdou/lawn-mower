package lawnmower.controller;

import lawnmower.domain.Command;
import lawnmower.domain.Direction;
import lawnmower.domain.Field;
import lawnmower.domain.Mower;

public class MowerController {





  private Field lawn ;


  public MowerController() {
  }


  public MowerController(Field lawn) {
    this.lawn = lawn;
  }

  public void init(int height , int width)
  {
    this.lawn =new Field(height,width);
  }



  public void move( Mower mower, Command command) throws Exception {

    switch (command)
    {
      case F:

        int x = mower.getCoordinate().getX();
        int y = mower.getCoordinate().getY();
            switch (mower.getDirection())
            {
              case E:
                x +=1;
                break;
              case W:
                x -=1;
                break;
              case N:
                y +=1;
                break;
               case S:
                y -=1;
              break;

            }

            if(lawn.outside(x,y)) {

              System.out.println("warn. mower outside ");
              return;
            }
            mower.getCoordinate().update(x,y);
        break;
      case L:
        mower.setDirection(Direction.nextOf(mower.getDirection(),-1));
        break;
      case R:
        mower.setDirection(Direction.nextOf(mower.getDirection(),1));
        break;
    }
  }
}
