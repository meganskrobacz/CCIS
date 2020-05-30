package assignment9.controller;

import assignment9.model.ToDo;
import assignment9.view.InvalidArgumentException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Interface for the methods to read a csvfile. */
public interface IRead {

  /**
   * Returns all of the to dos that are currently in the csvFile.
   * @return Returns all of the to dos that are currently in the csvFile. */
  ArrayList<ToDo> getAllToDos();

  /**
   * Reads the csv file provided and adds each to do to a list.
   * @param csvFile the csv file to be read.
   * @throws InvalidArgumentException Throws if it cannot find the file or there is an IO
   * exception. */
  void readFile(String csvFile) throws InvalidArgumentException;

}
