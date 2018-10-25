// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BytesRefIterator.java

package org.apache.lucene.util;

import java.io.IOException;
import java.util.Comparator;

// Referenced classes of package org.apache.lucene.util:
//			BytesRef

public interface BytesRefIterator
{

	public static final BytesRefIterator EMPTY = new BytesRefIterator() {

		public BytesRef next()
		{
			return null;
		}

		public Comparator getComparator()
		{
			return null;
		}

	};

	public abstract BytesRef next()
		throws IOException;

	public abstract Comparator getComparator();

}
