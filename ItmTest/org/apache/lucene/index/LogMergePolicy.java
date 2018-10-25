// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LogMergePolicy.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.util.InfoStream;
import org.apache.lucene.util.SetOnce;

// Referenced classes of package org.apache.lucene.index:
//			MergePolicy, IndexWriter, SegmentInfoPerCommit, SegmentInfos, 
//			SegmentInfo

public abstract class LogMergePolicy extends MergePolicy
{
	private static class SegmentInfoAndLevel
		implements Comparable
	{

		SegmentInfoPerCommit info;
		float level;
		int index;

		public int compareTo(SegmentInfoAndLevel other)
		{
			if (level < other.level)
				return 1;
			return level <= other.level ? 0 : -1;
		}

		public volatile int compareTo(Object x0)
		{
			return compareTo((SegmentInfoAndLevel)x0);
		}

		public SegmentInfoAndLevel(SegmentInfoPerCommit info, float level, int index)
		{
			this.info = info;
			this.level = level;
			this.index = index;
		}
	}


	public static final double LEVEL_LOG_SPAN = 0.75D;
	public static final int DEFAULT_MERGE_FACTOR = 10;
	public static final int DEFAULT_MAX_MERGE_DOCS = 0x7fffffff;
	public static final double DEFAULT_NO_CFS_RATIO = 0.10000000000000001D;
	public static final long DEFAULT_MAX_CFS_SEGMENT_SIZE = 0x7fffffffffffffffL;
	protected int mergeFactor;
	protected long minMergeSize;
	protected long maxMergeSize;
	protected long maxMergeSizeForForcedMerge;
	protected int maxMergeDocs;
	protected double noCFSRatio;
	protected long maxCFSSegmentSize;
	protected boolean calibrateSizeByDeletes;
	protected boolean useCompoundFile;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/LogMergePolicy.desiredAssertionStatus();

	public LogMergePolicy()
	{
		mergeFactor = 10;
		maxMergeSizeForForcedMerge = 0x7fffffffffffffffL;
		maxMergeDocs = 0x7fffffff;
		noCFSRatio = 0.10000000000000001D;
		maxCFSSegmentSize = 0x7fffffffffffffffL;
		calibrateSizeByDeletes = true;
		useCompoundFile = true;
	}

	protected boolean verbose()
	{
		IndexWriter w = (IndexWriter)writer.get();
		return w != null && w.infoStream.isEnabled("LMP");
	}

	public double getNoCFSRatio()
	{
		return noCFSRatio;
	}

