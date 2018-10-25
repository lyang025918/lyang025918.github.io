// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CharacterUtils.java

package org.apache.lucene.analysis.util;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.util.Version;

public abstract class CharacterUtils
{
	public static final class CharacterBuffer
	{

		private final char buffer[];
		private int offset;
		private int length;
		char lastTrailingHighSurrogate;

		public char[] getBuffer()
		{
			return buffer;
		}

		public int getOffset()
		{
			return offset;
		}

		public int getLength()
		{
			return length;
		}

		public void reset()
		{
			offset = 0;
			length = 0;
			lastTrailingHighSurrogate = '\0';
		}







		CharacterBuffer(char buffer[], int offset, int length)
		{
			this.buffer = buffer;
			this.offset = offset;
			this.length = length;
		}
	}

	private static final class Java4CharacterUtils extends CharacterUtils
	{

		public int codePointAt(char chars[], int offset)
		{
			return chars[offset];
		}

		public int codePointAt(CharSequence seq, int offset)
		{
			return seq.charAt(offset);
		}

		public int codePointAt(char chars[], int offset, int limit)
		{
			if (offset >= limit)
				throw new IndexOutOfBoundsException("offset must be less than limit");
			else
				return chars[offset];
		}

		public boolean fill(CharacterBuffer buffer, Reader reader)
			throws IOException
		{
			buffer.offset = 0;
			int read = reader.read(buffer.buffer);
			if (read == -1)
			{
				return false;
			} else
			{
				buffer.length = read;
				return true;
			}
		}

		Java4CharacterUtils()
		{
		}
	}

	private static final class Java5CharacterUtils extends CharacterUtils
	{

		static final boolean $assertionsDisabled = !org/apache/lucene/analysis/util/CharacterUtils.desiredAssertionStatus();

		public int codePointAt(char chars[], int offset)
		{
			return Character.codePointAt(chars, offset);
		}

		public int codePointAt(CharSequence seq, int offset)
		{
			return Character.codePointAt(seq, offset);
		}

		public int codePointAt(char chars[], int offset, int limit)
		{
			return Character.codePointAt(chars, offset, limit);
		}

		public boolean fill(CharacterBuffer buffer, Reader reader)
			throws IOException
		{
			char charBuffer[] = buffer.buffer;
			buffer.offset = 0;
			int offset;
			if (buffer.lastTrailingHighSurrogate != 0)
			{
				charBuffer[0] = buffer.lastTrailingHighSurrogate;
				offset = 1;
			} else
			{
				offset = 0;
			}
			int read = reader.read(charBuffer, offset, charBuffer.length - offset);
			if (read == -1)
			{
				buffer.length = offset;
				buffer.lastTrailingHighSurrogate = '\0';
				return offset != 0;
			}
			if (!$assertionsDisabled && read <= 0)
				throw new AssertionError();
			buffer.length = read + offset;
			if (buffer.length == 1 && Character.isHighSurrogate(charBuffer[buffer.length - 1]))
			{
				int read2 = reader.read(charBuffer, 1, charBuffer.length - 1);
				if (read2 == -1)
					return true;
				if (!$assertionsDisabled && read2 <= 0)
					throw new AssertionError();
				buffer.length+= = read2;
			}
			if (buffer.length > 1 && Character.isHighSurrogate(charBuffer[buffer.length - 1]))
				buffer.lastTrailingHighSurrogate = charBuffer[--buffer.length];
			else
				buffer.lastTrailingHighSurrogate = '\0';
			return true;
		}


		Java5CharacterUtils()
		{
		}
	}


	private static final Java4CharacterUtils JAVA_4 = new Java4CharacterUtils();
	private static final Java5CharacterUtils JAVA_5 = new Java5CharacterUtils();

	public CharacterUtils()
	{
	}

	public static CharacterUtils getInstance(Version matchVersion)
	{
		return ((CharacterUtils) (matchVersion.onOrAfter(Version.LUCENE_31) ? JAVA_5 : JAVA_4));
	}

	public abstract int codePointAt(char ac[], int i);

	public abstract int codePointAt(CharSequence charsequence, int i);

	public abstract int codePointAt(char ac[], int i, int j);

	public static CharacterBuffer newCharacterBuffer(int bufferSize)
	{
		if (bufferSize < 2)
			throw new IllegalArgumentException("buffersize must be >= 2");
		else
			return new CharacterBuffer(new char[bufferSize], 0, 0);
	}

	public abstract boolean fill(CharacterBuffer characterbuffer, Reader reader)
		throws IOException;

}
