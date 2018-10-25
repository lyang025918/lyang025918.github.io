// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SSLSessionManagerDel.java

package Glacier2;

import Ice._ObjectDel;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package Glacier2:
//			CannotCreateSessionException, SSLInfo, SessionControlPrx, SessionPrx

public interface _SSLSessionManagerDel
	extends _ObjectDel
{

	public abstract SessionPrx create(SSLInfo sslinfo, SessionControlPrx sessioncontrolprx, Map map)
		throws LocalExceptionWrapper, CannotCreateSessionException;
}
