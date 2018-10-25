// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Configuration.java

package org.wltea.analyzer.cfg;

import java.util.List;

public interface Configuration
{

	public abstract boolean useSmart();

	public abstract void setUseSmart(boolean flag);

	public abstract String getMainDictionary();

	public abstract String getQuantifierDicionary();

	public abstract List getExtDictionarys();

	public abstract List getExtStopWordDictionarys();
}
