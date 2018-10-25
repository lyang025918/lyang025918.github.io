// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UserExceptionWriter.java

package Ice;

import IceInternal.BasicStream;

// Referenced classes of package Ice:
//			UserException, OutputStream, OutputStreamI, Communicator, 
//			InputStream

public abstract class UserExceptionWriter extends UserException
{

	protected Communicator _communicator;
	static final boolean $assertionsDisabled = !Ice/UserExceptionWriter.desiredAssertionStatus();

	public UserExceptionWriter(Communicator communicator)
	{
		_communicator = communicator;
	}

	public abstract void write(OutputStream outputstream);

	public abstract boolean usesClasses();

	public void __write(BasicStream os)
	{
		OutputStream stream = (OutputStream)os.closure();
		if (stream == null)
			stream = new OutputStreamI(_communicator, os);
		write(stream);
	}

	public void __read(BasicStream is, boolean rid)
	{
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			return;
	}

	public void __write(OutputStream os)
	{
		write(os);
	}

	public void __read(InputStream is, boolean rid)
	{
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			return;
	}

	public boolean __usesClasses()
	{
		return usesClasses();
	}

}
