// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITM.java

package com.iflytek.itm.api;


// Referenced classes of package com.iflytek.itm.api:
//			ITMBuilder, ITMSearchContext

public interface ITM
{

	public abstract int build(String s, String s1, ITMBuilder itmbuilder);

	public abstract int build(String s, String s1, ITMBuilder.ITMDocument itmdocument);

	public abstract int search(String s, String s1, String s2, ITMSearchContext itmsearchcontext);

	public abstract int mining(String s, String s1, String s2, StringBuffer stringbuffer);

	public abstract int maintain(String s, String s1, String s2);
}
