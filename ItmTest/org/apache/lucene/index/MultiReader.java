// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiReader.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

// Referenced classes of package org.apache.lucene.index:
//			BaseCompositeReader, IndexReader

public class MultiReader extends BaseCompositeReader
{

	private final boolean closeSubReaders;

	public transient MultiReader(IndexReader subReaders[])
	{
		this(subReaders, true);
	}

	public MultiReader(IndexReader subReaders[], boolean closeSubReaders)
	{
		super((IndexReader[])subReaders.clone());
		this.closeSubReaders = closeSubReaders;
		if (!closeSubReaders)
		{
			for (int i = 0; i < subReaders.length; i++)
				subReaders[i].incRef();

		}
	}

	protected synchronized void doClose()
		throws IOException
	{
		IOException ioe = null;
		Iterator i$ = getSequentialSubReaders().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			IndexReader r = (IndexReader)i$.next();
			try
			{
				if (closeSubReaders)
					r.close();
				else
					r.decRef();
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
