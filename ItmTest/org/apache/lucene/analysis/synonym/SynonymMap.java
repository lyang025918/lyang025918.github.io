// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SynonymMap.java

package org.apache.lucene.analysis.synonym;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.store.ByteArrayDataOutput;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefHash;
import org.apache.lucene.util.CharsRef;
import org.apache.lucene.util.IntsRef;
import org.apache.lucene.util.UnicodeUtil;
import org.apache.lucene.util.fst.ByteSequenceOutputs;
import org.apache.lucene.util.fst.FST;
import org.apache.lucene.util.fst.Util;

public class SynonymMap
{
	public static class Builder
	{
		private static class MapEntry
		{

			boolean includeOrig;
			ArrayList ords;

			private MapEntry()
			{
				ords = new ArrayList();
			}

		}


		private final HashMap workingSet = new HashMap();
		private final BytesRefHash words = new BytesRefHash();
		private final BytesRef utf8Scratch = new BytesRef(8);
		private int maxHorizontalContext;
		private final boolean dedup;
		static final boolean $assertionsDisabled = !org/apache/lucene/analysis/synonym/SynonymMap.desiredAssertionStatus();

		public static CharsRef join(String words[], CharsRef reuse)
		{
			int upto = 0;
			char buffer[] = reuse.chars;
			String arr$[] = words;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				String word = arr$[i$];
				if (upto > 0)
				{
					if (upto >= buffer.length)
					{
						reuse.grow(upto);
						buffer = reuse.chars;
					}
					buffer[upto++] = '\0';
				}
				int wordLen = word.length();
				int needed = upto + wordLen;
				if (needed > buffer.length)
				{
					reuse.grow(needed);
					buffer = reuse.chars;
				}
				word.getChars(0, wordLen, buffer, upto);
				upto += wordLen;
			}

			return reuse;
		}

		public static CharsRef analyze(Analyzer analyzer, String text, CharsRef reuse)
			throws IOException
		{
			TokenStream ts = analyzer.tokenStream("", new StringReader(text));
			CharTermAttribute termAtt = (CharTermAttribute)ts.addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
			PositionIncrementAttribute posIncAtt = (PositionIncrementAttribute)ts.addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
			ts.reset();
			int length;
			for (reuse.length = 0; ts.incrementToken(); reuse.length += length)
			{
				length = termAtt.length();
				if (length == 0)
					throw new IllegalArgumentException((new StringBuilder()).append("term: ").append(text).append(" analyzed to a zero-length token").toString());
				if (posIncAtt.getPositionIncrement() != 1)
					throw new IllegalArgumentException((new StringBuilder()).append("term: ").append(text).append(" analyzed to a token with posinc != 1").toString());
				reuse.grow(reuse.length + length + 1);
				int end = reuse.offset + reuse.length;
				if (reuse.length > 0)
				{
					reuse.chars[end++] = '\0';
					reuse.length++;
				}
				System.arraycopy(termAtt.buffer(), 0, reuse.chars, end, length);
			}

			ts.end();
			ts.close();
			if (reuse.length == 0)
				throw new IllegalArgumentException((new StringBuilder()).append("term: ").append(text).append(" was completely eliminated by analyzer").toString());
			else
				return reuse;
		}

		private boolean hasHoles(CharsRef chars)
		{
			int end = chars.offset + chars.length;
			for (int idx = chars.offset + 1; idx < end; idx++)
				if (chars.chars[idx] == 0 && chars.chars[idx - 1] == 0)
					return true;

			if (chars.chars[chars.offset] == 0)
				return true;
			return chars.chars[(chars.offset + chars.length) - 1] == 0;
		}

