// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SSLPermissionsVerifierOperationsNC.java

package Glacier2;

import Ice.StringHolder;

// Referenced classes of package Glacier2:
//			SSLInfo

public interface _SSLPermissionsVerifierOperationsNC
{

	public abstract boolean authorize(SSLInfo sslinfo, StringHolder stringholder);
}
