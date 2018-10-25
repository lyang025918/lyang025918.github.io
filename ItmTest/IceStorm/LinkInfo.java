// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LinkInfo.java

package IceStorm;

import IceInternal.BasicStream;
import java.io.Serializable;

// Referenced classes of package IceStorm:
//			TopicPrxHelper, TopicPrx

public class LinkInfo
	implements Cloneable, Serializable
{

	public TopicPrx theTopic;
	public String name;
	public int cost;
	static final boolean $assertionsDisabled = !IceStorm/LinkInfo.desiredAssertionStatus();

	public LinkInfo()
	{
	}

	public LinkInfo(TopicPrx theTopic, String name, int cost)
	{
		this.theTopic = theTopic;
		this.name = name;
		this.cost = cost;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		LinkInfo _r = null;
		try
		{
			_r = (LinkInfo)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (theTopic != _r.theTopic && (theTopic == null || _r.theTopic == null || !theTopic.equals(_r.theTopic)))
				return false;
			if (name != _r.name && (name == null || _r.name == null || !name.equals(_r.name)))
				return false;
			return cost == _r.cost;
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		int __h = 0;
		if (theTopic != null)
			__h = 5 * __h + theTopic.hashCode();
		if (name != null)
			__h = 5 * __h + name.hashCode();
		__h = 5 * __h + cost;
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
		TopicPrxHelper.__write(__os, theTopic);
		__os.writeString(name);
		__os.writeInt(cost);
	}

	public void __read(BasicStream __is)
	{
		theTopic = TopicPrxHelper.__read(__is);
		name = __is.readString();
		cost = __is.readInt();
	}

}
