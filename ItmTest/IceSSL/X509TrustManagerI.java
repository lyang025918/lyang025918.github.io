// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   X509TrustManagerI.java

package IceSSL;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

// Referenced classes of package IceSSL:
//			Instance

final class X509TrustManagerI
	implements X509TrustManager
{

	private Instance _instance;
	private X509TrustManager _delegate;

	X509TrustManagerI(Instance instance, X509TrustManager delegate)
	{
		_instance = instance;
		_delegate = delegate;
	}

	public void checkClientTrusted(X509Certificate chain[], String authType)
		throws CertificateException
	{
		if (!authType.equals("DH_anon"))
			try
			{
				_delegate.checkClientTrusted(chain, authType);
			}
			catch (CertificateException ex)
			{
				_instance.trustManagerFailure(true, ex);
			}
	}

	public void checkServerTrusted(X509Certificate chain[], String authType)
		throws CertificateException
	{
		if (!authType.equals("DH_anon"))
			try
			{
				_delegate.checkServerTrusted(chain, authType);
			}
			catch (CertificateException ex)
			{
				_instance.trustManagerFailure(false, ex);
			}
	}

	public X509Certificate[] getAcceptedIssuers()
	{
		return _delegate.getAcceptedIssuers();
	}
}
