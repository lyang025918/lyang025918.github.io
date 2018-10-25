// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ISegmenter.java

package org.wltea.analyzer.core;


// Referenced classes of package org.wltea.analyzer.core:
//			AnalyzeContext

interface ISegmenter
{

	public abstract void analyze(AnalyzeContext analyzecontext);

	public abstract void reset();
}
