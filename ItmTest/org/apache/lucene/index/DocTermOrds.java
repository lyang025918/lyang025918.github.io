// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocTermOrds.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.index:
//			Fields, Terms, AtomicReader, TermsEnum, 
//			DocsEnum, DocsAndPositionsEnum

public class DocTermOrds
{
	private final class OrdWrappedTermsEnum extends TermsEnum
	{

		private final TermsEnum termsEnum;
		private BytesRef term;
		private long ord;
		static final boolean $assertionsDisabled = !org/apache/lucene/index/DocTermOrds.desiredAssertionStatus();
		final DocTermOrds this$0;

		public Comparator getComparator()
		{
			return termsEnum.getComparator();
		}

		public DocsEnum docs(Bits liveDocs, DocsEnum reuse, int flags)
			throws IOException
		{
			return termsEnum.docs(liveDocs, reuse, flags);
		}

		public DocsAndPositionsEnum docsAndPositions(Bits liveDocs, DocsAndPositionsEnum reuse, int flags)
			throws IOException
		{
			return termsEnum.docsAndPositions(liveDocs, reuse, flags);
		}

		public BytesRef term()
		{
			return term;
		}

		public BytesRef next()
			throws IOException
		{
			ord++;
			if (termsEnum.next() == null)
			{
				term = null;
				return null;
			} else
			{
				return setTerm();
			}
		}

		public int docFreq()
			throws IOException
		{
			return termsEnum.docFreq();
		}

		public long totalTermFreq()
			throws IOException
		{
			return termsEnum.totalTermFreq();
		}

		public long ord()
		{
			return (long)ordBase + ord;
		}

		public TermsEnum.SeekStatus seekCeil(BytesRef target, boolean useCache)
			throws IOException
		{
			if (term != null && term.equals(target))
				return TermsEnum.SeekStatus.FOUND;
			int startIdx = Arrays.binarySearch(indexedTermsArray, target);
			if (startIdx >= 0)
			{
				TermsEnum.SeekStatus seekStatus = termsEnum.seekCeil(target);
				if (!$assertionsDisabled && seekStatus != TermsEnum.SeekStatus.FOUND)
					throw new AssertionError();
				ord = startIdx << indexIntervalBits;
				setTerm();
				if (!$assertionsDisabled && term == null)
					throw new AssertionError();
				else
					return TermsEnum.SeekStatus.FOUND;
			}
			startIdx = -startIdx - 1;
			if (startIdx == 0)
			{
				TermsEnum.SeekStatus seekStatus = termsEnum.seekCeil(target);
				if (!$assertionsDisabled && seekStatus != TermsEnum.SeekStatus.NOT_FOUND)
					throw new AssertionError();
				ord = 0L;
				setTerm();
				if (!$assertionsDisabled && term == null)
					throw new AssertionError();
				else
					return TermsEnum.SeekStatus.NOT_FOUND;
			}
			startIdx--;
			if (ord >> indexIntervalBits != (long)startIdx || term == null || term.compareTo(target) > 0)
			{
				TermsEnum.SeekStatus seekStatus = termsEnum.seekCeil(indexedTermsArray[startIdx]);
				if (!$assertionsDisabled && seekStatus != TermsEnum.SeekStatus.FOUND)
					throw new AssertionError();
				ord = startIdx << indexIntervalBits;
				setTerm();
				if (!$assertionsDisabled && term == null)
					throw new AssertionError();
			}
			for (; term != null && term.compareTo(target) < 0; next());
			if (term == null)
				return TermsEnum.SeekStatus.END;
			if (term.compareTo(target) == 0)
				return TermsEnum.SeekStatus.FOUND;
			else
				return TermsEnum.SeekStatus.NOT_FOUND;
		}

		public void seekExact(long targetOrd)
			throws IOException
		{
			int delta = (int)(targetOrd - (long)ordBase - ord);
			if (delta < 0 || delta > indexInterval)
			{
				int idx = (int)(targetOrd >>> indexIntervalBits);
				BytesRef base = indexedTermsArray[idx];
				ord = idx << indexIntervalBits;
				delta = (int)(targetOrd - ord);
				TermsEnum.SeekStatus seekStatus = termsEnum.seekCeil(base, true);
				if (!$assertionsDisabled && seekStatus != TermsEnum.SeekStatus.FOUND)
					throw new AssertionError();
			}
			while (--delta >= 0) 
			{
				BytesRef br = termsEnum.next();
				if (br == null)
					if (!$assertionsDisabled)
						throw new AssertionError();
					else
						return;
				ord++;
			}
			setTerm();
			if (!$assertionsDisabled && term == null)
				throw new AssertionError();
			else
				return;
		}

