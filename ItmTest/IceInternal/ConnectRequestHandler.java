// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConnectRequestHandler.java

package IceInternal;

import Ice.*;
import java.util.*;

// Referenced classes of package IceInternal:
//			ConnectionRequestHandler, BasicStream, Outgoing, LocalExceptionWrapper, 
//			RequestHandler, Reference, Instance, Ex, 
//			Protocol, RouterInfo, ThreadPool, OutgoingAsync, 
//			BatchOutgoingAsync, BatchOutgoing, DispatchWorkItem, OutgoingAsyncMessageCallback

public class ConnectRequestHandler
	implements RequestHandler, Reference.GetConnectionCallback, RouterInfo.AddProxyCallback
{
	static class Request
	{

		OutgoingAsync out;
		BatchOutgoingAsync batchOut;
		BasicStream os;

		Request(BasicStream os)
		{
			out = null;
			batchOut = null;
			this.os = null;
			this.os = new BasicStream(os.instance());
			this.os.swap(os);
		}

		Request(OutgoingAsync out)
		{
			this.out = null;
			batchOut = null;
			os = null;
			this.out = out;
		}

		Request(BatchOutgoingAsync out)
		{
			this.out = null;
			batchOut = null;
			os = null;
			batchOut = out;
		}
	}


	private final Reference _reference;
	private boolean _response;
	private ObjectPrxHelperBase _proxy;
	private _ObjectDelM _delegate;
	private final boolean _batchAutoFlush;
	private ConnectionI _connection;
	private boolean _compress;
	private LocalException _exception;
	private boolean _initialized;
	private boolean _flushing;
	private List _requests;
	private boolean _batchRequestInProgress;
	private int _batchRequestsSize;
	private BasicStream _batchStream;
	private boolean _updateRequestHandler;
	static final boolean $assertionsDisabled = !IceInternal/ConnectRequestHandler.desiredAssertionStatus();

	public RequestHandler connect()
	{
		_reference.getConnection(this);
		ConnectRequestHandler connectrequesthandler = this;
		JVM INSTR monitorenter ;
		if (!initialized())
			break MISSING_BLOCK_LABEL_62;
		if (!$assertionsDisabled && _connection == null)
			throw new AssertionError();
		return new ConnectionRequestHandler(_reference, _connection, _compress);
		_updateRequestHandler = true;
		this;
		connectrequesthandler;
		JVM INSTR monitorexit ;
		return;
		Exception exception;
		exception;
		throw exception;
	}

	public void prepareBatchRequest(BasicStream os)
		throws LocalExceptionWrapper
	{
label0:
		{
			synchronized (this)
			{
				while (_batchRequestInProgress) 
					try
					{
						wait();
					}
					catch (InterruptedException ex) { }
				if (initialized())
					break label0;
				_batchRequestInProgress = true;
				_batchStream.swap(os);
			}
			return;
		}
		connectrequesthandler;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		_connection.prepareBatchRequest(os);
		return;
	}

	public void finishBatchRequest(BasicStream os)
	{
label0:
		{
			synchronized (this)
			{
				if (initialized())
					break label0;
				if (!$assertionsDisabled && !_batchRequestInProgress)
					throw new AssertionError();
				_batchRequestInProgress = false;
				notifyAll();
				_batchStream.swap(os);
				if (!_batchAutoFlush && _batchStream.size() + _batchRequestsSize > _reference.getInstance().messageSizeMax())
					Ex.throwMemoryLimitException(_batchStream.size() + _batchRequestsSize, _reference.getInstance().messageSizeMax());
				_requests.add(new Request(_batchStream));
			}
			return;
		}
		connectrequesthandler;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		_connection.finishBatchRequest(os, _compress);
		return;
	}

	public void abortBatchRequest()
	{
label0:
		{
			synchronized (this)
			{
				if (initialized())
					break label0;
				if (!$assertionsDisabled && !_batchRequestInProgress)
					throw new AssertionError();
				_batchRequestInProgress = false;
				notifyAll();
				BasicStream dummy = new BasicStream(_reference.getInstance(), _batchAutoFlush);
				_batchStream.swap(dummy);
				_batchRequestsSize = Protocol.requestBatchHdr.length;
			}
			return;
		}
		connectrequesthandler;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		_connection.abortBatchRequest();
		return;
	}

	public ConnectionI sendRequest(Outgoing out)
		throws LocalExceptionWrapper
	{
		if (!getConnection(true).sendRequest(out, _compress, _response) || _response)
			return _connection;
		else
			return null;
	}

	public int sendAsyncRequest(OutgoingAsync out)
		throws LocalExceptionWrapper
	{
		ConnectRequestHandler connectrequesthandler = this;
		JVM INSTR monitorenter ;
		if (initialized())
			break MISSING_BLOCK_LABEL_33;
		_requests.add(new Request(out));
		return 0;
		connectrequesthandler;
		JVM INSTR monitorexit ;
		  goto _L1
		Exception exception;
		exception;
		throw exception;
_L1:
		return _connection.sendAsyncRequest(out, _compress, _response);
	}

	public boolean flushBatchRequests(BatchOutgoing out)
	{
		return getConnection(true).flushBatchRequests(out);
	}

	public int flushAsyncBatchRequests(BatchOutgoingAsync out)
	{
		ConnectRequestHandler connectrequesthandler = this;
		JVM INSTR monitorenter ;
		if (initialized())
			break MISSING_BLOCK_LABEL_33;
		_requests.add(new Request(out));
		return 0;
		connectrequesthandler;
		JVM INSTR monitorexit ;
		  goto _L1
		Exception exception;
		exception;
		throw exception;
_L1:
		return _connection.flushAsyncBatchRequests(out);
	}

	public Outgoing getOutgoing(String operation, OperationMode mode, Map context)
		throws LocalExceptionWrapper
	{
		ConnectRequestHandler connectrequesthandler = this;
		JVM INSTR monitorenter ;
		if (!initialized())
			return new Outgoing(this, operation, mode, context);
		connectrequesthandler;
		JVM INSTR monitorexit ;
		  goto _L1
		Exception exception;
		exception;
		throw exception;
_L1:
		return _connection.getOutgoing(this, operation, mode, context);
	}

	public void reclaimOutgoing(Outgoing out)
	{
label0:
		{
			synchronized (this)
			{
				if (_connection != null)
					break label0;
			}
			return;
		}
		connectrequesthandler;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		_connection.reclaimOutgoing(out);
		return;
	}

	public Reference getReference()
	{
		return _reference;
	}

	public synchronized ConnectionI getConnection(boolean waitInit)
	{
		if (waitInit)
			while (!_initialized && _exception == null) 
				try
				{
					wait();
				}
				catch (InterruptedException ex) { }
		if (_exception != null)
			throw (LocalException)_exception.fillInStackTrace();
		if (!$assertionsDisabled && waitInit && !_initialized)
			throw new AssertionError();
		else
			return _connection;
	}

	public void setConnection(ConnectionI connection, boolean compress)
	{
		synchronized (this)
		{
			if (!$assertionsDisabled && (_exception != null || _connection != null))
				throw new AssertionError();
			if (!$assertionsDisabled && !_updateRequestHandler && !_requests.isEmpty())
				throw new AssertionError();
			_connection = connection;
			_compress = compress;
		}
		RouterInfo ri = _reference.getRouterInfo();
		if (ri != null && !ri.addProxy(_proxy, this))
		{
			return;
		} else
		{
			flushRequests();
			return;
		}
	}

	public synchronized void setException(LocalException ex)
	{
		if (!$assertionsDisabled && (_initialized || _exception != null))
			throw new AssertionError();
		if (!$assertionsDisabled && !_updateRequestHandler && !_requests.isEmpty())
			throw new AssertionError();
		_exception = ex;
		_proxy = null;
		_delegate = null;
		if (!_requests.isEmpty())
			_reference.getInstance().clientThreadPool().execute(new DispatchWorkItem(ex) {

				final LocalException val$ex;
				final ConnectRequestHandler this$0;

				public void run()
				{
					flushRequestsWithException(ex);
				}

			
			{
				this$0 = ConnectRequestHandler.this;
				ex = localexception;
				super(x0);
			}
			});
		notifyAll();
	}

	public void addedProxy()
	{
		flushRequests();
	}

	public ConnectRequestHandler(Reference ref, ObjectPrx proxy, _ObjectDelM delegate)
	{
		_requests = new LinkedList();
		_reference = ref;
		_response = _reference.getMode() == 0;
		_proxy = (ObjectPrxHelperBase)proxy;
		_delegate = delegate;
		_batchAutoFlush = ref.getInstance().initializationData().properties.getPropertyAsIntWithDefault("Ice.BatchAutoFlush", 1) > 0;
		_initialized = false;
		_flushing = false;
		_batchRequestInProgress = false;
		_batchRequestsSize = Protocol.requestBatchHdr.length;
		_batchStream = new BasicStream(ref.getInstance(), _batchAutoFlush);
		_updateRequestHandler = false;
	}

	private boolean initialized()
	{
		if (_initialized)
			if (!$assertionsDisabled && _connection == null)
				throw new AssertionError();
			else
				return true;
		while (_flushing && _exception == null) 
			try
			{
				wait();
			}
			catch (InterruptedException ex) { }
		if (_exception != null)
			throw (LocalException)_exception.fillInStackTrace();
		else
			return _initialized;
	}

	private void flushRequests()
	{
		synchronized (this)
		{
			if (!$assertionsDisabled && (_connection == null || _initialized))
				throw new AssertionError();
			while (_batchRequestInProgress) 
				try
				{
					wait();
				}
				catch (InterruptedException ex) { }
			_flushing = true;
		}
		List sentCallbacks = new ArrayList();
		try
		{
			for (Iterator p = _requests.iterator(); p.hasNext(); p.remove())
			{
				Request request = (Request)p.next();
				if (request.out != null)
				{
					if ((_connection.sendAsyncRequest(request.out, _compress, _response) & 2) > 0)
						sentCallbacks.add(request.out);
				} else
				if (request.batchOut != null)
				{
					if ((_connection.flushAsyncBatchRequests(request.batchOut) & 2) > 0)
						sentCallbacks.add(request.batchOut);
				} else
				{
					BasicStream os = new BasicStream(request.os.instance());
					_connection.prepareBatchRequest(os);
					try
					{
						request.os.pos(0);
						os.writeBlob(request.os.readBlob(request.os.size()));
					}
					catch (LocalException ex)
					{
						_connection.abortBatchRequest();
						throw ex;
					}
					_connection.finishBatchRequest(os, _compress);
				}
			}

		}
		catch (LocalExceptionWrapper ex)
		{
			synchronized (this)
			{
				if (!$assertionsDisabled && (_exception != null || _requests.isEmpty()))
					throw new AssertionError();
				_exception = ex.get();
				_reference.getInstance().clientThreadPool().execute(new DispatchWorkItem(ex) {

					final LocalExceptionWrapper val$ex;
					final ConnectRequestHandler this$0;

					public void run()
					{
						flushRequestsWithException(ex);
					}

			
			{
				this$0 = ConnectRequestHandler.this;
				ex = localexceptionwrapper;
				super(x0);
			}
				});
			}
		}
		catch (LocalException ex)
		{
			synchronized (this)
			{
				if (!$assertionsDisabled && (_exception != null || _requests.isEmpty()))
					throw new AssertionError();
				_exception = ex;
				_reference.getInstance().clientThreadPool().execute(new DispatchWorkItem(ex) {

					final LocalException val$ex;
					final ConnectRequestHandler this$0;

					public void run()
					{
						flushRequestsWithException(ex);
					}

			
			{
				this$0 = ConnectRequestHandler.this;
				ex = localexception;
				super(x0);
			}
				});
			}
		}
		if (!sentCallbacks.isEmpty())
		{
			final Instance instance = _reference.getInstance();
			instance.clientThreadPool().execute(new DispatchWorkItem(sentCallbacks) {

				final List val$sentCallbacks;
				final ConnectRequestHandler this$0;

				public void run()
				{
					OutgoingAsyncMessageCallback callback;
					for (Iterator i$ = sentCallbacks.iterator(); i$.hasNext(); callback.__sent())
						callback = (OutgoingAsyncMessageCallback)i$.next();

				}

			
			{
				this$0 = ConnectRequestHandler.this;
				sentCallbacks = list;
				super(x0);
			}
			});
		}
		if (_updateRequestHandler && _exception == null)
			_proxy.__setRequestHandler(_delegate, new ConnectionRequestHandler(_reference, _connection, _compress));
		synchronized (this)
		{
			if (!$assertionsDisabled && _initialized)
				throw new AssertionError();
			if (_exception == null)
			{
				_initialized = true;
				_flushing = false;
			}
			_proxy = null;
			_delegate = null;
			notifyAll();
		}
	}

	void flushRequestsWithException(LocalException ex)
	{
		Iterator i$ = _requests.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			Request request = (Request)i$.next();
			if (request.out != null)
				request.out.__finished(ex, false);
			else
			if (request.batchOut != null)
				request.batchOut.__finished(ex, false);
		} while (true);
		_requests.clear();
	}

	void flushRequestsWithException(LocalExceptionWrapper ex)
	{
		Iterator i$ = _requests.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			Request request = (Request)i$.next();
			if (request.out != null)
				request.out.__finished(ex);
			else
			if (request.batchOut != null)
				request.batchOut.__finished(ex.get(), false);
		} while (true);
		_requests.clear();
	}

}
