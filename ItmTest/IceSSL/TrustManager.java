// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TrustManager.java

package IceSSL;

import Ice.*;
import java.security.cert.X509Certificate;
import java.util.*;
import javax.security.auth.x500.X500Principal;

// Referenced classes of package IceSSL:
//			RFC2253, NativeConnectionInfo

class TrustManager
{

	private Communicator _communicator;
	private int _traceLevel;
	private List _rejectAll;
	private List _rejectClient;
	private List _rejectAllServer;
	private Map _rejectServer;
	private List _acceptAll;
	private List _acceptClient;
	private List _acceptAllServer;
	private Map _acceptServer;
	static final boolean $assertionsDisabled = !IceSSL/TrustManager.desiredAssertionStatus();

	TrustManager(Communicator communicator)
	{
		_rejectAll = new LinkedList();
		_rejectClient = new LinkedList();
		_rejectAllServer = new LinkedList();
		_rejectServer = new HashMap();
		_acceptAll = new LinkedList();
		_acceptClient = new LinkedList();
		_acceptAllServer = new LinkedList();
		_acceptServer = new HashMap();
		if (!$assertionsDisabled && communicator == null)
			throw new AssertionError();
		_communicator = communicator;
		Properties properties = communicator.getProperties();
		_traceLevel = properties.getPropertyAsInt("IceSSL.Trace.Security");
		String key = null;
		try
		{
			key = "IceSSL.TrustOnly";
			parse(properties.getProperty(key), _rejectAll, _acceptAll);
			key = "IceSSL.TrustOnly.Client";
			parse(properties.getProperty(key), _rejectClient, _acceptClient);
			key = "IceSSL.TrustOnly.Server";
			parse(properties.getProperty(key), _rejectAllServer, _acceptAllServer);
			Map dict = properties.getPropertiesForPrefix("IceSSL.TrustOnly.Server.");
			Iterator i$ = dict.entrySet().iterator();
			do
			{
				if (!i$.hasNext())
					break;
				java.util.Map.Entry p = (java.util.Map.Entry)i$.next();
				key = (String)p.getKey();
				String name = key.substring("IceSSL.TrustOnly.Server.".length());
				List reject = new LinkedList();
				List accept = new LinkedList();
				parse((String)p.getValue(), reject, accept);
				if (!reject.isEmpty())
					_rejectServer.put(name, reject);
				if (!accept.isEmpty())
					_acceptServer.put(name, accept);
			} while (true);
		}
		catch (RFC2253.ParseException e)
		{
			PluginInitializationException ex = new PluginInitializationException();
			ex.reason = (new StringBuilder()).append("IceSSL: invalid property ").append(key).append(":\n").append(e.reason).toString();
			throw ex;
		}
	}

