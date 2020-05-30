package assignment9.controller;

import static org.junit.Assert.*;

import assignment9.view.InvalidArgumentException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ValidateCommandTest {
  String validCSV = "csv-file";
  String cSV = "--csv-file";
  String validToDo = "todo-text";
  String invalidToDo = "to do-text";
  String addToDo = "add-todo";
  String invalidAddToDo = "add cleaning";
  List<String> validList;
  List<String> invalidDuplicates;
  List<String> invalidCommand;
  List<String> addMissText;
  List<String> addMissTextDes;
  List<String> addWithArg;
  List<String> completedArg;
  List<String> dueMissDate;
  List<String> incorrectDueFormat;
  List<String> priorityMissNum;
  List<String> priorityNumFour;
  List<String> priorityFloat;
  List<String> multCompletes;
  List<String> wordCompletes;
  List<String> displayArg;
  List<String> incompleteArg;
  List<String> categoryMiss;
  List<String> bothSorts;
  List<String> sortDateArg;
  List<String> sortPriorityArg;
  List<String> twoCSV;
  List<String> txtNotCSV;

  @Before
  public void setUp() throws Exception {
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
    invalidDuplicates = new ArrayList<>();
    invalidDuplicates.add("csv-file todos.csv");
    invalidDuplicates.add("completed");
    invalidDuplicates.add("add-todo");
    invalidDuplicates.add("priority 3");
    invalidDuplicates.add("todo-text Buying a new dog.");
    invalidDuplicates.add("add-todo");
    invalidDuplicates.add("todo-text clean");
    invalidDuplicates.add("due 03/12/2020");
    invalidDuplicates.add("category home");
    invalidCommand = new ArrayList<>();
    invalidCommand.add("csv-file todos.csv");
    invalidCommand.add("--display");
    addMissText = new ArrayList<>();
    addMissText.add("csv-file todos.csv");
    addMissText.add("add-todo");
    addMissTextDes = new ArrayList<>();
    addMissTextDes.add("csv-file todos.csv");
    addMissTextDes.add("add-todo");
    addMissTextDes.add("todo-text");
    addWithArg = new ArrayList<>();
    addWithArg.add("csv-file todos.csv");
    addWithArg.add("add-todo newToDo");
    addWithArg.add("todo-text clean");
    completedArg = new ArrayList<>();
    completedArg.add("csv-file todos.csv");
    completedArg.add("add-todo");
    completedArg.add("todo-text clean");
    completedArg.add("completed true");
    dueMissDate = new ArrayList<>();
    dueMissDate.add("csv-file todos.csv");
    dueMissDate.add("add-todo");
    dueMissDate.add("todo-text Paint");
    dueMissDate.add("due");
    incorrectDueFormat = new ArrayList<>();
    incorrectDueFormat.add("csv-file todos.csv");
    incorrectDueFormat.add("add-todo");
    incorrectDueFormat.add("todo-text Paint");
    incorrectDueFormat.add("due 3/2/2020");
    priorityMissNum = new ArrayList<>();
    priorityMissNum.add("add-todo");
    priorityMissNum.add("priority");
    priorityMissNum.add("csv-file todos.csv");
    priorityMissNum.add("todo-text Run");
    priorityNumFour = new ArrayList<>();
    priorityNumFour.add("add-todo");
    priorityNumFour.add("priority 4");
    priorityNumFour.add("csv-file todos.csv");
    priorityNumFour.add("todo-text Four");
    priorityFloat = new ArrayList<>();
    priorityFloat.add("add-todo");
    priorityFloat.add("priority two");
    priorityFloat.add("csv-file todos.csv");
    priorityFloat.add("todo-text Float");
    multCompletes = new ArrayList<>();
    multCompletes.add("csv-file todos.csv");
    multCompletes.add("complete-todo 3 5");
    wordCompletes = new ArrayList<>();
    wordCompletes.add("csv-file todos.csv");
    wordCompletes.add("complete-todo three");
    displayArg = new ArrayList<>();
    displayArg.add("csv-file todos.csv");
    displayArg.add("display home");
    incompleteArg = new ArrayList<>();
    incompleteArg.add("csv-file todos.csv");
    incompleteArg.add("display");
    incompleteArg.add("show-incomplete home");
    categoryMiss = new ArrayList<>();
    categoryMiss.add("csv-file todos.csv");
    categoryMiss.add("display");
    categoryMiss.add("show-category");
    bothSorts = new ArrayList<>();
    bothSorts.add("csv-file todos.csv");
    bothSorts.add("display");
    bothSorts.add("sort-by-date");
    bothSorts.add("sort-by-priority");
    sortDateArg = new ArrayList<>();
    sortDateArg.add("csv-file todos.csv");
    sortDateArg.add("display");
    sortDateArg.add("sort-by-date 03/12/2020");
    sortPriorityArg = new ArrayList<>();
    sortPriorityArg.add("csv-file todos.csv");
    sortPriorityArg.add("display");
    sortPriorityArg.add("sort-by-priority 1");
    twoCSV = new ArrayList<>();
    twoCSV.add("csv-file todos.csv todos2.csv");
    txtNotCSV = new ArrayList<>();
    txtNotCSV.add("csv-file todos.txt");
  }

  @Test
  public void validateCommands() throws InvalidArgumentException {
    ValidateCommand.validateCommands(validList);
  }

  @Test (expected = InvalidArgumentException.class)
  public void invalidDuplicates() throws InvalidArgumentException {
    ValidateCommand.validateCommands(invalidDuplicates);
  }

  @Test (expected = InvalidArgumentException.class)
  public void containsDashes() throws InvalidArgumentException {
    ValidateCommand.validateCommands(invalidCommand);
  }
  @Test (expected = InvalidArgumentException.class)
  public void missingTextArg() throws InvalidArgumentException {
    ValidateCommand.validateCommands(addMissText);
  }

  @Test (expected = InvalidArgumentException.class)
  public void missingTextDescription() throws InvalidArgumentException {
    ValidateCommand.validateCommands(addMissTextDes);
  }

  @Test (expected = InvalidArgumentException.class)
  public void addWithArgument() throws InvalidArgumentException {
    ValidateCommand.validateCommands(addWithArg);
  }

  @Test (expected = InvalidArgumentException.class)
  public void completedWithArg() throws InvalidArgumentException {
    ValidateCommand.validateCommands(completedArg);
  }

  @Test (expected = InvalidArgumentException.class)
  public void dateMissing() throws InvalidArgumentException {
    ValidateCommand.validateCommands(dueMissDate);
  }

  @Test (expected = InvalidArgumentException.class)
  public void incorrectDateFormat() throws InvalidArgumentException {
    ValidateCommand.validateCommands(incorrectDueFormat);
  }

  @Test (expected = InvalidArgumentException.class)
  public void priorityMissingNumber() throws InvalidArgumentException {
    ValidateCommand.validateCommands(priorityMissNum);
  }

  @Test (expected = InvalidArgumentException.class)
  public void priorityWithWord() throws InvalidArgumentException {
    ValidateCommand.validateCommands(priorityNumFour);
  }

  @Test (expected = InvalidArgumentException.class)
  public void priorityWithFloat() throws InvalidArgumentException {
    ValidateCommand.validateCommands(priorityFloat);
  }

  @Test (expected = InvalidArgumentException.class)
  public void completeWithMultNum() throws InvalidArgumentException {
    ValidateCommand.validateCommands(multCompletes);
  }

  @Test (expected = InvalidArgumentException.class)
  public void completeWithAWord() throws InvalidArgumentException {
    ValidateCommand.validateCommands(wordCompletes);
  }

  @Test (expected = InvalidArgumentException.class)
  public void displayWithArg() throws InvalidArgumentException {
    ValidateCommand.validateCommands(displayArg);
  }

  @Test (expected = InvalidArgumentException.class)
  public void incompleteWithArg() throws InvalidArgumentException {
    ValidateCommand.validateCommands(incompleteArg);
  }

  @Test (expected = InvalidArgumentException.class)
  public void missingCategoryName() throws InvalidArgumentException {
    ValidateCommand.validateCommands(categoryMiss);
  }

  @Test (expected = InvalidArgumentException.class)
  public void containsBothSorts() throws InvalidArgumentException {
    ValidateCommand.validateCommands(bothSorts);
  }

  @Test (expected = InvalidArgumentException.class)
  public void sortDateWithArg() throws InvalidArgumentException {
    ValidateCommand.validateCommands(sortDateArg);
  }

  @Test (expected = InvalidArgumentException.class)
  public void sortPriorityWithArg() throws InvalidArgumentException {
    ValidateCommand.validateCommands(sortPriorityArg);
  }

  @Test (expected = InvalidArgumentException.class)
  public void inputOfTwoCSV() throws InvalidArgumentException {
    ValidateCommand.validateCommands(twoCSV);
  }

  @Test (expected = InvalidArgumentException.class)
  public void textInsteadOfCSV() throws InvalidArgumentException {
    ValidateCommand.validateCommands(txtNotCSV);
  }

  @Test
  public void validateACommand() {
    assertTrue(ValidateCommand.validateACommand(validCSV));
    assertTrue(ValidateCommand.validateACommand(validToDo));
    assertTrue(ValidateCommand.validateACommand(addToDo));
    assertFalse(ValidateCommand.validateACommand(cSV));
    assertFalse(ValidateCommand.validateACommand(invalidToDo));
    assertFalse(ValidateCommand.validateACommand(invalidAddToDo));
  }

  @Test
  public void containsRequired() {
    assertTrue(ValidateCommand.containsRequired(cSV));
    assertFalse(ValidateCommand.containsRequired(validToDo));
  }
}