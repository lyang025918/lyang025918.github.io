// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryParserTokenManager.java

package org.apache.lucene.queryparser.surround.parser;

import java.io.IOException;
import java.io.PrintStream;

// Referenced classes of package org.apache.lucene.queryparser.surround.parser:
//			TokenMgrError, QueryParserConstants, Token, CharStream

public class QueryParserTokenManager
	implements QueryParserConstants
{

	public PrintStream debugStream;
	static final long jjbitVec0[] = {
		-2L, -1L, -1L, -1L
	};
	static final long jjbitVec2[] = {
		0L, 0L, -1L, -1L
	};
	static final int jjnextStates[] = {
		32, 33, 34, 35, 37, 24, 27, 28, 20, 17, 
		21, 18, 27, 28, 30, 24, 25, 0, 1
	};
	public static final String jjstrLiteralImages[] = {
		"", null, null, null, null, null, null, null, null, null, 
		null, null, null, "(", ")", ",", ":", "^", null, null, 
		null, null, null, null
	};
	public static final String lexStateNames[] = {
		"Boost", "DEFAULT"
	};
	public static final int jjnewLexState[] = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, 0, -1, -1, 
		-1, -1, -1, 1
	};
	static final long jjtoToken[] = {
		0xffff01L
	};
	static final long jjtoSkip[] = {
		128L
	};
	protected CharStream input_stream;
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

	private final int jjStopStringLiteralDfa_1(int pos, long active0)
	{
		switch (pos)
		{
		default:
			return -1;
		}
	}

	private final int jjStartNfa_1(int pos, long active0)
	{
		return jjMoveNfa_1(jjStopStringLiteralDfa_1(pos, active0), pos + 1);
	}

	private int jjStopAtPos(int pos, int kind)
	{
		jjmatchedKind = kind;
		jjmatchedPos = pos;
		return pos + 1;
	}

	private int jjMoveStringLiteralDfa0_1()
	{
		switch (curChar)
		{
		case 40: // '('
			return jjStopAtPos(0, 13);

		case 41: // ')'
			return jjStopAtPos(0, 14);

		case 44: // ','
			return jjStopAtPos(0, 15);

		case 58: // ':'
			return jjStopAtPos(0, 16);

		case 94: // '^'
			return jjStopAtPos(0, 17);
		}
		return jjMoveNfa_1(0, 0);
	}

	private int jjMoveNfa_1(int startState, int curPos)
	{
		int startsAt = 0;
		jjnewStateCnt = 38;
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
						if ((0x7bffe8faffffd9ffL & l) != 0L)
						{
							if (kind > 22)
								kind = 22;
							jjCheckNAddStates(0, 4);
						} else
						if ((0x100002600L & l) != 0L)
						{
							if (kind > 7)
								kind = 7;
						} else
						if (curChar == '"')
							jjCheckNAddStates(5, 7);
						if ((0x3fc000000000000L & l) != 0L)
							jjCheckNAddStates(8, 11);
						else
						if (curChar == '1')
							jjCheckNAddTwoStates(20, 21);
						break;

					case 19: // '\023'
						if ((0x3fc000000000000L & l) != 0L)
							jjCheckNAddStates(8, 11);
						break;

					case 20: // '\024'
						if ((0x3ff000000000000L & l) != 0L)
							jjCheckNAdd(17);
						break;

					case 21: // '\025'
						if ((0x3ff000000000000L & l) != 0L)
							jjCheckNAdd(18);
						break;

					case 22: // '\026'
						if (curChar == '1')
							jjCheckNAddTwoStates(20, 21);
						break;

					case 23: // '\027'
						if (curChar == '"')
							jjCheckNAddStates(5, 7);
						break;

					case 24: // '\030'
						if ((0xfffffffbffffffffL & l) != 0L)
							jjCheckNAddTwoStates(24, 25);
						break;

					case 25: // '\031'
						if (curChar == '"')
							jjstateSet[jjnewStateCnt++] = 26;
						break;

					case 26: // '\032'
						if (curChar == '*' && kind > 18)
							kind = 18;
						break;

					case 27: // '\033'
						if ((0xfffffffbffffffffL & l) != 0L)
							jjCheckNAddStates(12, 14);
						break;

					case 29: // '\035'
						if (curChar == '"')
							jjCheckNAddStates(12, 14);
						break;

					case 30: // '\036'
						if (curChar == '"' && kind > 19)
							kind = 19;
						break;

					case 31: // '\037'
						if ((0x7bffe8faffffd9ffL & l) != 0L)
						{
							if (kind > 22)
								kind = 22;
							jjCheckNAddStates(0, 4);
						}
						break;

					case 32: // ' '
						if ((0x7bffe8faffffd9ffL & l) != 0L)
							jjCheckNAddTwoStates(32, 33);
						break;

					case 33: // '!'
						if (curChar == '*' && kind > 20)
							kind = 20;
						break;

					case 34: // '"'
						if ((0x7bffe8faffffd9ffL & l) != 0L)
							jjCheckNAddTwoStates(34, 35);
						break;

					case 35: // '#'
						if ((0x8000040000000000L & l) != 0L)
						{
							if (kind > 21)
								kind = 21;
							jjCheckNAddTwoStates(35, 36);
						}
						break;

					case 36: // '$'
						if ((0xfbffecfaffffd9ffL & l) != 0L)
						{
							if (kind > 21)
								kind = 21;
							jjCheckNAdd(36);
						}
						break;

					case 37: // '%'
						if ((0x7bffe8faffffd9ffL & l) != 0L)
						{
							if (kind > 22)
								kind = 22;
							jjCheckNAdd(37);
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
						if ((0xffffffffbfffffffL & l) != 0L)
						{
							if (kind > 22)
								kind = 22;
							jjCheckNAddStates(0, 4);
						}
						if ((0x400000004000L & l) != 0L)
						{
							if (kind > 12)
								kind = 12;
						} else
						if ((0x80000000800000L & l) != 0L)
						{
							if (kind > 11)
								kind = 11;
						} else
						if (curChar == 'a')
							jjstateSet[jjnewStateCnt++] = 9;
						else
						if (curChar == 'A')
							jjstateSet[jjnewStateCnt++] = 6;
						else
						if (curChar == 'o')
							jjstateSet[jjnewStateCnt++] = 3;
						else
						if (curChar == 'O')
							jjstateSet[jjnewStateCnt++] = 1;
						if (curChar == 'n')
							jjstateSet[jjnewStateCnt++] = 15;
						else
						if (curChar == 'N')
							jjstateSet[jjnewStateCnt++] = 12;
						break;

					case 1: // '\001'
						if (curChar == 'R' && kind > 8)
							kind = 8;
						break;

					case 2: // '\002'
						if (curChar == 'O')
							jjstateSet[jjnewStateCnt++] = 1;
						break;

					case 3: // '\003'
						if (curChar == 'r' && kind > 8)
							kind = 8;
						break;

					case 4: // '\004'
						if (curChar == 'o')
							jjstateSet[jjnewStateCnt++] = 3;
						break;

					case 5: // '\005'
						if (curChar == 'D' && kind > 9)
							kind = 9;
						break;

					case 6: // '\006'
						if (curChar == 'N')
							jjstateSet[jjnewStateCnt++] = 5;
						break;

					case 7: // '\007'
						if (curChar == 'A')
							jjstateSet[jjnewStateCnt++] = 6;
						break;

					case 8: // '\b'
						if (curChar == 'd' && kind > 9)
							kind = 9;
						break;

					case 9: // '\t'
						if (curChar == 'n')
							jjstateSet[jjnewStateCnt++] = 8;
						break;

					case 10: // '\n'
						if (curChar == 'a')
							jjstateSet[jjnewStateCnt++] = 9;
						break;

					case 11: // '\013'
						if (curChar == 'T' && kind > 10)
							kind = 10;
						break;

					case 12: // '\f'
						if (curChar == 'O')
							jjstateSet[jjnewStateCnt++] = 11;
						break;

					case 13: // '\r'
						if (curChar == 'N')
							jjstateSet[jjnewStateCnt++] = 12;
						break;

					case 14: // '\016'
						if (curChar == 't' && kind > 10)
							kind = 10;
						break;

					case 15: // '\017'
						if (curChar == 'o')
							jjstateSet[jjnewStateCnt++] = 14;
						break;

					case 16: // '\020'
						if (curChar == 'n')
							jjstateSet[jjnewStateCnt++] = 15;
						break;

					case 17: // '\021'
						if ((0x80000000800000L & l) != 0L && kind > 11)
							kind = 11;
						break;

					case 18: // '\022'
						if ((0x400000004000L & l) != 0L && kind > 12)
							kind = 12;
						break;

					case 24: // '\030'
						jjAddStates(15, 16);
						break;

					case 27: // '\033'
						if ((0xffffffffefffffffL & l) != 0L)
							jjCheckNAddStates(12, 14);
						break;

					case 28: // '\034'
						if (curChar == '\\')
							jjstateSet[jjnewStateCnt++] = 29;
						break;

					case 29: // '\035'
						if (curChar == '\\')
							jjCheckNAddStates(12, 14);
						break;

					case 31: // '\037'
						if ((0xffffffffbfffffffL & l) != 0L)
						{
							if (kind > 22)
								kind = 22;
							jjCheckNAddStates(0, 4);
						}
						break;

					case 32: // ' '
						if ((0xffffffffbfffffffL & l) != 0L)
							jjCheckNAddTwoStates(32, 33);
						break;

					case 34: // '"'
						if ((0xffffffffbfffffffL & l) != 0L)
							jjCheckNAddTwoStates(34, 35);
						break;

					case 36: // '$'
						if ((0xffffffffbfffffffL & l) != 0L)
						{
							if (kind > 21)
								kind = 21;
							jjstateSet[jjnewStateCnt++] = 36;
						}
						break;

					case 37: // '%'
						if ((0xffffffffbfffffffL & l) != 0L)
						{
							if (kind > 22)
								kind = 22;
							jjCheckNAdd(37);
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
							if (kind > 22)
								kind = 22;
							jjCheckNAddStates(0, 4);
						}
						break;

					case 24: // '\030'
						if (jjCanMove_0(hiByte, i1, i2, l1, l2))
							jjAddStates(15, 16);
						break;

					case 27: // '\033'
						if (jjCanMove_0(hiByte, i1, i2, l1, l2))
							jjAddStates(12, 14);
						break;

					case 32: // ' '
						if (jjCanMove_0(hiByte, i1, i2, l1, l2))
							jjCheckNAddTwoStates(32, 33);
						break;

					case 34: // '"'
						if (jjCanMove_0(hiByte, i1, i2, l1, l2))
							jjCheckNAddTwoStates(34, 35);
						break;

					case 36: // '$'
						if (jjCanMove_0(hiByte, i1, i2, l1, l2))
						{
							if (kind > 21)
								kind = 21;
							jjstateSet[jjnewStateCnt++] = 36;
						}
						break;

					case 37: // '%'
						if (jjCanMove_0(hiByte, i1, i2, l1, l2))
						{
							if (kind > 22)
								kind = 22;
							jjCheckNAdd(37);
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
			if ((i = jjnewStateCnt) == (startsAt = 38 - (jjnewStateCnt = startsAt)))
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

	private int jjMoveStringLiteralDfa0_0()
	{
		return jjMoveNfa_0(0, 0);
	}

	private int jjMoveNfa_0(int startState, int curPos)
	{
		int startsAt = 0;
		jjnewStateCnt = 3;
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
						if ((0x3ff000000000000L & l) != 0L)
						{
							if (kind > 23)
								kind = 23;
							jjAddStates(17, 18);
						}
						break;

					case 1: // '\001'
						if (curChar == '.')
							jjCheckNAdd(2);
						break;

					case 2: // '\002'
						if ((0x3ff000000000000L & l) != 0L)
						{
							if (kind > 23)
								kind = 23;
							jjCheckNAdd(2);
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
			if ((i = jjnewStateCnt) == (startsAt = 3 - (jjnewStateCnt = startsAt)))
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
		case 0: // '\0'
			return (jjbitVec2[i2] & l2) != 0L;
		}
		return (jjbitVec0[i1] & l1) != 0L;
	}

	public QueryParserTokenManager(CharStream stream)
	{
		debugStream = System.out;
		jjrounds = new int[38];
		jjstateSet = new int[76];
		curLexState = 1;
		defaultLexState = 1;
		input_stream = stream;
	}

	public QueryParserTokenManager(CharStream stream, int lexState)
	{
		this(stream);
		SwitchTo(lexState);
	}

	public void ReInit(CharStream stream)
	{
		jjmatchedPos = jjnewStateCnt = 0;
		curLexState = defaultLexState;
		input_stream = stream;
		ReInitRounds();
	}

	private void ReInitRounds()
	{
		jjround = 0x80000001;
		for (int i = 38; i-- > 0;)
			jjrounds[i] = 0x80000000;

	}

	public void ReInit(CharStream stream, int lexState)
	{
		ReInit(stream);
		SwitchTo(lexState);
	}

	public void SwitchTo(int lexState)
	{
		if (lexState >= 2 || lexState < 0)
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
			switch (curLexState)
			{
			case 0: // '\0'
				jjmatchedKind = 0x7fffffff;
				jjmatchedPos = 0;
				curPos = jjMoveStringLiteralDfa0_0();
				break;

			case 1: // '\001'
				jjmatchedKind = 0x7fffffff;
				jjmatchedPos = 0;
				curPos = jjMoveStringLiteralDfa0_1();
				break;
			}
			if (jjmatchedKind == 0x7fffffff)
				break;
			if (jjmatchedPos + 1 < curPos)
				input_stream.backup(curPos - jjmatchedPos - 1);
			if ((jjtoToken[jjmatchedKind >> 6] & 1L << (jjmatchedKind & 0x3f)) != 0L)
			{
				Token matchedToken = jjFillToken();
				if (jjnewLexState[jjmatchedKind] != -1)
					curLexState = jjnewLexState[jjmatchedKind];
				return matchedToken;
			}
			if (jjnewLexState[jjmatchedKind] != -1)
				curLexState = jjnewLexState[jjmatchedKind];
		} while (true);
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

	private void jjCheckNAddStates(int start, int end)
	{
		do
			jjCheckNAdd(jjnextStates[start]);
		while (start++ != end);
	}

}
