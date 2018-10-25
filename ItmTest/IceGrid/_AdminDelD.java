// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AdminDelD.java

package IceGrid;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			AccessDeniedException, DeploymentException, ObjectExistsException, NodeUnreachableException, 
//			ServerNotExistException, AdapterInfoSeqHolder, AdapterNotExistException, ObjectInfoSeqHolder, 
//			ApplicationInfoHolder, ApplicationNotExistException, ApplicationDescriptorHolder, NodeNotExistException, 
//			NodeInfoHolder, LoadInfoHolder, ObjectInfoHolder, ObjectNotRegisteredException, 
//			RegistryInfoHolder, RegistryNotExistException, RegistryUnreachableException, ServerInfoHolder, 
//			ServerStateHolder, BadSignalException, _AdminDel, PatchException, 
//			ServerStartException, ServerStopException, ApplicationDescriptor, AdapterInfo, 
//			ObjectInfo, ApplicationInfo, NodeInfo, LoadInfo, 
//			RegistryInfo, ServerInfo, ServerState, ServerInstanceDescriptor, 
//			ApplicationUpdateDescriptor, Admin

public final class _AdminDelD extends _ObjectDelD
	implements _AdminDel
{

	static final boolean $assertionsDisabled = !IceGrid/_AdminDelD.desiredAssertionStatus();

	public _AdminDelD()
	{
	}

	public void addApplication(ApplicationDescriptor descriptor, Map __ctx)
		throws LocalExceptionWrapper, AccessDeniedException, DeploymentException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "addApplication", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(descriptor) {

			final Current val$__current;
			final ApplicationDescriptor val$descriptor;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.addApplication(descriptor, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				descriptor = applicationdescriptor;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_127;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		AccessDeniedException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public void addObject(ObjectPrx obj, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, ObjectExistsException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "addObject", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(obj) {

			final Current val$__current;
			final ObjectPrx val$obj;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.addObject(obj, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				obj = objectprx;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_127;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		DeploymentException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public void addObjectWithType(final ObjectPrx obj, String type, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, ObjectExistsException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "addObjectWithType", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(type) {

			final Current val$__current;
			final ObjectPrx val$obj;
			final String val$type;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.addObjectWithType(obj, type, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				obj = objectprx;
				type = s;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_132;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		DeploymentException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public void enableServer(final String id, boolean enabled, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "enableServer", OperationMode.Idempotent, __ctx);
		__direct = null;
		__direct = new Direct(enabled) {

			final Current val$__current;
			final String val$id;
			final boolean val$enabled;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.enableServer(id, enabled, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				id = s;
				enabled = flag;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_137;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		DeploymentException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public AdapterInfo[] getAdapterInfo(String id, Map __ctx)
		throws LocalExceptionWrapper, AdapterNotExistException
	{
		final Current __current;
		final AdapterInfoSeqHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getAdapterInfo", OperationMode.Nonmutating, __ctx);
		__result = new AdapterInfoSeqHolder();
		__direct = null;
		__direct = new Direct(id) {

			final Current val$__current;
			final AdapterInfoSeqHolder val$__result;
			final String val$id;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getAdapterInfo(id, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = adapterinfoseqholder;
				id = s;
				super(x0);
			}
		};
		AdapterInfo aadapterinfo[];
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		aadapterinfo = __result.value;
		__direct.destroy();
		return aadapterinfo;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		AdapterNotExistException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public String[] getAllAdapterIds(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		StringSeqHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getAllAdapterIds", OperationMode.Nonmutating, __ctx);
		__result = new StringSeqHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final StringSeqHolder val$__result;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getAllAdapterIds(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = stringseqholder;
				super(x0);
			}
		};
		String as[];
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		as = __result.value;
		__direct.destroy();
		return as;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		SystemException __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public String[] getAllApplicationNames(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		StringSeqHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getAllApplicationNames", OperationMode.Nonmutating, __ctx);
		__result = new StringSeqHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final StringSeqHolder val$__result;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getAllApplicationNames(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = stringseqholder;
				super(x0);
			}
		};
		String as[];
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		as = __result.value;
		__direct.destroy();
		return as;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		SystemException __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public String[] getAllNodeNames(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		StringSeqHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getAllNodeNames", OperationMode.Nonmutating, __ctx);
		__result = new StringSeqHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final StringSeqHolder val$__result;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getAllNodeNames(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = stringseqholder;
				super(x0);
			}
		};
		String as[];
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		as = __result.value;
		__direct.destroy();
		return as;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		SystemException __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public ObjectInfo[] getAllObjectInfos(String expr, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		final ObjectInfoSeqHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getAllObjectInfos", OperationMode.Nonmutating, __ctx);
		__result = new ObjectInfoSeqHolder();
		__direct = null;
		__direct = new Direct(expr) {

			final Current val$__current;
			final ObjectInfoSeqHolder val$__result;
			final String val$expr;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getAllObjectInfos(expr, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = objectinfoseqholder;
				expr = s;
				super(x0);
			}
		};
		ObjectInfo aobjectinfo[];
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		aobjectinfo = __result.value;
		__direct.destroy();
		return aobjectinfo;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		SystemException __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public String[] getAllRegistryNames(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		StringSeqHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getAllRegistryNames", OperationMode.Idempotent, __ctx);
		__result = new StringSeqHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final StringSeqHolder val$__result;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getAllRegistryNames(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = stringseqholder;
				super(x0);
			}
		};
		String as[];
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		as = __result.value;
		__direct.destroy();
		return as;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		SystemException __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public String[] getAllServerIds(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		StringSeqHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getAllServerIds", OperationMode.Nonmutating, __ctx);
		__result = new StringSeqHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final StringSeqHolder val$__result;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getAllServerIds(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = stringseqholder;
				super(x0);
			}
		};
		String as[];
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		as = __result.value;
		__direct.destroy();
		return as;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		SystemException __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public ApplicationInfo getApplicationInfo(String name, Map __ctx)
		throws LocalExceptionWrapper, ApplicationNotExistException
	{
		final Current __current;
		final ApplicationInfoHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getApplicationInfo", OperationMode.Nonmutating, __ctx);
		__result = new ApplicationInfoHolder();
		__direct = null;
		__direct = new Direct(name) {

			final Current val$__current;
			final ApplicationInfoHolder val$__result;
			final String val$name;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getApplicationInfo(name, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = applicationinfoholder;
				name = s;
				super(x0);
			}
		};
		ApplicationInfo applicationinfo;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		applicationinfo = __result.value;
		__direct.destroy();
		return applicationinfo;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		ApplicationNotExistException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public ApplicationDescriptor getDefaultApplicationDescriptor(Map __ctx)
		throws LocalExceptionWrapper, DeploymentException
	{
		final Current __current;
		ApplicationDescriptorHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getDefaultApplicationDescriptor", OperationMode.Nonmutating, __ctx);
		__result = new ApplicationDescriptorHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final ApplicationDescriptorHolder val$__result;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getDefaultApplicationDescriptor(__current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = applicationdescriptorholder;
				super(x0);
			}
		};
		ApplicationDescriptor applicationdescriptor;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		applicationdescriptor = __result.value;
		__direct.destroy();
		return applicationdescriptor;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		DeploymentException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public String getNodeHostname(String name, Map __ctx)
		throws LocalExceptionWrapper, NodeNotExistException, NodeUnreachableException
	{
		final Current __current;
		final StringHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getNodeHostname", OperationMode.Nonmutating, __ctx);
		__result = new StringHolder();
		__direct = null;
		__direct = new Direct(name) {

			final Current val$__current;
			final StringHolder val$__result;
			final String val$name;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getNodeHostname(name, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = stringholder;
				name = s;
				super(x0);
			}
		};
		String s;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		s = __result.value;
		__direct.destroy();
		return s;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		NodeNotExistException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public NodeInfo getNodeInfo(String name, Map __ctx)
		throws LocalExceptionWrapper, NodeNotExistException, NodeUnreachableException
	{
		final Current __current;
		final NodeInfoHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getNodeInfo", OperationMode.Nonmutating, __ctx);
		__result = new NodeInfoHolder();
		__direct = null;
		__direct = new Direct(name) {

			final Current val$__current;
			final NodeInfoHolder val$__result;
			final String val$name;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getNodeInfo(name, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = nodeinfoholder;
				name = s;
				super(x0);
			}
		};
		NodeInfo nodeinfo;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		nodeinfo = __result.value;
		__direct.destroy();
		return nodeinfo;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		NodeNotExistException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public LoadInfo getNodeLoad(String name, Map __ctx)
		throws LocalExceptionWrapper, NodeNotExistException, NodeUnreachableException
	{
		final Current __current;
		final LoadInfoHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getNodeLoad", OperationMode.Nonmutating, __ctx);
		__result = new LoadInfoHolder();
		__direct = null;
		__direct = new Direct(name) {

			final Current val$__current;
			final LoadInfoHolder val$__result;
			final String val$name;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getNodeLoad(name, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = loadinfoholder;
				name = s;
				super(x0);
			}
		};
		LoadInfo loadinfo;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		loadinfo = __result.value;
		__direct.destroy();
		return loadinfo;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		NodeNotExistException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public int getNodeProcessorSocketCount(String name, Map __ctx)
		throws LocalExceptionWrapper, NodeNotExistException, NodeUnreachableException
	{
		final Current __current;
		final IntHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getNodeProcessorSocketCount", OperationMode.Nonmutating, __ctx);
		__result = new IntHolder();
		__direct = null;
		__direct = new Direct(name) {

			final Current val$__current;
			final IntHolder val$__result;
			final String val$name;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getNodeProcessorSocketCount(name, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = intholder;
				name = s;
				super(x0);
			}
		};
		int i;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		i = __result.value;
		__direct.destroy();
		return i;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		NodeNotExistException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public ObjectInfo getObjectInfo(Identity id, Map __ctx)
		throws LocalExceptionWrapper, ObjectNotRegisteredException
	{
		final Current __current;
		final ObjectInfoHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getObjectInfo", OperationMode.Nonmutating, __ctx);
		__result = new ObjectInfoHolder();
		__direct = null;
		__direct = new Direct(id) {

			final Current val$__current;
			final ObjectInfoHolder val$__result;
			final Identity val$id;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getObjectInfo(id, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = objectinfoholder;
				id = identity;
				super(x0);
			}
		};
		ObjectInfo objectinfo;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		objectinfo = __result.value;
		__direct.destroy();
		return objectinfo;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		ObjectNotRegisteredException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public ObjectInfo[] getObjectInfosByType(String type, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		final ObjectInfoSeqHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getObjectInfosByType", OperationMode.Nonmutating, __ctx);
		__result = new ObjectInfoSeqHolder();
		__direct = null;
		__direct = new Direct(type) {

			final Current val$__current;
			final ObjectInfoSeqHolder val$__result;
			final String val$type;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getObjectInfosByType(type, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = objectinfoseqholder;
				type = s;
				super(x0);
			}
		};
		ObjectInfo aobjectinfo[];
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		aobjectinfo = __result.value;
		__direct.destroy();
		return aobjectinfo;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		SystemException __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public RegistryInfo getRegistryInfo(String name, Map __ctx)
		throws LocalExceptionWrapper, RegistryNotExistException, RegistryUnreachableException
	{
		final Current __current;
		final RegistryInfoHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getRegistryInfo", OperationMode.Idempotent, __ctx);
		__result = new RegistryInfoHolder();
		__direct = null;
		__direct = new Direct(name) {

			final Current val$__current;
			final RegistryInfoHolder val$__result;
			final String val$name;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getRegistryInfo(name, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = registryinfoholder;
				name = s;
				super(x0);
			}
		};
		RegistryInfo registryinfo;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		registryinfo = __result.value;
		__direct.destroy();
		return registryinfo;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		RegistryNotExistException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public ObjectPrx getServerAdmin(String id, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		final Current __current;
		final ObjectPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getServerAdmin", OperationMode.Idempotent, __ctx);
		__result = new ObjectPrxHolder();
		__direct = null;
		__direct = new Direct(id) {

			final Current val$__current;
			final ObjectPrxHolder val$__result;
			final String val$id;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getServerAdmin(id, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = objectprxholder;
				id = s;
				super(x0);
			}
		};
		ObjectPrx objectprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		objectprx = __result.value;
		__direct.destroy();
		return objectprx;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		DeploymentException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public String getServerAdminCategory(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		StringHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getServerAdminCategory", OperationMode.Idempotent, __ctx);
		__result = new StringHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final StringHolder val$__result;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getServerAdminCategory(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = stringholder;
				super(x0);
			}
		};
		String s;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		s = __result.value;
		__direct.destroy();
		return s;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		SystemException __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public ServerInfo getServerInfo(String id, Map __ctx)
		throws LocalExceptionWrapper, ServerNotExistException
	{
		final Current __current;
		final ServerInfoHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getServerInfo", OperationMode.Nonmutating, __ctx);
		__result = new ServerInfoHolder();
		__direct = null;
		__direct = new Direct(id) {

			final Current val$__current;
			final ServerInfoHolder val$__result;
			final String val$id;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getServerInfo(id, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = serverinfoholder;
				id = s;
				super(x0);
			}
		};
		ServerInfo serverinfo;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		serverinfo = __result.value;
		__direct.destroy();
		return serverinfo;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		ServerNotExistException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public int getServerPid(String id, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		final Current __current;
		final IntHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getServerPid", OperationMode.Nonmutating, __ctx);
		__result = new IntHolder();
		__direct = null;
		__direct = new Direct(id) {

			final Current val$__current;
			final IntHolder val$__result;
			final String val$id;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getServerPid(id, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = intholder;
				id = s;
				super(x0);
			}
		};
		int i;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		i = __result.value;
		__direct.destroy();
		return i;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		DeploymentException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public ServerState getServerState(String id, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		final Current __current;
		final ServerStateHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getServerState", OperationMode.Nonmutating, __ctx);
		__result = new ServerStateHolder();
		__direct = null;
		__direct = new Direct(id) {

			final Current val$__current;
			final ServerStateHolder val$__result;
			final String val$id;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getServerState(id, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = serverstateholder;
				id = s;
				super(x0);
			}
		};
		ServerState serverstate;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		serverstate = __result.value;
		__direct.destroy();
		return serverstate;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		DeploymentException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public Map getSliceChecksums(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		SliceChecksumDictHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getSliceChecksums", OperationMode.Nonmutating, __ctx);
		__result = new SliceChecksumDictHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final SliceChecksumDictHolder val$__result;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getSliceChecksums(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = slicechecksumdictholder;
				super(x0);
			}
		};
		Map map;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		map = __result.value;
		__direct.destroy();
		return map;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		SystemException __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public void instantiateServer(final String application, final String node, ServerInstanceDescriptor desc, Map __ctx)
		throws LocalExceptionWrapper, AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "instantiateServer", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(desc) {

			final Current val$__current;
			final String val$application;
			final String val$node;
			final ServerInstanceDescriptor val$desc;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.instantiateServer(application, node, desc, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				application = s;
				node = s1;
				desc = serverinstancedescriptor;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_139;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		AccessDeniedException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public boolean isServerEnabled(String id, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		final Current __current;
		final BooleanHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "isServerEnabled", OperationMode.Nonmutating, __ctx);
		__result = new BooleanHolder();
		__direct = null;
		__direct = new Direct(id) {

			final Current val$__current;
			final BooleanHolder val$__result;
			final String val$id;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.isServerEnabled(id, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = booleanholder;
				id = s;
				super(x0);
			}
		};
		boolean flag;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		flag = __result.value;
		__direct.destroy();
		return flag;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		DeploymentException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public void patchApplication(String name, boolean shutdown, Map __ctx)
		throws LocalExceptionWrapper, ApplicationNotExistException, PatchException
	{
		throw new CollocationOptimizationException();
	}

	public void patchServer(String id, boolean shutdown, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, PatchException, ServerNotExistException
	{
		throw new CollocationOptimizationException();
	}

	public boolean pingNode(String name, Map __ctx)
		throws LocalExceptionWrapper, NodeNotExistException
	{
		final Current __current;
		final BooleanHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "pingNode", OperationMode.Nonmutating, __ctx);
		__result = new BooleanHolder();
		__direct = null;
		__direct = new Direct(name) {

			final Current val$__current;
			final BooleanHolder val$__result;
			final String val$name;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.pingNode(name, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = booleanholder;
				name = s;
				super(x0);
			}
		};
		boolean flag;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		flag = __result.value;
		__direct.destroy();
		return flag;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		NodeNotExistException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public boolean pingRegistry(String name, Map __ctx)
		throws LocalExceptionWrapper, RegistryNotExistException
	{
		final Current __current;
		final BooleanHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "pingRegistry", OperationMode.Idempotent, __ctx);
		__result = new BooleanHolder();
		__direct = null;
		__direct = new Direct(name) {

			final Current val$__current;
			final BooleanHolder val$__result;
			final String val$name;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.pingRegistry(name, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				__result = booleanholder;
				name = s;
				super(x0);
			}
		};
		boolean flag;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		flag = __result.value;
		__direct.destroy();
		return flag;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		RegistryNotExistException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public void removeAdapter(String id, Map __ctx)
		throws LocalExceptionWrapper, AdapterNotExistException, DeploymentException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "removeAdapter", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(id) {

			final Current val$__current;
			final String val$id;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.removeAdapter(id, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				id = s;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_127;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		AdapterNotExistException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public void removeApplication(String name, Map __ctx)
		throws LocalExceptionWrapper, AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "removeApplication", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(name) {

			final Current val$__current;
			final String val$name;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.removeApplication(name, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
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
		break MISSING_BLOCK_LABEL_132;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		AccessDeniedException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public void removeObject(Identity id, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, ObjectNotRegisteredException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "removeObject", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(id) {

			final Current val$__current;
			final Identity val$id;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.removeObject(id, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				id = identity;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_127;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		DeploymentException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public void sendSignal(final String id, String signal, Map __ctx)
		throws LocalExceptionWrapper, BadSignalException, DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "sendSignal", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(signal) {

			final Current val$__current;
			final String val$id;
			final String val$signal;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.sendSignal(id, signal, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				id = s;
				signal = s1;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_142;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		BadSignalException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public void shutdown(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "shutdown", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(__current) {

			final Current val$__current;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.shutdown(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_109;
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

	public void shutdownNode(String name, Map __ctx)
		throws LocalExceptionWrapper, NodeNotExistException, NodeUnreachableException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "shutdownNode", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(name) {

			final Current val$__current;
			final String val$name;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.shutdownNode(name, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
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
		break MISSING_BLOCK_LABEL_127;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		NodeNotExistException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public void shutdownRegistry(String name, Map __ctx)
		throws LocalExceptionWrapper, RegistryNotExistException, RegistryUnreachableException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "shutdownRegistry", OperationMode.Idempotent, __ctx);
		__direct = null;
		__direct = new Direct(name) {

			final Current val$__current;
			final String val$name;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.shutdownRegistry(name, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
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
		break MISSING_BLOCK_LABEL_127;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		RegistryNotExistException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public void startServer(String id, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStartException
	{
		throw new CollocationOptimizationException();
	}

	public void stopServer(String id, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStopException
	{
		throw new CollocationOptimizationException();
	}

	public void syncApplication(ApplicationDescriptor descriptor, Map __ctx)
		throws LocalExceptionWrapper, AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "syncApplication", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(descriptor) {

			final Current val$__current;
			final ApplicationDescriptor val$descriptor;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.syncApplication(descriptor, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				descriptor = applicationdescriptor;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_132;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		AccessDeniedException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public void updateApplication(ApplicationUpdateDescriptor descriptor, Map __ctx)
		throws LocalExceptionWrapper, AccessDeniedException, ApplicationNotExistException, DeploymentException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "updateApplication", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(descriptor) {

			final Current val$__current;
			final ApplicationUpdateDescriptor val$descriptor;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.updateApplication(descriptor, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				descriptor = applicationupdatedescriptor;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_132;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		AccessDeniedException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public void updateObject(ObjectPrx obj, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, ObjectNotRegisteredException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "updateObject", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(obj) {

			final Current val$__current;
			final ObjectPrx val$obj;
			final _AdminDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.updateObject(obj, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				obj = objectprx;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_127;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		DeploymentException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	/**
	 * @deprecated Method writeMessage is deprecated
	 */

	public void writeMessage(final String id, final String message, int fd, Map __ctx)
		throws LocalExceptionWrapper, DeploymentException, NodeUnreachableException, ServerNotExistException
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "writeMessage", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(fd) {

			final Current val$__current;
			final String val$id;
			final String val$message;
			final int val$fd;
			final _AdminDelD this$0;

			/**
			 * @deprecated Method run is deprecated
			 */

			public DispatchStatus run(Ice.Object __obj)
			{
				Admin __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Admin)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.writeMessage(id, message, fd, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _AdminDelD.this;
				__current = current;
				id = s;
				message = s1;
				fd = i;
				super(x0);
			}
		};
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		__direct.destroy();
		break MISSING_BLOCK_LABEL_139;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		DeploymentException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

}
