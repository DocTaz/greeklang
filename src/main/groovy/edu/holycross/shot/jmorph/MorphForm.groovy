package edu.holycross.shot.jmorph



/**
 * A class identifying a morphological form.
 */
class MorphForm {

  // Temporary constructs for debugging:
  Integer SILENT = 0
  Integer WARN =  1
  Integer DEBUG = 2
  Integer VERBOSE = 3
  Integer debugLevel = 0



  /** Enumerated values for grammatical person. */
  enum Person {
    FIRST,SECOND,THIRD
  }
  /** Enumerated values for grammatical number. */
  enum GrammaticalNumber {
    SINGULAR, DUAL, PLURAL
  }
  /** Enumerated values for grammatical tense. */
  enum Tense {
    PRESENT,IMPERFECT,FUTURE,AORIST,PERFECT,PLUPERFECT
  }
  /** Enumerated values for categories of verb including
   * traditional grammatical mood and other inflectional groups
   * such as participles and verbal adjectives.
   */
  enum Mood {
    INDICATIVE,SUBJUNCTIVE,OPTATIVE,IMPERATIVE,PARTICIPLE,VADJ
  }
  /** Enumerated values for grammatical voice. */
  enum Voice {
    ACTIVE, MIDDLE, PASSIVE
  }
  /** Enumerated values for grammatical gender. */
  enum Gender {
    MASCULINE, FEMININE, NEUTER
  }
  /** Enumerated values for grammatical case. */
  enum GrammaticalCase {
    NOMINATIVE, GENITIVE, DATIVE, ACCUSATIVE, VOCATIVE
  }
  /** Enumerated values for parts of speech. */
  enum PartOfSpeech {
    NOUN, VERB, ADJECTIVE, ADVERB, CONJUNCTION, PARTICLE
  }


  /** Possibly null value for morphological form's person. */
  Person mPerson
  /** Possibly null value for morphological form's number. */
  GrammaticalNumber mNumber 
  /** Possibly null value for morphological form's tense. */
  Tense mTense
  /** Possibly null value for morphological form's category of verb.*/
  Mood mMood
  /** Possibly null value for morphological form's  voice. */
  Voice mVoice
  /** Possibly null value for morphological form's  gender. */
  Gender mGender
  /** Possibly null value for morphological form's  case. */
  GrammaticalCase mCase
  /** Possibly null value for morphological form's part of speech .*/
  PartOfSpeech mPos 



  /** Constructor initialized from colon-separated String.
   * @param srcString a String with 8 fields separated by colons.
   * The required sequence of values is:
   * 1. person
   * 2. number
   * 3. tense
   * 4. mood
   * 5. voice
   * 6. gender
   * 7. case
   * 8. part of speech
   * 
   * @throws Exception if invalid combinations of values are found.
   */
  MorphForm(String srcString) 
  throws Exception {
    def srcArray = srcString.split(/:/)
    if (srcArray.size() != 8) {
      throw new Exception("MorphForm: too few components in srcString ${srcString} (${srcArray.size()})")
    } 
    initForm(srcArray as ArrayList)
  }



  /** Constructor initialized from an ArrayList with 8 elements.
   * The order of the elements is the same as for a colon-delimited
   * String.
   * @param srcArray An ArrayList with 8 elements.
   */
  MorphForm(ArrayList srcArray) 
  throws Exception {
    if (srcArray.size() != 8) {
      throw new Exception("MorphForm: too few components in srcArray  ${srcArray} (${srcArray.size()})")
    } 
    initForm(srcArray)
  }



  /** Initializes all values for conjugated verb
   * or participle.
   * @param formArray ArrayList of 8 elements.
   * @throws Exception if invalid values or combination
   * of values are found.
   */
  void initVerb(ArrayList formArray) 
  throws Exception {

    String moodString = getMood(formArray).toUpperCase()

    if (moodString == "PARTICIPLE") {
      initParticiple(formArray)

    } else {
      try {
	// make sure we have a valid mood value:
	def m = Mood.valueOf(moodString)
      } catch (Exception e) {
	throw new Exception("MorphForm:initVerb: in ${formArray}, bad value for mood ${moodString}")
      }
      initConjugatedVerb(formArray)
    }
  }



