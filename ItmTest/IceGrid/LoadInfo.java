// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LoadInfo.java

package IceGrid;

import IceInternal.BasicStream;
import java.io.Serializable;

public class LoadInfo
	implements Cloneable, Serializable
{

	public float avg1;
	public float avg5;
	public float avg15;
	static final boolean $assertionsDisabled = !IceGrid/LoadInfo.desiredAssertionStatus();

	public LoadInfo()
	{
	}

	public LoadInfo(float avg1, float avg5, float avg15)
	{
		this.avg1 = avg1;
		this.avg5 = avg5;
		this.avg15 = avg15;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		LoadInfo _r = null;
		try
		{
			_r = (LoadInfo)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (avg1 != _r.avg1)
				return false;
			if (avg5 != _r.avg5)
				return false;
			return avg15 == _r.avg15;
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		int __h = 0;
		__h = 5 * __h + Float.floatToIntBits(avg1);
		__h = 5 * __h + Float.floatToIntBits(avg5);
		__h = 5 * __h + Float.floatToIntBits(avg15);
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
		__os.writeFloat(avg1);
		__os.writeFloat(avg5);
		__os.writeFloat(avg15);
	}

	public void __read(BasicStream __is)
	{
		avg1 = __is.readFloat();
		avg5 = __is.readFloat();
		avg15 = __is.readFloat();
	}

}
