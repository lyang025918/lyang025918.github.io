// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MD5Util.java

package mylib.utils;

import java.io.PrintStream;
import java.security.MessageDigest;

public class MD5Util
{

	public MD5Util()
	{
	}

	public static String doMD5(String origin)
	{
		String resultString = null;
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(origin.getBytes());
			byte tmp[] = md.digest();
			resultString = byteArrayToHexString(tmp);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return resultString;
	}

	public static String byteArrayToHexString(byte b[])
	{
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b)
	{
		String hexDigits[] = {
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", 
			"a", "b", "c", "d", "e", "f"
		};
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return (new StringBuilder()).append(hexDigits[d1]).append(hexDigits[d2]).toString();
	}

	public static void main(String args[])
	{
		String src = "a";
		String dst = doMD5(src);
		System.out.printf("src=%s\ndst=%s\n", new Object[] {
			src, dst
		});
	}
}
