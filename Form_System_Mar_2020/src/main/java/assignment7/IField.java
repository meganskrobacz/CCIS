package assignment7;

/**
 * Public interface representing fields in a form utilized by a client.
 * @param <T> A generic input utilized for the input values of clients.
 */
public interface IField<T> {

    /**
     * Updates the field's value if the input is deemed to be valid via to the given validators.
     * @param input The input a user is trying to enter, typically a String or a Boolean
     * @throws InvalidEntryException If the user's entry is deemed invalid by the validator.
     */
    void updateValue(T input) throws InvalidEntryException;

    /**
     * Determines if the field in question is empty or not. Used to determine if the field is ready
     * for submission. A field is considered filled if under two conditions: first, if the field is
     * required and the input text is deemed valid by the validator; or if the field is not a
     * required field (input does not matter in this case).
     * @return True if the field is filled, false otherwise.
     */
    boolean isFilled();
}
