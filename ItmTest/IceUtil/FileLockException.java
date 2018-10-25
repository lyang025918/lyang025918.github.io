// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileLockException.java

package IceUtil;

import IceInternal.ValueWriter;
import IceUtilInternal.OutputBase;
import java.io.PrintWriter;
import java.io.StringWriter;

public class FileLockException extends RuntimeException
	implements Cloneable
{

	public String path;
	static final boolean $assertionsDisabled = !IceUtil/FileLockException.desiredAssertionStatus();

	public FileLockException()
	{
	}

	public FileLockException(String path)
	{
		this.path = path;
	}

	public FileLockException(Throwable cause)
	{
		super(cause);
	}

	public FileLockException(String path, Throwable cause)
	{
		super(cause);
		this.path = path;
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

	public String ice_name()
	{
		return "IceUtil::FileLockException";
	}

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
