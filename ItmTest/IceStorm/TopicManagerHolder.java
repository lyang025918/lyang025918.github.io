// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TopicManagerHolder.java

package IceStorm;

import Ice.Object;
import Ice.ObjectHolderBase;
import IceInternal.Ex;

// Referenced classes of package IceStorm:
//			TopicManager, _TopicManagerDisp

public final class TopicManagerHolder extends ObjectHolderBase
{

	public TopicManagerHolder()
	{
	}

	public TopicManagerHolder(TopicManager value)
	{
		this.value = value;
	}

	public void patch(Ice.Object v)
	{
		try
		{
			value = (TopicManager)v;
		}
		catch (ClassCastException ex)
		{
			Ex.throwUOE(type(), v.ice_id());
		}
	}

	public String type()
	{
		return _TopicManagerDisp.ice_staticId();
	}
}
