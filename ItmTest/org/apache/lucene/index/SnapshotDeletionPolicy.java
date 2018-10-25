// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SnapshotDeletionPolicy.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.store.Directory;

// Referenced classes of package org.apache.lucene.index:
//			IndexCommit, IndexDeletionPolicy

public class SnapshotDeletionPolicy
	implements IndexDeletionPolicy
{
	protected class SnapshotCommitPoint extends IndexCommit
	{

		protected IndexCommit cp;
		final SnapshotDeletionPolicy this$0;

		public String toString()
		{
			return (new StringBuilder()).append("SnapshotDeletionPolicy.SnapshotCommitPoint(").append(cp).append(")").toString();
		}

		protected boolean shouldDelete(String segmentsFileName)
		{
			return !segmentsFileToIDs.containsKey(segmentsFileName);
		}

		public void delete()
		{
			synchronized (SnapshotDeletionPolicy.this)
			{
				if (shouldDelete(getSegmentsFileName()))
					cp.delete();
			}
		}

		public Directory getDirectory()
		{
			return cp.getDirectory();
		}

		public Collection getFileNames()
			throws IOException
		{
			return cp.getFileNames();
		}

		public long getGeneration()
		{
			return cp.getGeneration();
		}

		public String getSegmentsFileName()
		{
			return cp.getSegmentsFileName();
		}

		public Map getUserData()
			throws IOException
		{
			return cp.getUserData();
		}

		public boolean isDeleted()
		{
			return cp.isDeleted();
		}

		public int getSegmentCount()
		{
			return cp.getSegmentCount();
		}

		protected SnapshotCommitPoint(IndexCommit cp)
		{
			this$0 = SnapshotDeletionPolicy.this;
			super();
			this.cp = cp;
		}
	}

	private static class SnapshotInfo
	{

		String id;
		String segmentsFileName;
		IndexCommit commit;

		public String toString()
		{
			return (new StringBuilder()).append(id).append(" : ").append(segmentsFileName).toString();
		}

		public SnapshotInfo(String id, String segmentsFileName, IndexCommit commit)
		{
			this.id = id;
			this.segmentsFileName = segmentsFileName;
			this.commit = commit;
		}
	}


	private Map idToSnapshot;
	private Map segmentsFileToIDs;
	private IndexDeletionPolicy primary;
	protected IndexCommit lastCommit;

	public SnapshotDeletionPolicy(IndexDeletionPolicy primary)
	{
		idToSnapshot = new HashMap();
		segmentsFileToIDs = new HashMap();
		this.primary = primary;
	}

	public SnapshotDeletionPolicy(IndexDeletionPolicy primary, Map snapshotsInfo)
	{
		this(primary);
		if (snapshotsInfo != null)
		{
			java.util.Map.Entry e;
			for (Iterator i$ = snapshotsInfo.entrySet().iterator(); i$.hasNext(); registerSnapshotInfo((String)e.getKey(), (String)e.getValue(), null))
				e = (java.util.Map.Entry)i$.next();

		}
	}

	protected void checkSnapshotted(String id)
	{
		if (isSnapshotted(id))
			throw new IllegalStateException((new StringBuilder()).append("Snapshot ID ").append(id).append(" is already used - must be unique").toString());
		else
			return;
	}

	protected void registerSnapshotInfo(String id, String segment, IndexCommit commit)
	{
		idToSnapshot.put(id, new SnapshotInfo(id, segment, commit));
		Set ids = (Set)segmentsFileToIDs.get(segment);
		if (ids == null)
		{
			ids = new HashSet();
			segmentsFileToIDs.put(segment, ids);
		}
		ids.add(id);
	}

	protected List wrapCommits(List commits)
	{
		List wrappedCommits = new ArrayList(commits.size());
		IndexCommit ic;
		for (Iterator i$ = commits.iterator(); i$.hasNext(); wrappedCommits.add(new SnapshotCommitPoint(ic)))
			ic = (IndexCommit)i$.next();

		return wrappedCommits;
	}

	public synchronized IndexCommit getSnapshot(String id)
	{
		SnapshotInfo snapshotInfo = (SnapshotInfo)idToSnapshot.get(id);
		if (snapshotInfo == null)
			throw new IllegalStateException((new StringBuilder()).append("No snapshot exists by ID: ").append(id).toString());
		else
			return snapshotInfo.commit;
	}

	public synchronized Map getSnapshots()
	{
		Map snapshots = new HashMap();
		java.util.Map.Entry e;
		for (Iterator i$ = idToSnapshot.entrySet().iterator(); i$.hasNext(); snapshots.put(e.getKey(), ((SnapshotInfo)e.getValue()).segmentsFileName))
			e = (java.util.Map.Entry)i$.next();

		return snapshots;
	}

	public boolean isSnapshotted(String id)
	{
		return idToSnapshot.containsKey(id);
	}

	public synchronized void onCommit(List commits)
		throws IOException
	{
		primary.onCommit(wrapCommits(commits));
		lastCommit = (IndexCommit)commits.get(commits.size() - 1);
	}

	public synchronized void onInit(List commits)
		throws IOException
	{
		primary.onInit(wrapCommits(commits));
		lastCommit = (IndexCommit)commits.get(commits.size() - 1);
		Iterator i$ = commits.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			IndexCommit commit = (IndexCommit)i$.next();
			Set ids = (Set)segmentsFileToIDs.get(commit.getSegmentsFileName());
			if (ids != null)
			{
				Iterator i$ = ids.iterator();
				while (i$.hasNext()) 
				{
					String id = (String)i$.next();
					((SnapshotInfo)idToSnapshot.get(id)).commit = commit;
				}
			}
		} while (true);
		ArrayList idsToRemove = null;
		Iterator i$ = idToSnapshot.entrySet().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			java.util.Map.Entry e = (java.util.Map.Entry)i$.next();
			if (((SnapshotInfo)e.getValue()).commit == null)
			{
				if (idsToRemove == null)
					idsToRemove = new ArrayList();
				idsToRemove.add(e.getKey());
			}
		} while (true);
		if (idsToRemove != null)
		{
			SnapshotInfo info;
			for (i$ = idsToRemove.iterator(); i$.hasNext(); segmentsFileToIDs.remove(info.segmentsFileName))
			{
				String id = (String)i$.next();
				info = (SnapshotInfo)idToSnapshot.remove(id);
			}

		}
	}

	public synchronized void release(String id)
		throws IOException
	{
		SnapshotInfo info = (SnapshotInfo)idToSnapshot.remove(id);
		if (info == null)
			throw new IllegalStateException((new StringBuilder()).append("Snapshot doesn't exist: ").append(id).toString());
		Set ids = (Set)segmentsFileToIDs.get(info.segmentsFileName);
		if (ids != null)
		{
			ids.remove(id);
			if (ids.size() == 0)
				segmentsFileToIDs.remove(info.segmentsFileName);
		}
	}

	public synchronized IndexCommit snapshot(String id)
		throws IOException
	{
		if (lastCommit == null)
		{
			throw new IllegalStateException("No index commit to snapshot");
		} else
		{
			checkSnapshotted(id);
			registerSnapshotInfo(id, lastCommit.getSegmentsFileName(), lastCommit);
			return lastCommit;
		}
	}

}
