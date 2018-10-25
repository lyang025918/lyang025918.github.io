// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RewriteAppender.java

package org.apache.log4j.rewrite;

import java.util.Enumeration;
import java.util.Properties;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.helpers.AppenderAttachableImpl;
import org.apache.log4j.spi.*;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.xml.UnrecognizedElementHandler;
import org.w3c.dom.Element;

// Referenced classes of package org.apache.log4j.rewrite:
//			RewritePolicy

public class RewriteAppender extends AppenderSkeleton
	implements AppenderAttachable, UnrecognizedElementHandler
{

	private RewritePolicy policy;
	private final AppenderAttachableImpl appenders = new AppenderAttachableImpl();

	public RewriteAppender()
	{
	}

	protected void append(LoggingEvent event)
	{
		LoggingEvent rewritten = event;
		if (policy != null)
			rewritten = policy.rewrite(event);
		if (rewritten != null)
			synchronized (appenders)
			{
				appenders.appendLoopOnAppenders(rewritten);
			}
	}

	public void addAppender(Appender newAppender)
	{
		synchronized (appenders)
		{
			appenders.addAppender(newAppender);
		}
	}

	public Enumeration getAllAppenders()
	{
		AppenderAttachableImpl appenderattachableimpl = appenders;
		JVM INSTR monitorenter ;
		return appenders.getAllAppenders();
		Exception exception;
		exception;
		throw exception;
	}

	public Appender getAppender(String name)
	{
		AppenderAttachableImpl appenderattachableimpl = appenders;
		JVM INSTR monitorenter ;
		return appenders.getAppender(name);
		Exception exception;
		exception;
		throw exception;
	}

	public void close()
	{
		closed = true;
		synchronized (appenders)
		{
			Enumeration iter = appenders.getAllAppenders();
			if (iter != null)
				do
				{
					if (!iter.hasMoreElements())
						break;
					Object next = iter.nextElement();
					if (next instanceof Appender)
						((Appender)next).close();
				} while (true);
		}
	}

	public boolean isAttached(Appender appender)
	{
		AppenderAttachableImpl appenderattachableimpl = appenders;
		JVM INSTR monitorenter ;
		return appenders.isAttached(appender);
		Exception exception;
		exception;
		throw exception;
	}

	public boolean requiresLayout()
	{
		return false;
	}

	public void removeAllAppenders()
	{
		synchronized (appenders)
		{
			appenders.removeAllAppenders();
		}
	}

	public void removeAppender(Appender appender)
	{
		synchronized (appenders)
		{
			appenders.removeAppender(appender);
		}
	}

	public void removeAppender(String name)
	{
		synchronized (appenders)
		{
			appenders.removeAppender(name);
		}
	}

	public void setRewritePolicy(RewritePolicy rewritePolicy)
	{
		policy = rewritePolicy;
	}

	public boolean parseUnrecognizedElement(Element element, Properties props)
		throws Exception
	{
		String nodeName = element.getNodeName();
		if ("rewritePolicy".equals(nodeName))
		{
			Object rewritePolicy = DOMConfigurator.parseElement(element, props, org.apache.log4j.rewrite.RewritePolicy.class);
			if (rewritePolicy != null)
			{
				if (rewritePolicy instanceof OptionHandler)
					((OptionHandler)rewritePolicy).activateOptions();
				setRewritePolicy((RewritePolicy)rewritePolicy);
			}
			return true;
		} else
		{
			return false;
		}
	}
}
