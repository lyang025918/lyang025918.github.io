// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TopicHolder.java

package IceStorm;

import Ice.Object;
import Ice.ObjectHolderBase;
import IceInternal.Ex;

// Referenced classes of package IceStorm:
//			Topic, _TopicDisp

public final class TopicHolder extends ObjectHolderBase
{

	public TopicHolder()
	{
	}

	public TopicHolder(Topic value)
	{
		this.value = value;
	}

	public void patch(Ice.Object v)
	{
		try
		{
			value = (Topic)v;
		}
		catch (ClassCastException ex)
		{
			Ex.throwUOE(type(), v.ice_id());
		}
	}

	public String type()
	{
		return _TopicDisp.ice_staticId();
	}
}
