// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _TopicDelD.java

package IceStorm;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceStorm:
//			LinkInfoSeqHolder, LinkExists, AlreadySubscribed, BadQoS, 
//			NoSuchLink, _TopicDel, LinkInfo, TopicPrx, 
//			Topic

public final class _TopicDelD extends _ObjectDelD
	implements _TopicDel
{

	static final boolean $assertionsDisabled = !IceStorm/_TopicDelD.desiredAssertionStatus();

	public _TopicDelD()
	{
	}

	public void destroy(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "destroy", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(__current) {

			final Current val$__current;
			final _TopicDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Topic __servant = null;
				try
				{
					__servant = (Topic)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.destroy(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _TopicDelD.this;
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

	public LinkInfo[] getLinkInfoSeq(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		LinkInfoSeqHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getLinkInfoSeq", OperationMode.Nonmutating, __ctx);
		__result = new LinkInfoSeqHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final LinkInfoSeqHolder val$__result;
			final _TopicDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Topic __servant = null;
				try
				{
					__servant = (Topic)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getLinkInfoSeq(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _TopicDelD.this;
				__current = current;
				__result = linkinfoseqholder;
				super(x0);
			}
		};
		LinkInfo alinkinfo[];
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		alinkinfo = __result.value;
		__direct.destroy();
		return alinkinfo;
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

	public String getName(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		StringHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getName", OperationMode.Nonmutating, __ctx);
		__result = new StringHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final StringHolder val$__result;
			final _TopicDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Topic __servant = null;
				try
				{
					__servant = (Topic)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getName(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _TopicDelD.this;
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

	public ObjectPrx getNonReplicatedPublisher(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		ObjectPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getNonReplicatedPublisher", OperationMode.Nonmutating, __ctx);
		__result = new ObjectPrxHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final ObjectPrxHolder val$__result;
			final _TopicDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Topic __servant = null;
				try
				{
					__servant = (Topic)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getNonReplicatedPublisher(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _TopicDelD.this;
				__current = current;
				__result = objectprxholder;
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
		SystemException __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public ObjectPrx getPublisher(Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		ObjectPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "getPublisher", OperationMode.Nonmutating, __ctx);
		__result = new ObjectPrxHolder();
		__direct = null;
		__direct = new Direct(__result) {

			final Current val$__current;
			final ObjectPrxHolder val$__result;
			final _TopicDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Topic __servant = null;
				try
				{
					__servant = (Topic)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.getPublisher(__current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _TopicDelD.this;
				__current = current;
				__result = objectprxholder;
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
		SystemException __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

	public void link(final TopicPrx linkTo, int cost, Map __ctx)
		throws LocalExceptionWrapper, LinkExists
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "link", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(cost) {

			final Current val$__current;
			final TopicPrx val$linkTo;
			final int val$cost;
			final _TopicDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Topic __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Topic)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.link(linkTo, cost, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _TopicDelD.this;
				__current = current;
				linkTo = topicprx;
				cost = i;
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
		LinkExists __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	/**
	 * @deprecated Method subscribe is deprecated
	 */

	public void subscribe(final Map theQoS, ObjectPrx subscriber, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "subscribe", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(subscriber) {

			final Current val$__current;
			final Map val$theQoS;
			final ObjectPrx val$subscriber;
			final _TopicDelD this$0;

			/**
			 * @deprecated Method run is deprecated
			 */

			public DispatchStatus run(Ice.Object __obj)
			{
				Topic __servant = null;
				try
				{
					__servant = (Topic)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.subscribe(theQoS, subscriber, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _TopicDelD.this;
				__current = current;
				theQoS = map;
				subscriber = objectprx;
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

	public ObjectPrx subscribeAndGetPublisher(final Map theQoS, ObjectPrx subscriber, Map __ctx)
		throws LocalExceptionWrapper, AlreadySubscribed, BadQoS
	{
		final Current __current;
		final ObjectPrxHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "subscribeAndGetPublisher", OperationMode.Normal, __ctx);
		__result = new ObjectPrxHolder();
		__direct = null;
		__direct = new Direct(subscriber) {

			final Current val$__current;
			final ObjectPrxHolder val$__result;
			final Map val$theQoS;
			final ObjectPrx val$subscriber;
			final _TopicDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Topic __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Topic)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.subscribeAndGetPublisher(theQoS, subscriber, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _TopicDelD.this;
				__current = current;
				__result = objectprxholder;
				theQoS = map;
				subscriber = objectprx;
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
		AlreadySubscribed __ex;
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

	public void unlink(TopicPrx linkTo, Map __ctx)
		throws LocalExceptionWrapper, NoSuchLink
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "unlink", OperationMode.Normal, __ctx);
		__direct = null;
		__direct = new Direct(linkTo) {

			final Current val$__current;
			final TopicPrx val$linkTo;
			final _TopicDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Topic __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (Topic)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.unlink(linkTo, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _TopicDelD.this;
				__current = current;
				linkTo = topicprx;
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
		NoSuchLink __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
	}

	public void unsubscribe(ObjectPrx subscriber, Map __ctx)
		throws LocalExceptionWrapper
	{
		final Current __current;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "unsubscribe", OperationMode.Idempotent, __ctx);
		__direct = null;
		__direct = new Direct(subscriber) {

			final Current val$__current;
			final ObjectPrx val$subscriber;
			final _TopicDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				Topic __servant = null;
				try
				{
					__servant = (Topic)__obj;
				}
				catch (ClassCastException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__servant.unsubscribe(subscriber, __current);
				return DispatchStatus.DispatchOK;
			}

			
				throws UserException
			{
				this$0 = _TopicDelD.this;
				__current = current;
				subscriber = objectprx;
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

}
