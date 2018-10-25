// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AnalyzerQueryNodeProcessor.java

package org.apache.lucene.queryparser.flexible.standard.processors;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.nodes.*;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler;
import org.apache.lucene.queryparser.flexible.standard.nodes.*;

public class AnalyzerQueryNodeProcessor extends QueryNodeProcessorImpl
{

	private Analyzer analyzer;
	private boolean positionIncrementsEnabled;
	static final boolean $assertionsDisabled = !org/apache/lucene/queryparser/flexible/standard/processors/AnalyzerQueryNodeProcessor.desiredAssertionStatus();

	public AnalyzerQueryNodeProcessor()
	{
	}

	public QueryNode process(QueryNode queryTree)
		throws QueryNodeException
	{
		Analyzer analyzer = (Analyzer)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.ANALYZER);
		if (analyzer != null)
		{
			this.analyzer = analyzer;
			this.positionIncrementsEnabled = false;
			Boolean positionIncrementsEnabled = (Boolean)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.ENABLE_POSITION_INCREMENTS);
			if (positionIncrementsEnabled != null)
				this.positionIncrementsEnabled = positionIncrementsEnabled.booleanValue();
			if (this.analyzer != null)
				return super.process(queryTree);
		}
		return queryTree;
	}

	protected QueryNode postProcessNode(QueryNode node)
		throws QueryNodeException
	{
		if ((node instanceof TextableQueryNode) && !(node instanceof WildcardQueryNode) && !(node instanceof FuzzyQueryNode) && !(node instanceof RegexpQueryNode) && !(node.getParent() instanceof RangeQueryNode))
		{
			FieldQueryNode fieldNode = (FieldQueryNode)node;
			String text = fieldNode.getTextAsString();
			String field = fieldNode.getFieldAsString();
			TokenStream source;
			try
			{
				source = analyzer.tokenStream(field, new StringReader(text));
				source.reset();
			}
			catch (IOException e1)
			{
				throw new RuntimeException(e1);
			}
			CachingTokenFilter buffer = new CachingTokenFilter(source);
			PositionIncrementAttribute posIncrAtt = null;
			int numTokens = 0;
			int positionCount = 0;
			boolean severalTokensAtSamePosition = false;
			if (buffer.hasAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute))
				posIncrAtt = (PositionIncrementAttribute)buffer.getAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
			try
			{
				while (buffer.incrementToken()) 
				{
					numTokens++;
					int positionIncrement = posIncrAtt == null ? 1 : posIncrAtt.getPositionIncrement();
					if (positionIncrement != 0)
						positionCount += positionIncrement;
					else
						severalTokensAtSamePosition = true;
				}
			}
			catch (IOException e) { }
			try
			{
				buffer.reset();
				source.close();
			}
			catch (IOException e) { }
			if (!buffer.hasAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute))
				return new NoTokenFoundQueryNode();
			CharTermAttribute termAtt = (CharTermAttribute)buffer.getAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
			if (numTokens == 0)
				return new NoTokenFoundQueryNode();
			if (numTokens == 1)
			{
				String term = null;
				try
				{
					boolean hasNext = buffer.incrementToken();
					if (!$assertionsDisabled && !hasNext)
						throw new AssertionError();
					term = termAtt.toString();
				}
				catch (IOException e) { }
				fieldNode.setText(term);
				return fieldNode;
			}
			if (severalTokensAtSamePosition || !(node instanceof QuotedFieldQueryNode))
			{
				if (positionCount == 1 || !(node instanceof QuotedFieldQueryNode))
				{
					LinkedList children = new LinkedList();
					for (int i = 0; i < numTokens; i++)
					{
						String term = null;
						try
						{
							boolean hasNext = buffer.incrementToken();
							if (!$assertionsDisabled && !hasNext)
								throw new AssertionError();
							term = termAtt.toString();
						}
						catch (IOException e) { }
						children.add(new FieldQueryNode(field, term, -1, -1));
					}

					return new GroupQueryNode(new StandardBooleanQueryNode(children, positionCount == 1));
				}
				MultiPhraseQueryNode mpq = new MultiPhraseQueryNode();
				List multiTerms = new ArrayList();
				int position = -1;
				int i = 0;
				int termGroupCount = 0;
				for (; i < numTokens; i++)
				{
					String term = null;
					int positionIncrement = 1;
					try
					{
						boolean hasNext = buffer.incrementToken();
						if (!$assertionsDisabled && !hasNext)
							throw new AssertionError();
						term = termAtt.toString();
						if (posIncrAtt != null)
							positionIncrement = posIncrAtt.getPositionIncrement();
					}
					catch (IOException e) { }
					if (positionIncrement > 0 && multiTerms.size() > 0)
					{
						FieldQueryNode termNode;
						for (Iterator i$ = multiTerms.iterator(); i$.hasNext(); mpq.add(termNode))
						{
							termNode = (FieldQueryNode)i$.next();
							if (positionIncrementsEnabled)
								termNode.setPositionIncrement(position);
							else
								termNode.setPositionIncrement(termGroupCount);
						}

						termGroupCount++;
						multiTerms.clear();
					}
					position += positionIncrement;
					multiTerms.add(new FieldQueryNode(field, term, -1, -1));
				}

				FieldQueryNode termNode;
				for (Iterator i$ = multiTerms.iterator(); i$.hasNext(); mpq.add(termNode))
				{
					termNode = (FieldQueryNode)i$.next();
					if (positionIncrementsEnabled)
						termNode.setPositionIncrement(position);
					else
						termNode.setPositionIncrement(termGroupCount);
				}

				return mpq;
			}
			TokenizedPhraseQueryNode pq = new TokenizedPhraseQueryNode();
			int position = -1;
			for (int i = 0; i < numTokens; i++)
			{
				String term = null;
				int positionIncrement = 1;
				try
				{
					boolean hasNext = buffer.incrementToken();
					if (!$assertionsDisabled && !hasNext)
						throw new AssertionError();
					term = termAtt.toString();
					if (posIncrAtt != null)
						positionIncrement = posIncrAtt.getPositionIncrement();
				}
				catch (IOException e) { }
				FieldQueryNode newFieldNode = new FieldQueryNode(field, term, -1, -1);
				if (positionIncrementsEnabled)
				{
					position += positionIncrement;
					newFieldNode.setPositionIncrement(position);
				} else
				{
					newFieldNode.setPositionIncrement(i);
				}
				pq.add(newFieldNode);
			}

			return pq;
		} else
		{
			return node;
		}
	}

	protected QueryNode preProcessNode(QueryNode node)
		throws QueryNodeException
	{
		return node;
	}

	protected List setChildrenOrder(List children)
		throws QueryNodeException
	{
		return children;
	}

}
