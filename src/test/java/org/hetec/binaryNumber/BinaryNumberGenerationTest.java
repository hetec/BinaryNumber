package org.hetec.binaryNumber;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.hetc.binaryNumber.BinaryNumber;
import org.junit.Before;
import org.junit.Test;

public class BinaryNumberGenerationTest {

	private String one;
	private String four;
	private String negFour;
	private String eight;
	private String twenty;
	
	
	@Before
	public void setUp() {
		one = "01";
		four = "0100";
		negFour = "1100";
		eight = "00001000";
		twenty = "00010100";
	}
	
	//ofByte
	@Test
	public void testOfByteArrayWithFourBitNumber(){
		BinaryNumber fourBit = BinaryNumber.of(new byte[]{0,1,0,0});
		assertEquals(four, fourBit.toString());
	}
	
	@Test
	public void testOfByteArrayWithFourBitNumberAndExtensionToEightBit(){
		BinaryNumber fourBit = BinaryNumber.of(new byte[]{1,0,0,0});
		assertEquals(eight, fourBit.toString());
	}
	
	@Test
	public void testOfByteArrayWithFiveBitNumber(){
		BinaryNumber fourBit = BinaryNumber.of(new byte[]{1,0,1,0,0});
		assertEquals(twenty, fourBit.toString());
	}
	
	@Test
	public void testOfByteArrayWithZero(){
		BinaryNumber fourBit = BinaryNumber.of(new byte[]{0});
		assertEquals("0", fourBit.toString());
	}
	
	@Test
	public void testOfByteArrayWithOne(){
		BinaryNumber fourBit = BinaryNumber.of(new byte[]{1});
		assertEquals(one, fourBit.toString());
	}
	
//	@Test
//	public void testOfByteArrayWithNegFour(){
//		BinaryNumber fourBit = BinaryNumber.of(new byte[]{1,1,0,0});
//		assertEquals(negFour, fourBit.toString());
//	}
	
	//ofBigInt
	@Test
	public void testOfBigIntWithZero(){
		BinaryNumber fourBit = BinaryNumber.of(new BigInteger("0"));
		assertEquals("0", fourBit.toString());
	}
	
	@Test
	public void testOfBigIntWithOne(){
		BinaryNumber fourBit = BinaryNumber.of(new BigInteger("1"));
		assertEquals(one, fourBit.toString());
	}
	
	@Test
	public void testOfBigIntWithFour(){
		BinaryNumber fourBit = BinaryNumber.of(new BigInteger("4"));
		assertEquals(four, fourBit.toString());
	}
	
	@Test
	public void testOfBigIntWithEigth(){
		BinaryNumber fourBit = BinaryNumber.of(new BigInteger("8"));
		assertEquals(eight, fourBit.toString());
	}
	
	@Test
	public void testOfBigIntWithTwenty(){
		BinaryNumber fourBit = BinaryNumber.of(new BigInteger("20"));
		assertEquals(twenty, fourBit.toString());
	}
	
	@Test
	public void testOfBigIntWithNegativeFour(){
		BinaryNumber fourBit = BinaryNumber.of(new BigInteger("-4"));
		assertEquals(negFour, fourBit.toString());
	}
	
	
	//ofLong
	@Test
	public void testOfLongWithZero(){
		BinaryNumber fourBit = BinaryNumber.of(0);
		assertEquals("0", fourBit.toString());
	}
	
	@Test
	public void testOfLongWithOne(){
		BinaryNumber fourBit = BinaryNumber.of(1);
		assertEquals(one, fourBit.toString());
	}
	
	@Test
	public void testOfLongWithFour(){
		BinaryNumber fourBit = BinaryNumber.of(4);
		assertEquals(four, fourBit.toString());
	}
	
	@Test
	public void testOfLongWithEight(){
		BinaryNumber fourBit = BinaryNumber.of(8);
		assertEquals(eight, fourBit.toString());
	}
	
	@Test
	public void testOfLongWithTwenty(){
		BinaryNumber fourBit = BinaryNumber.of(20);
		assertEquals(twenty, fourBit.toString());
	}
	
	@Test
	public void testOfStringWithZero(){
		BinaryNumber fourBit = BinaryNumber.of("0");
		assertEquals("0", fourBit.toString());
	}
	
	@Test
	public void testOfStringWithOne(){
		BinaryNumber fourBit = BinaryNumber.of("1");
		assertEquals(one, fourBit.toString());
	}
	
	@Test
	public void testOfStringWithFour(){
		BinaryNumber fourBit = BinaryNumber.of("0100");
		assertEquals(four, fourBit.toString());
	}
	
	@Test
	public void testOfStringWithEight(){
		BinaryNumber fourBit = BinaryNumber.of("1000");
		assertEquals(eight, fourBit.toString());
	}
	
	@Test
	public void testOfStringWithTwenty(){
		BinaryNumber fourBit = BinaryNumber.of("10100");
		assertEquals(twenty, fourBit.toString());
	}

}
