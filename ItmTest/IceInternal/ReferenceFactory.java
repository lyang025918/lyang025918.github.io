// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReferenceFactory.java

package IceInternal;

import Ice.*;
import IceUtilInternal.StringUtil;
import java.lang.ref.WeakReference;
import java.util.*;

// Referenced classes of package IceInternal:
//			FixedReference, Reference, EndpointI, RoutableReference, 
//			Instance, EndpointFactoryManager, BasicStream, PropertyNames, 
//			LocatorManager, RouterManager, DefaultsAndOverrides

public final class ReferenceFactory
{

	private static String _suffixes[] = {
		"EndpointSelection", "ConnectionCached", "PreferSecure", "LocatorCacheTimeout", "Locator", "Router", "CollocationOptimized"
	};
	private final Instance _instance;
	private final Communicator _communicator;
	private RouterPrx _defaultRouter;
	private LocatorPrx _defaultLocator;
	private WeakHashMap _references;
	static final boolean $assertionsDisabled = !IceInternal/ReferenceFactory.desiredAssertionStatus();

	public Reference create(Identity ident, String facet, Reference tmpl, EndpointI endpoints[])
	{
		if (ident.name.length() == 0 && ident.category.length() == 0)
			return null;
		else
			return create(ident, facet, tmpl.getMode(), tmpl.getSecure(), endpoints, null, null);
	}

	public Reference create(Identity ident, String facet, Reference tmpl, String adapterId)
	{
		if (ident.name.length() == 0 && ident.category.length() == 0)
			return null;
		else
			return create(ident, facet, tmpl.getMode(), tmpl.getSecure(), null, adapterId, null);
	}

	public Reference create(Identity ident, ConnectionI fixedConnection)
	{
		if (ident.name.length() == 0 && ident.category.length() == 0)
		{
			return null;
		} else
		{
			FixedReference ref = new FixedReference(_instance, _communicator, ident, "", fixedConnection.endpoint().datagram() ? 3 : 0, fixedConnection.endpoint().secure(), fixedConnection);
			return updateCache(ref);
		}
	}

	public Reference copy(Reference r)
	{
		Identity ident = r.getIdentity();
		if (ident.name.length() == 0 && ident.category.length() == 0)
			return null;
		else
			return (Reference)r.clone();
	}

