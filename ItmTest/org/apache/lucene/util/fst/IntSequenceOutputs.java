// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IntSequenceOutputs.java

package org.apache.lucene.util.fst;

import java.io.IOException;
import org.apache.lucene.store.DataInput;
import org.apache.lucene.store.DataOutput;
import org.apache.lucene.util.IntsRef;

// Referenced classes of package org.apache.lucene.util.fst:
//			Outputs

public final class IntSequenceOutputs extends Outputs
{

	private static final IntsRef NO_OUTPUT = new IntsRef();
	private static final IntSequenceOutputs singleton = new IntSequenceOutputs();
	static final boolean $assertionsDisabled = !org/apache/lucene/util/fst/IntSequenceOutputs.desiredAssertionStatus();

	private IntSequenceOutputs()
	{
	}

	public static IntSequenceOutputs getSingleton()
	{
		return singleton;
	}

	public IntsRef common(IntsRef output1, IntsRef output2)
	{
		if (!$assertionsDisabled && output1 == null)
			throw new AssertionError();
		if (!$assertionsDisabled && output2 == null)
			throw new AssertionError();
		int pos1 = output1.offset;
		int pos2 = output2.offset;
		for (int stopAt1 = pos1 + Math.min(output1.length, output2.length); pos1 < stopAt1 && output1.ints[pos1] == output2.ints[pos2]; pos2++)
			pos1++;

		if (pos1 == output1.offset)
			return NO_OUTPUT;
		if (pos1 == output1.offset + output1.length)
			return output1;
		if (pos2 == output2.offset + output2.length)
			return output2;
		else
			return new IntsRef(output1.ints, output1.offset, pos1 - output1.offset);
	}

	public IntsRef subtract(IntsRef output, IntsRef inc)
	{
		if (!$assertionsDisabled && output == null)
			throw new AssertionError();
		if (!$assertionsDisabled && inc == null)
			throw new AssertionError();
		if (inc == NO_OUTPUT)
			return output;
		if (inc.length == output.length)
			return NO_OUTPUT;
		if (!$assertionsDisabled && inc.length >= output.length)
			throw new AssertionError((new StringBuilder()).append("inc.length=").append(inc.length).append(" vs output.length=").append(output.length).toString());
		if (!$assertionsDisabled && inc.length <= 0)
			throw new AssertionError();
		else
			return new IntsRef(output.ints, output.offset + inc.length, output.length - inc.length);
	}

	public IntsRef add(IntsRef prefix, IntsRef output)
	{
		if (!$assertionsDisabled && prefix == null)
			throw new AssertionError();
		if (!$assertionsDisabled && output == null)
			throw new AssertionError();
		if (prefix == NO_OUTPUT)
			return output;
		if (output == NO_OUTPUT)
			return prefix;
		if (!$assertionsDisabled && prefix.length <= 0)
			throw new AssertionError();
		if (!$assertionsDisabled && output.length <= 0)
		{
			throw new AssertionError();
		} else
		{
			IntsRef result = new IntsRef(prefix.length + output.length);
			System.arraycopy(prefix.ints, prefix.offset, result.ints, 0, prefix.length);
			System.arraycopy(output.ints, output.offset, result.ints, prefix.length, output.length);
			result.length = prefix.length + output.length;
			return result;
		}
	}

	public void write(IntsRef prefix, DataOutput out)
		throws IOException
	{
		if (!$assertionsDisabled && prefix == null)
			throw new AssertionError();
		out.writeVInt(prefix.length);
		for (int idx = 0; idx < prefix.length; idx++)
			out.writeVInt(prefix.ints[prefix.offset + idx]);

	}

	public IntsRef read(DataInput in)
		throws IOException
	{
		int len = in.readVInt();
		if (len == 0)
			return NO_OUTPUT;
		IntsRef output = new IntsRef(len);
		for (int idx = 0; idx < len; idx++)
			output.ints[idx] = in.readVInt();

		output.length = len;
		return output;
	}

	public IntsRef getNoOutput()
	{
		return NO_OUTPUT;
	}

	public String outputToString(IntsRef output)
	{
		return output.toString();
	}

	public volatile String outputToString(Object x0)
	{
		return outputToString((IntsRef)x0);
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
		write((IntsRef)x0, x1);
	}

	public volatile Object add(Object x0, Object x1)
	{
		return add((IntsRef)x0, (IntsRef)x1);
	}

	public volatile Object subtract(Object x0, Object x1)
	{
		return subtract((IntsRef)x0, (IntsRef)x1);
	}

	public volatile Object common(Object x0, Object x1)
	{
		return common((IntsRef)x0, (IntsRef)x1);
	}

}
