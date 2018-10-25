// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SvcSearchResultOperationsNC.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.Holder;

public interface _SvcSearchResultOperationsNC
{

	public abstract int getTotalHits();

	public abstract int docs(Holder holder);

	public abstract int getGroupTotalHits();

	public abstract int groups(Holder holder);

	public abstract int close();
}
