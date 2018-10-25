// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldCache.java

package org.apache.lucene.search;

import java.io.IOException;
import java.io.PrintStream;
import org.apache.lucene.index.*;
import org.apache.lucene.util.*;
import org.apache.lucene.util.packed.PackedInts;

// Referenced classes of package org.apache.lucene.search:
//			FieldCacheImpl

public interface FieldCache
{
	public static abstract class CacheEntry
	{

		private String size;

		public abstract Object getReaderKey();

		public abstract String getFieldName();

		public abstract Class getCacheType();

		public abstract Object getCustom();

		public abstract Object getValue();

		protected final void setEstimatedSize(String size)
		{
			this.size = size;
		}

		public void estimateSize()
		{
			long size = RamUsageEstimator.sizeOf(getValue());
			setEstimatedSize(RamUsageEstimator.humanReadableUnits(size));
		}

		public final String getEstimatedSize()
		{
			return size;
		}

		public String toString()
		{
			StringBuilder b = new StringBuilder();
			b.append("'").append(getReaderKey()).append("'=>");
			b.append("'").append(getFieldName()).append("',");
			b.append(getCacheType()).append(",").append(getCustom());
			b.append("=>").append(getValue().getClass().getName()).append("#");
			b.append(System.identityHashCode(getValue()));
			String s = getEstimatedSize();
			if (null != s)
				b.append(" (size =~ ").append(s).append(')');
			return b.toString();
		}

		public CacheEntry()
		{
			size = null;
		}
	}

	public static abstract class DocTermsIndex
	{

		public int binarySearchLookup(BytesRef key, BytesRef spare)
		{
			if (key == null)
				return 0;
			int low = 1;
			for (int high = numOrd() - 1; low <= high;)
			{
				int mid = low + high >>> 1;
				int cmp = lookup(mid, spare).compareTo(key);
				if (cmp < 0)
					low = mid + 1;
				else
				if (cmp > 0)
					high = mid - 1;
				else
					return mid;
			}

			return -(low + 1);
		}

		public abstract BytesRef lookup(int i, BytesRef bytesref);

		public BytesRef getTerm(int docID, BytesRef reuse)
		{
			return lookup(getOrd(docID), reuse);
		}

		public abstract int getOrd(int i);

		public abstract int numOrd();

		public abstract int size();

		public abstract TermsEnum getTermsEnum();

		public abstract org.apache.lucene.util.packed.PackedInts.Reader getDocToOrd();

		public DocTermsIndex()
		{
		}
	}

	public static abstract class DocTerms
	{

		public abstract BytesRef getTerm(int i, BytesRef bytesref);

		public abstract boolean exists(int i);

		public abstract int size();

		public DocTerms()
		{
		}
	}

	public static interface DoubleParser
		extends Parser
	{

		public abstract double parseDouble(BytesRef bytesref);
	}

	public static interface LongParser
		extends Parser
	{

		public abstract long parseLong(BytesRef bytesref);
	}

	public static interface FloatParser
		extends Parser
	{

		public abstract float parseFloat(BytesRef bytesref);
	}

	public static interface IntParser
		extends Parser
	{

		public abstract int parseInt(BytesRef bytesref);
	}

	public static interface ShortParser
		extends Parser
	{

		public abstract short parseShort(BytesRef bytesref);
	}

	public static interface ByteParser
		extends Parser
	{

		public abstract byte parseByte(BytesRef bytesref);
	}

	public static interface Parser
	{
	}

	public static final class StopFillCacheException extends RuntimeException
	{

		public StopFillCacheException()
		{
		}
	}

	public static final class CreationPlaceholder
	{

		Object value;

		public CreationPlaceholder()
		{
		}
	}


