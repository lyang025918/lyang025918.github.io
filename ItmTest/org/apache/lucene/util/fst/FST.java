// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FST.java

package org.apache.lucene.util.fst;

import java.io.*;
import java.util.*;
import org.apache.lucene.codecs.CodecUtil;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;
import org.apache.lucene.util.packed.GrowableWriter;
import org.apache.lucene.util.packed.PackedInts;

// Referenced classes of package org.apache.lucene.util.fst:
//			Outputs, Builder

public final class FST
{
	private static class NodeQueue extends PriorityQueue
	{

		static final boolean $assertionsDisabled = !org/apache/lucene/util/fst/FST.desiredAssertionStatus();

		public boolean lessThan(NodeAndInCount a, NodeAndInCount b)
		{
			int cmp = a.compareTo(b);
			if (!$assertionsDisabled && cmp == 0)
				throw new AssertionError();
			else
				return cmp < 0;
		}

		public volatile boolean lessThan(Object x0, Object x1)
		{
			return lessThan((NodeAndInCount)x0, (NodeAndInCount)x1);
		}


		public NodeQueue(int topN)
		{
			super(topN, false);
		}
	}

	private static class NodeAndInCount
		implements Comparable
	{

		final int node;
		final int count;

		public int compareTo(NodeAndInCount other)
		{
			if (count > other.count)
				return 1;
			if (count < other.count)
				return -1;
			else
				return other.node - node;
		}

		public volatile int compareTo(Object x0)
		{
			return compareTo((NodeAndInCount)x0);
		}

		public NodeAndInCount(int node, int count)
		{
			this.node = node;
			this.count = count;
		}
	}

	private static class ArcAndState
	{

		final Arc arc;
		final IntsRef chain;

		public ArcAndState(Arc arc, IntsRef chain)
		{
			this.arc = arc;
			this.chain = chain;
		}
	}

	static final class ForwardBytesReader extends BytesReader
	{

		public byte readByte()
		{
			return bytes[pos++];
		}

		public void readBytes(byte b[], int offset, int len)
		{
			System.arraycopy(bytes, pos, b, offset, len);
			pos += len;
		}

		public void skip(int count)
		{
			pos += count;
		}

		public void skip(int base, int count)
		{
			pos = base + count;
		}

		public ForwardBytesReader(byte bytes[], int pos)
		{
			super(bytes, pos);
		}
	}

	static final class ReverseBytesReader extends BytesReader
	{

		public byte readByte()
		{
			return bytes[pos--];
		}

		public void readBytes(byte b[], int offset, int len)
		{
			for (int i = 0; i < len; i++)
				b[offset + i] = bytes[pos--];

		}

		public void skip(int count)
		{
			pos -= count;
		}

		public void skip(int base, int count)
		{
			pos = base - count;
		}

		public ReverseBytesReader(byte bytes[], int pos)
		{
			super(bytes, pos);
		}
	}

	public static abstract class BytesReader extends DataInput
	{

		protected int pos;
		protected final byte bytes[];

		abstract void skip(int i);

		abstract void skip(int i, int j);

		protected BytesReader(byte bytes[], int pos)
		{
			this.bytes = bytes;
			this.pos = pos;
		}
	}

	class BytesWriter extends DataOutput
	{

		int posWrite;
		static final boolean $assertionsDisabled = !org/apache/lucene/util/fst/FST.desiredAssertionStatus();
		final FST this$0;

		public void writeByte(byte b)
		{
			if (!$assertionsDisabled && posWrite > bytes.length)
				throw new AssertionError();
			if (bytes.length == posWrite)
			{
				if (!$assertionsDisabled && bytes.length >= 0x7fffffff)
					throw new AssertionError("FST too large (> 2.1 GB)");
				bytes = ArrayUtil.grow(bytes);
			}
			if (!$assertionsDisabled && posWrite >= bytes.length)
			{
				throw new AssertionError((new StringBuilder()).append("posWrite=").append(posWrite).append(" bytes.length=").append(bytes.length).toString());
			} else
			{
				bytes[posWrite++] = b;
				return;
			}
		}

		public void setPosWrite(int posWrite)
		{
			this.posWrite = posWrite;
			if (bytes.length < posWrite)
			{
				if (!$assertionsDisabled && bytes.length >= 0x7fffffff)
					throw new AssertionError("FST too large (> 2.1 GB)");
				bytes = ArrayUtil.grow(bytes, posWrite);
			}
		}

		public void writeBytes(byte b[], int offset, int length)
		{
			int size = posWrite + length;
			if (!$assertionsDisabled && bytes.length >= 0x7fffffff)
			{
				throw new AssertionError("FST too large (> 2.1 GB)");
			} else
			{
				bytes = ArrayUtil.grow(bytes, size);
				System.arraycopy(b, offset, bytes, posWrite, length);
				posWrite += length;
				return;
			}
		}


		public BytesWriter()
		{
			this$0 = FST.this;
			super();
			posWrite = 1;
		}
	}

	public static final class Arc
	{

		public int label;
		public Object output;
		int node;
		public int target;
		byte flags;
		public Object nextFinalOutput;
		int nextArc;
		int posArcsStart;
		int bytesPerArc;
		int arcIdx;
		int numArcs;

