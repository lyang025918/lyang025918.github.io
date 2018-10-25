// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SlowSynonymFilter.java

package org.apache.lucene.analysis.synonym;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.tokenattributes.*;
import org.apache.lucene.analysis.util.CharArrayMap;
import org.apache.lucene.util.AttributeSource;

// Referenced classes of package org.apache.lucene.analysis.synonym:
//			SlowSynonymMap

/**
 * @deprecated Class SlowSynonymFilter is deprecated
 */

final class SlowSynonymFilter extends TokenFilter
{

	private final SlowSynonymMap map;
	private Iterator replacement;
	private LinkedList buffer;
	private LinkedList matched;
	private boolean exhausted;

	public SlowSynonymFilter(TokenStream in, SlowSynonymMap map)
	{
		super(in);
		if (map == null)
		{
			throw new IllegalArgumentException("map is required");
		} else
		{
			this.map = map;
			addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
			addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
			addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
			addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
			return;
		}
	}

	public boolean incrementToken()
		throws IOException
	{
		do
		{
			if (replacement != null && replacement.hasNext())
			{
				copy(this, (AttributeSource)replacement.next());
				return true;
			}
			AttributeSource firstTok = nextTok();
			if (firstTok == null)
				return false;
			CharTermAttribute termAtt = (CharTermAttribute)firstTok.addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
			SlowSynonymMap result = map.submap == null ? null : (SlowSynonymMap)map.submap.get(termAtt.buffer(), 0, termAtt.length());
			if (result == null)
			{
				copy(this, firstTok);
				return true;
			}
			if (firstTok == this)
				firstTok = cloneAttributes();
			matched = new LinkedList();
			result = match(result);
			if (result == null)
			{
				copy(this, firstTok);
				return true;
			}
			ArrayList generated = new ArrayList(result.synonyms.length + matched.size() + 1);
			AttributeSource lastTok = matched.isEmpty() ? firstTok : (AttributeSource)matched.getLast();
			boolean includeOrig = result.includeOrig();
			AttributeSource origTok = includeOrig ? firstTok : null;
			PositionIncrementAttribute firstPosIncAtt = (PositionIncrementAttribute)firstTok.addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
			int origPos = firstPosIncAtt.getPositionIncrement();
			int repPos = 0;
			int pos = 0;
			for (int i = 0; i < result.synonyms.length; i++)
			{
				Token repTok = result.synonyms[i];
				AttributeSource newTok = firstTok.cloneAttributes();
				CharTermAttribute newTermAtt = (CharTermAttribute)newTok.addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
				OffsetAttribute newOffsetAtt = (OffsetAttribute)newTok.addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
				PositionIncrementAttribute newPosIncAtt = (PositionIncrementAttribute)newTok.addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
				OffsetAttribute lastOffsetAtt = (OffsetAttribute)lastTok.addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
				newOffsetAtt.setOffset(newOffsetAtt.startOffset(), lastOffsetAtt.endOffset());
				newTermAtt.copyBuffer(repTok.buffer(), 0, repTok.length());
				repPos += repTok.getPositionIncrement();
				if (i == 0)
					repPos = origPos;
				do
				{
					if (origTok == null || origPos > repPos)
						break;
					PositionIncrementAttribute origPosInc = (PositionIncrementAttribute)origTok.addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
					origPosInc.setPositionIncrement(origPos - pos);
					generated.add(origTok);
					pos += origPosInc.getPositionIncrement();
					origTok = matched.isEmpty() ? null : (AttributeSource)matched.removeFirst();
					if (origTok != null)
					{
						origPosInc = (PositionIncrementAttribute)origTok.addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
						origPos += origPosInc.getPositionIncrement();
					}
				} while (true);
				newPosIncAtt.setPositionIncrement(repPos - pos);
				generated.add(newTok);
				pos += newPosIncAtt.getPositionIncrement();
			}

			do
			{
				if (origTok == null)
					break;
				PositionIncrementAttribute origPosInc = (PositionIncrementAttribute)origTok.addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
				origPosInc.setPositionIncrement(origPos - pos);
				generated.add(origTok);
				pos += origPosInc.getPositionIncrement();
				origTok = matched.isEmpty() ? null : (AttributeSource)matched.removeFirst();
				if (origTok != null)
				{
					origPosInc = (PositionIncrementAttribute)origTok.addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
					origPos += origPosInc.getPositionIncrement();
				}
			} while (true);
			replacement = generated.iterator();
		} while (true);
	}

	private AttributeSource nextTok()
		throws IOException
	{
		if (buffer != null && !buffer.isEmpty())
			return (AttributeSource)buffer.removeFirst();
		if (!exhausted && input.incrementToken())
		{
			return this;
		} else
		{
			exhausted = true;
			return null;
		}
	}

	private void pushTok(AttributeSource t)
	{
		if (buffer == null)
			buffer = new LinkedList();
		buffer.addFirst(t);
	}

	private SlowSynonymMap match(SlowSynonymMap map)
		throws IOException
	{
		SlowSynonymMap result = null;
		if (map.submap != null)
		{
			AttributeSource tok = nextTok();
			if (tok != null)
			{
				if (tok == this)
					tok = cloneAttributes();
				CharTermAttribute termAtt = (CharTermAttribute)tok.getAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
				SlowSynonymMap subMap = (SlowSynonymMap)map.submap.get(termAtt.buffer(), 0, termAtt.length());
				if (subMap != null)
					result = match(subMap);
				if (result != null)
					matched.addFirst(tok);
				else
					pushTok(tok);
			}
		}
		if (result == null && map.synonyms != null)
			result = map;
		return result;
	}

	private void copy(AttributeSource target, AttributeSource source)
	{
		if (target != source)
			source.copyTo(target);
	}

	public void reset()
		throws IOException
	{
		input.reset();
		replacement = null;
		exhausted = false;
	}
}
