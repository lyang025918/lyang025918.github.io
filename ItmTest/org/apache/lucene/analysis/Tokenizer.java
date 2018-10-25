// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Tokenizer.java

package org.apache.lucene.analysis;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.util.AttributeSource;

// Referenced classes of package org.apache.lucene.analysis:
//			TokenStream, CharFilter

public abstract class Tokenizer extends TokenStream
{

	protected Reader input;
	static final boolean $assertionsDisabled = !org/apache/lucene/analysis/Tokenizer.desiredAssertionStatus();

	protected Tokenizer(Reader input)
	{
		if (!$assertionsDisabled && input == null)
		{
			throw new AssertionError("input must not be null");
		} else
		{
			this.input = input;
			return;
		}
	}

	protected Tokenizer(org.apache.lucene.util.AttributeSource.AttributeFactory factory, Reader input)
	{
		super(factory);
		if (!$assertionsDisabled && input == null)
		{
			throw new AssertionError("input must not be null");
		} else
		{
			this.input = input;
			return;
		}
	}

	protected Tokenizer(AttributeSource source, Reader input)
	{
		super(source);
		if (!$assertionsDisabled && input == null)
		{
			throw new AssertionError("input must not be null");
		} else
		{
			this.input = input;
			return;
		}
	}

	public void close()
		throws IOException
	{
		if (input != null)
		{
			input.close();
			input = null;
		}
	}

	protected final int correctOffset(int currentOff)
	{
		if (!$assertionsDisabled && input == null)
			throw new AssertionError("this tokenizer is closed");
		else
			return (input instanceof CharFilter) ? ((CharFilter)input).correctOffset(currentOff) : currentOff;
	}

	public final void setReader(Reader input)
		throws IOException
	{
		if (!$assertionsDisabled && input == null)
			throw new AssertionError("input must not be null");
		this.input = input;
		if (!$assertionsDisabled && !setReaderTestPoint())
			throw new AssertionError();
		else
			return;
	}

	boolean setReaderTestPoint()
	{
		return true;
	}

}
