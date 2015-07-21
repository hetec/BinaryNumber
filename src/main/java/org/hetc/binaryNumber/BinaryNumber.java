package org.hetc.binaryNumber;

import java.math.BigInteger;

public final class BinaryNumber {
	private final byte[] binary;;
	private final int len;	
	
	private BinaryNumber(byte[] binary) {
		int index = 0;
		byte[] tmp = new byte[binary.length];
		for(byte positon : binary){
			if(positon > 1 || positon < 0)
				throw new IllegalArgumentException("The binary number must not contain numbers different to 0 and 1");
			tmp[index] = binary[index];
			index++;
		}
		this.len = tmp.length;
		this.binary = tmp;
	}
	
	public static BinaryNumber of(byte[] binary){
		byte[] bin = extendToNextTowsExponent(binary);
		return new BinaryNumber(bin);
	}
	
	public static BinaryNumber of(BigInteger bigInt){
		boolean isNegative = false;
		if(bigInt.compareTo(new BigInteger("0")) < 0){
			isNegative = true;
			bigInt = bigInt.negate();
		}
		int size = getNeededArraySizeForBigInt(bigInt);
		byte[] binNumber = new byte[size];
		calcBinaryNumberOfBigInt(bigInt, binNumber, binNumber.length);
		binNumber = extendToNextTowsExponent(binNumber);
		if(isNegative){
			binNumber = internalTowsComplement(binNumber);
		}
		return new BinaryNumber(binNumber);
	}
	
	public static BinaryNumber of(String binary){
		byte[] bin = new byte[binary.length()];
		char[] digits = binary.toCharArray();
		for(int i = 0; i < binary.length(); i++){
			bin[i] = Byte.parseByte(String.valueOf(digits[i]));
		}
		bin = extendToNextTowsExponent(bin);
		return new BinaryNumber(bin);
	}
	
	public static BinaryNumber of(long dezimal){
		int size = getNeededArraySizeForDezimal(dezimal);
		byte[] binNumber = new byte[size];
		calcBinaryNumberOfLong(dezimal, binNumber, binNumber.length);
		binNumber = extendToNextTowsExponent(binNumber);
		return new BinaryNumber(binNumber);
	}
	
	
	public BinaryNumber add(BinaryNumber bin){
		return new BinaryNumber(internalAdd(this.binary,bin.binary));
	}
	
	public BinaryNumber round(int precision){
		int p = countLeadingZeros(this.binary) + precision;
		System.out.println("INSERTED P: " + p);
		return new BinaryNumber(roundBin(this.binary,p));
	}
	
	private static byte[] roundBin(byte[] bin, int precision){
		if(precision > bin.length || precision == 0){
			return bin;
		}
		byte[] preNum = new byte[bin.length];
		printArrayAsInlineString(bin);
		preNum[precision] = 1;
		printArrayAsInlineString(preNum);
		bin = internalAdd(bin, preNum);
		printArrayAsInlineString(bin);
		byte[] result = new byte[precision];
		for(int i=0; i < precision; i++){
			result[i] = bin[i];
		}
		printArrayAsInlineString(result);
		return result;
		
	}
	
	private static boolean isNegative(byte[] bin){
		int sign = bin[0];
		if(sign == 1){
			return true;
		}
		
		return false;
	}
	
	public long asLong(){
		byte[] binary = this.binary;
		long dezimal = 0;
		if(isNegative(binary)){
			BinaryNumber inverted = new BinaryNumber(invert(binary));
			BinaryNumber invertedPlusOne = inverted.add(BinaryNumber.of(1));
			binary = invertedPlusOne.binary;
		}
		for(int i = 0; i < binary.length; i++){
			if(binary[binary.length - (i + 1)] == 1){
				dezimal += Math.pow(2, i);
			}
		}
		if(isNegative(binary)){
			dezimal = dezimal * -1;
		}
		return dezimal;
	}
	
	public BigInteger asBigInt(){
		byte[] binary = this.binary;
		BigInteger dezimal = new BigInteger("0");
		int sign = binary[0];
		if(sign == 1){
			BinaryNumber inverted = new BinaryNumber(invert(binary));
			BinaryNumber invertedPlusOne = inverted.add(BinaryNumber.of(1));
			binary = invertedPlusOne.binary;
		}
		for(int i = 0; i < binary.length; i++){
			if(binary[binary.length - (i + 1)] == 1){
				BigInteger two = new BigInteger("2");
				BigInteger towsPow = two.pow(i);
				dezimal = dezimal.add(towsPow);
			}
		}
		if(sign == 1){
			dezimal = dezimal.multiply(new BigInteger("-1"));
		}
		return dezimal;
	}
	
