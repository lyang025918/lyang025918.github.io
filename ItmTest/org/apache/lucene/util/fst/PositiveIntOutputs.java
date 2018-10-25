// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PositiveIntOutputs.java

package org.apache.lucene.util.fst;

import java.io.IOException;
import org.apache.lucene.store.DataInput;
import org.apache.lucene.store.DataOutput;

// Referenced classes of package org.apache.lucene.util.fst:
//			Outputs

public final class PositiveIntOutputs extends Outputs
{

	private static final Long NO_OUTPUT = new Long(0L);
	private final boolean doShare;
	private static final PositiveIntOutputs singletonShare = new PositiveIntOutputs(true);
	private static final PositiveIntOutputs singletonNoShare = new PositiveIntOutputs(false);
	static final boolean $assertionsDisabled = !org/apache/lucene/util/fst/PositiveIntOutputs.desiredAssertionStatus();

	private PositiveIntOutputs(boolean doShare)
	{
		this.doShare = doShare;
	}

	public static PositiveIntOutputs getSingleton()
	{
		return getSingleton(true);
	}

	public static PositiveIntOutputs getSingleton(boolean doShare)
	{
		return doShare ? singletonShare : singletonNoShare;
	}

	public Long common(Long output1, Long output2)
	{
		if (!$assertionsDisabled && !valid(output1))
			throw new AssertionError();
		if (!$assertionsDisabled && !valid(output2))
			throw new AssertionError();
		if (output1 == NO_OUTPUT || output2 == NO_OUTPUT)
			return NO_OUTPUT;
		if (doShare)
		{
			if (!$assertionsDisabled && output1.longValue() <= 0L)
				throw new AssertionError();
			if (!$assertionsDisabled && output2.longValue() <= 0L)
				throw new AssertionError();
			else
				return Long.valueOf(Math.min(output1.longValue(), output2.longValue()));
		}
		if (output1.equals(output2))
			return output1;
		else
			return NO_OUTPUT;
	}

	public Long subtract(Long output, Long inc)
	{
		if (!$assertionsDisabled && !valid(output))
			throw new AssertionError();
		if (!$assertionsDisabled && !valid(inc))
			throw new AssertionError();
		if (!$assertionsDisabled && output.longValue() < inc.longValue())
			throw new AssertionError();
		if (inc == NO_OUTPUT)
			return output;
		if (output.equals(inc))
			return NO_OUTPUT;
		else
			return Long.valueOf(output.longValue() - inc.longValue());
	}

	public Long add(Long prefix, Long output)
	{
		if (!$assertionsDisabled && !valid(prefix))
			throw new AssertionError();
		if (!$assertionsDisabled && !valid(output))
			throw new AssertionError();
		if (prefix == NO_OUTPUT)
			return output;
		if (output == NO_OUTPUT)
			return prefix;
		else
			return Long.valueOf(prefix.longValue() + output.longValue());
	}

	public void write(Long output, DataOutput out)
		throws IOException
	{
		if (!$assertionsDisabled && !valid(output))
		{
			throw new AssertionError();
		} else
		{
			out.writeVLong(output.longValue());
			return;
		}
	}

	public Long read(DataInput in)
		throws IOException
	{
		long v = in.readVLong();
		if (v == 0L)
			return NO_OUTPUT;
		else
			return Long.valueOf(v);
	}

	private boolean valid(Long o)
	{
		if (!$assertionsDisabled && o == null)
			throw new AssertionError();
		if (!$assertionsDisabled && o != NO_OUTPUT && o.longValue() <= 0L)
			throw new AssertionError();
		else
			return true;
	}

	public Long getNoOutput()
	{
		return NO_OUTPUT;
	}

	public String outputToString(Long output)
	{
		return output.toString();
	}

	public String toString()
	{
		return (new StringBuilder()).append("PositiveIntOutputs(doShare=").append(doShare).append(")").toString();
	}

	public volatile String outputToString(Object x0)
	{
		return outputToString((Long)x0);
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
		write((Long)x0, x1);
	}

	public volatile Object add(Object x0, Object x1)
	{
		return add((Long)x0, (Long)x1);
	}

	public volatile Object subtract(Object x0, Object x1)
	{
		return subtract((Long)x0, (Long)x1);
	}

	public volatile Object common(Object x0, Object x1)
	{
		return common((Long)x0, (Long)x1);
	}

}
