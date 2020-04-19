package assignment7;

import static org.junit.Assert.*;

import assignment7.Password;
import org.junit.Before;
import org.junit.Test;

public class PasswordTest {
  String correctPassword = "secREt123456";
  String notEnoughLowercaseLetters = "HASTALAVISTA3321";
  String notEnoughUppercaseLetters = "illbeback1919";
  String notEnoughDigits = "MyPrecious";
  String smallerThanMinimum = "CaTss1111";
  String largerThanMaximum = "YouWontGuessThisOne559900";
  String containsSpace = "Winter is coming";
  Password<String> checkInput;
  Password<String> sameCheckInput;
  Password<String> zeroDefault;

  @Before
  public void setUp() throws Exception {
    checkInput = new Password<>(10, 20, 3,
        2, 4);
    sameCheckInput = new Password<>(10, 20, 3,
        2, 4);
    zeroDefault = new Password<>(10, 20);
  }

  @Test
  public void isValid() {
    assertTrue(checkInput.isValid(correctPassword));
    assertFalse(checkInput.isValid(notEnoughLowercaseLetters));
    assertFalse(checkInput.isValid(notEnoughUppercaseLetters));
    assertFalse(checkInput.isValid(notEnoughDigits));
    assertFalse(checkInput.isValid(smallerThanMinimum));
    assertFalse(checkInput.isValid(largerThanMaximum));
    assertFalse(checkInput.isValid(containsSpace));
    assertTrue(zeroDefault.isValid(correctPassword));
  }

  @Test
  public void testEquals() {
    assertTrue(checkInput.equals(checkInput));
    assertNotEquals(null, checkInput);
    assertFalse(checkInput.equals(correctPassword));
    assertTrue(checkInput.equals(sameCheckInput));
  }

  @Test
  public void testHashCode() {
    assertTrue(checkInput.hashCode() == sameCheckInput.hashCode());
    assertFalse(checkInput.hashCode() == notEnoughDigits.hashCode());
  }


  @Test
  public void testToString() {
    assertEquals("This is a Validator Object of Type: Password.\n"
        + "Password requirements are: \n"
        + "Minimum Password Length: 10\n"
        + "Maximum Password Length: 20\n"
        + "Minimum Number of Lower Case Characters: 3\n"
        + "Minimum Number of Upper Case Characters: 2\n"
        + "Minimum Number of Digit Characters: 4", checkInput.toString());
  }
}