	public BinaryNumber subtract(BinaryNumber bin){
		byte[] binBinary = bin.binary;
		byte[] thisBinary = this.binary;
		BinaryNumber longer = null;
		if(this.len < bin.len){
			longer = bin;
		}else{
			longer = this;
		}
		binBinary = fillBinarayNumberWithZeros(binBinary, longer.len + 1);
		longer = new BinaryNumber(fillBinarayNumberWithZeros(thisBinary, longer.len + 1));
		byte[] twosCompl = internalTowsComplement(binBinary);
		twosCompl = internalAdd(longer.binary,twosCompl);
		if(twosCompl.length > longer.len){
			twosCompl = removeLeadingDigit(twosCompl);
		}
		return new BinaryNumber(twosCompl);
	}
	
	public BinaryNumber towsComplement(){
		return new BinaryNumber(internalTowsComplement(this.binary));
	}
	
	
	@Override
	public String toString(){
		return this.asString();
	}
	
	public BinaryNumber multiply(BinaryNumber bin){
		return new BinaryNumber(internalMultiply(this.binary, bin.binary));
	}
	
	private static byte[] internalMultiply(byte[] factor1, byte[] factor2){
		boolean isNeg = false;
		int lenFactor2 = factor2.length;
		int lenFactor1 = factor1.length;
		int lenResult = lenFactor1+lenFactor2 - 1;
		if(isNegative(factor2)){
			System.out.println("FACTOR 2 IS NEG");
			factor2 = internalTowsComplement(factor2);
			isNeg = true;
		}
		if(isNegative(factor1)){
			System.out.println("FACTOR 1 IS NEG");
			factor1 = internalTowsComplement(factor1);
			isNeg = true;
		}
		byte[] result = new byte[lenResult];
		byte[] twosComplOfResult = new byte[lenResult];
		byte[] tmp = new byte[lenResult];
		for(int i = lenFactor2-1, zeros = 0; i >= 0; i--, zeros++){
			for(int x = 0; x < tmp.length; x++){
				tmp[x] = 0;
			}
			for(int j = 0; j < lenFactor1; j++){
				tmp[lenResult-lenFactor1+j-zeros] = (byte)(factor2[i] * factor1[j]);
			}
			result = internalAdd(result, tmp);
			if(isNeg){
				twosComplOfResult = internalTowsComplement(result);
			}
			
			
		}
		System.out.println("Final Res: ");
		printArrayAsInlineString(twosComplOfResult);
		System.out.println();
		return twosComplOfResult;
	}
	
	public String asString(){
		String binAsString = "";
		for(byte bit : this.binary){
			binAsString += bit;
		}
		return binAsString;
	}
	
	private static void printArrayAsInlineString(byte[] bin){
		for(byte b:bin){
			System.out.print(b);
		}
		System.out.println();
	}
	
	private static byte[] internalAdd(byte[] bin0, byte[] bin1){
		byte[] binary = bin1;
		byte buffer = 0;
		byte[] result;
		int binOneLength = bin0.length;
		int binTwoLength = binary.length;
		int maxLength;
		if(binOneLength > binTwoLength){
			maxLength = binOneLength;
		}else{
			maxLength = binTwoLength;
		}
		result = new byte[maxLength + 1];
		for(int i = 0; i < maxLength; i++){
			byte addend1 = (i < binOneLength)?bin0[binOneLength - (i + 1)]:0;//this.binary[binOneLength - (i + 1)]:0;
			byte addend2 = (i < binTwoLength)?binary[binTwoLength - (i + 1)]:0; 
			int rowResult =  addend1 + addend2 + buffer;
			if(rowResult == 0){
				result[i] = 0;
			}else if(rowResult == 1){
				result[i] = 1;
				if(buffer == 1){
					buffer = 0;
				}
			}else if(rowResult == 2){
				result[i] = 0;
				if(buffer == 0){
					buffer = 1;
				}
			}else if(rowResult == 3){
				result[i] = 1;
				buffer = 1;
			}
		}
		if(buffer > 0){
			result[maxLength] = 1;
			result = mirror(result);
		}else{
			result = mirror(result);
			result = removeLeadingDigit(result);
		}
		return result;
	}
	
