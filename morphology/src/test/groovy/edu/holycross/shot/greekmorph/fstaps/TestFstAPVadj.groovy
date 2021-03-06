package edu.holycross.shot.greekmorph

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class TestFstAPVadj{
// <u>smyth.n64316_0</u><u>lexent.n64316</u>lu<lo><verb><w_regular>::<w_regular>te/os<masc><nom><sg><vadj1><u>verbinfl.teos_1</u>
  File inflCsvSource = new File("src/fst/collectionAbbreviations.csv")
  UrnManager umgr = new UrnManager(inflCsvSource)

  @Test
  void testParser() {
    File urnReg = new File("sampledata/smyth/urnregistry/collectionregistry.csv")
    umgr.addCsvFile(urnReg)
    String verb = "<u>smyth.n64316_0</u><u>lexent.n64316</u>lu<lo><verb><w_regular>::<w_regular><vadj>te/os<masc><nom><sg><vadj1><u>verbinfl.teos_1</u>"

    FstAnalysisParser fap = new FstAnalysisParser(verb, umgr)


    // Analysis type, lexical entity, and form:
    assert fap.analysisPattern == AnalyticalType.VERBAL_ADJECTIVE
    assert fap.lexicalEntity.toString() == "urn:cite:shot:lexent.n64316"

    assert  fap.explanation.stem.toString() == "urn:cite:gmorph:smyth.n64316_0"
    assert fap.explanation.inflection.toString() == "urn:cite:gmorph:verbinfl.teos_1"
    assert fap.getSurfaceStem() == "lu"
    assert fap.getSurfaceInflection()  == "te/os"
    assert fap.getSurface() == "lu-te/os"

    MorphForm mf = fap.getMorphForm()
    assert mf.toString() == "verbal adjective: masculine nominative singular"



  }
}
