// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ItmSvcOperationsNC.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.IntHolder;
import Ice.StringHolder;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			SvcBuildCallbackPrx, SvcSearchResultPrx

public interface _ItmSvcOperationsNC
{

	public abstract int build(String s, String s1, SvcBuildCallbackPrx svcbuildcallbackprx);

	public abstract SvcSearchResultPrx search(String s, String s1, String s2, IntHolder intholder);

	public abstract int mining(String s, String s1, String s2, StringHolder stringholder);

	public abstract int maintain(String s, String s1, String s2);
}
