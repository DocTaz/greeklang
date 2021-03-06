package edu.holycross.shot.greekmorph

import edu.holycross.shot.orthography.GreekString

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


/** Tests demonstrating parsing of nouns from Unicode string.
*/
class TestGreekEWSynopsisInfin {
  String fstBinary = "build/smyth/greek.a"
  File urnReg = new File("sampledata/smyth/urnregistry/collectionregistry.csv")
  UrnManager umgr = new UrnManager(urnReg)
  // The parser:
  LiteraryGreekParser mp = new LiteraryGreekParser(fstBinary, umgr)


  @Test
  void testUnique() {
    //
    def expectedUnique = [

    "ποιεῖν": [Tense.PRESENT, Voice.ACTIVE],
    "ποιήσειν": [Tense.FUTURE, Voice.ACTIVE],
    //"ποιῆσαι": [Tense.AORIST, Voice.ACTIVE],
    "πεποιηκέναι": [Tense.PERFECT, Voice.ACTIVE],


    "ποιήσεσθαι": [Tense.FUTURE, Voice.MIDDLE],
    "ποιήσασθαι": [Tense.AORIST, Voice.MIDDLE],


    "ποιηθήσεσθαι": [Tense.FUTURE, Voice.PASSIVE],
    "ποιηθῆναι": [Tense.AORIST, Voice.PASSIVE],

    ]

    expectedUnique.keySet().each { greek ->
      def expectedAnswer = expectedUnique[greek]
      MorphologicalAnalysis morph = mp.parseGreekString(new GreekString(greek,true))

      assert morph.analyses.size() == 1
      MorphForm form = morph.analyses[0].getMorphForm()
      assert form.getAnalyticalType() == AnalyticalType.INFINITIVE
      CitableId formIdentification = form.getAnalysis()
      assert formIdentification.getTense() == expectedAnswer[0]
      assert formIdentification.getVoice() == expectedAnswer[1]
    }
  }

// Voice.MIDDLE],
  @Test
  void testMP() {
    def expectedMP = [
    "ποιεῖσθαι": Tense.PRESENT,
    //"πεποιῆσθαι": Tense.PERFECT
    ]
    expectedMP.keySet().each { greek ->
      def expectedAnswer = expectedMP[greek]
      MorphologicalAnalysis morph = mp.parseGreekString(new GreekString(greek,true))

      assert morph.analyses.size() == 2
      morph.analyses.each{ ma ->
        MorphForm form = morph.analyses[0].getMorphForm()
        assert form.getAnalyticalType() == AnalyticalType.INFINITIVE
        CitableId formIdentification = form.getAnalysis()
        assert formIdentification.getTense() == expectedAnswer
        assert [Voice.MIDDLE, Voice.PASSIVE].contains(formIdentification.getVoice())
      }

    }
  }

}
