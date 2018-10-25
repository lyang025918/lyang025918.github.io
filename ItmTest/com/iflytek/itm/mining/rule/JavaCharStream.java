// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JavaCharStream.java

package com.iflytek.itm.mining.rule;

import java.io.*;

public class JavaCharStream
{

	public static final boolean staticFlag = false;
	public int bufpos;
	int bufsize;
	int available;
	int tokenBegin;
	protected int bufline[];
	protected int bufcolumn[];
	protected int column;
	protected int line;
	protected boolean prevCharIsCR;
	protected boolean prevCharIsLF;
	protected Reader inputStream;
	protected char nextCharBuf[];
	protected char buffer[];
	protected int maxNextCharInd;
	protected int nextCharInd;
	protected int inBuf;
	protected int tabSize;

	static final int hexval(char c)
		throws IOException
	{
		switch (c)
		{
		case 48: // '0'
			return 0;

		case 49: // '1'
			return 1;

		case 50: // '2'
			return 2;

		case 51: // '3'
			return 3;

		case 52: // '4'
			return 4;

		case 53: // '5'
			return 5;

		case 54: // '6'
			return 6;

		case 55: // '7'
			return 7;

		case 56: // '8'
			return 8;

		case 57: // '9'
			return 9;

		case 65: // 'A'
		case 97: // 'a'
			return 10;

		case 66: // 'B'
		case 98: // 'b'
			return 11;

		case 67: // 'C'
		case 99: // 'c'
			return 12;

		case 68: // 'D'
		case 100: // 'd'
			return 13;

		case 69: // 'E'
		case 101: // 'e'
			return 14;

		case 70: // 'F'
		case 102: // 'f'
			return 15;

		case 58: // ':'
		case 59: // ';'
		case 60: // '<'
		case 61: // '='
		case 62: // '>'
		case 63: // '?'
		case 64: // '@'
		case 71: // 'G'
		case 72: // 'H'
		case 73: // 'I'
		case 74: // 'J'
		case 75: // 'K'
		case 76: // 'L'
		case 77: // 'M'
		case 78: // 'N'
		case 79: // 'O'
		case 80: // 'P'
		case 81: // 'Q'
		case 82: // 'R'
		case 83: // 'S'
		case 84: // 'T'
		case 85: // 'U'
		case 86: // 'V'
		case 87: // 'W'
		case 88: // 'X'
		case 89: // 'Y'
		case 90: // 'Z'
		case 91: // '['
		case 92: // '\\'
		case 93: // ']'
		case 94: // '^'
		case 95: // '_'
		case 96: // '`'
		default:
			throw new IOException();
		}
	}

	protected void setTabSize(int i)
	{
		tabSize = i;
	}

	protected int getTabSize(int i)
	{
		return tabSize;
	}

	protected void ExpandBuff(boolean wrapAround)
	{
		char newbuffer[] = new char[bufsize + 2048];
		int newbufline[] = new int[bufsize + 2048];
		int newbufcolumn[] = new int[bufsize + 2048];
		try
		{
			if (wrapAround)
			{
				System.arraycopy(buffer, tokenBegin, newbuffer, 0, bufsize - tokenBegin);
				System.arraycopy(buffer, 0, newbuffer, bufsize - tokenBegin, bufpos);
				buffer = newbuffer;
				System.arraycopy(bufline, tokenBegin, newbufline, 0, bufsize - tokenBegin);
				System.arraycopy(bufline, 0, newbufline, bufsize - tokenBegin, bufpos);
				bufline = newbufline;
				System.arraycopy(bufcolumn, tokenBegin, newbufcolumn, 0, bufsize - tokenBegin);
				System.arraycopy(bufcolumn, 0, newbufcolumn, bufsize - tokenBegin, bufpos);
				bufcolumn = newbufcolumn;
				bufpos += bufsize - tokenBegin;
			} else
			{
				System.arraycopy(buffer, tokenBegin, newbuffer, 0, bufsize - tokenBegin);
				buffer = newbuffer;
				System.arraycopy(bufline, tokenBegin, newbufline, 0, bufsize - tokenBegin);
				bufline = newbufline;
				System.arraycopy(bufcolumn, tokenBegin, newbufcolumn, 0, bufsize - tokenBegin);
				bufcolumn = newbufcolumn;
				bufpos -= tokenBegin;
			}
		}
		catch (Throwable t)
		{
			throw new Error(t.getMessage());
		}
		available = bufsize += 2048;
		tokenBegin = 0;
	}

