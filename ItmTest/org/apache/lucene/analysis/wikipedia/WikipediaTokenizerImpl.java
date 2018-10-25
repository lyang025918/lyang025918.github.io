// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WikipediaTokenizerImpl.java

package org.apache.lucene.analysis.wikipedia;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

// Referenced classes of package org.apache.lucene.analysis.wikipedia:
//			WikipediaTokenizer

class WikipediaTokenizerImpl
{

	public static final int YYEOF = -1;
	private static final int ZZ_BUFFERSIZE = 4096;
	public static final int YYINITIAL = 0;
	public static final int CATEGORY_STATE = 2;
	public static final int INTERNAL_LINK_STATE = 4;
	public static final int EXTERNAL_LINK_STATE = 6;
	public static final int TWO_SINGLE_QUOTES_STATE = 8;
	public static final int THREE_SINGLE_QUOTES_STATE = 10;
	public static final int FIVE_SINGLE_QUOTES_STATE = 12;
	public static final int DOUBLE_EQUALS_STATE = 14;
	public static final int DOUBLE_BRACE_STATE = 16;
	public static final int STRING = 18;
	private static final int ZZ_LEXSTATE[] = {
		0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 
		5, 5, 6, 6, 7, 7, 8, 8, 9, 9
	};
	private static final String ZZ_CMAP_PACKED = "\t\000\001\024\001\023\001\000\001\024\001\022\022\000\001\024\001\000\001\n\001+\002\000\001\003\001\001\004\000\001\f\001\005\001\002\001\b\n\016\001\027\001\000\001\007\001\t\001\013\001+\001\004\002\r\001\030\005\r\001!\021\r\001\025\001\000\001\026\001\000\001\006\001\000\001\031\001#\002\r\001\033\001 \001\034\001(\001!\004\r\001\"\001\035\001)\001\r\001\036\001*\001\032\003\r\001$\001\037\001\r\001%\001'\001&B\000\027\r\001\000\037\r\001\000?\r\n\017\206\r\n\017?\r\n\017v\r\n\017v\r\n\017v\r\n\017v\r\n\017w\r\t\017v\r\n\017v\r\n\017v\r\n\017\340\r\n\017v\r\n\017?\r\n\017\266\r¨¡\r?\r?\000?\021`\000\020\021¨¡\000\200\021\200\000?\021@\000µ¶\021?\000?\020?\000?\021?\000;\021=\r#\0";
	private static final char ZZ_CMAP[] = zzUnpackCMap("\t\000\001\024\001\023\001\000\001\024\001\022\022\000\001\024\001\000\001\n\001+\002\000\001\003\001\001\004\000\001\f\001\005\001\002\001\b\n\016\001\027\001\000\001\007\001\t\001\013\001+\001\004\002\r\001\030\005\r\001!\021\r\001\025\001\000\001\026\001\000\001\006\001\000\001\031\001#\002\r\001\033\001 \001\034\001(\001!\004\r\001\"\001\035\001)\001\r\001\036\001*\001\032\003\r\001$\001\037\001\r\001%\001'\001&B\000\027\r\001\000\037\r\001\000?\r\n\017\206\r\n\017?\r\n\017v\r\n\017v\r\n\017v\r\n\017v\r\n\017w\r\t\017v\r\n\017v\r\n\017v\r\n\017\340\r\n\017v\r\n\017?\r\n\017\266\r¨¡\r?\r?\000?\021`\000\020\021¨¡\000\200\021\200\000?\021@\000µ¶\021?\000?\020?\000?\021?\000;\021=\r#\0");
	private static final int ZZ_ACTION[] = zzUnpackAction();
	private static final String ZZ_ACTION_PACKED_0 = "\n\000\004\001\004\002\001\003\001\001\001\004\001\001\002\005\001\006\002\005\001\007\001\005\002\b\001\t\001\n\001\t\001\013\001\f\001\b\001\r\001\016\001\r\001\017\001\020\001\b\001\021\001\b\004\022\001\023\001\022\001\024\001\025\001\026\003\000\001\027\f\000\001\030\001\031\001\032\001\033\001\t\001\000\001\034\001\035\001\036\001\000\001\037\001\000\001 \003\000\001!\001\"\002#\001\"\002$\002\000\001#\001\000\f#\001\"\003\000\001\t\001%\003\000\001&\001'\005\000\001(\004\000\001(\002\000\002(\002\000\001\t\005\000\001\031\001\"\001#\001)\003\000\001\t\002\000\001*\030\000\001+\002\000\001,\001-\001.";
	private static final int ZZ_ROWMAP[] = zzUnpackRowMap();
	private static final String ZZ_ROWMAP_PACKED_0 = "\000\000\000,\000X\000\204\000\260\000\334\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000¦¬\000¦×\000?\000§²\000§î\000?\000?\000¦¬\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000¦¬\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?";
	private static final int ZZ_TRANS[] = zzUnpackTrans();
	private static final String ZZ_TRANS_PACKED_0 = "\001\013\001\f\005\013\001\r\001\013\001\016\003\013\001\017\001\020\001\021\001\022\001\023\001\024\002\013\001\025\002\013\r\017\001\026\002\013\003\017\001\013\007\027\001\030\005\027\004\031\001\027\001\032\003\027\001\033\001\027\r\031\003\027\003\031\b\027\001\030\005\027\004\034\001\027\001\032\003\027\001\035\001\027\r\034\003\027\003\034\001\027\007\036\001\037\005\036\004 \001\036\001\032\002\027\001\036\001!\001\036\r \003\036\001\"\002 \002\036\001#\005\036\001\037\005\036\004$\001\036\001%\002\036\001&\002\036\r$\003\036\003$\b\036\001\037\005\036\004'\001\036\001%\002\036\001&\002\036\r'\003\036\003'\b\036\001\037\005\036\004'\001\036\001%\002\036\001(\002\036\r'\003\036\003'\b\036\001\037\001\036\001)\003\036\004*\001\036\001%\005\036\r*\003\036\003*\b\036\001+\005\036\004,\001\036\001%\005\036\r,\001\036\001-\001\036\003,\001\036\001.\001/\005.\0010\001.\0011\003.\0042\001.\0013\002.\0014\002.\r2\002.\0015\0032\001.-\000\00162\000\0017\004\000\0048\007\000\0068\0019\0068\003\000\0038\n\000\001:#\000\001;\001<\001=\001>\002?\001\000\001@\003\000\001@\001\017\001\020\001\021\001\022\007\000\r\017\003\000\003\017\003\000\001A\001\000\001B\002C\001\000\001D\003\000\001D\003\020\001\022\007\000\r\020\003\000\003\020\002\000\001;\001E\001=\001>\002C\001\000\001D\003\000\001D\001\021\001\020\001\021\001\022\007\000\r\021\003\000\003\021\003\000\001F\001\000\001B\002?\001\000\001@\003\000\001@\004\022\007\000\r\022\003\000\003\022\024\000\001\013-\000\001G;\000\001H\016\000\0017\004\000\0048\007\000\r8\003\000\0038\016\000\004\031\007\000\r\031\003\000\003\031\024\000\001\027.\000\001I\"\000\004\034\007\000\r\034\003\000\003\034\027\000\001J\"\000\004 \007\000\r \003\000\003 \016\000\004 \007\000\002 \001K\n \003\000\003 \002\000\001L7\000\004$\007\000\r$\003\000\003$\024\000\001\036-\000\001M#\000\004'\007\000\r'\003\000\003'\026\000\001N\037\000\001O/\000\004*\007\000\r*\003\000\003*\t\000\001P\004\000\0048\007\000\r8\003\000\0038\016\000\004,\007\000\r,\003\000\003,'\000\001O\006\000\001Q3\000\001R/\000\0042\007\000\r2\003\000\0032\024\000\001.-\000\001S#\000\0048\007\000\r8\003\000\0038\f\000\001\036\001\000\004T\001\000\003U\003\000\rT\003\000\003T\f\000\001\036\001\000\004T\001\000\003U\003\000\003T\001V\tT\003\000\003T\016\000\001W\001\000\001W\b\000\rW\003\000\003W\016\000\001X\001Y\001Z\001[\007\000\rX\003\000\003X\016\000\001\\\001\000\001\\\b\000\r\\\003\000\003\\\016\000\001]\001^\001]\001^\007\000\r]\003\000\003]\016\000\001_\002`\001a\007\000\r_\003\000\003_\016\000\001@\002b\b\000\r@\003\000\003@\016\000\001c\002d\001e\007\000\rc\003\000\003c\016\000\004^\007\000\r^\003\000\003^\016\000\001f\002g\001h\007\000\rf\003\000\003f\016\000\001i\002j\001k\007\000\ri\003\000\003i\016\000\001l\001d\001m\001e\007\000\rl\003\000\003l\016\000\001n\002Y\001[\007\000\rn\003\000\003n\030\000\001o\001p4\000\001q\027\000\004 \007\000\002 \001r\n \003\000\003 \002\000\001sA\000\001t\001u \000\0048\007\000\0068\001v\0068\003\000\0038\002\000\001w3\000\001x9\000\001y\001z\034\000\001{\001\000\001\036\001\000\004T\001\000\003U\003\000\rT\003\000\003T\016\000\004|\001\000\003U\003\000\r|\003\000\003|\n\000\001{\001\000\001\036\001\000\004T\001\000\003U\003\000\bT\001}\004T\003\000\003T\002\000\001;\013\000\001W\001\000\001W\b\000\rW\003\000\003W\003\000\001~\001\000\001B\002\177\006\000\001X\001Y\001Z\001[\007\000\rX\003\000\003X\003\000\001\200\001\000\001B\002\201\001\000\001\202\003\000\001\202\003Y\001[\007\000\rY\003\000\003Y\003\000\001\203\001\000\001B\002\201\001\000\001\202\003\000\001\202\001Z\001Y\001Z\001[\007\000\rZ\003\000\003Z\003\000\001\204\001\000\001B\002\177\006\000\004[\007\000\r[\003\000\003[\003\000\001\205\002\000\001\205\007\000\001]\001^\001]\001^\007\000\r]\003\000\003]\003\000\001\205\002\000\001\205\007\000\004^\007\000\r^\003\000\003^\003\000\001\177\001\000\001B\002\177\006\000\001_\002`\001a\007\000\r_\003\000\003_\003\000\001\201\001\000\001B\002\201\001\000\001\202\003\000\001\202\003`\001a\007\000\r`\003\000\003`\003\000\001\177\001\000\001B\002\177\006\000\004a\007\000\ra\003\000\003a\003\000\001\202\002\000\002\202\001\000\001\202\003\000\001\202\003b\b\000\rb\003\000\003b\003\000\001F\001\000\001B\002?\001\000\001@\003\000\001@\001c\002d\001e\007\000\rc\003\000\003c\003\000\001A\001\000\001B\002C\001\000\001D\003\000\001D\003d\001e\007\000\rd\003\000\003d\003\000\001F\001\000\001B\002?\001\000\001@\003\000\001@\004e\007\000\re\003\000\003e\003\000\001?\001\000\001B\002?\001\000\001@\003\000\001@\001f\002g\001h\007\000\rf\003\000\003f\003\000\001C\001\000\001B\002C\001\000\001D\003\000\001D\003g\001h\007\000\rg\003\000\003g\003\000\001?\001\000\001B\002?\001\000\001@\003\000\001@\004h\007\000\rh\003\000\003h\003\000\001@\002\000\002@\001\000\001@\003\000\001@\001i\002j\001k\007\000\ri\003\000\003i\003\000\001D\002\000\002D\001\000\001D\003\000\001D\003j\001k\007\000\rj\003\000\003j\003\000\001@\002\000\002@\001\000\001@\003\000\001@\004k\007\000\rk\003\000\003k\003\000\001\206\001\000\001B\002?\001\000\001@\003\000\001@\001l\001d\001m\001e\007\000\rl\003\000\003l\003\000\001\207\001\000\001B\002C\001\000\001D\003\000\001D\001m\001d\001m\001e\007\000\rm\003\000\003m\003\000\001\204\001\000\001B\002\177\006\000\001n\002Y\001[\007\000\rn\003\000\003n\031\000\001p,\000\001\2104\000\001\211\026\000\004 \007\000\r \003\000\001 \001\212\001 \031\000\001u,\000\001\213\035\000\001\036\001\000\004T\001\000\003U\003\000\003T\001\214\tT\003\000\003T\002\000\001\215B\000\001z,\000\001\216\034\000\001\217*\000\001{\003\000\004|\007\000\r|\003\000\003|\n\000\001{\001\000\001\220\001\000\004T\001\000\003U\003\000\rT\003\000\003T\016\000\001\221\001[\001\221\001[\007\000\r\221\003\000\003\221\016\000\004a\007\000\ra\003\000\003a\016\000\004e\007\000\re\003\000\003e\016\000\004h\007\000\rh\003\000\003h\016\000\004k\007\000\rk\003\000\003k\016\000\001\222\001e\001\222\001e\007\000\r\222\003\000\003\222\016\000\004[\007\000\r[\003\000\003[\016\000\004\223\007\000\r\223\003\000\003\223\033\000\001\2241\000\001\225\030\000\004 \006\000\001\226\r \003\000\002 \001\227\033\000\001\230\032\000\001{\001\000\001\036\001\000\004T\001\000\003U\003\000\bT\001\231\004T\003\000\003T\002\000\001\232D\000\001\233\036\000\004\234\007\000\r\234\003\000\003\234\003\000\001~\001\000\001B\002\177\006\000\001\221\001[\001\221\001[\007\000\r\221\003\000\003\221\003\000\001\206\001\000\001B\002?\001\000\001@\003\000\001@\001\222\001e\001\222\001e\007\000\r\222\003\000\003\222\003\000\001\205\002\000\001\205\007\000\004\223\007\000\r\223\003\000\003\223\034\000\001\235-\000\001\236\026\000\001\2370\000\004 \006\000\001\226\r \003\000\003 \034\000\001\240\031\000\001{\001\000\001O\001\000\004T\001\000\003U\003\000\rT\003\000\003T\034\000\001\241\032\000\001\242\002\000\004\234\007\000\r\234\003\000\003\234\035\000\001\2432\000\001\244\020\000\001\245?\000\001\246+\000\001\247\032\000\001\036\001\000\004|\001\000\003U\003\000\r|\003\000\003|\036\000\001\250+\000\001\251\033\000\004\252\007\000\r\252\003\000\003\252\036\000\001\253+\000\001\254,\000\001\2551\000\001\256\t\000\001\257\n\000\004\252\007\000\r\252\003\000\003\252\037\000\001\260+\000\001\261,\000\001\262\022\000\001\0132\000\004\263\007\000\r\263\003\000\003\263 \000\001\264+\000\001\265#\000\001\266\026\000\002\263\001\000\002\263\001\000\002\263\002\000\005\263\007\000\r\263\003\000\004\263\027\000\001\267+\000\001\270\024\0";
	private static final int ZZ_UNKNOWN_ERROR = 0;
	private static final int ZZ_NO_MATCH = 1;
	private static final int ZZ_PUSHBACK_2BIG = 2;
	private static final String ZZ_ERROR_MSG[] = {
		"Unkown internal scanner error", "Error: could not match input", "Error: pushback value was too large"
	};
	private static final int ZZ_ATTRIBUTE[] = zzUnpackAttribute();
	private static final String ZZ_ATTRIBUTE_PACKED_0 = "\n\000\001\t\007\001\001\t\003\001\001\t\006\001\001\t\002\001\001\t\f\001\001\t\006\001\002\t\003\000\001\t\f\000\002\001\002\t\001\001\001\000\002\001\001\t\001\000\001\001\001\000\001\001\003\000\007\001\002\000\001\001\001\000\r\001\003\000\001\001\001\t\003\000\001\001\001\t\005\000\001\001\004\000\001\001\002\000\002\001\002\000\001\001\005\000\001\t\003\001\003\000\001\001\002\000\001\t\030\000\001\001\002\000\003\t";
	private Reader zzReader;
	private int zzState;
	private int zzLexicalState;
	private char zzBuffer[];
	private int zzMarkedPos;
	private int zzCurrentPos;
	private int zzStartRead;
	private int zzEndRead;
	private int yyline;
	private int yychar;
	private int yycolumn;
	private boolean zzAtBOL;
	private boolean zzAtEOF;
	private boolean zzEOFDone;
	public static final int ALPHANUM = 0;
	public static final int APOSTROPHE = 1;
	public static final int ACRONYM = 2;
	public static final int COMPANY = 3;
	public static final int EMAIL = 4;
	public static final int HOST = 5;
	public static final int NUM = 6;
	public static final int CJ = 7;
	public static final int INTERNAL_LINK = 8;
	public static final int EXTERNAL_LINK = 9;
	public static final int CITATION = 10;
	public static final int CATEGORY = 11;
	public static final int BOLD = 12;
	public static final int ITALICS = 13;
	public static final int BOLD_ITALICS = 14;
	public static final int HEADING = 15;
	public static final int SUB_HEADING = 16;
	public static final int EXTERNAL_LINK_URL = 17;
	private int currentTokType;
	private int numBalanced;
	private int positionInc;
	private int numLinkToks;
	private int numWikiTokensSeen;
	public static final String TOKEN_TYPES[];

