// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FSTEnum.java

package org.apache.lucene.util.fst;

import java.io.IOException;
import org.apache.lucene.util.ArrayUtil;
import org.apache.lucene.util.RamUsageEstimator;

// Referenced classes of package org.apache.lucene.util.fst:
//			FST, Outputs

abstract class FSTEnum
{

	protected final FST fst;
	protected FST.Arc arcs[];
	protected Object output[];
	protected final Object NO_OUTPUT;
	protected final FST.BytesReader fstReader;
	protected final FST.Arc scratchArc = new FST.Arc();
	protected int upto;
	protected int targetLength;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/fst/FSTEnum.desiredAssertionStatus();

	protected FSTEnum(FST fst)
	{
		arcs = new FST.Arc[10];
		output = (Object[])new Object[10];
		this.fst = fst;
		fstReader = fst.getBytesReader(0);
		NO_OUTPUT = fst.outputs.getNoOutput();
		fst.getFirstArc(getArc(0));
		output[0] = NO_OUTPUT;
	}

	protected abstract int getTargetLabel();

	protected abstract int getCurrentLabel();

	protected abstract void setCurrentLabel(int i);

	protected abstract void grow();

	protected final void rewindPrefix()
		throws IOException
	{
		if (upto == 0)
		{
			upto = 1;
			fst.readFirstTargetArc(getArc(0), getArc(1), fstReader);
			return;
		}
		int currentLimit = upto;
		upto = 1;
		do
		{
			if (upto >= currentLimit || upto > targetLength + 1)
				break;
			int cmp = getCurrentLabel() - getTargetLabel();
			if (cmp < 0)
				break;
			if (cmp > 0)
			{
				FST.Arc arc = getArc(upto);
				fst.readFirstTargetArc(getArc(upto - 1), arc, fstReader);
				break;
			}
			upto++;
		} while (true);
	}

	protected void doNext()
		throws IOException
	{
		if (upto == 0)
		{
			upto = 1;
			fst.readFirstTargetArc(getArc(0), getArc(1), fstReader);
		} else
		{
			while (arcs[upto].isLast()) 
			{
				upto--;
				if (upto == 0)
					return;
			}
			fst.readNextArc(arcs[upto], fstReader);
		}
		pushFirst();
	}

	protected void doSeekCeil()
		throws IOException
	{
		rewindPrefix();
		FST.Arc arc = getArc(upto);
		int targetLabel = getTargetLabel();
		do
		{
label0:
			{
				FST.BytesReader in;
				int low;
				int high;
				int mid;
				boolean found;
label1:
				{
					if (arc.bytesPerArc == 0 || arc.label == -1)
						break label0;
					in = fst.getBytesReader(0);
					low = arc.arcIdx;
					high = arc.numArcs - 1;
					mid = 0;
					found = false;
					do
					{
						if (low > high)
							break label1;
						mid = low + high >>> 1;
						in.pos = arc.posArcsStart;
						in.skip(arc.bytesPerArc * mid + 1);
						int midLabel = fst.readLabel(in);
						int cmp = midLabel - targetLabel;
						if (cmp < 0)
						{
							low = mid + 1;
							continue;
						}
						if (cmp <= 0)
							break;
						high = mid - 1;
					} while (true);
					found = true;
				}
				if (found)
				{
					arc.arcIdx = mid - 1;
					fst.readNextRealArc(arc, in);
					if (!$assertionsDisabled && arc.arcIdx != mid)
						throw new AssertionError();
					if (!$assertionsDisabled && arc.label != targetLabel)
						throw new AssertionError((new StringBuilder()).append("arc.label=").append(arc.label).append(" vs targetLabel=").append(targetLabel).append(" mid=").append(mid).toString());
					output[upto] = fst.outputs.add(output[upto - 1], arc.output);
					if (targetLabel == -1)
						return;
					setCurrentLabel(arc.label);
					incr();
					arc = fst.readFirstTargetArc(arc, getArc(upto), fstReader);
					targetLabel = getTargetLabel();
				} else
				{
					if (low == arc.numArcs)
					{
						arc.arcIdx = arc.numArcs - 2;
						fst.readNextRealArc(arc, in);
						if (!$assertionsDisabled && !arc.isLast())
							throw new AssertionError();
						upto--;
						do
						{
							if (upto == 0)
								return;
							FST.Arc prevArc = getArc(upto);
							if (!prevArc.isLast())
							{
								fst.readNextArc(prevArc, fstReader);
								pushFirst();
								return;
							}
							upto--;
						} while (true);
					}
					arc.arcIdx = (low <= high ? high : low) - 1;
					fst.readNextRealArc(arc, in);
					if (!$assertionsDisabled && arc.label <= targetLabel)
					{
						throw new AssertionError();
					} else
					{
						pushFirst();
						return;
					}
				}
				continue;
			}
			if (arc.label == targetLabel)
			{
				output[upto] = fst.outputs.add(output[upto - 1], arc.output);
				if (targetLabel == -1)
					return;
				setCurrentLabel(arc.label);
				incr();
				arc = fst.readFirstTargetArc(arc, getArc(upto), fstReader);
				targetLabel = getTargetLabel();
			} else
			{
				if (arc.label > targetLabel)
				{
					pushFirst();
					return;
				}
				if (arc.isLast())
				{
					upto--;
					do
					{
						if (upto == 0)
							return;
						FST.Arc prevArc = getArc(upto);
						if (!prevArc.isLast())
						{
							fst.readNextArc(prevArc, fstReader);
							pushFirst();
							return;
						}
						upto--;
					} while (true);
				}
				fst.readNextArc(arc, fstReader);
			}
		} while (true);
	}

