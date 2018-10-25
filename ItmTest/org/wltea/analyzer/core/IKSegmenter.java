// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IKSegmenter.java

package org.wltea.analyzer.core;

import java.io.IOException;
import java.io.Reader;
import java.util.*;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.dic.Dictionary;

// Referenced classes of package org.wltea.analyzer.core:
//			AnalyzeContext, IKArbitrator, LetterSegmenter, CN_QuantifierSegmenter, 
//			CJKSegmenter, ISegmenter, Lexeme

public final class IKSegmenter
{

	private Reader input;
	private Configuration cfg;
	private AnalyzeContext context;
	private List segmenters;
	private IKArbitrator arbitrator;

	public IKSegmenter(Reader input, boolean useSmart)
	{
		this.input = input;
		cfg = DefaultConfig.getInstance();
		cfg.setUseSmart(useSmart);
		init();
	}

	public IKSegmenter(Reader input, Configuration cfg)
	{
		this.input = input;
		this.cfg = cfg;
		init();
	}

	private void init()
	{
		Dictionary.initial(cfg);
		context = new AnalyzeContext(cfg);
		segmenters = loadSegmenters();
		arbitrator = new IKArbitrator();
	}

	private List loadSegmenters()
	{
		List segmenters = new ArrayList(4);
		segmenters.add(new LetterSegmenter());
		segmenters.add(new CN_QuantifierSegmenter());
		segmenters.add(new CJKSegmenter());
		return segmenters;
	}

	public synchronized Lexeme next()
		throws IOException
	{
		Lexeme l;
		for (l = null; (l = context.getNextLexeme()) == null;)
		{
			int available = context.fillBuffer(input);
			if (available <= 0)
			{
				context.reset();
				return null;
			}
			context.initCursor();
			do
			{
				ISegmenter segmenter;
				for (Iterator iterator = segmenters.iterator(); iterator.hasNext(); segmenter.analyze(context))
					segmenter = (ISegmenter)iterator.next();

			} while (!context.needRefillBuffer() && context.moveCursor());
			ISegmenter segmenter;
			for (Iterator iterator1 = segmenters.iterator(); iterator1.hasNext(); segmenter.reset())
				segmenter = (ISegmenter)iterator1.next();

			arbitrator.process(context, cfg.useSmart());
			context.outputToResult();
			context.markBufferOffset();
		}

		return l;
	}

	public synchronized void reset(Reader input)
	{
		this.input = input;
		context.reset();
		ISegmenter segmenter;
		for (Iterator iterator = segmenters.iterator(); iterator.hasNext(); segmenter.reset())
			segmenter = (ISegmenter)iterator.next();

	}
}
