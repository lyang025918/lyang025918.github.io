// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CharSequenceOutputs.java

package org.apache.lucene.util.fst;

import java.io.IOException;
import org.apache.lucene.store.DataInput;
import org.apache.lucene.store.DataOutput;
import org.apache.lucene.util.CharsRef;

// Referenced classes of package org.apache.lucene.util.fst:
//			Outputs

public final class CharSequenceOutputs extends Outputs
{

	private static final CharsRef NO_OUTPUT = new CharsRef();
	private static final CharSequenceOutputs singleton = new CharSequenceOutputs();
	static final boolean $assertionsDisabled = !org/apache/lucene/util/fst/CharSequenceOutputs.desiredAssertionStatus();

	private CharSequenceOutputs()
	{
	}

	public static CharSequenceOutputs getSingleton()
	{
		return singleton;
	}

	public CharsRef common(CharsRef output1, CharsRef output2)
	{
		if (!$assertionsDisabled && output1 == null)
			throw new AssertionError();
		if (!$assertionsDisabled && output2 == null)
			throw new AssertionError();
		int pos1 = output1.offset;
		int pos2 = output2.offset;
		for (int stopAt1 = pos1 + Math.min(output1.length, output2.length); pos1 < stopAt1 && output1.chars[pos1] == output2.chars[pos2]; pos2++)
			pos1++;

		if (pos1 == output1.offset)
			return NO_OUTPUT;
		if (pos1 == output1.offset + output1.length)
			return output1;
		if (pos2 == output2.offset + output2.length)
			return output2;
		else
			return new CharsRef(output1.chars, output1.offset, pos1 - output1.offset);
	}

	public CharsRef subtract(CharsRef output, CharsRef inc)
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
			return new CharsRef(output.chars, output.offset + inc.length, output.length - inc.length);
	}

	public CharsRef add(CharsRef prefix, CharsRef output)
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
			CharsRef result = new CharsRef(prefix.length + output.length);
			System.arraycopy(prefix.chars, prefix.offset, result.chars, 0, prefix.length);
			System.arraycopy(output.chars, output.offset, result.chars, prefix.length, output.length);
			result.length = prefix.length + output.length;
			return result;
		}
	}

	public void write(CharsRef prefix, DataOutput out)
		throws IOException
	{
		if (!$assertionsDisabled && prefix == null)
			throw new AssertionError();
		out.writeVInt(prefix.length);
		for (int idx = 0; idx < prefix.length; idx++)
			out.writeVInt(prefix.chars[prefix.offset + idx]);

	}

	public CharsRef read(DataInput in)
		throws IOException
	{
		int len = in.readVInt();
		if (len == 0)
			return NO_OUTPUT;
		CharsRef output = new CharsRef(len);
		for (int idx = 0; idx < len; idx++)
			output.chars[idx] = (char)in.readVInt();

		output.length = len;
		return output;
	}

	public CharsRef getNoOutput()
	{
		return NO_OUTPUT;
	}

	public String outputToString(CharsRef output)
	{
		return output.toString();
	}

	public volatile String outputToString(Object x0)
	{
		return outputToString((CharsRef)x0);
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
		write((CharsRef)x0, x1);
	}

	public volatile Object add(Object x0, Object x1)
	{
		return add((CharsRef)x0, (CharsRef)x1);
	}

	public volatile Object subtract(Object x0, Object x1)
	{
		return subtract((CharsRef)x0, (CharsRef)x1);
	}

	public volatile Object common(Object x0, Object x1)
	{
		return common((CharsRef)x0, (CharsRef)x1);
	}

}
