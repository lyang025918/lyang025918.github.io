// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermSpans.java

package org.apache.lucene.search.spans;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import org.apache.lucene.index.DocsAndPositionsEnum;
import org.apache.lucene.index.Term;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.search.spans:
//			Spans

public class TermSpans extends Spans
{
	private static final class EmptyTermSpans extends TermSpans
	{

		public boolean next()
		{
			return false;
		}

		public boolean skipTo(int target)
		{
			return false;
		}

		public int doc()
		{
			return 0x7fffffff;
		}

		public int start()
		{
			return -1;
		}

		public int end()
		{
			return -1;
		}

		public Collection getPayload()
		{
			return null;
		}

		public boolean isPayloadAvailable()
		{
			return false;
		}

		private EmptyTermSpans()
		{
		}

	}


	protected final DocsAndPositionsEnum postings;
	protected final Term term;
	protected int doc;
	protected int freq;
	protected int count;
	protected int position;
	protected boolean readPayload;
	public static final TermSpans EMPTY_TERM_SPANS = new EmptyTermSpans();

	public TermSpans(DocsAndPositionsEnum postings, Term term)
	{
		this.postings = postings;
		this.term = term;
		doc = -1;
	}

	TermSpans()
	{
		term = null;
		postings = null;
	}

	public boolean next()
		throws IOException
	{
		if (count == freq)
		{
			if (postings == null)
				return false;
			doc = postings.nextDoc();
			if (doc == 0x7fffffff)
				return false;
			freq = postings.freq();
			count = 0;
		}
		position = postings.nextPosition();
		count++;
		readPayload = false;
		return true;
	}

	public boolean skipTo(int target)
		throws IOException
	{
		doc = postings.advance(target);
		if (doc == 0x7fffffff)
		{
			return false;
		} else
		{
			freq = postings.freq();
			count = 0;
			position = postings.nextPosition();
			count++;
			readPayload = false;
			return true;
		}
	}

	public int doc()
	{
		return doc;
	}

	public int start()
	{
		return position;
	}

	public int end()
	{
		return position + 1;
	}

	public Collection getPayload()
		throws IOException
	{
		BytesRef payload = postings.getPayload();
		readPayload = true;
		byte bytes[];
		if (payload != null)
		{
			bytes = new byte[payload.length];
			System.arraycopy(payload.bytes, payload.offset, bytes, 0, payload.length);
		} else
		{
			bytes = null;
		}
		return Collections.singletonList(bytes);
	}

	public boolean isPayloadAvailable()
		throws IOException
	{
		return !readPayload && postings.getPayload() != null;
	}

	public String toString()
	{
		return (new StringBuilder()).append("spans(").append(term.toString()).append(")@").append(doc != -1 ? doc != 0x7fffffff ? (new StringBuilder()).append(doc).append("-").append(position).toString() : "END" : "START").toString();
	}

	public DocsAndPositionsEnum getPostings()
	{
		return postings;
	}

	public Term getTerm()
	{
		return term;
	}

}