  /** Initializes number, tense, voice, gender and case
   * values for a participle.
   * @param formArray ArrayList of 8 elements.
   * @throws Exception if invalid values or combination
   * of values are found.
   */
  void initParticiple(ArrayList formArray) 
  throws Exception {


    if (getPerson(formArray) != "") {
      throw new Exception("MorphForm:initParticiple:  in ${formArray}, person value ${getPerson(formArray)} should be null for participle.")
    }
    String numStr = getNumber(formArray)
    try {
      this.mNumber = GrammaticalNumber.valueOf(numStr.toUpperCase())
    } catch (Exception e) {
      throw new Exception("MorphForm:initParticiple:  in ${formArray}, bad value ${numStr} for number.")
    }

    String tenseStr = getTense(formArray)
    try {
      this.mTense = Tense.valueOf(tenseStr.toUpperCase())
    } catch (Exception e) {
      throw new Exception("MorphForm:initParticiple:  in ${formArray}, bad value ${tenseStr} for tense.")
    }


    String moodStr = getMood(formArray)
    try {
      this.mMood = Mood.valueOf(moodStr.toUpperCase())
    } catch (Exception e) {
      throw new Exception("MorphForm:initParticiple: in ${formArray}, bad value ${moodStr} for mood.")
    }

    String voiceStr = getVoice(formArray)
    try {
      this.mVoice = Voice.valueOf(voiceStr.toUpperCase())
    } catch (Exception e) {
      throw new Exception("MorphForm:initParticiple: in ${formArray}, bad value ${voiceStr} for voice.")
    }


    String genStr = getGender(formArray)
    try {
      this.mGender = Gender.valueOf(genStr.toUpperCase())
    } catch (Exception e) {
      throw new Exception("MorphForm:initParticiple:  in ${formArray}, bad value ${genStr} for gender.")
    }

    String caseStr = getCase(formArray)
    try {
      this.mCase =  GrammaticalCase.valueOf(caseStr.toUpperCase())
    } catch (Exception e) {
      throw new Exception("MorphForm:initParticiple: in ${formArray}, bad value ${caseStr} for case.")
    }


    String pos = getPos(formArray)
    if (pos == "verb") {
      this.mPos = PartOfSpeech.VERB
    } else {
      throw new Exception("MorphForm:initParticiple:  in ${formArray}, bad value ${pos} for part of speech.")
    }

  }



  /** Initializes person, number, tense, voice, and mood
   * values for a conjugated verb.
   * @param formArray ArrayList of 8 elements.
   * @throws Exception if invalid values or combination
   * of values are found.
   */
  void initConjugatedVerb(ArrayList formArray) 
  throws Exception {
    [getGender(formArray), getCase(formArray)].each { val ->
      if (val != "") {
	throw new Exception("MorphForm:initVerb:  in ${formArray}, value ${val} should be null for conjugated verb.")
      }
    }

    String persStr = getPerson(formArray)
    try {
      this.mPerson = Person.valueOf(persStr.toUpperCase())
    } catch (Exception e) {
      throw new Exception("MorphForm:initVerb:  in ${formArray}, bad value ${persStr} for person.")
    }

    String numStr = getNumber(formArray)
    try {
      this.mNumber = GrammaticalNumber.valueOf(numStr.toUpperCase())
    } catch (Exception e) {
      throw new Exception("MorphForm:initVerb:  in ${formArray}, bad value ${numStr} for number.")
    }

    String tenseStr = getTense(formArray)
    try {
      this.mTense = Tense.valueOf(tenseStr.toUpperCase())
    } catch (Exception e) {
      throw new Exception("MorphForm:initVerb:  in ${formArray}, bad value ${tenseStr} for tense.")
    }


    String moodStr = getMood(formArray)
    try {
      this.mMood = Mood.valueOf(moodStr.toUpperCase())
    } catch (Exception e) {
      throw new Exception("MorphForm:initVerb: in ${formArray}, bad value ${moodStr} for mood.")
    }

    String voiceStr = getVoice(formArray)
    try {
      this.mVoice = Voice.valueOf(voiceStr.toUpperCase())
    } catch (Exception e) {
      throw new Exception("MorphForm:initVerb: in ${formArray}, bad value ${voiceStr} for voice.")
    }

    String pos = getPos(formArray)
    if (pos == "verb") {
      this.mPos = PartOfSpeech.VERB
    } else {
      throw new Exception("MorphForm:initVerb:  in ${formArray}, bad value ${pos} for part of speech.")
    }
  }

  /** Initializes gender, case, number values
   * for a noun or adjective.
   * @param formArray ArrayList of 8 elements.
   * @throws Exception if invalid values or combination
   * of values are found.
   */
  void initSubstantive(ArrayList formArray) 
  throws Exception {

    // Check that no inappropriate fields are set:
    [getPerson(formArray), getTense(formArray), getMood(formArray), getVoice(formArray)].each { val ->
      if (val != "") {
	throw new Exception("MorphForm:initSubstantive:  in ${formArray}, value ${val} should be null for substantive.")
      }
    }



    // And conversely check that only appropriate values for required fields are set:
    String genStr = getGender(formArray)
    try {
      this.mGender = Gender.valueOf(genStr.toUpperCase())
    } catch (Exception e) {
      throw new Exception("MorphForm:initSubstantive:  in ${formArray}, bad value ${genStr} for gender.")
    }

    String caseStr = getCase(formArray)
    try {
      this.mCase =  GrammaticalCase.valueOf(caseStr.toUpperCase())
    } catch (Exception e) {
      throw new Exception("MorphForm:initSubstantive: in ${formArray}, bad value ${caseStr} for case.")
    }


    String numStr = getNumber(formArray)
    try {
      this.mNumber = GrammaticalNumber.valueOf(numStr.toUpperCase())
    } catch (Exception e) {n
      throw new Exception("MorphForm:initSubstantive:  in ${formArray}, bad value ${numStr} for number.")
    }

    String pos = getPos(formArray)
    if ((pos == "noun") || (pos == "adjective")) {
      this.mPos = PartOfSpeech.valueOf(pos.toUpperCase())
    } else {
      throw new Exception("MorphForm:initSubstantive:  in ${formArray}, bad value ${pos} for part of speech.")
    }
  }



