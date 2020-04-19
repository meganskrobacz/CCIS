package assignment7;

import java.util.Objects;

/**
 * Mutable class representing a field on a form.
 * @param <T> A generic input utilized for the input values of clients.
 */
public class Field<T> implements IField<T> {
    private String label;
    private T value;
    private boolean required;
    private IValidator<T> validator;

    /**
     * Constructor for the Field class. Note that value is set to null upon field creation.
     * @param label The label of a created field.
     * @param required Whether the field is required or not.
     * @param validator The validator type to be implemented for this field.
     */
    public Field(String label, boolean required, IValidator<T> validator) {
        this.label = label;
        this.value = null;
        this.required = required;
        this.validator = validator;
    }

    /**
     * Getter method for the label of a field.
     * @return The label of the field, a String object.
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Allows a label for a field to be changed.
     * @param label The label assigned to the field, a String object.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Allows one to see the value that is currently assigned to the field. By default it is null.
     * @return The value that is captured in the input of the field.
     */
    public T getValue() {
        return this.value;
    }

    /**
     * Updates a field's value if an input is deemed to be valid according to the given validators.
     *
     * @param input What a user is trying to enter, either a String or a Boolean.
     * @throws InvalidEntryException If the user's entry is deemed invalid by the validator.
     */
    @Override
    public void updateValue(T input) throws InvalidEntryException {
        if (this.validator.isValid(input)) {
            this.value = input;
        }
        else {
            throw new InvalidEntryException();
        }
    }

    /**
     * Allows a user to see if the field is required or not.
     * @return True if the field is deemed to be a required field, false otherwise.
     */
    public boolean isRequired() {
        return this.required;
    }

    /**
     * Allows a user to update the requirement mandate for a field.
     * @param required A boolean representing whether the field is deemed mandatory or not.
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * Allows a user to see which validator is assigned to the current field.
     * @return The Validator object assigned to the current field object.
     */
    public IValidator<T> getValidator() {
        return this.validator;
    }

    /**
     * Allows a user to assign a different Validator object to a field.
     * @param validator The Validator object to use for the current field.
     */
    public void setValidator(IValidator<T> validator) {
        this.validator = validator;
    }

    /**
     * Determines if the field in question is empty or not. Used to determine if the field is ready
     * for submission. A field is considered filled if under two conditions: first, if the field is
     * required and the input text is deemed valid by the validator; or if the field is not a
     * required field (input does not matter in this case).
     *
     * @return True if the field is filled, false otherwise.
     */
    @Override
    public boolean isFilled() {
        if (this.value == null) {
            if (this.required) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if two field object are equal.
     * @param o The object being compared to the field in question.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field<?> field = (Field<?>) o;

        if (required != field.required) return false;
        if (!Objects.equals(label, field.label)) return false;
        if (!Objects.equals(value, field.value)) return false;
        return Objects.equals(validator, field.validator);
    }

    /**
     * Generates a hash code for an object to assist with collision interference.
     * @return A hash value.
     */
    @Override
    public int hashCode() {
        int result = label != null ? label.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (required ? 1 : 0);
        result = 31 * result + (validator != null ? validator.hashCode() : 0);
        return result;
    }

    /**
     * Generates a string representation of a Field object.
     * @return A String representation of a Field object.
     */
    @Override
    public String toString() {
        return "This field is called " + this.label + ". It has a current value of " + this.value +
                ". Is it required: " + this.required + ". " + validator;
    }
}
