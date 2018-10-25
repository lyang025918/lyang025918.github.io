// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   VectorWriter.java

package org.apache.log4j.spi;

import java.io.PrintWriter;
import java.util.Vector;

// Referenced classes of package org.apache.log4j.spi:
//			NullWriter

/**
 * @deprecated Class VectorWriter is deprecated
 */

class VectorWriter extends PrintWriter
{

	private Vector v;

	/**
	 * @deprecated Method VectorWriter is deprecated
	 */

	VectorWriter()
	{
		super(new NullWriter());
		v = new Vector();
	}

	public void print(Object o)
	{
		v.addElement(String.valueOf(o));
	}

	public void print(char chars[])
	{
		v.addElement(new String(chars));
	}

	public void print(String s)
	{
		v.addElement(s);
	}

	public void println(Object o)
	{
		v.addElement(String.valueOf(o));
	}

	public void println(char chars[])
	{
		v.addElement(new String(chars));
	}

	public void println(String s)
	{
		v.addElement(s);
	}

	public void write(char chars[])
	{
		v.addElement(new String(chars));
	}

	public void write(char chars[], int off, int len)
	{
		v.addElement(new String(chars, off, len));
	}

	public void write(String s, int off, int len)
	{
		v.addElement(s.substring(off, off + len));
	}

	public void write(String s)
	{
		v.addElement(s);
	}

	public String[] toStringArray()
	{
		int len = v.size();
		String sa[] = new String[len];
		for (int i = 0; i < len; i++)
			sa[i] = (String)v.elementAt(i);

		return sa;
	}
}
