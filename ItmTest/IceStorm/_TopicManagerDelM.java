// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _TopicManagerDelM.java

package IceStorm;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceStorm:
//			TopicExists, NoSuchTopic, _TopicManagerDel, TopicPrxHelper, 
//			TopicDictHelper, TopicPrx

public final class _TopicManagerDelM extends _ObjectDelM
	implements _TopicManagerDel
{

	public _TopicManagerDelM()
	{
	}

	public TopicPrx create(String name, Map __ctx)
		throws LocalExceptionWrapper, TopicExists
	{
		Outgoing __og = __handler.getOutgoing("create", OperationMode.Normal, __ctx);
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
		TopicPrx topicprx;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (TopicExists __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			TopicPrx __ret = TopicPrxHelper.__read(__is);
			__is.endReadEncaps();
			topicprx = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return topicprx;
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

	public TopicPrx retrieve(String name, Map __ctx)
		throws LocalExceptionWrapper, NoSuchTopic
	{
		Outgoing __og = __handler.getOutgoing("retrieve", OperationMode.Nonmutating, __ctx);
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
		TopicPrx topicprx;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (NoSuchTopic __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			TopicPrx __ret = TopicPrxHelper.__read(__is);
			__is.endReadEncaps();
			topicprx = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return topicprx;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public Map retrieveAll(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("retrieveAll", OperationMode.Nonmutating, __ctx);
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
			Map __ret = TopicDictHelper.read(__is);
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
}
