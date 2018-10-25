// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertyRewritePolicy.java

package org.apache.log4j.rewrite;

import java.util.*;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.rewrite:
//			RewritePolicy

public class PropertyRewritePolicy
	implements RewritePolicy
{

	private Map properties;

	public PropertyRewritePolicy()
	{
		properties = Collections.EMPTY_MAP;
	}

	public void setProperties(String props)
	{
		Map hashTable = new HashMap();
		StringTokenizer entry;
		for (StringTokenizer pairs = new StringTokenizer(props, ","); pairs.hasMoreTokens(); hashTable.put(entry.nextElement().toString().trim(), entry.nextElement().toString().trim()))
			entry = new StringTokenizer(pairs.nextToken(), "=");

		synchronized (this)
		{
			properties = hashTable;
		}
	}

	public LoggingEvent rewrite(LoggingEvent source)
	{
		if (!properties.isEmpty())
		{
			Map rewriteProps = new HashMap(source.getProperties());
			Iterator iter = properties.entrySet().iterator();
			do
			{
				if (!iter.hasNext())
					break;
				java.util.Map.Entry entry = (java.util.Map.Entry)iter.next();
				if (!rewriteProps.containsKey(entry.getKey()))
					rewriteProps.put(entry.getKey(), entry.getValue());
			} while (true);
			return new LoggingEvent(source.getFQNOfLoggerClass(), ((org.apache.log4j.Category) (source.getLogger() == null ? ((org.apache.log4j.Category) (Logger.getLogger(source.getLoggerName()))) : source.getLogger())), source.getTimeStamp(), source.getLevel(), source.getMessage(), source.getThreadName(), source.getThrowableInformation(), source.getNDC(), source.getLocationInformation(), rewriteProps);
		} else
		{
			return source;
		}
	}
}
