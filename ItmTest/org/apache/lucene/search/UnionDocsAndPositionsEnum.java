// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiPhraseQuery.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.*;
import org.apache.lucene.util.*;

class UnionDocsAndPositionsEnum extends DocsAndPositionsEnum
{
	private static final class IntQueue
	{

		private int _arraySize;
		private int _index;
		private int _lastIndex;
		private int _array[];

		final void add(int i)
		{
			if (_lastIndex == _arraySize)
				growArray();
			_array[_lastIndex++] = i;
		}

		final int next()
		{
			return _array[_index++];
		}

		final void sort()
		{
			Arrays.sort(_array, _index, _lastIndex);
		}

		final void clear()
		{
			_index = 0;
			_lastIndex = 0;
		}

		final int size()
		{
			return _lastIndex - _index;
		}

		private void growArray()
		{
			int newArray[] = new int[_arraySize * 2];
			System.arraycopy(_array, 0, newArray, 0, _arraySize);
			_array = newArray;
			_arraySize *= 2;
		}

		private IntQueue()
		{
			_arraySize = 16;
			_index = 0;
			_lastIndex = 0;
			_array = new int[_arraySize];
		}

	}

	private static final class DocsQueue extends PriorityQueue
	{

		public final boolean lessThan(DocsAndPositionsEnum a, DocsAndPositionsEnum b)
		{
			return a.docID() < b.docID();
		}

		public volatile boolean lessThan(Object x0, Object x1)
		{
			return lessThan((DocsAndPositionsEnum)x0, (DocsAndPositionsEnum)x1);
		}

		DocsQueue(List docsEnums)
			throws IOException
		{
			super(docsEnums.size());
			Iterator i = docsEnums.iterator();
			do
			{
				if (!i.hasNext())
					break;
				DocsAndPositionsEnum postings = (DocsAndPositionsEnum)i.next();
				if (postings.nextDoc() != 0x7fffffff)
					add(postings);
			} while (true);
		}
	}


	private int _doc;
	private int _freq;
	private DocsQueue _queue;
	private IntQueue _posList;

	public UnionDocsAndPositionsEnum(Bits liveDocs, AtomicReaderContext context, Term terms[], Map termContexts, TermsEnum termsEnum)
		throws IOException
	{
		List docsEnums = new LinkedList();
		for (int i = 0; i < terms.length; i++)
		{
			Term term = terms[i];
			TermState termState = ((TermContext)termContexts.get(term)).get(context.ord);
			if (termState == null)
				continue;
			termsEnum.seekExact(term.bytes(), termState);
			DocsAndPositionsEnum postings = termsEnum.docsAndPositions(liveDocs, null, 0);
			if (postings == null)
				throw new IllegalStateException((new StringBuilder()).append("field \"").append(term.field()).append("\" was indexed without position data; cannot run PhraseQuery (term=").append(term.text()).append(")").toString());
			docsEnums.add(postings);
		}

		_queue = new DocsQueue(docsEnums);
		_posList = new IntQueue();
	}

	public final int nextDoc()
		throws IOException
	{
		if (_queue.size() == 0)
			return 0x7fffffff;
		_posList.clear();
		_doc = ((DocsAndPositionsEnum)_queue.top()).docID();
		do
		{
			DocsAndPositionsEnum postings = (DocsAndPositionsEnum)_queue.top();
			int freq = postings.freq();
			for (int i = 0; i < freq; i++)
				_posList.add(postings.nextPosition());

			if (postings.nextDoc() != 0x7fffffff)
				_queue.updateTop();
			else
				_queue.pop();
		} while (_queue.size() > 0 && ((DocsAndPositionsEnum)_queue.top()).docID() == _doc);
		_posList.sort();
		_freq = _posList.size();
		return _doc;
	}

	public int nextPosition()
	{
		return _posList.next();
	}

	public int startOffset()
	{
		return -1;
	}

	public int endOffset()
	{
		return -1;
	}

	public BytesRef getPayload()
	{
		return null;
	}

	public final int advance(int target)
		throws IOException
	{
		do
		{
			if (_queue.top() == null || target <= ((DocsAndPositionsEnum)_queue.top()).docID())
				break;
			DocsAndPositionsEnum postings = (DocsAndPositionsEnum)_queue.pop();
			if (postings.advance(target) != 0x7fffffff)
				_queue.add(postings);
		} while (true);
		return nextDoc();
	}

	public final int freq()
	{
		return _freq;
	}

	public final int docID()
	{
		return _doc;
	}
}