	private static int[] zzUnpackAction()
	{
		int result[] = new int[184];
		int offset = 0;
		offset = zzUnpackAction("\n\000\004\001\004\002\001\003\001\001\001\004\001\001\002\005\001\006\002\005\001\007\001\005\002\b\001\t\001\n\001\t\001\013\001\f\001\b\001\r\001\016\001\r\001\017\001\020\001\b\001\021\001\b\004\022\001\023\001\022\001\024\001\025\001\026\003\000\001\027\f\000\001\030\001\031\001\032\001\033\001\t\001\000\001\034\001\035\001\036\001\000\001\037\001\000\001 \003\000\001!\001\"\002#\001\"\002$\002\000\001#\001\000\f#\001\"\003\000\001\t\001%\003\000\001&\001'\005\000\001(\004\000\001(\002\000\002(\002\000\001\t\005\000\001\031\001\"\001#\001)\003\000\001\t\002\000\001*\030\000\001+\002\000\001,\001-\001.", offset, result);
		return result;
	}

	private static int zzUnpackAction(String packed, int offset, int result[])
	{
		int i = 0;
		int j = offset;
		for (int l = packed.length(); i < l;)
		{
			int count = packed.charAt(i++);
			int value = packed.charAt(i++);
			do
				result[j++] = value;
			while (--count > 0);
		}

		return j;
	}

