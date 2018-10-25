// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConnectionI.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.BatchOutgoing;
import IceInternal.BatchOutgoingAsync;
import IceInternal.Buffer;
import IceInternal.CallbackBase;
import IceInternal.ConnectionBatchOutgoingAsync;
import IceInternal.ConnectionMonitor;
import IceInternal.ConnectionReaper;
import IceInternal.Connector;
import IceInternal.DefaultsAndOverrides;
import IceInternal.EndpointI;
import IceInternal.EventHandler;
import IceInternal.Ex;
import IceInternal.Incoming;
import IceInternal.Instance;
import IceInternal.LocalExceptionWrapper;
import IceInternal.Outgoing;
import IceInternal.OutgoingAsync;
import IceInternal.OutgoingAsyncMessageCallback;
import IceInternal.OutgoingMessageCallback;
import IceInternal.Protocol;
import IceInternal.ProxyFactory;
import IceInternal.ReferenceFactory;
import IceInternal.RequestHandler;
import IceInternal.ServantManager;
import IceInternal.ThreadPool;
import IceInternal.ThreadPoolCurrent;
import IceInternal.Time;
import IceInternal.Timer;
import IceInternal.TimerTask;
import IceInternal.TraceLevels;
import IceInternal.TraceUtil;
import IceInternal.Transceiver;
import IceUtilInternal.Assert;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.util.*;

// Referenced classes of package Ice:
//			LocalException, ObjectAdapterDeactivatedException, CommunicatorDestroyedException, ForcedCloseConnectionException, 
//			CloseConnectionException, ConnectionTimeoutException, ObjectAdapterI, IllegalMessageSizeException, 
//			BadMagicException, UnsupportedProtocolException, UnsupportedEncodingException, DatagramLimitException, 
//			SocketException, ConnectTimeoutException, TimeoutException, CloseTimeoutException, 
//			BooleanHolder, SyscallException, ConnectionLostException, ConnectionNotValidatedException, 
//			FeatureNotSupportedException, UnmarshalOutOfBoundsException, UnknownRequestIdException, UnknownMessageException, 
//			UnknownException, Connection, AsyncResult, Logger, 
//			Dispatcher, InitializationData, Properties, ObjectAdapter, 
//			ConnectionInfo, Callback, Callback_Connection_flushBatchRequests, Endpoint, 
//			Identity, ObjectPrx, OperationMode