		private BytesRef setTerm()
			throws IOException
		{
			term = termsEnum.term();
			if (prefix != null && !StringHelper.startsWith(term, prefix))
				term = null;
			return term;
		}


		public OrdWrappedTermsEnum(AtomicReader reader)
			throws IOException
		{
			this$0 = DocTermOrds.this;
			super();
			ord = -indexInterval - 1;
			if (!$assertionsDisabled && indexedTermsArray == null)
			{
				throw new AssertionError();
			} else
			{
				termsEnum = reader.fields().terms(field).iterator(null);
				return;
			}
		}
	}

	public class TermOrdsIterator
	{

		private int tnum;
		private int upto;
		private byte arr[];
		final DocTermOrds this$0;

		public int read(int buffer[])
		{
			int bufferUpto = 0;
			if (arr == null)
			{
				int code = upto;
				int delta = 0;
				do
				{
					delta = delta << 7 | code & 0x7f;
					if ((code & 0x80) == 0)
					{
						if (delta == 0)
							break;
						tnum += delta - 2;
						buffer[bufferUpto++] = ordBase + tnum;
						delta = 0;
					}
					code >>>= 8;
				} while (true);
			} else
			{
				do
				{
					int delta = 0;
					byte b;
					do
					{
						b = arr[upto++];
						delta = delta << 7 | b & 0x7f;
					} while ((b & 0x80) != 0);
					if (delta == 0)
						break;
					tnum += delta - 2;
					buffer[bufferUpto++] = ordBase + tnum;
				} while (bufferUpto != buffer.length);
			}
			return bufferUpto;
		}

		public TermOrdsIterator reset(int docID)
		{
			tnum = 0;
			int code = index[docID];
			if ((code & 0xff) == 1)
			{
				upto = code >>> 8;
				int whichArray = docID >>> 16 & 0xff;
				arr = tnums[whichArray];
			} else
			{
				arr = null;
				upto = code;
			}
			return this;
		}

		TermOrdsIterator()
		{
			this$0 = DocTermOrds.this;
			super();
		}
	}


	private static final int TNUM_OFFSET = 2;
	public static final int DEFAULT_INDEX_INTERVAL_BITS = 7;
	private int indexIntervalBits;
	private int indexIntervalMask;
	private int indexInterval;
	protected final int maxTermDocFreq;
	protected final String field;
	protected int numTermsInField;
	protected long termInstances;
	private long memsz;
	protected int total_time;
	protected int phase1_time;
	protected int index[];
	protected byte tnums[][];
	protected long sizeOfIndexedStrings;
	protected BytesRef indexedTermsArray[];
	protected BytesRef prefix;
	protected int ordBase;
	protected DocsEnum docsEnum;

