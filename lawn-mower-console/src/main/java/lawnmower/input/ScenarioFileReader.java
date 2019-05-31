package lawnmower.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.PrimitiveIterator.OfInt;
import lawnmower.domain.Command;
import lawnmower.domain.Direction;
import lawnmower.domain.Field;
import lawnmower.domain.Mower;
import lawnmower.domain.MowerCommand;

public class ScenarioFileReader {


  private static Integer sequence = 1;
  private static final String SEPARATOR = " ";
  private BufferedReader reader;

  private Field lawn;

  public ScenarioFileReader(File file) throws Exception {
    reader = new BufferedReader(new FileReader(file));

    String line = reader.readLine();

    if (line == null) {
      throw new IllegalArgumentException("file is empty");
    }

    String[] res = line.split(SEPARATOR);
    if(res.length <2)    throw new IllegalArgumentException("can't read lawn coordinates. must have x and y ");

    lawn = new Field(Integer.parseInt(res[0]),Integer.parseInt(res[1]));
  }

  public MowerCommand nextMowerCommand() throws Exception {
    String line = reader.readLine();
    if (line == null) {
      return null;
    }
    String[] res = line.split(SEPARATOR);
    if(res.length <3)    throw new IllegalArgumentException("can't read mower coordinates. must have x and y and direction");

    Mower mower = Mower.of(Integer.parseInt(res[0]), Integer.parseInt(res[1]),
        Direction.valueOf(res[2].toUpperCase()));

    mower.setId(""+sequence);
    sequence++;
    return new MowerCommand(mower,getCommands(reader.readLine()));
  }

  private Iterator<Command> getCommands(String line) {
    if(line ==null ) line = "";
    OfInt it = line.chars().iterator();

    return new Iterator<Command>() {
      @Override
      public boolean hasNext() {
        return it.hasNext();
      }

      @Override
      public Command next() {
        Integer c = it.next();

        if(c ==null) return null;
        try {
          return Command.valueOf(Character.toString((char) c.intValue()).toUpperCase());
        }
        catch (Exception ex)
        {
          return null;
        }

      }
    };
  }

  public Field getLawn() {
    return lawn;
  }

  public void setLawn(Field lawn) {
    this.lawn = lawn;
  }
}
