// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NodeObserverPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_NodeObserverDel, NodeObserverPrx, _NodeObserverDelM, _NodeObserverDelD, 
//			NodeDynamicInfoSeqHelper, NodeDynamicInfo, AdapterDynamicInfo, ServerDynamicInfo, 
//			Callback_NodeObserver_nodeDown, AMI_NodeObserver_nodeDown, Callback_NodeObserver_nodeInit, AMI_NodeObserver_nodeInit, 
//			Callback_NodeObserver_nodeUp, AMI_NodeObserver_nodeUp, Callback_NodeObserver_updateAdapter, AMI_NodeObserver_updateAdapter, 
//			Callback_NodeObserver_updateServer, AMI_NodeObserver_updateServer

public final class NodeObserverPrxHelper extends ObjectPrxHelperBase
	implements NodeObserverPrx
{

	private static final String __nodeDown_name = "nodeDown";
	private static final String __nodeInit_name = "nodeInit";
	private static final String __nodeUp_name = "nodeUp";
	private static final String __updateAdapter_name = "updateAdapter";
	private static final String __updateServer_name = "updateServer";
	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::NodeObserver"
	};

	public NodeObserverPrxHelper()
	{
	}

	public void nodeDown(String name)
	{
		nodeDown(name, null, false);
	}

	public void nodeDown(String name, Map __ctx)
	{
		nodeDown(name, __ctx, true);
	}

	private void nodeDown(String name, Map __ctx, boolean __explicitCtx)
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__delBase = __getDelegate(false);
				_NodeObserverDel __del = (_NodeObserverDel)__delBase;
				__del.nodeDown(name, __ctx);
				return;
			}
			catch (LocalExceptionWrapper __ex)
			{
				__handleExceptionWrapper(__delBase, __ex);
			}
			catch (LocalException __ex)
			{
				__cnt = __handleException(__delBase, __ex, null, __cnt);
			}
		} while (true);
	}

	public AsyncResult begin_nodeDown(String name)
	{
		return begin_nodeDown(name, null, false, null);
	}

	public AsyncResult begin_nodeDown(String name, Map __ctx)
	{
		return begin_nodeDown(name, __ctx, true, null);
	}

	public AsyncResult begin_nodeDown(String name, Callback __cb)
	{
		return begin_nodeDown(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_nodeDown(String name, Map __ctx, Callback __cb)
	{
		return begin_nodeDown(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_nodeDown(String name, Callback_NodeObserver_nodeDown __cb)
	{
		return begin_nodeDown(name, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_nodeDown(String name, Map __ctx, Callback_NodeObserver_nodeDown __cb)
	{
		return begin_nodeDown(name, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_nodeDown(String name, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "nodeDown", __cb);
		try
		{
			__result.__prepare("nodeDown", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(name);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_nodeDown(AsyncResult __result)
	{
		__end(__result, "nodeDown");
	}

	public boolean nodeDown_async(AMI_NodeObserver_nodeDown __cb, String name)
	{
		AsyncResult __r = begin_nodeDown(name, null, false, __cb);
		return __r.sentSynchronously();
	}

	public boolean nodeDown_async(AMI_NodeObserver_nodeDown __cb, String name, Map __ctx)
	{
		AsyncResult __r = begin_nodeDown(name, __ctx, true, __cb);
		return __r.sentSynchronously();
	}

	public void nodeInit(NodeDynamicInfo nodes[])
	{
		nodeInit(nodes, null, false);
	}

	public void nodeInit(NodeDynamicInfo nodes[], Map __ctx)
	{
		nodeInit(nodes, __ctx, true);
	}

	private void nodeInit(NodeDynamicInfo nodes[], Map __ctx, boolean __explicitCtx)
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__delBase = __getDelegate(false);
				_NodeObserverDel __del = (_NodeObserverDel)__delBase;
				__del.nodeInit(nodes, __ctx);
				return;
			}
			catch (LocalExceptionWrapper __ex)
			{
				__handleExceptionWrapper(__delBase, __ex);
			}
			catch (LocalException __ex)
			{
				__cnt = __handleException(__delBase, __ex, null, __cnt);
			}
		} while (true);
	}

	public AsyncResult begin_nodeInit(NodeDynamicInfo nodes[])
	{
		return begin_nodeInit(nodes, null, false, null);
	}

	public AsyncResult begin_nodeInit(NodeDynamicInfo nodes[], Map __ctx)
	{
		return begin_nodeInit(nodes, __ctx, true, null);
	}

	public AsyncResult begin_nodeInit(NodeDynamicInfo nodes[], Callback __cb)
	{
		return begin_nodeInit(nodes, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_nodeInit(NodeDynamicInfo nodes[], Map __ctx, Callback __cb)
	{
		return begin_nodeInit(nodes, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_nodeInit(NodeDynamicInfo nodes[], Callback_NodeObserver_nodeInit __cb)
	{
		return begin_nodeInit(nodes, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_nodeInit(NodeDynamicInfo nodes[], Map __ctx, Callback_NodeObserver_nodeInit __cb)
	{
		return begin_nodeInit(nodes, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_nodeInit(NodeDynamicInfo nodes[], Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "nodeInit", __cb);
		try
		{
			__result.__prepare("nodeInit", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			NodeDynamicInfoSeqHelper.write(__os, nodes);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_nodeInit(AsyncResult __result)
	{
		__end(__result, "nodeInit");
	}

	public boolean nodeInit_async(AMI_NodeObserver_nodeInit __cb, NodeDynamicInfo nodes[])
	{
		AsyncResult __r = begin_nodeInit(nodes, null, false, __cb);
		return __r.sentSynchronously();
	}

	public boolean nodeInit_async(AMI_NodeObserver_nodeInit __cb, NodeDynamicInfo nodes[], Map __ctx)
	{
		AsyncResult __r = begin_nodeInit(nodes, __ctx, true, __cb);
		return __r.sentSynchronously();
	}

	public void nodeUp(NodeDynamicInfo node)
	{
		nodeUp(node, null, false);
	}

	public void nodeUp(NodeDynamicInfo node, Map __ctx)
	{
		nodeUp(node, __ctx, true);
	}

	private void nodeUp(NodeDynamicInfo node, Map __ctx, boolean __explicitCtx)
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__delBase = __getDelegate(false);
				_NodeObserverDel __del = (_NodeObserverDel)__delBase;
				__del.nodeUp(node, __ctx);
				return;
			}
			catch (LocalExceptionWrapper __ex)
			{
				__handleExceptionWrapper(__delBase, __ex);
			}
			catch (LocalException __ex)
			{
				__cnt = __handleException(__delBase, __ex, null, __cnt);
			}
		} while (true);
	}

	public AsyncResult begin_nodeUp(NodeDynamicInfo node)
	{
		return begin_nodeUp(node, null, false, null);
	}

	public AsyncResult begin_nodeUp(NodeDynamicInfo node, Map __ctx)
	{
		return begin_nodeUp(node, __ctx, true, null);
	}

	public AsyncResult begin_nodeUp(NodeDynamicInfo node, Callback __cb)
	{
		return begin_nodeUp(node, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_nodeUp(NodeDynamicInfo node, Map __ctx, Callback __cb)
	{
		return begin_nodeUp(node, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_nodeUp(NodeDynamicInfo node, Callback_NodeObserver_nodeUp __cb)
	{
		return begin_nodeUp(node, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_nodeUp(NodeDynamicInfo node, Map __ctx, Callback_NodeObserver_nodeUp __cb)
	{
		return begin_nodeUp(node, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_nodeUp(NodeDynamicInfo node, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "nodeUp", __cb);
		try
		{
			__result.__prepare("nodeUp", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			node.__write(__os);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_nodeUp(AsyncResult __result)
	{
		__end(__result, "nodeUp");
	}

	public boolean nodeUp_async(AMI_NodeObserver_nodeUp __cb, NodeDynamicInfo node)
	{
		AsyncResult __r = begin_nodeUp(node, null, false, __cb);
		return __r.sentSynchronously();
	}

	public boolean nodeUp_async(AMI_NodeObserver_nodeUp __cb, NodeDynamicInfo node, Map __ctx)
	{
		AsyncResult __r = begin_nodeUp(node, __ctx, true, __cb);
		return __r.sentSynchronously();
	}

	public void updateAdapter(String node, AdapterDynamicInfo updatedInfo)
	{
		updateAdapter(node, updatedInfo, null, false);
	}

	public void updateAdapter(String node, AdapterDynamicInfo updatedInfo, Map __ctx)
	{
		updateAdapter(node, updatedInfo, __ctx, true);
	}

	private void updateAdapter(String node, AdapterDynamicInfo updatedInfo, Map __ctx, boolean __explicitCtx)
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__delBase = __getDelegate(false);
				_NodeObserverDel __del = (_NodeObserverDel)__delBase;
				__del.updateAdapter(node, updatedInfo, __ctx);
				return;
			}
			catch (LocalExceptionWrapper __ex)
			{
				__handleExceptionWrapper(__delBase, __ex);
			}
			catch (LocalException __ex)
			{
				__cnt = __handleException(__delBase, __ex, null, __cnt);
			}
		} while (true);
	}

	public AsyncResult begin_updateAdapter(String node, AdapterDynamicInfo updatedInfo)
	{
		return begin_updateAdapter(node, updatedInfo, null, false, null);
	}

	public AsyncResult begin_updateAdapter(String node, AdapterDynamicInfo updatedInfo, Map __ctx)
	{
		return begin_updateAdapter(node, updatedInfo, __ctx, true, null);
	}

	public AsyncResult begin_updateAdapter(String node, AdapterDynamicInfo updatedInfo, Callback __cb)
	{
		return begin_updateAdapter(node, updatedInfo, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_updateAdapter(String node, AdapterDynamicInfo updatedInfo, Map __ctx, Callback __cb)
	{
		return begin_updateAdapter(node, updatedInfo, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_updateAdapter(String node, AdapterDynamicInfo updatedInfo, Callback_NodeObserver_updateAdapter __cb)
	{
		return begin_updateAdapter(node, updatedInfo, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_updateAdapter(String node, AdapterDynamicInfo updatedInfo, Map __ctx, Callback_NodeObserver_updateAdapter __cb)
	{
		return begin_updateAdapter(node, updatedInfo, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_updateAdapter(String node, AdapterDynamicInfo updatedInfo, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "updateAdapter", __cb);
		try
		{
			__result.__prepare("updateAdapter", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(node);
			updatedInfo.__write(__os);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_updateAdapter(AsyncResult __result)
	{
		__end(__result, "updateAdapter");
	}

	public boolean updateAdapter_async(AMI_NodeObserver_updateAdapter __cb, String node, AdapterDynamicInfo updatedInfo)
	{
		AsyncResult __r = begin_updateAdapter(node, updatedInfo, null, false, __cb);
		return __r.sentSynchronously();
	}

	public boolean updateAdapter_async(AMI_NodeObserver_updateAdapter __cb, String node, AdapterDynamicInfo updatedInfo, Map __ctx)
	{
		AsyncResult __r = begin_updateAdapter(node, updatedInfo, __ctx, true, __cb);
		return __r.sentSynchronously();
	}

	public void updateServer(String node, ServerDynamicInfo updatedInfo)
	{
		updateServer(node, updatedInfo, null, false);
	}

	public void updateServer(String node, ServerDynamicInfo updatedInfo, Map __ctx)
	{
		updateServer(node, updatedInfo, __ctx, true);
	}

	private void updateServer(String node, ServerDynamicInfo updatedInfo, Map __ctx, boolean __explicitCtx)
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__delBase = __getDelegate(false);
				_NodeObserverDel __del = (_NodeObserverDel)__delBase;
				__del.updateServer(node, updatedInfo, __ctx);
				return;
			}
			catch (LocalExceptionWrapper __ex)
			{
				__handleExceptionWrapper(__delBase, __ex);
			}
			catch (LocalException __ex)
			{
				__cnt = __handleException(__delBase, __ex, null, __cnt);
			}
		} while (true);
	}

	public AsyncResult begin_updateServer(String node, ServerDynamicInfo updatedInfo)
	{
		return begin_updateServer(node, updatedInfo, null, false, null);
	}

	public AsyncResult begin_updateServer(String node, ServerDynamicInfo updatedInfo, Map __ctx)
	{
		return begin_updateServer(node, updatedInfo, __ctx, true, null);
	}

	public AsyncResult begin_updateServer(String node, ServerDynamicInfo updatedInfo, Callback __cb)
	{
		return begin_updateServer(node, updatedInfo, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_updateServer(String node, ServerDynamicInfo updatedInfo, Map __ctx, Callback __cb)
	{
		return begin_updateServer(node, updatedInfo, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_updateServer(String node, ServerDynamicInfo updatedInfo, Callback_NodeObserver_updateServer __cb)
	{
		return begin_updateServer(node, updatedInfo, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_updateServer(String node, ServerDynamicInfo updatedInfo, Map __ctx, Callback_NodeObserver_updateServer __cb)
	{
		return begin_updateServer(node, updatedInfo, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_updateServer(String node, ServerDynamicInfo updatedInfo, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "updateServer", __cb);
		try
		{
			__result.__prepare("updateServer", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(node);
			updatedInfo.__write(__os);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_updateServer(AsyncResult __result)
	{
		__end(__result, "updateServer");
	}

	public boolean updateServer_async(AMI_NodeObserver_updateServer __cb, String node, ServerDynamicInfo updatedInfo)
	{
		AsyncResult __r = begin_updateServer(node, updatedInfo, null, false, __cb);
		return __r.sentSynchronously();
	}

	public boolean updateServer_async(AMI_NodeObserver_updateServer __cb, String node, ServerDynamicInfo updatedInfo, Map __ctx)
	{
		AsyncResult __r = begin_updateServer(node, updatedInfo, __ctx, true, __cb);
		return __r.sentSynchronously();
	}

	public static NodeObserverPrx checkedCast(ObjectPrx __obj)
	{
		NodeObserverPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (NodeObserverPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					NodeObserverPrxHelper __h = new NodeObserverPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static NodeObserverPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		NodeObserverPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (NodeObserverPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					NodeObserverPrxHelper __h = new NodeObserverPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static NodeObserverPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		NodeObserverPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					NodeObserverPrxHelper __h = new NodeObserverPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static NodeObserverPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		NodeObserverPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					NodeObserverPrxHelper __h = new NodeObserverPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static NodeObserverPrx uncheckedCast(ObjectPrx __obj)
	{
		NodeObserverPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (NodeObserverPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				NodeObserverPrxHelper __h = new NodeObserverPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static NodeObserverPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		NodeObserverPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			NodeObserverPrxHelper __h = new NodeObserverPrxHelper();
			__h.__copyFrom(__bb);
			__d = __h;
		}
		return __d;
	}

	public static String ice_staticId()
	{
		return __ids[1];
	}

	protected _ObjectDelM __createDelegateM()
	{
		return new _NodeObserverDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _NodeObserverDelD();
	}

	public static void __write(BasicStream __os, NodeObserverPrx v)
	{
		__os.writeProxy(v);
	}

	public static NodeObserverPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			NodeObserverPrxHelper result = new NodeObserverPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
