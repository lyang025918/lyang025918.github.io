// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TwoPhaseCommit.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.Map;

public interface TwoPhaseCommit
{

	public abstract void prepareCommit()
		throws IOException;

	public abstract void prepareCommit(Map map)
		throws IOException;

	public abstract void commit()
		throws IOException;

	public abstract void commit(Map map)
		throws IOException;

	public abstract void rollback()
		throws IOException;
}
