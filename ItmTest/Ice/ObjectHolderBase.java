// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectHolderBase.java

package Ice;

import IceInternal.Patcher;

// Referenced classes of package Ice:
//			ReadObjectCallback, Object

public abstract class ObjectHolderBase
	implements ReadObjectCallback, Patcher
{

	public Object value;

	public ObjectHolderBase()
	{
	}

	public ObjectHolderBase(Object obj)
	{
		value = obj;
	}

	public void invoke(Object obj)
	{
		patch(obj);
	}
}
