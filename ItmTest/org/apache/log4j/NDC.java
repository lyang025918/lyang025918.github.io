// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NDC.java

package org.apache.log4j;

import java.util.*;
import org.apache.log4j.helpers.LogLog;

public class NDC
{
	private static class DiagnosticContext
	{

		String fullMessage;
		String message;

		DiagnosticContext(String message, DiagnosticContext parent)
		{
			this.message = message;
			if (parent != null)
				fullMessage = parent.fullMessage + ' ' + message;
			else
				fullMessage = message;
		}
	}


	static Hashtable ht = new Hashtable();
	static int pushCounter = 0;
	static final int REAP_THRESHOLD = 5;

	private NDC()
	{
	}

	private static Stack getCurrentStack()
	{
		if (ht != null)
			return (Stack)ht.get(Thread.currentThread());
		else
			return null;
	}

	public static void clear()
	{
		Stack stack = getCurrentStack();
		if (stack != null)
			stack.setSize(0);
	}

	public static Stack cloneStack()
	{
		Stack stack = getCurrentStack();
		if (stack == null)
			return null;
		else
			return (Stack)stack.clone();
	}

	public static void inherit(Stack stack)
	{
		if (stack != null)
			ht.put(Thread.currentThread(), stack);
	}

	public static String get()
	{
		Stack s = getCurrentStack();
		if (s != null && !s.isEmpty())
			return ((DiagnosticContext)s.peek()).fullMessage;
		else
			return null;
	}

	public static int getDepth()
	{
		Stack stack = getCurrentStack();
		if (stack == null)
			return 0;
		else
			return stack.size();
	}

	private static void lazyRemove()
	{
label0:
		{
			if (ht == null)
				return;
			synchronized (ht)
			{
				if (++pushCounter > 5)
					break label0;
			}
			return;
		}
		Vector v;
		pushCounter = 0;
		int misses = 0;
		v = new Vector();
		for (Enumeration enumeration = ht.keys(); enumeration.hasMoreElements() && misses <= 4;)
		{
			Thread t = (Thread)enumeration.nextElement();
			if (t.isAlive())
			{
				misses++;
			} else
			{
				misses = 0;
				v.addElement(t);
			}
		}

		hashtable;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		int size = v.size();
		for (int i = 0; i < size; i++)
		{
			Thread t = (Thread)v.elementAt(i);
			LogLog.debug("Lazy NDC removal for thread [" + t.getName() + "] (" + ht.size() + ").");
			ht.remove(t);
		}

		return;
	}

	public static String pop()
	{
		Stack stack = getCurrentStack();
		if (stack != null && !stack.isEmpty())
			return ((DiagnosticContext)stack.pop()).message;
		else
			return "";
	}

	public static String peek()
	{
		Stack stack = getCurrentStack();
		if (stack != null && !stack.isEmpty())
			return ((DiagnosticContext)stack.peek()).message;
		else
			return "";
	}

	public static void push(String message)
	{
		Stack stack = getCurrentStack();
		if (stack == null)
		{
			DiagnosticContext dc = new DiagnosticContext(message, null);
			stack = new Stack();
			Thread key = Thread.currentThread();
			ht.put(key, stack);
			stack.push(dc);
		} else
		if (stack.isEmpty())
		{
			DiagnosticContext dc = new DiagnosticContext(message, null);
			stack.push(dc);
		} else
		{
			DiagnosticContext parent = (DiagnosticContext)stack.peek();
			stack.push(new DiagnosticContext(message, parent));
		}
	}

	public static void remove()
	{
		if (ht != null)
		{
			ht.remove(Thread.currentThread());
			lazyRemove();
		}
	}

	public static void setMaxDepth(int maxDepth)
	{
		Stack stack = getCurrentStack();
		if (stack != null && maxDepth < stack.size())
			stack.setSize(maxDepth);
	}

}
