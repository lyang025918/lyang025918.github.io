// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _NodeObserverDelD.java

package IceGrid;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_NodeObserverDel, NodeDynamicInfo, AdapterDynamicInfo, ServerDynamicInfo, 
//			NodeObserver

public final class _NodeObserverDelD extends _ObjectDelD
	implements _NodeObserverDel
{

	static final boolean $assertionsDisabled = !IceGrid/_NodeObserverDelD.desiredAssertionStatus();

	public _NodeObserverDelD()
	{
	}

	public void nodeDown(String name, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "nodeDown", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(name) {

			final Current val$__current;
			final String val$name;
			final _NodeObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				NodeObserver __servant = null;
				try
				{
					__servant = (NodeObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.nodeDown(name, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _NodeObserverDelD.this;
				__current = current;
				name = s;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_117;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		SystemException __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public void nodeInit(NodeDynamicInfo nodes[], Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "nodeInit", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(nodes) {

			final Current val$__current;
			final NodeDynamicInfo val$nodes[];
			final _NodeObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				NodeObserver __servant = null;
				try
				{
					__servant = (NodeObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.nodeInit(nodes, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _NodeObserverDelD.this;
				__current = current;
				nodes = anodedynamicinfo;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_117;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		SystemException __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public void nodeUp(NodeDynamicInfo node, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "nodeUp", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(node) {

			final Current val$__current;
			final NodeDynamicInfo val$node;
			final _NodeObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				NodeObserver __servant = null;
				try
				{
					__servant = (NodeObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.nodeUp(node, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _NodeObserverDelD.this;
				__current = current;
				node = nodedynamicinfo;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_117;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		SystemException __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public void updateAdapter(final String node, AdapterDynamicInfo updatedInfo, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "updateAdapter", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(updatedInfo) {

			final Current val$__current;
			final String val$node;
			final AdapterDynamicInfo val$updatedInfo;
			final _NodeObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				NodeObserver __servant = null;
				try
				{
					__servant = (NodeObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.updateAdapter(node, updatedInfo, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _NodeObserverDelD.this;
				__current = current;
				node = s;
				updatedInfo = adapterdynamicinfo;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_122;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		SystemException __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public void updateServer(final String node, ServerDynamicInfo updatedInfo, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "updateServer", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(updatedInfo) {

			final Current val$__current;
			final String val$node;
			final ServerDynamicInfo val$updatedInfo;
			final _NodeObserverDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				NodeObserver __servant = null;
				try
				{
					__servant = (NodeObserver)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.updateServer(node, updatedInfo, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _NodeObserverDelD.this;
				__current = current;
				node = s;
				updatedInfo = serverdynamicinfo;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_122;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		SystemException __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

}
