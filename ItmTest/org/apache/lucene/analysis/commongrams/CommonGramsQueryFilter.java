// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CommonGramsQueryFilter.java

package org.apache.lucene.analysis.commongrams;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.AttributeSource;

// Referenced classes of package org.apache.lucene.analysis.commongrams:
//			CommonGramsFilter

public final class CommonGramsQueryFilter extends TokenFilter
{

	private final TypeAttribute typeAttribute = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
	private final PositionIncrementAttribute posIncAttribute = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
	private org.apache.lucene.util.AttributeSource.State previous;
	private String previousType;
	private boolean exhausted;

	public CommonGramsQueryFilter(CommonGramsFilter input)
	{
		super(input);
	}

	public void reset()
		throws IOException
	{
		super.reset();
		previous = null;
		previousType = null;
		exhausted = false;
	}

	public boolean incrementToken()
		throws IOException
	{
		while (!exhausted && input.incrementToken()) 
		{
			org.apache.lucene.util.AttributeSource.State current = captureState();
			if (previous != null && !isGramType())
			{
				restoreState(previous);
				previous = current;
				previousType = typeAttribute.type();
				if (isGramType())
					posIncAttribute.setPositionIncrement(1);
				return true;
			}
			previous = current;
		}
		exhausted = true;
		if (previous == null || "gram".equals(previousType))
			return false;
		restoreState(previous);
		previous = null;
		if (isGramType())
			posIncAttribute.setPositionIncrement(1);
		return true;
	}

	public boolean isGramType()
	{
		return "gram".equals(typeAttribute.type());
	}
}
