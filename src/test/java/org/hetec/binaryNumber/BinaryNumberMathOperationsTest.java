package org.hetec.binaryNumber;
import static org.junit.Assert.assertEquals;


import org.hetc.binaryNumber.BinaryNumber;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import org.junit.BeforeClass;

public class BinaryNumberMathOperationsTest {

	private static BinaryNumber six;
	private static BinaryNumber minusSix;
	private static BinaryNumber seven;
	private static BinaryNumber zero;
	private static BinaryNumber minusSeven;
        private static BinaryNumber thirty;
        private static BinaryNumber minusThirty;

	@BeforeClass
	public static void setUp(){
            six = BinaryNumber.of(6);
            seven = BinaryNumber.of(7);
            zero = BinaryNumber.of(0);
            minusSix = BinaryNumber.of(-6);
            minusSeven = BinaryNumber.of(-7);
            thirty = BinaryNumber.of(30);
            minusThirty = BinaryNumber.of(-30);
	}

	//Addition

	@Test
	public void testAdditionWithTwoPosNumbers(){
            BinaryNumber thriteen = six.add(seven);
            BinaryNumber thriteen_sym = seven.add(six);
            assertThat(thriteen.asLong(), equalTo(13L));
            assertThat(thriteen_sym.asLong(), equalTo(13L));
	}
	

	@Test
	public void testAdditionWithOneNumberAndZero(){
            BinaryNumber sixPlusZero = six.add(zero);
            BinaryNumber sixPlusZero_sym = zero.add(six);
            BinaryNumber minusSixPlusZero = minusSix.add(zero);
            BinaryNumber zeroPlusMinusSix = zero.add(minusSix);
            assertThat(sixPlusZero.asLong(), equalTo(6L));
            assertThat(sixPlusZero_sym.asLong(), equalTo(6L));
            assertThat(minusSixPlusZero.asLong(), equalTo(-6L));
            assertThat(zeroPlusMinusSix.asLong(), equalTo(-6L));
	}

	@Test
	public void testAdditionWithNegAndPosNumber(){
            BinaryNumber one = minusSix.add(seven);
            BinaryNumber minusOne = six.add(minusSeven);
            assertThat(one.asLong(), equalTo(1L));
            assertThat(minusOne.asLong(), equalTo(-1L));
	}

	@Test
	public void testAdditionWithTwoNegNumbers(){
            BinaryNumber minusThirteen = minusSix.add(minusSeven);
            assertThat(minusThirteen.asLong(), equalTo(-13L));
	}
	
	//Subtraction

	@Test
	public void testSubtractWithNumberAndZero(){
            BinaryNumber sixMinusZero = six.subtract(zero);
            BinaryNumber zeroMinusSix = zero.subtract(six);
            assertThat(sixMinusZero.asLong(), equalTo(6L));
            assertThat(zeroMinusSix.asLong(), equalTo(-6L));
	}

	@Test
	public void testSubtractWithTwoPosNumbers(){
            BinaryNumber minusOne = six.subtract(seven);
            BinaryNumber one = seven.subtract(six);
            assertThat(minusOne.asLong(), equalTo(-1L));
            assertThat(one.asLong(), equalTo(1L));
	}

	@Test
	public void testSubtractWithOneNegAndPosNumber(){
            BinaryNumber thirteen = six.subtract(minusSeven);
            BinaryNumber minusThirteen = minusSeven.subtract(six);
            assertThat(thirteen.asLong(), equalTo(13L));
            assertThat(minusThirteen.asLong(), equalTo(-13L));
	}

	@Test
	public void testSubtractWithTwoNegNumbers(){
            BinaryNumber one = minusSix.subtract(minusSeven);
            BinaryNumber minusOne = minusSeven.subtract(minusSix);
            assertThat(one.asLong(), equalTo(1L));
            assertThat(minusOne.asLong(), equalTo(-1L));
	}

	//Multiplication

	@Test
	public void testMultiplyWithTwoPosNumbers(){
            BinaryNumber fourtyTwo = six.multiply(seven);
            BinaryNumber fourtyTwo_inverted = seven.multiply(six);
            assertThat(fourtyTwo.asLong(), equalTo(42L));
            assertThat(fourtyTwo_inverted.asLong(), equalTo(42L));
	}

	@Test
	public void testMultiplyWithOnePosAndNegNumber(){
            BinaryNumber minusFourtyTwo = six.multiply(minusSeven);
            BinaryNumber minusFourtyTwo_inverted = minusSeven.multiply(six);
            assertThat(minusFourtyTwo.asLong(), equalTo(-42L));
            assertThat(minusFourtyTwo_inverted.asLong(), equalTo(-42L));
	}