	private static byte[] internalTowsComplement(byte[] bin){
		byte[] b = invert(bin);
		b = internalAdd(b,BinaryNumber.of(1).binary);
		return b;
	}
	
	private static byte[] removeLeadingDigit(byte[] bin){
		byte[] tmp = new byte[bin.length - 1];
		for (int i = 0; i < bin.length - 1; i++){
			tmp[bin.length - (i + 2)] = bin[bin.length - (i+1)];
		}
		return tmp;
	}
	
	private static byte[] removeLeadingZeros(byte[] bin){
		int numberOfZeros = countLeadingZeros(bin);
		byte[] tmp = new byte[bin.length - numberOfZeros];
		for (int i = numberOfZeros, j = 0; i < bin.length; i++, j++){
			tmp[j] = bin[i];
		}
		return tmp;
	}
	
	private static int countLeadingZeros(byte[] bin){
		int counter = 0;
		while(bin[counter] == 0){
			counter++;
		}
		return counter;
	}
	
	private static int getNeededArraySizeForDezimal(long number){
		if(number == 0){
			return 1;
		}
		double log_2 = (Math.log(number)/Math.log(2));
		double size = Math.floor(log_2 + 1);
		return (int)size;
	}
	
	private static int getNeededArraySizeForBigInt(BigInteger number){
		if(number.equals(new BigInteger("0"))){
			return 1;
		}
		double log_2 = (Math.log(number.doubleValue())/Math.log(2));
		double size = Math.floor(log_2 + 1);
		System.out.println("Ness Size: " + size);
		return (int)size;
	}
	
	public BinaryNumber getBinaryNumber(){
		return new BinaryNumber(mirror(this.binary));
	}
	
	public BinaryNumber invert(){
		return new BinaryNumber(invert(this.binary));
	}
	
	private static byte[] invert(byte[] binary){
		byte[] inverted = new byte[binary.length];
		for(int i = 0; i < binary.length; i++){
			if(binary[i] == 0){
				inverted[i] = 1;
			}else{
				inverted[i] = 0;
			}
		}
		return inverted;
	}
	
	private static byte[] mirror(byte[] binary){
		byte[] bin = new byte[binary.length];
		for(int i = 0; i < binary.length; i++){
			bin[i] = binary[binary.length - (i + 1)];
		}
		return bin;
	}	
	
	private static void calcBinaryNumberOfLong(long number, byte[] result, int index){
		if(number != 0){
			long newNumber = number / 2; 
			byte bin = (byte)(number % 2);
			index--;
			calcBinaryNumberOfLong(newNumber, result, index);
			result[index] = bin;
		}
	}
	
	private static void calcBinaryNumberOfBigInt(BigInteger number, byte[] result, int index){
		if(!number.equals(new BigInteger("0"))){
			BigInteger newNumber = number.divide(new BigInteger("2")); 
			System.out.println("NUMBER/2= " + newNumber);
			System.out.println("Equals 0? " + newNumber.equals(new BigInteger("0")));
			byte bin = number.remainder(new BigInteger("2")).byteValue();
			System.out.println("NUMBER%2= " + bin);
			index--;
			calcBinaryNumberOfBigInt(newNumber, result, index);
			result[index] = bin;
		}
	}
	
	private static byte[] fillBinarayNumberWithZeros(byte[] bin, int len){
		byte[] tmp = new byte[len];
		int counter = 0;
		for(int i = 0; i < len; i++){
			if(i < (len - bin.length)){
				tmp[i] = 0;
			}else{
				tmp[i] = bin[counter];
				counter++;
			}
		}
		return tmp;
	}
	
	private static byte[] extendToNextTowsExponent(byte[] bin){
		int len = getNextValidNumberOfBits(bin);
		return fillBinarayNumberWithZeros(bin, len);
	}
	
	private static int getNextValidNumberOfBits(byte[] bin){
		int len = bin.length;
		int currentExp =  (int)(Math.log(len) / Math.log(2));
		if(currentExp % 2 != 0 && len % 2 == 0){
			currentExp++;
		}
		if(len % 2 != 0){
			currentExp++;
		}
		if(bin[0] == 1 && len % 2 == 0){
			currentExp++;
		}
		int newlength = (int)Math.pow(2, currentExp);
		return newlength;
	}
	
	
}
