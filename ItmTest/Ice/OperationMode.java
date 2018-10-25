// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OperationMode.java

package Ice;

import IceInternal.BasicStream;
import java.io.Serializable;

public final class OperationMode extends Enum
	implements Serializable
{

	public static final OperationMode Normal;
	public static final OperationMode Nonmutating;
	public static final OperationMode Idempotent;
	private static final OperationMode $VALUES[];

	public static final OperationMode[] values()
	{
		return (OperationMode[])$VALUES.clone();
	}

	public static OperationMode valueOf(String name)
	{
		return (OperationMode)Enum.valueOf(Ice/OperationMode, name);
	}

	private OperationMode(String s, int i)
	{
		super(s, i);
	}

	public void __write(BasicStream __os)
	{
		__os.writeByte((byte)ordinal());
	}

	public static OperationMode __read(BasicStream __is)
	{
		int __v = __is.readByte(3);
		return values()[__v];
	}

	static 
	{
		Normal = new OperationMode("Normal", 0);
		Nonmutating = new OperationMode("Nonmutating", 1);
		Idempotent = new OperationMode("Idempotent", 2);
		$VALUES = (new OperationMode[] {
			Normal, Nonmutating, Idempotent
		});
	}
}
