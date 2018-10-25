// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SvcBuildCallbackPrx.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;
import java.util.Map;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			SvcDocumentHolder, Callback_SvcBuildCallback_read, Callback_SvcBuildCallback_event

public interface SvcBuildCallbackPrx
	extends ObjectPrx
{

	public abstract int read(SvcDocumentHolder svcdocumentholder);

	public abstract int read(SvcDocumentHolder svcdocumentholder, Map map);

	public abstract AsyncResult begin_read();

	public abstract AsyncResult begin_read(Map map);

	public abstract AsyncResult begin_read(Callback callback);

	public abstract AsyncResult begin_read(Map map, Callback callback);

	public abstract AsyncResult begin_read(Callback_SvcBuildCallback_read callback_svcbuildcallback_read);

	public abstract AsyncResult begin_read(Map map, Callback_SvcBuildCallback_read callback_svcbuildcallback_read);

	public abstract int end_read(SvcDocumentHolder svcdocumentholder, AsyncResult asyncresult);

	public abstract void event(String s, int i, String s1);

	public abstract void event(String s, int i, String s1, Map map);

	public abstract AsyncResult begin_event(String s, int i, String s1);

	public abstract AsyncResult begin_event(String s, int i, String s1, Map map);

	public abstract AsyncResult begin_event(String s, int i, String s1, Callback callback);

	public abstract AsyncResult begin_event(String s, int i, String s1, Map map, Callback callback);

	public abstract AsyncResult begin_event(String s, int i, String s1, Callback_SvcBuildCallback_event callback_svcbuildcallback_event);

	public abstract AsyncResult begin_event(String s, int i, String s1, Map map, Callback_SvcBuildCallback_event callback_svcbuildcallback_event);

	public abstract void end_event(AsyncResult asyncresult);
}
