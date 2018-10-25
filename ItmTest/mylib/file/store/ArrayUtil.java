// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ArrayUtil.java

package mylib.file.store;

import java.util.*;

// Referenced classes of package mylib.file.store:
//			RamUsageEstimator, Constants, SorterTemplate

public final class ArrayUtil
{

	static final boolean $assertionsDisabled = !mylib/file/store/ArrayUtil.desiredAssertionStatus();

	/**
	 * @deprecated Method ArrayUtil is deprecated
	 */

	public ArrayUtil()
	{
	}

	public static int parseInt(char chars[])
		throws NumberFormatException
	{
		return parseInt(chars, 0, chars.length, 10);
	}

	public static int parseInt(char chars[], int offset, int len)
		throws NumberFormatException
	{
		return parseInt(chars, offset, len, 10);
	}

	public static int parseInt(char chars[], int offset, int len, int radix)
		throws NumberFormatException
	{
		if (chars == null || radix < 2 || radix > 36)
			throw new NumberFormatException();
		int i = 0;
		if (len == 0)
			throw new NumberFormatException("chars length is 0");
		boolean negative = chars[offset + i] == '-';
		if (negative && ++i == len)
			throw new NumberFormatException("can't convert to an int");
		if (negative)
		{
			offset++;
			len--;
		}
		return parse(chars, offset, len, radix, negative);
	}

	private static int parse(char chars[], int offset, int len, int radix, boolean negative)
		throws NumberFormatException
	{
		int max = 0x80000000 / radix;
		int result = 0;
		for (int i = 0; i < len; i++)
		{
			int digit = Character.digit(chars[i + offset], radix);
			if (digit == -1)
				throw new NumberFormatException("Unable to parse");
			if (max > result)
				throw new NumberFormatException("Unable to parse");
			int next = result * radix - digit;
			if (next > result)
				throw new NumberFormatException("Unable to parse");
			result = next;
		}

		if (!negative)
		{
			result = -result;
			if (result < 0)
				throw new NumberFormatException("Unable to parse");
		}
		return result;
	}

	public static int oversize(int minTargetSize, int bytesPerElement)
	{
		if (minTargetSize < 0)
			throw new IllegalArgumentException((new StringBuilder()).append("invalid array size ").append(minTargetSize).toString());
		if (minTargetSize == 0)
			return 0;
		int extra = minTargetSize >> 3;
		if (extra < 3)
			extra = 3;
		int newSize = minTargetSize + extra;
		if (newSize + 7 < 0)
			return 0x7fffffff;
		if (Constants.JRE_IS_64BIT)
			switch (bytesPerElement)
			{
			case 4: // '\004'
				return newSize + 1 & 0x7ffffffe;

			case 2: // '\002'
				return newSize + 3 & 0x7ffffffc;

			case 1: // '\001'
				return newSize + 7 & 0x7ffffff8;

			case 3: // '\003'
			case 5: // '\005'
			case 6: // '\006'
			case 7: // '\007'
			case 8: // '\b'
			default:
				return newSize;
			}
		switch (bytesPerElement)
		{
		case 2: // '\002'
			return newSize + 1 & 0x7ffffffe;

		case 1: // '\001'
			return newSize + 3 & 0x7ffffffc;

		case 3: // '\003'
		case 4: // '\004'
		case 5: // '\005'
		case 6: // '\006'
		case 7: // '\007'
		case 8: // '\b'
		default:
			return newSize;
		}
	}

	public static int getShrinkSize(int currentSize, int targetSize, int bytesPerElement)
	{
		int newSize = oversize(targetSize, bytesPerElement);
		if (newSize < currentSize / 2)
			return newSize;
		else
			return currentSize;
	}

	public static short[] grow(short array[], int minSize)
	{
		if (!$assertionsDisabled && minSize < 0)
			throw new AssertionError((new StringBuilder()).append("size must be positive (got ").append(minSize).append("): likely integer overflow?").toString());
		if (array.length < minSize)
		{
			short newArray[] = new short[oversize(minSize, 2)];
			System.arraycopy(array, 0, newArray, 0, array.length);
			return newArray;
		} else
		{
			return array;
		}
	}

	public static short[] grow(short array[])
	{
		return grow(array, 1 + array.length);
	}

	public static float[] grow(float array[], int minSize)
	{
		if (!$assertionsDisabled && minSize < 0)
			throw new AssertionError((new StringBuilder()).append("size must be positive (got ").append(minSize).append("): likely integer overflow?").toString());
		if (array.length < minSize)
		{
			float newArray[] = new float[oversize(minSize, 4)];
			System.arraycopy(array, 0, newArray, 0, array.length);
			return newArray;
		} else
		{
			return array;
		}
	}

