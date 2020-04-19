package assignment7;

import static org.junit.Assert.*;

import assignment7.FreeText;
import assignment7.InvalidParameterException;
import org.junit.Before;
import org.junit.Test;

public class FreeTextTest {
  String incredibles = "'Greater good?' I am your wife! I'm the greatest good you're ever "
      + "gonna get!";
  String anchorman = "I'm in a glass case of emotions!";
  FreeText<String> longerThanAllowed;
  FreeText<String> sameLongerThanAllowed;
  FreeText<String> correctLength;
  FreeText<String> negativeAllowed;
  FreeText<String> zeroAllowed;

  @Before
  public void setUp() throws Exception {
    longerThanAllowed = new FreeText<>(10, 3);
    sameLongerThanAllowed = new FreeText<>(10, 3);
    correctLength = new FreeText<>(20, 5);
  }

  @Test (expected = InvalidParameterException.class)
  public void incorrectObject() throws InvalidParameterException {
    negativeAllowed = new FreeText<>(-2, -10);
    zeroAllowed = new FreeText<>(20, 0);
  }

  @Test
  public void getNumCharactersPerLine() {
    assertEquals(10, longerThanAllowed.getNumCharactersPerLine(), 0.001);
    assertNotEquals(5, correctLength.getNumCharactersPerLine(), 0.001);
  }

  @Test
  public void getNumLinesAllowed() {
    assertEquals(3, longerThanAllowed.getNumLinesAllowed(), 0.001);
    assertNotEquals(20, correctLength.getNumLinesAllowed(), 0.001);
  }

  @Test
  public void isValid() {
    assertFalse(longerThanAllowed.isValid(incredibles));
    assertTrue(correctLength.isValid(anchorman));
  }

  @Test
  public void testEquals() {
    assertTrue(longerThanAllowed.equals(longerThanAllowed));
    assertNotEquals(null, longerThanAllowed);
    assertFalse(correctLength.equals(anchorman));
    assertTrue(longerThanAllowed.equals(sameLongerThanAllowed));
    assertFalse(correctLength.equals(zeroAllowed));
  }

  @Test
  public void testHashCode() {
    assertTrue(longerThanAllowed.hashCode() == sameLongerThanAllowed.hashCode());
    assertFalse(correctLength.hashCode() == longerThanAllowed.hashCode());
  }


  @Test
  public void testToString() {
    assertEquals("This is a Validator Object of Type:  FreeText. This field allows "
        + "for 5 lines with 20 characters allowed per line.", correctLength.toString());
  }
}
