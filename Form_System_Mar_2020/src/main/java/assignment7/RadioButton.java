package assignment7;

/**
 * Mutable class representing a Radio Button Validator object.
 */
public class RadioButton<T extends Boolean> extends AbstractValidator<T> {

  /**
   * Creates a RadioButton Validator.
   */
  public RadioButton() {
  }

  /**
   * Checks if the user input is valid for a the field.
   * @param input the user input
   * @return True if the input is valid for the field, false otherwise.
   */
  @Override
  public boolean isValid(T input) {
    return input != null;
  }

  /**
   * Generates a string representation of a Radio Button object.
   * @return A String representation of a Radio Button object.
   */
  @Override
  public String toString() {
    return super.toString() + "Radio Button.";
  }
}