	public Reference create(String s, String propertyPrefix)
	{
		if (s == null || s.length() == 0)
			return null;
		String delim = " \t\n\r";
		int end = 0;
		int beg = StringUtil.findFirstNotOf(s, " \t\n\r", end);
		if (beg == -1)
		{
			ProxyParseException e = new ProxyParseException();
			e.str = (new StringBuilder()).append("no non-whitespace characters found in `").append(s).append("'").toString();
			throw e;
		}
		String idstr = null;
		end = StringUtil.checkQuote(s, beg);
		if (end == -1)
		{
			ProxyParseException e = new ProxyParseException();
			e.str = (new StringBuilder()).append("mismatched quotes around identity in `").append(s).append("'").toString();
			throw e;
		}
		if (end == 0)
		{
			end = StringUtil.findFirstOf(s, " \t\n\r:@", beg);
			if (end == -1)
				end = s.length();
			idstr = s.substring(beg, end);
		} else
		{
			beg++;
			idstr = s.substring(beg, end);
			end++;
		}
		if (beg == end)
		{
			ProxyParseException e = new ProxyParseException();
			e.str = (new StringBuilder()).append("no identity in `").append(s).append("'").toString();
			throw e;
		}
		Identity ident = _instance.stringToIdentity(idstr);
		if (ident.name.length() == 0)
		{
			if (ident.category.length() > 0)
			{
				IllegalIdentityException e = new IllegalIdentityException();
				e.id = ident;
				throw e;
			}
			if (StringUtil.findFirstNotOf(s, " \t\n\r", end) != -1)
			{
				ProxyParseException e = new ProxyParseException();
				e.str = (new StringBuilder()).append("invalid characters after identity in `").append(s).append("'").toString();
				throw e;
			} else
			{
				return null;
			}
		}
		String facet = "";
		int mode = 0;
		boolean secure = false;
		String adapter = "";
		do
		{
			beg = StringUtil.findFirstNotOf(s, " \t\n\r", end);
			if (beg == -1 || s.charAt(beg) == ':' || s.charAt(beg) == '@')
				break;
			end = StringUtil.findFirstOf(s, " \t\n\r:@", beg);
			if (end == -1)
				end = s.length();
			if (beg == end)
				break;
			String option = s.substring(beg, end);
			if (option.length() != 2 || option.charAt(0) != '-')
			{
				ProxyParseException e = new ProxyParseException();
				e.str = (new StringBuilder()).append("expected a proxy option but found `").append(option).append("' in `").append(s).append("'").toString();
				throw e;
			}
			String argument = null;
			int argumentBeg = StringUtil.findFirstNotOf(s, " \t\n\r", end);
			if (argumentBeg != -1)
			{
				char ch = s.charAt(argumentBeg);
				if (ch != '@' && ch != ':' && ch != '-')
				{
					beg = argumentBeg;
					end = StringUtil.checkQuote(s, beg);
					if (end == -1)
					{
						ProxyParseException e = new ProxyParseException();
						e.str = (new StringBuilder()).append("mismatched quotes around value for ").append(option).append(" option in `").append(s).append("'").toString();
						throw e;
					}
					if (end == 0)
					{
						end = StringUtil.findFirstOf(s, " \t\n\r:@", beg);
						if (end == -1)
							end = s.length();
						argument = s.substring(beg, end);
					} else
					{
						beg++;
						argument = s.substring(beg, end);
						end++;
					}
				}
			}
			switch (option.charAt(1))
			{
			case 102: // 'f'
				if (argument == null)
				{
					ProxyParseException e = new ProxyParseException();
					e.str = (new StringBuilder()).append("no argument provided for -f option in `").append(s).append("'").toString();
					throw e;
				}
				try
				{
					facet = StringUtil.unescapeString(argument, 0, argument.length());
				}
				catch (IllegalArgumentException ex)
				{
					ProxyParseException e = new ProxyParseException();
					e.str = (new StringBuilder()).append("invalid facet in `").append(s).append("': ").append(ex.getMessage()).toString();
					throw e;
				}
				break;

			case 116: // 't'
				if (argument != null)
				{
					ProxyParseException e = new ProxyParseException();
					e.str = (new StringBuilder()).append("unexpected argument `").append(argument).append("' provided for -t option in `").append(s).append("'").toString();
					throw e;
				}
				mode = 0;
				break;

			case 111: // 'o'
				if (argument != null)
				{
					ProxyParseException e = new ProxyParseException();
					e.str = (new StringBuilder()).append("unexpected argument `").append(argument).append("' provided for -o option in `").append(s).append("'").toString();
					throw e;
				}
				mode = 1;
				break;

			case 79: // 'O'
				if (argument != null)
				{
					ProxyParseException e = new ProxyParseException();
					e.str = (new StringBuilder()).append("unexpected argument `").append(argument).append("' provided for -O option in `").append(s).append("'").toString();
					throw e;
				}
				mode = 2;
				break;

			case 100: // 'd'
				if (argument != null)
				{
					ProxyParseException e = new ProxyParseException();
					e.str = (new StringBuilder()).append("unexpected argument `").append(argument).append("' provided for -d option in `").append(s).append("'").toString();
					throw e;
				}
				mode = 3;
				break;

			case 68: // 'D'
				if (argument != null)
				{
					ProxyParseException e = new ProxyParseException();
					e.str = (new StringBuilder()).append("unexpected argument `").append(argument).append("' provided for -D option in `").append(s).append("'").toString();
					throw e;
				}
				mode = 4;
				break;

			case 115: // 's'
				if (argument != null)
				{
					ProxyParseException e = new ProxyParseException();
					e.str = (new StringBuilder()).append("unexpected argument `").append(argument).append("' provided for -s option in `").append(s).append("'").toString();
					throw e;
				}
				secure = true;
				break;

			default:
				ProxyParseException e = new ProxyParseException();
				e.str = (new StringBuilder()).append("unknown option `").append(option).append("' in `").append(s).append("'").toString();
				throw e;
			}
		} while (true);
		if (beg == -1)
			return create(ident, facet, mode, secure, null, null, propertyPrefix);
		ArrayList endpoints = new ArrayList();
		if (s.charAt(beg) == ':')
		{
			ArrayList unknownEndpoints = new ArrayList();
			for (end = beg; end < s.length() && s.charAt(end) == ':';)
			{
				beg = end + 1;
				end = beg;
				do
				{
					end = s.indexOf(':', end);
					if (end == -1)
					{
						end = s.length();
						break;
					}
					boolean quoted = false;
					int quote = beg;
					do
					{
						quote = s.indexOf('"', quote);
						if (quote == -1 || end < quote)
							break;
						quote = s.indexOf('"', ++quote);
						if (quote == -1)
							break;
						if (end < quote)
						{
							quoted = true;
							break;
						}
						quote++;
					} while (true);
					if (!quoted)
						break;
					end++;
				} while (true);
				String es = s.substring(beg, end);
				EndpointI endp = _instance.endpointFactoryManager().create(es, false);
				if (endp != null)
					endpoints.add(endp);
				else
					unknownEndpoints.add(es);
			}

			if (endpoints.size() == 0)
				if (!$assertionsDisabled && unknownEndpoints.isEmpty())
				{
					throw new AssertionError();
				} else
				{
					EndpointParseException e = new EndpointParseException();
					e.str = (new StringBuilder()).append("invalid endpoint `").append((String)unknownEndpoints.get(0)).append("' in `").append(s).append("'").toString();
					throw e;
				}
			if (unknownEndpoints.size() != 0 && _instance.initializationData().properties.getPropertyAsIntWithDefault("Ice.Warn.Endpoints", 1) > 0)
			{
				StringBuffer msg = new StringBuffer("Proxy contains unknown endpoints:");
				for (Iterator i$ = unknownEndpoints.iterator(); i$.hasNext(); msg.append("'"))
				{
					String e = (String)i$.next();
					msg.append(" `");
					msg.append(e);
				}

				_instance.initializationData().logger.warning(msg.toString());
			}
			EndpointI endp[] = new EndpointI[endpoints.size()];
			endpoints.toArray(endp);
			return create(ident, facet, mode, secure, endp, null, propertyPrefix);
		}
		if (s.charAt(beg) == '@')
		{
			beg = StringUtil.findFirstNotOf(s, " \t\n\r", beg + 1);
			if (beg == -1)
			{
				ProxyParseException e = new ProxyParseException();
				e.str = (new StringBuilder()).append("missing adapter id in `").append(s).append("'").toString();
				throw e;
			}
			String adapterstr = null;
			end = StringUtil.checkQuote(s, beg);
			if (end == -1)
			{
				ProxyParseException e = new ProxyParseException();
				e.str = (new StringBuilder()).append("mismatched quotes around adapter id in `").append(s).append("'").toString();
				throw e;
			}
			if (end == 0)
			{
				end = StringUtil.findFirstOf(s, " \t\n\r", beg);
				if (end == -1)
					end = s.length();
				adapterstr = s.substring(beg, end);
			} else
			{
				beg++;
				adapterstr = s.substring(beg, end);
				end++;
			}
			if (end != s.length() && StringUtil.findFirstNotOf(s, " \t\n\r", end) != -1)
			{
				ProxyParseException e = new ProxyParseException();
				e.str = (new StringBuilder()).append("invalid trailing characters after `").append(s.substring(0, end + 1)).append("' in `").append(s).append("'").toString();
				throw e;
			}
			try
			{
				adapter = StringUtil.unescapeString(adapterstr, 0, adapterstr.length());
			}
			catch (IllegalArgumentException ex)
			{
				ProxyParseException e = new ProxyParseException();
				e.str = (new StringBuilder()).append("invalid adapter id in `").append(s).append("': ").append(ex.getMessage()).toString();
				throw e;
			}
			if (adapter.length() == 0)
			{
				ProxyParseException e = new ProxyParseException();
				e.str = (new StringBuilder()).append("empty adapter id in `").append(s).append("'").toString();
				throw e;
			} else
			{
				return create(ident, facet, mode, secure, null, adapter, propertyPrefix);
			}
		} else
		{
			ProxyParseException ex = new ProxyParseException();
			ex.str = (new StringBuilder()).append("malformed proxy `").append(s).append("'").toString();
			throw ex;
		}
	}

