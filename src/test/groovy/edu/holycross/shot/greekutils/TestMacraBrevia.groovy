package edu.holycross.shot.greekutils


import static org.junit.Assert.*
import org.junit.Test

/** Class tests extracting integer and fraction
 * components of a MilesianString.
 */
class TestMacraBrevia extends GroovyTestCase {



  void testMacron() {
    String macron = "κώκῡσεν"
    String expectedAscii = "kw/ku_sen"
    
    GreekMsString macronString = new GreekMsString(macron, "Unicode")
    assert macronString.toString(false) ==  GreekMsString.asciifyUnicode(macron, "Unicode")

    assert macronString.toString(false) == expectedAscii
    
  }

  void testBreve() {
    // map to ^
    String breve =  "πί̆θεσθέ"
    String expectedAscii = "pi/^qesqe/"
    
    
    GreekMsString breveString = new GreekMsString(breve, "Unicode")
    assert breveString.toString(false) ==  GreekMsString.asciifyUnicode(breve, "Unicode")

    assert breveString.toString(false) == expectedAscii
    
  }

  void testDiaeresis () {
    String diaeresis = "ί̈σχειν"
    String expectedAscii = "i/+sxein"

    GreekMsString diaeresisString = new GreekMsString(diaeresis, "Unicode")
    assert diaeresisString.toString(false) ==  GreekMsString.asciifyUnicode(diaeresis, "Unicode")
    assert diaeresisString.toString(false) == expectedAscii

  }
  
}