package edu.holycross.shot.phonology

import edu.holycross.shot.orthography.GreekWord
import edu.holycross.shot.orthography.GreekString

/** A class for working with the phonology of GreekWords. */
class Phonology {

  /** Immutable set of consonant characters. */
  static consonant = [
    'b','g','d',
    'z','q','k',
    'l','m','n',
    'c','p','r',
    's','t','f',
    'x','y'
  ]

  /** Immutable set of vowel characters. */
  static vowel = [
    'a', 'e','h',
    'i','o','u',
    'w','|'
  ]

  /** Immutable set of breathing characters. */
  static breathing = [')','(']

  /** Immutable set of accent characters. */
  static accent = ['/','\\','=']

  // these are not really orthographic, are they?
  /** Immutable set of vowel quantity markers. */
  static quantity = ['_','^']

  /** Diaeresis in ascii. */
  static String diaeresis = "+"

  /** Elision mark in ascii. */
  static String elision = "'"


  /** Pairs of vowels forming diphthongs. */
  static diphthong = [
    "ai", "ei","oi",
    "a|", "h|", "w|",
    "au","eu", "ou",
    "hu", "ui"
  ]



  static boolean isElision(String ch) {
    return ch == elision
  }

  static boolean isBreathing(String ch) {
    return breathing.contains(ch)
  }

  static boolean isAccent(String ch) {
    return accent.contains(ch)
  }

  static boolean isConsonant(String ch) {
    return consonant.contains(ch)
  }

  static boolean isVowel(String ch) {
    return vowel.contains(ch)
  }

  static boolean isDiphthong(String ch) {
    return diphthong.contains(ch)
  }

  static boolean isQuantity(String ch) {
    return quantity.contains(ch)
  }

  static GreekString stripAccents(GreekString gs) {
    return new GreekString(gs.toString().findAll {
      ! Phonology.isAccent(it)
      }.join(''))
  }

  static GreekWord stripAccents(GreekWord gs) {
    return new GreekWord(gs.toString().findAll {
      ! Phonology.isAccent(it)
      }.join(''))
  }



}
