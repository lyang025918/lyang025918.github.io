// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DistributionDescriptor.java

package IceGrid;

import IceInternal.BasicStream;
import java.io.Serializable;
import java.util.*;

public class DistributionDescriptor
	implements Cloneable, Serializable
{

	public String icepatch;
	public List directories;
	static final boolean $assertionsDisabled = !IceGrid/DistributionDescriptor.desiredAssertionStatus();

	public DistributionDescriptor()
	{
	}

	public DistributionDescriptor(String icepatch, List directories)
	{
		this.icepatch = icepatch;
		this.directories = directories;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		DistributionDescriptor _r = null;
		try
		{
			_r = (DistributionDescriptor)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (icepatch != _r.icepatch && (icepatch == null || _r.icepatch == null || !icepatch.equals(_r.icepatch)))
				return false;
			return directories == _r.directories || directories != null && _r.directories != null && directories.equals(_r.directories);
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		int __h = 0;
		if (icepatch != null)
			__h = 5 * __h + icepatch.hashCode();
		if (directories != null)
			__h = 5 * __h + directories.hashCode();
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
		__os.writeString(icepatch);
		if (directories == null)
		{
			__os.writeSize(0);
		} else
		{
			__os.writeSize(directories.size());
			String __elem;
			for (Iterator i$ = directories.iterator(); i$.hasNext(); __os.writeString(__elem))
				__elem = (String)i$.next();

		}
	}

	public void __read(BasicStream __is)
	{
		icepatch = __is.readString();
		directories = new LinkedList();
		int __len0 = __is.readAndCheckSeqSize(1);
		for (int __i0 = 0; __i0 < __len0; __i0++)
		{
			String __elem = __is.readString();
			directories.add(__elem);
		}

	}

}
