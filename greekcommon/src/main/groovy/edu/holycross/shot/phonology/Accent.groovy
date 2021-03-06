package edu.holycross.shot.phonology

import edu.holycross.shot.orthography.GreekWord
import edu.holycross.shot.orthography.GreekString

/** A class for working with the movable accent of GreekWords. */
class Accent {

  /** Regex to check string representation of a syllable for diphthong or long vowel. */
  static java.util.regex.Pattern syllLongByNature =  ~/.*(ai|oi|ei|au|eu|ou|hu|wu|ui|[hw_]).*/



  static boolean hasAccent(GreekWord gw) {
    def count = gw.toString().findAll {
     Phonology.isAccent(it)
    }
    return (count.size() > 0)
  }

  static GreekString removeMultipleAccents(GreekString gs) {
    def count = gs.toString().findAll {
     Phonology.isAccent(it)
    }
    if (count.size() > 1){
      def removed = 0
      StringBuilder bldr = new StringBuilder()
      String str = gs.toString()
      Integer index = str.size() - 1
      Integer max = count.size() - 1
      while ((index >= 0) ) {
        String ch = str[index]
        // if it's not an accent, add it to the builder
        if  (Phonology.isAccent(ch)) {
          if  (removed == max) {
          bldr.append(ch)
          }
          removed++
        } else {
          bldr.append(ch)
        }
        index--
      }

      return new GreekString(bldr.toString().reverse())
    } else {
      return gs
    }
}

  /** Converts grave accent to acute.
  * @param gs Accented GreekString to convert.
  * @returns A GreekString with any grave converted
  * to acute accent.
  */
  static GreekString flipGrave(GreekString gs) {
    String flipped = gs.toString().replaceFirst('\\\\', '/')
    return new GreekString(flipped)
  }

  /** Normalizes the accentuation on a GreekString by
  * removing multiple accents and converting final grave
  * accents to acute.
  * @param gs GreekString to normalize.
  * @returns A GreekString in normalized accentuation.
  */
  static GreekString normalizeAccent(GreekString gs) {
      GreekString reduced = removeMultipleAccents(gs)
      return (flipGrave(reduced))
    }


  /** Removes accents from a GreekString.
  * @param gs GreekString to strip accents from.
  * @return A GreekString with accents removed.
  */
  static GreekString stripAccents(GreekString gs) {
    return new GreekString(gs.toString().findAll {
      ! Phonology.isAccent(it)
      }.join(''))
  }


  /** Removes accents from a GreekWord.
  * @param gw GreekWord to strip accents from.
  * @return A GreekWord with accents removed.
  */
  static GreekWord stripAccents(GreekWord gw) {
    return new GreekWord(gw.toString().findAll {
      ! Phonology.isAccent(it)
      }.join(''))
  }


  /** Places a given accent character on a single syllable by
  * reading from the end of the syllable back until a vowel is
  * found.
  * @param syllable A syllable to accent, as a GreekString.
  * @param accentChar Accent character to add.
  * @returns A String representation of the accented syllable.
  */
  static String accentSyllable(GreekString syllable, String accentChar) {
    return accentSyllable(syllable.toString(), accentChar)
  }


  /** Places a given accent character on a single syllable by
  * reading from the end of the syllable back until a vowel is
  * found.
  * @param syllable ASCII String representing a single syllable,
  * e.g., as returned by GreekWord class' getSyllables method.
  * @param accentChar One of "/" or "=".
  * @returns String with accent character inserted.
  */
  static String accentSyllable(String syllable, String accentChar) {
    String accentedSyllable = ""
    boolean noAccent = true
    Integer index = syllable.size() - 1
    Integer max = index
    while ((index >= 0) && (noAccent)) {
      String ch = syllable[index]

      if ((Phonology.isVowel(ch)) || (Phonology.isBreathing(ch)) || Phonology.isQuantity(ch)) {
	if (ch != "|") {
	  if (index == syllable.size() - 1) {
	    accentedSyllable = syllable  + accentChar
	  } else {
	    Integer nextIndex = index + 1
	    accentedSyllable = syllable[0..index] + accentChar + syllable[nextIndex..max]
	  }
	  noAccent = false
	}
      }
      index--
    }
    if (noAccent) {
      throw new Exception("Accent: no vowel found in ${syllable}")
    } else {
      return accentedSyllable
    }
  }



  /** Adds persistent accent on penult to a GreekWord.
  * @param gw Unaccented form to accent.
  * @returns A GreekWord with accent added.
  * @throws Exception if gw does not have at least two syllables.
  */
  static GreekWord addPenultAccent(GreekWord gw) {
    return addPenultAccent(gw, false)
  }


