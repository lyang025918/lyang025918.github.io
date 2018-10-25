// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Dispatcher.java

package org.apache.log4j;

import org.apache.log4j.helpers.AppenderAttachableImpl;
import org.apache.log4j.helpers.BoundedFIFO;

// Referenced classes of package org.apache.log4j:
//			AsyncAppender

/**
 * @deprecated Class Dispatcher is deprecated
 */

class Dispatcher extends Thread
{

	/**
	 * @deprecated Field bf is deprecated
	 */
	private BoundedFIFO bf;
	private AppenderAttachableImpl aai;
	private boolean interrupted;
	AsyncAppender container;

	/**
	 * @deprecated Method Dispatcher is deprecated
	 */

	Dispatcher(BoundedFIFO bf, AsyncAppender container)
	{
		interrupted = false;
		this.bf = bf;
		this.container = container;
		aai = container.aai;
		setDaemon(true);
		setPriority(1);
		setName("Dispatcher-" + getName());
	}

	void close()
	{
		synchronized (bf)
		{
			interrupted = true;
			if (bf.length() == 0)
				bf.notify();
		}
	}

	public void run()
	{
_L3:
label0:
		{
			synchronized (bf)
			{
				if (bf.length() != 0)
					break MISSING_BLOCK_LABEL_45;
				if (!interrupted)
					break label0;
			}
			break; /* Loop/switch isn't completed */
		}
		try
		{
			bf.wait();
			break MISSING_BLOCK_LABEL_45;
		}
		catch (InterruptedException e) { }
		boundedfifo;
		JVM INSTR monitorexit ;
		break; /* Loop/switch isn't completed */
		org.apache.log4j.spi.LoggingEvent event;
		event = bf.get();
		if (bf.wasFull())
			bf.notify();
		boundedfifo;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		synchronized (container.aai)
		{
			if (aai != null && event != null)
				aai.appendLoopOnAppenders(event);
		}
		if (true) goto _L3; else goto _L2
_L2:
		aai.removeAllAppenders();
		return;
	}
}
