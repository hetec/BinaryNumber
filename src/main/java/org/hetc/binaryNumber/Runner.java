package org.hetc.binaryNumber;

import java.math.BigInteger;
import java.util.Arrays;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class Runner {
	public static void main(String[] args) {
		BinaryNumber negThirty = BinaryNumber.of(-30);
		BinaryNumber five = BinaryNumber.of(new byte[]{1,0,1});
		BinaryNumber negSix = negThirty.divide(five);
		System.out.println(negSix);
	} 
}
