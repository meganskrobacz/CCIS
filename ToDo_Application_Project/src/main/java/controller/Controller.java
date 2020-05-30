package assignment9.controller;

import assignment9.model.ToDo;
import assignment9.model.ToDo.Builder;
import assignment9.view.Display;
import assignment9.view.InvalidArgumentException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class that runs the commands in the program provided by the user. */
public class Controller {
  private List<String> commands;
  private List<ToDo> currentToDos;
  private String file;

  /**
   * Constructor to allow the main class to pass in the validated list of commands.
   * @param commands The validated list of commands. */
  public Controller(List<String> commands) {
    this.commands = commands;
    this.currentToDos = new ArrayList<>();
  }

  /**
   * Returns the current to dos in the csv file.
   * @return Returns the current to dos in the csv file. */
  public List<ToDo> getCurrentToDos() {
    return this.currentToDos;
  }

  /**
   * Helper method to allow the runCommands method to read the csv file.
   * @param csvFile The csv file.
   * @throws InvalidArgumentException Throws if it cannot find the file or there is an IO
   * exception. */
  private void readFile(String csvFile) throws InvalidArgumentException {
    file = csvFile;
    Read file = new Read();
    file.readFile(csvFile);
    this.currentToDos = file.getAllToDos();
  }

  /**
   * Helper method to find the csv file in the list of commands to read the file.
   * @throws InvalidArgumentException Throws if it cannot find the file or there is an IO
   * exception. */
  private void findCsvFile() throws InvalidArgumentException {
    for (int i = 0; i < this.commands.size(); i++) {
      if (this.commands.get(i).contains("csv-file")) {
        String[] fileLine = this.commands.get(i).split(" ");
        readFile(fileLine[1]);
      }
    }
  }

  /**
   * Creates a new to do based on the fields found in the command line.
   * @throws InvalidArgumentException Throws if it cannot find the file to update the list or there
   * is an IO exception. */
  private void createNewToDo() throws InvalidArgumentException {
    int iD = (this.currentToDos.size() + 1);
    String text = findToDoText();
    Builder builder = new ToDo.Builder(iD, text);
    builder.complete(findCompleted());
    if (findDueDate() != null)  {
      builder.addDueDate(LocalDate.parse(findDueDate(), DateTimeFormatter.ofPattern("MM/dd/uuuu")));
    }
    if (findPriority() > 0) {
      builder.addPriority(findPriority());
    }
    if (findCategory() != null) {
      builder.addCategory(findCategory());
    }
    ToDo toDo = builder.build();
    this.currentToDos.add(toDo);
    Write.updateCsvFile(this.currentToDos, file);
    readFile(file);
  }

  /**
   * Returns a string of the to do text.
   * @return Returns a string of the to do text. */
  private String findToDoText() {
    int textLimit = 2;
    String[] text = new String[0];
    for (int i = 0; i < this.commands.size(); i++) {
      if (this.commands.get(i).contains("todo-text")) {
        text = this.commands.get(i).split(" ", textLimit);
      }
    }
    return text[1];
  }

  /**
   * Returns the boolean if the to do is completed.
   * @return Returns the boolean if the to do is completed. */
  private boolean findCompleted() {
    boolean flag = false;
    for (int i = 0; i < this.commands.size(); i++) {
      if (this.commands.get(i).contains("completed")) {
        flag = true;
      }
    }
    return flag;
  }

  /**
   * Returns a string of the due date.
   * @return Returns a string of the due date. */
  private String findDueDate() {
    String[] dueLine = new String[0];
    for (int i = 0; i < this.commands.size(); i++) {
      if (this.commands.get(i).contains("due")) {
        dueLine = this.commands.get(i).split(" ");
      }
    }
    return (dueLine.length > 0) ? dueLine[1] : null;
  }

  /**
   * Returns the priority as an integer.
   * @return Returns the priority as an integer. */
  private int findPriority() {
    String[] priorityLine = new String[0];
    for (int i = 0; i < this.commands.size(); i++) {
      if (this.commands.get(i).contains("priority")) {
        priorityLine = this.commands.get(i).split(" ");
      }
    }
    if (priorityLine.length == 0) {
      return 0;
    }
    return (Integer.parseInt(priorityLine[1]));
  }