		public Arc copyFrom(Arc other)
		{
			node = other.node;
			label = other.label;
			target = other.target;
			flags = other.flags;
			output = other.output;
			nextFinalOutput = other.nextFinalOutput;
			nextArc = other.nextArc;
			bytesPerArc = other.bytesPerArc;
			if (bytesPerArc != 0)
			{
				posArcsStart = other.posArcsStart;
				arcIdx = other.arcIdx;
				numArcs = other.numArcs;
			}
			return this;
		}

		boolean flag(int flag)
		{
			return FST.flag(flags, flag);
		}

		public boolean isLast()
		{
			return flag(2);
		}

		public boolean isFinal()
		{
			return flag(1);
		}

		public String toString()
		{
			StringBuilder b = new StringBuilder();
			b.append((new StringBuilder()).append("node=").append(node).toString());
			b.append((new StringBuilder()).append(" target=").append(target).toString());
			b.append((new StringBuilder()).append(" label=").append(label).toString());
			if (flag(2))
				b.append(" last");
			if (flag(1))
				b.append(" final");
			if (flag(4))
				b.append(" targetNext");
			if (flag(16))
				b.append((new StringBuilder()).append(" output=").append(output).toString());
			if (flag(32))
				b.append((new StringBuilder()).append(" nextFinalOutput=").append(nextFinalOutput).toString());
			if (bytesPerArc != 0)
				b.append((new StringBuilder()).append(" arcArray(idx=").append(arcIdx).append(" of ").append(numArcs).append(")").toString());
			return b.toString();
		}

		public Arc()
		{
		}
	}

	public static final class INPUT_TYPE extends Enum
	{

		public static final INPUT_TYPE BYTE1;
		public static final INPUT_TYPE BYTE2;
		public static final INPUT_TYPE BYTE4;
		private static final INPUT_TYPE $VALUES[];

		public static INPUT_TYPE[] values()
		{
			return (INPUT_TYPE[])$VALUES.clone();
		}

		public static INPUT_TYPE valueOf(String name)
		{
			return (INPUT_TYPE)Enum.valueOf(org/apache/lucene/util/fst/FST$INPUT_TYPE, name);
		}

		static 
		{
			BYTE1 = new INPUT_TYPE("BYTE1", 0);
			BYTE2 = new INPUT_TYPE("BYTE2", 1);
			BYTE4 = new INPUT_TYPE("BYTE4", 2);
			$VALUES = (new INPUT_TYPE[] {
				BYTE1, BYTE2, BYTE4
			});
		}

		private INPUT_TYPE(String s, int i)
		{
			super(s, i);
		}
	}


	public final INPUT_TYPE inputType;
	static final int BIT_FINAL_ARC = 1;
	static final int BIT_LAST_ARC = 2;
	static final int BIT_TARGET_NEXT = 4;
	static final int BIT_STOP_NODE = 8;
	static final int BIT_ARC_HAS_OUTPUT = 16;
	static final int BIT_ARC_HAS_FINAL_OUTPUT = 32;
	private static final int BIT_TARGET_DELTA = 64;
	private static final byte ARCS_AS_FIXED_ARRAY = 32;
	static final int FIXED_ARRAY_SHALLOW_DISTANCE = 3;
	static final int FIXED_ARRAY_NUM_ARCS_SHALLOW = 5;
	static final int FIXED_ARRAY_NUM_ARCS_DEEP = 10;
	private int bytesPerArc[];
	private static final String FILE_FORMAT_NAME = "FST";
	private static final int VERSION_START = 0;
	private static final int VERSION_INT_NUM_BYTES_PER_ARC = 1;
	private static final int VERSION_SHORT_BYTE2_LABELS = 2;
	private static final int VERSION_PACKED = 3;
	private static final int VERSION_CURRENT = 3;
	private static final int FINAL_END_NODE = -1;
	private static final int NON_FINAL_END_NODE = 0;
	Object emptyOutput;
	private byte emptyOutputBytes[];
	byte bytes[];
	int byteUpto;
	private int startNode;
	public final Outputs outputs;
	private int lastFrozenNode;
	private final Object NO_OUTPUT;
	public int nodeCount;
	public int arcCount;
	public int arcWithOutputCount;
	private final boolean packed;
	private org.apache.lucene.util.packed.PackedInts.Reader nodeRefToAddress;
	public static final int END_LABEL = -1;
	private boolean allowArrayArcs;
	private Arc cachedRootArcs[];
	private final BytesWriter writer;
	private GrowableWriter nodeAddress;
	private GrowableWriter inCounts;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/fst/FST.desiredAssertionStatus();

	private static boolean flag(int flags, int bit)
	{
		return (flags & bit) != 0;
	}

	FST(INPUT_TYPE inputType, Outputs outputs, boolean willPackFST, float acceptableOverheadRatio)
	{
		bytesPerArc = new int[0];
		byteUpto = 0;
		startNode = -1;
		allowArrayArcs = true;
		this.inputType = inputType;
		this.outputs = outputs;
		bytes = new byte[128];
		NO_OUTPUT = outputs.getNoOutput();
		if (willPackFST)
		{
			nodeAddress = new GrowableWriter(PackedInts.bitsRequired(bytes.length - 1), 8, acceptableOverheadRatio);
			inCounts = new GrowableWriter(1, 8, acceptableOverheadRatio);
		} else
		{
			nodeAddress = null;
			inCounts = null;
		}
		writer = new BytesWriter();
		emptyOutput = null;
		packed = false;
		nodeRefToAddress = null;
	}

