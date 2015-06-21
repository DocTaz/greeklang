package edu.holycross.shot.greekutils

import edu.unc.epidoc.transcoder.TransCoder
import java.text.Normalizer
import java.text.Normalizer.Form

class GreekMsString extends GreekString {

  // Temporary constructs for debugging:
  Integer SILENT = 0
  Integer WARN =  1
  Integer DEBUG = 2
  Integer VERBOSE = 3
  Integer debugLevel = 0

  static evilImpostorStopCodePoint = 183
  
  static highStop = "\u0387"
  static twoDots = "\u205A"
  static endOfScholion = "\u2051"
  
  /** Immutable set of punctuation characters allowed in MSS. */
  static msPunctuation = [
    highStop, endOfScholion , twoDots
    //    "·", "⁑" , "⁚"
  ]

  /** Floating macron character. */
  static macron = "\u0304"
  /** Floating breve character. */
  static breve = "\u0306"
  /** Floating diaeresis character. */
  static diaeresis = "\u0308"
  /** Floating grave character. */
  static floatingGrave = "\u0300"


  /** Immutable set of further quantity and breathing characters allowed in MSS. */
  static msQuantityOrBreathing = [
    macron,breve,diaeresis,floatingGrave
  ]


  String msBetaString
  String msUnicodeString
  
  GreekMsString(String srcString, String greekMapping)  {
    super(srcString, greekMapping, true)
    Integer debug = 0
    int lastCp = srcString.codePointCount(0, srcString.size() - 1)
    if (debug > 0) {
      System.err.println "Final codepoint in ${srcString} = " + srcString.codePointAt(lastCp)
    }
    // Slay the evil unicode equivalency of Ano Teleia and Mid Dot:
    if (srcString.codePointAt(lastCp) == evilImpostorStopCodePoint)  {
      throw new Exception("GreekMsString: Unicode 'middle dot' not allowed in Greek Strings: please use Greek Ano Teleia instead (Unicode x0387 == 903). ")
    }


    this.msUnicodeString = srcString
    
    TransCoder xcoder = new TransCoder()
    xcoder.setParser(greekMapping)
    xcoder.setConverter("BetaCode")
    
    Integer count = 0
    String betaString = xcoder.getString(srcString).toLowerCase()
    betaString = betaString.replaceAll("s1","s")


    if (debug > 0) {
      System.err.println "GreekMsString: ${srcString} -> ascii ${betaString}"
    }
    
    StringBuilder cleanString = new StringBuilder()
        
    if (betaString.size() > 0) {
      int max = betaString.codePointCount(0, betaString.size() - 1)
      int idx = 0
      while (idx <= max) {
	int cp = betaString.codePointAt(idx)
	if (cp != null) {
	  String s = new String(Character.toChars(cp))
	  if (GreekString.isValidChar(s)) {
	    cleanString.append(s)
	  }
	}
	idx = betaString.offsetByCodePoints(idx,1)	
      }
    } else {
      System.err.println "GreekMsString:warning: 0-length ascii form for " + srcString
    }
    this.msBetaString = cleanString.toString()
  }


  /** Determines if a single-character String
   * is a specially allowed MS punctuation character.
   * @param ch Character to examine.
   * @returns True if ch is a punctuation character.
   */
  static boolean isMsPunctuation (String ch) {
    boolean validPunct = false
    GreekMsString.msPunctuation.each { s ->
      if (s == ch) {
	validPunct = true
      }
    }
    [".", "," ].each { s ->
      if (s == ch) {
	validPunct = true
      }
    }
    
    return validPunct
  }


  /** Determines if a single-character String
   * is a specially allowed MS quantity or breathing character.
   * @param ch Character to examine.
   * @returns True if ch is a quantity or breathing character.
   */
  static boolean isMsQuantityOrBreathing (String ch) {
    boolean validChar = false
    GreekMsString.msQuantityOrBreathing.each { s ->
      if (s == ch) {
	validChar = true
      }
    }
    return validChar
  }


  /** Determines if a single-character String
   * is allowed in the orthography of Greek manuscripts.
   * @param ch Character to examine.
   * @returns True if ch is a valid character.
   */
  static boolean isValidMsChar(String ch) {
    if (
      (GreekString.isAlphabetic(ch)) 
      || (GreekString.isAccentOrBreathing(ch)) 
      || (GreekString.isQuantity(ch))
      || (GreekString.isPunctuation(ch))
      || (GreekString.isWhiteSpace(ch))
      || (ch == GreekString.diaeresis)
      || (ch == GreekString.asterisk)
      // Additional tests specific to MS orthography:
      || (GreekMsString.isMsPunctuation(ch))
      || (GreekMsString.isMsQuantityOrBreathing(ch))
    )  {
      return true
    } else {
      System.err.println("GreekMsString: invalid beta code character ${ch}")
      return false
    }
  }




  String toString() {
    return this.msUnicodeString
  }

  String toString(boolean asUnicode) {
    if (asUnicode) {
      return this.msUnicodeString
    } else {
      return this.msBetaString
    }
  }

}