package org.hetc.binaryNumber;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public final class BinaryNumber implements Comparable<BinaryNumber>{

	public final static BinaryNumber MAX_LONG_VALUE = BinaryNumber.of(Long.MAX_VALUE);
	public final static BinaryNumber MIN_LONG_VALUE = BinaryNumber.of(Long.MIN_VALUE+1);
	public final static BinaryNumber ONE = BinaryNumber.of(1);
	public final static BinaryNumber ZERO = BinaryNumber.of(0);

	private final static BigInteger TWO_DEZ = new BigInteger("2");
	private final static BigInteger MINUS_ONE_DEZ = new BigInteger("-1");

	private static final char ZERO_DIGIT = '0';
	private static final char ONE_DIGIT = '1';
	private static final char MINUS = '-';

	public static enum Constants{
		MAX_LONG_VALUE(BinaryNumber.of(Long.MAX_VALUE)),
		MIN_LONG_VALUE(BinaryNumber.of(Long.MIN_VALUE+1)),
		ONE(BinaryNumber.of(1)),
		ZERO(BinaryNumber.of(0));

		private final BinaryNumber value;

		private Constants(BinaryNumber value){
			this.value = value;
		}

		public BinaryNumber value(){
			return this.value;
		}
	}

	private final byte[] binary;;
	private final int len;

	private BinaryNumber(byte[] binary) {
		int index = 0;
		byte[] tmp = new byte[binary.length];
		for(byte positon : binary){
			if(positon > 1 || positon < 0)
				throw new NumberFormatException("The binary number must not contain numbers different to 0 and 1");
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
		if(bigInt.compareTo(BigInteger.ZERO) < 0){
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
		checkStringIsValidBinNumber(binary);
		boolean isNeg = getSignOfBinaryNumberFromString(binary);
		if(isNeg){
			binary = binary.substring(1);
		}
		byte[] bin = new byte[binary.length()];
		char[] digits = binary.toCharArray();
		for(int i = 0; i < binary.length(); i++){
			bin[i] = Byte.parseByte(String.valueOf(digits[i]));
		}
		bin = extendToNextTowsExponent(bin);
		if(isNeg){
			bin = internalTowsComplement(bin);
		}
		return new BinaryNumber(bin);
	}

	private static void checkStringIsValidBinNumber(String bin){
		char firstDigit = bin.charAt(0);
		System.out.println("First Char: " + firstDigit);
		if(firstDigit != ZERO_DIGIT && firstDigit != ONE_DIGIT && firstDigit != MINUS){
			throw new NumberFormatException("Invalid charackter for the first digit of the String"
					+ " - must be 0, 1 or '-'");
		}

	}

	private static boolean getSignOfBinaryNumberFromString(String bin){
		char firstDigit = bin.charAt(0);
		boolean isNeg = false;
		if(firstDigit == MINUS)
			isNeg = true;
		return isNeg;
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
			return ZERO;
		return new BinaryNumber(internalMultiply(this.binary, bin.binary));
	}

	public BinaryNumber towsComplement(){
		if(this.equals(ZERO))
			return ZERO;
		return new BinaryNumber(internalTowsComplement(this.binary));
	}

	public BinaryNumber invert(){
		return new BinaryNumber(invert(this.binary));
	}

	public long asLong(){
		if(this.compareTo(MAX_LONG_VALUE) > 0 || this.compareTo(MIN_LONG_VALUE) < 0){
				throw new IllegalStateException("The assigned binary number is to large or \nsmall to be converted into a long value");
		}
		byte[] binary = this.binary;

		long dezimal = 0;
		boolean neg = isNegative(binary);
		if(neg){
			BinaryNumber inverted = new BinaryNumber(invert(binary));
			BinaryNumber invertedPlusOne = inverted.add(ONE);
			binary = invertedPlusOne.binary;
		}
		int binaryLen = binary.length;
		for(int i = 0; i < binaryLen; i++){
			if(binary[binaryLen - (i + 1)] == 1){
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
		BigInteger dezimal = BigInteger.ZERO;
		int sign = binary[0];
		if(sign == 1){
			BinaryNumber inverted = new BinaryNumber(invert(binary));
			BinaryNumber invertedPlusOne = inverted.add(ONE);
			binary = invertedPlusOne.binary;
		}
		int binaryLen = binary.length;
		for(int i = 0; i < binaryLen; i++){
			if(binary[binaryLen - (i + 1)] == 1){
				BigInteger towsPow = TWO_DEZ.pow(i);
				dezimal = dezimal.add(towsPow);
			}
		}
		if(sign == 1){
			dezimal = dezimal.multiply(MINUS_ONE_DEZ);
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
		byte[] binaryBytes = removeLeadingZeros(binary.binary);
		byte[] thisBytes = removeLeadingZeros(this.binary);
		int binaryLen = binaryBytes.length;
		int thisLen = thisBytes.length;
		if(binaryLen != thisLen){
			return false;
		}else{
			for(int i = 0; i < binaryLen; i++){
				if(binaryBytes[i] != thisBytes[i])
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
				return (binDigit > thisDigit)? -1 : 1;
		}
		return 0;
	}


	private static byte[] internalSubtract(byte[] minuend, byte[] subtrahend){
		byte[] binBinary = subtrahend;
		byte[] longer;
		if(minuend.length < subtrahend.length){
			longer = subtrahend;
		}else{
			longer = minuend;
		}

		int longerLen = longer.length;
		binBinary = fillBinarayNumberWithZeros(binBinary, longerLen + 1);
		longer = fillBinarayNumberWithZeros(minuend, longerLen + 1);
		byte[] twosCompl = internalTowsComplement(binBinary);
		twosCompl = removeLeadingZeros(internalAdd(longer,twosCompl));
		if(twosCompl.length > longerLen){
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
		int binLen = bin.length;
		if((bin[0] == 0 && binLen == 1))
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

	private static byte[] internalMultiply(byte[] multiplicant, byte[] multiplicator){
		boolean isNeg1 = false;
		boolean isNeg2 = false;
		int multiplicatorLen = multiplicator.length;
		int multiplicantLen = multiplicant.length;
		int secureResultLen = multiplicantLen+multiplicatorLen - 1;
		if(isNegative(multiplicator)){
			multiplicator = extendToNextTowsExponent(internalTowsComplement(multiplicator));
			isNeg1 = true;
		}
		if(isNegative(multiplicant)){
			multiplicant = extendToNextTowsExponent(internalTowsComplement(multiplicant));
			isNeg2 = true;
		}
		byte[] result = new byte[secureResultLen];
		byte[] partialStep = new byte[secureResultLen];
		for(int i = multiplicatorLen-1, zeros = 0; i >= 0; i--, zeros++){
			int partialStepLen = partialStep.length;
			for(int x = 0; x < partialStepLen; x++){
				partialStep[x] = 0;
			}
			for(int j = 0; j < multiplicantLen; j++){
				partialStep[secureResultLen-multiplicantLen+j-zeros] = (byte)(multiplicator[i] * multiplicant[j]);
			}

			result = internalAdd(result, partialStep);
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

	private static byte[] internalAdd(byte[] leftSummand, byte[] rightSummand){
		byte buffer = 0;
		byte[] result;
		int leftSummandLen = leftSummand.length;
		int rightSummandLen = rightSummand.length;
		int maxLength;
		if(leftSummandLen > rightSummandLen){
			maxLength = leftSummandLen;
		}else{
			maxLength = rightSummandLen;
		}
		result = new byte[maxLength + 1];
		for(int i = 0; i < maxLength; i++){
			byte addend1 = (i < leftSummandLen)?leftSummand[leftSummandLen - (i + 1)]:0;
			byte addend2 = (i < rightSummandLen)?rightSummand[rightSummandLen - (i + 1)]:0;
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
		b = removeLeadingZeros(internalAdd(b,new byte[]{1}));
		return b;
	}

	private static byte[] removeLeadingDigit(byte[] bin){
		int binLen = bin.length;
		byte[] shotenedBin = new byte[binLen - 1];
		for (int i = 0; i < binLen - 1; i++){
			shotenedBin[binLen - (i + 2)] = bin[binLen - (i+1)];
		}
		return shotenedBin;
	}

	private static byte[] removeLeadingZeros(byte[] bin){
		int binLen = bin.length;
		if(binLen == 1)
			return bin;
		int numberOfZeros = countLeadingZeros(bin);
		byte[] binWithoutZeros = new byte[binLen - numberOfZeros];
		for (int i = numberOfZeros, j = 0; i < binLen; i++, j++){
			binWithoutZeros[j] = bin[i];
		}
		return binWithoutZeros;
	}

	private static int countLeadingZeros(byte[] bin){
		int counter = 0;
		while(bin[counter] == 0){
			counter++;
			int binLen = bin.length;
			if(counter == binLen){
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
		if(number.equals(BigInteger.ZERO)){
			return 1;
		}
		double log_2 = (Math.log(number.doubleValue())/Math.log(2));
		double size = Math.floor(log_2 + 1);
		return (int)size;
	}

	private static byte[] invert(byte[] binary){
		int binaryLen = binary.length;
		byte[] inverted = new byte[binaryLen];
		for(int i = 0; i < binaryLen; i++){
			if(binary[i] == 0){
				inverted[i] = 1;
			}else{
				inverted[i] = 0;
			}
		}
		return inverted;
	}

	private static byte[] mirror(byte[] binary){
		int binaryLen = binary.length;
		byte[] bin = new byte[binaryLen];
		for(int i = 0; i < binaryLen; i++){
			bin[i] = binary[binaryLen - (i + 1)];
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
		if(!number.equals(BigInteger.ZERO)){
			BigInteger newNumber = number.divide(TWO_DEZ);
			byte bin = number.remainder(TWO_DEZ).byteValue();
			index--;
			calcBinaryNumberOfBigInt(newNumber, result, index);
			result[index] = bin;
		}
	}

	private static byte[] fillBinarayNumberWithZeros(byte[] bin, int len){
		byte[] filledBin = new byte[len];
		int counter = 0;
		for(int i = 0; i < len; i++){
			int binLen = bin.length;
			if(i < (len - binLen)){
				filledBin[i] = 0;
			}else{
				filledBin[i] = bin[counter];
				counter++;
			}
		}
		return filledBin;
	}

	private static byte[] extendToNextTowsExponent(byte[] bin){
		int validLen = getNextValidNumberOfBits(bin);
		return fillBinarayNumberWithZeros(bin, validLen);
	}

	private static int getNextValidNumberOfBits(byte[] bin){
		int binLen = bin.length;
		if(binLen == 1 && bin[0] == 0){
			return 1;
		}
		int currentExp =  (int)(Math.log(binLen) / Math.log(2));
		if(currentExp % 2 != 0 && binLen % 2 == 0){
			currentExp++;
		}else if(binLen % 2 != 0){
			currentExp++;
		}else if(bin[0] == 1 && binLen % 2 == 0){
			currentExp++;
		}else if(((int)Math.pow(2, currentExp)) < binLen){
			currentExp++;
		}
		int lenOfNextTwosExp = (int)Math.pow(2, currentExp);
		return lenOfNextTwosExp;
	}

	public BinaryNumber divide(BinaryNumber bin){
		if(isNull(this))
			return ZERO;
		if(isNull(bin)){
			throw new ArithmeticException("Division by zero");
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
		byte[] startValue = new byte[divLen];
		for(int i = 0; i < divLen; i++){
			startValue[i] = numerator[i];
		}
		return startValue;
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

	public byte[] asByteArray() {
		// TODO Auto-generated method stub
		return this.binary;
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
