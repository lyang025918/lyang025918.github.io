// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _PropertiesAdminDisp.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;
import java.util.Map;

// Referenced classes of package Ice:
//			ObjectImpl, OperationNotExistException, MarshalException, PropertiesAdmin, 
//			OperationMode, Current, DispatchStatus, PropertyDictHelper, 
//			Object, OutputStream, InputStream

public abstract class _PropertiesAdminDisp extends ObjectImpl
	implements PropertiesAdmin
{

	public static final String __ids[] = {
		"::Ice::Object", "::Ice::PropertiesAdmin"
	};
	private static final String __all[] = {
		"getPropertiesForPrefix", "getProperty", "ice_id", "ice_ids", "ice_isA", "ice_ping"
	};
	static final boolean $assertionsDisabled = !Ice/_PropertiesAdminDisp.desiredAssertionStatus();

	public _PropertiesAdminDisp()
	{
	}

	protected void ice_copyStateFrom(Object __obj)
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

	public final Map getPropertiesForPrefix(String prefix)
	{
		return getPropertiesForPrefix(prefix, null);
	}

	public final String getProperty(String key)
	{
		return getProperty(key, null);
	}

	public static DispatchStatus ___getProperty(PropertiesAdmin __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String key = __is.readString();
		__is.endReadEncaps();
		BasicStream __os = __inS.os();
		String __ret = __obj.getProperty(key, __current);
		__os.writeString(__ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___getPropertiesForPrefix(PropertiesAdmin __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String prefix = __is.readString();
		__is.endReadEncaps();
		BasicStream __os = __inS.os();
		Map __ret = __obj.getPropertiesForPrefix(prefix, __current);
		PropertyDictHelper.write(__os, __ret);
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
			return ___getPropertiesForPrefix(this, in, __current);

		case 1: // '\001'
			return ___getProperty(this, in, __current);

		case 2: // '\002'
			return ___ice_id(this, in, __current);

		case 3: // '\003'
			return ___ice_ids(this, in, __current);

		case 4: // '\004'
			return ___ice_isA(this, in, __current);

		case 5: // '\005'
			return ___ice_ping(this, in, __current);
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
		ex.reason = "type Ice::PropertiesAdmin was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type Ice::PropertiesAdmin was not generated with stream support";
		throw ex;
	}

}
