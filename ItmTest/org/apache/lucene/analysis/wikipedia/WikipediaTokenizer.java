// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WikipediaTokenizer.java

package org.apache.lucene.analysis.wikipedia;

import java.io.IOException;
import java.io.Reader;
import java.util.*;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.*;
import org.apache.lucene.util.AttributeSource;

// Referenced classes of package org.apache.lucene.analysis.wikipedia:
//			WikipediaTokenizerImpl

public final class WikipediaTokenizer extends Tokenizer
{

	public static final String INTERNAL_LINK = "il";
	public static final String EXTERNAL_LINK = "el";
	public static final String EXTERNAL_LINK_URL = "elu";
	public static final String CITATION = "ci";
	public static final String CATEGORY = "c";
	public static final String BOLD = "b";
	public static final String ITALICS = "i";
	public static final String BOLD_ITALICS = "bi";
	public static final String HEADING = "h";
	public static final String SUB_HEADING = "sh";
	public static final int ALPHANUM_ID = 0;
	public static final int APOSTROPHE_ID = 1;
	public static final int ACRONYM_ID = 2;
	public static final int COMPANY_ID = 3;
	public static final int EMAIL_ID = 4;
	public static final int HOST_ID = 5;
	public static final int NUM_ID = 6;
	public static final int CJ_ID = 7;
	public static final int INTERNAL_LINK_ID = 8;
	public static final int EXTERNAL_LINK_ID = 9;
	public static final int CITATION_ID = 10;
	public static final int CATEGORY_ID = 11;
	public static final int BOLD_ID = 12;
	public static final int ITALICS_ID = 13;
	public static final int BOLD_ITALICS_ID = 14;
	public static final int HEADING_ID = 15;
	public static final int SUB_HEADING_ID = 16;
	public static final int EXTERNAL_LINK_URL_ID = 17;
	public static final String TOKEN_TYPES[] = {
		"<ALPHANUM>", "<APOSTROPHE>", "<ACRONYM>", "<COMPANY>", "<EMAIL>", "<HOST>", "<NUM>", "<CJ>", "il", "el", 
		"ci", "c", "b", "i", "bi", "h", "sh", "elu"
	};
	public static final int TOKENS_ONLY = 0;
	public static final int UNTOKENIZED_ONLY = 1;
	public static final int BOTH = 2;
	public static final int UNTOKENIZED_TOKEN_FLAG = 1;
	private final WikipediaTokenizerImpl scanner;
	private int tokenOutput;
	private Set untokenizedTypes;
	private Iterator tokens;
	private final OffsetAttribute offsetAtt;
	private final TypeAttribute typeAtt;
	private final PositionIncrementAttribute posIncrAtt;
	private final CharTermAttribute termAtt;
	private final FlagsAttribute flagsAtt;
	private boolean first;

	public WikipediaTokenizer(Reader input)
	{
		this(input, 0, Collections.emptySet());
	}

	public WikipediaTokenizer(Reader input, int tokenOutput, Set untokenizedTypes)
	{
		super(input);
		this.tokenOutput = 0;
		this.untokenizedTypes = Collections.emptySet();
		tokens = null;
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		typeAtt = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
		posIncrAtt = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		flagsAtt = (FlagsAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/FlagsAttribute);
		scanner = new WikipediaTokenizerImpl(input);
		init(tokenOutput, untokenizedTypes);
	}

	public WikipediaTokenizer(org.apache.lucene.util.AttributeSource.AttributeFactory factory, Reader input, int tokenOutput, Set untokenizedTypes)
	{
		super(factory, input);
		this.tokenOutput = 0;
		this.untokenizedTypes = Collections.emptySet();
		tokens = null;
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		typeAtt = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
		posIncrAtt = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		flagsAtt = (FlagsAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/FlagsAttribute);
		scanner = new WikipediaTokenizerImpl(input);
		init(tokenOutput, untokenizedTypes);
	}

	public WikipediaTokenizer(AttributeSource source, Reader input, int tokenOutput, Set untokenizedTypes)
	{
		super(source, input);
		this.tokenOutput = 0;
		this.untokenizedTypes = Collections.emptySet();
		tokens = null;
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		typeAtt = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
		posIncrAtt = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		flagsAtt = (FlagsAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/FlagsAttribute);
		scanner = new WikipediaTokenizerImpl(input);
		init(tokenOutput, untokenizedTypes);
	}

