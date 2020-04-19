package assignment7;

import static org.junit.Assert.*;

import assignment7.CheckBox;
import org.junit.Before;
import org.junit.Test;

public class CheckBoxTest {
  Boolean trueValue = true;
  Boolean nullValue = null;
  Boolean falseValue = false;
  CheckBox<Boolean> checkValues;
  CheckBox<Boolean> sameCheckValues;

  @Before
  public void setUp() throws Exception {
    checkValues = new CheckBox<>();
    sameCheckValues = new CheckBox<>();
  }

  @Test
  public void isValid() {
    assertTrue(checkValues.isValid(trueValue));
    assertTrue(checkValues.isValid(nullValue));
    assertTrue(checkValues.isValid(falseValue));
  }

  @Test
  public void testToString() {
    assertEquals("This is a Validator Object of Type:  Checkbox.",
        checkValues.toString());
  }
}
