// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RuleParserConstants.java

package com.iflytek.itm.mining.rule;


public interface RuleParserConstants
{

	public static final int EOF = 0;
	public static final int AND = 5;
	public static final int OR = 6;
	public static final int NOT = 7;
	public static final int NEAR = 8;
	public static final int PLUS = 9;
	public static final int TERM = 10;
	public static final int LPAREN = 11;
	public static final int RPAREN = 12;
	public static final int DEFAULT = 0;
	public static final String tokenImage[] = {
		"<EOF>", "\" \"", "\"\\\\t\"", "\"\\\\r\"", "\"\\\\n\"", "\"&\"", "\"|\"", "\"!\"", "\"#\"", "\"+\"", 
		"<TERM>", "\"(\"", "\")\""
	};

}