	public Reference create(Identity ident, BasicStream s)
	{
		if (ident.name.length() == 0 && ident.category.length() == 0)
			return null;
		String facetPath[] = s.readStringSeq();
		String facet;
		if (facetPath.length > 0)
		{
			if (facetPath.length > 1)
				throw new ProxyUnmarshalException();
			facet = facetPath[0];
		} else
		{
			facet = "";
		}
		int mode = s.readByte();
		if (mode < 0 || mode > 4)
			throw new ProxyUnmarshalException();
		boolean secure = s.readBool();
		EndpointI endpoints[] = null;
		String adapterId = null;
		int sz = s.readSize();
		if (sz > 0)
		{
			endpoints = new EndpointI[sz];
			for (int i = 0; i < sz; i++)
				endpoints[i] = _instance.endpointFactoryManager().read(s);

		} else
		{
			adapterId = s.readString();
		}
		return create(ident, facet, mode, secure, endpoints, adapterId, null);
	}

	public ReferenceFactory setDefaultRouter(RouterPrx defaultRouter)
	{
		if (_defaultRouter != null ? _defaultRouter.equals(defaultRouter) : defaultRouter == null)
		{
			return this;
		} else
		{
			ReferenceFactory factory = new ReferenceFactory(_instance, _communicator);
			factory._defaultLocator = _defaultLocator;
			factory._defaultRouter = defaultRouter;
			return factory;
		}
	}

