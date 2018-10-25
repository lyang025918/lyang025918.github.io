// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OpenBitSetIterator.java

package org.apache.lucene.util;

import org.apache.lucene.search.DocIdSetIterator;

// Referenced classes of package org.apache.lucene.util:
//			OpenBitSet

public class OpenBitSetIterator extends DocIdSetIterator
{

	protected static final int bitlist[] = {
		0, 1, 2, 33, 3, 49, 50, 801, 4, 65, 
		66, 1057, 67, 1073, 1074, 17185, 5, 81, 82, 1313, 
		83, 1329, 1330, 21281, 84, 1345, 1346, 21537, 1347, 21553, 
		21554, 0x54321, 6, 97, 98, 1569, 99, 1585, 1586, 25377, 
		100, 1601, 1602, 25633, 1603, 25649, 25650, 0x64321, 101, 1617, 
		1618, 25889, 1619, 25905, 25906, 0x65321, 1620, 25921, 25922, 0x65421, 
		25923, 0x65431, 0x65432, 0x654321, 7, 113, 114, 1825, 115, 1841, 
		1842, 29473, 116, 1857, 1858, 29729, 1859, 29745, 29746, 0x74321, 
		117, 1873, 1874, 29985, 1875, 30001, 30002, 0x75321, 1876, 30017, 
		30018, 0x75421, 30019, 0x75431, 0x75432, 0x754321, 118, 1889, 1890, 30241, 
		1891, 30257, 30258, 0x76321, 1892, 30273, 30274, 0x76421, 30275, 0x76431, 
		0x76432, 0x764321, 1893, 30289, 30290, 0x76521, 30291, 0x76531, 0x76532, 0x765321, 
		30292, 0x76541, 0x76542, 0x765421, 0x76543, 0x765431, 0x765432, 0x7654321, 8, 129, 
		130, 2081, 131, 2097, 2098, 33569, 132, 2113, 2114, 33825, 
		2115, 33841, 33842, 0x84321, 133, 2129, 2130, 34081, 2131, 34097, 
		34098, 0x85321, 2132, 34113, 34114, 0x85421, 34115, 0x85431, 0x85432, 0x854321, 
		134, 2145, 2146, 34337, 2147, 34353, 34354, 0x86321, 2148, 34369, 
		34370, 0x86421, 34371, 0x86431, 0x86432, 0x864321, 2149, 34385, 34386, 0x86521, 
		34387, 0x86531, 0x86532, 0x865321, 34388, 0x86541, 0x86542, 0x865421, 0x86543, 0x865431, 
		0x865432, 0x8654321, 135, 2161, 2162, 34593, 2163, 34609, 34610, 0x87321, 
		2164, 34625, 34626, 0x87421, 34627, 0x87431, 0x87432, 0x874321, 2165, 34641, 
		34642, 0x87521, 34643, 0x87531, 0x87532, 0x875321, 34644, 0x87541, 0x87542, 0x875421, 
		0x87543, 0x875431, 0x875432, 0x8754321, 2166, 34657, 34658, 0x87621, 34659, 0x87631, 
		0x87632, 0x876321, 34660, 0x87641, 0x87642, 0x876421, 0x87643, 0x876431, 0x876432, 0x8764321, 
		34661, 0x87651, 0x87652, 0x876521, 0x87653, 0x876531, 0x876532, 0x8765321, 0x87654, 0x876541, 
		0x876542, 0x8765421, 0x876543, 0x8765431, 0x8765432, 0x87654321
	};
	final long arr[];
	final int words;
	private int i;
	private long word;
	private int wordShift;
	private int indexArray;
	private int curDocId;

	public OpenBitSetIterator(OpenBitSet obs)
	{
		this(obs.getBits(), obs.getNumWords());
	}

	public OpenBitSetIterator(long bits[], int numWords)
	{
		i = -1;
		curDocId = -1;
		arr = bits;
		words = numWords;
	}

	private void shift()
	{
		if ((int)word == 0)
		{
			wordShift += 32;
			word = word >>> 32;
		}
		if ((word & 65535L) == 0L)
		{
			wordShift += 16;
			word >>>= 16;
		}
		if ((word & 255L) == 0L)
		{
			wordShift += 8;
			word >>>= 8;
		}
		indexArray = bitlist[(int)word & 0xff];
	}

	public int nextDoc()
	{
		if (indexArray == 0)
		{
			if (word != 0L)
			{
				word >>>= 8;
				wordShift += 8;
			}
			while (word == 0L) 
			{
				if (++i >= words)
					return curDocId = 0x7fffffff;
				word = arr[i];
				wordShift = -1;
			}
			shift();
		}
		int bitIndex = (indexArray & 0xf) + wordShift;
		indexArray >>>= 4;
		return curDocId = (i << 6) + bitIndex;
	}

	public int advance(int target)
	{
		indexArray = 0;
		i = target >> 6;
		if (i >= words)
		{
			word = 0L;
			return curDocId = 0x7fffffff;
		}
		wordShift = target & 0x3f;
		word = arr[i] >>> wordShift;
		if (word != 0L)
		{
			wordShift--;
		} else
		{
			for (; word == 0L; word = arr[i])
				if (++i >= words)
					return curDocId = 0x7fffffff;

			wordShift = -1;
		}
		shift();
		int bitIndex = (indexArray & 0xf) + wordShift;
		indexArray >>>= 4;
		return curDocId = (i << 6) + bitIndex;
	}

	public int docID()
	{
		return curDocId;
	}

}
