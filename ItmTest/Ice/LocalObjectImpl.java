// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LocalObjectImpl.java

package Ice;


// Referenced classes of package Ice:
//			LocalObject

/**
 * @deprecated Class LocalObjectImpl is deprecated
 */

public abstract class LocalObjectImpl
	implements LocalObject, Cloneable
{

	public LocalObjectImpl()
	{
	}

	public Object clone()
		throws CloneNotSupportedException
	{
		return super.clone();
	}

	public int ice_hash()
	{
		return hashCode();
	}
}
