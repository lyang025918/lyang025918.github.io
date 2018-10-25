// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FastCharStream.java

package org.apache.lucene.queryparser.classic;

import java.io.IOException;
import java.io.Reader;

// Referenced classes of package org.apache.lucene.queryparser.classic:
//			CharStream

public final class FastCharStream
	implements CharStream
{

	char buffer[];
	int bufferLength;
	int bufferPosition;
	int tokenStart;
	int bufferStart;
	Reader input;

	public FastCharStream(Reader r)
	{
		buffer = null;
		bufferLength = 0;
		bufferPosition = 0;
		tokenStart = 0;
		bufferStart = 0;
		input = r;
	}

	public final char readChar()
		throws IOException
	{
		if (bufferPosition >= bufferLength)
			refill();
		return buffer[bufferPosition++];
	}

	private final void refill()
		throws IOException
	{
		int newPosition = bufferLength - tokenStart;
		if (tokenStart == 0)
		{
			if (buffer == null)
				buffer = new char[2048];
			else
			if (bufferLength == buffer.length)
			{
				char newBuffer[] = new char[buffer.length * 2];
				System.arraycopy(buffer, 0, newBuffer, 0, bufferLength);
				buffer = newBuffer;
			}
		} else
		{
			System.arraycopy(buffer, tokenStart, buffer, 0, newPosition);
		}
		bufferLength = newPosition;
		bufferPosition = newPosition;
		bufferStart += tokenStart;
		tokenStart = 0;
		int charsRead = input.read(buffer, newPosition, buffer.length - newPosition);
		if (charsRead == -1)
		{
			throw new IOException("read past eof");
		} else
		{
			bufferLength += charsRead;
			return;
		}
	}

	public final char BeginToken()
		throws IOException
	{
		tokenStart = bufferPosition;
		return readChar();
	}

	public final void backup(int amount)
	{
		bufferPosition -= amount;
	}

	public final String GetImage()
	{
		return new String(buffer, tokenStart, bufferPosition - tokenStart);
	}

	public final char[] GetSuffix(int len)
	{
		char value[] = new char[len];
		System.arraycopy(buffer, bufferPosition - len, value, 0, len);
		return value;
	}

	public final void Done()
	{
		try
		{
			input.close();
		}
		catch (IOException e) { }
	}

	public final int getColumn()
	{
		return bufferStart + bufferPosition;
	}

	public final int getLine()
	{
		return 1;
	}

	public final int getEndColumn()
	{
		return bufferStart + bufferPosition;
	}

	public final int getEndLine()
	{
		return 1;
	}

	public final int getBeginColumn()
	{
		return bufferStart + tokenStart;
	}

	public final int getBeginLine()
	{
		return 1;
	}
}
