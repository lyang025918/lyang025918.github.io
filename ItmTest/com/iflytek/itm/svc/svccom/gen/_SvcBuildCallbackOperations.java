// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SvcBuildCallbackOperations.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.Current;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			SvcDocumentHolder

public interface _SvcBuildCallbackOperations
{

	public abstract int read(SvcDocumentHolder svcdocumentholder, Current current);

	public abstract void event(String s, int i, String s1, Current current);
}
