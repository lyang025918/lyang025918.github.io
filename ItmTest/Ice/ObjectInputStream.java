// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectInputStream.java

package Ice;

import java.io.IOException;
import java.io.InputStream;

// Referenced classes of package Ice:
//			Communicator

public class ObjectInputStream extends java.io.ObjectInputStream
{

	private Communicator _communicator;

	public ObjectInputStream(Communicator communicator, InputStream stream)
		throws IOException
	{
		super(stream);
		_communicator = communicator;
	}

	public Communicator getCommunicator()
	{
		return _communicator;
	}
}
