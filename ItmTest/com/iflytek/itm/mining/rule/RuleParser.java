// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RuleParser.java

package com.iflytek.itm.mining.rule;

import java.io.*;
import java.util.*;

// Referenced classes of package com.iflytek.itm.mining.rule:
//			ParseException, RuleParserConstants, JavaCharStream, RuleParserTokenManager, 
//			Token, TokenMgrError

public class RuleParser
	implements RuleParserConstants
{
	static final class JJCalls
	{

		int gen;
		Token first;
		int arg;
		JJCalls next;

		JJCalls()
		{
		}
	}

	private static final class LookaheadSuccess extends Error
	{

		private LookaheadSuccess()
		{
		}

	}


	public RuleParserTokenManager token_source;
	JavaCharStream jj_input_stream;
	public Token token;
	public Token jj_nt;
	private int jj_ntk;
	private Token jj_scanpos;
	private Token jj_lastpos;
	private int jj_la;
	private int jj_gen;
	private final int jj_la1[];
	private static int jj_la1_0[];
	private final JJCalls jj_2_rtns[];
	private boolean jj_rescan;
	private int jj_gc;
	private final LookaheadSuccess jj_ls;
	private List jj_expentries;
	private int jj_expentry[];
	private int jj_kind;
	private int jj_lasttokens[];
	private int jj_endpos;

	public RuleParser(String s)
	{
		this(((Reader) (new StringReader(s))));
	}

	public static void main(String args[])
		throws Exception, TokenMgrError
	{
		String s = "test";
		parse(s);
	}

	public static void parse(String str)
		throws Exception, TokenMgrError
	{
		RuleParser parser = new RuleParser(str);
		parser.expression();
		if (parser.getNextToken().kind != 0)
			throw new ParseException();
		else
			return;
	}

	public final void expression()
		throws ParseException
	{
		query();
label0:
		do
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			default:
				jj_la1[0] = jj_gen;
				break label0;

			case 5: // '\005'
			case 6: // '\006'
				switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
				{
				case 5: // '\005'
					jj_consume_token(5);
					break;

				case 6: // '\006'
					jj_consume_token(6);
					break;

				default:
					jj_la1[1] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
				}
				query();
				break;
			}
		while (true);
	}

	public final void query()
		throws ParseException
	{
		switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
		{
		case 7: // '\007'
			jj_consume_token(7);
			break;

		default:
			jj_la1[2] = jj_gen;
			break;
		}
		term();
	}

	public final void term()
		throws ParseException
	{
		switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
		{
		case 10: // '\n'
			jj_consume_token(10);
			break;

		case 11: // '\013'
			if (jj_2_1(3))
			{
				near();
				break;
			}
			if (jj_2_2(3))
			{
				plus();
				break;
			}
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 11: // '\013'
				jj_consume_token(11);
				expression();
				jj_consume_token(12);
				break;

			default:
				jj_la1[3] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
			}
			break;

		default:
			jj_la1[4] = jj_gen;
			jj_consume_token(-1);
			throw new ParseException();
		}
	}

	public final void near()
		throws ParseException
	{
		jj_consume_token(11);
		jj_consume_token(10);
		jj_consume_token(8);
		jj_consume_token(10);
		jj_consume_token(12);
	}

	public final void plus()
		throws ParseException
	{
		jj_consume_token(11);
		jj_consume_token(10);
		jj_consume_token(9);
		jj_consume_token(10);
		jj_consume_token(12);
	}

	private boolean jj_2_1(int xla)
	{
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		boolean flag = !jj_3_1();
		jj_save(0, xla);
		return flag;
		LookaheadSuccess ls;
		ls;
		boolean flag1 = true;
		jj_save(0, xla);
		return flag1;
		Exception exception;
		exception;
		jj_save(0, xla);
		throw exception;
	}

	private boolean jj_2_2(int xla)
	{
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		boolean flag = !jj_3_2();
		jj_save(1, xla);
		return flag;
		LookaheadSuccess ls;
		ls;
		boolean flag1 = true;
		jj_save(1, xla);
		return flag1;
		Exception exception;
		exception;
		jj_save(1, xla);
		throw exception;
	}

	private boolean jj_3R_2()
	{
		if (jj_scan_token(11))
			return true;
		if (jj_scan_token(10))
			return true;
		return jj_scan_token(8);
	}

	private boolean jj_3R_3()
	{
		if (jj_scan_token(11))
			return true;
		if (jj_scan_token(10))
			return true;
		return jj_scan_token(9);
	}

	private boolean jj_3_2()
	{
		return jj_3R_3();
	}

	private boolean jj_3_1()
	{
		return jj_3R_2();
	}

	private static void jj_la1_init_0()
	{
		jj_la1_0 = (new int[] {
			96, 96, 128, 2048, 3072
		});
	}

	public RuleParser(InputStream stream)
	{
		this(stream, null);
	}

	public RuleParser(InputStream stream, String encoding)
	{
		jj_la1 = new int[5];
		jj_2_rtns = new JJCalls[2];
		jj_rescan = false;
		jj_gc = 0;
		jj_ls = new LookaheadSuccess();
		jj_expentries = new ArrayList();
		jj_kind = -1;
		jj_lasttokens = new int[100];
		try
		{
			jj_input_stream = new JavaCharStream(stream, encoding, 1, 1);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}
		token_source = new RuleParserTokenManager(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 5; i++)
			jj_la1[i] = -1;

		for (int i = 0; i < jj_2_rtns.length; i++)
			jj_2_rtns[i] = new JJCalls();

	}

	public void ReInit(InputStream stream)
	{
		ReInit(stream, null);
	}

	public void ReInit(InputStream stream, String encoding)
	{
		try
		{
			jj_input_stream.ReInit(stream, encoding, 1, 1);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}
		token_source.ReInit(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 5; i++)
			jj_la1[i] = -1;

		for (int i = 0; i < jj_2_rtns.length; i++)
			jj_2_rtns[i] = new JJCalls();

	}

	public RuleParser(Reader stream)
	{
		jj_la1 = new int[5];
		jj_2_rtns = new JJCalls[2];
		jj_rescan = false;
		jj_gc = 0;
		jj_ls = new LookaheadSuccess();
		jj_expentries = new ArrayList();
		jj_kind = -1;
		jj_lasttokens = new int[100];
		jj_input_stream = new JavaCharStream(stream, 1, 1);
		token_source = new RuleParserTokenManager(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 5; i++)
			jj_la1[i] = -1;

		for (int i = 0; i < jj_2_rtns.length; i++)
			jj_2_rtns[i] = new JJCalls();

	}

	public void ReInit(Reader stream)
	{
		jj_input_stream.ReInit(stream, 1, 1);
		token_source.ReInit(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 5; i++)
			jj_la1[i] = -1;

		for (int i = 0; i < jj_2_rtns.length; i++)
			jj_2_rtns[i] = new JJCalls();

	}

	public RuleParser(RuleParserTokenManager tm)
	{
		jj_la1 = new int[5];
		jj_2_rtns = new JJCalls[2];
		jj_rescan = false;
		jj_gc = 0;
		jj_ls = new LookaheadSuccess();
		jj_expentries = new ArrayList();
		jj_kind = -1;
		jj_lasttokens = new int[100];
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 5; i++)
			jj_la1[i] = -1;

		for (int i = 0; i < jj_2_rtns.length; i++)
			jj_2_rtns[i] = new JJCalls();

	}

	public void ReInit(RuleParserTokenManager tm)
	{
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 5; i++)
			jj_la1[i] = -1;

		for (int i = 0; i < jj_2_rtns.length; i++)
			jj_2_rtns[i] = new JJCalls();

	}

	private Token jj_consume_token(int kind)
		throws ParseException
	{
		Token oldToken;
		if ((oldToken = token).next != null)
			token = token.next;
		else
			token = token.next = token_source.getNextToken();
		jj_ntk = -1;
		if (token.kind == kind)
		{
			jj_gen++;
			if (++jj_gc > 100)
			{
				jj_gc = 0;
				for (int i = 0; i < jj_2_rtns.length; i++)
				{
					for (JJCalls c = jj_2_rtns[i]; c != null; c = c.next)
						if (c.gen < jj_gen)
							c.first = null;

				}

			}
			return token;
		} else
		{
			token = oldToken;
			jj_kind = kind;
			throw generateParseException();
		}
	}

	private boolean jj_scan_token(int kind)
	{
		if (jj_scanpos == jj_lastpos)
		{
			jj_la--;
			if (jj_scanpos.next == null)
				jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
			else
				jj_lastpos = jj_scanpos = jj_scanpos.next;
		} else
		{
			jj_scanpos = jj_scanpos.next;
		}
		if (jj_rescan)
		{
			int i = 0;
			Token tok;
			for (tok = token; tok != null && tok != jj_scanpos; tok = tok.next)
				i++;

			if (tok != null)
				jj_add_error_token(kind, i);
		}
		if (jj_scanpos.kind != kind)
			return true;
		if (jj_la == 0 && jj_scanpos == jj_lastpos)
			throw jj_ls;
		else
			return false;
	}

	public final Token getNextToken()
	{
		if (token.next != null)
			token = token.next;
		else
			token = token.next = token_source.getNextToken();
		jj_ntk = -1;
		jj_gen++;
		return token;
	}

	public final Token getToken(int index)
	{
		Token t = token;
		for (int i = 0; i < index; i++)
			if (t.next != null)
				t = t.next;
			else
				t = t.next = token_source.getNextToken();

		return t;
	}

	private int jj_ntk()
	{
		if ((jj_nt = token.next) == null)
			return jj_ntk = (token.next = token_source.getNextToken()).kind;
		else
			return jj_ntk = jj_nt.kind;
	}

	private void jj_add_error_token(int kind, int pos)
	{
label0:
		{
label1:
			{
				if (pos >= 100)
					return;
				if (pos == jj_endpos + 1)
				{
					jj_lasttokens[jj_endpos++] = kind;
					break label0;
				}
				if (jj_endpos == 0)
					break label0;
				jj_expentry = new int[jj_endpos];
				for (int i = 0; i < jj_endpos; i++)
					jj_expentry[i] = jj_lasttokens[i];

				Iterator it = jj_expentries.iterator();
label2:
				do
				{
					int oldentry[];
					do
					{
						if (!it.hasNext())
							break label1;
						oldentry = (int[])(int[])it.next();
					} while (oldentry.length != jj_expentry.length);
					int i = 0;
					do
					{
						if (i >= jj_expentry.length)
							break label2;
						if (oldentry[i] != jj_expentry[i])
							continue label2;
						i++;
					} while (true);
				} while (true);
				jj_expentries.add(jj_expentry);
			}
			if (pos != 0)
				jj_lasttokens[(jj_endpos = pos) - 1] = kind;
		}
	}

	public ParseException generateParseException()
	{
		jj_expentries.clear();
		boolean la1tokens[] = new boolean[13];
		if (jj_kind >= 0)
		{
			la1tokens[jj_kind] = true;
			jj_kind = -1;
		}
		for (int i = 0; i < 5; i++)
		{
			if (jj_la1[i] != jj_gen)
				continue;
			for (int j = 0; j < 32; j++)
				if ((jj_la1_0[i] & 1 << j) != 0)
					la1tokens[j] = true;

		}

		for (int i = 0; i < 13; i++)
			if (la1tokens[i])
			{
				jj_expentry = new int[1];
				jj_expentry[0] = i;
				jj_expentries.add(jj_expentry);
			}

		jj_endpos = 0;
		jj_rescan_token();
		jj_add_error_token(0, 0);
		int exptokseq[][] = new int[jj_expentries.size()][];
		for (int i = 0; i < jj_expentries.size(); i++)
			exptokseq[i] = (int[])jj_expentries.get(i);

		return new ParseException(token, exptokseq, tokenImage);
	}

	public final void enable_tracing()
	{
	}

	public final void disable_tracing()
	{
	}

	private void jj_rescan_token()
	{
		jj_rescan = true;
		for (int i = 0; i < 2; i++)
			try
			{
				JJCalls p = jj_2_rtns[i];
				do
				{
					if (p.gen > jj_gen)
					{
						jj_la = p.arg;
						jj_lastpos = jj_scanpos = p.first;
						switch (i)
						{
						case 0: // '\0'
							jj_3_1();
							break;

						case 1: // '\001'
							jj_3_2();
							break;
						}
					}
					p = p.next;
				} while (p != null);
			}
			catch (LookaheadSuccess ls) { }

		jj_rescan = false;
	}

	private void jj_save(int index, int xla)
	{
		JJCalls p = jj_2_rtns[index];
		do
		{
			if (p.gen <= jj_gen)
				break;
			if (p.next == null)
			{
				p = p.next = new JJCalls();
				break;
			}
			p = p.next;
		} while (true);
		p.gen = (jj_gen + xla) - jj_la;
		p.first = token;
		p.arg = xla;
	}

	static 
	{
		jj_la1_init_0();
	}
}
