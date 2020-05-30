package assignment9.controller;

import static org.junit.Assert.*;

import assignment9.view.InvalidArgumentException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CommandLineParserTest {
  String[] validCommand;
  String[] missingFileCommand;
  String[] invalidCommand;
  CommandLineParser validParser;
  CommandLineParser missingFileParser;
  CommandLineParser invalidCommandParser;
  List<String> validList;

  @Before
  public void setUp() throws Exception {
    validCommand = new String[]{"--csv-file", "todos.csv", "--completed", "--complete-todo", "3",
        "--add-todo", "--priority", "3", "--todo-text", "Buying", "a", "new", "dog.", "--display",
        "--due", "03/12/2020", "--show-incomplete", "--sort-by-date", "--complete-todo", "4",
        "--category", "home"};
    missingFileCommand = new String[]{"--completed", "todos.csv", "--complete-todo", "3",
        "--add-todo", "--priority", "3", "--todo-text", "Buying", "a", "new", "dog.", "--display",
        "--due", "03/12/2020", "--show-incomplete", "--sort-by-date", "--complete-todo", "4",
        "--category", "home"};
    invalidCommand = new String[]{"--csv-file", "todos.csv", "--completed", "--complete-todo", "3",
        "--add", "--priority", "3", "--todo-text", "Buying", "a", "new", "dog.", "--display",
        "--due", "03/12/2020", "--show-incomplete", "--sort-by-date", "--complete-todo", "4",
        "--category", "home"};
    validParser = new CommandLineParser(validCommand);
    validList = new ArrayList<>();
    validList.add("csv-file todos.csv");
    validList.add("completed");
    validList.add("complete-todo 3");
    validList.add("add-todo");
    validList.add("priority 3");
    validList.add("todo-text Buying a new dog.");
    validList.add("display");
    validList.add("due 03/12/2020");
    validList.add("show-incomplete");
    validList.add("sort-by-date");
    validList.add("complete-todo 4");
    validList.add("category home");
  }

  @Test (expected = InvalidArgumentException.class)
  public void incorrectParserSetUp() throws InvalidArgumentException {
    missingFileParser = new CommandLineParser(missingFileCommand);
    invalidCommandParser = new CommandLineParser(invalidCommand);
  }

  @Test
  public void getCommands() {
    assertEquals(validList, validParser.getCommands());
  }
}