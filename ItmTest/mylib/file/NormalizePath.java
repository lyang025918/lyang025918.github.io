// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NormalizePath.java

package mylib.file;

import java.io.File;
import java.io.PrintStream;

public class NormalizePath
{

	static final char BSLASH_CHR = 92;
	static final char SLASH_CHR = 47;

	public NormalizePath()
	{
	}

	public static String doNorm(String path, char split)
	{
		return doNorm(path, split, false);
	}

	public static String doNorm(String path, char split, boolean dirCreate)
	{
		String tmp = "";
		if (path.isEmpty() || split != '\\' && split != '/')
			return path;
		path = path.trim();
		for (int i = 0; i < path.length(); i++)
			if (path.charAt(i) == '\\' || path.charAt(i) == '/')
				tmp = (new StringBuilder()).append(tmp).append(split).toString();
			else
				tmp = (new StringBuilder()).append(tmp).append(path.charAt(i)).toString();

		File f = new File(tmp);
		if (!f.isDirectory() && dirCreate)
		{
			boolean succ = f.mkdirs();
			if (!succ)
				System.out.println((new StringBuilder()).append("create dir failed, dir=").append(tmp).toString());
		}
		if (f.isDirectory() && tmp.charAt(tmp.length() - 1) != split)
			tmp = (new StringBuilder()).append(tmp).append(split).toString();
		return tmp;
	}

	public static void main(String args[])
	{
		String path = "e:\\download";
		System.out.printf("before normalize: %s\n", new Object[] {
			path
		});
		path = doNorm(path, '\\');
		System.out.printf("after normalize: %s\n", new Object[] {
			path
		});
	}
}
