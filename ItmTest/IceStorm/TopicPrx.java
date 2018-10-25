// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TopicPrx.java

package IceStorm;

import Ice.*;
import java.util.Map;

// Referenced classes of package IceStorm:
//			AlreadySubscribed, BadQoS, LinkExists, NoSuchLink, 
//			Callback_Topic_getName, Callback_Topic_getPublisher, Callback_Topic_getNonReplicatedPublisher, Callback_Topic_subscribe, 
//			Callback_Topic_subscribeAndGetPublisher, Callback_Topic_unsubscribe, Callback_Topic_link, Callback_Topic_unlink, 
//			LinkInfo, Callback_Topic_getLinkInfoSeq, Callback_Topic_destroy

public interface TopicPrx
	extends ObjectPrx
{

	public abstract String getName();

	public abstract String getName(Map map);

	public abstract AsyncResult begin_getName();

	public abstract AsyncResult begin_getName(Map map);

	public abstract AsyncResult begin_getName(Callback callback);

	public abstract AsyncResult begin_getName(Map map, Callback callback);

	public abstract AsyncResult begin_getName(Callback_Topic_getName callback_topic_getname);

	public abstract AsyncResult begin_getName(Map map, Callback_Topic_getName callback_topic_getname);

	public abstract String end_getName(AsyncResult asyncresult);

	public abstract ObjectPrx getPublisher();

	public abstract ObjectPrx getPublisher(Map map);

	public abstract AsyncResult begin_getPublisher();

	public abstract AsyncResult begin_getPublisher(Map map);

	public abstract AsyncResult begin_getPublisher(Callback callback);

	public abstract AsyncResult begin_getPublisher(Map map, Callback callback);

	public abstract AsyncResult begin_getPublisher(Callback_Topic_getPublisher callback_topic_getpublisher);

	public abstract AsyncResult begin_getPublisher(Map map, Callback_Topic_getPublisher callback_topic_getpublisher);

	public abstract ObjectPrx end_getPublisher(AsyncResult asyncresult);

	public abstract ObjectPrx getNonReplicatedPublisher();

	public abstract ObjectPrx getNonReplicatedPublisher(Map map);

	public abstract AsyncResult begin_getNonReplicatedPublisher();

	public abstract AsyncResult begin_getNonReplicatedPublisher(Map map);

	public abstract AsyncResult begin_getNonReplicatedPublisher(Callback callback);

	public abstract AsyncResult begin_getNonReplicatedPublisher(Map map, Callback callback);

	public abstract AsyncResult begin_getNonReplicatedPublisher(Callback_Topic_getNonReplicatedPublisher callback_topic_getnonreplicatedpublisher);

	public abstract AsyncResult begin_getNonReplicatedPublisher(Map map, Callback_Topic_getNonReplicatedPublisher callback_topic_getnonreplicatedpublisher);

	public abstract ObjectPrx end_getNonReplicatedPublisher(AsyncResult asyncresult);

	/**
	 * @deprecated Method subscribe is deprecated
	 */

	public abstract void subscribe(Map map, ObjectPrx objectprx);

	/**
	 * @deprecated Method subscribe is deprecated
	 */

	public abstract void subscribe(Map map, ObjectPrx objectprx, Map map1);

	/**
	 * @deprecated Method begin_subscribe is deprecated
	 */

	public abstract AsyncResult begin_subscribe(Map map, ObjectPrx objectprx);

	/**
	 * @deprecated Method begin_subscribe is deprecated
	 */

	public abstract AsyncResult begin_subscribe(Map map, ObjectPrx objectprx, Map map1);

	/**
	 * @deprecated Method begin_subscribe is deprecated
	 */

	public abstract AsyncResult begin_subscribe(Map map, ObjectPrx objectprx, Callback callback);

	/**
	 * @deprecated Method begin_subscribe is deprecated
	 */

	public abstract AsyncResult begin_subscribe(Map map, ObjectPrx objectprx, Map map1, Callback callback);

	/**
	 * @deprecated Method begin_subscribe is deprecated
	 */

	public abstract AsyncResult begin_subscribe(Map map, ObjectPrx objectprx, Callback_Topic_subscribe callback_topic_subscribe);

	/**
	 * @deprecated Method begin_subscribe is deprecated
	 */

	public abstract AsyncResult begin_subscribe(Map map, ObjectPrx objectprx, Map map1, Callback_Topic_subscribe callback_topic_subscribe);

	public abstract void end_subscribe(AsyncResult asyncresult);

	public abstract ObjectPrx subscribeAndGetPublisher(Map map, ObjectPrx objectprx)
		throws AlreadySubscribed, BadQoS;

	public abstract ObjectPrx subscribeAndGetPublisher(Map map, ObjectPrx objectprx, Map map1)
		throws AlreadySubscribed, BadQoS;

	public abstract AsyncResult begin_subscribeAndGetPublisher(Map map, ObjectPrx objectprx);

	public abstract AsyncResult begin_subscribeAndGetPublisher(Map map, ObjectPrx objectprx, Map map1);

	public abstract AsyncResult begin_subscribeAndGetPublisher(Map map, ObjectPrx objectprx, Callback callback);

	public abstract AsyncResult begin_subscribeAndGetPublisher(Map map, ObjectPrx objectprx, Map map1, Callback callback);

	public abstract AsyncResult begin_subscribeAndGetPublisher(Map map, ObjectPrx objectprx, Callback_Topic_subscribeAndGetPublisher callback_topic_subscribeandgetpublisher);

	public abstract AsyncResult begin_subscribeAndGetPublisher(Map map, ObjectPrx objectprx, Map map1, Callback_Topic_subscribeAndGetPublisher callback_topic_subscribeandgetpublisher);

	public abstract ObjectPrx end_subscribeAndGetPublisher(AsyncResult asyncresult)
		throws AlreadySubscribed, BadQoS;

	public abstract void unsubscribe(ObjectPrx objectprx);

	public abstract void unsubscribe(ObjectPrx objectprx, Map map);

	public abstract AsyncResult begin_unsubscribe(ObjectPrx objectprx);

	public abstract AsyncResult begin_unsubscribe(ObjectPrx objectprx, Map map);

	public abstract AsyncResult begin_unsubscribe(ObjectPrx objectprx, Callback callback);

	public abstract AsyncResult begin_unsubscribe(ObjectPrx objectprx, Map map, Callback callback);

	public abstract AsyncResult begin_unsubscribe(ObjectPrx objectprx, Callback_Topic_unsubscribe callback_topic_unsubscribe);

	public abstract AsyncResult begin_unsubscribe(ObjectPrx objectprx, Map map, Callback_Topic_unsubscribe callback_topic_unsubscribe);

	public abstract void end_unsubscribe(AsyncResult asyncresult);

	public abstract void link(TopicPrx topicprx, int i)
		throws LinkExists;

	public abstract void link(TopicPrx topicprx, int i, Map map)
		throws LinkExists;

	public abstract AsyncResult begin_link(TopicPrx topicprx, int i);

	public abstract AsyncResult begin_link(TopicPrx topicprx, int i, Map map);

	public abstract AsyncResult begin_link(TopicPrx topicprx, int i, Callback callback);

	public abstract AsyncResult begin_link(TopicPrx topicprx, int i, Map map, Callback callback);

	public abstract AsyncResult begin_link(TopicPrx topicprx, int i, Callback_Topic_link callback_topic_link);

	public abstract AsyncResult begin_link(TopicPrx topicprx, int i, Map map, Callback_Topic_link callback_topic_link);

	public abstract void end_link(AsyncResult asyncresult)
		throws LinkExists;

	public abstract void unlink(TopicPrx topicprx)
		throws NoSuchLink;

	public abstract void unlink(TopicPrx topicprx, Map map)
		throws NoSuchLink;

	public abstract AsyncResult begin_unlink(TopicPrx topicprx);

	public abstract AsyncResult begin_unlink(TopicPrx topicprx, Map map);

	public abstract AsyncResult begin_unlink(TopicPrx topicprx, Callback callback);

	public abstract AsyncResult begin_unlink(TopicPrx topicprx, Map map, Callback callback);

	public abstract AsyncResult begin_unlink(TopicPrx topicprx, Callback_Topic_unlink callback_topic_unlink);

	public abstract AsyncResult begin_unlink(TopicPrx topicprx, Map map, Callback_Topic_unlink callback_topic_unlink);

	public abstract void end_unlink(AsyncResult asyncresult)
		throws NoSuchLink;

	public abstract LinkInfo[] getLinkInfoSeq();

	public abstract LinkInfo[] getLinkInfoSeq(Map map);

	public abstract AsyncResult begin_getLinkInfoSeq();

	public abstract AsyncResult begin_getLinkInfoSeq(Map map);

	public abstract AsyncResult begin_getLinkInfoSeq(Callback callback);

	public abstract AsyncResult begin_getLinkInfoSeq(Map map, Callback callback);

	public abstract AsyncResult begin_getLinkInfoSeq(Callback_Topic_getLinkInfoSeq callback_topic_getlinkinfoseq);

	public abstract AsyncResult begin_getLinkInfoSeq(Map map, Callback_Topic_getLinkInfoSeq callback_topic_getlinkinfoseq);

	public abstract LinkInfo[] end_getLinkInfoSeq(AsyncResult asyncresult);

	public abstract void destroy();

	public abstract void destroy(Map map);

	public abstract AsyncResult begin_destroy();

	public abstract AsyncResult begin_destroy(Map map);

	public abstract AsyncResult begin_destroy(Callback callback);

	public abstract AsyncResult begin_destroy(Map map, Callback callback);

	public abstract AsyncResult begin_destroy(Callback_Topic_destroy callback_topic_destroy);

	public abstract AsyncResult begin_destroy(Map map, Callback_Topic_destroy callback_topic_destroy);

	public abstract void end_destroy(AsyncResult asyncresult);
}
