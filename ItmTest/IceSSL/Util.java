// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Util.java

package IceSSL;

import java.io.*;
import java.security.cert.*;

public final class Util
{

	public static final String jdkTarget = "1.5";
	static final boolean $assertionsDisabled = !IceSSL/Util.desiredAssertionStatus();

	public Util()
	{
	}

	public static X509Certificate createCertificate(String certPEM)
		throws CertificateException
	{
		String header = "-----BEGIN CERTIFICATE-----";
		String footer = "-----END CERTIFICATE-----";
		int pos = certPEM.indexOf("-----BEGIN CERTIFICATE-----");
		if (pos == -1)
			certPEM = (new StringBuilder()).append("-----BEGIN CERTIFICATE-----\n").append(certPEM).toString();
		else
		if (pos > 0)
			certPEM = certPEM.substring(pos);
		if (certPEM.indexOf("-----END CERTIFICATE-----") == -1)
			certPEM = (new StringBuilder()).append(certPEM).append("-----END CERTIFICATE-----").toString();
		byte bytes[] = null;
		try
		{
			bytes = certPEM.getBytes("UTF8");
		}
		catch (UnsupportedEncodingException ex)
		{
			if (!$assertionsDisabled)
				throw new AssertionError();
			else
				return null;
		}
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		return (X509Certificate)cf.generateCertificate(in);
	}

	public static void main(String args[])
	{
		System.out.println("1.5");
	}

}
