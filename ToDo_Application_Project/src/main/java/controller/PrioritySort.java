package assignment9.controller;

import assignment9.model.ToDo;

import java.util.*;

/**
 * Utility class that sorts a list of to-dos by the value priority.
 */
public class PrioritySort implements Comparator<ToDo> {

    /**
     * Constructor for a PrioritySort object.
     */
    public PrioritySort() {
    }

    /**
     * Compares two To-Do entries and sorts by the priority. Note that if a priority isn't set,
     * it would be 0, which is being considered "greater" than all other priorities so that the
     * order will be 0, 3, 2, 1. When returning a sorted list, this order will be reversed.
     * @param left The To-Do entry being compared to the right.
     * @param right The To-Do entry being compared to the left.
     * @return A neg. number if left<right; 0 if left=right; and positive number if left>right
     */
    public int compare(ToDo left, ToDo right) {
        Integer leftValue = left.getPriority();
        Integer rightValue = right.getPriority();
        if (leftValue == null && rightValue == null) {
            return 0;
        } else if (leftValue == null) {
            return 1;
        } else if (rightValue == null) {
            return -1;
        }
        return Integer.compare(leftValue, rightValue);
    }
}
