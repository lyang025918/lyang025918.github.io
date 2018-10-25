// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ImplicitContext.java

package Ice;

import java.util.Map;

public interface ImplicitContext
{

	public abstract Map getContext();

	public abstract void setContext(Map map);

	public abstract boolean containsKey(String s);

	public abstract String get(String s);

	public abstract String put(String s, String s1);

	public abstract String remove(String s);
}
