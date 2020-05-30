package assignment9.controller;

import assignment9.model.ToDo;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class which houses any potential filters needed by the program. All methods are static
 * so they can be accessed without instantiating this class.
 */
public class Filters {

    /**
     * Constructor for the Filters class. It is private so that the class can't be instantiated.
     */
    private Filters() {
    }

    /**
     * Method which takes the list of ToDos and only includes the entries that are incomplete.
     * @param listToFilter The list of ToDos being filtered.
     * @return The list containing only entries that have incomplete to-dos.
     */
    public static List<ToDo> filterByIncomplete(List<ToDo> listToFilter) {
        List<ToDo> filteredList = new ArrayList<>();
        for (ToDo entry : listToFilter) {
            if (!(entry.isCompleted())) {
                filteredList.add(entry);
            }
        }
        if (filteredList.size() == 0) {
            System.out.println("No results to display");
        }
        return filteredList;
    }

    /**
     * Method which takes the list of ToDos and only keeps those that match the given category.
     * @param listToFilter The list of ToDos being filtered.
     * @param category The category in question that the user wants to filter by.
     * @return The list containing only entries that match the given category.
     */
    public static List<ToDo> filterByCategory(List<ToDo> listToFilter, String category) {
        List<ToDo> filteredList = new ArrayList<>();
        for (ToDo entry : listToFilter) {
            if (entry.getCategory() != null && entry.getCategory().equals(category)) {
                filteredList.add(entry);
            }
        }
        if (filteredList.size() == 0) {
            System.out.println("No results to display");
        }
        return filteredList;
    }
}