	private static int[] zzUnpackRowMap()
	{
		int result[] = new int[184];
		int offset = 0;
		offset = zzUnpackRowMap("\000\000\000,\000X\000\204\000\260\000\334\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000¦¬\000¦×\000?\000§²\000§î\000?\000?\000¦¬\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000¦¬\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?", offset, result);
		return result;
	}

	private static int zzUnpackRowMap(String packed, int offset, int result[])
	{
		int i = 0;
		int j = offset;
		for (int l = packed.length(); i < l;)
		{
			int high = packed.charAt(i++) << 16;
			result[j++] = high | packed.charAt(i++);
		}

		return j;
	}

	private static int[] zzUnpackTrans()
	{
		int result[] = new int[7040];
		int offset = 0;
		offset = zzUnpackTrans("\001\013\001\f\005\013\001\r\001\013\001\016\003\013\001\017\001\020\001\021\001\022\001\023\001\024\002\013\001\025\002\013\r\017\001\026\002\013\003\017\001\013\007\027\001\030\005\027\004\031\001\027\001\032\003\027\001\033\001\027\r\031\003\027\003\031\b\027\001\030\005\027\004\034\001\027\001\032\003\027\001\035\001\027\r\034\003\027\003\034\001\027\007\036\001\037\005\036\004 \001\036\001\032\002\027\001\036\001!\001\036\r \003\036\001\"\002 \002\036\001#\005\036\001\037\005\036\004$\001\036\001%\002\036\001&\002\036\r$\003\036\003$\b\036\001\037\005\036\004'\001\036\001%\002\036\001&\002\036\r'\003\036\003'\b\036\001\037\005\036\004'\001\036\001%\002\036\001(\002\036\r'\003\036\003'\b\036\001\037\001\036\001)\003\036\004*\001\036\001%\005\036\r*\003\036\003*\b\036\001+\005\036\004,\001\036\001%\005\036\r,\001\036\001-\001\036\003,\001\036\001.\001/\005.\0010\001.\0011\003.\0042\001.\0013\002.\0014\002.\r2\002.\0015\0032\001.-\000\00162\000\0017\004\000\0048\007\000\0068\0019\0068\003\000\0038\n\000\001:#\000\001;\001<\001=\001>\002?\001\000\001@\003\000\001@\001\017\001\020\001\021\001\022\007\000\r\017\003\000\003\017\003\000\001A\001\000\001B\002C\001\000\001D\003\000\001D\003\020\001\022\007\000\r\020\003\000\003\020\002\000\001;\001E\001=\001>\002C\001\000\001D\003\000\001D\001\021\001\020\001\021\001\022\007\000\r\021\003\000\003\021\003\000\001F\001\000\001B\002?\001\000\001@\003\000\001@\004\022\007\000\r\022\003\000\003\022\024\000\001\013-\000\001G;\000\001H\016\000\0017\004\000\0048\007\000\r8\003\000\0038\016\000\004\031\007\000\r\031\003\000\003\031\024\000\001\027.\000\001I\"\000\004\034\007\000\r\034\003\000\003\034\027\000\001J\"\000\004 \007\000\r \003\000\003 \016\000\004 \007\000\002 \001K\n \003\000\003 \002\000\001L7\000\004$\007\000\r$\003\000\003$\024\000\001\036-\000\001M#\000\004'\007\000\r'\003\000\003'\026\000\001N\037\000\001O/\000\004*\007\000\r*\003\000\003*\t\000\001P\004\000\0048\007\000\r8\003\000\0038\016\000\004,\007\000\r,\003\000\003,'\000\001O\006\000\001Q3\000\001R/\000\0042\007\000\r2\003\000\0032\024\000\001.-\000\001S#\000\0048\007\000\r8\003\000\0038\f\000\001\036\001\000\004T\001\000\003U\003\000\rT\003\000\003T\f\000\001\036\001\000\004T\001\000\003U\003\000\003T\001V\tT\003\000\003T\016\000\001W\001\000\001W\b\000\rW\003\000\003W\016\000\001X\001Y\001Z\001[\007\000\rX\003\000\003X\016\000\001\\\001\000\001\\\b\000\r\\\003\000\003\\\016\000\001]\001^\001]\001^\007\000\r]\003\000\003]\016\000\001_\002`\001a\007\000\r_\003\000\003_\016\000\001@\002b\b\000\r@\003\000\003@\016\000\001c\002d\001e\007\000\rc\003\000\003c\016\000\004^\007\000\r^\003\000\003^\016\000\001f\002g\001h\007\000\rf\003\000\003f\016\000\001i\002j\001k\007\000\ri\003\000\003i\016\000\001l\001d\001m\001e\007\000\rl\003\000\003l\016\000\001n\002Y\001[\007\000\rn\003\000\003n\030\000\001o\001p4\000\001q\027\000\004 \007\000\002 \001r\n \003\000\003 \002\000\001sA\000\001t\001u \000\0048\007\000\0068\001v\0068\003\000\0038\002\000\001w3\000\001x9\000\001y\001z\034\000\001{\001\000\001\036\001\000\004T\001\000\003U\003\000\rT\003\000\003T\016\000\004|\001\000\003U\003\000\r|\003\000\003|\n\000\001{\001\000\001\036\001\000\004T\001\000\003U\003\000\bT\001}\004T\003\000\003T\002\000\001;\013\000\001W\001\000\001W\b\000\rW\003\000\003W\003\000\001~\001\000\001B\002\177\006\000\001X\001Y\001Z\001[\007\000\rX\003\000\003X\003\000\001\200\001\000\001B\002\201\001\000\001\202\003\000\001\202\003Y\001[\007\000\rY\003\000\003Y\003\000\001\203\001\000\001B\002\201\001\000\001\202\003\000\001\202\001Z\001Y\001Z\001[\007\000\rZ\003\000\003Z\003\000\001\204\001\000\001B\002\177\006\000\004[\007\000\r[\003\000\003[\003\000\001\205\002\000\001\205\007\000\001]\001^\001]\001^\007\000\r]\003\000\003]\003\000\001\205\002\000\001\205\007\000\004^\007\000\r^\003\000\003^\003\000\001\177\001\000\001B\002\177\006\000\001_\002`\001a\007\000\r_\003\000\003_\003\000\001\201\001\000\001B\002\201\001\000\001\202\003\000\001\202\003`\001a\007\000\r`\003\000\003`\003\000\001\177\001\000\001B\002\177\006\000\004a\007\000\ra\003\000\003a\003\000\001\202\002\000\002\202\001\000\001\202\003\000\001\202\003b\b\000\rb\003\000\003b\003\000\001F\001\000\001B\002?\001\000\001@\003\000\001@\001c\002d\001e\007\000\rc\003\000\003c\003\000\001A\001\000\001B\002C\001\000\001D\003\000\001D\003d\001e\007\000\rd\003\000\003d\003\000\001F\001\000\001B\002?\001\000\001@\003\000\001@\004e\007\000\re\003\000\003e\003\000\001?\001\000\001B\002?\001\000\001@\003\000\001@\001f\002g\001h\007\000\rf\003\000\003f\003\000\001C\001\000\001B\002C\001\000\001D\003\000\001D\003g\001h\007\000\rg\003\000\003g\003\000\001?\001\000\001B\002?\001\000\001@\003\000\001@\004h\007\000\rh\003\000\003h\003\000\001@\002\000\002@\001\000\001@\003\000\001@\001i\002j\001k\007\000\ri\003\000\003i\003\000\001D\002\000\002D\001\000\001D\003\000\001D\003j\001k\007\000\rj\003\000\003j\003\000\001@\002\000\002@\001\000\001@\003\000\001@\004k\007\000\rk\003\000\003k\003\000\001\206\001\000\001B\002?\001\000\001@\003\000\001@\001l\001d\001m\001e\007\000\rl\003\000\003l\003\000\001\207\001\000\001B\002C\001\000\001D\003\000\001D\001m\001d\001m\001e\007\000\rm\003\000\003m\003\000\001\204\001\000\001B\002\177\006\000\001n\002Y\001[\007\000\rn\003\000\003n\031\000\001p,\000\001\2104\000\001\211\026\000\004 \007\000\r \003\000\001 \001\212\001 \031\000\001u,\000\001\213\035\000\001\036\001\000\004T\001\000\003U\003\000\003T\001\214\tT\003\000\003T\002\000\001\215B\000\001z,\000\001\216\034\000\001\217*\000\001{\003\000\004|\007\000\r|\003\000\003|\n\000\001{\001\000\001\220\001\000\004T\001\000\003U\003\000\rT\003\000\003T\016\000\001\221\001[\001\221\001[\007\000\r\221\003\000\003\221\016\000\004a\007\000\ra\003\000\003a\016\000\004e\007\000\re\003\000\003e\016\000\004h\007\000\rh\003\000\003h\016\000\004k\007\000\rk\003\000\003k\016\000\001\222\001e\001\222\001e\007\000\r\222\003\000\003\222\016\000\004[\007\000\r[\003\000\003[\016\000\004\223\007\000\r\223\003\000\003\223\033\000\001\2241\000\001\225\030\000\004 \006\000\001\226\r \003\000\002 \001\227\033\000\001\230\032\000\001{\001\000\001\036\001\000\004T\001\000\003U\003\000\bT\001\231\004T\003\000\003T\002\000\001\232D\000\001\233\036\000\004\234\007\000\r\234\003\000\003\234\003\000\001~\001\000\001B\002\177\006\000\001\221\001[\001\221\001[\007\000\r\221\003\000\003\221\003\000\001\206\001\000\001B\002?\001\000\001@\003\000\001@\001\222\001e\001\222\001e\007\000\r\222\003\000\003\222\003\000\001\205\002\000\001\205\007\000\004\223\007\000\r\223\003\000\003\223\034\000\001\235-\000\001\236\026\000\001\2370\000\004 \006\000\001\226\r \003\000\003 \034\000\001\240\031\000\001{\001\000\001O\001\000\004T\001\000\003U\003\000\rT\003\000\003T\034\000\001\241\032\000\001\242\002\000\004\234\007\000\r\234\003\000\003\234\035\000\001\2432\000\001\244\020\000\001\245?\000\001\246+\000\001\247\032\000\001\036\001\000\004|\001\000\003U\003\000\r|\003\000\003|\036\000\001\250+\000\001\251\033\000\004\252\007\000\r\252\003\000\003\252\036\000\001\253+\000\001\254,\000\001\2551\000\001\256\t\000\001\257\n\000\004\252\007\000\r\252\003\000\003\252\037\000\001\260+\000\001\261,\000\001\262\022\000\001\0132\000\004\263\007\000\r\263\003\000\003\263 \000\001\264+\000\001\265#\000\001\266\026\000\002\263\001\000\002\263\001\000\002\263\002\000\005\263\007\000\r\263\003\000\004\263\027\000\001\267+\000\001\270\024\0", offset, result);
		return result;
	}

