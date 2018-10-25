// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   X509KeyManagerI.java

package IceSSL;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.*;

final class X509KeyManagerI extends X509ExtendedKeyManager
{

	private X509KeyManager _delegate;
	private String _alias;

	X509KeyManagerI(X509KeyManager del, String alias)
	{
		_delegate = del;
		_alias = alias;
	}

	public String chooseClientAlias(String keyType[], Principal issuers[], Socket socket)
	{
		return _alias;
	}

	public String chooseEngineClientAlias(String keyType[], Principal issuers[], SSLEngine engine)
	{
		return _alias;
	}

	public String chooseServerAlias(String keyType, Principal issuers[], Socket socket)
	{
		return _alias;
	}

	public String chooseEngineServerAlias(String keyType[], Principal issuers[], SSLEngine engine)
	{
		return _alias;
	}

	public X509Certificate[] getCertificateChain(String alias)
	{
		return _delegate.getCertificateChain(alias);
	}

	public String[] getClientAliases(String keyType, Principal issuers[])
	{
		return _delegate.getClientAliases(keyType, issuers);
	}

	public String[] getServerAliases(String keyType, Principal issuers[])
	{
		return _delegate.getServerAliases(keyType, issuers);
	}

	public PrivateKey getPrivateKey(String alias)
	{
		return _delegate.getPrivateKey(alias);
	}
}
