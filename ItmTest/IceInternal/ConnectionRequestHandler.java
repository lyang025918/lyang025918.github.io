// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConnectionRequestHandler.java

package IceInternal;

import Ice.*;
import java.util.Map;

// Referenced classes of package IceInternal:
//			RequestHandler, LocalExceptionWrapper, Reference, RouterInfo, 
//			BasicStream, Outgoing, OutgoingAsync, BatchOutgoing, 
//			BatchOutgoingAsync

public class ConnectionRequestHandler
	implements RequestHandler
{

	private final Reference _reference;
	private final boolean _response;
	private final ConnectionI _connection;
	private final boolean _compress;

	public void prepareBatchRequest(BasicStream out)
		throws LocalExceptionWrapper
	{
		_connection.prepareBatchRequest(out);
	}

	public void finishBatchRequest(BasicStream out)
	{
		_connection.finishBatchRequest(out, _compress);
	}

	public void abortBatchRequest()
	{
		_connection.abortBatchRequest();
	}

	public ConnectionI sendRequest(Outgoing out)
		throws LocalExceptionWrapper
	{
		if (!_connection.sendRequest(out, _compress, _response) || _response)
			return _connection;
		else
			return null;
	}

	public int sendAsyncRequest(OutgoingAsync out)
		throws LocalExceptionWrapper
	{
		return _connection.sendAsyncRequest(out, _compress, _response);
	}

	public boolean flushBatchRequests(BatchOutgoing out)
	{
		return _connection.flushBatchRequests(out);
	}

	public int flushAsyncBatchRequests(BatchOutgoingAsync out)
	{
		return _connection.flushAsyncBatchRequests(out);
	}

	public Outgoing getOutgoing(String operation, OperationMode mode, Map context)
		throws LocalExceptionWrapper
	{
		return _connection.getOutgoing(this, operation, mode, context);
	}

	public void reclaimOutgoing(Outgoing out)
	{
		_connection.reclaimOutgoing(out);
	}

	public Reference getReference()
	{
		return _reference;
	}

	public ConnectionI getConnection(boolean wait)
	{
		return _connection;
	}

	public ConnectionRequestHandler(Reference ref, ObjectPrx proxy)
	{
		_reference = ref;
		_response = _reference.getMode() == 0;
		BooleanHolder compress = new BooleanHolder();
		_connection = _reference.getConnection(compress);
		_compress = compress.value;
		RouterInfo ri = _reference.getRouterInfo();
		if (ri != null)
			ri.addProxy(proxy);
	}

	public ConnectionRequestHandler(Reference ref, ConnectionI connection, boolean compress)
	{
		_reference = ref;
		_response = _reference.getMode() == 0;
		_connection = connection;
		_compress = compress;
	}
}