	public FST(DataInput in, Outputs outputs)
		throws IOException
	{
		bytesPerArc = new int[0];
		byteUpto = 0;
		startNode = -1;
		allowArrayArcs = true;
		this.outputs = outputs;
		writer = null;
		CodecUtil.checkHeader(in, "FST", 3, 3);
		packed = in.readByte() == 1;
		if (in.readByte() == 1)
		{
			int numBytes = in.readVInt();
			bytes = new byte[numBytes];
			in.readBytes(bytes, 0, numBytes);
			if (packed)
				emptyOutput = outputs.read(getBytesReader(0));
			else
				emptyOutput = outputs.read(getBytesReader(numBytes - 1));
		} else
		{
			emptyOutput = null;
		}
		byte t = in.readByte();
		switch (t)
		{
		case 0: // '\0'
			inputType = INPUT_TYPE.BYTE1;
			break;

		case 1: // '\001'
			inputType = INPUT_TYPE.BYTE2;
			break;

		case 2: // '\002'
			inputType = INPUT_TYPE.BYTE4;
			break;

		default:
			throw new IllegalStateException((new StringBuilder()).append("invalid input type ").append(t).toString());
		}
		if (packed)
			nodeRefToAddress = PackedInts.getReader(in);
		else
			nodeRefToAddress = null;
		startNode = in.readVInt();
		nodeCount = in.readVInt();
		arcCount = in.readVInt();
		arcWithOutputCount = in.readVInt();
		bytes = new byte[in.readVInt()];
		in.readBytes(bytes, 0, bytes.length);
		NO_OUTPUT = outputs.getNoOutput();
		cacheRootArcs();
	}

	public INPUT_TYPE getInputType()
	{
		return inputType;
	}

	public int sizeInBytes()
	{
		int size = bytes.length;
		if (packed)
			size = (int)((long)size + nodeRefToAddress.ramBytesUsed());
		else
		if (nodeAddress != null)
		{
			size = (int)((long)size + nodeAddress.ramBytesUsed());
			size = (int)((long)size + inCounts.ramBytesUsed());
		}
		return size;
	}

	void finish(int startNode)
		throws IOException
	{
		if (startNode == -1 && emptyOutput != null)
			startNode = 0;
		if (this.startNode != -1)
		{
			throw new IllegalStateException("already finished");
		} else
		{
			byte finalBytes[] = new byte[writer.posWrite];
			System.arraycopy(bytes, 0, finalBytes, 0, writer.posWrite);
			bytes = finalBytes;
			this.startNode = startNode;
			cacheRootArcs();
			return;
		}
	}

	private int getNodeAddress(int node)
	{
		if (nodeAddress != null)
			return (int)nodeAddress.get(node);
		else
			return node;
	}

	private void cacheRootArcs()
		throws IOException
	{
		cachedRootArcs = (Arc[])new Arc[128];
		Arc arc = new Arc();
		getFirstArc(arc);
		BytesReader in = getBytesReader(0);
		if (targetHasArcs(arc))
		{
			readFirstRealTargetArc(arc.target, arc, in);
			do
			{
				if (!$assertionsDisabled && arc.label == -1)
					throw new AssertionError();
				if (arc.label >= cachedRootArcs.length)
					break;
				cachedRootArcs[arc.label] = (new Arc()).copyFrom(arc);
				if (arc.isLast())
					break;
				readNextRealArc(arc, in);
			} while (true);
		}
	}

	public Object getEmptyOutput()
	{
		return emptyOutput;
	}

	void setEmptyOutput(Object v)
		throws IOException
	{
		if (emptyOutput != null)
			emptyOutput = outputs.merge(emptyOutput, v);
		else
			emptyOutput = v;
		int posSave = writer.posWrite;
		outputs.write(emptyOutput, writer);
		emptyOutputBytes = new byte[writer.posWrite - posSave];
		if (!packed)
		{
			int stopAt = (writer.posWrite - posSave) / 2;
			for (int upto = 0; upto < stopAt; upto++)
			{
				byte b = bytes[posSave + upto];
				bytes[posSave + upto] = bytes[writer.posWrite - upto - 1];
				bytes[writer.posWrite - upto - 1] = b;
			}

		}
		System.arraycopy(bytes, posSave, emptyOutputBytes, 0, writer.posWrite - posSave);
		writer.posWrite = posSave;
	}

	public void save(DataOutput out)
		throws IOException
	{
		if (startNode == -1)
			throw new IllegalStateException("call finish first");
		if (nodeAddress != null)
			throw new IllegalStateException("cannot save an FST pre-packed FST; it must first be packed");
		if (packed && !(nodeRefToAddress instanceof org.apache.lucene.util.packed.PackedInts.Mutable))
			throw new IllegalStateException("cannot save a FST which has been loaded from disk ");
		CodecUtil.writeHeader(out, "FST", 3);
		if (packed)
			out.writeByte((byte)1);
		else
			out.writeByte((byte)0);
		if (emptyOutput != null)
		{
			out.writeByte((byte)1);
			out.writeVInt(emptyOutputBytes.length);
			out.writeBytes(emptyOutputBytes, 0, emptyOutputBytes.length);
		} else
		{
			out.writeByte((byte)0);
		}
		byte t;
		if (inputType == INPUT_TYPE.BYTE1)
			t = 0;
		else
		if (inputType == INPUT_TYPE.BYTE2)
			t = 1;
		else
			t = 2;
		out.writeByte(t);
		if (packed)
			((org.apache.lucene.util.packed.PackedInts.Mutable)nodeRefToAddress).save(out);
		out.writeVInt(startNode);
		out.writeVInt(nodeCount);
		out.writeVInt(arcCount);
		out.writeVInt(arcWithOutputCount);
		out.writeVInt(bytes.length);
		out.writeBytes(bytes, 0, bytes.length);
	}

