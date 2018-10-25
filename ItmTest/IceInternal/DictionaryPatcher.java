// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DictionaryPatcher.java

package IceInternal;

import Ice.Object;
import Ice.ReadObjectCallback;
import java.util.Map;

// Referenced classes of package IceInternal:
//			Patcher

public class DictionaryPatcher
	implements Patcher, ReadObjectCallback
{

	private Map _dict;
	private Class _cls;
	private String _type;
	private Object _key;

	public DictionaryPatcher(Map dict, Class cls, String type, Object key)
	{
		_dict = dict;
		_cls = cls;
		_type = type;
		_key = key;
	}

	public void patch(Ice.Object v)
	{
		if (v != null && !_cls.isInstance(v))
		{
			throw new ClassCastException((new StringBuilder()).append("expected element of type ").append(_cls.getName()).append(" but received ").append(v.getClass().getName()).toString());
		} else
		{
			_dict.put(_key, _cls.cast(v));
			return;
		}
	}

	public String type()
	{
		return _type;
	}

	public void invoke(Ice.Object v)
	{
		patch(v);
	}
}
