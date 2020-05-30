package assignment9.controller;

import static org.junit.Assert.*;

import assignment9.model.ToDo;
import assignment9.model.ToDo.Builder;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class DateSortTest {

  ToDo todo1;
  Builder builder1;
  LocalDate localDate1;
  ToDo todo2;
  Builder builder2;
  LocalDate localDate2;
  ToDo todo0;
  Builder builder0;
  ToDo todo;
  Builder builder;
  LocalDate localDate;
  DateSort dateSort;

  @Before
  public void setUp() throws Exception {
    builder1 = new ToDo.Builder(1, "todo1");
    localDate1 = LocalDate.parse("2020-03-22");
    builder1.addDueDate(localDate1);
    todo1 = builder1.build();

    builder2 = new ToDo.Builder(2, "todo2");
    localDate2 = LocalDate.parse("2020-04-01");
    builder2.addDueDate(localDate2);
    todo2 = builder2.build();

    builder0 = new ToDo.Builder(0, "todo0");
    todo0 = builder0.build();

    builder = new ToDo.Builder(11, "todo1");
    localDate = LocalDate.parse("2020-03-22");
    builder.addDueDate(localDate);
    todo = builder.build();

    dateSort = new DateSort();
  }

  @Test
  public void compare() {
    int result = dateSort.compare(todo1, todo2);
    assertEquals(-1, result);
    int result2 = dateSort.compare(todo2, todo1);
    assertEquals(1, result2);
  }

  @Test
  public void compareEqual() {
    int result = dateSort.compare(todo1, todo);
    assertEquals(0, result);
  }

  @Test
  public void compareNull() {
    int result = dateSort.compare(todo0, todo1);
    assertEquals(1, result);
    int result2 = dateSort.compare(todo1, todo0);
    assertEquals(-1, result2);
    int result3 = dateSort.compare(todo0, todo0);
    assertEquals(0, result3);
  }
}