	public static float[] grow(float array[])
	{
		return grow(array, 1 + array.length);
	}

	public static double[] grow(double array[], int minSize)
	{
		if (!$assertionsDisabled && minSize < 0)
			throw new AssertionError((new StringBuilder()).append("size must be positive (got ").append(minSize).append("): likely integer overflow?").toString());
		if (array.length < minSize)
		{
			double newArray[] = new double[oversize(minSize, 8)];
			System.arraycopy(array, 0, newArray, 0, array.length);
			return newArray;
		} else
		{
			return array;
		}
	}

	public static double[] grow(double array[])
	{
		return grow(array, 1 + array.length);
	}

	public static short[] shrink(short array[], int targetSize)
	{
		if (!$assertionsDisabled && targetSize < 0)
			throw new AssertionError((new StringBuilder()).append("size must be positive (got ").append(targetSize).append("): likely integer overflow?").toString());
		int newSize = getShrinkSize(array.length, targetSize, 2);
		if (newSize != array.length)
		{
			short newArray[] = new short[newSize];
			System.arraycopy(array, 0, newArray, 0, newSize);
			return newArray;
		} else
		{
			return array;
		}
	}

	public static int[] grow(int array[], int minSize)
	{
		if (!$assertionsDisabled && minSize < 0)
			throw new AssertionError((new StringBuilder()).append("size must be positive (got ").append(minSize).append("): likely integer overflow?").toString());
		if (array.length < minSize)
		{
			int newArray[] = new int[oversize(minSize, 4)];
			System.arraycopy(array, 0, newArray, 0, array.length);
			return newArray;
		} else
		{
			return array;
		}
	}

	public static int[] grow(int array[])
	{
		return grow(array, 1 + array.length);
	}

	public static int[] shrink(int array[], int targetSize)
	{
		if (!$assertionsDisabled && targetSize < 0)
			throw new AssertionError((new StringBuilder()).append("size must be positive (got ").append(targetSize).append("): likely integer overflow?").toString());
		int newSize = getShrinkSize(array.length, targetSize, 4);
		if (newSize != array.length)
		{
			int newArray[] = new int[newSize];
			System.arraycopy(array, 0, newArray, 0, newSize);
			return newArray;
		} else
		{
			return array;
		}
	}

	public static long[] grow(long array[], int minSize)
	{
		if (!$assertionsDisabled && minSize < 0)
			throw new AssertionError((new StringBuilder()).append("size must be positive (got ").append(minSize).append("): likely integer overflow?").toString());
		if (array.length < minSize)
		{
			long newArray[] = new long[oversize(minSize, 8)];
			System.arraycopy(array, 0, newArray, 0, array.length);
			return newArray;
		} else
		{
			return array;
		}
	}

	public static long[] grow(long array[])
	{
		return grow(array, 1 + array.length);
	}

	public static long[] shrink(long array[], int targetSize)
	{
		if (!$assertionsDisabled && targetSize < 0)
			throw new AssertionError((new StringBuilder()).append("size must be positive (got ").append(targetSize).append("): likely integer overflow?").toString());
		int newSize = getShrinkSize(array.length, targetSize, 8);
		if (newSize != array.length)
		{
			long newArray[] = new long[newSize];
			System.arraycopy(array, 0, newArray, 0, newSize);
			return newArray;
		} else
		{
			return array;
		}
	}

	public static byte[] grow(byte array[], int minSize)
	{
		if (!$assertionsDisabled && minSize < 0)
			throw new AssertionError((new StringBuilder()).append("size must be positive (got ").append(minSize).append("): likely integer overflow?").toString());
		if (array.length < minSize)
		{
			byte newArray[] = new byte[oversize(minSize, 1)];
			System.arraycopy(array, 0, newArray, 0, array.length);
			return newArray;
		} else
		{
			return array;
		}
	}

	public static byte[] grow(byte array[])
	{
		return grow(array, 1 + array.length);
	}

	public static byte[] shrink(byte array[], int targetSize)
	{
		if (!$assertionsDisabled && targetSize < 0)
			throw new AssertionError((new StringBuilder()).append("size must be positive (got ").append(targetSize).append("): likely integer overflow?").toString());
		int newSize = getShrinkSize(array.length, targetSize, 1);
		if (newSize != array.length)
		{
			byte newArray[] = new byte[newSize];
			System.arraycopy(array, 0, newArray, 0, newSize);
			return newArray;
		} else
		{
			return array;
		}
	}

	public static boolean[] grow(boolean array[], int minSize)
	{
		if (!$assertionsDisabled && minSize < 0)
			throw new AssertionError((new StringBuilder()).append("size must be positive (got ").append(minSize).append("): likely integer overflow?").toString());
		if (array.length < minSize)
		{
			boolean newArray[] = new boolean[oversize(minSize, 1)];
			System.arraycopy(array, 0, newArray, 0, array.length);
			return newArray;
		} else
		{
			return array;
		}
	}

