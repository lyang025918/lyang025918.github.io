// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ThreadPoolCurrent.java

package IceInternal;


// Referenced classes of package IceInternal:
//			BasicStream, ThreadPool, EventHandler, Instance

public final class ThreadPoolCurrent
{

	public int operation;
	public BasicStream stream;
	final ThreadPool _threadPool;
	EventHandler _handler;
	boolean _ioCompleted;
	boolean _leader;

	ThreadPoolCurrent(Instance instance, ThreadPool threadPool)
	{
		operation = 0;
		stream = new BasicStream(instance);
		_threadPool = threadPool;
		_ioCompleted = false;
		_leader = false;
	}

	public void ioCompleted()
	{
		_threadPool.ioCompleted(this);
	}
}
