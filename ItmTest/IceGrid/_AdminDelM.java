// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AdminDelM.java

package IceGrid;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			AccessDeniedException, DeploymentException, ObjectExistsException, NodeUnreachableException, 
//			ServerNotExistException, AdapterNotExistException, ApplicationNotExistException, ApplicationInfo, 
//			ApplicationDescriptor, NodeNotExistException, NodeInfo, LoadInfo, 
//			ObjectNotRegisteredException, ObjectInfo, RegistryNotExistException, RegistryUnreachableException, 
//			RegistryInfo, ServerInfo, PatchException, BadSignalException, 
//			ServerStartException, ServerStopException, _AdminDel, AdapterInfoSeqHelper, 
//			ObjectInfoSeqHelper, ServerState, ServerInstanceDescriptor, ApplicationUpdateDescriptor, 
//			AdapterInfo

public final class _AdminDelM extends _ObjectDelM
	implements _AdminDel
{

	public _AdminDelM()
	{
	}

	public void addApplication(ApplicationDescriptor descriptor, Map __ctx)
		throws LocalExceptionWrapper, AccessDeniedException, DeploymentException
	{
		Outgoing __og = __handler.getOutgoing("addApplication", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			descriptor.__write(__os);
			__os.writePendingObjects();
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
				catch (AccessDeniedException __ex)
				{
					throw __ex;
				}
				catch (DeploymentException __ex)
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
		break MISSING_BLOCK_LABEL_140;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void addObject(ObjectPrx obj, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, ObjectExistsException
	{
		Outgoing __og = __handler.getOutgoing("addObject", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeProxy(obj);
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
				catch (DeploymentException __ex)
				{
					throw __ex;
				}
				catch (ObjectExistsException __ex)
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
		break MISSING_BLOCK_LABEL_135;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void addObjectWithType(ObjectPrx obj, String type, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, ObjectExistsException
	{
		Outgoing __og = __handler.getOutgoing("addObjectWithType", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeProxy(obj);
			__os.writeString(type);
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
				catch (DeploymentException __ex)
				{
					throw __ex;
				}
				catch (ObjectExistsException __ex)
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

	public void enableServer(String id, boolean enabled, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		Outgoing __og = __handler.getOutgoing("enableServer", OperationMode.Idempotent, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(id);
			__os.writeBool(enabled);
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
				catch (DeploymentException __ex)
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
			__og.is().skipEmptyEncaps();
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		break MISSING_BLOCK_LABEL_154;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public AdapterInfo[] getAdapterInfo(String id, Map __ctx)
		throws LocalExceptionWrapper, AdapterNotExistException
	{
		Outgoing __og = __handler.getOutgoing("getAdapterInfo", OperationMode.Nonmutating, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(id);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		AdapterInfo aadapterinfo[];
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (AdapterNotExistException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			AdapterInfo __ret[] = AdapterInfoSeqHelper.read(__is);
			__is.endReadEncaps();
			aadapterinfo = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return aadapterinfo;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public String[] getAllAdapterIds(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getAllAdapterIds", OperationMode.Nonmutating, __ctx);
		boolean __ok = __og.invoke();
		String as[];
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
			String __ret[] = StringSeqHelper.read(__is);
			__is.endReadEncaps();
			as = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return as;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public String[] getAllApplicationNames(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getAllApplicationNames", OperationMode.Nonmutating, __ctx);
		boolean __ok = __og.invoke();
		String as[];
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
			String __ret[] = StringSeqHelper.read(__is);
			__is.endReadEncaps();
			as = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return as;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public String[] getAllNodeNames(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getAllNodeNames", OperationMode.Nonmutating, __ctx);
		boolean __ok = __og.invoke();
		String as[];
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
			String __ret[] = StringSeqHelper.read(__is);
			__is.endReadEncaps();
			as = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return as;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public ObjectInfo[] getAllObjectInfos(String expr, Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getAllObjectInfos", OperationMode.Nonmutating, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(expr);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		ObjectInfo aobjectinfo[];
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
			ObjectInfo __ret[] = ObjectInfoSeqHelper.read(__is);
			__is.endReadEncaps();
			aobjectinfo = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return aobjectinfo;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public String[] getAllRegistryNames(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getAllRegistryNames", OperationMode.Idempotent, __ctx);
		boolean __ok = __og.invoke();
		String as[];
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
			String __ret[] = StringSeqHelper.read(__is);
			__is.endReadEncaps();
			as = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return as;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public String[] getAllServerIds(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getAllServerIds", OperationMode.Nonmutating, __ctx);
		boolean __ok = __og.invoke();
		String as[];
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
			String __ret[] = StringSeqHelper.read(__is);
			__is.endReadEncaps();
			as = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return as;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public ApplicationInfo getApplicationInfo(String name, Map __ctx)
		throws LocalExceptionWrapper, ApplicationNotExistException
	{
		Outgoing __og = __handler.getOutgoing("getApplicationInfo", OperationMode.Nonmutating, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(name);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		ApplicationInfo applicationinfo;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (ApplicationNotExistException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			ApplicationInfo __ret = new ApplicationInfo();
			__ret.__read(__is);
			__is.readPendingObjects();
			__is.endReadEncaps();
			applicationinfo = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return applicationinfo;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public ApplicationDescriptor getDefaultApplicationDescriptor(Map __ctx)
		throws LocalExceptionWrapper, DeploymentException
	{
		Outgoing __og = __handler.getOutgoing("getDefaultApplicationDescriptor", OperationMode.Nonmutating, __ctx);
		boolean __ok = __og.invoke();
		ApplicationDescriptor applicationdescriptor;
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
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			ApplicationDescriptor __ret = new ApplicationDescriptor();
			__ret.__read(__is);
			__is.readPendingObjects();
			__is.endReadEncaps();
			applicationdescriptor = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return applicationdescriptor;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public String getNodeHostname(String name, Map __ctx)
		throws LocalExceptionWrapper, NodeNotExistException, NodeUnreachableException
	{
		Outgoing __og = __handler.getOutgoing("getNodeHostname", OperationMode.Nonmutating, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(name);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		String s;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
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

	public NodeInfo getNodeInfo(String name, Map __ctx)
		throws LocalExceptionWrapper, NodeNotExistException, NodeUnreachableException
	{
		Outgoing __og = __handler.getOutgoing("getNodeInfo", OperationMode.Nonmutating, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(name);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		NodeInfo nodeinfo;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
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
			NodeInfo __ret = new NodeInfo();
			__ret.__read(__is);
			__is.endReadEncaps();
			nodeinfo = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return nodeinfo;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public LoadInfo getNodeLoad(String name, Map __ctx)
		throws LocalExceptionWrapper, NodeNotExistException, NodeUnreachableException
	{
		Outgoing __og = __handler.getOutgoing("getNodeLoad", OperationMode.Nonmutating, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(name);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		LoadInfo loadinfo;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
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
			LoadInfo __ret = new LoadInfo();
			__ret.__read(__is);
			__is.endReadEncaps();
			loadinfo = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return loadinfo;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public int getNodeProcessorSocketCount(String name, Map __ctx)
		throws LocalExceptionWrapper, NodeNotExistException, NodeUnreachableException
	{
		Outgoing __og = __handler.getOutgoing("getNodeProcessorSocketCount", OperationMode.Nonmutating, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(name);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		int i;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
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

	public ObjectInfo getObjectInfo(Identity id, Map __ctx)
		throws LocalExceptionWrapper, ObjectNotRegisteredException
	{
		Outgoing __og = __handler.getOutgoing("getObjectInfo", OperationMode.Nonmutating, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			id.__write(__os);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		ObjectInfo objectinfo;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (ObjectNotRegisteredException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			ObjectInfo __ret = new ObjectInfo();
			__ret.__read(__is);
			__is.endReadEncaps();
			objectinfo = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return objectinfo;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public ObjectInfo[] getObjectInfosByType(String type, Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getObjectInfosByType", OperationMode.Nonmutating, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(type);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		ObjectInfo aobjectinfo[];
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
			ObjectInfo __ret[] = ObjectInfoSeqHelper.read(__is);
			__is.endReadEncaps();
			aobjectinfo = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return aobjectinfo;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public RegistryInfo getRegistryInfo(String name, Map __ctx)
		throws LocalExceptionWrapper, RegistryNotExistException, RegistryUnreachableException
	{
		Outgoing __og = __handler.getOutgoing("getRegistryInfo", OperationMode.Idempotent, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(name);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		RegistryInfo registryinfo;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
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
			RegistryInfo __ret = new RegistryInfo();
			__ret.__read(__is);
			__is.endReadEncaps();
			registryinfo = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return registryinfo;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public ObjectPrx getServerAdmin(String id, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		Outgoing __og = __handler.getOutgoing("getServerAdmin", OperationMode.Idempotent, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(id);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		ObjectPrx objectprx;
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

	public String getServerAdminCategory(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getServerAdminCategory", OperationMode.Idempotent, __ctx);
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

	public ServerInfo getServerInfo(String id, Map __ctx)
		throws LocalExceptionWrapper, ServerNotExistException
	{
		Outgoing __og = __handler.getOutgoing("getServerInfo", OperationMode.Nonmutating, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(id);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		ServerInfo serverinfo;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
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
			ServerInfo __ret = new ServerInfo();
			__ret.__read(__is);
			__is.readPendingObjects();
			__is.endReadEncaps();
			serverinfo = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return serverinfo;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public int getServerPid(String id, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		Outgoing __og = __handler.getOutgoing("getServerPid", OperationMode.Nonmutating, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(id);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		int i;
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

	public ServerState getServerState(String id, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		Outgoing __og = __handler.getOutgoing("getServerState", OperationMode.Nonmutating, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(id);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		ServerState serverstate;
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
			ServerState __ret = ServerState.__read(__is);
			__is.endReadEncaps();
			serverstate = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return serverstate;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public Map getSliceChecksums(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getSliceChecksums", OperationMode.Nonmutating, __ctx);
		boolean __ok = __og.invoke();
		Map map;
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
			Map __ret = SliceChecksumDictHelper.read(__is);
			__is.endReadEncaps();
			map = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return map;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void instantiateServer(String application, String node, ServerInstanceDescriptor desc, Map __ctx)
		throws LocalExceptionWrapper, AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		Outgoing __og = __handler.getOutgoing("instantiateServer", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(application);
			__os.writeString(node);
			desc.__write(__os);
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
				catch (AccessDeniedException __ex)
				{
					throw __ex;
				}
				catch (ApplicationNotExistException __ex)
				{
					throw __ex;
				}
				catch (DeploymentException __ex)
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

	public boolean isServerEnabled(String id, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		Outgoing __og = __handler.getOutgoing("isServerEnabled", OperationMode.Nonmutating, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(id);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		boolean flag;
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
			boolean __ret = __is.readBool();
			__is.endReadEncaps();
			flag = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return flag;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void patchApplication(String name, boolean shutdown, Map __ctx)
		throws LocalExceptionWrapper, ApplicationNotExistException, PatchException
	{
		Outgoing __og = __handler.getOutgoing("patchApplication", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(name);
			__os.writeBool(shutdown);
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
				catch (ApplicationNotExistException __ex)
				{
					throw __ex;
				}
				catch (PatchException __ex)
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

	public void patchServer(String id, boolean shutdown, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, PatchException, ServerNotExistException
	{
		Outgoing __og = __handler.getOutgoing("patchServer", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(id);
			__os.writeBool(shutdown);
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
				catch (DeploymentException __ex)
				{
					throw __ex;
				}
				catch (NodeUnreachableException __ex)
				{
					throw __ex;
				}
				catch (PatchException __ex)
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
			__og.is().skipEmptyEncaps();
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		break MISSING_BLOCK_LABEL_159;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public boolean pingNode(String name, Map __ctx)
		throws LocalExceptionWrapper, NodeNotExistException
	{
		Outgoing __og = __handler.getOutgoing("pingNode", OperationMode.Nonmutating, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(name);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		boolean flag;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (NodeNotExistException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			boolean __ret = __is.readBool();
			__is.endReadEncaps();
			flag = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return flag;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public boolean pingRegistry(String name, Map __ctx)
		throws LocalExceptionWrapper, RegistryNotExistException
	{
		Outgoing __og = __handler.getOutgoing("pingRegistry", OperationMode.Idempotent, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(name);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		boolean flag;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (RegistryNotExistException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			boolean __ret = __is.readBool();
			__is.endReadEncaps();
			flag = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return flag;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void removeAdapter(String id, Map __ctx)
		throws LocalExceptionWrapper, AdapterNotExistException, DeploymentException
	{
		Outgoing __og = __handler.getOutgoing("removeAdapter", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(id);
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
				catch (AdapterNotExistException __ex)
				{
					throw __ex;
				}
				catch (DeploymentException __ex)
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
		break MISSING_BLOCK_LABEL_135;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void removeApplication(String name, Map __ctx)
		throws LocalExceptionWrapper, AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		Outgoing __og = __handler.getOutgoing("removeApplication", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(name);
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
				catch (AccessDeniedException __ex)
				{
					throw __ex;
				}
				catch (ApplicationNotExistException __ex)
				{
					throw __ex;
				}
				catch (DeploymentException __ex)
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
		break MISSING_BLOCK_LABEL_140;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void removeObject(Identity id, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, ObjectNotRegisteredException
	{
		Outgoing __og = __handler.getOutgoing("removeObject", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			id.__write(__os);
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
				catch (DeploymentException __ex)
				{
					throw __ex;
				}
				catch (ObjectNotRegisteredException __ex)
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
		break MISSING_BLOCK_LABEL_135;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void sendSignal(String id, String signal, Map __ctx)
		throws LocalExceptionWrapper, BadSignalException, DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		Outgoing __og = __handler.getOutgoing("sendSignal", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(id);
			__os.writeString(signal);
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
				catch (BadSignalException __ex)
				{
					throw __ex;
				}
				catch (DeploymentException __ex)
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
			__og.is().skipEmptyEncaps();
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		break MISSING_BLOCK_LABEL_159;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void shutdown(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("shutdown", OperationMode.Normal, __ctx);
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

	public void shutdownNode(String name, Map __ctx)
		throws LocalExceptionWrapper, NodeNotExistException, NodeUnreachableException
	{
		Outgoing __og = __handler.getOutgoing("shutdownNode", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(name);
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
			__og.is().skipEmptyEncaps();
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		break MISSING_BLOCK_LABEL_135;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void shutdownRegistry(String name, Map __ctx)
		throws LocalExceptionWrapper, RegistryNotExistException, RegistryUnreachableException
	{
		Outgoing __og = __handler.getOutgoing("shutdownRegistry", OperationMode.Idempotent, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(name);
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
			__og.is().skipEmptyEncaps();
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		break MISSING_BLOCK_LABEL_135;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void startServer(String id, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStartException
	{
		Outgoing __og = __handler.getOutgoing("startServer", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(id);
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
				catch (DeploymentException __ex)
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
				catch (ServerStartException __ex)
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
		break MISSING_BLOCK_LABEL_145;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void stopServer(String id, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStopException
	{
		Outgoing __og = __handler.getOutgoing("stopServer", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(id);
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
				catch (DeploymentException __ex)
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
				catch (ServerStopException __ex)
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
		break MISSING_BLOCK_LABEL_145;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void syncApplication(ApplicationDescriptor descriptor, Map __ctx)
		throws LocalExceptionWrapper, AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		Outgoing __og = __handler.getOutgoing("syncApplication", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			descriptor.__write(__os);
			__os.writePendingObjects();
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
				catch (AccessDeniedException __ex)
				{
					throw __ex;
				}
				catch (ApplicationNotExistException __ex)
				{
					throw __ex;
				}
				catch (DeploymentException __ex)
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
		break MISSING_BLOCK_LABEL_145;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void updateApplication(ApplicationUpdateDescriptor descriptor, Map __ctx)
		throws LocalExceptionWrapper, AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		Outgoing __og = __handler.getOutgoing("updateApplication", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			descriptor.__write(__os);
			__os.writePendingObjects();
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
				catch (AccessDeniedException __ex)
				{
					throw __ex;
				}
				catch (ApplicationNotExistException __ex)
				{
					throw __ex;
				}
				catch (DeploymentException __ex)
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
		break MISSING_BLOCK_LABEL_145;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void updateObject(ObjectPrx obj, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, ObjectNotRegisteredException
	{
		Outgoing __og = __handler.getOutgoing("updateObject", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeProxy(obj);
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
				catch (DeploymentException __ex)
				{
					throw __ex;
				}
				catch (ObjectNotRegisteredException __ex)
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
		break MISSING_BLOCK_LABEL_135;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void writeMessage(String id, String message, int fd, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		Outgoing __og = __handler.getOutgoing("writeMessage", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(id);
			__os.writeString(message);
			__os.writeInt(fd);
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
				catch (DeploymentException __ex)
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
}