	public static boolean[] grow(boolean array[])
	{
		return grow(array, 1 + array.length);
	}

	public static boolean[] shrink(boolean array[], int targetSize)
	{
		if (!$assertionsDisabled && targetSize < 0)
			throw new AssertionError((new StringBuilder()).append("size must be positive (got ").append(targetSize).append("): likely integer overflow?").toString());
		int newSize = getShrinkSize(array.length, targetSize, 1);
		if (newSize != array.length)
		{
			boolean newArray[] = new boolean[newSize];
			System.arraycopy(array, 0, newArray, 0, newSize);
			return newArray;
		} else
		{
			return array;
		}
	}

	public static char[] grow(char array[], int minSize)
	{
		if (!$assertionsDisabled && minSize < 0)
			throw new AssertionError((new StringBuilder()).append("size must be positive (got ").append(minSize).append("): likely integer overflow?").toString());
		if (array.length < minSize)
		{
			char newArray[] = new char[oversize(minSize, 2)];
			System.arraycopy(array, 0, newArray, 0, array.length);
			return newArray;
		} else
		{
			return array;
		}
	}

	public static char[] grow(char array[])
	{
		return grow(array, 1 + array.length);
	}

	public static char[] shrink(char array[], int targetSize)
	{
		if (!$assertionsDisabled && targetSize < 0)
			throw new AssertionError((new StringBuilder()).append("size must be positive (got ").append(targetSize).append("): likely integer overflow?").toString());
		int newSize = getShrinkSize(array.length, targetSize, 2);
		if (newSize != array.length)
		{
			char newArray[] = new char[newSize];
			System.arraycopy(array, 0, newArray, 0, newSize);
			return newArray;
		} else
		{
			return array;
		}
	}

	public static int[][] grow(int array[][], int minSize)
	{
		if (!$assertionsDisabled && minSize < 0)
			throw new AssertionError((new StringBuilder()).append("size must be positive (got ").append(minSize).append("): likely integer overflow?").toString());
		if (array.length < minSize)
		{
			int newArray[][] = new int[oversize(minSize, RamUsageEstimator.NUM_BYTES_OBJECT_REF)][];
			System.arraycopy(array, 0, newArray, 0, array.length);
			return newArray;
		} else
		{
			return array;
		}
	}

	public static int[][] grow(int array[][])
	{
		return grow(array, 1 + array.length);
	}

	public static int[][] shrink(int array[][], int targetSize)
	{
		if (!$assertionsDisabled && targetSize < 0)
			throw new AssertionError((new StringBuilder()).append("size must be positive (got ").append(targetSize).append("): likely integer overflow?").toString());
		int newSize = getShrinkSize(array.length, targetSize, RamUsageEstimator.NUM_BYTES_OBJECT_REF);
		if (newSize != array.length)
		{
			int newArray[][] = new int[newSize][];
			System.arraycopy(array, 0, newArray, 0, newSize);
			return newArray;
		} else
		{
			return array;
		}
	}

	public static float[][] grow(float array[][], int minSize)
	{
		if (!$assertionsDisabled && minSize < 0)
			throw new AssertionError((new StringBuilder()).append("size must be positive (got ").append(minSize).append("): likely integer overflow?").toString());
		if (array.length < minSize)
		{
			float newArray[][] = new float[oversize(minSize, RamUsageEstimator.NUM_BYTES_OBJECT_REF)][];
			System.arraycopy(array, 0, newArray, 0, array.length);
			return newArray;
		} else
		{
			return array;
		}
	}

	public static float[][] grow(float array[][])
	{
		return grow(array, 1 + array.length);
	}

	public static float[][] shrink(float array[][], int targetSize)
	{
		if (!$assertionsDisabled && targetSize < 0)
			throw new AssertionError((new StringBuilder()).append("size must be positive (got ").append(targetSize).append("): likely integer overflow?").toString());
		int newSize = getShrinkSize(array.length, targetSize, RamUsageEstimator.NUM_BYTES_OBJECT_REF);
		if (newSize != array.length)
		{
			float newArray[][] = new float[newSize][];
			System.arraycopy(array, 0, newArray, 0, newSize);
			return newArray;
		} else
		{
			return array;
		}
	}

	public static int hashCode(char array[], int start, int end)
	{
		int code = 0;
		for (int i = end - 1; i >= start; i--)
			code = code * 31 + array[i];

		return code;
	}

	public static int hashCode(byte array[], int start, int end)
	{
		int code = 0;
		for (int i = end - 1; i >= start; i--)
			code = code * 31 + array[i];

		return code;
	}

