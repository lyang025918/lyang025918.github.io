// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndexableBinaryStringTools.java

package org.apache.lucene.util;


/**
 * @deprecated Class IndexableBinaryStringTools is deprecated
 */

public final class IndexableBinaryStringTools
{
	static class CodingCase
	{

		int numBytes;
		int initialShift;
		int middleShift;
		int finalShift;
		int advanceBytes;
		short middleMask;
		short finalMask;

		CodingCase(int initialShift, int middleShift, int finalShift)
		{
			advanceBytes = 2;
			numBytes = 3;
			this.initialShift = initialShift;
			this.middleShift = middleShift;
			this.finalShift = finalShift;
			finalMask = (short)(255 >>> finalShift);
			middleMask = (short)(255 << middleShift);
		}

		CodingCase(int initialShift, int finalShift)
		{
			advanceBytes = 2;
			numBytes = 2;
			this.initialShift = initialShift;
			this.finalShift = finalShift;
			finalMask = (short)(255 >>> finalShift);
			if (finalShift != 0)
				advanceBytes = 1;
		}
	}


	private static final CodingCase CODING_CASES[] = {
		new CodingCase(7, 1), new CodingCase(14, 6, 2), new CodingCase(13, 5, 3), new CodingCase(12, 4, 4), new CodingCase(11, 3, 5), new CodingCase(10, 2, 6), new CodingCase(9, 1, 7), new CodingCase(8, 0)
	};
	static final boolean $assertionsDisabled = !org/apache/lucene/util/IndexableBinaryStringTools.desiredAssertionStatus();

	private IndexableBinaryStringTools()
	{
	}

	public static int getEncodedLength(byte inputArray[], int inputOffset, int inputLength)
	{
		return (int)((8L * (long)inputLength + 14L) / 15L) + 1;
	}

	public static int getDecodedLength(char encoded[], int offset, int length)
	{
		int numChars = length - 1;
		if (numChars <= 0)
		{
			return 0;
		} else
		{
			long numFullBytesInFinalChar = encoded[(offset + length) - 1];
			long numEncodedChars = numChars - 1;
			return (int)((numEncodedChars * 15L + 7L) / 8L + numFullBytesInFinalChar);
		}
	}

	public static void encode(byte inputArray[], int inputOffset, int inputLength, char outputArray[], int outputOffset, int outputLength)
	{
		if (!$assertionsDisabled && outputLength != getEncodedLength(inputArray, inputOffset, inputLength))
			throw new AssertionError();
		if (inputLength > 0)
		{
			int inputByteNum = inputOffset;
			int caseNum = 0;
			int outputCharNum;
			CodingCase codingCase;
			for (outputCharNum = outputOffset; inputByteNum + CODING_CASES[caseNum].numBytes <= inputLength; outputCharNum++)
			{
				codingCase = CODING_CASES[caseNum];
				if (2 == codingCase.numBytes)
					outputArray[outputCharNum] = (char)(((inputArray[inputByteNum] & 0xff) << codingCase.initialShift) + ((inputArray[inputByteNum + 1] & 0xff) >>> codingCase.finalShift & codingCase.finalMask) & 0x7fff);
				else
					outputArray[outputCharNum] = (char)(((inputArray[inputByteNum] & 0xff) << codingCase.initialShift) + ((inputArray[inputByteNum + 1] & 0xff) << codingCase.middleShift) + ((inputArray[inputByteNum + 2] & 0xff) >>> codingCase.finalShift & codingCase.finalMask) & 0x7fff);
				inputByteNum += codingCase.advanceBytes;
				if (++caseNum == CODING_CASES.length)
					caseNum = 0;
			}

			codingCase = CODING_CASES[caseNum];
			if (inputByteNum + 1 < inputLength)
			{
				outputArray[outputCharNum++] = (char)(((inputArray[inputByteNum] & 0xff) << codingCase.initialShift) + ((inputArray[inputByteNum + 1] & 0xff) << codingCase.middleShift) & 0x7fff);
				outputArray[outputCharNum++] = '\001';
			} else
			if (inputByteNum < inputLength)
			{
				outputArray[outputCharNum++] = (char)((inputArray[inputByteNum] & 0xff) << codingCase.initialShift & 0x7fff);
				outputArray[outputCharNum++] = caseNum != 0 ? '\0' : '\001';
			} else
			{
				outputArray[outputCharNum++] = '\001';
			}
		}
	}

	public static void decode(char inputArray[], int inputOffset, int inputLength, byte outputArray[], int outputOffset, int outputLength)
	{
		if (!$assertionsDisabled && outputLength != getDecodedLength(inputArray, inputOffset, inputLength))
			throw new AssertionError();
		int numInputChars = inputLength - 1;
		int numOutputBytes = outputLength;
		if (numOutputBytes > 0)
		{
			int caseNum = 0;
			int outputByteNum = outputOffset;
			int inputCharNum;
			short inputChar;
			CodingCase codingCase;
			for (inputCharNum = inputOffset; inputCharNum < numInputChars - 1; inputCharNum++)
			{
				codingCase = CODING_CASES[caseNum];
				inputChar = (short)inputArray[inputCharNum];
				if (2 == codingCase.numBytes)
				{
					if (0 == caseNum)
						outputArray[outputByteNum] = (byte)(inputChar >>> codingCase.initialShift);
					else
						outputArray[outputByteNum] += (byte)(inputChar >>> codingCase.initialShift);
					outputArray[outputByteNum + 1] = (byte)((inputChar & codingCase.finalMask) << codingCase.finalShift);
				} else
				{
					outputArray[outputByteNum] += (byte)(inputChar >>> codingCase.initialShift);
					outputArray[outputByteNum + 1] = (byte)((inputChar & codingCase.middleMask) >>> codingCase.middleShift);
					outputArray[outputByteNum + 2] = (byte)((inputChar & codingCase.finalMask) << codingCase.finalShift);
				}
				outputByteNum += codingCase.advanceBytes;
				if (++caseNum == CODING_CASES.length)
					caseNum = 0;
			}

			inputChar = (short)inputArray[inputCharNum];
			codingCase = CODING_CASES[caseNum];
			if (0 == caseNum)
				outputArray[outputByteNum] = 0;
			outputArray[outputByteNum] += (byte)(inputChar >>> codingCase.initialShift);
			int bytesLeft = numOutputBytes - outputByteNum;
			if (bytesLeft > 1)
				if (2 == codingCase.numBytes)
				{
					outputArray[outputByteNum + 1] = (byte)((inputChar & codingCase.finalMask) >>> codingCase.finalShift);
				} else
				{
					outputArray[outputByteNum + 1] = (byte)((inputChar & codingCase.middleMask) >>> codingCase.middleShift);
					if (bytesLeft > 2)
						outputArray[outputByteNum + 2] = (byte)((inputChar & codingCase.finalMask) << codingCase.finalShift);
				}
		}
	}

}
