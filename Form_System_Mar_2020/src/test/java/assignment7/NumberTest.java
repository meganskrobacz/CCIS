package assignment7;

import static org.junit.Assert.*;

import assignment7.Number;
import org.junit.Before;
import org.junit.Test;

public class NumberTest {
  String year = "1995";
  String tip = "13.50";
  String withDecimal = "4.5";
  String withoutDecimal = "12";
  String dollarSign = "$35.60";
  String comma = "3,000.1454";
  String endsWithPeriod = "2.";
  String lowerThanMinimum = "30";
  String largerThanMaximum = "7000";
  String hasLetters = "seven000";
  Number<String> checkInput;
  Number<String> checkMinimum;
  Number<String> checkMaximum;
  Number<String> checkDecimalPlace;
  Number<String> sameCheckInput;
  Number<String> negativeNumber;

  @Before
  public void setUp() throws Exception {
    checkInput = new Number<>(0, 3000,
        0);
    sameCheckInput = new Number<>(0, 3000,
        0);
    checkMinimum = new Number<>(10, 5000,
        5);
    checkMaximum = new Number<>(0, 15,
        5);
    checkDecimalPlace = new Number<>(0, 500,
        2);
    negativeNumber = new Number<>(0, 100,
        -1);
  }

  @Test
  public void getNumMinimumValues() {
    assertEquals(10, checkMinimum.getNumMinimumValues(), 0.001);
    assertEquals(0, checkInput.getNumMinimumValues(), 0.001);
  }

  @Test
  public void setNumMinimumValues() {
    checkInput.setNumMinimumValues(20);
    assertEquals(20, checkInput.getNumMinimumValues(), 0.001);
  }

  @Test
  public void getNumMaximumValues() {
    assertEquals(3000, checkInput.getNumMaximumValues(), 0.001);
    assertEquals(15, checkMaximum.getNumMaximumValues(), 0.001);
  }

  @Test
  public void setNumMaximumValues() {
    checkMaximum.setNumMaximumValues(100);
    assertEquals(100, checkMaximum.getNumMaximumValues(), 0.001);
  }

  @Test
  public void getNumMaximumDecimalPlaces() {
    assertEquals(0, checkInput.getNumMaximumDecimalPlaces(), 0.001);
    assertEquals(5, checkMaximum.getNumMaximumDecimalPlaces(), 0.001);
  }

  @Test
  public void isValid() {
    assertTrue(checkInput.isValid(year));
    assertFalse(checkMinimum.isValid(withDecimal));
    assertTrue(checkMaximum.isValid(withoutDecimal));
    assertTrue(checkMinimum.isValid(tip));
    assertFalse(checkMinimum.isValid(dollarSign));
    assertFalse(checkMaximum.isValid(comma));
    assertFalse(checkMaximum.isValid(endsWithPeriod));
    assertTrue(checkMinimum.isValid(lowerThanMinimum));
    assertFalse(checkMinimum.isValid(largerThanMaximum));
    assertFalse(checkInput.isValid(hasLetters));
    assertFalse(checkInput.isValid(withDecimal));
  }

  @Test
  public void testEquals() {
    assertTrue(checkInput.equals(checkInput));
    assertNotEquals(null, checkInput);
    assertFalse(checkInput.equals(comma));
    assertTrue(checkInput.equals(sameCheckInput));
    assertFalse(checkInput.equals(checkMaximum));
  }

  @Test
  public void testHashCode() {
    assertTrue(checkInput.hashCode() == sameCheckInput.hashCode());
    assertFalse(checkInput.hashCode() == checkMinimum.hashCode());
  }


  @Test
  public void testToString() {
    assertEquals("This is a Validator Object of Type: The number requires a minimum "
            + "value of 0, a maximum value of 3000, and a maximum amount of decimal places of 0.",
        checkInput.toString());
  }
}
