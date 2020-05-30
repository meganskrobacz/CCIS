package assignment9.controller;

import static org.junit.Assert.*;

import assignment9.model.ToDo;
import assignment9.model.ToDo.Builder;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FiltersTest {
  ToDo todo1;
  Builder builder1;
  String category1;
  ToDo todo2;
  Builder builder2;
  String category2;
  ToDo todo0;
  Builder builder0;
  ToDo todo;
  Builder builder;
  String category;
  List<ToDo> list1;
  List<ToDo> list2;
  List<ToDo> list;

  @Before
  public void setUp() throws Exception {
    builder1 = new ToDo.Builder(1, "todo1");
    category1 = "homework";
    builder1.addCategory(category1);
    builder1.complete(true);
    todo1 = builder1.build();

    builder2 = new ToDo.Builder(2, "todo2");
    category2 = "chores";
    builder2.addCategory(category2);
    todo2 = builder2.build();

    builder0 = new ToDo.Builder(0, "todo0");
    todo0 = builder0.build();

    builder = new ToDo.Builder(11, "todo1");
    category = "homework";
    builder.addCategory(category);
    builder.complete(true);
    todo = builder.build();

    list = new ArrayList<>();
    list.add(todo);
    list.add(todo0);
    list.add(todo1);
    list.add(todo2);

    list1 = new ArrayList<>();
    list1.add(todo0);
    list1.add(todo2);

    list2 = new ArrayList<>();
    list2.add(todo);
    list2.add(todo1);

  }

  @Test
  public void filterByIncomplete() {
    //Getting a java.util.ConcurrentModificationException
    List<ToDo> result = Filters.filterByIncomplete(list);
    assertEquals(result, list1);
  }

  @Test
  public void filterByCategory() {
    //Getting a java.lang.NullPointerException
    List<ToDo> result2 = Filters.filterByCategory(list, "homework");
    assertEquals(list2, result2);
  }
}