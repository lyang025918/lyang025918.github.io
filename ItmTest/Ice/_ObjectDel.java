// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ObjectDel.java

package Ice;

import IceInternal.LocalExceptionWrapper;
import IceInternal.RequestHandler;
import java.util.Map;

// Referenced classes of package Ice:
//			OperationMode, ByteSeqHolder

public interface _ObjectDel
{

	public abstract boolean ice_isA(String s, Map map)
		throws LocalExceptionWrapper;

	public abstract void ice_ping(Map map)
		throws LocalExceptionWrapper;

	public abstract String[] ice_ids(Map map)
		throws LocalExceptionWrapper;

	public abstract String ice_id(Map map)
		throws LocalExceptionWrapper;

	public abstract boolean ice_invoke(String s, OperationMode operationmode, byte abyte0[], ByteSeqHolder byteseqholder, Map map)
		throws LocalExceptionWrapper;

	public abstract void ice_flushBatchRequests();

	public abstract RequestHandler __getRequestHandler();

	public abstract void __setRequestHandler(RequestHandler requesthandler);
}
