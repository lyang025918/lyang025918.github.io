// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DispatchWorkItem.java

package IceInternal;

import Ice.*;
import java.io.PrintWriter;
import java.io.StringWriter;

// Referenced classes of package IceInternal:
//			ThreadPoolWorkItem, Instance, ThreadPoolCurrent

public abstract class DispatchWorkItem
	implements ThreadPoolWorkItem, Runnable
{

	private Instance _instance;

	public DispatchWorkItem(Instance instance)
	{
		_instance = instance;
	}

	public final void execute(ThreadPoolCurrent current)
	{
		Dispatcher dispatcher = _instance.initializationData().dispatcher;
		if (dispatcher != null)
		{
			try
			{
				dispatcher.dispatch(this, null);
			}
			catch (Exception ex)
			{
				if (_instance.initializationData().properties.getPropertyAsIntWithDefault("Ice.Warn.Dispatch", 1) > 1)
				{
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					ex.printStackTrace(pw);
					pw.flush();
					_instance.initializationData().logger.warning((new StringBuilder()).append("dispatch exception:\n").append(sw.toString()).toString());
				}
			}
		} else
		{
			current.ioCompleted();
			run();
		}
	}
}
