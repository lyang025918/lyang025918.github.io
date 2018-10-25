// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItmSvcPrx.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;
import java.util.Map;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			SvcBuildCallbackPrx, Callback_ItmSvc_build, SvcSearchResultPrx, Callback_ItmSvc_search, 
//			Callback_ItmSvc_mining, Callback_ItmSvc_maintain

public interface ItmSvcPrx
	extends ObjectPrx
{

	public abstract int build(String s, String s1, SvcBuildCallbackPrx svcbuildcallbackprx);

	public abstract int build(String s, String s1, SvcBuildCallbackPrx svcbuildcallbackprx, Map map);

	public abstract AsyncResult begin_build(String s, String s1, SvcBuildCallbackPrx svcbuildcallbackprx);

	public abstract AsyncResult begin_build(String s, String s1, SvcBuildCallbackPrx svcbuildcallbackprx, Map map);

	public abstract AsyncResult begin_build(String s, String s1, SvcBuildCallbackPrx svcbuildcallbackprx, Callback callback);

	public abstract AsyncResult begin_build(String s, String s1, SvcBuildCallbackPrx svcbuildcallbackprx, Map map, Callback callback);

	public abstract AsyncResult begin_build(String s, String s1, SvcBuildCallbackPrx svcbuildcallbackprx, Callback_ItmSvc_build callback_itmsvc_build);

	public abstract AsyncResult begin_build(String s, String s1, SvcBuildCallbackPrx svcbuildcallbackprx, Map map, Callback_ItmSvc_build callback_itmsvc_build);

	public abstract int end_build(AsyncResult asyncresult);

	public abstract SvcSearchResultPrx search(String s, String s1, String s2, IntHolder intholder);

	public abstract SvcSearchResultPrx search(String s, String s1, String s2, IntHolder intholder, Map map);

	public abstract AsyncResult begin_search(String s, String s1, String s2);

	public abstract AsyncResult begin_search(String s, String s1, String s2, Map map);

	public abstract AsyncResult begin_search(String s, String s1, String s2, Callback callback);

	public abstract AsyncResult begin_search(String s, String s1, String s2, Map map, Callback callback);

	public abstract AsyncResult begin_search(String s, String s1, String s2, Callback_ItmSvc_search callback_itmsvc_search);

	public abstract AsyncResult begin_search(String s, String s1, String s2, Map map, Callback_ItmSvc_search callback_itmsvc_search);

	public abstract SvcSearchResultPrx end_search(IntHolder intholder, AsyncResult asyncresult);

	public abstract int mining(String s, String s1, String s2, StringHolder stringholder);

	public abstract int mining(String s, String s1, String s2, StringHolder stringholder, Map map);

	public abstract AsyncResult begin_mining(String s, String s1, String s2);

	public abstract AsyncResult begin_mining(String s, String s1, String s2, Map map);

	public abstract AsyncResult begin_mining(String s, String s1, String s2, Callback callback);

	public abstract AsyncResult begin_mining(String s, String s1, String s2, Map map, Callback callback);

	public abstract AsyncResult begin_mining(String s, String s1, String s2, Callback_ItmSvc_mining callback_itmsvc_mining);

	public abstract AsyncResult begin_mining(String s, String s1, String s2, Map map, Callback_ItmSvc_mining callback_itmsvc_mining);

	public abstract int end_mining(StringHolder stringholder, AsyncResult asyncresult);

	public abstract int maintain(String s, String s1, String s2);

	public abstract int maintain(String s, String s1, String s2, Map map);

	public abstract AsyncResult begin_maintain(String s, String s1, String s2);

	public abstract AsyncResult begin_maintain(String s, String s1, String s2, Map map);

	public abstract AsyncResult begin_maintain(String s, String s1, String s2, Callback callback);

	public abstract AsyncResult begin_maintain(String s, String s1, String s2, Map map, Callback callback);

	public abstract AsyncResult begin_maintain(String s, String s1, String s2, Callback_ItmSvc_maintain callback_itmsvc_maintain);

	public abstract AsyncResult begin_maintain(String s, String s1, String s2, Map map, Callback_ItmSvc_maintain callback_itmsvc_maintain);

	public abstract int end_maintain(AsyncResult asyncresult);
}
