package org.hetc.binaryNumber;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public final class BinaryNumber implements Comparable<BinaryNumber>{
	private static BinaryNumber LIMIT_LONG_MAX = BinaryNumber.of(Long.MAX_VALUE);
	static long min = Long.MIN_VALUE+1;
	private static BinaryNumber LIMIT_LONG_MIN = BinaryNumber.of(min);

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

	public static BinaryNumber of(byte[] binary, boolean isNegative) {
		return isNegative ?
				new BinaryNumber(removeLeadingZeros(binary)) :
				of(binary);
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
		boolean isNeg = false;
		if(dezimal < 0){
			if(dezimal < Long.MIN_VALUE + 1)
				throw new IllegalArgumentException("Assigned argument is out of range! "
						+ "Must be between Long.Min_VALUE + 1 and Long.MAX_VALUE");
			dezimal = -1 * dezimal;
			isNeg = true;
		}
		int size = getNeededArraySizeForDezimal(dezimal);
		byte[] binNumber = new byte[size];
		calcBinaryNumberOfLong(dezimal, binNumber, binNumber.length);
		binNumber = extendToNextTowsExponent(binNumber);
		if(isNeg){
			binNumber = internalTowsComplement(binNumber);
		}
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

	public BinaryNumber multiply(BinaryNumber bin){
		if(isNull(this) || isNull(bin))
			return new BinaryNumber(new byte[]{0});
		return new BinaryNumber(internalMultiply(this.binary, bin.binary));
	}

	public BinaryNumber towsComplement(){
		BinaryNumber zero = new BinaryNumber(new byte[]{0});
		if(this.equals(zero))
			return zero;
		return new BinaryNumber(internalTowsComplement(this.binary));
	}

	public BinaryNumber invert(){
		return new BinaryNumber(invert(this.binary));
	}

	public long asLong(){
		if(this.compareTo(LIMIT_LONG_MAX) > 0 || this.compareTo(LIMIT_LONG_MIN) < 0){
				throw new IllegalStateException("The assigned binary number is to large or \nsmall to be converted into a long value");
		}
		byte[] binary = this.binary;

		long dezimal = 0;
		boolean neg = isNegative(binary);
		if(neg){
			BinaryNumber inverted = new BinaryNumber(invert(binary));
			BinaryNumber invertedPlusOne = inverted.add(BinaryNumber.of(1));
			binary = invertedPlusOne.binary;
		}
		for(int i = 0; i < binary.length; i++){
			if(binary[binary.length - (i + 1)] == 1){
				dezimal += Math.pow(2, i);

			}
		}
		if(neg){
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

	public BinaryNumber removeLeadingZeros(){
		return new BinaryNumber(removeLeadingZeros(this.binary));
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

	@Override
	public String toString(){
		return this.asString();
	}

	@Override
	public int compareTo(BinaryNumber bin) {
		boolean thisNeg = isNegative(this.binary);
		boolean binNeg = isNegative(bin.binary);
		if(thisNeg == true && binNeg == false){
			return -1;
		}else if(thisNeg == false && binNeg == true){
			return 1;
		}else if(thisNeg == true && binNeg == true){
			return -1 * interalCompareTo(this.binary, bin.binary);
		}else{
			return interalCompareTo(this.binary, bin.binary);
		}
	}

	private static int interalCompareTo(byte[] bin1, byte[] bin2){
		byte[] binBytes = removeLeadingZeros(bin2);
		byte[] thisBytes = removeLeadingZeros(bin1);
		long binBytesLen = binBytes.length;
		long thisBytesLen = thisBytes.length;
		if(binBytesLen != thisBytesLen){
			return (binBytesLen > thisBytesLen)? -1 : 1;
		}
		for(int i = 0; i < binBytesLen; i++){
			byte binDigit = binBytes[i];
			byte thisDigit = thisBytes[i];
			if(binDigit != thisDigit)
				return (binDigit > thisDigit)?-1:1;
		}
		return 0;
	}


	private static byte[] internalSubtract(byte[] one, byte[] two){
		byte[] binBinary = two;
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

	private static boolean isNegative(byte[] bin){
		int sign = bin[0];
		if(sign == 1){
			return true;
		}

		return false;
	}

	private static boolean isNull(BinaryNumber bin){
		return isNull(bin.binary);
	}

	private static boolean isNull(byte[] bin){
		boolean hasNull = false;
		if((bin[0] == 0 && bin.length == 1))
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

	private static byte[] internalMultiply(byte[] factor1, byte[] factor2){
		boolean isNeg1 = false;
		boolean isNeg2 = false;
		int lenFactor2 = factor2.length;
		int lenFactor1 = factor1.length;
		int lenResult = lenFactor1+lenFactor2 - 1;
		if(isNegative(factor2)){
			factor2 = extendToNextTowsExponent(internalTowsComplement(factor2));
			isNeg1 = true;
		}
		if(isNegative(factor1)){
			factor1 = extendToNextTowsExponent(internalTowsComplement(factor1));
			isNeg2 = true;
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
		return result;
	}

	private String asString(){
		String binAsString = "";
		for(byte bit : this.binary){
			binAsString += bit;
		}
		return binAsString;
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

	private static byte[] removeLeadingZeros(byte[] bin){
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
			if(counter == bin.length){
				--counter;
				break;
			}
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
		return (int)size;
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
		}else if(len % 2 != 0){
			currentExp++;
		}else if(bin[0] == 1 && len % 2 == 0){
			currentExp++;
		}else if(((int)Math.pow(2, currentExp)) < len){
			currentExp++;
		}
		int newlength = (int)Math.pow(2, currentExp);
		return newlength;
	}

	public BinaryNumber divide(BinaryNumber bin){
		if(isNull(this))
			return new BinaryNumber(new byte[]{0});
		if(isNull(bin)){
			throw new IllegalArgumentException("Division by zero is not allowed");
		}
		if(!isNegative(this.binary) && isNegative(bin.binary)){
			return new BinaryNumber(internalTowsComplement(
					internalDivide(this.binary, internalTowsComplement(bin.binary))));
		}else if(isNegative(this.binary) && !isNegative(bin.binary)){
			return new BinaryNumber(internalTowsComplement(
					internalDivide(internalTowsComplement(this.binary), bin.binary)));
		}else if(isNegative(this.binary) && isNegative(bin.binary)){
			return new BinaryNumber(internalDivide(
					internalTowsComplement(this.binary), internalTowsComplement(bin.binary)));
		}else{
			return new BinaryNumber(internalDivide(this.binary, bin.binary));
		}
	}

	private static byte[] internalDivide(byte[] numerator, byte[] dividend) {
		byte[] internalNumerator = removeLeadingZeros(numerator);
		byte[] internalDividend = removeLeadingZeros(dividend);
		List<Byte> result = new LinkedList<>();
		byte[] partialNumeratorValue = getStartValue(internalNumerator, internalDividend);
		byte[] partialDividendValue = internalDividend;
		byte[] partialNumeratorValueExtendedByOneDigit = getStartValue(internalNumerator, internalDividend);//extendToNextTowsExponent(binBinary);
		int posistion = internalDividend.length;
		int stepCounter = 0;
		while(true){
			partialNumeratorValue = checkDividendFitsInPartOfNumberator(partialNumeratorValue, internalDividend);
			result.add(isNull(partialNumeratorValue)?(byte)0:(byte)1);
			if(posistion >= internalNumerator.length){
				break;
			}
			if(stepCounter > 0)
				partialDividendValue = partialNumeratorValue;
			partialNumeratorValue = internalSubtract(partialNumeratorValueExtendedByOneDigit,partialDividendValue);//dividend was tmp before
			partialNumeratorValueExtendedByOneDigit = extendByOneDigit(posistion, partialNumeratorValue, internalNumerator);
			partialNumeratorValue = partialNumeratorValueExtendedByOneDigit;
			posistion++;
			stepCounter++;

		}
		byte[] resultAsBytes = mapListToArray(result);
		return extendToNextTowsExponent(resultAsBytes);
	}

	private static byte[] mapListToArray(List<Byte> values){
		int numberOfValues = values.size();
		byte[] primitives = new byte[numberOfValues];
		for(int i = 0; i < numberOfValues; i++){
			primitives[i] = values.get(i);
		}
		return primitives;
	}

	private static byte[] getStartValue(byte[] numerator, byte[] dividend){
		int divLen = dividend.length;
		byte[] tmp = new byte[divLen];
		for(int i = 0; i < divLen; i++){
			tmp[i] = numerator[i];
		}
		return tmp;
	}

	public static byte[] extendByOneDigit(int position, byte[] partialNumeratorValue, byte[] numerator){
		int currentPos = position;
		int existingDigits = numerator.length;
		if(currentPos > existingDigits){
			return partialNumeratorValue;
		}else{
			byte[] extendedPartialNumeratorValue = new byte[currentPos + 1];
			for(int i = currentPos-1; i >= 0; i--){
				extendedPartialNumeratorValue[i] = partialNumeratorValue[i+1];
			}
			extendedPartialNumeratorValue[extendedPartialNumeratorValue.length -1] = numerator[currentPos];
			return extendedPartialNumeratorValue;
		}


	}

	public static byte[] checkDividendFitsInPartOfNumberator(byte[] numerator, byte[] dividend){
		byte[] fittingPart = numerator;
		if(interalCompareTo(fittingPart, dividend) >= 0){
			return fittingPart;
		}else{
			return new byte[]{0};
		}
	}

//	private BinaryNumber round(int precision){
//		int p = countLeadingZeros(this.binary) + precision;
//		return new BinaryNumber(internalRound(this.binary,p));
//	}
//
//	private static byte[] internalRound(byte[] bin, int precision){
//		if(precision > bin.length || precision == 0){
//			return bin;
//		}
//		byte[] preNum = new byte[bin.length];
//		preNum[precision] = 1;
//		bin = internalAdd(bin, preNum);
//		byte[] result = new byte[precision];
//		for(int i=0; i < precision; i++){
//			result[i] = bin[i];
//		}
//		return result;
//
//	}
}
