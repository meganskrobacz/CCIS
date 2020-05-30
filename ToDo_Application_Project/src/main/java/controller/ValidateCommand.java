package assignment9.controller;

import assignment9.view.InvalidArgumentException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ValidateCommand {

  private static final String[] VALID_COMMANDS = {"csv-file", "add-todo", "todo-text", "completed",
  "due", "priority", "category", "complete-todo", "display", "show-incomplete", "show-category",
  "sort-by-date", "sort-by-priority"};
  protected static final String[] REQUIRED_COMMANDS = {"--csv-file"};
  private static final String[] DUPLICATE_ALLOWED = {"complete-todo"};
  private static final String FILE_REGEX = "[\\w\\d-_\\/]*[\\w\\d-_]+\\.csv";
  private static final int COMMAND_ARG_SIZE = 2;

  /**
   * The Validate command class validates the commands.
   */
  private ValidateCommand() {
  }

  /**
   * Validates the commands
   * @param argsList a list of string arguments
   * @throws InvalidArgumentException if the commands are invalid
   */
  public static void validateCommands(List<String> argsList)
      throws InvalidArgumentException {
    HashMap<String, String> commandHashMap = createCommandHashMap(argsList);
    for (String s : argsList) {
      String[] tempList = s.split(" ");
      switch (tempList[0]) {
        case "add-todo":
          validateAddToDo(commandHashMap);
          break;
        case "complete-todo":
          validateCompleteToDo(tempList);
          break;
        case "display":
          validateDisplay(commandHashMap);
          break;
        case "csv-file":
          validateCSV(tempList);
          break;
      }
    }
  }

  /**
   * Parses the command list into a Hash Map, and validates the keys as valid commands.
   * If there are multiple Keys, only stores the first Key/Value pair.
   * @param argsList a list of commands
   * @return a hashmap of commands
   * @throws InvalidArgumentException if the command is invalid
   */
  private static HashMap<String, String> createCommandHashMap(List<String> argsList)
      throws InvalidArgumentException {
    //Create a HashMap. Key is the Command, Value is any argument(s)
    //If there are multiple Keys, only stores the first Key/Value pair.
    HashMap<String, String> tempCommands = new HashMap<>();
    for (String s : argsList) {
      //Split strings. First arg is the command.
      String[] tempList = s.split(" ", COMMAND_ARG_SIZE);
      if (!validateACommand(tempList[0])) {
        throw new InvalidArgumentException("An invalid command was given.");
      }
      //If the current Command argument is Not already in the HashMap, add it to the HashMap
      //as a Key, and a string argument (if any) for the value.
      if (!tempCommands.containsKey(tempList[0])) {
        String tempArg;
        if (tempList.length == COMMAND_ARG_SIZE) {
          tempArg = tempList[1];
        } else {
          tempArg = null;
        }
        tempCommands.put(tempList[0], tempArg);
      } else { //If the current Command argument is already in the HashMap
        //Validate that duplicate commands are allowed
        if (!validateDuplicatesAllowed(tempList[0])) {
          throw new InvalidArgumentException("Duplicate commands are only allowed for" + Arrays
              .toString(DUPLICATE_ALLOWED));
        }
      }
    }
    return tempCommands;
  }

  /**
   * Checks the string contains a valid command
   * @param arg a string of commands
   * @return true if the string contains a valid command, false otherwise.
   */
  protected static boolean validateACommand(String arg) {
    return Arrays.asList(VALID_COMMANDS).contains(arg);
  }

  /**
   * Checks the string contains any required commands
   * @param arg a string of commands
   * @return true if the string contains the required commands, false otherwise.
   */
  protected static boolean containsRequired(String arg) {
    for (String reqArg : REQUIRED_COMMANDS) {
      if (!arg.contains(reqArg)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Validates that duplicate commands are allowed.
   * @param arg a string command
   * @return true if duplicate commands are allowed, false otherwise.
   */
  private static boolean validateDuplicatesAllowed(String arg) {
    return Arrays.asList(DUPLICATE_ALLOWED).contains(arg);
  }

  /**
   * Validates the "add-todo" command.
   * @param commandHashMap a hashmap of commands
   * @throws InvalidArgumentException if the command is invalid.
   */
  private static void validateAddToDo(HashMap<String, String> commandHashMap)
      throws InvalidArgumentException {
    String addToDo = "add-todo";
    if (commandHashMap.get(addToDo) != null) {
      throw new InvalidArgumentException("--add-todo does not take an argument, and must be "
          + "provided with --todo-text.");
    }
    validateToDoText(commandHashMap);
    validateCompleted(commandHashMap);
    validateDueDate(commandHashMap);
    validatePriority(commandHashMap);
  }

  /**
   * Validates the "todo-text" command.
   * @param commandHashMap a hashmap of commands
   * @throws InvalidArgumentException if the command is invalid.
   */
  private static void validateToDoText (HashMap<String, String> commandHashMap)
      throws InvalidArgumentException {
    String todoText = "todo-text";
    if (!commandHashMap.containsKey(todoText)) {
      throw new InvalidArgumentException ("--add-todo must also have --todo-text"
          + "<description of todo>");
    }
    if (commandHashMap.get(todoText) == null) {
      throw new InvalidArgumentException ("--add-todo must also have --todo-text"
          + "<description of todo>");
    }
  }

  /**
   * Validates the "completed" command.
   * @param commandHashMap a hashmap of commands.
   * @throws InvalidArgumentException if the command is invalid.
   */
  private static void validateCompleted (HashMap<String, String> commandHashMap)
      throws InvalidArgumentException {
    String completed = "completed";
    if (commandHashMap.containsKey(completed)) {
      if (commandHashMap.get(completed) != null) {
        throw new InvalidArgumentException("--completed does not take an argument.");
      }
    }
  }

  /**
   * Validates the "due" command.
   * @param commandHashMap a hashmap of commands.
   * @throws InvalidArgumentException if the command is invalid.
   */
  private static void validateDueDate(HashMap<String, String> commandHashMap)
      throws InvalidArgumentException {
    String dueDate = "due";
    if (commandHashMap.containsKey(dueDate)) {
      if (commandHashMap.get(dueDate) == null) {
        throw new InvalidArgumentException("--due must also have <due date> in the format"
            + "YYYY-MM-DD");
      }
      try {
        LocalDate.parse(commandHashMap.get(dueDate), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
      }
      catch (DateTimeParseException e) {
        throw new InvalidArgumentException("<due date> must be in the format MM/dd/yyyy");
      }
    }
  }

  /**
   * Validates the "priority" command.
   * @param commandHashMap a hashmap of commands.
   * @throws InvalidArgumentException if the command is invalid.
   */
  private static void validatePriority(HashMap<String, String> commandHashMap)
      throws InvalidArgumentException {
    String priority = "priority";
    int[] priorityNum = {1, 2, 3};
    if (commandHashMap.containsKey(priority)) {
      if (commandHashMap.get(priority) == null) {
        throw new InvalidArgumentException("--priority must also have a priority of <1, 2, or 3>.");
      }
      try {
        int priorityInt = Integer.parseInt(commandHashMap.get(priority));
        if (priorityInt != priorityNum[0] && priorityInt != priorityNum[1] &&
            priorityInt != priorityNum[2]) {
          throw new InvalidArgumentException("Priority must be <1, 2, or 3>.");
        }
      }
      catch (NumberFormatException e) {
        throw new InvalidArgumentException("Priority must be <1, 2, or 3>.");
      }
    }
  }

  /**
   * Validates the "complete-todo" command.
   * @param tempList a string array of commands.
   * @throws InvalidArgumentException if the command is invalid.
   */
  private static void validateCompleteToDo(String[] tempList)
      throws InvalidArgumentException {
    if (tempList.length != COMMAND_ARG_SIZE) {
      throw new InvalidArgumentException("Enter only one <ID> per --complete-todo.");
    }
    try {
      Integer.parseInt(tempList[1]);
    }
    catch (NumberFormatException e)
    {
      throw new InvalidArgumentException("The <ID> must be numbers only.");
    }
  }

  /**
   * Validates the "display" command.
   * @param commandHashMap a hashmap of commands.
   * @throws InvalidArgumentException if the command is invalid.
   */
  private static void validateDisplay(HashMap<String, String> commandHashMap)
      throws InvalidArgumentException {
    String display = "display";
    if (commandHashMap.get(display) != null) {
      throw new InvalidArgumentException("--display does not take an argument, and may be "
          + "provided with --show-incomplete or --show-category <category>, or --sort-by-date "
          + "or --sort-by-priority.");
    }
    validateShow(commandHashMap);
    validateSort(commandHashMap);
  }

  /**
   * Validates the "show-incomplete" and "show-category" commands.
   * @param commandHashMap a hashmap of commands.
   * @throws InvalidArgumentException if the command is invalid.
   */
  private static void validateShow(HashMap<String, String> commandHashMap)
    throws InvalidArgumentException {
    String showIncomplete = "show-incomplete";
    String showCategory = "show-category";
    if (commandHashMap.containsKey(showIncomplete)) {
      if (commandHashMap.get(showIncomplete) != null) {
        throw new InvalidArgumentException("--show-incomplete does not take an argument.");
      }
    }
    if (commandHashMap.containsKey(showCategory)) {
      if (commandHashMap.get(showCategory) == null) {
        throw new InvalidArgumentException("--show-category must also provide a <category>.");
      }
    }
  }

  /**
   * Validates the "sort-by-date" and "sort-by-priority" commands.
   * @param commandHashMap a hashmap of commands.
   * @throws InvalidArgumentException if the command is invalid.
   */
  private static void validateSort(HashMap<String, String> commandHashMap)
      throws InvalidArgumentException {
    String sortDate = "sort-by-date";
    String sortPriority = "sort-by-priority";
    if (commandHashMap.containsKey(sortDate) && commandHashMap.containsKey(sortPriority)) {
      throw new InvalidArgumentException("--sort-by-date cannot be combined with "
          + "--sort-by-priority.");
    }
    if (commandHashMap.containsKey(sortDate)) {
      if (commandHashMap.get(sortDate) != null) {
        throw new InvalidArgumentException("--sort-by-date does not take an argument.");
      }
    }
    if (commandHashMap.containsKey(sortPriority)) {
      if (commandHashMap.get(sortPriority) != null) {
        throw new InvalidArgumentException("--sort-by-priority does not take an argument.");
      }
    }
  }

  /**
   * Validates the "csv-file" command.
   * @param tempList a string array of commands
   * @throws InvalidArgumentException if the command is invalid.
   */
  private static void validateCSV(String[] tempList)  throws InvalidArgumentException {
    if (tempList.length != COMMAND_ARG_SIZE) {
      throw new InvalidArgumentException("Enter only one <path/to/file.csv> for --csv-file.");
    }
    if (!tempList[1].matches(FILE_REGEX)) {
      throw new InvalidArgumentException("File path must be in the format path/to/file.csv.");
    }
  }
}
