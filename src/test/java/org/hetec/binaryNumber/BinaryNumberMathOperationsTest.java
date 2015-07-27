package org.hetec.binaryNumber;
import static org.junit.Assert.*;

import java.math.BigInteger;

import org.hetc.binaryNumber.BinaryNumber;
import org.junit.Before;
import org.junit.Test;

public class BinaryNumberMathOperationsTest {

	private BinaryNumber six;
	private BinaryNumber negSix;
	private BinaryNumber seven;
	private BinaryNumber zero;
	private BinaryNumber negSeven;
	
	@Before
	public void setUp(){
		six = BinaryNumber.of(6);
		seven = BinaryNumber.of(7);
		zero = BinaryNumber.of(0);
		negSix = BinaryNumber.of(new BigInteger("-6"));
		negSeven = BinaryNumber.of(new BigInteger("-7"));
	}
	
	//Addition
	
	@Test
	public void testAdditionWithTwoPosNumbers(){
		BinaryNumber result = six.add(seven);
		BinaryNumber result_reverse = seven.add(six);
		assertEquals(new BigInteger("13"), result.asBigInt());
		assertEquals(new BigInteger("13"), result_reverse.asBigInt());
	}
	
	@Test
	public void testAdditionWithOneNumberAndZero(){
		BinaryNumber result = six.add(zero);
		BinaryNumber result_reverse = zero.add(six);
		assertEquals(new BigInteger("6"), result.asBigInt());
		assertEquals(new BigInteger("6"), result_reverse.asBigInt());
	}
	
	@Test
	public void testAdditionWithNegAndPosNumber(){
		BinaryNumber result = negSix.add(seven);
		BinaryNumber result2 = negSeven.add(six);
		assertEquals(new BigInteger("1"), result.asBigInt());
		assertEquals(new BigInteger("-1"), result2.asBigInt());
	}
	
	@Test
	public void testAdditionWithTwoNegNumbers(){
		BinaryNumber result = negSix.add(negSeven);
		assertEquals(new BigInteger("-13"), result.asBigInt());
	}
	
	//Subtraction
	
	@Test
	public void testSubtractWithNumberAndZero(){
		BinaryNumber result = six.subtract(zero);
		BinaryNumber result2 = zero.subtract(six);
		assertEquals("6", result.asBigInt().toString());
		assertEquals("-6", result2.asBigInt().toString());
	}
	
	@Test
	public void testSubtractWithTwoPosNumbers(){
		BinaryNumber result = six.subtract(seven);
		BinaryNumber result2 = seven.subtract(six);
		assertEquals("-1", result.asBigInt().toString());
		assertEquals("1", result2.asBigInt().toString());
	}
	
	@Test
	public void testSubtractWithOneNegAndPosNumber(){
		BinaryNumber result = six.subtract(negSeven);
		BinaryNumber result2 = negSeven.subtract(six);
		assertEquals("13", result.asBigInt().toString());
		assertEquals("-13", result2.asBigInt().toString());
	}
	
	@Test
	public void testSubtractWithTwoNegNumbers(){
		BinaryNumber result = negSix.subtract(negSeven);
		BinaryNumber result2 = negSeven.subtract(negSix);
		assertEquals("1", result.asBigInt().toString());
		assertEquals("-1", result2.asBigInt().toString());
	}
	
	//Multiplication
	
	@Test
	public void testMultiplyWithTwoPosNumbers(){
		BinaryNumber result = six.multiply(seven);
		BinaryNumber result2 = seven.multiply(six);
		assertEquals("42", result.asBigInt().toString());
		assertEquals("42", result2.asBigInt().toString());
	}
	
	@Test
	public void testMultiplyWithOnePosAndNegNumber(){
		BinaryNumber result = six.multiply(negSeven);
		BinaryNumber result2 = negSeven.multiply(six);
		assertEquals("-42", result.asBigInt().toString());
		assertEquals("-42", result2.asBigInt().toString());
	}
	
	@Test
	public void testMultiplyWithTwoNegNumbers(){
		BinaryNumber result = negSix.multiply(negSeven);
		BinaryNumber result2 = negSeven.multiply(negSix);
		assertEquals("42", result.asBigInt().toString());
		assertEquals("42", result2.asBigInt().toString());
	}
	
	@Test
	public void testMultiplyWithNumberAndZero(){
		BinaryNumber result = zero.multiply(seven);
		BinaryNumber result2 = negSix.multiply(zero);
		assertEquals("0", result.asBigInt().toString());
		assertEquals("0", result2.asBigInt().toString());
	}
	
	@Test
	public void testMultiplyOfZeros(){
		BinaryNumber result = zero.multiply(zero);
		assertEquals("0", result.asBigInt().toString());
	}

}
