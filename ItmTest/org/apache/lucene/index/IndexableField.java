// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndexableField.java

package org.apache.lucene.index;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.index:
//			IndexableFieldType

public interface IndexableField
{

	public abstract String name();

	public abstract IndexableFieldType fieldType();

	public abstract float boost();

	public abstract BytesRef binaryValue();

	public abstract String stringValue();

	public abstract Reader readerValue();

	public abstract Number numericValue();

	public abstract TokenStream tokenStream(Analyzer analyzer)
		throws IOException;
}
