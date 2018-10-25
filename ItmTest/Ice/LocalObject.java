// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LocalObject.java

package Ice;


/**
 * @deprecated Interface LocalObject is deprecated
 */

public interface LocalObject
{

	public abstract boolean equals(Object obj);

	public abstract Object clone()
		throws CloneNotSupportedException;

	public abstract int ice_hash();
}
