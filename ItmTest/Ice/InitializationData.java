// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InitializationData.java

package Ice;


// Referenced classes of package Ice:
//			Properties, Logger, Stats, ThreadNotification, 
//			Dispatcher

public final class InitializationData
	implements Cloneable
{

	public Properties properties;
	public Logger logger;
	public Stats stats;
	public ThreadNotification threadHook;
	public ClassLoader classLoader;
	public Dispatcher dispatcher;

	public InitializationData()
	{
	}

	public Object clone()
	{
		Object o = null;
		try
		{
			o = super.clone();
		}
		catch (CloneNotSupportedException ex) { }
		return o;
	}
}
