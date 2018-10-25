// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EscapeQuerySyntax.java

package org.apache.lucene.queryparser.flexible.core.parser;

import java.util.Locale;

public interface EscapeQuerySyntax
{
	public static final class Type extends Enum
	{

		public static final Type STRING;
		public static final Type NORMAL;
		private static final Type $VALUES[];

		public static Type[] values()
		{
			return (Type[])$VALUES.clone();
		}

		public static Type valueOf(String name)
		{
			return (Type)Enum.valueOf(org/apache/lucene/queryparser/flexible/core/parser/EscapeQuerySyntax$Type, name);
		}

		static 
		{
			STRING = new Type("STRING", 0);
			NORMAL = new Type("NORMAL", 1);
			$VALUES = (new Type[] {
				STRING, NORMAL
			});
		}

		private Type(String s, int i)
		{
			super(s, i);
		}
	}


	public abstract CharSequence escape(CharSequence charsequence, Locale locale, Type type);
}
