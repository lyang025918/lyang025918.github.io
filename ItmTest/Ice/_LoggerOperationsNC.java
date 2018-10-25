// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _LoggerOperationsNC.java

package Ice;


// Referenced classes of package Ice:
//			Logger

public interface _LoggerOperationsNC
{

	public abstract void print(String s);

	public abstract void trace(String s, String s1);

	public abstract void warning(String s);

	public abstract void error(String s);

	public abstract Logger cloneWithPrefix(String s);
}
