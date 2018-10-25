// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PrefixAndSuffixAwareTokenFilter.java

package org.apache.lucene.analysis.miscellaneous;

import java.io.IOException;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;

// Referenced classes of package org.apache.lucene.analysis.miscellaneous:
//			PrefixAwareTokenFilter

public class PrefixAndSuffixAwareTokenFilter extends TokenStream
{

	private PrefixAwareTokenFilter suffix;

	public PrefixAndSuffixAwareTokenFilter(TokenStream prefix, TokenStream input, TokenStream suffix)
	{
		super(suffix);
		prefix = new PrefixAwareTokenFilter(prefix, input) {

			final PrefixAndSuffixAwareTokenFilter this$0;

			public Token updateSuffixToken(Token suffixToken, Token lastInputToken)
			{
				return updateInputToken(suffixToken, lastInputToken);
			}

			
			{
				this$0 = PrefixAndSuffixAwareTokenFilter.this;
				super(x0, x1);
			}
		};
		this.suffix = new PrefixAwareTokenFilter(prefix, suffix) {

			final PrefixAndSuffixAwareTokenFilter this$0;

			public Token updateSuffixToken(Token suffixToken, Token lastInputToken)
			{
				return PrefixAndSuffixAwareTokenFilter.this.updateSuffixToken(suffixToken, lastInputToken);
			}

			
			{
				this$0 = PrefixAndSuffixAwareTokenFilter.this;
				super(x0, x1);
			}
		};
	}

	public Token updateInputToken(Token inputToken, Token lastPrefixToken)
	{
		inputToken.setOffset(lastPrefixToken.endOffset() + inputToken.startOffset(), lastPrefixToken.endOffset() + inputToken.endOffset());
		return inputToken;
	}

	public Token updateSuffixToken(Token suffixToken, Token lastInputToken)
	{
		suffixToken.setOffset(lastInputToken.endOffset() + suffixToken.startOffset(), lastInputToken.endOffset() + suffixToken.endOffset());
		return suffixToken;
	}

	public final boolean incrementToken()
		throws IOException
	{
		return suffix.incrementToken();
	}

	public void reset()
		throws IOException
	{
		suffix.reset();
	}

	public void close()
		throws IOException
	{
		suffix.close();
	}

	public void end()
		throws IOException
	{
		suffix.end();
	}
}
