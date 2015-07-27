package org.hetc.binaryNumber;

import java.math.BigInteger;

import javax.management.BadBinaryOpValueExpException;
import javax.swing.plaf.multi.MultiButtonUI;
import javax.swing.plaf.synth.SynthScrollBarUI;

public class Runner {
	public static void main(String[] args) {
		BinaryNumber six = BinaryNumber.of(new BigInteger("-7"));
		BinaryNumber seven = BinaryNumber.of(new BigInteger("-6"));
		BinaryNumber zero = BinaryNumber.of(new BigInteger("0"));
		zero.removeLeadingZeros();
		System.out.println("Six: " + six);
		System.out.println((six.multiply(seven)).asBigInt());
		
	} 
}
