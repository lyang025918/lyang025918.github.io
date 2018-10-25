// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AdminSessionDelM.java

package IceGrid;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			AccessDeniedException, FileNotAvailableException, NodeNotExistException, NodeUnreachableException, 
//			RegistryNotExistException, RegistryUnreachableException, DeploymentException, ServerNotExistException, 
//			ObserverAlreadyRegisteredException, _AdminSessionDel, AdminPrxHelper, FileIteratorPrxHelper, 
//			RegistryObserverPrxHelper, NodeObserverPrxHelper, ApplicationObserverPrxHelper, AdapterObserverPrxHelper, 
//			ObjectObserverPrxHelper, AdminPrx, FileIteratorPrx, RegistryObserverPrx, 
//			NodeObserverPrx, ApplicationObserverPrx, AdapterObserverPrx, ObjectObserverPrx

public final class _AdminSessionDelM extends _ObjectDelM
	implements _AdminSessionDel
{

	public _AdminSessionDelM()
	{
	}

	public void destroy(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("destroy", OperationMode.Normal, __ctx);
		boolean __ok = __og.invoke();
		if (!__og.is().isEmpty())
			try
			{
				if (!__ok)
					try
					{
						__og.throwUserException();
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
		break MISSING_BLOCK_LABEL_110;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void finishUpdate(Map __ctx)
		throws LocalExceptionWrapper, AccessDeniedException
	{
		Outgoing __og = __handler.getOutgoing("finishUpdate", OperationMode.Normal, __ctx);
		boolean __ok = __og.invoke();
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (AccessDeniedException __ex)
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
		break MISSING_BLOCK_LABEL_105;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public AdminPrx getAdmin(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getAdmin", OperationMode.Nonmutating, __ctx);
		boolean __ok = __og.invoke();
		AdminPrx adminprx;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			AdminPrx __ret = AdminPrxHelper.__read(__is);
			__is.endReadEncaps();
			adminprx = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return adminprx;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public ObjectPrx getAdminCallbackTemplate(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getAdminCallbackTemplate", OperationMode.Idempotent, __ctx);
		boolean __ok = __og.invoke();
		ObjectPrx objectprx;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			ObjectPrx __ret = __is.readProxy();
			__is.endReadEncaps();
			objectprx = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return objectprx;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public String getReplicaName(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getReplicaName", OperationMode.Idempotent, __ctx);
		boolean __ok = __og.invoke();
		String s;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			String __ret = __is.readString();
			__is.endReadEncaps();
			s = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return s;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void keepAlive(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("keepAlive", OperationMode.Idempotent, __ctx);
		boolean __ok = __og.invoke();
		if (!__og.is().isEmpty())
			try
			{
				if (!__ok)
					try
					{
						__og.throwUserException();
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
		break MISSING_BLOCK_LABEL_110;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public FileIteratorPrx openNodeStdErr(String name, int count, Map __ctx)
		throws LocalExceptionWrapper, FileNotAvailableException, NodeNotExistException, NodeUnreachableException
	{
		Outgoing __og = __handler.getOutgoing("openNodeStdErr", OperationMode.Normal, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(name);
			__os.writeInt(count);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		FileIteratorPrx fileiteratorprx;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (FileNotAvailableException __ex)
				{
					throw __ex;
				}
				catch (NodeNotExistException __ex)
				{
					throw __ex;
				}
				catch (NodeUnreachableException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			FileIteratorPrx __ret = FileIteratorPrxHelper.__read(__is);
			__is.endReadEncaps();
			fileiteratorprx = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return fileiteratorprx;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public FileIteratorPrx openNodeStdOut(String name, int count, Map __ctx)
		throws LocalExceptionWrapper, FileNotAvailableException, NodeNotExistException, NodeUnreachableException
	{
		Outgoing __og = __handler.getOutgoing("openNodeStdOut", OperationMode.Normal, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(name);
			__os.writeInt(count);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		FileIteratorPrx fileiteratorprx;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (FileNotAvailableException __ex)
				{
					throw __ex;
				}
				catch (NodeNotExistException __ex)
				{
					throw __ex;
				}
				catch (NodeUnreachableException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			FileIteratorPrx __ret = FileIteratorPrxHelper.__read(__is);
			__is.endReadEncaps();
			fileiteratorprx = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return fileiteratorprx;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public FileIteratorPrx openRegistryStdErr(String name, int count, Map __ctx)
		throws LocalExceptionWrapper, FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException
	{
		Outgoing __og = __handler.getOutgoing("openRegistryStdErr", OperationMode.Normal, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(name);
			__os.writeInt(count);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		FileIteratorPrx fileiteratorprx;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (FileNotAvailableException __ex)
				{
					throw __ex;
				}
				catch (RegistryNotExistException __ex)
				{
					throw __ex;
				}
				catch (RegistryUnreachableException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			FileIteratorPrx __ret = FileIteratorPrxHelper.__read(__is);
			__is.endReadEncaps();
			fileiteratorprx = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return fileiteratorprx;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public FileIteratorPrx openRegistryStdOut(String name, int count, Map __ctx)
		throws LocalExceptionWrapper, FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException
	{
		Outgoing __og = __handler.getOutgoing("openRegistryStdOut", OperationMode.Normal, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(name);
			__os.writeInt(count);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		FileIteratorPrx fileiteratorprx;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (FileNotAvailableException __ex)
				{
					throw __ex;
				}
				catch (RegistryNotExistException __ex)
				{
					throw __ex;
				}
				catch (RegistryUnreachableException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			FileIteratorPrx __ret = FileIteratorPrxHelper.__read(__is);
			__is.endReadEncaps();
			fileiteratorprx = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return fileiteratorprx;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public FileIteratorPrx openServerLog(String id, String path, int count, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException
	{
		Outgoing __og = __handler.getOutgoing("openServerLog", OperationMode.Normal, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(id);
			__os.writeString(path);
			__os.writeInt(count);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		FileIteratorPrx fileiteratorprx;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (DeploymentException __ex)
				{
					throw __ex;
				}
				catch (FileNotAvailableException __ex)
				{
					throw __ex;
				}
				catch (NodeUnreachableException __ex)
				{
					throw __ex;
				}
				catch (ServerNotExistException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			FileIteratorPrx __ret = FileIteratorPrxHelper.__read(__is);
			__is.endReadEncaps();
			fileiteratorprx = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return fileiteratorprx;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public FileIteratorPrx openServerStdErr(String id, int count, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException
	{
		Outgoing __og = __handler.getOutgoing("openServerStdErr", OperationMode.Normal, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(id);
			__os.writeInt(count);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		FileIteratorPrx fileiteratorprx;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (DeploymentException __ex)
				{
					throw __ex;
				}
				catch (FileNotAvailableException __ex)
				{
					throw __ex;
				}
				catch (NodeUnreachableException __ex)
				{
					throw __ex;
				}
				catch (ServerNotExistException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			FileIteratorPrx __ret = FileIteratorPrxHelper.__read(__is);
			__is.endReadEncaps();
			fileiteratorprx = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return fileiteratorprx;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public FileIteratorPrx openServerStdOut(String id, int count, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException
	{
		Outgoing __og = __handler.getOutgoing("openServerStdOut", OperationMode.Normal, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(id);
			__os.writeInt(count);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		FileIteratorPrx fileiteratorprx;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (DeploymentException __ex)
				{
					throw __ex;
				}
				catch (FileNotAvailableException __ex)
				{
					throw __ex;
				}
				catch (NodeUnreachableException __ex)
				{
					throw __ex;
				}
				catch (ServerNotExistException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			FileIteratorPrx __ret = FileIteratorPrxHelper.__read(__is);
			__is.endReadEncaps();
			fileiteratorprx = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return fileiteratorprx;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void setObservers(RegistryObserverPrx registryObs, NodeObserverPrx nodeObs, ApplicationObserverPrx appObs, AdapterObserverPrx adptObs, ObjectObserverPrx objObs, Map __ctx)
		throws LocalExceptionWrapper, ObserverAlreadyRegisteredException
	{
		Outgoing __og = __handler.getOutgoing("setObservers", OperationMode.Idempotent, __ctx);
		try
		{
			BasicStream __os = __og.os();
			RegistryObserverPrxHelper.__write(__os, registryObs);
			NodeObserverPrxHelper.__write(__os, nodeObs);
			ApplicationObserverPrxHelper.__write(__os, appObs);
			AdapterObserverPrxHelper.__write(__os, adptObs);
			ObjectObserverPrxHelper.__write(__os, objObs);
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
				catch (ObserverAlreadyRegisteredException __ex)
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
		break MISSING_BLOCK_LABEL_165;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void setObserversByIdentity(Identity registryObs, Identity nodeObs, Identity appObs, Identity adptObs, Identity objObs, Map __ctx)
		throws LocalExceptionWrapper, ObserverAlreadyRegisteredException
	{
		Outgoing __og = __handler.getOutgoing("setObserversByIdentity", OperationMode.Idempotent, __ctx);
		try
		{
			BasicStream __os = __og.os();
			registryObs.__write(__os);
			nodeObs.__write(__os);
			appObs.__write(__os);
			adptObs.__write(__os);
			objObs.__write(__os);
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
				catch (ObserverAlreadyRegisteredException __ex)
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
		break MISSING_BLOCK_LABEL_165;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public int startUpdate(Map __ctx)
		throws LocalExceptionWrapper, AccessDeniedException
	{
		Outgoing __og = __handler.getOutgoing("startUpdate", OperationMode.Normal, __ctx);
		boolean __ok = __og.invoke();
		int i;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (AccessDeniedException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			int __ret = __is.readInt();
			__is.endReadEncaps();
			i = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return i;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}
}
