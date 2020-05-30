package assignment9.controller;

import static org.junit.Assert.*;

import assignment9.model.ToDo;
import assignment9.model.ToDo.Builder;
import assignment9.view.InvalidArgumentException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReadTest {
    String invalidAddress;
    String oneDescription;
    String twoDescription;
    String threeDescription;
    String fourDescription;
    LocalDate date1;
    LocalDate date2;
    LocalDate date4;
    String category1and3;
    String category4;
    Builder build1;
    Builder build2;
    Builder build3;
    Builder build4;
    ToDo one;
    ToDo two;
    ToDo three;
    ToDo four;
    List<ToDo> allToDos = new ArrayList<>();
    Read read;

    @Before
    public void setUp() throws Exception {
        invalidAddress = "ningun.csv";
        oneDescription = "Finish HW9";
        twoDescription = "Mail Passport";
        threeDescription = "Study for finals";
        fourDescription = "Clean the house";

        date1 = LocalDate.parse("2020-03-22");
        date2 = LocalDate.parse("2020-02-28");
        date4 = LocalDate.parse("2020-03-21");

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

        allToDos.add(one);
        allToDos.add(two);
        allToDos.add(three);
        allToDos.add(four);

        read = new Read();
    }

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void getAllToDosAndReadFile() throws InvalidArgumentException, IOException {
        final File tempFile = tempFolder.newFile("tempFile.txt");
        Write.updateCsvFile(allToDos, tempFile.getAbsolutePath());
        read.readFile(tempFile.getAbsolutePath());
        assertEquals(read.getAllToDos(), allToDos);
    }

    @Test (expected = InvalidArgumentException.class)
    public void readInvalidFile() throws InvalidArgumentException {
        read.readFile(invalidAddress);
    }
}