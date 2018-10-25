// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WordlistLoader.java

package org.apache.lucene.analysis.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.util.IOUtils;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.util:
//			CharArraySet, CharArrayMap

public class WordlistLoader
{

	private static final int INITIAL_CAPACITY = 16;

	private WordlistLoader()
	{
	}

	public static CharArraySet getWordSet(Reader reader, CharArraySet result)
		throws IOException
	{
		BufferedReader br = null;
		br = getBufferedReader(reader);
		for (String word = null; (word = br.readLine()) != null;)
			result.add(word.trim());

		IOUtils.close(new Closeable[] {
			br
		});
		break MISSING_BLOCK_LABEL_60;
		Exception exception;
		exception;
		IOUtils.close(new Closeable[] {
			br
		});
		throw exception;
		return result;
	}

	public static CharArraySet getWordSet(Reader reader, Version matchVersion)
		throws IOException
	{
		return getWordSet(reader, new CharArraySet(matchVersion, 16, false));
	}

	public static CharArraySet getWordSet(Reader reader, String comment, Version matchVersion)
		throws IOException
	{
		return getWordSet(reader, comment, new CharArraySet(matchVersion, 16, false));
	}

	public static CharArraySet getWordSet(Reader reader, String comment, CharArraySet result)
		throws IOException
	{
		BufferedReader br = null;
		br = getBufferedReader(reader);
		String word = null;
		do
		{
			if ((word = br.readLine()) == null)
				break;
			if (!word.startsWith(comment))
				result.add(word.trim());
		} while (true);
		IOUtils.close(new Closeable[] {
			br
		});
		break MISSING_BLOCK_LABEL_72;
		Exception exception;
		exception;
		IOUtils.close(new Closeable[] {
			br
		});
		throw exception;
		return result;
	}

	public static CharArraySet getSnowballWordSet(Reader reader, CharArraySet result)
		throws IOException
	{
		BufferedReader br = null;
		br = getBufferedReader(reader);
		for (String line = null; (line = br.readLine()) != null;)
		{
			int comment = line.indexOf('|');
			if (comment >= 0)
				line = line.substring(0, comment);
			String words[] = line.split("\\s+");
			int i = 0;
			while (i < words.length) 
			{
				if (words[i].length() > 0)
					result.add(words[i]);
				i++;
			}
		}

		IOUtils.close(new Closeable[] {
			br
		});
		break MISSING_BLOCK_LABEL_118;
		Exception exception;
		exception;
		IOUtils.close(new Closeable[] {
			br
		});
		throw exception;
		return result;
	}

	public static CharArraySet getSnowballWordSet(Reader reader, Version matchVersion)
		throws IOException
	{
		return getSnowballWordSet(reader, new CharArraySet(matchVersion, 16, false));
	}

	public static CharArrayMap getStemDict(Reader reader, CharArrayMap result)
		throws IOException
	{
		BufferedReader br = null;
		br = getBufferedReader(reader);
		String line;
		while ((line = br.readLine()) != null) 
		{
			String wordstem[] = line.split("\t", 2);
			result.put(wordstem[0], wordstem[1]);
		}
		IOUtils.close(new Closeable[] {
			br
		});
		break MISSING_BLOCK_LABEL_71;
		Exception exception;
		exception;
		IOUtils.close(new Closeable[] {
			br
		});
		throw exception;
		return result;
	}

	public static List getLines(InputStream stream, Charset charset)
		throws IOException
	{
		BufferedReader input;
		boolean success;
		input = null;
		success = false;
		String word;
		input = getBufferedReader(IOUtils.getDecodingReader(stream, charset));
		ArrayList lines = new ArrayList();
		word = null;
		do
		{
			if ((word = input.readLine()) == null)
				break;
			if (lines.isEmpty() && word.length() > 0 && word.charAt(0) == '\uFEFF')
				word = word.substring(1);
			if (!word.startsWith("#"))
			{
				word = word.trim();
				if (word.length() != 0)
					lines.add(word);
			}
		} while (true);
		success = true;
		word = lines;
		if (success)
			IOUtils.close(new Closeable[] {
				input
			});
		else
			IOUtils.closeWhileHandlingException(new Closeable[] {
				input
			});
		return word;
		Exception exception;
		exception;
		if (success)
			IOUtils.close(new Closeable[] {
				input
			});
		else
			IOUtils.closeWhileHandlingException(new Closeable[] {
				input
			});
		throw exception;
	}

	private static BufferedReader getBufferedReader(Reader reader)
	{
		return (reader instanceof BufferedReader) ? (BufferedReader)reader : new BufferedReader(reader);
	}
}
