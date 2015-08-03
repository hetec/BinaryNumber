package org.hetec.binaryNumber;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.hetc.binaryNumber.BinaryNumber;
import org.junit.Test;

public class BinaryNumberGenericOperationsTest {

	//equals
	
		@Test
		public void testEqualsForDifferentLength(){
			BinaryNumber numOne = BinaryNumber.of(new byte[]{0,0,0,0,1,0,1,0,1,1});
			BinaryNumber numTwo = BinaryNumber.of(new byte[]{0,1,0,1,0,1,1});
			assertEquals(numOne, numTwo);
		}
		
		@Test
		public void testNotEqual(){
			BinaryNumber numOne = BinaryNumber.of(new byte[]{0,0,0,0,1,0,1,0,1,1});
			BinaryNumber numTwo = BinaryNumber.of(new byte[]{0,1,0,0,0,1,1});
			assertNotEquals(numOne, numTwo);
		}
		
		@Test
		public void testEqualWihtoutLeadingZeros(){
			BinaryNumber numOne = BinaryNumber.of(new byte[]{1,1});
			BinaryNumber numTwo = BinaryNumber.of(new byte[]{1,1});
			assertEquals(numOne, numTwo);
		}
		
		@Test
		public void testEqualForNegativeValue(){
			BinaryNumber numOne = BinaryNumber.of(new BigInteger("-5"));
			BinaryNumber numTwo = BinaryNumber.of(new BigInteger("-5"));
			assertEquals(numOne, numTwo);
		}
		
		@Test
		public void testNotEqualForNegativeValue(){
			BinaryNumber numOne = BinaryNumber.of(new BigInteger("-5"));
			BinaryNumber numTwo = BinaryNumber.of(new BigInteger("-7"));
			assertNotEquals(numOne, numTwo);
		}
		
		@Test
		public void testEqualForZero(){
			BinaryNumber numOne = BinaryNumber.of(new byte[]{0});
			BinaryNumber numTwo = BinaryNumber.of(new BigInteger("0"));
			assertEquals(numOne, numTwo);
		}
		
		@Test
		public void testNotEqualForZero(){
			BinaryNumber numOne = BinaryNumber.of(new byte[]{0});
			BinaryNumber numTwo = BinaryNumber.of(new BigInteger("-11"));
			assertNotEquals(numOne, numTwo);
		}
		
		@Test
		public void testTransivity(){
			BinaryNumber numOne = BinaryNumber.of(new BigInteger("11"));
			BinaryNumber numTwo = BinaryNumber.of(new BigInteger("11"));
			BinaryNumber numThree = BinaryNumber.of(new BigInteger("11"));
			assertTrue(numOne.equals(numTwo) && numTwo.equals(numThree) && numOne.equals(numThree));
		}
		
		@Test
		public void testConsistency(){
			BinaryNumber numOne = BinaryNumber.of(new BigInteger("11"));
			BinaryNumber numTwo = BinaryNumber.of(new BigInteger("11"));
			assertTrue(numOne.equals(numTwo));
			assertTrue(numOne.equals(numTwo));
		}
		
		@Test
		public void testReflexivity(){
			BinaryNumber numOne = BinaryNumber.of(new BigInteger("11"));
			assertTrue(numOne.equals(numOne));
		}
		
		@Test
		public void testSymmetry(){
			BinaryNumber numOne = BinaryNumber.of(new BigInteger("11"));
			BinaryNumber numTwo = BinaryNumber.of(new BigInteger("11"));
			assertTrue(numOne.equals(numTwo));
			assertTrue(numTwo.equals(numOne));
		}
		
		@Test
		public void testNotEqualForNullValue(){
			BinaryNumber numOne = BinaryNumber.of(new BigInteger("11"));
			BinaryNumber numTwo = null;
			assertFalse(numOne.equals(null));
		}
		
		//hashCode
		
		@Test
		public void testHashCodeForTwoObjects(){
			BinaryNumber numOne = BinaryNumber.of(new byte[]{0,0,0,1,0,1,1});
			BinaryNumber numTwo = BinaryNumber.of(new byte[]{0,0,0,1,0,1,1});
			assertEquals(numOne.hashCode(), numTwo.hashCode());
		}
		
		@Test
		public void testHashCodeForTwoObjectsWithDiffLength(){
			BinaryNumber numOne = BinaryNumber.of(new byte[]{0,1,0,1,1});
			BinaryNumber numTwo = BinaryNumber.of(new byte[]{0,0,0,1,0,1,1});
			assertEquals(numOne.hashCode(), numTwo.hashCode());
		}
		
		@Test
		public void testHashCodeNotEqualsForTwoDiffNumbers(){
			BinaryNumber numOne = BinaryNumber.of(new byte[]{0,1,0,1,0});
			BinaryNumber numTwo = BinaryNumber.of(new byte[]{0,0,0,1,0,1,1});
			assertNotEquals(numOne.hashCode(), numTwo.hashCode());
		}
		
		@Test
		public void testHashCodeInConjunctionWithEquals(){
			BinaryNumber numOne = BinaryNumber.of(new byte[]{0,1,0,1,1});
			BinaryNumber numTwo = BinaryNumber.of(new byte[]{0,0,0,1,0,1,1});
			assertEquals(numOne.equals(numTwo),numOne.hashCode() == numTwo.hashCode());
		}
		
		@Test
		public void testHashCodeInConjunctionWithEqualsTwoDiffNumbers(){
			BinaryNumber numOne = BinaryNumber.of(new byte[]{0,1,0,1,1});
			BinaryNumber numTwo = BinaryNumber.of(new byte[]{0,0,0,1,0,1,0});
			assertEquals(numOne.equals(numTwo),numOne.hashCode() == numTwo.hashCode());
		}
		
