// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermInfosReaderIndex.java

package org.apache.lucene.codecs.lucene3x;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.Term;
import org.apache.lucene.util.*;
import org.apache.lucene.util.packed.GrowableWriter;
import org.apache.lucene.util.packed.PackedInts;

// Referenced classes of package org.apache.lucene.codecs.lucene3x:
//			TermInfo, SegmentTermEnum

/**
 * @deprecated Class TermInfosReaderIndex is deprecated
 */

class TermInfosReaderIndex
{

	private static final int MAX_PAGE_BITS = 18;
	private Term fields[];
	private int totalIndexInterval;
	private Comparator comparator;
	private final org.apache.lucene.util.PagedBytes.PagedBytesDataInput dataInput;
	private final org.apache.lucene.util.packed.PackedInts.Reader indexToDataOffset;
	private final int indexSize;
	private final int skipInterval;

	TermInfosReaderIndex(SegmentTermEnum indexEnum, int indexDivisor, long tiiFileLength, int totalIndexInterval)
		throws IOException
	{
		comparator = BytesRef.getUTF8SortedAsUTF16Comparator();
		this.totalIndexInterval = totalIndexInterval;
		indexSize = 1 + ((int)indexEnum.size - 1) / indexDivisor;
		skipInterval = indexEnum.skipInterval;
		long initialSize = (long)((double)tiiFileLength * 1.5D) / (long)indexDivisor;
		PagedBytes dataPagedBytes = new PagedBytes(estimatePageBits(initialSize));
		org.apache.lucene.util.PagedBytes.PagedBytesDataOutput dataOutput = dataPagedBytes.getDataOutput();
		int bitEstimate = 1 + MathUtil.log(tiiFileLength, 2);
		GrowableWriter indexToTerms = new GrowableWriter(bitEstimate, indexSize, 0.2F);
		String currentField = null;
		List fieldStrs = new ArrayList();
		int fieldCounter = -1;
		for (int i = 0; indexEnum.next(); i++)
		{
			Term term = indexEnum.term();
			if (currentField == null || !currentField.equals(term.field()))
			{
				currentField = term.field();
				fieldStrs.add(currentField);
				fieldCounter++;
			}
			TermInfo termInfo = indexEnum.termInfo();
			indexToTerms.set(i, dataOutput.getPosition());
			dataOutput.writeVInt(fieldCounter);
			dataOutput.writeString(term.text());
			dataOutput.writeVInt(termInfo.docFreq);
			if (termInfo.docFreq >= skipInterval)
				dataOutput.writeVInt(termInfo.skipOffset);
			dataOutput.writeVLong(termInfo.freqPointer);
			dataOutput.writeVLong(termInfo.proxPointer);
			dataOutput.writeVLong(indexEnum.indexPointer);
			for (int j = 1; j < indexDivisor && indexEnum.next(); j++);
		}

		fields = new Term[fieldStrs.size()];
		for (int i = 0; i < fields.length; i++)
			fields[i] = new Term((String)fieldStrs.get(i));

		dataPagedBytes.freeze(true);
		dataInput = dataPagedBytes.getDataInput();
		indexToDataOffset = indexToTerms.getMutable();
	}

	private static int estimatePageBits(long estSize)
	{
		return Math.max(Math.min(64 - BitUtil.nlz(estSize), 18), 4);
	}

	void seekEnum(SegmentTermEnum enumerator, int indexOffset)
		throws IOException
	{
		org.apache.lucene.util.PagedBytes.PagedBytesDataInput input = dataInput.clone();
		input.setPosition(indexToDataOffset.get(indexOffset));
		int fieldId = input.readVInt();
		Term field = fields[fieldId];
		Term term = new Term(field.field(), input.readString());
		TermInfo termInfo = new TermInfo();
		termInfo.docFreq = input.readVInt();
		if (termInfo.docFreq >= skipInterval)
			termInfo.skipOffset = input.readVInt();
		else
			termInfo.skipOffset = 0;
		termInfo.freqPointer = input.readVLong();
		termInfo.proxPointer = input.readVLong();
		long pointer = input.readVLong();
		enumerator.seek(pointer, (long)indexOffset * (long)totalIndexInterval - 1L, term, termInfo);
	}

	int getIndexOffset(Term term)
		throws IOException
	{
		int lo = 0;
		int hi = indexSize - 1;
		org.apache.lucene.util.PagedBytes.PagedBytesDataInput input = dataInput.clone();
		BytesRef scratch = new BytesRef();
		while (hi >= lo) 
		{
			int mid = lo + hi >>> 1;
			int delta = compareTo(term, mid, input, scratch);
			if (delta < 0)
				hi = mid - 1;
			else
			if (delta > 0)
				lo = mid + 1;
			else
				return mid;
		}
		return hi;
	}

	Term getTerm(int termIndex)
		throws IOException
	{
		org.apache.lucene.util.PagedBytes.PagedBytesDataInput input = dataInput.clone();
		input.setPosition(indexToDataOffset.get(termIndex));
		int fieldId = input.readVInt();
		Term field = fields[fieldId];
		return new Term(field.field(), input.readString());
	}

	int length()
	{
		return indexSize;
	}

	int compareTo(Term term, int termIndex)
		throws IOException
	{
		return compareTo(term, termIndex, dataInput.clone(), new BytesRef());
	}

	private int compareTo(Term term, int termIndex, org.apache.lucene.util.PagedBytes.PagedBytesDataInput input, BytesRef reuse)
		throws IOException
	{
		int c = compareField(term, termIndex, input);
		if (c == 0)
		{
			reuse.length = input.readVInt();
			reuse.grow(reuse.length);
			input.readBytes(reuse.bytes, 0, reuse.length);
			return comparator.compare(term.bytes(), reuse);
		} else
		{
			return c;
		}
	}

	private int compareField(Term term, int termIndex, org.apache.lucene.util.PagedBytes.PagedBytesDataInput input)
		throws IOException
	{
		input.setPosition(indexToDataOffset.get(termIndex));
		return term.field().compareTo(fields[input.readVInt()].field());
	}
}
