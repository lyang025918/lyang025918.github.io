// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PluginFactory.java

package Ice;


// Referenced classes of package Ice:
//			Communicator, Plugin

public interface PluginFactory
{

	public abstract Plugin create(Communicator communicator, String s, String as[]);
}
