// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ParallelCompositeReader.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.*;

// Referenced classes of package org.apache.lucene.index:
//			BaseCompositeReader, CompositeReader, IndexReader, AtomicReader, 
//			ParallelAtomicReader

public final class ParallelCompositeReader extends BaseCompositeReader
{

	private final boolean closeSubReaders;
	private final Set completeReaderSet;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/ParallelCompositeReader.desiredAssertionStatus();

	public transient ParallelCompositeReader(CompositeReader readers[])
		throws IOException
	{
		this(true, readers);
	}

	public transient ParallelCompositeReader(boolean closeSubReaders, CompositeReader readers[])
		throws IOException
	{
		this(closeSubReaders, readers, readers);
	}

	public ParallelCompositeReader(boolean closeSubReaders, CompositeReader readers[], CompositeReader storedFieldReaders[])
		throws IOException
	{
		super(prepareSubReaders(readers, storedFieldReaders));
		completeReaderSet = Collections.newSetFromMap(new IdentityHashMap());
		this.closeSubReaders = closeSubReaders;
		Collections.addAll(completeReaderSet, readers);
		Collections.addAll(completeReaderSet, storedFieldReaders);
		if (!closeSubReaders)
		{
			CompositeReader reader;
			for (Iterator i$ = completeReaderSet.iterator(); i$.hasNext(); reader.incRef())
				reader = (CompositeReader)i$.next();

		}
	}

	private static IndexReader[] prepareSubReaders(CompositeReader readers[], CompositeReader storedFieldsReaders[])
		throws IOException
	{
		if (readers.length == 0)
			if (storedFieldsReaders.length > 0)
				throw new IllegalArgumentException("There must be at least one main reader if storedFieldsReaders are used.");
			else
				return new IndexReader[0];
		List firstSubReaders = readers[0].getSequentialSubReaders();
		int maxDoc = readers[0].maxDoc();
		int noSubs = firstSubReaders.size();
		int childMaxDoc[] = new int[noSubs];
		boolean childAtomic[] = new boolean[noSubs];
		for (int i = 0; i < noSubs; i++)
		{
			IndexReader r = (IndexReader)firstSubReaders.get(i);
			childMaxDoc[i] = r.maxDoc();
			childAtomic[i] = r instanceof AtomicReader;
		}

		validate(readers, maxDoc, childMaxDoc, childAtomic);
		validate(storedFieldsReaders, maxDoc, childMaxDoc, childAtomic);
		IndexReader subReaders[] = new IndexReader[noSubs];
		for (int i = 0; i < subReaders.length; i++)
		{
			CompositeReader storedSubs[];
			if (firstSubReaders.get(i) instanceof AtomicReader)
			{
				AtomicReader atomicSubs[] = new AtomicReader[readers.length];
				for (int j = 0; j < readers.length; j++)
					atomicSubs[j] = (AtomicReader)readers[j].getSequentialSubReaders().get(i);

				storedSubs = new AtomicReader[storedFieldsReaders.length];
				for (int j = 0; j < storedFieldsReaders.length; j++)
					storedSubs[j] = (AtomicReader)storedFieldsReaders[j].getSequentialSubReaders().get(i);

				subReaders[i] = new ParallelAtomicReader(true, atomicSubs, storedSubs);
				continue;
			}
			if (!$assertionsDisabled && !(firstSubReaders.get(i) instanceof CompositeReader))
				throw new AssertionError();
			CompositeReader compositeSubs[] = new CompositeReader[readers.length];
			for (int j = 0; j < readers.length; j++)
				compositeSubs[j] = (CompositeReader)readers[j].getSequentialSubReaders().get(i);

			j = new CompositeReader[storedFieldsReaders.length];
			for (int j = 0; j < storedFieldsReaders.length; j++)
				j[j] = (CompositeReader)storedFieldsReaders[j].getSequentialSubReaders().get(i);

			subReaders[i] = new ParallelCompositeReader(true, compositeSubs, j);
		}

		return subReaders;
	}

	private static void validate(CompositeReader readers[], int maxDoc, int childMaxDoc[], boolean childAtomic[])
	{
		for (int i = 0; i < readers.length; i++)
		{
			CompositeReader reader = readers[i];
			List subs = reader.getSequentialSubReaders();
			if (reader.maxDoc() != maxDoc)
				throw new IllegalArgumentException((new StringBuilder()).append("All readers must have same maxDoc: ").append(maxDoc).append("!=").append(reader.maxDoc()).toString());
			int noSubs = subs.size();
			if (noSubs != childMaxDoc.length)
				throw new IllegalArgumentException("All readers must have same number of subReaders");
			for (int subIDX = 0; subIDX < noSubs; subIDX++)
			{
				IndexReader r = (IndexReader)subs.get(subIDX);
				if (r.maxDoc() != childMaxDoc[subIDX])
					throw new IllegalArgumentException("All readers must have same corresponding subReader maxDoc");
				if (childAtomic[subIDX] ? !(r instanceof AtomicReader) : !(r instanceof CompositeReader))
					throw new IllegalArgumentException("All readers must have same corresponding subReader types (atomic or composite)");
			}

		}

	}

	public String toString()
	{
		StringBuilder buffer = new StringBuilder("ParallelCompositeReader(");
		Iterator iter = completeReaderSet.iterator();
		do
		{
			if (!iter.hasNext())
				break;
			buffer.append(iter.next());
			if (iter.hasNext())
				buffer.append(", ");
		} while (true);
		return buffer.append(')').toString();
	}

	protected synchronized void doClose()
		throws IOException
	{
		IOException ioe = null;
		Iterator i$ = completeReaderSet.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			CompositeReader reader = (CompositeReader)i$.next();
			try
			{
				if (closeSubReaders)
					reader.close();
				else
					reader.decRef();
			}
			catch (IOException e)
			{
				if (ioe == null)
					ioe = e;
			}
		} while (true);
		if (ioe != null)
			throw ioe;
		else
			return;
	}

}
