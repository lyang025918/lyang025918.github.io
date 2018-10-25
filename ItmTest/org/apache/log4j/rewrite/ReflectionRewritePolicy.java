// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReflectionRewritePolicy.java

package org.apache.log4j.rewrite;

import java.beans.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.rewrite:
//			RewritePolicy

public class ReflectionRewritePolicy
	implements RewritePolicy
{

	public ReflectionRewritePolicy()
	{
	}

	public LoggingEvent rewrite(LoggingEvent source)
	{
		Object msg;
		Object newMsg;
		Map rewriteProps;
		msg = source.getMessage();
		if (msg instanceof String)
			break MISSING_BLOCK_LABEL_251;
		newMsg = msg;
		rewriteProps = new HashMap(source.getProperties());
		PropertyDescriptor props[] = Introspector.getBeanInfo(msg.getClass(), java.lang.Object.class).getPropertyDescriptors();
		if (props.length <= 0)
			break MISSING_BLOCK_LABEL_251;
		for (int i = 0; i < props.length; i++)
			try
			{
				Object propertyValue = props[i].getReadMethod().invoke(msg, (Object[])null);
				if ("message".equalsIgnoreCase(props[i].getName()))
					newMsg = propertyValue;
				else
					rewriteProps.put(props[i].getName(), propertyValue);
			}
			catch (Exception e)
			{
				LogLog.warn("Unable to evaluate property " + props[i].getName(), e);
			}

		return new LoggingEvent(source.getFQNOfLoggerClass(), ((org.apache.log4j.Category) (source.getLogger() == null ? ((org.apache.log4j.Category) (Logger.getLogger(source.getLoggerName()))) : source.getLogger())), source.getTimeStamp(), source.getLevel(), newMsg, source.getThreadName(), source.getThrowableInformation(), source.getNDC(), source.getLocationInformation(), rewriteProps);
		Exception e;
		e;
		LogLog.warn("Unable to get property descriptors", e);
		return source;
	}
}
