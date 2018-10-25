// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NoLockFactory.java

package org.apache.lucene.store;

import java.io.IOException;

// Referenced classes of package org.apache.lucene.store:
//			Lock

class NoLock extends Lock
{

	NoLock()
	{
	}

	public boolean obtain()
		throws IOException
	{
		return true;
	}

	public void release()
	{
	}

	public boolean isLocked()
	{
		return false;
	}

	public String toString()
	{
		return "NoLock";
	}
}
