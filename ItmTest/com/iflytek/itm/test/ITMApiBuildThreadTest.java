// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMApiBuildThreadTest.java

package com.iflytek.itm.test;


public class ITMApiBuildThreadTest extends Thread
{

	private String indexPath;
	private String docsPath;
	private String params;

	ITMApiBuildThreadTest(String indexPath, String docsPath, String params)
	{
		this.indexPath = indexPath;
		this.docsPath = docsPath;
		this.params = params;
	}

	public void run()
	{
	}

	public static void main(String args[])
	{
		String indexPath = "e:\\test_home\\index\\itm";
		String docsPath = "e:\\work\\itm\\java\\test";
		String params = "sub_index_dir=test";
		int Thread_Len = 10;
		ITMApiBuildThreadTest trs[] = new ITMApiBuildThreadTest[Thread_Len];
		for (int i = 0; i < trs.length; i++)
			trs[i] = new ITMApiBuildThreadTest(indexPath, (new StringBuilder()).append(docsPath).append(i).toString(), params);

		for (int i = 0; i < trs.length; i++)
			trs[i].start();

	}
}