	public RouterPrx getDefaultRouter()
	{
		return _defaultRouter;
	}

	public ReferenceFactory setDefaultLocator(LocatorPrx defaultLocator)
	{
		if (_defaultLocator != null ? _defaultLocator.equals(defaultLocator) : defaultLocator == null)
		{
			return this;
		} else
		{
			ReferenceFactory factory = new ReferenceFactory(_instance, _communicator);
			factory._defaultRouter = _defaultRouter;
			factory._defaultLocator = defaultLocator;
			return factory;
		}
	}

	public LocatorPrx getDefaultLocator()
	{
		return _defaultLocator;
	}

	ReferenceFactory(Instance instance, Communicator communicator)
	{
		_references = new WeakHashMap();
		_instance = instance;
		_communicator = communicator;
	}

	synchronized void destroy()
	{
		_references.clear();
	}

	private synchronized Reference updateCache(Reference ref)
	{
		WeakReference w = (WeakReference)_references.get(ref);
		if (w != null)
		{
			Reference r = (Reference)w.get();
			if (r != null)
				ref = r;
			else
				_references.put(ref, new WeakReference(ref));
		} else
		{
			_references.put(ref, new WeakReference(ref));
		}
		return ref;
	}

	private void checkForUnknownProperties(String prefix)
	{
		for (int i = 0; PropertyNames.clPropNames[i] != null; i++)
			if (prefix.startsWith((new StringBuilder()).append(PropertyNames.clPropNames[i]).append(".").toString()))
				return;

		ArrayList unknownProps = new ArrayList();
		Map props = _instance.initializationData().properties.getPropertiesForPrefix((new StringBuilder()).append(prefix).append(".").toString());
		Iterator i$ = props.entrySet().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			java.util.Map.Entry p = (java.util.Map.Entry)i$.next();
			String prop = (String)p.getKey();
			boolean valid = false;
			String arr$[] = _suffixes;
			int len$ = arr$.length;
			int i$ = 0;
			do
			{
				if (i$ >= len$)
					break;
				String suffix = arr$[i$];
				if (prop.equals((new StringBuilder()).append(prefix).append(".").append(suffix).toString()))
				{
					valid = true;
					break;
				}
				i$++;
			} while (true);
			if (!valid)
				unknownProps.add(prop);
		} while (true);
		if (unknownProps.size() != 0)
		{
			StringBuffer message = new StringBuffer("found unknown properties for proxy '");
			message.append(prefix);
			message.append("':");
			String s;
			for (Iterator i$ = unknownProps.iterator(); i$.hasNext(); message.append(s))
			{
				s = (String)i$.next();
				message.append("\n    ");
			}

			_instance.initializationData().logger.warning(message.toString());
		}
	}

	private Reference create(Identity ident, String facet, int mode, boolean secure, EndpointI endpoints[], String adapterId, String propertyPrefix)
	{
		DefaultsAndOverrides defaultsAndOverrides = _instance.defaultsAndOverrides();
		LocatorInfo locatorInfo = _instance.locatorManager().get(_defaultLocator);
		RouterInfo routerInfo = _instance.routerManager().get(_defaultRouter);
		boolean collocationOptimized = defaultsAndOverrides.defaultCollocationOptimization;
		boolean cacheConnection = true;
		boolean preferSecure = defaultsAndOverrides.defaultPreferSecure;
		EndpointSelectionType endpointSelection = defaultsAndOverrides.defaultEndpointSelection;
		int locatorCacheTimeout = defaultsAndOverrides.defaultLocatorCacheTimeout;
		if (propertyPrefix != null && propertyPrefix.length() > 0)
		{
			Properties properties = _instance.initializationData().properties;
			if (properties.getPropertyAsIntWithDefault("Ice.Warn.UnknownProperties", 1) > 0)
				checkForUnknownProperties(propertyPrefix);
			String property = (new StringBuilder()).append(propertyPrefix).append(".Locator").toString();
			LocatorPrx locator = LocatorPrxHelper.uncheckedCast(_communicator.propertyToProxy(property));
			if (locator != null)
				locatorInfo = _instance.locatorManager().get(locator);
			property = (new StringBuilder()).append(propertyPrefix).append(".Router").toString();
			RouterPrx router = RouterPrxHelper.uncheckedCast(_communicator.propertyToProxy(property));
			if (router != null)
				if (propertyPrefix.endsWith(".Router"))
				{
					String s = (new StringBuilder()).append("`").append(property).append("=").append(properties.getProperty(property)).append("': cannot set a router on a router; setting ignored").toString();
					_instance.initializationData().logger.warning(s);
				} else
				{
					routerInfo = _instance.routerManager().get(router);
				}
			property = (new StringBuilder()).append(propertyPrefix).append(".CollocationOptimized").toString();
			collocationOptimized = properties.getPropertyAsIntWithDefault(property, collocationOptimized ? 1 : 0) > 0;
			property = (new StringBuilder()).append(propertyPrefix).append(".ConnectionCached").toString();
			cacheConnection = properties.getPropertyAsIntWithDefault(property, cacheConnection ? 1 : 0) > 0;
			property = (new StringBuilder()).append(propertyPrefix).append(".PreferSecure").toString();
			preferSecure = properties.getPropertyAsIntWithDefault(property, preferSecure ? 1 : 0) > 0;
			property = (new StringBuilder()).append(propertyPrefix).append(".EndpointSelection").toString();
			if (properties.getProperty(property).length() > 0)
			{
				String type = properties.getProperty(property);
				if (type.equals("Random"))
					endpointSelection = EndpointSelectionType.Random;
				else
				if (type.equals("Ordered"))
					endpointSelection = EndpointSelectionType.Ordered;
				else
					throw new EndpointSelectionTypeParseException((new StringBuilder()).append("illegal value `").append(type).append("'; expected `Random' or `Ordered'").toString());
			}
			property = (new StringBuilder()).append(propertyPrefix).append(".LocatorCacheTimeout").toString();
			locatorCacheTimeout = properties.getPropertyAsIntWithDefault(property, locatorCacheTimeout);
		}
		return updateCache(new RoutableReference(_instance, _communicator, ident, facet, mode, secure, endpoints, adapterId, locatorInfo, routerInfo, collocationOptimized, cacheConnection, preferSecure, endpointSelection, locatorCacheTimeout));
	}

}
