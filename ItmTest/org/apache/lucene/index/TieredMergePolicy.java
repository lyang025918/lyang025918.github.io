// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TieredMergePolicy.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.util.InfoStream;
import org.apache.lucene.util.SetOnce;

// Referenced classes of package org.apache.lucene.index:
//			MergePolicy, IndexWriter, SegmentInfoPerCommit, SegmentInfos, 
//			SegmentInfo

public class TieredMergePolicy extends MergePolicy
{
	protected static abstract class MergeScore
	{

		abstract double getScore();

		abstract String getExplanation();

		protected MergeScore()
		{
		}
	}

	private class SegmentByteSizeDescending
		implements Comparator
	{

		final TieredMergePolicy this$0;

		public int compare(SegmentInfoPerCommit o1, SegmentInfoPerCommit o2)
		{
			long sz1;
			long sz2;
			sz1 = size(o1);
			sz2 = size(o2);
			if (sz1 > sz2)
				return -1;
			if (sz2 > sz1)
				return 1;
			return o1.info.name.compareTo(o2.info.name);
			IOException ioe;
			ioe;
			throw new RuntimeException(ioe);
		}

		public volatile int compare(Object x0, Object x1)
		{
			return compare((SegmentInfoPerCommit)x0, (SegmentInfoPerCommit)x1);
		}

		private SegmentByteSizeDescending()
		{
			this$0 = TieredMergePolicy.this;
			super();
		}

	}


	private int maxMergeAtOnce;
	private long maxMergedSegmentBytes;
	private int maxMergeAtOnceExplicit;
	private long floorSegmentBytes;
	private double segsPerTier;
	private double forceMergeDeletesPctAllowed;
	private boolean useCompoundFile;
	private double noCFSRatio;
	private long maxCFSSegmentSize;
	private double reclaimDeletesWeight;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/TieredMergePolicy.desiredAssertionStatus();

	public TieredMergePolicy()
	{
		maxMergeAtOnce = 10;
		maxMergedSegmentBytes = 0x140000000L;
		maxMergeAtOnceExplicit = 30;
		floorSegmentBytes = 0x200000L;
		segsPerTier = 10D;
		forceMergeDeletesPctAllowed = 10D;
		useCompoundFile = true;
		noCFSRatio = 0.10000000000000001D;
		maxCFSSegmentSize = 0x7fffffffffffffffL;
		reclaimDeletesWeight = 2D;
	}

