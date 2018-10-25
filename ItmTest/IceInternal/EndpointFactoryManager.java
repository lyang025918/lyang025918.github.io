// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EndpointFactoryManager.java

package IceInternal;

import Ice.EndpointParseException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Referenced classes of package IceInternal:
//			EndpointFactory, OpaqueEndpointI, BasicStream, Instance, 
//			DefaultsAndOverrides, EndpointI, Buffer

public final class EndpointFactoryManager
{

	private Instance _instance;
	private List _factories;
	static final boolean $assertionsDisabled = !IceInternal/EndpointFactoryManager.desiredAssertionStatus();

	EndpointFactoryManager(Instance instance)
	{
		_factories = new ArrayList();
		_instance = instance;
	}

	public synchronized void add(EndpointFactory factory)
	{
		for (int i = 0; i < _factories.size(); i++)
		{
			EndpointFactory f = (EndpointFactory)_factories.get(i);
			if (f.type() == factory.type() && !$assertionsDisabled)
				throw new AssertionError();
		}

		_factories.add(factory);
	}

	public synchronized EndpointFactory get(short type)
	{
		for (int i = 0; i < _factories.size(); i++)
		{
			EndpointFactory f = (EndpointFactory)_factories.get(i);
			if (f.type() == type)
				return f;
		}

		return null;
	}

	public synchronized EndpointI create(String str, boolean oaEndpoint)
	{
		String s = str.trim();
		if (s.length() == 0)
		{
			EndpointParseException e = new EndpointParseException();
			e.str = "value has no non-whitespace characters";
			throw e;
		}
		Pattern p = Pattern.compile("([ \t\n\r]+)|$");
		Matcher m = p.matcher(s);
		boolean b = m.find();
		if (!$assertionsDisabled && !b)
			throw new AssertionError();
		String protocol = s.substring(0, m.start());
		if (protocol.equals("default"))
			protocol = _instance.defaultsAndOverrides().defaultProtocol;
		for (int i = 0; i < _factories.size(); i++)
		{
			EndpointFactory f = (EndpointFactory)_factories.get(i);
			if (f.protocol().equals(protocol))
				return f.create(s.substring(m.end()), oaEndpoint);
		}

		if (protocol.equals("opaque"))
		{
			EndpointI ue = new OpaqueEndpointI(s.substring(m.end()));
			for (int i = 0; i < _factories.size(); i++)
			{
				EndpointFactory f = (EndpointFactory)_factories.get(i);
				if (f.type() == ue.type())
				{
					BasicStream bs = new BasicStream(_instance, true, false);
					ue.streamWrite(bs);
					Buffer buf = bs.getBuffer();
					buf.b.position(0);
					bs.readShort();
					return f.read(bs);
				}
			}

			return ue;
		} else
		{
			return null;
		}
	}

	public synchronized EndpointI read(BasicStream s)
	{
		short type = s.readShort();
		for (int i = 0; i < _factories.size(); i++)
		{
			EndpointFactory f = (EndpointFactory)_factories.get(i);
			if (f.type() == type)
				return f.read(s);
		}

		return new OpaqueEndpointI(type, s);
	}

	void destroy()
	{
		for (int i = 0; i < _factories.size(); i++)
		{
			EndpointFactory f = (EndpointFactory)_factories.get(i);
			f.destroy();
		}

		_factories.clear();
	}

}