  /**
   * Returns a string of the category.
   * @return Returns a string of the category. */
  private String findCategory() {
    String[] categoryLine = new String[0];
    for (int i = 0; i < this.commands.size(); i++) {
      if (this.commands.get(i).contains("category")) {
        categoryLine = this.commands.get(i).split(" ");
      }
    }
    if (categoryLine.length == 0) {
      return null;
    }
    return categoryLine[1];
  }

  /**
   * Returns a list of the ids to change to complete.
   * @return Returns a list of the ids to change to complete. */
  private ArrayList<Integer> findCompleteId() {
    String[] completeLine;
    ArrayList<Integer> idsToComplete = new ArrayList<>();
    for (int i = 0; i < this.commands.size(); i++) {
      if (this.commands.get(i).contains("complete-todo")) {
        completeLine = this.commands.get(i).split(" ");
        idsToComplete.add(Integer.parseInt(completeLine[1]));
      }
    }
    return idsToComplete;
  }

  /**
   * Completes the to dos listed in the findCompleteId array and updates the CSV file.
   * @throws InvalidArgumentException Throws if there is an IO exception. */
  private void completeToDo() throws InvalidArgumentException {
    ArrayList<Integer> idsToComplete = findCompleteId();
    for (int i = 0; i < idsToComplete.size(); i++) {
      for (int j = 0; j < this.currentToDos.size(); j++) {
        if (this.currentToDos.get(j).getID() == idsToComplete.get(i)) {
          this.currentToDos.get(j).setCompleted(true);
        }
      }
      Write.updateCsvFile(this.currentToDos, file);
      readFile(file);
    }
  }

  /**
   * Filters the to dos based on the incomplete or category command.
   * @param incomplete Boolean on if incomplete was given in the list of commands.
   * @param category Boolean on if category was given in the list of commands. */
  private void filters(boolean incomplete, boolean category) {
    if (incomplete && category) {
      this.currentToDos = Filters.filterByIncomplete(this.currentToDos);
      if (findCategory() != null) {
        this.currentToDos = Filters.filterByCategory(this.currentToDos, findCategory());
      }
    } else if (incomplete) {
      this.currentToDos = Filters.filterByIncomplete(this.currentToDos);
    } else if ((category) && (findCategory() != null)) {
      this.currentToDos = Filters.filterByCategory(this.currentToDos, findCategory());
    }
  }

  /**
   * Sorts the list based on what commands are given. */
  private void sorts() {
    if (this.commands.contains("sort-by-date")) {
      this.currentToDos.sort(new DateSort());
    } else if (this.commands.contains("sort-by-priority")) {
      this.currentToDos.sort(new PrioritySort());
    }
  }

  /**
   * Returns true if it finds show-incomplete, false otherwise.
   * @return Returns true if it finds show-incomplete, false otherwise. */
  private boolean displayHasIncomplete() {
    for (int i = 0; i < this.commands.size(); i++) {
      if (this.commands.get(i).contains("show-incomplete")) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns true if it finds show-category, false otherwise.
   * @return Returns true if it finds show-category, false otherwise. */
  private boolean displayHasCategory() {
    for (int i = 0; i < this.commands.size(); i++) {
      if (this.commands.get(i).contains("show-category")) {
        return true;
      }
    }
    return false;
  }

  /**
   * Runs all of the commands found in the list provided by the main class.
   * @throws InvalidArgumentException Throws if it cannot find the file to read or there is an
   * IO exception. */
  public void runCommands() throws InvalidArgumentException {
    findCsvFile();
    for (int i = 0; i < this.commands.size(); i++) {
      if (this.commands.get(i).contains("add-todo")) {
        createNewToDo();
      }
      if (this.commands.get(i).contains("complete-todo")) {
        completeToDo();
      }
      if (this.commands.get(i).contains("display")) {
        boolean incomplete = displayHasIncomplete();
        boolean category = displayHasCategory();
        filters(incomplete, category);
        sorts();
        Display show = new Display(this.currentToDos);
        show.displayToDos();
      }
    }
  }

  /**
   * Converts the commands given by the main to a string.
   * @return A string of the commands. */
  @Override
  public String toString() {
    return "Controller{" +
        "commands=" + this.commands +
        '}';
  }
}
