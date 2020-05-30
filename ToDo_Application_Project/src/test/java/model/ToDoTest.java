package assignment9.model;

import static org.junit.Assert.*;

import assignment9.model.ToDo.Builder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import jdk.vm.ci.meta.Local;
import org.junit.Before;
import org.junit.Test;

public class ToDoTest {
  ToDo one;
  int iDOne = 1;
  String textOne = "Finish HW9";
  Builder builderOne;
  ToDo sameOne;
  int sameIDOne = 1;
  String sameTextOne = "Finish HW9";
  Builder sameBuilderOne;
  ToDo two;
  int iDTwo = 2;
  String textTwo = "Mail passport";
  Builder builderTwo;
  ToDo three;
  int iDThree = 3;
  String textThree = "Study for finals";
  Builder builderThree;
  ToDo four;
  int iDFour = 4;
  String textFour = "Clean the house";
  Builder builderFour;
  LocalDate feb;
  LocalDate march;

  @Before
  public void setUp() throws Exception {
    sameBuilderOne = new ToDo.Builder(sameIDOne, sameTextOne);
    sameBuilderOne.addDueDate(LocalDate.of(2020, 3, 20));
    sameBuilderOne.addPriority(1);
    sameBuilderOne.addCategory("school");
    sameOne = sameBuilderOne.build();
    builderOne = new ToDo.Builder(iDOne, textOne);
    builderOne.addDueDate(LocalDate.of(2020, 3, 20));
    builderOne.addPriority(1);
    builderOne.addCategory("school");
    one = builderOne.build();
    builderTwo = new ToDo.Builder(iDTwo, textTwo);
    builderTwo.complete(true);
    builderTwo.addDueDate(LocalDate.of(2020, 2, 28));
    two = builderTwo.build();
    builderThree = new ToDo.Builder(iDThree, textThree);
    builderThree.addPriority(2);
    builderThree.addCategory("school");
    three = builderThree.build();
    builderFour = new ToDo.Builder(iDFour, textFour);
    builderFour.addDueDate(LocalDate.of(2020, 3, 22));
    builderFour.addPriority(3);
    builderFour.addCategory("home");
    four = builderFour.build();
    feb = LocalDate.of(2020, 2, 28);
    march = LocalDate.of(2020, 3, 20);
  }

  @Test
  public void getID() {
    assertEquals(1, one.getID());
    assertNotEquals(5, two.getID());
  }

  @Test
  public void getDescription() {
    assertEquals("Mail passport", two.getDescription());
    assertNotEquals("Study for finals", two.getDescription());
  }

  @Test
  public void isCompleted() {
    assertTrue(two.isCompleted());
    assertFalse(four.isCompleted());
  }

  @Test
  public void getPriority() {
    assertEquals(1, one.getPriority(), 0.001);
    assertNotEquals(4, four.getPriority(), 0.001);
  }

  @Test
  public void getDueDate() {
    assertEquals(feb, two.getDueDate());
    assertNotEquals(march, two.getDueDate());
  }

  @Test
  public void getCategory() {
    assertEquals("school", one.getCategory());
    assertNotEquals("home", one.getCategory());
  }

  @Test
  public void setCompleted() {
    assertFalse(three.isCompleted());
    three.setCompleted(true);
    assertTrue(three.isCompleted());
  }

  @Test
  public void testEquals() {
    assertTrue(one.equals(one));
    assertTrue(one.equals(sameOne));
    assertNotEquals(null, one);
    assertFalse(one.equals(two));
    assertFalse(one.equals(feb));
  }

  @Test
  public void testHashCode() {
    assertTrue(one.hashCode() == sameOne.hashCode());
    assertFalse(one.hashCode() == four.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("ToDo ID = 1, description = Finish HW9, completed = false, priority = 1, "
        + "dueDate = 2020-03-20, category = school", one.toString());
    assertEquals("ToDo ID = 3, description = Study for finals, completed = false, "
        + "priority = 2, dueDate = null, category = school", three.toString());
    assertNotEquals("ToDo ID = 1, description = Finish HW9, completed = false, "
        + "priority = 1, dueDate = 2020-03-20, category = school", three.toString());
  }
}