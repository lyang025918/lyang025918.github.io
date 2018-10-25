// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ThreadPoolWorkQueue.java

package IceInternal;

import Ice.*;
import IceUtilInternal.Assert;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.util.LinkedList;

// Referenced classes of package IceInternal:
//			EventHandler, ThreadPoolWorkItem, Network, Selector, 
//			ThreadPool, Instance, ThreadPoolCurrent

final class ThreadPoolWorkQueue extends EventHandler
{

	private final ThreadPool _threadPool;
	private final Instance _instance;
	private final Selector _selector;
	boolean _destroyed;
	private ReadableByteChannel _fdIntrRead;
	private WritableByteChannel _fdIntrWrite;
	private LinkedList _workItems;
	static final boolean $assertionsDisabled = !IceInternal/ThreadPoolWorkQueue.desiredAssertionStatus();

	ThreadPoolWorkQueue(ThreadPool threadPool, Instance instance, Selector selector)
	{
		_workItems = new LinkedList();
		_threadPool = threadPool;
		_instance = instance;
		_selector = selector;
		_destroyed = false;
		Network.SocketPair pair = Network.createPipe();
		_fdIntrRead = (ReadableByteChannel)pair.source;
		_fdIntrWrite = pair.sink;
		try
		{
			pair.source.configureBlocking(false);
		}
		catch (IOException ex)
		{
			throw new SyscallException(ex);
		}
		_selector.initialize(this);
		_selector.update(this, 0, 1);
	}

	protected synchronized void finalize()
		throws Throwable
	{
		Assert.FinalizerAssert(_destroyed);
	}

	public synchronized void close()
	{
		try
		{
			_fdIntrWrite.close();
		}
		catch (IOException ex) { }
		_fdIntrWrite = null;
		try
		{
			_fdIntrRead.close();
		}
		catch (IOException ex) { }
		_fdIntrRead = null;
	}

	public synchronized void destroy()
	{
		if (!$assertionsDisabled && _destroyed)
		{
			throw new AssertionError();
		} else
		{
			_destroyed = true;
			postMessage();
			return;
		}
	}

	public synchronized void queue(ThreadPoolWorkItem item)
	{
		if (_destroyed)
		{
			throw new CommunicatorDestroyedException();
		} else
		{
			_workItems.add(item);
			postMessage();
			return;
		}
	}

	public void message(ThreadPoolCurrent current)
	{
		ByteBuffer buf = ByteBuffer.allocate(1);
		try
		{
			buf.rewind();
			int ret = _fdIntrRead.read(buf);
			if (!$assertionsDisabled && ret <= 0)
				throw new AssertionError();
		}
		catch (IOException ex)
		{
			throw new SocketException(ex);
		}
		ThreadPoolWorkItem workItem = null;
		synchronized (this)
		{
			if (!_workItems.isEmpty())
			{
				workItem = (ThreadPoolWorkItem)_workItems.removeFirst();
			} else
			{
				if (!$assertionsDisabled && !_destroyed)
					throw new AssertionError();
				postMessage();
			}
		}
		if (workItem != null)
		{
			workItem.execute(current);
		} else
		{
			_threadPool.ioCompleted(current);
			throw new ThreadPool.DestroyedException();
		}
	}

	public void finished(ThreadPoolCurrent current)
	{
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			return;
	}

	public String toString()
	{
		return "work queue";
	}

	public SelectableChannel fd()
	{
		return (SelectableChannel)_fdIntrRead;
	}

	public boolean hasMoreData()
	{
		return false;
	}

	public void postMessage()
	{
		ByteBuffer buf = ByteBuffer.allocate(1);
		buf.put(0, (byte)0);
		while (buf.hasRemaining()) 
			try
			{
				_fdIntrWrite.write(buf);
			}
			catch (IOException ex)
			{
				throw new SocketException(ex);
			}
	}

}
