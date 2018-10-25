// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StoredFieldVisitor.java

package org.apache.lucene.index;

import java.io.IOException;

// Referenced classes of package org.apache.lucene.index:
//			FieldInfo

public abstract class StoredFieldVisitor
{
	public static final class Status extends Enum
	{

		public static final Status YES;
		public static final Status NO;
		public static final Status STOP;
		private static final Status $VALUES[];

		public static Status[] values()
		{
			return (Status[])$VALUES.clone();
		}

		public static Status valueOf(String name)
		{
			return (Status)Enum.valueOf(org/apache/lucene/index/StoredFieldVisitor$Status, name);
		}

		static 
		{
			YES = new Status("YES", 0);
			NO = new Status("NO", 1);
			STOP = new Status("STOP", 2);
			$VALUES = (new Status[] {
				YES, NO, STOP
			});
		}

		private Status(String s, int i)
		{
			super(s, i);
		}
	}


	protected StoredFieldVisitor()
	{
	}

	public void binaryField(FieldInfo fieldinfo, byte abyte0[])
		throws IOException
	{
	}

	public void stringField(FieldInfo fieldinfo, String s)
		throws IOException
	{
	}

	public void intField(FieldInfo fieldinfo, int i)
		throws IOException
	{
	}

	public void longField(FieldInfo fieldinfo, long l)
		throws IOException
	{
	}

	public void floatField(FieldInfo fieldinfo, float f)
		throws IOException
	{
	}

	public void doubleField(FieldInfo fieldinfo, double d)
		throws IOException
	{
	}

	public abstract Status needsField(FieldInfo fieldinfo)
		throws IOException;
}
