package edu.holycross.shot.greekmorph

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail

class TestDegreeEnum {

  def expectedTokens = ["<pos>", "<comp>", "<superl>"]
  def expectedLabels = ["positive", "comparative", "superlative"]

  @Test
  void testDegreeEnum() {
    ArrayList testList = Degree.values()  as ArrayList
    testList.eachWithIndex { n, i ->
      assert n.getToken() == expectedTokens[i]
      assert n.getLabel() == expectedLabels[i]
    }
  }
  @Test
  void testIndex() {
    assert Degree.getByToken("<pos>") == Degree.POSITIVE
    assert Degree.getByToken("<comp>") == Degree.COMPARATIVE
    assert Degree.getByToken("<superl>") == Degree.SUPERLATIVE
  }
}
