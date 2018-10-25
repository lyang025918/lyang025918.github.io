// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NormalizeCharMap.java

package org.apache.lucene.analysis.charfilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.lucene.util.CharsRef;
import org.apache.lucene.util.IntsRef;
import org.apache.lucene.util.fst.CharSequenceOutputs;
import org.apache.lucene.util.fst.FST;
import org.apache.lucene.util.fst.Outputs;
import org.apache.lucene.util.fst.Util;

public class NormalizeCharMap
{
	public static class Builder
	{

		private final Map pendingPairs = new TreeMap();

		public void add(String match, String replacement)
		{
			if (match.length() == 0)
				throw new IllegalArgumentException("cannot match the empty string");
			if (pendingPairs.containsKey(match))
			{
				throw new IllegalArgumentException((new StringBuilder()).append("match \"").append(match).append("\" was already added").toString());
			} else
			{
				pendingPairs.put(match, replacement);
				return;
			}
		}

		public NormalizeCharMap build()
		{
			FST map;
			try
			{
				Outputs outputs = CharSequenceOutputs.getSingleton();
				org.apache.lucene.util.fst.Builder builder = new org.apache.lucene.util.fst.Builder(org.apache.lucene.util.fst.FST.INPUT_TYPE.BYTE2, outputs);
				IntsRef scratch = new IntsRef();
				java.util.Map.Entry ent;
				for (Iterator i$ = pendingPairs.entrySet().iterator(); i$.hasNext(); builder.add(Util.toUTF16((CharSequence)ent.getKey(), scratch), new CharsRef((String)ent.getValue())))
					ent = (java.util.Map.Entry)i$.next();

				map = builder.finish();
				pendingPairs.clear();
			}
			catch (IOException ioe)
			{
				throw new RuntimeException(ioe);
			}
			return new NormalizeCharMap(map);
		}

		public Builder()
		{
		}
	}


	final FST map;
	final Map cachedRootArcs;
	static final boolean $assertionsDisabled = !org/apache/lucene/analysis/charfilter/NormalizeCharMap.desiredAssertionStatus();

	private NormalizeCharMap(FST map)
	{
		cachedRootArcs = new HashMap();
		this.map = map;
		if (map != null)
			try
			{
				org.apache.lucene.util.fst.FST.Arc scratchArc = new org.apache.lucene.util.fst.FST.Arc();
				org.apache.lucene.util.fst.FST.BytesReader fstReader = map.getBytesReader(0);
				map.getFirstArc(scratchArc);
				if (FST.targetHasArcs(scratchArc))
				{
					map.readFirstRealTargetArc(scratchArc.target, scratchArc, fstReader);
					do
					{
						if (!$assertionsDisabled && scratchArc.label == -1)
							throw new AssertionError();
						cachedRootArcs.put(Character.valueOf((char)scratchArc.label), (new org.apache.lucene.util.fst.FST.Arc()).copyFrom(scratchArc));
						if (scratchArc.isLast())
							break;
						map.readNextRealArc(scratchArc, fstReader);
					} while (true);
				}
			}
			catch (IOException ioe)
			{
				throw new RuntimeException(ioe);
			}
	}


}
