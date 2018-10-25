// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SegmentTermDocs.java

package org.apache.lucene.codecs.lucene3x;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.codecs.lucene3x:
//			Lucene3xSkipListReader, TermInfo, TermInfosReader, SegmentTermEnum

/**
 * @deprecated Class SegmentTermDocs is deprecated
 */

class SegmentTermDocs
{

	private final FieldInfos fieldInfos;
	private final TermInfosReader tis;
	protected Bits liveDocs;
	protected IndexInput freqStream;
	protected int count;
	protected int df;
	int doc;
	int freq;
	private int skipInterval;
	private int maxSkipLevels;
	private Lucene3xSkipListReader skipListReader;
	private long freqBasePointer;
	private long proxBasePointer;
	private long skipPointer;
	private boolean haveSkipped;
	protected boolean currentFieldStoresPayloads;
	protected org.apache.lucene.index.FieldInfo.IndexOptions indexOptions;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene3x/SegmentTermDocs.desiredAssertionStatus();

	public SegmentTermDocs(IndexInput freqStream, TermInfosReader tis, FieldInfos fieldInfos)
	{
		doc = 0;
		this.freqStream = freqStream.clone();
		this.tis = tis;
		this.fieldInfos = fieldInfos;
		skipInterval = tis.getSkipInterval();
		maxSkipLevels = tis.getMaxSkipLevels();
	}

	public void seek(Term term)
		throws IOException
	{
		TermInfo ti = tis.get(term);
		seek(ti, term);
	}

	public void setLiveDocs(Bits liveDocs)
	{
		this.liveDocs = liveDocs;
	}

	public void seek(SegmentTermEnum segmentTermEnum)
		throws IOException
	{
		TermInfo ti;
		Term term;
		if (segmentTermEnum.fieldInfos == fieldInfos)
		{
			term = segmentTermEnum.term();
			ti = segmentTermEnum.termInfo();
		} else
		{
			term = segmentTermEnum.term();
			ti = tis.get(term);
		}
		seek(ti, term);
	}

	void seek(TermInfo ti, Term term)
		throws IOException
	{
		count = 0;
		FieldInfo fi = fieldInfos.fieldInfo(term.field());
		indexOptions = fi == null ? org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS : fi.getIndexOptions();
		currentFieldStoresPayloads = fi == null ? false : fi.hasPayloads();
		if (ti == null)
		{
			df = 0;
		} else
		{
			df = ti.docFreq;
			doc = 0;
			freqBasePointer = ti.freqPointer;
			proxBasePointer = ti.proxPointer;
			skipPointer = freqBasePointer + (long)ti.skipOffset;
			freqStream.seek(freqBasePointer);
			haveSkipped = false;
		}
	}

	public void close()
		throws IOException
	{
		freqStream.close();
		if (skipListReader != null)
			skipListReader.close();
	}

	public final int doc()
	{
		return doc;
	}

	public final int freq()
	{
		return freq;
	}

	protected void skippingDoc()
		throws IOException
	{
	}

	public boolean next()
		throws IOException
	{
		do
		{
			if (count == df)
				return false;
			int docCode = freqStream.readVInt();
			if (indexOptions == org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_ONLY)
			{
				doc += docCode;
			} else
			{
				doc += docCode >>> 1;
				if ((docCode & 1) != 0)
				{
					freq = 1;
				} else
				{
					freq = freqStream.readVInt();
					if (!$assertionsDisabled && freq == 1)
						throw new AssertionError();
				}
			}
			count++;
			if (liveDocs != null && !liveDocs.get(doc))
				skippingDoc();
			else
				return true;
		} while (true);
	}

	public int read(int docs[], int freqs[])
		throws IOException
	{
		int length = docs.length;
		if (indexOptions == org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_ONLY)
			return readNoTf(docs, freqs, length);
		int i = 0;
		do
		{
			if (i >= length || count >= df)
				break;
			int docCode = freqStream.readVInt();
			doc += docCode >>> 1;
			if ((docCode & 1) != 0)
				freq = 1;
			else
				freq = freqStream.readVInt();
			count++;
			if (liveDocs == null || liveDocs.get(doc))
			{
				docs[i] = doc;
				freqs[i] = freq;
				i++;
			}
		} while (true);
		return i;
	}

	private final int readNoTf(int docs[], int freqs[], int length)
		throws IOException
	{
		int i = 0;
		do
		{
			if (i >= length || count >= df)
				break;
			doc += freqStream.readVInt();
			count++;
			if (liveDocs == null || liveDocs.get(doc))
			{
				docs[i] = doc;
				freqs[i] = 1;
				i++;
			}
		} while (true);
		return i;
	}

	protected void skipProx(long l, int i)
		throws IOException
	{
	}

	public boolean skipTo(int target)
		throws IOException
	{
		if (target - skipInterval >= doc && df >= skipInterval)
		{
			if (skipListReader == null)
				skipListReader = new Lucene3xSkipListReader(freqStream.clone(), maxSkipLevels, skipInterval);
			if (!haveSkipped)
			{
				skipListReader.init(skipPointer, freqBasePointer, proxBasePointer, df, currentFieldStoresPayloads);
				haveSkipped = true;
			}
			int newCount = skipListReader.skipTo(target);
			if (newCount > count)
			{
				freqStream.seek(skipListReader.getFreqPointer());
				skipProx(skipListReader.getProxPointer(), skipListReader.getPayloadLength());
				doc = skipListReader.getDoc();
				count = newCount;
			}
		}
		do
			if (!next())
				return false;
		while (target > doc);
		return true;
	}

}
