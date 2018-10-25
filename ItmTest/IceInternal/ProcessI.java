// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ProcessI.java

package IceInternal;

import Ice.*;
import java.io.PrintStream;

public class ProcessI extends _ProcessDisp
{

	private Communicator _communicator;

	public ProcessI(Communicator communicator)
	{
		_communicator = communicator;
	}

	public void shutdown(Current current)
	{
		_communicator.shutdown();
	}

	public void writeMessage(String message, int fd, Current current)
	{
		switch (fd)
		{
		case 1: // '\001'
			System.out.println(message);
			break;

		case 2: // '\002'
			System.err.println(message);
			break;
		}
	}
}