	public void save(File file)
		throws IOException
	{
		boolean success;
		OutputStream os;
		success = false;
		os = new BufferedOutputStream(new FileOutputStream(file));
		save(((DataOutput) (new OutputStreamDataOutput(os))));
		success = true;
		if (success)
			IOUtils.close(new Closeable[] {
				os
			});
		else
			IOUtils.closeWhileHandlingException(new Closeable[] {
				os
			});
		break MISSING_BLOCK_LABEL_98;
		Exception exception;
		exception;
		if (success)
			IOUtils.close(new Closeable[] {
				os
			});
		else
			IOUtils.closeWhileHandlingException(new Closeable[] {
				os
			});
		throw exception;
	}

	public static FST read(File file, Outputs outputs)
		throws IOException
	{
		InputStream is;
		boolean success;
		is = new BufferedInputStream(new FileInputStream(file));
		success = false;
		FST fst1;
		FST fst = new FST(new InputStreamDataInput(is), outputs);
		success = true;
		fst1 = fst;
		if (success)
			IOUtils.close(new Closeable[] {
				is
			});
		else
			IOUtils.closeWhileHandlingException(new Closeable[] {
				is
			});
		return fst1;
		Exception exception;
		exception;
		if (success)
			IOUtils.close(new Closeable[] {
				is
			});
		else
			IOUtils.closeWhileHandlingException(new Closeable[] {
				is
			});
		throw exception;
	}

	private void writeLabel(int v)
		throws IOException
	{
		if (!$assertionsDisabled && v < 0)
			throw new AssertionError((new StringBuilder()).append("v=").append(v).toString());
		if (inputType == INPUT_TYPE.BYTE1)
		{
			if (!$assertionsDisabled && v > 255)
				throw new AssertionError((new StringBuilder()).append("v=").append(v).toString());
			writer.writeByte((byte)v);
		} else
		if (inputType == INPUT_TYPE.BYTE2)
		{
			if (!$assertionsDisabled && v > 65535)
				throw new AssertionError((new StringBuilder()).append("v=").append(v).toString());
			writer.writeShort((short)v);
		} else
		{
			writer.writeVInt(v);
		}
	}

	int readLabel(DataInput in)
		throws IOException
	{
		int v;
		if (inputType == INPUT_TYPE.BYTE1)
			v = in.readByte() & 0xff;
		else
		if (inputType == INPUT_TYPE.BYTE2)
			v = in.readShort() & 0xffff;
		else
			v = in.readVInt();
		return v;
	}

	public static boolean targetHasArcs(Arc arc)
	{
		return arc.target > 0;
	}

