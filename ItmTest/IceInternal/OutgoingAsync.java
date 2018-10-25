// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OutgoingAsync.java

package IceInternal;

import Ice.*;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package IceInternal:
//			LocalExceptionWrapper, OutgoingAsyncMessageCallback, Reference, Protocol, 
//			BasicStream, Instance, Timer, RetryQueue, 
//			RequestHandler, TimerTask, CallbackBase

public class OutgoingAsync extends AsyncResult
	implements OutgoingAsyncMessageCallback
{

	protected ObjectPrxHelperBase _proxy;
	private ConnectionI _timerTaskConnection;
	private TimerTask _timerTask;
	private _ObjectDel _delegate;
	private int _cnt;
	private OperationMode _mode;
	private static final Map _emptyContext = new HashMap();
	static final boolean $assertionsDisabled = !IceInternal/OutgoingAsync.desiredAssertionStatus();

	public OutgoingAsync(ObjectPrx prx, String operation, CallbackBase callback)
	{
		super(((ObjectPrxHelperBase)prx).__reference().getInstance(), operation, callback);
		_proxy = (ObjectPrxHelperBase)prx;
	}

	public void __prepare(String operation, OperationMode mode, Map ctx, boolean explicitCtx)
	{
		_delegate = null;
		_cnt = 0;
		_mode = mode;
		_sentSynchronously = false;
		if (explicitCtx && ctx == null)
			ctx = _emptyContext;
		if (_proxy.ice_isBatchOneway() || _proxy.ice_isBatchDatagram())
			throw new FeatureNotSupportedException("can't send batch requests with AMI");
		_os.writeBlob(Protocol.requestHdr);
		Reference ref = _proxy.__reference();
		ref.getIdentity().__write(_os);
		String facet = ref.getFacet();
		if (facet == null || facet.length() == 0)
		{
			_os.writeStringSeq(null);
		} else
		{
			String facetPath[] = {
				facet
			};
			_os.writeStringSeq(facetPath);
		}
		_os.writeString(operation);
		_os.writeByte((byte)mode.ordinal());
		if (ctx != null)
		{
			ContextHelper.write(_os, ctx);
		} else
		{
			ImplicitContextI implicitContext = ref.getInstance().getImplicitContext();
			Map prxContext = ref.getContext();
			if (implicitContext == null)
				ContextHelper.write(_os, prxContext);
			else
				implicitContext.write(prxContext, _os);
		}
		_os.startWriteEncaps();
	}

	public ObjectPrx getProxy()
	{
		return _proxy;
	}

	public boolean __sent(ConnectionI connection)
	{
		Object obj = _monitor;
		JVM INSTR monitorenter ;
		boolean alreadySent;
		alreadySent = (_state & 4) != 0;
		_state |= 4;
		if ((_state & 2) == 0)
			if (!_proxy.ice_isTwoway())
				_state |= 3;
			else
			if (connection.timeout() > 0)
			{
				if (!$assertionsDisabled && (_timerTaskConnection != null || _timerTask != null))
					throw new AssertionError();
				_timerTaskConnection = connection;
				_timerTask = new TimerTask() {

					final OutgoingAsync this$0;

					public void runTimerTask()
					{
						__runTimerTask();
					}

			
			{
				this$0 = OutgoingAsync.this;
				super();
			}
				};
				_instance.timer().schedule(_timerTask, connection.timeout());
			}
		_monitor.notifyAll();
		return !alreadySent;
		Exception exception;
		exception;
		throw exception;
	}

	public void __sent()
	{
		__sentInternal();
	}

	public void __finished(LocalException exc, boolean sent)
	{
		synchronized (_monitor)
		{
			if (!$assertionsDisabled && (_state & 2) != 0)
				throw new AssertionError();
			if (_timerTaskConnection != null)
			{
				_instance.timer().cancel(_timerTask);
				_timerTaskConnection = null;
				_timerTask = null;
			}
		}
		try
		{
			int interval = handleException(exc, sent);
			if (interval > 0)
				_instance.retryQueue().add(this, interval);
			else
				__send(false);
		}
		catch (LocalException ex)
		{
			__exception(ex);
		}
	}

	public final void __finished(LocalExceptionWrapper exc)
	{
		try
		{
			int interval = handleException(exc);
			if (interval > 0)
				_instance.retryQueue().add(this, interval);
			else
				__send(false);
		}
		catch (LocalException ex)
		{
			__exception(ex);
		}
	}

	public final void __finished(BasicStream is)
	{
		if (!$assertionsDisabled && !_proxy.ice_isTwoway())
			throw new AssertionError();
		byte replyStatus;
		try
		{
			synchronized (_monitor)
			{
				if (!$assertionsDisabled && (_exception != null || (_state & 2) != 0))
					throw new AssertionError();
				if (_timerTaskConnection != null)
				{
					_instance.timer().cancel(_timerTask);
					_timerTaskConnection = null;
					_timerTask = null;
				}
				_is.swap(is);
				replyStatus = _is.readByte();
				switch (replyStatus)
				{
				case 2: // '\002'
				case 3: // '\003'
				case 4: // '\004'
				{
					Identity id = new Identity();
					id.__read(_is);
					String facetPath[] = _is.readStringSeq();
					String facet;
					if (facetPath.length > 0)
					{
						if (facetPath.length > 1)
							throw new MarshalException();
						facet = facetPath[0];
					} else
					{
						facet = "";
					}
					String operation = _is.readString();
					RequestFailedException ex = null;
					switch (replyStatus)
					{
					case 2: // '\002'
						ex = new ObjectNotExistException();
						break;

					case 3: // '\003'
						ex = new FacetNotExistException();
						break;

					case 4: // '\004'
						ex = new OperationNotExistException();
						break;

					default:
						if (!$assertionsDisabled)
							throw new AssertionError();
						break;
					}
					ex.id = id;
					ex.facet = facet;
					ex.operation = operation;
					throw ex;
				}

				case 5: // '\005'
				case 6: // '\006'
				case 7: // '\007'
				{
					String unknown = _is.readString();
					UnknownException ex = null;
					switch (replyStatus)
					{
					case 7: // '\007'
						ex = new UnknownException();
						break;

					case 5: // '\005'
						ex = new UnknownLocalException();
						break;

					case 6: // '\006'
						ex = new UnknownUserException();
						break;

					default:
						if (!$assertionsDisabled)
							throw new AssertionError();
						break;
					}
					ex.unknown = unknown;
					throw ex;
				}

				default:
				{
					throw new UnknownReplyStatusException();
				}

				case 0: // '\0'
				case 1: // '\001'
				{
					_state |= 2;
					break;
				}
				}
				if (replyStatus == 0)
					_state |= 1;
				_monitor.notifyAll();
			}
		}
		catch (LocalException ex)
		{
			__finished(ex, true);
			return;
		}
		if (!$assertionsDisabled && replyStatus != 0 && replyStatus != 1)
		{
			throw new AssertionError();
		} else
		{
			__response();
			return;
		}
	}

	public final boolean __send(boolean synchronous)
	{
label0:
		{
			int interval;
			do
			{
				interval = 0;
				try
				{
					_delegate = _proxy.__getDelegate(true);
					int status = _delegate.__getRequestHandler().sendAsyncRequest(this);
					if ((status & 1) > 0)
						if (synchronous)
						{
							_sentSynchronously = true;
							if ((status & 2) > 0)
								__sent();
						} else
						if ((status & 2) > 0)
							__sentAsync();
					break label0;
				}
				catch (LocalExceptionWrapper ex)
				{
					interval = handleException(ex);
					continue;
				}
				catch (LocalException ex)
				{
					interval = handleException(ex, false);
				}
			} while (interval <= 0);
			_instance.retryQueue().add(this, interval);
			return false;
		}
		return _sentSynchronously;
	}

	private int handleException(LocalException exc, boolean sent)
	{
		IntHolder interval = new IntHolder(0);
		try
		{
			if (!sent || (exc instanceof CloseConnectionException) || (exc instanceof ObjectNotExistException))
				throw exc;
			else
				throw new LocalExceptionWrapper(exc, false);
		}
		catch (LocalExceptionWrapper ex)
		{
			if (_mode == OperationMode.Nonmutating || _mode == OperationMode.Idempotent)
				_cnt = _proxy.__handleExceptionWrapperRelaxed(_delegate, ex, interval, _cnt);
			else
				_proxy.__handleExceptionWrapper(_delegate, ex);
		}
		catch (LocalException ex)
		{
			_cnt = _proxy.__handleException(_delegate, ex, interval, _cnt);
		}
		return interval.value;
	}

	private int handleException(LocalExceptionWrapper ex)
	{
		IntHolder interval = new IntHolder(0);
		if (_mode == OperationMode.Nonmutating || _mode == OperationMode.Idempotent)
			_cnt = _proxy.__handleExceptionWrapperRelaxed(_delegate, ex, interval, _cnt);
		else
			_proxy.__handleExceptionWrapper(_delegate, ex);
		return interval.value;
	}

	private final void __runTimerTask()
	{
		ConnectionI connection;
		synchronized (_monitor)
		{
			connection = _timerTaskConnection;
			_timerTaskConnection = null;
			_timerTask = null;
		}
		if (connection != null)
			connection.exception(new TimeoutException());
	}


}
