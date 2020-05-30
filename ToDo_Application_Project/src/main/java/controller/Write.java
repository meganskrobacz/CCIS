package assignment9.controller;

import assignment9.model.ToDo;
import assignment9.view.InvalidArgumentException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Static write class to update the CSV file whenever a new to do has been created or completed. */
public class Write {

  /**
   * Blank constructor to make it easier to access the static method. */
  public Write() {
  }

  /**
   * Updates the csv file based on what is in the full list of to dos.
   * @param allToDos The updated to dos to use to rewrite the file.
   * @param csvFile The csv file to update.
   * @throws InvalidArgumentException Throws if there is an IO exception. */
  public static void updateCsvFile(List<ToDo> allToDos, String csvFile) throws
      InvalidArgumentException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
      writer.write("\"id\",\"text\",\"completed\",\"due\",\"priority\",\"category\"");
      writer.newLine();
      for (int i = 0; i < allToDos.size(); i ++) {
        writer.write("\"" + allToDos.get(i).getID() + "\",");
        writer.write("\"" + allToDos.get(i).getDescription() + "\",");
        writer.write("\"" + allToDos.get(i).isCompleted() + "\",");
        if (allToDos.get(i).getDueDate() == null) {
          writer.write("\"?\",");
        } else {
        writer.write("\"" +
            allToDos.get(i).getDueDate().format(DateTimeFormatter.ofPattern("MM/dd/uuuu")) + "\",");
        }
        if (allToDos.get(i).getPriority() == null) {
          writer.write("\"?\",");
        } else {
          writer.write("\"" + allToDos.get(i).getPriority() + "\",");
        }
        if (allToDos.get(i).getCategory() == null) {
          writer.write("\"?\"");
        } else {
          writer.write("\"" + allToDos.get(i).getCategory() + "\"");
        }
        writer.newLine();
      }
    } catch (IOException e) {
      throw new InvalidArgumentException("IO exception.");
    }
  }
}
