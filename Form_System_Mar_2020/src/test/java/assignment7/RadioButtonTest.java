package assignment7;

import static org.junit.Assert.*;

import assignment7.RadioButton;
import org.junit.Before;
import org.junit.Test;

public class RadioButtonTest {
  Boolean trueValue = true;
  Boolean nullValue = null;
  Boolean falseValue = false;
  RadioButton<Boolean> checkValues;
  RadioButton<Boolean> sameCheckValues;

  @Before
  public void setUp() throws Exception {
    checkValues = new RadioButton<>();
  }

  @Test
  public void isValid() {
    assertTrue(checkValues.isValid(trueValue));
    assertTrue(checkValues.isValid(falseValue));
    assertFalse(checkValues.isValid(nullValue));
  }

  @Test
  public void testToString() {
    assertEquals("This is a Validator Object of Type: Radio Button.",
        checkValues.toString());
  }
}
