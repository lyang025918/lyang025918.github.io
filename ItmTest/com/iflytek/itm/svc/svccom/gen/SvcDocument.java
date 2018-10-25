// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SvcDocument.java

package com.iflytek.itm.svc.svccom.gen;

import IceInternal.BasicStream;
import java.io.Serializable;
import java.util.*;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			SvcField

public class SvcDocument
	implements Cloneable, Serializable
{

	public List fields;
	public int primaryIndex;
	static final boolean $assertionsDisabled = !com/iflytek/itm/svc/svccom/gen/SvcDocument.desiredAssertionStatus();

	public SvcDocument()
	{
	}

	public SvcDocument(List fields, int primaryIndex)
	{
		this.fields = fields;
		this.primaryIndex = primaryIndex;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		SvcDocument _r = null;
		try
		{
			_r = (SvcDocument)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (fields != _r.fields && (fields == null || _r.fields == null || !fields.equals(_r.fields)))
				return false;
			return primaryIndex == _r.primaryIndex;
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		int __h = 0;
		if (fields != null)
			__h = 5 * __h + fields.hashCode();
		__h = 5 * __h + primaryIndex;
		return __h;
	}

	public Object clone()
	{
		Object o = null;
		try
		{
			o = super.clone();
		}
		catch (CloneNotSupportedException ex)
		{
			if (!$assertionsDisabled)
				throw new AssertionError();
		}
		return o;
	}

	public void __write(BasicStream __os)
	{
		if (fields == null)
		{
			__os.writeSize(0);
		} else
		{
			__os.writeSize(fields.size());
			SvcField __elem;
			for (Iterator iterator = fields.iterator(); iterator.hasNext(); __elem.__write(__os))
				__elem = (SvcField)iterator.next();

		}
		__os.writeInt(primaryIndex);
	}

	public void __read(BasicStream __is)
	{
		fields = new ArrayList();
		int __len0 = __is.readAndCheckSeqSize(5);
		for (int __i0 = 0; __i0 < __len0; __i0++)
		{
			SvcField __elem = new SvcField();
			__elem.__read(__is);
			fields.add(__elem);
		}

		primaryIndex = __is.readInt();
	}

}
