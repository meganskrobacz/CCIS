package assignment9.controller;

import assignment9.view.InvalidArgumentException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Parses and validates the command line arguments passed in from Main
 */
public class CommandLineParser {

  // Just parsing the arguments into a list.

  private static final String REGEX_COMMAND = "--";
  private List<String> commands;

  /**
   * Constructor for the CommandLineParser class
   * @param args The command line arguments provided by the user.
   * @throws InvalidArgumentException If the arguments are invalid.
   */
  public CommandLineParser(String[] args) throws InvalidArgumentException {
    this.commands = parseCommands(args);
  }

  /**
   * Gets the commands as a list of strings
   */
  public List<String> getCommands() {
    return this.commands;
  }

  /**
   * Converts a string array into a single string delimited by " "
   * @param args a string array
   * @return a string
   */
  private static String convertToString(String[] args) {
    return String.join(" ", args);
  }

  /**
   * Converts a string into a list of strings per the Regex Command
   * @param args a string
   * @return a list of strings
   */
  private static List<String> convertToList(String args) {
    String[] temp = args.split(REGEX_COMMAND);
    //Because we're splitting by "--" the first arg[0] will be empty
    ArrayList<String> argList = new ArrayList<>(Arrays.asList(temp).subList(1, temp.length));
    for (int i = 0; i<argList.size(); i++) {
      argList.set(i, argList.get(i).trim());
    }
    return argList;
  }

  /**
   * Parses the string array of arguments, and calls the validate command.
   * @param args a string array of commands
   * @return a list of parsed and validated commands
   * @throws InvalidArgumentException if the commands are invalid
   */
  private List<String> parseCommands(String[] args)
      throws InvalidArgumentException {
    String argsAsString = convertToString(args);
    if (!ValidateCommand.containsRequired(argsAsString)) {
      throw new InvalidArgumentException("Input must contain required commands: " + Arrays
          .toString(ValidateCommand.REQUIRED_COMMANDS));
    }
    List<String> commandList = convertToList(argsAsString);
    ValidateCommand.validateCommands(commandList);
  return commandList;
  }


}
