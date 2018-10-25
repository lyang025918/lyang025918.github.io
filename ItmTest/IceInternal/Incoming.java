// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Incoming.java

package IceInternal;

import Ice.*;
import java.util.*;

// Referenced classes of package IceInternal:
//			IncomingBase, BasicStream, ServantManager, IncomingAsync, 
//			Instance

public final class Incoming extends IncomingBase
	implements Request
{

	public Incoming next;
	private BasicStream _is;
	private IncomingAsync _cb;
	private int _inParamPos;
	static final boolean $assertionsDisabled = !IceInternal/Incoming.desiredAssertionStatus();

	public Incoming(Instance instance, ConnectionI connection, ObjectAdapter adapter, boolean response, byte compress, int requestId)
	{
		super(instance, connection, adapter, response, compress, requestId);
		_inParamPos = -1;
		_is = new BasicStream(instance);
	}

	public boolean isCollocated()
	{
		return false;
	}

	public Current getCurrent()
	{
		return _current;
	}

	public void reset(Instance instance, ConnectionI connection, ObjectAdapter adapter, boolean response, byte compress, int requestId)
	{
		_cb = null;
		_inParamPos = -1;
		if (_is == null)
			_is = new BasicStream(instance);
		super.reset(instance, connection, adapter, response, compress, requestId);
	}

	public void reclaim()
	{
		_cb = null;
		_inParamPos = -1;
		if (_is != null)
			_is.reset();
		super.reclaim();
	}

	public void invoke(ServantManager servantManager)
	{
		_current.id.__read(_is);
		String facetPath[] = _is.readStringSeq();
		if (facetPath.length > 0)
		{
			if (facetPath.length > 1)
				throw new MarshalException();
			_current.facet = facetPath[0];
		} else
		{
			_current.facet = "";
		}
		_current.operation = _is.readString();
		_current.mode = OperationMode.values()[_is.readByte()];
		_current.ctx = new HashMap();
		for (int sz = _is.readSize(); sz-- > 0;)
		{
			String first = _is.readString();
			String second = _is.readString();
			_current.ctx.put(first, second);
		}

		if (_response)
		{
			if (!$assertionsDisabled && _os.size() != 18)
				throw new AssertionError();
			_os.writeByte((byte)0);
			_os.startWriteEncaps();
		}
		byte replyStatus = 0;
		DispatchStatus dispatchStatus = DispatchStatus.DispatchOK;
		if (servantManager != null)
		{
			_servant = servantManager.findServant(_current.id, _current.facet);
			if (_servant == null)
			{
				_locator = servantManager.findServantLocator(_current.id.category);
				if (_locator == null && _current.id.category.length() > 0)
					_locator = servantManager.findServantLocator("");
				if (_locator != null)
					try
					{
						_servant = _locator.locate(_current, _cookie);
					}
					catch (UserException ex)
					{
						_os.writeUserException(ex);
						replyStatus = 1;
					}
					catch (Exception ex)
					{
						__handleException(ex);
						return;
					}
			}
		}
		if (_servant != null)
			try
			{
				if (!$assertionsDisabled && replyStatus != 0)
					throw new AssertionError();
				dispatchStatus = _servant.__dispatch(this, _current);
				if (dispatchStatus == DispatchStatus.DispatchUserException)
					replyStatus = 1;
				if (dispatchStatus != DispatchStatus.DispatchAsync && _locator != null && !__servantLocatorFinished())
					return;
			}
			catch (Exception ex)
			{
				if (_locator != null && !__servantLocatorFinished())
				{
					return;
				} else
				{
					__handleException(ex);
					return;
				}
			}
		else
		if (replyStatus == 0)
			if (servantManager != null && servantManager.hasServant(_current.id))
				replyStatus = 3;
			else
				replyStatus = 2;
		if (dispatchStatus == DispatchStatus.DispatchAsync)
			return;
		if (!$assertionsDisabled && _connection == null)
			throw new AssertionError();
		if (_response)
		{
			_os.endWriteEncaps();
			if (replyStatus != 0 && replyStatus != 1)
			{
				if (!$assertionsDisabled && replyStatus != 2 && replyStatus != 3)
					throw new AssertionError();
				_os.resize(18, false);
				_os.writeByte(replyStatus);
				_current.id.__write(_os);
				if (_current.facet == null || _current.facet.length() == 0)
				{
					_os.writeStringSeq(null);
				} else
				{
					String facetPath2[] = {
						_current.facet
					};
					_os.writeStringSeq(facetPath2);
				}
				_os.writeString(_current.operation);
			} else
			{
				int save = _os.pos();
				_os.pos(18);
				_os.writeByte(replyStatus);
				_os.pos(save);
			}
			_connection.sendResponse(_os, _compress);
		} else
		{
			_connection.sendNoResponse();
		}
		_connection = null;
	}

	public BasicStream is()
	{
		return _is;
	}

	public BasicStream os()
	{
		return _os;
	}

	public final void push(DispatchInterceptorAsyncCallback cb)
	{
		if (_interceptorAsyncCallbackList == null)
			_interceptorAsyncCallbackList = new LinkedList();
		_interceptorAsyncCallbackList.addFirst(cb);
	}

	public final void pop()
	{
		if (!$assertionsDisabled && _interceptorAsyncCallbackList == null)
		{
			throw new AssertionError();
		} else
		{
			_interceptorAsyncCallbackList.removeFirst();
			return;
		}
	}

	public final void startOver()
	{
		if (_inParamPos == -1)
		{
			_inParamPos = _is.pos();
		} else
		{
			killAsync();
			_is.pos(_inParamPos);
			if (_response)
			{
				_os.endWriteEncaps();
				_os.resize(18, false);
				_os.writeByte((byte)0);
				_os.startWriteEncaps();
			}
		}
	}

	public final void killAsync()
	{
		if (_cb != null)
		{
			_cb.__deactivate(this);
			_cb = null;
		}
	}

	final void setActive(IncomingAsync cb)
	{
		if (!$assertionsDisabled && _cb != null)
		{
			throw new AssertionError();
		} else
		{
			_cb = cb;
			return;
		}
	}

	final boolean isRetriable()
	{
		return _inParamPos != -1;
	}

}
