// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AttributesRenderer.java

package org.apache.log4j.or.sax;

import org.apache.log4j.or.ObjectRenderer;
import org.xml.sax.Attributes;

public class AttributesRenderer
	implements ObjectRenderer
{

	public AttributesRenderer()
	{
	}

	public String doRender(Object o)
	{
		if (o instanceof Attributes)
		{
			StringBuffer sbuf = new StringBuffer();
			Attributes a = (Attributes)o;
			int len = a.getLength();
			boolean first = true;
			for (int i = 0; i < len; i++)
			{
				if (first)
					first = false;
				else
					sbuf.append(", ");
				sbuf.append(a.getQName(i));
				sbuf.append('=');
				sbuf.append(a.getValue(i));
			}

			return sbuf.toString();
		}
		return o.toString();
		Exception ex;
		ex;
		return ex.toString();
	}
}
