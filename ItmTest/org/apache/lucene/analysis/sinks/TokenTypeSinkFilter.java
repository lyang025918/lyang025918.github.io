// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TokenTypeSinkFilter.java

package org.apache.lucene.analysis.sinks;

import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.AttributeSource;

// Referenced classes of package org.apache.lucene.analysis.sinks:
//			TeeSinkTokenFilter

public class TokenTypeSinkFilter extends TeeSinkTokenFilter.SinkFilter
{

	private String typeToMatch;
	private TypeAttribute typeAtt;

	public TokenTypeSinkFilter(String typeToMatch)
	{
		this.typeToMatch = typeToMatch;
	}

	public boolean accept(AttributeSource source)
	{
		if (typeAtt == null)
			typeAtt = (TypeAttribute)source.addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
		return typeToMatch.equals(typeAtt.type());
	}
}
