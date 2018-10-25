// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SvcField.java

package com.iflytek.itm.svc.svccom.gen;

import IceInternal.BasicStream;
import java.io.Serializable;

public class SvcField
	implements Cloneable, Serializable
{

	public String name;
	public String type;
	public String value;
	public String analyzer;
	public boolean isPrimaryKey;
	static final boolean $assertionsDisabled = !com/iflytek/itm/svc/svccom/gen/SvcField.desiredAssertionStatus();

	public SvcField()
	{
	}

	public SvcField(String name, String type, String value, String analyzer, boolean isPrimaryKey)
	{
		this.name = name;
		this.type = type;
		this.value = value;
		this.analyzer = analyzer;
		this.isPrimaryKey = isPrimaryKey;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		SvcField _r = null;
		try
		{
			_r = (SvcField)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (name != _r.name && (name == null || _r.name == null || !name.equals(_r.name)))
				return false;
			if (type != _r.type && (type == null || _r.type == null || !type.equals(_r.type)))
				return false;
			if (value != _r.value && (value == null || _r.value == null || !value.equals(_r.value)))
				return false;
			if (analyzer != _r.analyzer && (analyzer == null || _r.analyzer == null || !analyzer.equals(_r.analyzer)))
				return false;
			return isPrimaryKey == _r.isPrimaryKey;
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		int __h = 0;
		if (name != null)
			__h = 5 * __h + name.hashCode();
		if (type != null)
			__h = 5 * __h + type.hashCode();
		if (value != null)
			__h = 5 * __h + value.hashCode();
		if (analyzer != null)
			__h = 5 * __h + analyzer.hashCode();
		__h = 5 * __h + (isPrimaryKey ? 1 : 0);
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
		__os.writeString(name);
		__os.writeString(type);
		__os.writeString(value);
		__os.writeString(analyzer);
		__os.writeBool(isPrimaryKey);
	}

	public void __read(BasicStream __is)
	{
		name = __is.readString();
		type = __is.readString();
		value = __is.readString();
		analyzer = __is.readString();
		isPrimaryKey = __is.readBool();
	}

}
