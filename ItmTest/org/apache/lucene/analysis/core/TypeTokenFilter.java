// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TypeTokenFilter.java

package org.apache.lucene.analysis.core;

import java.util.Set;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.analysis.util.FilteringTokenFilter;

public final class TypeTokenFilter extends FilteringTokenFilter
{

	private final Set stopTypes;
	private final TypeAttribute typeAttribute;
	private final boolean useWhiteList;

	public TypeTokenFilter(boolean enablePositionIncrements, TokenStream input, Set stopTypes, boolean useWhiteList)
	{
		super(enablePositionIncrements, input);
		typeAttribute = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
		this.stopTypes = stopTypes;
		this.useWhiteList = useWhiteList;
	}

	public TypeTokenFilter(boolean enablePositionIncrements, TokenStream input, Set stopTypes)
	{
		this(enablePositionIncrements, input, stopTypes, false);
	}

	protected boolean accept()
	{
		return useWhiteList == stopTypes.contains(typeAttribute.type());
	}
}
