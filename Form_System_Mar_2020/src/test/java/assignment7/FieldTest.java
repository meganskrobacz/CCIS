package assignment7;

import static org.junit.Assert.*;

import assignment7.*;
import assignment7.Number;
import org.junit.Before;
import org.junit.Test;

public class FieldTest {
  //For the number class.
  String year = "1995";
  String withDecimal = "4.5";
  String dollarSign = "$35.60";
  String comma = "3,000.1454";
  String endsWithPeriod = "2.";
  Number<String> checkNumberInput = new Number<>(0, 3000,
      1);

  //For the checkbox and Radio class.
  Boolean trueValue = true;
  Boolean nullValue = null;
  Boolean falseValue = false;
  CheckBox<Boolean> checkValues = new CheckBox<>();
  RadioButton<Boolean> checkRadioValues = new RadioButton<>();

  //For the free text class.
  String incredibles = "'Greater good?' I am your wife! I'm the greatest good you're ever "
      + "gonna get!";
  String anchorman = "I'm in a glass case of emotions!";
  FreeText<String> correctTextLength = new FreeText<>(20, 5);
  FreeText<String> longerTextThanAllowed = new FreeText<>(10,
      3);

  //For the assignment7.Phone class.
  String normalNumber = "2168652322";
  String longerThanNormal = "5867956785657";
  String shorterThanNormal = "3453";
  String hasDashes = "216-865-2322";
  Phone<String> checksPhone = new Phone<>(10);

  //For the assignment7.Password class.
  String correctPassword = "secREt123456";
  String notEnoughLowercaseLetters = "HASTALAVISTA3321";
  String notEnoughUppercaseLetters = "illbeback1919";
  String notEnoughDigits = "MyPrecious";
  String smallerThanMinimum = "CaTss1111";
  String largerThanMaximum = "YouWontGuessThisOne559900";
  String containsSpace = "Winter is coming";
  Password<String> checkPasswordInput = new Password<>(10, 20,
      3, 2, 4);

  //For the fields class.
  Field<String> correctNumber;
  Field<String> incorrectNumber;
  Field<Boolean> checkBox;
  Field<String> longerThanAllowed;
  Field<String> correctLength;
  Field<String> sameCorrectLength;
  Field<String> correctPhone;
  Field<String> incorrectPhone;
  Field<String> matchingPassword;
  Field<String> incorrectPassword;
  Field<Boolean> radiobutton;

  public FieldTest() throws InvalidParameterException {
  }

  @Before
  public void setUp() throws Exception {
    correctNumber = new Field<>("Birth Year", true, checkNumberInput);
    incorrectNumber = new Field<>("Tip Amount", false, checkNumberInput);
    checkBox = new Field<>("Would Like to Receive Email Offers", false, checkValues);
    longerThanAllowed = new Field<>("Favorite Quote", false,
        longerTextThanAllowed);
    correctLength = new Field<>("Least Favorite Quote", true,
        correctTextLength);
    sameCorrectLength = new Field<>("Least Favorite Quote", true,
        correctTextLength);
    correctPhone = new Field<>("assignment7.Phone assignment7.Number", true,
        checksPhone);
    incorrectPhone = new Field<>("Wrong assignment7.Phone assignment7.Number", false,
        checksPhone);
    matchingPassword = new Field<>("Correct assignment7.Password", true,
        checkPasswordInput);
    incorrectPassword = new Field<>("Incorrect assignment7.Password", true,
        checkPasswordInput);
    radiobutton = new Field<>("Radio Button", true, checkRadioValues);
  }

  @Test
  public void getLabel() {
    assertEquals("Birth Year", correctNumber.getLabel());
    assertNotEquals("Incredible Movie", longerThanAllowed.getLabel());
  }

  @Test
  public void setLabel() {
    correctNumber.setLabel("Year of Birth");
    assertEquals("Year of Birth", correctNumber.getLabel());
    assertNotEquals("Birth Year", correctNumber.getLabel());
  }

