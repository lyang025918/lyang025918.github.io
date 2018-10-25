// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMApiMaintainTest.java

package com.iflytek.itm.test;

import com.iflytek.itm.api.ITM;
import com.iflytek.itm.api.ITMFactory;
import java.io.PrintStream;

public class ITMApiMaintainTest
{

	public ITMApiMaintainTest()
	{
	}

	public static void main(String args[])
	{
		System.out.println("≤‚ ‘π¶ƒ‹");
		ITMApiMaintainTest inst = new ITMApiMaintainTest();
		inst.doMaintain();
	}

	public void doMaintain()
	{
		long start = System.currentTimeMillis();
		String indexPath = "e:\\study\\source\\java\\lucene\\index\\itm";
		ITM inst = ITMFactory.create();
		int ret = inst.maintain(indexPath, "delete", "sub_index_dir=test");
		if (ret != 0)
			System.out.println((new StringBuilder()).append("Error: errcode=").append(ret).toString());
		long end = System.currentTimeMillis();
		System.out.println((new StringBuilder()).append(end - start).append(" total milliseconds").toString());
	}
}
