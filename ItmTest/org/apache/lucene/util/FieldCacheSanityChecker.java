// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldCacheSanityChecker.java

package org.apache.lucene.util;

import java.util.*;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexReaderContext;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.store.AlreadyClosedException;

// Referenced classes of package org.apache.lucene.util:
//			MapOfSets, Bits

public final class FieldCacheSanityChecker
{
	public static final class InsanityType
	{

		private final String label;
		public static final InsanityType SUBREADER = new InsanityType("SUBREADER");
		public static final InsanityType VALUEMISMATCH = new InsanityType("VALUEMISMATCH");
		public static final InsanityType EXPECTED = new InsanityType("EXPECTED");

		public String toString()
		{
			return label;
		}


		private InsanityType(String label)
		{
			this.label = label;
		}
	}

	public static final class Insanity
	{

		private final InsanityType type;
		private final String msg;
		private final org.apache.lucene.search.FieldCache.CacheEntry entries[];

		public InsanityType getType()
		{
			return type;
		}

		public String getMsg()
		{
			return msg;
		}

		public org.apache.lucene.search.FieldCache.CacheEntry[] getCacheEntries()
		{
			return entries;
		}

		public String toString()
		{
			StringBuilder buf = new StringBuilder();
			buf.append(getType()).append(": ");
			String m = getMsg();
			if (null != m)
				buf.append(m);
			buf.append('\n');
			org.apache.lucene.search.FieldCache.CacheEntry ce[] = getCacheEntries();
			for (int i = 0; i < ce.length; i++)
				buf.append('\t').append(ce[i].toString()).append('\n');

			return buf.toString();
		}

		public transient Insanity(InsanityType type, String msg, org.apache.lucene.search.FieldCache.CacheEntry entries[])
		{
			if (null == type)
				throw new IllegalArgumentException("Insanity requires non-null InsanityType");
			if (null == entries || 0 == entries.length)
			{
				throw new IllegalArgumentException("Insanity requires non-null/non-empty CacheEntry[]");
			} else
			{
				this.type = type;
				this.msg = msg;
				this.entries = entries;
				return;
			}
		}
	}

	private static final class ReaderField
	{

		public final Object readerKey;
		public final String fieldName;

		public int hashCode()
		{
			return System.identityHashCode(readerKey) * fieldName.hashCode();
		}

		public boolean equals(Object that)
		{
			if (!(that instanceof ReaderField))
			{
				return false;
			} else
			{
				ReaderField other = (ReaderField)that;
				return readerKey == other.readerKey && fieldName.equals(other.fieldName);
			}
		}

		public String toString()
		{
			return (new StringBuilder()).append(readerKey.toString()).append("+").append(fieldName).toString();
		}

		public ReaderField(Object readerKey, String fieldName)
		{
			this.readerKey = readerKey;
			this.fieldName = fieldName;
		}
	}


	private boolean estimateRam;

	public FieldCacheSanityChecker()
	{
	}

	public void setRamUsageEstimator(boolean flag)
	{
		estimateRam = flag;
	}

	public static Insanity[] checkSanity(FieldCache cache)
	{
		return checkSanity(cache.getCacheEntries());
	}

	public static transient Insanity[] checkSanity(org.apache.lucene.search.FieldCache.CacheEntry cacheEntries[])
	{
		FieldCacheSanityChecker sanityChecker = new FieldCacheSanityChecker();
		sanityChecker.setRamUsageEstimator(true);
		return sanityChecker.check(cacheEntries);
	}

	public transient Insanity[] check(org.apache.lucene.search.FieldCache.CacheEntry cacheEntries[])
	{
		if (null == cacheEntries || 0 == cacheEntries.length)
			return new Insanity[0];
		if (estimateRam)
		{
			for (int i = 0; i < cacheEntries.length; i++)
				cacheEntries[i].estimateSize();

		}
		MapOfSets valIdToItems = new MapOfSets(new HashMap(17));
		MapOfSets readerFieldToValIds = new MapOfSets(new HashMap(17));
		Set valMismatchKeys = new HashSet();
		for (int i = 0; i < cacheEntries.length; i++)
		{
			org.apache.lucene.search.FieldCache.CacheEntry item = cacheEntries[i];
			Object val = item.getValue();
			if ((val instanceof Bits) || (val instanceof org.apache.lucene.search.FieldCache.CreationPlaceholder))
				continue;
			ReaderField rf = new ReaderField(item.getReaderKey(), item.getFieldName());
			Integer valId = Integer.valueOf(System.identityHashCode(val));
			valIdToItems.put(valId, item);
			if (1 < readerFieldToValIds.put(rf, valId))
				valMismatchKeys.add(rf);
		}

		List insanity = new ArrayList(valMismatchKeys.size() * 3);
		insanity.addAll(checkValueMismatch(valIdToItems, readerFieldToValIds, valMismatchKeys));
		insanity.addAll(checkSubreaders(valIdToItems, readerFieldToValIds));
		return (Insanity[])insanity.toArray(new Insanity[insanity.size()]);
	}

