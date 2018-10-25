// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Terms.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.Comparator;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.automaton.CompiledAutomaton;

// Referenced classes of package org.apache.lucene.index:
//			AutomatonTermsEnum, TermsEnum

public abstract class Terms
{

	public static final Terms EMPTY_ARRAY[] = new Terms[0];

	protected Terms()
	{
	}

	public abstract TermsEnum iterator(TermsEnum termsenum)
		throws IOException;

	public TermsEnum intersect(CompiledAutomaton compiled, BytesRef startTerm)
		throws IOException
	{
		if (compiled.type != org.apache.lucene.util.automaton.CompiledAutomaton.AUTOMATON_TYPE.NORMAL)
			throw new IllegalArgumentException("please use CompiledAutomaton.getTermsEnum instead");
		if (startTerm == null)
			return new AutomatonTermsEnum(iterator(null), compiled);
		else
			return new AutomatonTermsEnum(compiled, startTerm) {

				final BytesRef val$startTerm;
				final Terms this$0;

				protected BytesRef nextSeekTerm(BytesRef term)
					throws IOException
				{
					if (term == null)
						term = startTerm;
					return super.nextSeekTerm(term);
				}

			
			{
				this$0 = Terms.this;
				startTerm = bytesref;
				super(x0, x1);
			}
			};
	}

	public abstract Comparator getComparator()
		throws IOException;

	public abstract long size()
		throws IOException;

	public abstract long getSumTotalTermFreq()
		throws IOException;

	public abstract long getSumDocFreq()
		throws IOException;

	public abstract int getDocCount()
		throws IOException;

	public abstract boolean hasOffsets();

	public abstract boolean hasPositions();

	public abstract boolean hasPayloads();

}
