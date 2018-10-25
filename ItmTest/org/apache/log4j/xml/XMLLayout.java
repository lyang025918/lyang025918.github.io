// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   XMLLayout.java

package org.apache.log4j.xml;

import java.util.Arrays;
import java.util.Set;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.Transform;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

public class XMLLayout extends Layout
{

	private final int DEFAULT_SIZE = 256;
	private final int UPPER_LIMIT = 2048;
	private StringBuffer buf;
	private boolean locationInfo;
	private boolean properties;

	public XMLLayout()
	{
		buf = new StringBuffer(256);
		locationInfo = false;
		properties = false;
	}

	public void setLocationInfo(boolean flag)
	{
		locationInfo = flag;
	}

	public boolean getLocationInfo()
	{
		return locationInfo;
	}

	public void setProperties(boolean flag)
	{
		properties = flag;
	}

	public boolean getProperties()
	{
		return properties;
	}

	public void activateOptions()
	{
	}

	public String format(LoggingEvent event)
	{
		if (buf.capacity() > 2048)
			buf = new StringBuffer(256);
		else
			buf.setLength(0);
		buf.append("<log4j:event logger=\"");
		buf.append(Transform.escapeTags(event.getLoggerName()));
		buf.append("\" timestamp=\"");
		buf.append(event.timeStamp);
		buf.append("\" level=\"");
		buf.append(Transform.escapeTags(String.valueOf(event.getLevel())));
		buf.append("\" thread=\"");
		buf.append(Transform.escapeTags(event.getThreadName()));
		buf.append("\">\r\n");
		buf.append("<log4j:message><![CDATA[");
		Transform.appendEscapingCDATA(buf, event.getRenderedMessage());
		buf.append("]]></log4j:message>\r\n");
		String ndc = event.getNDC();
		if (ndc != null)
		{
			buf.append("<log4j:NDC><![CDATA[");
			Transform.appendEscapingCDATA(buf, ndc);
			buf.append("]]></log4j:NDC>\r\n");
		}
		String s[] = event.getThrowableStrRep();
		if (s != null)
		{
			buf.append("<log4j:throwable><![CDATA[");
			for (int i = 0; i < s.length; i++)
			{
				Transform.appendEscapingCDATA(buf, s[i]);
				buf.append("\r\n");
			}

			buf.append("]]></log4j:throwable>\r\n");
		}
		if (this.locationInfo)
		{
			LocationInfo locationInfo = event.getLocationInformation();
			buf.append("<log4j:locationInfo class=\"");
			buf.append(Transform.escapeTags(locationInfo.getClassName()));
			buf.append("\" method=\"");
			buf.append(Transform.escapeTags(locationInfo.getMethodName()));
			buf.append("\" file=\"");
			buf.append(Transform.escapeTags(locationInfo.getFileName()));
			buf.append("\" line=\"");
			buf.append(locationInfo.getLineNumber());
			buf.append("\"/>\r\n");
		}
		if (properties)
		{
			Set keySet = event.getPropertyKeySet();
			if (keySet.size() > 0)
			{
				buf.append("<log4j:properties>\r\n");
				Object keys[] = keySet.toArray();
				Arrays.sort(keys);
				for (int i = 0; i < keys.length; i++)
				{
					String key = keys[i].toString();
					Object val = event.getMDC(key);
					if (val != null)
					{
						buf.append("<log4j:data name=\"");
						buf.append(Transform.escapeTags(key));
						buf.append("\" value=\"");
						buf.append(Transform.escapeTags(String.valueOf(val)));
						buf.append("\"/>\r\n");
					}
				}

				buf.append("</log4j:properties>\r\n");
			}
		}
		buf.append("</log4j:event>\r\n\r\n");
		return buf.toString();
	}

	public boolean ignoresThrowable()
	{
		return false;
	}
}