	public long ramUsedInBytes()
	{
		if (memsz != 0L)
			return memsz;
		long sz = 96L;
		if (index != null)
			sz += index.length * 4;
		if (tnums != null)
		{
			byte arr$[][] = tnums;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				byte arr[] = arr$[i$];
				if (arr != null)
					sz += arr.length;
			}

		}
		memsz = sz;
		return sz;
	}

	public DocTermOrds(AtomicReader reader, String field)
		throws IOException
	{
		this(reader, field, null, 0x7fffffff);
	}

	public DocTermOrds(AtomicReader reader, String field, BytesRef termPrefix)
		throws IOException
	{
		this(reader, field, termPrefix, 0x7fffffff);
	}

	public DocTermOrds(AtomicReader reader, String field, BytesRef termPrefix, int maxTermDocFreq)
		throws IOException
	{
		this(reader, field, termPrefix, maxTermDocFreq, 7);
		uninvert(reader, termPrefix);
	}

	public DocTermOrds(AtomicReader reader, String field, BytesRef termPrefix, int maxTermDocFreq, int indexIntervalBits)
		throws IOException
	{
		this(field, maxTermDocFreq, indexIntervalBits);
		uninvert(reader, termPrefix);
	}

	protected DocTermOrds(String field, int maxTermDocFreq, int indexIntervalBits)
	{
		tnums = new byte[256][];
		this.field = field;
		this.maxTermDocFreq = maxTermDocFreq;
		this.indexIntervalBits = indexIntervalBits;
		indexIntervalMask = -1 >>> 32 - indexIntervalBits;
		indexInterval = 1 << indexIntervalBits;
	}

	public TermsEnum getOrdTermsEnum(AtomicReader reader)
		throws IOException
	{
		if (indexedTermsArray == null)
		{
			Fields fields = reader.fields();
			if (fields == null)
				return null;
			Terms terms = fields.terms(field);
			if (terms == null)
				return null;
			else
				return terms.iterator(null);
		} else
		{
			return new OrdWrappedTermsEnum(reader);
		}
	}

	public int numTerms()
	{
		return numTermsInField;
	}

	public boolean isEmpty()
	{
		return index == null;
	}

	protected void visitTerm(TermsEnum termsenum, int i)
		throws IOException
	{
	}

	protected void setActualDocFreq(int i, int j)
		throws IOException
	{
	}

	protected void uninvert(AtomicReader reader, BytesRef termPrefix)
		throws IOException
	{
		long startTime = System.currentTimeMillis();
		prefix = termPrefix != null ? BytesRef.deepCopyOf(termPrefix) : null;
		int maxDoc = reader.maxDoc();
		int index[] = new int[maxDoc];
		int lastTerm[] = new int[maxDoc];
		byte bytes[][] = new byte[maxDoc][];
		Fields fields = reader.fields();
		if (fields == null)
			return;
		Terms terms = fields.terms(field);
		if (terms == null)
			return;
		TermsEnum te = terms.iterator(null);
		BytesRef seekStart = termPrefix == null ? new BytesRef() : termPrefix;
		if (te.seekCeil(seekStart) == TermsEnum.SeekStatus.END)
			return;
		List indexedTerms = null;
		PagedBytes indexedTermsBytes = null;
		boolean testedOrd = false;
		Bits liveDocs = reader.getLiveDocs();
		byte tempArr[] = new byte[12];
		int termNum = 0;
		docsEnum = null;
		do
		{
			BytesRef t = te.term();
			if (t == null || termPrefix != null && !StringHelper.startsWith(t, termPrefix))
				break;
			if (!testedOrd)
			{
				try
				{
					ordBase = (int)te.ord();
				}
				catch (UnsupportedOperationException uoe)
				{
					indexedTerms = new ArrayList();
					indexedTermsBytes = new PagedBytes(15);
				}
				testedOrd = true;
			}
			visitTerm(te, termNum);
			if (indexedTerms != null && (termNum & indexIntervalMask) == 0)
			{
				sizeOfIndexedStrings += t.length;
				BytesRef indexedTerm = new BytesRef();
				indexedTermsBytes.copy(t, indexedTerm);
				indexedTerms.add(indexedTerm);
			}
			int df = te.docFreq();
			if (df <= maxTermDocFreq)
			{
				docsEnum = te.docs(liveDocs, docsEnum, 0);
				int actualDF = 0;
				do
				{
					int doc = docsEnum.nextDoc();
					if (doc == 0x7fffffff)
						break;
					actualDF++;
					termInstances++;
					int delta = (termNum - lastTerm[doc]) + 2;
					lastTerm[doc] = termNum;
					int val = index[doc];
					if ((val & 0xff) == 1)
					{
						int pos = val >>> 8;
						int ilen = vIntSize(delta);
						byte arr[] = bytes[doc];
						int newend = pos + ilen;
						if (newend > arr.length)
						{
							int newLen = newend + 3 & -4;
							byte newarr[] = new byte[newLen];
							System.arraycopy(arr, 0, newarr, 0, pos);
							arr = newarr;
							bytes[doc] = newarr;
						}
						pos = writeInt(delta, arr, pos);
						index[doc] = pos << 8 | 1;
					} else
					{
						int ipos;
						if (val == 0)
							ipos = 0;
						else
						if ((val & 0xff80) == 0)
							ipos = 1;
						else
						if ((val & 0xff8000) == 0)
							ipos = 2;
						else
						if ((val & 0xff800000) == 0)
							ipos = 3;
						else
							ipos = 4;
						int endPos = writeInt(delta, tempArr, ipos);
						if (endPos <= 4)
						{
							for (int j = ipos; j < endPos; j++)
								val |= (tempArr[j] & 0xff) << (j << 3);

							index[doc] = val;
						} else
						{
							for (int j = 0; j < ipos; j++)
							{
								tempArr[j] = (byte)val;
								val >>>= 8;
							}

							index[doc] = endPos << 8 | 1;
							bytes[doc] = tempArr;
							tempArr = new byte[12];
						}
					}
				} while (true);
				setActualDocFreq(termNum, actualDF);
			}
			termNum++;
		} while (te.next() != null);
		numTermsInField = termNum;
		long midPoint = System.currentTimeMillis();
		if (termInstances == 0L)
		{
			tnums = (byte[][])null;
		} else
		{
			this.index = index;
			int pass = 0;
			do
			{
				if (pass >= 256)
					break;
				byte target[] = tnums[pass];
				int pos = 0;
				if (target != null)
					pos = target.length;
				else
					target = new byte[4096];
				for (int docbase = pass << 16; docbase < maxDoc; docbase += 0x1000000)
				{
					int lim = Math.min(docbase + 0x10000, maxDoc);
					for (int doc = docbase; doc < lim; doc++)
					{
						int val = index[doc];
						if ((val & 0xff) != 1)
							continue;
						int len = val >>> 8;
						index[doc] = pos << 8 | 1;
						if ((pos & 0xff000000) != 0)
							throw new IllegalStateException((new StringBuilder()).append("Too many values for UnInvertedField faceting on field ").append(field).toString());
						byte arr[] = bytes[doc];
						bytes[doc] = null;
						if (target.length <= pos + len)
						{
							int newlen;
							for (newlen = target.length; newlen <= pos + len; newlen <<= 1);
							byte newtarget[] = new byte[newlen];
							System.arraycopy(target, 0, newtarget, 0, pos);
							target = newtarget;
						}
						System.arraycopy(arr, 0, target, pos, len);
						pos += len + 1;
					}

				}

				if (pos < target.length)
				{
					byte newtarget[] = new byte[pos];
					System.arraycopy(target, 0, newtarget, 0, pos);
					target = newtarget;
				}
				tnums[pass] = target;
				if (pass << 16 > maxDoc)
					break;
				pass++;
			} while (true);
		}
		if (indexedTerms != null)
			indexedTermsArray = (BytesRef[])indexedTerms.toArray(new BytesRef[indexedTerms.size()]);
		long endTime = System.currentTimeMillis();
		total_time = (int)(endTime - startTime);
		phase1_time = (int)(midPoint - startTime);
	}

	private static int vIntSize(int x)
	{
		if ((x & 0xffffff80) == 0)
			return 1;
		if ((x & 0xffffc000) == 0)
			return 2;
		if ((x & 0xffe00000) == 0)
			return 3;
		return (x & 0xf0000000) != 0 ? 5 : 4;
	}

	private static int writeInt(int x, byte arr[], int pos)
	{
		int a = x >>> 28;
		if (a != 0)
			arr[pos++] = (byte)(a | 0x80);
		a = x >>> 21;
		if (a != 0)
			arr[pos++] = (byte)(a | 0x80);
		a = x >>> 14;
		if (a != 0)
			arr[pos++] = (byte)(a | 0x80);
		a = x >>> 7;
		if (a != 0)
			arr[pos++] = (byte)(a | 0x80);
		arr[pos++] = (byte)(x & 0x7f);
		return pos;
	}

	public TermOrdsIterator lookup(int doc, TermOrdsIterator reuse)
	{
		TermOrdsIterator ret;
		if (reuse != null)
			ret = reuse;
		else
			ret = new TermOrdsIterator();
		return ret.reset(doc);
	}

	public BytesRef lookupTerm(TermsEnum termsEnum, int ord)
		throws IOException
	{
		termsEnum.seekExact(ord);
		return termsEnum.term();
	}


}
