package assignment7;

/**
 * Exceptions which throws if an entry is deemed to be invalid via a Validator.
 */
public class InvalidEntryException extends Exception {
    public InvalidEntryException() {
        super("Invalid entry; does not meet minimum requirements. Please try again.");
    }
}