	public TieredMergePolicy setMaxMergeAtOnce(int v)
	{
		if (v < 2)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("maxMergeAtOnce must be > 1 (got ").append(v).append(")").toString());
		} else
		{
			maxMergeAtOnce = v;
			return this;
		}
	}

	public int getMaxMergeAtOnce()
	{
		return maxMergeAtOnce;
	}

	public TieredMergePolicy setMaxMergeAtOnceExplicit(int v)
	{
		if (v < 2)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("maxMergeAtOnceExplicit must be > 1 (got ").append(v).append(")").toString());
		} else
		{
			maxMergeAtOnceExplicit = v;
			return this;
		}
	}

	public int getMaxMergeAtOnceExplicit()
	{
		return maxMergeAtOnceExplicit;
	}

	public TieredMergePolicy setMaxMergedSegmentMB(double v)
	{
		if (v < 0.0D)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("maxMergedSegmentMB must be >=0 (got ").append(v).append(")").toString());
		} else
		{
			v *= 1048576D;
			maxMergedSegmentBytes = v <= 9.2233720368547758E+018D ? (long)v : 0x7fffffffffffffffL;
			return this;
		}
	}

	public double getMaxMergedSegmentMB()
	{
		return (double)(maxMergedSegmentBytes / 1024L) / 1024D;
	}

	public TieredMergePolicy setReclaimDeletesWeight(double v)
	{
		if (v < 0.0D)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("reclaimDeletesWeight must be >= 0.0 (got ").append(v).append(")").toString());
		} else
		{
			reclaimDeletesWeight = v;
			return this;
		}
	}

	public double getReclaimDeletesWeight()
	{
		return reclaimDeletesWeight;
	}

	public TieredMergePolicy setFloorSegmentMB(double v)
	{
		if (v <= 0.0D)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("floorSegmentMB must be >= 0.0 (got ").append(v).append(")").toString());
		} else
		{
			v *= 1048576D;
			floorSegmentBytes = v <= 9.2233720368547758E+018D ? (long)v : 0x7fffffffffffffffL;
			return this;
		}
	}

	public double getFloorSegmentMB()
	{
		return (double)floorSegmentBytes / 1048576D;
	}

	public TieredMergePolicy setForceMergeDeletesPctAllowed(double v)
	{
		if (v < 0.0D || v > 100D)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("forceMergeDeletesPctAllowed must be between 0.0 and 100.0 inclusive (got ").append(v).append(")").toString());
		} else
		{
			forceMergeDeletesPctAllowed = v;
			return this;
		}
	}

	public double getForceMergeDeletesPctAllowed()
	{
		return forceMergeDeletesPctAllowed;
	}

	public TieredMergePolicy setSegmentsPerTier(double v)
	{
		if (v < 2D)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("segmentsPerTier must be >= 2.0 (got ").append(v).append(")").toString());
		} else
		{
			segsPerTier = v;
			return this;
		}
	}

	public double getSegmentsPerTier()
	{
		return segsPerTier;
	}

	public TieredMergePolicy setUseCompoundFile(boolean useCompoundFile)
	{
		this.useCompoundFile = useCompoundFile;
		return this;
	}

	public boolean getUseCompoundFile()
	{
		return useCompoundFile;
	}

	public TieredMergePolicy setNoCFSRatio(double noCFSRatio)
	{
		if (noCFSRatio < 0.0D || noCFSRatio > 1.0D)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("noCFSRatio must be 0.0 to 1.0 inclusive; got ").append(noCFSRatio).toString());
		} else
		{
			this.noCFSRatio = noCFSRatio;
			return this;
		}
	}

	public double getNoCFSRatio()
	{
		return noCFSRatio;
	}

	public MergePolicy.MergeSpecification findMerges(SegmentInfos infos)
		throws IOException
	{
		if (verbose())
			message((new StringBuilder()).append("findMerges: ").append(infos.size()).append(" segments").toString());
		if (infos.size() == 0)
			return null;
		Collection merging = ((IndexWriter)writer.get()).getMergingSegments();
		Collection toBeMerged = new HashSet();
		List infosSorted = new ArrayList(infos.asList());
		Collections.sort(infosSorted, new SegmentByteSizeDescending());
		long totIndexBytes = 0L;
		long minSegmentBytes = 0x7fffffffffffffffL;
		for (Iterator i$ = infosSorted.iterator(); i$.hasNext();)
		{
			SegmentInfoPerCommit info = (SegmentInfoPerCommit)i$.next();
			long segBytes = size(info);
			if (verbose())
			{
				String extra = merging.contains(info) ? " [merging]" : "";
				if ((double)segBytes >= (double)maxMergedSegmentBytes / 2D)
					extra = (new StringBuilder()).append(extra).append(" [skip: too large]").toString();
				else
				if (segBytes < floorSegmentBytes)
					extra = (new StringBuilder()).append(extra).append(" [floored]").toString();
				message((new StringBuilder()).append("  seg=").append(((IndexWriter)writer.get()).segString(info)).append(" size=").append(String.format(Locale.ROOT, "%.3f", new Object[] {
					Double.valueOf((double)(segBytes / 1024L) / 1024D)
				})).append(" MB").append(extra).toString());
			}
			minSegmentBytes = Math.min(segBytes, minSegmentBytes);
			totIndexBytes += segBytes;
		}

		int tooBigCount;
		for (tooBigCount = 0; tooBigCount < infosSorted.size() && (double)size((SegmentInfoPerCommit)infosSorted.get(tooBigCount)) >= (double)maxMergedSegmentBytes / 2D; tooBigCount++)
			totIndexBytes -= size((SegmentInfoPerCommit)infosSorted.get(tooBigCount));

		minSegmentBytes = floorSize(minSegmentBytes);
		long levelSize = minSegmentBytes;
		long bytesLeft = totIndexBytes;
		double allowedSegCount = 0.0D;
		do
		{
			double segCountLevel = (double)bytesLeft / (double)levelSize;
			if (segCountLevel < segsPerTier)
			{
				allowedSegCount += Math.ceil(segCountLevel);
				break;
			}
			allowedSegCount += segsPerTier;
			bytesLeft = (long)((double)bytesLeft - segsPerTier * (double)levelSize);
			levelSize *= maxMergeAtOnce;
		} while (true);
		int allowedSegCountInt = (int)allowedSegCount;
		MergePolicy.MergeSpecification spec = null;
		do
		{
			long mergingBytes = 0L;
			List eligible = new ArrayList();
			for (int idx = tooBigCount; idx < infosSorted.size(); idx++)
			{
				SegmentInfoPerCommit info = (SegmentInfoPerCommit)infosSorted.get(idx);
				if (merging.contains(info))
				{
					mergingBytes += info.info.sizeInBytes();
					continue;
				}
				if (!toBeMerged.contains(info))
					eligible.add(info);
			}

			boolean maxMergeIsRunning = mergingBytes >= maxMergedSegmentBytes;
			if (verbose())
				message((new StringBuilder()).append("  allowedSegmentCount=").append(allowedSegCountInt).append(" vs count=").append(infosSorted.size()).append(" (eligible count=").append(eligible.size()).append(") tooBigCount=").append(tooBigCount).toString());
			if (eligible.size() == 0)
				return spec;
			if (eligible.size() >= allowedSegCountInt)
			{
				MergeScore bestScore = null;
				List best = null;
				boolean bestTooLarge = false;
				long bestMergeBytes = 0L;
				for (int startIdx = 0; startIdx <= eligible.size() - maxMergeAtOnce; startIdx++)
				{
					long totAfterMergeBytes = 0L;
					List candidate = new ArrayList();
					boolean hitTooLarge = false;
					for (int idx = startIdx; idx < eligible.size() && candidate.size() < maxMergeAtOnce; idx++)
					{
						SegmentInfoPerCommit info = (SegmentInfoPerCommit)eligible.get(idx);
						long segBytes = size(info);
						if (totAfterMergeBytes + segBytes > maxMergedSegmentBytes)
						{
							hitTooLarge = true;
						} else
						{
							candidate.add(info);
							totAfterMergeBytes += segBytes;
						}
					}

					MergeScore score = score(candidate, hitTooLarge, mergingBytes);
					if (verbose())
						message((new StringBuilder()).append("  maybe=").append(((IndexWriter)writer.get()).segString(candidate)).append(" score=").append(score.getScore()).append(" ").append(score.getExplanation()).append(" tooLarge=").append(hitTooLarge).append(" size=").append(String.format(Locale.ROOT, "%.3f MB", new Object[] {
							Double.valueOf((double)totAfterMergeBytes / 1024D / 1024D)
						})).toString());
					if ((bestScore == null || score.getScore() < bestScore.getScore()) && (!hitTooLarge || !maxMergeIsRunning))
					{
						best = candidate;
						bestScore = score;
						bestTooLarge = hitTooLarge;
						bestMergeBytes = totAfterMergeBytes;
					}
				}

				if (best != null)
				{
					if (spec == null)
						spec = new MergePolicy.MergeSpecification();
					MergePolicy.OneMerge merge = new MergePolicy.OneMerge(best);
					spec.add(merge);
					SegmentInfoPerCommit info;
					for (Iterator i$ = merge.segments.iterator(); i$.hasNext(); toBeMerged.add(info))
						info = (SegmentInfoPerCommit)i$.next();

					if (verbose())
						message((new StringBuilder()).append("  add merge=").append(((IndexWriter)writer.get()).segString(merge.segments)).append(" size=").append(String.format(Locale.ROOT, "%.3f MB", new Object[] {
							Double.valueOf((double)bestMergeBytes / 1024D / 1024D)
						})).append(" score=").append(String.format(Locale.ROOT, "%.3f", new Object[] {
							Double.valueOf(bestScore.getScore())
						})).append(" ").append(bestScore.getExplanation()).append(bestTooLarge ? " [max merge]" : "").toString());
				} else
				{
					return spec;
				}
			} else
			{
				return spec;
			}
		} while (true);
	}

	protected MergeScore score(List candidate, boolean hitTooLarge, long mergingBytes)
		throws IOException
	{
		long totBeforeMergeBytes = 0L;
		long totAfterMergeBytes = 0L;
		long totAfterMergeBytesFloored = 0L;
		for (Iterator i$ = candidate.iterator(); i$.hasNext();)
		{
			SegmentInfoPerCommit info = (SegmentInfoPerCommit)i$.next();
			long segBytes = size(info);
			totAfterMergeBytes += segBytes;
			totAfterMergeBytesFloored += floorSize(segBytes);
			totBeforeMergeBytes += info.info.sizeInBytes();
		}

		final double skew;
		if (hitTooLarge)
			skew = 1.0D / (double)maxMergeAtOnce;
		else
			skew = (double)floorSize(size((SegmentInfoPerCommit)candidate.get(0))) / (double)totAfterMergeBytesFloored;
		double mergeScore = skew;
		mergeScore *= Math.pow(totAfterMergeBytes, 0.050000000000000003D);
		final double nonDelRatio = (double)totAfterMergeBytes / (double)totBeforeMergeBytes;
		mergeScore *= Math.pow(nonDelRatio, reclaimDeletesWeight);
		final double finalMergeScore = mergeScore;
		return new MergeScore() {

			final double val$finalMergeScore;
			final double val$skew;
			final double val$nonDelRatio;
			final TieredMergePolicy this$0;

			public double getScore()
			{
				return finalMergeScore;
			}

			public String getExplanation()
			{
				return (new StringBuilder()).append("skew=").append(String.format(Locale.ROOT, "%.3f", new Object[] {
					Double.valueOf(skew)
				})).append(" nonDelRatio=").append(String.format(Locale.ROOT, "%.3f", new Object[] {
					Double.valueOf(nonDelRatio)
				})).toString();
			}

			
			{
				this$0 = TieredMergePolicy.this;
				finalMergeScore = d;
				skew = d1;
				nonDelRatio = d2;
				super();
			}
		};
	}

	public MergePolicy.MergeSpecification findForcedMerges(SegmentInfos infos, int maxSegmentCount, Map segmentsToMerge)
		throws IOException
	{
		if (verbose())
			message((new StringBuilder()).append("findForcedMerges maxSegmentCount=").append(maxSegmentCount).append(" infos=").append(((IndexWriter)writer.get()).segString(infos)).append(" segmentsToMerge=").append(segmentsToMerge).toString());
		List eligible = new ArrayList();
		boolean forceMergeRunning = false;
		Collection merging = ((IndexWriter)writer.get()).getMergingSegments();
		boolean segmentIsOriginal = false;
		Iterator i$ = infos.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			SegmentInfoPerCommit info = (SegmentInfoPerCommit)i$.next();
			Boolean isOriginal = (Boolean)segmentsToMerge.get(info);
			if (isOriginal != null)
			{
				segmentIsOriginal = isOriginal.booleanValue();
				if (!merging.contains(info))
					eligible.add(info);
				else
					forceMergeRunning = true;
			}
		} while (true);
		if (eligible.size() == 0)
			return null;
		if (maxSegmentCount > 1 && eligible.size() <= maxSegmentCount || maxSegmentCount == 1 && eligible.size() == 1 && (!segmentIsOriginal || isMerged((SegmentInfoPerCommit)eligible.get(0))))
		{
			if (verbose())
				message("already merged");
			return null;
		}
		Collections.sort(eligible, new SegmentByteSizeDescending());
		if (verbose())
		{
			message((new StringBuilder()).append("eligible=").append(eligible).toString());
			message((new StringBuilder()).append("forceMergeRunning=").append(forceMergeRunning).toString());
		}
		int end = eligible.size();
		MergePolicy.MergeSpecification spec = null;
		for (; end >= (maxMergeAtOnceExplicit + maxSegmentCount) - 1; end -= maxMergeAtOnceExplicit)
		{
			if (spec == null)
				spec = new MergePolicy.MergeSpecification();
			MergePolicy.OneMerge merge = new MergePolicy.OneMerge(eligible.subList(end - maxMergeAtOnceExplicit, end));
			if (verbose())
				message((new StringBuilder()).append("add merge=").append(((IndexWriter)writer.get()).segString(merge.segments)).toString());
			spec.add(merge);
		}

		if (spec == null && !forceMergeRunning)
		{
			int numToMerge = (end - maxSegmentCount) + 1;
			MergePolicy.OneMerge merge = new MergePolicy.OneMerge(eligible.subList(end - numToMerge, end));
			if (verbose())
				message((new StringBuilder()).append("add final merge=").append(merge.segString(((IndexWriter)writer.get()).getDirectory())).toString());
			spec = new MergePolicy.MergeSpecification();
			spec.add(merge);
		}
		return spec;
	}

	public MergePolicy.MergeSpecification findForcedDeletesMerges(SegmentInfos infos)
		throws IOException
	{
		if (verbose())
			message((new StringBuilder()).append("findForcedDeletesMerges infos=").append(((IndexWriter)writer.get()).segString(infos)).append(" forceMergeDeletesPctAllowed=").append(forceMergeDeletesPctAllowed).toString());
		List eligible = new ArrayList();
		Collection merging = ((IndexWriter)writer.get()).getMergingSegments();
		Iterator i$ = infos.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			SegmentInfoPerCommit info = (SegmentInfoPerCommit)i$.next();
			double pctDeletes = (100D * (double)((IndexWriter)writer.get()).numDeletedDocs(info)) / (double)info.info.getDocCount();
			if (pctDeletes > forceMergeDeletesPctAllowed && !merging.contains(info))
				eligible.add(info);
		} while (true);
		if (eligible.size() == 0)
			return null;
		Collections.sort(eligible, new SegmentByteSizeDescending());
		if (verbose())
			message((new StringBuilder()).append("eligible=").append(eligible).toString());
		int start = 0;
		MergePolicy.MergeSpecification spec = null;
		int end;
		for (; start < eligible.size(); start = end)
		{
			end = Math.min(start + maxMergeAtOnceExplicit, eligible.size());
			if (spec == null)
				spec = new MergePolicy.MergeSpecification();
			MergePolicy.OneMerge merge = new MergePolicy.OneMerge(eligible.subList(start, end));
			if (verbose())
				message((new StringBuilder()).append("add merge=").append(((IndexWriter)writer.get()).segString(merge.segments)).toString());
			spec.add(merge);
		}

		return spec;
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

	public void close()
	{
	}

	private boolean isMerged(SegmentInfoPerCommit info)
	{
		IndexWriter w = (IndexWriter)writer.get();
		if (!$assertionsDisabled && w == null)
		{
			throw new AssertionError();
		} else
		{
			boolean hasDeletions = w.numDeletedDocs(info) > 0;
			return !hasDeletions && !info.info.hasSeparateNorms() && info.info.dir == w.getDirectory() && (info.info.getUseCompoundFile() == useCompoundFile || noCFSRatio < 1.0D || maxCFSSegmentSize < 0x7fffffffffffffffL);
		}
	}

	private long size(SegmentInfoPerCommit info)
		throws IOException
	{
		long byteSize = info.info.sizeInBytes();
		int delCount = ((IndexWriter)writer.get()).numDeletedDocs(info);
		double delRatio = info.info.getDocCount() > 0 ? (double)delCount / (double)info.info.getDocCount() : 0.0D;
		if (!$assertionsDisabled && delRatio > 1.0D)
			throw new AssertionError();
		else
			return (long)((double)byteSize * (1.0D - delRatio));
	}

	private long floorSize(long bytes)
	{
		return Math.max(floorSegmentBytes, bytes);
	}

	private boolean verbose()
	{
		IndexWriter w = (IndexWriter)writer.get();
		return w != null && w.infoStream.isEnabled("TMP");
	}

	private void message(String message)
	{
		((IndexWriter)writer.get()).infoStream.message("TMP", message);
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder((new StringBuilder()).append("[").append(getClass().getSimpleName()).append(": ").toString());
		sb.append("maxMergeAtOnce=").append(maxMergeAtOnce).append(", ");
		sb.append("maxMergeAtOnceExplicit=").append(maxMergeAtOnceExplicit).append(", ");
		sb.append("maxMergedSegmentMB=").append((double)(maxMergedSegmentBytes / 1024L) / 1024D).append(", ");
		sb.append("floorSegmentMB=").append((double)(floorSegmentBytes / 1024L) / 1024D).append(", ");
		sb.append("forceMergeDeletesPctAllowed=").append(forceMergeDeletesPctAllowed).append(", ");
		sb.append("segmentsPerTier=").append(segsPerTier).append(", ");
		sb.append("useCompoundFile=").append(useCompoundFile).append(", ");
		sb.append("maxCFSSegmentSizeMB=").append(getMaxCFSSegmentSizeMB()).append(", ");
		sb.append("noCFSRatio=").append(noCFSRatio);
		return sb.toString();
	}

	public final double getMaxCFSSegmentSizeMB()
	{
		return (double)(maxCFSSegmentSize / 1024L) / 1024D;
	}

	public final TieredMergePolicy setMaxCFSSegmentSizeMB(double v)
	{
		if (v < 0.0D)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("maxCFSSegmentSizeMB must be >=0 (got ").append(v).append(")").toString());
		} else
		{
			v *= 1048576D;
			maxCFSSegmentSize = v <= 9.2233720368547758E+018D ? (long)v : 0x7fffffffffffffffL;
			return this;
		}
	}


}
