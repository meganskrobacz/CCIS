package assignment9.controller;

import assignment9.view.InvalidArgumentException;

/**
 * Main class which takes in the user input, allows CommandLineParser to parse and validate it
 * and then pass it to the Controller class to run all commands found. */
public class Main {

  /**
   * Function which takes input and passes it to the commandlineparser for validation.
   * @param args The arguments passed in by the user.
   * @throws InvalidArgumentException Thrown if any issues arise in validation or when the
   * controller runs the commands. */
  public static void main(String[] args) throws InvalidArgumentException {
    CommandLineParser parser = new CommandLineParser(args);
    Controller controller = new Controller(parser.getCommands());
    controller.runCommands();
  }
}
