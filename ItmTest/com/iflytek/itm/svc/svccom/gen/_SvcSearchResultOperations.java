// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SvcSearchResultOperations.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.Current;
import Ice.Holder;

public interface _SvcSearchResultOperations
{

	public abstract int getTotalHits(Current current);

	public abstract int docs(Holder holder, Current current);

	public abstract int getGroupTotalHits(Current current);

	public abstract int groups(Holder holder, Current current);

	public abstract int close(Current current);
}
