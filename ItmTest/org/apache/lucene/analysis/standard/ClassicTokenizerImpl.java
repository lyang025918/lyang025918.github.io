// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ClassicTokenizerImpl.java

package org.apache.lucene.analysis.standard;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

// Referenced classes of package org.apache.lucene.analysis.standard:
//			StandardTokenizerInterface, StandardTokenizer

class ClassicTokenizerImpl
	implements StandardTokenizerInterface
{

	public static final int YYEOF = -1;
	private static final int ZZ_BUFFERSIZE = 4096;
	public static final int YYINITIAL = 0;
	private static final int ZZ_LEXSTATE[] = {
		0, 0
	};
	private static final String ZZ_CMAP_PACKED = "\t\000\001\000\001\r\001\000\001\000\001\f\022\000\001\000\005\000\001\005\001\003\004\000\001\t\001\007\001\004\001\t\n\002\006\000\001\006\032\n\004\000\001\b\001\000\032\n/\000\001\n\n\000\001\n\004\000\001\n\005\000\027\n\001\000\037\n\001\000?\n\002\000\022\n\034\000^\n\002\000\t\n\002\000\007\n\016\000\002\n\016\000\005\n\t\000\001\n\213\000\001\n\013\000\001\n\001\000\003\n\001\000\001\n\001\000\024\n\001\000,\n\001\000\b\n\002\000\032\n\f\000\202\n\n\0009\n\002\000\002\n\002\000\002\n\003\000&\n\002\000\002\n7\000&\n\002\000\001\n\007\000'\nH\000\033\n\005\000\003\n.\000\032\n\005\000\013\n\025\000\n\002\007\000c\n\001\000\001\n\017\000\002\n\t\000\n\002\003\n\023\000\001\n\001\000\033\nS\000&\n?\0005\n\003\000\001\n\022\000\001\n\007\000\n\n\004\000\n\002\025\000\b\n\002\000\002\n\002\000\026\n\001\000\007\n\001\000\001\n\003\000\004\n\"\000\002\n\001\000\003\n\004\000\n\002\002\n\023\000\006\n\004\000\002\n\002\000\026\n\001\000\007\n\001\000\002\n\001\000\002\n\001\000\002\n\037\000\004\n\001\000\001\n\007\000\n\002\002\000\003\n\020\000\007\n\001\000\001\n\001\000\003\n\001\000\026\n\001\000\007\n\001\000\002\n\001\000\005\n\003\000\001\n\022\000\001\n\017\000\001\n\005\000\n\002\025\000\b\n\002\000\002\n\002\000\026\n\001\000\007\n\001\000\002\n\002\000\004\n\003\000\001\n\036\000\002\n\001\000\003\n\004\000\n\002\025\000\006\n\003\000\003\n\001\000\004\n\003\000\002\n\001\000\001\n\001\000\002\n\003\000\002\n\003\000\003\n\003\000\b\n\001\000\003\n-\000\t\002\025\000\b\n\001\000\003\n\001\000\027\n\001\000\n\n\001\000\005\n&\000\002\n\004\000\n\002\025\000\b\n\001\000\003\n\001\000\027\n\001\000\n\n\001\000\005\n$\000\001\n\001\000\002\n\004\000\n\002\025\000\b\n\001\000\003\n\001\000\027\n\001\000\020\n&\000\002\n\004\000\n\002\025\000\022\n\003\000\030\n\001\000\t\n\001\000\001\n\002\000\007\n9\000\001\0010\n\001\001\002\n\f\001\007\n\t\001\n\002'\000\002\n\001\000\001\n\002\000\002\n\001\000\001\n\002\000\001\n\006\000\004\n\001\000\007\n\001\000\003\n\001\000\001\n\001\000\001\n\002\000\002\n\001\000\004\n\001\000\002\n\t\000\001\n\002\000\005\n\001\000\001\n\t\000\n\002\002\000\002\n\"\000\001\n\037\000\n\002\026\000\b\n\001\000\"\n\035\000\004\nt\000\"\n\001\000\005\n\001\000\002\n\025\000\n\002\006\000\006\nJ\000&\n\n\000'\n\t\000Z\n\005\000D\n\005\000R\n\006\000\007\n\001\000?\n\001\000\001\n\001\000\004\n\002\000\007\n\001\000\001\n\001\000\004\n\002\000'\n\001\000\001\n\001\000\004\n\002\000\037\n\001\000\001\n\001\000\004\n\002\000\007\n\001\000\001\n\001\000\004\n\002\000\007\n\001\000\007\n\001\000\027\n\001\000\037\n\001\000\001\n\001\000\004\n\002\000\007\n\001\000'\n\001\000\023\n\016\000\t\002.\000U\n\f\000?\n\002\000\b\n\n\000\032\n\005\000K\n\225\0004\n,\000\n\002&\000\n\002\006\000X\n\b\000)\n?\000\234\n\004\000Z\n\006\000\026\n\002\000\006\n\002\000&\n\002\000\006\n\002\000\b\n\001\000\001\n\001\000\001\n\001\000\001\n\001\000\037\n\002\0005\n\001\000\007\n\001\000\001\n\003\000\003\n\001\000\007\n\003\000\004\n\002\000\006\n\004\000\r\n\005\000\003\n\001\000\007\n\202\000\001\n\202\000\001\n\004\000\001\n\002\000\n\n\001\000\001\n\003\000\005\n\006\000\001\n\001\000\001\n\001\000\001\n\001\000\004\n\001\000\003\n\001\000\007\n?\000\002\n*\000\005\n\n\000\001\013T\013\b\013\002\013\002\013Z\013\001\013\003\013\006\013(\013\003\013\001\000^\n\021\000\030\n8\000\020\013¨¡\000\200\013\200\000?\013\n\013@\000ƒã\013Z\013?\n?\000?\n?\000?\013\322\013\007\n\f\000\005\n\005\000\001\n\001\000\n\n\001\000\r\n\001\000\005\n\001\000\001\n\001\000\002\n\001\000\002\n\001\000l\n!\000¨±\n\022\000@\n\002\0006\n(\000\f\nt\000\003\n\001\000\001\n\001\000\207\n\023\000\n\002\007\000\032\n\006\000\032\n\n\000\001\013:\013\037\n\003\000\006\n\002\000\006\n\002\000\006\n\002\000\003\n#\0";
	private static final char ZZ_CMAP[] = zzUnpackCMap("\t\000\001\000\001\r\001\000\001\000\001\f\022\000\001\000\005\000\001\005\001\003\004\000\001\t\001\007\001\004\001\t\n\002\006\000\001\006\032\n\004\000\001\b\001\000\032\n/\000\001\n\n\000\001\n\004\000\001\n\005\000\027\n\001\000\037\n\001\000?\n\002\000\022\n\034\000^\n\002\000\t\n\002\000\007\n\016\000\002\n\016\000\005\n\t\000\001\n\213\000\001\n\013\000\001\n\001\000\003\n\001\000\001\n\001\000\024\n\001\000,\n\001\000\b\n\002\000\032\n\f\000\202\n\n\0009\n\002\000\002\n\002\000\002\n\003\000&\n\002\000\002\n7\000&\n\002\000\001\n\007\000'\nH\000\033\n\005\000\003\n.\000\032\n\005\000\013\n\025\000\n\002\007\000c\n\001\000\001\n\017\000\002\n\t\000\n\002\003\n\023\000\001\n\001\000\033\nS\000&\n?\0005\n\003\000\001\n\022\000\001\n\007\000\n\n\004\000\n\002\025\000\b\n\002\000\002\n\002\000\026\n\001\000\007\n\001\000\001\n\003\000\004\n\"\000\002\n\001\000\003\n\004\000\n\002\002\n\023\000\006\n\004\000\002\n\002\000\026\n\001\000\007\n\001\000\002\n\001\000\002\n\001\000\002\n\037\000\004\n\001\000\001\n\007\000\n\002\002\000\003\n\020\000\007\n\001\000\001\n\001\000\003\n\001\000\026\n\001\000\007\n\001\000\002\n\001\000\005\n\003\000\001\n\022\000\001\n\017\000\001\n\005\000\n\002\025\000\b\n\002\000\002\n\002\000\026\n\001\000\007\n\001\000\002\n\002\000\004\n\003\000\001\n\036\000\002\n\001\000\003\n\004\000\n\002\025\000\006\n\003\000\003\n\001\000\004\n\003\000\002\n\001\000\001\n\001\000\002\n\003\000\002\n\003\000\003\n\003\000\b\n\001\000\003\n-\000\t\002\025\000\b\n\001\000\003\n\001\000\027\n\001\000\n\n\001\000\005\n&\000\002\n\004\000\n\002\025\000\b\n\001\000\003\n\001\000\027\n\001\000\n\n\001\000\005\n$\000\001\n\001\000\002\n\004\000\n\002\025\000\b\n\001\000\003\n\001\000\027\n\001\000\020\n&\000\002\n\004\000\n\002\025\000\022\n\003\000\030\n\001\000\t\n\001\000\001\n\002\000\007\n9\000\001\0010\n\001\001\002\n\f\001\007\n\t\001\n\002'\000\002\n\001\000\001\n\002\000\002\n\001\000\001\n\002\000\001\n\006\000\004\n\001\000\007\n\001\000\003\n\001\000\001\n\001\000\001\n\002\000\002\n\001\000\004\n\001\000\002\n\t\000\001\n\002\000\005\n\001\000\001\n\t\000\n\002\002\000\002\n\"\000\001\n\037\000\n\002\026\000\b\n\001\000\"\n\035\000\004\nt\000\"\n\001\000\005\n\001\000\002\n\025\000\n\002\006\000\006\nJ\000&\n\n\000'\n\t\000Z\n\005\000D\n\005\000R\n\006\000\007\n\001\000?\n\001\000\001\n\001\000\004\n\002\000\007\n\001\000\001\n\001\000\004\n\002\000'\n\001\000\001\n\001\000\004\n\002\000\037\n\001\000\001\n\001\000\004\n\002\000\007\n\001\000\001\n\001\000\004\n\002\000\007\n\001\000\007\n\001\000\027\n\001\000\037\n\001\000\001\n\001\000\004\n\002\000\007\n\001\000'\n\001\000\023\n\016\000\t\002.\000U\n\f\000?\n\002\000\b\n\n\000\032\n\005\000K\n\225\0004\n,\000\n\002&\000\n\002\006\000X\n\b\000)\n?\000\234\n\004\000Z\n\006\000\026\n\002\000\006\n\002\000&\n\002\000\006\n\002\000\b\n\001\000\001\n\001\000\001\n\001\000\001\n\001\000\037\n\002\0005\n\001\000\007\n\001\000\001\n\003\000\003\n\001\000\007\n\003\000\004\n\002\000\006\n\004\000\r\n\005\000\003\n\001\000\007\n\202\000\001\n\202\000\001\n\004\000\001\n\002\000\n\n\001\000\001\n\003\000\005\n\006\000\001\n\001\000\001\n\001\000\001\n\001\000\004\n\001\000\003\n\001\000\007\n?\000\002\n*\000\005\n\n\000\001\013T\013\b\013\002\013\002\013Z\013\001\013\003\013\006\013(\013\003\013\001\000^\n\021\000\030\n8\000\020\013¨¡\000\200\013\200\000?\013\n\013@\000ƒã\013Z\013?\n?\000?\n?\000?\013\322\013\007\n\f\000\005\n\005\000\001\n\001\000\n\n\001\000\r\n\001\000\005\n\001\000\001\n\001\000\002\n\001\000\002\n\001\000l\n!\000¨±\n\022\000@\n\002\0006\n(\000\f\nt\000\003\n\001\000\001\n\001\000\207\n\023\000\n\002\007\000\032\n\006\000\032\n\n\000\001\013:\013\037\n\003\000\006\n\002\000\006\n\002\000\006\n\002\000\003\n#\0");
	private static final int ZZ_ACTION[] = zzUnpackAction();
	private static final String ZZ_ACTION_PACKED_0 = "\001\000\001\001\003\002\001\003\001\001\013\000\001\002\003\004\002\000\001\005\001\000\001\005\003\004\006\005\001\006\001\004\002\007\001\b\001\000\001\b\003\000\002\b\001\t\001\n\001\004";
	private static final int ZZ_ROWMAP[] = zzUnpackRowMap();
	private static final String ZZ_ROWMAP_PACKED_0 = "\000\000\000\016\000\034\000*\0008\000\016\000F\000T\000b\000p\000~\000\214\000\232\000\250\000\266\000\304\000\322\000\340\000\356\000\374\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000¨£\000¨¸\000?\000?\000\322\000?\000?\000?\000?\000?\000?\000?\000T\000\214\000?\000?\000?";
	private static final int ZZ_TRANS[] = zzUnpackTrans();
	private static final String ZZ_TRANS_PACKED_0 = "\001\002\001\003\001\004\007\002\001\005\001\006\001\007\001\002\017\000\002\003\001\000\001\b\001\000\001\t\002\n\001\013\001\003\004\000\001\003\001\004\001\000\001\f\001\000\001\t\002\r\001\016\001\004\004\000\001\003\001\004\001\017\001\020\001\021\001\022\002\n\001\013\001\023\020\000\001\002\001\000\001\024\001\025\007\000\001\026\004\000\002\027\007\000\001\027\004\000\001\030\001\031\007\000\001\032\005\000\001\033\007\000\001\013\004\000\001\034\001\035\007\000\001\036\004\000\001\037\001 \007\000\001!\004\000\001\"\001#\007\000\001$\r\000\001%\004\000\001\024\001\025\007\000\001&\r\000\001'\004\000\002\027\007\000\001(\004\000\001\003\001\004\001\017\001\b\001\021\001\022\002\n\001\013\001\023\004\000\002\024\001\000\001)\001\000\001\t\002*\001\000\001\024\004\000\001\024\001\025\001\000\001+\001\000\001\t\002,\001-\001\025\004\000\001\024\001\025\001\000\001)\001\000\001\t\002*\001\000\001\026\004\000\002\027\001\000\001.\002\000\001.\002\000\001\027\004\000\002\030\001\000\001*\001\000\001\t\002*\001\000\001\030\004\000\001\030\001\031\001\000\001,\001\000\001\t\002,\001-\001\031\004\000\001\030\001\031\001\000\001*\001\000\001\t\002*\001\000\001\032\005\000\001\033\001\000\001-\002\000\003-\001\033\004\000\002\034\001\000\001/\001\000\001\t\002\n\001\013\001\034\004\000\001\034\001\035\001\000\0010\001\000\001\t\002\r\001\016\001\035\004\000\001\034\001\035\001\000\001/\001\000\001\t\002\n\001\013\001\036\004\000\002\037\001\000\001\n\001\000\001\t\002\n\001\013\001\037\004\000\001\037\001 \001\000\001\r\001\000\001\t\002\r\001\016\001 \004\000\001\037\001 \001\000\001\n\001\000\001\t\002\n\001\013\001!\004\000\002\"\001\000\001\013\002\000\003\013\001\"\004\000\001\"\001#\001\000\001\016\002\000\003\016\001#\004\000\001\"\001#\001\000\001\013\002\000\003\013\001$\006\000\001\017\006\000\001%\004\000\001\024\001\025\001\000\0011\001\000\001\t\002*\001\000\001\026\004\000\002\027\001\000\001.\002\000\001.\002\000\001(\004\000\002\024\007\000\001\024\004\000\002\030\007\000\001\030\004\000\002\034\007\000\001\034\004\000\002\037\007\000\001\037\004\000\002\"\007\000\001\"\004\000\0022\007\000\0012\004\000\002\024\007\000\0013\004\000\0022\001\000\001.\002\000\001.\002\000\0012\004\000\002\024\001\000\0011\001\000\001\t\002*\001\000\001\024\003\0";
	private static final int ZZ_UNKNOWN_ERROR = 0;
	private static final int ZZ_NO_MATCH = 1;
	private static final int ZZ_PUSHBACK_2BIG = 2;
	private static final String ZZ_ERROR_MSG[] = {
		"Unkown internal scanner error", "Error: could not match input", "Error: pushback value was too large"
	};
	private static final int ZZ_ATTRIBUTE[] = zzUnpackAttribute();
	private static final String ZZ_ATTRIBUTE_PACKED_0 = "\001\000\001\t\003\001\001\t\001\001\013\000\004\001\002\000\001\001\001\000\017\001\001\000\001\001\003\000\005\001";
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
	public static final int ACRONYM_DEP = 8;
	public static final String TOKEN_TYPES[];

	private static int[] zzUnpackAction()
	{
		int result[] = new int[51];
		int offset = 0;
		offset = zzUnpackAction("\001\000\001\001\003\002\001\003\001\001\013\000\001\002\003\004\002\000\001\005\001\000\001\005\003\004\006\005\001\006\001\004\002\007\001\b\001\000\001\b\003\000\002\b\001\t\001\n\001\004", offset, result);
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
		int result[] = new int[51];
		int offset = 0;
		offset = zzUnpackRowMap("\000\000\000\016\000\034\000*\0008\000\016\000F\000T\000b\000p\000~\000\214\000\232\000\250\000\266\000\304\000\322\000\340\000\356\000\374\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000?\000¨£\000¨¸\000?\000?\000\322\000?\000?\000?\000?\000?\000?\000?\000T\000\214\000?\000?\000?", offset, result);
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
		int result[] = new int[658];
		int offset = 0;
		offset = zzUnpackTrans("\001\002\001\003\001\004\007\002\001\005\001\006\001\007\001\002\017\000\002\003\001\000\001\b\001\000\001\t\002\n\001\013\001\003\004\000\001\003\001\004\001\000\001\f\001\000\001\t\002\r\001\016\001\004\004\000\001\003\001\004\001\017\001\020\001\021\001\022\002\n\001\013\001\023\020\000\001\002\001\000\001\024\001\025\007\000\001\026\004\000\002\027\007\000\001\027\004\000\001\030\001\031\007\000\001\032\005\000\001\033\007\000\001\013\004\000\001\034\001\035\007\000\001\036\004\000\001\037\001 \007\000\001!\004\000\001\"\001#\007\000\001$\r\000\001%\004\000\001\024\001\025\007\000\001&\r\000\001'\004\000\002\027\007\000\001(\004\000\001\003\001\004\001\017\001\b\001\021\001\022\002\n\001\013\001\023\004\000\002\024\001\000\001)\001\000\001\t\002*\001\000\001\024\004\000\001\024\001\025\001\000\001+\001\000\001\t\002,\001-\001\025\004\000\001\024\001\025\001\000\001)\001\000\001\t\002*\001\000\001\026\004\000\002\027\001\000\001.\002\000\001.\002\000\001\027\004\000\002\030\001\000\001*\001\000\001\t\002*\001\000\001\030\004\000\001\030\001\031\001\000\001,\001\000\001\t\002,\001-\001\031\004\000\001\030\001\031\001\000\001*\001\000\001\t\002*\001\000\001\032\005\000\001\033\001\000\001-\002\000\003-\001\033\004\000\002\034\001\000\001/\001\000\001\t\002\n\001\013\001\034\004\000\001\034\001\035\001\000\0010\001\000\001\t\002\r\001\016\001\035\004\000\001\034\001\035\001\000\001/\001\000\001\t\002\n\001\013\001\036\004\000\002\037\001\000\001\n\001\000\001\t\002\n\001\013\001\037\004\000\001\037\001 \001\000\001\r\001\000\001\t\002\r\001\016\001 \004\000\001\037\001 \001\000\001\n\001\000\001\t\002\n\001\013\001!\004\000\002\"\001\000\001\013\002\000\003\013\001\"\004\000\001\"\001#\001\000\001\016\002\000\003\016\001#\004\000\001\"\001#\001\000\001\013\002\000\003\013\001$\006\000\001\017\006\000\001%\004\000\001\024\001\025\001\000\0011\001\000\001\t\002*\001\000\001\026\004\000\002\027\001\000\001.\002\000\001.\002\000\001(\004\000\002\024\007\000\001\024\004\000\002\030\007\000\001\030\004\000\002\034\007\000\001\034\004\000\002\037\007\000\001\037\004\000\002\"\007\000\001\"\004\000\0022\007\000\0012\004\000\002\024\007\000\0013\004\000\0022\001\000\001.\002\000\001.\002\000\0012\004\000\002\024\001\000\0011\001\000\001\t\002*\001\000\001\024\003\0", offset, result);
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
		int result[] = new int[51];
		int offset = 0;
		offset = zzUnpackAttribute("\001\000\001\t\003\001\001\t\001\001\013\000\004\001\002\000\001\001\001\000\017\001\001\000\001\001\003\000\005\001", offset, result);
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

	public final int yychar()
	{
		return yychar;
	}

	public final void getText(CharTermAttribute t)
	{
		t.copyBuffer(zzBuffer, zzStartRead, zzMarkedPos - zzStartRead);
	}

	ClassicTokenizerImpl(Reader in)
	{
		zzLexicalState = 0;
		zzBuffer = new char[4096];
		zzAtBOL = true;
		zzReader = in;
	}

	private static char[] zzUnpackCMap(String packed)
	{
		char map[] = new char[0x10000];
		int i = 0;
		int j = 0;
		while (i < 1154) 
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
			case 2: // '\002'
				return 0;

			case 3: // '\003'
				return 7;

			case 4: // '\004'
				return 5;

			case 5: // '\005'
				return 6;

			case 6: // '\006'
				return 1;

			case 7: // '\007'
				return 3;

			case 8: // '\b'
				return 8;

			case 9: // '\t'
				return 2;

			case 10: // '\n'
				return 4;

			default:
				if (zzInput == -1 && zzStartRead == zzCurrentPos)
				{
					zzAtEOF = true;
					return -1;
				}
				zzScanError(1);
				break;

			case 1: // '\001'
			case 11: // '\013'
			case 12: // '\f'
			case 13: // '\r'
			case 14: // '\016'
			case 15: // '\017'
			case 16: // '\020'
			case 17: // '\021'
			case 18: // '\022'
			case 19: // '\023'
			case 20: // '\024'
				break;
			}
		} while (true);
	}

	static 
	{
		TOKEN_TYPES = StandardTokenizer.TOKEN_TYPES;
	}
}
