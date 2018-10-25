// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StringHelper.java

package org.apache.lucene.util;

import java.util.Comparator;
import java.util.StringTokenizer;

// Referenced classes of package org.apache.lucene.util:
//			BytesRef

public abstract class StringHelper
{

	private static Comparator versionComparator = new Comparator() {

		public int compare(String a, String b)
		{
			StringTokenizer bTokens;
label0:
			{
				StringTokenizer aTokens = new StringTokenizer(a, ".");
				bTokens = new StringTokenizer(b, ".");
				int aToken;
label1:
				do
				{
					int bToken;
					do
					{
						if (!aTokens.hasMoreTokens())
							break label0;
						aToken = Integer.parseInt(aTokens.nextToken());
						if (!bTokens.hasMoreTokens())
							continue label1;
						bToken = Integer.parseInt(bTokens.nextToken());
					} while (aToken == bToken);
					return aToken >= bToken ? 1 : -1;
				} while (aToken == 0);
				return 1;
			}
			while (bTokens.hasMoreTokens()) 
				if (Integer.parseInt(bTokens.nextToken()) != 0)
					return -1;
			return 0;
		}

		public volatile int compare(Object x0, Object x1)
		{
			return compare((String)x0, (String)x1);
		}

	};

	public static int bytesDifference(BytesRef left, BytesRef right)
	{
		int len = left.length >= right.length ? right.length : left.length;
		byte bytesLeft[] = left.bytes;
		int offLeft = left.offset;
		byte bytesRight[] = right.bytes;
		int offRight = right.offset;
		for (int i = 0; i < len; i++)
			if (bytesLeft[i + offLeft] != bytesRight[i + offRight])
				return i;

		return len;
	}

	private StringHelper()
	{
	}

	public static Comparator getVersionComparator()
	{
		return versionComparator;
	}

	public static boolean equals(String s1, String s2)
	{
		if (s1 == null)
			return s2 == null;
		else
			return s1.equals(s2);
	}

	public static boolean startsWith(BytesRef ref, BytesRef prefix)
	{
		return sliceEquals(ref, prefix, 0);
	}

	public static boolean endsWith(BytesRef ref, BytesRef suffix)
	{
		return sliceEquals(ref, suffix, ref.length - suffix.length);
	}

	private static boolean sliceEquals(BytesRef sliceToTest, BytesRef other, int pos)
	{
		if (pos < 0 || sliceToTest.length - pos < other.length)
			return false;
		int i = sliceToTest.offset + pos;
		int j = other.offset;
		for (int k = other.offset + other.length; j < k;)
			if (sliceToTest.bytes[i++] != other.bytes[j++])
				return false;

		return true;
	}

}
