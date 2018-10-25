// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SvcBuildCallbackDel.java

package com.iflytek.itm.svc.svccom.gen;

import Ice._ObjectDel;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			SvcDocumentHolder

public interface _SvcBuildCallbackDel
	extends _ObjectDel
{

	public abstract int read(SvcDocumentHolder svcdocumentholder, Map map)
		throws LocalExceptionWrapper;

	public abstract void event(String s, int i, String s1, Map map)
		throws LocalExceptionWrapper;
}
