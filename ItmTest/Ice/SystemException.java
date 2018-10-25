// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SystemException.java

package Ice;

import IceInternal.ValueWriter;
import IceUtilInternal.OutputBase;
import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class SystemException extends RuntimeException
	implements Cloneable
{

	static final boolean $assertionsDisabled = !Ice/SystemException.desiredAssertionStatus();

	public SystemException()
	{
	}

	public Object clone()
	{
		Object o = null;
		try
		{
			o = super.clone();
		}
		catch (CloneNotSupportedException ex)
		{
			if (!$assertionsDisabled)
				throw new AssertionError();
		}
		return o;
	}

	public abstract String ice_name();

	public String toString()
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		OutputBase out = new OutputBase(pw);
		out.setUseTab(false);
		out.print(getClass().getName());
		out.inc();
		ValueWriter.write(this, out);
		pw.flush();
		return sw.toString();
	}

}
