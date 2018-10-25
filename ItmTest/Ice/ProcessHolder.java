// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ProcessHolder.java

package Ice;

import IceInternal.Ex;

// Referenced classes of package Ice:
//			ObjectHolderBase, Process, Object, _ProcessDisp

public final class ProcessHolder extends ObjectHolderBase
{

	public ProcessHolder()
	{
	}

	public ProcessHolder(Process value)
	{
		this.value = value;
	}

	public void patch(Object v)
	{
		try
		{
			value = (Process)v;
		}
		catch (ClassCastException ex)
		{
			Ex.throwUOE(type(), v.ice_id());
		}
	}

	public String type()
	{
		return _ProcessDisp.ice_staticId();
	}
}
