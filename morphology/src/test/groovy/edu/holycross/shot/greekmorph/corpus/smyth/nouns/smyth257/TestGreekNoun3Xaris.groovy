package edu.holycross.shot.greekmorph

import edu.holycross.shot.orthography.GreekString

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


/** Tests demonstrating parsing of nouns from Unicode string.
*/
class TestGreekNoun3Xaris {
  // CSV files with URN abbreviations for stems and inflectional rules
  File urnReg = new File("sampledata/smyth/urnregistry/collectionregistry.csv")

  // A URN manager configured with CITE collection abbreviations
  // for both inflectional patterns and lexicon of stems:
  UrnManager umgr = new UrnManager(urnReg)

  // Compiled finite state transducer:
  String fstBinary = "build/smyth/greek.a"

  // The parser:
  LiteraryGreekParser mp = new LiteraryGreekParser(fstBinary, umgr)


    @Test
    void testDeclension() {
      mp.debug = 10
      mp.fstParser.debug = 10
        // map keyed by forms to analyze, to a unique GCN of noun form
        def expectedUnique = [

        "χαρίσματος": [Gender.NEUTER, GrammaticalCase.GENITIVE, GrammaticalNumber.SINGULAR],
        "χαρίσματι": [Gender.NEUTER, GrammaticalCase.DATIVE, GrammaticalNumber.SINGULAR],


        "χαρισμάτων": [Gender.NEUTER, GrammaticalCase.GENITIVE, GrammaticalNumber.PLURAL],
        "χαρίσμασι": [Gender.NEUTER, GrammaticalCase.DATIVE, GrammaticalNumber.PLURAL],
        "χαρίσμασιν": [Gender.NEUTER, GrammaticalCase.DATIVE, GrammaticalNumber.PLURAL]


        ]

        expectedUnique.keySet().each { greek ->
          def expectedAnswer = expectedUnique[greek]
          MorphologicalAnalysis morph = mp.parseGreekString(new GreekString(greek,true))
          assert morph.analyses.size() == 1
          MorphForm form = morph.analyses[0].getMorphForm()
          assert form.getAnalyticalType() == AnalyticalType.NOUN
          CitableId formIdentification = form.getAnalysis()
          assert formIdentification.getGender() == expectedAnswer[0]
          assert formIdentification.getCas() == expectedAnswer[1]
          assert formIdentification.getNum() == expectedAnswer[2]
        }
/*
        // Check also the ambiguous nom/voc forms.
        // Singular:
        def nom_voc = [GrammaticalCase.NOMINATIVE,GrammaticalCase.VOCATIVE ]
        GreekString ambiguous = new GreekString("ψυχή",true)
        MorphologicalAnalysis morph = mp.parseGreekString(ambiguous)
        assert morph.analyses.size() == 2
        morph.analyses.each {
            MorphForm form = it.getMorphForm()
            assert form.getAnalyticalType() == AnalyticalType.NOUN
            CitableId formIdentification = form.getAnalysis()
            // can't know ordering of analyses, but case must be
            // ONE of these two!
            assert nom_voc.contains(formIdentification.getCas())
            assert formIdentification.getGender() == Gender.NEUTER
            assert formIdentification.getNum() == GrammaticalNumber.SINGULAR
        }
        // Plural:
        GreekString ambiguousPlural = new GreekString("ψυχαί",true)
        MorphologicalAnalysis morphPl = mp.parseGreekString(ambiguousPlural)
        assert morphPl.analyses.size() == 2
        morphPl.analyses.each {
            MorphForm form = it.getMorphForm()
            assert form.getAnalyticalType() == AnalyticalType.NOUN
            CitableId formIdentification = form.getAnalysis()
            assert nom_voc.contains(formIdentification.getCas())
            assert formIdentification.getGender() == Gender.NEUTER
            assert formIdentification.getNum() == GrammaticalNumber.PLURAL
        }*/
      }


}