	private Collection checkValueMismatch(MapOfSets valIdToItems, MapOfSets readerFieldToValIds, Set valMismatchKeys)
	{
		List insanity = new ArrayList(valMismatchKeys.size() * 3);
		if (!valMismatchKeys.isEmpty())
		{
			Map rfMap = readerFieldToValIds.getMap();
			Map valMap = valIdToItems.getMap();
			ReaderField rf;
			org.apache.lucene.search.FieldCache.CacheEntry badness[];
			for (Iterator i$ = valMismatchKeys.iterator(); i$.hasNext(); insanity.add(new Insanity(InsanityType.VALUEMISMATCH, (new StringBuilder()).append("Multiple distinct value objects for ").append(rf.toString()).toString(), badness)))
			{
				rf = (ReaderField)i$.next();
				List badEntries = new ArrayList(valMismatchKeys.size() * 2);
				for (Iterator i$ = ((Set)rfMap.get(rf)).iterator(); i$.hasNext();)
				{
					Integer value = (Integer)i$.next();
					Iterator i$ = ((Set)valMap.get(value)).iterator();
					while (i$.hasNext()) 
					{
						org.apache.lucene.search.FieldCache.CacheEntry cacheEntry = (org.apache.lucene.search.FieldCache.CacheEntry)i$.next();
						badEntries.add(cacheEntry);
					}
				}

				badness = new org.apache.lucene.search.FieldCache.CacheEntry[badEntries.size()];
				badness = (org.apache.lucene.search.FieldCache.CacheEntry[])badEntries.toArray(badness);
			}

		}
		return insanity;
	}

	private Collection checkSubreaders(MapOfSets valIdToItems, MapOfSets readerFieldToValIds)
	{
		List insanity = new ArrayList(23);
		Map badChildren = new HashMap(17);
		MapOfSets badKids = new MapOfSets(badChildren);
		Map viToItemSets = valIdToItems.getMap();
		Map rfToValIdSets = readerFieldToValIds.getMap();
		Set seen = new HashSet(17);
		Set readerFields = rfToValIdSets.keySet();
		Iterator i$ = readerFields.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			ReaderField rf = (ReaderField)i$.next();
			if (!seen.contains(rf))
			{
				List kids = getAllDescendantReaderKeys(rf.readerKey);
				ReaderField kid;
				for (Iterator i$ = kids.iterator(); i$.hasNext(); seen.add(kid))
				{
					Object kidKey = i$.next();
					kid = new ReaderField(kidKey, rf.fieldName);
					if (badChildren.containsKey(kid))
					{
						badKids.put(rf, kid);
						badKids.putAll(rf, (Collection)badChildren.get(kid));
						badChildren.remove(kid);
						continue;
					}
					if (rfToValIdSets.containsKey(kid))
						badKids.put(rf, kid);
				}

				seen.add(rf);
			}
		} while (true);
		ReaderField parent;
		org.apache.lucene.search.FieldCache.CacheEntry badness[];
		for (i$ = badChildren.keySet().iterator(); i$.hasNext(); insanity.add(new Insanity(InsanityType.SUBREADER, (new StringBuilder()).append("Found caches for descendants of ").append(parent.toString()).toString(), badness)))
		{
			parent = (ReaderField)i$.next();
			Set kids = (Set)badChildren.get(parent);
			List badEntries = new ArrayList(kids.size() * 2);
			Integer value;
			for (Iterator i$ = ((Set)rfToValIdSets.get(parent)).iterator(); i$.hasNext(); badEntries.addAll((Collection)viToItemSets.get(value)))
				value = (Integer)i$.next();

			for (Iterator i$ = kids.iterator(); i$.hasNext();)
			{
				ReaderField kid = (ReaderField)i$.next();
				Iterator i$ = ((Set)rfToValIdSets.get(kid)).iterator();
				while (i$.hasNext()) 
				{
					Integer value = (Integer)i$.next();
					badEntries.addAll((Collection)viToItemSets.get(value));
				}
			}

			badness = new org.apache.lucene.search.FieldCache.CacheEntry[badEntries.size()];
			badness = (org.apache.lucene.search.FieldCache.CacheEntry[])badEntries.toArray(badness);
		}

		return insanity;
	}

	private List getAllDescendantReaderKeys(Object seed)
	{
		List all = new ArrayList(17);
		all.add(seed);
		for (int i = 0; i < all.size(); i++)
		{
			Object obj = all.get(i);
			if (!(obj instanceof IndexReader))
				continue;
			try
			{
				List childs = ((IndexReader)obj).getContext().children();
				if (childs == null)
					continue;
				IndexReaderContext ctx;
				for (Iterator i$ = childs.iterator(); i$.hasNext(); all.add(ctx.reader().getCoreCacheKey()))
					ctx = (IndexReaderContext)i$.next();

			}
			catch (AlreadyClosedException ace) { }
		}

		return all.subList(1, all.size());
	}
}
