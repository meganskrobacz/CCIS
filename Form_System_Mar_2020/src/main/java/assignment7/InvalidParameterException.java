package assignment7;

/**
 * Exception which throws if a user inputs a zero or negative number when setting parameters.
 */
public class InvalidParameterException extends Exception {
    public InvalidParameterException() {
        super("Invalid entry; cannot have a line or character limit less than 1");
    }
}
