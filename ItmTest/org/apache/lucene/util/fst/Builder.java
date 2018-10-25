// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Builder.java

package org.apache.lucene.util.fst;

import java.io.IOException;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.util.fst:
//			FST, NodeHash, Outputs

public class Builder
{
	public static final class UnCompiledNode
		implements Node
	{

		final Builder owner;
		public int numArcs;
		public Arc arcs[];
		public Object output;
		public boolean isFinal;
		public long inputCount;
		public final int depth;
		static final boolean $assertionsDisabled = !org/apache/lucene/util/fst/Builder.desiredAssertionStatus();

		public boolean isCompiled()
		{
			return false;
		}

		public void clear()
		{
			numArcs = 0;
			isFinal = false;
			output = owner.NO_OUTPUT;
			inputCount = 0L;
		}

		public Object getLastOutput(int labelToMatch)
		{
			if (!$assertionsDisabled && numArcs <= 0)
				throw new AssertionError();
			if (!$assertionsDisabled && arcs[numArcs - 1].label != labelToMatch)
				throw new AssertionError();
			else
				return arcs[numArcs - 1].output;
		}

		public void addArc(int label, Node target)
		{
			if (!$assertionsDisabled && label < 0)
				throw new AssertionError();
			if (!$assertionsDisabled && numArcs != 0 && label <= arcs[numArcs - 1].label)
				throw new AssertionError((new StringBuilder()).append("arc[-1].label=").append(arcs[numArcs - 1].label).append(" new label=").append(label).append(" numArcs=").append(numArcs).toString());
			if (numArcs == arcs.length)
			{
				Arc newArcs[] = new Arc[ArrayUtil.oversize(numArcs + 1, RamUsageEstimator.NUM_BYTES_OBJECT_REF)];
				System.arraycopy(arcs, 0, newArcs, 0, arcs.length);
				for (int arcIdx = numArcs; arcIdx < newArcs.length; arcIdx++)
					newArcs[arcIdx] = new Arc();

				arcs = newArcs;
			}
			Arc arc = arcs[numArcs++];
			arc.label = label;
			arc.target = target;
			arc.output = arc.nextFinalOutput = owner.NO_OUTPUT;
			arc.isFinal = false;
		}

		public void replaceLast(int labelToMatch, Node target, Object nextFinalOutput, boolean isFinal)
		{
			if (!$assertionsDisabled && numArcs <= 0)
				throw new AssertionError();
			Arc arc = arcs[numArcs - 1];
			if (!$assertionsDisabled && arc.label != labelToMatch)
			{
				throw new AssertionError((new StringBuilder()).append("arc.label=").append(arc.label).append(" vs ").append(labelToMatch).toString());
			} else
			{
				arc.target = target;
				arc.nextFinalOutput = nextFinalOutput;
				arc.isFinal = isFinal;
				return;
			}
		}

		public void deleteLast(int label, Node target)
		{
			if (!$assertionsDisabled && numArcs <= 0)
				throw new AssertionError();
			if (!$assertionsDisabled && label != arcs[numArcs - 1].label)
				throw new AssertionError();
			if (!$assertionsDisabled && target != arcs[numArcs - 1].target)
			{
				throw new AssertionError();
			} else
			{
				numArcs--;
				return;
			}
		}

		public void setLastOutput(int labelToMatch, Object newOutput)
		{
			if (!$assertionsDisabled && !owner.validOutput(newOutput))
				throw new AssertionError();
			if (!$assertionsDisabled && numArcs <= 0)
				throw new AssertionError();
			Arc arc = arcs[numArcs - 1];
			if (!$assertionsDisabled && arc.label != labelToMatch)
			{
				throw new AssertionError();
			} else
			{
				arc.output = newOutput;
				return;
			}
		}

		public void prependOutput(Object outputPrefix)
		{
			if (!$assertionsDisabled && !owner.validOutput(outputPrefix))
				throw new AssertionError();
			for (int arcIdx = 0; arcIdx < numArcs; arcIdx++)
			{
				arcs[arcIdx].output = owner.fst.outputs.add(outputPrefix, arcs[arcIdx].output);
				if (!$assertionsDisabled && !owner.validOutput(arcs[arcIdx].output))
					throw new AssertionError();
			}

			if (isFinal)
			{
				output = owner.fst.outputs.add(outputPrefix, output);
				if (!$assertionsDisabled && !owner.validOutput(output))
					throw new AssertionError();
			}
		}


		public UnCompiledNode(Builder owner, int depth)
		{
			this.owner = owner;
			arcs = (Arc[])new Arc[1];
			arcs[0] = new Arc();
			output = owner.NO_OUTPUT;
			this.depth = depth;
		}
	}

