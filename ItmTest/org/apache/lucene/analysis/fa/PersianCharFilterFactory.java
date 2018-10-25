// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PersianCharFilterFactory.java

package org.apache.lucene.analysis.fa;

import java.io.Reader;
import org.apache.lucene.analysis.CharFilter;
import org.apache.lucene.analysis.util.*;

// Referenced classes of package org.apache.lucene.analysis.fa:
//			PersianCharFilter

public class PersianCharFilterFactory extends CharFilterFactory
	implements MultiTermAwareComponent
{

	public PersianCharFilterFactory()
	{
	}

	public CharFilter create(Reader input)
	{
		return new PersianCharFilter(input);
	}

	public AbstractAnalysisFactory getMultiTermComponent()
	{
		return this;
	}

	public volatile Reader create(Reader x0)
	{
		return create(x0);
	}
}
