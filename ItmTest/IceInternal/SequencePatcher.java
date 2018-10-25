// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SequencePatcher.java

package IceInternal;

import Ice.Object;
import Ice.ReadObjectCallback;

// Referenced classes of package IceInternal:
//			Patcher

public class SequencePatcher
	implements Patcher, ReadObjectCallback
{

	private Object _seq[];
	private Class _cls;
	private String _type;
	private int _index;

	public SequencePatcher(Object seq[], Class cls, String type, int index)
	{
		_seq = seq;
		_cls = cls;
		_type = type;
		_index = index;
	}

	public void patch(Ice.Object v)
	{
		if (v != null && !_cls.isInstance(v))
		{
			throw new ClassCastException((new StringBuilder()).append("expected element of type ").append(_cls.getName()).append(" but received ").append(v.getClass().getName()).toString());
		} else
		{
			_seq[_index] = v;
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
