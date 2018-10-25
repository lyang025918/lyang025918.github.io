// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LogRecordFilter.java

package org.apache.log4j.lf5;


// Referenced classes of package org.apache.log4j.lf5:
//			LogRecord

public interface LogRecordFilter
{

	public abstract boolean passes(LogRecord logrecord);
}
