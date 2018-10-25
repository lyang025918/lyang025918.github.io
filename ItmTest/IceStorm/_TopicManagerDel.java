// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _TopicManagerDel.java

package IceStorm;

import Ice._ObjectDel;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceStorm:
//			TopicExists, NoSuchTopic, TopicPrx

public interface _TopicManagerDel
	extends _ObjectDel
{

	public abstract TopicPrx create(String s, Map map)
		throws LocalExceptionWrapper, TopicExists;

	public abstract TopicPrx retrieve(String s, Map map)
		throws LocalExceptionWrapper, NoSuchTopic;

	public abstract Map retrieveAll(Map map)
		throws LocalExceptionWrapper;

	public abstract Map getSliceChecksums(Map map)
		throws LocalExceptionWrapper;
}