	static final class CompiledNode
		implements Node
	{

		int node;

		public boolean isCompiled()
		{
			return true;
		}

		CompiledNode()
		{
		}
	}

	static interface Node
	{

		public abstract boolean isCompiled();
	}

	public static class Arc
	{

		public int label;
		public Node target;
		public boolean isFinal;
		public Object output;
		public Object nextFinalOutput;

		public Arc()
		{
		}
	}

	public static abstract class FreezeTail
	{

		public abstract void freeze(UnCompiledNode auncompilednode[], int i, IntsRef intsref)
			throws IOException;

		public FreezeTail()
		{
		}
	}


	private final NodeHash dedupHash;
	private final FST fst;
	private final Object NO_OUTPUT;
	private final int minSuffixCount1;
	private final int minSuffixCount2;
	private final boolean doShareNonSingletonNodes;
	private final int shareMaxTailLength;
	private final IntsRef lastInput;
	private UnCompiledNode frontier[];
	private final FreezeTail freezeTail;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/fst/Builder.desiredAssertionStatus();

	public Builder(FST.INPUT_TYPE inputType, Outputs outputs)
	{
		this(inputType, 0, 0, true, true, 0x7fffffff, outputs, null, false, 0.0F);
	}

	public Builder(FST.INPUT_TYPE inputType, int minSuffixCount1, int minSuffixCount2, boolean doShareSuffix, boolean doShareNonSingletonNodes, int shareMaxTailLength, Outputs outputs, 
			FreezeTail freezeTail, boolean willPackFST)
	{
		this(inputType, minSuffixCount1, minSuffixCount2, doShareSuffix, doShareNonSingletonNodes, shareMaxTailLength, outputs, freezeTail, willPackFST, 0.2F);
	}

	public Builder(FST.INPUT_TYPE inputType, int minSuffixCount1, int minSuffixCount2, boolean doShareSuffix, boolean doShareNonSingletonNodes, int shareMaxTailLength, Outputs outputs, 
			FreezeTail freezeTail, boolean willPackFST, float acceptableOverheadRatio)
	{
		lastInput = new IntsRef();
		this.minSuffixCount1 = minSuffixCount1;
		this.minSuffixCount2 = minSuffixCount2;
		this.freezeTail = freezeTail;
		this.doShareNonSingletonNodes = doShareNonSingletonNodes;
		this.shareMaxTailLength = shareMaxTailLength;
		fst = new FST(inputType, outputs, willPackFST, acceptableOverheadRatio);
		if (doShareSuffix)
			dedupHash = new NodeHash(fst);
		else
			dedupHash = null;
		NO_OUTPUT = outputs.getNoOutput();
		UnCompiledNode f[] = (UnCompiledNode[])new UnCompiledNode[10];
		frontier = f;
		for (int idx = 0; idx < frontier.length; idx++)
			frontier[idx] = new UnCompiledNode(this, idx);

	}

	public int getTotStateCount()
	{
		return fst.nodeCount;
	}

	public long getTermCount()
	{
		return frontier[0].inputCount;
	}

	public int getMappedStateCount()
	{
		return dedupHash != null ? fst.nodeCount : 0;
	}

