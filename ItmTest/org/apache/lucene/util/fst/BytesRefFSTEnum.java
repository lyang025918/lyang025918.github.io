// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BytesRefFSTEnum.java

package org.apache.lucene.util.fst;

import java.io.IOException;
import org.apache.lucene.util.ArrayUtil;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.util.fst:
//			FSTEnum, FST

public final class BytesRefFSTEnum extends FSTEnum
{
	public static class InputOutput
	{

		public BytesRef input;
		public Object output;

		public InputOutput()
		{
		}
	}


	private final BytesRef current = new BytesRef(10);
	private final InputOutput result = new InputOutput();
	private BytesRef target;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/fst/BytesRefFSTEnum.desiredAssertionStatus();

	public BytesRefFSTEnum(FST fst)
	{
		super(fst);
		result.input = current;
		current.offset = 1;
	}

	public InputOutput current()
	{
		return result;
	}

	public InputOutput next()
		throws IOException
	{
		doNext();
		return setResult();
	}

	public InputOutput seekCeil(BytesRef target)
		throws IOException
	{
		this.target = target;
		targetLength = target.length;
		super.doSeekCeil();
		return setResult();
	}

	public InputOutput seekFloor(BytesRef target)
		throws IOException
	{
		this.target = target;
		targetLength = target.length;
		super.doSeekFloor();
		return setResult();
	}

	public InputOutput seekExact(BytesRef target)
		throws IOException
	{
		this.target = target;
		targetLength = target.length;
		if (super.doSeekExact())
		{
			if (!$assertionsDisabled && upto != 1 + target.length)
				throw new AssertionError();
			else
				return setResult();
		} else
		{
			return null;
		}
	}

	protected int getTargetLabel()
	{
		if (upto - 1 == target.length)
			return -1;
		else
			return target.bytes[(target.offset + upto) - 1] & 0xff;
	}

	protected int getCurrentLabel()
	{
		return current.bytes[upto] & 0xff;
	}

	protected void setCurrentLabel(int label)
	{
		current.bytes[upto] = (byte)label;
	}

	protected void grow()
	{
		current.bytes = ArrayUtil.grow(current.bytes, upto + 1);
	}

	private InputOutput setResult()
	{
		if (upto == 0)
		{
			return null;
		} else
		{
			current.length = upto - 1;
			result.output = output[upto];
			return result;
		}
	}

}
