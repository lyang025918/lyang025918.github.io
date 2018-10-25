// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PluginManager.java

package Ice;


// Referenced classes of package Ice:
//			Plugin

public interface PluginManager
{

	public abstract void initializePlugins();

	public abstract String[] getPlugins();

	public abstract Plugin getPlugin(String s);

	public abstract void addPlugin(String s, Plugin plugin);

	public abstract void destroy();
}
