// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SegmentMerger.java

package org.apache.lucene.index;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import org.apache.lucene.codecs.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.util.IOUtils;
import org.apache.lucene.util.InfoStream;

// Referenced classes of package org.apache.lucene.index:
//			MergeState, AtomicReaderContext, SegmentWriteState, SegmentReader, 
//			AtomicReader, FieldInfo, TypePromoter, ReaderSlice, 
//			MultiFields, PerDocWriteState, FieldInfos, Fields, 
//			SegmentInfo, IndexReader, DocValues

final class SegmentMerger
{

	private final Directory directory;
	private final int termIndexInterval;
	private final Codec codec;
	private final IOContext context;
	private final MergeState mergeState = new MergeState();
	private final FieldInfos.Builder fieldInfosBuilder;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/SegmentMerger.desiredAssertionStatus();

	SegmentMerger(SegmentInfo segmentInfo, InfoStream infoStream, Directory dir, int termIndexInterval, MergeState.CheckAbort checkAbort, FieldInfos.FieldNumbers fieldNumbers, IOContext context)
	{
		mergeState.segmentInfo = segmentInfo;
		mergeState.infoStream = infoStream;
		mergeState.readers = new ArrayList();
		mergeState.checkAbort = checkAbort;
		directory = dir;
		this.termIndexInterval = termIndexInterval;
		codec = segmentInfo.getCodec();
		this.context = context;
		fieldInfosBuilder = new FieldInfos.Builder(fieldNumbers);
	}

	final void add(IndexReader reader)
	{
		AtomicReader r;
		for (Iterator i$ = reader.leaves().iterator(); i$.hasNext(); mergeState.readers.add(r))
		{
			AtomicReaderContext ctx = (AtomicReaderContext)i$.next();
			r = ctx.reader();
		}

	}

	final void add(SegmentReader reader)
	{
		mergeState.readers.add(reader);
	}

	final MergeState merge()
		throws IOException
	{
		mergeState.segmentInfo.setDocCount(setDocMaps());
		mergeDocValuesAndNormsFieldInfos();
		setMatchingSegmentReaders();
		int numMerged = mergeFields();
		if (!$assertionsDisabled && numMerged != mergeState.segmentInfo.getDocCount())
			throw new AssertionError();
		SegmentWriteState segmentWriteState = new SegmentWriteState(mergeState.infoStream, directory, mergeState.segmentInfo, mergeState.fieldInfos, termIndexInterval, null, context);
		mergeTerms(segmentWriteState);
		mergePerDoc(segmentWriteState);
		if (mergeState.fieldInfos.hasNorms())
			mergeNorms(segmentWriteState);
		if (mergeState.fieldInfos.hasVectors())
		{
			numMerged = mergeVectors();
			if (!$assertionsDisabled && numMerged != mergeState.segmentInfo.getDocCount())
				throw new AssertionError();
		}
		FieldInfosWriter fieldInfosWriter = codec.fieldInfosFormat().getFieldInfosWriter();
		fieldInfosWriter.write(directory, mergeState.segmentInfo.name, mergeState.fieldInfos, context);
		return mergeState;
	}

	private void setMatchingSegmentReaders()
	{
		int numReaders = mergeState.readers.size();
		mergeState.matchingSegmentReaders = new SegmentReader[numReaders];
		for (int i = 0; i < numReaders; i++)
		{
			AtomicReader reader = (AtomicReader)mergeState.readers.get(i);
			if (!(reader instanceof SegmentReader))
				continue;
			SegmentReader segmentReader = (SegmentReader)reader;
			boolean same = true;
			FieldInfos segmentFieldInfos = segmentReader.getFieldInfos();
			Iterator i$ = segmentFieldInfos.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				FieldInfo fi = (FieldInfo)i$.next();
				FieldInfo other = mergeState.fieldInfos.fieldInfo(fi.number);
				if (other != null && other.name.equals(fi.name))
					continue;
				same = false;
				break;
			} while (true);
			if (same)
			{
				mergeState.matchingSegmentReaders[i] = segmentReader;
				mergeState.matchedCount++;
			}
		}

