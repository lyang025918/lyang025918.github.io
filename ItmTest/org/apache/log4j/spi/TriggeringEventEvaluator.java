// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TriggeringEventEvaluator.java

package org.apache.log4j.spi;


// Referenced classes of package org.apache.log4j.spi:
//			LoggingEvent

public interface TriggeringEventEvaluator
{

	public abstract boolean isTriggeringEvent(LoggingEvent loggingevent);
}
