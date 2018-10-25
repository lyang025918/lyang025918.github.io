// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Outgoing.java

package IceInternal;

import Ice.*;
import java.util.Map;

// Referenced classes of package IceInternal:
//			BasicStream, LocalExceptionWrapper, OutgoingMessageCallback, RequestHandler, 
//			Reference, Protocol, Instance

public final class Outgoing
	implements OutgoingMessageCallback
{

	private RequestHandler _handler;
	private BasicStream _is;
	private BasicStream _os;
	private boolean _sent;
	private LocalException _exception;
	private static final int StateUnsent = 0;
	private static final int StateInProgress = 1;
	private static final int StateOK = 2;
	private static final int StateUserException = 3;
	private static final int StateLocalException = 4;
	private static final int StateFailed = 5;
	private int _state;
	public Outgoing next;
	static final boolean $assertionsDisabled = !IceInternal/Outgoing.desiredAssertionStatus();

	public Outgoing(RequestHandler handler, String operation, OperationMode mode, Map context)
		throws LocalExceptionWrapper
	{
		_state = 0;
		_sent = false;
		_handler = handler;
		Instance instance = _handler.getReference().getInstance();
		_is = new BasicStream(instance);
		_os = new BasicStream(instance);
		writeHeader(operation, mode, context);
	}

	public void reset(RequestHandler handler, String operation, OperationMode mode, Map context)
		throws LocalExceptionWrapper
	{
		_state = 0;
		_exception = null;
		_sent = false;
		_handler = handler;
		writeHeader(operation, mode, context);
	}

	public void reclaim()
	{
		_is.reset();
		_os.reset();
	}

	public boolean invoke()
		throws LocalExceptionWrapper
	{
		if (!$assertionsDisabled && _state != 0)
			throw new AssertionError();
		_os.endWriteEncaps();
		switch (_handler.getReference().getMode())
		{
		case 0: // '\0'
			_state = 1;
			ConnectionI connection = _handler.sendRequest(this);
			if (!$assertionsDisabled && connection == null)
				throw new AssertionError();
			boolean timedOut = false;
			synchronized (this)
			{
				while (_state != 5 && !_sent) 
					try
					{
						wait();
					}
					catch (InterruptedException ex) { }
				int timeout = connection.timeout();
				do
				{
					if (_state != 1 || timedOut)
						break;
					try
					{
						if (timeout >= 0)
						{
							wait(timeout);
							if (_state == 1)
								timedOut = true;
						} else
						{
							wait();
						}
					}
					catch (InterruptedException ex) { }
				} while (true);
			}
			if (timedOut)
			{
				connection.exception(new TimeoutException());
				synchronized (this)
				{
					while (_state == 1) 
						try
						{
							wait();
						}
						catch (InterruptedException ex) { }
				}
			}
			if (_exception != null)
			{
				_exception.fillInStackTrace();
				if (!_sent || (_exception instanceof CloseConnectionException) || (_exception instanceof ObjectNotExistException))
					throw _exception;
				else
					throw new LocalExceptionWrapper(_exception, false);
			}
			if (_state == 3)
				return false;
			if (!$assertionsDisabled && _state != 2)
				throw new AssertionError();
			else
				return true;

		case 1: // '\001'
		case 3: // '\003'
			_state = 1;
			if (_handler.sendRequest(this) != null)
				synchronized (this)
				{
					while (_state != 5 && !_sent) 
						try
						{
							wait();
						}
						catch (InterruptedException ex) { }
					if (_exception != null)
						if (!$assertionsDisabled && _sent)
							throw new AssertionError();
						else
							throw _exception;
				}
			return true;

		case 2: // '\002'
		case 4: // '\004'
			_state = 1;
			_handler.finishBatchRequest(_os);
			return true;
		}
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			return false;
	}

	public void abort(LocalException ex)
		throws LocalExceptionWrapper
	{
		if (!$assertionsDisabled && _state != 0)
			throw new AssertionError();
		int mode = _handler.getReference().getMode();
		if (mode == 2 || mode == 4)
			_handler.abortBatchRequest();
		throw ex;
	}

	public void sent(boolean async)
	{
		if (async)
			synchronized (this)
			{
				_sent = true;
				notify();
			}
		else
			_sent = true;
	}

	public synchronized void finished(BasicStream is)
	{
		if (!$assertionsDisabled && _handler.getReference().getMode() != 0)
			throw new AssertionError();
		if (!$assertionsDisabled && _state > 1)
			throw new AssertionError();
		_is.swap(is);
		byte replyStatus = _is.readByte();
		switch (replyStatus)
		{
		case 0: // '\0'
		{
			_state = 2;
			break;
		}

		case 1: // '\001'
		{
			_state = 3;
			break;
		}

		case 2: // '\002'
		case 3: // '\003'
		case 4: // '\004'
		{
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
			ex.id = new Identity();
			ex.id.__read(_is);
			String facetPath[] = _is.readStringSeq();
			if (facetPath.length > 0)
			{
				if (facetPath.length > 1)
					throw new MarshalException();
				ex.facet = facetPath[0];
			} else
			{
				ex.facet = "";
			}
			ex.operation = _is.readString();
			_exception = ex;
			_state = 4;
			break;
		}

		case 5: // '\005'
		case 6: // '\006'
		case 7: // '\007'
		{
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
			ex.unknown = _is.readString();
			_exception = ex;
			_state = 4;
			break;
		}

		default:
		{
			_exception = new UnknownReplyStatusException();
			_state = 4;
			break;
		}
		}
		notify();
	}

	public synchronized void finished(LocalException ex, boolean sent)
	{
		if (!$assertionsDisabled && _state > 1)
		{
			throw new AssertionError();
		} else
		{
			_state = 5;
			_exception = ex;
			_sent = sent;
			notify();
			return;
		}
	}

	public BasicStream is()
	{
		return _is;
	}

	public BasicStream os()
	{
		return _os;
	}

	public void throwUserException()
		throws UserException
	{
		try
		{
			_is.startReadEncaps();
			_is.throwException();
		}
		catch (UserException ex)
		{
			_is.endReadEncaps();
			throw ex;
		}
	}

	private void writeHeader(String operation, OperationMode mode, Map context)
		throws LocalExceptionWrapper
	{
		switch (_handler.getReference().getMode())
		{
		case 0: // '\0'
		case 1: // '\001'
		case 3: // '\003'
			_os.writeBlob(Protocol.requestHdr);
			break;

		case 2: // '\002'
		case 4: // '\004'
			_handler.prepareBatchRequest(_os);
			break;
		}
		try
		{
			_handler.getReference().getIdentity().__write(_os);
			String facet = _handler.getReference().getFacet();
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
			if (context != null)
			{
				ContextHelper.write(_os, context);
			} else
			{
				ImplicitContextI implicitContext = _handler.getReference().getInstance().getImplicitContext();
				Map prxContext = _handler.getReference().getContext();
				if (implicitContext == null)
					ContextHelper.write(_os, prxContext);
				else
					implicitContext.write(prxContext, _os);
			}
			_os.startWriteEncaps();
		}
		catch (LocalException ex)
		{
			abort(ex);
		}
	}

}
