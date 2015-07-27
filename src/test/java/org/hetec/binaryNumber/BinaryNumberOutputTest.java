package org.hetec.binaryNumber;

import static org.junit.Assert.*;

import org.hetc.binaryNumber.BinaryNumber;
import org.junit.Before;
import org.junit.Test;

public class BinaryNumberOutputTest {
	
	BinaryNumber thirtyTwo;
	BinaryNumber zero;
	BinaryNumber one;
	
	@Before
	public void setUp(){
		thirtyTwo = BinaryNumber.of(new byte[]{1,0,1,1,0,1});
		zero = BinaryNumber.of(new byte[]{0});
		one = BinaryNumber.of(new byte[]{1});
	}
	
	@Test
	public void testToBigIntForThirtyFive(){
		assertEquals("45", thirtyTwo.asBigInt().toString());
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
	public void testToStringWithThirtyTwo(){
		assertEquals("00101101", thirtyTwo.toString());
	}
	
	@Test
	public void testToStringWithThirtyOne(){
		assertEquals("01", one.toString());
	}
	
	@Test
	public void testToStringWithThirtyZero(){
		assertEquals("0", zero.toString());
	}
	

}