	public void setAllowArrayArcs(boolean b)
	{
		fst.setAllowArrayArcs(b);
	}

	private CompiledNode compileNode(UnCompiledNode nodeIn, int tailLength)
		throws IOException
	{
		int node;
		if (dedupHash != null && (doShareNonSingletonNodes || nodeIn.numArcs <= 1) && tailLength <= shareMaxTailLength)
		{
			if (nodeIn.numArcs == 0)
				node = fst.addNode(nodeIn);
			else
				node = dedupHash.add(nodeIn);
		} else
		{
			node = fst.addNode(nodeIn);
		}
		if (!$assertionsDisabled && node == -2)
		{
			throw new AssertionError();
		} else
		{
			nodeIn.clear();
			CompiledNode fn = new CompiledNode();
			fn.node = node;
			return fn;
		}
	}

	private void freezeTail(int prefixLenPlus1)
		throws IOException
	{
		if (freezeTail != null)
		{
			freezeTail.freeze(frontier, prefixLenPlus1, lastInput);
		} else
		{
			int downTo = Math.max(1, prefixLenPlus1);
			for (int idx = lastInput.length; idx >= downTo; idx--)
			{
				boolean doPrune = false;
				boolean doCompile = false;
				UnCompiledNode node = frontier[idx];
				UnCompiledNode parent = frontier[idx - 1];
				if (node.inputCount < (long)minSuffixCount1)
				{
					doPrune = true;
					doCompile = true;
				} else
				if (idx > prefixLenPlus1)
				{
					if (parent.inputCount < (long)minSuffixCount2 || minSuffixCount2 == 1 && parent.inputCount == 1L && idx > 1)
						doPrune = true;
					else
						doPrune = false;
					doCompile = true;
				} else
				{
					doCompile = minSuffixCount2 == 0;
				}
				if (node.inputCount < (long)minSuffixCount2 || minSuffixCount2 == 1 && node.inputCount == 1L && idx > 1)
				{
					for (int arcIdx = 0; arcIdx < node.numArcs; arcIdx++)
					{
						UnCompiledNode target = (UnCompiledNode)node.arcs[arcIdx].target;
						target.clear();
					}

					node.numArcs = 0;
				}
				if (doPrune)
				{
					node.clear();
					parent.deleteLast(lastInput.ints[(lastInput.offset + idx) - 1], node);
					continue;
				}
				if (minSuffixCount2 != 0)
					compileAllTargets(node, lastInput.length - idx);
				Object nextFinalOutput = node.output;
				boolean isFinal = node.isFinal || node.numArcs == 0;
				if (doCompile)
				{
					parent.replaceLast(lastInput.ints[(lastInput.offset + idx) - 1], compileNode(node, (1 + lastInput.length) - idx), nextFinalOutput, isFinal);
				} else
				{
					parent.replaceLast(lastInput.ints[(lastInput.offset + idx) - 1], node, nextFinalOutput, isFinal);
					frontier[idx] = new UnCompiledNode(this, idx);
				}
			}

		}
	}

