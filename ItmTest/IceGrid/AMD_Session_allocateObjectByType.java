// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AMD_Session_allocateObjectByType.java

package IceGrid;

import Ice.AMDCallback;
import Ice.ObjectPrx;

public interface AMD_Session_allocateObjectByType
	extends AMDCallback
{

	public abstract void ice_response(ObjectPrx objectprx);
}
