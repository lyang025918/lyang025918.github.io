// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DelRepeat.java

package mylib.utils;

import java.io.*;
import java.util.*;

public class DelRepeat
{

	public DelRepeat()
	{
	}

	public static void main(String args[])
		throws Exception
	{
		String path1 = "e:\\study\\source\\java\\lucene\\lucene-4.0.0\\log\\FilterSearch.log";
		String path2 = "e:\\study\\source\\java\\lucene\\lucene-4.0.0\\log\\ฤ๚าช.log";
		Set set1 = readLines(path1);
		printSet("set1", set1);
		System.out.println("end");
	}

	public static void printSet(String setName, Set set)
	{
		System.out.println((new StringBuilder()).append("set=").append(setName).toString());
		for (Iterator it = set.iterator(); it.hasNext(); System.out.println((new StringBuilder()).append("").append((String)it.next()).toString()));
	}

	public static Set readLines(String path)
		throws Exception
	{
		Set lines = new HashSet();
		BufferedReader bw = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "gbk"));
		String line = null;
		do
		{
			if ((line = bw.readLine()) == null)
				break;
			line = line.replace("\r", "");
			line = line.replace("\n", "");
			if (!line.isEmpty())
				lines.add(line);
		} while (true);
		bw.close();
		return lines;
	}

	public static Set intersection(String path1, String path2)
		throws Exception
	{
		Set lines1 = readLines(path1);
		Set lines2 = readLines(path2);
		Set result = new HashSet();
		result.addAll(lines1);
		result.retainAll(lines2);
		return result;
	}

	public static Set union(String path1, String path2)
		throws Exception
	{
		Set lines1 = readLines(path1);
		Set lines2 = readLines(path2);
		Set result = new HashSet();
		result.addAll(lines1);
		result.addAll(lines2);
		return result;
	}
}
