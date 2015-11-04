package org.hetec.binaryNumber;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.hetc.binaryNumber.BinaryNumber;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BinaryNumberGenerationTest {

	private static String one;
	private static String four;
        private static String four8Bits;
	private static String minusFour;
	private static String eight;
	private static String twenty;
	private static String thirtySix;
	private static String minusTwenty;
	private static String twelf;


	@BeforeClass
	public static void setUp() {
		one = "01";
		four = "0100";
                four8Bits = "00000100";
		thirtySix = "00100100";
		minusFour = "1100";
                twelf = "00001100";
		eight = "00001000";
		twenty = "00010100";
		minusTwenty = "11101100";
	}


	//ofByte
	@Test(expected=NumberFormatException.class)
	public void testGenerationFailsForWrongFromat(){
		BinaryNumber.of(new byte[]{1,2,0,0,1});
	}
        
        @Test(expected = IllegalArgumentException.class)
        public void testInvalidEmptyByteArray(){
            BinaryNumber.of(new byte[]{});
        }
        
	@Test
	public void testOfByteArrayForDiffBits(){
            assertThat(BinaryNumber.of(new byte[]{0,1,0,0}).toString(), equalTo(four));
            assertThat(BinaryNumber.of(new byte[]{0,0,0,1,0,0}).toString(), equalTo(four8Bits));
            assertThat(BinaryNumber.of(new byte[]{1,0,0}).toString(), equalTo(four));
            assertThat(BinaryNumber.of(new byte[]{1,0,0,1,0,0}).toString(), equalTo(thirtySix));
            assertThat(BinaryNumber.of(new byte[]{1,0,0,0}).toString(), equalTo(eight));
            assertThat(BinaryNumber.of(new byte[]{1,0,1,0,0}).toString(), equalTo(twenty));
            assertThat(BinaryNumber.of(new byte[]{0}).toString(), equalTo("0"));
            assertThat(BinaryNumber.of(new byte[]{1}).toString(), equalTo(one));
	}

	@Test
	public void testOfByteArrayNegNumbers(){
            assertThat(BinaryNumber.of(new byte[]{1,1,0,0}, true).toString(), equalTo(minusFour));
            assertThat(BinaryNumber.of(new byte[]{1,1,0,0}, false).toString(), equalTo(twelf));
            assertThat(BinaryNumber.of(new byte[]{0}, true).toString(), equalTo("0"));
            assertThat(BinaryNumber.of(new byte[]{1,1,1,0,1,1,0,0}, true).toString(), equalTo(minusTwenty));
            assertThat(BinaryNumber.of(new byte[]{0,0,1,1,1,0,1,1,0,0}, true).toString(), equalTo(minusTwenty));
	}

	//ofBigInt
	@Test
	public void testOfBigIntforPosNumbers(){
            assertThat(BinaryNumber.of(new BigInteger("0")).toString(), equalTo("0"));
            assertThat(BinaryNumber.of(new BigInteger("1")).toString(), equalTo(one));
            assertThat(BinaryNumber.of(new BigInteger("4")).toString(), equalTo(four));
            assertThat(BinaryNumber.of(new BigInteger("20")).toString(), equalTo(twenty));
	}

	@Test
	public void testOfBigIntForNegNumbers(){
            assertThat(BinaryNumber.of(new BigInteger("-4")).toString(), equalTo(minusFour));
            assertThat(BinaryNumber.of(new BigInteger("-20")).toString(), equalTo(minusTwenty));
	}


	//ofLong
	@Test
	public void testOfLongForPosNumbers(){
            assertThat(BinaryNumber.of(0).toString(), equalTo("0"));
            assertThat(BinaryNumber.of(1).toString(), equalTo(one));
            assertThat(BinaryNumber.of(4).toString(), equalTo(four));
            assertThat(BinaryNumber.of(20).toString(), equalTo(twenty));
	}
        
        @Test
	public void testOfLongForNegNumbers(){
            assertThat(BinaryNumber.of(-4).toString(), equalTo(minusFour));
            assertThat(BinaryNumber.of(-20).toString(), equalTo(minusTwenty));
	}

	@Test
	public void testOfLongLimits(){
            assertThat(BinaryNumber.of(Long.MAX_VALUE).asLong(), equalTo(9223372036854775807L));
            assertThat(BinaryNumber.of(Long.MIN_VALUE + 1).asLong(), equalTo(-9223372036854775807L));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testOfLongThrowExIfInputOutOfRange(){
            BinaryNumber.of(Long.MAX_VALUE + 1);
            BinaryNumber.of(Long.MIN_VALUE);
	}

	//string
        
        @Test(expected = IllegalArgumentException.class)
        public void testInvalidEmptyString(){
            BinaryNumber.of("");
        }
        
	@Test
	public void testOfStringWithZero(){
            assertThat(BinaryNumber.of("0").toString(), equalTo("0"));
            assertThat(BinaryNumber.of("1").toString(), equalTo(one));
            assertThat(BinaryNumber.of("0100").toString(), equalTo(four));
            assertThat(BinaryNumber.of("10100").toString(), equalTo(twenty));
	}

	@Test
	public void testOfStringForNegNumbers(){
            assertThat(BinaryNumber.of("-0100").toString(), equalTo(minusFour));
            assertThat(BinaryNumber.of("-10100").toString(), equalTo(minusTwenty));
                
	}

	@Test(expected = NumberFormatException.class)
	public void testOfStringForIllegalNegNumbers(){
            BinaryNumber.of(" 0100");
            BinaryNumber.of("ab0100");
	}

}