	private static int zzUnpackTrans(String packed, int offset, int result[])
	{
		int i = 0;
		int j = offset;
		for (int l = packed.length(); i < l;)
		{
			int count = packed.charAt(i++);
			int value = packed.charAt(i++);
			value--;
			do
				result[j++] = value;
			while (--count > 0);
		}

		return j;
	}

	private static int[] zzUnpackAttribute()
	{
		int result[] = new int[184];
		int offset = 0;
		offset = zzUnpackAttribute("\n\000\001\t\007\001\001\t\003\001\001\t\006\001\001\t\002\001\001\t\f\001\001\t\006\001\002\t\003\000\001\t\f\000\002\001\002\t\001\001\001\000\002\001\001\t\001\000\001\001\001\000\001\001\003\000\007\001\002\000\001\001\001\000\r\001\003\000\001\001\001\t\003\000\001\001\001\t\005\000\001\001\004\000\001\001\002\000\002\001\002\000\001\001\005\000\001\t\003\001\003\000\001\001\002\000\001\t\030\000\001\001\002\000\003\t", offset, result);
		return result;
	}

	private static int zzUnpackAttribute(String packed, int offset, int result[])
	{
		int i = 0;
		int j = offset;
		for (int l = packed.length(); i < l;)
		{
			int count = packed.charAt(i++);
			int value = packed.charAt(i++);
			do
				result[j++] = value;
			while (--count > 0);
		}

		return j;
	}

