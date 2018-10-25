// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Connection.java

package Ice;


// Referenced classes of package Ice:
//			Identity, ObjectPrx, ObjectAdapter, Endpoint, 
//			AsyncResult, Callback, Callback_Connection_flushBatchRequests, ConnectionInfo

public interface Connection
{

	public abstract void close(boolean flag);

	public abstract ObjectPrx createProxy(Identity identity);

	public abstract void setAdapter(ObjectAdapter objectadapter);

	public abstract ObjectAdapter getAdapter();

	public abstract Endpoint getEndpoint();

	public abstract void flushBatchRequests();

	public abstract AsyncResult begin_flushBatchRequests();

	public abstract AsyncResult begin_flushBatchRequests(Callback callback);

	public abstract AsyncResult begin_flushBatchRequests(Callback_Connection_flushBatchRequests callback_connection_flushbatchrequests);

	public abstract void end_flushBatchRequests(AsyncResult asyncresult);

	public abstract String type();

	public abstract int timeout();

	public abstract String _toString();

	public abstract ConnectionInfo getInfo();
}
