package assignment9.view;

import assignment9.model.ToDo;

import java.util.List;

/**
 * Helper class which takes the sorted, filtered list of ToDos passed in from the controller and
 * prints them for the user.
 */
public class Display {
    List<ToDo> toDos;

    /**
     * Constructor for the Display class.
     * @param toDos A list of the ToDos to be printed.
     */
    public Display(List<ToDo> toDos) {
        this.toDos = toDos;
    }

    /**
     * Helper method which displays the ToDos present in the Display object.
     * Helpful for troubleshooting and testing.
     * @return The list of ToDos that will be printed to the user.
     */
    public List<ToDo> getToDosInDisplay() {
        return this.toDos;
    }

    /**
     * Iterates through the list of ToDos and prints them for the user to see.
     */
    public void displayToDos() {
        for (ToDo entry : this.toDos) {
            System.out.println(entry);
        }
    }
}
