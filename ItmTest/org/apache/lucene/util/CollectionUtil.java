// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CollectionUtil.java

package org.apache.lucene.util;

import java.util.*;

// Referenced classes of package org.apache.lucene.util:
//			SorterTemplate

public final class CollectionUtil
{

	private CollectionUtil()
	{
	}

	private static SorterTemplate getSorter(List list, Comparator comp)
	{
		if (!(list instanceof RandomAccess))
			throw new IllegalArgumentException("CollectionUtil can only sort random access lists in-place.");
		else
			return new SorterTemplate(list, comp) {

				private Object pivot;
				final List val$list;
				final Comparator val$comp;

				protected void swap(int i, int j)
				{
					Collections.swap(list, i, j);
				}

				protected int compare(int i, int j)
				{
					return comp.compare(list.get(i), list.get(j));
				}

				protected void setPivot(int i)
				{
					pivot = list.get(i);
				}

				protected int comparePivot(int j)
				{
					return comp.compare(pivot, list.get(j));
				}

			
			{
				list = list1;
				comp = comparator;
				super();
			}
			};
	}

	private static SorterTemplate getSorter(List list)
	{
		if (!(list instanceof RandomAccess))
			throw new IllegalArgumentException("CollectionUtil can only sort random access lists in-place.");
		else
			return new SorterTemplate(list) {

				private Comparable pivot;
				final List val$list;

				protected void swap(int i, int j)
				{
					Collections.swap(list, i, j);
				}

				protected int compare(int i, int j)
				{
					return ((Comparable)list.get(i)).compareTo(list.get(j));
				}

				protected void setPivot(int i)
				{
					pivot = (Comparable)list.get(i);
				}

				protected int comparePivot(int j)
				{
					return pivot.compareTo(list.get(j));
				}

			
			{
				list = list1;
				super();
			}
			};
	}

	public static void quickSort(List list, Comparator comp)
	{
		int size = list.size();
		if (size <= 1)
		{
			return;
		} else
		{
			getSorter(list, comp).quickSort(0, size - 1);
			return;
		}
	}

	public static void quickSort(List list)
	{
		int size = list.size();
		if (size <= 1)
		{
			return;
		} else
		{
			getSorter(list).quickSort(0, size - 1);
			return;
		}
	}

	public static void mergeSort(List list, Comparator comp)
	{
		int size = list.size();
		if (size <= 1)
		{
			return;
		} else
		{
			getSorter(list, comp).mergeSort(0, size - 1);
			return;
		}
	}

	public static void mergeSort(List list)
	{
		int size = list.size();
		if (size <= 1)
		{
			return;
		} else
		{
			getSorter(list).mergeSort(0, size - 1);
			return;
		}
	}

	public static void insertionSort(List list, Comparator comp)
	{
		int size = list.size();
		if (size <= 1)
		{
			return;
		} else
		{
			getSorter(list, comp).insertionSort(0, size - 1);
			return;
		}
	}

	public static void insertionSort(List list)
	{
		int size = list.size();
		if (size <= 1)
		{
			return;
		} else
		{
			getSorter(list).insertionSort(0, size - 1);
			return;
		}
	}
}
