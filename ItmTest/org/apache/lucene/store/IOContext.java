// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IOContext.java

package org.apache.lucene.store;


// Referenced classes of package org.apache.lucene.store:
//			FlushInfo, MergeInfo

public class IOContext
{
	public static final class Context extends Enum
	{

		public static final Context MERGE;
		public static final Context READ;
		public static final Context FLUSH;
		public static final Context DEFAULT;
		private static final Context $VALUES[];

		public static Context[] values()
		{
			return (Context[])$VALUES.clone();
		}

		public static Context valueOf(String name)
		{
			return (Context)Enum.valueOf(org/apache/lucene/store/IOContext$Context, name);
		}

		static 
		{
			MERGE = new Context("MERGE", 0);
			READ = new Context("READ", 1);
			FLUSH = new Context("FLUSH", 2);
			DEFAULT = new Context("DEFAULT", 3);
			$VALUES = (new Context[] {
				MERGE, READ, FLUSH, DEFAULT
			});
		}

		private Context(String s, int i)
		{
			super(s, i);
		}
	}


	public final Context context;
	public final MergeInfo mergeInfo;
	public final FlushInfo flushInfo;
	public final boolean readOnce;
	public static final IOContext DEFAULT;
	public static final IOContext READONCE = new IOContext(true);
	public static final IOContext READ = new IOContext(false);
	static final boolean $assertionsDisabled = !org/apache/lucene/store/IOContext.desiredAssertionStatus();

	public IOContext()
	{
		this(false);
	}

	public IOContext(FlushInfo flushInfo)
	{
		if (!$assertionsDisabled && flushInfo == null)
		{
			throw new AssertionError();
		} else
		{
			context = Context.FLUSH;
			mergeInfo = null;
			readOnce = false;
			this.flushInfo = flushInfo;
			return;
		}
	}

	public IOContext(Context context)
	{
		this(context, ((MergeInfo) (null)));
	}

	private IOContext(boolean readOnce)
	{
		context = Context.READ;
		mergeInfo = null;
		this.readOnce = readOnce;
		flushInfo = null;
	}

	public IOContext(MergeInfo mergeInfo)
	{
		this(Context.MERGE, mergeInfo);
	}

	private IOContext(Context context, MergeInfo mergeInfo)
	{
		if (!$assertionsDisabled && context == Context.MERGE && mergeInfo == null)
			throw new AssertionError("MergeInfo must not be null if context is MERGE");
		if (!$assertionsDisabled && context == Context.FLUSH)
		{
			throw new AssertionError("Use IOContext(FlushInfo) to create a FLUSH IOContext");
		} else
		{
			this.context = context;
			readOnce = false;
			this.mergeInfo = mergeInfo;
			flushInfo = null;
			return;
		}
	}

	public IOContext(IOContext ctxt, boolean readOnce)
	{
		context = ctxt.context;
		mergeInfo = ctxt.mergeInfo;
		flushInfo = ctxt.flushInfo;
		this.readOnce = readOnce;
	}

	public int hashCode()
	{
		int prime = 31;
		int result = 1;
		result = 31 * result + (context != null ? context.hashCode() : 0);
		result = 31 * result + (flushInfo != null ? flushInfo.hashCode() : 0);
		result = 31 * result + (mergeInfo != null ? mergeInfo.hashCode() : 0);
		result = 31 * result + (readOnce ? 1231 : '\u04D5');
		return result;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IOContext other = (IOContext)obj;
		if (context != other.context)
			return false;
		if (flushInfo == null)
		{
			if (other.flushInfo != null)
				return false;
		} else
		if (!flushInfo.equals(other.flushInfo))
			return false;
		if (mergeInfo == null)
		{
			if (other.mergeInfo != null)
				return false;
		} else
		if (!mergeInfo.equals(other.mergeInfo))
			return false;
		return readOnce == other.readOnce;
	}

	public String toString()
	{
		return (new StringBuilder()).append("IOContext [context=").append(context).append(", mergeInfo=").append(mergeInfo).append(", flushInfo=").append(flushInfo).append(", readOnce=").append(readOnce).append("]").toString();
	}

	static 
	{
		DEFAULT = new IOContext(Context.DEFAULT);
	}
}
