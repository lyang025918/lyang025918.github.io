// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   XMLOutput.java

package IceUtilInternal;

import java.io.PrintWriter;
import java.util.LinkedList;

// Referenced classes of package IceUtilInternal:
//			OutputBase

public class XMLOutput extends OutputBase
{

	private LinkedList _elementStack;
	boolean _se;
	boolean _text;
	private boolean _sgml;
	private boolean _escape;
	static final boolean $assertionsDisabled = !IceUtilInternal/XMLOutput.desiredAssertionStatus();

	public XMLOutput()
	{
		_elementStack = new LinkedList();
		_se = false;
		_text = false;
		_sgml = false;
		_escape = false;
	}

	public XMLOutput(PrintWriter writer)
	{
		super(writer);
		_elementStack = new LinkedList();
		_se = false;
		_text = false;
		_sgml = false;
		_escape = false;
	}

	public XMLOutput(String s)
	{
		super(s);
		_elementStack = new LinkedList();
		_se = false;
		_text = false;
		_sgml = false;
		_escape = false;
	}

	public void setSGML(boolean sgml)
	{
		_sgml = true;
	}

	public void print(String s)
	{
		if (_se)
		{
			_out.print('>');
			_se = false;
		}
		_text = true;
		if (_escape)
		{
			String escaped = escape(s);
			super.print(escaped);
		} else
		{
			super.print(s);
		}
	}

	public XMLOutput write(String s)
	{
		print(s);
		return this;
	}

	public void nl()
	{
		if (_se)
		{
			_se = false;
			_out.print('>');
		}
		super.nl();
	}

	public XMLOutput se(String element)
	{
		nl();
		if (_escape)
		{
			_out.print('<');
			_out.print(escape(element));
		} else
		{
			_out.print('<');
			_out.print(element);
		}
		_se = true;
		_text = false;
		int pos = element.indexOf(' ');
		if (pos == -1)
			pos = element.indexOf('\t');
		if (pos == -1)
			_elementStack.addFirst(element);
		else
			_elementStack.addFirst(element.substring(0, pos));
		_pos++;
		inc();
		_separator = false;
		return this;
	}

	public XMLOutput ee()
	{
		String element = (String)_elementStack.removeFirst();
		dec();
		if (_se)
		{
			if (_sgml)
			{
				_out.print("></");
				_out.print(element);
				_out.print(">");
			} else
			{
				_out.print("/>");
			}
		} else
		{
			if (!_text)
				nl();
			_out.print("</");
			_out.print(element);
			_out.print(">");
		}
		_pos--;
		_se = false;
		_text = false;
		return this;
	}

	public XMLOutput attr(String name, String value)
	{
		if (!$assertionsDisabled && !_se)
		{
			throw new AssertionError();
		} else
		{
			_out.print(" ");
			_out.print(name);
			_out.print("=\"");
			_out.print(escape(value));
			_out.print("\"");
			return this;
		}
	}

	public XMLOutput startEscapes()
	{
		_escape = true;
		return this;
	}

	public XMLOutput endEscapes()
	{
		_escape = false;
		return this;
	}

	public String currentElement()
	{
		if (_elementStack.size() > 0)
			return (String)_elementStack.getFirst();
		else
			return "";
	}

	private String escape(String input)
	{
		String v = input;
		String allReserved = "<>'\"&";
		boolean hasReserved = false;
		char arr[] = input.toCharArray();
		int i = 0;
		do
		{
			if (i >= arr.length)
				break;
			if ("<>'\"&".indexOf(arr[i]) != -1)
			{
				hasReserved = true;
				break;
			}
			i++;
		} while (true);
		if (hasReserved)
		{
			if (v.indexOf('&') != -1)
				v = v.replaceAll("&", "&amp;");
			if (v.indexOf('>') != -1)
				v = v.replaceAll(">", "&gt;");
			if (v.indexOf('<') != -1)
				v = v.replaceAll("<", "&lt;");
			if (v.indexOf('\'') != -1)
				v = v.replaceAll("'", "&apos;");
			if (v.indexOf('"') != -1)
				v = v.replaceAll("\"", "&quot;");
		}
		return v;
	}

}