	protected void doSeekFloor()
		throws IOException
	{
		rewindPrefix();
		FST.Arc arc = getArc(upto);
		int targetLabel = getTargetLabel();
		do
		{
label0:
			{
				FST.BytesReader in;
				int low;
				int high;
				int mid;
				boolean found;
label1:
				{
					if (arc.bytesPerArc == 0 || arc.label == -1)
						break label0;
					in = fst.getBytesReader(0);
					low = arc.arcIdx;
					high = arc.numArcs - 1;
					mid = 0;
					found = false;
					do
					{
						if (low > high)
							break label1;
						mid = low + high >>> 1;
						in.pos = arc.posArcsStart;
						in.skip(arc.bytesPerArc * mid + 1);
						int midLabel = fst.readLabel(in);
						int cmp = midLabel - targetLabel;
						if (cmp < 0)
						{
							low = mid + 1;
							continue;
						}
						if (cmp <= 0)
							break;
						high = mid - 1;
					} while (true);
					found = true;
				}
				if (found)
				{
					arc.arcIdx = mid - 1;
					fst.readNextRealArc(arc, in);
					if (!$assertionsDisabled && arc.arcIdx != mid)
						throw new AssertionError();
					if (!$assertionsDisabled && arc.label != targetLabel)
						throw new AssertionError((new StringBuilder()).append("arc.label=").append(arc.label).append(" vs targetLabel=").append(targetLabel).append(" mid=").append(mid).toString());
					output[upto] = fst.outputs.add(output[upto - 1], arc.output);
					if (targetLabel == -1)
						return;
					setCurrentLabel(arc.label);
					incr();
					arc = fst.readFirstTargetArc(arc, getArc(upto), fstReader);
					targetLabel = getTargetLabel();
				} else
				{
					if (high == -1)
						do
						{
							fst.readFirstTargetArc(getArc(upto - 1), arc, fstReader);
							if (arc.label < targetLabel)
							{
								for (; !arc.isLast() && fst.readNextArcLabel(arc, in) < targetLabel; fst.readNextArc(arc, fstReader));
								pushLast();
								return;
							}
							upto--;
							if (upto == 0)
								return;
							targetLabel = getTargetLabel();
							arc = getArc(upto);
						} while (true);
					arc.arcIdx = (low <= high ? low : high) - 1;
					fst.readNextRealArc(arc, in);
					if (!$assertionsDisabled && !arc.isLast() && fst.readNextArcLabel(arc, in) <= targetLabel)
						throw new AssertionError();
					if (!$assertionsDisabled && arc.label >= targetLabel)
					{
						throw new AssertionError((new StringBuilder()).append("arc.label=").append(arc.label).append(" vs targetLabel=").append(targetLabel).toString());
					} else
					{
						pushLast();
						return;
					}
				}
				continue;
			}
			if (arc.label == targetLabel)
			{
				output[upto] = fst.outputs.add(output[upto - 1], arc.output);
				if (targetLabel == -1)
					return;
				setCurrentLabel(arc.label);
				incr();
				arc = fst.readFirstTargetArc(arc, getArc(upto), fstReader);
				targetLabel = getTargetLabel();
			} else
			{
				if (arc.label > targetLabel)
					do
					{
						fst.readFirstTargetArc(getArc(upto - 1), arc, fstReader);
						if (arc.label < targetLabel)
						{
							for (; !arc.isLast() && fst.readNextArcLabel(arc, fstReader) < targetLabel; fst.readNextArc(arc, fstReader));
							pushLast();
							return;
						}
						upto--;
						if (upto == 0)
							return;
						targetLabel = getTargetLabel();
						arc = getArc(upto);
					} while (true);
				if (!arc.isLast())
				{
					if (fst.readNextArcLabel(arc, fstReader) > targetLabel)
					{
						pushLast();
						return;
					}
					fst.readNextArc(arc, fstReader);
				} else
				{
					pushLast();
					return;
				}
			}
		} while (true);
	}

