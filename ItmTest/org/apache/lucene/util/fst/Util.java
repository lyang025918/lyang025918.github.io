// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Util.java

package org.apache.lucene.util.fst;

import java.io.IOException;
import java.io.Writer;
import java.util.*;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.IntsRef;

// Referenced classes of package org.apache.lucene.util.fst:
//			FST, Outputs

public final class Util
{
	public static final class MinResult
		implements Comparable
	{

		public final IntsRef input;
		public final Object output;
		final Comparator comparator;

		public int compareTo(MinResult other)
		{
			int cmp = comparator.compare(output, other.output);
			if (cmp == 0)
				return input.compareTo(other.input);
			else
				return cmp;
		}

		public volatile int compareTo(Object x0)
		{
			return compareTo((MinResult)x0);
		}

		public MinResult(IntsRef input, Object output, Comparator comparator)
		{
			this.input = input;
			this.output = output;
			this.comparator = comparator;
		}
	}

	private static class TopNSearcher
	{

		private final FST fst;
		private final FST.Arc fromNode;
		private final int topN;
		final Comparator comparator;
		FSTPath bottom;
		TreeSet queue;
		static final boolean $assertionsDisabled = !org/apache/lucene/util/fst/Util.desiredAssertionStatus();

		private void addIfCompetitive(FSTPath path)
		{
			if (!$assertionsDisabled && queue == null)
				throw new AssertionError();
			Object cost = fst.outputs.add(path.cost, path.arc.output);
			if (bottom != null)
			{
				int comp = comparator.compare(cost, bottom.cost);
				if (comp > 0)
					return;
				if (comp == 0)
				{
					path.input.grow(path.input.length + 1);
					path.input.ints[path.input.length++] = path.arc.label;
					int cmp = bottom.input.compareTo(path.input);
					path.input.length--;
					if (!$assertionsDisabled && cmp == 0)
						throw new AssertionError();
					if (cmp < 0)
						return;
				}
			}
			FSTPath newPath = new FSTPath(cost, path.arc, comparator);
			newPath.input.grow(path.input.length + 1);
			System.arraycopy(path.input.ints, 0, newPath.input.ints, 0, path.input.length);
			newPath.input.ints[path.input.length] = path.arc.label;
			newPath.input.length = path.input.length + 1;
			queue.add(newPath);
			if (bottom != null)
			{
				FSTPath removed = (FSTPath)queue.pollLast();
				if (!$assertionsDisabled && removed != bottom)
					throw new AssertionError();
				bottom = (FSTPath)queue.last();
			} else
			if (queue.size() == topN)
				bottom = (FSTPath)queue.last();
		}

		public MinResult[] search()
			throws IOException
		{
			FST.Arc scratchArc = new FST.Arc();
			List results = new ArrayList();
			FST.BytesReader fstReader = fst.getBytesReader(0);
			Object NO_OUTPUT = fst.outputs.getNoOutput();
label0:
			do
			{
label1:
				{
					if (results.size() >= topN)
						break label1;
					FSTPath path;
					if (queue == null)
					{
						if (results.size() != 0)
							break label1;
						if (topN > 1)
							queue = new TreeSet();
						Object minArcCost = null;
						FST.Arc minArc = null;
						path = new FSTPath(NO_OUTPUT, fromNode, comparator);
						fst.readFirstTargetArc(fromNode, path.arc, fstReader);
						do
						{
							Object arcScore = path.arc.output;
							if (minArcCost == null || comparator.compare(arcScore, minArcCost) < 0)
							{
								minArcCost = arcScore;
								minArc = scratchArc.copyFrom(path.arc);
							}
							if (queue != null)
								addIfCompetitive(path);
							if (path.arc.isLast())
								break;
							fst.readNextArc(path.arc, fstReader);
						} while (true);
						if (!$assertionsDisabled && minArc == null)
							throw new AssertionError();
						if (queue != null)
						{
							path = (FSTPath)queue.pollFirst();
							if (!$assertionsDisabled && path.arc.label != minArc.label)
								throw new AssertionError();
							if (bottom != null && queue.size() == topN - 1)
								bottom = (FSTPath)queue.last();
						} else
						{
							path.arc.copyFrom(minArc);
							path.input.grow(1);
							path.input.ints[0] = minArc.label;
							path.input.length = 1;
							path.cost = minArc.output;
						}
					} else
					{
						path = (FSTPath)queue.pollFirst();
						if (path == null)
							break label1;
					}
					if (path.arc.label == -1)
					{
						path.input.length--;
						results.add(new MinResult(path.input, path.cost, comparator));
						continue;
					}
					if (results.size() == topN - 1)
						queue = null;
					do
					{
						fst.readFirstTargetArc(path.arc, path.arc, fstReader);
						boolean foundZero = false;
						do
						{
							if (comparator.compare(NO_OUTPUT, path.arc.output) == 0)
							{
								if (queue == null)
								{
									foundZero = true;
									break;
								}
								if (!foundZero)
								{
									scratchArc.copyFrom(path.arc);
									foundZero = true;
								} else
								{
									addIfCompetitive(path);
								}
							} else
							if (queue != null)
								addIfCompetitive(path);
							if (path.arc.isLast())
								break;
							fst.readNextArc(path.arc, fstReader);
						} while (true);
						if (!$assertionsDisabled && !foundZero)
							throw new AssertionError();
						if (queue != null)
							path.arc.copyFrom(scratchArc);
						if (path.arc.label == -1)
						{
							results.add(new MinResult(path.input, fst.outputs.add(path.cost, path.arc.output), comparator));
							continue label0;
						}
						path.input.grow(1 + path.input.length);
						path.input.ints[path.input.length] = path.arc.label;
						path.input.length++;
						path.cost = fst.outputs.add(path.cost, path.arc.output);
					} while (true);
				}
				MinResult arr[] = (MinResult[])new MinResult[results.size()];
				return (MinResult[])results.toArray(arr);
			} while (true);
		}


