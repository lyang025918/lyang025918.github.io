// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ParamParser.java

package com.iflytek.itm.util;

import com.iflytek.itm.api.ITMErrors;
import java.util.Map;

public class ParamParser
{

	public ParamParser()
	{
	}

	public static int parse(String params, Map map)
	{
		int ret = 0;
		if (params == null || params.isEmpty())
		{
			ret = ITMErrors.ITM_ERROR_INVALID_PARAM.code();
			return ret;
		}
		String arrPair[] = params.trim().split("\\n");
		String as[] = arrPair;
		int i = as.length;
		for (int j = 0; j < i; j++)
		{
			String str = as[j];
			String pair[] = str.trim().split("=");
			if (pair.length != 2)
			{
				if (str.trim().startsWith("="))
					ret = ITMErrors.ITM_ERROR_PARAM_EMPTY.code();
				else
					ret = ITMErrors.ITM_ERROR_PARAM_VALUE_EMPTY.code();
			} else
			{
				map.put(pair[0].trim(), pair[1].trim());
			}
		}

		return ret;
	}
}