		if (mergeState.infoStream.isEnabled("SM"))
		{
			mergeState.infoStream.message("SM", (new StringBuilder()).append("merge store matchedCount=").append(mergeState.matchedCount).append(" vs ").append(mergeState.readers.size()).toString());
			if (mergeState.matchedCount != mergeState.readers.size())
				mergeState.infoStream.message("SM", (new StringBuilder()).append("").append(mergeState.readers.size() - mergeState.matchedCount).append(" non-bulk merges").toString());
		}
	}

	private TypePromoter mergeDocValuesType(TypePromoter previous, DocValues docValues)
	{
		TypePromoter incoming = TypePromoter.create(docValues.getType(), docValues.getValueSize());
		if (previous == null)
			previous = TypePromoter.getIdentityPromoter();
		return previous.promote(incoming);
	}

	public void mergeDocValuesAndNormsFieldInfos()
		throws IOException
	{
		Map docValuesTypes = new HashMap();
		Map normValuesTypes = new HashMap();
		for (Iterator i$ = mergeState.readers.iterator(); i$.hasNext();)
		{
			AtomicReader reader = (AtomicReader)i$.next();
			FieldInfos readerFieldInfos = reader.getFieldInfos();
			Iterator i$ = readerFieldInfos.iterator();
			while (i$.hasNext()) 
			{
				FieldInfo fi = (FieldInfo)i$.next();
				FieldInfo merged = fieldInfosBuilder.add(fi);
				if (fi.hasDocValues())
				{
					TypePromoter previous = (TypePromoter)docValuesTypes.get(merged);
					docValuesTypes.put(merged, mergeDocValuesType(previous, reader.docValues(fi.name)));
				}
				if (fi.hasNorms())
				{
					TypePromoter previous = (TypePromoter)normValuesTypes.get(merged);
					normValuesTypes.put(merged, mergeDocValuesType(previous, reader.normValues(fi.name)));
				}
			}
		}

		updatePromoted(normValuesTypes, true);
		updatePromoted(docValuesTypes, false);
		mergeState.fieldInfos = fieldInfosBuilder.finish();
	}

	protected void updatePromoted(Map infoAndPromoter, boolean norms)
	{
		Iterator i$ = infoAndPromoter.entrySet().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			java.util.Map.Entry e = (java.util.Map.Entry)i$.next();
			FieldInfo fi = (FieldInfo)e.getKey();
			TypePromoter promoter = (TypePromoter)e.getValue();
			if (promoter == null)
			{
				if (norms)
					fi.setNormValueType(null);
				else
					fi.setDocValuesType(null);
			} else
			{
				if (!$assertionsDisabled && promoter == TypePromoter.getIdentityPromoter())
					throw new AssertionError();
				if (norms)
				{
					if (fi.getNormType() != promoter.type() && !fi.omitsNorms())
						fi.setNormValueType(promoter.type());
				} else
				if (fi.getDocValuesType() != promoter.type())
					fi.setDocValuesType(promoter.type());
			}
		} while (true);
	}

	private int mergeFields()
		throws IOException
	{
		StoredFieldsWriter fieldsWriter = codec.storedFieldsFormat().fieldsWriter(directory, mergeState.segmentInfo, context);
		int i = fieldsWriter.merge(mergeState);
		fieldsWriter.close();
		return i;
		Exception exception;
		exception;
		fieldsWriter.close();
		throw exception;
	}

	private final int mergeVectors()
		throws IOException
	{
		TermVectorsWriter termVectorsWriter = codec.termVectorsFormat().vectorsWriter(directory, mergeState.segmentInfo, context);
		int i = termVectorsWriter.merge(mergeState);
		termVectorsWriter.close();
		return i;
		Exception exception;
		exception;
		termVectorsWriter.close();
		throw exception;
	}

	private int setDocMaps()
		throws IOException
	{
		int numReaders = mergeState.readers.size();
		mergeState.docMaps = new MergeState.DocMap[numReaders];
		mergeState.docBase = new int[numReaders];
		int docBase = 0;
		for (int i = 0; i < mergeState.readers.size(); i++)
		{
			AtomicReader reader = (AtomicReader)mergeState.readers.get(i);
			mergeState.docBase[i] = docBase;
			MergeState.DocMap docMap = MergeState.DocMap.build(reader);
			mergeState.docMaps[i] = docMap;
			docBase += docMap.numDocs();
		}

		return docBase;
	}

	private final void mergeTerms(SegmentWriteState segmentWriteState)
		throws IOException
	{
		List fields;
		List slices;
		FieldsConsumer consumer;
		boolean success;
		fields = new ArrayList();
		slices = new ArrayList();
		int docBase = 0;
		for (int readerIndex = 0; readerIndex < mergeState.readers.size(); readerIndex++)
		{
			AtomicReader reader = (AtomicReader)mergeState.readers.get(readerIndex);
			Fields f = reader.fields();
			int maxDoc = reader.maxDoc();
			if (f != null)
			{
				slices.add(new ReaderSlice(docBase, maxDoc, readerIndex));
				fields.add(f);
			}
			docBase += maxDoc;
		}

		consumer = codec.postingsFormat().fieldsConsumer(segmentWriteState);
		success = false;
		consumer.merge(mergeState, new MultiFields((Fields[])fields.toArray(Fields.EMPTY_ARRAY), (ReaderSlice[])slices.toArray(ReaderSlice.EMPTY_ARRAY)));
		success = true;
		if (success)
			IOUtils.close(new Closeable[] {
				consumer
			});
		else
			IOUtils.closeWhileHandlingException(new Closeable[] {
				consumer
			});
		break MISSING_BLOCK_LABEL_250;
		Exception exception;
		exception;
		if (success)
			IOUtils.close(new Closeable[] {
				consumer
			});
		else
			IOUtils.closeWhileHandlingException(new Closeable[] {
				consumer
			});
		throw exception;
	}

	private void mergePerDoc(SegmentWriteState segmentWriteState)
		throws IOException
	{
		PerDocConsumer docsConsumer;
		boolean success;
		docsConsumer = codec.docValuesFormat().docsConsumer(new PerDocWriteState(segmentWriteState));
		if (docsConsumer == null)
			return;
		success = false;
		docsConsumer.merge(mergeState);
		success = true;
		if (success)
			IOUtils.close(new Closeable[] {
				docsConsumer
			});
		else
			IOUtils.closeWhileHandlingException(new Closeable[] {
				docsConsumer
			});
		break MISSING_BLOCK_LABEL_102;
		Exception exception;
		exception;
		if (success)
			IOUtils.close(new Closeable[] {
				docsConsumer
			});
		else
			IOUtils.closeWhileHandlingException(new Closeable[] {
				docsConsumer
			});
		throw exception;
	}

	private void mergeNorms(SegmentWriteState segmentWriteState)
		throws IOException
	{
		PerDocConsumer docsConsumer;
		boolean success;
		docsConsumer = codec.normsFormat().docsConsumer(new PerDocWriteState(segmentWriteState));
		if (docsConsumer == null)
			return;
		success = false;
		docsConsumer.merge(mergeState);
		success = true;
		if (success)
			IOUtils.close(new Closeable[] {
				docsConsumer
			});
		else
			IOUtils.closeWhileHandlingException(new Closeable[] {
				docsConsumer
			});
		break MISSING_BLOCK_LABEL_102;
		Exception exception;
		exception;
		if (success)
			IOUtils.close(new Closeable[] {
				docsConsumer
			});
		else
			IOUtils.closeWhileHandlingException(new Closeable[] {
				docsConsumer
			});
		throw exception;
	}

}
