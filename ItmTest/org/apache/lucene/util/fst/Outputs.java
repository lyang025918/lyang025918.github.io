// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Outputs.java

package org.apache.lucene.util.fst;

import java.io.IOException;
import org.apache.lucene.store.DataInput;
import org.apache.lucene.store.DataOutput;

public abstract class Outputs
{

	public Outputs()
	{
	}

	public abstract Object common(Object obj, Object obj1);

	public abstract Object subtract(Object obj, Object obj1);

	public abstract Object add(Object obj, Object obj1);

	public abstract void write(Object obj, DataOutput dataoutput)
		throws IOException;

	public abstract Object read(DataInput datainput)
		throws IOException;

	public abstract Object getNoOutput();

	public abstract String outputToString(Object obj);

	public Object merge(Object first, Object second)
	{
		throw new UnsupportedOperationException();
	}
}
