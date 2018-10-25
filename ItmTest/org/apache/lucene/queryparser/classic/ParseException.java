// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ParseException.java

package org.apache.lucene.queryparser.classic;


// Referenced classes of package org.apache.lucene.queryparser.classic:
//			Token

public class ParseException extends Exception
{

	private static final long serialVersionUID = 1L;
	public Token currentToken;
	public int expectedTokenSequences[][];
	public String tokenImage[];
	protected String eol;

	public ParseException(Token currentTokenVal, int expectedTokenSequencesVal[][], String tokenImageVal[])
	{
		super(initialise(currentTokenVal, expectedTokenSequencesVal, tokenImageVal));
		eol = System.getProperty("line.separator", "\n");
		currentToken = currentTokenVal;
		expectedTokenSequences = expectedTokenSequencesVal;
		tokenImage = tokenImageVal;
	}

	public ParseException()
	{
		eol = System.getProperty("line.separator", "\n");
	}

	public ParseException(String message)
	{
		super(message);
		eol = System.getProperty("line.separator", "\n");
	}

	private static String initialise(Token currentToken, int expectedTokenSequences[][], String tokenImage[])
	{
		String eol = System.getProperty("line.separator", "\n");
		StringBuffer expected = new StringBuffer();
		int maxSize = 0;
		for (int i = 0; i < expectedTokenSequences.length; i++)
		{
			if (maxSize < expectedTokenSequences[i].length)
				maxSize = expectedTokenSequences[i].length;
			for (int j = 0; j < expectedTokenSequences[i].length; j++)
				expected.append(tokenImage[expectedTokenSequences[i][j]]).append(' ');

			if (expectedTokenSequences[i][expectedTokenSequences[i].length - 1] != 0)
				expected.append("...");
			expected.append(eol).append("    ");
		}

		String retval = "Encountered \"";
		Token tok = currentToken.next;
		int i = 0;
		do
		{
			if (i >= maxSize)
				break;
			if (i != 0)
				retval = (new StringBuilder()).append(retval).append(" ").toString();
			if (tok.kind == 0)
			{
				retval = (new StringBuilder()).append(retval).append(tokenImage[0]).toString();
				break;
			}
			retval = (new StringBuilder()).append(retval).append(" ").append(tokenImage[tok.kind]).toString();
			retval = (new StringBuilder()).append(retval).append(" \"").toString();
			retval = (new StringBuilder()).append(retval).append(add_escapes(tok.image)).toString();
			retval = (new StringBuilder()).append(retval).append(" \"").toString();
			tok = tok.next;
			i++;
		} while (true);
		retval = (new StringBuilder()).append(retval).append("\" at line ").append(currentToken.next.beginLine).append(", column ").append(currentToken.next.beginColumn).toString();
		retval = (new StringBuilder()).append(retval).append(".").append(eol).toString();
		if (expectedTokenSequences.length == 1)
			retval = (new StringBuilder()).append(retval).append("Was expecting:").append(eol).append("    ").toString();
		else
			retval = (new StringBuilder()).append(retval).append("Was expecting one of:").append(eol).append("    ").toString();
		retval = (new StringBuilder()).append(retval).append(expected.toString()).toString();
		return retval;
	}

	static String add_escapes(String str)
	{
		StringBuffer retval = new StringBuffer();
		for (int i = 0; i < str.length(); i++)
		{
			char ch;
			switch (str.charAt(i))
			{
			case 0: // '\0'
				break;

			case 8: // '\b'
				retval.append("\\b");
				break;

			case 9: // '\t'
				retval.append("\\t");
				break;

			case 10: // '\n'
				retval.append("\\n");
				break;

			case 12: // '\f'
				retval.append("\\f");
				break;

			case 13: // '\r'
				retval.append("\\r");
				break;

			case 34: // '"'
				retval.append("\\\"");
				break;

			case 39: // '\''
				retval.append("\\'");
				break;

			case 92: // '\\'
				retval.append("\\\\");
				break;

			default:
				if ((ch = str.charAt(i)) < ' ' || ch > '~')
				{
					String s = (new StringBuilder()).append("0000").append(Integer.toString(ch, 16)).toString();
					retval.append((new StringBuilder()).append("\\u").append(s.substring(s.length() - 4, s.length())).toString());
				} else
				{
					retval.append(ch);
				}
				break;
			}
		}

		return retval.toString();
	}
}
