// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ErrorCode.java

package org.apache.log4j.spi;


public interface ErrorCode
{

	public static final int GENERIC_FAILURE = 0;
	public static final int WRITE_FAILURE = 1;
	public static final int FLUSH_FAILURE = 2;
	public static final int CLOSE_FAILURE = 3;
	public static final int FILE_OPEN_FAILURE = 4;
	public static final int MISSING_LAYOUT = 5;
	public static final int ADDRESS_PARSE_FAILURE = 6;
}
