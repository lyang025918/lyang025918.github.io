// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ProcessOperations.java

package Ice;


// Referenced classes of package Ice:
//			Current

public interface _ProcessOperations
{

	public abstract void shutdown(Current current);

	public abstract void writeMessage(String s, int i, Current current);
}
