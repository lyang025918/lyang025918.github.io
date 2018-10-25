// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermsEnum.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.Comparator;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.index:
//			TermState, DocsEnum, DocsAndPositionsEnum

public abstract class TermsEnum
	implements BytesRefIterator
{
	public static final class SeekStatus extends Enum
	{

		public static final SeekStatus END;
		public static final SeekStatus FOUND;
		public static final SeekStatus NOT_FOUND;
		private static final SeekStatus $VALUES[];

		public static SeekStatus[] values()
		{
			return (SeekStatus[])$VALUES.clone();
		}

		public static SeekStatus valueOf(String name)
		{
			return (SeekStatus)Enum.valueOf(org/apache/lucene/index/TermsEnum$SeekStatus, name);
		}

		static 
		{
			END = new SeekStatus("END", 0);
			FOUND = new SeekStatus("FOUND", 1);
			NOT_FOUND = new SeekStatus("NOT_FOUND", 2);
			$VALUES = (new SeekStatus[] {
				END, FOUND, NOT_FOUND
			});
		}

		private SeekStatus(String s, int i)
		{
			super(s, i);
		}
	}


	private AttributeSource atts;
	public static final TermsEnum EMPTY = new TermsEnum() {

		public SeekStatus seekCeil(BytesRef term, boolean useCache)
		{
			return SeekStatus.END;
		}

		public void seekExact(long l)
		{
		}

		public BytesRef term()
		{
			throw new IllegalStateException("this method should never be called");
		}

		public Comparator getComparator()
		{
			return null;
		}

		public int docFreq()
		{
			throw new IllegalStateException("this method should never be called");
		}

		public long totalTermFreq()
		{
			throw new IllegalStateException("this method should never be called");
		}

		public long ord()
		{
			throw new IllegalStateException("this method should never be called");
		}

		public DocsEnum docs(Bits liveDocs, DocsEnum reuse, int flags)
		{
			throw new IllegalStateException("this method should never be called");
		}

		public DocsAndPositionsEnum docsAndPositions(Bits liveDocs, DocsAndPositionsEnum reuse, int flags)
		{
			throw new IllegalStateException("this method should never be called");
		}

		public BytesRef next()
		{
			return null;
		}

		public synchronized AttributeSource attributes()
		{
			return attributes();
		}

		public TermState termState()
		{
			throw new IllegalStateException("this method should never be called");
		}

		public void seekExact(BytesRef term, TermState state)
		{
			throw new IllegalStateException("this method should never be called");
		}

	};

	protected TermsEnum()
	{
		atts = null;
	}

	public AttributeSource attributes()
	{
		if (atts == null)
			atts = new AttributeSource();
		return atts;
	}

	public boolean seekExact(BytesRef text, boolean useCache)
		throws IOException
	{
		return seekCeil(text, useCache) == SeekStatus.FOUND;
	}

	public abstract SeekStatus seekCeil(BytesRef bytesref, boolean flag)
		throws IOException;

	public final SeekStatus seekCeil(BytesRef text)
		throws IOException
	{
		return seekCeil(text, true);
	}

	public abstract void seekExact(long l)
		throws IOException;

	public void seekExact(BytesRef term, TermState state)
		throws IOException
	{
		if (!seekExact(term, true))
			throw new IllegalArgumentException((new StringBuilder()).append("term=").append(term).append(" does not exist").toString());
		else
			return;
	}

	public abstract BytesRef term()
		throws IOException;

	public abstract long ord()
		throws IOException;

	public abstract int docFreq()
		throws IOException;

	public abstract long totalTermFreq()
		throws IOException;

	public final DocsEnum docs(Bits liveDocs, DocsEnum reuse)
		throws IOException
	{
		return docs(liveDocs, reuse, 1);
	}

	public abstract DocsEnum docs(Bits bits, DocsEnum docsenum, int i)
		throws IOException;

	public final DocsAndPositionsEnum docsAndPositions(Bits liveDocs, DocsAndPositionsEnum reuse)
		throws IOException
	{
		return docsAndPositions(liveDocs, reuse, 3);
	}

	public abstract DocsAndPositionsEnum docsAndPositions(Bits bits, DocsAndPositionsEnum docsandpositionsenum, int i)
		throws IOException;

	public TermState termState()
		throws IOException
	{
		return new TermState() {

			final TermsEnum this$0;

			public void copyFrom(TermState termstate)
			{
			}

			
			{
				this$0 = TermsEnum.this;
				super();
			}
		};
	}

}
