// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TopicPrxHelper.java

package IceStorm;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceStorm:
//			_TopicDel, LinkExists, AlreadySubscribed, BadQoS, 
//			NoSuchLink, TopicPrx, _TopicDelM, _TopicDelD, 
//			LinkInfoSeqHelper, QoSHelper, Callback_Topic_destroy, LinkInfo, 
//			Callback_Topic_getLinkInfoSeq, Callback_Topic_getName, Callback_Topic_getNonReplicatedPublisher, Callback_Topic_getPublisher, 
//			Callback_Topic_link, Callback_Topic_subscribe, Callback_Topic_subscribeAndGetPublisher, Callback_Topic_unlink, 
//			Callback_Topic_unsubscribe

public final class TopicPrxHelper extends ObjectPrxHelperBase
	implements TopicPrx
{

	private static final String __destroy_name = "destroy";
	private static final String __getLinkInfoSeq_name = "getLinkInfoSeq";
	private static final String __getName_name = "getName";
	private static final String __getNonReplicatedPublisher_name = "getNonReplicatedPublisher";
	private static final String __getPublisher_name = "getPublisher";
	private static final String __link_name = "link";
	private static final String __subscribe_name = "subscribe";
	private static final String __subscribeAndGetPublisher_name = "subscribeAndGetPublisher";
	private static final String __unlink_name = "unlink";
	private static final String __unsubscribe_name = "unsubscribe";
	public static final String __ids[] = {
		"::Ice::Object", "::IceStorm::Topic"
	};

	public TopicPrxHelper()
	{
	}

	public void destroy()
	{
		destroy(null, false);
	}

	public void destroy(Map __ctx)
	{
		destroy(__ctx, true);
	}

	private void destroy(Map __ctx, boolean __explicitCtx)
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
				_TopicDel __del = (_TopicDel)__delBase;
				__del.destroy(__ctx);
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

	public AsyncResult begin_destroy()
	{
		return begin_destroy(null, false, null);
	}

	public AsyncResult begin_destroy(Map __ctx)
	{
		return begin_destroy(__ctx, true, null);
	}

	public AsyncResult begin_destroy(Callback __cb)
	{
		return begin_destroy(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_destroy(Map __ctx, Callback __cb)
	{
		return begin_destroy(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_destroy(Callback_Topic_destroy __cb)
	{
		return begin_destroy(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_destroy(Map __ctx, Callback_Topic_destroy __cb)
	{
		return begin_destroy(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_destroy(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "destroy", __cb);
		try
		{
			__result.__prepare("destroy", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_destroy(AsyncResult __result)
	{
		__end(__result, "destroy");
	}

	public LinkInfo[] getLinkInfoSeq()
	{
		return getLinkInfoSeq(null, false);
	}

	public LinkInfo[] getLinkInfoSeq(Map __ctx)
	{
		return getLinkInfoSeq(__ctx, true);
	}

	private LinkInfo[] getLinkInfoSeq(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_TopicDel __del;
		__checkTwowayOnly("getLinkInfoSeq");
		__delBase = __getDelegate(false);
		__del = (_TopicDel)__delBase;
		return __del.getLinkInfoSeq(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getLinkInfoSeq()
	{
		return begin_getLinkInfoSeq(null, false, null);
	}

	public AsyncResult begin_getLinkInfoSeq(Map __ctx)
	{
		return begin_getLinkInfoSeq(__ctx, true, null);
	}

	public AsyncResult begin_getLinkInfoSeq(Callback __cb)
	{
		return begin_getLinkInfoSeq(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getLinkInfoSeq(Map __ctx, Callback __cb)
	{
		return begin_getLinkInfoSeq(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getLinkInfoSeq(Callback_Topic_getLinkInfoSeq __cb)
	{
		return begin_getLinkInfoSeq(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getLinkInfoSeq(Map __ctx, Callback_Topic_getLinkInfoSeq __cb)
	{
		return begin_getLinkInfoSeq(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getLinkInfoSeq(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getLinkInfoSeq");
		OutgoingAsync __result = new OutgoingAsync(this, "getLinkInfoSeq", __cb);
		try
		{
			__result.__prepare("getLinkInfoSeq", OperationMode.Nonmutating, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public LinkInfo[] end_getLinkInfoSeq(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getLinkInfoSeq");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		LinkInfo __ret[] = LinkInfoSeqHelper.read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public String getName()
	{
		return getName(null, false);
	}

	public String getName(Map __ctx)
	{
		return getName(__ctx, true);
	}

	private String getName(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_TopicDel __del;
		__checkTwowayOnly("getName");
		__delBase = __getDelegate(false);
		__del = (_TopicDel)__delBase;
		return __del.getName(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getName()
	{
		return begin_getName(null, false, null);
	}

	public AsyncResult begin_getName(Map __ctx)
	{
		return begin_getName(__ctx, true, null);
	}

	public AsyncResult begin_getName(Callback __cb)
	{
		return begin_getName(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getName(Map __ctx, Callback __cb)
	{
		return begin_getName(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getName(Callback_Topic_getName __cb)
	{
		return begin_getName(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getName(Map __ctx, Callback_Topic_getName __cb)
	{
		return begin_getName(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getName(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getName");
		OutgoingAsync __result = new OutgoingAsync(this, "getName", __cb);
		try
		{
			__result.__prepare("getName", OperationMode.Nonmutating, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public String end_getName(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getName");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		String __ret = __is.readString();
		__is.endReadEncaps();
		return __ret;
	}

	public ObjectPrx getNonReplicatedPublisher()
	{
		return getNonReplicatedPublisher(null, false);
	}

	public ObjectPrx getNonReplicatedPublisher(Map __ctx)
	{
		return getNonReplicatedPublisher(__ctx, true);
	}

	private ObjectPrx getNonReplicatedPublisher(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_TopicDel __del;
		__checkTwowayOnly("getNonReplicatedPublisher");
		__delBase = __getDelegate(false);
		__del = (_TopicDel)__delBase;
		return __del.getNonReplicatedPublisher(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getNonReplicatedPublisher()
	{
		return begin_getNonReplicatedPublisher(null, false, null);
	}

	public AsyncResult begin_getNonReplicatedPublisher(Map __ctx)
	{
		return begin_getNonReplicatedPublisher(__ctx, true, null);
	}

	public AsyncResult begin_getNonReplicatedPublisher(Callback __cb)
	{
		return begin_getNonReplicatedPublisher(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getNonReplicatedPublisher(Map __ctx, Callback __cb)
	{
		return begin_getNonReplicatedPublisher(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getNonReplicatedPublisher(Callback_Topic_getNonReplicatedPublisher __cb)
	{
		return begin_getNonReplicatedPublisher(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getNonReplicatedPublisher(Map __ctx, Callback_Topic_getNonReplicatedPublisher __cb)
	{
		return begin_getNonReplicatedPublisher(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getNonReplicatedPublisher(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getNonReplicatedPublisher");
		OutgoingAsync __result = new OutgoingAsync(this, "getNonReplicatedPublisher", __cb);
		try
		{
			__result.__prepare("getNonReplicatedPublisher", OperationMode.Nonmutating, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public ObjectPrx end_getNonReplicatedPublisher(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getNonReplicatedPublisher");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		ObjectPrx __ret = __is.readProxy();
		__is.endReadEncaps();
		return __ret;
	}

	public ObjectPrx getPublisher()
	{
		return getPublisher(null, false);
	}

	public ObjectPrx getPublisher(Map __ctx)
	{
		return getPublisher(__ctx, true);
	}

	private ObjectPrx getPublisher(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_TopicDel __del;
		__checkTwowayOnly("getPublisher");
		__delBase = __getDelegate(false);
		__del = (_TopicDel)__delBase;
		return __del.getPublisher(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getPublisher()
	{
		return begin_getPublisher(null, false, null);
	}

	public AsyncResult begin_getPublisher(Map __ctx)
	{
		return begin_getPublisher(__ctx, true, null);
	}

	public AsyncResult begin_getPublisher(Callback __cb)
	{
		return begin_getPublisher(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getPublisher(Map __ctx, Callback __cb)
	{
		return begin_getPublisher(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getPublisher(Callback_Topic_getPublisher __cb)
	{
		return begin_getPublisher(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getPublisher(Map __ctx, Callback_Topic_getPublisher __cb)
	{
		return begin_getPublisher(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getPublisher(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getPublisher");
		OutgoingAsync __result = new OutgoingAsync(this, "getPublisher", __cb);
		try
		{
			__result.__prepare("getPublisher", OperationMode.Nonmutating, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public ObjectPrx end_getPublisher(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getPublisher");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		ObjectPrx __ret = __is.readProxy();
		__is.endReadEncaps();
		return __ret;
	}

	public void link(TopicPrx linkTo, int cost)
		throws LinkExists
	{
		link(linkTo, cost, null, false);
	}

	public void link(TopicPrx linkTo, int cost, Map __ctx)
		throws LinkExists
	{
		link(linkTo, cost, __ctx, true);
	}

	private void link(TopicPrx linkTo, int cost, Map __ctx, boolean __explicitCtx)
		throws LinkExists
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("link");
				__delBase = __getDelegate(false);
				_TopicDel __del = (_TopicDel)__delBase;
				__del.link(linkTo, cost, __ctx);
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

	public AsyncResult begin_link(TopicPrx linkTo, int cost)
	{
		return begin_link(linkTo, cost, null, false, null);
	}

	public AsyncResult begin_link(TopicPrx linkTo, int cost, Map __ctx)
	{
		return begin_link(linkTo, cost, __ctx, true, null);
	}

	public AsyncResult begin_link(TopicPrx linkTo, int cost, Callback __cb)
	{
		return begin_link(linkTo, cost, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_link(TopicPrx linkTo, int cost, Map __ctx, Callback __cb)
	{
		return begin_link(linkTo, cost, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_link(TopicPrx linkTo, int cost, Callback_Topic_link __cb)
	{
		return begin_link(linkTo, cost, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_link(TopicPrx linkTo, int cost, Map __ctx, Callback_Topic_link __cb)
	{
		return begin_link(linkTo, cost, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_link(TopicPrx linkTo, int cost, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("link");
		OutgoingAsync __result = new OutgoingAsync(this, "link", __cb);
		try
		{
			__result.__prepare("link", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__write(__os, linkTo);
			__os.writeInt(cost);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_link(AsyncResult __result)
		throws LinkExists
	{
		AsyncResult.__check(__result, this, "link");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (LinkExists __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.skipEmptyEncaps();
	}

	/**
	 * @deprecated Method subscribe is deprecated
	 */

	public void subscribe(Map theQoS, ObjectPrx subscriber)
	{
		subscribe(theQoS, subscriber, null, false);
	}

	/**
	 * @deprecated Method subscribe is deprecated
	 */

	public void subscribe(Map theQoS, ObjectPrx subscriber, Map __ctx)
	{
		subscribe(theQoS, subscriber, __ctx, true);
	}

	private void subscribe(Map theQoS, ObjectPrx subscriber, Map __ctx, boolean __explicitCtx)
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
				_TopicDel __del = (_TopicDel)__delBase;
				__del.subscribe(theQoS, subscriber, __ctx);
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

	/**
	 * @deprecated Method begin_subscribe is deprecated
	 */

	public AsyncResult begin_subscribe(Map theQoS, ObjectPrx subscriber)
	{
		return begin_subscribe(theQoS, subscriber, null, false, null);
	}

	/**
	 * @deprecated Method begin_subscribe is deprecated
	 */

	public AsyncResult begin_subscribe(Map theQoS, ObjectPrx subscriber, Map __ctx)
	{
		return begin_subscribe(theQoS, subscriber, __ctx, true, null);
	}

	/**
	 * @deprecated Method begin_subscribe is deprecated
	 */

	public AsyncResult begin_subscribe(Map theQoS, ObjectPrx subscriber, Callback __cb)
	{
		return begin_subscribe(theQoS, subscriber, null, false, ((CallbackBase) (__cb)));
	}

	/**
	 * @deprecated Method begin_subscribe is deprecated
	 */

	public AsyncResult begin_subscribe(Map theQoS, ObjectPrx subscriber, Map __ctx, Callback __cb)
	{
		return begin_subscribe(theQoS, subscriber, __ctx, true, ((CallbackBase) (__cb)));
	}

	/**
	 * @deprecated Method begin_subscribe is deprecated
	 */

	public AsyncResult begin_subscribe(Map theQoS, ObjectPrx subscriber, Callback_Topic_subscribe __cb)
	{
		return begin_subscribe(theQoS, subscriber, null, false, ((CallbackBase) (__cb)));
	}

	/**
	 * @deprecated Method begin_subscribe is deprecated
	 */

	public AsyncResult begin_subscribe(Map theQoS, ObjectPrx subscriber, Map __ctx, Callback_Topic_subscribe __cb)
	{
		return begin_subscribe(theQoS, subscriber, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_subscribe(Map theQoS, ObjectPrx subscriber, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "subscribe", __cb);
		try
		{
			__result.__prepare("subscribe", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			QoSHelper.write(__os, theQoS);
			__os.writeProxy(subscriber);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_subscribe(AsyncResult __result)
	{
		__end(__result, "subscribe");
	}

	public ObjectPrx subscribeAndGetPublisher(Map theQoS, ObjectPrx subscriber)
		throws AlreadySubscribed, BadQoS
	{
		return subscribeAndGetPublisher(theQoS, subscriber, null, false);
	}

	public ObjectPrx subscribeAndGetPublisher(Map theQoS, ObjectPrx subscriber, Map __ctx)
		throws AlreadySubscribed, BadQoS
	{
		return subscribeAndGetPublisher(theQoS, subscriber, __ctx, true);
	}

	private ObjectPrx subscribeAndGetPublisher(Map theQoS, ObjectPrx subscriber, Map __ctx, boolean __explicitCtx)
		throws AlreadySubscribed, BadQoS
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_TopicDel __del;
		__checkTwowayOnly("subscribeAndGetPublisher");
		__delBase = __getDelegate(false);
		__del = (_TopicDel)__delBase;
		return __del.subscribeAndGetPublisher(theQoS, subscriber, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_subscribeAndGetPublisher(Map theQoS, ObjectPrx subscriber)
	{
		return begin_subscribeAndGetPublisher(theQoS, subscriber, null, false, null);
	}

	public AsyncResult begin_subscribeAndGetPublisher(Map theQoS, ObjectPrx subscriber, Map __ctx)
	{
		return begin_subscribeAndGetPublisher(theQoS, subscriber, __ctx, true, null);
	}

	public AsyncResult begin_subscribeAndGetPublisher(Map theQoS, ObjectPrx subscriber, Callback __cb)
	{
		return begin_subscribeAndGetPublisher(theQoS, subscriber, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_subscribeAndGetPublisher(Map theQoS, ObjectPrx subscriber, Map __ctx, Callback __cb)
	{
		return begin_subscribeAndGetPublisher(theQoS, subscriber, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_subscribeAndGetPublisher(Map theQoS, ObjectPrx subscriber, Callback_Topic_subscribeAndGetPublisher __cb)
	{
		return begin_subscribeAndGetPublisher(theQoS, subscriber, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_subscribeAndGetPublisher(Map theQoS, ObjectPrx subscriber, Map __ctx, Callback_Topic_subscribeAndGetPublisher __cb)
	{
		return begin_subscribeAndGetPublisher(theQoS, subscriber, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_subscribeAndGetPublisher(Map theQoS, ObjectPrx subscriber, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("subscribeAndGetPublisher");
		OutgoingAsync __result = new OutgoingAsync(this, "subscribeAndGetPublisher", __cb);
		try
		{
			__result.__prepare("subscribeAndGetPublisher", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			QoSHelper.write(__os, theQoS);
			__os.writeProxy(subscriber);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public ObjectPrx end_subscribeAndGetPublisher(AsyncResult __result)
		throws AlreadySubscribed, BadQoS
	{
		AsyncResult.__check(__result, this, "subscribeAndGetPublisher");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (AlreadySubscribed __ex)
			{
				throw __ex;
			}
			catch (BadQoS __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		ObjectPrx __ret = __is.readProxy();
		__is.endReadEncaps();
		return __ret;
	}

	public void unlink(TopicPrx linkTo)
		throws NoSuchLink
	{
		unlink(linkTo, null, false);
	}

	public void unlink(TopicPrx linkTo, Map __ctx)
		throws NoSuchLink
	{
		unlink(linkTo, __ctx, true);
	}

	private void unlink(TopicPrx linkTo, Map __ctx, boolean __explicitCtx)
		throws NoSuchLink
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("unlink");
				__delBase = __getDelegate(false);
				_TopicDel __del = (_TopicDel)__delBase;
				__del.unlink(linkTo, __ctx);
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

	public AsyncResult begin_unlink(TopicPrx linkTo)
	{
		return begin_unlink(linkTo, null, false, null);
	}

	public AsyncResult begin_unlink(TopicPrx linkTo, Map __ctx)
	{
		return begin_unlink(linkTo, __ctx, true, null);
	}

	public AsyncResult begin_unlink(TopicPrx linkTo, Callback __cb)
	{
		return begin_unlink(linkTo, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_unlink(TopicPrx linkTo, Map __ctx, Callback __cb)
	{
		return begin_unlink(linkTo, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_unlink(TopicPrx linkTo, Callback_Topic_unlink __cb)
	{
		return begin_unlink(linkTo, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_unlink(TopicPrx linkTo, Map __ctx, Callback_Topic_unlink __cb)
	{
		return begin_unlink(linkTo, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_unlink(TopicPrx linkTo, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("unlink");
		OutgoingAsync __result = new OutgoingAsync(this, "unlink", __cb);
		try
		{
			__result.__prepare("unlink", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__write(__os, linkTo);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_unlink(AsyncResult __result)
		throws NoSuchLink
	{
		AsyncResult.__check(__result, this, "unlink");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (NoSuchLink __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.skipEmptyEncaps();
	}

	public void unsubscribe(ObjectPrx subscriber)
	{
		unsubscribe(subscriber, null, false);
	}

	public void unsubscribe(ObjectPrx subscriber, Map __ctx)
	{
		unsubscribe(subscriber, __ctx, true);
	}

	private void unsubscribe(ObjectPrx subscriber, Map __ctx, boolean __explicitCtx)
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
				_TopicDel __del = (_TopicDel)__delBase;
				__del.unsubscribe(subscriber, __ctx);
				return;
			}
			catch (LocalExceptionWrapper __ex)
			{
				__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
			}
			catch (LocalException __ex)
			{
				__cnt = __handleException(__delBase, __ex, null, __cnt);
			}
		} while (true);
	}

	public AsyncResult begin_unsubscribe(ObjectPrx subscriber)
	{
		return begin_unsubscribe(subscriber, null, false, null);
	}

	public AsyncResult begin_unsubscribe(ObjectPrx subscriber, Map __ctx)
	{
		return begin_unsubscribe(subscriber, __ctx, true, null);
	}

	public AsyncResult begin_unsubscribe(ObjectPrx subscriber, Callback __cb)
	{
		return begin_unsubscribe(subscriber, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_unsubscribe(ObjectPrx subscriber, Map __ctx, Callback __cb)
	{
		return begin_unsubscribe(subscriber, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_unsubscribe(ObjectPrx subscriber, Callback_Topic_unsubscribe __cb)
	{
		return begin_unsubscribe(subscriber, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_unsubscribe(ObjectPrx subscriber, Map __ctx, Callback_Topic_unsubscribe __cb)
	{
		return begin_unsubscribe(subscriber, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_unsubscribe(ObjectPrx subscriber, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "unsubscribe", __cb);
		try
		{
			__result.__prepare("unsubscribe", OperationMode.Idempotent, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeProxy(subscriber);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_unsubscribe(AsyncResult __result)
	{
		__end(__result, "unsubscribe");
	}

	public static TopicPrx checkedCast(ObjectPrx __obj)
	{
		TopicPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (TopicPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					TopicPrxHelper __h = new TopicPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static TopicPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		TopicPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (TopicPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					TopicPrxHelper __h = new TopicPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static TopicPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		TopicPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					TopicPrxHelper __h = new TopicPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static TopicPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		TopicPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					TopicPrxHelper __h = new TopicPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static TopicPrx uncheckedCast(ObjectPrx __obj)
	{
		TopicPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (TopicPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				TopicPrxHelper __h = new TopicPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static TopicPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		TopicPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			TopicPrxHelper __h = new TopicPrxHelper();
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
		return new _TopicDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _TopicDelD();
	}

	public static void __write(BasicStream __os, TopicPrx v)
	{
		__os.writeProxy(v);
	}

	public static TopicPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			TopicPrxHelper result = new TopicPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
