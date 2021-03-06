package edu.holycross.shot.greekmorph

import edu.holycross.shot.orthography.GreekString

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail

// add: λύσωμαι λύσοιμι λυσοίμην λύσαιμι λυσαίμην

/** Tests transducer in acceptors/verb.a for hanlding
* of formation of second princ part of omega verbs
*/
class TestOmegaSixthPrincPart {

  String fstinfl = "/usr/bin/fst-infl"
  File inflCsvSource = new File("src/fst/collectionAbbreviations.csv")
  File lexCsvSource = new File("sampledata/urn-registries/datasets.csv")
  File testFile = new File("build/testInput.txt")

  // Maps submitted FST string to expected value of morphform.toString()
  def testTransducers = [
  "<coretests.n64316_0><lexent.n64316><#>lu<lo>q<verb><w_regular>::<w_regular><w_indicative.67>hsomai<1st><sg><fut><indic><pass>":
  ["conjugated verb: first person singular future indicative passive"],

  "<coretests.n64316_0><lexent.n64316><#>e<sm>lu<lo>q<verb><w_regular>::<w_regular><w_indicative.37>hn<1st><sg><aor><indic><pass>":
  ["conjugated verb: first person singular aorist indicative passive"]

  ]

  def testFstStrings = [
  "luqhsomai":
  ["conjugated verb: first person singular future indicative passive"],

  "e<sm>luqhn":
  ["conjugated verb: first person singular aorist indicative passive"]

  ]

  def testUnicodeInput = [
  "λυθήσομαι":
  ["conjugated verb: first person singular future indicative passive"],

  "ἐλύθην":
  ["conjugated verb: first person singular aorist indicative passive"]

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
      "build/fst/acceptors/verb/6th_pp.a",
      "build/fst/acceptors/verb/w_princparts.a",
      "build/fst/acceptors/verb.a",
      "build/fst/acceptor.a"
      ]
      transducers.each { t ->
        String cmd = "${fstinfl} ${t} ${testFile}"
        System.err.println "Testing second princ part on ${t}"
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
      System.err.println "second princ part on ${parser}"
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
      System.err.println "second princ part on Morphological parser configured wtih  ${fstBinary}"
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