	public static boolean equals(char left[], int offsetLeft, char right[], int offsetRight, int length)
	{
		if (offsetLeft + length <= left.length && offsetRight + length <= right.length)
		{
			for (int i = 0; i < length; i++)
				if (left[offsetLeft + i] != right[offsetRight + i])
					return false;

			return true;
		} else
		{
			return false;
		}
	}

	public static boolean equals(int left[], int offsetLeft, int right[], int offsetRight, int length)
	{
		if (offsetLeft + length <= left.length && offsetRight + length <= right.length)
		{
			for (int i = 0; i < length; i++)
				if (left[offsetLeft + i] != right[offsetRight + i])
					return false;

			return true;
		} else
		{
			return false;
		}
	}

	public static int[] toIntArray(Collection ints)
	{
		int result[] = new int[ints.size()];
		int upto = 0;
		for (Iterator iterator = ints.iterator(); iterator.hasNext();)
		{
			int v = ((Integer)iterator.next()).intValue();
			result[upto++] = v;
		}

		if (!$assertionsDisabled && upto != result.length)
			throw new AssertionError();
		else
			return result;
	}

	private static SorterTemplate getSorter(Object a[], Comparator comp)
	{
		return new SorterTemplate(a, comp) {

			private Object pivot;
			final Object val$a[];
			final Comparator val$comp;

			protected void swap(int i, int j)
			{
				Object o = a[i];
				a[i] = a[j];
				a[j] = o;
			}

			protected int compare(int i, int j)
			{
				return comp.compare(a[i], a[j]);
			}

			protected void setPivot(int i)
			{
				pivot = a[i];
			}

			protected int comparePivot(int j)
			{
				return comp.compare(pivot, a[j]);
			}

			
			{
				a = aobj;
				comp = comparator;
				super();
			}
		};
	}

	private static SorterTemplate getSorter(Comparable a[])
	{
		return new SorterTemplate(a) {

			private Comparable pivot;
			final Comparable val$a[];

			protected void swap(int i, int j)
			{
				Comparable o = a[i];
				a[i] = a[j];
				a[j] = o;
			}

			protected int compare(int i, int j)
			{
				return a[i].compareTo(a[j]);
			}

			protected void setPivot(int i)
			{
				pivot = a[i];
			}

			protected int comparePivot(int j)
			{
				return pivot.compareTo(a[j]);
			}

			
			{
				a = acomparable;
				super();
			}
		};
	}

	public static void quickSort(Object a[], int fromIndex, int toIndex, Comparator comp)
	{
		if (toIndex - fromIndex <= 1)
		{
			return;
		} else
		{
			getSorter(a, comp).quickSort(fromIndex, toIndex - 1);
			return;
		}
	}

	public static void quickSort(Object a[], Comparator comp)
	{
		quickSort(a, 0, a.length, comp);
	}

	public static void quickSort(Comparable a[], int fromIndex, int toIndex)
	{
		if (toIndex - fromIndex <= 1)
		{
			return;
		} else
		{
			getSorter(a).quickSort(fromIndex, toIndex - 1);
			return;
		}
	}

	public static void quickSort(Comparable a[])
	{
		quickSort(a, 0, a.length);
	}

	public static void mergeSort(Object a[], int fromIndex, int toIndex, Comparator comp)
	{
		if (toIndex - fromIndex <= 1)
		{
			return;
		} else
		{
			getSorter(a, comp).mergeSort(fromIndex, toIndex - 1);
			return;
		}
	}

	public static void mergeSort(Object a[], Comparator comp)
	{
		mergeSort(a, 0, a.length, comp);
	}

	public static void mergeSort(Comparable a[], int fromIndex, int toIndex)
	{
		if (toIndex - fromIndex <= 1)
		{
			return;
		} else
		{
			getSorter(a).mergeSort(fromIndex, toIndex - 1);
			return;
		}
	}

	public static void mergeSort(Comparable a[])
	{
		mergeSort(a, 0, a.length);
	}

	public static void insertionSort(Object a[], int fromIndex, int toIndex, Comparator comp)
	{
		if (toIndex - fromIndex <= 1)
		{
			return;
		} else
		{
			getSorter(a, comp).insertionSort(fromIndex, toIndex - 1);
			return;
		}
	}

	public static void insertionSort(Object a[], Comparator comp)
	{
		insertionSort(a, 0, a.length, comp);
	}

	public static void insertionSort(Comparable a[], int fromIndex, int toIndex)
	{
		if (toIndex - fromIndex <= 1)
		{
			return;
		} else
		{
			getSorter(a).insertionSort(fromIndex, toIndex - 1);
			return;
		}
	}

	public static void insertionSort(Comparable a[])
	{
		insertionSort(a, 0, a.length);
	}

}
