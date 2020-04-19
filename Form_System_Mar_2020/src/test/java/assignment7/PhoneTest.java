package assignment7;

import static org.junit.Assert.*;

import assignment7.Phone;
import org.junit.Before;
import org.junit.Test;

public class PhoneTest {
  String normalNumber = "2168652322";
  String longerThanNormal = "5867956785657";
  String shorterThanNormal = "3453";
  String hasDashes = "216-865-2322";
  String hasCharacters = "two8652322";
  String sixDigit = "454656";
  Phone<String> checksPhone;
  Phone<String> sameChecksPhone;
  Phone<String> notPhone;

  @Before
  public void setUp() throws Exception {
    checksPhone = new Phone<>(10);
    sameChecksPhone = new Phone<>(10);
    notPhone = new Phone<>(6);
  }

  @Test
  public void isValid() {
    assertTrue(checksPhone.isValid(normalNumber));
    assertTrue(notPhone.isValid(sixDigit));
    assertFalse(checksPhone.isValid(longerThanNormal));
    assertFalse(checksPhone.isValid(shorterThanNormal));
    assertFalse(checksPhone.isValid(hasDashes));
    assertFalse(checksPhone.isValid(hasCharacters));
  }

  @Test
  public void testEquals() {
    assertTrue(checksPhone.equals(checksPhone));
    assertNotEquals(null, checksPhone);
    assertFalse(checksPhone.equals(normalNumber));
    assertTrue(checksPhone.equals(sameChecksPhone));
    assertFalse(checksPhone.equals(notPhone));
  }

  @Test
  public void testHashCode() {
    assertTrue(checksPhone.hashCode() == sameChecksPhone.hashCode());
    assertFalse(checksPhone.hashCode() == notPhone.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("This is a Validator Object of Type: Phone.\n"
        + "Phone number required length is: 10", checksPhone.toString());
  }
}
