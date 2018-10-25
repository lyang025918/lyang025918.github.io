// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ApplicationInfo.java

package IceGrid;

import IceInternal.BasicStream;
import java.io.Serializable;

// Referenced classes of package IceGrid:
//			ApplicationDescriptor

public class ApplicationInfo
	implements Cloneable, Serializable
{

	public String uuid;
	public long createTime;
	public String createUser;
	public long updateTime;
	public String updateUser;
	public int revision;
	public ApplicationDescriptor descriptor;
	static final boolean $assertionsDisabled = !IceGrid/ApplicationInfo.desiredAssertionStatus();

	public ApplicationInfo()
	{
	}

	public ApplicationInfo(String uuid, long createTime, String createUser, long updateTime, String updateUser, 
			int revision, ApplicationDescriptor descriptor)
	{
		this.uuid = uuid;
		this.createTime = createTime;
		this.createUser = createUser;
		this.updateTime = updateTime;
		this.updateUser = updateUser;
		this.revision = revision;
		this.descriptor = descriptor;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		ApplicationInfo _r = null;
		try
		{
			_r = (ApplicationInfo)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (uuid != _r.uuid && (uuid == null || _r.uuid == null || !uuid.equals(_r.uuid)))
				return false;
			if (createTime != _r.createTime)
				return false;
			if (createUser != _r.createUser && (createUser == null || _r.createUser == null || !createUser.equals(_r.createUser)))
				return false;
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
		if (uuid != null)
			__h = 5 * __h + uuid.hashCode();
		__h = 5 * __h + (int)createTime;
		if (createUser != null)
			__h = 5 * __h + createUser.hashCode();
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
		__os.writeString(uuid);
		__os.writeLong(createTime);
		__os.writeString(createUser);
		__os.writeLong(updateTime);
		__os.writeString(updateUser);
		__os.writeInt(revision);
		descriptor.__write(__os);
	}

	public void __read(BasicStream __is)
	{
		uuid = __is.readString();
		createTime = __is.readLong();
		createUser = __is.readString();
		updateTime = __is.readLong();
		updateUser = __is.readString();
		revision = __is.readInt();
		descriptor = new ApplicationDescriptor();
		descriptor.__read(__is);
	}

}