	int addNode(Builder.UnCompiledNode nodeIn)
		throws IOException
	{
		if (nodeIn.numArcs == 0)
			return !nodeIn.isFinal ? 0 : -1;
		int startAddress = writer.posWrite;
		boolean doFixedArray = shouldExpand(nodeIn);
		int fixedArrayStart;
		if (doFixedArray)
		{
			if (bytesPerArc.length < nodeIn.numArcs)
				bytesPerArc = new int[ArrayUtil.oversize(nodeIn.numArcs, 1)];
			writer.writeByte((byte)32);
			writer.writeVInt(nodeIn.numArcs);
			writer.writeInt(0);
			fixedArrayStart = writer.posWrite;
		} else
		{
			fixedArrayStart = 0;
		}
		arcCount += nodeIn.numArcs;
		int lastArc = nodeIn.numArcs - 1;
		int lastArcStart = writer.posWrite;
		int maxBytesPerArc = 0;
		for (int arcIdx = 0; arcIdx < nodeIn.numArcs; arcIdx++)
		{
			Builder.Arc arc = nodeIn.arcs[arcIdx];
			Builder.CompiledNode target = (Builder.CompiledNode)arc.target;
			int flags = 0;
			if (arcIdx == lastArc)
				flags += 2;
			if (lastFrozenNode == target.node && !doFixedArray)
				flags += 4;
			if (arc.isFinal)
			{
				flags++;
				if (arc.nextFinalOutput != NO_OUTPUT)
					flags += 32;
			} else
			if (!$assertionsDisabled && arc.nextFinalOutput != NO_OUTPUT)
				throw new AssertionError();
			boolean targetHasArcs = target.node > 0;
			if (!targetHasArcs)
				flags += 8;
			else
			if (inCounts != null)
				inCounts.set(target.node, inCounts.get(target.node) + 1L);
			if (arc.output != NO_OUTPUT)
				flags += 16;
			writer.writeByte((byte)flags);
			writeLabel(arc.label);
			if (arc.output != NO_OUTPUT)
			{
				outputs.write(arc.output, writer);
				arcWithOutputCount++;
			}
			if (arc.nextFinalOutput != NO_OUTPUT)
				outputs.write(arc.nextFinalOutput, writer);
			if (targetHasArcs && (flags & 4) == 0)
			{
				if (!$assertionsDisabled && target.node <= 0)
					throw new AssertionError();
				writer.writeInt(target.node);
			}
			if (doFixedArray)
			{
				bytesPerArc[arcIdx] = writer.posWrite - lastArcStart;
				lastArcStart = writer.posWrite;
				maxBytesPerArc = Math.max(maxBytesPerArc, bytesPerArc[arcIdx]);
			}
		}

		if (doFixedArray)
		{
			if (!$assertionsDisabled && maxBytesPerArc <= 0)
				throw new AssertionError();
			int sizeNeeded = fixedArrayStart + nodeIn.numArcs * maxBytesPerArc;
			if (!$assertionsDisabled && (long)fixedArrayStart + (long)nodeIn.numArcs * (long)maxBytesPerArc >= 0x7fffffffL)
				throw new AssertionError("FST too large (> 2.1 GB)");
			bytes = ArrayUtil.grow(bytes, sizeNeeded);
			bytes[fixedArrayStart - 4] = (byte)(maxBytesPerArc >> 24);
			bytes[fixedArrayStart - 3] = (byte)(maxBytesPerArc >> 16);
			bytes[fixedArrayStart - 2] = (byte)(maxBytesPerArc >> 8);
			bytes[fixedArrayStart - 1] = (byte)maxBytesPerArc;
			int srcPos = writer.posWrite;
			int destPos = fixedArrayStart + nodeIn.numArcs * maxBytesPerArc;
			writer.posWrite = destPos;
			for (int arcIdx = nodeIn.numArcs - 1; arcIdx >= 0; arcIdx--)
			{
				destPos -= maxBytesPerArc;
				srcPos -= bytesPerArc[arcIdx];
				if (srcPos == destPos)
					continue;
				if (!$assertionsDisabled && destPos <= srcPos)
					throw new AssertionError((new StringBuilder()).append("destPos=").append(destPos).append(" srcPos=").append(srcPos).append(" arcIdx=").append(arcIdx).append(" maxBytesPerArc=").append(maxBytesPerArc).append(" bytesPerArc[arcIdx]=").append(bytesPerArc[arcIdx]).append(" nodeIn.numArcs=").append(nodeIn.numArcs).toString());
				System.arraycopy(bytes, srcPos, bytes, destPos, bytesPerArc[arcIdx]);
			}

		}
		int endAddress = writer.posWrite - 1;
		int left = startAddress;
		for (int right = endAddress; left < right;)
		{
			byte b = bytes[left];
			bytes[left++] = bytes[right];
			bytes[right--] = b;
		}

		nodeCount++;
		int node;
		if (nodeAddress != null)
		{
			if (nodeCount == nodeAddress.size())
			{
				nodeAddress = nodeAddress.resize(ArrayUtil.oversize(nodeAddress.size() + 1, nodeAddress.getBitsPerValue()));
				inCounts = inCounts.resize(ArrayUtil.oversize(inCounts.size() + 1, inCounts.getBitsPerValue()));
			}
			nodeAddress.set(nodeCount, endAddress);
			node = nodeCount;
		} else
		{
			node = endAddress;
		}
		lastFrozenNode = node;
		return node;
	}

	public Arc getFirstArc(Arc arc)
	{
		if (emptyOutput != null)
		{
			arc.flags = 3;
			arc.nextFinalOutput = emptyOutput;
		} else
		{
			arc.flags = 2;
			arc.nextFinalOutput = NO_OUTPUT;
		}
		arc.output = NO_OUTPUT;
		arc.target = startNode;
		return arc;
	}

	public Arc readLastTargetArc(Arc follow, Arc arc, BytesReader in)
		throws IOException
	{
		if (!targetHasArcs(follow))
			if (!$assertionsDisabled && !follow.isFinal())
			{
				throw new AssertionError();
			} else
			{
				arc.label = -1;
				arc.target = -1;
				arc.output = follow.nextFinalOutput;
				arc.flags = 2;
				return arc;
			}
		in.pos = getNodeAddress(follow.target);
		arc.node = follow.target;
		byte b = in.readByte();
		if (b == 32)
		{
			arc.numArcs = in.readVInt();
			if (packed)
				arc.bytesPerArc = in.readVInt();
			else
				arc.bytesPerArc = in.readInt();
			arc.posArcsStart = in.pos;
			arc.arcIdx = arc.numArcs - 2;
		} else
		{
			arc.flags = b;
			arc.bytesPerArc = 0;
			for (; !arc.isLast(); arc.flags = in.readByte())
			{
				readLabel(in);
				if (arc.flag(16))
					outputs.read(in);
				if (arc.flag(32))
					outputs.read(in);
				if (arc.flag(8) || arc.flag(4))
					continue;
				if (packed)
					in.readVInt();
				else
					in.skip(4);
			}

			in.skip(-1);
			arc.nextArc = in.pos;
		}
		readNextRealArc(arc, in);
		if (!$assertionsDisabled && !arc.isLast())
			throw new AssertionError();
		else
			return arc;
	}

	public Arc readFirstTargetArc(Arc follow, Arc arc, BytesReader in)
		throws IOException
	{
		if (follow.isFinal())
		{
			arc.label = -1;
			arc.output = follow.nextFinalOutput;
			arc.flags = 1;
			if (follow.target <= 0)
			{
				arc.flags |= 2;
			} else
			{
				arc.node = follow.target;
				arc.nextArc = follow.target;
			}
			arc.target = -1;
			return arc;
		} else
		{
			return readFirstRealTargetArc(follow.target, arc, in);
		}
	}