		public TopNSearcher(FST fst, FST.Arc fromNode, int topN, Comparator comparator)
		{
			bottom = null;
			queue = null;
			this.fst = fst;
			this.topN = topN;
			this.fromNode = fromNode;
			this.comparator = comparator;
		}
	}

	private static class FSTPath
		implements Comparable
	{

		public FST.Arc arc;
		public Object cost;
		public final IntsRef input = new IntsRef();
		final Comparator comparator;

		public String toString()
		{
			return (new StringBuilder()).append("input=").append(input).append(" cost=").append(cost).toString();
		}

		public int compareTo(FSTPath other)
		{
			int cmp = comparator.compare(cost, other.cost);
			if (cmp == 0)
				return input.compareTo(other.input);
			else
				return cmp;
		}

		public volatile int compareTo(Object x0)
		{
			return compareTo((FSTPath)x0);
		}

		public FSTPath(Object cost, FST.Arc arc, Comparator comparator)
		{
			this.arc = (new FST.Arc()).copyFrom(arc);
			this.cost = cost;
			this.comparator = comparator;
		}
	}


	static final boolean $assertionsDisabled = !org/apache/lucene/util/fst/Util.desiredAssertionStatus();

	private Util()
	{
	}

	public static Object get(FST fst, IntsRef input)
		throws IOException
	{
		FST.Arc arc = fst.getFirstArc(new FST.Arc());
		FST.BytesReader fstReader = fst.getBytesReader(0);
		Object output = fst.outputs.getNoOutput();
		for (int i = 0; i < input.length; i++)
		{
			if (fst.findTargetArc(input.ints[input.offset + i], arc, arc, fstReader) == null)
				return null;
			output = fst.outputs.add(output, arc.output);
		}

		if (arc.isFinal())
			return fst.outputs.add(output, arc.nextFinalOutput);
		else
			return null;
	}

	public static Object get(FST fst, BytesRef input)
		throws IOException
	{
		if (!$assertionsDisabled && fst.inputType != FST.INPUT_TYPE.BYTE1)
			throw new AssertionError();
		FST.BytesReader fstReader = fst.getBytesReader(0);
		FST.Arc arc = fst.getFirstArc(new FST.Arc());
		Object output = fst.outputs.getNoOutput();
		for (int i = 0; i < input.length; i++)
		{
			if (fst.findTargetArc(input.bytes[i + input.offset] & 0xff, arc, arc, fstReader) == null)
				return null;
			output = fst.outputs.add(output, arc.output);
		}

		if (arc.isFinal())
			return fst.outputs.add(output, arc.nextFinalOutput);
		else
			return null;
	}

