// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocInverterPerField.java

package org.apache.lucene.index;

import java.io.Closeable;
import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.IOUtils;

// Referenced classes of package org.apache.lucene.index:
//			DocFieldConsumerPerField, FieldInvertState, IndexableField, IndexableFieldType, 
//			DocInverter, FieldInfo, InvertedDocConsumer, InvertedDocEndConsumer, 
//			InvertedDocConsumerPerField, InvertedDocEndConsumerPerField, DocumentsWriterPerThread

final class DocInverterPerField extends DocFieldConsumerPerField
{

	final FieldInfo fieldInfo;
	final InvertedDocConsumerPerField consumer;
	final InvertedDocEndConsumerPerField endConsumer;
	final DocumentsWriterPerThread.DocState docState;
	final FieldInvertState fieldState;

	public DocInverterPerField(DocInverter parent, FieldInfo fieldInfo)
	{
		this.fieldInfo = fieldInfo;
		docState = parent.docState;
		fieldState = new FieldInvertState(fieldInfo.name);
		consumer = parent.consumer.addField(this, fieldInfo);
		endConsumer = parent.endConsumer.addField(this, fieldInfo);
	}

	void abort()
	{
		consumer.abort();
		endConsumer.abort();
		break MISSING_BLOCK_LABEL_27;
		Exception exception;
		exception;
		endConsumer.abort();
		throw exception;
	}

	public void processFields(IndexableField fields[], int count)
		throws IOException
	{
		boolean doInvert;
		int i;
		fieldState.reset();
		doInvert = consumer.start(fields, count);
		i = 0;
_L6:
		if (i >= count) goto _L2; else goto _L1
_L1:
		IndexableField field;
		boolean analyzed;
		boolean checkOffsets;
		int lastStartOffset;
		TokenStream stream;
		boolean success2;
		field = fields[i];
		IndexableFieldType fieldType = field.fieldType();
		if (!fieldType.indexed() || !doInvert)
			break MISSING_BLOCK_LABEL_787;
		analyzed = fieldType.tokenized() && docState.analyzer != null;
		if (fieldType.omitNorms() && field.boost() != 1.0F)
			throw new UnsupportedOperationException((new StringBuilder()).append("You cannot set an index-time boost: norms are omitted for field '").append(field.name()).append("'").toString());
		checkOffsets = fieldType.indexOptions() == FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS;
		lastStartOffset = 0;
		if (i > 0)
			fieldState.position += analyzed ? docState.analyzer.getPositionIncrementGap(fieldInfo.name) : 0;
		stream = field.tokenStream(docState.analyzer);
		stream.reset();
		success2 = false;
		boolean hasMoreTokens;
		OffsetAttribute offsetAttribute;
		PositionIncrementAttribute posIncrAttribute;
		hasMoreTokens = stream.incrementToken();
		fieldState.attributeSource = stream;
		offsetAttribute = (OffsetAttribute)fieldState.attributeSource.addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		posIncrAttribute = (PositionIncrementAttribute)fieldState.attributeSource.addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		consumer.start(field);
_L5:
		if (hasMoreTokens) goto _L4; else goto _L3
_L4:
		boolean success;
		int posIncr = posIncrAttribute.getPositionIncrement();
		if (posIncr >= 0);
		if (fieldState.position == 0 && posIncr == 0)
			throw new IllegalArgumentException("first position increment must be > 0 (got 0)");
		int position = fieldState.position + posIncr;
		if (position > 0)
			position--;
		else
		if (position < 0)
			throw new IllegalArgumentException((new StringBuilder()).append("position overflow for field '").append(field.name()).append("'").toString());
		fieldState.position = position;
		if (posIncr == 0 && checkOffsets)
		{
			int startOffset = fieldState.offset + offsetAttribute.startOffset();
			int endOffset = fieldState.offset + offsetAttribute.endOffset();
			if (startOffset < 0 || endOffset < startOffset)
				throw new IllegalArgumentException((new StringBuilder()).append("startOffset must be non-negative, and endOffset must be >= startOffset, startOffset=").append(startOffset).append(",endOffset=").append(endOffset).toString());
			if (startOffset < lastStartOffset)
				throw new IllegalArgumentException((new StringBuilder()).append("offsets must not go backwards startOffset=").append(startOffset).append(" is < lastStartOffset=").append(lastStartOffset).toString());
			lastStartOffset = startOffset;
		}
		success = false;
		consumer.add();
		success = true;
		break MISSING_BLOCK_LABEL_571;
		exception;
		if (!success)
			docState.docWriter.setAborting();
		throw exception;
		Exception exception;
		if (!success)
			docState.docWriter.setAborting();
		fieldState.length++;
		fieldState.position++;
		hasMoreTokens = stream.incrementToken();
		  goto _L5
_L3:
		stream.end();
		fieldState.offset += offsetAttribute.endOffset();
		success2 = true;
		if (!success2)
			IOUtils.closeWhileHandlingException(new Closeable[] {
				stream
			});
		else
			stream.close();
		break MISSING_BLOCK_LABEL_730;
		Exception exception1;
		exception1;
		if (!success2)
			IOUtils.closeWhileHandlingException(new Closeable[] {
				stream
			});
		else
			stream.close();
		throw exception1;
		fieldState.offset += analyzed ? docState.analyzer.getOffsetGap(fieldInfo.name) : 0;
		fieldState.boost *= field.boost();
		fields[i] = null;
		i++;
		  goto _L6
_L2:
		consumer.finish();
		endConsumer.finish();
		return;
	}

	FieldInfo getFieldInfo()
	{
		return fieldInfo;
	}
}
