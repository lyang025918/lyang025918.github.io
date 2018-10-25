// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AdminSessionDisp.java

package IceGrid;

import Glacier2._SessionDisp;
import Ice.*;
import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;

// Referenced classes of package IceGrid:
//			ObserverAlreadyRegisteredException, AccessDeniedException, DeploymentException, FileNotAvailableException, 
//			NodeUnreachableException, ServerNotExistException, NodeNotExistException, RegistryNotExistException, 
//			RegistryUnreachableException, AdminSession, AdminPrxHelper, RegistryObserverPrxHelper, 
//			NodeObserverPrxHelper, ApplicationObserverPrxHelper, AdapterObserverPrxHelper, ObjectObserverPrxHelper, 
//			FileIteratorPrxHelper, AdminPrx, FileIteratorPrx, RegistryObserverPrx, 
//			NodeObserverPrx, ApplicationObserverPrx, AdapterObserverPrx, ObjectObserverPrx

public abstract class _AdminSessionDisp extends ObjectImpl
	implements AdminSession
{

	public static final String __ids[] = {
		"::Glacier2::Session", "::Ice::Object", "::IceGrid::AdminSession"
	};
	private static final String __all[] = {
		"destroy", "finishUpdate", "getAdmin", "getAdminCallbackTemplate", "getReplicaName", "ice_id", "ice_ids", "ice_isA", "ice_ping", "keepAlive", 
		"openNodeStdErr", "openNodeStdOut", "openRegistryStdErr", "openRegistryStdOut", "openServerLog", "openServerStdErr", "openServerStdOut", "setObservers", "setObserversByIdentity", "startUpdate"
	};
	static final boolean $assertionsDisabled = !IceGrid/_AdminSessionDisp.desiredAssertionStatus();

	public _AdminSessionDisp()
	{
	}

	protected void ice_copyStateFrom(Ice.Object __obj)
		throws CloneNotSupportedException
	{
		throw new CloneNotSupportedException();
	}

	public boolean ice_isA(String s)
	{
		return Arrays.binarySearch(__ids, s) >= 0;
	}

	public boolean ice_isA(String s, Current __current)
	{
		return Arrays.binarySearch(__ids, s) >= 0;
	}

	public String[] ice_ids()
	{
		return __ids;
	}

	public String[] ice_ids(Current __current)
	{
		return __ids;
	}

	public String ice_id()
	{
		return __ids[2];
	}

	public String ice_id(Current __current)
	{
		return __ids[2];
	}

	public static String ice_staticId()
	{
		return __ids[2];
	}

	public final void destroy()
	{
		destroy(null);
	}

	public final void finishUpdate()
		throws AccessDeniedException
	{
		finishUpdate(null);
	}

	public final AdminPrx getAdmin()
	{
		return getAdmin(null);
	}

	public final ObjectPrx getAdminCallbackTemplate()
	{
		return getAdminCallbackTemplate(null);
	}

	public final String getReplicaName()
	{
		return getReplicaName(null);
	}

	public final void keepAlive()
	{
		keepAlive(null);
	}

	public final FileIteratorPrx openNodeStdErr(String name, int count)
		throws FileNotAvailableException, NodeNotExistException, NodeUnreachableException
	{
		return openNodeStdErr(name, count, null);
	}

	public final FileIteratorPrx openNodeStdOut(String name, int count)
		throws FileNotAvailableException, NodeNotExistException, NodeUnreachableException
	{
		return openNodeStdOut(name, count, null);
	}

	public final FileIteratorPrx openRegistryStdErr(String name, int count)
		throws FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException
	{
		return openRegistryStdErr(name, count, null);
	}

	public final FileIteratorPrx openRegistryStdOut(String name, int count)
		throws FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException
	{
		return openRegistryStdOut(name, count, null);
	}

	public final FileIteratorPrx openServerLog(String id, String path, int count)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException
	{
		return openServerLog(id, path, count, null);
	}

	public final FileIteratorPrx openServerStdErr(String id, int count)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException
	{
		return openServerStdErr(id, count, null);
	}

	public final FileIteratorPrx openServerStdOut(String id, int count)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException
	{
		return openServerStdOut(id, count, null);
	}

	public final void setObservers(RegistryObserverPrx registryObs, NodeObserverPrx nodeObs, ApplicationObserverPrx appObs, AdapterObserverPrx adptObs, ObjectObserverPrx objObs)
		throws ObserverAlreadyRegisteredException
	{
		setObservers(registryObs, nodeObs, appObs, adptObs, objObs, null);
	}

	public final void setObserversByIdentity(Identity registryObs, Identity nodeObs, Identity appObs, Identity adptObs, Identity objObs)
		throws ObserverAlreadyRegisteredException
	{
		setObserversByIdentity(registryObs, nodeObs, appObs, adptObs, objObs, null);
	}

	public final int startUpdate()
		throws AccessDeniedException
	{
		return startUpdate(null);
	}

	public static DispatchStatus ___keepAlive(AdminSession __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		__obj.keepAlive(__current);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___getAdmin(AdminSession __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		AdminPrx __ret = __obj.getAdmin(__current);
		AdminPrxHelper.__write(__os, __ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___getAdminCallbackTemplate(AdminSession __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		ObjectPrx __ret = __obj.getAdminCallbackTemplate(__current);
		__os.writeProxy(__ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___setObservers(AdminSession __obj, Incoming __inS, Current __current)
	{
		RegistryObserverPrx registryObs;
		NodeObserverPrx nodeObs;
		ApplicationObserverPrx appObs;
		AdapterObserverPrx adptObs;
		ObjectObserverPrx objObs;
		BasicStream __os;
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		registryObs = RegistryObserverPrxHelper.__read(__is);
		nodeObs = NodeObserverPrxHelper.__read(__is);
		appObs = ApplicationObserverPrxHelper.__read(__is);
		adptObs = AdapterObserverPrxHelper.__read(__is);
		objObs = ObjectObserverPrxHelper.__read(__is);
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.setObservers(registryObs, nodeObs, appObs, adptObs, objObs, __current);
		return DispatchStatus.DispatchOK;
		ObserverAlreadyRegisteredException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___setObserversByIdentity(AdminSession __obj, Incoming __inS, Current __current)
	{
		Identity registryObs;
		Identity nodeObs;
		Identity appObs;
		Identity adptObs;
		Identity objObs;
		BasicStream __os;
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		registryObs = new Identity();
		registryObs.__read(__is);
		nodeObs = new Identity();
		nodeObs.__read(__is);
		appObs = new Identity();
		appObs.__read(__is);
		adptObs = new Identity();
		adptObs.__read(__is);
		objObs = new Identity();
		objObs.__read(__is);
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.setObserversByIdentity(registryObs, nodeObs, appObs, adptObs, objObs, __current);
		return DispatchStatus.DispatchOK;
		ObserverAlreadyRegisteredException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___startUpdate(AdminSession __obj, Incoming __inS, Current __current)
	{
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		__inS.is().skipEmptyEncaps();
		__os = __inS.os();
		int __ret = __obj.startUpdate(__current);
		__os.writeInt(__ret);
		return DispatchStatus.DispatchOK;
		AccessDeniedException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___finishUpdate(AdminSession __obj, Incoming __inS, Current __current)
	{
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		__inS.is().skipEmptyEncaps();
		__os = __inS.os();
		__obj.finishUpdate(__current);
		return DispatchStatus.DispatchOK;
		AccessDeniedException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___getReplicaName(AdminSession __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		String __ret = __obj.getReplicaName(__current);
		__os.writeString(__ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___openServerLog(AdminSession __obj, Incoming __inS, Current __current)
	{
		String id;
		String path;
		int count;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		id = __is.readString();
		path = __is.readString();
		count = __is.readInt();
		__is.endReadEncaps();
		__os = __inS.os();
		FileIteratorPrx __ret = __obj.openServerLog(id, path, count, __current);
		FileIteratorPrxHelper.__write(__os, __ret);
		return DispatchStatus.DispatchOK;
		DeploymentException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___openServerStdErr(AdminSession __obj, Incoming __inS, Current __current)
	{
		String id;
		int count;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		id = __is.readString();
		count = __is.readInt();
		__is.endReadEncaps();
		__os = __inS.os();
		FileIteratorPrx __ret = __obj.openServerStdErr(id, count, __current);
		FileIteratorPrxHelper.__write(__os, __ret);
		return DispatchStatus.DispatchOK;
		DeploymentException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___openServerStdOut(AdminSession __obj, Incoming __inS, Current __current)
	{
		String id;
		int count;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		id = __is.readString();
		count = __is.readInt();
		__is.endReadEncaps();
		__os = __inS.os();
		FileIteratorPrx __ret = __obj.openServerStdOut(id, count, __current);
		FileIteratorPrxHelper.__write(__os, __ret);
		return DispatchStatus.DispatchOK;
		DeploymentException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___openNodeStdErr(AdminSession __obj, Incoming __inS, Current __current)
	{
		String name;
		int count;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		name = __is.readString();
		count = __is.readInt();
		__is.endReadEncaps();
		__os = __inS.os();
		FileIteratorPrx __ret = __obj.openNodeStdErr(name, count, __current);
		FileIteratorPrxHelper.__write(__os, __ret);
		return DispatchStatus.DispatchOK;
		FileNotAvailableException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___openNodeStdOut(AdminSession __obj, Incoming __inS, Current __current)
	{
		String name;
		int count;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		name = __is.readString();
		count = __is.readInt();
		__is.endReadEncaps();
		__os = __inS.os();
		FileIteratorPrx __ret = __obj.openNodeStdOut(name, count, __current);
		FileIteratorPrxHelper.__write(__os, __ret);
		return DispatchStatus.DispatchOK;
		FileNotAvailableException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___openRegistryStdErr(AdminSession __obj, Incoming __inS, Current __current)
	{
		String name;
		int count;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		name = __is.readString();
		count = __is.readInt();
		__is.endReadEncaps();
		__os = __inS.os();
		FileIteratorPrx __ret = __obj.openRegistryStdErr(name, count, __current);
		FileIteratorPrxHelper.__write(__os, __ret);
		return DispatchStatus.DispatchOK;
		FileNotAvailableException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___openRegistryStdOut(AdminSession __obj, Incoming __inS, Current __current)
	{
		String name;
		int count;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		name = __is.readString();
		count = __is.readInt();
		__is.endReadEncaps();
		__os = __inS.os();
		FileIteratorPrx __ret = __obj.openRegistryStdOut(name, count, __current);
		FileIteratorPrxHelper.__write(__os, __ret);
		return DispatchStatus.DispatchOK;
		FileNotAvailableException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public DispatchStatus __dispatch(Incoming in, Current __current)
	{
		int pos = Arrays.binarySearch(__all, __current.operation);
		if (pos < 0)
			throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
		switch (pos)
		{
		case 0: // '\0'
			return _SessionDisp.___destroy(this, in, __current);

		case 1: // '\001'
			return ___finishUpdate(this, in, __current);

		case 2: // '\002'
			return ___getAdmin(this, in, __current);

		case 3: // '\003'
			return ___getAdminCallbackTemplate(this, in, __current);

		case 4: // '\004'
			return ___getReplicaName(this, in, __current);

		case 5: // '\005'
			return ___ice_id(this, in, __current);

		case 6: // '\006'
			return ___ice_ids(this, in, __current);

		case 7: // '\007'
			return ___ice_isA(this, in, __current);

		case 8: // '\b'
			return ___ice_ping(this, in, __current);

		case 9: // '\t'
			return ___keepAlive(this, in, __current);

		case 10: // '\n'
			return ___openNodeStdErr(this, in, __current);

		case 11: // '\013'
			return ___openNodeStdOut(this, in, __current);

		case 12: // '\f'
			return ___openRegistryStdErr(this, in, __current);

		case 13: // '\r'
			return ___openRegistryStdOut(this, in, __current);

		case 14: // '\016'
			return ___openServerLog(this, in, __current);

		case 15: // '\017'
			return ___openServerStdErr(this, in, __current);

		case 16: // '\020'
			return ___openServerStdOut(this, in, __current);

		case 17: // '\021'
			return ___setObservers(this, in, __current);

		case 18: // '\022'
			return ___setObserversByIdentity(this, in, __current);

		case 19: // '\023'
			return ___startUpdate(this, in, __current);
		}
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
	}

	public void __write(BasicStream __os)
	{
		__os.writeTypeId(ice_staticId());
		__os.startWriteSlice();
		__os.endWriteSlice();
		super.__write(__os);
	}

	public void __read(BasicStream __is, boolean __rid)
	{
		if (__rid)
			__is.readTypeId();
		__is.startReadSlice();
		__is.endReadSlice();
		super.__read(__is, true);
	}

	public void __write(OutputStream __outS)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::AdminSession was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::AdminSession was not generated with stream support";
		throw ex;
	}

}