	public static final FieldCache DEFAULT = new FieldCacheImpl();
	public static final ByteParser DEFAULT_BYTE_PARSER = new ByteParser() {

		public byte parseByte(BytesRef term)
		{
			return Byte.parseByte(term.utf8ToString());
		}

		public String toString()
		{
			return (new StringBuilder()).append(org/apache/lucene/search/FieldCache.getName()).append(".DEFAULT_BYTE_PARSER").toString();
		}

	};
	public static final ShortParser DEFAULT_SHORT_PARSER = new ShortParser() {

		public short parseShort(BytesRef term)
		{
			return Short.parseShort(term.utf8ToString());
		}

		public String toString()
		{
			return (new StringBuilder()).append(org/apache/lucene/search/FieldCache.getName()).append(".DEFAULT_SHORT_PARSER").toString();
		}

	};
	public static final IntParser DEFAULT_INT_PARSER = new IntParser() {

		public int parseInt(BytesRef term)
		{
			return Integer.parseInt(term.utf8ToString());
		}

		public String toString()
		{
			return (new StringBuilder()).append(org/apache/lucene/search/FieldCache.getName()).append(".DEFAULT_INT_PARSER").toString();
		}

	};
	public static final FloatParser DEFAULT_FLOAT_PARSER = new FloatParser() {

		public float parseFloat(BytesRef term)
		{
			return Float.parseFloat(term.utf8ToString());
		}

		public String toString()
		{
			return (new StringBuilder()).append(org/apache/lucene/search/FieldCache.getName()).append(".DEFAULT_FLOAT_PARSER").toString();
		}

	};
	public static final LongParser DEFAULT_LONG_PARSER = new LongParser() {

		public long parseLong(BytesRef term)
		{
			return Long.parseLong(term.utf8ToString());
		}

		public String toString()
		{
			return (new StringBuilder()).append(org/apache/lucene/search/FieldCache.getName()).append(".DEFAULT_LONG_PARSER").toString();
		}

	};
	public static final DoubleParser DEFAULT_DOUBLE_PARSER = new DoubleParser() {

		public double parseDouble(BytesRef term)
		{
			return Double.parseDouble(term.utf8ToString());
		}

		public String toString()
		{
			return (new StringBuilder()).append(org/apache/lucene/search/FieldCache.getName()).append(".DEFAULT_DOUBLE_PARSER").toString();
		}

	};
	public static final IntParser NUMERIC_UTILS_INT_PARSER = new IntParser() {

		public int parseInt(BytesRef term)
		{
			if (NumericUtils.getPrefixCodedIntShift(term) > 0)
				throw new StopFillCacheException();
			else
				return NumericUtils.prefixCodedToInt(term);
		}

		public String toString()
		{
			return (new StringBuilder()).append(org/apache/lucene/search/FieldCache.getName()).append(".NUMERIC_UTILS_INT_PARSER").toString();
		}

	};
	public static final FloatParser NUMERIC_UTILS_FLOAT_PARSER = new FloatParser() {

		public float parseFloat(BytesRef term)
		{
			if (NumericUtils.getPrefixCodedIntShift(term) > 0)
				throw new StopFillCacheException();
			else
				return NumericUtils.sortableIntToFloat(NumericUtils.prefixCodedToInt(term));
		}

		public String toString()
		{
			return (new StringBuilder()).append(org/apache/lucene/search/FieldCache.getName()).append(".NUMERIC_UTILS_FLOAT_PARSER").toString();
		}

	};
	public static final LongParser NUMERIC_UTILS_LONG_PARSER = new LongParser() {

		public long parseLong(BytesRef term)
		{
			if (NumericUtils.getPrefixCodedLongShift(term) > 0)
				throw new StopFillCacheException();
			else
				return NumericUtils.prefixCodedToLong(term);
		}

		public String toString()
		{
			return (new StringBuilder()).append(org/apache/lucene/search/FieldCache.getName()).append(".NUMERIC_UTILS_LONG_PARSER").toString();
		}

	};
	public static final DoubleParser NUMERIC_UTILS_DOUBLE_PARSER = new DoubleParser() {

		public double parseDouble(BytesRef term)
		{
			if (NumericUtils.getPrefixCodedLongShift(term) > 0)
				throw new StopFillCacheException();
			else
				return NumericUtils.sortableLongToDouble(NumericUtils.prefixCodedToLong(term));
		}

		public String toString()
		{
			return (new StringBuilder()).append(org/apache/lucene/search/FieldCache.getName()).append(".NUMERIC_UTILS_DOUBLE_PARSER").toString();
		}

	};

	public abstract Bits getDocsWithField(AtomicReader atomicreader, String s)
		throws IOException;

	public abstract byte[] getBytes(AtomicReader atomicreader, String s, boolean flag)
		throws IOException;

	public abstract byte[] getBytes(AtomicReader atomicreader, String s, ByteParser byteparser, boolean flag)
		throws IOException;

	public abstract short[] getShorts(AtomicReader atomicreader, String s, boolean flag)
		throws IOException;

	public abstract short[] getShorts(AtomicReader atomicreader, String s, ShortParser shortparser, boolean flag)
		throws IOException;

	public abstract int[] getInts(AtomicReader atomicreader, String s, boolean flag)
		throws IOException;

	public abstract int[] getInts(AtomicReader atomicreader, String s, IntParser intparser, boolean flag)
		throws IOException;

	public abstract float[] getFloats(AtomicReader atomicreader, String s, boolean flag)
		throws IOException;

	public abstract float[] getFloats(AtomicReader atomicreader, String s, FloatParser floatparser, boolean flag)
		throws IOException;

	public abstract long[] getLongs(AtomicReader atomicreader, String s, boolean flag)
		throws IOException;

	public abstract long[] getLongs(AtomicReader atomicreader, String s, LongParser longparser, boolean flag)
		throws IOException;

	public abstract double[] getDoubles(AtomicReader atomicreader, String s, boolean flag)
		throws IOException;

	public abstract double[] getDoubles(AtomicReader atomicreader, String s, DoubleParser doubleparser, boolean flag)
		throws IOException;

	public abstract DocTerms getTerms(AtomicReader atomicreader, String s)
		throws IOException;

	public abstract DocTerms getTerms(AtomicReader atomicreader, String s, float f)
		throws IOException;

	public abstract DocTermsIndex getTermsIndex(AtomicReader atomicreader, String s)
		throws IOException;

	public abstract DocTermsIndex getTermsIndex(AtomicReader atomicreader, String s, float f)
		throws IOException;

	public abstract DocTermOrds getDocTermOrds(AtomicReader atomicreader, String s)
		throws IOException;

	public abstract CacheEntry[] getCacheEntries();

	public abstract void purgeAllCaches();

	public abstract void purge(AtomicReader atomicreader);

	public abstract void setInfoStream(PrintStream printstream);

	public abstract PrintStream getInfoStream();

}
