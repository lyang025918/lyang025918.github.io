// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PersistentSnapshotDeletionPolicy.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.document.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.index:
//			SnapshotDeletionPolicy, IndexableField, IndexWriter, IndexWriterConfig, 
//			IndexReader, IndexDeletionPolicy, DirectoryReader, IndexCommit

public class PersistentSnapshotDeletionPolicy extends SnapshotDeletionPolicy
{

	private static final String SNAPSHOTS_ID = "$SNAPSHOTS_DOC$";
	private final IndexWriter writer;

	public static Map readSnapshotsInfo(Directory dir)
		throws IOException
	{
		IndexReader r;
		Map snapshots;
		r = DirectoryReader.open(dir);
		snapshots = new HashMap();
		int numDocs = r.numDocs();
		if (numDocs == 1)
		{
			Document doc = r.document(r.maxDoc() - 1);
			if (doc.getField("$SNAPSHOTS_DOC$") == null)
				throw new IllegalStateException("directory is not a valid snapshots store!");
			doc.removeField("$SNAPSHOTS_DOC$");
			IndexableField f;
			for (Iterator i$ = doc.iterator(); i$.hasNext(); snapshots.put(f.name(), f.stringValue()))
				f = (IndexableField)i$.next();

		} else
		if (numDocs != 0)
			throw new IllegalStateException((new StringBuilder()).append("should be at most 1 document in the snapshots directory: ").append(numDocs).toString());
		r.close();
		break MISSING_BLOCK_LABEL_165;
		Exception exception;
		exception;
		r.close();
		throw exception;
		return snapshots;
	}

	public PersistentSnapshotDeletionPolicy(IndexDeletionPolicy primary, Directory dir, IndexWriterConfig.OpenMode mode, Version matchVersion)
		throws IOException
	{
		super(primary, null);
		writer = new IndexWriter(dir, (new IndexWriterConfig(matchVersion, null)).setOpenMode(mode));
		if (mode != IndexWriterConfig.OpenMode.APPEND)
			writer.commit();
		try
		{
			java.util.Map.Entry e;
			for (Iterator i$ = readSnapshotsInfo(dir).entrySet().iterator(); i$.hasNext(); registerSnapshotInfo((String)e.getKey(), (String)e.getValue(), null))
				e = (java.util.Map.Entry)i$.next();

		}
		catch (RuntimeException e)
		{
			writer.close();
			throw e;
		}
		catch (IOException e)
		{
			writer.close();
			throw e;
		}
	}

	public synchronized void onInit(List commits)
		throws IOException
	{
		super.onInit(commits);
		persistSnapshotInfos(null, null);
	}

	public synchronized IndexCommit snapshot(String id)
		throws IOException
	{
		checkSnapshotted(id);
		if ("$SNAPSHOTS_DOC$".equals(id))
		{
			throw new IllegalArgumentException((new StringBuilder()).append(id).append(" is reserved and cannot be used as a snapshot id").toString());
		} else
		{
			persistSnapshotInfos(id, lastCommit.getSegmentsFileName());
			return super.snapshot(id);
		}
	}

	public synchronized void release(String id)
		throws IOException
	{
		super.release(id);
		persistSnapshotInfos(null, null);
	}

	public void close()
		throws IOException
	{
		writer.close();
	}

	private void persistSnapshotInfos(String id, String segment)
		throws IOException
	{
		writer.deleteAll();
		Document d = new Document();
		FieldType ft = new FieldType();
		ft.setStored(true);
		d.add(new Field("$SNAPSHOTS_DOC$", "", ft));
		java.util.Map.Entry e;
		for (Iterator i$ = super.getSnapshots().entrySet().iterator(); i$.hasNext(); d.add(new Field((String)e.getKey(), (String)e.getValue(), ft)))
			e = (java.util.Map.Entry)i$.next();

		if (id != null)
			d.add(new Field(id, segment, ft));
		writer.addDocument(d);
		writer.commit();
	}
}
