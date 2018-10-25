// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HierarchyEventListener.java

package org.apache.log4j.spi;

import org.apache.log4j.Appender;
import org.apache.log4j.Category;

public interface HierarchyEventListener
{

	public abstract void addAppenderEvent(Category category, Appender appender);

	public abstract void removeAppenderEvent(Category category, Appender appender);
}
