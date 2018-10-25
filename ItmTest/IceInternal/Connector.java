// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Connector.java

package IceInternal;


// Referenced classes of package IceInternal:
//			Transceiver

public interface Connector
{

	public abstract Transceiver connect();

	public abstract short type();

	public abstract String toString();

	public abstract boolean equals(Object obj);
}