	public static IntsRef getByOutput(FST fst, long targetOutput)
		throws IOException
	{
		FST.BytesReader in = fst.getBytesReader(0);
		FST.Arc arc = fst.getFirstArc(new FST.Arc());
		FST.Arc scratchArc = new FST.Arc();
		IntsRef result = new IntsRef();
		long output = ((Long)arc.output).longValue();
		int upto = 0;
label0:
		do
		{
			if (arc.isFinal())
			{
				long finalOutput = output + ((Long)arc.nextFinalOutput).longValue();
				if (finalOutput == targetOutput)
				{
					result.length = upto;
					return result;
				}
				if (finalOutput > targetOutput)
					return null;
			}
			if (FST.targetHasArcs(arc))
			{
				if (result.ints.length == upto)
					result.grow(1 + upto);
				fst.readFirstRealTargetArc(arc.target, arc, in);
				if (arc.bytesPerArc != 0)
				{
					int low = 0;
					int high = arc.numArcs - 1;
					int mid = 0;
					boolean exact = false;
					do
					{
						if (low > high)
							break;
						mid = low + high >>> 1;
						in.pos = arc.posArcsStart;
						in.skip(arc.bytesPerArc * mid);
						byte flags = in.readByte();
						fst.readLabel(in);
						long minArcOutput;
						if ((flags & 0x10) != 0)
						{
							long arcOutput = ((Long)fst.outputs.read(in)).longValue();
							minArcOutput = output + arcOutput;
						} else
						{
							minArcOutput = output;
						}
						if (minArcOutput == targetOutput)
						{
							exact = true;
							break;
						}
						if (minArcOutput < targetOutput)
							low = mid + 1;
						else
							high = mid - 1;
					} while (true);
					if (high == -1)
						return null;
					if (exact)
						arc.arcIdx = mid - 1;
					else
						arc.arcIdx = low - 2;
					fst.readNextRealArc(arc, in);
					result.ints[upto++] = arc.label;
					output += ((Long)arc.output).longValue();
					continue;
				}
				FST.Arc prevArc = null;
				do
				{
					long minArcOutput = output + ((Long)arc.output).longValue();
					if (minArcOutput == targetOutput)
					{
						output = minArcOutput;
						result.ints[upto++] = arc.label;
						continue label0;
					}
					if (minArcOutput > targetOutput)
					{
						if (prevArc == null)
							return null;
						arc.copyFrom(prevArc);
						result.ints[upto++] = arc.label;
						output += ((Long)arc.output).longValue();
						continue label0;
					}
					if (arc.isLast())
					{
						output = minArcOutput;
						result.ints[upto++] = arc.label;
						continue label0;
					}
					prevArc = scratchArc;
					prevArc.copyFrom(arc);
					fst.readNextRealArc(arc, in);
				} while (true);
			}
			return null;
		} while (true);
	}

	public static MinResult[] shortestPaths(FST fst, FST.Arc fromNode, Comparator comparator, int topN)
		throws IOException
	{
		return (new TopNSearcher(fst, fromNode, topN, comparator)).search();
	}

	public static void toDot(FST fst, Writer out, boolean sameRank, boolean labelStates)
		throws IOException
	{
		String expandedNodeColor = "blue";
		FST.Arc startArc = fst.getFirstArc(new FST.Arc());
		List thisLevelQueue = new ArrayList();
		List nextLevelQueue = new ArrayList();
		nextLevelQueue.add(startArc);
		List sameLevelStates = new ArrayList();
		BitSet seen = new BitSet();
		seen.set(startArc.target);
		String stateShape = "circle";
		String finalStateShape = "doublecircle";
		out.write("digraph FST {\n");
		out.write("  rankdir = LR; splines=true; concentrate=true; ordering=out; ranksep=2.5; \n");
		if (!labelStates)
			out.write("  node [shape=circle, width=.2, height=.2, style=filled]\n");
		emitDotState(out, "initial", "point", "white", "");
		Object NO_OUTPUT = fst.outputs.getNoOutput();
		FST.BytesReader r = fst.getBytesReader(0);
		String stateColor;
		if (fst.isExpandedTarget(startArc, r))
			stateColor = "blue";
		else
			stateColor = null;
		boolean isFinal;
		Object finalOutput;
		if (startArc.isFinal())
		{
			isFinal = true;
			finalOutput = startArc.nextFinalOutput != NO_OUTPUT ? startArc.nextFinalOutput : null;
		} else
		{
			isFinal = false;
			finalOutput = null;
		}
		emitDotState(out, Integer.toString(startArc.target), isFinal ? "doublecircle" : "circle", stateColor, finalOutput != null ? fst.outputs.outputToString(finalOutput) : "");
		out.write((new StringBuilder()).append("  initial -> ").append(startArc.target).append("\n").toString());
		int level = 0;
		for (; !nextLevelQueue.isEmpty(); sameLevelStates.clear())
		{
label0:
			{
				thisLevelQueue.addAll(nextLevelQueue);
				nextLevelQueue.clear();
				level++;
				out.write((new StringBuilder()).append("\n  // Transitions and states at level: ").append(level).append("\n").toString());
				do
				{
					FST.Arc arc;
					do
					{
						if (thisLevelQueue.isEmpty())
							break label0;
						arc = (FST.Arc)thisLevelQueue.remove(thisLevelQueue.size() - 1);
					} while (!FST.targetHasArcs(arc));
					int node = arc.target;
					fst.readFirstRealTargetArc(arc.target, arc, r);
					do
					{
						if (arc.target >= 0 && !seen.get(arc.target))
						{
							String stateColor;
							if (fst.isExpandedTarget(arc, r))
								stateColor = "blue";
							else
								stateColor = null;
							String finalOutput;
							if (arc.nextFinalOutput != null && arc.nextFinalOutput != NO_OUTPUT)
								finalOutput = fst.outputs.outputToString(arc.nextFinalOutput);
							else
								finalOutput = "";
							emitDotState(out, Integer.toString(arc.target), "circle", stateColor, finalOutput);
							seen.set(arc.target);
							nextLevelQueue.add((new FST.Arc()).copyFrom(arc));
							sameLevelStates.add(Integer.valueOf(arc.target));
						}
						String outs;
						if (arc.output != NO_OUTPUT)
							outs = (new StringBuilder()).append("/").append(fst.outputs.outputToString(arc.output)).toString();
						else
							outs = "";
						if (!FST.targetHasArcs(arc) && arc.isFinal() && arc.nextFinalOutput != NO_OUTPUT)
							outs = (new StringBuilder()).append(outs).append("/[").append(fst.outputs.outputToString(arc.nextFinalOutput)).append("]").toString();
						String arcColor;
						if (arc.flag(4))
							arcColor = "red";
						else
							arcColor = "black";
						if (!$assertionsDisabled && arc.label == -1)
							throw new AssertionError();
						out.write((new StringBuilder()).append("  ").append(node).append(" -> ").append(arc.target).append(" [label=\"").append(printableLabel(arc.label)).append(outs).append("\"").append(arc.isFinal() ? " style=\"bold\"" : "").append(" color=\"").append(arcColor).append("\"]\n").toString());
						if (arc.isLast())
							break;
						fst.readNextRealArc(arc, r);
					} while (true);
				} while (true);
			}
			if (!sameRank || sameLevelStates.size() <= 1)
				continue;
			out.write("  {rank=same; ");
			int state;
			for (Iterator i$ = sameLevelStates.iterator(); i$.hasNext(); out.write((new StringBuilder()).append(state).append("; ").toString()))
				state = ((Integer)i$.next()).intValue();

			out.write(" }\n");
		}

		out.write("  -1 [style=filled, color=black, shape=doublecircle, label=\"\"]\n\n");
		out.write("  {rank=sink; -1 }\n");
		out.write("}\n");
		out.flush();
	}

