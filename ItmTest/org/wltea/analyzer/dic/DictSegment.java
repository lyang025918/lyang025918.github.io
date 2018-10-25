// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DictSegment.java

package org.wltea.analyzer.dic;

import java.util.*;

// Referenced classes of package org.wltea.analyzer.dic:
//			Hit

class DictSegment
	implements Comparable
{

	private static final Map charMap = new HashMap(16, 0.95F);
	private static final int ARRAY_LENGTH_LIMIT = 3;
	private Map childrenMap;
	private DictSegment childrenArray[];
	private Character nodeChar;
	private int storeSize;
	private int nodeState;

	DictSegment(Character nodeChar)
	{
		storeSize = 0;
		nodeState = 0;
		if (nodeChar == null)
		{
			throw new IllegalArgumentException("参数为空异常，字符不能为空");
		} else
		{
			this.nodeChar = nodeChar;
			return;
		}
	}

	Character getNodeChar()
	{
		return nodeChar;
	}

	boolean hasNextNode()
	{
		return storeSize > 0;
	}

	Hit match(char charArray[])
	{
		return match(charArray, 0, charArray.length, null);
	}

	Hit match(char charArray[], int begin, int length)
	{
		return match(charArray, begin, length, null);
	}

	Hit match(char charArray[], int begin, int length, Hit searchHit)
	{
		if (searchHit == null)
		{
			searchHit = new Hit();
			searchHit.setBegin(begin);
		} else
		{
			searchHit.setUnmatch();
		}
		searchHit.setEnd(begin);
		Character keyChar = new Character(charArray[begin]);
		DictSegment ds = null;
		DictSegment segmentArray[] = childrenArray;
		Map segmentMap = childrenMap;
		if (segmentArray != null)
		{
			DictSegment keySegment = new DictSegment(keyChar);
			int position = Arrays.binarySearch(segmentArray, 0, storeSize, keySegment);
			if (position >= 0)
				ds = segmentArray[position];
		} else
		if (segmentMap != null)
			ds = (DictSegment)segmentMap.get(keyChar);
		if (ds != null)
		{
			if (length > 1)
				return ds.match(charArray, begin + 1, length - 1, searchHit);
			if (length == 1)
			{
				if (ds.nodeState == 1)
					searchHit.setMatch();
				if (ds.hasNextNode())
				{
					searchHit.setPrefix();
					searchHit.setMatchedDictSegment(ds);
				}
				return searchHit;
			}
		}
		return searchHit;
	}

	void fillSegment(char charArray[])
	{
		fillSegment(charArray, 0, charArray.length, 1);
	}

	void disableSegment(char charArray[])
	{
		fillSegment(charArray, 0, charArray.length, 0);
	}

	private synchronized void fillSegment(char charArray[], int begin, int length, int enabled)
	{
		Character beginChar = new Character(charArray[begin]);
		Character keyChar = (Character)charMap.get(beginChar);
		if (keyChar == null)
		{
			charMap.put(beginChar, beginChar);
			keyChar = beginChar;
		}
		DictSegment ds = lookforSegment(keyChar, enabled);
		if (ds != null)
			if (length > 1)
				ds.fillSegment(charArray, begin + 1, length - 1, enabled);
			else
			if (length == 1)
				ds.nodeState = enabled;
	}

	private DictSegment lookforSegment(Character keyChar, int create)
	{
		DictSegment ds = null;
		if (storeSize <= 3)
		{
			DictSegment segmentArray[] = getChildrenArray();
			DictSegment keySegment = new DictSegment(keyChar);
			int position = Arrays.binarySearch(segmentArray, 0, storeSize, keySegment);
			if (position >= 0)
				ds = segmentArray[position];
			if (ds == null && create == 1)
			{
				ds = keySegment;
				if (storeSize < 3)
				{
					segmentArray[storeSize] = ds;
					storeSize++;
					Arrays.sort(segmentArray, 0, storeSize);
				} else
				{
					Map segmentMap = getChildrenMap();
					migrate(segmentArray, segmentMap);
					segmentMap.put(keyChar, ds);
					storeSize++;
					childrenArray = null;
				}
			}
		} else
		{
			Map segmentMap = getChildrenMap();
			ds = (DictSegment)segmentMap.get(keyChar);
			if (ds == null && create == 1)
			{
				ds = new DictSegment(keyChar);
				segmentMap.put(keyChar, ds);
				storeSize++;
			}
		}
		return ds;
	}

	private DictSegment[] getChildrenArray()
	{
		if (childrenArray == null)
			synchronized (this)
			{
				if (childrenArray == null)
					childrenArray = new DictSegment[3];
			}
		return childrenArray;
	}

	private Map getChildrenMap()
	{
		if (childrenMap == null)
			synchronized (this)
			{
				if (childrenMap == null)
					childrenMap = new HashMap(6, 0.8F);
			}
		return childrenMap;
	}

	private void migrate(DictSegment segmentArray[], Map segmentMap)
	{
		DictSegment adictsegment[];
		int j = (adictsegment = segmentArray).length;
		for (int i = 0; i < j; i++)
		{
			DictSegment segment = adictsegment[i];
			if (segment != null)
				segmentMap.put(segment.nodeChar, segment);
		}

	}

	public int compareTo(DictSegment o)
	{
		return nodeChar.compareTo(o.nodeChar);
	}

	public volatile int compareTo(Object obj)
	{
		return compareTo((DictSegment)obj);
	}

}
