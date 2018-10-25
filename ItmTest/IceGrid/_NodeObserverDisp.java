// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _NodeObserverDisp.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;

// Referenced classes of package IceGrid:
//			NodeDynamicInfo, ServerDynamicInfo, AdapterDynamicInfo, NodeObserver, 
//			NodeDynamicInfoSeqHelper

public abstract class _NodeObserverDisp extends ObjectImpl
	implements NodeObserver
{

	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::NodeObserver"
	};
	private static final String __all[] = {
		"ice_id", "ice_ids", "ice_isA", "ice_ping", "nodeDown", "nodeInit", "nodeUp", "updateAdapter", "updateServer"
	};
	static final boolean $assertionsDisabled = !IceGrid/_NodeObserverDisp.desiredAssertionStatus();

	public _NodeObserverDisp()
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
		return __ids[1];
	}

	public String ice_id(Current __current)
	{
		return __ids[1];
	}

	public static String ice_staticId()
	{
		return __ids[1];
	}

	public final void nodeDown(String name)
	{
		nodeDown(name, null);
	}

	public final void nodeInit(NodeDynamicInfo nodes[])
	{
		nodeInit(nodes, null);
	}

	public final void nodeUp(NodeDynamicInfo node)
	{
		nodeUp(node, null);
	}

	public final void updateAdapter(String node, AdapterDynamicInfo updatedInfo)
	{
		updateAdapter(node, updatedInfo, null);
	}

	public final void updateServer(String node, ServerDynamicInfo updatedInfo)
	{
		updateServer(node, updatedInfo, null);
	}

	public static DispatchStatus ___nodeInit(NodeObserver __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		NodeDynamicInfo nodes[] = NodeDynamicInfoSeqHelper.read(__is);
		__is.endReadEncaps();
		__obj.nodeInit(nodes, __current);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___nodeUp(NodeObserver __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		NodeDynamicInfo node = new NodeDynamicInfo();
		node.__read(__is);
		__is.endReadEncaps();
		__obj.nodeUp(node, __current);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___nodeDown(NodeObserver __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String name = __is.readString();
		__is.endReadEncaps();
		__obj.nodeDown(name, __current);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___updateServer(NodeObserver __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String node = __is.readString();
		ServerDynamicInfo updatedInfo = new ServerDynamicInfo();
		updatedInfo.__read(__is);
		__is.endReadEncaps();
		__obj.updateServer(node, updatedInfo, __current);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___updateAdapter(NodeObserver __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String node = __is.readString();
		AdapterDynamicInfo updatedInfo = new AdapterDynamicInfo();
		updatedInfo.__read(__is);
		__is.endReadEncaps();
		__obj.updateAdapter(node, updatedInfo, __current);
		return DispatchStatus.DispatchOK;
	}

	public DispatchStatus __dispatch(Incoming in, Current __current)
	{
		int pos = Arrays.binarySearch(__all, __current.operation);
		if (pos < 0)
			throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
		switch (pos)
		{
		case 0: // '\0'
			return ___ice_id(this, in, __current);

		case 1: // '\001'
			return ___ice_ids(this, in, __current);

		case 2: // '\002'
			return ___ice_isA(this, in, __current);

		case 3: // '\003'
			return ___ice_ping(this, in, __current);

		case 4: // '\004'
			return ___nodeDown(this, in, __current);

		case 5: // '\005'
			return ___nodeInit(this, in, __current);

		case 6: // '\006'
			return ___nodeUp(this, in, __current);

		case 7: // '\007'
			return ___updateAdapter(this, in, __current);

		case 8: // '\b'
			return ___updateServer(this, in, __current);
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
		ex.reason = "type IceGrid::NodeObserver was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::NodeObserver was not generated with stream support";
		throw ex;
	}

}
