// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TwoPhaseCommitTool.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.Map;

// Referenced classes of package org.apache.lucene.index:
//			TwoPhaseCommit

public final class TwoPhaseCommitTool
{
	public static class CommitFailException extends IOException
	{

		public CommitFailException(Throwable cause, TwoPhaseCommit obj)
		{
			super((new StringBuilder()).append("commit() failed on ").append(obj).toString());
			initCause(cause);
		}
	}

	public static class PrepareCommitFailException extends IOException
	{

		public PrepareCommitFailException(Throwable cause, TwoPhaseCommit obj)
		{
			super((new StringBuilder()).append("prepareCommit() failed on ").append(obj).toString());
			initCause(cause);
		}
	}

	public static final class TwoPhaseCommitWrapper
		implements TwoPhaseCommit
	{

		private final TwoPhaseCommit tpc;
		private final Map commitData;

		public void prepareCommit()
			throws IOException
		{
			prepareCommit(commitData);
		}

		public void prepareCommit(Map commitData)
			throws IOException
		{
			tpc.prepareCommit(this.commitData);
		}

		public void commit()
			throws IOException
		{
			commit(commitData);
		}

		public void commit(Map commitData)
			throws IOException
		{
			tpc.commit(this.commitData);
		}

		public void rollback()
			throws IOException
		{
			tpc.rollback();
		}

		public TwoPhaseCommitWrapper(TwoPhaseCommit tpc, Map commitData)
		{
			this.tpc = tpc;
			this.commitData = commitData;
		}
	}


	private TwoPhaseCommitTool()
	{
	}

	private static transient void rollback(TwoPhaseCommit objects[])
	{
		TwoPhaseCommit arr$[] = objects;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			TwoPhaseCommit tpc = arr$[i$];
			if (tpc == null)
				continue;
			try
			{
				tpc.rollback();
			}
			catch (Throwable t) { }
		}

	}

	public static transient void execute(TwoPhaseCommit objects[])
		throws PrepareCommitFailException, CommitFailException
	{
		TwoPhaseCommit tpc = null;
		try
		{
			for (int i = 0; i < objects.length; i++)
			{
				tpc = objects[i];
				if (tpc != null)
					tpc.prepareCommit();
			}

		}
		catch (Throwable t)
		{
			rollback(objects);
			throw new PrepareCommitFailException(t, tpc);
		}
		try
		{
			for (int i = 0; i < objects.length; i++)
			{
				tpc = objects[i];
				if (tpc != null)
					tpc.commit();
			}

		}
		catch (Throwable t)
		{
			rollback(objects);
			throw new CommitFailException(t, tpc);
		}
	}
}
