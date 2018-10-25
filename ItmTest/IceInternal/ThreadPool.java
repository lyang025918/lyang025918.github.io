// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ThreadPool.java

package IceInternal;

import Ice.*;
import IceUtilInternal.Assert;
import java.util.*;

// Referenced classes of package IceInternal:
//			Selector, ThreadPoolWorkQueue, ThreadPoolCurrent, EventHandler, 
//			Instance, TraceLevels, Ex, BasicStream, 
//			Time, ThreadPoolWorkItem, ObjectAdapterFactory

public final class ThreadPool
{
	private final class EventHandlerThread
		implements Runnable
	{

		private final String _name;
		private Thread _thread;
		final ThreadPool this$0;

		public void join()
		{
			do
				try
				{
					_thread.join();
					break;
				}
				catch (InterruptedException ex) { }
			while (true);
		}

		public void start(int priority)
		{
			_thread = new Thread(null, this, _name, _stackSize);
			_thread.setPriority(priority);
			_thread.start();
		}

		public void run()
		{
			if (_instance.initializationData().threadHook != null)
				try
				{
					_instance.initializationData().threadHook.start();
				}
				catch (Exception ex)
				{
					String s = "thread hook start() method raised an unexpected exception in `";
					s = (new StringBuilder()).append(s).append(_prefix).append("' thread ").append(_name).append(":\n").append(Ex.toString(ex)).toString();
					_instance.initializationData().logger.error(s);
				}
			try
			{
				ThreadPool.this.run(this);
			}
			catch (Exception ex)
			{
				String s = (new StringBuilder()).append("exception in `").append(_prefix).append("' thread ").append(_name).append(":\n").append(Ex.toString(ex)).toString();
				_instance.initializationData().logger.error(s);
			}
			if (_instance.initializationData().threadHook != null)
				try
				{
					_instance.initializationData().threadHook.stop();
				}
				catch (Exception ex)
				{
					String s = "thread hook stop() method raised an unexpected exception in `";
					s = (new StringBuilder()).append(s).append(_prefix).append("' thread ").append(_name).append(":\n").append(Ex.toString(ex)).toString();
					_instance.initializationData().logger.error(s);
				}
		}

		EventHandlerThread(String name)
		{
			this$0 = ThreadPool.this;
			super();
			_name = name;
		}
	}

	static final class DestroyedException extends RuntimeException
	{

		DestroyedException()
		{
		}
	}

	static final class JoinThreadWorkItem
		implements ThreadPoolWorkItem
	{

		private final EventHandlerThread _thread;

		public void execute(ThreadPoolCurrent current)
		{
			_thread.join();
		}

		public JoinThreadWorkItem(EventHandlerThread thread)
		{
			_thread = thread;
		}
	}

	static final class FinishedWorkItem
		implements ThreadPoolWorkItem
	{

		private final EventHandler _handler;

		public void execute(ThreadPoolCurrent current)
		{
			_handler.finished(current);
		}

		public FinishedWorkItem(EventHandler handler)
		{
			_handler = handler;
		}
	}

	final class ShutdownWorkItem
		implements ThreadPoolWorkItem
	{

		final ThreadPool this$0;

		public void execute(ThreadPoolCurrent current)
		{
			current.ioCompleted();
			try
			{
				_instance.objectAdapterFactory().shutdown();
			}
			catch (CommunicatorDestroyedException ex) { }
		}

		ShutdownWorkItem()
		{
			this$0 = ThreadPool.this;
			super();
		}
	}


	private final Instance _instance;
	private final ThreadPoolWorkQueue _workQueue;
	private boolean _destroyed;
	private final String _prefix;
	private final String _threadPrefix;
	private final Selector _selector;
	private final int _size;
	private final int _sizeIO;
	private final int _sizeMax;
	private final int _sizeWarn;
	private final boolean _serialize;
	private final int _priority;
	private final boolean _hasPriority;
	private final long _serverIdleTime;
	private final long _threadIdleTime;
	private final int _stackSize;
	private List _threads;
	private int _threadIndex;
	private int _inUse;
	private int _inUseIO;
	private List _handlers;
	private Iterator _nextHandler;
	private boolean _promote;
	static final boolean $assertionsDisabled = !IceInternal/ThreadPool.desiredAssertionStatus();

