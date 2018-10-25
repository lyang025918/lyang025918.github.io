// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertyPrinter.java

package org.apache.log4j.config;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import org.apache.log4j.*;

// Referenced classes of package org.apache.log4j.config:
//			PropertyGetter

public class PropertyPrinter
	implements PropertyGetter.PropertyCallback
{

	protected int numAppenders;
	protected Hashtable appenderNames;
	protected Hashtable layoutNames;
	protected PrintWriter out;
	protected boolean doCapitalize;

	public PropertyPrinter(PrintWriter out)
	{
		this(out, false);
	}

	public PropertyPrinter(PrintWriter out, boolean doCapitalize)
	{
		numAppenders = 0;
		appenderNames = new Hashtable();
		layoutNames = new Hashtable();
		this.out = out;
		this.doCapitalize = doCapitalize;
		print(out);
		out.flush();
	}

	protected String genAppName()
	{
		return "A" + numAppenders++;
	}

	protected boolean isGenAppName(String name)
	{
		if (name.length() < 2 || name.charAt(0) != 'A')
			return false;
		for (int i = 0; i < name.length(); i++)
			if (name.charAt(i) < '0' || name.charAt(i) > '9')
				return false;

		return true;
	}

	public void print(PrintWriter out)
	{
		printOptions(out, Logger.getRootLogger());
		for (Enumeration cats = LogManager.getCurrentLoggers(); cats.hasMoreElements(); printOptions(out, (Logger)cats.nextElement()));
	}

	protected void printOptions(PrintWriter out, Category cat)
	{
		Enumeration appenders = cat.getAllAppenders();
		Level prio = cat.getLevel();
		String appenderString;
		String name;
		for (appenderString = prio != null ? prio.toString() : ""; appenders.hasMoreElements(); appenderString = appenderString + ", " + name)
		{
			Appender app = (Appender)appenders.nextElement();
			if ((name = (String)appenderNames.get(app)) != null)
				continue;
			if ((name = app.getName()) == null || isGenAppName(name))
				name = genAppName();
			appenderNames.put(app, name);
			printOptions(out, app, "log4j.appender." + name);
			if (app.getLayout() != null)
				printOptions(out, app.getLayout(), "log4j.appender." + name + ".layout");
		}

		String catKey = cat != Logger.getRootLogger() ? "log4j.logger." + cat.getName() : "log4j.rootLogger";
		if (appenderString != "")
			out.println(catKey + "=" + appenderString);
		if (!cat.getAdditivity() && cat != Logger.getRootLogger())
			out.println("log4j.additivity." + cat.getName() + "=false");
	}

	protected void printOptions(PrintWriter out, Logger cat)
	{
		printOptions(out, ((Category) (cat)));
	}

	protected void printOptions(PrintWriter out, Object obj, String fullname)
	{
		out.println(fullname + "=" + obj.getClass().getName());
		PropertyGetter.getProperties(obj, this, fullname + ".");
	}

	public void foundProperty(Object obj, String prefix, String name, Object value)
	{
		if ((obj instanceof Appender) && "name".equals(name))
			return;
		if (doCapitalize)
			name = capitalize(name);
		out.println(prefix + name + "=" + value.toString());
	}

	public static String capitalize(String name)
	{
		if (Character.isLowerCase(name.charAt(0)) && (name.length() == 1 || Character.isLowerCase(name.charAt(1))))
		{
			StringBuffer newname = new StringBuffer(name);
			newname.setCharAt(0, Character.toUpperCase(name.charAt(0)));
			return newname.toString();
		} else
		{
			return name;
		}
	}

	public static void main(String args[])
	{
		new PropertyPrinter(new PrintWriter(System.out));
	}
}
