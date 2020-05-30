package assignment9.view;

import static org.junit.Assert.*;

import assignment9.model.ToDo;
import assignment9.model.ToDo.Builder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class DisplayTest {
  ToDo todo1;
  Builder builder1;
  String category1;
  LocalDate localDate1;
  ToDo todo2;
  Builder builder2;
  String category2;
  ToDo todo0;
  Builder builder0;
  ToDo todo;
  Builder builder;
  String category;
  List<ToDo> list;
  Display display;

  @Before
  public void setUp() throws Exception {
    builder1 = new ToDo.Builder(1, "todo1");
    category1 = "homework";
    builder1.addCategory(category1);
    builder1.complete(true);
    localDate1 = LocalDate.parse("2020-03-22");
    builder1.addDueDate(localDate1);
    todo1 = builder1.build();

    builder2 = new ToDo.Builder(2, "todo2");
    category2 = "chores";
    builder2.addCategory(category2);
    todo2 = builder2.build();

    builder0 = new ToDo.Builder(0, "todo0");
    todo0 = builder0.build();

    builder = new ToDo.Builder(11, "todo11");
    category = "homework";
    builder.addCategory(category);
    builder.complete(true);
    todo = builder.build();

    list = new ArrayList<>();
    list.add(todo);
    list.add(todo0);
    list.add(todo1);
    list.add(todo2);

    display = new Display(list);
  }

  @Test
  public void getToDosInDisplay() {
    assertEquals(list, display.getToDosInDisplay());
  }
  
}