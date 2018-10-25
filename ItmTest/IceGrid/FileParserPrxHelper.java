// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileParserPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_FileParserDel, ParseException, ApplicationDescriptor, FileParserPrx, 
//			_FileParserDelM, _FileParserDelD, AdminPrxHelper, AdminPrx, 
//			Callback_FileParser_parse

public final class FileParserPrxHelper extends ObjectPrxHelperBase
	implements FileParserPrx
{

	private static final String __parse_name = "parse";
	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::FileParser"
	};

	public FileParserPrxHelper()
	{
	}

	public ApplicationDescriptor parse(String xmlFile, AdminPrx adminProxy)
		throws ParseException
	{
		return parse(xmlFile, adminProxy, null, false);
	}

	public ApplicationDescriptor parse(String xmlFile, AdminPrx adminProxy, Map __ctx)
		throws ParseException
	{
		return parse(xmlFile, adminProxy, __ctx, true);
	}

	private ApplicationDescriptor parse(String xmlFile, AdminPrx adminProxy, Map __ctx, boolean __explicitCtx)
		throws ParseException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_FileParserDel __del;
		__checkTwowayOnly("parse");
		__delBase = __getDelegate(false);
		__del = (_FileParserDel)__delBase;
		return __del.parse(xmlFile, adminProxy, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_parse(String xmlFile, AdminPrx adminProxy)
	{
		return begin_parse(xmlFile, adminProxy, null, false, null);
	}

	public AsyncResult begin_parse(String xmlFile, AdminPrx adminProxy, Map __ctx)
	{
		return begin_parse(xmlFile, adminProxy, __ctx, true, null);
	}

	public AsyncResult begin_parse(String xmlFile, AdminPrx adminProxy, Callback __cb)
	{
		return begin_parse(xmlFile, adminProxy, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_parse(String xmlFile, AdminPrx adminProxy, Map __ctx, Callback __cb)
	{
		return begin_parse(xmlFile, adminProxy, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_parse(String xmlFile, AdminPrx adminProxy, Callback_FileParser_parse __cb)
	{
		return begin_parse(xmlFile, adminProxy, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_parse(String xmlFile, AdminPrx adminProxy, Map __ctx, Callback_FileParser_parse __cb)
	{
		return begin_parse(xmlFile, adminProxy, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_parse(String xmlFile, AdminPrx adminProxy, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("parse");
		OutgoingAsync __result = new OutgoingAsync(this, "parse", __cb);
		try
		{
			__result.__prepare("parse", OperationMode.Idempotent, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(xmlFile);
			AdminPrxHelper.__write(__os, adminProxy);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public ApplicationDescriptor end_parse(AsyncResult __result)
		throws ParseException
	{
		AsyncResult.__check(__result, this, "parse");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (ParseException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		ApplicationDescriptor __ret = new ApplicationDescriptor();
		__ret.__read(__is);
		__is.readPendingObjects();
		__is.endReadEncaps();
		return __ret;
	}

	public static FileParserPrx checkedCast(ObjectPrx __obj)
	{
		FileParserPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (FileParserPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					FileParserPrxHelper __h = new FileParserPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static FileParserPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		FileParserPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (FileParserPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					FileParserPrxHelper __h = new FileParserPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static FileParserPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		FileParserPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					FileParserPrxHelper __h = new FileParserPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static FileParserPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		FileParserPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					FileParserPrxHelper __h = new FileParserPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static FileParserPrx uncheckedCast(ObjectPrx __obj)
	{
		FileParserPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (FileParserPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				FileParserPrxHelper __h = new FileParserPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static FileParserPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		FileParserPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			FileParserPrxHelper __h = new FileParserPrxHelper();
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
		return new _FileParserDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _FileParserDelD();
	}

	public static void __write(BasicStream __os, FileParserPrx v)
	{
		__os.writeProxy(v);
	}

	public static FileParserPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			FileParserPrxHelper result = new FileParserPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
