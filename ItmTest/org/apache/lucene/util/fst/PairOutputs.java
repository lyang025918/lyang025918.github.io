// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PairOutputs.java

package org.apache.lucene.util.fst;

import java.io.IOException;
import org.apache.lucene.store.DataInput;
import org.apache.lucene.store.DataOutput;

// Referenced classes of package org.apache.lucene.util.fst:
//			Outputs

public class PairOutputs extends Outputs
{
	public static class Pair
	{

		public final Object output1;
		public final Object output2;

		public boolean equals(Object other)
		{
			if (other == this)
				return true;
			if (other instanceof Pair)
			{
				Pair pair = (Pair)other;
				return output1.equals(pair.output1) && output2.equals(pair.output2);
			} else
			{
				return false;
			}
		}

		public int hashCode()
		{
			return output1.hashCode() + output2.hashCode();
		}

		private Pair(Object output1, Object output2)
		{
			this.output1 = output1;
			this.output2 = output2;
		}

	}


	private final Pair NO_OUTPUT;
	private final Outputs outputs1;
	private final Outputs outputs2;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/fst/PairOutputs.desiredAssertionStatus();

	public PairOutputs(Outputs outputs1, Outputs outputs2)
	{
		this.outputs1 = outputs1;
		this.outputs2 = outputs2;
		NO_OUTPUT = new Pair(outputs1.getNoOutput(), outputs2.getNoOutput());
	}

	public Pair newPair(Object a, Object b)
	{
		if (a.equals(outputs1.getNoOutput()))
			a = outputs1.getNoOutput();
		if (b.equals(outputs2.getNoOutput()))
			b = outputs2.getNoOutput();
		if (a == outputs1.getNoOutput() && b == outputs2.getNoOutput())
			return NO_OUTPUT;
		Pair p = new Pair(a, b);
		if (!$assertionsDisabled && !valid(p))
			throw new AssertionError();
		else
			return p;
	}

	private boolean valid(Pair pair)
	{
		boolean noOutput1 = pair.output1.equals(outputs1.getNoOutput());
		boolean noOutput2 = pair.output2.equals(outputs2.getNoOutput());
		if (noOutput1 && pair.output1 != outputs1.getNoOutput())
			return false;
		if (noOutput2 && pair.output2 != outputs2.getNoOutput())
			return false;
		if (noOutput1 && noOutput2)
			return pair == NO_OUTPUT;
		else
			return true;
	}

	public Pair common(Pair pair1, Pair pair2)
	{
		if (!$assertionsDisabled && !valid(pair1))
			throw new AssertionError();
		if (!$assertionsDisabled && !valid(pair2))
			throw new AssertionError();
		else
			return newPair(outputs1.common(pair1.output1, pair2.output1), outputs2.common(pair1.output2, pair2.output2));
	}

	public Pair subtract(Pair output, Pair inc)
	{
		if (!$assertionsDisabled && !valid(output))
			throw new AssertionError();
		if (!$assertionsDisabled && !valid(inc))
			throw new AssertionError();
		else
			return newPair(outputs1.subtract(output.output1, inc.output1), outputs2.subtract(output.output2, inc.output2));
	}

	public Pair add(Pair prefix, Pair output)
	{
		if (!$assertionsDisabled && !valid(prefix))
			throw new AssertionError();
		if (!$assertionsDisabled && !valid(output))
			throw new AssertionError();
		else
			return newPair(outputs1.add(prefix.output1, output.output1), outputs2.add(prefix.output2, output.output2));
	}

	public void write(Pair output, DataOutput writer)
		throws IOException
	{
		if (!$assertionsDisabled && !valid(output))
		{
			throw new AssertionError();
		} else
		{
			outputs1.write(output.output1, writer);
			outputs2.write(output.output2, writer);
			return;
		}
	}

	public Pair read(DataInput in)
		throws IOException
	{
		Object output1 = outputs1.read(in);
		Object output2 = outputs2.read(in);
		return newPair(output1, output2);
	}

	public Pair getNoOutput()
	{
		return NO_OUTPUT;
	}

	public String outputToString(Pair output)
	{
		if (!$assertionsDisabled && !valid(output))
			throw new AssertionError();
		else
			return (new StringBuilder()).append("<pair:").append(outputs1.outputToString(output.output1)).append(",").append(outputs2.outputToString(output.output2)).append(">").toString();
	}

	public String toString()
	{
		return (new StringBuilder()).append("PairOutputs<").append(outputs1).append(",").append(outputs2).append(">").toString();
	}

	public volatile String outputToString(Object x0)
	{
		return outputToString((Pair)x0);
	}

	public volatile Object getNoOutput()
	{
		return getNoOutput();
	}

	public volatile Object read(DataInput x0)
		throws IOException
	{
		return read(x0);
	}

	public volatile void write(Object x0, DataOutput x1)
		throws IOException
	{
		write((Pair)x0, x1);
	}

	public volatile Object add(Object x0, Object x1)
	{
		return add((Pair)x0, (Pair)x1);
	}

	public volatile Object subtract(Object x0, Object x1)
	{
		return subtract((Pair)x0, (Pair)x1);
	}

	public volatile Object common(Object x0, Object x1)
	{
		return common((Pair)x0, (Pair)x1);
	}

}