	public void add(IntsRef input, Object output)
		throws IOException
	{
		if (output.equals(NO_OUTPUT))
			output = NO_OUTPUT;
		if (!$assertionsDisabled && lastInput.length != 0 && input.compareTo(lastInput) < 0)
			throw new AssertionError((new StringBuilder()).append("inputs are added out of order lastInput=").append(lastInput).append(" vs input=").append(input).toString());
		if (!$assertionsDisabled && !validOutput(output))
			throw new AssertionError();
		if (input.length == 0)
		{
			frontier[0].inputCount++;
			frontier[0].isFinal = true;
			fst.setEmptyOutput(output);
			return;
		}
		int pos1 = 0;
		int pos2 = input.offset;
		int pos1Stop = Math.min(lastInput.length, input.length);
		do
		{
			frontier[pos1].inputCount++;
			if (pos1 >= pos1Stop || lastInput.ints[pos1] != input.ints[pos2])
				break;
			pos1++;
			pos2++;
		} while (true);
		int prefixLenPlus1 = pos1 + 1;
		if (frontier.length < input.length + 1)
		{
			UnCompiledNode next[] = new UnCompiledNode[ArrayUtil.oversize(input.length + 1, RamUsageEstimator.NUM_BYTES_OBJECT_REF)];
			System.arraycopy(frontier, 0, next, 0, frontier.length);
			for (int idx = frontier.length; idx < next.length; idx++)
				next[idx] = new UnCompiledNode(this, idx);

			frontier = next;
		}
		freezeTail(prefixLenPlus1);
		for (int idx = prefixLenPlus1; idx <= input.length; idx++)
		{
			frontier[idx - 1].addArc(input.ints[(input.offset + idx) - 1], frontier[idx]);
			frontier[idx].inputCount++;
		}

		UnCompiledNode lastNode = frontier[input.length];
		lastNode.isFinal = true;
		lastNode.output = NO_OUTPUT;
		for (int idx = 1; idx < prefixLenPlus1; idx++)
		{
			UnCompiledNode node = frontier[idx];
			UnCompiledNode parentNode = frontier[idx - 1];
			Object lastOutput = parentNode.getLastOutput(input.ints[(input.offset + idx) - 1]);
			if (!$assertionsDisabled && !validOutput(lastOutput))
				throw new AssertionError();
			Object commonOutputPrefix;
			Object wordSuffix;
			if (lastOutput != NO_OUTPUT)
			{
				commonOutputPrefix = fst.outputs.common(output, lastOutput);
				if (!$assertionsDisabled && !validOutput(commonOutputPrefix))
					throw new AssertionError();
				wordSuffix = fst.outputs.subtract(lastOutput, commonOutputPrefix);
				if (!$assertionsDisabled && !validOutput(wordSuffix))
					throw new AssertionError();
				parentNode.setLastOutput(input.ints[(input.offset + idx) - 1], commonOutputPrefix);
				node.prependOutput(wordSuffix);
			} else
			{
				commonOutputPrefix = wordSuffix = NO_OUTPUT;
			}
			output = fst.outputs.subtract(output, commonOutputPrefix);
			if (!$assertionsDisabled && !validOutput(output))
				throw new AssertionError();
		}

		if (lastInput.length == input.length && prefixLenPlus1 == 1 + input.length)
			lastNode.output = fst.outputs.merge(lastNode.output, output);
		else
			frontier[prefixLenPlus1 - 1].setLastOutput(input.ints[(input.offset + prefixLenPlus1) - 1], output);
		lastInput.copyInts(input);
	}

	private boolean validOutput(Object output)
	{
		return output == NO_OUTPUT || !output.equals(NO_OUTPUT);
	}

	public FST finish()
		throws IOException
	{
		UnCompiledNode root = frontier[0];
		freezeTail(0);
		if (root.inputCount < (long)minSuffixCount1 || root.inputCount < (long)minSuffixCount2 || root.numArcs == 0)
		{
			if (fst.emptyOutput == null)
				return null;
			if (minSuffixCount1 > 0 || minSuffixCount2 > 0)
				return null;
		} else
		if (minSuffixCount2 != 0)
			compileAllTargets(root, lastInput.length);
		fst.finish(compileNode(root, lastInput.length).node);
		return fst;
	}

	private void compileAllTargets(UnCompiledNode node, int tailLength)
		throws IOException
	{
		for (int arcIdx = 0; arcIdx < node.numArcs; arcIdx++)
		{
			Arc arc = node.arcs[arcIdx];
			if (arc.target.isCompiled())
				continue;
			UnCompiledNode n = (UnCompiledNode)arc.target;
			if (n.numArcs == 0)
				arc.isFinal = n.isFinal = true;
			arc.target = compileNode(n, tailLength - 1);
		}

	}




}
