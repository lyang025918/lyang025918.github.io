// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _TopicManagerOperationsNC.java

package IceStorm;

import java.util.Map;

// Referenced classes of package IceStorm:
//			TopicExists, NoSuchTopic, TopicPrx

public interface _TopicManagerOperationsNC
{

	public abstract TopicPrx create(String s)
		throws TopicExists;

	public abstract TopicPrx retrieve(String s)
		throws NoSuchTopic;

	public abstract Map retrieveAll();

	public abstract Map getSliceChecksums();
}
