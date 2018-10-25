// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IntsRefFSTEnum.java

package org.apache.lucene.util.fst;

import java.io.IOException;
import org.apache.lucene.util.ArrayUtil;
import org.apache.lucene.util.IntsRef;

// Referenced classes of package org.apache.lucene.util.fst:
//			FSTEnum, FST

public final class IntsRefFSTEnum extends FSTEnum
{
	public static class InputOutput
	{

		public IntsRef input;
		public Object output;

		public InputOutput()
		{
		}
	}


	private final IntsRef current = new IntsRef(10);
	private final InputOutput result = new InputOutput();
	private IntsRef target;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/fst/IntsRefFSTEnum.desiredAssertionStatus();

	public IntsRefFSTEnum(FST fst)
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

	public InputOutput seekCeil(IntsRef target)
		throws IOException
	{
		this.target = target;
		targetLength = target.length;
		super.doSeekCeil();
		return setResult();
	}

	public InputOutput seekFloor(IntsRef target)
		throws IOException
	{
		this.target = target;
		targetLength = target.length;
		super.doSeekFloor();
		return setResult();
	}

	public InputOutput seekExact(IntsRef target)
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
			return target.ints[(target.offset + upto) - 1];
	}

	protected int getCurrentLabel()
	{
		return current.ints[upto];
	}

	protected void setCurrentLabel(int label)
	{
		current.ints[upto] = label;
	}

	protected void grow()
	{
		current.ints = ArrayUtil.grow(current.ints, upto + 1);
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
