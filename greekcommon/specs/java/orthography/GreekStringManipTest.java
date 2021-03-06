package orthography;


import edu.holycross.shot.orthography.GreekString;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

@RunWith(ConcordionRunner.class)
public class GreekStringManipTest {


    public boolean isAlphabetic(String str) {
	return GreekString.isAlphabetic(str);
    }

    public boolean isConsonant(String str) {
	return GreekString.isConsonant(str);
    }

    public boolean isVowel(String str) {
	return GreekString.isVowel(str);
    }


    public boolean isAccent(String str) {
	return GreekString.isAccent(str);
    }


    public boolean isBreathing(String str) {
	return GreekString.isBreathing(str);
    }



    public boolean isValid(String str) {
	return GreekString.isValidChar(str);
    }

    public boolean isPunctuation(String str) {
	return GreekString.isPunctuation(str);
    }



    public boolean isDiphthong(String str) {
	return GreekString.isDiphthong(str);
    }
}
