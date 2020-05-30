package assignment9.controller;

import assignment9.model.ToDo;
import assignment9.view.InvalidArgumentException;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ControllerTest {
    String addCommand = "add-todo";
    String textCommand5 = "todo-text Buy yarn for blanket, stuffed toy";
    String completedCommand = "completed";
    String dueCommand5 = "due 03/12/2020";
    String priority5 = "priority 3";
    String category5 = "category home";
    String complete3 = "complete-todo 3";
    String displayCommands = "display";
    String showIncomplete = "show-incomplete";
    String showSchool = "show-category school";
    String sortDate = "sort-by-date";
    String sortPriority = "sort-by-priority";
    List<String> addToDoAndUpdate = new ArrayList<>();
    List<String> allFilters = new ArrayList<>();
    List<String> incompleteFilterOnly = new ArrayList<>();
    List<String> categoryFilterOnly = new ArrayList<>();
    List<String> sortDateOnly = new ArrayList<>();
    List<String> priorityOnly = new ArrayList<>();
    String oneDescription;
    String twoDescription;
    String threeDescription;
    String fourDescription;
    LocalDate date1;
    LocalDate date2;
    LocalDate date4;
    LocalDate date5;
    String category1and3;
    String category4;
    ToDo.Builder build1;
    ToDo.Builder build2;
    ToDo.Builder build3;
    ToDo.Builder build4;
    ToDo one;
    ToDo two;
    ToDo three;
    ToDo four;
    ToDo five;
    List<ToDo> originalToDos = new ArrayList<>();
    Read read1 = new Read();
    Read read2 = new Read();
    Controller controllerSortPriority;
    List<ToDo> priorityResults = new ArrayList<>();
    Controller controllerAddAndUpdate;
    Controller controllerFilter;
    List<ToDo> filterResults = new ArrayList<>();
    Controller controllerSortDate;
    List<ToDo> sortResults = new ArrayList<>();
    Controller controllerIncompleteFilter;
    List<ToDo> incompleteFilterResults = new ArrayList<>();
    Controller controllerCategoryFilter;
    List<ToDo> categoryFilterResults = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        oneDescription = "Finish HW9";
        twoDescription = "Mail Passport";
        threeDescription = "Study for finals";
        fourDescription = "Clean the house";

        date1 = LocalDate.parse("2020-03-22");
        date2 = LocalDate.parse("2020-02-28");
        date4 = LocalDate.parse("2020-03-21");
        date5 = LocalDate.parse("2020-03-12");

        category1and3 = "school";
        category4 = "home";

        build1 = new ToDo.Builder(1, oneDescription);
        build1.complete(true);
        build1.addDueDate(date1);
        build1.addPriority(1);
        build1.addCategory(category1and3);
        one = build1.build();

        build2 = new ToDo.Builder(2, twoDescription);
        build2.complete(true);
        build2.addDueDate(date2);
        two = build2.build();

        build3 = new ToDo.Builder(3, threeDescription);
        build3.complete(false);
        build3.addPriority(2);
        build3.addCategory(category1and3);
        three = build3.build();

        build4 = new ToDo.Builder(4, fourDescription);
        build4.complete(false);
        build4.addDueDate(date4);
        build4.addPriority(3);
        build4.addCategory(category4);
        four = build4.build();

        originalToDos.add(one);
        originalToDos.add(two);
        originalToDos.add(three);
        originalToDos.add(four);

        five = new ToDo.Builder(5, "Buy yarn for blanket, stuffed toy")
                .complete(true)
                .addPriority(3)
                .addDueDate(date5)
                .addCategory("home").build();

        priorityOnly.add(displayCommands);
        priorityOnly.add(sortPriority);

        priorityResults.add(one);
        priorityResults.add(three);
        priorityResults.add(four);
        priorityResults.add(two);

        addToDoAndUpdate.add(addCommand);
        addToDoAndUpdate.add(textCommand5);
        addToDoAndUpdate.add(completedCommand);
        addToDoAndUpdate.add(dueCommand5);
        addToDoAndUpdate.add(priority5);
        addToDoAndUpdate.add(category5);
        addToDoAndUpdate.add(complete3);

        allFilters.add(displayCommands);
        allFilters.add(showIncomplete);
        allFilters.add(showSchool);

        filterResults.add(three);

        incompleteFilterOnly.add(displayCommands);
        incompleteFilterOnly.add(showIncomplete);

        incompleteFilterResults.add(three);
        incompleteFilterResults.add(four);

        categoryFilterOnly.add(displayCommands);
        categoryFilterOnly.add(showSchool);

        categoryFilterResults.add(one);
        categoryFilterResults.add(three);

        sortDateOnly.add(displayCommands);
        sortDateOnly.add(sortDate);

        sortResults.add(two);
        sortResults.add(four);
        sortResults.add(one);
        sortResults.add(three);
    }

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testGetToDos() throws InvalidArgumentException, IOException {
        final File tempFile1 = tempFolder.newFile("testGetToDos.txt");
        Write.updateCsvFile(originalToDos, tempFile1.getAbsolutePath());
        allFilters.add("csv-file " + tempFile1.getAbsolutePath());
        controllerFilter = new Controller(allFilters);
        controllerFilter.runCommands();
        assertEquals(filterResults, controllerFilter.getCurrentToDos());
    }

    @Test
    public void testDisplaySorted() throws InvalidArgumentException, IOException {
        final File tempFile2 = tempFolder.newFile("testDisplaySorted.txt");
        Write.updateCsvFile(originalToDos, tempFile2.getAbsolutePath());
        sortDateOnly.add("csv-file " + tempFile2.getAbsolutePath());
        controllerSortDate = new Controller(sortDateOnly);
        controllerSortDate.runCommands();
        assertEquals(sortResults, controllerSortDate.getCurrentToDos());
    }

    @Test
    public void testDisplaySortPriority() throws InvalidArgumentException, IOException {
        final File tempFile3 = tempFolder.newFile("testDisplayPriority.txt");
        Write.updateCsvFile(originalToDos, tempFile3.getAbsolutePath());
        priorityOnly.add("csv-file " + tempFile3.getAbsolutePath());
        controllerSortPriority = new Controller(priorityOnly);
        controllerSortPriority.runCommands();
        assertEquals(priorityResults, controllerSortPriority.getCurrentToDos());
    }

    @Test
    public void testAddToDoAndUpdate() throws InvalidArgumentException, IOException {
        // Set up the temporary file for writing
        final File tempFile4 = tempFolder.newFile("tempFile.txt");
        Write.updateCsvFile(originalToDos, tempFile4.getAbsolutePath());

        // Read the file
        read1.readFile(tempFile4.getAbsolutePath());
        assertFalse(read1.getAllToDos().get(2).isCompleted());
        assertEquals(4, read1.getAllToDos().size());

        // runCommands
        addToDoAndUpdate.add("csv-file " + tempFile4.getAbsolutePath());
        controllerAddAndUpdate = new Controller(addToDoAndUpdate);
        controllerAddAndUpdate.runCommands();
        assertEquals(5, controllerAddAndUpdate.getCurrentToDos().size());

        // Read the file again with a different Read object
        read2.readFile(tempFile4.getAbsolutePath());
        assertEquals(5, read2.getAllToDos().size());
        assertTrue(read2.getAllToDos().get(2).isCompleted());
    }

    @Test
    public void testDisplayFiltered() throws InvalidArgumentException, IOException {
        final File tempFile5 = tempFolder.newFile("testFilter.txt");
        Write.updateCsvFile(originalToDos, tempFile5.getAbsolutePath());
        allFilters.add("csv-file " + tempFile5.getAbsolutePath());
        controllerFilter = new Controller(allFilters);
        controllerFilter.runCommands();
        assertEquals(filterResults, controllerFilter.getCurrentToDos());
    }

    @Test
    public void testDisplayIncompleteFilter() throws InvalidArgumentException, IOException {
        final File tempFile6 = tempFolder.newFile("testIncomplete.txt");
        Write.updateCsvFile(originalToDos, tempFile6.getAbsolutePath());
        incompleteFilterOnly.add("csv-file " + tempFile6.getAbsolutePath());
        controllerIncompleteFilter = new Controller(incompleteFilterOnly);
        controllerIncompleteFilter.runCommands();
        assertEquals(incompleteFilterResults, controllerIncompleteFilter.getCurrentToDos());
    }

    @Test
    public void testDisplayCategoryFilter() throws InvalidArgumentException, IOException {
        final File tempFile7 = tempFolder.newFile("testCategory.txt");
        Write.updateCsvFile(originalToDos, tempFile7.getAbsolutePath());
        categoryFilterOnly.add("csv-file " + tempFile7.getAbsolutePath());
        controllerCategoryFilter = new Controller(categoryFilterOnly);
        controllerCategoryFilter.runCommands();
        assertEquals(categoryFilterResults, controllerCategoryFilter.getCurrentToDos());
    }

    @Test
    public void testToString() {
        allFilters.add("csv-file todos2.csv");
        controllerFilter = new Controller(allFilters);
        assertEquals("Controller{commands=[display, show-incomplete, show-category " +
                "school, csv-file todos2.csv]}", controllerFilter.toString());
    }
}