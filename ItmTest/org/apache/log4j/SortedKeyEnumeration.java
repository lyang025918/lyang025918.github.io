// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertyConfigurator.java

package org.apache.log4j;

import java.util.*;

class SortedKeyEnumeration
	implements Enumeration
{

	private Enumeration e;

	public SortedKeyEnumeration(Hashtable ht)
	{
		Enumeration f = ht.keys();
		Vector keys = new Vector(ht.size());
		for (int last = 0; f.hasMoreElements(); last++)
		{
			String key = (String)f.nextElement();
			int i = 0;
			do
			{
				if (i >= last)
					break;
				String s = (String)keys.get(i);
				if (key.compareTo(s) <= 0)
					break;
				i++;
			} while (true);
			keys.add(i, key);
		}

		e = keys.elements();
	}

	public boolean hasMoreElements()
	{
		return e.hasMoreElements();
	}

	public Object nextElement()
	{
		return e.nextElement();
	}
}
