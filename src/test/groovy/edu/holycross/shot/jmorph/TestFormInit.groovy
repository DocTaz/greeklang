package edu.holycross.shot.jmorph


import static org.junit.Assert.*
import org.junit.Test


class TestFormInit extends GroovyTestCase {


  def nounArray = ["","singular","","","","masculine","nominative","noun"]
  String nounStr = ":singular::::masculine:genitive:noun"

  def conjVerbArray = ["1","singular","present","indicative","active","","","verb"]
  String conjVerbStr = "1:singular:present:indicative:active:::verb"

  def ptcplArray = ["","singular","present","participle","active","masculine","nominative","verb"]
  String ptcplStr = ":singular:present:participle:active:masculine:case:verb"

  void testSimple() {
    MorphForm nounForm = new MorphForm(nounStr)
    assert nounForm

    MorphForm verbForm = new MorphForm(conjVerbStr)
    assert verbForm

    MorphForm ptcpForm = new MorphForm(ptcplStr)
    assert ptcpForm

  }


}