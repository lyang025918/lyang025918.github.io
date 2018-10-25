// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SSLSessionManagerOperationsNC.java

package Glacier2;


// Referenced classes of package Glacier2:
//			CannotCreateSessionException, SSLInfo, SessionControlPrx, SessionPrx

public interface _SSLSessionManagerOperationsNC
{

	public abstract SessionPrx create(SSLInfo sslinfo, SessionControlPrx sessioncontrolprx)
		throws CannotCreateSessionException;
}
