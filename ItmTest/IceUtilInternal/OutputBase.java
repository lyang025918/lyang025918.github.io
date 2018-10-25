// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OutputBase.java

package IceUtilInternal;

import java.io.*;
import java.util.LinkedList;

public class OutputBase
{

	protected PrintWriter _out;
	protected int _pos;
	protected int _indent;
	protected int _indentSize;
	protected LinkedList _indentSave;
	protected boolean _useTab;
	protected boolean _separator;
	static final boolean $assertionsDisabled = !IceUtilInternal/OutputBase.desiredAssertionStatus();

	public OutputBase()
	{
		_indentSave = new LinkedList();
		_out = null;
		_pos = 0;
		_indent = 0;
		_indentSize = 4;
		_useTab = true;
		_separator = true;
	}

	public OutputBase(PrintWriter out)
	{
		_indentSave = new LinkedList();
		_out = out;
		_pos = 0;
		_indent = 0;
		_indentSize = 4;
		_useTab = true;
		_separator = true;
	}

	public OutputBase(String s)
	{
		_indentSave = new LinkedList();
		_out = null;
		_pos = 0;
		_indent = 0;
		_indentSize = 4;
		_useTab = true;
		_separator = true;
		open(s);
	}

	public void setIndent(int indentSize)
	{
		_indentSize = indentSize;
	}

	public void setUseTab(boolean useTab)
	{
		_useTab = useTab;
	}

	public void open(String s)
	{
		try
		{
			FileWriter fw = new FileWriter(s);
			BufferedWriter bw = new BufferedWriter(fw);
			_out = new PrintWriter(bw);
		}
		catch (IOException ex) { }
	}

	public void print(String s)
	{
		char arr[] = s.toCharArray();
		for (int i = 0; i < arr.length; i++)
			if (arr[i] == '\n')
				_pos = 0;
			else
				_pos++;

		_out.print(s);
	}

	public void inc()
	{
		_indent += _indentSize;
	}

	public void dec()
	{
		if (!$assertionsDisabled && _indent < _indentSize)
		{
			throw new AssertionError();
		} else
		{
			_indent -= _indentSize;
			return;
		}
	}

	public void useCurrentPosAsIndent()
	{
		_indentSave.addFirst(Integer.valueOf(_indent));
		_indent = _pos;
	}

	public void zeroIndent()
	{
		_indentSave.addFirst(Integer.valueOf(_indent));
		_indent = 0;
	}

	public void restoreIndent()
	{
		if (!$assertionsDisabled && _indentSave.isEmpty())
		{
			throw new AssertionError();
		} else
		{
			_indent = ((Integer)_indentSave.removeFirst()).intValue();
			return;
		}
	}

	public void nl()
	{
		_out.println();
		_pos = 0;
		_separator = true;
		int indent = _indent;
		if (_useTab)
			while (indent >= 8) 
			{
				indent -= 8;
				_out.print('\t');
				_pos += 8;
			}
		else
			while (indent >= _indentSize) 
			{
				indent -= _indentSize;
				_out.print("    ");
				_pos += _indentSize;
			}
		while (indent > 0) 
		{
			indent--;
			_out.print(' ');
			_pos++;
		}
		_out.flush();
	}

	public void sp()
	{
		if (_separator)
			_out.println();
	}

	public boolean valid()
	{
		return _out != null;
	}

}
