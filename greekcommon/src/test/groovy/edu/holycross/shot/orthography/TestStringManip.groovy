package edu.holycross.shot.orthography

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class TestStringManip {

  @Test void testUnicode() {
    String src = "mh=nin"
    String expected = "mhnin"
    assert GreekString.stripAccents(src) == expected
  }


}
