// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NativeConnectionInfo.java

package IceSSL;

import java.security.cert.Certificate;

// Referenced classes of package IceSSL:
//			ConnectionInfo

public class NativeConnectionInfo extends ConnectionInfo
{

	public Certificate nativeCerts[];

	public NativeConnectionInfo()
	{
	}
}
