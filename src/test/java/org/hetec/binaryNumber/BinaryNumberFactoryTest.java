package org.hetec.binaryNumber;

import org.hetc.binaryNumber.BinaryNumber;
import org.hetc.binaryNumber.BinaryNumberFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class BinaryNumberFactoryTest {

    private static BinaryNumber binaryNumber;
    private static BinaryNumber negBinaryNumber;


    @BeforeClass
    public static void setUp(){
        binaryNumber = BinaryNumber.of("010101101");
        negBinaryNumber = BinaryNumber.of("-10101");
    }

    @Test
    public void testFactoryForStrings(){
        assertThat(new BinaryNumberFactory().instanceOf("010101101"), is(equalTo(binaryNumber)));
        assertThat(new BinaryNumberFactory().instanceOf("-10101"), is(equalTo(negBinaryNumber)));
    }

    @Test
    public void testFactoryForLong(){
        assertThat(new BinaryNumberFactory().instanceOf(173), is(equalTo(binaryNumber)));
        assertThat(new BinaryNumberFactory().instanceOf(-21), is(equalTo(negBinaryNumber)));
    }

    @Test
    public void testFactoryForBigInt(){
        assertThat(new BinaryNumberFactory().instanceOf(new BigInteger("173")), is(equalTo(binaryNumber)));
        assertThat(new BinaryNumberFactory().instanceOf(new BigInteger("-21")), is(equalTo(negBinaryNumber)));
    }

    @Test
    public void testFactoryForByteArray(){
        assertThat(new BinaryNumberFactory().instanceOf(new byte[]{0,1,0,1,0,1,1,0,1}), is(equalTo(binaryNumber)));
        assertThat(new BinaryNumberFactory().instanceOf(new byte[]{1,1,1,0,1,0,1,1}, true), is(equalTo(negBinaryNumber)));
    }
}