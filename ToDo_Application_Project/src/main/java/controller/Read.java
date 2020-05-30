package assignment9.controller;

import assignment9.model.ToDo;
import assignment9.model.ToDo.Builder;
import assignment9.view.InvalidArgumentException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * The Read class will read the csv file provided and add each to do to a list. */
public class Read implements IRead {
  private ArrayList<ToDo> getAllToDos;

  /**
   * Constructor for the read class. */
  public Read() {
    this.getAllToDos = new ArrayList<>();
  }

  /**
   * Returns all of the to dos that are currently in the csvFile.
   * @return Returns all of the to dos that are currently in the csvFile. */
  public ArrayList<ToDo> getAllToDos() {
    return this.getAllToDos;
  }

  /**
   * Helper method to check if the to do has a field or not.
   * @param field Each field for the to do.
   * @return False if it contains a "?", true otherwise. */
  private boolean hasField(String field) {
    if (field.contains("?")) {
      return false;
    }
    return true;
  }

  /**
   * Reads the csv file provided and adds each to do to a list.
   * @param csvFile the csv file to be read.
   * @throws InvalidArgumentException Throws if it cannot find the file or there is an IO
   * exception. */
  public void readFile(String csvFile) throws InvalidArgumentException {
    try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if ((line.contains("id"))) {
          continue;
        }
        String[] fullLine = line.split("\",\"");
        for (int i = 0; i < fullLine.length; i++) {
          fullLine[i] = fullLine[i].replace("\"", "");
        }
        Builder builder = new ToDo.Builder(Integer.parseInt(fullLine[0]), fullLine[1]);
        if (hasField(fullLine[2])) {
          builder.complete(Boolean.parseBoolean(fullLine[2]));
        }
        if (hasField(fullLine[3])) {
          builder.addDueDate(LocalDate.parse(fullLine[3],
              DateTimeFormatter.ofPattern("MM/dd/uuuu")));
        }
        if (hasField(fullLine[4])) {
          builder.addPriority(Integer.parseInt(fullLine[4]));
        }
        if (hasField(fullLine[5])) {
          builder.addCategory(fullLine[5]);
        }
        ToDo toDo = builder.build();
        this.getAllToDos.add(toDo);
      }
    } catch (FileNotFoundException e) {
      throw new InvalidArgumentException("File was not found.");
    } catch (IOException e) {
      throw new InvalidArgumentException("IO exception.");
    }
  }
}