  /** Initializes member properties.
   * @param formArray ArrayList of 8 elements.
   * @throws Exception if invalid combination
   * of values is found.
   */
  void initForm(ArrayList formArray) 
  throws Exception {

    switch (getPos(formArray)) {

    case "noun":
    case "adjective":
    initSubstantive(formArray)
    break

    case "verb":
    initVerb(formArray)
    break

    default:
    throw new Exception("MorphForm: could not initialize form. In ${formArray}, unrecognized part of speech #${getPos(formArray)}#")
    break
    }
    
  }




  /** Selects person value from formArray.
   * @param formArray ArrayList of 8 elements.
   * @returns Person value (1st element).
   * @throws Exception if ArrayList is wrong size.
   */
  static String getPerson(ArrayList formArray) 
  throws Exception {
    if (formArray.size() != 8) {
      throw new Exception("MorphForm:getCase:  formArray ${formArray} is wrong size (${formArray.size()})")
    }
    return formArray[0]
  }

  /** Selects number value from formArray.
   * @param formArray ArrayList of 8 elements.
   * @returns Gender value (2nd element).
   * @throws Exception if ArrayList is wrong size.
   */
  static String getNumber(ArrayList formArray) {
    if (formArray.size() != 8) {
      throw new Exception("MorphForm:getCase:  formArray ${formArray} is wrong size (${formArray.size()})")
    }
    return formArray[1]
  }


  /** Selects tense value from formArray.
   * @param formArray ArrayList of 8 elements.
   * @returns Tense value (3rd element).
   * @throws Exception if ArrayList is wrong size.
   */
  static String getTense(ArrayList formArray) {
    if (formArray.size() != 8) {
      throw new Exception("MorphForm:getCase:  formArray ${formArray}, is wrong size (${formArray.size()})")
    }
    return formArray[2]
  }



  /** Selects mood value from formArray.
   * @param formArray ArrayList of 8 elements.
   * @returns Mood value (4th element).
   * @throws Exception if ArrayList is wrong size.
   */
  static String getMood(ArrayList formArray) {
    if (formArray.size() != 8) {
      throw new Exception("MorphForm:getCase:  formArray ${formArray}  is wrong size (${formArray.size()})")
    }
    return formArray[3]
  }



  /** Selects voice value from formArray.
   * @param formArray ArrayList of 8 elements.
   * @returns Voice value (5th element).
   * @throws Exception if ArrayList is wrong size.
   */
  static String getVoice(ArrayList formArray) {
    if (formArray.size() != 8) {
      throw new Exception("MorphForm:getCase:  formArray ${formArray}  is wrong size (${formArray.size()})")
    }
    return formArray[4]
  }



  /** Selects gender value from formArray.
   * @param formArray ArrayList of 8 elements.
   * @returns Gender value (6th element).
   * @throws Exception if ArrayList is wrong size.
   */
  static String getGender(ArrayList formArray) {
    if (formArray.size() != 8) {
      throw new Exception("MorphForm:getCase:  formArray ${formArray} is wrong size (${formArray.size()})")
    }
    return formArray[5]
  }



  /** Selects case value from formArray.
   * @param formArray ArrayList of 8 elements.
   * @returns Case value (7th element).
   * @throws Exception if ArrayList is wrong size.
   */
  static String getCase(ArrayList formArray) 
  throws Exception {
    if (formArray.size() != 8) {
      throw new Exception("MorphForm:getCase:  formArray ${formArray} is wrong size (${formArray.size()})")
    }
    return formArray[6]
  }



  /** Selects part of speech value from formArray.
   * @param formArray ArrayList of 8 elements.
   * @returns Case value (8th element).
   * @throws Exception if ArrayList is wrong size.
   */
  static String getPos(ArrayList formArray) 
  throws Exception {
    if (formArray.size() != 8) {
      throw new Exception("MorphForm:getCase:  formArray ${formArray} is wrong size (${formArray.size()})")
    }
    return formArray[7]
  }







}