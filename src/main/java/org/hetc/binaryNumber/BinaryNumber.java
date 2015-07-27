package org.hetc.binaryNumber;

import java.math.BigInteger;
import java.util.Objects;

public final class BinaryNumber implements Comparable<BinaryNumber>{
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
		BinaryNumber nonNull = getNonNullNumber(this, bin);
		if(!Objects.nonNull(nonNull))
			return nonNull;
		
		if(isNegative(this.binary) && !isNegative(bin.binary)){
			return new BinaryNumber(internalSubtract(bin.binary, internalTowsComplement(this.binary)));
		}else if(!isNegative(this.binary) && isNegative(bin.binary)){
			return new BinaryNumber(internalSubtract(this.binary, internalTowsComplement(bin.binary)));
		}else if(isNegative(this.binary) && isNegative(bin.binary)){
			return new BinaryNumber(internalTowsComplement(internalAdd(internalTowsComplement(this.binary), internalTowsComplement(bin.binary))));
		}else{
			byte[] tmp = internalAdd(this.binary,bin.binary);
			return new BinaryNumber(tmp);
		}
		
	}
	
	public BinaryNumber round(int precision){
		int p = countLeadingZeros(this.binary) + precision;
		return new BinaryNumber(internalRound(this.binary,p));
	}
	
	private static byte[] internalRound(byte[] bin, int precision){
		if(precision > bin.length || precision == 0){
			return bin;
		}
		byte[] preNum = new byte[bin.length];
		preNum[precision] = 1;
		bin = internalAdd(bin, preNum);
		byte[] result = new byte[precision];
		for(int i=0; i < precision; i++){
			result[i] = bin[i];
		}
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
	
	public static byte[] internalSubtract(byte[] one, byte[] two){
		byte[] binBinary = two;//bin.binary;
		byte[] longer;
		if(one.length < two.length){
			longer = two;
		}else{
			longer = one;
		}

		binBinary = fillBinarayNumberWithZeros(binBinary, longer.length + 1);
		longer = fillBinarayNumberWithZeros(one, longer.length + 1);
		byte[] twosCompl = internalTowsComplement(binBinary);
		twosCompl = removeLeadingZeros(internalAdd(longer,twosCompl));
		if(twosCompl.length > longer.length){
			twosCompl = removeLeadingDigit(twosCompl);
		}
		return twosCompl;
	}
	
	public BinaryNumber subtract(BinaryNumber bin){
		BinaryNumber nonNull = getNonNullNumber(this, bin);
		if(!Objects.nonNull(nonNull))
			return nonNull;
		
		if(!isNegative(this.binary) && isNegative(bin.binary)){
			return new BinaryNumber(internalAdd(
					this.binary, internalTowsComplement(bin.binary)));
		}else if(isNegative(this.binary) && !isNegative(bin.binary)){
			return new BinaryNumber(internalTowsComplement(
					internalAdd(internalTowsComplement(this.binary), bin.binary)));
		}else if(isNegative(this.binary) && isNegative(bin.binary)){
			return new BinaryNumber(internalSubtract(
					internalTowsComplement(bin.binary), internalTowsComplement(this.binary)));
		}else{
			return new BinaryNumber(internalSubtract(this.binary, bin.binary));
		}
		
	}
	
	public BinaryNumber towsComplement(){
		return new BinaryNumber(internalTowsComplement(this.binary));
	}
	
	
	@Override
	public String toString(){
		return this.asString();
	}
	

	private static boolean isNull(BinaryNumber bin){
		boolean hasNull = false;
		if((bin.binary[0] == 0 && bin.len == 1))
			hasNull = true;
		return hasNull;
	}
	
	private static BinaryNumber getNonNullNumber(BinaryNumber bin1, BinaryNumber bin2){
		if(!isNull(bin1))
			return bin1;
		if(!isNull(bin2))
			return bin1;
		return null;
	}
	
	public BinaryNumber multiply(BinaryNumber bin){
		if(isNull(this) || isNull(bin))
			return new BinaryNumber(new byte[]{0});
		return new BinaryNumber(internalMultiply(this.binary, bin.binary));
	}
	
	private static byte[] internalMultiply(byte[] factor1, byte[] factor2){
		System.out.println("Fac1: ");
		printArrayAsInlineString(factor1);
		System.out.println("Fac2: ");
		printArrayAsInlineString(factor2);
		boolean isNeg1 = false;
		boolean isNeg2 = false;
		int lenFactor2 = factor2.length;
		int lenFactor1 = factor1.length;
		int lenResult = lenFactor1+lenFactor2 - 1;
		if(isNegative(factor2)){
			System.out.println("fac2 is neg");
			factor2 = extendToNextTowsExponent(internalTowsComplement(factor2));
			isNeg1 = true;
			System.out.println("neg Fac2: ");
			printArrayAsInlineString(factor2);
		}
		if(isNegative(factor1)){
			System.out.println("fac1 is neg");
			factor1 = extendToNextTowsExponent(internalTowsComplement(factor1));
			isNeg2 = true;
			System.out.println("neg Fac1: ");
			printArrayAsInlineString(factor1);
		}
		byte[] result = new byte[lenResult];
		byte[] tmp = new byte[lenResult];
		for(int i = lenFactor2-1, zeros = 0; i >= 0; i--, zeros++){
			for(int x = 0; x < tmp.length; x++){
				tmp[x] = 0;
			}
			for(int j = 0; j < lenFactor1; j++){
				tmp[lenResult-lenFactor1+j-zeros] = (byte)(factor2[i] * factor1[j]);
			}
			
			result = internalAdd(result, tmp);
		}
		if(!(isNeg1 && isNeg2) && !(!isNeg1 && !isNeg2) ){
			result = internalTowsComplement(result);
		}
//		System.out.println("res: ");
//		printArrayAsInlineString(result);
		return result;
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
		return extendToNextTowsExponent(result);
	}
	
	private static byte[] internalTowsComplement(byte[] bin){
		byte[] b = invert(bin);
		b = removeLeadingZeros(internalAdd(b,BinaryNumber.of(1).binary));
		return b;
	}
	
	private static byte[] removeLeadingDigit(byte[] bin){
		byte[] tmp = new byte[bin.length - 1];
		for (int i = 0; i < bin.length - 1; i++){
			tmp[bin.length - (i + 2)] = bin[bin.length - (i+1)];
		}
		return tmp;
	}
	
	public BinaryNumber removeLeadingZeros(){
		return new BinaryNumber(removeLeadingZeros(this.binary));
	}
	
	private static byte[] removeLeadingZeros(byte[] bin){
		printArrayAsInlineString(bin);
		if(bin.length == 1)
			return bin;
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
			byte bin = number.remainder(new BigInteger("2")).byteValue();
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
		if(len == 1 && bin[0] == 0){
			return 1;
		}
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
	
	@Override
	public boolean equals(Object bin){
		if(this == bin)
			return true;
		if(!(bin instanceof BinaryNumber))
			return false;
		
		BinaryNumber binary = (BinaryNumber)bin;
		byte[] binary_bytes = removeLeadingZeros(binary.binary);
		byte[] this_bytes = removeLeadingZeros(this.binary);
		if(binary_bytes.length != this_bytes.length){
			return false;
		}else{
			for(int i = 0; i < binary_bytes.length; i++){
				if(binary_bytes[i] != this_bytes[i])
					return false;
			}
		}
		return true;
	}
	
	
	@Override
	public int hashCode(){
		int result = 17;
		byte[] this_binary = removeLeadingZeros(this.binary);
		for(byte digit : this_binary){
			result += 31 * result + digit;
		}
		return result;
	}
	
	public int compareTo(BinaryNumber bin) {
		if(this == bin)
			return 0;
		return (this.asBigInt()).compareTo(bin.asBigInt());
	}
	
	
}
