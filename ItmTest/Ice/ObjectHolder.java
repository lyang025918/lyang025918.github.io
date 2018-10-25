// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectHolder.java

package Ice;


// Referenced classes of package Ice:
//			ObjectHolderBase, ObjectImpl, Object

public final class ObjectHolder extends ObjectHolderBase
{

	public ObjectHolder()
	{
	}

	public ObjectHolder(Object value)
	{
		super(value);
	}

	public void patch(Object v)
	{
		value = v;
	}

	public String type()
	{
		return ObjectImpl.ice_staticId();
	}
}
