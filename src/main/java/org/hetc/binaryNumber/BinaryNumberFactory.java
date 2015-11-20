package org.hetc.binaryNumber;

import java.math.BigInteger;

/**
 * Created by patrick on 18.11.15.
 */
public class BinaryNumberFactory {

    public BinaryNumberFactory (){

    }

    public BinaryNumber instanceOf(String binaryNumber){
        return BinaryNumber.of(binaryNumber);
    }

    public BinaryNumber instanceOf(long binaryNumber){
        return BinaryNumber.of(binaryNumber);
    }

    public BinaryNumber instanceOf(BigInteger binaryNumber){
        return BinaryNumber.of(binaryNumber);
    }

    public BinaryNumber instanceOf(byte[] binaryNumber){
        return BinaryNumber.of(binaryNumber);
    }

    public BinaryNumber instanceOf(byte[] binaryNumber, boolean isNegative){
        return BinaryNumber.of(binaryNumber, isNegative);
    }
}
