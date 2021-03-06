package orthography.milesian;


import org.concordion.integration.junit3.ConcordionTestCase;

import edu.holycross.shot.orthography.MilesianInteger;
import edu.holycross.shot.orthography.MilesianString;
import java.lang.StringBuffer;

public class MilesianIntegerTest extends ConcordionTestCase {

    public Integer getDigitValue(String digit)
    throws Exception {
	StringBuffer buff = new StringBuffer(digit);
	int cp = buff.codePointAt(0);
	return MilesianInteger.getDigitValue(cp);
    }

    public Integer milesianToInteger(String str)
    throws Exception {
	MilesianString ms = new MilesianString(str);
	MilesianInteger mi = new MilesianInteger(ms);
	return mi.toInteger();
	//	return MilesianInteger.toInteger(mi.codePoints);
    }
}
