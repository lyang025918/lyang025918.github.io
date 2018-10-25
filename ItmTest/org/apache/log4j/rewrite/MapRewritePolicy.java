// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MapRewritePolicy.java

package org.apache.log4j.rewrite;

import java.util.*;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.rewrite:
//			RewritePolicy

public class MapRewritePolicy
	implements RewritePolicy
{

	public MapRewritePolicy()
	{
	}

	public LoggingEvent rewrite(LoggingEvent source)
	{
		Object msg = source.getMessage();
		if (msg instanceof Map)
		{
			Map props = new HashMap(source.getProperties());
			Map eventProps = (Map)msg;
			Object newMsg = eventProps.get("message");
			if (newMsg == null)
				newMsg = msg;
			Iterator iter = eventProps.entrySet().iterator();
			do
			{
				if (!iter.hasNext())
					break;
				java.util.Map.Entry entry = (java.util.Map.Entry)iter.next();
				if (!"message".equals(entry.getKey()))
					props.put(entry.getKey(), entry.getValue());
			} while (true);
			return new LoggingEvent(source.getFQNOfLoggerClass(), ((org.apache.log4j.Category) (source.getLogger() == null ? ((org.apache.log4j.Category) (Logger.getLogger(source.getLoggerName()))) : source.getLogger())), source.getTimeStamp(), source.getLevel(), newMsg, source.getThreadName(), source.getThrowableInformation(), source.getNDC(), source.getLocationInformation(), props);
		} else
		{
			return source;
		}
	}
}
