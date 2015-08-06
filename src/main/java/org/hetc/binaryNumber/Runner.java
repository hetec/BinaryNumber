package org.hetc.binaryNumber;

public class Runner {
	public static void main(String[] args) {
		BinaryNumber negThirty = BinaryNumber.of("10100").towsComplement();
		System.out.println(negThirty);
	}
}
