// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PrefixCodedTerms.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.Iterator;
import org.apache.lucene.store.*;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.index:
//			Term

class PrefixCodedTerms
	implements Iterable
{
	public static class Builder
	{

		private RAMFile buffer;
		private RAMOutputStream output;
		private Term lastTerm;
		static final boolean $assertionsDisabled = !org/apache/lucene/index/PrefixCodedTerms.desiredAssertionStatus();

		public void add(Term term)
		{
			if (!$assertionsDisabled && !lastTerm.equals(new Term("")) && term.compareTo(lastTerm) <= 0)
				throw new AssertionError();
			try
			{
				int prefix = sharedPrefix(lastTerm.bytes, term.bytes);
				int suffix = term.bytes.length - prefix;
				if (term.field.equals(lastTerm.field))
				{
					output.writeVInt(prefix << 1);
				} else
				{
					output.writeVInt(prefix << 1 | 1);
					output.writeString(term.field);
				}
				output.writeVInt(suffix);
				output.writeBytes(term.bytes.bytes, term.bytes.offset + prefix, suffix);
				lastTerm.bytes.copyBytes(term.bytes);
				lastTerm.field = term.field;
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}

		public PrefixCodedTerms finish()
		{
			output.close();
			return new PrefixCodedTerms(buffer);
			IOException e;
			e;
			throw new RuntimeException(e);
		}

		private int sharedPrefix(BytesRef term1, BytesRef term2)
		{
			int pos1 = 0;
			int pos1End = pos1 + Math.min(term1.length, term2.length);
			for (int pos2 = 0; pos1 < pos1End; pos2++)
			{
				if (term1.bytes[term1.offset + pos1] != term2.bytes[term2.offset + pos2])
					return pos1;
				pos1++;
			}

			return pos1;
		}


		public Builder()
		{
			buffer = new RAMFile();
			output = new RAMOutputStream(buffer);
			lastTerm = new Term("");
		}
	}

	class PrefixCodedTermsIterator
		implements Iterator
	{

		final IndexInput input;
		String field;
		BytesRef bytes;
		Term term;
		static final boolean $assertionsDisabled = !org/apache/lucene/index/PrefixCodedTerms.desiredAssertionStatus();
		final PrefixCodedTerms this$0;

		public boolean hasNext()
		{
			return input.getFilePointer() < input.length();
		}

		public Term next()
		{
			if (!$assertionsDisabled && !hasNext())
				throw new AssertionError();
			int code = input.readVInt();
			if ((code & 1) != 0)
				field = input.readString();
			int prefix = code >>> 1;
			int suffix = input.readVInt();
			bytes.grow(prefix + suffix);
			input.readBytes(bytes.bytes, prefix, suffix);
			bytes.length = prefix + suffix;
			term.set(field, bytes);
			return term;
			IOException e;
			e;
			throw new RuntimeException(e);
		}

		public void remove()
		{
			throw new UnsupportedOperationException();
		}

		public volatile Object next()
		{
			return next();
		}


		PrefixCodedTermsIterator()
		{
			this$0 = PrefixCodedTerms.this;
			super();
			field = "";
			bytes = new BytesRef();
			term = new Term(field, bytes);
			try
			{
				input = new RAMInputStream("PrefixCodedTermsIterator", buffer);
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
	}


	final RAMFile buffer;

	private PrefixCodedTerms(RAMFile buffer)
	{
		this.buffer = buffer;
	}

	public long getSizeInBytes()
	{
		return buffer.getSizeInBytes();
	}

	public Iterator iterator()
	{
		return new PrefixCodedTermsIterator();
	}

}
