// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FreqProxTermsWriter.java

package org.apache.lucene.index;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import org.apache.lucene.codecs.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.index:
//			TermsHashConsumer, TermsHashConsumerPerField, FreqProxTermsWriterPerField, SegmentWriteState, 
//			TermsHash, FieldInfo, TermsHashPerField, SegmentInfo

final class FreqProxTermsWriter extends TermsHashConsumer
{

	BytesRef payload;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/FreqProxTermsWriter.desiredAssertionStatus();

	FreqProxTermsWriter()
	{
	}

	void abort()
	{
	}

	public void flush(Map fieldsToFlush, SegmentWriteState state)
		throws IOException
	{
		List allFields;
		int numAllFields;
		FieldsConsumer consumer;
		boolean success;
		allFields = new ArrayList();
		Iterator i$ = fieldsToFlush.values().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			TermsHashConsumerPerField f = (TermsHashConsumerPerField)i$.next();
			FreqProxTermsWriterPerField perField = (FreqProxTermsWriterPerField)f;
			if (perField.termsHashPerField.bytesHash.size() > 0)
				allFields.add(perField);
		} while (true);
		numAllFields = allFields.size();
		CollectionUtil.quickSort(allFields);
		consumer = state.segmentInfo.getCodec().postingsFormat().fieldsConsumer(state);
		success = false;
		TermsHash termsHash = null;
		for (int fieldNumber = 0; fieldNumber < numAllFields; fieldNumber++)
		{
			FieldInfo fieldInfo = ((FreqProxTermsWriterPerField)allFields.get(fieldNumber)).fieldInfo;
			FreqProxTermsWriterPerField fieldWriter = (FreqProxTermsWriterPerField)allFields.get(fieldNumber);
			fieldWriter.flush(fieldInfo.name, consumer, state);
			TermsHashPerField perField = fieldWriter.termsHashPerField;
			if (!$assertionsDisabled && termsHash != null && termsHash != perField.termsHash)
				throw new AssertionError();
			termsHash = perField.termsHash;
			int numPostings = perField.bytesHash.size();
			perField.reset();
			perField.shrinkHash(numPostings);
			fieldWriter.reset();
		}

		if (termsHash != null)
			termsHash.reset();
		success = true;
		if (success)
			IOUtils.close(new Closeable[] {
				consumer
			});
		else
			IOUtils.closeWhileHandlingException(new Closeable[] {
				consumer
			});
		break MISSING_BLOCK_LABEL_323;
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

	public TermsHashConsumerPerField addField(TermsHashPerField termsHashPerField, FieldInfo fieldInfo)
	{
		return new FreqProxTermsWriterPerField(termsHashPerField, this, fieldInfo);
	}

	void finishDocument(TermsHash termshash)
	{
	}

	void startDocument()
	{
	}

}
