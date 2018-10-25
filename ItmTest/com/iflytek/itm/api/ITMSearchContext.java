// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMSearchContext.java

package com.iflytek.itm.api;


// Referenced classes of package com.iflytek.itm.api:
//			ITMBuilder

public class ITMSearchContext
{
	public static class ITMGroup
	{

		public String value;
		public int docCount;

		public ITMGroup()
		{
		}
	}

	public static interface ITMSearchResult
	{

		public abstract int getTotalHits();

		public abstract ITMBuilder.ITMDocument[] docs();

		public abstract int getGroupTotalHits();

		public abstract ITMGroup[] groups();

		public abstract int close();
	}


	public ITMSearchResult itmSearchResult;

	public ITMSearchContext()
	{
		itmSearchResult = null;
	}
}
