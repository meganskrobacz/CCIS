package assignment9.view;

//From lecture 9 code
/**
 * Exception thrown if invalid arguments are provided at the command line. */
public class InvalidArgumentException extends Exception {
  public InvalidArgumentException(String message) {
    super(message);
  }
}
