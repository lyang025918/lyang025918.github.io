// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ApplicationUpdateInfo.java

package IceGrid;

import IceInternal.BasicStream;
import java.io.Serializable;

// Referenced classes of package IceGrid:
//			ApplicationUpdateDescriptor

public class ApplicationUpdateInfo
	implements Cloneable, Serializable
{

	public long updateTime;
	public String updateUser;
	public int revision;
	public ApplicationUpdateDescriptor descriptor;
	static final boolean $assertionsDisabled = !IceGrid/ApplicationUpdateInfo.desiredAssertionStatus();

	public ApplicationUpdateInfo()
	{
	}

	public ApplicationUpdateInfo(long updateTime, String updateUser, int revision, ApplicationUpdateDescriptor descriptor)
	{
		this.updateTime = updateTime;
		this.updateUser = updateUser;
		this.revision = revision;
		this.descriptor = descriptor;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		ApplicationUpdateInfo _r = null;
		try
		{
			_r = (ApplicationUpdateInfo)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (updateTime != _r.updateTime)
				return false;
			if (updateUser != _r.updateUser && (updateUser == null || _r.updateUser == null || !updateUser.equals(_r.updateUser)))
				return false;
			if (revision != _r.revision)
				return false;
			return descriptor == _r.descriptor || descriptor != null && _r.descriptor != null && descriptor.equals(_r.descriptor);
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		int __h = 0;
		__h = 5 * __h + (int)updateTime;
		if (updateUser != null)
			__h = 5 * __h + updateUser.hashCode();
		__h = 5 * __h + revision;
		if (descriptor != null)
			__h = 5 * __h + descriptor.hashCode();
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
		__os.writeLong(updateTime);
		__os.writeString(updateUser);
		__os.writeInt(revision);
		descriptor.__write(__os);
	}

	public void __read(BasicStream __is)
	{
		updateTime = __is.readLong();
		updateUser = __is.readString();
		revision = __is.readInt();
		descriptor = new ApplicationUpdateDescriptor();
		descriptor.__read(__is);
	}

}
