// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WordnetSynonymParser.java

package org.apache.lucene.analysis.synonym;

import java.io.*;
import java.text.ParseException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.CharsRef;

// Referenced classes of package org.apache.lucene.analysis.synonym:
//			SynonymMap

public class WordnetSynonymParser extends SynonymMap.Builder
{

	private final boolean expand;
	private final Analyzer analyzer;

	public WordnetSynonymParser(boolean dedup, boolean expand, Analyzer analyzer)
	{
		super(dedup);
		this.expand = expand;
		this.analyzer = analyzer;
	}

	public void add(Reader in)
		throws IOException, ParseException
	{
		Exception exception;
		LineNumberReader br = new LineNumberReader(in);
		try
		{
			String line = null;
			String lastSynSetID = "";
			CharsRef synset[] = new CharsRef[8];
			int synsetSize = 0;
			while ((line = br.readLine()) != null) 
			{
				String synSetID = line.substring(2, 11);
				if (!synSetID.equals(lastSynSetID))
				{
					addInternal(synset, synsetSize);
					synsetSize = 0;
				}
				if (synset.length <= synsetSize + 1)
				{
					CharsRef larger[] = new CharsRef[synset.length * 2];
					System.arraycopy(synset, 0, larger, 0, synsetSize);
					synset = larger;
				}
				synset[synsetSize] = parseSynonym(line, synset[synsetSize]);
				synsetSize++;
				lastSynSetID = synSetID;
			}
			addInternal(synset, synsetSize);
		}
		catch (IllegalArgumentException e)
		{
			ParseException ex = new ParseException((new StringBuilder()).append("Invalid synonym rule at line ").append(br.getLineNumber()).toString(), 0);
			ex.initCause(e);
			throw ex;
		}
		finally
		{
			br.close();
		}
		br.close();
		break MISSING_BLOCK_LABEL_191;
		throw exception;
	}

	private CharsRef parseSynonym(String line, CharsRef reuse)
		throws IOException
	{
		if (reuse == null)
			reuse = new CharsRef(8);
		int start = line.indexOf('\'') + 1;
		int end = line.lastIndexOf('\'');
		String text = line.substring(start, end).replace("''", "'");
		return analyze(analyzer, text, reuse);
	}

	private void addInternal(CharsRef synset[], int size)
	{
		if (size <= 1)
			return;
		if (expand)
		{
			for (int i = 0; i < size; i++)
			{
				for (int j = 0; j < size; j++)
					add(synset[i], synset[j], false);

			}

		} else
		{
			for (int i = 0; i < size; i++)
				add(synset[i], synset[0], false);

		}
	}
}