	public final int getNumWikiTokensSeen()
	{
		return numWikiTokensSeen;
	}

	public final int yychar()
	{
		return yychar;
	}

	public final int getPositionIncrement()
	{
		return positionInc;
	}

	final void getText(CharTermAttribute t)
	{
		t.copyBuffer(zzBuffer, zzStartRead, zzMarkedPos - zzStartRead);
	}

	final int setText(StringBuilder buffer)
	{
		int length = zzMarkedPos - zzStartRead;
		buffer.append(zzBuffer, zzStartRead, length);
		return length;
	}

	final void reset()
	{
		currentTokType = 0;
		numBalanced = 0;
		positionInc = 1;
		numLinkToks = 0;
		numWikiTokensSeen = 0;
	}

	WikipediaTokenizerImpl(Reader in)
	{
		zzLexicalState = 0;
		zzBuffer = new char[4096];
		zzAtBOL = true;
		numBalanced = 0;
		positionInc = 1;
		numLinkToks = 0;
		numWikiTokensSeen = 0;
		zzReader = in;
	}

	private static char[] zzUnpackCMap(String packed)
	{
		char map[] = new char[0x10000];
		int i = 0;
		int j = 0;
		while (i < 230) 
		{
			int count = packed.charAt(i++);
			char value = packed.charAt(i++);
			do
				map[j++] = value;
			while (--count > 0);
		}
		return map;
	}

