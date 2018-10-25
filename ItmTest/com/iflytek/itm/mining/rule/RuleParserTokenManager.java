// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RuleParserTokenManager.java

package com.iflytek.itm.mining.rule;

import java.io.IOException;
import java.io.PrintStream;

// Referenced classes of package com.iflytek.itm.mining.rule:
//			JavaCharStream, TokenMgrError, RuleParserConstants, Token

public class RuleParserTokenManager
	implements RuleParserConstants
{

	public PrintStream debugStream;
	static final long jjbitVec0[] = {
		0L, -16384L, 0x7fffffffL, 0L
	};
	static final long jjbitVec2[] = {
		-1L, -1L, 0x3fffffffffL, 0L
	};
	static final int jjnextStates[] = new int[0];
	public static final String jjstrLiteralImages[] = {
		"", null, null, null, null, "&", "|", "!", "#", "+", 
		null, "(", ")"
	};
	public static final String lexStateNames[] = {
		"DEFAULT"
	};
	static final long jjtoToken[] = {
		8161L
	};
	static final long jjtoSkip[] = {
		30L
	};
	protected JavaCharStream input_stream;
	private final int jjrounds[];
	private final int jjstateSet[];
	protected char curChar;
	int curLexState;
	int defaultLexState;
	int jjnewStateCnt;
	int jjround;
	int jjmatchedPos;
	int jjmatchedKind;

	public void setDebugStream(PrintStream ds)
	{
		debugStream = ds;
	}

	private final int jjStopStringLiteralDfa_0(int pos, long active0)
	{
		switch (pos)
		{
		default:
			return -1;
		}
	}

	private final int jjStartNfa_0(int pos, long active0)
	{
		return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
	}

	private int jjStopAtPos(int pos, int kind)
	{
		jjmatchedKind = kind;
		jjmatchedPos = pos;
		return pos + 1;
	}

	private int jjMoveStringLiteralDfa0_0()
	{
		switch (curChar)
		{
		case 33: // '!'
			return jjStopAtPos(0, 7);

		case 35: // '#'
			return jjStopAtPos(0, 8);

		case 38: // '&'
			return jjStopAtPos(0, 5);

		case 40: // '('
			return jjStopAtPos(0, 11);

		case 41: // ')'
			return jjStopAtPos(0, 12);

		case 43: // '+'
			return jjStopAtPos(0, 9);

		case 92: // '\\'
			return jjMoveStringLiteralDfa1_0(28L);

		case 124: // '|'
			return jjStopAtPos(0, 6);
		}
		return jjMoveNfa_0(0, 0);
	}

	private int jjMoveStringLiteralDfa1_0(long active0)
	{
		try
		{
			curChar = input_stream.readChar();
		}
		catch (IOException e)
		{
			jjStopStringLiteralDfa_0(0, active0);
			return 1;
		}
		switch (curChar)
		{
		default:
			break;

		case 110: // 'n'
			if ((active0 & 16L) != 0L)
				return jjStopAtPos(1, 4);
			break;

		case 114: // 'r'
			if ((active0 & 8L) != 0L)
				return jjStopAtPos(1, 3);
			break;

		case 116: // 't'
			if ((active0 & 4L) != 0L)
				return jjStopAtPos(1, 2);
			break;
		}
		return jjStartNfa_0(0, active0);
	}

	private int jjMoveNfa_0(int startState, int curPos)
	{
		int startsAt = 0;
		jjnewStateCnt = 1;
		int i = 1;
		jjstateSet[0] = startState;
		int kind = 0x7fffffff;
		do
		{
			if (++jjround == 0x7fffffff)
				ReInitRounds();
			if (curChar < '@')
			{
				long l = 1L << curChar;
				do
					switch (jjstateSet[--i])
					{
					case 0: // '\0'
						if ((0x3ff002000000000L & l) != 0L)
						{
							kind = 10;
							jjstateSet[jjnewStateCnt++] = 0;
						}
						break;
					}
				while (i != startsAt);
			} else
			if (curChar < '\200')
			{
				long l = 1L << (curChar & 0x3f);
				do
					switch (jjstateSet[--i])
					{
					case 0: // '\0'
						if ((0x7fffffe07fffffeL & l) != 0L)
						{
							kind = 10;
							jjstateSet[jjnewStateCnt++] = 0;
						}
						break;
					}
				while (i != startsAt);
			} else
			{
				int hiByte = curChar >> 8;
				int i1 = hiByte >> 6;
				long l1 = 1L << (hiByte & 0x3f);
				int i2 = (curChar & 0xff) >> 6;
				long l2 = 1L << (curChar & 0x3f);
				do
					switch (jjstateSet[--i])
					{
					case 0: // '\0'
						if (jjCanMove_0(hiByte, i1, i2, l1, l2))
						{
							if (kind > 10)
								kind = 10;
							jjstateSet[jjnewStateCnt++] = 0;
						}
						break;
					}
				while (i != startsAt);
			}
			if (kind != 0x7fffffff)
			{
				jjmatchedKind = kind;
				jjmatchedPos = curPos;
				kind = 0x7fffffff;
			}
			curPos++;
			if ((i = jjnewStateCnt) == (startsAt = 1 - (jjnewStateCnt = startsAt)))
				return curPos;
			try
			{
				curChar = input_stream.readChar();
			}
			catch (IOException e)
			{
				return curPos;
			}
		} while (true);
	}

	private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2)
	{
		switch (hiByte)
		{
		case 159: 
			return (jjbitVec2[i2] & l2) != 0L;
		}
		return (jjbitVec0[i1] & l1) != 0L;
	}

	public RuleParserTokenManager(JavaCharStream stream)
	{
		debugStream = System.out;
		jjrounds = new int[1];
		jjstateSet = new int[2];
		curLexState = 0;
		defaultLexState = 0;
		input_stream = stream;
	}

	public RuleParserTokenManager(JavaCharStream stream, int lexState)
	{
		this(stream);
		SwitchTo(lexState);
	}

	public void ReInit(JavaCharStream stream)
	{
		jjmatchedPos = jjnewStateCnt = 0;
		curLexState = defaultLexState;
		input_stream = stream;
		ReInitRounds();
	}

	private void ReInitRounds()
	{
		jjround = 0x80000001;
		for (int i = 1; i-- > 0;)
			jjrounds[i] = 0x80000000;

	}

	public void ReInit(JavaCharStream stream, int lexState)
	{
		ReInit(stream);
		SwitchTo(lexState);
	}

	public void SwitchTo(int lexState)
	{
		if (lexState >= 1 || lexState < 0)
		{
			throw new TokenMgrError((new StringBuilder()).append("Error: Ignoring invalid lexical state : ").append(lexState).append(". State unchanged.").toString(), 2);
		} else
		{
			curLexState = lexState;
			return;
		}
	}

	protected Token jjFillToken()
	{
		String im = jjstrLiteralImages[jjmatchedKind];
		String curTokenImage = im != null ? im : input_stream.GetImage();
		int beginLine = input_stream.getBeginLine();
		int beginColumn = input_stream.getBeginColumn();
		int endLine = input_stream.getEndLine();
		int endColumn = input_stream.getEndColumn();
		Token t = Token.newToken(jjmatchedKind, curTokenImage);
		t.beginLine = beginLine;
		t.endLine = endLine;
		t.beginColumn = beginColumn;
		t.endColumn = endColumn;
		return t;
	}

	public Token getNextToken()
	{
		int curPos = 0;
		do
		{
			do
			{
				try
				{
					curChar = input_stream.BeginToken();
				}
				catch (IOException e)
				{
					jjmatchedKind = 0;
					Token matchedToken = jjFillToken();
					return matchedToken;
				}
				try
				{
					input_stream.backup(0);
					for (; curChar <= ' ' && (0x100000000L & 1L << curChar) != 0L; curChar = input_stream.BeginToken());
					break;
				}
				catch (IOException e1) { }
			} while (true);
			jjmatchedKind = 0x7fffffff;
			jjmatchedPos = 0;
			curPos = jjMoveStringLiteralDfa0_0();
			if (jjmatchedKind != 0x7fffffff)
			{
				if (jjmatchedPos + 1 < curPos)
					input_stream.backup(curPos - jjmatchedPos - 1);
				if ((jjtoToken[jjmatchedKind >> 6] & 1L << (jjmatchedKind & 0x3f)) != 0L)
				{
					Token matchedToken = jjFillToken();
					return matchedToken;
				}
			} else
			{
				int error_line = input_stream.getEndLine();
				int error_column = input_stream.getEndColumn();
				String error_after = null;
				boolean EOFSeen = false;
				try
				{
					input_stream.readChar();
					input_stream.backup(1);
				}
				catch (IOException e1)
				{
					EOFSeen = true;
					error_after = curPos > 1 ? input_stream.GetImage() : "";
					if (curChar == '\n' || curChar == '\r')
					{
						error_line++;
						error_column = 0;
					} else
					{
						error_column++;
					}
				}
				if (!EOFSeen)
				{
					input_stream.backup(1);
					error_after = curPos > 1 ? input_stream.GetImage() : "";
				}
				throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, 0);
			}
		} while (true);
	}

	private void jjCheckNAdd(int state)
	{
		if (jjrounds[state] != jjround)
		{
			jjstateSet[jjnewStateCnt++] = state;
			jjrounds[state] = jjround;
		}
	}

	private void jjAddStates(int start, int end)
	{
		do
			jjstateSet[jjnewStateCnt++] = jjnextStates[start];
		while (start++ != end);
	}

	private void jjCheckNAddTwoStates(int state1, int state2)
	{
		jjCheckNAdd(state1);
		jjCheckNAdd(state2);
	}

}