	public void setNoCFSRatio(double noCFSRatio)
	{
		if (noCFSRatio < 0.0D || noCFSRatio > 1.0D)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("noCFSRatio must be 0.0 to 1.0 inclusive; got ").append(noCFSRatio).toString());
		} else
		{
			this.noCFSRatio = noCFSRatio;
			return;
		}
	}

	protected void message(String message)
	{
		if (verbose())
			((IndexWriter)writer.get()).infoStream.message("LMP", message);
	}

	public int getMergeFactor()
	{
		return mergeFactor;
	}

	public void setMergeFactor(int mergeFactor)
	{
		if (mergeFactor < 2)
		{
			throw new IllegalArgumentException("mergeFactor cannot be less than 2");
		} else
		{
			this.mergeFactor = mergeFactor;
			return;
		}
	}

	public boolean useCompoundFile(SegmentInfos infos, SegmentInfoPerCommit mergedInfo)
		throws IOException
	{
		if (!getUseCompoundFile())
			return false;
		long mergedInfoSize = size(mergedInfo);
		if (mergedInfoSize > maxCFSSegmentSize)
			return false;
		if (getNoCFSRatio() >= 1.0D)
			return true;
		long totalSize = 0L;
		for (Iterator i$ = infos.iterator(); i$.hasNext();)
		{
			SegmentInfoPerCommit info = (SegmentInfoPerCommit)i$.next();
			totalSize += size(info);
		}

		return (double)mergedInfoSize <= getNoCFSRatio() * (double)totalSize;
	}

	public void setUseCompoundFile(boolean useCompoundFile)
	{
		this.useCompoundFile = useCompoundFile;
	}

	public boolean getUseCompoundFile()
	{
		return useCompoundFile;
	}

	public void setCalibrateSizeByDeletes(boolean calibrateSizeByDeletes)
	{
		this.calibrateSizeByDeletes = calibrateSizeByDeletes;
	}

	public boolean getCalibrateSizeByDeletes()
	{
		return calibrateSizeByDeletes;
	}

	public void close()
	{
	}

	protected abstract long size(SegmentInfoPerCommit segmentinfopercommit)
		throws IOException;

	protected long sizeDocs(SegmentInfoPerCommit info)
		throws IOException
	{
		if (calibrateSizeByDeletes)
		{
			int delCount = ((IndexWriter)writer.get()).numDeletedDocs(info);
			if (!$assertionsDisabled && delCount > info.info.getDocCount())
				throw new AssertionError();
			else
				return (long)info.info.getDocCount() - (long)delCount;
		} else
		{
			return (long)info.info.getDocCount();
		}
	}

	protected long sizeBytes(SegmentInfoPerCommit info)
		throws IOException
	{
		long byteSize = info.sizeInBytes();
		if (calibrateSizeByDeletes)
		{
			int delCount = ((IndexWriter)writer.get()).numDeletedDocs(info);
			double delRatio = info.info.getDocCount() > 0 ? (float)delCount / (float)info.info.getDocCount() : 0.0F;
			if (!$assertionsDisabled && delRatio > 1.0D)
				throw new AssertionError();
			else
				return info.info.getDocCount() > 0 ? (long)((double)byteSize * (1.0D - delRatio)) : byteSize;
		} else
		{
			return byteSize;
		}
	}

	protected boolean isMerged(SegmentInfos infos, int maxNumSegments, Map segmentsToMerge)
		throws IOException
	{
		int numSegments = infos.size();
		int numToMerge = 0;
		SegmentInfoPerCommit mergeInfo = null;
		boolean segmentIsOriginal = false;
		for (int i = 0; i < numSegments && numToMerge <= maxNumSegments; i++)
		{
			SegmentInfoPerCommit info = infos.info(i);
			Boolean isOriginal = (Boolean)segmentsToMerge.get(info);
			if (isOriginal != null)
			{
				segmentIsOriginal = isOriginal.booleanValue();
				numToMerge++;
				mergeInfo = info;
			}
		}

		return numToMerge <= maxNumSegments && (numToMerge != 1 || !segmentIsOriginal || isMerged(mergeInfo));
	}

	protected boolean isMerged(SegmentInfoPerCommit info)
		throws IOException
	{
		IndexWriter w = (IndexWriter)writer.get();
		if (!$assertionsDisabled && w == null)
		{
			throw new AssertionError();
		} else
		{
			boolean hasDeletions = w.numDeletedDocs(info) > 0;
			return !hasDeletions && !info.info.hasSeparateNorms() && info.info.dir == w.getDirectory() && (info.info.getUseCompoundFile() == useCompoundFile || noCFSRatio < 1.0D);
		}
	}

	private MergePolicy.MergeSpecification findForcedMergesSizeLimit(SegmentInfos infos, int maxNumSegments, int last)
		throws IOException
	{
		MergePolicy.MergeSpecification spec = new MergePolicy.MergeSpecification();
		List segments = infos.asList();
		int start;
		for (start = last - 1; start >= 0; start--)
		{
			SegmentInfoPerCommit info = infos.info(start);
			if (size(info) > maxMergeSizeForForcedMerge || sizeDocs(info) > (long)maxMergeDocs)
			{
				if (verbose())
					message((new StringBuilder()).append("findForcedMergesSizeLimit: skip segment=").append(info).append(": size is > maxMergeSize (").append(maxMergeSizeForForcedMerge).append(") or sizeDocs is > maxMergeDocs (").append(maxMergeDocs).append(")").toString());
				if (last - start - 1 > 1 || start != last - 1 && !isMerged(infos.info(start + 1)))
					spec.add(new MergePolicy.OneMerge(segments.subList(start + 1, last)));
				last = start;
				continue;
			}
			if (last - start == mergeFactor)
			{
				spec.add(new MergePolicy.OneMerge(segments.subList(start, last)));
				last = start;
			}
		}

		if (last > 0 && (++start + 1 < last || !isMerged(infos.info(start))))
			spec.add(new MergePolicy.OneMerge(segments.subList(start, last)));
		return spec.merges.size() != 0 ? spec : null;
	}

	private MergePolicy.MergeSpecification findForcedMergesMaxNumSegments(SegmentInfos infos, int maxNumSegments, int last)
		throws IOException
	{
		MergePolicy.MergeSpecification spec = new MergePolicy.MergeSpecification();
		List segments = infos.asList();
		for (; (last - maxNumSegments) + 1 >= mergeFactor; last -= mergeFactor)
			spec.add(new MergePolicy.OneMerge(segments.subList(last - mergeFactor, last)));

		if (0 == spec.merges.size())
			if (maxNumSegments == 1)
			{
				if (last > 1 || !isMerged(infos.info(0)))
					spec.add(new MergePolicy.OneMerge(segments.subList(0, last)));
			} else
			if (last > maxNumSegments)
			{
				int finalMergeSize = (last - maxNumSegments) + 1;
				long bestSize = 0L;
				int bestStart = 0;
				for (int i = 0; i < (last - finalMergeSize) + 1; i++)
				{
					long sumSize = 0L;
					for (int j = 0; j < finalMergeSize; j++)
						sumSize += size(infos.info(j + i));

					if (i == 0 || sumSize < 2L * size(infos.info(i - 1)) && sumSize < bestSize)
					{
						bestStart = i;
						bestSize = sumSize;
					}
				}

				spec.add(new MergePolicy.OneMerge(segments.subList(bestStart, bestStart + finalMergeSize)));
			}
		return spec.merges.size() != 0 ? spec : null;
	}

	public MergePolicy.MergeSpecification findForcedMerges(SegmentInfos infos, int maxNumSegments, Map segmentsToMerge)
		throws IOException
	{
		if (!$assertionsDisabled && maxNumSegments <= 0)
			throw new AssertionError();
		if (verbose())
			message((new StringBuilder()).append("findForcedMerges: maxNumSegs=").append(maxNumSegments).append(" segsToMerge=").append(segmentsToMerge).toString());
		if (isMerged(infos, maxNumSegments, segmentsToMerge))
		{
			if (verbose())
				message("already merged; skip");
			return null;
		}
		int last = infos.size();
		do
		{
			if (last <= 0)
				break;
			SegmentInfoPerCommit info = infos.info(--last);
			if (segmentsToMerge.get(info) == null)
				continue;
			last++;
			break;
		} while (true);
		if (last == 0)
		{
			if (verbose())
				message("last == 0; skip");
			return null;
		}
		if (maxNumSegments == 1 && last == 1 && isMerged(infos.info(0)))
		{
			if (verbose())
				message("already 1 seg; skip");
			return null;
		}
		boolean anyTooLarge = false;
		int i = 0;
		do
		{
			if (i >= last)
				break;
			SegmentInfoPerCommit info = infos.info(i);
			if (size(info) > maxMergeSizeForForcedMerge || sizeDocs(info) > (long)maxMergeDocs)
			{
				anyTooLarge = true;
				break;
			}
			i++;
		} while (true);
		if (anyTooLarge)
			return findForcedMergesSizeLimit(infos, maxNumSegments, last);
		else
			return findForcedMergesMaxNumSegments(infos, maxNumSegments, last);
	}

	public MergePolicy.MergeSpecification findForcedDeletesMerges(SegmentInfos segmentInfos)
		throws IOException
	{
		List segments = segmentInfos.asList();
		int numSegments = segments.size();
		if (verbose())
			message((new StringBuilder()).append("findForcedDeleteMerges: ").append(numSegments).append(" segments").toString());
		MergePolicy.MergeSpecification spec = new MergePolicy.MergeSpecification();
		int firstSegmentWithDeletions = -1;
		IndexWriter w = (IndexWriter)writer.get();
		if (!$assertionsDisabled && w == null)
			throw new AssertionError();
		for (int i = 0; i < numSegments; i++)
		{
			SegmentInfoPerCommit info = segmentInfos.info(i);
			int delCount = w.numDeletedDocs(info);
			if (delCount > 0)
			{
				if (verbose())
					message((new StringBuilder()).append("  segment ").append(info.info.name).append(" has deletions").toString());
				if (firstSegmentWithDeletions == -1)
				{
					firstSegmentWithDeletions = i;
					continue;
				}
				if (i - firstSegmentWithDeletions != mergeFactor)
					continue;
				if (verbose())
					message((new StringBuilder()).append("  add merge ").append(firstSegmentWithDeletions).append(" to ").append(i - 1).append(" inclusive").toString());
				spec.add(new MergePolicy.OneMerge(segments.subList(firstSegmentWithDeletions, i)));
				firstSegmentWithDeletions = i;
				continue;
			}
			if (firstSegmentWithDeletions == -1)
				continue;
			if (verbose())
				message((new StringBuilder()).append("  add merge ").append(firstSegmentWithDeletions).append(" to ").append(i - 1).append(" inclusive").toString());
			spec.add(new MergePolicy.OneMerge(segments.subList(firstSegmentWithDeletions, i)));
			firstSegmentWithDeletions = -1;
		}

		if (firstSegmentWithDeletions != -1)
		{
			if (verbose())
				message((new StringBuilder()).append("  add merge ").append(firstSegmentWithDeletions).append(" to ").append(numSegments - 1).append(" inclusive").toString());
			spec.add(new MergePolicy.OneMerge(segments.subList(firstSegmentWithDeletions, numSegments)));
		}
		return spec;
	}

	public MergePolicy.MergeSpecification findMerges(SegmentInfos infos)
		throws IOException
	{
		int numSegments = infos.size();
		if (verbose())
			message((new StringBuilder()).append("findMerges: ").append(numSegments).append(" segments").toString());
		List levels = new ArrayList();
		float norm = (float)Math.log(mergeFactor);
		Collection mergingSegments = ((IndexWriter)writer.get()).getMergingSegments();
		for (int i = 0; i < numSegments; i++)
		{
			SegmentInfoPerCommit info = infos.info(i);
			long size = size(info);
			if (size < 1L)
				size = 1L;
			SegmentInfoAndLevel infoLevel = new SegmentInfoAndLevel(info, (float)Math.log(size) / norm, i);
			levels.add(infoLevel);
			if (!verbose())
				continue;
			long segBytes = sizeBytes(info);
			String extra = mergingSegments.contains(info) ? " [merging]" : "";
			if (size >= maxMergeSize)
				extra = (new StringBuilder()).append(extra).append(" [skip: too large]").toString();
			message((new StringBuilder()).append("seg=").append(((IndexWriter)writer.get()).segString(info)).append(" level=").append(infoLevel.level).append(" size=").append(String.format(Locale.ROOT, "%.3f MB", new Object[] {
				Double.valueOf((double)(segBytes / 1024L) / 1024D)
			})).append(extra).toString());
		}

		float levelFloor;
		if (minMergeSize <= 0L)
			levelFloor = 0.0F;
		else
			levelFloor = (float)(Math.log(minMergeSize) / (double)norm);
		MergePolicy.MergeSpecification spec = null;
		int numMergeableSegments = levels.size();
		int upto;
		for (int start = 0; start < numMergeableSegments; start = 1 + upto)
		{
			float maxLevel = ((SegmentInfoAndLevel)levels.get(start)).level;
			for (int i = 1 + start; i < numMergeableSegments; i++)
			{
				float level = ((SegmentInfoAndLevel)levels.get(i)).level;
				if (level > maxLevel)
					maxLevel = level;
			}

			float levelBottom;
			if (maxLevel <= levelFloor)
			{
				levelBottom = -1F;
			} else
			{
				levelBottom = (float)((double)maxLevel - 0.75D);
				if (levelBottom < levelFloor && maxLevel >= levelFloor)
					levelBottom = levelFloor;
			}
			for (upto = numMergeableSegments - 1; upto >= start && ((SegmentInfoAndLevel)levels.get(upto)).level < levelBottom; upto--);
			if (verbose())
				message((new StringBuilder()).append("  level ").append(levelBottom).append(" to ").append(maxLevel).append(": ").append((1 + upto) - start).append(" segments").toString());
			for (int end = start + mergeFactor; end <= 1 + upto; end = start + mergeFactor)
			{
				boolean anyTooLarge = false;
				boolean anyMerging = false;
				int i = start;
				do
				{
					if (i >= end)
						break;
					SegmentInfoPerCommit info = ((SegmentInfoAndLevel)levels.get(i)).info;
					anyTooLarge |= size(info) >= maxMergeSize || sizeDocs(info) >= (long)maxMergeDocs;
					if (mergingSegments.contains(info))
					{
						anyMerging = true;
						break;
					}
					i++;
				} while (true);
				if (!anyMerging)
					if (!anyTooLarge)
					{
						if (spec == null)
							spec = new MergePolicy.MergeSpecification();
						List mergeInfos = new ArrayList();
						for (int i = start; i < end; i++)
						{
							mergeInfos.add(((SegmentInfoAndLevel)levels.get(i)).info);
							if (!$assertionsDisabled && !infos.contains(((SegmentInfoAndLevel)levels.get(i)).info))
								throw new AssertionError();
						}

						if (verbose())
							message((new StringBuilder()).append("  add merge=").append(((IndexWriter)writer.get()).segString(mergeInfos)).append(" start=").append(start).append(" end=").append(end).toString());
						spec.add(new MergePolicy.OneMerge(mergeInfos));
					} else
					if (verbose())
						message((new StringBuilder()).append("    ").append(start).append(" to ").append(end).append(": contains segment over maxMergeSize or maxMergeDocs; skipping").toString());
				start = end;
			}

		}

		return spec;
	}

	public void setMaxMergeDocs(int maxMergeDocs)
	{
		this.maxMergeDocs = maxMergeDocs;
	}

	public int getMaxMergeDocs()
	{
		return maxMergeDocs;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder((new StringBuilder()).append("[").append(getClass().getSimpleName()).append(": ").toString());
		sb.append("minMergeSize=").append(minMergeSize).append(", ");
		sb.append("mergeFactor=").append(mergeFactor).append(", ");
		sb.append("maxMergeSize=").append(maxMergeSize).append(", ");
		sb.append("maxMergeSizeForForcedMerge=").append(maxMergeSizeForForcedMerge).append(", ");
		sb.append("calibrateSizeByDeletes=").append(calibrateSizeByDeletes).append(", ");
		sb.append("maxMergeDocs=").append(maxMergeDocs).append(", ");
		sb.append("useCompoundFile=").append(useCompoundFile).append(", ");
		sb.append("maxCFSSegmentSizeMB=").append(getMaxCFSSegmentSizeMB()).append(", ");
		sb.append("noCFSRatio=").append(noCFSRatio);
		sb.append("]");
		return sb.toString();
	}

	public final double getMaxCFSSegmentSizeMB()
	{
		return (double)(maxCFSSegmentSize / 1024L) / 1024D;
	}

	public final void setMaxCFSSegmentSizeMB(double v)
	{
		if (v < 0.0D)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("maxCFSSegmentSizeMB must be >=0 (got ").append(v).append(")").toString());
		} else
		{
			v *= 1048576D;
			maxCFSSegmentSize = v <= 9.2233720368547758E+018D ? (long)v : 0x7fffffffffffffffL;
			return;
		}
	}

}