	public ThreadPool(Instance instance, String prefix, int timeout)
	{
		_threads = new ArrayList();
		_handlers = new ArrayList();
		_instance = instance;
		_destroyed = false;
		_prefix = prefix;
		_selector = new Selector(instance);
		_threadIndex = 0;
		_inUse = 0;
		_inUseIO = 0;
		_promote = true;
		_serialize = _instance.initializationData().properties.getPropertyAsInt((new StringBuilder()).append(_prefix).append(".Serialize").toString()) > 0;
		_serverIdleTime = timeout;
		Properties properties = _instance.initializationData().properties;
		String programName = properties.getProperty("Ice.ProgramName");
		if (programName.length() > 0)
			_threadPrefix = (new StringBuilder()).append(programName).append("-").append(_prefix).toString();
		else
			_threadPrefix = _prefix;
		int nProcessors = Runtime.getRuntime().availableProcessors();
		int size = properties.getPropertyAsIntWithDefault((new StringBuilder()).append(_prefix).append(".Size").toString(), 1);
		if (size < 1)
		{
			String s = (new StringBuilder()).append(_prefix).append(".Size < 1; Size adjusted to 1").toString();
			_instance.initializationData().logger.warning(s);
			size = 1;
		}
		int sizeMax = properties.getPropertyAsIntWithDefault((new StringBuilder()).append(_prefix).append(".SizeMax").toString(), size);
		if (sizeMax == -1)
			sizeMax = nProcessors;
		if (sizeMax < size)
		{
			String s = (new StringBuilder()).append(_prefix).append(".SizeMax < ").append(_prefix).append(".Size; SizeMax adjusted to Size (").append(size).append(")").toString();
			_instance.initializationData().logger.warning(s);
			sizeMax = size;
		}
		int sizeWarn = properties.getPropertyAsInt((new StringBuilder()).append(_prefix).append(".SizeWarn").toString());
		if (sizeWarn != 0 && sizeWarn < size)
		{
			String s = (new StringBuilder()).append(_prefix).append(".SizeWarn < ").append(_prefix).append(".Size; adjusted SizeWarn to Size (").append(size).append(")").toString();
			_instance.initializationData().logger.warning(s);
			sizeWarn = size;
		} else
		if (sizeWarn > sizeMax)
		{
			String s = (new StringBuilder()).append(_prefix).append(".SizeWarn > ").append(_prefix).append(".SizeMax; adjusted SizeWarn to SizeMax (").append(sizeMax).append(")").toString();
			_instance.initializationData().logger.warning(s);
			sizeWarn = sizeMax;
		}
		int threadIdleTime = properties.getPropertyAsIntWithDefault((new StringBuilder()).append(_prefix).append(".ThreadIdleTime").toString(), 60);
		if (threadIdleTime < 0)
		{
			String s = (new StringBuilder()).append(_prefix).append(".ThreadIdleTime < 0; ThreadIdleTime adjusted to 0").toString();
			_instance.initializationData().logger.warning(s);
			threadIdleTime = 0;
		}
		_size = size;
		_sizeMax = sizeMax;
		_sizeWarn = sizeWarn;
		_sizeIO = Math.min(sizeMax, nProcessors);
		_threadIdleTime = threadIdleTime;
		int stackSize = properties.getPropertyAsInt((new StringBuilder()).append(_prefix).append(".StackSize").toString());
		if (stackSize < 0)
		{
			String s = (new StringBuilder()).append(_prefix).append(".StackSize < 0; Size adjusted to JRE default").toString();
			_instance.initializationData().logger.warning(s);
			stackSize = 0;
		}
		_stackSize = stackSize;
		boolean hasPriority = properties.getProperty((new StringBuilder()).append(_prefix).append(".ThreadPriority").toString()).length() > 0;
		int priority = properties.getPropertyAsInt((new StringBuilder()).append(_prefix).append(".ThreadPriority").toString());
		if (!hasPriority)
		{
			hasPriority = properties.getProperty("Ice.ThreadPriority").length() > 0;
			priority = properties.getPropertyAsInt("Ice.ThreadPriority");
		}
		_hasPriority = hasPriority;
		_priority = priority;
		_workQueue = new ThreadPoolWorkQueue(this, _instance, _selector);
		_nextHandler = _handlers.iterator();
		if (_instance.traceLevels().threadPool >= 1)
		{
			String s = (new StringBuilder()).append("creating ").append(_prefix).append(": Size = ").append(_size).append(", SizeMax = ").append(_sizeMax).append(", SizeWarn = ").append(_sizeWarn).toString();
			_instance.initializationData().logger.trace(_instance.traceLevels().threadPoolCat, s);
		}
		try
		{
			for (int i = 0; i < _size; i++)
			{
				EventHandlerThread thread = new EventHandlerThread((new StringBuilder()).append(_threadPrefix).append("-").append(_threadIndex++).toString());
				_threads.add(thread);
				if (_hasPriority)
					thread.start(_priority);
				else
					thread.start(5);
			}

		}
		catch (RuntimeException ex)
		{
			String s = (new StringBuilder()).append("cannot create thread for `").append(_prefix).append("':\n").append(Ex.toString(ex)).toString();
			_instance.initializationData().logger.error(s);
			destroy();
			joinWithAllThreads();
			throw ex;
		}
	}

