// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectWriter.java

package Ice;

import IceInternal.BasicStream;

// Referenced classes of package Ice:
//			ObjectImpl, OutputStream

public abstract class ObjectWriter extends ObjectImpl
{

	static final boolean $assertionsDisabled = !Ice/ObjectWriter.desiredAssertionStatus();

	public ObjectWriter()
	{
	}

	public abstract void write(OutputStream outputstream);

	public void __write(BasicStream os)
	{
		OutputStream stream = (OutputStream)os.closure();
		write(stream);
	}

	public void __read(BasicStream is, boolean rid)
	{
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			return;
	}

}
