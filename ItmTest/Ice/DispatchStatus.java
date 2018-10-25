// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DispatchStatus.java

package Ice;

import java.io.Serializable;

public final class DispatchStatus extends Enum
	implements Serializable
{

	public static final DispatchStatus DispatchOK;
	public static final DispatchStatus DispatchUserException;
	public static final DispatchStatus DispatchAsync;
	private static final DispatchStatus $VALUES[];

	public static final DispatchStatus[] values()
	{
		return (DispatchStatus[])$VALUES.clone();
	}

	public static DispatchStatus valueOf(String name)
	{
		return (DispatchStatus)Enum.valueOf(Ice/DispatchStatus, name);
	}

	private DispatchStatus(String s, int i)
	{
		super(s, i);
	}

	static 
	{
		DispatchOK = new DispatchStatus("DispatchOK", 0);
		DispatchUserException = new DispatchStatus("DispatchUserException", 1);
		DispatchAsync = new DispatchStatus("DispatchAsync", 2);
		$VALUES = (new DispatchStatus[] {
			DispatchOK, DispatchUserException, DispatchAsync
		});
	}
}
