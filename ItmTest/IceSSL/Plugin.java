// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Plugin.java

package IceSSL;

import java.io.InputStream;
import javax.net.ssl.SSLContext;

// Referenced classes of package IceSSL:
//			CertificateVerifier, PasswordCallback

public interface Plugin
	extends Ice.Plugin
{

	public abstract void setContext(SSLContext sslcontext);

	public abstract SSLContext getContext();

	public abstract void setCertificateVerifier(CertificateVerifier certificateverifier);

	public abstract CertificateVerifier getCertificateVerifier();

	public abstract void setPasswordCallback(PasswordCallback passwordcallback);

	public abstract PasswordCallback getPasswordCallback();

	public abstract void setKeystoreStream(InputStream inputstream);

	public abstract void setTruststoreStream(InputStream inputstream);

	public abstract void addSeedStream(InputStream inputstream);
}
