// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CertificateVerifier.java

package IceSSL;


// Referenced classes of package IceSSL:
//			NativeConnectionInfo

public interface CertificateVerifier
{

	public abstract boolean verify(NativeConnectionInfo nativeconnectioninfo);
}