	private void init(int tokenOutput, Set untokenizedTypes)
	{
		if (tokenOutput != 0 && tokenOutput != 1 && tokenOutput != 2)
		{
			throw new IllegalArgumentException("tokenOutput must be TOKENS_ONLY, UNTOKENIZED_ONLY or BOTH");
		} else
		{
			this.tokenOutput = tokenOutput;
			this.untokenizedTypes = untokenizedTypes;
			return;
		}
	}

	public final boolean incrementToken()
		throws IOException
	{
		if (tokens != null && tokens.hasNext())
		{
			org.apache.lucene.util.AttributeSource.State state = (org.apache.lucene.util.AttributeSource.State)tokens.next();
			restoreState(state);
			return true;
		}
		clearAttributes();
		int tokenType = scanner.getNextToken();
		if (tokenType == -1)
			return false;
		String type = WikipediaTokenizerImpl.TOKEN_TYPES[tokenType];
		if (tokenOutput == 0 || !untokenizedTypes.contains(type))
			setupToken();
		else
		if (tokenOutput == 1 && untokenizedTypes.contains(type))
			collapseTokens(tokenType);
		else
		if (tokenOutput == 2)
			collapseAndSaveTokens(tokenType, type);
		int posinc = scanner.getPositionIncrement();
		if (first && posinc == 0)
			posinc = 1;
		posIncrAtt.setPositionIncrement(posinc);
		typeAtt.setType(type);
		first = false;
		return true;
	}

	private void collapseAndSaveTokens(int tokenType, String type)
		throws IOException
	{
		StringBuilder buffer = new StringBuilder(32);
		int numAdded = scanner.setText(buffer);
		int theStart = scanner.yychar();
		int lastPos = theStart + numAdded;
		int numSeen = 0;
		List tmp = new ArrayList();
		setupSavedToken(0, type);
		tmp.add(captureState());
		int tmpTokType;
		while ((tmpTokType = scanner.getNextToken()) != -1 && tmpTokType == tokenType && scanner.getNumWikiTokensSeen() > numSeen) 
		{
			int currPos = scanner.yychar();
			for (int i = 0; i < currPos - lastPos; i++)
				buffer.append(' ');

			numAdded = scanner.setText(buffer);
			setupSavedToken(scanner.getPositionIncrement(), type);
			tmp.add(captureState());
			numSeen++;
			lastPos = currPos + numAdded;
		}
		String s = buffer.toString().trim();
		termAtt.setEmpty().append(s);
		offsetAtt.setOffset(correctOffset(theStart), correctOffset(theStart + s.length()));
		flagsAtt.setFlags(1);
		if (tmpTokType != -1)
			scanner.yypushback(scanner.yylength());
		tokens = tmp.iterator();
	}

	private void setupSavedToken(int positionInc, String type)
	{
		setupToken();
		posIncrAtt.setPositionIncrement(positionInc);
		typeAtt.setType(type);
	}

	private void collapseTokens(int tokenType)
		throws IOException
	{
		StringBuilder buffer = new StringBuilder(32);
		int numAdded = scanner.setText(buffer);
		int theStart = scanner.yychar();
		int lastPos = theStart + numAdded;
		int tmpTokType;
		for (int numSeen = 0; (tmpTokType = scanner.getNextToken()) != -1 && tmpTokType == tokenType && scanner.getNumWikiTokensSeen() > numSeen;)
		{
			int currPos = scanner.yychar();
			for (int i = 0; i < currPos - lastPos; i++)
				buffer.append(' ');

			numAdded = scanner.setText(buffer);
			numSeen++;
			lastPos = currPos + numAdded;
		}

		String s = buffer.toString().trim();
		termAtt.setEmpty().append(s);
		offsetAtt.setOffset(correctOffset(theStart), correctOffset(theStart + s.length()));
		flagsAtt.setFlags(1);
		if (tmpTokType != -1)
			scanner.yypushback(scanner.yylength());
		else
			tokens = null;
	}

	private void setupToken()
	{
		scanner.getText(termAtt);
		int start = scanner.yychar();
		offsetAtt.setOffset(correctOffset(start), correctOffset(start + termAtt.length()));
	}

	public void reset()
		throws IOException
	{
		scanner.yyreset(input);
		tokens = null;
		scanner.reset();
		first = true;
	}

	public void end()
	{
		int finalOffset = correctOffset(scanner.yychar() + scanner.yylength());
		offsetAtt.setOffset(finalOffset, finalOffset);
	}

}
