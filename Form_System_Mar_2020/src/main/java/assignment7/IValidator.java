package assignment7;

/**
 * Required operations for a validator.
 */
public interface IValidator<T> {

  /**
   * Checks if the user input is valid for a the field.
   * @param input the user input
   * @return True if the input is valid for the field, false otherwise.
   */
  boolean isValid(T input);

}
