package assignment7;

/**
 * Class representing a validator that handles a checkbox.
 * @param <T> The Boolean object passed into the validator.
 */
public class CheckBox<T extends Boolean> extends AbstractValidator<T> {

    /**
     * Constructor for a CheckBox object.
     */
    public CheckBox() {
    }

    /**
     * Checks if the user input is valid for a the field.
     * @param input the user input
     * @return True if the input is valid for the field, false otherwise.
     */
    @Override
    public boolean isValid(T input) {
        return true;
    }

    /**
     * Generates a String representation of a Checkbox object.
     * @return A Checkbox object represented in String format.
     */
    @Override
    public String toString() {
        return super.toString() + " Checkbox.";
    }
}
