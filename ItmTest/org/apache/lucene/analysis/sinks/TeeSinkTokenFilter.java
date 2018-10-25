// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TeeSinkTokenFilter.java

package org.apache.lucene.analysis.sinks;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.*;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.util.AttributeImpl;
import org.apache.lucene.util.AttributeSource;

public final class TeeSinkTokenFilter extends TokenFilter
{
	public static final class SinkTokenStream extends TokenStream
	{

		private final List cachedStates;
		private org.apache.lucene.util.AttributeSource.State finalState;
		private Iterator it;
		private SinkFilter filter;

		private boolean accept(AttributeSource source)
		{
			return filter.accept(source);
		}

		private void addState(org.apache.lucene.util.AttributeSource.State state)
		{
			if (it != null)
			{
				throw new IllegalStateException("The tee must be consumed before sinks are consumed.");
			} else
			{
				cachedStates.add(state);
				return;
			}
		}

		private void setFinalState(org.apache.lucene.util.AttributeSource.State finalState)
		{
			this.finalState = finalState;
		}

		public final boolean incrementToken()
		{
			if (it == null)
				it = cachedStates.iterator();
			if (!it.hasNext())
			{
				return false;
			} else
			{
				org.apache.lucene.util.AttributeSource.State state = (org.apache.lucene.util.AttributeSource.State)it.next();
				restoreState(state);
				return true;
			}
		}

		public final void end()
		{
			if (finalState != null)
				restoreState(finalState);
		}

		public final void reset()
		{
			it = cachedStates.iterator();
		}




		private SinkTokenStream(AttributeSource source, SinkFilter filter)
		{
			super(source);
			cachedStates = new LinkedList();
			it = null;
			this.filter = filter;
		}

	}

	public static abstract class SinkFilter
	{

		public abstract boolean accept(AttributeSource attributesource);

		public void reset()
			throws IOException
		{
		}

		public SinkFilter()
		{
		}
	}


	private final List sinks = new LinkedList();
	private static final SinkFilter ACCEPT_ALL_FILTER = new SinkFilter() {

		public boolean accept(AttributeSource source)
		{
			return true;
		}

	};

	public TeeSinkTokenFilter(TokenStream input)
	{
		super(input);
	}

	public SinkTokenStream newSinkTokenStream()
	{
		return newSinkTokenStream(ACCEPT_ALL_FILTER);
	}

	public SinkTokenStream newSinkTokenStream(SinkFilter filter)
	{
		SinkTokenStream sink = new SinkTokenStream(cloneAttributes(), filter);
		sinks.add(new WeakReference(sink));
		return sink;
	}

	public void addSinkTokenStream(SinkTokenStream sink)
	{
		if (!getAttributeFactory().equals(sink.getAttributeFactory()))
			throw new IllegalArgumentException("The supplied sink is not compatible to this tee");
		for (Iterator it = cloneAttributes().getAttributeImplsIterator(); it.hasNext(); sink.addAttributeImpl((AttributeImpl)it.next()));
		sinks.add(new WeakReference(sink));
	}

	public void consumeAllTokens()
		throws IOException
	{
		while (incrementToken()) ;
	}

	public boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			org.apache.lucene.util.AttributeSource.State state = null;
			Iterator i$ = sinks.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				WeakReference ref = (WeakReference)i$.next();
				SinkTokenStream sink = (SinkTokenStream)ref.get();
				if (sink != null && sink.accept(this))
				{
					if (state == null)
						state = captureState();
					sink.addState(state);
				}
			} while (true);
			return true;
		} else
		{
			return false;
		}
	}

	public final void end()
		throws IOException
	{
		super.end();
		org.apache.lucene.util.AttributeSource.State finalState = captureState();
		Iterator i$ = sinks.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			WeakReference ref = (WeakReference)i$.next();
			SinkTokenStream sink = (SinkTokenStream)ref.get();
			if (sink != null)
				sink.setFinalState(finalState);
		} while (true);
	}

}