	private boolean zzRefill()
		throws IOException
	{
		if (zzStartRead > 0)
		{
			System.arraycopy(zzBuffer, zzStartRead, zzBuffer, 0, zzEndRead - zzStartRead);
			zzEndRead -= zzStartRead;
			zzCurrentPos -= zzStartRead;
			zzMarkedPos -= zzStartRead;
			zzStartRead = 0;
		}
		if (zzCurrentPos >= zzBuffer.length)
		{
			char newBuffer[] = new char[zzCurrentPos * 2];
			System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
			zzBuffer = newBuffer;
		}
		int numRead = zzReader.read(zzBuffer, zzEndRead, zzBuffer.length - zzEndRead);
		if (numRead > 0)
		{
			zzEndRead += numRead;
			return false;
		}
		if (numRead == 0)
		{
			int c = zzReader.read();
			if (c == -1)
			{
				return true;
			} else
			{
				zzBuffer[zzEndRead++] = (char)c;
				return false;
			}
		} else
		{
			return true;
		}
	}

	public final void yyclose()
		throws IOException
	{
		zzAtEOF = true;
		zzEndRead = zzStartRead;
		if (zzReader != null)
			zzReader.close();
	}

	public final void yyreset(Reader reader)
	{
		zzReader = reader;
		zzAtBOL = true;
		zzAtEOF = false;
		zzEOFDone = false;
		zzEndRead = zzStartRead = 0;
		zzCurrentPos = zzMarkedPos = 0;
		yyline = yychar = yycolumn = 0;
		zzLexicalState = 0;
		if (zzBuffer.length > 4096)
			zzBuffer = new char[4096];
	}

