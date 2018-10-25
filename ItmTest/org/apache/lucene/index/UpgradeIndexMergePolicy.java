// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UpgradeIndexMergePolicy.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.index:
//			MergePolicy, SegmentInfoPerCommit, IndexWriter, SegmentInfo, 
//			SegmentInfos

public class UpgradeIndexMergePolicy extends MergePolicy
{

	protected final MergePolicy base;

	public UpgradeIndexMergePolicy(MergePolicy base)
	{
		this.base = base;
	}

	protected boolean shouldUpgradeSegment(SegmentInfoPerCommit si)
	{
		return !Constants.LUCENE_MAIN_VERSION.equals(si.info.getVersion());
	}

	public void setIndexWriter(IndexWriter writer)
	{
		super.setIndexWriter(writer);
		base.setIndexWriter(writer);
	}

	public MergePolicy.MergeSpecification findMerges(SegmentInfos segmentInfos)
		throws IOException
	{
		return base.findMerges(segmentInfos);
	}

	public MergePolicy.MergeSpecification findForcedMerges(SegmentInfos segmentInfos, int maxSegmentCount, Map segmentsToMerge)
		throws IOException
	{
		Map oldSegments = new HashMap();
		Iterator i$ = segmentInfos.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			SegmentInfoPerCommit si = (SegmentInfoPerCommit)i$.next();
			Boolean v = (Boolean)segmentsToMerge.get(si);
			if (v != null && shouldUpgradeSegment(si))
				oldSegments.put(si, v);
		} while (true);
		if (verbose())
			message((new StringBuilder()).append("findForcedMerges: segmentsToUpgrade=").append(oldSegments).toString());
		if (oldSegments.isEmpty())
			return null;
		MergePolicy.MergeSpecification spec = base.findForcedMerges(segmentInfos, maxSegmentCount, oldSegments);
		if (spec != null)
		{
			MergePolicy.OneMerge om;
			for (Iterator i$ = spec.merges.iterator(); i$.hasNext(); oldSegments.keySet().removeAll(om.segments))
				om = (MergePolicy.OneMerge)i$.next();

		}
		if (!oldSegments.isEmpty())
		{
			if (verbose())
				message((new StringBuilder()).append("findForcedMerges: ").append(base.getClass().getSimpleName()).append(" does not want to merge all old segments, merge remaining ones into new segment: ").append(oldSegments).toString());
			List newInfos = new ArrayList();
			Iterator i$ = segmentInfos.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				SegmentInfoPerCommit si = (SegmentInfoPerCommit)i$.next();
				if (oldSegments.containsKey(si))
					newInfos.add(si);
			} while (true);
			if (spec == null)
				spec = new MergePolicy.MergeSpecification();
			spec.add(new MergePolicy.OneMerge(newInfos));
		}
		return spec;
	}

	public MergePolicy.MergeSpecification findForcedDeletesMerges(SegmentInfos segmentInfos)
		throws IOException
	{
		return base.findForcedDeletesMerges(segmentInfos);
	}

	public boolean useCompoundFile(SegmentInfos segments, SegmentInfoPerCommit newSegment)
		throws IOException
	{
		return base.useCompoundFile(segments, newSegment);
	}

	public void close()
	{
		base.close();
	}

	public String toString()
	{
		return (new StringBuilder()).append("[").append(getClass().getSimpleName()).append("->").append(base).append("]").toString();
	}

	private boolean verbose()
	{
		IndexWriter w = (IndexWriter)writer.get();
		return w != null && w.infoStream.isEnabled("UPGMP");
	}

	private void message(String message)
	{
		((IndexWriter)writer.get()).infoStream.message("UPGMP", message);
	}
}
