// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   KeepOnlyLastCommitDeletionPolicy.java

package org.apache.lucene.index;

import java.util.List;

// Referenced classes of package org.apache.lucene.index:
//			IndexCommit, IndexDeletionPolicy

public final class KeepOnlyLastCommitDeletionPolicy
	implements IndexDeletionPolicy
{

	public KeepOnlyLastCommitDeletionPolicy()
	{
	}

	public void onInit(List commits)
	{
		onCommit(commits);
	}

	public void onCommit(List commits)
	{
		int size = commits.size();
		for (int i = 0; i < size - 1; i++)
			((IndexCommit)commits.get(i)).delete();

	}
}