  static GreekWord addPenultAccent(GreekWord gw, boolean overrideShortDiphthong) {
    def syllables = gw.getSyllables()
    if (syllables.size() < 2){
      throw new Exception("Accent: cannot accent penult of ${gw}. Too few syllables.")
    }
    //println "No. syllables in ${gw}: " + syllables.size()
    Integer lastIndex = syllables.size() - 1
    String lastSyll = syllables[lastIndex]
    Integer penultIdx = lastIndex - 1
    String penult = syllables[penultIdx]

    if (hasFinalDiphthong(gw) && (!overrideShortDiphthong)) {
      // treat as short:  Smyth 169
      if (penult ==~ syllLongByNature ) {
        // properispomenon
	syllables[penultIdx] = accentSyllable(penult, "=")

      } else {
        // paroxytone:
	syllables[penultIdx] = accentSyllable(syllables[penultIdx], "/")
      }

    } else if (lastSyll ==~ syllLongByNature) {
      // last syllable long, accent must be paroxytone:
      syllables[penultIdx] = accentSyllable(penult, "/")

    } else {
      // last syllable short: check length of penult
      if (penult ==~ syllLongByNature ) {
        // properispomenon
	syllables[penultIdx] = accentSyllable(penult, "=")

      } else {
        // paroxytone:
	syllables[penultIdx] = accentSyllable(syllables[penultIdx], "/")
      }
    }
    return new GreekWord(syllables.join(""))
  }




  static GreekWord addRecessiveAccentShortFinal(GreekWord gw) {
    def syllables = gw.getSyllables()
    Integer lastIndex = syllables.size() - 1
    String lastSyll = syllables[lastIndex]

    switch(lastIndex) {
      case 0:
      syllables[lastIndex] = accentSyllable(syllables[lastIndex], "/")
      break

      case 1:

      Integer penultIdx = lastIndex - 1
      String penult = syllables[penultIdx]
      if (penult ==~ syllLongByNature ) {
        syllables[penultIdx] = accentSyllable(syllables[penultIdx], "=")
      } else {
        syllables[penultIdx] = accentSyllable(syllables[penultIdx], "/")
      }
      break

      default:
      Integer antepenultIdx = lastIndex - 2
      syllables[antepenultIdx] = accentSyllable(syllables[antepenultIdx], "/")
      break
    }
    return new GreekWord(syllables.join(""))
  }



  /** Adds recessive accent to a GreekWord.
  * @param gw Unaccented form to accent.
  * @returns A GreekWord with accent added.
  */
  static GreekWord addRecessiveAccent(GreekWord gw) {
    return addRecessiveAccent(gw, false)
  }
  static GreekWord addRecessiveAccent(GreekWord gw, boolean overrideShortDiphthong) {
    def syllables = gw.getSyllables()
    Integer lastIndex = syllables.size() - 1
    String lastSyll = syllables[lastIndex]

    if (hasFinalDiphthong(gw) && (!overrideShortDiphthong)) {
      // treat as short:  Smyth 169
      return addRecessiveAccentShortFinal(gw)
    } else if (lastSyll ==~ syllLongByNature) {
      // last syllable long:
      switch(lastIndex) {
        case 0:
        syllables[lastIndex] = accentSyllable(syllables[lastIndex], "=")
        break

        default:
        Integer penultIdx = lastIndex - 1
        syllables[penultIdx] = accentSyllable(syllables[penultIdx], "/")
        break
      }
      return new GreekWord(syllables.join(""))
    } else {
      // last syllable short
      return addRecessiveAccentShortFinal(gw)


    }
  }

  // makes best guess at what accent to apply to what syllable
  // based on accent pattern and string content of greekword

  static GreekWord accentWord(GreekWord gw, AccentPattern acc) {
    //System.err.println "Add accent to ${gw}, ${acc}"
    switch (acc) {
      case AccentPattern.RECESSIVE:
      return addRecessiveAccent(gw)
      break

      case AccentPattern.PENULT:
      return addPenultAccent(gw)
      break


      case AccentPattern.ULTIMA:
      throw new Exception("Accent: cannot accent ultima without morphological information about ${gw}.")
      break
    }
  }



  // Smyth 169 : final -ai and -oi are short
  static boolean hasFinalDiphthong(GreekWord gs) {
    String ascii = gs.toString()
    if ((ascii ==~ /.+ai$/) || (ascii ==~ /.+oi$/)) {
      return true
    } else {
      return false
    }
  }

}
