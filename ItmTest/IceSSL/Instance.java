// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Instance.java

package IceSSL;

import Ice.*;
import IceInternal.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;
import java.security.*;
import java.security.cert.*;
import java.util.*;
import java.util.regex.*;
import javax.net.ssl.*;
import javax.security.auth.x500.X500Principal;

// Referenced classes of package IceSSL:
//			TrustManager, EndpointFactoryI, CertificateVerifier, PasswordCallback, 
//			X509KeyManagerI, X509TrustManagerI, NativeConnectionInfo

class Instance
{
	private static class CipherExpression
	{

		boolean not;
		String cipher;
		Pattern re;

		private CipherExpression()
		{
		}

	}


	private Logger _logger;
	private ProtocolPluginFacade _facade;
	private int _securityTraceLevel;
	private String _securityTraceCategory;
	private boolean _initialized;
	private SSLContext _context;
	private String _defaultDir;
	private CipherExpression _ciphers[];
	private boolean _allCiphers;
	private boolean _noCiphers;
	private String _protocols[];
	private boolean _checkCertName;
	private int _verifyDepthMax;
	private int _verifyPeer;
	private CertificateVerifier _verifier;
	private PasswordCallback _passwordCallback;
	private TrustManager _trustManager;
	private InputStream _keystoreStream;
	private InputStream _truststoreStream;
	private List _seeds;
	static final boolean $assertionsDisabled = !IceSSL/Instance.desiredAssertionStatus();

	Instance(Communicator communicator)
	{
		_seeds = new ArrayList();
		_logger = communicator.getLogger();
		_facade = Util.getProtocolPluginFacade(communicator);
		_securityTraceLevel = communicator.getProperties().getPropertyAsIntWithDefault("IceSSL.Trace.Security", 0);
		_securityTraceCategory = "Security";
		_trustManager = new TrustManager(communicator);
		_facade.addEndpointFactory(new EndpointFactoryI(this));
	}

