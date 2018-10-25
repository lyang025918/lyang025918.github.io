// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ProcessPrx.java

package Ice;

import java.util.Map;

// Referenced classes of package Ice:
//			ObjectPrx, AsyncResult, Callback, Callback_Process_shutdown, 
//			AMI_Process_shutdown, Callback_Process_writeMessage, AMI_Process_writeMessage

public interface ProcessPrx
	extends ObjectPrx
{

	public abstract void shutdown();

	public abstract void shutdown(Map map);

	public abstract AsyncResult begin_shutdown();

	public abstract AsyncResult begin_shutdown(Map map);

	public abstract AsyncResult begin_shutdown(Callback callback);

	public abstract AsyncResult begin_shutdown(Map map, Callback callback);

	public abstract AsyncResult begin_shutdown(Callback_Process_shutdown callback_process_shutdown);

	public abstract AsyncResult begin_shutdown(Map map, Callback_Process_shutdown callback_process_shutdown);

	public abstract void end_shutdown(AsyncResult asyncresult);

	public abstract boolean shutdown_async(AMI_Process_shutdown ami_process_shutdown);

	public abstract boolean shutdown_async(AMI_Process_shutdown ami_process_shutdown, Map map);

	public abstract void writeMessage(String s, int i);

	public abstract void writeMessage(String s, int i, Map map);

	public abstract AsyncResult begin_writeMessage(String s, int i);

	public abstract AsyncResult begin_writeMessage(String s, int i, Map map);

	public abstract AsyncResult begin_writeMessage(String s, int i, Callback callback);

	public abstract AsyncResult begin_writeMessage(String s, int i, Map map, Callback callback);

	public abstract AsyncResult begin_writeMessage(String s, int i, Callback_Process_writeMessage callback_process_writemessage);

	public abstract AsyncResult begin_writeMessage(String s, int i, Map map, Callback_Process_writeMessage callback_process_writemessage);

	public abstract void end_writeMessage(AsyncResult asyncresult);

	public abstract boolean writeMessage_async(AMI_Process_writeMessage ami_process_writemessage, String s, int i);

	public abstract boolean writeMessage_async(AMI_Process_writeMessage ami_process_writemessage, String s, int i, Map map);
}
