// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryParserTokenManager.java

package org.apache.lucene.queryparser.classic;

import java.io.IOException;
import java.io.PrintStream;

// Referenced classes of package org.apache.lucene.queryparser.classic:
//			TokenMgrError, QueryParserConstants, Token, CharStream

public class QueryParserTokenManager
	implements QueryParserConstants
{

	public PrintStream debugStream;
	static final long jjbitVec0[] = {
		1L, 0L, 0L, 0L
	};
	static final long jjbitVec1[] = {
		-2L, -1L, -1L, -1L
	};
	static final long jjbitVec3[] = {
		0L, 0L, -1L, -1L
	};
	static final long jjbitVec4[] = {
		0xfffefffffffffffeL, -1L, -1L, -1L
	};
	static final int jjnextStates[] = {
		31, 33, 34, 17, 18, 20, 36, 39, 25, 40, 
		37, 22, 23, 39, 25, 40, 38, 41, 29, 0, 
		1, 2, 4, 5
	};
	public static final String jjstrLiteralImages[] = {
		"", null, null, null, null, null, null, null, null, null, 
		null, "+", "-", null, "(", ")", ":", "*", "^", null, 
		null, null, null, null, null, "[", "{", null, "TO", "]", 
		"}", null, null
	};
	public static final String lexStateNames[] = {
		"Boost", "Range", "DEFAULT"
	};
	public static final int jjnewLexState[] = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, 0, -1, 
		-1, -1, -1, -1, -1, 1, 1, 2, -1, 2, 
		2, -1, -1
	};
	static final long jjtoToken[] = {
		0x1ffffff01L
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

	private final int jjStopStringLiteralDfa_2(int pos, long active0)
	{
		switch (pos)
		{
		default:
			return -1;
		}
	}

	private final int jjStartNfa_2(int pos, long active0)
	{
		return jjMoveNfa_2(jjStopStringLiteralDfa_2(pos, active0), pos + 1);
	}

	private int jjStopAtPos(int pos, int kind)
	{
		jjmatchedKind = kind;
		jjmatchedPos = pos;
		return pos + 1;
	}

	private int jjMoveStringLiteralDfa0_2()
	{
		switch (curChar)
		{
		case 40: // '('
			return jjStopAtPos(0, 14);

		case 41: // ')'
			return jjStopAtPos(0, 15);

		case 42: // '*'
			return jjStartNfaWithStates_2(0, 17, 43);

		case 43: // '+'
			return jjStartNfaWithStates_2(0, 11, 15);

		case 45: // '-'
			return jjStartNfaWithStates_2(0, 12, 15);

		case 58: // ':'
			return jjStopAtPos(0, 16);

		case 91: // '['
			return jjStopAtPos(0, 25);

		case 94: // '^'
			return jjStopAtPos(0, 18);

		case 123: // '{'
			return jjStopAtPos(0, 26);
		}
		return jjMoveNfa_2(0, 0);
	}

	private int jjStartNfaWithStates_2(int pos, int kind, int state)
	{
		jjmatchedKind = kind;
		jjmatchedPos = pos;
		try
		{
			curChar = input_stream.readChar();
		}
		catch (IOException e)
		{
			return pos + 1;
		}
		return jjMoveNfa_2(state, pos + 1);
	}

	private int jjMoveNfa_2(int startState, int curPos)
	{
		int startsAt = 0;
		jjnewStateCnt = 43;
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
						if ((0xfbff54f8ffffd9ffL & l) != 0L)
						{
							if (kind > 23)
								kind = 23;
							jjCheckNAddTwoStates(27, 28);
						} else
						if ((0x100002600L & l) != 0L)
						{
							if (kind > 7)
								kind = 7;
						} else
						if ((0x280200000000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 15;
						else
						if (curChar == '/')
							jjCheckNAddStates(0, 2);
						else
						if (curChar == '"')
							jjCheckNAddStates(3, 5);
						if ((0x7bff50f8ffffd9ffL & l) != 0L)
						{
							if (kind > 20)
								kind = 20;
							jjCheckNAddStates(6, 10);
						} else
						if (curChar == '*')
						{
							if (kind > 22)
								kind = 22;
						} else
						if (curChar == '!' && kind > 10)
							kind = 10;
						if (curChar == '&')
							jjstateSet[jjnewStateCnt++] = 4;
						break;

					case 27: // '\033'
					case 43: // '+'
						if ((0xfbff7cf8ffffd9ffL & l) != 0L)
						{
							if (kind > 23)
								kind = 23;
							jjCheckNAddTwoStates(27, 28);
						}
						break;

					case 4: // '\004'
						if (curChar == '&' && kind > 8)
							kind = 8;
						break;

					case 5: // '\005'
						if (curChar == '&')
							jjstateSet[jjnewStateCnt++] = 4;
						break;

					case 13: // '\r'
						if (curChar == '!' && kind > 10)
							kind = 10;
						break;

					case 14: // '\016'
						if ((0x280200000000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 15;
						break;

					case 15: // '\017'
						if ((0x100002600L & l) != 0L && kind > 13)
							kind = 13;
						break;

					case 16: // '\020'
						if (curChar == '"')
							jjCheckNAddStates(3, 5);
						break;

					case 17: // '\021'
						if ((0xfffffffbffffffffL & l) != 0L)
							jjCheckNAddStates(3, 5);
						break;

					case 19: // '\023'
						jjCheckNAddStates(3, 5);
						break;

					case 20: // '\024'
						if (curChar == '"' && kind > 19)
							kind = 19;
						break;

					case 22: // '\026'
						if ((0x3ff000000000000L & l) != 0L)
						{
							if (kind > 21)
								kind = 21;
							jjAddStates(11, 12);
						}
						break;

					case 23: // '\027'
						if (curChar == '.')
							jjCheckNAdd(24);
						break;

					case 24: // '\030'
						if ((0x3ff000000000000L & l) != 0L)
						{
							if (kind > 21)
								kind = 21;
							jjCheckNAdd(24);
						}
						break;

					case 25: // '\031'
						if (curChar == '*' && kind > 22)
							kind = 22;
						break;

					case 26: // '\032'
						if ((0xfbff54f8ffffd9ffL & l) != 0L)
						{
							if (kind > 23)
								kind = 23;
							jjCheckNAddTwoStates(27, 28);
						}
						break;

					case 29: // '\035'
						if (kind > 23)
							kind = 23;
						jjCheckNAddTwoStates(27, 28);
						break;

					case 30: // '\036'
					case 32: // ' '
						if (curChar == '/')
							jjCheckNAddStates(0, 2);
						break;

					case 31: // '\037'
						if ((0xffff7fffffffffffL & l) != 0L)
							jjCheckNAddStates(0, 2);
						break;

					case 34: // '"'
						if (curChar == '/' && kind > 24)
							kind = 24;
						break;

					case 35: // '#'
						if ((0x7bff50f8ffffd9ffL & l) != 0L)
						{
							if (kind > 20)
								kind = 20;
							jjCheckNAddStates(6, 10);
						}
						break;

					case 36: // '$'
						if ((0x7bff78f8ffffd9ffL & l) != 0L)
						{
							if (kind > 20)
								kind = 20;
							jjCheckNAddTwoStates(36, 37);
						}
						break;

					case 38: // '&'
						if (kind > 20)
							kind = 20;
						jjCheckNAddTwoStates(36, 37);
						break;

					case 39: // '\''
						if ((0x7bff78f8ffffd9ffL & l) != 0L)
							jjCheckNAddStates(13, 15);
						break;

					case 41: // ')'
						jjCheckNAddStates(13, 15);
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
						if ((0x97ffffff87ffffffL & l) != 0L)
						{
							if (kind > 20)
								kind = 20;
							jjCheckNAddStates(6, 10);
						} else
						if (curChar == '\\')
							jjCheckNAddStates(16, 18);
						else
						if (curChar == '~')
						{
							if (kind > 21)
								kind = 21;
							jjstateSet[jjnewStateCnt++] = 22;
						}
						if ((0x97ffffff87ffffffL & l) != 0L)
						{
							if (kind > 23)
								kind = 23;
							jjCheckNAddTwoStates(27, 28);
						}
						if (curChar == 'N')
							jjstateSet[jjnewStateCnt++] = 11;
						else
						if (curChar == '|')
							jjstateSet[jjnewStateCnt++] = 8;
						else
						if (curChar == 'O')
							jjstateSet[jjnewStateCnt++] = 6;
						else
						if (curChar == 'A')
							jjstateSet[jjnewStateCnt++] = 2;
						break;

					case 43: // '+'
						if ((0x97ffffff87ffffffL & l) != 0L)
						{
							if (kind > 23)
								kind = 23;
							jjCheckNAddTwoStates(27, 28);
						} else
						if (curChar == '\\')
							jjCheckNAddTwoStates(29, 29);
						break;

					case 1: // '\001'
						if (curChar == 'D' && kind > 8)
							kind = 8;
						break;

					case 2: // '\002'
						if (curChar == 'N')
							jjstateSet[jjnewStateCnt++] = 1;
						break;

					case 3: // '\003'
						if (curChar == 'A')
							jjstateSet[jjnewStateCnt++] = 2;
						break;

					case 6: // '\006'
						if (curChar == 'R' && kind > 9)
							kind = 9;
						break;

					case 7: // '\007'
						if (curChar == 'O')
							jjstateSet[jjnewStateCnt++] = 6;
						break;

					case 8: // '\b'
						if (curChar == '|' && kind > 9)
							kind = 9;
						break;

					case 9: // '\t'
						if (curChar == '|')
							jjstateSet[jjnewStateCnt++] = 8;
						break;

					case 10: // '\n'
						if (curChar == 'T' && kind > 10)
							kind = 10;
						break;

					case 11: // '\013'
						if (curChar == 'O')
							jjstateSet[jjnewStateCnt++] = 10;
						break;

					case 12: // '\f'
						if (curChar == 'N')
							jjstateSet[jjnewStateCnt++] = 11;
						break;

					case 17: // '\021'
						if ((0xffffffffefffffffL & l) != 0L)
							jjCheckNAddStates(3, 5);
						break;

					case 18: // '\022'
						if (curChar == '\\')
							jjstateSet[jjnewStateCnt++] = 19;
						break;

					case 19: // '\023'
						jjCheckNAddStates(3, 5);
						break;

					case 21: // '\025'
						if (curChar == '~')
						{
							if (kind > 21)
								kind = 21;
							jjstateSet[jjnewStateCnt++] = 22;
						}
						break;

					case 26: // '\032'
						if ((0x97ffffff87ffffffL & l) != 0L)
						{
							if (kind > 23)
								kind = 23;
							jjCheckNAddTwoStates(27, 28);
						}
						break;

					case 27: // '\033'
						if ((0x97ffffff87ffffffL & l) != 0L)
						{
							if (kind > 23)
								kind = 23;
							jjCheckNAddTwoStates(27, 28);
						}
						break;

					case 28: // '\034'
						if (curChar == '\\')
							jjCheckNAddTwoStates(29, 29);
						break;

					case 29: // '\035'
						if (kind > 23)
							kind = 23;
						jjCheckNAddTwoStates(27, 28);
						break;

					case 31: // '\037'
						jjAddStates(0, 2);
						break;

					case 33: // '!'
						if (curChar == '\\')
							jjstateSet[jjnewStateCnt++] = 32;
						break;

					case 35: // '#'
						if ((0x97ffffff87ffffffL & l) != 0L)
						{
							if (kind > 20)
								kind = 20;
							jjCheckNAddStates(6, 10);
						}
						break;

					case 36: // '$'
						if ((0x97ffffff87ffffffL & l) != 0L)
						{
							if (kind > 20)
								kind = 20;
							jjCheckNAddTwoStates(36, 37);
						}
						break;

					case 37: // '%'
						if (curChar == '\\')
							jjCheckNAddTwoStates(38, 38);
						break;

					case 38: // '&'
						if (kind > 20)
							kind = 20;
						jjCheckNAddTwoStates(36, 37);
						break;

					case 39: // '\''
						if ((0x97ffffff87ffffffL & l) != 0L)
							jjCheckNAddStates(13, 15);
						break;

					case 40: // '('
						if (curChar == '\\')
							jjCheckNAddTwoStates(41, 41);
						break;

					case 41: // ')'
						jjCheckNAddStates(13, 15);
						break;

					case 42: // '*'
						if (curChar == '\\')
							jjCheckNAddStates(16, 18);
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
						if (jjCanMove_0(hiByte, i1, i2, l1, l2) && kind > 7)
							kind = 7;
						if (jjCanMove_2(hiByte, i1, i2, l1, l2))
						{
							if (kind > 23)
								kind = 23;
							jjCheckNAddTwoStates(27, 28);
						}
						if (jjCanMove_2(hiByte, i1, i2, l1, l2))
						{
							if (kind > 20)
								kind = 20;
							jjCheckNAddStates(6, 10);
						}
						break;

					case 27: // '\033'
					case 43: // '+'
						if (jjCanMove_2(hiByte, i1, i2, l1, l2))
						{
							if (kind > 23)
								kind = 23;
							jjCheckNAddTwoStates(27, 28);
						}
						break;

					case 15: // '\017'
						if (jjCanMove_0(hiByte, i1, i2, l1, l2) && kind > 13)
							kind = 13;
						break;

					case 17: // '\021'
					case 19: // '\023'
						if (jjCanMove_1(hiByte, i1, i2, l1, l2))
							jjCheckNAddStates(3, 5);
						break;

					case 26: // '\032'
						if (jjCanMove_2(hiByte, i1, i2, l1, l2))
						{
							if (kind > 23)
								kind = 23;
							jjCheckNAddTwoStates(27, 28);
						}
						break;

					case 29: // '\035'
						if (jjCanMove_1(hiByte, i1, i2, l1, l2))
						{
							if (kind > 23)
								kind = 23;
							jjCheckNAddTwoStates(27, 28);
						}
						break;

					case 31: // '\037'
						if (jjCanMove_1(hiByte, i1, i2, l1, l2))
							jjAddStates(0, 2);
						break;

					case 35: // '#'
						if (jjCanMove_2(hiByte, i1, i2, l1, l2))
						{
							if (kind > 20)
								kind = 20;
							jjCheckNAddStates(6, 10);
						}
						break;

					case 36: // '$'
						if (jjCanMove_2(hiByte, i1, i2, l1, l2))
						{
							if (kind > 20)
								kind = 20;
							jjCheckNAddTwoStates(36, 37);
						}
						break;

					case 38: // '&'
						if (jjCanMove_1(hiByte, i1, i2, l1, l2))
						{
							if (kind > 20)
								kind = 20;
							jjCheckNAddTwoStates(36, 37);
						}
						break;

					case 39: // '\''
						if (jjCanMove_2(hiByte, i1, i2, l1, l2))
							jjCheckNAddStates(13, 15);
						break;

					case 41: // ')'
						if (jjCanMove_1(hiByte, i1, i2, l1, l2))
							jjCheckNAddStates(13, 15);
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
			if ((i = jjnewStateCnt) == (startsAt = 43 - (jjnewStateCnt = startsAt)))
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
							if (kind > 27)
								kind = 27;
							jjAddStates(19, 20);
						}
						break;

					case 1: // '\001'
						if (curChar == '.')
							jjCheckNAdd(2);
						break;

					case 2: // '\002'
						if ((0x3ff000000000000L & l) != 0L)
						{
							if (kind > 27)
								kind = 27;
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

	private final int jjStopStringLiteralDfa_1(int pos, long active0)
	{
		switch (pos)
		{
		case 0: // '\0'
			if ((active0 & 0x10000000L) != 0L)
			{
				jjmatchedKind = 32;
				return 6;
			} else
			{
				return -1;
			}
		}
		return -1;
	}

	private final int jjStartNfa_1(int pos, long active0)
	{
		return jjMoveNfa_1(jjStopStringLiteralDfa_1(pos, active0), pos + 1);
	}

	private int jjMoveStringLiteralDfa0_1()
	{
		switch (curChar)
		{
		case 84: // 'T'
			return jjMoveStringLiteralDfa1_1(0x10000000L);

		case 93: // ']'
			return jjStopAtPos(0, 29);

		case 125: // '}'
			return jjStopAtPos(0, 30);
		}
		return jjMoveNfa_1(0, 0);
	}

	private int jjMoveStringLiteralDfa1_1(long active0)
	{
		try
		{
			curChar = input_stream.readChar();
		}
		catch (IOException e)
		{
			jjStopStringLiteralDfa_1(0, active0);
			return 1;
		}
		switch (curChar)
		{
		case 79: // 'O'
			if ((active0 & 0x10000000L) != 0L)
				return jjStartNfaWithStates_1(1, 28, 6);
			break;
		}
		return jjStartNfa_1(0, active0);
	}

	private int jjStartNfaWithStates_1(int pos, int kind, int state)
	{
		jjmatchedKind = kind;
		jjmatchedPos = pos;
		try
		{
			curChar = input_stream.readChar();
		}
		catch (IOException e)
		{
			return pos + 1;
		}
		return jjMoveNfa_1(state, pos + 1);
	}

	private int jjMoveNfa_1(int startState, int curPos)
	{
		int startsAt = 0;
		jjnewStateCnt = 7;
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
						if ((0xfffffffeffffffffL & l) != 0L)
						{
							if (kind > 32)
								kind = 32;
							jjCheckNAdd(6);
						}
						if ((0x100002600L & l) != 0L)
						{
							if (kind > 7)
								kind = 7;
						} else
						if (curChar == '"')
							jjCheckNAddTwoStates(2, 4);
						break;

					case 1: // '\001'
						if (curChar == '"')
							jjCheckNAddTwoStates(2, 4);
						break;

					case 2: // '\002'
						if ((0xfffffffbffffffffL & l) != 0L)
							jjCheckNAddStates(21, 23);
						break;

					case 3: // '\003'
						if (curChar == '"')
							jjCheckNAddStates(21, 23);
						break;

					case 5: // '\005'
						if (curChar == '"' && kind > 31)
							kind = 31;
						break;

					case 6: // '\006'
						if ((0xfffffffeffffffffL & l) != 0L)
						{
							if (kind > 32)
								kind = 32;
							jjCheckNAdd(6);
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
					case 6: // '\006'
						if ((0xdfffffffdfffffffL & l) != 0L)
						{
							if (kind > 32)
								kind = 32;
							jjCheckNAdd(6);
						}
						break;

					case 2: // '\002'
						jjAddStates(21, 23);
						break;

					case 4: // '\004'
						if (curChar == '\\')
							jjstateSet[jjnewStateCnt++] = 3;
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
						if (jjCanMove_0(hiByte, i1, i2, l1, l2) && kind > 7)
							kind = 7;
						if (jjCanMove_1(hiByte, i1, i2, l1, l2))
						{
							if (kind > 32)
								kind = 32;
							jjCheckNAdd(6);
						}
						break;

					case 2: // '\002'
						if (jjCanMove_1(hiByte, i1, i2, l1, l2))
							jjAddStates(21, 23);
						break;

					case 6: // '\006'
						if (jjCanMove_1(hiByte, i1, i2, l1, l2))
						{
							if (kind > 32)
								kind = 32;
							jjCheckNAdd(6);
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
			if ((i = jjnewStateCnt) == (startsAt = 7 - (jjnewStateCnt = startsAt)))
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
		case 48: // '0'
			return (jjbitVec0[i2] & l2) != 0L;
		}
		return false;
	}

	private static final boolean jjCanMove_1(int hiByte, int i1, int i2, long l1, long l2)
	{
		switch (hiByte)
		{
		case 0: // '\0'
			return (jjbitVec3[i2] & l2) != 0L;
		}
		return (jjbitVec1[i1] & l1) != 0L;
	}

	private static final boolean jjCanMove_2(int hiByte, int i1, int i2, long l1, long l2)
	{
		switch (hiByte)
		{
		case 0: // '\0'
			return (jjbitVec3[i2] & l2) != 0L;

		case 48: // '0'
			return (jjbitVec1[i2] & l2) != 0L;
		}
		return (jjbitVec4[i1] & l1) != 0L;
	}

	public QueryParserTokenManager(CharStream stream)
	{
		debugStream = System.out;
		jjrounds = new int[43];
		jjstateSet = new int[86];
		curLexState = 2;
		defaultLexState = 2;
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
		for (int i = 43; i-- > 0;)
			jjrounds[i] = 0x80000000;

	}

	public void ReInit(CharStream stream, int lexState)
	{
		ReInit(stream);
		SwitchTo(lexState);
	}

	public void SwitchTo(int lexState)
	{
		if (lexState >= 3 || lexState < 0)
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

			case 2: // '\002'
				jjmatchedKind = 0x7fffffff;
				jjmatchedPos = 0;
				curPos = jjMoveStringLiteralDfa0_2();
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
