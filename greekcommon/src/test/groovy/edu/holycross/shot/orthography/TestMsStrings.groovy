package edu.holycross.shot.orthography

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail

/** Class tests extracting integer and fraction
 * components of a MilesianString.
 */
class TestMsStrings {




  @Test void testInstance() {
    String eoSchol = "⁑"
    GreekMsString greekEoSchol = new GreekMsString(eoSchol, true)
    assert greekEoSchol.toString() == eoSchol
  }



  @Test void testCombos() {
    String macronCombo = "ἀ̄λλ'"
    GreekMsString greekMacronCombo = new GreekMsString(macronCombo, true)
    String expectedAscii = "a)_ll'"
    assert greekMacronCombo.toString(false) == expectedAscii
  }

  @Test void testAwfulHighStops() {
    // valid Greek Ano Teleia:
    String high = "δίῳ·"
    GreekMsString greekHigh = new GreekMsString(high, true)
    String expectedAscii = "di/w|:"
    assert greekHigh.toString(false) == expectedAscii

    // Latin high stop pointlessly equated with Greek Ano Teleia.
    String evilImpostor = "πυκίλαι·"
    assert shouldFail {
      GreekMsString dotless = new GreekMsString(evilImpostor, true)
    }
  }


  @Test void testWordString() {
    String verbForm = "κλονέονται"
    GreekMsString msVerbForm = new GreekMsString(verbForm, true)
    String expectedAscii = "klone/ontai"
    assert msVerbForm.toString(false) == expectedAscii
  }


}