		@Test
		public void testHashCode(){
			BinaryNumber numOne = BinaryNumber.of(new byte[]{0,1,0,1,1});
			BinaryNumber numTwo = BinaryNumber.of(new byte[]{0,0,0,1,0,1,0});
			assertEquals(numOne.equals(numTwo),numOne.hashCode() == numTwo.hashCode());
		}
		
		//compareTo
		
		@Test
		public void testCompareToWithNumOneSmallerNumTwo(){
			BinaryNumber numOne = BinaryNumber.of(new BigInteger("6"));
			BinaryNumber numTwo = BinaryNumber.of(new BigInteger("8"));
			BinaryNumber zero = BinaryNumber.of(new BigInteger("0"));
			assertTrue(numOne.compareTo(numTwo) < 0);
			assertTrue(numTwo.compareTo(numOne) > 0);
			assertTrue(numOne.compareTo(zero) > 0);
		}
		
		@Test
		public void testCompareToWithEqualNumbersOfDiffLength(){
			BinaryNumber numOne = BinaryNumber.of(new byte[]{1,1,0,1});
			BinaryNumber numTwo = BinaryNumber.of(new byte[]{0,0,0,1,1,0,1});
			assertTrue(numOne.compareTo(numTwo) == 0);
		}
		
		@Test
		public void testCompareToInConjunctionWithEquals(){
			BinaryNumber numOne = BinaryNumber.of(new byte[]{1,1,0,1});
			BinaryNumber numTwo = BinaryNumber.of(new byte[]{0,0,0,1,1,0,1});
			assertTrue(numOne.compareTo(numTwo) == 0 && numOne.equals(numTwo));
		}
		
		@Test
		public void testCompareToSymmetry(){
			BinaryNumber numOne = BinaryNumber.of(new BigInteger("6"));
			BinaryNumber numTwo = BinaryNumber.of(new BigInteger("8"));
			assertEquals(numOne.compareTo(numTwo),-1 * (numTwo.compareTo(numOne)));
		}
		
		@Test
		public void testCompareToTransivity(){
			BinaryNumber numOne = BinaryNumber.of(new BigInteger("6"));
			BinaryNumber numTwo = BinaryNumber.of(new BigInteger("5"));
			BinaryNumber numThree = BinaryNumber.of(new BigInteger("4"));
			assertTrue(numOne.compareTo(numTwo) > 0 && numTwo.compareTo(numThree) > 0
					&& numOne.compareTo(numThree) > 0);
			assertTrue(numThree.compareTo(numTwo) < 0 && numTwo.compareTo(numOne) < 0
					&& numThree.compareTo(numOne) < 0);
		}
		
		//compareTo byte based
		
		@Test
		public void testCompareToWithoutLeadingZeros(){
			BinaryNumber nine = BinaryNumber.of(new byte[]{1,1,0,1});
			BinaryNumber twelve = BinaryNumber.of(new byte[]{1,1,1,0});
			BinaryNumber twelveV2 = BinaryNumber.of(new byte[]{1,1,1,0});
			int twelveGtNine = twelve.compareTo(nine);
			int twelveEqTwelveV2 = twelve.compareTo(twelveV2);
			int nineStTwelve= nine.compareTo(twelve);
			assertEquals(1, twelveGtNine);
			assertEquals(0, twelveEqTwelveV2);
			assertEquals(-1, nineStTwelve);
		}
		
		@Test
		public void testCompareToWithLeadingZeros(){
			BinaryNumber nine = BinaryNumber.of(new byte[]{0,0,0,1,1,0,1});
			BinaryNumber twelve = BinaryNumber.of(new byte[]{0,1,1,1,0});
			BinaryNumber twelveV2 = BinaryNumber.of(new byte[]{0,0,1,1,1,0});
			int twelveGtNine = twelve.compareTo(nine);
			int twelveEqTwelveV2 = twelve.compareTo(twelveV2);
			int nineStTwelve= nine.compareTo(twelve);
			assertEquals(1, twelveGtNine);
			assertEquals(0, twelveEqTwelveV2);
			assertEquals(-1, nineStTwelve);
		}
		
		@Test
		public void testCompareToZero(){
			BinaryNumber zero = BinaryNumber.of(new byte[]{0,0,0});
			BinaryNumber twelve = BinaryNumber.of(new byte[]{0,1,1,1,0});
			BinaryNumber zeroV2 = BinaryNumber.of(new byte[]{0});
			int twelveGtZero = twelve.compareTo(zero);
			int twelveGtZeroV2 = twelve.compareTo(zeroV2);
			int zeroStTwelve= zero.compareTo(twelve);
			assertEquals(1, twelveGtZero);
			assertEquals(1, twelveGtZeroV2);
			assertEquals(-1, zeroStTwelve);
		}
		
		@Test
		public void testCompareToWithNegNumbers(){
			BinaryNumber negTen = BinaryNumber.of(-10);
			BinaryNumber ten = BinaryNumber.of(10);
			BinaryNumber negSix = BinaryNumber.of(-6);
			int negTenStTen = negTen.compareTo(ten);
			int TenLtNegTen = ten.compareTo(negTen);
			int negTenEquals = negTen.compareTo(negTen);
			int negTenStNegSix = negTen.compareTo(negSix);
			assertEquals(-1, negTenStTen);
			assertEquals(1, TenLtNegTen);
			assertEquals(0, negTenEquals);
			assertEquals(-1, negTenStNegSix);
		}
		

}