	protected synchronized void finalize()
		throws Throwable
	{
		Assert.FinalizerAssert(_destroyed);
	}

	public synchronized void destroy()
	{
		if (!$assertionsDisabled && _destroyed)
		{
			throw new AssertionError();
		} else
		{
			_destroyed = true;
			_workQueue.destroy();
			return;
		}
	}

	public synchronized void initialize(EventHandler handler)
	{
		if (!$assertionsDisabled && _destroyed)
		{
			throw new AssertionError();
		} else
		{
			_selector.initialize(handler);
			return;
		}
	}

	public void register(EventHandler handler, int op)
	{
		update(handler, 0, op);
	}

	public synchronized void update(EventHandler handler, int remove, int add)
	{
		if (!$assertionsDisabled && _destroyed)
		{
			throw new AssertionError();
		} else
		{
			_selector.update(handler, remove, add);
			return;
		}
	}

	public void unregister(EventHandler handler, int op)
	{
		update(handler, op, 0);
	}

	public synchronized void finish(EventHandler handler)
	{
		if (!$assertionsDisabled && _destroyed)
		{
			throw new AssertionError();
		} else
		{
			_selector.finish(handler);
			_workQueue.queue(new FinishedWorkItem(handler));
			return;
		}
	}

	public void execute(ThreadPoolWorkItem workItem)
	{
		_workQueue.queue(workItem);
	}

	public void joinWithAllThreads()
	{
		EventHandlerThread thread;
		for (Iterator i$ = _threads.iterator(); i$.hasNext(); thread.join())
			thread = (EventHandlerThread)i$.next();

		_workQueue.close();
		_selector.destroy();
	}

	public String prefix()
	{
		return _prefix;
	}

	private void run(EventHandlerThread thread)
	{
		ThreadPoolCurrent current;
		boolean select;
		current = new ThreadPoolCurrent(_instance, this);
		select = false;
_L2:
label0:
		{
			if (current._handler != null)
				try
				{
					current._handler.message(current);
				}
				catch (DestroyedException ex)
				{
					return;
				}
				catch (Exception ex)
				{
					String s = (new StringBuilder()).append("exception in `").append(_prefix).append("':\n").append(Ex.toString(ex)).toString();
					s = (new StringBuilder()).append(s).append("\nevent handler: ").append(current._handler.toString()).toString();
					_instance.initializationData().logger.error(s);
				}
			else
			if (select)
				try
				{
					_selector.select(_serverIdleTime);
				}
				catch (Selector.TimeoutException ex)
				{
					synchronized (this)
					{
						if (!_destroyed && _inUse == 0)
							_workQueue.queue(new ShutdownWorkItem());
					}
					continue; /* Loop/switch isn't completed */
				}
			synchronized (this)
			{
				if (current._handler != null)
					break label0;
				if (select)
				{
					_selector.finishSelect(_handlers, _serverIdleTime);
					_nextHandler = _handlers.iterator();
					select = false;
					break MISSING_BLOCK_LABEL_425;
				}
				if (current._leader || !followerWait(thread, current))
					break MISSING_BLOCK_LABEL_425;
			}
			return;
		}
		if (_sizeMax <= 1)
			break MISSING_BLOCK_LABEL_388;
		if (!current._ioCompleted)
		{
			_inUseIO--;
			if ((current.operation & 1) != 0 && current._handler.hasMoreData())
				_selector.hasMoreData(current._handler);
		} else
		{
			_selector.enable(current._handler, current.operation);
			if (!$assertionsDisabled && _inUse <= 0)
				throw new AssertionError();
			_inUse--;
		}
		if (current._leader || !followerWait(thread, current))
			break MISSING_BLOCK_LABEL_425;
		threadpool;
		JVM INSTR monitorexit ;
		return;
		if (!current._ioCompleted && (current.operation & 1) != 0 && current._handler.hasMoreData())
			_selector.hasMoreData(current._handler);
		if (_nextHandler.hasNext())
		{
			current._ioCompleted = false;
			current._handler = (EventHandler)_nextHandler.next();
			current.operation = current._handler._ready;
		} else
		{
			current._handler = null;
		}
		if (current._handler == null)
		{
			if (_inUseIO > 0)
			{
				promoteFollower(current);
			} else
			{
				_selector.startSelect();
				select = true;
			}
		} else
		if (_sizeMax > 1)
		{
			_inUseIO++;
			if (_nextHandler.hasNext() && _inUseIO < _sizeIO)
				promoteFollower(current);
		}
		threadpool;
		JVM INSTR monitorexit ;
		if (true) goto _L2; else goto _L1
_L1:
		exception1;
		throw exception1;
	}