	void initialize()
	{
		Properties properties;
		Throwable ex;
		if (_initialized)
			return;
		String prefix = "IceSSL.";
		properties = communicator().getProperties();
		String ciphers = properties.getProperty("IceSSL.Ciphers");
		if (ciphers.length() > 0)
			parseCiphers(ciphers);
		String protocols[] = properties.getPropertyAsList("IceSSL.Protocols");
		if (protocols.length != 0)
		{
			ArrayList l = new ArrayList();
			String arr$[] = protocols;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				String prot = arr$[i$];
				String s = prot.toLowerCase();
				if (s.equals("ssl3") || s.equals("sslv3"))
				{
					l.add("SSLv3");
					continue;
				}
				if (s.equals("tls") || s.equals("tls1") || s.equals("tlsv1"))
				{
					l.add("TLSv1");
				} else
				{
					PluginInitializationException e = new PluginInitializationException();
					e.reason = (new StringBuilder()).append("IceSSL: unrecognized protocol `").append(prot).append("'").toString();
					throw e;
				}
			}

			_protocols = new String[l.size()];
			l.toArray(_protocols);
		}
		_checkCertName = properties.getPropertyAsIntWithDefault("IceSSL.CheckCertName", 0) > 0;
		_verifyDepthMax = properties.getPropertyAsIntWithDefault("IceSSL.VerifyDepthMax", 2);
		_verifyPeer = communicator().getProperties().getPropertyAsIntWithDefault("IceSSL.VerifyPeer", 2);
		String certVerifierClass = properties.getProperty("IceSSL.CertVerifier");
		if (certVerifierClass.length() > 0)
		{
			if (_verifier != null)
			{
				PluginInitializationException e = new PluginInitializationException();
				e.reason = "IceSSL: certificate verifier already installed";
				throw e;
			}
			Class cls = null;
			try
			{
				cls = _facade.findClass(certVerifierClass);
			}
			// Misplaced declaration of an exception variable
			catch (Throwable ex)
			{
				throw new PluginInitializationException((new StringBuilder()).append("IceSSL: unable to load certificate verifier class ").append(certVerifierClass).toString(), ex);
			}
			try
			{
				_verifier = (CertificateVerifier)cls.newInstance();
			}
			// Misplaced declaration of an exception variable
			catch (Throwable ex)
			{
				throw new PluginInitializationException((new StringBuilder()).append("IceSSL: unable to instantiate certificate verifier class ").append(certVerifierClass).toString(), ex);
			}
		}
		String passwordCallbackClass = properties.getProperty("IceSSL.PasswordCallback");
		if (passwordCallbackClass.length() > 0)
		{
			if (_passwordCallback != null)
			{
				PluginInitializationException e = new PluginInitializationException();
				e.reason = "IceSSL: password callback already installed";
				throw e;
			}
			Class cls = null;
			try
			{
				cls = _facade.findClass(passwordCallbackClass);
			}
			catch (Throwable ex)
			{
				throw new PluginInitializationException((new StringBuilder()).append("IceSSL: unable to load password callback class ").append(passwordCallbackClass).toString(), ex);
			}
			try
			{
				_passwordCallback = (PasswordCallback)cls.newInstance();
			}
			catch (Throwable ex)
			{
				throw new PluginInitializationException((new StringBuilder()).append("IceSSL: unable to instantiate password callback class ").append(passwordCallbackClass).toString(), ex);
			}
		}
		if (_context != null)
			break MISSING_BLOCK_LABEL_1908;
		SecureRandom rand;
		byte seed[];
		InputStream in;
		Exception exception;
		_defaultDir = properties.getProperty("IceSSL.DefaultDir");
		rand = new SecureRandom();
		String seedFiles = properties.getProperty("IceSSL.Random");
		if (seedFiles.length() > 0)
		{
			String arr[] = seedFiles.split(File.pathSeparator);
			String arr$[] = arr;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				String file = arr$[i$];
				try
				{
					InputStream seedStream = openResource(file);
					if (seedStream == null)
					{
						PluginInitializationException e = new PluginInitializationException();
						e.reason = (new StringBuilder()).append("IceSSL: random seed file not found:\n").append(file).toString();
						throw e;
					}
					_seeds.add(seedStream);
				}
				catch (IOException ex)
				{
					throw new PluginInitializationException((new StringBuilder()).append("IceSSL: unable to access random seed file:\n").append(file).toString(), ex);
				}
			}

		}
		if (_seeds.isEmpty())
			break MISSING_BLOCK_LABEL_933;
		seed = null;
		int start = 0;
		for (Iterator i$ = _seeds.iterator(); i$.hasNext();)
		{
			in = (InputStream)i$.next();
			try
			{
				int num = in.available();
				if (seed == null)
				{
					seed = new byte[num];
				} else
				{
					byte tmp[] = new byte[seed.length + num];
					System.arraycopy(seed, 0, tmp, 0, seed.length);
					start = seed.length;
					seed = tmp;
				}
				in.read(seed, start, num);
			}
			catch (IOException ex)
			{
				throw new PluginInitializationException("IceSSL: error while reading random seed", ex);
			}
			finally { }
			try
			{
				in.close();
			}
			catch (IOException e) { }
		}

		break MISSING_BLOCK_LABEL_926;
		try
		{
			in.close();
		}
		catch (IOException e) { }
		throw exception;
		rand.setSeed(seed);
		String keystorePath;
		String password;
		String alias;
		String truststorePath;
		String truststorePassword;
		String truststoreType;
		javax.net.ssl.KeyManager keyManagers[];
		KeyStore keys;
		InputStream keystoreStream;
		Exception exception1;
		_seeds.clear();
		rand.nextInt();
		keystorePath = properties.getProperty("IceSSL.Keystore");
		password = properties.getProperty("IceSSL.Password");
		String keystorePassword = properties.getProperty("IceSSL.KeystorePassword");
		String defaultType = KeyStore.getDefaultType();
		String keystoreType = properties.getPropertyWithDefault("IceSSL.KeystoreType", defaultType);
		alias = properties.getProperty("IceSSL.Alias");
		truststorePath = properties.getProperty("IceSSL.Truststore");
		truststorePassword = properties.getProperty("IceSSL.TruststorePassword");
		truststoreType = properties.getPropertyWithDefault("IceSSL.TruststoreType", KeyStore.getDefaultType());
		keyManagers = null;
		keys = null;
		if (_keystoreStream == null && keystorePath.length() <= 0)
			break MISSING_BLOCK_LABEL_1470;
		keystoreStream = null;
		try
		{
			if (_keystoreStream != null)
			{
				keystoreStream = _keystoreStream;
			} else
			{
				keystoreStream = openResource(keystorePath);
				if (keystoreStream == null)
				{
					PluginInitializationException e = new PluginInitializationException();
					e.reason = (new StringBuilder()).append("IceSSL: keystore not found:\n").append(keystorePath).toString();
					throw e;
				}
			}
			keys = KeyStore.getInstance(keystoreType);
			char passwordChars[] = null;
			if (keystorePassword.length() > 0)
				passwordChars = keystorePassword.toCharArray();
			else
			if (_passwordCallback != null)
				passwordChars = _passwordCallback.getKeystorePassword();
			else
			if (keystoreType.equals("BKS"))
				passwordChars = new char[0];
			keys.load(keystoreStream, passwordChars);
			if (passwordChars != null)
				Arrays.fill(passwordChars, '\0');
			keystorePassword = null;
		}
		catch (IOException ex)
		{
			throw new PluginInitializationException((new StringBuilder()).append("IceSSL: unable to load keystore:\n").append(keystorePath).toString(), ex);
		}
		finally
		{
			if (keystoreStream == null) goto _L0; else goto _L0
		}
		if (keystoreStream != null)
			try
			{
				keystoreStream.close();
			}
			catch (IOException e) { }
		break MISSING_BLOCK_LABEL_1285;
		try
		{
			keystoreStream.close();
		}
		catch (IOException e) { }
		throw exception1;
		String algorithm = KeyManagerFactory.getDefaultAlgorithm();
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
		char passwordChars[] = new char[0];
		if (password.length() > 0)
			passwordChars = password.toCharArray();
		else
		if (_passwordCallback != null)
			passwordChars = _passwordCallback.getPassword(alias);
		kmf.init(keys, passwordChars);
		if (passwordChars.length > 0)
			Arrays.fill(passwordChars, '\0');
		password = null;
		keyManagers = kmf.getKeyManagers();
		if (alias.length() > 0)
		{
			if (!keys.isKeyEntry(alias))
			{
				PluginInitializationException e = new PluginInitializationException();
				e.reason = (new StringBuilder()).append("IceSSL: keystore does not contain an entry with alias `").append(alias).append("'").toString();
				throw e;
			}
			for (int i = 0; i < keyManagers.length; i++)
				keyManagers[i] = new X509KeyManagerI((X509KeyManager)keyManagers[i], alias);

		}
		KeyStore ts;
		ts = null;
		InputStream truststoreStream;
		Exception exception2;
		IOException e;
		if (_truststoreStream != null || truststorePath.length() > 0)
		{
			if (_truststoreStream != null && _truststoreStream == _keystoreStream || truststorePath.length() > 0 && truststorePath.equals(keystorePath))
			{
				if (!$assertionsDisabled && keys == null)
					throw new AssertionError();
				ts = keys;
			} else
			{
				truststoreStream = null;
				try
				{
					if (_truststoreStream != null)
					{
						truststoreStream = _truststoreStream;
					} else
					{
						truststoreStream = openResource(truststorePath);
						if (truststoreStream == null)
						{
							PluginInitializationException e = new PluginInitializationException();
							e.reason = (new StringBuilder()).append("IceSSL: truststore not found:\n").append(truststorePath).toString();
							throw e;
						}
					}
					ts = KeyStore.getInstance(truststoreType);
					char passwordChars[] = null;
					if (truststorePassword.length() > 0)
						passwordChars = truststorePassword.toCharArray();
					else
					if (_passwordCallback != null)
						passwordChars = _passwordCallback.getTruststorePassword();
					else
					if (truststoreType.equals("BKS"))
						passwordChars = new char[0];
					ts.load(truststoreStream, passwordChars);
					if (passwordChars != null)
						Arrays.fill(passwordChars, '\0');
					truststorePassword = null;
				}
				catch (IOException ex)
				{
					throw new PluginInitializationException((new StringBuilder()).append("IceSSL: unable to load truststore:\n").append(truststorePath).toString(), ex);
				}
				finally
				{
					if (truststoreStream == null) goto _L0; else goto _L0
				}
				if (truststoreStream != null)
					try
					{
						truststoreStream.close();
					}
					catch (IOException e) { }
			}
		} else
		{
			ts = keys;
		}
		break MISSING_BLOCK_LABEL_1783;
		try
		{
			truststoreStream.close();
		}
		// Misplaced declaration of an exception variable
		catch (IOException e) { }
		throw exception2;
		javax.net.ssl.TrustManager trustManagers[] = null;
		String algorithm = TrustManagerFactory.getDefaultAlgorithm();
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(algorithm);
		tmf.init(ts);
		trustManagers = tmf.getTrustManagers();
		if (!$assertionsDisabled && trustManagers == null)
			throw new AssertionError();
		for (int i = 0; i < trustManagers.length; i++)
			trustManagers[i] = new X509TrustManagerI(this, (X509TrustManager)trustManagers[i]);

		_context = SSLContext.getInstance("TLS");
		_context.init(keyManagers, trustManagers, rand);
		break MISSING_BLOCK_LABEL_1908;
		rand;
		throw new PluginInitializationException("IceSSL: unable to initialize context", rand);
		_seeds.clear();
		_keystoreStream = null;
		_truststoreStream = null;
		_initialized = true;
		return;
	}

	void context(SSLContext context)
	{
		if (_initialized)
		{
			PluginInitializationException ex = new PluginInitializationException();
			ex.reason = "IceSSL: plug-in is already initialized";
			throw ex;
		} else
		{
			_context = context;
			return;
		}
	}

	SSLContext context()
	{
		return _context;
	}

	void setCertificateVerifier(CertificateVerifier verifier)
	{
		_verifier = verifier;
	}

	CertificateVerifier getCertificateVerifier()
	{
		return _verifier;
	}

	void setPasswordCallback(PasswordCallback callback)
	{
		_passwordCallback = callback;
	}

	PasswordCallback getPasswordCallback()
	{
		return _passwordCallback;
	}

	void setKeystoreStream(InputStream stream)
	{
		if (_initialized)
		{
			PluginInitializationException ex = new PluginInitializationException();
			ex.reason = "IceSSL: plugin is already initialized";
			throw ex;
		} else
		{
			_keystoreStream = stream;
			return;
		}
	}

	void setTruststoreStream(InputStream stream)
	{
		if (_initialized)
		{
			PluginInitializationException ex = new PluginInitializationException();
			ex.reason = "IceSSL: plugin is already initialized";
			throw ex;
		} else
		{
			_truststoreStream = stream;
			return;
		}
	}

	void addSeedStream(InputStream stream)
	{
		_seeds.add(stream);
	}

	Communicator communicator()
	{
		return _facade.getCommunicator();
	}

	EndpointHostResolver endpointHostResolver()
	{
		return _facade.getEndpointHostResolver();
	}

	int protocolSupport()
	{
		return _facade.getProtocolSupport();
	}

	String defaultHost()
	{
		return _facade.getDefaultHost();
	}

	int networkTraceLevel()
	{
		return _facade.getNetworkTraceLevel();
	}

	String networkTraceCategory()
	{
		return _facade.getNetworkTraceCategory();
	}

	int securityTraceLevel()
	{
		return _securityTraceLevel;
	}

	String securityTraceCategory()
	{
		return _securityTraceCategory;
	}

	boolean initialized()
	{
		return _initialized;
	}

	SSLEngine createSSLEngine(boolean incoming, InetSocketAddress peerAddr)
	{
		SSLEngine engine;
		if (peerAddr != null)
			engine = _context.createSSLEngine(peerAddr.getHostName(), peerAddr.getPort());
		else
			engine = _context.createSSLEngine();
		engine.setUseClientMode(!incoming);
		String cipherSuites[] = filterCiphers(engine.getSupportedCipherSuites(), engine.getEnabledCipherSuites());
		try
		{
			engine.setEnabledCipherSuites(cipherSuites);
		}
		catch (IllegalArgumentException ex)
		{
			throw new SecurityException("IceSSL: invalid ciphersuite", ex);
		}
		if (_securityTraceLevel >= 1)
		{
			StringBuilder s = new StringBuilder(128);
			s.append("enabling SSL ciphersuites:");
			String arr$[] = cipherSuites;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				String suite = arr$[i$];
				s.append("\n  ");
				s.append(suite);
			}

			_logger.trace(_securityTraceCategory, s.toString());
		}
		if (_protocols != null)
			try
			{
				engine.setEnabledProtocols(_protocols);
			}
			catch (IllegalArgumentException ex)
			{
				throw new SecurityException("IceSSL: invalid protocol", ex);
			}
		if (incoming)
			if (_verifyPeer == 0)
			{
				engine.setWantClientAuth(false);
				engine.setNeedClientAuth(false);
			} else
			if (_verifyPeer == 1)
				engine.setWantClientAuth(true);
			else
				engine.setNeedClientAuth(true);
		try
		{
			engine.beginHandshake();
		}
		catch (SSLException ex)
		{
			throw new SecurityException("IceSSL: handshake error", ex);
		}
		return engine;
	}

	String[] filterCiphers(String supportedCiphers[], String defaultCiphers[])
	{
		LinkedList result = new LinkedList();
		if (_allCiphers)
		{
			String arr$[] = supportedCiphers;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				String cipher = arr$[i$];
				result.add(cipher);
			}

		} else
		if (!_noCiphers)
		{
			String arr$[] = defaultCiphers;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				String cipher = arr$[i$];
				result.add(cipher);
			}

		}
		if (_ciphers != null)
		{
			CipherExpression arr$[] = _ciphers;
			int len$ = arr$.length;
label0:
			for (int i$ = 0; i$ < len$; i$++)
			{
				CipherExpression ce = arr$[i$];
				if (ce.not)
				{
					Iterator e = result.iterator();
					do
					{
						if (!e.hasNext())
							continue label0;
						String cipher = (String)e.next();
						if (ce.cipher != null)
						{
							if (ce.cipher.equals(cipher))
								e.remove();
						} else
						{
							if (!$assertionsDisabled && ce.re == null)
								throw new AssertionError();
							Matcher m = ce.re.matcher(cipher);
							if (m.find())
								e.remove();
						}
					} while (true);
				}
				if (ce.cipher != null)
				{
					result.add(0, ce.cipher);
					continue;
				}
				if (!$assertionsDisabled && ce.re == null)
					throw new AssertionError();
				String arr$[] = supportedCiphers;
				int len$ = arr$.length;
				for (int i$ = 0; i$ < len$; i$++)
				{
					String cipher = arr$[i$];
					Matcher m = ce.re.matcher(cipher);
					if (m.find())
						result.add(0, cipher);
				}

			}

		}
		String arr[] = new String[result.size()];
		result.toArray(arr);
		return arr;
	}

	String[] protocols()
	{
		return _protocols;
	}

	void traceConnection(SocketChannel fd, SSLEngine engine, boolean incoming)
	{
		SSLSession session = engine.getSession();
		String msg = (new StringBuilder()).append("SSL summary for ").append(incoming ? "incoming" : "outgoing").append(" connection\n").append("cipher = ").append(session.getCipherSuite()).append("\n").append("protocol = ").append(session.getProtocol()).append("\n").append(Network.fdToString(fd)).toString();
		_logger.trace(_securityTraceCategory, msg);
	}

	void verifyPeer(NativeConnectionInfo info, SelectableChannel fd, String address)
	{
		if (info.nativeCerts != null && info.nativeCerts.length > 0 && address.length() > 0)
		{
			X509Certificate cert = (X509Certificate)info.nativeCerts[0];
			ArrayList ipAddresses = new ArrayList();
			ArrayList dnsNames = new ArrayList();
			try
			{
				Collection subjectAltNames = cert.getSubjectAlternativeNames();
				if (subjectAltNames != null)
				{
					Iterator i$ = subjectAltNames.iterator();
					do
					{
						if (!i$.hasNext())
							break;
						List l = (List)i$.next();
						if (!$assertionsDisabled && l.isEmpty())
							throw new AssertionError();
						Integer n = (Integer)l.get(0);
						if (n.intValue() == 7)
							ipAddresses.add((String)l.get(1));
						else
						if (n.intValue() == 2)
							dnsNames.add(((String)l.get(1)).toLowerCase());
					} while (true);
				}
			}
			catch (CertificateParsingException ex)
			{
				if (!$assertionsDisabled)
					throw new AssertionError();
			}
			boolean certNameOK = false;
			String dn = "";
			String addrLower = address.toLowerCase();
			X500Principal principal = cert.getSubjectX500Principal();
			dn = principal.getName("CANONICAL");
			String cn = (new StringBuilder()).append("cn=").append(addrLower).toString();
			int pos = dn.indexOf(cn);
			if (pos >= 0)
				certNameOK = pos + cn.length() == dn.length() || dn.charAt(pos + cn.length()) == ',';
			if (!certNameOK)
				certNameOK = ipAddresses.contains(addrLower);
			if (!certNameOK)
				certNameOK = dnsNames.contains(addrLower);
			if (!certNameOK && (_checkCertName || _securityTraceLevel >= 1 && _verifier == null))
			{
				StringBuilder sb = new StringBuilder(128);
				sb.append("IceSSL: ");
				if (!_checkCertName)
					sb.append("ignoring ");
				sb.append("certificate validation failure:\npeer certificate does not have `");
				sb.append(address);
				sb.append("' as its commonName or in its subjectAltName extension");
				if (dn.length() > 0)
				{
					sb.append("\nSubject DN: ");
					sb.append(dn);
				}
				if (!dnsNames.isEmpty())
				{
					sb.append("\nDNS names found in certificate: ");
					for (int j = 0; j < dnsNames.size(); j++)
					{
						if (j > 0)
							sb.append(", ");
						sb.append((String)dnsNames.get(j));
					}

				}
				if (!ipAddresses.isEmpty())
				{
					sb.append("\nIP addresses found in certificate: ");
					for (int j = 0; j < ipAddresses.size(); j++)
					{
						if (j > 0)
							sb.append(", ");
						sb.append((String)ipAddresses.get(j));
					}

				}
				if (_securityTraceLevel >= 1)
					_logger.trace(_securityTraceCategory, sb.toString());
				if (_checkCertName)
				{
					SecurityException ex = new SecurityException();
					ex.reason = sb.toString();
					throw ex;
				}
			}
		}
		if (_verifyDepthMax > 0 && info.nativeCerts != null && info.nativeCerts.length > _verifyDepthMax)
		{
			String msg = (new StringBuilder()).append(info.incoming ? "incoming" : "outgoing").append(" connection rejected:\n").append("length of peer's certificate chain (").append(info.nativeCerts.length).append(") exceeds maximum of ").append(_verifyDepthMax).append("\n").append(Network.fdToString(fd)).toString();
			if (_securityTraceLevel >= 1)
				_logger.trace(_securityTraceCategory, msg);
			SecurityException ex = new SecurityException();
			ex.reason = msg;
			throw ex;
		}
		if (!_trustManager.verify(info))
		{
			String msg = (new StringBuilder()).append(info.incoming ? "incoming" : "outgoing").append(" connection rejected by trust manager\n").append(Network.fdToString(fd)).toString();
			if (_securityTraceLevel >= 1)
				_logger.trace(_securityTraceCategory, msg);
			SecurityException ex = new SecurityException();
			ex.reason = msg;
			throw ex;
		}
		if (_verifier != null && !_verifier.verify(info))
		{
			String msg = (new StringBuilder()).append(info.incoming ? "incoming" : "outgoing").append(" connection rejected by certificate verifier\n").append(Network.fdToString(fd)).toString();
			if (_securityTraceLevel >= 1)
				_logger.trace(_securityTraceCategory, msg);
			SecurityException ex = new SecurityException();
			ex.reason = msg;
			throw ex;
		} else
		{
			return;
		}
	}

	void trustManagerFailure(boolean incoming, CertificateException ex)
		throws CertificateException
	{
		if (_verifyPeer == 0)
		{
			if (_securityTraceLevel >= 1)
			{
				String msg = "ignoring peer verification failure";
				if (_securityTraceLevel > 1)
				{
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					ex.printStackTrace(pw);
					pw.flush();
					msg = (new StringBuilder()).append(msg).append(":\n").append(sw.toString()).toString();
				}
				_logger.trace(_securityTraceCategory, msg);
			}
		} else
		{
			throw ex;
		}
	}

	private void parseCiphers(String ciphers)
	{
		ArrayList cipherList = new ArrayList();
		String expr[] = ciphers.split("[ \t]+");
		for (int i = 0; i < expr.length; i++)
		{
			if (expr[i].equals("ALL"))
			{
				if (i != 0)
				{
					PluginInitializationException ex = new PluginInitializationException();
					ex.reason = (new StringBuilder()).append("IceSSL: `ALL' must be first in cipher list `").append(ciphers).append("'").toString();
					throw ex;
				}
				_allCiphers = true;
				continue;
			}
			if (expr[i].equals("NONE"))
			{
				if (i != 0)
				{
					PluginInitializationException ex = new PluginInitializationException();
					ex.reason = (new StringBuilder()).append("IceSSL: `NONE' must be first in cipher list `").append(ciphers).append("'").toString();
					throw ex;
				}
				_noCiphers = true;
				continue;
			}
			CipherExpression ce = new CipherExpression();
			String exp = expr[i];
			if (exp.charAt(0) == '!')
			{
				ce.not = true;
				if (exp.length() > 1)
				{
					exp = exp.substring(1);
				} else
				{
					PluginInitializationException ex = new PluginInitializationException();
					ex.reason = (new StringBuilder()).append("IceSSL: invalid cipher expression `").append(exp).append("'").toString();
					throw ex;
				}
			}
			if (exp.charAt(0) == '(')
			{
				if (!exp.endsWith(")"))
				{
					PluginInitializationException ex = new PluginInitializationException();
					ex.reason = (new StringBuilder()).append("IceSSL: invalid cipher expression `").append(exp).append("'").toString();
					throw ex;
				}
				try
				{
					ce.re = Pattern.compile(exp.substring(1, exp.length() - 2));
				}
				catch (PatternSyntaxException ex)
				{
					throw new PluginInitializationException((new StringBuilder()).append("IceSSL: invalid cipher expression `").append(exp).append("'").toString(), ex);
				}
			} else
			{
				ce.cipher = exp;
			}
			cipherList.add(ce);
		}

		_ciphers = new CipherExpression[cipherList.size()];
		cipherList.toArray(_ciphers);
	}

	private InputStream openResource(String path)
		throws IOException
	{
		InputStream stream = Util.openResource(getClass().getClassLoader(), path);
		if (stream == null && _defaultDir.length() > 0)
			stream = Util.openResource(getClass().getClassLoader(), (new StringBuilder()).append(_defaultDir).append(File.separator).append(path).toString());
		if (stream != null)
			stream = new BufferedInputStream(stream);
		return stream;
	}

}
