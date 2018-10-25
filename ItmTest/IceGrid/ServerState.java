// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServerState.java

package IceGrid;

import IceInternal.BasicStream;
import java.io.Serializable;

public final class ServerState extends Enum
	implements Serializable
{

	public static final ServerState Inactive;
	public static final ServerState Activating;
	public static final ServerState ActivationTimedOut;
	public static final ServerState Active;
	public static final ServerState Deactivating;
	public static final ServerState Destroying;
	public static final ServerState Destroyed;
	private static final ServerState $VALUES[];

	public static final ServerState[] values()
	{
		return (ServerState[])$VALUES.clone();
	}

	public static ServerState valueOf(String name)
	{
		return (ServerState)Enum.valueOf(IceGrid/ServerState, name);
	}

	private ServerState(String s, int i)
	{
		super(s, i);
	}

	public void __write(BasicStream __os)
	{
		__os.writeByte((byte)ordinal());
	}

	public static ServerState __read(BasicStream __is)
	{
		int __v = __is.readByte(7);
		return values()[__v];
	}

	static 
	{
		Inactive = new ServerState("Inactive", 0);
		Activating = new ServerState("Activating", 1);
		ActivationTimedOut = new ServerState("ActivationTimedOut", 2);
		Active = new ServerState("Active", 3);
		Deactivating = new ServerState("Deactivating", 4);
		Destroying = new ServerState("Destroying", 5);
		Destroyed = new ServerState("Destroyed", 6);
		$VALUES = (new ServerState[] {
			Inactive, Activating, ActivationTimedOut, Active, Deactivating, Destroying, Destroyed
		});
	}
}