	public Arc readFirstRealTargetArc(int node, Arc arc, BytesReader in)
		throws IOException
	{
		if (!$assertionsDisabled && in.bytes != bytes)
			throw new AssertionError();
		int address = getNodeAddress(node);
		in.pos = address;
		arc.node = node;
		if (in.readByte() == 32)
		{
			arc.numArcs = in.readVInt();
			if (packed)
				arc.bytesPerArc = in.readVInt();
			else
				arc.bytesPerArc = in.readInt();
			arc.arcIdx = -1;
			arc.nextArc = arc.posArcsStart = in.pos;
		} else
		{
			arc.nextArc = address;
			arc.bytesPerArc = 0;
		}
		return readNextRealArc(arc, in);
	}

	boolean isExpandedTarget(Arc follow, BytesReader in)
		throws IOException
	{
		if (!targetHasArcs(follow))
		{
			return false;
		} else
		{
			in.pos = getNodeAddress(follow.target);
			return in.readByte() == 32;
		}
	}

	public Arc readNextArc(Arc arc, BytesReader in)
		throws IOException
	{
		if (arc.label == -1)
		{
			if (arc.nextArc <= 0)
				throw new IllegalArgumentException("cannot readNextArc when arc.isLast()=true");
			else
				return readFirstRealTargetArc(arc.nextArc, arc, in);
		} else
		{
			return readNextRealArc(arc, in);
		}
	}

	public int readNextArcLabel(Arc arc, BytesReader in)
		throws IOException
	{
		if (!$assertionsDisabled && arc.isLast())
			throw new AssertionError();
		if (arc.label == -1)
		{
			in.pos = getNodeAddress(arc.nextArc);
			byte b = bytes[in.pos];
			if (b == 32)
			{
				in.skip(1);
				in.readVInt();
				if (packed)
					in.readVInt();
				else
					in.readInt();
			}
		} else
		if (arc.bytesPerArc != 0)
		{
			in.pos = arc.posArcsStart;
			in.skip((1 + arc.arcIdx) * arc.bytesPerArc);
		} else
		{
			in.pos = arc.nextArc;
		}
		in.readByte();
		return readLabel(in);
	}

	public Arc readNextRealArc(Arc arc, BytesReader in)
		throws IOException
	{
		if (!$assertionsDisabled && in.bytes != bytes)
			throw new AssertionError();
		if (arc.bytesPerArc != 0)
		{
			arc.arcIdx++;
			if (!$assertionsDisabled && arc.arcIdx >= arc.numArcs)
				throw new AssertionError();
			in.skip(arc.posArcsStart, arc.arcIdx * arc.bytesPerArc);
		} else
		{
			in.pos = arc.nextArc;
		}
		arc.flags = in.readByte();
		arc.label = readLabel(in);
		if (arc.flag(16))
			arc.output = outputs.read(in);
		else
			arc.output = outputs.getNoOutput();
		if (arc.flag(32))
			arc.nextFinalOutput = outputs.read(in);
		else
			arc.nextFinalOutput = outputs.getNoOutput();
		if (arc.flag(8))
		{
			if (arc.flag(1))
				arc.target = -1;
			else
				arc.target = 0;
			arc.nextArc = in.pos;
		} else
		if (arc.flag(4))
		{
			arc.nextArc = in.pos;
			if (nodeAddress == null)
			{
				if (!arc.flag(2))
					if (arc.bytesPerArc == 0)
						seekToNextNode(in);
					else
						in.skip(arc.posArcsStart, arc.bytesPerArc * arc.numArcs);
				arc.target = in.pos;
			} else
			{
				arc.target = arc.node - 1;
				if (!$assertionsDisabled && arc.target <= 0)
					throw new AssertionError();
			}
		} else
		{
			if (packed)
			{
				int pos = in.pos;
				int code = in.readVInt();
				if (arc.flag(64))
					arc.target = pos + code;
				else
				if (code < nodeRefToAddress.size())
					arc.target = (int)nodeRefToAddress.get(code);
				else
					arc.target = code;
			} else
			{
				arc.target = in.readInt();
			}
			arc.nextArc = in.pos;
		}
		return arc;
	}

	public Arc findTargetArc(int labelToMatch, Arc follow, Arc arc, BytesReader in)
		throws IOException
	{
		if (!$assertionsDisabled && cachedRootArcs == null)
			throw new AssertionError();
		if (!$assertionsDisabled && in.bytes != bytes)
			throw new AssertionError();
		if (labelToMatch == -1)
			if (follow.isFinal())
			{
				if (follow.target <= 0)
				{
					arc.flags = 2;
				} else
				{
					arc.flags = 0;
					arc.nextArc = follow.target;
					arc.node = follow.target;
				}
				arc.output = follow.nextFinalOutput;
				arc.label = -1;
				return arc;
			} else
			{
				return null;
			}
		if (follow.target == startNode && labelToMatch < cachedRootArcs.length)
		{
			Arc result = cachedRootArcs[labelToMatch];
			if (result == null)
			{
				return result;
			} else
			{
				arc.copyFrom(result);
				return arc;
			}
		}
		if (!targetHasArcs(follow))
			return null;
		in.pos = getNodeAddress(follow.target);
		arc.node = follow.target;
		if (in.readByte() == 32)
		{
			arc.numArcs = in.readVInt();
			if (packed)
				arc.bytesPerArc = in.readVInt();
			else
				arc.bytesPerArc = in.readInt();
			arc.posArcsStart = in.pos;
			int low = 0;
			for (int high = arc.numArcs - 1; low <= high;)
			{
				int mid = low + high >>> 1;
				in.skip(arc.posArcsStart, arc.bytesPerArc * mid + 1);
				int midLabel = readLabel(in);
				int cmp = midLabel - labelToMatch;
				if (cmp < 0)
					low = mid + 1;
				else
				if (cmp > 0)
				{
					high = mid - 1;
				} else
				{
					arc.arcIdx = mid - 1;
					return readNextRealArc(arc, in);
				}
			}

			return null;
		}
		readFirstRealTargetArc(follow.target, arc, in);
		do
		{
			if (arc.label == labelToMatch)
				return arc;
			if (arc.label > labelToMatch)
				return null;
			if (arc.isLast())
				return null;
			readNextRealArc(arc, in);
		} while (true);
	}

