// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MappingCharFilter.java

package org.apache.lucene.analysis.charfilter;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import org.apache.lucene.analysis.util.RollingCharBuffer;
import org.apache.lucene.util.CharsRef;
import org.apache.lucene.util.fst.*;

// Referenced classes of package org.apache.lucene.analysis.charfilter:
//			BaseCharFilter, NormalizeCharMap

public class MappingCharFilter extends BaseCharFilter
{

	private final Outputs outputs = CharSequenceOutputs.getSingleton();
	private final FST map;
	private final org.apache.lucene.util.fst.FST.BytesReader fstReader;
	private final RollingCharBuffer buffer = new RollingCharBuffer();
	private final org.apache.lucene.util.fst.FST.Arc scratchArc = new org.apache.lucene.util.fst.FST.Arc();
	private final Map cachedRootArcs;
	private CharsRef replacement;
	private int replacementPointer;
	private int inputOff;
	static final boolean $assertionsDisabled = !org/apache/lucene/analysis/charfilter/MappingCharFilter.desiredAssertionStatus();

	public MappingCharFilter(NormalizeCharMap normMap, Reader in)
	{
		super(in);
		buffer.reset(in);
		map = normMap.map;
		cachedRootArcs = normMap.cachedRootArcs;
		if (map != null)
			fstReader = map.getBytesReader(0);
		else
			fstReader = null;
	}

	public void reset()
		throws IOException
	{
		input.reset();
		buffer.reset(input);
		replacement = null;
		inputOff = 0;
	}

	public int read()
		throws IOException
	{
		do
		{
			if (replacement != null && replacementPointer < replacement.length)
				return replacement.chars[replacement.offset + replacementPointer++];
			int lastMatchLen = -1;
			CharsRef lastMatch = null;
			int firstCH = buffer.get(inputOff);
			if (firstCH != -1)
			{
				org.apache.lucene.util.fst.FST.Arc arc = (org.apache.lucene.util.fst.FST.Arc)cachedRootArcs.get(Character.valueOf((char)firstCH));
				if (arc != null)
					if (!FST.targetHasArcs(arc))
					{
						if (!$assertionsDisabled && !arc.isFinal())
							throw new AssertionError();
						lastMatchLen = 1;
						lastMatch = (CharsRef)arc.output;
					} else
					{
						int lookahead = 0;
						CharsRef output = (CharsRef)arc.output;
						do
						{
							lookahead++;
							if (arc.isFinal())
							{
								lastMatchLen = lookahead;
								lastMatch = (CharsRef)outputs.add(output, arc.nextFinalOutput);
							}
							if (!FST.targetHasArcs(arc))
								break;
							int ch = buffer.get(inputOff + lookahead);
							if (ch == -1 || (arc = map.findTargetArc(ch, arc, scratchArc, fstReader)) == null)
								break;
							output = (CharsRef)outputs.add(output, arc.output);
						} while (true);
					}
			}
			if (lastMatch == null)
				break;
			inputOff += lastMatchLen;
			int diff = lastMatchLen - lastMatch.length;
			if (diff != 0)
			{
				int prevCumulativeDiff = getLastCumulativeDiff();
				if (diff > 0)
				{
					addOffCorrectMap(inputOff - diff - prevCumulativeDiff, prevCumulativeDiff + diff);
				} else
				{
					int outputStart = inputOff - prevCumulativeDiff;
					for (int extraIDX = 0; extraIDX < -diff; extraIDX++)
						addOffCorrectMap(outputStart + extraIDX, prevCumulativeDiff - extraIDX - 1);

				}
			}
			replacement = lastMatch;
			replacementPointer = 0;
		} while (true);
		int ret = buffer.get(inputOff);
		if (ret != -1)
		{
			inputOff++;
			buffer.freeBefore(inputOff);
		}
		return ret;
	}

	public int read(char cbuf[], int off, int len)
		throws IOException
	{
		int numRead = 0;
		int i = off;
		do
		{
			if (i >= off + len)
				break;
			int c = read();
			if (c == -1)
				break;
			cbuf[i] = (char)c;
			numRead++;
			i++;
		} while (true);
		return numRead != 0 ? numRead : -1;
	}

}