  @Test
  public void updateValue() throws InvalidEntryException {
    correctNumber.updateValue(year);
    assertEquals("1995", correctNumber.getValue());
    correctNumber.updateValue(withDecimal);
    assertEquals("4.5", correctNumber.getValue());
    checkBox.updateValue(trueValue);
    assertTrue(checkBox.getValue());
    checkBox.updateValue(nullValue);
    assertNull(checkBox.getValue());
    checkBox.updateValue(falseValue);
    assertFalse(checkBox.getValue());
    correctLength.updateValue(anchorman);
    assertEquals("I'm in a glass case of emotions!", correctLength.getValue());
    correctPhone.updateValue(normalNumber);
    assertEquals("2168652322", correctPhone.getValue());
    matchingPassword.updateValue(correctPassword);
    assertEquals("secREt123456", matchingPassword.getValue());
    radiobutton.updateValue(trueValue);
    assertTrue(radiobutton.getValue());
    radiobutton.updateValue(falseValue);
    assertFalse(radiobutton.getValue());
  }

  @Test (expected = InvalidEntryException.class)
  public void incorrectUpdateValue() throws InvalidEntryException {
    incorrectNumber.updateValue(dollarSign);
    incorrectNumber.updateValue(comma);
    incorrectNumber.updateValue(endsWithPeriod);
    longerThanAllowed.updateValue(incredibles);
    incorrectPhone.updateValue(longerThanNormal);
    incorrectPhone.updateValue(shorterThanNormal);
    incorrectPhone.updateValue(hasDashes);
    incorrectPassword.updateValue(notEnoughLowercaseLetters);
    incorrectPassword.updateValue(notEnoughUppercaseLetters);
    incorrectPassword.updateValue(notEnoughDigits);
    incorrectPassword.updateValue(smallerThanMinimum);
    incorrectPassword.updateValue(largerThanMaximum);
    incorrectPassword.updateValue(containsSpace);
    radiobutton.updateValue(nullValue);
  }

  @Test
  public void getValue() {
    assertNull(incorrectNumber.getValue());
    assertNotEquals(false, incorrectNumber.getValue());
  }

  @Test
  public void isRequired() {
    assertTrue(correctNumber.isRequired());
    assertFalse(incorrectNumber.isRequired());
  }

  @Test
  public void setRequired() {
    checkBox.setRequired(true);
    assertTrue(checkBox.isRequired());
  }

  @Test
  public void getValidator() {
    assertEquals(longerTextThanAllowed, longerThanAllowed.getValidator());
    assertNotEquals(checkPasswordInput, checkBox.getValidator());
  }

  @Test
  public void setValidator() {
    correctNumber.setValidator(correctTextLength);
    assertEquals(correctTextLength, correctNumber.getValidator());
    assertNotEquals(checkNumberInput, correctNumber.getValidator());
  }

  @Test
  public void isFilled() throws InvalidEntryException {
    assertFalse(correctLength.isFilled());
    assertTrue(incorrectNumber.isFilled());
    correctLength.updateValue(anchorman);
    assertTrue(correctLength.isFilled());
  }

  @Test
  public void testEquals() {
    assertTrue(checkBox.equals(checkBox));
    assertNotEquals(null, checkBox);
    assertFalse(longerThanAllowed.equals(comma));
    assertTrue(correctLength.equals(sameCorrectLength));
    assertFalse(checkBox.equals(longerThanAllowed));
    assertFalse(correctNumber.equals(correctPhone));
    assertFalse(correctPhone.equals(incorrectPhone));
  }

  @Test
  public void testValueEquals() throws InvalidEntryException {
    radiobutton.updateValue(falseValue);
    correctNumber.updateValue(year);
    assertFalse(correctNumber.equals(radiobutton));
  }

  @Test
  public void testHashCode() {
    assertTrue(correctLength.hashCode() == sameCorrectLength.hashCode());
    assertFalse(correctLength.hashCode() == longerThanAllowed.hashCode());
  }


  @Test
  public void testToString() {
    assertEquals("This field is called Least Favorite Quote. It has a current value of "
        + "null. Is it required: true. This is a Validator Object of Type:  FreeText. This field "
        + "allows for 5 lines with 20 characters allowed per line.", correctLength.toString());
  }
}