	private void seekToNextNode(BytesReader in)
		throws IOException
	{
		int flags;
		do
		{
			flags = in.readByte();
			readLabel(in);
			if (flag(flags, 16))
				outputs.read(in);
			if (flag(flags, 32))
				outputs.read(in);
			if (!flag(flags, 8) && !flag(flags, 4))
				if (packed)
					in.readVInt();
				else
					in.readInt();
		} while (!flag(flags, 2));
	}

	public int getNodeCount()
	{
		return 1 + nodeCount;
	}

	public int getArcCount()
	{
		return arcCount;
	}

	public int getArcWithOutputCount()
	{
		return arcWithOutputCount;
	}

	public void setAllowArrayArcs(boolean v)
	{
		allowArrayArcs = v;
	}

	private boolean shouldExpand(Builder.UnCompiledNode node)
	{
		return allowArrayArcs && (node.depth <= 3 && node.numArcs >= 5 || node.numArcs >= 10);
	}

	public BytesReader getBytesReader(int pos)
	{
		if (packed)
			return new ForwardBytesReader(bytes, pos);
		else
			return new ReverseBytesReader(bytes, pos);
	}

	private FST(INPUT_TYPE inputType, org.apache.lucene.util.packed.PackedInts.Reader nodeRefToAddress, Outputs outputs)
	{
		bytesPerArc = new int[0];
		byteUpto = 0;
		startNode = -1;
		allowArrayArcs = true;
		packed = true;
		this.inputType = inputType;
		bytes = new byte[128];
		this.nodeRefToAddress = nodeRefToAddress;
		this.outputs = outputs;
		NO_OUTPUT = outputs.getNoOutput();
		writer = new BytesWriter();
	}