	protected boolean doSeekExact()
		throws IOException
	{
		rewindPrefix();
		FST.Arc arc = getArc(upto - 1);
		int targetLabel = getTargetLabel();
		FST.BytesReader fstReader = fst.getBytesReader(0);
		do
		{
			FST.Arc nextArc = fst.findTargetArc(targetLabel, arc, getArc(upto), fstReader);
			if (nextArc == null)
			{
				fst.readFirstTargetArc(arc, getArc(upto), fstReader);
				return false;
			}
			output[upto] = fst.outputs.add(output[upto - 1], nextArc.output);
			if (targetLabel == -1)
				return true;
			setCurrentLabel(targetLabel);
			incr();
			targetLabel = getTargetLabel();
			arc = nextArc;
		} while (true);
	}

	private void incr()
	{
		upto++;
		grow();
		if (arcs.length <= upto)
		{
			FST.Arc newArcs[] = new FST.Arc[ArrayUtil.oversize(1 + upto, RamUsageEstimator.NUM_BYTES_OBJECT_REF)];
			System.arraycopy(arcs, 0, newArcs, 0, arcs.length);
			arcs = newArcs;
		}
		if (output.length <= upto)
		{
			Object newOutput[] = (Object[])new Object[ArrayUtil.oversize(1 + upto, RamUsageEstimator.NUM_BYTES_OBJECT_REF)];
			System.arraycopy(((Object) (output)), 0, ((Object) (newOutput)), 0, output.length);
			output = newOutput;
		}
	}

	private void pushFirst()
		throws IOException
	{
		FST.Arc arc = arcs[upto];
		if (!$assertionsDisabled && arc == null)
			throw new AssertionError();
		do
		{
			output[upto] = fst.outputs.add(output[upto - 1], arc.output);
			if (arc.label != -1)
			{
				setCurrentLabel(arc.label);
				incr();
				FST.Arc nextArc = getArc(upto);
				fst.readFirstTargetArc(arc, nextArc, fstReader);
				arc = nextArc;
			} else
			{
				return;
			}
		} while (true);
	}

	private void pushLast()
		throws IOException
	{
		FST.Arc arc = arcs[upto];
		if (!$assertionsDisabled && arc == null)
			throw new AssertionError();
		do
		{
			setCurrentLabel(arc.label);
			output[upto] = fst.outputs.add(output[upto - 1], arc.output);
			if (arc.label != -1)
			{
				incr();
				arc = fst.readLastTargetArc(arc, getArc(upto), fstReader);
			} else
			{
				return;
			}
		} while (true);
	}

	private FST.Arc getArc(int idx)
	{
		if (arcs[idx] == null)
			arcs[idx] = new FST.Arc();
		return arcs[idx];
	}

}
