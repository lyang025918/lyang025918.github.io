// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Transceiver.java

package IceInternal;

import Ice.BooleanHolder;
import Ice.ConnectionInfo;
import java.nio.channels.SelectableChannel;

// Referenced classes of package IceInternal:
//			Buffer

public interface Transceiver
{

	public abstract SelectableChannel fd();

	public abstract int initialize();

	public abstract void close();

	public abstract boolean write(Buffer buffer);

	public abstract boolean read(Buffer buffer, BooleanHolder booleanholder);

	public abstract String type();

	public abstract String toString();

	public abstract ConnectionInfo getInfo();

	public abstract void checkSendSize(Buffer buffer, int i);
}
