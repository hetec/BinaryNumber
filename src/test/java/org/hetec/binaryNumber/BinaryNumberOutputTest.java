package org.hetec.binaryNumber;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.hetc.binaryNumber.BinaryNumber;
import org.junit.Before;
import org.junit.Test;

public class BinaryNumberOutputTest {

	BinaryNumber fourtyFive;
	BinaryNumber zero;
	BinaryNumber one;
	BinaryNumber minusTen;

	@Before
	public void setUp(){
		fourtyFive = BinaryNumber.of(new byte[]{1,0,1,1,0,1});
		zero = BinaryNumber.of(new byte[]{0});
		one = BinaryNumber.of(new byte[]{1});
		minusTen = BinaryNumber.of(-10);
	}

	//asBigInt
	@Test
	public void testToBigIntForThirtyFive(){
		assertEquals("45", fourtyFive.asBigInt().toString());
	}

	@Test
	public void testToBigIntForZero(){
		assertEquals("0", zero.asBigInt().toString());
	}

	@Test
	public void testToBigIntForOne(){
		assertEquals("1", one.asBigInt().toString());
	}

	@Test
	public void testToBigIntNegValue(){
		assertEquals("-10", minusTen.asBigInt().toString());
	}

	@Test
	public void testToStringWithThirtyTwo(){
		assertEquals("00101101", fourtyFive.toString());
	}

	@Test
	public void testToStringWithThirtyOne(){
		assertEquals("01", one.toString());
	}

	@Test
	public void testToStringWithThirtyZero(){
		assertEquals("0", zero.toString());
	}

	@Test
	public void testToStringWithNegValue(){
		assertEquals("11110110", minusTen.toString());
	}

	//asLong
	@Test
	public void testAsLong(){
		assertEquals(45L, fourtyFive.asLong());
	}

	@Test
	public void testAsLongNegValue(){
		assertEquals(-10, minusTen.asLong());
	}

	@Test
	public void testAsLongZero(){
		assertEquals(0, zero.asLong());
	}

	@Test(expected=IllegalStateException.class)
	public void testAsLongThrowExceptionForToLageInputValues(){
		//toLarge = Long.MAX_VALUE +1
		BinaryNumber tooLarge = BinaryNumber.of(new BigInteger("9223372036854775808"));
		BinaryNumber tooSmall = BinaryNumber.of(new BigInteger("-9223372036854775808"));
		tooLarge.asLong();
		tooSmall.asLong();
	}

	//asByteArray

	@Test
	public void testAsByteArray(){
		BinaryNumber five = BinaryNumber.of(5);
		assertArrayEquals(new byte[]{0,1,0,1}, five.asByteArray());
		assertArrayEquals(new byte[]{1,1,1,1,0,1,1,0}, minusTen.asByteArray());
	}


}
