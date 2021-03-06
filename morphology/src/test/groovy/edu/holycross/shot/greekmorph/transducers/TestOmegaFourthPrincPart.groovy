package edu.holycross.shot.greekmorph

import edu.holycross.shot.orthography.GreekString

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


/** Tests transducer in acceptors/verb.a for hanlding
* of formation of second princ part of omega verbs
*/
class TestOmegaFourthPrincPart {

  String fstinfl = "/usr/bin/fst-infl"
  File inflCsvSource = new File("src/fst/collectionAbbreviations.csv")
  File lexCsvSource = new File("sampledata/urn-registries/datasets.csv")
  File testFile = new File("build/testInput.txt")

  // Maps submitted FST string to expected value of morphform.toString()
  def testTransducers = [
  "<coretests.n64316_0><lexent.n64316><#>lelu<lo>k<verb><w_regular>::<w_regular><w_indicative.43>a<1st><sg><pft><indic><act>":
  ["conjugated verb: first person singular perfect indicative active"]

  // Note that augment on pluperfect is applied in subsequent transducer.
  // Therefore need to test it separately lower-tier transducers and in
  // verb.a where integration with augment changes the relation of
  // generated to analysis.
  /*
  "<coretests.n64316_0><lexent.n64316><#>leluk<verb><w_regular>::<w_regular><w_indicative.55>h<1st><sg><plupft><indic><act>":
    ["conjugated verb: first person singular pluperfect indicative active"]
    */
  ]

  def testFstStrings = [
  "leluka":
  ["conjugated verb: first person singular perfect indicative active"],
  "e<sm>lelukh":
  ["conjugated verb: first person singular pluperfect indicative active"]
]


  def testUnicodeInput = [
  "λέλυκα":
  ["conjugated verb: first person singular perfect indicative active"],
  "ἐλελύκη":
  ["conjugated verb: first person singular pluperfect indicative active"]
  ]

  /** Runs a given command a returns a list with FST strings for each analysis.
  */
  ArrayList getAnalysisStrings(String cmd, UrnManager urnManager) {
    def analysisStrings = []
    Process process = cmd.execute()
    def out = new StringBuffer()
    def err = new StringBuffer()
    process.consumeProcessOutput( out, err )
    process.waitFor()
    out.toString().eachLine { l ->
      if (l[0] == ">") {
        // omit
      } else if (l ==~ /[Nn]o result.+/) {
        // omit
      } else {
        FstAnalysisParser fsp = new FstAnalysisParser(l, urnManager)
        MorphForm morphForm = fsp.getMorphForm()
        analysisStrings.add(morphForm.toString())
      }
    }
    return analysisStrings
  }

    @Test
    void testVerbTransducers() {
      UrnManager umgr = new UrnManager(inflCsvSource)
      umgr.addCsvFile(lexCsvSource)
      def transducers = [
      "build/fst/acceptors/verb/4th_5th_pp.a",
      "build/fst/acceptors/verb/w_princparts.a",
      "build/fst/acceptors/verb.a",
      "build/fst/acceptor.a"
      ]
      transducers.each { t ->
        String cmd = "${fstinfl} ${t} ${testFile}"
        System.err.println "Testing fourth-fifth princ parts on ${t}"
        testTransducers.each { wd ->
          testFile.setText(wd.key)
          def actualReplies = getAnalysisStrings(cmd, umgr)
          System.err.println "\tFor ${wd.key}, got \n${actualReplies}\n"
          //assert actualReplies as Set ==  wd.value as Set
        }
      }
    }

    @Test
    void testFinalParser() {
      String parser = "build/fst/greek.a"
      String cmd = "${fstinfl} ${parser} ${testFile}"

      UrnManager umgr = new UrnManager(inflCsvSource)
      umgr.addCsvFile(lexCsvSource)
      System.err.println "fourth-fifth princ parts on final ${parser}"
      testFstStrings.each { wd ->
        testFile.setText(wd.key)
        def actualReplies = getAnalysisStrings(cmd, umgr)
        System.err.println "\tFor ${wd.key}, got \n${actualReplies}\n"
        //assert actualReplies as Set ==  wd.value as Set
      }
    }

    @Test
    void testMorphParser() {
      UrnManager umgr = new UrnManager(inflCsvSource)
      umgr.addCsvFile(lexCsvSource)

      String fstBinary = "build/fst/greek.a"
      LiteraryGreekParser mp = new LiteraryGreekParser(fstBinary, umgr)
      System.err.println "testing fourth-fifth princ part on Morphological parser configured wtih  ${fstBinary}"
      testUnicodeInput.each { wd ->
        GreekString s = new GreekString(wd.key, true)
        MorphologicalAnalysis morph = mp.parseGreekString(s)
        def actualReplies = []
        morph.analyses.each {
          actualReplies.add(it.toString())
        }
        System.err.println "\tFor ${wd.key}, got \n${actualReplies}\n"
        //assert actualReplies as Set ==  wd.value as Set
      }

    }
}
