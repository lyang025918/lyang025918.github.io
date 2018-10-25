// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SvcSearchResultPrx.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;
import java.util.Map;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			Callback_SvcSearchResult_getTotalHits, Callback_SvcSearchResult_docs, Callback_SvcSearchResult_getGroupTotalHits, Callback_SvcSearchResult_groups, 
//			Callback_SvcSearchResult_close

public interface SvcSearchResultPrx
	extends ObjectPrx
{

	public abstract int getTotalHits();

	public abstract int getTotalHits(Map map);

	public abstract AsyncResult begin_getTotalHits();

	public abstract AsyncResult begin_getTotalHits(Map map);

	public abstract AsyncResult begin_getTotalHits(Callback callback);

	public abstract AsyncResult begin_getTotalHits(Map map, Callback callback);

	public abstract AsyncResult begin_getTotalHits(Callback_SvcSearchResult_getTotalHits callback_svcsearchresult_gettotalhits);

	public abstract AsyncResult begin_getTotalHits(Map map, Callback_SvcSearchResult_getTotalHits callback_svcsearchresult_gettotalhits);

	public abstract int end_getTotalHits(AsyncResult asyncresult);

	public abstract int docs(Holder holder);

	public abstract int docs(Holder holder, Map map);

	public abstract AsyncResult begin_docs();

	public abstract AsyncResult begin_docs(Map map);

	public abstract AsyncResult begin_docs(Callback callback);

	public abstract AsyncResult begin_docs(Map map, Callback callback);

	public abstract AsyncResult begin_docs(Callback_SvcSearchResult_docs callback_svcsearchresult_docs);

	public abstract AsyncResult begin_docs(Map map, Callback_SvcSearchResult_docs callback_svcsearchresult_docs);

	public abstract int end_docs(Holder holder, AsyncResult asyncresult);

	public abstract int getGroupTotalHits();

	public abstract int getGroupTotalHits(Map map);

	public abstract AsyncResult begin_getGroupTotalHits();

	public abstract AsyncResult begin_getGroupTotalHits(Map map);

	public abstract AsyncResult begin_getGroupTotalHits(Callback callback);

	public abstract AsyncResult begin_getGroupTotalHits(Map map, Callback callback);

	public abstract AsyncResult begin_getGroupTotalHits(Callback_SvcSearchResult_getGroupTotalHits callback_svcsearchresult_getgrouptotalhits);

	public abstract AsyncResult begin_getGroupTotalHits(Map map, Callback_SvcSearchResult_getGroupTotalHits callback_svcsearchresult_getgrouptotalhits);

	public abstract int end_getGroupTotalHits(AsyncResult asyncresult);

	public abstract int groups(Holder holder);

	public abstract int groups(Holder holder, Map map);

	public abstract AsyncResult begin_groups();

	public abstract AsyncResult begin_groups(Map map);

	public abstract AsyncResult begin_groups(Callback callback);

	public abstract AsyncResult begin_groups(Map map, Callback callback);

	public abstract AsyncResult begin_groups(Callback_SvcSearchResult_groups callback_svcsearchresult_groups);

	public abstract AsyncResult begin_groups(Map map, Callback_SvcSearchResult_groups callback_svcsearchresult_groups);

	public abstract int end_groups(Holder holder, AsyncResult asyncresult);

	public abstract int close();

	public abstract int close(Map map);

	public abstract AsyncResult begin_close();

	public abstract AsyncResult begin_close(Map map);

	public abstract AsyncResult begin_close(Callback callback);

	public abstract AsyncResult begin_close(Map map, Callback callback);

	public abstract AsyncResult begin_close(Callback_SvcSearchResult_close callback_svcsearchresult_close);

	public abstract AsyncResult begin_close(Map map, Callback_SvcSearchResult_close callback_svcsearchresult_close);

	public abstract int end_close(AsyncResult asyncresult);
}
