package assignment7;

import java.util.Objects;


/**
 * Mutable class representing a Password Validator object.
 */
public class Password<T extends String> extends AbstractValidator<T>{

  private Integer minPasswordLength;
  private Integer maxPasswordLength;
  private Integer minLowerCase;
  private Integer minUpperCase;
  private Integer minDigits;


  /**
   * Creates a Password Validator with minimum and maximum password length requirements.
   * @param minPasswordLength the minimum password length (inclusive).
   * @param maxPasswordLength the maximum password length (inclusive).
   */
  public Password(Integer minPasswordLength, Integer maxPasswordLength) {
    this.minPasswordLength = minPasswordLength;
    this.maxPasswordLength = maxPasswordLength;
    this.minLowerCase = 0;
    this.minUpperCase = 0;
    this.minDigits = 0;
  }

  /**
   * Creates a Password Validator with minimum and maximum password length,
   * and minimum upper case, lower case, and digit requirements.
   * @param minPasswordLength the minimum password length (inclusive).
   * @param maxPasswordLength the maximum password length (inclusive).
   * @param minLowerCase the minimum number of lower case characters (inclusive).
   * @param minUpperCase the minimum number of upper case characters (inclusive).
   * @param minDigits the minimum number of digit characters (inclusive).
   */
  public Password(Integer minPasswordLength, Integer maxPasswordLength, Integer minLowerCase,
      Integer minUpperCase, Integer minDigits) {
    this.minPasswordLength = minPasswordLength;
    this.maxPasswordLength = maxPasswordLength;
    this.minLowerCase = minLowerCase;
    this.minUpperCase = minUpperCase;
    this.minDigits = minDigits;
  }

  /**
   * Checks if the input contains a space character
   * @param input the user input
   * @return True if the input contains a space character, false otherwise.
   */
  private boolean containsSpace(T input) {
    return input.contains(" ");
  }

  /**
   * Checks if the input is within the bounds of the minimum and maximum length requirements.
   * @param input the user input
   * @return True if the input is within length bounds, false otherwise.
   */
  private boolean checkPasswordLength(T input) {
    return input.length() >= this.minPasswordLength && input.length() <= this.maxPasswordLength;
  }


  /**
   * Checks if the input contains at least the minimum number of required characters.
   * @param input the user input
   * @return True if the input meets minimum character requirements, false otherwise.
   */
  private boolean containsMinCharacters(T input) {
    if (this.minLowerCase != 0) {
      if (!this.containsMinLowerCase(input)) {return false;}
    }
    if (this.minUpperCase != 0) {
      if (!this.containsMinUpperCase(input)) {return false;}
    }
    if (this.minDigits != 0) {
      if (!this.containsMinDigits(input)) {return false;}
    }
    return true;
  }

  /**
   * Checks if the input contains at least the minimum number of lower case characters.
   * @param input the user input
   * @return True if the input meets minimum character requirements, false otherwise.
   */
  private boolean containsMinLowerCase(T input) {
    Integer numLowerCase = 0;
    for (int i = 0; i<input.length(); i++) {
      if (Character.isLowerCase(input.charAt(i))){
        numLowerCase += 1;
      }
    }
    return numLowerCase >= this.minLowerCase;
  }

  /**
   * Checks if the input contains at least the minimum number of upper case characters.
   * @param input the user input
   * @return True if the input meets minimum character requirements, false otherwise.
   */
  private boolean containsMinUpperCase(T input) {
    Integer numUpperCase = 0;
    for (int i = 0; i<input.length(); i++) {
      if (Character.isUpperCase(input.charAt(i))){
        numUpperCase += 1;
      }
    }
    return numUpperCase >= this.minUpperCase;
  }

  /**
   * Checks if the input contains at least the minimum number of digit characters.
   * @param input the user input
   * @return True if the input meets minimum character requirements, false otherwise.
   */
  private boolean containsMinDigits(T input) {
    Integer numDigits = 0;
    for (int i = 0; i<input.length(); i++) {
      if (Character.isDigit(input.charAt(i))){
        numDigits += 1;
      }
    }
    return numDigits >= this.minDigits;
  }

  /**
   * Checks if the user input is valid for a the field.
   * @param input the user input
   * @return True if the input is valid for the field, false otherwise.
   */
  @Override
  public boolean isValid(T input) {
    return (!this.containsSpace(input)) && this.checkPasswordLength(input) &&
        this.containsMinCharacters(input);
  }

  /**
   * Determines if two Password object are equal.
   * @param o The object being compared to the Password object in question.
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
    Password<?> password = (Password<?>) o;
    return minPasswordLength.equals(password.minPasswordLength) &&
        maxPasswordLength.equals(password.maxPasswordLength) &&
        minLowerCase.equals(password.minLowerCase) &&
        minUpperCase.equals(password.minUpperCase) &&
        minDigits.equals(password.minDigits);
  }

  /**
   * Generates a hash code for an object to assist with collision interference.
   * @return A hash value.
   */
  @Override
  public int hashCode() {
    return Objects
        .hash(super.hashCode(), minPasswordLength, maxPasswordLength, minLowerCase, minUpperCase,
            minDigits);
  }

  /**
   * Generates a string representation of a Password object.
   * @return A String representation of a Password object.
   */
  @Override
  public String toString() {
    return super.toString() + "Password." +
        "\nPassword requirements are: " +
        "\nMinimum Password Length: " + minPasswordLength +
        "\nMaximum Password Length: " + maxPasswordLength +
        "\nMinimum Number of Lower Case Characters: " + minLowerCase +
        "\nMinimum Number of Upper Case Characters: " + minUpperCase +
        "\nMinimum Number of Digit Characters: " + minDigits;
  }
}