	private static void emitDotState(Writer out, String name, String shape, String color, String label)
		throws IOException
	{
		out.write((new StringBuilder()).append("  ").append(name).append(" [").append(shape == null ? "" : (new StringBuilder()).append("shape=").append(shape).toString()).append(" ").append(color == null ? "" : (new StringBuilder()).append("color=").append(color).toString()).append(" ").append(label == null ? "label=\"\"" : (new StringBuilder()).append("label=\"").append(label).append("\"").toString()).append(" ").append("]\n").toString());
	}

	private static String printableLabel(int label)
	{
		if (label >= 32 && label <= 125)
			return Character.toString((char)label);
		else
			return (new StringBuilder()).append("0x").append(Integer.toHexString(label)).toString();
	}

	public static IntsRef toUTF16(CharSequence s, IntsRef scratch)
	{
		int charLimit = s.length();
		scratch.offset = 0;
		scratch.length = charLimit;
		scratch.grow(charLimit);
		for (int idx = 0; idx < charLimit; idx++)
			scratch.ints[idx] = s.charAt(idx);

		return scratch;
	}

	public static IntsRef toUTF32(CharSequence s, IntsRef scratch)
	{
		int charIdx = 0;
		int intIdx = 0;
		for (int charLimit = s.length(); charIdx < charLimit;)
		{
			scratch.grow(intIdx + 1);
			int utf32 = Character.codePointAt(s, charIdx);
			scratch.ints[intIdx] = utf32;
			charIdx += Character.charCount(utf32);
			intIdx++;
		}

		scratch.length = intIdx;
		return scratch;
	}

	public static IntsRef toUTF32(char s[], int offset, int length, IntsRef scratch)
	{
		int charIdx = offset;
		int intIdx = 0;
		for (int charLimit = offset + length; charIdx < charLimit;)
		{
			scratch.grow(intIdx + 1);
			int utf32 = Character.codePointAt(s, charIdx);
			scratch.ints[intIdx] = utf32;
			charIdx += Character.charCount(utf32);
			intIdx++;
		}

		scratch.length = intIdx;
		return scratch;
	}

	public static IntsRef toIntsRef(BytesRef input, IntsRef scratch)
	{
		scratch.grow(input.length);
		for (int i = 0; i < input.length; i++)
			scratch.ints[i] = input.bytes[i + input.offset] & 0xff;

		scratch.length = input.length;
		return scratch;
	}

	public static BytesRef toBytesRef(IntsRef input, BytesRef scratch)
	{
		scratch.grow(input.length);
		for (int i = 0; i < input.length; i++)
			scratch.bytes[i] = (byte)input.ints[i + input.offset];

		scratch.length = input.length;
		return scratch;
	}

}
