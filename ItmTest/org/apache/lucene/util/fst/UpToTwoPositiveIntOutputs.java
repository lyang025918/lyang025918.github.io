// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UpToTwoPositiveIntOutputs.java

package org.apache.lucene.util.fst;

import java.io.IOException;
import org.apache.lucene.store.DataInput;
import org.apache.lucene.store.DataOutput;

// Referenced classes of package org.apache.lucene.util.fst:
//			Outputs

public final class UpToTwoPositiveIntOutputs extends Outputs
{
	public static final class TwoLongs
	{

		public final long first;
		public final long second;
		static final boolean $assertionsDisabled = !org/apache/lucene/util/fst/UpToTwoPositiveIntOutputs.desiredAssertionStatus();

		public String toString()
		{
			return (new StringBuilder()).append("TwoLongs:").append(first).append(",").append(second).toString();
		}

		public boolean equals(Object _other)
		{
			if (_other instanceof TwoLongs)
			{
				TwoLongs other = (TwoLongs)_other;
				return first == other.first && second == other.second;
			} else
			{
				return false;
			}
		}

		public int hashCode()
		{
			return (int)(first ^ first >>> 32 ^ (second ^ second >> 32));
		}


		public TwoLongs(long first, long second)
		{
			this.first = first;
			this.second = second;
			if (!$assertionsDisabled && first < 0L)
				throw new AssertionError();
			if (!$assertionsDisabled && second < 0L)
				throw new AssertionError();
			else
				return;
		}
	}


	private static final Long NO_OUTPUT = new Long(0L);
	private final boolean doShare;
	private static final UpToTwoPositiveIntOutputs singletonShare = new UpToTwoPositiveIntOutputs(true);
	private static final UpToTwoPositiveIntOutputs singletonNoShare = new UpToTwoPositiveIntOutputs(false);
	static final boolean $assertionsDisabled = !org/apache/lucene/util/fst/UpToTwoPositiveIntOutputs.desiredAssertionStatus();

	private UpToTwoPositiveIntOutputs(boolean doShare)
	{
		this.doShare = doShare;
	}

	public static UpToTwoPositiveIntOutputs getSingleton(boolean doShare)
	{
		return doShare ? singletonShare : singletonNoShare;
	}

	public Long get(long v)
	{
		if (v == 0L)
			return NO_OUTPUT;
		else
			return Long.valueOf(v);
	}

	public TwoLongs get(long first, long second)
	{
		return new TwoLongs(first, second);
	}

	public Long common(Object _output1, Object _output2)
	{
		if (!$assertionsDisabled && !valid(_output1, false))
			throw new AssertionError();
		if (!$assertionsDisabled && !valid(_output2, false))
			throw new AssertionError();
		Long output1 = (Long)_output1;
		Long output2 = (Long)_output2;
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

	public Long subtract(Object _output, Object _inc)
	{
		if (!$assertionsDisabled && !valid(_output, false))
			throw new AssertionError();
		if (!$assertionsDisabled && !valid(_inc, false))
			throw new AssertionError();
		Long output = (Long)_output;
		Long inc = (Long)_inc;
		if (!$assertionsDisabled && output.longValue() < inc.longValue())
			throw new AssertionError();
		if (inc == NO_OUTPUT)
			return output;
		if (output.equals(inc))
			return NO_OUTPUT;
		else
			return Long.valueOf(output.longValue() - inc.longValue());
	}

	public Object add(Object _prefix, Object _output)
	{
		if (!$assertionsDisabled && !valid(_prefix, false))
			throw new AssertionError();
		if (!$assertionsDisabled && !valid(_output, true))
			throw new AssertionError();
		Long prefix = (Long)_prefix;
		if (_output instanceof Long)
		{
			Long output = (Long)_output;
			if (prefix == NO_OUTPUT)
				return output;
			if (output == NO_OUTPUT)
				return prefix;
			else
				return Long.valueOf(prefix.longValue() + output.longValue());
		} else
		{
			TwoLongs output = (TwoLongs)_output;
			long v = prefix.longValue();
			return new TwoLongs(output.first + v, output.second + v);
		}
	}

	public void write(Object _output, DataOutput out)
		throws IOException
	{
		if (!$assertionsDisabled && !valid(_output, true))
			throw new AssertionError();
		if (_output instanceof Long)
		{
			Long output = (Long)_output;
			out.writeVLong(output.longValue() << 1);
		} else
		{
			TwoLongs output = (TwoLongs)_output;
			out.writeVLong(output.first << 1 | 1L);
			out.writeVLong(output.second);
		}
	}

	public Object read(DataInput in)
		throws IOException
	{
		long code = in.readVLong();
		if ((code & 1L) == 0L)
		{
			long v = code >>> 1;
			if (v == 0L)
				return NO_OUTPUT;
			else
				return Long.valueOf(v);
		} else
		{
			long first = code >>> 1;
			long second = in.readVLong();
			return new TwoLongs(first, second);
		}
	}

	private boolean valid(Long o)
	{
		if (!$assertionsDisabled && o == null)
			throw new AssertionError();
		if (!$assertionsDisabled && !(o instanceof Long))
			throw new AssertionError();
		if (!$assertionsDisabled && o != NO_OUTPUT && o.longValue() <= 0L)
			throw new AssertionError();
		else
			return true;
	}

	private boolean valid(Object _o, boolean allowDouble)
	{
		if (!allowDouble)
			if (!$assertionsDisabled && !(_o instanceof Long))
				throw new AssertionError();
			else
				return valid((Long)_o);
		if (_o instanceof TwoLongs)
			return true;
		else
			return valid((Long)_o);
	}

	public Object getNoOutput()
	{
		return NO_OUTPUT;
	}

	public String outputToString(Object output)
	{
		return output.toString();
	}

	public Object merge(Object first, Object second)
	{
		if (!$assertionsDisabled && !valid(first, false))
			throw new AssertionError();
		if (!$assertionsDisabled && !valid(second, false))
			throw new AssertionError();
		else
			return new TwoLongs(((Long)first).longValue(), ((Long)second).longValue());
	}

	public volatile Object subtract(Object x0, Object x1)
	{
		return subtract(x0, x1);
	}

	public volatile Object common(Object x0, Object x1)
	{
		return common(x0, x1);
	}

}
