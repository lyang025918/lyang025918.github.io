// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectReader.java

package Ice;

import IceInternal.BasicStream;

// Referenced classes of package Ice:
//			ObjectImpl, InputStream

public abstract class ObjectReader extends ObjectImpl
{

	static final boolean $assertionsDisabled = !Ice/ObjectReader.desiredAssertionStatus();

	public ObjectReader()
	{
	}

	public abstract void read(InputStream inputstream, boolean flag);

	public void __write(BasicStream os)
	{
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			return;
	}

	public void __read(BasicStream is, boolean rid)
	{
		InputStream stream = (InputStream)is.closure();
		read(stream, rid);
	}

}
