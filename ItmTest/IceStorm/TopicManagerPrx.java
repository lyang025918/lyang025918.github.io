// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TopicManagerPrx.java

package IceStorm;

import Ice.*;
import java.util.Map;

// Referenced classes of package IceStorm:
//			TopicExists, NoSuchTopic, TopicPrx, Callback_TopicManager_create, 
//			Callback_TopicManager_retrieve, Callback_TopicManager_retrieveAll, Callback_TopicManager_getSliceChecksums

public interface TopicManagerPrx
	extends ObjectPrx
{

	public abstract TopicPrx create(String s)
		throws TopicExists;

	public abstract TopicPrx create(String s, Map map)
		throws TopicExists;

	public abstract AsyncResult begin_create(String s);

	public abstract AsyncResult begin_create(String s, Map map);

	public abstract AsyncResult begin_create(String s, Callback callback);

	public abstract AsyncResult begin_create(String s, Map map, Callback callback);

	public abstract AsyncResult begin_create(String s, Callback_TopicManager_create callback_topicmanager_create);

	public abstract AsyncResult begin_create(String s, Map map, Callback_TopicManager_create callback_topicmanager_create);

	public abstract TopicPrx end_create(AsyncResult asyncresult)
		throws TopicExists;

	public abstract TopicPrx retrieve(String s)
		throws NoSuchTopic;

	public abstract TopicPrx retrieve(String s, Map map)
		throws NoSuchTopic;

	public abstract AsyncResult begin_retrieve(String s);

	public abstract AsyncResult begin_retrieve(String s, Map map);

	public abstract AsyncResult begin_retrieve(String s, Callback callback);

	public abstract AsyncResult begin_retrieve(String s, Map map, Callback callback);

	public abstract AsyncResult begin_retrieve(String s, Callback_TopicManager_retrieve callback_topicmanager_retrieve);

	public abstract AsyncResult begin_retrieve(String s, Map map, Callback_TopicManager_retrieve callback_topicmanager_retrieve);

	public abstract TopicPrx end_retrieve(AsyncResult asyncresult)
		throws NoSuchTopic;

	public abstract Map retrieveAll();

	public abstract Map retrieveAll(Map map);

	public abstract AsyncResult begin_retrieveAll();

	public abstract AsyncResult begin_retrieveAll(Map map);

	public abstract AsyncResult begin_retrieveAll(Callback callback);

	public abstract AsyncResult begin_retrieveAll(Map map, Callback callback);

	public abstract AsyncResult begin_retrieveAll(Callback_TopicManager_retrieveAll callback_topicmanager_retrieveall);

	public abstract AsyncResult begin_retrieveAll(Map map, Callback_TopicManager_retrieveAll callback_topicmanager_retrieveall);

	public abstract Map end_retrieveAll(AsyncResult asyncresult);

	public abstract Map getSliceChecksums();

	public abstract Map getSliceChecksums(Map map);

	public abstract AsyncResult begin_getSliceChecksums();

	public abstract AsyncResult begin_getSliceChecksums(Map map);

	public abstract AsyncResult begin_getSliceChecksums(Callback callback);

	public abstract AsyncResult begin_getSliceChecksums(Map map, Callback callback);

	public abstract AsyncResult begin_getSliceChecksums(Callback_TopicManager_getSliceChecksums callback_topicmanager_getslicechecksums);

	public abstract AsyncResult begin_getSliceChecksums(Map map, Callback_TopicManager_getSliceChecksums callback_topicmanager_getslicechecksums);

	public abstract Map end_getSliceChecksums(AsyncResult asyncresult);
}
