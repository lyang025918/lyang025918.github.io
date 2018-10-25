// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CoalescedDeletes.java

package org.apache.lucene.index;

import java.util.*;
import org.apache.lucene.search.Query;

// Referenced classes of package org.apache.lucene.index:
//			FrozenBufferedDeletes, BufferedDeletes, BufferedDeletesStream, MergedIterator

class CoalescedDeletes
{

	final Map queries = new HashMap();
	final List iterables = new ArrayList();

	CoalescedDeletes()
	{
	}

	public String toString()
	{
		return (new StringBuilder()).append("CoalescedDeletes(termSets=").append(iterables.size()).append(",queries=").append(queries.size()).append(")").toString();
	}

	void update(FrozenBufferedDeletes in)
	{
		iterables.add(in.termsIterable());
		for (int queryIdx = 0; queryIdx < in.queries.length; queryIdx++)
		{
			Query query = in.queries[queryIdx];
			queries.put(query, BufferedDeletes.MAX_INT);
		}

	}

	public Iterable termsIterable()
	{
		return new Iterable() {

			final CoalescedDeletes this$0;

			public Iterator iterator()
			{
				Iterator subs[] = new Iterator[iterables.size()];
				for (int i = 0; i < iterables.size(); i++)
					subs[i] = ((Iterable)iterables.get(i)).iterator();

				return new MergedIterator(subs);
			}

			
			{
				this$0 = CoalescedDeletes.this;
				super();
			}
		};
	}

	public Iterable queriesIterable()
	{
		return new Iterable() {

			final CoalescedDeletes this$0;

			public Iterator iterator()
			{
				return new Iterator() {

					private final Iterator iter;
					final 2 this$1;

					public boolean hasNext()
					{
						return iter.hasNext();
					}

					public BufferedDeletesStream.QueryAndLimit next()
					{
						java.util.Map.Entry ent = (java.util.Map.Entry)iter.next();
						return new BufferedDeletesStream.QueryAndLimit((Query)ent.getKey(), ((Integer)ent.getValue()).intValue());
					}

					public void remove()
					{
						throw new UnsupportedOperationException();
					}

					public volatile Object next()
					{
						return next();
					}

					
					{
						this$1 = 2.this;
						super();
						iter = queries.entrySet().iterator();
					}
				};
			}

			
			{
				this$0 = CoalescedDeletes.this;
				super();
			}
		};
	}
}
