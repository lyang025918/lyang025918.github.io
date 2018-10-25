// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NodeHash.java

package org.apache.lucene.util.fst;

import java.io.IOException;

// Referenced classes of package org.apache.lucene.util.fst:
//			FST, Builder

final class NodeHash
{

	private int table[];
	private int count;
	private int mask;
	private final FST fst;
	private final FST.Arc scratchArc = new FST.Arc();
	static final boolean $assertionsDisabled = !org/apache/lucene/util/fst/NodeHash.desiredAssertionStatus();

	public NodeHash(FST fst)
	{
		table = new int[16];
		mask = 15;
		this.fst = fst;
	}

	private boolean nodesEqual(Builder.UnCompiledNode node, int address, FST.BytesReader in)
		throws IOException
	{
		fst.readFirstRealTargetArc(address, scratchArc, in);
		if (scratchArc.bytesPerArc != 0 && node.numArcs != scratchArc.numArcs)
			return false;
		for (int arcUpto = 0; arcUpto < node.numArcs; arcUpto++)
		{
			Builder.Arc arc = node.arcs[arcUpto];
			if (arc.label != scratchArc.label || !arc.output.equals(scratchArc.output) || ((Builder.CompiledNode)arc.target).node != scratchArc.target || !arc.nextFinalOutput.equals(scratchArc.nextFinalOutput) || arc.isFinal != scratchArc.isFinal())
				return false;
			if (scratchArc.isLast())
				return arcUpto == node.numArcs - 1;
			fst.readNextRealArc(scratchArc, in);
		}

		return false;
	}

	private int hash(Builder.UnCompiledNode node)
	{
		int PRIME = 31;
		int h = 0;
		for (int arcIdx = 0; arcIdx < node.numArcs; arcIdx++)
		{
			Builder.Arc arc = node.arcs[arcIdx];
			h = 31 * h + arc.label;
			h = 31 * h + ((Builder.CompiledNode)arc.target).node;
			h = 31 * h + arc.output.hashCode();
			h = 31 * h + arc.nextFinalOutput.hashCode();
			if (arc.isFinal)
				h += 17;
		}

		return h & 0x7fffffff;
	}

	private int hash(int node)
		throws IOException
	{
		int PRIME = 31;
		FST.BytesReader in = fst.getBytesReader(0);
		int h = 0;
		fst.readFirstRealTargetArc(node, scratchArc, in);
		do
		{
			h = 31 * h + scratchArc.label;
			h = 31 * h + scratchArc.target;
			h = 31 * h + scratchArc.output.hashCode();
			h = 31 * h + scratchArc.nextFinalOutput.hashCode();
			if (scratchArc.isFinal())
				h += 17;
			if (!scratchArc.isLast())
				fst.readNextRealArc(scratchArc, in);
			else
				return h & 0x7fffffff;
		} while (true);
	}

	public int add(Builder.UnCompiledNode nodeIn)
		throws IOException
	{
		FST.BytesReader in = fst.getBytesReader(0);
		int h = hash(nodeIn);
		int pos = h & mask;
		int c = 0;
		do
		{
			int v = table[pos];
			if (v == 0)
			{
				int node = fst.addNode(nodeIn);
				if (!$assertionsDisabled && hash(node) != h)
					throw new AssertionError((new StringBuilder()).append("frozenHash=").append(hash(node)).append(" vs h=").append(h).toString());
				count++;
				table[pos] = node;
				if (table.length < 2 * count)
					rehash();
				return node;
			}
			if (nodesEqual(nodeIn, v, in))
				return v;
			pos = pos + ++c & mask;
		} while (true);
	}

	private void addNew(int address)
		throws IOException
	{
		int pos = hash(address) & mask;
		int c = 0;
		do
		{
			if (table[pos] == 0)
			{
				table[pos] = address;
				break;
			}
			pos = pos + ++c & mask;
		} while (true);
	}

	private void rehash()
		throws IOException
	{
		int oldTable[] = table;
		if (oldTable.length >= 0x3fffffff)
			throw new IllegalStateException("FST too large (> 2.1 GB)");
		table = new int[2 * table.length];
		mask = table.length - 1;
		for (int idx = 0; idx < oldTable.length; idx++)
		{
			int address = oldTable[idx];
			if (address != 0)
				addNew(address);
		}

	}

	public int count()
	{
		return count;
	}

}
