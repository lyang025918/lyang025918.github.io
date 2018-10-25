// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ByteSequenceOutputs.java

package org.apache.lucene.util.fst;

import java.io.IOException;
import org.apache.lucene.store.DataInput;
import org.apache.lucene.store.DataOutput;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.util.fst:
//			Outputs

public final class ByteSequenceOutputs extends Outputs
{

	private static final BytesRef NO_OUTPUT = new BytesRef();
	private static final ByteSequenceOutputs singleton = new ByteSequenceOutputs();
	static final boolean $assertionsDisabled = !org/apache/lucene/util/fst/ByteSequenceOutputs.desiredAssertionStatus();

	private ByteSequenceOutputs()
	{
	}

	public static ByteSequenceOutputs getSingleton()
	{
		return singleton;
	}

	public BytesRef common(BytesRef output1, BytesRef output2)
	{
		if (!$assertionsDisabled && output1 == null)
			throw new AssertionError();
		if (!$assertionsDisabled && output2 == null)
			throw new AssertionError();
		int pos1 = output1.offset;
		int pos2 = output2.offset;
		for (int stopAt1 = pos1 + Math.min(output1.length, output2.length); pos1 < stopAt1 && output1.bytes[pos1] == output2.bytes[pos2]; pos2++)
			pos1++;

		if (pos1 == output1.offset)
			return NO_OUTPUT;
		if (pos1 == output1.offset + output1.length)
			return output1;
		if (pos2 == output2.offset + output2.length)
			return output2;
		else
			return new BytesRef(output1.bytes, output1.offset, pos1 - output1.offset);
	}

	public BytesRef subtract(BytesRef output, BytesRef inc)
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
			return new BytesRef(output.bytes, output.offset + inc.length, output.length - inc.length);
	}

	public BytesRef add(BytesRef prefix, BytesRef output)
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
			BytesRef result = new BytesRef(prefix.length + output.length);
			System.arraycopy(prefix.bytes, prefix.offset, result.bytes, 0, prefix.length);
			System.arraycopy(output.bytes, output.offset, result.bytes, prefix.length, output.length);
			result.length = prefix.length + output.length;
			return result;
		}
	}

	public void write(BytesRef prefix, DataOutput out)
		throws IOException
	{
		if (!$assertionsDisabled && prefix == null)
		{
			throw new AssertionError();
		} else
		{
			out.writeVInt(prefix.length);
			out.writeBytes(prefix.bytes, prefix.offset, prefix.length);
			return;
		}
	}

	public BytesRef read(DataInput in)
		throws IOException
	{
		int len = in.readVInt();
		if (len == 0)
		{
			return NO_OUTPUT;
		} else
		{
			BytesRef output = new BytesRef(len);
			in.readBytes(output.bytes, 0, len);
			output.length = len;
			return output;
		}
	}

	public BytesRef getNoOutput()
	{
		return NO_OUTPUT;
	}

	public String outputToString(BytesRef output)
	{
		return output.toString();
	}

	public volatile String outputToString(Object x0)
	{
		return outputToString((BytesRef)x0);
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
		write((BytesRef)x0, x1);
	}

	public volatile Object add(Object x0, Object x1)
	{
		return add((BytesRef)x0, (BytesRef)x1);
	}

	public volatile Object subtract(Object x0, Object x1)
	{
		return subtract((BytesRef)x0, (BytesRef)x1);
	}

	public volatile Object common(Object x0, Object x1)
	{
		return common((BytesRef)x0, (BytesRef)x1);
	}

}
