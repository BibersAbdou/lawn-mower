package lawnmower;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lawnmower.controller.MowerController;
import lawnmower.domain.Command;
import lawnmower.domain.Mower;
import lawnmower.domain.MowerCommand;
import lawnmower.input.ScenarioFileReader;

public class LawnMowerApp {






  public static void main(String[] args)
  {



    if(args.length==0 || args[0] == null)
    {
      throw new IllegalArgumentException("file name must be specified");
    }

    try {
      ScenarioFileReader scenario = new ScenarioFileReader(new File(args[0])) ;

      MowerController mowerController =new MowerController(scenario.getLawn());
      MowerCommand mowerCommand = scenario.nextMowerCommand();
      List<String> res = new ArrayList<>();
      while (mowerCommand !=null)
      {
        try {


          Mower mower = mowerCommand.getMower();
          Iterator<Command> it = mowerCommand
              .getCommandIterator();

          while (it.hasNext() )
          {
            Command c = it.next();
            if(c !=null)
            {
              mowerController.move(mower ,c);

            }
          }


          res.add(mower.toString());
          mowerCommand = scenario.nextMowerCommand();

        }
        catch (Exception ex)
        {
          ex.printStackTrace();
        }
      }

      System.out.println("result : ");
      for (String s: res) {
        System.out.println(s);
      }

    } catch (Exception e) {
      System.out.println("error. fail to load scenario file cause  " + e.getMessage());
      e.printStackTrace();
    }
  }
}
