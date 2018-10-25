// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Cluster.java

package com.iflytek.itm.mining;

import java.util.Arrays;

public class Cluster
{

	public double center[];
	public int members[];
	public int numMember;

	public void clear()
	{
		Arrays.fill(center, 0.0D);
		Arrays.fill(members, 0);
		numMember = 0;
	}

	public Cluster(int pattern_num, int dim_num)
	{
		center = null;
		members = null;
		numMember = 0;
		center = new double[dim_num];
		members = new int[pattern_num];
		numMember = 0;
	}
}
