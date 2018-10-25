// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertySetDescriptor.java

package IceGrid;

import Ice.StringSeqHelper;
import IceInternal.BasicStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

// Referenced classes of package IceGrid:
//			PropertyDescriptorSeqHelper

public class PropertySetDescriptor
	implements Cloneable, Serializable
{

	public String references[];
	public List properties;
	static final boolean $assertionsDisabled = !IceGrid/PropertySetDescriptor.desiredAssertionStatus();

	public PropertySetDescriptor()
	{
	}

	public PropertySetDescriptor(String references[], List properties)
	{
		this.references = references;
		this.properties = properties;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		PropertySetDescriptor _r = null;
		try
		{
			_r = (PropertySetDescriptor)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (!Arrays.equals(references, _r.references))
				return false;
			return properties == _r.properties || properties != null && _r.properties != null && properties.equals(_r.properties);
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		int __h = 0;
		if (references != null)
		{
			for (int __i0 = 0; __i0 < references.length; __i0++)
				if (references[__i0] != null)
					__h = 5 * __h + references[__i0].hashCode();

		}
		if (properties != null)
			__h = 5 * __h + properties.hashCode();
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
		StringSeqHelper.write(__os, references);
		PropertyDescriptorSeqHelper.write(__os, properties);
	}

	public void __read(BasicStream __is)
	{
		references = StringSeqHelper.read(__is);
		properties = PropertyDescriptorSeqHelper.read(__is);
	}

}
