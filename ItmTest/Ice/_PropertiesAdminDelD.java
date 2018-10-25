// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _PropertiesAdminDelD.java

package Ice;

import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package Ice:
//			_ObjectDelD, Current, Holder, SystemException, 
//			StringHolder, _PropertiesAdminDel, OperationMode, Object, 
//			DispatchStatus, PropertiesAdmin, OperationNotExistException, UserException

public final class _PropertiesAdminDelD extends _ObjectDelD
	implements _PropertiesAdminDel
{

	static final boolean $assertionsDisabled = !Ice/_PropertiesAdminDelD.desiredAssertionStatus();

	public _PropertiesAdminDelD()
	{
	}

	public Map getPropertiesForPrefix(String prefix, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		final Holder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getPropertiesForPrefix", OperationMode.Normal, __ctx);
		__result = new Holder();
		__direct = null;
		__direct = new Direct(prefix) {

			final Current val$__current;
			final Holder val$__result;
			final String val$prefix;
			final _PropertiesAdminDelD this$0;

			public DispatchStatus run(Object __obj)
			{
				PropertiesAdmin __servant = null;
				try
				{
					__servant = (PropertiesAdmin)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getPropertiesForPrefix(prefix, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _PropertiesAdminDelD.this;
				__current = current;
				__result = holder;
				prefix = s;
				super(x0);
			}
		};
		Map map;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		map = (Map)__result.value;
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
		return (Map)__result.value;
	}

	public String getProperty(String key, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		final StringHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getProperty", OperationMode.Normal, __ctx);
		__result = new StringHolder();
		__direct = null;
		__direct = new Direct(key) {

			final Current val$__current;
			final StringHolder val$__result;
			final String val$key;
			final _PropertiesAdminDelD this$0;

			public DispatchStatus run(Object __obj)
			{
				PropertiesAdmin __servant = null;
				try
				{
					__servant = (PropertiesAdmin)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getProperty(key, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _PropertiesAdminDelD.this;
				__current = current;
				__result = stringholder;
				key = s;
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

}
