// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EndpointSelectionType.java

package Ice;

import IceInternal.BasicStream;
import java.io.Serializable;

public final class EndpointSelectionType extends Enum
	implements Serializable
{

	public static final EndpointSelectionType Random;
	public static final EndpointSelectionType Ordered;
	private static final EndpointSelectionType $VALUES[];

	public static final EndpointSelectionType[] values()
	{
		return (EndpointSelectionType[])$VALUES.clone();
	}

	public static EndpointSelectionType valueOf(String name)
	{
		return (EndpointSelectionType)Enum.valueOf(Ice/EndpointSelectionType, name);
	}

	private EndpointSelectionType(String s, int i)
	{
		super(s, i);
	}

	public void __write(BasicStream __os)
	{
		__os.writeByte((byte)ordinal());
	}

	public static EndpointSelectionType __read(BasicStream __is)
	{
		int __v = __is.readByte(2);
		return values()[__v];
	}

	static 
	{
		Random = new EndpointSelectionType("Random", 0);
		Ordered = new EndpointSelectionType("Ordered", 1);
		$VALUES = (new EndpointSelectionType[] {
			Random, Ordered
		});
	}
}
