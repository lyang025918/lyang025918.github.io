// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NoOutputs.java

package org.apache.lucene.util.fst;

import org.apache.lucene.store.DataInput;
import org.apache.lucene.store.DataOutput;

// Referenced classes of package org.apache.lucene.util.fst:
//			Outputs

public final class NoOutputs extends Outputs
{

	static final Object NO_OUTPUT = new Object() {

		public int hashCode()
		{
			return 42;
		}

		public boolean equals(Object other)
		{
			return other == this;
		}

	};
	private static final NoOutputs singleton = new NoOutputs();
	static final boolean $assertionsDisabled = !org/apache/lucene/util/fst/NoOutputs.desiredAssertionStatus();

	private NoOutputs()
	{
	}

	public static NoOutputs getSingleton()
	{
		return singleton;
	}

	public Object common(Object output1, Object output2)
	{
		if (!$assertionsDisabled && output1 != NO_OUTPUT)
			throw new AssertionError();
		if (!$assertionsDisabled && output2 != NO_OUTPUT)
			throw new AssertionError();
		else
			return NO_OUTPUT;
	}

	public Object subtract(Object output, Object inc)
	{
		if (!$assertionsDisabled && output != NO_OUTPUT)
			throw new AssertionError();
		if (!$assertionsDisabled && inc != NO_OUTPUT)
			throw new AssertionError();
		else
			return NO_OUTPUT;
	}

	public Object add(Object prefix, Object output)
	{
		if (!$assertionsDisabled && prefix != NO_OUTPUT)
			throw new AssertionError((new StringBuilder()).append("got ").append(prefix).toString());
		if (!$assertionsDisabled && output != NO_OUTPUT)
			throw new AssertionError();
		else
			return NO_OUTPUT;
	}

	public void write(Object obj, DataOutput dataoutput)
	{
	}

	public Object read(DataInput in)
	{
		return NO_OUTPUT;
	}

	public Object getNoOutput()
	{
		return NO_OUTPUT;
	}

	public String outputToString(Object output)
	{
		return "";
	}

}
