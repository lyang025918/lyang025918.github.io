// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WordInfoListComp.java

package com.iflytek.itm.mining;

import java.util.Comparator;

// Referenced classes of package com.iflytek.itm.mining:
//			WordInfo

public class WordInfoListComp
	implements Comparator
{

	public WordInfoListComp()
	{
	}

	public int compare(Object arg0, Object arg1)
	{
		WordInfo relWord1 = (WordInfo)arg0;
		WordInfo relWord2 = (WordInfo)arg1;
		int flag = 0;
		if (relWord1.score > relWord2.score)
			flag = -1;
		else
		if (relWord1.score == relWord2.score)
			flag = 0;
		else
			flag = 1;
		return flag;
	}
}
