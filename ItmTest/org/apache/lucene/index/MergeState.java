// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MergeState.java

package org.apache.lucene.index;

import java.util.List;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.InfoStream;
import org.apache.lucene.util.packed.PackedInts;

// Referenced classes of package org.apache.lucene.index:
//			SegmentInfo, FieldInfos, FieldInfo, SegmentReader, 
//			MergePolicy, AtomicReader

public class MergeState
{
	public static class CheckAbort
	{

		private double workCount;
		private final MergePolicy.OneMerge merge;
		private final Directory dir;
		static final CheckAbort NONE = new CheckAbort(null, null) {

			public void work(double d)
			{
			}

		};

		public void work(double units)
			throws MergePolicy.MergeAbortedException
		{
			workCount += units;
			if (workCount >= 10000D)
			{
				merge.checkAborted(dir);
				workCount = 0.0D;
			}
		}


		public CheckAbort(MergePolicy.OneMerge merge, Directory dir)
		{
			this.merge = merge;
			this.dir = dir;
		}
	}

	private static class DelCountDocMap extends DocMap
	{

		private final org.apache.lucene.util.packed.PackedInts.Mutable numDeletesSoFar;

		public int remap(int docId)
		{
			return docId - (int)numDeletesSoFar.get(docId);
		}

		public int maxDoc()
		{
			return numDeletesSoFar.size();
		}

		public int numDeletedDocs()
		{
			int maxDoc = maxDoc();
			return (int)numDeletesSoFar.get(maxDoc - 1);
		}

		private DelCountDocMap(Bits liveDocs, org.apache.lucene.util.packed.PackedInts.Mutable numDeletesSoFar)
		{
			super(liveDocs);
			this.numDeletesSoFar = numDeletesSoFar;
		}

	}

	private static class DirectDocMap extends DocMap
	{

		private final org.apache.lucene.util.packed.PackedInts.Mutable docIds;
		private final int numDeletedDocs;

		public int remap(int docId)
		{
			return (int)docIds.get(docId);
		}

		public int maxDoc()
		{
			return docIds.size();
		}

		public int numDeletedDocs()
		{
			return numDeletedDocs;
		}

		private DirectDocMap(Bits liveDocs, org.apache.lucene.util.packed.PackedInts.Mutable docIds, int numDeletedDocs)
		{
			super(liveDocs);
			this.docIds = docIds;
			this.numDeletedDocs = numDeletedDocs;
		}

	}

	private static class NoDelDocMap extends DocMap
	{

		private final int maxDoc;

		public int remap(int docId)
		{
			return docId;
		}

		public int maxDoc()
		{
			return maxDoc;
		}

		public int numDeletedDocs()
		{
			return 0;
		}

		private NoDelDocMap(int maxDoc)
		{
			super(null);
			this.maxDoc = maxDoc;
		}

	}

	public static abstract class DocMap
	{

		private final Bits liveDocs;
		static final boolean $assertionsDisabled = !org/apache/lucene/index/MergeState.desiredAssertionStatus();

		public static DocMap build(AtomicReader reader)
		{
			int maxDoc = reader.maxDoc();
			int numDeletes = reader.numDeletedDocs();
			int numDocs = maxDoc - numDeletes;
			if (!$assertionsDisabled && reader.getLiveDocs() == null && numDeletes != 0)
				throw new AssertionError();
			if (numDeletes == 0)
				return new NoDelDocMap(maxDoc);
			if (numDeletes < numDocs)
				return buildDelCountDocmap(maxDoc, numDeletes, reader.getLiveDocs(), 0.0F);
			else
				return buildDirectDocMap(maxDoc, numDocs, reader.getLiveDocs(), 0.0F);
		}

		static DocMap buildDelCountDocmap(int maxDoc, int numDeletes, Bits liveDocs, float acceptableOverheadRatio)
		{
			org.apache.lucene.util.packed.PackedInts.Mutable numDeletesSoFar = PackedInts.getMutable(maxDoc, PackedInts.bitsRequired(numDeletes), acceptableOverheadRatio);
			int del = 0;
			for (int i = 0; i < maxDoc; i++)
			{
				if (!liveDocs.get(i))
					del++;
				numDeletesSoFar.set(i, del);
			}

			if (!$assertionsDisabled && del != numDeletes)
				throw new AssertionError((new StringBuilder()).append("del=").append(del).append(", numdeletes=").append(numDeletes).toString());
			else
				return new DelCountDocMap(liveDocs, numDeletesSoFar);
		}

		static DocMap buildDirectDocMap(int maxDoc, int numDocs, Bits liveDocs, float acceptableOverheadRatio)
		{
			org.apache.lucene.util.packed.PackedInts.Mutable docIds = PackedInts.getMutable(maxDoc, PackedInts.bitsRequired(Math.max(0, numDocs - 1)), acceptableOverheadRatio);
			int del = 0;
			for (int i = 0; i < maxDoc; i++)
				if (liveDocs.get(i))
					docIds.set(i, i - del);
				else
					del++;

			if (!$assertionsDisabled && numDocs + del != maxDoc)
				throw new AssertionError((new StringBuilder()).append("maxDoc=").append(maxDoc).append(", del=").append(del).append(", numDocs=").append(numDocs).toString());
			else
				return new DirectDocMap(liveDocs, docIds, del);
		}

		public int get(int docId)
		{
			if (liveDocs == null || liveDocs.get(docId))
				return remap(docId);
			else
				return -1;
		}

		public abstract int remap(int i);

		public abstract int maxDoc();

		public final int numDocs()
		{
			return maxDoc() - numDeletedDocs();
		}

		public abstract int numDeletedDocs();

		public boolean hasDeletions()
		{
			return numDeletedDocs() > 0;
		}


		protected DocMap(Bits liveDocs)
		{
			this.liveDocs = liveDocs;
		}
	}


	public SegmentInfo segmentInfo;
	public FieldInfos fieldInfos;
	public List readers;
	public DocMap docMaps[];
	public int docBase[];
	public CheckAbort checkAbort;
	public InfoStream infoStream;
	public FieldInfo fieldInfo;
	public SegmentReader matchingSegmentReaders[];
	public int matchedCount;

	MergeState()
	{
	}
}
