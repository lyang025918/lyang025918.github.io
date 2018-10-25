// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EventHandler.java

package IceInternal;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;

// Referenced classes of package IceInternal:
//			ThreadPoolCurrent

public abstract class EventHandler
{

	int _disabled;
	int _registered;
	int _ready;
	SelectionKey _key;

	public EventHandler()
	{
		_disabled = 0;
		_registered = 0;
		_ready = 0;
		_key = null;
	}

	public abstract void message(ThreadPoolCurrent threadpoolcurrent);

	public abstract void finished(ThreadPoolCurrent threadpoolcurrent);

	public abstract String toString();

	public abstract SelectableChannel fd();

	public abstract boolean hasMoreData();
}
