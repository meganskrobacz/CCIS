package assignment9.controller;

import static org.junit.Assert.*;

import assignment9.model.ToDo;
import assignment9.model.ToDo.Builder;
import com.sun.tools.javac.comp.Todo;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class PrioritySortTest {

  ToDo todo1;
  Builder builder1;
  ToDo todo2;
  Builder builder2;
  ToDo todo0;
  Builder builder0;
  ToDo todo;
  Builder builder;
  PrioritySort prioritySort;

  @Before
  public void setUp() throws Exception {
    builder1 = new ToDo.Builder(1, "todo1");
    builder1.addPriority(1);
    todo1 = builder1.build();

    builder2 = new ToDo.Builder(2, "todo2");
    builder2.addPriority(2);
    todo2 = builder2.build();

    builder0 = new ToDo.Builder(0, "todo0");
    todo0 = builder0.build();

    builder = new ToDo.Builder(11, "todo1");
    builder.addPriority(1);
    todo = builder.build();

    prioritySort = new PrioritySort();
    }

  @Test
  public void compare() {
    int result = prioritySort.compare(todo1, todo2);
    assertEquals(-1, result);
    int result2 = prioritySort.compare(todo2, todo1);
    assertEquals(1, result2);
  }

  @Test
  public void compareEqual() {
    int result = prioritySort.compare(todo1, todo);
    assertEquals(0, result);
  }

  @Test
  public void compareNull() {
    int result = prioritySort.compare(todo0, todo1);
    assertEquals(1, result);
    int result2 = prioritySort.compare(todo2, todo0);
    assertEquals(-1, result2);
    int result3 = prioritySort.compare(todo0, todo0);
    assertEquals(0, result3);
  }
}