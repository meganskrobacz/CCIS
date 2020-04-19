package assignment7;

import java.util.Objects;

/**
 * Mutable class representing a Number Validator object.
 * @param <T> The String object that will be validated with this validator. */
public class Number<T extends String> extends AbstractValidator<T> {
  private Integer numMinimumValues;
  private Integer numMaximumValues;
  private Integer numMaximumDecimalPlaces;

  /**
   * Constructor for the Number class.
   * @param numMinimumValues The minimum value allowed for the number.
   * @param numMaximumValues The maximum value allowed for the number.
   * @param numMaximumDecimalPlaces The maximum allowed of numbers after the decimal place. */
  public Number(Integer numMinimumValues, Integer numMaximumValues,
      Integer numMaximumDecimalPlaces) {
    this.numMinimumValues = numMinimumValues;
    this.numMaximumValues = numMaximumValues;
    this.numMaximumDecimalPlaces = setNumMaximumDecimalPlaces(numMaximumDecimalPlaces);
  }

  /**
   * Returns the minimum value allowed for the number.
   * @return Returns the minimum value allowed. */
  public Integer getNumMinimumValues() {
    return this.numMinimumValues;
  }

  /**
   * Sets the minimum value allowed for the number.
   * @param numMinimumValues The minimum value allowed for the number. */
  public void setNumMinimumValues(Integer numMinimumValues) {
    this.numMinimumValues = numMinimumValues;
  }

  /**
   * Returns the maximum value allowed for the number.
   * @return Returns the maximum value allowed for the number. */
  public Integer getNumMaximumValues() {
    return this.numMaximumValues;
  }

  /**
   * Sets the maximum value allowed for the number.
   * @param numMaximumValues Sets the maximum value allowed for the number. */
  public void setNumMaximumValues(Integer numMaximumValues) {
    this.numMaximumValues = numMaximumValues;
  }

  /**
   * Returns the decimal place of the number.
   * @return Returns the decimal place of the number. */
  public Integer getNumMaximumDecimalPlaces() {
    return this.numMaximumDecimalPlaces;
  }

  /**
   * Sets the decimal place of the number. If it is a negative number, it will be changed to zero.
   * @param numMaximumDecimalPlaces The decimal place of the number.
   * @return The field with the updated value. */
  private Integer setNumMaximumDecimalPlaces(Integer numMaximumDecimalPlaces) {
    if (numMaximumDecimalPlaces < 0) {
      return this.numMaximumDecimalPlaces = 0;
    }
    return this.numMaximumDecimalPlaces = numMaximumDecimalPlaces;
  }

  /**
   * Returns true if the number contains a dollar sign, false otherwise.
   * @param input The input to be checked.
   * @return Returns true if the number contains a dollar sign, false otherwise. */
  private boolean containsDollarSign(T input) {
    return (input.contains("$"));
  }

  /**
   * Returns true if the number contains a comma, false otherwise.
   * @param input The input to be checked.
   * @return Returns true if the number contains a comma, false otherwise. */
  private boolean containsCommas(T input) {
    return (input.contains(","));
  }

  /**
   * Returns true if the number contains a character, false otherwise.
   * @param input The input to be checked.
   * @return Returns true if the number contains a character, false otherwise. */
  private boolean containsCharacters(T input) {
    for (int i=0; i<input.length(); i++) {
      if (input.indexOf('.') == i) {
        continue;
      } else if (!(Character.isDigit(input.charAt(i)))) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns true if the number ends with a period, false otherwise.
   * @param input The input to be checked.
   * @return Returns true if the number ends with a period, false otherwise. */
  private boolean endsWithADecimal(T input) {
    return (input.indexOf(".") == (input.length() - 1));
  }

  /**
   * Returns true if decimal place of the input is greater than the allowed amount, false otherwise.
   * @param input The input to be checked.
   * @return Returns true if decimal place of the input is greater than the allowed amount,
   * false otherwise. */
  private boolean checkDecimalPlaces(T input) {
    int decimalIndex = input.indexOf(".");
    int digitsAfterDecimal = (input.length() - (decimalIndex + 1));
    if (decimalIndex == -1) {
      return false;
    }
    return (digitsAfterDecimal > numMaximumDecimalPlaces);
  }

  /**
   * Returns true if the input is valid, false otherwise.
   * @param input The input to be checked.
   * @return Returns true if the input is valid, false otherwise. */
  @Override
  public boolean isValid (T input) {
    return !(containsDollarSign(input) || containsCommas(input) || endsWithADecimal(input) ||
        checkDecimalPlaces(input) || containsCharacters(input) ||
        (Float.parseFloat(input) < numMinimumValues) ||
        (Float.parseFloat(input) > numMaximumValues));
  }

  /**
   * Returns true if the two objects are the same, false otherwise.
   * @param o The object being compared.
   * @return Returns true if the two objects are the same, false otherwise. */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Number<?> number = (Number<?>) o;
    return Objects.equals(numMinimumValues, number.numMinimumValues) &&
        Objects.equals(numMaximumValues, number.numMaximumValues) &&
        Objects.equals(numMaximumDecimalPlaces, number.numMaximumDecimalPlaces);
  }

  /**
   * Returns true if the hashcode matches the one being compared, false otherwise.
   * @return Returns true if the hashcode matches the one being compared, false otherwise. */
  @Override
  public int hashCode() {
    return Objects.hash(numMinimumValues, numMaximumValues, numMaximumDecimalPlaces);
  }

  /**
   * Returns a string of the number.
   * @return Returns a string of the number. */
  @Override
  public String toString() {
    return super.toString() + "The number requires a minimum value of " + numMinimumValues +
        ", a maximum value of " + numMaximumValues +
        ", and a maximum amount of decimal places of " + numMaximumDecimalPlaces +
        '.';
  }
}