	boolean verify(NativeConnectionInfo info)
	{
		List reject;
		List accept;
		String subjectName;
		reject = new LinkedList();
		accept = new LinkedList();
		if (!_rejectAll.isEmpty())
			reject.add(_rejectAll);
		if (info.incoming)
		{
			if (!_rejectAllServer.isEmpty())
				reject.add(_rejectAllServer);
			if (info.adapterName.length() > 0)
			{
				List p = (List)_rejectServer.get(info.adapterName);
				if (p != null)
					reject.add(p);
			}
		} else
		if (!_rejectClient.isEmpty())
			reject.add(_rejectClient);
		if (!_acceptAll.isEmpty())
			accept.add(_acceptAll);
		if (info.incoming)
		{
			if (!_acceptAllServer.isEmpty())
				accept.add(_acceptAllServer);
			if (info.adapterName.length() > 0)
			{
				List p = (List)_acceptServer.get(info.adapterName);
				if (p != null)
					accept.add(p);
			}
		} else
		if (!_acceptClient.isEmpty())
			accept.add(_acceptClient);
		if (reject.isEmpty() && accept.isEmpty())
			return true;
		if (info.nativeCerts == null || info.nativeCerts.length <= 0)
			break MISSING_BLOCK_LABEL_809;
		X500Principal subjectDN = ((X509Certificate)info.nativeCerts[0]).getSubjectX500Principal();
		subjectName = subjectDN.getName("RFC2253");
		if (!$assertionsDisabled && subjectName == null)
			throw new AssertionError();
		List dn;
		Iterator i$;
		if (_traceLevel > 0)
			if (info.incoming)
				_communicator.getLogger().trace("Security", (new StringBuilder()).append("trust manager evaluating client:\nsubject = ").append(subjectName).append("\n").append("adapter = ").append(info.adapterName).append("\n").append("local addr = ").append(info.localAddress).append(":").append(info.localPort).append("\n").append("remote addr = ").append(info.remoteAddress).append(":").append(info.remotePort).toString());
			else
				_communicator.getLogger().trace("Security", (new StringBuilder()).append("trust manager evaluating server:\nsubject = ").append(subjectName).append("\n").append("local addr = ").append(info.localAddress).append(":").append(info.localPort).append("\n").append("remote addr = ").append(info.remoteAddress).append(":").append(info.remotePort).toString());
		dn = RFC2253.parseStrict(subjectName);
		i$ = reject.iterator();
_L1:
		List matchSet;
		if (!i$.hasNext())
			break MISSING_BLOCK_LABEL_657;
		matchSet = (List)i$.next();
		if (_traceLevel > 1)
		{
			StringBuilder s = new StringBuilder("trust manager rejecting PDNs:\n");
			stringify(matchSet, s);
			_communicator.getLogger().trace("Security", s.toString());
		}
		if (match(matchSet, dn))
			return false;
		  goto _L1
		i$ = accept.iterator();
_L2:
		if (!i$.hasNext())
			break MISSING_BLOCK_LABEL_802;
		matchSet = (List)i$.next();
		if (_traceLevel > 1)
		{
			StringBuilder s = new StringBuilder("trust manager accepting PDNs:\n");
			stringify(matchSet, s);
			_communicator.getLogger().trace("Security", s.toString());
		}
		if (match(matchSet, dn))
			return true;
		  goto _L2
		RFC2253.ParseException e;
		e;
		_communicator.getLogger().warning((new StringBuilder()).append("IceSSL: unable to parse certificate DN `").append(subjectName).append("'\nreason: ").append(e.reason).toString());
		return accept.isEmpty();
		return false;
	}

	private boolean match(List matchSet, List subject)
	{
		for (Iterator i$ = matchSet.iterator(); i$.hasNext();)
		{
			List r = (List)i$.next();
			if (matchRDNs(r, subject))
				return true;
		}

		return false;
	}

	private boolean matchRDNs(List match, List subject)
	{
		Iterator i$ = match.iterator();
		boolean found;
label0:
		do
			if (i$.hasNext())
			{
				RFC2253.RDNPair matchRDN = (RFC2253.RDNPair)i$.next();
				found = false;
				Iterator i$ = subject.iterator();
				RFC2253.RDNPair subjectRDN;
				do
				{
					do
					{
						if (!i$.hasNext())
							continue label0;
						subjectRDN = (RFC2253.RDNPair)i$.next();
					} while (!matchRDN.key.equals(subjectRDN.key));
					found = true;
				} while (matchRDN.value.equals(subjectRDN.value));
				return false;
			} else
			{
				return true;
			}
		while (found);
		return false;
	}

	void parse(String value, List reject, List accept)
		throws RFC2253.ParseException
	{
		List l = RFC2253.parse(value);
		for (Iterator i$ = l.iterator(); i$.hasNext();)
		{
			RFC2253.RDNEntry e = (RFC2253.RDNEntry)i$.next();
			StringBuilder v = new StringBuilder();
			boolean first = true;
			RFC2253.RDNPair pair;
			for (Iterator i$ = e.rdn.iterator(); i$.hasNext(); v.append(pair.value))
			{
				pair = (RFC2253.RDNPair)i$.next();
				if (!first)
					v.append(",");
				first = false;
				v.append(pair.key);
				v.append("=");
			}

			X500Principal princ = new X500Principal(v.toString());
			String subjectName = princ.getName("RFC2253");
			if (e.negate)
				reject.add(RFC2253.parseStrict(subjectName));
			else
				accept.add(RFC2253.parseStrict(subjectName));
		}

	}

	private static void stringify(List matchSet, StringBuilder s)
	{
		boolean addSemi = false;
		for (Iterator i$ = matchSet.iterator(); i$.hasNext();)
		{
			List rdnSet = (List)i$.next();
			if (addSemi)
				s.append(';');
			addSemi = true;
			boolean addComma = false;
			Iterator i$ = rdnSet.iterator();
			while (i$.hasNext()) 
			{
				RFC2253.RDNPair rdn = (RFC2253.RDNPair)i$.next();
				if (addComma)
					s.append(',');
				addComma = true;
				s.append(rdn.key);
				s.append('=');
				s.append(rdn.value);
			}
		}

	}

}
