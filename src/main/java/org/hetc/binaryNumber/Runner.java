package org.hetc.binaryNumber;

import java.math.BigInteger;
import java.util.Arrays;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class Runner {
	public static void main(String[] args) {
		byte[] n = new byte[]{1,1,1,1,0};
		byte[] d = new byte[]{1,0,1};
		BinaryNumber fourtyFive = BinaryNumber.of(n);
		BinaryNumber five = BinaryNumber.of(d);
		BinaryNumber res = fourtyFive.divide(five);
		System.out.println("FINAL RESULT: " + res.asLong());
		
//		byte[] one = new byte[]{0,0,0,1};
//		byte[] two = new byte[]{0};
//		System.out.println(Arrays.toString(
//				BinaryNumber.internalSubtract(one, two)));
	} 
}
