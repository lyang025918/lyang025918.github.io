// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _TopicManagerOperations.java

package IceStorm;

import Ice.Current;
import java.util.Map;

// Referenced classes of package IceStorm:
//			TopicExists, NoSuchTopic, TopicPrx

public interface _TopicManagerOperations
{

	public abstract TopicPrx create(String s, Current current)
		throws TopicExists;

	public abstract TopicPrx retrieve(String s, Current current)
		throws NoSuchTopic;

	public abstract Map retrieveAll(Current current);

	public abstract Map getSliceChecksums(Current current);
}
