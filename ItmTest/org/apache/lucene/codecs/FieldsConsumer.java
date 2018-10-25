// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldsConsumer.java

package org.apache.lucene.codecs;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import org.apache.lucene.index.*;

// Referenced classes of package org.apache.lucene.codecs:
//			TermsConsumer

public abstract class FieldsConsumer
	implements Closeable
{

	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/FieldsConsumer.desiredAssertionStatus();

	protected FieldsConsumer()
	{
	}

	public abstract TermsConsumer addField(FieldInfo fieldinfo)
		throws IOException;

	public abstract void close()
		throws IOException;

	public void merge(MergeState mergeState, Fields fields)
		throws IOException
	{
		Iterator i$ = fields.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			String field = (String)i$.next();
			mergeState.fieldInfo = mergeState.fieldInfos.fieldInfo(field);
			if (!$assertionsDisabled && mergeState.fieldInfo == null)
				throw new AssertionError((new StringBuilder()).append("FieldInfo for field is null: ").append(field).toString());
			Terms terms = fields.terms(field);
			if (terms != null)
			{
				TermsConsumer termsConsumer = addField(mergeState.fieldInfo);
				termsConsumer.merge(mergeState, terms.iterator(null));
			}
		} while (true);
	}

}
