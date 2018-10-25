// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WeakIdentityMap.java

package org.apache.lucene.util;

import java.lang.ref.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class WeakIdentityMap
{
	private static final class IdentityWeakReference extends WeakReference
	{

		private final int hash;

		public int hashCode()
		{
			return hash;
		}

		public boolean equals(Object o)
		{
			if (this == o)
				return true;
			if (o instanceof IdentityWeakReference)
			{
				IdentityWeakReference ref = (IdentityWeakReference)o;
				if (get() == ref.get())
					return true;
			}
			return false;
		}

		IdentityWeakReference(Object obj, ReferenceQueue queue)
		{
			super(obj != null ? obj : WeakIdentityMap.NULL, queue);
			hash = System.identityHashCode(obj);
		}
	}


	private final ReferenceQueue queue = new ReferenceQueue();
	private final Map backingStore;
	static final Object NULL = new Object();

	public static final WeakIdentityMap newHashMap()
	{
		return new WeakIdentityMap(new HashMap());
	}

	public static final WeakIdentityMap newConcurrentHashMap()
	{
		return new WeakIdentityMap(new ConcurrentHashMap());
	}

	private WeakIdentityMap(Map backingStore)
	{
		this.backingStore = backingStore;
	}

	public void clear()
	{
		backingStore.clear();
		reap();
	}

	public boolean containsKey(Object key)
	{
		reap();
		return backingStore.containsKey(new IdentityWeakReference(key, null));
	}

	public Object get(Object key)
	{
		reap();
		return backingStore.get(new IdentityWeakReference(key, null));
	}

	public Object put(Object key, Object value)
	{
		reap();
		return backingStore.put(new IdentityWeakReference(key, queue), value);
	}

	public boolean isEmpty()
	{
		return size() == 0;
	}

	public Object remove(Object key)
	{
		reap();
		return backingStore.remove(new IdentityWeakReference(key, null));
	}

	public int size()
	{
		if (backingStore.isEmpty())
		{
			return 0;
		} else
		{
			reap();
			return backingStore.size();
		}
	}

	public Iterator keyIterator()
	{
		reap();
		final Iterator iterator = backingStore.keySet().iterator();
		return new Iterator() {

			private Object next;
			private boolean nextIsSet;
			static final boolean $assertionsDisabled = !org/apache/lucene/util/WeakIdentityMap.desiredAssertionStatus();
			final Iterator val$iterator;
			final WeakIdentityMap this$0;

			public boolean hasNext()
			{
				return nextIsSet ? true : setNext();
			}

			public Object next()
			{
				if (!nextIsSet && !setNext())
					break MISSING_BLOCK_LABEL_65;
				Object obj;
				if (!$assertionsDisabled && !nextIsSet)
					throw new AssertionError();
				obj = next;
				nextIsSet = false;
				next = null;
				return obj;
				Exception exception;
				exception;
				nextIsSet = false;
				next = null;
				throw exception;
				throw new NoSuchElementException();
			}

			public void remove()
			{
				throw new UnsupportedOperationException();
			}

			private boolean setNext()
			{
				if (!$assertionsDisabled && nextIsSet)
					throw new AssertionError();
				while (iterator.hasNext()) 
				{
					next = ((IdentityWeakReference)iterator.next()).get();
					if (next != null)
					{
						if (next == WeakIdentityMap.NULL)
							next = null;
						return nextIsSet = true;
					}
				}
				return false;
			}


			
			{
				this$0 = WeakIdentityMap.this;
				iterator = iterator1;
				super();
				next = null;
				nextIsSet = false;
			}
		};
	}

	public Iterator valueIterator()
	{
		reap();
		return backingStore.values().iterator();
	}

	private void reap()
	{
		Reference zombie;
		while ((zombie = queue.poll()) != null) 
			backingStore.remove(zombie);
	}

}
