// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ApplicationObserverHolder.java

package IceGrid;

import Ice.Object;
import Ice.ObjectHolderBase;
import IceInternal.Ex;

// Referenced classes of package IceGrid:
//			ApplicationObserver, _ApplicationObserverDisp

public final class ApplicationObserverHolder extends ObjectHolderBase
{

	public ApplicationObserverHolder()
	{
	}

	public ApplicationObserverHolder(ApplicationObserver value)
	{
		this.value = value;
	}

	public void patch(Ice.Object v)
	{
		try
		{
			value = (ApplicationObserver)v;
		}
		catch (ClassCastException ex)
		{
			Ex.throwUOE(type(), v.ice_id());
		}
	}

	public String type()
	{
		return _ApplicationObserverDisp.ice_staticId();
	}
}
