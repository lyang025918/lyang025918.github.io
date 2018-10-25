// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServantManager.java

package IceInternal;

import Ice.*;
import IceUtilInternal.StringUtil;
import java.util.*;

// Referenced classes of package IceInternal:
//			Instance, Ex

public final class ServantManager
{

	private Instance _instance;
	private final String _adapterName;
	private Map _servantMapMap;
	private Map _defaultServantMap;
	private Map _locatorMap;
	static final boolean $assertionsDisabled = !IceInternal/ServantManager.desiredAssertionStatus();

	public synchronized void addServant(Ice.Object servant, Identity ident, String facet)
	{
		Map m;
		AlreadyRegisteredException ex;
		if (!$assertionsDisabled && _instance == null)
			throw new AssertionError();
		if (facet == null)
			facet = "";
		m = (Map)_servantMapMap.get(ident);
		if (m == null)
		{
			m = new HashMap();
			_servantMapMap.put(ident, m);
			break MISSING_BLOCK_LABEL_159;
		}
		if (!m.containsKey(facet))
			break MISSING_BLOCK_LABEL_159;
		ex = new AlreadyRegisteredException();
		ex.id = _instance.identityToString(ident);
		ex.kindOfObject = "servant";
		if (facet.length() <= 0) goto _L2; else goto _L1
_L1:
		new StringBuilder();
		ex;
		JVM INSTR dup_x1 ;
		id;
		append();
		" -f ";
		append();
		StringUtil.escapeString(facet, "");
		append();
		toString();
		id;
_L2:
		throw ex;
		m.put(facet, servant);
		return;
	}

	public synchronized void addDefaultServant(Ice.Object servant, String category)
	{
		if (!$assertionsDisabled && _instance == null)
			throw new AssertionError();
		Ice.Object obj = (Ice.Object)_defaultServantMap.get(category);
		if (obj != null)
		{
			AlreadyRegisteredException ex = new AlreadyRegisteredException();
			ex.kindOfObject = "default servant";
			ex.id = category;
			throw ex;
		} else
		{
			_defaultServantMap.put(category, servant);
			return;
		}
	}

	public synchronized Ice.Object removeServant(Identity ident, String facet)
	{
		Map m;
		Ice.Object obj;
		NotRegisteredException ex;
		if (!$assertionsDisabled && _instance == null)
			throw new AssertionError();
		if (facet == null)
			facet = "";
		m = (Map)_servantMapMap.get(ident);
		obj = null;
		if (m != null && (obj = (Ice.Object)m.remove(facet)) != null)
			break MISSING_BLOCK_LABEL_140;
		ex = new NotRegisteredException();
		ex.id = _instance.identityToString(ident);
		ex.kindOfObject = "servant";
		if (facet.length() <= 0) goto _L2; else goto _L1
_L1:
		new StringBuilder();
		ex;
		JVM INSTR dup_x1 ;
		id;
		append();
		" -f ";
		append();
		StringUtil.escapeString(facet, "");
		append();
		toString();
		id;
_L2:
		throw ex;
		if (m.isEmpty())
			_servantMapMap.remove(ident);
		return obj;
	}

	public synchronized Ice.Object removeDefaultServant(String category)
	{
		if (!$assertionsDisabled && _instance == null)
			throw new AssertionError();
		Ice.Object obj = (Ice.Object)_defaultServantMap.get(category);
		if (obj == null)
		{
			NotRegisteredException ex = new NotRegisteredException();
			ex.kindOfObject = "default servant";
			ex.id = category;
			throw ex;
		} else
		{
			_defaultServantMap.remove(category);
			return obj;
		}
	}

	public synchronized Map removeAllFacets(Identity ident)
	{
		if (!$assertionsDisabled && _instance == null)
			throw new AssertionError();
		Map m = (Map)_servantMapMap.get(ident);
		if (m == null)
		{
			NotRegisteredException ex = new NotRegisteredException();
			ex.id = _instance.identityToString(ident);
			ex.kindOfObject = "servant";
			throw ex;
		} else
		{
			_servantMapMap.remove(ident);
			return m;
		}
	}

