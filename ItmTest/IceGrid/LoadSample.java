// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LoadSample.java

package IceGrid;

import IceInternal.BasicStream;
import java.io.Serializable;

public final class LoadSample extends Enum
	implements Serializable
{

	public static final LoadSample LoadSample1;
	public static final LoadSample LoadSample5;
	public static final LoadSample LoadSample15;
	private static final LoadSample $VALUES[];

	public static final LoadSample[] values()
	{
		return (LoadSample[])$VALUES.clone();
	}

	public static LoadSample valueOf(String name)
	{
		return (LoadSample)Enum.valueOf(IceGrid/LoadSample, name);
	}

	private LoadSample(String s, int i)
	{
		super(s, i);
	}

	public void __write(BasicStream __os)
	{
		__os.writeByte((byte)ordinal());
	}

	public static LoadSample __read(BasicStream __is)
	{
		int __v = __is.readByte(3);
		return values()[__v];
	}

	static 
	{
		LoadSample1 = new LoadSample("LoadSample1", 0);
		LoadSample5 = new LoadSample("LoadSample5", 1);
		LoadSample15 = new LoadSample("LoadSample15", 2);
		$VALUES = (new LoadSample[] {
			LoadSample1, LoadSample5, LoadSample15
		});
	}
}
