// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Selector.java

package IceInternal;

import Ice.InitializationData;
import Ice.Logger;
import Ice.SyscallException;
import java.io.IOException;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

// Referenced classes of package IceInternal:
//			EventHandler, Instance, Time, Network

public final class Selector
{
	static final class TimeoutException extends Exception
	{

		TimeoutException()
		{
		}
	}


	private final Instance _instance;
	private java.nio.channels.Selector _selector;
	private Set _keys;
	private HashSet _changes;
	private HashSet _pendingHandlers;
	private boolean _selecting;
	private int _spuriousWakeUp;
	static final boolean $assertionsDisabled = !IceInternal/Selector.desiredAssertionStatus();

	public Selector(Instance instance)
	{
		_changes = new HashSet();
		_pendingHandlers = new HashSet();
		_instance = instance;
		try
		{
			_selector = java.nio.channels.Selector.open();
		}
		catch (IOException ex)
		{
			throw new SyscallException(ex);
		}
		_keys = _selector.selectedKeys();
	}

	public void destroy()
	{
		try
		{
			_selector.close();
		}
		catch (IOException ex) { }
		_selector = null;
	}

	public void initialize(EventHandler handler)
	{
		updateImpl(handler);
	}

	public void update(EventHandler handler, int remove, int add)
	{
		int previous = handler._registered;
		handler._registered = handler._registered & ~remove;
		handler._registered = handler._registered | add;
		if (previous == handler._registered)
			return;
		updateImpl(handler);
		if (handler.hasMoreData() && (handler._disabled & 1) == 0)
		{
			if ((add & 1) != 0)
				_pendingHandlers.add(handler);
			if ((remove & 1) != 0)
				_pendingHandlers.remove(handler);
		}
	}

	public void enable(EventHandler handler, int status)
	{
		if ((handler._disabled & status) == 0)
			return;
		handler._disabled = handler._disabled & ~status;
		if ((handler._registered & status) != 0)
		{
			updateImpl(handler);
			if ((status & 1) != 0 && handler.hasMoreData())
				_pendingHandlers.add(handler);
		}
	}

	public void disable(EventHandler handler, int status)
	{
		if ((handler._disabled & status) != 0)
			return;
		handler._disabled = handler._disabled | status;
		if ((handler._registered & status) != 0)
		{
			updateImpl(handler);
			if ((status & 1) != 0 && handler.hasMoreData())
				_pendingHandlers.remove(handler);
		}
	}

	public void finish(EventHandler handler)
	{
		handler._registered = 0;
		if (handler._key != null)
		{
			handler._key.cancel();
			handler._key = null;
		}
		_changes.remove(handler);
		_pendingHandlers.remove(handler);
	}

	public void startSelect()
	{
		if (!$assertionsDisabled && !_changes.isEmpty())
			throw new AssertionError();
		if (_pendingHandlers.isEmpty())
			_selecting = true;
	}

	public void finishSelect(List handlers, long timeout)
	{
		_selecting = false;
		handlers.clear();
		Iterator i$;
		if (!_changes.isEmpty())
		{
			EventHandler h;
			for (i$ = _changes.iterator(); i$.hasNext(); updateImpl(h))
				h = (EventHandler)i$.next();

			_changes.clear();
		} else
		if (_keys.isEmpty() && _pendingHandlers.isEmpty() && timeout <= 0L)
		{
			try
			{
				Thread.sleep(1L);
			}
			catch (InterruptedException ex) { }
			if (++_spuriousWakeUp > 100)
				_instance.initializationData().logger.error("spurious selector wake up");
			return;
		}
		_spuriousWakeUp = 0;
		for (ex = _keys.iterator(); ex.hasNext();)
		{
			SelectionKey key = (SelectionKey)ex.next();
			EventHandler handler = (EventHandler)key.attachment();
			try
			{
				handler._ready = fromJavaOps(key.readyOps() & key.interestOps());
				if (handler.hasMoreData() && _pendingHandlers.remove(handler))
					handler._ready |= 1;
				handlers.add(handler);
			}
			catch (CancelledKeyException ex)
			{
				if (!$assertionsDisabled && handler._registered != 0)
					throw new AssertionError();
			}
		}

		_keys.clear();
		ex = _pendingHandlers.iterator();
		do
		{
			if (!ex.hasNext())
				break;
			EventHandler handler = (EventHandler)ex.next();
			if (handler.hasMoreData())
			{
				handler._ready = 1;
				handlers.add(handler);
			}
		} while (true);
		_pendingHandlers.clear();
	}

	public void select(long timeout)
		throws TimeoutException
	{
_L1:
		if (_selecting)
		{
			if (timeout > 0L)
			{
				long before = Time.currentMonotonicTimeMillis();
				if (_selector.select(timeout * 1000L + 10L) == 0 && Time.currentMonotonicTimeMillis() - before >= timeout * 1000L)
					throw new TimeoutException();
			} else
			{
				_selector.select();
			}
		} else
		{
			_selector.selectNow();
		}
		break MISSING_BLOCK_LABEL_167;
		CancelledKeyException ex;
		ex;
		  goto _L1
		ex;
		if (!Network.interrupted(ex)) goto _L2; else goto _L1
_L2:
		String s = (new StringBuilder()).append("fatal error: selector failed:\n").append(ex.getCause().getMessage()).toString();
		_instance.initializationData().logger.error(s);
		Runtime.getRuntime().halt(1);
		break MISSING_BLOCK_LABEL_167;
		Exception exception;
		exception;
		Runtime.getRuntime().halt(1);
		throw exception;
	}

	public void hasMoreData(EventHandler handler)
	{
		if (!$assertionsDisabled && (_selecting || !handler.hasMoreData()))
			throw new AssertionError();
		if ((handler._registered & ~handler._disabled & 1) != 0)
			_pendingHandlers.add(handler);
	}

	private void updateImpl(EventHandler handler)
	{
		if (_selecting)
		{
			if (_changes.isEmpty())
				_selector.wakeup();
			_changes.add(handler);
			return;
		}
		int ops = toJavaOps(handler, handler._registered & ~handler._disabled);
		if (handler._key == null)
		{
			if (handler._registered != 0)
				try
				{
					handler._key = handler.fd().register(_selector, ops, handler);
				}
				catch (ClosedChannelException ex)
				{
					if (!$assertionsDisabled)
						throw new AssertionError();
				}
		} else
		{
			handler._key.interestOps(ops);
		}
	}

	int toJavaOps(EventHandler handler, int o)
	{
		int op = 0;
		if ((o & 1) != 0)
			if ((handler.fd().validOps() & 1) != 0)
				op |= 1;
			else
				op |= 0x10;
		if ((o & 4) != 0)
			op |= 4;
		if ((o & 8) != 0)
			op |= 8;
		return op;
	}

	int fromJavaOps(int o)
	{
		int op = 0;
		if ((o & 0x11) != 0)
			op |= 1;
		if ((o & 4) != 0)
			op |= 4;
		if ((o & 8) != 0)
			op |= 8;
		return op;
	}

}
