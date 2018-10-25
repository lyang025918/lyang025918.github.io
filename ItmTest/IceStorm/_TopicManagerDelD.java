// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _TopicManagerDelD.java

package IceStorm;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceStorm:
//			TopicPrxHolder, TopicExists, NoSuchTopic, TopicDictHolder, 
//			_TopicManagerDel, TopicPrx, TopicManager

public final class _TopicManagerDelD extends _ObjectDelD
	implements _TopicManagerDel
{

	static final boolean $assertionsDisabled = !IceStorm/_TopicManagerDelD.desiredAssertionStatus();

	public _TopicManagerDelD()
	{
	}

	public TopicPrx create(String name, Map __ctx)
		throws LocalExceptionWrapper, TopicExists
	{
		final Current __current;
		final TopicPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "create", OperationMode.Normal, __ctx);
		__result = new TopicPrxHolder();
		__direct = null;
		__direct = new Direct(name) {

			final Current val$__current;
			final TopicPrxHolder val$__result;
			final String val$name;
			final _TopicManagerDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				TopicManager __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (TopicManager)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.create(name, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _TopicManagerDelD.this;
				__current = current;
				__result = topicprxholder;
				name = s;
				super(x0);
			}
		};
		TopicPrx topicprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		topicprx = __result.value;
		__direct.destroy();
		return topicprx;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		TopicExists __ex;
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
			final _TopicManagerDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				TopicManager __servant = null;
				try
				{
					__servant = (TopicManager)__obj;
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
				this$0 = _TopicManagerDelD.this;
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

	public TopicPrx retrieve(String name, Map __ctx)
		throws LocalExceptionWrapper, NoSuchTopic
	{
		final Current __current;
		final TopicPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "retrieve", OperationMode.Nonmutating, __ctx);
		__result = new TopicPrxHolder();
		__direct = null;
		__direct = new Direct(name) {

			final Current val$__current;
			final TopicPrxHolder val$__result;
			final String val$name;
			final _TopicManagerDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				TopicManager __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (TopicManager)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.retrieve(name, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _TopicManagerDelD.this;
				__current = current;
				__result = topicprxholder;
				name = s;
				super(x0);
			}
		};
		TopicPrx topicprx;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		topicprx = __result.value;
		__direct.destroy();
		return topicprx;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		NoSuchTopic __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public Map retrieveAll(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		TopicDictHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "retrieveAll", OperationMode.Nonmutating, __ctx);
		__result = new TopicDictHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final TopicDictHolder val$__result;
			final _TopicManagerDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				TopicManager __servant = null;
				try
				{
					__servant = (TopicManager)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.retrieveAll(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _TopicManagerDelD.this;
				__current = current;
				__result = topicdictholder;
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

}
