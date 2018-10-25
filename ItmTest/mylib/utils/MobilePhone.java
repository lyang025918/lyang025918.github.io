// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MobilePhone.java

package mylib.utils;

import java.io.PrintStream;
import java.util.Random;

public class MobilePhone
{

	public MobilePhone()
	{
	}

	public static void main(String args[])
	{
		String code = randomGenerate();
		System.out.println((new StringBuilder()).append("code=").append(code).toString());
	}

	public static String randomGenerate()
	{
		Random ran = new Random(System.currentTimeMillis());
		StringBuffer buffer = new StringBuffer(1024);
		buffer.setLength(0);
		buffer.append(1);
		int temp = (ran.nextInt(3) * 25) / 10 + 3;
		buffer.append(temp);
		buffer.append(temp != 8 ? ran.nextInt(10) : ran.nextInt(3) + 6);
		buffer.append(ran.nextInt(0x55d4a80) + 0x989680);
		return buffer.toString();
	}

	public static boolean isMobileNumber(String phone)
	{
		boolean isExist = false;
		phone = phone.trim();
		String code = phone;
		if (code.startsWith("134") || code.startsWith("135") || code.startsWith("136") || code.startsWith("137") || code.startsWith("138") || code.startsWith("139") || code.startsWith("159") || code.startsWith("158") || code.startsWith("150") || code.startsWith("157") || code.startsWith("151") || code.startsWith("188") || code.startsWith("189"))
			isExist = true;
		return isExist;
	}
}
