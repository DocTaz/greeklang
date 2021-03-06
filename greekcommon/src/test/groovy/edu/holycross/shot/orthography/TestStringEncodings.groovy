package edu.holycross.shot.orthography

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail

class TestStringEncodings {



  @Test void testUnicode() {
    String testUnicode = "μῆνιν"
    String testBeta = "mh=nin"

    GreekString uniGreek = new GreekString(testUnicode, true)
    GreekString betaGreek = new GreekString(testBeta)

    assert uniGreek.toString() == betaGreek.toString()
  }

  @Test void testLimits() {
    String notGreek = "αλφα = 1"

    assert shouldFail {
      GreekString badString = new GreekString(notGreek, true)
    }

  }



}
