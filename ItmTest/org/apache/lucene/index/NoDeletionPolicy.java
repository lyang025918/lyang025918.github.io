// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NoDeletionPolicy.java

package org.apache.lucene.index;

import java.util.List;

// Referenced classes of package org.apache.lucene.index:
//			IndexDeletionPolicy

public final class NoDeletionPolicy
	implements IndexDeletionPolicy
{

	public static final IndexDeletionPolicy INSTANCE = new NoDeletionPolicy();

	private NoDeletionPolicy()
	{
	}

	public void onCommit(List list)
	{
	}

	public void onInit(List list)
	{
	}

}
