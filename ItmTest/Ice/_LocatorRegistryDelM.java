// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _LocatorRegistryDelM.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.LocalExceptionWrapper;
import IceInternal.Outgoing;
import IceInternal.RequestHandler;
import java.util.Map;

// Referenced classes of package Ice:
//			_ObjectDelM, LocalException, AdapterAlreadyActiveException, AdapterNotFoundException, 
//			UserException, UnknownUserException, InvalidReplicaGroupIdException, ServerNotFoundException, 
//			_LocatorRegistryDel, OperationMode, ProcessPrxHelper, ObjectPrx, 
//			ProcessPrx

public final class _LocatorRegistryDelM extends _ObjectDelM
	implements _LocatorRegistryDel
{

	public _LocatorRegistryDelM()
	{
	}

	public void setAdapterDirectProxy(String id, ObjectPrx proxy, Map __ctx)
		throws LocalExceptionWrapper, AdapterAlreadyActiveException, AdapterNotFoundException
	{
		Outgoing __og = __handler.getOutgoing("setAdapterDirectProxy", OperationMode.Idempotent, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(id);
			__os.writeProxy(proxy);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		boolean __ok = __og.invoke();
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (AdapterAlreadyActiveException __ex)
				{
					throw __ex;
				}
				catch (AdapterNotFoundException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			__og.is().skipEmptyEncaps();
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		break MISSING_BLOCK_LABEL_149;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void setReplicatedAdapterDirectProxy(String adapterId, String replicaGroupId, ObjectPrx p, Map __ctx)
		throws LocalExceptionWrapper, AdapterAlreadyActiveException, AdapterNotFoundException, InvalidReplicaGroupIdException
	{
		Outgoing __og = __handler.getOutgoing("setReplicatedAdapterDirectProxy", OperationMode.Idempotent, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(adapterId);
			__os.writeString(replicaGroupId);
			__os.writeProxy(p);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		boolean __ok = __og.invoke();
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (AdapterAlreadyActiveException __ex)
				{
					throw __ex;
				}
				catch (AdapterNotFoundException __ex)
				{
					throw __ex;
				}
				catch (InvalidReplicaGroupIdException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			__og.is().skipEmptyEncaps();
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		break MISSING_BLOCK_LABEL_161;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void setServerProcessProxy(String id, ProcessPrx proxy, Map __ctx)
		throws LocalExceptionWrapper, ServerNotFoundException
	{
		Outgoing __og = __handler.getOutgoing("setServerProcessProxy", OperationMode.Idempotent, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(id);
			ProcessPrxHelper.__write(__os, proxy);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		boolean __ok = __og.invoke();
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (ServerNotFoundException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			__og.is().skipEmptyEncaps();
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		break MISSING_BLOCK_LABEL_144;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}
}
