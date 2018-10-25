// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ServantLocatorOperationsNC.java

package Ice;


// Referenced classes of package Ice:
//			UserException, Current, LocalObjectHolder, Object

public interface _ServantLocatorOperationsNC
{

	public abstract Object locate(Current current, LocalObjectHolder localobjectholder)
		throws UserException;

	public abstract void finished(Current current, Object obj, Object obj1)
		throws UserException;

	public abstract void deactivate(String s);
}
