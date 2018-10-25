// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CollationKeyFilter.java

package org.apache.lucene.collation;

import java.io.IOException;
import java.text.CollationKey;
import java.text.Collator;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.IndexableBinaryStringTools;

/**
 * @deprecated Class CollationKeyFilter is deprecated
 */

public final class CollationKeyFilter extends TokenFilter
{

	private final Collator collator;
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);

	public CollationKeyFilter(TokenStream input, Collator collator)
	{
		super(input);
		this.collator = (Collator)collator.clone();
	}

	public boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			byte collationKey[] = collator.getCollationKey(termAtt.toString()).toByteArray();
			int encodedLength = IndexableBinaryStringTools.getEncodedLength(collationKey, 0, collationKey.length);
			termAtt.resizeBuffer(encodedLength);
			termAtt.setLength(encodedLength);
			IndexableBinaryStringTools.encode(collationKey, 0, collationKey.length, termAtt.buffer(), 0, encodedLength);
			return true;
		} else
		{
			return false;
		}
	}
}
