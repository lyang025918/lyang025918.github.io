// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ThreadGroupRenderer.java

package org.apache.log4j.or;

import org.apache.log4j.Layout;

// Referenced classes of package org.apache.log4j.or:
//			ObjectRenderer

public class ThreadGroupRenderer
	implements ObjectRenderer
{

	public ThreadGroupRenderer()
	{
	}

	public String doRender(Object o)
	{
		if (o instanceof ThreadGroup)
		{
			StringBuffer sbuf = new StringBuffer();
			ThreadGroup tg = (ThreadGroup)o;
			sbuf.append("java.lang.ThreadGroup[name=");
			sbuf.append(tg.getName());
			sbuf.append(", maxpri=");
			sbuf.append(tg.getMaxPriority());
			sbuf.append("]");
			Thread t[] = new Thread[tg.activeCount()];
			tg.enumerate(t);
			for (int i = 0; i < t.length; i++)
			{
				sbuf.append(Layout.LINE_SEP);
				sbuf.append("   Thread=[");
				sbuf.append(t[i].getName());
				sbuf.append(",");
				sbuf.append(t[i].getPriority());
				sbuf.append(",");
				sbuf.append(t[i].isDaemon());
				sbuf.append("]");
			}

			return sbuf.toString();
		}
		return o.toString();
		Exception ex;
		ex;
		return ex.toString();
	}
}
