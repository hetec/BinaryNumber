package org.hetc.binaryNumber;

import java.math.BigInteger;

import javax.management.BadBinaryOpValueExpException;
import javax.swing.plaf.multi.MultiButtonUI;
import javax.swing.plaf.synth.SynthScrollBarUI;

public class Runner {
	public static void main(String[] args) {
		BinaryNumber x = BinaryNumber.of(new BigInteger("1234"));
		BinaryNumber y = BinaryNumber.of(new BigInteger("11"));
		System.out.println(x);
		
	} 
}
