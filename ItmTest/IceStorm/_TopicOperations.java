// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _TopicOperations.java

package IceStorm;

import Ice.Current;
import Ice.ObjectPrx;
import java.util.Map;

// Referenced classes of package IceStorm:
//			AlreadySubscribed, BadQoS, LinkExists, NoSuchLink, 
//			TopicPrx, LinkInfo

public interface _TopicOperations
{

	public abstract String getName(Current current);

	public abstract ObjectPrx getPublisher(Current current);

	public abstract ObjectPrx getNonReplicatedPublisher(Current current);

	/**
	 * @deprecated Method subscribe is deprecated
	 */

	public abstract void subscribe(Map map, ObjectPrx objectprx, Current current);

	public abstract ObjectPrx subscribeAndGetPublisher(Map map, ObjectPrx objectprx, Current current)
		throws AlreadySubscribed, BadQoS;

	public abstract void unsubscribe(ObjectPrx objectprx, Current current);

	public abstract void link(TopicPrx topicprx, int i, Current current)
		throws LinkExists;

	public abstract void unlink(TopicPrx topicprx, Current current)
		throws NoSuchLink;

	public abstract LinkInfo[] getLinkInfoSeq(Current current);

	public abstract void destroy(Current current);
}
