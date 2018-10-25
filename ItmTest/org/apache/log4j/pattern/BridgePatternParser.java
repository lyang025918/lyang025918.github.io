// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BridgePatternParser.java

package org.apache.log4j.pattern;

import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;

// Referenced classes of package org.apache.log4j.pattern:
//			BridgePatternConverter

public final class BridgePatternParser extends PatternParser
{

	public BridgePatternParser(String conversionPattern)
	{
		super(conversionPattern);
	}

	public PatternConverter parse()
	{
		return new BridgePatternConverter(pattern);
	}
}
