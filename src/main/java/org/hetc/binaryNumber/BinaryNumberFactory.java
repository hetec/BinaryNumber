package org.hetc.binaryNumber;

/**
 * Created by patrick on 18.11.15.
 */
public class BinaryNumberFactory {

    public static BinaryNumber instanceOf(String binaryNumber){
        return BinaryNumber.of(binaryNumber);
    }
}
