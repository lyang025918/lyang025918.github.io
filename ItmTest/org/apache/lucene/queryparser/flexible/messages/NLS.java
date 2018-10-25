// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NLS.java

package org.apache.lucene.queryparser.flexible.messages;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.MessageFormat;
import java.util.*;

public class NLS
{

	private static Map bundles = new HashMap(0);

	protected NLS()
	{
	}

	public static String getLocalizedMessage(String key)
	{
		return getLocalizedMessage(key, Locale.getDefault());
	}

	public static String getLocalizedMessage(String key, Locale locale)
	{
		Object message = getResourceBundleObject(key, locale);
		if (message == null)
			return (new StringBuilder()).append("Message with key:").append(key).append(" and locale: ").append(locale).append(" not found.").toString();
		else
			return message.toString();
	}

	public static transient String getLocalizedMessage(String key, Locale locale, Object args[])
	{
		String str = getLocalizedMessage(key, locale);
		if (args.length > 0)
			str = MessageFormat.format(str, args);
		return str;
	}

	public static transient String getLocalizedMessage(String key, Object args[])
	{
		return getLocalizedMessage(key, Locale.getDefault(), args);
	}

	protected static void initializeMessages(String bundleName, Class clazz)
	{
		try
		{
			load(clazz);
			if (!bundles.containsKey(bundleName))
				bundles.put(bundleName, clazz);
		}
		catch (Throwable e) { }
	}

	private static Object getResourceBundleObject(String messageKey, Locale locale)
	{
		Iterator it = bundles.keySet().iterator();
_L2:
		ResourceBundle resourceBundle;
		if (!it.hasNext())
			break; /* Loop/switch isn't completed */
		Class clazz = (Class)bundles.get(it.next());
		resourceBundle = ResourceBundle.getBundle(clazz.getName(), locale);
		if (resourceBundle == null)
			continue; /* Loop/switch isn't completed */
		Object obj = resourceBundle.getObject(messageKey);
		if (obj != null)
			return obj;
		continue; /* Loop/switch isn't completed */
		MissingResourceException e;
		e;
		if (true) goto _L2; else goto _L1
_L1:
		return null;
	}

	private static void load(Class clazz)
	{
		Field fieldArray[] = clazz.getDeclaredFields();
		boolean isFieldAccessible = (clazz.getModifiers() & 1) != 0;
		int len = fieldArray.length;
		Map fields = new HashMap(len * 2);
		for (int i = 0; i < len; i++)
		{
			fields.put(fieldArray[i].getName(), fieldArray[i]);
			loadfieldValue(fieldArray[i], isFieldAccessible, clazz);
		}

	}

	private static void loadfieldValue(Field field, boolean isFieldAccessible, Class clazz)
	{
		int MOD_EXPECTED = 9;
		int MOD_MASK = MOD_EXPECTED | 0x10;
		if ((field.getModifiers() & MOD_MASK) != MOD_EXPECTED)
			return;
		if (!isFieldAccessible)
			makeAccessible(field);
		try
		{
			field.set(null, field.getName());
			validateMessage(field.getName(), clazz);
		}
		catch (IllegalArgumentException e) { }
		catch (IllegalAccessException e) { }
	}

	private static void validateMessage(String key, Class clazz)
	{
		try
		{
			ResourceBundle resourceBundle = ResourceBundle.getBundle(clazz.getName(), Locale.getDefault());
			Object obj;
			if (resourceBundle != null)
				obj = resourceBundle.getObject(key);
		}
		catch (MissingResourceException e) { }
		catch (Throwable e) { }
	}

	private static void makeAccessible(Field field)
	{
		if (System.getSecurityManager() == null)
			field.setAccessible(true);
		else
			AccessController.doPrivileged(new PrivilegedAction(field) {

				final Field val$field;

				public Void run()
				{
					field.setAccessible(true);
					return null;
				}

				public volatile Object run()
				{
					return run();
				}

			
			{
				field = field1;
				super();
			}
			});
	}

}
