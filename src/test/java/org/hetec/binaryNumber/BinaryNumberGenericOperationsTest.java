package org.hetec.binaryNumber;

import static org.junit.Assert.*;

import java.math.BigInteger;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.hetc.binaryNumber.BinaryNumber;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BinaryNumberGenericOperationsTest {
    //equals
    private static BinaryNumber ten;
    private static BinaryNumber minusTen;
    private static BinaryNumber minusTenClone;
    private static BinaryNumber tenClone;
    private static BinaryNumber four;
    private static BinaryNumber minusFour;
    private static BinaryNumber zero;
    private static BinaryNumber zeroClone;

    @BeforeClass
    public static void setUp(){
        ten = BinaryNumber.of(10);
        minusTen = BinaryNumber.of(-10);
        minusTenClone = BinaryNumber.of(-10);
        tenClone = BinaryNumber.of(10);
        four = BinaryNumber.of(4);
        minusFour = BinaryNumber.of(-4);
        zero = BinaryNumber.of(0);
        zeroClone = BinaryNumber.of(0);
    }
    
    @Test
    public void testEqualsForPosNumbers(){
        assertThat(ten, equalTo(tenClone));
        assertThat(ten, not(equalTo(four)));
        assertThat(ten, not(equalTo(zero)));
        assertThat(zero, equalTo(zeroClone));
    }

    @Test
    public void testEqualsForNegativeNumbers(){
        assertThat(minusTen, equalTo(minusTenClone));
        assertThat(minusTen, not(equalTo(minusFour)));
        assertThat(minusTen, not(equalTo(zero)));
    }

    @Test
    public void testNotEqualsForMixedNumbers(){
        assertThat(minusTen, not(equalTo(ten)));
        assertThat(minusFour, not(equalTo(ten)));
        
    }

    @Test
    public void testTransivity(){
        BinaryNumber testTen = BinaryNumber.of(10);
        assertThat(ten, equalTo(tenClone));
        assertThat(tenClone, equalTo(testTen));
    }

    @Test
    public void testConsequalTotency(){
        assertThat(ten, equalTo(tenClone));
        assertThat(ten, equalTo(tenClone));
    }

    @Test
    public void testReflexivity(){
        assertThat(ten, equalTo(ten));
    }

    @Test
    public void testSymmetry(){
        assertThat(ten, equalTo(tenClone));
        assertThat(tenClone, equalTo(ten));
    }

    @Test
    public void testEqualForNull(){
        assertThat(ten, notNullValue());
    }

    //hashCode

    @Test
    public void testHashCode(){
            assertThat(ten.hashCode(), equalTo(tenClone.hashCode()));
            assertThat((ten.hashCode()), not(equalTo(four.hashCode())));
            assertThat(minusTen.hashCode(), equalTo(minusTenClone.hashCode()));
            assertThat(minusTen.hashCode(), not(equalTo(minusFour.hashCode())));
            assertThat(ten.hashCode(), not(equalTo(minusFour.hashCode())));
            assertThat(ten.hashCode(), notNullValue());
    }

    @Test
    public void testHashCodeInConjunctionWithEquals(){
            assertThat(ten.equals(tenClone), equalTo(ten.hashCode() == tenClone.hashCode()));
            assertThat(ten.equals(four), equalTo(ten.hashCode() == four.hashCode()));
    }

    //compareTo

    @Test
    public void testCompareTo(){
            assertThat(ten.compareTo(four), greaterThanOrEqualTo(1));
            assertThat(four.compareTo(ten), lessThanOrEqualTo(-1));
            assertThat(ten.compareTo(zero), greaterThanOrEqualTo(1));
    }

    @Test
    public void testCompareToInConjunctionWithEquals(){
            assertThat(ten, equalTo(tenClone));
            assertThat(ten.compareTo(tenClone), equalTo(0));
    }

    @Test
    public void testCompareToSymmetry(){
            assertThat(four.compareTo(ten), equalTo(-1 * ten.compareTo(four)));
    }

    @Test
    public void testCompareToTransivity(){
            assertThat(four.compareTo(zero), greaterThanOrEqualTo(1));
            assertThat(ten.compareTo(four), greaterThanOrEqualTo(1));
            assertThat(ten.compareTo(zero), greaterThanOrEqualTo(1));
    }
}
