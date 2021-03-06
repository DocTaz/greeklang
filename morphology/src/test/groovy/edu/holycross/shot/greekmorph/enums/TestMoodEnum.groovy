package edu.holycross.shot.greekmorph

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail

class TestMoodEnum {

  def expectedTokens = ["<indic>", "<subj>", "<opt>", "<imptv>"]
  def expectedLabels = ["indicative", "subjunctive", "optative", "imperative"]

  @Test
  void testMoodEnum() {
    ArrayList testList = Mood.values()  as ArrayList
    testList.eachWithIndex { n, i ->
      assert n.getToken() == expectedTokens[i]
      assert n.getLabel() == expectedLabels[i]
    }
  }

  @Test
  void testIndex() {
    assert Mood.getByToken("<indic>") == Mood.INDICATIVE
    assert Mood.getByToken("<subj>") == Mood.SUBJUNCTIVE
    assert Mood.getByToken("<opt>") == Mood.OPTATIVE
    assert Mood.getByToken("<imptv>") == Mood.IMPERATIVE
  }

}
