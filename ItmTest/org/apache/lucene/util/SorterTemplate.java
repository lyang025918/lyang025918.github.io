// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SorterTemplate.java

package org.apache.lucene.util;


public abstract class SorterTemplate
{

	private static final int MERGESORT_THRESHOLD = 12;
	private static final int QUICKSORT_THRESHOLD = 7;

	public SorterTemplate()
	{
	}

	protected abstract void swap(int i, int j);

	protected abstract int compare(int i, int j);

	protected abstract void setPivot(int i);

	protected abstract int comparePivot(int i);

	public final void insertionSort(int lo, int hi)
	{
		for (int i = lo + 1; i <= hi; i++)
		{
			for (int j = i; j > lo && compare(j - 1, j) > 0; j--)
				swap(j - 1, j);

		}

	}

	public final void quickSort(int lo, int hi)
	{
		if (hi <= lo)
		{
			return;
		} else
		{
			quickSort(lo, hi, 32 - Integer.numberOfLeadingZeros(hi - lo) << 1);
			return;
		}
	}

	private void quickSort(int lo, int hi, int maxDepth)
	{
		int diff = hi - lo;
		if (diff <= 7)
		{
			insertionSort(lo, hi);
			return;
		}
		if (--maxDepth == 0)
		{
			mergeSort(lo, hi);
			return;
		}
		int mid = lo + (diff >>> 1);
		if (compare(lo, mid) > 0)
			swap(lo, mid);
		if (compare(mid, hi) > 0)
		{
			swap(mid, hi);
			if (compare(lo, mid) > 0)
				swap(lo, mid);
		}
		int left = lo + 1;
		int right = hi - 1;
		setPivot(mid);
		do
		{
			for (; comparePivot(right) < 0; right--);
			for (; left < right && comparePivot(left) >= 0; left++);
			if (left < right)
			{
				swap(left, right);
				right--;
			} else
			{
				quickSort(lo, left, maxDepth);
				quickSort(left + 1, hi, maxDepth);
				return;
			}
		} while (true);
	}

	public final void mergeSort(int lo, int hi)
	{
		int diff = hi - lo;
		if (diff <= 12)
		{
			insertionSort(lo, hi);
			return;
		} else
		{
			int mid = lo + (diff >>> 1);
			mergeSort(lo, mid);
			mergeSort(mid, hi);
			merge(lo, mid, hi, mid - lo, hi - mid);
			return;
		}
	}

	private void merge(int lo, int pivot, int hi, int len1, int len2)
	{
		if (len1 == 0 || len2 == 0)
			return;
		if (len1 + len2 == 2)
		{
			if (compare(pivot, lo) < 0)
				swap(pivot, lo);
			return;
		}
		int first_cut;
		int second_cut;
		int len11;
		int len22;
		if (len1 > len2)
		{
			len11 = len1 >>> 1;
			first_cut = lo + len11;
			second_cut = lower(pivot, hi, first_cut);
			len22 = second_cut - pivot;
		} else
		{
			len22 = len2 >>> 1;
			second_cut = pivot + len22;
			first_cut = upper(lo, pivot, second_cut);
			len11 = first_cut - lo;
		}
		rotate(first_cut, pivot, second_cut);
		int new_mid = first_cut + len22;
		merge(lo, first_cut, new_mid, len11, len22);
		merge(new_mid, second_cut, hi, len1 - len11, len2 - len22);
	}

	private void rotate(int lo, int mid, int hi)
	{
		int lot = lo;
		for (int hit = mid - 1; lot < hit;)
			swap(lot++, hit--);

		lot = mid;
		for (int hit = hi - 1; lot < hit;)
			swap(lot++, hit--);

		lot = lo;
		for (int hit = hi - 1; lot < hit;)
			swap(lot++, hit--);

	}

	private int lower(int lo, int hi, int val)
	{
		for (int len = hi - lo; len > 0;)
		{
			int half = len >>> 1;
			int mid = lo + half;
			if (compare(mid, val) < 0)
			{
				lo = mid + 1;
				len = len - half - 1;
			} else
			{
				len = half;
			}
		}

		return lo;
	}

	private int upper(int lo, int hi, int val)
	{
		for (int len = hi - lo; len > 0;)
		{
			int half = len >>> 1;
			int mid = lo + half;
			if (compare(val, mid) < 0)
			{
				len = half;
			} else
			{
				lo = mid + 1;
				len = len - half - 1;
			}
		}

		return lo;
	}
}