	protected void FillBuff()
		throws IOException
	{
		if (maxNextCharInd == 4096)
			maxNextCharInd = nextCharInd = 0;
		int i;
		try
		{
			if ((i = inputStream.read(nextCharBuf, maxNextCharInd, 4096 - maxNextCharInd)) == -1)
			{
				inputStream.close();
				throw new IOException();
			} else
			{
				maxNextCharInd += i;
				return;
			}
		}
		catch (IOException e)
		{
			if (bufpos != 0)
			{
				bufpos--;
				backup(0);
			} else
			{
				bufline[bufpos] = line;
				bufcolumn[bufpos] = column;
			}
			throw e;
		}
	}

	protected char ReadByte()
		throws IOException
	{
		if (++nextCharInd >= maxNextCharInd)
			FillBuff();
		return nextCharBuf[nextCharInd];
	}

	public char BeginToken()
		throws IOException
	{
		if (inBuf > 0)
		{
			inBuf--;
			if (++bufpos == bufsize)
				bufpos = 0;
			tokenBegin = bufpos;
			return buffer[bufpos];
		} else
		{
			tokenBegin = 0;
			bufpos = -1;
			return readChar();
		}
	}

	protected void AdjustBuffSize()
	{
		if (available == bufsize)
		{
			if (tokenBegin > 2048)
			{
				bufpos = 0;
				available = tokenBegin;
			} else
			{
				ExpandBuff(false);
			}
		} else
		if (available > tokenBegin)
			available = bufsize;
		else
		if (tokenBegin - available < 2048)
			ExpandBuff(true);
		else
			available = tokenBegin;
	}

	protected void UpdateLineColumn(char c)
	{
		column++;
		if (prevCharIsLF)
		{
			prevCharIsLF = false;
			line += column = 1;
		} else
		if (prevCharIsCR)
		{
			prevCharIsCR = false;
			if (c == '\n')
				prevCharIsLF = true;
			else
				line += column = 1;
		}
		switch (c)
		{
		case 13: // '\r'
			prevCharIsCR = true;
			break;

		case 10: // '\n'
			prevCharIsLF = true;
			break;

		case 9: // '\t'
			column--;
			column += tabSize - column % tabSize;
			break;
		}
		bufline[bufpos] = line;
		bufcolumn[bufpos] = column;
	}

	public char readChar()
		throws IOException
	{
		char c;
		int backSlashCnt;
		if (inBuf > 0)
		{
			inBuf--;
			if (++bufpos == bufsize)
				bufpos = 0;
			return buffer[bufpos];
		}
		if (++bufpos == available)
			AdjustBuffSize();
		if ((buffer[bufpos] = c = ReadByte()) != '\\')
			break MISSING_BLOCK_LABEL_376;
		UpdateLineColumn(c);
		backSlashCnt = 1;
_L2:
label0:
		{
			if (++bufpos == available)
				AdjustBuffSize();
			try
			{
				if ((buffer[bufpos] = c = ReadByte()) == '\\')
					break label0;
				UpdateLineColumn(c);
				if (c == 'u' && (backSlashCnt & 1) == 1)
				{
					if (--bufpos < 0)
						bufpos = bufsize - 1;
					break; /* Loop/switch isn't completed */
				}
			}
			catch (IOException e)
			{
				if (backSlashCnt > 1)
					backup(backSlashCnt - 1);
				return '\\';
			}
		}
		backup(backSlashCnt);
		return '\\';
		UpdateLineColumn(c);
		backSlashCnt++;
		if (true) goto _L2; else goto _L1
_L1:
		try
		{
			while ((c = ReadByte()) == 'u') 
				column++;
			buffer[bufpos] = c = (char)(hexval(c) << 12 | hexval(ReadByte()) << 8 | hexval(ReadByte()) << 4 | hexval(ReadByte()));
			column += 4;
		}
		catch (IOException e)
		{
			throw new Error((new StringBuilder()).append("Invalid escape character at line ").append(line).append(" column ").append(column).append(".").toString());
		}
		if (backSlashCnt == 1)
		{
			return c;
		} else
		{
			backup(backSlashCnt - 1);
			return '\\';
		}
		UpdateLineColumn(c);
		return c;
	}

