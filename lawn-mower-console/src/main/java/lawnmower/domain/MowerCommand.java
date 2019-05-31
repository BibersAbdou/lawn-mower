package lawnmower.domain;

import java.util.Iterator;

public class MowerCommand {

  private Mower mower ;

  private Iterator<Command> commandIterator ;

  public MowerCommand(Mower mower ,
      Iterator<Command> commandIterator) {

    this.mower =mower;
    this.commandIterator = commandIterator;
  }


  public Mower getMower() {
    return mower;
  }

  public void setMower(Mower mower) {
    this.mower = mower;
  }

  public Iterator<Command> getCommandIterator() {
    return commandIterator;
  }

  public void setCommandIterator(
      Iterator<Command> commandIterator) {
    this.commandIterator = commandIterator;
  }
}
