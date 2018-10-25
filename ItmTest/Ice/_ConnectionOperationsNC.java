// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ConnectionOperationsNC.java

package Ice;


// Referenced classes of package Ice:
//			Identity, ObjectPrx, ObjectAdapter, Endpoint, 
//			ConnectionInfo

public interface _ConnectionOperationsNC
{

	public abstract void close(boolean flag);

	public abstract ObjectPrx createProxy(Identity identity);

	public abstract void setAdapter(ObjectAdapter objectadapter);

	public abstract ObjectAdapter getAdapter();

	public abstract Endpoint getEndpoint();

	public abstract void flushBatchRequests();

	public abstract String type();

	public abstract int timeout();

	public abstract String _toString();

	public abstract ConnectionInfo getInfo();
}
