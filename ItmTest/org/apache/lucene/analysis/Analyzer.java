// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Analyzer.java

package org.apache.lucene.analysis;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.store.AlreadyClosedException;
import org.apache.lucene.util.CloseableThreadLocal;

// Referenced classes of package org.apache.lucene.analysis:
//			TokenStream, Tokenizer

public abstract class Analyzer
	implements Closeable
{
	public static class PerFieldReuseStrategy extends ReuseStrategy
	{

		public TokenStreamComponents getReusableComponents(String fieldName)
		{
			Map componentsPerField = (Map)getStoredValue();
			return componentsPerField == null ? null : (TokenStreamComponents)componentsPerField.get(fieldName);
		}

		public void setReusableComponents(String fieldName, TokenStreamComponents components)
		{
			Map componentsPerField = (Map)getStoredValue();
			if (componentsPerField == null)
			{
				componentsPerField = new HashMap();
				setStoredValue(componentsPerField);
			}
			componentsPerField.put(fieldName, components);
		}

		public PerFieldReuseStrategy()
		{
		}
	}

	public static final class GlobalReuseStrategy extends ReuseStrategy
	{

		public TokenStreamComponents getReusableComponents(String fieldName)
		{
			return (TokenStreamComponents)getStoredValue();
		}

		public void setReusableComponents(String fieldName, TokenStreamComponents components)
		{
			setStoredValue(components);
		}

		public GlobalReuseStrategy()
		{
		}
	}

	public static abstract class ReuseStrategy
		implements Closeable
	{

		private CloseableThreadLocal storedValue;

		public abstract TokenStreamComponents getReusableComponents(String s);

		public abstract void setReusableComponents(String s, TokenStreamComponents tokenstreamcomponents);

		protected final Object getStoredValue()
		{
			return storedValue.get();
			NullPointerException npe;
			npe;
			if (storedValue == null)
				throw new AlreadyClosedException("this Analyzer is closed");
			else
				throw npe;
		}

		protected final void setStoredValue(Object storedValue)
		{
			try
			{
				this.storedValue.set(storedValue);
			}
			catch (NullPointerException npe)
			{
				if (storedValue == null)
					throw new AlreadyClosedException("this Analyzer is closed");
				else
					throw npe;
			}
		}

		public void close()
		{
			if (storedValue != null)
			{
				storedValue.close();
				storedValue = null;
			}
		}

		public ReuseStrategy()
		{
			storedValue = new CloseableThreadLocal();
		}
	}

	public static class TokenStreamComponents
	{

		protected final Tokenizer source;
		protected final TokenStream sink;

		protected void setReader(Reader reader)
			throws IOException
		{
			source.setReader(reader);
		}

		public TokenStream getTokenStream()
		{
			return sink;
		}

		public Tokenizer getTokenizer()
		{
			return source;
		}

		public TokenStreamComponents(Tokenizer source, TokenStream result)
		{
			this.source = source;
			sink = result;
		}

		public TokenStreamComponents(Tokenizer source)
		{
			this.source = source;
			sink = source;
		}
	}


	private final ReuseStrategy reuseStrategy;

	public Analyzer()
	{
		this(((ReuseStrategy) (new GlobalReuseStrategy())));
	}

	public Analyzer(ReuseStrategy reuseStrategy)
	{
		this.reuseStrategy = reuseStrategy;
	}

	protected abstract TokenStreamComponents createComponents(String s, Reader reader);

	public final TokenStream tokenStream(String fieldName, Reader reader)
		throws IOException
	{
		TokenStreamComponents components = reuseStrategy.getReusableComponents(fieldName);
		Reader r = initReader(fieldName, reader);
		if (components == null)
		{
			components = createComponents(fieldName, r);
			reuseStrategy.setReusableComponents(fieldName, components);
		} else
		{
			components.setReader(r);
		}
		return components.getTokenStream();
	}

	protected Reader initReader(String fieldName, Reader reader)
	{
		return reader;
	}

	public int getPositionIncrementGap(String fieldName)
	{
		return 0;
	}

	public int getOffsetGap(String fieldName)
	{
		return 1;
	}

	public void close()
	{
		reuseStrategy.close();
	}
}
