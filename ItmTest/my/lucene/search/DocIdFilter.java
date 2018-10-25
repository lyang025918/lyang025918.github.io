// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocIdFilter.java

package my.lucene.search;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.DocIdBitSet;

public class DocIdFilter extends Filter
{

	private String fieldName;
	private Set docIds;
	private Set docInnerIds;

	DocIdFilter(String fieldName, Set docIds)
	{
		this.docIds = null;
		docInnerIds = null;
		this.fieldName = fieldName;
		this.docIds = docIds;
	}

	DocIdFilter(Set docIds)
	{
		this.docIds = null;
		docInnerIds = null;
		docInnerIds = docIds;
	}

	public DocIdSet getDocIdSet(AtomicReaderContext context, Bits acceptDocs)
		throws IOException
	{
		BitSet bits;
label0:
		{
			AtomicReader reader = context.reader();
			bits = new BitSet(reader.maxDoc());
			bits.set(0, bits.size() - 1, false);
			if (docInnerIds != null)
			{
				for (Iterator it = docInnerIds.iterator(); it.hasNext(); bits.set(((Integer)it.next()).intValue(), true));
				break label0;
			}
			if (docIds != null)
			{
				Iterator it = docIds.iterator();
				do
				{
					DocsEnum docsEnum;
					do
					{
						if (!it.hasNext())
							break label0;
						String docId = (String)it.next();
						Term term = new Term(fieldName, docId);
						docsEnum = reader.termDocsEnum(term);
					} while (docsEnum == null);
					do
					{
						int docState = docsEnum.nextDoc();
						if (0x7fffffff == docState)
							break;
						int id = docsEnum.docID();
						int freq = docsEnum.freq();
						bits.set(id, true);
					} while (true);
				} while (true);
			} else
			{
				throw new IOException("Error: you must assign a docInnerIds or a docIds !");
			}
		}
		return new DocIdBitSet(bits);
	}
}
