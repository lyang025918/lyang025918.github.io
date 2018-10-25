// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LoggerFactory.java

package org.apache.log4j.spi;

import org.apache.log4j.Logger;

public interface LoggerFactory
{

	public abstract Logger makeNewLoggerInstance(String s);
}
