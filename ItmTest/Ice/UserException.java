// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UserException.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.ValueWriter;
import IceUtilInternal.OutputBase;
import java.io.PrintWriter;
import java.io.StringWriter;

// Referenced classes of package Ice:
//			OutputStream, InputStream

public abstract class UserException extends Exception
	implements Cloneable
{

	static final boolean $assertionsDisabled = !Ice/UserException.desiredAssertionStatus();

	public UserException()
	{
	}

	public UserException(Throwable cause)
	{
		super(cause);
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

	public abstract void __write(BasicStream basicstream);

	public abstract void __read(BasicStream basicstream, boolean flag);

	public void __write(OutputStream __outS)
	{
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			return;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			return;
	}

	public boolean __usesClasses()
	{
		return false;
	}

}
