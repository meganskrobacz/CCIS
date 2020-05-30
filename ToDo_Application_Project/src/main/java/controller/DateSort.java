package assignment9.controller;

import assignment9.model.ToDo;

import java.time.LocalDate;
import java.util.*;

/**
 * Utility class that sorts a list of to-dos by the value date.
 */
public class DateSort implements Comparator<ToDo> {

    /**
     * Constructor for a DateSort object.
     */
    public DateSort() {
    }

    /**
     * Compares two To-do entries and sorts by the due date. Note that if the due date is not set
     * it will default to null so these dates will be considered the "largest" so that they can be
     * sorted at the end.
     * @param left The To-Do entry being compared to the right.
     * @param right The To-Do entry being compared to the left.
     * @return A neg. number if left<right; 0 if left=right; and positive number if left>right
     */
    public int compare(ToDo left, ToDo right) {
        LocalDate leftDate = left.getDueDate();
        LocalDate rightDate = right.getDueDate();
        if (leftDate == null && rightDate == null) {
            return 0;
        } else if (leftDate == null) {
            return 1;
        } else if (rightDate == null) {
            return -1;
        }
        return leftDate.compareTo(rightDate);
    }
}
