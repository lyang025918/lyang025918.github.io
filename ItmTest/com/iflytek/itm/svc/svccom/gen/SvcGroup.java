// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SvcGroup.java

package com.iflytek.itm.svc.svccom.gen;

import IceInternal.BasicStream;
import java.io.Serializable;

public class SvcGroup
	implements Cloneable, Serializable
{

	public String value;
	public int docCount;
	static final boolean $assertionsDisabled = !com/iflytek/itm/svc/svccom/gen/SvcGroup.desiredAssertionStatus();

	public SvcGroup()
	{
	}

	public SvcGroup(String value, int docCount)
	{
		this.value = value;
		this.docCount = docCount;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		SvcGroup _r = null;
		try
		{
			_r = (SvcGroup)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (value != _r.value && (value == null || _r.value == null || !value.equals(_r.value)))
				return false;
			return docCount == _r.docCount;
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		int __h = 0;
		if (value != null)
			__h = 5 * __h + value.hashCode();
		__h = 5 * __h + docCount;
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
		__os.writeString(value);
		__os.writeInt(docCount);
	}

	public void __read(BasicStream __is)
	{
		value = __is.readString();
		docCount = __is.readInt();
	}

}
