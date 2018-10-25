// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Reference.java

package IceInternal;

import Ice.*;
import IceUtilInternal.StringUtil;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package IceInternal:
//			Instance, ReferenceFactory, BasicStream, EndpointI, 
//			RouterInfo, LocatorInfo

public abstract class Reference
	implements Cloneable
{
	public static interface GetConnectionCallback
	{

		public abstract void setConnection(ConnectionI connectioni, boolean flag);

		public abstract void setException(LocalException localexception);
	}


	public static final int ModeTwoway = 0;
	public static final int ModeOneway = 1;
	public static final int ModeBatchOneway = 2;
	public static final int ModeDatagram = 3;
	public static final int ModeBatchDatagram = 4;
	public static final int ModeLast = 4;
	protected int _hashValue;
	protected boolean _hashInitialized;
	private static Map _emptyContext = new HashMap();
	private final Instance _instance;
	private final Communicator _communicator;
	private int _mode;
	private boolean _secure;
	private Identity _identity;
	private Map _context;
	private String _facet;
	protected boolean _overrideCompress;
	protected boolean _compress;
	static final boolean $assertionsDisabled = !IceInternal/Reference.desiredAssertionStatus();

	public final int getMode()
	{
		return _mode;
	}

	public final boolean getSecure()
	{
		return _secure;
	}

	public final Identity getIdentity()
	{
		return _identity;
	}

	public final String getFacet()
	{
		return _facet;
	}

	public final Instance getInstance()
	{
		return _instance;
	}

	public final Map getContext()
	{
		return _context;
	}

	public final Communicator getCommunicator()
	{
		return _communicator;
	}

	public abstract EndpointI[] getEndpoints();

	public abstract String getAdapterId();

	public abstract RouterInfo getRouterInfo();

	public abstract LocatorInfo getLocatorInfo();

	public abstract boolean getCollocationOptimized();

	public abstract boolean getCacheConnection();

	public abstract boolean getPreferSecure();

	public abstract EndpointSelectionType getEndpointSelection();

	public abstract int getLocatorCacheTimeout();

	public abstract String getConnectionId();

	public final Reference changeContext(Map newContext)
	{
		if (newContext == null)
			newContext = _emptyContext;
		Reference r = _instance.referenceFactory().copy(this);
		if (newContext.isEmpty())
			r._context = _emptyContext;
		else
			r._context = new HashMap(newContext);
		return r;
	}

	public final Reference changeMode(int newMode)
	{
		if (newMode == _mode)
		{
			return this;
		} else
		{
			Reference r = _instance.referenceFactory().copy(this);
			r._mode = newMode;
			return r;
		}
	}

	public Reference changeSecure(boolean newSecure)
	{
		if (newSecure == _secure)
		{
			return this;
		} else
		{
			Reference r = _instance.referenceFactory().copy(this);
			r._secure = newSecure;
			return r;
		}
	}

	public final Reference changeIdentity(Identity newIdentity)
	{
		if (newIdentity.equals(_identity))
		{
			return this;
		} else
		{
			Reference r = _instance.referenceFactory().copy(this);
			r._identity = (Identity)newIdentity.clone();
			return r;
		}
	}

	public final Reference changeFacet(String newFacet)
	{
		if (newFacet.equals(_facet))
		{
			return this;
		} else
		{
			Reference r = _instance.referenceFactory().copy(this);
			r._facet = newFacet;
			return r;
		}
	}

	public Reference changeCompress(boolean newCompress)
	{
		if (_overrideCompress && _compress == newCompress)
		{
			return this;
		} else
		{
			Reference r = _instance.referenceFactory().copy(this);
			r._compress = newCompress;
			r._overrideCompress = true;
			return r;
		}
	}

	public abstract Reference changeAdapterId(String s);

	public abstract Reference changeEndpoints(EndpointI aendpointi[]);

	public abstract Reference changeLocator(LocatorPrx locatorprx);

	public abstract Reference changeRouter(RouterPrx routerprx);

	public abstract Reference changeCollocationOptimized(boolean flag);

	public abstract Reference changeCacheConnection(boolean flag);

	public abstract Reference changePreferSecure(boolean flag);

	public abstract Reference changeEndpointSelection(EndpointSelectionType endpointselectiontype);

	public abstract Reference changeLocatorCacheTimeout(int i);

	public abstract Reference changeTimeout(int i);

	public abstract Reference changeConnectionId(String s);

	public synchronized int hashCode()
	{
		if (_hashInitialized)
		{
			return _hashValue;
		} else
		{
			int h = _mode;
			h = 5 * h + _identity.hashCode();
			h = 5 * h + _context.hashCode();
			h = 5 * h + _facet.hashCode();
			h = 5 * h + (_secure ? 1 : 0);
			_hashValue = h;
			_hashInitialized = true;
			return h;
		}
	}

	public abstract boolean isIndirect();

	public abstract boolean isWellKnown();

	public void streamWrite(BasicStream s)
	{
		if (_facet.length() == 0)
		{
			s.writeStringSeq(null);
		} else
		{
			String facetPath[] = {
				_facet
			};
			s.writeStringSeq(facetPath);
		}
		s.writeByte((byte)_mode);
		s.writeBool(_secure);
	}

	public String toString()
	{
		StringBuilder s = new StringBuilder(128);
		String id = _instance.identityToString(_identity);
		if (StringUtil.findFirstOf(id, " :@") != -1)
		{
			s.append('"');
			s.append(id);
			s.append('"');
		} else
		{
			s.append(id);
		}
		if (_facet.length() > 0)
		{
			s.append(" -f ");
			String fs = StringUtil.escapeString(_facet, "");
			if (StringUtil.findFirstOf(fs, " :@") != -1)
			{
				s.append('"');
				s.append(fs);
				s.append('"');
			} else
			{
				s.append(fs);
			}
		}
		switch (_mode)
		{
		case 0: // '\0'
			s.append(" -t");
			break;

		case 1: // '\001'
			s.append(" -o");
			break;

		case 2: // '\002'
			s.append(" -O");
			break;

		case 3: // '\003'
			s.append(" -d");
			break;

		case 4: // '\004'
			s.append(" -D");
			break;
		}
		if (_secure)
			s.append(" -s");
		return s.toString();
	}

	public abstract Map toProperty(String s);

	public abstract ConnectionI getConnection(BooleanHolder booleanholder);

	public abstract void getConnection(GetConnectionCallback getconnectioncallback);

	public boolean equals(Object obj)
	{
		Reference r = (Reference)obj;
		if (_mode != r._mode)
			return false;
		if (_secure != r._secure)
			return false;
		if (!_identity.equals(r._identity))
			return false;
		if (!_context.equals(r._context))
			return false;
		if (!_facet.equals(r._facet))
			return false;
		if (_overrideCompress != r._overrideCompress)
			return false;
		return !_overrideCompress || _compress == r._compress;
	}

	public Object clone()
	{
		Object o = null;
		try
		{
			o = super.clone();
		}
		catch (CloneNotSupportedException ex) { }
		return o;
	}

	protected Reference(Instance instance, Communicator communicator, Identity identity, String facet, int mode, boolean secure)
	{
		if (!$assertionsDisabled && identity.name == null)
			throw new AssertionError();
		if (!$assertionsDisabled && identity.category == null)
			throw new AssertionError();
		if (!$assertionsDisabled && facet == null)
		{
			throw new AssertionError();
		} else
		{
			_instance = instance;
			_communicator = communicator;
			_mode = mode;
			_secure = secure;
			_identity = identity;
			_context = _emptyContext;
			_facet = facet;
			_hashInitialized = false;
			_overrideCompress = false;
			_compress = false;
			return;
		}
	}

}
