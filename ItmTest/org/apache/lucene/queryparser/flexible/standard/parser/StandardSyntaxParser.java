// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StandardSyntaxParser.java

package org.apache.lucene.queryparser.flexible.standard.parser;

import java.io.StringReader;
import java.util.*;
import org.apache.lucene.queryparser.flexible.core.QueryNodeParseException;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.core.nodes.*;
import org.apache.lucene.queryparser.flexible.core.parser.SyntaxParser;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.queryparser.flexible.standard.nodes.RegexpQueryNode;
import org.apache.lucene.queryparser.flexible.standard.nodes.TermRangeQueryNode;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.parser:
//			FastCharStream, ParseException, StandardSyntaxParserTokenManager, Token, 
//			StandardSyntaxParserConstants, CharStream, EscapeQuerySyntaxImpl

public class StandardSyntaxParser
	implements SyntaxParser, StandardSyntaxParserConstants
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


	private static final int CONJ_NONE = 0;
	private static final int CONJ_AND = 2;
	private static final int CONJ_OR = 2;
	public StandardSyntaxParserTokenManager token_source;
	public Token token;
	public Token jj_nt;
	private int jj_ntk;
	private Token jj_scanpos;
	private Token jj_lastpos;
	private int jj_la;
	private int jj_gen;
	private final int jj_la1[];
	private static int jj_la1_0[];
	private static int jj_la1_1[];
	private final JJCalls jj_2_rtns[];
	private boolean jj_rescan;
	private int jj_gc;
	private final LookaheadSuccess jj_ls;
	private List jj_expentries;
	private int jj_expentry[];
	private int jj_kind;
	private int jj_lasttokens[];
	private int jj_endpos;

	public StandardSyntaxParser()
	{
		this(((CharStream) (new FastCharStream(new StringReader("")))));
	}

	public QueryNode parse(CharSequence query, CharSequence field)
		throws QueryNodeParseException
	{
		ReInit(new FastCharStream(new StringReader(query.toString())));
		QueryNode querynode = TopLevelQuery(field);
		return querynode;
		ParseException tme;
		tme;
		tme.setQuery(query);
		throw tme;
		tme;
		org.apache.lucene.queryparser.flexible.messages.Message message = new MessageImpl(QueryParserMessages.INVALID_SYNTAX_CANNOT_PARSE, new Object[] {
			query, tme.getMessage()
		});
		QueryNodeParseException e = new QueryNodeParseException(tme);
		e.setQuery(query);
		e.setNonLocalizedMessage(message);
		throw e;
	}

	public final int Conjunction()
		throws ParseException
	{
		int ret = 0;
		switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
		{
		case 8: // '\b'
		case 9: // '\t'
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 8: // '\b'
				jj_consume_token(8);
				ret = 2;
				break;

			case 9: // '\t'
				jj_consume_token(9);
				ret = 2;
				break;

			default:
				jj_la1[0] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
			}
			break;

		default:
			jj_la1[1] = jj_gen;
			break;
		}
		return ret;
	}

	public final org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier Modifiers()
		throws ParseException
	{
		org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier ret = org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_NONE;
		switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
		{
		case 10: // '\n'
		case 11: // '\013'
		case 12: // '\f'
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 11: // '\013'
				jj_consume_token(11);
				ret = org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_REQ;
				break;

			case 12: // '\f'
				jj_consume_token(12);
				ret = org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_NOT;
				break;

			case 10: // '\n'
				jj_consume_token(10);
				ret = org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_NOT;
				break;

			default:
				jj_la1[2] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
			}
			break;

		default:
			jj_la1[3] = jj_gen;
			break;
		}
		return ret;
	}

	public final QueryNode TopLevelQuery(CharSequence field)
		throws ParseException
	{
		QueryNode q = Query(field);
		jj_consume_token(0);
		return q;
	}

	public final QueryNode Query(CharSequence field)
		throws ParseException
	{
		Vector clauses = null;
		QueryNode first = null;
		first = DisjQuery(field);
label0:
		do
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 14: // '\016'
			case 15: // '\017'
			case 16: // '\020'
			case 17: // '\021'
			case 18: // '\022'
			case 19: // '\023'
			case 20: // '\024'
			case 21: // '\025'
			case 24: // '\030'
			default:
				jj_la1[4] = jj_gen;
				break label0;

			case 10: // '\n'
			case 11: // '\013'
			case 12: // '\f'
			case 13: // '\r'
			case 22: // '\026'
			case 23: // '\027'
			case 25: // '\031'
			case 26: // '\032'
			case 27: // '\033'
			case 28: // '\034'
				QueryNode c = DisjQuery(field);
				if (clauses == null)
				{
					clauses = new Vector();
					clauses.addElement(first);
				}
				clauses.addElement(c);
				break;
			}
		while (true);
		if (clauses != null)
			return new BooleanQueryNode(clauses);
		else
			return first;
	}

	public final QueryNode DisjQuery(CharSequence field)
		throws ParseException
	{
		Vector clauses = null;
		QueryNode first = ConjQuery(field);
label0:
		do
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			default:
				jj_la1[5] = jj_gen;
				break label0;

			case 9: // '\t'
				jj_consume_token(9);
				QueryNode c = ConjQuery(field);
				if (clauses == null)
				{
					clauses = new Vector();
					clauses.addElement(first);
				}
				clauses.addElement(c);
				break;
			}
		while (true);
		if (clauses != null)
			return new OrQueryNode(clauses);
		else
			return first;
	}

	public final QueryNode ConjQuery(CharSequence field)
		throws ParseException
	{
		Vector clauses = null;
		QueryNode first = ModClause(field);
label0:
		do
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			default:
				jj_la1[6] = jj_gen;
				break label0;

			case 8: // '\b'
				jj_consume_token(8);
				QueryNode c = ModClause(field);
				if (clauses == null)
				{
					clauses = new Vector();
					clauses.addElement(first);
				}
				clauses.addElement(c);
				break;
			}
		while (true);
		if (clauses != null)
			return new AndQueryNode(clauses);
		else
			return first;
	}

	public final QueryNode ModClause(CharSequence field)
		throws ParseException
	{
		org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier mods = Modifiers();
		QueryNode q = Clause(field);
		if (mods != org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier.MOD_NONE)
			q = new ModifierQueryNode(q, mods);
		return q;
	}

	public final QueryNode Clause(CharSequence field)
		throws ParseException
	{
		Token fieldToken = null;
		Token boost = null;
		Token operator = null;
		Token term = null;
		boolean group = false;
		QueryNode q;
		if (jj_2_2(3))
		{
			fieldToken = jj_consume_token(23);
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 15: // '\017'
			case 16: // '\020'
				switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
				{
				case 15: // '\017'
					jj_consume_token(15);
					break;

				case 16: // '\020'
					jj_consume_token(16);
					break;

				default:
					jj_la1[7] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
				}
				field = EscapeQuerySyntaxImpl.discardEscapeChar(fieldToken.image);
				q = Term(field);
				break;

			case 17: // '\021'
			case 18: // '\022'
			case 19: // '\023'
			case 20: // '\024'
				switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
				{
				case 17: // '\021'
					operator = jj_consume_token(17);
					break;

				case 18: // '\022'
					operator = jj_consume_token(18);
					break;

				case 19: // '\023'
					operator = jj_consume_token(19);
					break;

				case 20: // '\024'
					operator = jj_consume_token(20);
					break;

				default:
					jj_la1[8] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
				}
				field = EscapeQuerySyntaxImpl.discardEscapeChar(fieldToken.image);
				switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
				{
				case 23: // '\027'
					term = jj_consume_token(23);
					break;

				case 22: // '\026'
					term = jj_consume_token(22);
					break;

				case 28: // '\034'
					term = jj_consume_token(28);
					break;

				default:
					jj_la1[9] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
				}
				if (term.kind == 22)
					term.image = term.image.substring(1, term.image.length() - 1);
				FieldQueryNode qLower;
				FieldQueryNode qUpper;
				boolean lowerInclusive;
				boolean upperInclusive;
				switch (operator.kind)
				{
				case 17: // '\021'
					lowerInclusive = true;
					upperInclusive = false;
					qLower = new FieldQueryNode(field, "*", term.beginColumn, term.endColumn);
					qUpper = new FieldQueryNode(field, EscapeQuerySyntaxImpl.discardEscapeChar(term.image), term.beginColumn, term.endColumn);
					break;

				case 18: // '\022'
					lowerInclusive = true;
					upperInclusive = true;
					qLower = new FieldQueryNode(field, "*", term.beginColumn, term.endColumn);
					qUpper = new FieldQueryNode(field, EscapeQuerySyntaxImpl.discardEscapeChar(term.image), term.beginColumn, term.endColumn);
					break;

				case 19: // '\023'
					lowerInclusive = false;
					upperInclusive = true;
					qLower = new FieldQueryNode(field, EscapeQuerySyntaxImpl.discardEscapeChar(term.image), term.beginColumn, term.endColumn);
					qUpper = new FieldQueryNode(field, "*", term.beginColumn, term.endColumn);
					break;

				case 20: // '\024'
					lowerInclusive = true;
					upperInclusive = true;
					qLower = new FieldQueryNode(field, EscapeQuerySyntaxImpl.discardEscapeChar(term.image), term.beginColumn, term.endColumn);
					qUpper = new FieldQueryNode(field, "*", term.beginColumn, term.endColumn);
					break;

				default:
					throw new Error((new StringBuilder()).append("Unhandled case: operator=").append(operator.toString()).toString());
				}
				q = new TermRangeQueryNode(qLower, qUpper, lowerInclusive, upperInclusive);
				break;

			default:
				jj_la1[10] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
			}
		} else
		{
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 13: // '\r'
			case 22: // '\026'
			case 23: // '\027'
			case 25: // '\031'
			case 26: // '\032'
			case 27: // '\033'
			case 28: // '\034'
				if (jj_2_1(2))
				{
					fieldToken = jj_consume_token(23);
					switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
					{
					case 15: // '\017'
						jj_consume_token(15);
						break;

					case 16: // '\020'
						jj_consume_token(16);
						break;

					default:
						jj_la1[11] = jj_gen;
						jj_consume_token(-1);
						throw new ParseException();
					}
					field = EscapeQuerySyntaxImpl.discardEscapeChar(fieldToken.image);
				}
				switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
				{
				case 22: // '\026'
				case 23: // '\027'
				case 25: // '\031'
				case 26: // '\032'
				case 27: // '\033'
				case 28: // '\034'
					q = Term(field);
					break;

				case 13: // '\r'
					jj_consume_token(13);
					q = Query(field);
					jj_consume_token(14);
					switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
					{
					case 21: // '\025'
						jj_consume_token(21);
						boost = jj_consume_token(28);
						break;

					default:
						jj_la1[12] = jj_gen;
						break;
					}
					group = true;
					break;

				case 14: // '\016'
				case 15: // '\017'
				case 16: // '\020'
				case 17: // '\021'
				case 18: // '\022'
				case 19: // '\023'
				case 20: // '\024'
				case 21: // '\025'
				case 24: // '\030'
				default:
					jj_la1[13] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
				}
				break;

			case 14: // '\016'
			case 15: // '\017'
			case 16: // '\020'
			case 17: // '\021'
			case 18: // '\022'
			case 19: // '\023'
			case 20: // '\024'
			case 21: // '\025'
			case 24: // '\030'
			default:
				jj_la1[14] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
			}
		}
		if (boost != null)
		{
			float f = 1.0F;
			try
			{
				f = Float.valueOf(boost.image).floatValue();
				if (q != null)
					q = new BoostQueryNode(q, f);
			}
			catch (Exception ignored) { }
		}
		if (group)
			q = new GroupQueryNode(q);
		return q;
	}

	public final QueryNode Term(CharSequence field)
		throws ParseException
	{
		Token boost = null;
		Token fuzzySlop = null;
		boolean fuzzy = false;
		boolean regexp = false;
		boolean startInc = false;
		boolean endInc = false;
		QueryNode q = null;
		float defaultMinSimilarity = 2.0F;
		switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
		{
		case 23: // '\027'
		case 25: // '\031'
		case 28: // '\034'
		{
			Token term;
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 23: // '\027'
				term = jj_consume_token(23);
				q = new FieldQueryNode(field, EscapeQuerySyntaxImpl.discardEscapeChar(term.image), term.beginColumn, term.endColumn);
				break;

			case 25: // '\031'
				term = jj_consume_token(25);
				regexp = true;
				break;

			case 28: // '\034'
				term = jj_consume_token(28);
				break;

			default:
				jj_la1[15] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
			}
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 24: // '\030'
				fuzzySlop = jj_consume_token(24);
				fuzzy = true;
				break;

			default:
				jj_la1[16] = jj_gen;
				break;
			}
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 21: // '\025'
				jj_consume_token(21);
				boost = jj_consume_token(28);
				switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
				{
				case 24: // '\030'
					fuzzySlop = jj_consume_token(24);
					fuzzy = true;
					break;

				default:
					jj_la1[17] = jj_gen;
					break;
				}
				break;

			default:
				jj_la1[18] = jj_gen;
				break;
			}
			if (fuzzy)
			{
				float fms = defaultMinSimilarity;
				try
				{
					fms = Float.valueOf(fuzzySlop.image.substring(1)).floatValue();
				}
				catch (Exception ignored) { }
				if (fms < 0.0F)
					throw new ParseException(new MessageImpl(QueryParserMessages.INVALID_SYNTAX_FUZZY_LIMITS));
				if (fms >= 1.0F && fms != (float)(int)fms)
					throw new ParseException(new MessageImpl(QueryParserMessages.INVALID_SYNTAX_FUZZY_EDITS));
				q = new FuzzyQueryNode(field, EscapeQuerySyntaxImpl.discardEscapeChar(term.image), fms, term.beginColumn, term.endColumn);
			} else
			if (regexp)
			{
				String re = term.image.substring(1, term.image.length() - 1);
				q = new RegexpQueryNode(field, re, 0, re.length());
			}
			break;
		}

		case 26: // '\032'
		case 27: // '\033'
		{
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 26: // '\032'
				jj_consume_token(26);
				startInc = true;
				break;

			case 27: // '\033'
				jj_consume_token(27);
				break;

			default:
				jj_la1[19] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
			}
			Token goop1;
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 33: // '!'
				goop1 = jj_consume_token(33);
				break;

			case 32: // ' '
				goop1 = jj_consume_token(32);
				break;

			default:
				jj_la1[20] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
			}
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 29: // '\035'
				jj_consume_token(29);
				break;

			default:
				jj_la1[21] = jj_gen;
				break;
			}
			Token goop2;
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 33: // '!'
				goop2 = jj_consume_token(33);
				break;

			case 32: // ' '
				goop2 = jj_consume_token(32);
				break;

			default:
				jj_la1[22] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
			}
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 30: // '\036'
				jj_consume_token(30);
				endInc = true;
				break;

			case 31: // '\037'
				jj_consume_token(31);
				break;

			default:
				jj_la1[23] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
			}
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 21: // '\025'
				jj_consume_token(21);
				boost = jj_consume_token(28);
				break;

			default:
				jj_la1[24] = jj_gen;
				break;
			}
			if (goop1.kind == 32)
				goop1.image = goop1.image.substring(1, goop1.image.length() - 1);
			if (goop2.kind == 32)
				goop2.image = goop2.image.substring(1, goop2.image.length() - 1);
			FieldQueryNode qLower = new FieldQueryNode(field, EscapeQuerySyntaxImpl.discardEscapeChar(goop1.image), goop1.beginColumn, goop1.endColumn);
			FieldQueryNode qUpper = new FieldQueryNode(field, EscapeQuerySyntaxImpl.discardEscapeChar(goop2.image), goop2.beginColumn, goop2.endColumn);
			q = new TermRangeQueryNode(qLower, qUpper, startInc, endInc);
			break;
		}

		case 22: // '\026'
		{
			Token term = jj_consume_token(22);
			q = new QuotedFieldQueryNode(field, EscapeQuerySyntaxImpl.discardEscapeChar(term.image.substring(1, term.image.length() - 1)), term.beginColumn + 1, term.endColumn - 1);
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 24: // '\030'
				fuzzySlop = jj_consume_token(24);
				break;

			default:
				jj_la1[25] = jj_gen;
				break;
			}
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 21: // '\025'
				jj_consume_token(21);
				boost = jj_consume_token(28);
				break;

			default:
				jj_la1[26] = jj_gen;
				break;
			}
			int phraseSlop = 0;
			if (fuzzySlop == null)
				break;
			try
			{
				phraseSlop = Float.valueOf(fuzzySlop.image.substring(1)).intValue();
				q = new SlopQueryNode(q, phraseSlop);
			}
			catch (Exception ignored) { }
			break;
		}

		case 24: // '\030'
		default:
		{
			jj_la1[27] = jj_gen;
			jj_consume_token(-1);
			throw new ParseException();
		}
		}
		if (boost != null)
		{
			float f = 1.0F;
			try
			{
				f = Float.valueOf(boost.image).floatValue();
				if (q != null)
					q = new BoostQueryNode(q, f);
			}
			catch (Exception ignored) { }
		}
		return q;
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

	private boolean jj_3_2()
	{
		if (jj_scan_token(23))
			return true;
		Token xsp = jj_scanpos;
		if (jj_3R_4())
		{
			jj_scanpos = xsp;
			if (jj_3R_5())
				return true;
		}
		return false;
	}

	private boolean jj_3R_12()
	{
		return jj_scan_token(26);
	}

	private boolean jj_3R_11()
	{
		return jj_scan_token(25);
	}

	private boolean jj_3_1()
	{
		if (jj_scan_token(23))
			return true;
		Token xsp = jj_scanpos;
		if (jj_scan_token(15))
		{
			jj_scanpos = xsp;
			if (jj_scan_token(16))
				return true;
		}
		return false;
	}

	private boolean jj_3R_8()
	{
		Token xsp = jj_scanpos;
		if (jj_3R_12())
		{
			jj_scanpos = xsp;
			if (jj_scan_token(27))
				return true;
		}
		return false;
	}

	private boolean jj_3R_10()
	{
		return jj_scan_token(23);
	}

	private boolean jj_3R_7()
	{
		Token xsp = jj_scanpos;
		if (jj_3R_10())
		{
			jj_scanpos = xsp;
			if (jj_3R_11())
			{
				jj_scanpos = xsp;
				if (jj_scan_token(28))
					return true;
			}
		}
		return false;
	}

	private boolean jj_3R_9()
	{
		return jj_scan_token(22);
	}

	private boolean jj_3R_5()
	{
		Token xsp = jj_scanpos;
		if (jj_scan_token(17))
		{
			jj_scanpos = xsp;
			if (jj_scan_token(18))
			{
				jj_scanpos = xsp;
				if (jj_scan_token(19))
				{
					jj_scanpos = xsp;
					if (jj_scan_token(20))
						return true;
				}
			}
		}
		xsp = jj_scanpos;
		if (jj_scan_token(23))
		{
			jj_scanpos = xsp;
			if (jj_scan_token(22))
			{
				jj_scanpos = xsp;
				if (jj_scan_token(28))
					return true;
			}
		}
		return false;
	}

	private boolean jj_3R_4()
	{
		Token xsp = jj_scanpos;
		if (jj_scan_token(15))
		{
			jj_scanpos = xsp;
			if (jj_scan_token(16))
				return true;
		}
		return jj_3R_6();
	}

	private boolean jj_3R_6()
	{
		Token xsp = jj_scanpos;
		if (jj_3R_7())
		{
			jj_scanpos = xsp;
			if (jj_3R_8())
			{
				jj_scanpos = xsp;
				if (jj_3R_9())
					return true;
			}
		}
		return false;
	}

	private static void jj_la1_init_0()
	{
		jj_la1_0 = (new int[] {
			768, 768, 7168, 7168, 0x1ec03c00, 512, 256, 0x18000, 0x1e0000, 0x10c00000, 
			0x1f8000, 0x18000, 0x200000, 0x1ec02000, 0x1ec02000, 0x12800000, 0x1000000, 0x1000000, 0x200000, 0xc000000, 
			0, 0x20000000, 0, 0xc0000000, 0x200000, 0x1000000, 0x200000, 0x1ec00000
		});
	}

	private static void jj_la1_init_1()
	{
		jj_la1_1 = (new int[] {
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			3, 0, 3, 0, 0, 0, 0, 0
		});
	}

	public StandardSyntaxParser(CharStream stream)
	{
		jj_la1 = new int[28];
		jj_2_rtns = new JJCalls[2];
		jj_rescan = false;
		jj_gc = 0;
		jj_ls = new LookaheadSuccess();
		jj_expentries = new ArrayList();
		jj_kind = -1;
		jj_lasttokens = new int[100];
		token_source = new StandardSyntaxParserTokenManager(stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 28; i++)
			jj_la1[i] = -1;

		for (int i = 0; i < jj_2_rtns.length; i++)
			jj_2_rtns[i] = new JJCalls();

	}

	public void ReInit(CharStream stream)
	{
		token_source.ReInit(stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 28; i++)
			jj_la1[i] = -1;

		for (int i = 0; i < jj_2_rtns.length; i++)
			jj_2_rtns[i] = new JJCalls();

	}

	public StandardSyntaxParser(StandardSyntaxParserTokenManager tm)
	{
		jj_la1 = new int[28];
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
		for (int i = 0; i < 28; i++)
			jj_la1[i] = -1;

		for (int i = 0; i < jj_2_rtns.length; i++)
			jj_2_rtns[i] = new JJCalls();

	}

	public void ReInit(StandardSyntaxParserTokenManager tm)
	{
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 28; i++)
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
		boolean la1tokens[] = new boolean[34];
		if (jj_kind >= 0)
		{
			la1tokens[jj_kind] = true;
			jj_kind = -1;
		}
		for (int i = 0; i < 28; i++)
		{
			if (jj_la1[i] != jj_gen)
				continue;
			for (int j = 0; j < 32; j++)
			{
				if ((jj_la1_0[i] & 1 << j) != 0)
					la1tokens[j] = true;
				if ((jj_la1_1[i] & 1 << j) != 0)
					la1tokens[32 + j] = true;
			}

		}

		for (int i = 0; i < 34; i++)
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
		jj_la1_init_1();
	}
}
