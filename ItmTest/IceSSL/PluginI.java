// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PluginI.java

package IceSSL;

import Ice.Communicator;
import java.io.InputStream;
import javax.net.ssl.SSLContext;

// Referenced classes of package IceSSL:
//			Instance, Plugin, CertificateVerifier, PasswordCallback

class PluginI
	implements Plugin
{

	private Instance _instance;

	public PluginI(Communicator communicator)
	{
		_instance = new Instance(communicator);
	}

	public void initialize()
	{
		_instance.initialize();
	}

	public void destroy()
	{
	}

	public void setContext(SSLContext context)
	{
		_instance.context(context);
	}

	public SSLContext getContext()
	{
		return _instance.context();
	}

	public void setCertificateVerifier(CertificateVerifier verifier)
	{
		_instance.setCertificateVerifier(verifier);
	}

	public CertificateVerifier getCertificateVerifier()
	{
		return _instance.getCertificateVerifier();
	}

	public void setPasswordCallback(PasswordCallback callback)
	{
		_instance.setPasswordCallback(callback);
	}

	public PasswordCallback getPasswordCallback()
	{
		return _instance.getPasswordCallback();
	}

	public void setKeystoreStream(InputStream stream)
	{
		_instance.setKeystoreStream(stream);
	}

	public void setTruststoreStream(InputStream stream)
	{
		_instance.setTruststoreStream(stream);
	}

	public void addSeedStream(InputStream stream)
	{
		_instance.addSeedStream(stream);
	}
}
