// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Ex.java

package IceInternal;

import Ice.MemoryLimitException;
import Ice.UnexpectedObjectException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Ex
{

	public Ex()
	{
	}

	public static void throwUOE(String expectedType, String actualType)
	{
		throw new UnexpectedObjectException((new StringBuilder()).append("expected element of type `").append(expectedType).append("' but received '").append(actualType).toString(), actualType, expectedType);
	}

	public static void throwMemoryLimitException(int requested, int maximum)
	{
		throw new MemoryLimitException((new StringBuilder()).append("requested ").append(requested).append(" bytes, maximum allowed is ").append(maximum).append(" bytes (see Ice.MessageSizeMax)").toString());
	}

	public static String toString(Throwable ex)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		pw.flush();
		return sw.toString();
	}
}
