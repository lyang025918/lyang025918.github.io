// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertiesAdminHolder.java

package Ice;

import IceInternal.Ex;

// Referenced classes of package Ice:
//			ObjectHolderBase, PropertiesAdmin, Object, _PropertiesAdminDisp

public final class PropertiesAdminHolder extends ObjectHolderBase
{

	public PropertiesAdminHolder()
	{
	}

	public PropertiesAdminHolder(PropertiesAdmin value)
	{
		this.value = value;
	}

	public void patch(Object v)
	{
		try
		{
			value = (PropertiesAdmin)v;
		}
		catch (ClassCastException ex)
		{
			Ex.throwUOE(type(), v.ice_id());
		}
	}

	public String type()
	{
		return _PropertiesAdminDisp.ice_staticId();
	}
}