	@Test
	public void testMultiplyWithTwoNegNumbers(){
            BinaryNumber fourtyTwo = minusSix.multiply(minusSeven);
            BinaryNumber fourtyTwo_inverted = minusSeven.multiply(minusSix);
            assertThat(fourtyTwo.asLong(), equalTo(42L));
            assertThat(fourtyTwo_inverted.asLong(), equalTo(42L));
	}

	@Test
	public void testMultiplyWithNumberAndZero(){
            BinaryNumber local_zero = zero.multiply(seven);
            BinaryNumber local_zero_inverted = minusSix.multiply(zero);
            assertThat(local_zero.asLong(), equalTo(0L));
            assertThat(local_zero_inverted.asLong(), equalTo(0L));
	}

	@Test
	public void testMultiplyOfZeros(){
            BinaryNumber local_zero = zero.multiply(zero);
            assertThat(local_zero.asLong(), equalTo(0L));
	}

	//invert
	@Test
	public void testInvert(){
            BinaryNumber num = BinaryNumber.of(new byte[]{0,0,1,1,0,1});
            BinaryNumber inverted = num.invert();
            assertThat(BinaryNumber.of(new byte[]{1,1,1,1,0,0,1,0}, true), equalTo(inverted));
            assertThat(inverted.invert(), equalTo(num));
	}

	@Test
	public void testInvertZero(){
            BinaryNumber num = BinaryNumber.of(new byte[]{0});
            BinaryNumber inverted = num.invert();
            assertThat(BinaryNumber.of(new byte[]{1}, true), equalTo(inverted));
	}

	//removeLeadingZeros

	@Test
	public void testRemoveLeadingZeros(){
            BinaryNumber leadingZeros = BinaryNumber.of(new byte[]{0,0,1,1,0,1});
            BinaryNumber noLeadingZeros = BinaryNumber.of(new byte[]{1,1,0,1}, true);
            BinaryNumber onlyZeros = BinaryNumber.of(new byte[]{0,0,0,0});
            assertThat(BinaryNumber.of(new byte[]{1,1,0,1}, true), equalTo(leadingZeros.removeLeadingZeros()));
            assertThat(BinaryNumber.of(new byte[]{1,1,0,1}, true), equalTo(noLeadingZeros.removeLeadingZeros()));
            assertThat(BinaryNumber.of(new byte[]{0}), equalTo(onlyZeros.removeLeadingZeros()));
	}

	//twosComplement

	@Test
	public void testTwosComplement(){
            BinaryNumber leadingZeros = BinaryNumber.of(new byte[]{0,0,1,1,0,1});
            BinaryNumber local_zero = BinaryNumber.of(new byte[]{0});
            BinaryNumber noZeros = BinaryNumber.of(new byte[]{1,1,0,1});
            assertThat(BinaryNumber.of(new byte[]{1,1,1,1,0,0,1,1}, true), equalTo(leadingZeros.towsComplement()));
            assertThat(BinaryNumber.of(new byte[]{0}), equalTo(local_zero.towsComplement()));
            assertThat(BinaryNumber.of(new byte[]{1,1,1,1,0,0,1,1}, true), equalTo(noZeros.towsComplement()));
	}

	//divide

	@Test
	public void testDividendLessThandStartPart(){
        BinaryNumber five = thirty.divide(six);
        BinaryNumber local_zero = zero.divide(six);
        BinaryNumber four = thirty.divide(seven);
        BinaryNumber fiveTest = BinaryNumber.of(4);
        assertThat(BinaryNumber.of(5), equalTo(five));
        assertThat(BinaryNumber.of(0), equalTo(local_zero));
        assertThat(BinaryNumber.of(4), equalTo(four));
	}


	@Test
	public void testDivideForPosAndNegNumbers(){
            BinaryNumber minusFive = minusThirty.divide(six);
            BinaryNumber minusFive_inverted = thirty.divide(minusSix);
            BinaryNumber five = minusThirty.divide(minusSix);
            assertThat(BinaryNumber.of(-5), equalTo(minusFive));
            assertThat(BinaryNumber.of(-5), equalTo(minusFive_inverted));
            assertThat(BinaryNumber.of(5), equalTo(five));
    }

    @Test
    public void testDividendGreaterThandStartPart(){
        BinaryNumber three = BinaryNumber.of("10000").divide(BinaryNumber.of("101"));//16/5
        BinaryNumber minusThree = BinaryNumber.of(-16).divide(BinaryNumber.of(5));
        assertThat(BinaryNumber.of(3), equalTo(three));
        assertThat(BinaryNumber.of(-3), equalTo(minusThree));
    }

	@Test(expected = ArithmeticException.class)
	public void testDivideForDivequalToionByZero(){
		thirty.divide(zero);
	}
}
