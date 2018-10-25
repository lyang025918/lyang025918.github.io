// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PayloadEncoder.java

package org.apache.lucene.analysis.payloads;

import org.apache.lucene.util.BytesRef;

public interface PayloadEncoder
{

	public abstract BytesRef encode(char ac[]);

	public abstract BytesRef encode(char ac[], int i, int j);
}
