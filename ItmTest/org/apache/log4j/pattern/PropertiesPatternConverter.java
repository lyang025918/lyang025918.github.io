// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertiesPatternConverter.java

package org.apache.log4j.pattern;

import java.util.Iterator;
import java.util.Set;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.MDCKeySetExtractor;
import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.pattern:
//			LoggingEventPatternConverter

public final class PropertiesPatternConverter extends LoggingEventPatternConverter
{

	private final String option;

	private PropertiesPatternConverter(String options[])
	{
		super(options == null || options.length <= 0 ? "Properties" : "Property{" + options[0] + "}", "property");
		if (options != null && options.length > 0)
			option = options[0];
		else
			option = null;
	}

	public static PropertiesPatternConverter newInstance(String options[])
	{
		return new PropertiesPatternConverter(options);
	}

	public void format(LoggingEvent event, StringBuffer toAppendTo)
	{
		if (option == null)
		{
			toAppendTo.append("{");
			try
			{
				Set keySet = MDCKeySetExtractor.INSTANCE.getPropertyKeySet(event);
				if (keySet != null)
				{
					Object item;
					Object val;
					for (Iterator i = keySet.iterator(); i.hasNext(); toAppendTo.append("{").append(item).append(",").append(val).append("}"))
					{
						item = i.next();
						val = event.getMDC(item.toString());
					}

				}
			}
			catch (Exception ex)
			{
				LogLog.error("Unexpected exception while extracting MDC keys", ex);
			}
			toAppendTo.append("}");
		} else
		{
			Object val = event.getMDC(option);
			if (val != null)
				toAppendTo.append(val);
		}
	}
}
