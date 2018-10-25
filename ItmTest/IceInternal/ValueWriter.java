// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ValueWriter.java

package IceInternal;

import Ice.Object;
import Ice.ObjectPrxHelperBase;
import IceUtilInternal.OutputBase;
import java.lang.reflect.*;
import java.util.*;

// Referenced classes of package IceInternal:
//			Reference

public final class ValueWriter
{

	static final boolean $assertionsDisabled = !IceInternal/ValueWriter.desiredAssertionStatus();

	public ValueWriter()
	{
	}

	public static void write(Object obj, OutputBase out)
	{
		writeValue(null, obj, null, out);
	}

	private static void writeValue(String name, Object value, Map objectTable, OutputBase out)
	{
		if (value == null)
		{
			writeName(name, out);
			out.print("(null)");
		} else
		{
			Class c = value.getClass();
			if (c.equals(java/lang/Byte) || c.equals(java/lang/Short) || c.equals(java/lang/Integer) || c.equals(java/lang/Long) || c.equals(java/lang/Double) || c.equals(java/lang/Float) || c.equals(java/lang/Boolean))
			{
				writeName(name, out);
				out.print(value.toString());
			} else
			if (c.equals(java/lang/String))
			{
				writeName(name, out);
				out.print("\"");
				out.useCurrentPosAsIndent();
				String str = value.toString();
				int start;
				int pos;
				for (start = 0; start < str.length() && (pos = str.indexOf('\n', start)) != -1; start = pos + 1)
				{
					out.print(str.substring(start, pos));
					out.nl();
				}

				if (start < str.length())
					out.print(str.substring(start));
				out.print("\"");
				out.restoreIndent();
			} else
			if (c.isArray())
			{
				int n = Array.getLength(value);
				for (int i = 0; i < n; i++)
				{
					String elem = name == null ? "" : name;
					elem = (new StringBuilder()).append(elem).append("[").append(i).append("]").toString();
					writeValue(elem, Array.get(value, i), objectTable, out);
				}

			} else
			if (value instanceof Map)
			{
				Map map = (Map)value;
				java.util.Map.Entry entry;
				String elem;
				for (Iterator i = map.entrySet().iterator(); i.hasNext(); writeValue((new StringBuilder()).append(elem).append("value").toString(), entry.getValue(), objectTable, out))
				{
					entry = (java.util.Map.Entry)i.next();
					elem = name == null ? "" : (new StringBuilder()).append(name).append(".").toString();
					writeValue((new StringBuilder()).append(elem).append("key").toString(), entry.getKey(), objectTable, out);
				}

			} else
			if (value instanceof ObjectPrxHelperBase)
			{
				writeName(name, out);
				ObjectPrxHelperBase proxy = (ObjectPrxHelperBase)value;
				out.print(proxy.__reference().toString());
			} else
			if (value instanceof Ice.Object)
			{
				if (objectTable != null && objectTable.containsKey(value))
				{
					writeName(name, out);
					out.print("(recursive)");
				} else
				{
					if (objectTable == null)
						objectTable = new IdentityHashMap();
					objectTable.put(value, null);
					writeFields(name, value, c, objectTable, out);
				}
			} else
			if (value instanceof Enum)
			{
				writeName(name, out);
				out.print(((Enum)value).name());
			} else
			{
				writeFields(name, value, c, objectTable, out);
			}
		}
	}

	private static void writeFields(String name, Object obj, Class c, Map objectTable, OutputBase out)
	{
		if (!c.equals(java/lang/Object))
		{
			writeFields(name, obj, c.getSuperclass(), objectTable, out);
			Field fields[] = null;
			try
			{
				fields = c.getDeclaredFields();
			}
			catch (SecurityException ex)
			{
				try
				{
					fields = c.getFields();
				}
				catch (SecurityException e)
				{
					return;
				}
			}
			if (!$assertionsDisabled && fields == null)
				throw new AssertionError();
			Field arr$[] = fields;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Field field = arr$[i$];
				int mods = field.getModifiers();
				if (!Modifier.isPublic(mods) || Modifier.isStatic(mods))
					continue;
				String fieldName = name == null ? field.getName() : (new StringBuilder()).append(name).append('.').append(field.getName()).toString();
				try
				{
					Object value = field.get(obj);
					writeValue(fieldName, value, objectTable, out);
					continue;
				}
				catch (IllegalAccessException ex) { }
				if (!$assertionsDisabled)
					throw new AssertionError();
			}

		}
	}

	private static void writeName(String name, OutputBase out)
	{
		if (name != null)
		{
			out.nl();
			out.print((new StringBuilder()).append(name).append(" = ").toString());
		}
	}

}