	public final int yystate()
	{
		return zzLexicalState;
	}

	public final void yybegin(int newState)
	{
		zzLexicalState = newState;
	}

	public final String yytext()
	{
		return new String(zzBuffer, zzStartRead, zzMarkedPos - zzStartRead);
	}

	public final char yycharat(int pos)
	{
		return zzBuffer[zzStartRead + pos];
	}

	public final int yylength()
	{
		return zzMarkedPos - zzStartRead;
	}

	private void zzScanError(int errorCode)
	{
		String message;
		try
		{
			message = ZZ_ERROR_MSG[errorCode];
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			message = ZZ_ERROR_MSG[0];
		}
		throw new Error(message);
	}

	public void yypushback(int number)
	{
		if (number > yylength())
			zzScanError(2);
		zzMarkedPos -= number;
	}

	public int getNextToken()
		throws IOException
	{
		int zzEndReadL = zzEndRead;
		char zzBufferL[] = zzBuffer;
		char zzCMapL[] = ZZ_CMAP;
		int zzTransL[] = ZZ_TRANS;
		int zzRowMapL[] = ZZ_ROWMAP;
		int zzAttrL[] = ZZ_ATTRIBUTE;
		do
		{
			int zzMarkedPosL = zzMarkedPos;
			yychar += zzMarkedPosL - zzStartRead;
			int zzAction = -1;
			int zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
			zzState = ZZ_LEXSTATE[zzLexicalState];
			int zzAttributes = zzAttrL[zzState];
			if ((zzAttributes & 1) == 1)
				zzAction = zzState;
			int zzInput;
label0:
			do
			{
				do
				{
					if (zzCurrentPosL < zzEndReadL)
					{
						zzInput = zzBufferL[zzCurrentPosL++];
					} else
					{
						if (zzAtEOF)
						{
							zzInput = -1;
							break label0;
						}
						zzCurrentPos = zzCurrentPosL;
						zzMarkedPos = zzMarkedPosL;
						boolean eof = zzRefill();
						zzCurrentPosL = zzCurrentPos;
						zzMarkedPosL = zzMarkedPos;
						zzBufferL = zzBuffer;
						zzEndReadL = zzEndRead;
						if (eof)
						{
							zzInput = -1;
							break label0;
						}
						zzInput = zzBufferL[zzCurrentPosL++];
					}
					int zzNext = zzTransL[zzRowMapL[zzState] + zzCMapL[zzInput]];
					if (zzNext == -1)
						break label0;
					zzState = zzNext;
					zzAttributes = zzAttrL[zzState];
				} while ((zzAttributes & 1) != 1);
				zzAction = zzState;
				zzMarkedPosL = zzCurrentPosL;
			} while ((zzAttributes & 8) != 8);
			zzMarkedPos = zzMarkedPosL;
			switch (zzAction >= 0 ? ZZ_ACTION[zzAction] : zzAction)
			{
			case 1: // '\001'
				numWikiTokensSeen = 0;
				positionInc = 1;
				break;

			case 2: // '\002'
				positionInc = 1;
				return 0;

			case 3: // '\003'
				positionInc = 1;
				return 7;

			case 4: // '\004'
				numWikiTokensSeen = 0;
				positionInc = 1;
				currentTokType = 17;
				yybegin(6);
				break;

			case 5: // '\005'
				positionInc = 1;
				break;

			case 6: // '\006'
				yybegin(2);
				numWikiTokensSeen++;
				return currentTokType;

			case 7: // '\007'
				yybegin(4);
				numWikiTokensSeen++;
				return currentTokType;

			case 9: // '\t'
				if (numLinkToks == 0)
					positionInc = 0;
				else
					positionInc = 1;
				numWikiTokensSeen++;
				currentTokType = 9;
				yybegin(6);
				numLinkToks++;
				return currentTokType;

			case 10: // '\n'
				numLinkToks = 0;
				positionInc = 0;
				yybegin(0);
				break;

			case 11: // '\013'
				currentTokType = 12;
				yybegin(10);
				break;

			case 12: // '\f'
				currentTokType = 13;
				numWikiTokensSeen++;
				yybegin(18);
				return currentTokType;

			case 13: // '\r'
				currentTokType = 9;
				numWikiTokensSeen = 0;
				yybegin(6);
				break;

			case 14: // '\016'
				yybegin(18);
				numWikiTokensSeen++;
				return currentTokType;

			case 15: // '\017'
				currentTokType = 16;
				numWikiTokensSeen = 0;
				yybegin(18);
				break;

			case 16: // '\020'
				currentTokType = 15;
				yybegin(14);
				numWikiTokensSeen++;
				return currentTokType;

			case 17: // '\021'
				yybegin(16);
				numWikiTokensSeen = 0;
				return currentTokType;

			case 19: // '\023'
				yybegin(18);
				numWikiTokensSeen++;
				return currentTokType;

			case 20: // '\024'
				numBalanced = 0;
				numWikiTokensSeen = 0;
				currentTokType = 9;
				yybegin(6);
				break;

			case 21: // '\025'
				yybegin(18);
				return currentTokType;

			case 22: // '\026'
				numWikiTokensSeen = 0;
				positionInc = 1;
				if (numBalanced == 0)
				{
					numBalanced++;
					yybegin(8);
				} else
				{
					numBalanced = 0;
				}
				break;

			case 23: // '\027'
				numWikiTokensSeen = 0;
				positionInc = 1;
				yybegin(14);
				break;

			case 24: // '\030'
				numWikiTokensSeen = 0;
				positionInc = 1;
				currentTokType = 8;
				yybegin(4);
				break;

			case 25: // '\031'
				numWikiTokensSeen = 0;
				positionInc = 1;
				currentTokType = 10;
				yybegin(16);
				break;

			case 26: // '\032'
				yybegin(0);
				break;

			case 27: // '\033'
				numLinkToks = 0;
				yybegin(0);
				break;

			case 28: // '\034'
				currentTokType = 8;
				numWikiTokensSeen = 0;
				yybegin(4);
				break;

			case 29: // '\035'
				currentTokType = 8;
				numWikiTokensSeen = 0;
				yybegin(4);
				break;

			case 30: // '\036'
				yybegin(0);
				break;

			case 31: // '\037'
				numBalanced = 0;
				currentTokType = 0;
				yybegin(0);
				break;

			case 32: // ' '
				numBalanced = 0;
				numWikiTokensSeen = 0;
				currentTokType = 8;
				yybegin(4);
				break;

			case 33: // '!'
				positionInc = 1;
				return 1;

			case 34: // '"'
				positionInc = 1;
				return 5;

			case 35: // '#'
				positionInc = 1;
				return 6;

			case 36: // '$'
				positionInc = 1;
				return 3;

			case 37: // '%'
				currentTokType = 14;
				yybegin(12);
				break;

			case 38: // '&'
				numBalanced = 0;
				currentTokType = 0;
				yybegin(0);
				break;

			case 39: // '\''
				numBalanced = 0;
				currentTokType = 0;
				yybegin(0);
				break;

			case 40: // '('
				positionInc = 1;
				return 2;

			case 41: // ')'
				positionInc = 1;
				return 4;

			case 42: // '*'
				numBalanced = 0;
				currentTokType = 0;
				yybegin(0);
				break;

			case 43: // '+'
				positionInc = 1;
				numWikiTokensSeen++;
				yybegin(6);
				return currentTokType;

			case 44: // ','
				numWikiTokensSeen = 0;
				positionInc = 1;
				currentTokType = 11;
				yybegin(2);
				break;

			case 45: // '-'
				currentTokType = 11;
				numWikiTokensSeen = 0;
				yybegin(2);
				break;

			case 46: // '.'
				numBalanced = 0;
				numWikiTokensSeen = 0;
				currentTokType = 11;
				yybegin(2);
				break;

			default:
				if (zzInput == -1 && zzStartRead == zzCurrentPos)
				{
					zzAtEOF = true;
					return -1;
				}
				zzScanError(1);
				break;

			case 8: // '\b'
			case 18: // '\022'
			case 47: // '/'
			case 48: // '0'
			case 49: // '1'
			case 50: // '2'
			case 51: // '3'
			case 52: // '4'
			case 53: // '5'
			case 54: // '6'
			case 55: // '7'
			case 56: // '8'
			case 57: // '9'
			case 58: // ':'
			case 59: // ';'
			case 60: // '<'
			case 61: // '='
			case 62: // '>'
			case 63: // '?'
			case 64: // '@'
			case 65: // 'A'
			case 66: // 'B'
			case 67: // 'C'
			case 68: // 'D'
			case 69: // 'E'
			case 70: // 'F'
			case 71: // 'G'
			case 72: // 'H'
			case 73: // 'I'
			case 74: // 'J'
			case 75: // 'K'
			case 76: // 'L'
			case 77: // 'M'
			case 78: // 'N'
			case 79: // 'O'
			case 80: // 'P'
			case 81: // 'Q'
			case 82: // 'R'
			case 83: // 'S'
			case 84: // 'T'
			case 85: // 'U'
			case 86: // 'V'
			case 87: // 'W'
			case 88: // 'X'
			case 89: // 'Y'
			case 90: // 'Z'
			case 91: // '['
			case 92: // '\\'
				break;
			}
		} while (true);
	}

	static 
	{
		TOKEN_TYPES = WikipediaTokenizer.TOKEN_TYPES;
	}
}
