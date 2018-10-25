// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _TopicDel.java

package IceStorm;

import Ice.ObjectPrx;
import Ice._ObjectDel;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceStorm:
//			AlreadySubscribed, BadQoS, LinkExists, NoSuchLink, 
//			TopicPrx, LinkInfo

public interface _TopicDel
	extends _ObjectDel
{

	public abstract String getName(Map map)
		throws LocalExceptionWrapper;

	public abstract ObjectPrx getPublisher(Map map)
		throws LocalExceptionWrapper;

	public abstract ObjectPrx getNonReplicatedPublisher(Map map)
		throws LocalExceptionWrapper;

	public abstract void subscribe(Map map, ObjectPrx objectprx, Map map1)
		throws LocalExceptionWrapper;

	public abstract ObjectPrx subscribeAndGetPublisher(Map map, ObjectPrx objectprx, Map map1)
		throws LocalExceptionWrapper, AlreadySubscribed, BadQoS;

	public abstract void unsubscribe(ObjectPrx objectprx, Map map)
		throws LocalExceptionWrapper;

	public abstract void link(TopicPrx topicprx, int i, Map map)
		throws LocalExceptionWrapper, LinkExists;

	public abstract void unlink(TopicPrx topicprx, Map map)
		throws LocalExceptionWrapper, NoSuchLink;

	public abstract LinkInfo[] getLinkInfoSeq(Map map)
		throws LocalExceptionWrapper;

	public abstract void destroy(Map map)
		throws LocalExceptionWrapper;
}