	public synchronized Ice.Object findServant(Identity ident, String facet)
	{
		if (facet == null)
			facet = "";
		Map m = (Map)_servantMapMap.get(ident);
		Ice.Object obj = null;
		if (m == null)
		{
			obj = (Ice.Object)_defaultServantMap.get(ident.category);
			if (obj == null)
				obj = (Ice.Object)_defaultServantMap.get("");
		} else
		{
			obj = (Ice.Object)m.get(facet);
		}
		return obj;
	}

	public synchronized Ice.Object findDefaultServant(String category)
	{
		if (!$assertionsDisabled && _instance == null)
			throw new AssertionError();
		else
			return (Ice.Object)_defaultServantMap.get(category);
	}

	public synchronized Map findAllFacets(Identity ident)
	{
		if (!$assertionsDisabled && _instance == null)
			throw new AssertionError();
		Map m = (Map)_servantMapMap.get(ident);
		if (m != null)
			return new HashMap(m);
		else
			return new HashMap();
	}

	public synchronized boolean hasServant(Identity ident)
	{
		Map m = (Map)_servantMapMap.get(ident);
		if (m == null)
			return false;
		if (!$assertionsDisabled && m.isEmpty())
			throw new AssertionError();
		else
			return true;
	}

	public synchronized void addServantLocator(ServantLocator locator, String category)
	{
		if (!$assertionsDisabled && _instance == null)
			throw new AssertionError();
		ServantLocator l = (ServantLocator)_locatorMap.get(category);
		if (l != null)
		{
			AlreadyRegisteredException ex = new AlreadyRegisteredException();
			ex.id = StringUtil.escapeString(category, "");
			ex.kindOfObject = "servant locator";
			throw ex;
		} else
		{
			_locatorMap.put(category, locator);
			return;
		}
	}

	public synchronized ServantLocator removeServantLocator(String category)
	{
		ServantLocator l = null;
		if (!$assertionsDisabled && _instance == null)
			throw new AssertionError();
		l = (ServantLocator)_locatorMap.remove(category);
		if (l == null)
		{
			NotRegisteredException ex = new NotRegisteredException();
			ex.id = StringUtil.escapeString(category, "");
			ex.kindOfObject = "servant locator";
			throw ex;
		} else
		{
			return l;
		}
	}

	public synchronized ServantLocator findServantLocator(String category)
	{
		return (ServantLocator)_locatorMap.get(category);
	}

	public ServantManager(Instance instance, String adapterName)
	{
		_servantMapMap = new HashMap();
		_defaultServantMap = new HashMap();
		_locatorMap = new HashMap();
		_instance = instance;
		_adapterName = adapterName;
	}

	public void destroy()
	{
		Map locatorMap = new HashMap();
		Logger logger = null;
		synchronized (this)
		{
			if (!$assertionsDisabled && _instance == null)
				throw new AssertionError();
			logger = _instance.initializationData().logger;
			_servantMapMap.clear();
			locatorMap.putAll(_locatorMap);
			_locatorMap.clear();
			_instance = null;
		}
		for (Iterator i$ = locatorMap.entrySet().iterator(); i$.hasNext();)
		{
			java.util.Map.Entry p = (java.util.Map.Entry)i$.next();
			ServantLocator locator = (ServantLocator)p.getValue();
			try
			{
				locator.deactivate((String)p.getKey());
			}
			catch (Exception ex)
			{
				String s = (new StringBuilder()).append("exception during locator deactivation:\nobject adapter: `").append(_adapterName).append("'\n").append("locator category: `").append((String)p.getKey()).append("'\n").append(Ex.toString(ex)).toString();
				logger.error(s);
			}
		}

	}

}