		private void add(CharsRef input, int numInputWords, CharsRef output, int numOutputWords, boolean includeOrig)
		{
			if (numInputWords <= 0)
				throw new IllegalArgumentException((new StringBuilder()).append("numInputWords must be > 0 (got ").append(numInputWords).append(")").toString());
			if (input.length <= 0)
				throw new IllegalArgumentException((new StringBuilder()).append("input.length must be > 0 (got ").append(input.length).append(")").toString());
			if (numOutputWords <= 0)
				throw new IllegalArgumentException((new StringBuilder()).append("numOutputWords must be > 0 (got ").append(numOutputWords).append(")").toString());
			if (output.length <= 0)
				throw new IllegalArgumentException((new StringBuilder()).append("output.length must be > 0 (got ").append(output.length).append(")").toString());
			if (!$assertionsDisabled && hasHoles(input))
				throw new AssertionError((new StringBuilder()).append("input has holes: ").append(input).toString());
			if (!$assertionsDisabled && hasHoles(output))
				throw new AssertionError((new StringBuilder()).append("output has holes: ").append(output).toString());
			int hashCode = UnicodeUtil.UTF16toUTF8WithHash(output.chars, output.offset, output.length, utf8Scratch);
			int ord = words.add(utf8Scratch, hashCode);
			if (ord < 0)
				ord = -ord - 1;
			MapEntry e = (MapEntry)workingSet.get(input);
			if (e == null)
			{
				e = new MapEntry();
				workingSet.put(CharsRef.deepCopyOf(input), e);
			}
			e.ords.add(Integer.valueOf(ord));
			e.includeOrig |= includeOrig;
			maxHorizontalContext = Math.max(maxHorizontalContext, numInputWords);
			maxHorizontalContext = Math.max(maxHorizontalContext, numOutputWords);
		}

		private int countWords(CharsRef chars)
		{
			int wordCount = 1;
			int upto = chars.offset;
			int limit = chars.offset + chars.length;
			do
			{
				if (upto >= limit)
					break;
				if (chars.chars[upto++] == 0)
					wordCount++;
			} while (true);
			return wordCount;
		}

		public void add(CharsRef input, CharsRef output, boolean includeOrig)
		{
			add(input, countWords(input), output, countWords(output), includeOrig);
		}

		public SynonymMap build()
			throws IOException
		{
			ByteSequenceOutputs outputs = ByteSequenceOutputs.getSingleton();
			org.apache.lucene.util.fst.Builder builder = new org.apache.lucene.util.fst.Builder(org.apache.lucene.util.fst.FST.INPUT_TYPE.BYTE4, outputs);
			BytesRef scratch = new BytesRef(64);
			ByteArrayDataOutput scratchOutput = new ByteArrayDataOutput();
			Set dedupSet;
			if (dedup)
				dedupSet = new HashSet();
			else
				dedupSet = null;
			byte spare[] = new byte[5];
			Set keys = workingSet.keySet();
			CharsRef sortedKeys[] = (CharsRef[])keys.toArray(new CharsRef[keys.size()]);
			Arrays.sort(sortedKeys, CharsRef.getUTF16SortedAsUTF8Comparator());
			IntsRef scratchIntsRef = new IntsRef();
			for (int keyIdx = 0; keyIdx < sortedKeys.length; keyIdx++)
			{
				CharsRef input = sortedKeys[keyIdx];
				MapEntry output = (MapEntry)workingSet.get(input);
				int numEntries = output.ords.size();
				int estimatedSize = 5 + numEntries * 5;
				scratch.grow(estimatedSize);
				scratchOutput.reset(scratch.bytes, scratch.offset, scratch.bytes.length);
				if (!$assertionsDisabled && scratch.offset != 0)
					throw new AssertionError();
				int count = 0;
				for (int i = 0; i < numEntries; i++)
				{
					if (dedupSet != null)
					{
						Integer ent = (Integer)output.ords.get(i);
						if (dedupSet.contains(ent))
							continue;
						dedupSet.add(ent);
					}
					scratchOutput.writeVInt(((Integer)output.ords.get(i)).intValue());
					count++;
				}

				int pos = scratchOutput.getPosition();
				scratchOutput.writeVInt(count << 1 | (output.includeOrig ? 0 : 1));
				int pos2 = scratchOutput.getPosition();
				int vIntLen = pos2 - pos;
				System.arraycopy(scratch.bytes, pos, spare, 0, vIntLen);
				System.arraycopy(scratch.bytes, 0, scratch.bytes, vIntLen, pos);
				System.arraycopy(spare, 0, scratch.bytes, 0, vIntLen);
				if (dedupSet != null)
					dedupSet.clear();
				scratch.length = scratchOutput.getPosition() - scratch.offset;
				builder.add(Util.toUTF32(input, scratchIntsRef), BytesRef.deepCopyOf(scratch));
			}

			FST fst = builder.finish();
			return new SynonymMap(fst, words, maxHorizontalContext);
		}


		public Builder(boolean dedup)
		{
			this.dedup = dedup;
		}
	}


	public static final char WORD_SEPARATOR = 0;
	public final FST fst;
	public final BytesRefHash words;
	public final int maxHorizontalContext;

	public SynonymMap(FST fst, BytesRefHash words, int maxHorizontalContext)
	{
		this.fst = fst;
		this.words = words;
		this.maxHorizontalContext = maxHorizontalContext;
	}
}
