// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _TopicOperationsNC.java

package IceStorm;

import Ice.ObjectPrx;
import java.util.Map;

// Referenced classes of package IceStorm:
//			AlreadySubscribed, BadQoS, LinkExists, NoSuchLink, 
//			TopicPrx, LinkInfo

public interface _TopicOperationsNC
{

	public abstract String getName();

	public abstract ObjectPrx getPublisher();

	public abstract ObjectPrx getNonReplicatedPublisher();

	/**
	 * @deprecated Method subscribe is deprecated
	 */

	public abstract void subscribe(Map map, ObjectPrx objectprx);

	public abstract ObjectPrx subscribeAndGetPublisher(Map map, ObjectPrx objectprx)
		throws AlreadySubscribed, BadQoS;

	public abstract void unsubscribe(ObjectPrx objectprx);

	public abstract void link(TopicPrx topicprx, int i)
		throws LinkExists;

	public abstract void unlink(TopicPrx topicprx)
		throws NoSuchLink;

	public abstract LinkInfo[] getLinkInfoSeq();

	public abstract void destroy();
}