	/**
	 * @deprecated Method getColumn is deprecated
	 */

	public int getColumn()
	{
		return bufcolumn[bufpos];
	}

	/**
	 * @deprecated Method getLine is deprecated
	 */

	public int getLine()
	{
		return bufline[bufpos];
	}

	public int getEndColumn()
	{
		return bufcolumn[bufpos];
	}

	public int getEndLine()
	{
		return bufline[bufpos];
	}

	public int getBeginColumn()
	{
		return bufcolumn[tokenBegin];
	}

	public int getBeginLine()
	{
		return bufline[tokenBegin];
	}

	public void backup(int amount)
	{
		inBuf += amount;
		if ((bufpos -= amount) < 0)
			bufpos += bufsize;
	}

	public JavaCharStream(Reader dstream, int startline, int startcolumn, int buffersize)
	{
		bufpos = -1;
		column = 0;
		line = 1;
		prevCharIsCR = false;
		prevCharIsLF = false;
		maxNextCharInd = 0;
		nextCharInd = -1;
		inBuf = 0;
		tabSize = 8;
		inputStream = dstream;
		line = startline;
		column = startcolumn - 1;
		available = bufsize = buffersize;
		buffer = new char[buffersize];
		bufline = new int[buffersize];
		bufcolumn = new int[buffersize];
		nextCharBuf = new char[4096];
	}

	public JavaCharStream(Reader dstream, int startline, int startcolumn)
	{
		this(dstream, startline, startcolumn, 4096);
	}

	public JavaCharStream(Reader dstream)
	{
		this(dstream, 1, 1, 4096);
	}

	public void ReInit(Reader dstream, int startline, int startcolumn, int buffersize)
	{
		inputStream = dstream;
		line = startline;
		column = startcolumn - 1;
		if (buffer == null || buffersize != buffer.length)
		{
			available = bufsize = buffersize;
			buffer = new char[buffersize];
			bufline = new int[buffersize];
			bufcolumn = new int[buffersize];
			nextCharBuf = new char[4096];
		}
		prevCharIsLF = prevCharIsCR = false;
		tokenBegin = inBuf = maxNextCharInd = 0;
		nextCharInd = bufpos = -1;
	}

	public void ReInit(Reader dstream, int startline, int startcolumn)
	{
		ReInit(dstream, startline, startcolumn, 4096);
	}

	public void ReInit(Reader dstream)
	{
		ReInit(dstream, 1, 1, 4096);
	}

	public JavaCharStream(InputStream dstream, String encoding, int startline, int startcolumn, int buffersize)
		throws UnsupportedEncodingException
	{
		this(((Reader) (encoding != null ? ((Reader) (new InputStreamReader(dstream, encoding))) : ((Reader) (new InputStreamReader(dstream))))), startline, startcolumn, buffersize);
	}

	public JavaCharStream(InputStream dstream, int startline, int startcolumn, int buffersize)
	{
		this(((Reader) (new InputStreamReader(dstream))), startline, startcolumn, 4096);
	}

