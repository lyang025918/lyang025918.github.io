// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Base64.java

package IceUtilInternal;

import java.nio.ByteBuffer;

public class Base64
{

	public Base64()
	{
	}

	public static String encode(byte plainSeq[])
	{
		if (plainSeq == null || plainSeq.length == 0)
			return "";
		int base64Bytes = (plainSeq.length * 4) / 3 + 1;
		int newlineBytes = (base64Bytes * 2) / 76 + 1;
		int totalBytes = base64Bytes + newlineBytes;
		StringBuilder retval = new StringBuilder(totalBytes);
		for (int i = 0; i < plainSeq.length; i += 3)
		{
			int by1 = plainSeq[i] & 0xff;
			int by2 = 0;
			int by3 = 0;
			if (i + 1 < plainSeq.length)
				by2 = plainSeq[i + 1] & 0xff;
			if (i + 2 < plainSeq.length)
				by3 = plainSeq[i + 2] & 0xff;
			int by4 = by1 >> 2 & 0xff;
			int by5 = ((by1 & 3) << 4 | by2 >> 4) & 0xff;
			int by6 = ((by2 & 0xf) << 2 | by3 >> 6) & 0xff;
			int by7 = by3 & 0x3f;
			retval.append(encode((byte)by4));
			retval.append(encode((byte)by5));
			if (i + 1 < plainSeq.length)
				retval.append(encode((byte)by6));
			else
				retval.append('=');
			if (i + 2 < plainSeq.length)
				retval.append(encode((byte)by7));
			else
				retval.append('=');
		}

		StringBuilder outString = new StringBuilder(totalBytes);
		int iter;
		for (iter = 0; retval.length() - iter > 76; iter += 76)
		{
			outString.append(retval.substring(iter, iter + 76));
			outString.append("\r\n");
		}

		outString.append(retval.substring(iter));
		return outString.toString();
	}

	public static byte[] decode(String str)
	{
		StringBuilder newStr = new StringBuilder(str.length());
		for (int j = 0; j < str.length(); j++)
		{
			char c = str.charAt(j);
			if (isBase64(c))
				newStr.append(c);
		}

		if (newStr.length() == 0)
			return null;
		int totalBytes = (newStr.length() * 3) / 4 + 1;
		ByteBuffer retval = ByteBuffer.allocate(totalBytes);
		int pos = 0;
		for (int i = 0; i < newStr.length(); i += 4)
		{
			char c1 = 'A';
			char c2 = 'A';
			char c3 = 'A';
			char c4 = 'A';
			c1 = newStr.charAt(i);
			if (i + 1 < newStr.length())
				c2 = newStr.charAt(i + 1);
			if (i + 2 < newStr.length())
				c3 = newStr.charAt(i + 2);
			if (i + 3 < newStr.length())
				c4 = newStr.charAt(i + 3);
			int by1 = decode(c1) & 0xff;
			int by2 = decode(c2) & 0xff;
			int by3 = decode(c3) & 0xff;
			int by4 = decode(c4) & 0xff;
			retval.put((byte)(by1 << 2 | by2 >> 4));
			pos++;
			if (c3 != '=')
			{
				retval.put((byte)((by2 & 0xf) << 4 | by3 >> 2));
				pos++;
			}
			if (c4 != '=')
			{
				retval.put((byte)((by3 & 3) << 6 | by4));
				pos++;
			}
		}

		byte arr[] = new byte[pos];
		System.arraycopy(retval.array(), 0, arr, 0, pos);
		return arr;
	}

	public static boolean isBase64(char c)
	{
		if (c >= 'A' && c <= 'Z')
			return true;
		if (c >= 'a' && c <= 'z')
			return true;
		if (c >= '0' && c <= '9')
			return true;
		if (c == '+')
			return true;
		if (c == '/')
			return true;
		return c == '=';
	}

	private static char encode(byte uc)
	{
		if (uc < 26)
			return (char)(65 + uc);
		if (uc < 52)
			return (char)(97 + (uc - 26));
		if (uc < 62)
			return (char)(48 + (uc - 52));
		return uc != 62 ? '/' : '+';
	}

	private static byte decode(char c)
	{
		if (c >= 'A' && c <= 'Z')
			return (byte)(c - 65);
		if (c >= 'a' && c <= 'z')
			return (byte)((c - 97) + 26);
		if (c >= '0' && c <= '9')
			return (byte)((c - 48) + 52);
		return ((byte)(c != '+' ? 63 : 62));
	}
}
