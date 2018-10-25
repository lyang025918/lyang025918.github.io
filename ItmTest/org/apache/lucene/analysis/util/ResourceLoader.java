// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ResourceLoader.java

package org.apache.lucene.analysis.util;

import java.io.IOException;
import java.io.InputStream;

public interface ResourceLoader
{

	public abstract InputStream openResource(String s)
		throws IOException;

	public abstract Object newInstance(String s, Class class1);
}
