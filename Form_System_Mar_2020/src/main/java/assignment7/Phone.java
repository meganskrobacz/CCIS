package assignment7;

import java.util.Objects;

/**2
 * Mutable class representing a assignment7.Phone assignment7.Number Validator object.
 */
public class Phone<T extends String> extends AbstractValidator<T> {

  private Integer length;

  /**
   * Creates a Phone Number Validator with a phone number length requirement.
   * @param length the phone number length.
   */
  public Phone(Integer length) {
    this.length = length;
  }

  /**
   * Checks if the input is all digits
   * @param input the user input
   * @return True if the input is all digits, false otherwise.
   */
  private boolean checkIsDigit(T input) {
    for (int i = 0; i<input.length(); i++) {
      if (!Character.isDigit(input.charAt(i))){
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if the user input is valid for a the field.
   * @param input the user input
   * @return True if the input is valid for the field, false otherwise.
   */
  @Override
  public boolean isValid(T input) {
    return input.length() == this.length && this.checkIsDigit(input);
  }

  /**
   * Determines if two Phone object are equal.
   * @param o The object being compared to the Phone object in question.
   * @return True if the objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Phone<?> phone = (Phone<?>) o;
    return Objects.equals(this.length, phone.length);
  }

  /**
   * Generates a hash code for an object to assist with collision interference.
   * @return A hash value.
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), this.length);
  }

  /**
   * Generates a string representation of a Phone object.
   * @return A String representation of a Phone object.
   */
  @Override
  public String toString() {
    return super.toString() + "Phone.\nPhone number required length is: " + this.length;
  }

}