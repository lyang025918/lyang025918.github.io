// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CharTermAttributeImpl.java

package org.apache.lucene.analysis.tokenattributes;

import java.io.IOException;
import java.nio.CharBuffer;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.analysis.tokenattributes:
//			CharTermAttribute, TermToBytesRefAttribute

public class CharTermAttributeImpl extends AttributeImpl
	implements CharTermAttribute, TermToBytesRefAttribute, Cloneable
{

	private static int MIN_BUFFER_SIZE = 10;
	private char termBuffer[];
	private int termLength;
	private BytesRef bytes;

	public CharTermAttributeImpl()
	{
		termBuffer = new char[ArrayUtil.oversize(MIN_BUFFER_SIZE, 2)];
		termLength = 0;
		bytes = new BytesRef(MIN_BUFFER_SIZE);
	}

	public final void copyBuffer(char buffer[], int offset, int length)
	{
		growTermBuffer(length);
		System.arraycopy(buffer, offset, termBuffer, 0, length);
		termLength = length;
	}

	public final char[] buffer()
	{
		return termBuffer;
	}

	public final char[] resizeBuffer(int newSize)
	{
		if (termBuffer.length < newSize)
		{
			char newCharBuffer[] = new char[ArrayUtil.oversize(newSize, 2)];
			System.arraycopy(termBuffer, 0, newCharBuffer, 0, termBuffer.length);
			termBuffer = newCharBuffer;
		}
		return termBuffer;
	}

	private void growTermBuffer(int newSize)
	{
		if (termBuffer.length < newSize)
			termBuffer = new char[ArrayUtil.oversize(newSize, 2)];
	}

	public final CharTermAttribute setLength(int length)
	{
		if (length > termBuffer.length)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("length ").append(length).append(" exceeds the size of the termBuffer (").append(termBuffer.length).append(")").toString());
		} else
		{
			termLength = length;
			return this;
		}
	}

	public final CharTermAttribute setEmpty()
	{
		termLength = 0;
		return this;
	}

	public int fillBytesRef()
	{
		return UnicodeUtil.UTF16toUTF8WithHash(termBuffer, 0, termLength, bytes);
	}

	public BytesRef getBytesRef()
	{
		return bytes;
	}

	public final int length()
	{
		return termLength;
	}

	public final char charAt(int index)
	{
		if (index >= termLength)
			throw new IndexOutOfBoundsException();
		else
			return termBuffer[index];
	}

	public final CharSequence subSequence(int start, int end)
	{
		if (start > termLength || end > termLength)
			throw new IndexOutOfBoundsException();
		else
			return new String(termBuffer, start, end - start);
	}

	public final CharTermAttribute append(CharSequence csq)
	{
		if (csq == null)
			return appendNull();
		else
			return append(csq, 0, csq.length());
	}

	public final CharTermAttribute append(CharSequence csq, int start, int end)
	{
		if (csq == null)
			csq = "null";
		int len = end - start;
		int csqlen = csq.length();
		if (len < 0 || start > csqlen || end > csqlen)
			throw new IndexOutOfBoundsException();
		if (len == 0)
			return this;
		resizeBuffer(termLength + len);
		if (len > 4)
		{
			if (csq instanceof String)
				((String)csq).getChars(start, end, termBuffer, termLength);
			else
			if (csq instanceof StringBuilder)
				((StringBuilder)csq).getChars(start, end, termBuffer, termLength);
			else
			if (csq instanceof CharTermAttribute)
				System.arraycopy(((CharTermAttribute)csq).buffer(), start, termBuffer, termLength, len);
			else
			if ((csq instanceof CharBuffer) && ((CharBuffer)csq).hasArray())
			{
				CharBuffer cb = (CharBuffer)csq;
				System.arraycopy(cb.array(), cb.arrayOffset() + cb.position() + start, termBuffer, termLength, len);
			} else
			if (csq instanceof StringBuffer)
			{
				((StringBuffer)csq).getChars(start, end, termBuffer, termLength);
			} else
			{
				while (start < end) 
					termBuffer[termLength++] = csq.charAt(start++);
				return this;
			}
			termLength += len;
			return this;
		}
		while (start < end) 
			termBuffer[termLength++] = csq.charAt(start++);
		return this;
	}

	public final CharTermAttribute append(char c)
	{
		resizeBuffer(termLength + 1)[termLength++] = c;
		return this;
	}

	public final CharTermAttribute append(String s)
	{
		if (s == null)
		{
			return appendNull();
		} else
		{
			int len = s.length();
			s.getChars(0, len, resizeBuffer(termLength + len), termLength);
			termLength += len;
			return this;
		}
	}

	public final CharTermAttribute append(StringBuilder s)
	{
		if (s == null)
		{
			return appendNull();
		} else
		{
			int len = s.length();
			s.getChars(0, len, resizeBuffer(termLength + len), termLength);
			termLength += len;
			return this;
		}
	}

	public final CharTermAttribute append(CharTermAttribute ta)
	{
		if (ta == null)
		{
			return appendNull();
		} else
		{
			int len = ta.length();
			System.arraycopy(ta.buffer(), 0, resizeBuffer(termLength + len), termLength, len);
			termLength += len;
			return this;
		}
	}

	private CharTermAttribute appendNull()
	{
		resizeBuffer(termLength + 4);
		termBuffer[termLength++] = 'n';
		termBuffer[termLength++] = 'u';
		termBuffer[termLength++] = 'l';
		termBuffer[termLength++] = 'l';
		return this;
	}

	public int hashCode()
	{
		int code = termLength;
		code = code * 31 + ArrayUtil.hashCode(termBuffer, 0, termLength);
		return code;
	}

	public void clear()
	{
		termLength = 0;
	}

	public CharTermAttributeImpl clone()
	{
		CharTermAttributeImpl t = (CharTermAttributeImpl)super.clone();
		t.termBuffer = new char[termLength];
		System.arraycopy(termBuffer, 0, t.termBuffer, 0, termLength);
		t.bytes = BytesRef.deepCopyOf(bytes);
		return t;
	}

	public boolean equals(Object other)
	{
		if (other == this)
			return true;
		if (other instanceof CharTermAttributeImpl)
		{
			CharTermAttributeImpl o = (CharTermAttributeImpl)other;
			if (termLength != o.termLength)
				return false;
			for (int i = 0; i < termLength; i++)
				if (termBuffer[i] != o.termBuffer[i])
					return false;

			return true;
		} else
		{
			return false;
		}
	}

	public String toString()
	{
		return new String(termBuffer, 0, termLength);
	}

	public void reflectWith(AttributeReflector reflector)
	{
		reflector.reflect(org/apache/lucene/analysis/tokenattributes/CharTermAttribute, "term", toString());
		fillBytesRef();
		reflector.reflect(org/apache/lucene/analysis/tokenattributes/TermToBytesRefAttribute, "bytes", BytesRef.deepCopyOf(bytes));
	}

	public void copyTo(AttributeImpl target)
	{
		CharTermAttribute t = (CharTermAttribute)target;
		t.copyBuffer(termBuffer, 0, termLength);
	}

	public volatile AttributeImpl clone()
	{
		return clone();
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}

	public volatile Appendable append(char x0)
		throws IOException
	{
		return append(x0);
	}

	public volatile Appendable append(CharSequence x0, int x1, int x2)
		throws IOException
	{
		return append(x0, x1, x2);
	}

	public volatile Appendable append(CharSequence x0)
		throws IOException
	{
		return append(x0);
	}

}
