// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HotUtil.java

package com.iflytek.itm.mining;


// Referenced classes of package com.iflytek.itm.mining:
//			Cluster

public class HotUtil
{

	public static int SUCCESS = 1;
	public static int FAILURE = 0;
	public static int TRUE = 1;
	public static int FALSE = 0;
	public static String NGRAM_WORD_SPLIT_CHAR = "#";

	public HotUtil()
	{
	}

	public static void arrayCopy(double dst[], double src[])
	{
		for (int i = 0; i < src.length; i++)
			dst[i] = src[i];

	}

	public static void clusterArrayCopy(Cluster dst[], Cluster src[], int n)
	{
		for (int i = 0; i < n; i++)
			clusterCopy(dst[i], src[i]);

	}

	public static void clusterCopy(Cluster dst, Cluster src)
	{
		for (int ic = 0; ic < src.center.length; ic++)
			dst.center[ic] = src.center[ic];

		for (int im = 0; im < src.members.length; im++)
			dst.members[im] = src.members[im];

		dst.numMember = src.numMember;
	}

}