public final class ConnectionI extends EventHandler
	implements Connection
{
	private static class OutgoingMessage
	{

		public BasicStream stream;
		public OutgoingMessageCallback out;
		public OutgoingAsyncMessageCallback outAsync;
		public boolean compress;
		public int requestId;
		boolean adopt;
		boolean prepared;
		boolean isSent;

		public void adopt()
		{
			if (adopt)
			{
				BasicStream stream = new BasicStream(this.stream.instance());
				stream.swap(this.stream);
				this.stream = stream;
				adopt = false;
			}
		}

		public boolean sent(ConnectionI connection, boolean notify)
		{
			isSent = true;
			if (out != null)
			{
				out.sent(notify);
				return false;
			}
			if (outAsync != null)
				return outAsync.__sent(connection);
			else
				return false;
		}

		public void finished(LocalException ex)
		{
			if (out != null)
				out.finished(ex, isSent);
			else
			if (outAsync != null)
				outAsync.__finished(ex, isSent);
		}

		OutgoingMessage(BasicStream stream, boolean compress, boolean adopt)
		{
			this.stream = stream;
			this.compress = compress;
			this.adopt = adopt;
			isSent = false;
			requestId = 0;
		}

		OutgoingMessage(OutgoingMessageCallback out, BasicStream stream, boolean compress, int requestId)
		{
			this.stream = stream;
			this.compress = compress;
			this.out = out;
			this.requestId = requestId;
			isSent = false;
		}

		OutgoingMessage(OutgoingAsyncMessageCallback out, BasicStream stream, boolean compress, int requestId)
		{
			this.stream = stream;
			this.compress = compress;
			outAsync = out;
			this.requestId = requestId;
			isSent = false;
		}
	}

	private static class MessageInfo
	{

		BasicStream stream;
		int invokeNum;
		int requestId;
		byte compress;
		ServantManager servantManager;
		ObjectAdapter adapter;
		OutgoingAsync outAsync;

		MessageInfo(BasicStream stream)
		{
			this.stream = stream;
		}
	}

	private class TimeoutCallback
		implements TimerTask
	{

		final ConnectionI this$0;

		public void runTimerTask()
		{
			timedOut();
		}

		private TimeoutCallback()
		{
			this$0 = ConnectionI.this;
			super();
		}

	}

	public static interface StartCallback
	{

		public abstract void connectionStartCompleted(ConnectionI connectioni);

		public abstract void connectionStartFailed(ConnectionI connectioni, LocalException localexception);
	}


	public static final int ObjectAdapterDeactivated = 0;
	public static final int CommunicatorDestroyed = 1;
	private static final String __flushBatchRequests_name = "flushBatchRequests";
	private static final int StateNotInitialized = 0;
	private static final int StateNotValidated = 1;
	private static final int StateActive = 2;
	private static final int StateHolding = 3;
	private static final int StateClosing = 4;
	private static final int StateClosed = 5;
	private static final int StateFinished = 6;
	private final Instance _instance;
	private final ConnectionReaper _reaper;
	private final Transceiver _transceiver;
	private String _desc;
	private final String _type;
	private final Connector _connector;
	private final EndpointI _endpoint;
	private ObjectAdapter _adapter;
	private ServantManager _servantManager;
	private final Dispatcher _dispatcher;
	private final Logger _logger;
	private final TraceLevels _traceLevels;
	private final ThreadPool _threadPool;
	private final Timer _timer;
	private final TimerTask _writeTimeout = new TimeoutCallback();
	private boolean _writeTimeoutScheduled;
	private final TimerTask _readTimeout = new TimeoutCallback();
	private boolean _readTimeoutScheduled;
	private StartCallback _startCallback;
	private BooleanHolder _hasMoreData;
	private final boolean _warn;
	private final boolean _warnUdp;
	private final long _acmTimeout;
	private long _acmAbsoluteTimeoutMillis;
	private final int _compressionLevel;
	private int _nextRequestId;
	private Map _requests;
	private Map _asyncRequests;
	private LocalException _exception;
	private boolean _batchAutoFlush;
	private BasicStream _batchStream;
	private boolean _batchStreamInUse;
	private int _batchRequestNum;
	private boolean _batchRequestCompress;
	private int _batchMarker;
	private LinkedList _sendStreams;
	private BasicStream _readStream;
	private boolean _readHeader;
	private BasicStream _writeStream;
	private int _dispatchCount;
	private int _state;
	private Incoming _incomingCache;
	private Object _incomingCacheMutex;
	private Outgoing _outgoingCache;
	private Object _outgoingCacheMutex;
	private int _cacheBuffers;
	static final boolean $assertionsDisabled = !Ice/ConnectionI.desiredAssertionStatus();

	public void start(StartCallback callback)
	{
		try
		{
label0:
			{
				synchronized (this)
				{
					if (_state >= 5)
						if (!$assertionsDisabled && _exception == null)
							throw new AssertionError();
						else
							throw (LocalException)_exception.fillInStackTrace();
					if (initialize(0) && validate(0))
						break MISSING_BLOCK_LABEL_131;
					if (callback == null)
						break label0;
					_startCallback = callback;
				}
				return;
			}
		}
		catch (LocalException ex)
		{
			exception(ex);
			if (callback != null)
			{
				callback.connectionStartFailed(this, _exception);
				return;
			} else
			{
				waitUntilFinished();
				throw ex;
			}
		}
		while (_state <= 1) 
			try
			{
				wait();
			}
			catch (InterruptedException ex) { }
		if (_state >= 4)
			if (!$assertionsDisabled && _exception == null)
				throw new AssertionError();
			else
				throw (LocalException)_exception.fillInStackTrace();
		setState(3);
		connectioni;
		JVM INSTR monitorexit ;
		  goto _L1
		exception1;
		throw exception1;
_L1:
		if (callback != null)
			callback.connectionStartCompleted(this);
		return;
	}

	public synchronized void activate()
	{
		if (_state <= 1)
			return;
		if (_acmTimeout > 0L)
			_acmAbsoluteTimeoutMillis = Time.currentMonotonicTimeMillis() + _acmTimeout * 1000L;
		setState(2);
	}

	public synchronized void hold()
	{
		if (_state <= 1)
		{
			return;
		} else
		{
			setState(3);
			return;
		}
	}

	public synchronized void destroy(int reason)
	{
		switch (reason)
		{
		case 0: // '\0'
			setState(4, new ObjectAdapterDeactivatedException());
			break;

		case 1: // '\001'
			setState(4, new CommunicatorDestroyedException());
			break;
		}
	}

	public synchronized void close(boolean force)
	{
		if (force)
		{
			setState(5, new ForcedCloseConnectionException());
		} else
		{
			while (!_requests.isEmpty() || !_asyncRequests.isEmpty()) 
				try
				{
					wait();
				}
				catch (InterruptedException ex) { }
			setState(4, new CloseConnectionException());
		}
	}

	public synchronized boolean isActiveOrHolding()
	{
		return _state > 1 && _state < 4;
	}

	public synchronized boolean isFinished()
	{
		if (_state != 6 || _dispatchCount != 0)
			return false;
		if (!$assertionsDisabled && _state != 6)
			throw new AssertionError();
		else
			return true;
	}

	public synchronized void throwException()
	{
		if (_exception != null)
		{
			if (!$assertionsDisabled && _state < 4)
				throw new AssertionError();
			else
				throw (LocalException)_exception.fillInStackTrace();
		} else
		{
			return;
		}
	}

	public synchronized void waitUntilHolding()
	{
		while (_state < 3 || _dispatchCount > 0) 
			try
			{
				wait();
			}
			catch (InterruptedException ex) { }
	}

	public synchronized void waitUntilFinished()
	{
		while (_state < 6 || _dispatchCount > 0) 
			try
			{
				wait();
			}
			catch (InterruptedException ex) { }
		if (!$assertionsDisabled && _state != 6)
		{
			throw new AssertionError();
		} else
		{
			_adapter = null;
			return;
		}
	}

	public synchronized void monitor(long now)
	{
		if (_state != 2)
			return;
		if (_acmTimeout <= 0L || !_requests.isEmpty() || !_asyncRequests.isEmpty() || _dispatchCount > 0 || _readStream.size() > 14 || !_writeStream.isEmpty() || !_batchStream.isEmpty())
			return;
		if (now >= _acmAbsoluteTimeoutMillis)
			setState(4, new ConnectionTimeoutException());
	}

	public synchronized boolean sendRequest(Outgoing out, boolean compress, boolean response)
		throws LocalExceptionWrapper
	{
		int requestId = 0;
		BasicStream os = out.os();
		if (_exception != null)
			throw new LocalExceptionWrapper((LocalException)_exception.fillInStackTrace(), true);
		if (!$assertionsDisabled && _state <= 1)
			throw new AssertionError();
		if (!$assertionsDisabled && _state >= 4)
			throw new AssertionError();
		_transceiver.checkSendSize(os.getBuffer(), _instance.messageSizeMax());
		if (response)
		{
			requestId = _nextRequestId++;
			if (requestId <= 0)
			{
				_nextRequestId = 1;
				requestId = _nextRequestId++;
			}
			os.pos(14);
			os.writeInt(requestId);
		}
		boolean sent = false;
		try
		{
			OutgoingMessage message = new OutgoingMessage(out, out.os(), compress, requestId);
			sent = (sendMessage(message) & 1) > 0;
		}
		catch (LocalException ex)
		{
			setState(5, ex);
			if (!$assertionsDisabled && _exception == null)
				throw new AssertionError();
			else
				throw (LocalException)_exception.fillInStackTrace();
		}
		if (response)
			_requests.put(Integer.valueOf(requestId), out);
		return sent;
	}

	public synchronized int sendAsyncRequest(OutgoingAsync out, boolean compress, boolean response)
		throws LocalExceptionWrapper
	{
		int requestId = 0;
		BasicStream os = out.__os();
		if (_exception != null)
			throw new LocalExceptionWrapper((LocalException)_exception.fillInStackTrace(), true);
		if (!$assertionsDisabled && _state <= 1)
			throw new AssertionError();
		if (!$assertionsDisabled && _state >= 4)
			throw new AssertionError();
		_transceiver.checkSendSize(os.getBuffer(), _instance.messageSizeMax());
		if (response)
		{
			requestId = _nextRequestId++;
			if (requestId <= 0)
			{
				_nextRequestId = 1;
				requestId = _nextRequestId++;
			}
			os.pos(14);
			os.writeInt(requestId);
		}
		int status;
		try
		{
			status = sendMessage(new OutgoingMessage(out, out.__os(), compress, requestId));
		}
		catch (LocalException ex)
		{
			setState(5, ex);
			if (!$assertionsDisabled && _exception == null)
				throw new AssertionError();
			else
				throw (LocalException)_exception.fillInStackTrace();
		}
		if (response)
			_asyncRequests.put(Integer.valueOf(requestId), out);
		return status;
	}

	public synchronized void prepareBatchRequest(BasicStream os)
		throws LocalExceptionWrapper
	{
		while (_batchStreamInUse && _exception == null) 
			try
			{
				wait();
			}
			catch (InterruptedException ex) { }
		if (_exception != null)
			if (_batchStream.isEmpty())
				throw new LocalExceptionWrapper((LocalException)_exception.fillInStackTrace(), true);
			else
				throw (LocalException)_exception.fillInStackTrace();
		if (!$assertionsDisabled && _state <= 1)
			throw new AssertionError();
		if (!$assertionsDisabled && _state >= 4)
			throw new AssertionError();
		if (_batchStream.isEmpty())
			try
			{
				_batchStream.writeBlob(Protocol.requestBatchHdr);
			}
			catch (LocalException ex)
			{
				setState(5, ex);
				throw ex;
			}
		_batchStreamInUse = true;
		_batchMarker = _batchStream.size();
		_batchStream.swap(os);
	}

	public void finishBatchRequest(BasicStream os, boolean compress)
	{
		try
		{
			synchronized (this)
			{
				_batchStream.swap(os);
				if (_exception != null)
					throw (LocalException)_exception.fillInStackTrace();
				boolean flush = false;
				if (_batchAutoFlush)
					try
					{
						_transceiver.checkSendSize(_batchStream.getBuffer(), _instance.messageSizeMax());
					}
					catch (LocalException ex)
					{
						if (_batchRequestNum > 0)
							flush = true;
						else
							throw ex;
					}
				if (flush)
				{
					byte lastRequest[] = new byte[_batchStream.size() - _batchMarker];
					Buffer buffer = _batchStream.getBuffer();
					buffer.b.position(_batchMarker);
					buffer.b.get(lastRequest);
					_batchStream.resize(_batchMarker, false);
					try
					{
						_batchStream.pos(14);
						_batchStream.writeInt(_batchRequestNum);
						OutgoingMessage message = new OutgoingMessage(_batchStream, _batchRequestCompress, true);
						sendMessage(message);
					}
					catch (LocalException ex)
					{
						setState(5, ex);
						if (!$assertionsDisabled && _exception == null)
							throw new AssertionError();
						else
							throw (LocalException)_exception.fillInStackTrace();
					}
					_batchStream = new BasicStream(_instance, _batchAutoFlush);
					_batchRequestNum = 0;
					_batchRequestCompress = false;
					_batchMarker = 0;
					if (Protocol.requestBatchHdr.length + lastRequest.length > _instance.messageSizeMax())
						Ex.throwMemoryLimitException(Protocol.requestBatchHdr.length + lastRequest.length, _instance.messageSizeMax());
					_batchStream.writeBlob(Protocol.requestBatchHdr);
					_batchStream.writeBlob(lastRequest);
				}
				_batchRequestNum++;
				if (compress)
					_batchRequestCompress = true;
				if (!$assertionsDisabled && !_batchStreamInUse)
					throw new AssertionError();
				_batchStreamInUse = false;
				notifyAll();
			}
		}
		catch (LocalException ex)
		{
			abortBatchRequest();
			throw ex;
		}
	}

	public synchronized void abortBatchRequest()
	{
		_batchStream = new BasicStream(_instance, _batchAutoFlush);
		_batchRequestNum = 0;
		_batchRequestCompress = false;
		_batchMarker = 0;
		if (!$assertionsDisabled && !_batchStreamInUse)
		{
			throw new AssertionError();
		} else
		{
			_batchStreamInUse = false;
			notifyAll();
			return;
		}
	}

	public void flushBatchRequests()
	{
		BatchOutgoing out = new BatchOutgoing(this, _instance);
		out.invoke();
	}

	public AsyncResult begin_flushBatchRequests()
	{
		return begin_flushBatchRequestsInternal(null);
	}

	public AsyncResult begin_flushBatchRequests(Callback cb)
	{
		return begin_flushBatchRequestsInternal(cb);
	}

	public AsyncResult begin_flushBatchRequests(Callback_Connection_flushBatchRequests cb)
	{
		return begin_flushBatchRequestsInternal(cb);
	}

	private AsyncResult begin_flushBatchRequestsInternal(CallbackBase cb)
	{
		ConnectionBatchOutgoingAsync result = new ConnectionBatchOutgoingAsync(this, _instance, "flushBatchRequests", cb);
		try
		{
			result.__send();
		}
		catch (LocalException __ex)
		{
			result.__exceptionAsync(__ex);
		}
		return result;
	}

	public void end_flushBatchRequests(AsyncResult r)
	{
		AsyncResult.__check(r, this, "flushBatchRequests");
		r.__wait();
	}

	public synchronized boolean flushBatchRequests(BatchOutgoing out)
	{
		while (_batchStreamInUse && _exception == null) 
			try
			{
				wait();
			}
			catch (InterruptedException ex) { }
		if (_exception != null)
			throw (LocalException)_exception.fillInStackTrace();
		if (_batchRequestNum == 0)
		{
			out.sent(false);
			return true;
		}
		_batchStream.pos(14);
		_batchStream.writeInt(_batchRequestNum);
		_batchStream.swap(out.os());
		boolean sent = false;
		try
		{
			OutgoingMessage message = new OutgoingMessage(out, out.os(), _batchRequestCompress, 0);
			sent = (sendMessage(message) & 1) > 0;
		}
		catch (LocalException ex)
		{
			setState(5, ex);
			if (!$assertionsDisabled && _exception == null)
				throw new AssertionError();
			else
				throw (LocalException)_exception.fillInStackTrace();
		}
		_batchStream = new BasicStream(_instance, _batchAutoFlush);
		_batchRequestNum = 0;
		_batchRequestCompress = false;
		_batchMarker = 0;
		return sent;
	}

	public synchronized int flushAsyncBatchRequests(BatchOutgoingAsync outAsync)
	{
		while (_batchStreamInUse && _exception == null) 
			try
			{
				wait();
			}
			catch (InterruptedException ex) { }
		if (_exception != null)
			throw (LocalException)_exception.fillInStackTrace();
		int status;
		if (_batchRequestNum == 0)
		{
			status = 1;
			if (outAsync.__sent(this))
				status |= 2;
			return status;
		}
		_batchStream.pos(14);
		_batchStream.writeInt(_batchRequestNum);
		_batchStream.swap(outAsync.__os());
		try
		{
			OutgoingMessage message = new OutgoingMessage(outAsync, outAsync.__os(), _batchRequestCompress, 0);
			status = sendMessage(message);
		}
		catch (LocalException ex)
		{
			setState(5, ex);
			if (!$assertionsDisabled && _exception == null)
				throw new AssertionError();
			else
				throw (LocalException)_exception.fillInStackTrace();
		}
		_batchStream = new BasicStream(_instance, _batchAutoFlush);
		_batchRequestNum = 0;
		_batchRequestCompress = false;
		_batchMarker = 0;
		return status;
	}

	public synchronized void sendResponse(BasicStream os, byte compressFlag)
	{
		if (!$assertionsDisabled && _state <= 1)
			throw new AssertionError();
		try
		{
			if (--_dispatchCount == 0)
			{
				if (_state == 6)
					_reaper.add(this);
				notifyAll();
			}
			if (_state >= 5)
				if (!$assertionsDisabled && _exception == null)
					throw new AssertionError();
				else
					throw (LocalException)_exception.fillInStackTrace();
			sendMessage(new OutgoingMessage(os, compressFlag != 0, true));
			if (_state == 4 && _dispatchCount == 0)
				initiateShutdown();
		}
		catch (LocalException ex)
		{
			setState(5, ex);
		}
	}

	public synchronized void sendNoResponse()
	{
		if (!$assertionsDisabled && _state <= 1)
			throw new AssertionError();
		try
		{
			if (--_dispatchCount == 0)
			{
				if (_state == 6)
					_reaper.add(this);
				notifyAll();
			}
			if (_state >= 5)
				if (!$assertionsDisabled && _exception == null)
					throw new AssertionError();
				else
					throw (LocalException)_exception.fillInStackTrace();
			if (_state == 4 && _dispatchCount == 0)
				initiateShutdown();
		}
		catch (LocalException ex)
		{
			setState(5, ex);
		}
	}

	public EndpointI endpoint()
	{
		return _endpoint;
	}

	public Connector connector()
	{
		return _connector;
	}

	public synchronized void setAdapter(ObjectAdapter adapter)
	{
		if (_state <= 1 || _state >= 4)
			return;
		if (!$assertionsDisabled && _state >= 4)
			throw new AssertionError();
		_adapter = adapter;
		if (_adapter != null)
		{
			_servantManager = ((ObjectAdapterI)_adapter).getServantManager();
			if (_servantManager == null)
				_adapter = null;
		} else
		{
			_servantManager = null;
		}
	}

	public synchronized ObjectAdapter getAdapter()
	{
		return _adapter;
	}

	public Endpoint getEndpoint()
	{
		return _endpoint;
	}

	public ObjectPrx createProxy(Identity ident)
	{
		return _instance.proxyFactory().referenceToProxy(_instance.referenceFactory().create(ident, this));
	}

	public void message(ThreadPoolCurrent current)
	{
		StartCallback startCB;
		List sentCBs;
		MessageInfo info;
label0:
		{
			startCB = null;
			sentCBs = null;
			info = null;
			synchronized (this)
			{
				if (_state < 5)
					break label0;
			}
			return;
		}
		unscheduleTimeout(current.operation);
		if ((current.operation & 4) == 0 || _writeStream.isEmpty())
			break MISSING_BLOCK_LABEL_140;
		if (_transceiver.write(_writeStream.getBuffer()))
			break MISSING_BLOCK_LABEL_110;
		if (!$assertionsDisabled && _writeStream.isEmpty())
			throw new AssertionError();
		scheduleTimeout(4, _endpoint.timeout());
		connectioni;
		JVM INSTR monitorexit ;
		return;
		if (!$assertionsDisabled && _writeStream.getBuffer().b.hasRemaining())
			throw new AssertionError();
		if ((current.operation & 1) == 0 || _readStream.isEmpty())
			break MISSING_BLOCK_LABEL_789;
		if (!_readHeader)
			break MISSING_BLOCK_LABEL_661;
		if (_transceiver.read(_readStream.getBuffer(), _hasMoreData))
			break MISSING_BLOCK_LABEL_193;
		connectioni;
		JVM INSTR monitorexit ;
		return;
		if (!$assertionsDisabled && _readStream.getBuffer().b.hasRemaining())
			throw new AssertionError();
		_readHeader = false;
		int pos = _readStream.pos();
		if (pos < 14)
			throw new IllegalMessageSizeException();
		_readStream.pos(0);
		byte m[] = new byte[4];
		m[0] = _readStream.readByte();
		m[1] = _readStream.readByte();
		m[2] = _readStream.readByte();
		m[3] = _readStream.readByte();
		if (m[0] != Protocol.magic[0] || m[1] != Protocol.magic[1] || m[2] != Protocol.magic[2] || m[3] != Protocol.magic[3])
		{
			BadMagicException ex = new BadMagicException();
			ex.badMagic = m;
			throw ex;
		}
		byte pMajor = _readStream.readByte();
		byte pMinor = _readStream.readByte();
		if (pMajor != 1 || pMinor > 0)
		{
			UnsupportedProtocolException e = new UnsupportedProtocolException();
			e.badMajor = pMajor >= 0 ? ((int) (pMajor)) : pMajor + 255;
			e.badMinor = pMinor >= 0 ? ((int) (pMinor)) : pMinor + 255;
			e.major = 1;
			e.minor = 0;
			throw e;
		}
		byte eMajor = _readStream.readByte();
		byte eMinor = _readStream.readByte();
		if (eMajor != 1 || eMinor > 0)
		{
			UnsupportedEncodingException e = new UnsupportedEncodingException();
			e.badMajor = eMajor >= 0 ? ((int) (eMajor)) : eMajor + 255;
			e.badMinor = eMinor >= 0 ? ((int) (eMinor)) : eMinor + 255;
			e.major = 1;
			e.minor = 0;
			throw e;
		}
		_readStream.readByte();
		_readStream.readByte();
		int size = _readStream.readInt();
		if (size < 14)
			throw new IllegalMessageSizeException();
		if (size > _instance.messageSizeMax())
			Ex.throwMemoryLimitException(size, _instance.messageSizeMax());
		if (size > _readStream.size())
			_readStream.resize(size, true);
		_readStream.pos(pos);
		if (_readStream.pos() == _readStream.size())
			break MISSING_BLOCK_LABEL_789;
		if (_endpoint.datagram())
			throw new DatagramLimitException();
		if (_transceiver.read(_readStream.getBuffer(), _hasMoreData))
			break MISSING_BLOCK_LABEL_759;
		if (!$assertionsDisabled && _readStream.isEmpty())
			throw new AssertionError();
		scheduleTimeout(1, _endpoint.timeout());
		connectioni;
		JVM INSTR monitorexit ;
		return;
		if (!$assertionsDisabled && _readStream.getBuffer().b.hasRemaining())
			throw new AssertionError();
		if (_state > 1)
			break MISSING_BLOCK_LABEL_872;
		if (_state != 0 || initialize(current.operation))
			break MISSING_BLOCK_LABEL_819;
		connectioni;
		JVM INSTR monitorexit ;
		return;
		if (_state > 1 || validate(current.operation))
			break MISSING_BLOCK_LABEL_842;
		connectioni;
		JVM INSTR monitorexit ;
		return;
		_threadPool.unregister(this, current.operation);
		setState(3);
		startCB = _startCallback;
		_startCallback = null;
		break MISSING_BLOCK_LABEL_1146;
		if (!$assertionsDisabled && _state > 4)
			throw new AssertionError();
		if ((current.operation & 4) != 0)
			sentCBs = sendNextMessage();
		if ((current.operation & 1) != 0)
			info = parseMessage(current.stream);
		if (sentCBs != null || info != null && info.outAsync != null)
			_dispatchCount++;
		break MISSING_BLOCK_LABEL_1146;
		DatagramLimitException ex;
		ex;
		if (_warnUdp)
			_logger.warning((new StringBuilder()).append("maximum datagram size of ").append(_readStream.pos()).append(" exceeded").toString());
		_readStream.resize(14, true);
		_readStream.pos(0);
		_readHeader = true;
		connectioni;
		JVM INSTR monitorexit ;
		return;
		ex;
		setState(5, ex);
		connectioni;
		JVM INSTR monitorexit ;
		return;
		ex;
		if (_endpoint.datagram())
		{
			if (_warn)
			{
				String s = (new StringBuilder()).append("datagram connection exception:\n").append(ex).append('\n').append(_desc).toString();
				_logger.warning(s);
			}
			_readStream.resize(14, true);
			_readStream.pos(0);
			_readHeader = true;
		} else
		{
			setState(5, ex);
		}
		connectioni;
		JVM INSTR monitorexit ;
		return;
		if (_acmTimeout > 0L)
			_acmAbsoluteTimeoutMillis = Time.currentMonotonicTimeMillis() + _acmTimeout * 1000L;
		current.ioCompleted();
		connectioni;
		JVM INSTR monitorexit ;
		  goto _L1
		exception1;
		throw exception1;
_L1:
		if (_dispatcher != null)
		{
			if (info != null)
			{
				if (!$assertionsDisabled && info.stream != current.stream)
					throw new AssertionError();
				BasicStream stream = info.stream;
				info.stream = new BasicStream(_instance);
				info.stream.swap(stream);
			}
			final StartCallback finalStartCB = startCB;
			final List finalSentCBs = sentCBs;
			final MessageInfo finalInfo = info;
			try
			{
				_dispatcher.dispatch(new Runnable() {

					final StartCallback val$finalStartCB;
					final List val$finalSentCBs;
					final MessageInfo val$finalInfo;
					final ConnectionI this$0;

					public void run()
					{
						dispatch(finalStartCB, finalSentCBs, finalInfo);
					}

			
			{
				this$0 = ConnectionI.this;
				finalStartCB = startcallback;
				finalSentCBs = list;
				finalInfo = messageinfo;
				super();
			}
				}, this);
			}
			catch (Exception ex)
			{
				if (_instance.initializationData().properties.getPropertyAsIntWithDefault("Ice.Warn.Dispatch", 1) > 1)
					warning("dispatch exception", ex);
			}
		} else
		{
			dispatch(startCB, sentCBs, info);
		}
		return;
	}

	protected void dispatch(StartCallback startCB, List sentCBs, MessageInfo info)
	{
		if (startCB != null)
			startCB.connectionStartCompleted(this);
		if (sentCBs != null)
		{
			OutgoingMessage msg;
			for (Iterator i$ = sentCBs.iterator(); i$.hasNext(); msg.outAsync.__sent())
				msg = (OutgoingMessage)i$.next();

		}
		if (info != null)
		{
			if (info.outAsync != null)
				info.outAsync.__finished(info.stream);
			if (info.invokeNum > 0)
				invokeAll(info.stream, info.invokeNum, info.requestId, info.compress, info.servantManager, info.adapter);
		}
		if (sentCBs != null || info != null && info.outAsync != null)
			synchronized (this)
			{
				if (--_dispatchCount == 0)
				{
					if (_state == 4)
						try
						{
							initiateShutdown();
						}
						catch (LocalException ex)
						{
							setState(5, ex);
						}
					else
					if (_state == 6)
						_reaper.add(this);
					notifyAll();
				}
			}
	}

	public void finished(ThreadPoolCurrent current)
	{
		if (_startCallback == null && _sendStreams.isEmpty() && _asyncRequests.isEmpty())
		{
			finish();
			return;
		}
		if (_dispatcher == null)
		{
			current.ioCompleted();
			finish();
		} else
		{
			try
			{
				_dispatcher.dispatch(new Runnable() {

					final ConnectionI this$0;

					public void run()
					{
						finish();
					}

			
			{
				this$0 = ConnectionI.this;
				super();
			}
				}, this);
			}
			catch (Exception ex)
			{
				if (_instance.initializationData().properties.getPropertyAsIntWithDefault("Ice.Warn.Dispatch", 1) > 1)
					warning("dispatch exception", ex);
			}
		}
	}

	public void finish()
	{
		synchronized (this)
		{
			if (!$assertionsDisabled && _state != 5)
				throw new AssertionError();
			unscheduleTimeout(13);
		}
		if (_startCallback != null)
		{
			_startCallback.connectionStartFailed(this, _exception);
			_startCallback = null;
		}
		if (!_sendStreams.isEmpty())
		{
			OutgoingMessage message = (OutgoingMessage)_sendStreams.getFirst();
			_writeStream.swap(message.stream);
			OutgoingMessage p;
			for (Iterator i$ = _sendStreams.iterator(); i$.hasNext(); p.finished(_exception))
			{
				p = (OutgoingMessage)i$.next();
				if (p.requestId <= 0)
					continue;
				if (p.out != null)
					_requests.remove(Integer.valueOf(p.requestId));
				else
					_asyncRequests.remove(Integer.valueOf(p.requestId));
			}

			_sendStreams.clear();
		}
		Outgoing p;
		for (Iterator i$ = _requests.values().iterator(); i$.hasNext(); p.finished(_exception, true))
			p = (Outgoing)i$.next();

		_requests.clear();
		OutgoingAsync p;
		for (Iterator i$ = _asyncRequests.values().iterator(); i$.hasNext(); p.__finished(_exception, true))
			p = (OutgoingAsync)i$.next();

		_asyncRequests.clear();
		synchronized (this)
		{
			setState(6);
			if (_dispatchCount == 0)
				_reaper.add(this);
		}
	}

	public String toString()
	{
		return _toString();
	}

	public SelectableChannel fd()
	{
		return _transceiver.fd();
	}

	public boolean hasMoreData()
	{
		return _hasMoreData.value;
	}

	public synchronized void timedOut()
	{
		if (_state <= 1)
			setState(5, new ConnectTimeoutException());
		else
		if (_state < 4)
			setState(5, new TimeoutException());
		else
		if (_state == 4)
			setState(5, new CloseTimeoutException());
	}

	public String type()
	{
		return _type;
	}

	public int timeout()
	{
		return _endpoint.timeout();
	}

	public synchronized ConnectionInfo getInfo()
	{
		if (_state >= 5)
		{
			throw (LocalException)_exception.fillInStackTrace();
		} else
		{
			ConnectionInfo info = _transceiver.getInfo();
			info.adapterName = _adapter == null ? "" : _adapter.getName();
			info.incoming = _connector == null;
			return info;
		}
	}

	public String _toString()
	{
		return _desc;
	}

	public synchronized void exception(LocalException ex)
	{
		setState(5, ex);
	}

	public synchronized void invokeException(LocalException ex, int invokeNum)
	{
		setState(5, ex);
		if (invokeNum > 0)
		{
			if (!$assertionsDisabled && _dispatchCount <= 0)
				throw new AssertionError();
			_dispatchCount -= invokeNum;
			if (!$assertionsDisabled && _dispatchCount < 0)
				throw new AssertionError();
			if (_dispatchCount == 0)
			{
				if (_state == 6)
					_reaper.add(this);
				notifyAll();
			}
		}
	}

	public ConnectionI(Instance instance, ConnectionReaper reaper, Transceiver transceiver, Connector connector, EndpointI endpoint, ObjectAdapter adapter)
	{
		_startCallback = null;
		_hasMoreData = new BooleanHolder(false);
		_requests = new HashMap();
		_asyncRequests = new HashMap();
		_sendStreams = new LinkedList();
		_incomingCacheMutex = new Object();
		_outgoingCacheMutex = new Object();
		_instance = instance;
		_reaper = reaper;
		_transceiver = transceiver;
		_desc = transceiver.toString();
		_type = transceiver.type();
		_connector = connector;
		_endpoint = endpoint;
		_adapter = adapter;
		InitializationData initData = instance.initializationData();
		_dispatcher = initData.dispatcher;
		_logger = initData.logger;
		_traceLevels = instance.traceLevels();
		_timer = instance.timer();
		_writeTimeoutScheduled = false;
		_readTimeoutScheduled = false;
		_warn = initData.properties.getPropertyAsInt("Ice.Warn.Connections") > 0;
		_warnUdp = instance.initializationData().properties.getPropertyAsInt("Ice.Warn.Datagrams") > 0;
		_cacheBuffers = instance.cacheMessageBuffers();
		_acmAbsoluteTimeoutMillis = 0L;
		_nextRequestId = 1;
		_batchAutoFlush = initData.properties.getPropertyAsIntWithDefault("Ice.BatchAutoFlush", 1) > 0;
		_batchStream = new BasicStream(instance, _batchAutoFlush);
		_batchStreamInUse = false;
		_batchRequestNum = 0;
		_batchRequestCompress = false;
		_batchMarker = 0;
		_readStream = new BasicStream(instance);
		_readHeader = false;
		_writeStream = new BasicStream(instance);
		_dispatchCount = 0;
		_state = 0;
		int compressionLevel = initData.properties.getPropertyAsIntWithDefault("Ice.Compression.Level", 1);
		if (compressionLevel < 1)
			compressionLevel = 1;
		else
		if (compressionLevel > 9)
			compressionLevel = 9;
		_compressionLevel = compressionLevel;
		if (_adapter != null)
			_servantManager = ((ObjectAdapterI)_adapter).getServantManager();
		else
			_servantManager = null;
		try
		{
			if (_endpoint.datagram())
				_acmTimeout = 0L;
			else
			if (_adapter != null)
				_acmTimeout = ((ObjectAdapterI)_adapter).getACM();
			else
				_acmTimeout = _instance.clientACM();
			if (_adapter != null)
				_threadPool = ((ObjectAdapterI)_adapter).getThreadPool();
			else
				_threadPool = _instance.clientThreadPool();
			_threadPool.initialize(this);
		}
		catch (LocalException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			throw new SyscallException(ex);
		}
	}

	protected synchronized void finalize()
		throws Throwable
	{
		Assert.FinalizerAssert(_startCallback == null);
		Assert.FinalizerAssert(_state == 6);
		Assert.FinalizerAssert(_dispatchCount == 0);
		Assert.FinalizerAssert(_sendStreams.isEmpty());
		Assert.FinalizerAssert(_requests.isEmpty());
		Assert.FinalizerAssert(_asyncRequests.isEmpty());
		super.finalize();
	}

	private void setState(int state, LocalException ex)
	{
		if (!$assertionsDisabled && state < 4)
			throw new AssertionError();
		if (_state == state)
			return;
		if (_exception == null)
		{
			_exception = ex;
			if (_warn && _state > 1 && !(_exception instanceof CloseConnectionException) && !(_exception instanceof ForcedCloseConnectionException) && !(_exception instanceof ConnectionTimeoutException) && !(_exception instanceof CommunicatorDestroyedException) && !(_exception instanceof ObjectAdapterDeactivatedException) && (!(_exception instanceof ConnectionLostException) || _state != 4))
				warning("connection exception", _exception);
		}
		setState(state);
	}

	private void setState(int state)
	{
		if (_endpoint.datagram() && state == 4)
			state = 5;
		if (_state <= 1 && state == 4)
			state = 5;
		if (_state == state)
			return;
		state;
		JVM INSTR tableswitch 0 6: default 275
	//	               0 84
	//	               1 98
	//	               2 128
	//	               3 157
	//	               4 194
	//	               5 223
	//	               6 244;
		   goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8
_L1:
		break; /* Loop/switch isn't completed */
_L2:
		if (!$assertionsDisabled)
			throw new AssertionError();
		break; /* Loop/switch isn't completed */
_L3:
		if (_state != 0)
			if (!$assertionsDisabled && _state != 5)
				throw new AssertionError();
			else
				return;
		break; /* Loop/switch isn't completed */
_L4:
		if (_state != 3 && _state != 1)
			return;
		_threadPool.register(this, 1);
		break; /* Loop/switch isn't completed */
_L5:
		if (_state != 2 && _state != 1)
			return;
		if (_state == 2)
			_threadPool.unregister(this, 1);
		break; /* Loop/switch isn't completed */
_L6:
		if (_state >= 5)
			return;
		if (_state == 3)
			_threadPool.register(this, 1);
		break; /* Loop/switch isn't completed */
_L7:
		if (_state == 6)
			return;
		_threadPool.finish(this);
		break; /* Loop/switch isn't completed */
_L8:
		if (!$assertionsDisabled && _state != 5)
			throw new AssertionError();
		_transceiver.close();
		break; /* Loop/switch isn't completed */
		LocalException ex;
		ex;
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		pw.flush();
		String s = (new StringBuilder()).append("unexpected connection exception:\n ").append(_desc).append("\n").append(sw.toString()).toString();
		_instance.initializationData().logger.error(s);
		if (_acmTimeout > 0L)
			if (state == 2)
				_instance.connectionMonitor().add(this);
			else
			if (_state == 2)
				_instance.connectionMonitor().remove(this);
		_state = state;
		notifyAll();
		if (_state == 4 && _dispatchCount == 0)
			try
			{
				initiateShutdown();
			}
			// Misplaced declaration of an exception variable
			catch (LocalException ex)
			{
				setState(5, ex);
			}
		return;
	}

	private void initiateShutdown()
	{
		if (!$assertionsDisabled && _state != 4)
			throw new AssertionError();
		if (!$assertionsDisabled && _dispatchCount != 0)
			throw new AssertionError();
		if (!_endpoint.datagram())
		{
			BasicStream os = new BasicStream(_instance, false, false);
			os.writeBlob(Protocol.magic);
			os.writeByte((byte)1);
			os.writeByte((byte)0);
			os.writeByte((byte)1);
			os.writeByte((byte)0);
			os.writeByte((byte)4);
			os.writeByte((byte)0);
			os.writeInt(14);
			if ((sendMessage(new OutgoingMessage(os, false, false)) & 1) > 0)
				scheduleTimeout(4, closeTimeout());
		}
	}

	private boolean initialize(int operation)
	{
		int s = _transceiver.initialize();
		if (s != 0)
		{
			scheduleTimeout(s, connectTimeout());
			_threadPool.update(this, operation, s);
			return false;
		} else
		{
			_desc = _transceiver.toString();
			setState(1);
			return true;
		}
	}

	private boolean validate(int operation)
	{
		if (!_endpoint.datagram())
			if (_adapter != null)
			{
				if (_writeStream.size() == 0)
				{
					_writeStream.writeBlob(Protocol.magic);
					_writeStream.writeByte((byte)1);
					_writeStream.writeByte((byte)0);
					_writeStream.writeByte((byte)1);
					_writeStream.writeByte((byte)0);
					_writeStream.writeByte((byte)3);
					_writeStream.writeByte((byte)0);
					_writeStream.writeInt(14);
					TraceUtil.traceSend(_writeStream, _logger, _traceLevels);
					_writeStream.prepareWrite();
				}
				if (_writeStream.pos() != _writeStream.size() && !_transceiver.write(_writeStream.getBuffer()))
				{
					scheduleTimeout(4, connectTimeout());
					_threadPool.update(this, operation, 4);
					return false;
				}
			} else
			{
				if (_readStream.size() == 0)
				{
					_readStream.resize(14, true);
					_readStream.pos(0);
				}
				if (_readStream.pos() != _readStream.size() && !_transceiver.read(_readStream.getBuffer(), _hasMoreData))
				{
					scheduleTimeout(1, connectTimeout());
					_threadPool.update(this, operation, 1);
					return false;
				}
				if (!$assertionsDisabled && _readStream.pos() != 14)
					throw new AssertionError();
				_readStream.pos(0);
				byte m[] = _readStream.readBlob(4);
				if (m[0] != Protocol.magic[0] || m[1] != Protocol.magic[1] || m[2] != Protocol.magic[2] || m[3] != Protocol.magic[3])
				{
					BadMagicException ex = new BadMagicException();
					ex.badMagic = m;
					throw ex;
				}
				byte pMajor = _readStream.readByte();
				byte pMinor = _readStream.readByte();
				if (pMajor != 1)
				{
					UnsupportedProtocolException e = new UnsupportedProtocolException();
					e.badMajor = pMajor >= 0 ? ((int) (pMajor)) : pMajor + 255;
					e.badMinor = pMinor >= 0 ? ((int) (pMinor)) : pMinor + 255;
					e.major = 1;
					e.minor = 0;
					throw e;
				}
				byte eMajor = _readStream.readByte();
				byte eMinor = _readStream.readByte();
				if (eMajor != 1)
				{
					UnsupportedEncodingException e = new UnsupportedEncodingException();
					e.badMajor = eMajor >= 0 ? ((int) (eMajor)) : eMajor + 255;
					e.badMinor = eMinor >= 0 ? ((int) (eMinor)) : eMinor + 255;
					e.major = 1;
					e.minor = 0;
					throw e;
				}
				byte messageType = _readStream.readByte();
				if (messageType != 3)
					throw new ConnectionNotValidatedException();
				_readStream.readByte();
				int size = _readStream.readInt();
				if (size != 14)
					throw new IllegalMessageSizeException();
				TraceUtil.traceRecv(_readStream, _logger, _traceLevels);
			}
		_writeStream.resize(0, false);
		_writeStream.pos(0);
		_readStream.resize(14, true);
		_readHeader = true;
		_readStream.pos(0);
		return true;
	}

	private List sendNextMessage()
	{
		List callbacks;
		if (!$assertionsDisabled && _sendStreams.isEmpty())
			throw new AssertionError();
		if (!$assertionsDisabled && (_writeStream.isEmpty() || _writeStream.pos() != _writeStream.size()))
			throw new AssertionError();
		callbacks = new LinkedList();
		do
		{
			OutgoingMessage message = (OutgoingMessage)_sendStreams.getFirst();
			_writeStream.swap(message.stream);
			if (message.sent(this, true))
				callbacks.add(message);
			_sendStreams.removeFirst();
			if (_sendStreams.isEmpty())
				break MISSING_BLOCK_LABEL_333;
			message = (OutgoingMessage)_sendStreams.getFirst();
			if (!$assertionsDisabled && message.prepared)
				throw new AssertionError();
			BasicStream stream = message.stream;
			message.stream = doCompress(stream, message.compress);
			message.stream.prepareWrite();
			message.prepared = true;
			if (message.outAsync != null)
				TraceUtil.trace("sending asynchronous request", stream, _logger, _traceLevels);
			else
				TraceUtil.traceSend(stream, _logger, _traceLevels);
			_writeStream.swap(message.stream);
		} while (_writeStream.pos() == _writeStream.size() || _transceiver.write(_writeStream.getBuffer()));
		if (!$assertionsDisabled && _writeStream.isEmpty())
			throw new AssertionError();
		scheduleTimeout(4, _endpoint.timeout());
		return callbacks;
		LocalException ex;
		ex;
		setState(5, ex);
		return callbacks;
		if (!$assertionsDisabled && !_writeStream.isEmpty())
			throw new AssertionError();
		_threadPool.unregister(this, 4);
		if (_state == 4)
			scheduleTimeout(4, closeTimeout());
		return callbacks;
	}

	private int sendMessage(OutgoingMessage message)
	{
		if (!$assertionsDisabled && _state >= 5)
			throw new AssertionError();
		if (!_sendStreams.isEmpty())
		{
			message.adopt();
			_sendStreams.addLast(message);
			return 0;
		}
		if (!$assertionsDisabled && message.prepared)
			throw new AssertionError();
		BasicStream stream = message.stream;
		message.stream = doCompress(stream, message.compress);
		message.stream.prepareWrite();
		message.prepared = true;
		if (message.outAsync != null)
			TraceUtil.trace("sending asynchronous request", stream, _logger, _traceLevels);
		else
			TraceUtil.traceSend(stream, _logger, _traceLevels);
		if (_transceiver.write(message.stream.getBuffer()))
		{
			int status = 1;
			if (message.sent(this, false))
				status |= 2;
			if (_acmTimeout > 0L)
				_acmAbsoluteTimeoutMillis = Time.currentMonotonicTimeMillis() + _acmTimeout * 1000L;
			return status;
		} else
		{
			message.adopt();
			_writeStream.swap(message.stream);
			_sendStreams.addLast(message);
			scheduleTimeout(4, _endpoint.timeout());
			_threadPool.register(this, 4);
			return 0;
		}
	}

	private BasicStream doCompress(BasicStream uncompressed, boolean compress)
	{
		boolean compressionSupported = false;
		if (compress)
			compressionSupported = BasicStream.compressible();
		if (compressionSupported && uncompressed.size() >= 100)
		{
			BasicStream cstream = uncompressed.compress(14, _compressionLevel);
			if (cstream != null)
			{
				cstream.pos(9);
				cstream.writeByte((byte)2);
				cstream.pos(10);
				cstream.writeInt(cstream.size());
				uncompressed.pos(9);
				uncompressed.writeByte((byte)2);
				uncompressed.writeInt(cstream.size());
				return cstream;
			}
		}
		uncompressed.pos(9);
		uncompressed.writeByte((byte)(compressionSupported ? 1 : 0));
		uncompressed.pos(10);
		uncompressed.writeInt(uncompressed.size());
		return uncompressed;
	}

	private MessageInfo parseMessage(BasicStream stream)
	{
		if (!$assertionsDisabled && (_state <= 1 || _state >= 5))
			throw new AssertionError();
		MessageInfo info = new MessageInfo(stream);
		_readStream.swap(info.stream);
		_readStream.resize(14, true);
		_readStream.pos(0);
		_readHeader = true;
		if (!$assertionsDisabled && info.stream.pos() != info.stream.size())
			throw new AssertionError();
		try
		{
			if (!$assertionsDisabled && info.stream.pos() != info.stream.size())
				throw new AssertionError();
			info.stream.pos(8);
			byte messageType = info.stream.readByte();
			info.compress = info.stream.readByte();
			if (info.compress == 2)
				if (BasicStream.compressible())
				{
					info.stream = info.stream.uncompress(14);
				} else
				{
					FeatureNotSupportedException ex = new FeatureNotSupportedException();
					ex.unsupportedFeature = "Cannot uncompress compressed message: org.apache.tools.bzip2.CBZip2OutputStream was not found";
					throw ex;
				}
			info.stream.pos(14);
			switch (messageType)
			{
			case 4: // '\004'
				TraceUtil.traceRecv(info.stream, _logger, _traceLevels);
				if (_endpoint.datagram())
				{
					if (_warn)
						_logger.warning((new StringBuilder()).append("ignoring close connection message for datagram connection:\n").append(_desc).toString());
				} else
				{
					setState(5, new CloseConnectionException());
				}
				break;

			case 0: // '\0'
				if (_state == 4)
				{
					TraceUtil.trace("received request during closing\n(ignored by server, client will retry)", info.stream, _logger, _traceLevels);
				} else
				{
					TraceUtil.traceRecv(info.stream, _logger, _traceLevels);
					info.requestId = info.stream.readInt();
					info.invokeNum = 1;
					info.servantManager = _servantManager;
					info.adapter = _adapter;
					_dispatchCount++;
				}
				break;

			case 1: // '\001'
				if (_state == 4)
				{
					TraceUtil.trace("received batch request during closing\n(ignored by server, client will retry)", info.stream, _logger, _traceLevels);
					break;
				}
				TraceUtil.traceRecv(info.stream, _logger, _traceLevels);
				info.invokeNum = info.stream.readInt();
				if (info.invokeNum < 0)
				{
					info.invokeNum = 0;
					throw new UnmarshalOutOfBoundsException();
				}
				info.servantManager = _servantManager;
				info.adapter = _adapter;
				_dispatchCount += info.invokeNum;
				break;

			case 2: // '\002'
				TraceUtil.traceRecv(info.stream, _logger, _traceLevels);
				info.requestId = info.stream.readInt();
				Outgoing out = (Outgoing)_requests.remove(Integer.valueOf(info.requestId));
				if (out != null)
				{
					out.finished(info.stream);
				} else
				{
					info.outAsync = (OutgoingAsync)_asyncRequests.remove(Integer.valueOf(info.requestId));
					if (info.outAsync == null)
						throw new UnknownRequestIdException();
				}
				notifyAll();
				break;

			case 3: // '\003'
				TraceUtil.traceRecv(info.stream, _logger, _traceLevels);
				if (_warn)
					_logger.warning((new StringBuilder()).append("ignoring unexpected validate connection message:\n").append(_desc).toString());
				break;

			default:
				TraceUtil.trace("received unknown message\n(invalid, closing connection)", info.stream, _logger, _traceLevels);
				throw new UnknownMessageException();
			}
		}
		catch (LocalException ex)
		{
			if (_endpoint.datagram())
			{
				if (_warn)
					_logger.warning((new StringBuilder()).append("datagram connection exception:\n").append(ex).append('\n').append(_desc).toString());
			} else
			{
				setState(5, ex);
			}
		}
		return info;
	}

	private void invokeAll(BasicStream stream, int invokeNum, int requestId, byte compress, ServantManager servantManager, ObjectAdapter adapter)
	{
		Incoming in = null;
		while (invokeNum > 0) 
		{
			boolean response = !_endpoint.datagram() && requestId != 0;
			in = getIncoming(adapter, response, compress, requestId);
			BasicStream is = in.is();
			stream.swap(is);
			BasicStream os = in.os();
			if (response)
			{
				if (!$assertionsDisabled && invokeNum != 1)
					throw new AssertionError();
				os.writeBlob(Protocol.replyHdr);
				os.writeInt(requestId);
			}
			in.invoke(servantManager);
			if (--invokeNum > 0)
				stream.swap(is);
			reclaimIncoming(in);
			in = null;
		}
		if (in != null)
			reclaimIncoming(in);
		break MISSING_BLOCK_LABEL_364;
		LocalException ex;
		ex;
		invokeException(ex, invokeNum);
		if (in != null)
			reclaimIncoming(in);
		break MISSING_BLOCK_LABEL_364;
		ex;
		UnknownException uex = new UnknownException(ex);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		pw.flush();
		uex.unknown = sw.toString();
		_logger.error(uex.unknown);
		invokeException(uex, invokeNum);
		if (in != null)
			reclaimIncoming(in);
		break MISSING_BLOCK_LABEL_364;
		ex;
		UnknownException uex = new UnknownException(ex);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		pw.flush();
		uex.unknown = sw.toString();
		_logger.error(uex.unknown);
		invokeException(uex, invokeNum);
		if (in != null)
			reclaimIncoming(in);
		break MISSING_BLOCK_LABEL_364;
		Exception exception1;
		exception1;
		if (in != null)
			reclaimIncoming(in);
		throw exception1;
	}

	private void scheduleTimeout(int status, int timeout)
	{
		if (timeout < 0)
			return;
		if ((status & 1) != 0)
		{
			_timer.schedule(_readTimeout, timeout);
			_readTimeoutScheduled = true;
		}
		if ((status & 0xc) != 0)
		{
			_timer.schedule(_writeTimeout, timeout);
			_writeTimeoutScheduled = true;
		}
	}

	private void unscheduleTimeout(int status)
	{
		if ((status & 1) != 0 && _readTimeoutScheduled)
		{
			_timer.cancel(_readTimeout);
			_readTimeoutScheduled = false;
		}
		if ((status & 0xc) != 0 && _writeTimeoutScheduled)
		{
			_timer.cancel(_writeTimeout);
			_writeTimeoutScheduled = false;
		}
	}

	private int connectTimeout()
	{
		DefaultsAndOverrides defaultsAndOverrides = _instance.defaultsAndOverrides();
		if (defaultsAndOverrides.overrideConnectTimeout)
			return defaultsAndOverrides.overrideConnectTimeoutValue;
		else
			return _endpoint.timeout();
	}

	private int closeTimeout()
	{
		DefaultsAndOverrides defaultsAndOverrides = _instance.defaultsAndOverrides();
		if (defaultsAndOverrides.overrideCloseTimeout)
			return defaultsAndOverrides.overrideCloseTimeoutValue;
		else
			return _endpoint.timeout();
	}

	private void warning(String msg, Exception ex)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		pw.flush();
		String s = (new StringBuilder()).append(msg).append(":\n").append(_desc).append("\n").append(sw.toString()).toString();
		_logger.warning(s);
	}

	private Incoming getIncoming(ObjectAdapter adapter, boolean response, byte compress, int requestId)
	{
		Incoming in = null;
		if (_cacheBuffers > 0)
			synchronized (_incomingCacheMutex)
			{
				if (_incomingCache == null)
				{
					in = new Incoming(_instance, this, adapter, response, compress, requestId);
				} else
				{
					in = _incomingCache;
					_incomingCache = _incomingCache.next;
					in.reset(_instance, this, adapter, response, compress, requestId);
					in.next = null;
				}
			}
		else
			in = new Incoming(_instance, this, adapter, response, compress, requestId);
		return in;
	}

	private void reclaimIncoming(Incoming in)
	{
		if (_cacheBuffers > 0)
			synchronized (_incomingCacheMutex)
			{
				in.next = _incomingCache;
				_incomingCache = in;
				_incomingCache.reclaim();
			}
	}

	public Outgoing getOutgoing(RequestHandler handler, String operation, OperationMode mode, Map context)
		throws LocalExceptionWrapper
	{
		Outgoing out = null;
		if (_cacheBuffers > 0)
			synchronized (_outgoingCacheMutex)
			{
				if (_outgoingCache == null)
				{
					out = new Outgoing(handler, operation, mode, context);
				} else
				{
					out = _outgoingCache;
					_outgoingCache = _outgoingCache.next;
					out.reset(handler, operation, mode, context);
					out.next = null;
				}
			}
		else
			out = new Outgoing(handler, operation, mode, context);
		return out;
	}

	public void reclaimOutgoing(Outgoing out)
	{
		if (_cacheBuffers > 0)
		{
			out.reclaim();
			synchronized (_outgoingCacheMutex)
			{
				out.next = _outgoingCache;
				_outgoingCache = out;
			}
		}
	}

}
