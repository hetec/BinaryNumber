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
		

}
