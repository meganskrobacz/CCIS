package assignment7;

import java.util.Objects;

/**
 * Immutable class representing a FreeText Validator object.
 * @param <T> The String object that will be validated with this validator.
 */
public class FreeText<T extends String> extends AbstractValidator<T> {
    private Integer numCharactersPerLine;
    private Integer numLinesAllowed;

    /**
     * Constructor for a FreeText validator.
     *
     * @param numCharactersPerLine The number of characters per line allowed in the free text field.
     * @param numLinesAllowed      The number of lines permitted in the free text field.
     * @throws InvalidParameterException User inputs a character/line parameter that is less than 1
     */
    public FreeText(Integer numCharactersPerLine, Integer numLinesAllowed) throws
            InvalidParameterException {
        if (!(isValidParameter(numCharactersPerLine) && isValidParameter(numLinesAllowed))) {
            throw new InvalidParameterException();
        }
        this.numCharactersPerLine = numCharactersPerLine;
        this.numLinesAllowed = numLinesAllowed;
    }

    /**
     * Helper function that makes sure a size requirement is not negative or zero.
     * @param parameter The characters per line or the number of lines allowed.
     * @return True if the parameter is valid - otherwise, will return false.
     */
    private boolean isValidParameter(Integer parameter)  {
        return parameter > 0;
    }

    /**
     * Allows a user to see the number of characters permitted per line.
     *
     * @return The number of characters permitted per line of free text.
     */
    public Integer getNumCharactersPerLine() {
        return numCharactersPerLine;
    }

    /**
     * Allows a user to see the number of lines permitted in a field of free text.
     *
     * @return The number of lines permitted in a field of free text.
     */
    public Integer getNumLinesAllowed() {
        return numLinesAllowed;
    }

    /**
     * Checks if the user input is valid for a the field.
     * @param input the user input
     * @return True if the input is valid for the field, false otherwise.
     */
    @Override
    public boolean isValid(T input) {
        return input.length() <= (this.numCharactersPerLine * this.numLinesAllowed);
    }

    /**
     * Determines if two objects are equal.
     * @param o The object being compared to the FreeText object.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FreeText<?> freeText = (FreeText<?>) o;

        if (!Objects.equals(numCharactersPerLine, freeText.numCharactersPerLine))
            return false;
        return Objects.equals(numLinesAllowed, freeText.numLinesAllowed);
    }

    /**
     * Generates a hash code for a FreeText object to assist with collision interference.
     * @return A hash code number, an integer.
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (numCharactersPerLine != null ? numCharactersPerLine.hashCode() : 0);
        result = 31 * result + (numLinesAllowed != null ? numLinesAllowed.hashCode() : 0);
        return result;
    }

    /**
     * Generates a String message for a FreeText object.
     * @return A string representation of a FreeText object.
     */
    @Override
    public String toString() {
        return super.toString() + " FreeText. This field allows for " + this.numLinesAllowed +
                " lines with " + this.numCharactersPerLine + " characters allowed per line.";
    }
}

