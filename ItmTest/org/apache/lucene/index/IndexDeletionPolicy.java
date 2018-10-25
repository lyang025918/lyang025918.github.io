// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndexDeletionPolicy.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.List;

public interface IndexDeletionPolicy
{

	public abstract void onInit(List list)
		throws IOException;

	public abstract void onCommit(List list)
		throws IOException;
}
