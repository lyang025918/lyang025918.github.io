// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SegmentCoreReaders.java

package org.apache.lucene.index;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.lucene.codecs.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.CloseableThreadLocal;
import org.apache.lucene.util.IOUtils;

// Referenced classes of package org.apache.lucene.index:
//			SegmentReadState, SegmentReader, SegmentInfoPerCommit, SegmentInfo, 
//			IndexFileNames, FieldInfos

final class SegmentCoreReaders
{

	private final AtomicInteger ref;
	final FieldInfos fieldInfos;
	final FieldsProducer fields;
	final PerDocProducer perDocProducer;
	final PerDocProducer norms;
	final int termsIndexDivisor;
	private final SegmentReader owner;
	final StoredFieldsReader fieldsReaderOrig;
	final TermVectorsReader termVectorsReaderOrig;
	final CompoundFileDirectory cfsReader;
	final CloseableThreadLocal fieldsReaderLocal;
	final CloseableThreadLocal termVectorsLocal;
	private final Set coreClosedListeners;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/SegmentCoreReaders.desiredAssertionStatus();

	SegmentCoreReaders(SegmentReader owner, Directory dir, SegmentInfoPerCommit si, IOContext context, int termsIndexDivisor)
		throws IOException
	{
		Codec codec;
		boolean success;
		ref = new AtomicInteger(1);
		fieldsReaderLocal = new CloseableThreadLocal() {

			final SegmentCoreReaders this$0;

			protected StoredFieldsReader initialValue()
			{
				return fieldsReaderOrig.clone();
			}

			protected volatile Object initialValue()
			{
				return initialValue();
			}

			
			{
				this$0 = SegmentCoreReaders.this;
				super();
			}
		};
		termVectorsLocal = new CloseableThreadLocal() {

			final SegmentCoreReaders this$0;

			protected TermVectorsReader initialValue()
			{
				return termVectorsReaderOrig != null ? termVectorsReaderOrig.clone() : null;
			}

			protected volatile Object initialValue()
			{
				return initialValue();
			}

			
			{
				this$0 = SegmentCoreReaders.this;
				super();
			}
		};
		coreClosedListeners = Collections.synchronizedSet(new LinkedHashSet());
		if (termsIndexDivisor == 0)
			throw new IllegalArgumentException("indexDivisor must be < 0 (don't load terms index) or greater than 0 (got 0)");
		codec = si.info.getCodec();
		success = false;
		Directory cfsDir;
		if (si.info.getUseCompoundFile())
		{
			cfsDir = cfsReader = new CompoundFileDirectory(dir, IndexFileNames.segmentFileName(si.info.name, "", "cfs"), context, false);
		} else
		{
			cfsReader = null;
			cfsDir = dir;
		}
		fieldInfos = codec.fieldInfosFormat().getFieldInfosReader().read(cfsDir, si.info.name, IOContext.READONCE);
		this.termsIndexDivisor = termsIndexDivisor;
		PostingsFormat format = codec.postingsFormat();
		SegmentReadState segmentReadState = new SegmentReadState(cfsDir, si.info, fieldInfos, context, termsIndexDivisor);
		fields = format.fieldsProducer(segmentReadState);
		if (!$assertionsDisabled && fields == null)
			throw new AssertionError();
		norms = codec.normsFormat().docsProducer(segmentReadState);
		perDocProducer = codec.docValuesFormat().docsProducer(segmentReadState);
		fieldsReaderOrig = si.info.getCodec().storedFieldsFormat().fieldsReader(cfsDir, si.info, fieldInfos, context);
		if (fieldInfos.hasVectors())
			termVectorsReaderOrig = si.info.getCodec().termVectorsFormat().vectorsReader(cfsDir, si.info, fieldInfos, context);
		else
			termVectorsReaderOrig = null;
		success = true;
		if (!success)
			decRef();
		break MISSING_BLOCK_LABEL_362;
		Exception exception;
		exception;
		if (!success)
			decRef();
		throw exception;
		this.owner = owner;
		return;
	}

	void incRef()
	{
		ref.incrementAndGet();
	}

	void decRef()
		throws IOException
	{
		if (ref.decrementAndGet() == 0)
		{
			IOUtils.close(new Closeable[] {
				termVectorsLocal, fieldsReaderLocal, fields, perDocProducer, termVectorsReaderOrig, fieldsReaderOrig, cfsReader, norms
			});
			notifyCoreClosedListeners();
		}
	}

	private final void notifyCoreClosedListeners()
	{
		synchronized (coreClosedListeners)
		{
			SegmentReader.CoreClosedListener listener;
			for (Iterator i$ = coreClosedListeners.iterator(); i$.hasNext(); listener.onClose(owner))
				listener = (SegmentReader.CoreClosedListener)i$.next();

		}
	}

	void addCoreClosedListener(SegmentReader.CoreClosedListener listener)
	{
		coreClosedListeners.add(listener);
	}

	void removeCoreClosedListener(SegmentReader.CoreClosedListener listener)
	{
		coreClosedListeners.remove(listener);
	}

	public String toString()
	{
		return (new StringBuilder()).append("SegmentCoreReader(owner=").append(owner).append(")").toString();
	}

}
