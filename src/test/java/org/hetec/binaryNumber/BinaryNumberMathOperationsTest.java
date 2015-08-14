package org.hetec.binaryNumber;
import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.hetc.binaryNumber.BinaryNumber;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class BinaryNumberMathOperationsTest {

	private BinaryNumber six;
	private BinaryNumber minusSix;
	private BinaryNumber seven;
	private BinaryNumber zero;
	private BinaryNumber minusSeven;

	@Before
	public void setUp(){
		six = BinaryNumber.of(6);
		seven = BinaryNumber.of(7);
		zero = BinaryNumber.of(0);
		minusSix = BinaryNumber.of(-6);
		minusSeven = BinaryNumber.of(-7);
	}

	//Addition

	@Test
	public void testAdditionWithTwoPosNumbers(){
		BinaryNumber thriteen = six.add(seven);
		BinaryNumber thriteen_sym = seven.add(six);
		assertThat(thriteen.asLong(), is(13L));
		assertThat(thriteen_sym.asLong(), is(13L));
	}
	

	@Test
	public void testAdditionWithOneNumberAndZero(){
		BinaryNumber sixPlusZero = six.add(zero);
		BinaryNumber sixPlusZero_sym = zero.add(six);
		BinaryNumber minusSixPlusZero = minusSix.add(zero);
		BinaryNumber zeroPlusMinusSix = zero.add(minusSix);
		assertThat(sixPlusZero.asLong(), is(6L));
		assertThat(sixPlusZero_sym.asLong(), is(6L));
		assertThat(minusSixPlusZero.asLong(), is(-6L));
		assertThat(zeroPlusMinusSix.asLong(), is(-6L));
	}

	@Test
	public void testAdditionWithNegAndPosNumber(){
		BinaryNumber one = minusSix.add(seven);
		BinaryNumber minusOne = six.add(minusSeven);
		assertThat(one.asLong(), is(1L));
		assertThat(minusOne.asLong(), is(-1L));
	}

	@Test
	public void testAdditionWithTwoNegNumbers(){
		BinaryNumber minusThirteen = minusSix.add(minusSeven);
		assertThat(minusThirteen.asLong(), is(-13L));
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
		BinaryNumber result = six.subtract(minusSeven);
		BinaryNumber result2 = minusSeven.subtract(six);
		assertEquals("13", result.asBigInt().toString());
		assertEquals("-13", result2.asBigInt().toString());
	}

	@Test
	public void testSubtractWithTwoNegNumbers(){
		BinaryNumber result = minusSix.subtract(minusSeven);
		BinaryNumber result2 = minusSeven.subtract(minusSix);
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
		BinaryNumber result = six.multiply(minusSeven);
		BinaryNumber result2 = minusSeven.multiply(six);
		assertEquals("-42", result.asBigInt().toString());
		assertEquals("-42", result2.asBigInt().toString());
	}

	@Test
	public void testMultiplyWithTwoNegNumbers(){
		BinaryNumber result = minusSix.multiply(minusSeven);
		BinaryNumber result2 = minusSeven.multiply(minusSix);
		assertEquals("42", result.asBigInt().toString());
		assertEquals("42", result2.asBigInt().toString());
	}

	@Test
	public void testMultiplyWithNumberAndZero(){
		BinaryNumber result = zero.multiply(seven);
		BinaryNumber result2 = minusSix.multiply(zero);
		assertEquals("0", result.asBigInt().toString());
		assertEquals("0", result2.asBigInt().toString());
	}

	@Test
	public void testMultiplyOfZeros(){
		BinaryNumber result = zero.multiply(zero);
		assertEquals("0", result.asBigInt().toString());
	}

	//invert
	@Test
	public void testInvert(){
		BinaryNumber num = BinaryNumber.of(new byte[]{0,0,1,1,0,1});
		BinaryNumber inverted = num.invert();
		assertEquals(BinaryNumber.of(new byte[]{1,1,1,1,0,0,1,0}), inverted);
		assertEquals(inverted.invert(), num);
	}

	@Test
	public void testInvertZero(){
		BinaryNumber num = BinaryNumber.of(new byte[]{0});
		BinaryNumber inverted = num.invert();
		assertEquals(BinaryNumber.of(new byte[]{1}), inverted);

	}

	//removeLeadingZeros

	@Test
	public void testRemoveLeadingZeros(){
		BinaryNumber num = BinaryNumber.of(new byte[]{0,0,1,1,0,1});
		BinaryNumber num1 = BinaryNumber.of(new byte[]{1,1,0,1});
		BinaryNumber num2 = BinaryNumber.of(new byte[]{0,0,0,0});
		assertEquals(BinaryNumber.of(new byte[]{1,1,0,1}), num.removeLeadingZeros());
		assertEquals(BinaryNumber.of(new byte[]{1,1,0,1}), num1.removeLeadingZeros());
		assertEquals(BinaryNumber.of(new byte[]{0}), num2.removeLeadingZeros());
	}

	//twosComplement

	@Test
	public void testTwosComplement(){
		BinaryNumber num = BinaryNumber.of(new byte[]{0,0,1,1,0,1});
		BinaryNumber num1 = BinaryNumber.of(new byte[]{0});
		BinaryNumber num2 = BinaryNumber.of(new byte[]{1,1,0,1});
		BinaryNumber twos0 = num.towsComplement();
		BinaryNumber twos1 = num1.towsComplement();
		BinaryNumber twos2 = num2.towsComplement();
		assertEquals(BinaryNumber.of(new byte[]{1,1,1,1,0,0,1,1}), twos0);
		assertEquals(BinaryNumber.of(new byte[]{0}), twos1);
		assertEquals(BinaryNumber.of(new byte[]{1,1,1,1,0,0,1,1}), twos2);

	}

	//divide

	@Test
	public void testDivideForPosNumbers(){
		BinaryNumber thirty = BinaryNumber.of(new byte[]{1,1,1,1,0});
		BinaryNumber twenty = BinaryNumber.of(new byte[]{1,0,1,0,0});
		BinaryNumber five = BinaryNumber.of(new byte[]{1,0,1});
		BinaryNumber six = thirty.divide(five);
		BinaryNumber four = twenty.divide(five);
		BinaryNumber one = thirty.divide(twenty);
		BinaryNumber zero = twenty.divide(thirty);
		assertEquals(BinaryNumber.of(new byte[]{0,1,1,0}), six);
		assertEquals(BinaryNumber.of(new byte[]{0,1,0,0}), four);
		assertEquals(BinaryNumber.of(new byte[]{1}), one);
		assertEquals(BinaryNumber.of(new byte[]{0}), zero);
	}

	@Test
	public void testDivideForPosAndNegNumbers(){
		BinaryNumber negThirty = BinaryNumber.of(-30);
		BinaryNumber thirty = BinaryNumber.of(30);
		BinaryNumber five = BinaryNumber.of(new byte[]{1,0,1});
		BinaryNumber negFive = BinaryNumber.of(-5);
		BinaryNumber negSixDirectionOne = negThirty.divide(five);
		BinaryNumber negSixDirectionTwo = thirty.divide(negFive);
		BinaryNumber six = negThirty.divide(negFive);
		assertEquals(BinaryNumber.of(-6), negSixDirectionOne);
		assertEquals(BinaryNumber.of(-6), negSixDirectionTwo);
		assertEquals(BinaryNumber.of(6), six);
	}

	@Test(expected = ArithmeticException.class)
	public void testDivideForDivisionByZero(){
		BinaryNumber zero = BinaryNumber.of(0);
		BinaryNumber thirty = BinaryNumber.of(30);
		thirty.divide(zero);
	}

	@Test
	public void testDivideForZeroAsNumerator(){
		BinaryNumber zero = BinaryNumber.of(0);
		BinaryNumber thirty = BinaryNumber.of(30);
		BinaryNumber zeroAsResult = zero.divide(thirty);
		assertEquals(BinaryNumber.of(0), zeroAsResult);
	}
}
