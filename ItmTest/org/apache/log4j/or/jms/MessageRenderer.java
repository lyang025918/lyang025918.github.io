// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MessageRenderer.java

package org.apache.log4j.or.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.or.ObjectRenderer;

public class MessageRenderer
	implements ObjectRenderer
{

	public MessageRenderer()
	{
	}

	public String doRender(Object o)
	{
		if (o instanceof Message)
		{
			StringBuffer sbuf = new StringBuffer();
			Message m = (Message)o;
			try
			{
				sbuf.append("DeliveryMode=");
				switch (m.getJMSDeliveryMode())
				{
				case 1: // '\001'
					sbuf.append("NON_PERSISTENT");
					break;

				case 2: // '\002'
					sbuf.append("PERSISTENT");
					break;

				default:
					sbuf.append("UNKNOWN");
					break;
				}
				sbuf.append(", CorrelationID=");
				sbuf.append(m.getJMSCorrelationID());
				sbuf.append(", Destination=");
				sbuf.append(m.getJMSDestination());
				sbuf.append(", Expiration=");
				sbuf.append(m.getJMSExpiration());
				sbuf.append(", MessageID=");
				sbuf.append(m.getJMSMessageID());
				sbuf.append(", Priority=");
				sbuf.append(m.getJMSPriority());
				sbuf.append(", Redelivered=");
				sbuf.append(m.getJMSRedelivered());
				sbuf.append(", ReplyTo=");
				sbuf.append(m.getJMSReplyTo());
				sbuf.append(", Timestamp=");
				sbuf.append(m.getJMSTimestamp());
				sbuf.append(", Type=");
				sbuf.append(m.getJMSType());
			}
			catch (JMSException e)
			{
				LogLog.error("Could not parse Message.", e);
			}
			return sbuf.toString();
		} else
		{
			return o.toString();
		}
	}
}
