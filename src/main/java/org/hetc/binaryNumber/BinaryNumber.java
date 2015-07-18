package org.hetc.binaryNumber;


public final class BinaryNumber {
	public final byte[] binary;;
	private final int len;	
	
	public static BinaryNumber of(byte[] binary){
		byte[] bin = extendToNextTowsExponent(binary);
		return new BinaryNumber(bin);
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
		calcBinaryNumber(dezimal, binNumber, binNumber.length);
		binNumber = extendToNextTowsExponent(binNumber);
		return new BinaryNumber(binNumber);
	}
	
	
	public BinaryNumber add(BinaryNumber bin){
		return new BinaryNumber(internalAdd(this.binary,bin.binary));
	}
	
	public long toDezimal(){
		byte[] binary = this.binary;
		long dezimal = 0;
		int sign = binary[0];
		if(sign == 1){
			BinaryNumber inverted = new BinaryNumber(invert(binary));
			BinaryNumber invertedPlusOne = inverted.add(BinaryNumber.of(1));
			binary = invertedPlusOne.binary;
		}
		for(int i = 0; i < binary.length; i++){
			if(binary[binary.length - (i + 1)] == 1){
				dezimal += Math.pow(2, i);
			}
		}
		if(sign == 1){
			dezimal = dezimal * -1;
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
	
	public String toZerolessString(){
		return null;
	}
	
	@Override
	public String toString(){
		return this.binaryToString();
	}
	
	private String binaryToString(){
		String binAsString = "";
		for(byte bit : this.binary){
			binAsString += bit;
		}
		return binAsString;
	}
	
	private BinaryNumber(byte[] binary) {
		int index = 0;
		byte[] tmp = new byte[binary.length];
		for(byte positon : binary){
			if(positon > 1 || positon < 0)
				throw new IllegalArgumentException("The binary number must not contain numbers different to 0 or 1");
			tmp[index] = binary[index];
			index++;
		}
		this.len = tmp.length;
		this.binary = tmp;
	}
	
	private byte[] internalAdd(byte[] bin0, byte[] bin1){
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
		return result;//new BinaryNumber(result);
	}
	
//	public static BinaryNumber adjustBinaries(BinaryNumber one, BinaryNumber two){
//		BinaryNumber shorter = (one.len < two.len)?one:two;
//		int maxlen = (one.len > two.len)?one.len:two.len;
//		byte[] adjusted = fillBinarayNumberWithZeros(shorter.binary, maxlen);
//		return new BinaryNumber(adjusted);
//	}
	
	
	
	private byte[] internalTowsComplement(byte[] bin){
		byte[] b = invert(bin);
		b = internalAdd(b,BinaryNumber.of(1).binary);//new BinaryNumber(b).internalAdd(BinaryNumber.of(1).binary);
		return b;
	}
	
	private byte[] removeLeadingDigit(byte[] bin){
		byte[] tmp = new byte[bin.length - 1];
		for (int i = 0; i < bin.length - 1; i++){
			tmp[bin.length - (i + 2)] = bin[bin.length - (i+1)];
		}
		return tmp;
	}
	
	private static byte[] removeLeadingZeros(byte[] bin){
		int numberOfZeros = countNonZeroDigits(bin);
		byte[] tmp = new byte[bin.length - numberOfZeros];
		for (int i = numberOfZeros, j = 0; i < bin.length; i++, j++){
			tmp[j] = bin[i];
		}
		return tmp;
	}
	
	private static int countNonZeroDigits(byte[] bin){
		int counter = 0;
		for(int i = 0; i < bin.length; i++){
			counter = i;
			byte tmp = bin[i];
			if(tmp != 0){
				return i;
			}
		}
		return (counter > 0)?(counter-1):0;
	}
	
	private static int getNeededArraySizeForDezimal(long number){
		if(number == 0){
			return 1;
		}
		double log_2 = (Math.log(number)/Math.log(2));
		double size = Math.floor(log_2 + 1);
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
	
	private static void calcBinaryNumber(long number, byte[] result, int index){
		if(number != 0){
			long newNumber = number / 2; 
			byte bin = (byte)(number % 2);
			index--;
			calcBinaryNumber(newNumber, result, index);
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
		if(bin[0] == 1){
			currentExp++;
		}
		int newlength = (int)Math.pow(2, currentExp);
		return newlength;
	}
	
	
}
