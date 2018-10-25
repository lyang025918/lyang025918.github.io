// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Assert.java

package IceUtilInternal;

import java.io.PrintStream;

public final class Assert
{

	public Assert()
	{
	}

	public static void FinalizerAssert(boolean b)
	{
		if (!b)
		{
			Throwable t = new Throwable();
			StackTraceElement trace[] = t.getStackTrace();
			if (trace.length > 1)
			{
				System.err.println("Assertion failure:");
				StackTraceElement arr$[] = trace;
				int len$ = arr$.length;
				for (int i$ = 0; i$ < len$; i$++)
				{
					StackTraceElement e = arr$[i$];
					System.err.println((new StringBuilder()).append("\tat ").append(e).toString());
				}

			} else
			{
				System.err.println("Assertion failure (no stack trace information)");
			}
		}
	}
}
