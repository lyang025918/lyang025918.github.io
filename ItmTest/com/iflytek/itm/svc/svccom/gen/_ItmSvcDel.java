// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ItmSvcDel.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			SvcBuildCallbackPrx, SvcSearchResultPrx

public interface _ItmSvcDel
	extends _ObjectDel
{

	public abstract int build(String s, String s1, SvcBuildCallbackPrx svcbuildcallbackprx, Map map)
		throws LocalExceptionWrapper;

	public abstract SvcSearchResultPrx search(String s, String s1, String s2, IntHolder intholder, Map map)
		throws LocalExceptionWrapper;

	public abstract int mining(String s, String s1, String s2, StringHolder stringholder, Map map)
		throws LocalExceptionWrapper;

	public abstract int maintain(String s, String s1, String s2, Map map)
		throws LocalExceptionWrapper;
}
