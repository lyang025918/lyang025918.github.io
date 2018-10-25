// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HotViewWords.java

package com.iflytek.itm.mining;

import java.io.*;

public class HotViewWords
{

	public HotViewWords()
	{
	}

	public static void main(String args[])
	{
		String fileName = "GetSenWord.pl";
		String params[] = new String[3];
		params[0] = "ÒµÎñ´Ê.txt";
		params[1] = "Class1text.txt";
		params[2] = "Class1words.txt";
		HotViewWords inst = new HotViewWords();
		inst.execPerl(fileName, params);
	}

	private String execPerl(String fileName, String args[])
	{
		String cmd = getCommand(fileName, args);
		String msg = "";
		String brs = "";
		try
		{
			Process pro = Runtime.getRuntime().exec(cmd);
			InputStream ins = pro.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(ins));
			while ((brs = br.readLine()) != null) 
				msg = (new StringBuilder()).append(msg).append(brs).toString();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println((new StringBuilder()).append("error: ").append(e.getMessage()).toString());
		}
		return msg;
	}

	private String getCommand(String fileName, String args[])
	{
		StringBuffer buffer = new StringBuffer(1024);
		buffer.append((new StringBuilder()).append("perl ").append(fileName).append(" ").toString());
		for (int i = 0; i < args.length; i++)
			buffer.append((new StringBuilder()).append(args[i]).append(" ").toString());

		buffer.append("50");
		return buffer.toString();
	}
}
