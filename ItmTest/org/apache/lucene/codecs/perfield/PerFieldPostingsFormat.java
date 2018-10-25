// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PerFieldPostingsFormat.java

package org.apache.lucene.codecs.perfield;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import org.apache.lucene.codecs.*;
import org.apache.lucene.index.*;
import org.apache.lucene.util.IOUtils;

public abstract class PerFieldPostingsFormat extends PostingsFormat
{
	private class FieldsReader extends FieldsProducer
	{

		private final Map fields;
		private final Map formats;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/perfield/PerFieldPostingsFormat.desiredAssertionStatus();
		final PerFieldPostingsFormat this$0;

		public Iterator iterator()
		{
			return Collections.unmodifiableSet(fields.keySet()).iterator();
		}

		public Terms terms(String field)
			throws IOException
		{
			FieldsProducer fieldsProducer = (FieldsProducer)fields.get(field);
			return fieldsProducer != null ? fieldsProducer.terms(field) : null;
		}

		public int size()
		{
			return fields.size();
		}

		public void close()
			throws IOException
		{
			IOUtils.close(formats.values());
		}


		public FieldsReader(SegmentReadState readState)
			throws IOException
		{
			boolean success;
			this$0 = PerFieldPostingsFormat.this;
			super();
			fields = new TreeMap();
			formats = new HashMap();
			success = false;
			Iterator i$ = readState.fieldInfos.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				FieldInfo fi = (FieldInfo)i$.next();
				if (fi.isIndexed())
				{
					String fieldName = fi.name;
					String formatName = fi.getAttribute(PerFieldPostingsFormat.PER_FIELD_FORMAT_KEY);
					if (formatName != null)
					{
						String suffix = fi.getAttribute(PerFieldPostingsFormat.PER_FIELD_SUFFIX_KEY);
						if (!$assertionsDisabled && suffix == null)
							throw new AssertionError();
						PostingsFormat format = PostingsFormat.forName(formatName);
						String segmentSuffix = PerFieldPostingsFormat.getSuffix(formatName, suffix);
						if (!formats.containsKey(segmentSuffix))
							formats.put(segmentSuffix, format.fieldsProducer(new SegmentReadState(readState, segmentSuffix)));
						fields.put(fieldName, formats.get(segmentSuffix));
					}
				}
			} while (true);
			success = true;
			if (!success)
				IOUtils.closeWhileHandlingException(formats.values());
			break MISSING_BLOCK_LABEL_248;
			Exception exception;
			exception;
			if (!success)
				IOUtils.closeWhileHandlingException(formats.values());
			throw exception;
		}
	}

	private class FieldsWriter extends FieldsConsumer
	{

		private final Map formats = new HashMap();
		private final Map suffixes = new HashMap();
		private final SegmentWriteState segmentWriteState;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/perfield/PerFieldPostingsFormat.desiredAssertionStatus();
		final PerFieldPostingsFormat this$0;

		public TermsConsumer addField(FieldInfo field)
			throws IOException
		{
			PostingsFormat format = getPostingsFormatForField(field.name);
			if (format == null)
				throw new IllegalStateException((new StringBuilder()).append("invalid null PostingsFormat for field=\"").append(field.name).append("\"").toString());
			String formatName = format.getName();
			String previousValue = field.putAttribute(PerFieldPostingsFormat.PER_FIELD_FORMAT_KEY, formatName);
			if (!$assertionsDisabled && previousValue != null)
				throw new AssertionError();
			FieldsConsumerAndSuffix consumer = (FieldsConsumerAndSuffix)formats.get(format);
			Integer suffix;
			if (consumer == null)
			{
				suffix = (Integer)suffixes.get(formatName);
				if (suffix == null)
					suffix = Integer.valueOf(0);
				else
					suffix = Integer.valueOf(suffix.intValue() + 1);
				suffixes.put(formatName, suffix);
				String segmentSuffix = PerFieldPostingsFormat.getFullSegmentSuffix(field.name, segmentWriteState.segmentSuffix, PerFieldPostingsFormat.getSuffix(formatName, Integer.toString(suffix.intValue())));
				consumer = new FieldsConsumerAndSuffix();
				consumer.consumer = format.fieldsConsumer(new SegmentWriteState(segmentWriteState, segmentSuffix));
				consumer.suffix = suffix.intValue();
				formats.put(format, consumer);
			} else
			{
				if (!$assertionsDisabled && !suffixes.containsKey(formatName))
					throw new AssertionError();
				suffix = Integer.valueOf(consumer.suffix);
			}
			previousValue = field.putAttribute(PerFieldPostingsFormat.PER_FIELD_SUFFIX_KEY, Integer.toString(suffix.intValue()));
			if (!$assertionsDisabled && previousValue != null)
				throw new AssertionError();
			else
				return consumer.consumer.addField(field);
		}

		public void close()
			throws IOException
		{
			IOUtils.close(formats.values());
		}


		public FieldsWriter(SegmentWriteState state)
		{
			this$0 = PerFieldPostingsFormat.this;
			super();
			segmentWriteState = state;
		}
	}

	static class FieldsConsumerAndSuffix
		implements Closeable
	{

		FieldsConsumer consumer;
		int suffix;

		public void close()
			throws IOException
		{
			consumer.close();
		}

		FieldsConsumerAndSuffix()
		{
		}
	}


	public static final String PER_FIELD_NAME = "PerField40";
	public static final String PER_FIELD_FORMAT_KEY = (new StringBuilder()).append(org/apache/lucene/codecs/perfield/PerFieldPostingsFormat.getSimpleName()).append(".format").toString();
	public static final String PER_FIELD_SUFFIX_KEY = (new StringBuilder()).append(org/apache/lucene/codecs/perfield/PerFieldPostingsFormat.getSimpleName()).append(".suffix").toString();

	public PerFieldPostingsFormat()
	{
		super("PerField40");
	}

	public final FieldsConsumer fieldsConsumer(SegmentWriteState state)
		throws IOException
	{
		return new FieldsWriter(state);
	}

	static String getSuffix(String formatName, String suffix)
	{
		return (new StringBuilder()).append(formatName).append("_").append(suffix).toString();
	}

	static String getFullSegmentSuffix(String fieldName, String outerSegmentSuffix, String segmentSuffix)
	{
		if (outerSegmentSuffix.length() == 0)
			return segmentSuffix;
		else
			throw new IllegalStateException((new StringBuilder()).append("cannot embed PerFieldPostingsFormat inside itself (field \"").append(fieldName).append("\" returned PerFieldPostingsFormat)").toString());
	}

	public final FieldsProducer fieldsProducer(SegmentReadState state)
		throws IOException
	{
		return new FieldsReader(state);
	}

	public abstract PostingsFormat getPostingsFormatForField(String s);

}
