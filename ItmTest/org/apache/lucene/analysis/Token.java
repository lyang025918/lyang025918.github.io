// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Token.java

package org.apache.lucene.analysis;

import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.CharTermAttributeImpl;
import org.apache.lucene.analysis.tokenattributes.FlagsAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionLengthAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.*;

public class Token extends CharTermAttributeImpl
	implements TypeAttribute, PositionIncrementAttribute, FlagsAttribute, OffsetAttribute, PayloadAttribute, PositionLengthAttribute
{
	public static final class TokenAttributeFactory extends org.apache.lucene.util.AttributeSource.AttributeFactory
	{

		private final org.apache.lucene.util.AttributeSource.AttributeFactory delegate;

		public AttributeImpl createAttributeInstance(Class attClass)
		{
			return ((AttributeImpl) (attClass.isAssignableFrom(org/apache/lucene/analysis/Token) ? new Token() : delegate.createAttributeInstance(attClass)));
		}

		public boolean equals(Object other)
		{
			if (this == other)
				return true;
			if (other instanceof TokenAttributeFactory)
			{
				TokenAttributeFactory af = (TokenAttributeFactory)other;
				return delegate.equals(af.delegate);
			} else
			{
				return false;
			}
		}

		public int hashCode()
		{
			return delegate.hashCode() ^ 0xa45aa31;
		}

		public TokenAttributeFactory(org.apache.lucene.util.AttributeSource.AttributeFactory delegate)
		{
			this.delegate = delegate;
		}
	}


	private int startOffset;
	private int endOffset;
	private String type;
	private int flags;
	private BytesRef payload;
	private int positionIncrement;
	private int positionLength;
	public static final org.apache.lucene.util.AttributeSource.AttributeFactory TOKEN_ATTRIBUTE_FACTORY;

	public Token()
	{
		type = "word";
		positionIncrement = 1;
		positionLength = 1;
	}

	public Token(int start, int end)
	{
		type = "word";
		positionIncrement = 1;
		positionLength = 1;
		checkOffsets(start, end);
		startOffset = start;
		endOffset = end;
	}

	public Token(int start, int end, String typ)
	{
		type = "word";
		positionIncrement = 1;
		positionLength = 1;
		checkOffsets(start, end);
		startOffset = start;
		endOffset = end;
		type = typ;
	}

	public Token(int start, int end, int flags)
	{
		type = "word";
		positionIncrement = 1;
		positionLength = 1;
		checkOffsets(start, end);
		startOffset = start;
		endOffset = end;
		this.flags = flags;
	}

	public Token(String text, int start, int end)
	{
		type = "word";
		positionIncrement = 1;
		positionLength = 1;
		checkOffsets(start, end);
		append(text);
		startOffset = start;
		endOffset = end;
	}

	public Token(String text, int start, int end, String typ)
	{
		type = "word";
		positionIncrement = 1;
		positionLength = 1;
		checkOffsets(start, end);
		append(text);
		startOffset = start;
		endOffset = end;
		type = typ;
	}

	public Token(String text, int start, int end, int flags)
	{
		type = "word";
		positionIncrement = 1;
		positionLength = 1;
		checkOffsets(start, end);
		append(text);
		startOffset = start;
		endOffset = end;
		this.flags = flags;
	}

	public Token(char startTermBuffer[], int termBufferOffset, int termBufferLength, int start, int end)
	{
		type = "word";
		positionIncrement = 1;
		positionLength = 1;
		checkOffsets(start, end);
		copyBuffer(startTermBuffer, termBufferOffset, termBufferLength);
		startOffset = start;
		endOffset = end;
	}

	public void setPositionIncrement(int positionIncrement)
	{
		if (positionIncrement < 0)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("Increment must be zero or greater: ").append(positionIncrement).toString());
		} else
		{
			this.positionIncrement = positionIncrement;
			return;
		}
	}

	public int getPositionIncrement()
	{
		return positionIncrement;
	}

	public void setPositionLength(int positionLength)
	{
		this.positionLength = positionLength;
	}

	public int getPositionLength()
	{
		return positionLength;
	}

	public final int startOffset()
	{
		return startOffset;
	}

	public final int endOffset()
	{
		return endOffset;
	}

	public void setOffset(int startOffset, int endOffset)
	{
		checkOffsets(startOffset, endOffset);
		this.startOffset = startOffset;
		this.endOffset = endOffset;
	}

	public final String type()
	{
		return type;
	}

	public final void setType(String type)
	{
		this.type = type;
	}

	public int getFlags()
	{
		return flags;
	}

	public void setFlags(int flags)
	{
		this.flags = flags;
	}

	public BytesRef getPayload()
	{
		return payload;
	}

	public void setPayload(BytesRef payload)
	{
		this.payload = payload;
	}

	public void clear()
	{
		super.clear();
		payload = null;
		positionIncrement = 1;
		flags = 0;
		startOffset = endOffset = 0;
		type = "word";
	}

	public Token clone()
	{
		Token t = (Token)super.clone();
		if (payload != null)
			t.payload = payload.clone();
		return t;
	}

	public Token clone(char newTermBuffer[], int newTermOffset, int newTermLength, int newStartOffset, int newEndOffset)
	{
		Token t = new Token(newTermBuffer, newTermOffset, newTermLength, newStartOffset, newEndOffset);
		t.positionIncrement = positionIncrement;
		t.flags = flags;
		t.type = type;
		if (payload != null)
			t.payload = payload.clone();
		return t;
	}

	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;
		if (obj instanceof Token)
		{
			Token other = (Token)obj;
			return startOffset == other.startOffset && endOffset == other.endOffset && flags == other.flags && positionIncrement == other.positionIncrement && (type != null ? type.equals(other.type) : other.type == null) && (payload != null ? payload.equals(other.payload) : other.payload == null) && super.equals(obj);
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		int code = super.hashCode();
		code = code * 31 + startOffset;
		code = code * 31 + endOffset;
		code = code * 31 + flags;
		code = code * 31 + positionIncrement;
		if (type != null)
			code = code * 31 + type.hashCode();
		if (payload != null)
			code = code * 31 + payload.hashCode();
		return code;
	}

	private void clearNoTermBuffer()
	{
		payload = null;
		positionIncrement = 1;
		flags = 0;
		startOffset = endOffset = 0;
		type = "word";
	}

	public Token reinit(char newTermBuffer[], int newTermOffset, int newTermLength, int newStartOffset, int newEndOffset, String newType)
	{
		checkOffsets(newStartOffset, newEndOffset);
		clearNoTermBuffer();
		copyBuffer(newTermBuffer, newTermOffset, newTermLength);
		payload = null;
		positionIncrement = 1;
		startOffset = newStartOffset;
		endOffset = newEndOffset;
		type = newType;
		return this;
	}

	public Token reinit(char newTermBuffer[], int newTermOffset, int newTermLength, int newStartOffset, int newEndOffset)
	{
		checkOffsets(newStartOffset, newEndOffset);
		clearNoTermBuffer();
		copyBuffer(newTermBuffer, newTermOffset, newTermLength);
		startOffset = newStartOffset;
		endOffset = newEndOffset;
		type = "word";
		return this;
	}

	public Token reinit(String newTerm, int newStartOffset, int newEndOffset, String newType)
	{
		checkOffsets(newStartOffset, newEndOffset);
		clear();
		append(newTerm);
		startOffset = newStartOffset;
		endOffset = newEndOffset;
		type = newType;
		return this;
	}

	public Token reinit(String newTerm, int newTermOffset, int newTermLength, int newStartOffset, int newEndOffset, String newType)
	{
		checkOffsets(newStartOffset, newEndOffset);
		clear();
		append(newTerm, newTermOffset, newTermOffset + newTermLength);
		startOffset = newStartOffset;
		endOffset = newEndOffset;
		type = newType;
		return this;
	}

	public Token reinit(String newTerm, int newStartOffset, int newEndOffset)
	{
		checkOffsets(newStartOffset, newEndOffset);
		clear();
		append(newTerm);
		startOffset = newStartOffset;
		endOffset = newEndOffset;
		type = "word";
		return this;
	}

	public Token reinit(String newTerm, int newTermOffset, int newTermLength, int newStartOffset, int newEndOffset)
	{
		checkOffsets(newStartOffset, newEndOffset);
		clear();
		append(newTerm, newTermOffset, newTermOffset + newTermLength);
		startOffset = newStartOffset;
		endOffset = newEndOffset;
		type = "word";
		return this;
	}

	public void reinit(Token prototype)
	{
		copyBuffer(prototype.buffer(), 0, prototype.length());
		positionIncrement = prototype.positionIncrement;
		flags = prototype.flags;
		startOffset = prototype.startOffset;
		endOffset = prototype.endOffset;
		type = prototype.type;
		payload = prototype.payload;
	}

	public void reinit(Token prototype, String newTerm)
	{
		setEmpty().append(newTerm);
		positionIncrement = prototype.positionIncrement;
		flags = prototype.flags;
		startOffset = prototype.startOffset;
		endOffset = prototype.endOffset;
		type = prototype.type;
		payload = prototype.payload;
	}

	public void reinit(Token prototype, char newTermBuffer[], int offset, int length)
	{
		copyBuffer(newTermBuffer, offset, length);
		positionIncrement = prototype.positionIncrement;
		flags = prototype.flags;
		startOffset = prototype.startOffset;
		endOffset = prototype.endOffset;
		type = prototype.type;
		payload = prototype.payload;
	}

	public void copyTo(AttributeImpl target)
	{
		if (target instanceof Token)
		{
			Token to = (Token)target;
			to.reinit(this);
			if (payload != null)
				to.payload = payload.clone();
		} else
		{
			super.copyTo(target);
			((OffsetAttribute)target).setOffset(startOffset, endOffset);
			((PositionIncrementAttribute)target).setPositionIncrement(positionIncrement);
			((PayloadAttribute)target).setPayload(payload != null ? payload.clone() : null);
			((FlagsAttribute)target).setFlags(flags);
			((TypeAttribute)target).setType(type);
		}
	}

	public void reflectWith(AttributeReflector reflector)
	{
		super.reflectWith(reflector);
		reflector.reflect(org/apache/lucene/analysis/tokenattributes/OffsetAttribute, "startOffset", Integer.valueOf(startOffset));
		reflector.reflect(org/apache/lucene/analysis/tokenattributes/OffsetAttribute, "endOffset", Integer.valueOf(endOffset));
		reflector.reflect(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute, "positionIncrement", Integer.valueOf(positionIncrement));
		reflector.reflect(org/apache/lucene/analysis/tokenattributes/PayloadAttribute, "payload", payload);
		reflector.reflect(org/apache/lucene/analysis/tokenattributes/FlagsAttribute, "flags", Integer.valueOf(flags));
		reflector.reflect(org/apache/lucene/analysis/tokenattributes/TypeAttribute, "type", type);
	}

	private void checkOffsets(int startOffset, int endOffset)
	{
		if (startOffset < 0 || endOffset < startOffset)
			throw new IllegalArgumentException((new StringBuilder()).append("startOffset must be non-negative, and endOffset must be >= startOffset, startOffset=").append(startOffset).append(",endOffset=").append(endOffset).toString());
		else
			return;
	}

	public volatile CharTermAttributeImpl clone()
	{
		return clone();
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

	static 
	{
		TOKEN_ATTRIBUTE_FACTORY = new TokenAttributeFactory(org.apache.lucene.util.AttributeSource.AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY);
	}
}