	public JavaCharStream(InputStream dstream, String encoding, int startline, int startcolumn)
		throws UnsupportedEncodingException
	{
		this(dstream, encoding, startline, startcolumn, 4096);
	}

	public JavaCharStream(InputStream dstream, int startline, int startcolumn)
	{
		this(dstream, startline, startcolumn, 4096);
	}

	public JavaCharStream(InputStream dstream, String encoding)
		throws UnsupportedEncodingException
	{
		this(dstream, encoding, 1, 1, 4096);
	}

	public JavaCharStream(InputStream dstream)
	{
		this(dstream, 1, 1, 4096);
	}

	public void ReInit(InputStream dstream, String encoding, int startline, int startcolumn, int buffersize)
		throws UnsupportedEncodingException
	{
		ReInit(((Reader) (encoding != null ? ((Reader) (new InputStreamReader(dstream, encoding))) : ((Reader) (new InputStreamReader(dstream))))), startline, startcolumn, buffersize);
	}

	public void ReInit(InputStream dstream, int startline, int startcolumn, int buffersize)
	{
		ReInit(((Reader) (new InputStreamReader(dstream))), startline, startcolumn, buffersize);
	}

	public void ReInit(InputStream dstream, String encoding, int startline, int startcolumn)
		throws UnsupportedEncodingException
	{
		ReInit(dstream, encoding, startline, startcolumn, 4096);
	}

	public void ReInit(InputStream dstream, int startline, int startcolumn)
	{
		ReInit(dstream, startline, startcolumn, 4096);
	}

	public void ReInit(InputStream dstream, String encoding)
		throws UnsupportedEncodingException
	{
		ReInit(dstream, encoding, 1, 1, 4096);
	}

	public void ReInit(InputStream dstream)
	{
		ReInit(dstream, 1, 1, 4096);
	}

	public String GetImage()
	{
		if (bufpos >= tokenBegin)
			return new String(buffer, tokenBegin, (bufpos - tokenBegin) + 1);
		else
			return (new StringBuilder()).append(new String(buffer, tokenBegin, bufsize - tokenBegin)).append(new String(buffer, 0, bufpos + 1)).toString();
	}

	public char[] GetSuffix(int len)
	{
		char ret[] = new char[len];
		if (bufpos + 1 >= len)
		{
			System.arraycopy(buffer, (bufpos - len) + 1, ret, 0, len);
		} else
		{
			System.arraycopy(buffer, bufsize - (len - bufpos - 1), ret, 0, len - bufpos - 1);
			System.arraycopy(buffer, 0, ret, len - bufpos - 1, bufpos + 1);
		}
		return ret;
	}

	public void Done()
	{
		nextCharBuf = null;
		buffer = null;
		bufline = null;
		bufcolumn = null;
	}

	public void adjustBeginLineColumn(int newLine, int newCol)
	{
		int start = tokenBegin;
		int len;
		if (bufpos >= tokenBegin)
			len = (bufpos - tokenBegin) + inBuf + 1;
		else
			len = (bufsize - tokenBegin) + bufpos + 1 + inBuf;
		int i = 0;
		int j = 0;
		int k = 0;
		int nextColDiff = 0;
		int columnDiff = 0;
		for (; i < len && bufline[j = start % bufsize] == bufline[k = ++start % bufsize]; i++)
		{
			bufline[j] = newLine;
			nextColDiff = (columnDiff + bufcolumn[k]) - bufcolumn[j];
			bufcolumn[j] = newCol + columnDiff;
			columnDiff = nextColDiff;
		}

		if (i < len)
		{
			bufline[j] = newLine++;
			bufcolumn[j] = newCol + columnDiff;
			while (i++ < len) 
				if (bufline[j = start % bufsize] != bufline[++start % bufsize])
					bufline[j] = newLine++;
				else
					bufline[j] = newLine;
		}
		line = bufline[j];
		column = bufcolumn[j];
	}
}
