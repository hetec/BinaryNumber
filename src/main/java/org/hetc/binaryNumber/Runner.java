package org.hetc.binaryNumber;

import java.math.BigInteger;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class Runner {
	public static void main(String[] args) {
		BinaryNumber one =  BinaryNumber.of(new BigInteger("43756237429472934729472937489237423756243562783642974534753947594752394572307502937592375982375893759837598375893758937589237520972957"));
		BinaryNumber two = BinaryNumber.of(new BigInteger("4375623742947293472947293748923742375624356278364297443756237429472934729472937489237423756243562783642974"));
		BinaryNumber res = one.multiply(two);
		System.out.println(res);
		System.out.println("Number of digits: " + res.toString().length());
	
	} 
}
