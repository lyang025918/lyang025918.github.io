// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ParallelAtomicReader.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.index:
//			AtomicReader, FieldInfo, FieldInfos, Fields, 
//			Terms, DocValues, StoredFieldVisitor

public final class ParallelAtomicReader extends AtomicReader
{
	private final class ParallelFields extends Fields
	{

		final Map fields = new TreeMap();
		final ParallelAtomicReader this$0;

		void addField(String fieldName, Terms terms)
		{
			fields.put(fieldName, terms);
		}

		public Iterator iterator()
		{
			return Collections.unmodifiableSet(fields.keySet()).iterator();
		}

		public Terms terms(String field)
		{
			return (Terms)fields.get(field);
		}

		public int size()
		{
			return fields.size();
		}

		ParallelFields()
		{
			this$0 = ParallelAtomicReader.this;
			super();
		}
	}


	private final FieldInfos fieldInfos;
	private final ParallelFields fields;
	private final AtomicReader parallelReaders[];
	private final AtomicReader storedFieldsReaders[];
	private final Set completeReaderSet;
	private final boolean closeSubReaders;
	private final int maxDoc;
	private final int numDocs;
	private final boolean hasDeletions;
	private final SortedMap fieldToReader;
	private final SortedMap tvFieldToReader;

	public transient ParallelAtomicReader(AtomicReader readers[])
		throws IOException
	{
		this(true, readers);
	}

	public transient ParallelAtomicReader(boolean closeSubReaders, AtomicReader readers[])
		throws IOException
	{
		this(closeSubReaders, readers, readers);
	}

	public ParallelAtomicReader(boolean closeSubReaders, AtomicReader readers[], AtomicReader storedFieldsReaders[])
		throws IOException
	{
		fields = new ParallelFields();
		completeReaderSet = Collections.newSetFromMap(new IdentityHashMap());
		fieldToReader = new TreeMap();
		tvFieldToReader = new TreeMap();
		this.closeSubReaders = closeSubReaders;
		if (readers.length == 0 && storedFieldsReaders.length > 0)
			throw new IllegalArgumentException("There must be at least one main reader if storedFieldsReaders are used.");
		parallelReaders = (AtomicReader[])readers.clone();
		this.storedFieldsReaders = (AtomicReader[])storedFieldsReaders.clone();
		if (parallelReaders.length > 0)
		{
			AtomicReader first = parallelReaders[0];
			maxDoc = first.maxDoc();
			numDocs = first.numDocs();
			hasDeletions = first.hasDeletions();
		} else
		{
			maxDoc = numDocs = 0;
			hasDeletions = false;
		}
		Collections.addAll(completeReaderSet, parallelReaders);
		Collections.addAll(completeReaderSet, this.storedFieldsReaders);
		for (Iterator i$ = completeReaderSet.iterator(); i$.hasNext();)
		{
			AtomicReader reader = (AtomicReader)i$.next();
			if (reader.maxDoc() != maxDoc)
				throw new IllegalArgumentException((new StringBuilder()).append("All readers must have same maxDoc: ").append(maxDoc).append("!=").append(reader.maxDoc()).toString());
		}

		FieldInfos.Builder builder = new FieldInfos.Builder();
		AtomicReader arr$[] = parallelReaders;
		int len$ = arr$.length;
label0:
		for (int i$ = 0; i$ < len$; i$++)
		{
			AtomicReader reader = arr$[i$];
			FieldInfos readerFieldInfos = reader.getFieldInfos();
			Iterator i$ = readerFieldInfos.iterator();
			do
			{
				if (!i$.hasNext())
					continue label0;
				FieldInfo fieldInfo = (FieldInfo)i$.next();
				if (!fieldToReader.containsKey(fieldInfo.name))
				{
					builder.add(fieldInfo);
					fieldToReader.put(fieldInfo.name, reader);
					if (fieldInfo.hasVectors())
						tvFieldToReader.put(fieldInfo.name, reader);
				}
			} while (true);
		}

		fieldInfos = builder.finish();
		arr$ = parallelReaders;
		len$ = arr$.length;
label1:
		for (int i$ = 0; i$ < len$; i$++)
		{
			AtomicReader reader = arr$[i$];
			Fields readerFields = reader.fields();
			if (readerFields == null)
				continue;
			Iterator i$ = readerFields.iterator();
			do
			{
				String field;
				do
				{
					if (!i$.hasNext())
						continue label1;
					field = (String)i$.next();
				} while (fieldToReader.get(field) != reader);
				fields.addField(field, readerFields.terms(field));
			} while (true);
		}

		AtomicReader reader;
		for (Iterator i$ = completeReaderSet.iterator(); i$.hasNext(); reader.registerParentReader(this))
		{
			reader = (AtomicReader)i$.next();
			if (!closeSubReaders)
				reader.incRef();
		}

	}

	public String toString()
	{
		StringBuilder buffer = new StringBuilder("ParallelAtomicReader(");
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

	public FieldInfos getFieldInfos()
	{
		return fieldInfos;
	}

	public Bits getLiveDocs()
	{
		ensureOpen();
		return hasDeletions ? parallelReaders[0].getLiveDocs() : null;
	}

	public Fields fields()
	{
		ensureOpen();
		return fields;
	}

	public int numDocs()
	{
		return numDocs;
	}

	public int maxDoc()
	{
		return maxDoc;
	}

	public boolean hasDeletions()
	{
		ensureOpen();
		return hasDeletions;
	}

	public void document(int docID, StoredFieldVisitor visitor)
		throws IOException
	{
		ensureOpen();
		AtomicReader arr$[] = storedFieldsReaders;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			AtomicReader reader = arr$[i$];
			reader.document(docID, visitor);
		}

	}

	public Fields getTermVectors(int docID)
		throws IOException
	{
		ensureOpen();
		ParallelFields fields = null;
		Iterator i$ = tvFieldToReader.entrySet().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			java.util.Map.Entry ent = (java.util.Map.Entry)i$.next();
			String fieldName = (String)ent.getKey();
			Terms vector = ((AtomicReader)ent.getValue()).getTermVector(docID, fieldName);
			if (vector != null)
			{
				if (fields == null)
					fields = new ParallelFields();
				fields.addField(fieldName, vector);
			}
		} while (true);
		return fields;
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
			AtomicReader reader = (AtomicReader)i$.next();
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

	public DocValues docValues(String field)
		throws IOException
	{
		ensureOpen();
		AtomicReader reader = (AtomicReader)fieldToReader.get(field);
		return reader != null ? reader.docValues(field) : null;
	}

	public DocValues normValues(String field)
		throws IOException
	{
		ensureOpen();
		AtomicReader reader = (AtomicReader)fieldToReader.get(field);
		return reader != null ? reader.normValues(field) : null;
	}
}
