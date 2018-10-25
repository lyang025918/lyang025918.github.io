// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocFieldConsumerPerField.java

package org.apache.lucene.index;

import java.io.IOException;

// Referenced classes of package org.apache.lucene.index:
//			IndexableField, FieldInfo

abstract class DocFieldConsumerPerField
{

	DocFieldConsumerPerField()
	{
	}

	abstract void processFields(IndexableField aindexablefield[], int i)
		throws IOException;

	abstract void abort();

	abstract FieldInfo getFieldInfo();
}
