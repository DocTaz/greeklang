package edu.holycross.shot.greekmorph

import edu.holycross.shot.orthography.GreekString

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


/** Tests demonstrating parsing of nouns from Unicode string.
*/
class TestSmythW383fut {
  String fstBinary = "build/smyth/greek.a"
  File urnReg = new File("sampledata/smyth/urnregistry/collectionregistry.csv")
  UrnManager umgr = new UrnManager(urnReg)
  // The parser:
  LiteraryGreekParser mp = new LiteraryGreekParser(fstBinary, umgr, 8)
  TableTester tester = new TableTester(mp)

  @Test
  void testFutAct() {
    File dataFile =  new File("unit_tests_data/smyth/smyth383/smyth383fut-act.csv")
    assert tester.testFile(dataFile)
  }


  @Test
  void testFutMid() {
    File dataFile =  new File("unit_tests_data/smyth/smyth383/smyth383fut-mid.csv")
    assert tester.testFile(dataFile)
  }


  @Test
  void testFutPass() {
    File dataFile =  new File("unit_tests_data/smyth/smyth383/smyth383fut-pass.csv")
    assert tester.testFile(dataFile)
  }
}
