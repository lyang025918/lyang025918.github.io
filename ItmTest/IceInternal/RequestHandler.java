// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RequestHandler.java

package IceInternal;

import Ice.ConnectionI;
import Ice.OperationMode;
import java.util.Map;

// Referenced classes of package IceInternal:
//			LocalExceptionWrapper, BasicStream, Outgoing, OutgoingAsync, 
//			BatchOutgoing, BatchOutgoingAsync, Reference

public interface RequestHandler
{

	public abstract void prepareBatchRequest(BasicStream basicstream)
		throws LocalExceptionWrapper;

	public abstract void finishBatchRequest(BasicStream basicstream);

	public abstract void abortBatchRequest();

	public abstract ConnectionI sendRequest(Outgoing outgoing)
		throws LocalExceptionWrapper;

	public abstract int sendAsyncRequest(OutgoingAsync outgoingasync)
		throws LocalExceptionWrapper;

	public abstract boolean flushBatchRequests(BatchOutgoing batchoutgoing);

	public abstract int flushAsyncBatchRequests(BatchOutgoingAsync batchoutgoingasync);

	public abstract Reference getReference();

	public abstract ConnectionI getConnection(boolean flag);

	public abstract Outgoing getOutgoing(String s, OperationMode operationmode, Map map)
		throws LocalExceptionWrapper;

	public abstract void reclaimOutgoing(Outgoing outgoing);
}