	public FST pack(int minInCountDeref, int maxDerefNodes, float acceptableOverheadRatio)
		throws IOException
	{
		if (nodeAddress == null)
			throw new IllegalArgumentException("this FST was not built with willPackFST=true");
		Arc arc = new Arc();
		BytesReader r = getBytesReader(0);
		int topN = Math.min(maxDerefNodes, inCounts.size());
		NodeQueue q = new NodeQueue(topN);
		NodeAndInCount bottom = null;
		for (int node = 0; node < inCounts.size(); node++)
		{
			if (inCounts.get(node) < (long)minInCountDeref)
				continue;
			if (bottom == null)
			{
				q.add(new NodeAndInCount(node, (int)inCounts.get(node)));
				if (q.size() == topN)
					bottom = (NodeAndInCount)q.top();
				continue;
			}
			if (inCounts.get(node) > (long)bottom.count)
				q.insertWithOverflow(new NodeAndInCount(node, (int)inCounts.get(node)));
		}

		inCounts = null;
		Map topNodeMap = new HashMap();
		for (int downTo = q.size() - 1; downTo >= 0; downTo--)
		{
			NodeAndInCount n = (NodeAndInCount)q.pop();
			topNodeMap.put(Integer.valueOf(n.node), Integer.valueOf(downTo));
		}

		FST fst = new FST(inputType, null, outputs);
		BytesWriter writer = fst.writer;
		GrowableWriter newNodeAddress = new GrowableWriter(PackedInts.bitsRequired(bytes.length), 1 + nodeCount, acceptableOverheadRatio);
		for (int node = 1; node <= nodeCount; node++)
			newNodeAddress.set(node, (long)(1 + bytes.length) - nodeAddress.get(node));

		boolean changed;
		boolean negDelta;
		do
		{
			changed = false;
			negDelta = false;
			writer.posWrite = 0;
			writer.writeByte((byte)0);
			fst.arcWithOutputCount = 0;
			fst.nodeCount = 0;
			fst.arcCount = 0;
			int deltaCount;
			int topCount;
			int nextCount;
			int absCount = deltaCount = topCount = nextCount = 0;
			int changedCount = 0;
			int addressError = 0;
			for (int node = nodeCount; node >= 1; node--)
			{
				fst.nodeCount++;
				int address = writer.posWrite;
				if ((long)address != newNodeAddress.get(node))
				{
					addressError = address - (int)newNodeAddress.get(node);
					changed = true;
					newNodeAddress.set(node, address);
					changedCount++;
				}
				int nodeArcCount = 0;
				int bytesPerArc = 0;
				boolean retry = false;
				boolean anyNegDelta = false;
				do
				{
					readFirstRealTargetArc(node, arc, r);
					boolean useArcArray = arc.bytesPerArc != 0;
					if (useArcArray)
					{
						if (bytesPerArc == 0)
							bytesPerArc = arc.bytesPerArc;
						writer.writeByte((byte)32);
						writer.writeVInt(arc.numArcs);
						writer.writeVInt(bytesPerArc);
					}
					int maxBytesPerArc = 0;
					do
					{
						int arcStartPos = writer.posWrite;
						nodeArcCount++;
						byte flags = 0;
						if (arc.isLast())
							flags += 2;
						if (!useArcArray && node != 1 && arc.target == node - 1)
						{
							flags += 4;
							if (!retry)
								nextCount++;
						}
						if (arc.isFinal())
						{
							flags++;
							if (arc.nextFinalOutput != NO_OUTPUT)
								flags += 32;
						} else
						if (!$assertionsDisabled && arc.nextFinalOutput != NO_OUTPUT)
							throw new AssertionError();
						if (!targetHasArcs(arc))
							flags += 8;
						if (arc.output != NO_OUTPUT)
							flags += 16;
						boolean doWriteTarget = targetHasArcs(arc) && (flags & 4) == 0;
						int absPtr;
						if (doWriteTarget)
						{
							Integer ptr = (Integer)topNodeMap.get(Integer.valueOf(arc.target));
							if (ptr != null)
								absPtr = ptr.intValue();
							else
								absPtr = topNodeMap.size() + (int)newNodeAddress.get(arc.target) + addressError;
							int delta = ((int)newNodeAddress.get(arc.target) + addressError) - writer.posWrite - 2;
							if (delta < 0)
							{
								anyNegDelta = true;
								delta = 0;
							}
							if (delta < absPtr)
								flags |= 0x40;
						} else
						{
							Integer ptr = null;
							absPtr = 0;
						}
						writer.writeByte(flags);
						fst.writeLabel(arc.label);
						if (arc.output != NO_OUTPUT)
						{
							outputs.write(arc.output, writer);
							if (!retry)
								fst.arcWithOutputCount++;
						}
						if (arc.nextFinalOutput != NO_OUTPUT)
							outputs.write(arc.nextFinalOutput, writer);
						if (doWriteTarget)
						{
							int delta = ((int)newNodeAddress.get(arc.target) + addressError) - writer.posWrite;
							if (delta < 0)
							{
								anyNegDelta = true;
								delta = 0;
							}
							if (flag(flags, 64))
							{
								writer.writeVInt(delta);
								if (!retry)
									deltaCount++;
							} else
							{
								writer.writeVInt(absPtr);
								if (!retry)
									if (absPtr >= topNodeMap.size())
										absCount++;
									else
										topCount++;
							}
						}
						if (useArcArray)
						{
							int arcBytes = writer.posWrite - arcStartPos;
							maxBytesPerArc = Math.max(maxBytesPerArc, arcBytes);
							writer.setPosWrite(arcStartPos + bytesPerArc);
						}
						if (arc.isLast())
							break;
						readNextRealArc(arc, r);
					} while (true);
					if (!useArcArray || maxBytesPerArc == bytesPerArc || retry && maxBytesPerArc <= bytesPerArc)
						break;
					bytesPerArc = maxBytesPerArc;
					writer.posWrite = address;
					nodeArcCount = 0;
					retry = true;
					anyNegDelta = false;
				} while (true);
				negDelta |= anyNegDelta;
				fst.arcCount += nodeArcCount;
			}

		} while (changed);
		if (!$assertionsDisabled && negDelta)
			throw new AssertionError();
		long maxAddress = 0L;
		for (Iterator i$ = topNodeMap.keySet().iterator(); i$.hasNext();)
		{
			int key = ((Integer)i$.next()).intValue();
			maxAddress = Math.max(maxAddress, newNodeAddress.get(key));
		}

		org.apache.lucene.util.packed.PackedInts.Mutable nodeRefToAddressIn = PackedInts.getMutable(topNodeMap.size(), PackedInts.bitsRequired(maxAddress), acceptableOverheadRatio);
		java.util.Map.Entry ent;
		for (Iterator i$ = topNodeMap.entrySet().iterator(); i$.hasNext(); nodeRefToAddressIn.set(((Integer)ent.getValue()).intValue(), newNodeAddress.get(((Integer)ent.getKey()).intValue())))
			ent = (java.util.Map.Entry)i$.next();

		fst.nodeRefToAddress = nodeRefToAddressIn;
		fst.startNode = (int)newNodeAddress.get(startNode);
		if (emptyOutput != null)
			fst.setEmptyOutput(emptyOutput);
		if (!$assertionsDisabled && fst.nodeCount != nodeCount)
			throw new AssertionError((new StringBuilder()).append("fst.nodeCount=").append(fst.nodeCount).append(" nodeCount=").append(nodeCount).toString());
		if (!$assertionsDisabled && fst.arcCount != arcCount)
			throw new AssertionError();
		if (!$assertionsDisabled && fst.arcWithOutputCount != arcWithOutputCount)
		{
			throw new AssertionError((new StringBuilder()).append("fst.arcWithOutputCount=").append(fst.arcWithOutputCount).append(" arcWithOutputCount=").append(arcWithOutputCount).toString());
		} else
		{
			byte finalBytes[] = new byte[writer.posWrite];
			System.arraycopy(fst.bytes, 0, finalBytes, 0, writer.posWrite);
			fst.bytes = finalBytes;
			fst.cacheRootArcs();
			return fst;
		}
	}


}
