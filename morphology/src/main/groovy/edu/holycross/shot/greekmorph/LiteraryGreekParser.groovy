package edu.holycross.shot.greekmorph

import edu.holycross.shot.orthography.GreekString
import edu.holycross.shot.orthography.GreekOrthography
import edu.holycross.shot.orthography.GreekWord
import edu.holycross.shot.phonology.Accent
import edu.holycross.shot.phonology.AccentPattern
import edu.holycross.shot.phonology.Syllable
import edu.harvard.chs.cite.CiteUrn
import edu.harvard.chs.cite.CtsUrn

/** A Greek morphological parser.
*/
class LiteraryGreekParser implements GreekParser {

  Integer debug  = 10

  /** Implementation of accent-free Greek morphology
  * in a finite state transducer. */
  FstParser fstParser

  /** Manager for expanding collection IDs to full CITE URNs. */
  UrnManager urnManager

  /** Constructor with location of sfst binary and
  * a system for expanding abbreviated URN references.
  * @param fstBinary Full path to binary sfst (.a) file.
  * @param umgr UrnManager configured for collections cited
  * in stem lexica for this corpus.
  */
  LiteraryGreekParser(String fstBinary, UrnManager umgr) {
    fstParser = new FstParser(fstBinary)
    urnManager = umgr
  }


  // is this the best way to determine this?
  boolean isPreAccented(String inflectionClass) {
    switch (inflectionClass) {
      case "irregacc":
      case "eus_ews":
      return true
      break
      default:
      return false
      break
    }
  }


  /** Determines if form retrieved from FST parser should be considered a
   * match for GreekString gs.
    // Add accent to form retrieved from FST based on analysisInfo.
    // Method is true if accented form we create matches gs.
   * @param gs GreekString submitted for analysis.
   * @param analysisInfo Parsed results from FST parser.
   */
  boolean checkNounAccent(GreekString gs, FstAnalysisParser analysisInfo) {
    // Normalized, canonically accented form to compare with gs:
    GreekWord accented
    // Surface form from FST parser:
    GreekWord retrievedForm = new GreekWord(analysisInfo.getSurfaceStem() + analysisInfo.getSurfaceInflection())

    String inflectionTag = analysisInfo.getInflectionTag()

    // see if retrieved from is pre-accented.
    //inflectionTag
    if (isPreAccented(inflectionTag)) {
      System.err.println "${inflectionTag} class is already accented!"
      accented = retrievedForm
    } else {
      GreekString fstSurfaceString = new GreekString(analysisInfo.surfaceInflection)
      accented = LiteraryGreekNounAccent.getAccentedNounForm(fstSurfaceString, analysisInfo)
    }


    if (debug > 0 ) {
      System.err.println "Check noun accent by comparing ${accented} to ${gs}"
      System.err.println "(removing quanity markers to get " + accented.toString().replaceAll("[_^]","") + ")"
    }
    return (accented.toString().replaceAll("[_^]","") == gs.toString())
  }





  boolean checkAdjAccent(GreekString gs, FstAnalysisParser analysisInfo) {
    // Normalized, canonically accented form to compare with gs:
    GreekWord accented
    // Surface form from FST parser:
    GreekWord retrievedForm = new GreekWord(analysisInfo.getSurfaceStem() + analysisInfo.getSurfaceInflection())

    String inflectionTag = analysisInfo.getInflectionTag()
    System.err.println "ADJ: ${retrievedForm} with infl tag ${inflectionTag}"
    // see if retrieved from is pre-accented.
    //inflectionTag
    if (isPreAccented(inflectionTag)) {
      System.err.println "${inflectionTag} class is already accented!"
      accented = retrievedForm
    } else {
      GreekString fstSurfaceString = new GreekString(analysisInfo.surfaceInflection)
      System.err.println "Surface form of analysis: " + fstSurfaceString
      accented = LiteraryGreekAdjectiveAccent.getAccentedAdjForm(fstSurfaceString, analysisInfo)
    }


    if (debug > 0 ) {
      System.err.println "Check adjective accent by comparing ${accented} to ${gs}"
      System.err.println "(removing quanity markers to get " + accented.toString().replaceAll("[_^]","") + ")"
    }
    return (accented.toString().replaceAll("[_^]","") == gs.toString())
  }

  

  /** Adds the morphologically appropriate accent to the unaccented form
   * generated by the FST, and compares the result to the originally submitted
   * Greek string.
   * @param utf8String The originally sumitted Greek string to check.
   * @param analysisInfo Candidate morphological analysis.
   * @returns True if orginally submitted string matches FST candidate with
   * appropriate accent added.
   */
  boolean checkAccent(GreekString utf8String, FstAnalysisParser analysisInfo) {
    // depends on type of analysis
    AnalysisTriple triple = analysisInfo.getTriple()
    MorphForm form = triple.getMorphForm()



    switch (form.getAnalyticalType()) {

      // Have to check morphological data when dealing with persistent accent:
    case AnalyticalType.NOUN:
    return checkNounAccent(utf8String, analysisInfo)
    break
    case AnalyticalType.ADJECTIVE:
    return checkAdjAccent(utf8String, analysisInfo)
    break


    
    case AnalyticalType.INDECLINABLE:
    GreekWord retrievedForm = new GreekWord(analysisInfo.getSurfaceStem() + analysisInfo.getSurfaceInflection())
    return retrievedForm.toString().replaceAll("[_^]","") == utf8String.toString()
    break


    case AnalyticalType.CVERB:
    String candidateString = analysisInfo.getSurfaceStem() + analysisInfo.getSurfaceInflection()
    GreekWord retrievedForm = Accent.addRecessiveAccent(new GreekWord(candidateString))
    System.err.println "PARSER: candidate " + candidateString + " accented as " + retrievedForm
    return retrievedForm.toString().replaceAll("[_^]","")  == utf8String.toString()
    break

    default:
    System.err.println "MorphologicalParser: analytical type ${triple.morphForm.getAnalyticalType()} not yet implemented"
    return false
    break
    }
  }

  /** Gets a morphological analysis for a Greek string.
  * @param gkStr The string to analyze.
  * @returns A MorphologicalAnalysis object.
  */
  MorphologicalAnalysis parseGreekString(GreekOrthography gkStr) {
    ArrayList analysisList  = []
    if (debug > 0) {System.err.println "\nParsing " + gkStr}

    FstToken fstToken = new FstToken(gkStr)
    if (debug > 0) {System.err.println "MorphologicalAnalysis: submit ${fstToken}"}
    String parseOutput = fstParser.parseToken(fstToken)
    if (debug > 0) {System.err.println "MorphologicalAnalysis: got ${parseOutput}"}
    parseOutput.eachLine { l ->
      if (l[0] == ">") {
        // omit
      } else if (l ==~ /[Nn]o result.+/) {
        // omit
	
      } else {
        FstAnalysisParser fap = new FstAnalysisParser(l, urnManager)
        if (debug > 0) {System.err.println "parse with FAP: " + fap}
        if (checkAccent(gkStr,fap)) {
          analysisList.add(fap.getTriple())
        } else {
          if (debug > 0) {
            System.err.println "Reject ${gkStr}"
          }
        }
      }
    }
    return( new MorphologicalAnalysis(gkStr, analysisList))
  }




}