	void ioCompleted(ThreadPoolCurrent current)
	{
		current._ioCompleted = true;
		if (_sizeMax > 1)
			synchronized (this)
			{
				_inUseIO--;
				if ((current.operation & 1) != 0 && current._handler.hasMoreData())
					_selector.hasMoreData(current._handler);
				if (_serialize && !_destroyed)
					_selector.disable(current._handler, current.operation);
				if (current._leader)
					promoteFollower(current);
				else
				if (_promote && (_nextHandler.hasNext() || _inUseIO == 0))
					notify();
				if (!$assertionsDisabled && _inUse < 0)
					throw new AssertionError();
				_inUse++;
				if (_inUse == _sizeWarn)
				{
					String s = (new StringBuilder()).append("thread pool `").append(_prefix).append("' is running low on threads\n").append("Size=").append(_size).append(", ").append("SizeMax=").append(_sizeMax).append(", ").append("SizeWarn=").append(_sizeWarn).toString();
					_instance.initializationData().logger.warning(s);
				}
				if (!_destroyed)
				{
					if (!$assertionsDisabled && _inUse > _threads.size())
						throw new AssertionError();
					if (_inUse < _sizeMax && _inUse == _threads.size())
					{
						if (_instance.traceLevels().threadPool >= 1)
						{
							String s = (new StringBuilder()).append("growing ").append(_prefix).append(": Size=").append(_threads.size() + 1).toString();
							_instance.initializationData().logger.trace(_instance.traceLevels().threadPoolCat, s);
						}
						try
						{
							EventHandlerThread thread = new EventHandlerThread((new StringBuilder()).append(_threadPrefix).append("-").append(_threadIndex++).toString());
							_threads.add(thread);
							if (_hasPriority)
								thread.start(_priority);
							else
								thread.start(5);
						}
						catch (RuntimeException ex)
						{
							String s = (new StringBuilder()).append("cannot create thread for `").append(_prefix).append("':\n").append(Ex.toString(ex)).toString();
							_instance.initializationData().logger.error(s);
						}
					}
				}
			}
		else
		if ((current.operation & 1) != 0 && current._handler.hasMoreData())
			synchronized (this)
			{
				_selector.hasMoreData(current._handler);
			}
	}

	private synchronized void promoteFollower(ThreadPoolCurrent current)
	{
		if (!$assertionsDisabled && (_promote || !current._leader))
			throw new AssertionError();
		_promote = true;
		if (_inUseIO < _sizeIO && (_nextHandler.hasNext() || _inUseIO == 0))
			notify();
		current._leader = false;
	}

	private synchronized boolean followerWait(EventHandlerThread thread, ThreadPoolCurrent current)
	{
		if (!$assertionsDisabled && current._leader)
			throw new AssertionError();
		current._handler = null;
		current.stream.reset();
_L2:
		if (_promote && _inUseIO != _sizeIO && (_nextHandler.hasNext() || _inUseIO <= 0))
			break; /* Loop/switch isn't completed */
		if (_threadIdleTime <= 0L)
			break MISSING_BLOCK_LABEL_298;
		long before = Time.currentMonotonicTimeMillis();
		wait(_threadIdleTime * 1000L);
		if (Time.currentMonotonicTimeMillis() - before < _threadIdleTime * 1000L || _destroyed || _promote && _inUseIO != _sizeIO && (_nextHandler.hasNext() || _inUseIO <= 0))
			continue; /* Loop/switch isn't completed */
		if (_instance.traceLevels().threadPool >= 1)
		{
			String s = (new StringBuilder()).append("shrinking ").append(_prefix).append(": Size=").append(_threads.size() - 1).toString();
			_instance.initializationData().logger.trace(_instance.traceLevels().threadPoolCat, s);
		}
		if (!$assertionsDisabled && _threads.size() <= 1)
			throw new AssertionError();
		_threads.remove(thread);
		_workQueue.queue(new JoinThreadWorkItem(thread));
		return true;
		wait();
		continue; /* Loop/switch isn't completed */
		InterruptedException ex;
		ex;
		if (true) goto _L2; else goto _L1
_L1:
		current._leader = true;
		_promote = false;
		return false;
	}





}
