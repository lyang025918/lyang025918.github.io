// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SvcSearchResultDel.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.Holder;
import Ice._ObjectDel;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

public interface _SvcSearchResultDel
	extends _ObjectDel
{

	public abstract int getTotalHits(Map map)
		throws LocalExceptionWrapper;

	public abstract int docs(Holder holder, Map map)
		throws LocalExceptionWrapper;

	public abstract int getGroupTotalHits(Map map)
		throws LocalExceptionWrapper;

	public abstract int groups(Holder holder, Map map)
		throws LocalExceptionWrapper;

	public abstract int close(Map map)
		throws LocalExceptionWrapper;
}
