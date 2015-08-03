package org.hetc.binaryNumber;

import java.math.BigInteger;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class Runner {
	public static void main(String[] args) {
		BinaryNumber bin = BinaryNumber.of("100010101000101");
		BinaryNumber bin1= BinaryNumber.of("0101");
		System.out.println(bin.compareTo(bin1));
		System.out.println(bin.asBigInt());
//		System.out.println(bin);
//		System.out.println(bin.asLong());
	
	} 
}
