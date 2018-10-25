// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Request.java

package Ice;


// Referenced classes of package Ice:
//			Current

public interface Request
{

	public abstract boolean isCollocated();

	public abstract Current getCurrent();
}
