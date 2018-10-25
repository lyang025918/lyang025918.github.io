// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GreekStemmer.java

package org.apache.lucene.analysis.el;

import java.util.Arrays;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

public class GreekStemmer
{

	private static final CharArraySet exc4;
	private static final CharArraySet exc6;
	private static final CharArraySet exc7;
	private static final CharArraySet exc8a;
	private static final CharArraySet exc8b;
	private static final CharArraySet exc9;
	private static final CharArraySet exc12a;
	private static final CharArraySet exc12b;
	private static final CharArraySet exc13;
	private static final CharArraySet exc14;
	private static final CharArraySet exc15a;
	private static final CharArraySet exc15b;
	private static final CharArraySet exc16;
	private static final CharArraySet exc17;
	private static final CharArraySet exc18;
	private static final CharArraySet exc19;

	public GreekStemmer()
	{
	}

	public int stem(char s[], int len)
	{
		if (len < 4)
			return len;
		int origLen = len;
		len = rule0(s, len);
		len = rule1(s, len);
		len = rule2(s, len);
		len = rule3(s, len);
		len = rule4(s, len);
		len = rule5(s, len);
		len = rule6(s, len);
		len = rule7(s, len);
		len = rule8(s, len);
		len = rule9(s, len);
		len = rule10(s, len);
		len = rule11(s, len);
		len = rule12(s, len);
		len = rule13(s, len);
		len = rule14(s, len);
		len = rule15(s, len);
		len = rule16(s, len);
		len = rule17(s, len);
		len = rule18(s, len);
		len = rule19(s, len);
		len = rule20(s, len);
		if (len == origLen)
			len = rule21(s, len);
		return rule22(s, len);
	}

	private int rule0(char s[], int len)
	{
		if (len > 9 && (endsWith(s, len, "�ʦ��ȦŦҦӦئӦϦ�") || endsWith(s, len, "�ʦ��ȦŦҦӦئӦئ�")))
			return len - 4;
		if (len > 8 && (endsWith(s, len, "�æŦæϦͦϦӦϦ�") || endsWith(s, len, "�æŦæϦͦϦӦئ�")))
			return len - 4;
		if (len > 8 && endsWith(s, len, "�ʦ��ȦŦҦӦئӦ�"))
			return len - 3;
		if (len > 7 && (endsWith(s, len, "�Ӧ��ӦϦæɦϦ�") || endsWith(s, len, "�Ӧ��ӦϦæɦئ�")))
			return len - 4;
		if (len > 7 && endsWith(s, len, "�æŦæϦͦϦӦ�"))
			return len - 3;
		if (len > 7 && endsWith(s, len, "�ʦ��ȦŦҦӦئ�"))
			return len - 2;
		if (len > 6 && endsWith(s, len, "�Ҧʦ��æɦϦ�") || endsWith(s, len, "�Ҧʦ��æɦئ�") || endsWith(s, len, "�Ϧ˦ϦæɦϦ�") || endsWith(s, len, "�Ϧ˦Ϧæɦئ�") || endsWith(s, len, "�ʦѦŦ��ӦϦ�") || endsWith(s, len, "�ʦѦŦ��Ӧئ�") || endsWith(s, len, "�ЦŦѦ��ӦϦ�") || endsWith(s, len, "�ЦŦѦ��Ӧئ�") || endsWith(s, len, "�ӦŦѦ��ӦϦ�") || endsWith(s, len, "�ӦŦѦ��Ӧئ�"))
			return len - 4;
		if (len > 6 && endsWith(s, len, "�Ӧ��ӦϦæɦ�"))
			return len - 3;
		if (len > 6 && endsWith(s, len, "�æŦæϦͦϦ�"))
			return len - 2;
		if (len > 5 && (endsWith(s, len, "�զ��æɦϦ�") || endsWith(s, len, "�զ��æɦئ�") || endsWith(s, len, "�ҦϦæɦϦ�") || endsWith(s, len, "�ҦϦæɦئ�")))
			return len - 4;
		if (len > 5 && (endsWith(s, len, "�Ҧʦ��æɦ�") || endsWith(s, len, "�Ϧ˦Ϧæɦ�") || endsWith(s, len, "�ʦѦŦ��Ӧ�") || endsWith(s, len, "�ЦŦѦ��Ӧ�") || endsWith(s, len, "�ӦŦѦ��Ӧ�")))
			return len - 3;
		if (len > 4 && (endsWith(s, len, "�զ��æɦ�") || endsWith(s, len, "�ҦϦæɦ�") || endsWith(s, len, "�զئӦϦ�") || endsWith(s, len, "�զئӦئ�")))
			return len - 3;
		if (len > 4 && (endsWith(s, len, "�ʦѦŦ���") || endsWith(s, len, "�ЦŦѦ���") || endsWith(s, len, "�ӦŦѦ���")))
			return len - 2;
		if (len > 3 && endsWith(s, len, "�զئӦ�"))
			return len - 2;
		if (len > 2 && endsWith(s, len, "�զئ�"))
			return len - 1;
		else
			return len;
	}

	private int rule1(char s[], int len)
	{
		if (len > 4 && (endsWith(s, len, "���ĦŦ�") || endsWith(s, len, "���Ħئ�")))
		{
			len -= 4;
			if (!endsWith(s, len, "�Ϧ�") && !endsWith(s, len, "�̦���") && !endsWith(s, len, "�̦���") && !endsWith(s, len, "�̦Ц��̦�") && !endsWith(s, len, "�Ц��ӦŦ�") && !endsWith(s, len, "�æɦ��æ�") && !endsWith(s, len, "�ͦӦ��ͦ�") && !endsWith(s, len, "�ʦԦ�") && !endsWith(s, len, "�ȦŦ�") && !endsWith(s, len, "�ЦŦȦŦ�"))
				len += 2;
		}
		return len;
	}

	private int rule2(char s[], int len)
	{
		if (len > 4 && (endsWith(s, len, "�ŦĦŦ�") || endsWith(s, len, "�ŦĦئ�")))
		{
			len -= 4;
			if (endsWith(s, len, "�Ϧ�") || endsWith(s, len, "�ɦ�") || endsWith(s, len, "�Ŧ̦�") || endsWith(s, len, "�Ԧ�") || endsWith(s, len, "�æǦ�") || endsWith(s, len, "�Ħ���") || endsWith(s, len, "�ʦѦ��Ҧ�") || endsWith(s, len, "�̦ɦ�"))
				len += 2;
		}
		return len;
	}

	private int rule3(char s[], int len)
	{
		if (len > 5 && (endsWith(s, len, "�ϦԦĦŦ�") || endsWith(s, len, "�ϦԦĦئ�")))
		{
			len -= 5;
			if (endsWith(s, len, "���Ѧ�") || endsWith(s, len, "�ʦ��˦ɦ���") || endsWith(s, len, "�ЦŦӦ���") || endsWith(s, len, "�˦ɦ�") || endsWith(s, len, "�Ц˦Ŧ�") || endsWith(s, len, "�Ҧ�") || endsWith(s, len, "��") || endsWith(s, len, "�զ�") || endsWith(s, len, "�զ�") || endsWith(s, len, "�¦Ŧ�") || endsWith(s, len, "�˦ϦԦ�") || endsWith(s, len, "�֦�") || endsWith(s, len, "�Ҧ�") || endsWith(s, len, "�ӦѦ���") || endsWith(s, len, "�զ�"))
				len += 3;
		}
		return len;
	}

	private int rule4(char s[], int len)
	{
		if (len > 3 && (endsWith(s, len, "�Ŧئ�") || endsWith(s, len, "�Ŧئ�")))
		{
			len -= 3;
			if (exc4.contains(s, 0, len))
				len++;
		}
		return len;
	}

	private int rule5(char s[], int len)
	{
		if (len > 2 && endsWith(s, len, "�ɦ�"))
		{
			len -= 2;
			if (endsWithVowel(s, len))
				len++;
		} else
		if (len > 3 && (endsWith(s, len, "�ɦϦ�") || endsWith(s, len, "�ɦئ�")))
		{
			len -= 3;
			if (endsWithVowel(s, len))
				len++;
		}
		return len;
	}

	private int rule6(char s[], int len)
	{
		boolean removed = false;
		if (len > 3 && (endsWith(s, len, "�ɦʦ�") || endsWith(s, len, "�ɦʦ�")))
		{
			len -= 3;
			removed = true;
		} else
		if (len > 4 && (endsWith(s, len, "�ɦʦϦ�") || endsWith(s, len, "�ɦʦئ�")))
		{
			len -= 4;
			removed = true;
		}
		if (removed && (endsWithVowel(s, len) || exc6.contains(s, 0, len)))
			len += 2;
		return len;
	}

	private int rule7(char s[], int len)
	{
		if (len == 5 && endsWith(s, len, "���æ��̦�"))
			return len - 1;
		if (len > 7 && endsWith(s, len, "�ǦȦǦʦ��̦�"))
			len -= 7;
		else
		if (len > 6 && endsWith(s, len, "�ϦԦҦ��̦�"))
			len -= 6;
		else
		if (len > 5 && (endsWith(s, len, "���æ��̦�") || endsWith(s, len, "�ǦҦ��̦�") || endsWith(s, len, "�Ǧʦ��̦�")))
			len -= 5;
		if (len > 3 && endsWith(s, len, "���̦�"))
		{
			len -= 3;
			if (exc7.contains(s, 0, len))
				len += 2;
		}
		return len;
	}

	private int rule8(char s[], int len)
	{
		boolean removed = false;
		if (len > 8 && endsWith(s, len, "�ɦϦԦͦӦ��ͦ�"))
		{
			len -= 8;
			removed = true;
		} else
		if (len > 7 && endsWith(s, len, "�ɦϦͦӦ��ͦ�") || endsWith(s, len, "�ϦԦͦӦ��ͦ�") || endsWith(s, len, "�ǦȦǦʦ��ͦ�"))
		{
			len -= 7;
			removed = true;
		} else
		if (len > 6 && endsWith(s, len, "�ɦϦӦ��ͦ�") || endsWith(s, len, "�ϦͦӦ��ͦ�") || endsWith(s, len, "�ϦԦҦ��ͦ�"))
		{
			len -= 6;
			removed = true;
		} else
		if (len > 5 && endsWith(s, len, "���æ��ͦ�") || endsWith(s, len, "�ǦҦ��ͦ�") || endsWith(s, len, "�ϦӦ��ͦ�") || endsWith(s, len, "�Ǧʦ��ͦ�"))
		{
			len -= 5;
			removed = true;
		}
		if (removed && exc8a.contains(s, 0, len))
		{
			len += 4;
			s[len - 4] = '\u03B1';
			s[len - 3] = '\u03B3';
			s[len - 2] = '\u03B1';
			s[len - 1] = '\u03BD';
		}
		if (len > 3 && endsWith(s, len, "���ͦ�"))
		{
			len -= 3;
			if (endsWithVowelNoY(s, len) || exc8b.contains(s, 0, len))
				len += 2;
		}
		return len;
	}

	private int rule9(char s[], int len)
	{
		if (len > 5 && endsWith(s, len, "�ǦҦŦӦ�"))
			len -= 5;
		if (len > 3 && endsWith(s, len, "�ŦӦ�"))
		{
			len -= 3;
			if (exc9.contains(s, 0, len) || endsWithVowelNoY(s, len) || endsWith(s, len, "�Ϧ�") || endsWith(s, len, "���ɦ�") || endsWith(s, len, "�զϦ�") || endsWith(s, len, "�Ӧ���") || endsWith(s, len, "�Ħɦ���") || endsWith(s, len, "�Ҧ�") || endsWith(s, len, "�Ŧͦ�") || endsWith(s, len, "�ŦԦ�") || endsWith(s, len, "�Ӧɦ�") || endsWith(s, len, "�ԦЦŦѦ�") || endsWith(s, len, "�Ѧ���") || endsWith(s, len, "�Ŧͦ�") || endsWith(s, len, "�ѦϦ�") || endsWith(s, len, "�Ҧ�") || endsWith(s, len, "�ЦԦ�") || endsWith(s, len, "���ɦ�") || endsWith(s, len, "�ҦԦͦ�") || endsWith(s, len, "�ҦԦ�") || endsWith(s, len, "�ҦԦͦ�") || endsWith(s, len, "�֦ئ�") || endsWith(s, len, "�ЦϦ�") || endsWith(s, len, "�¦�") || endsWith(s, len, "�ʦ���") || endsWith(s, len, "�ŦԦ�") || endsWith(s, len, "�Ŧʦ�") || endsWith(s, len, "�ͦŦ�") || endsWith(s, len, "�ѦϦ�") || endsWith(s, len, "���Ѧ�") || endsWith(s, len, "�¦���") || endsWith(s, len, "�¦Ϧ�") || endsWith(s, len, "�ئզŦ�"))
				len += 2;
		}
		return len;
	}

	private int rule10(char s[], int len)
	{
		if (len > 5 && (endsWith(s, len, "�ϦͦӦ���") || endsWith(s, len, "�ئͦӦ���")))
		{
			if ((len -= 5) == 3 && endsWith(s, len, "���Ѧ�"))
			{
				len += 3;
				s[len - 3] = '\u03BF';
			}
			if (endsWith(s, len, "�ʦѦ�"))
			{
				len += 3;
				s[len - 3] = '\u03C9';
			}
		}
		return len;
	}

	private int rule11(char s[], int len)
	{
		if (len > 6 && endsWith(s, len, "�Ϧ̦��ҦӦ�"))
		{
			if ((len -= 6) == 2 && endsWith(s, len, "�Ϧ�"))
				len += 5;
		} else
		if (len > 7 && endsWith(s, len, "�ɦϦ̦��ҦӦ�") && (len -= 7) == 2 && endsWith(s, len, "�Ϧ�"))
		{
			len += 5;
			s[len - 5] = '\u03BF';
			s[len - 4] = '\u03BC';
			s[len - 3] = '\u03B1';
			s[len - 2] = '\u03C3';
			s[len - 1] = '\u03C4';
		}
		return len;
	}

	private int rule12(char s[], int len)
	{
		if (len > 5 && endsWith(s, len, "�ɦŦҦӦ�"))
		{
			len -= 5;
			if (exc12a.contains(s, 0, len))
				len += 4;
		}
		if (len > 4 && endsWith(s, len, "�ŦҦӦ�"))
		{
			len -= 4;
			if (exc12b.contains(s, 0, len))
				len += 3;
		}
		return len;
	}

	private int rule13(char s[], int len)
	{
		if (len > 6 && endsWith(s, len, "�ǦȦǦʦŦ�"))
			len -= 6;
		else
		if (len > 5 && (endsWith(s, len, "�ǦȦǦʦ�") || endsWith(s, len, "�ǦȦǦʦ�")))
			len -= 5;
		boolean removed = false;
		if (len > 4 && endsWith(s, len, "�ǦʦŦ�"))
		{
			len -= 4;
			removed = true;
		} else
		if (len > 3 && (endsWith(s, len, "�Ǧʦ�") || endsWith(s, len, "�Ǧʦ�")))
		{
			len -= 3;
			removed = true;
		}
		if (removed && (exc13.contains(s, 0, len) || endsWith(s, len, "�Ҧʦئ�") || endsWith(s, len, "�ҦʦϦԦ�") || endsWith(s, len, "�ͦ��Ѧ�") || endsWith(s, len, "�Ҧ�") || endsWith(s, len, "�Ϧ�") || endsWith(s, len, "�Цɦ�")))
			len += 2;
		return len;
	}

	private int rule14(char s[], int len)
	{
		boolean removed = false;
		if (len > 5 && endsWith(s, len, "�ϦԦҦŦ�"))
		{
			len -= 5;
			removed = true;
		} else
		if (len > 4 && (endsWith(s, len, "�ϦԦҦ�") || endsWith(s, len, "�ϦԦҦ�")))
		{
			len -= 4;
			removed = true;
		}
		if (removed && (exc14.contains(s, 0, len) || endsWithVowel(s, len) || endsWith(s, len, "�ЦϦĦ���") || endsWith(s, len, "�¦˦Ŧ�") || endsWith(s, len, "�Ц��ͦӦ���") || endsWith(s, len, "�զѦԦ�") || endsWith(s, len, "�̦��ͦӦɦ�") || endsWith(s, len, "�̦��˦�") || endsWith(s, len, "�ʦԦ̦���") || endsWith(s, len, "�˦���") || endsWith(s, len, "�˦Ǧ�") || endsWith(s, len, "�զ���") || endsWith(s, len, "�Ϧ�") || endsWith(s, len, "�ЦѦئ�")))
			len += 3;
		return len;
	}

	private int rule15(char s[], int len)
	{
		boolean removed = false;
		if (len > 4 && endsWith(s, len, "���æŦ�"))
		{
			len -= 4;
			removed = true;
		} else
		if (len > 3 && (endsWith(s, len, "���æ�") || endsWith(s, len, "���æ�")))
		{
			len -= 3;
			removed = true;
		}
		if (removed)
		{
			boolean cond1 = exc15a.contains(s, 0, len) || endsWith(s, len, "�Ϧ�") || endsWith(s, len, "�ЦŦ�") || endsWith(s, len, "�֦ϦѦ�") || endsWith(s, len, "�˦�") || endsWith(s, len, "�Ҧ�") || endsWith(s, len, "�Ѧ�") || endsWith(s, len, "�զ�") || endsWith(s, len, "�Ц�") || endsWith(s, len, "�˦Ϧ�") || endsWith(s, len, "�Ҧ̦Ǧ�");
			boolean cond2 = exc15b.contains(s, 0, len) || endsWith(s, len, "�ʦϦ˦�");
			if (cond1 && !cond2)
				len += 2;
		}
		return len;
	}

	private int rule16(char s[], int len)
	{
		boolean removed = false;
		if (len > 4 && endsWith(s, len, "�ǦҦϦ�"))
		{
			len -= 4;
			removed = true;
		} else
		if (len > 3 && (endsWith(s, len, "�ǦҦ�") || endsWith(s, len, "�ǦҦ�")))
		{
			len -= 3;
			removed = true;
		}
		if (removed && exc16.contains(s, 0, len))
			len += 2;
		return len;
	}

	private int rule17(char s[], int len)
	{
		if (len > 4 && endsWith(s, len, "�ǦҦӦ�"))
		{
			len -= 4;
			if (exc17.contains(s, 0, len))
				len += 3;
		}
		return len;
	}

	private int rule18(char s[], int len)
	{
		boolean removed = false;
		if (len > 6 && (endsWith(s, len, "�ǦҦϦԦͦ�") || endsWith(s, len, "�ǦȦϦԦͦ�")))
		{
			len -= 6;
			removed = true;
		} else
		if (len > 4 && endsWith(s, len, "�ϦԦͦ�"))
		{
			len -= 4;
			removed = true;
		}
		if (removed && exc18.contains(s, 0, len))
		{
			len += 3;
			s[len - 3] = '\u03BF';
			s[len - 2] = '\u03C5';
			s[len - 1] = '\u03BD';
		}
		return len;
	}

	private int rule19(char s[], int len)
	{
		boolean removed = false;
		if (len > 6 && (endsWith(s, len, "�ǦҦϦԦ̦�") || endsWith(s, len, "�ǦȦϦԦ̦�")))
		{
			len -= 6;
			removed = true;
		} else
		if (len > 4 && endsWith(s, len, "�ϦԦ̦�"))
		{
			len -= 4;
			removed = true;
		}
		if (removed && exc19.contains(s, 0, len))
		{
			len += 3;
			s[len - 3] = '\u03BF';
			s[len - 2] = '\u03C5';
			s[len - 1] = '\u03BC';
		}
		return len;
	}

	private int rule20(char s[], int len)
	{
		if (len > 5 && (endsWith(s, len, "�̦��Ӧئ�") || endsWith(s, len, "�̦��ӦϦ�")))
			len -= 3;
		else
		if (len > 4 && endsWith(s, len, "�̦��Ӧ�"))
			len -= 2;
		return len;
	}

	private int rule21(char s[], int len)
	{
		if (len > 9 && endsWith(s, len, "�ɦϦͦӦϦԦҦ���"))
			return len - 9;
		if (len > 8 && (endsWith(s, len, "�ɦϦ̦��ҦӦ���") || endsWith(s, len, "�ɦϦҦ��ҦӦ���") || endsWith(s, len, "�ɦϦԦ̦��ҦӦ�") || endsWith(s, len, "�ϦͦӦϦԦҦ���")))
			return len - 8;
		if (len > 7 && (endsWith(s, len, "�ɦŦ̦��ҦӦ�") || endsWith(s, len, "�ɦŦҦ��ҦӦ�") || endsWith(s, len, "�ɦϦ̦ϦԦͦ�") || endsWith(s, len, "�ɦϦҦ��ҦӦ�") || endsWith(s, len, "�ɦϦҦϦԦͦ�") || endsWith(s, len, "�ɦϦԦͦӦ���") || endsWith(s, len, "�ɦϦԦͦӦ���") || endsWith(s, len, "�ǦȦǦʦ��Ӧ�") || endsWith(s, len, "�Ϧ̦��ҦӦ���") || endsWith(s, len, "�ϦҦ��ҦӦ���") || endsWith(s, len, "�ϦԦ̦��ҦӦ�")))
			return len - 7;
		if (len > 6 && (endsWith(s, len, "�ɦϦ̦ϦԦ�") || endsWith(s, len, "�ɦϦͦӦ���") || endsWith(s, len, "�ɦϦҦϦԦ�") || endsWith(s, len, "�ǦȦŦɦӦ�") || endsWith(s, len, "�ǦȦǦʦ���") || endsWith(s, len, "�Ϧ̦ϦԦͦ�") || endsWith(s, len, "�ϦҦ��ҦӦ�") || endsWith(s, len, "�ϦҦϦԦͦ�") || endsWith(s, len, "�ϦԦͦӦ���") || endsWith(s, len, "�ϦԦͦӦ���") || endsWith(s, len, "�ϦԦҦ��Ӧ�")))
			return len - 6;
		if (len > 5 && (endsWith(s, len, "���æ��Ӧ�") || endsWith(s, len, "�ɦŦ̦���") || endsWith(s, len, "�ɦŦӦ���") || endsWith(s, len, "�ɦŦҦ���") || endsWith(s, len, "�ɦϦӦ���") || endsWith(s, len, "�ɦϦԦ̦�") || endsWith(s, len, "�ǦȦŦɦ�") || endsWith(s, len, "�ǦȦϦԦ�") || endsWith(s, len, "�Ǧʦ��Ӧ�") || endsWith(s, len, "�ǦҦ��Ӧ�") || endsWith(s, len, "�ǦҦϦԦ�") || endsWith(s, len, "�Ϧ̦ϦԦ�") || endsWith(s, len, "�ϦͦӦ���") || endsWith(s, len, "�ϦͦӦ���") || endsWith(s, len, "�ϦҦϦԦ�") || endsWith(s, len, "�ϦԦ̦���") || endsWith(s, len, "�ϦԦҦ���")))
			return len - 5;
		if (len > 4 && (endsWith(s, len, "���æ���") || endsWith(s, len, "���̦���") || endsWith(s, len, "���Ҧ���") || endsWith(s, len, "���Ӧ���") || endsWith(s, len, "�ŦɦӦ�") || endsWith(s, len, "�ŦҦ���") || endsWith(s, len, "�ŦӦ���") || endsWith(s, len, "�ǦĦŦ�") || endsWith(s, len, "�ǦĦئ�") || endsWith(s, len, "�ǦȦŦ�") || endsWith(s, len, "�Ǧʦ���") || endsWith(s, len, "�ǦҦ���") || endsWith(s, len, "�ǦҦŦ�") || endsWith(s, len, "�ǦҦŦ�") || endsWith(s, len, "�Ϧ̦���") || endsWith(s, len, "�ϦӦ���")))
			return len - 4;
		if (len > 3 && (endsWith(s, len, "���Ŧ�") || endsWith(s, len, "�Ŧɦ�") || endsWith(s, len, "�ǦȦ�") || endsWith(s, len, "�ǦҦ�") || endsWith(s, len, "�ϦԦ�") || endsWith(s, len, "�ϦԦ�")))
			return len - 3;
		if (len > 2 && (endsWith(s, len, "����") || endsWith(s, len, "����") || endsWith(s, len, "����") || endsWith(s, len, "�Ŧ�") || endsWith(s, len, "�Ŧ�") || endsWith(s, len, "�Ǧ�") || endsWith(s, len, "�Ϧ�") || endsWith(s, len, "�Ϧ�") || endsWith(s, len, "�Ϧ�") || endsWith(s, len, "�Ԧ�") || endsWith(s, len, "�ئ�")))
			return len - 2;
		if (len > 1 && endsWithVowel(s, len))
			return len - 1;
		else
			return len;
	}

	private int rule22(char s[], int len)
	{
		if (endsWith(s, len, "�ŦҦӦŦ�") || endsWith(s, len, "�ŦҦӦ���"))
			return len - 5;
		if (endsWith(s, len, "�ϦӦŦ�") || endsWith(s, len, "�ϦӦ���") || endsWith(s, len, "�ԦӦŦ�") || endsWith(s, len, "�ԦӦ���") || endsWith(s, len, "�ئӦŦ�") || endsWith(s, len, "�ئӦ���"))
			return len - 4;
		else
			return len;
	}

	private boolean endsWith(char s[], int len, String suffix)
	{
		int suffixLen = suffix.length();
		if (suffixLen > len)
			return false;
		for (int i = suffixLen - 1; i >= 0; i--)
			if (s[len - (suffixLen - i)] != suffix.charAt(i))
				return false;

		return true;
	}

	private boolean endsWithVowel(char s[], int len)
	{
		if (len == 0)
			return false;
		switch (s[len - 1])
		{
		case 945: 
		case 949: 
		case 951: 
		case 953: 
		case 959: 
		case 965: 
		case 969: 
			return true;

		case 946: 
		case 947: 
		case 948: 
		case 950: 
		case 952: 
		case 954: 
		case 955: 
		case 956: 
		case 957: 
		case 958: 
		case 960: 
		case 961: 
		case 962: 
		case 963: 
		case 964: 
		case 966: 
		case 967: 
		case 968: 
		default:
			return false;
		}
	}

	private boolean endsWithVowelNoY(char s[], int len)
	{
		if (len == 0)
			return false;
		switch (s[len - 1])
		{
		case 945: 
		case 949: 
		case 951: 
		case 953: 
		case 959: 
		case 969: 
			return true;
		}
		return false;
	}

	static 
	{
		exc4 = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"��", "��", "�Ŧ�", "�æ���", "��", "��", "�ɦ�", "�Ц���"
		}), false);
		exc6 = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"����", "����", "�Ŧͦ�", "���̦���", "���̦̦Ϧ֦���", "�Ǧ�", "���ͦǦ�", "���ͦӦɦ�", "�զԦ�", "�¦Ѧئ�", 
			"�æŦ�", "�ŦΦئ�", "�ʦ��˦�", "�ʦ��˦˦ɦ�", "�ʦ��Ӧ���", "�̦ϦԦ�", "�̦Ц���", "�̦Ц��æɦ���", "�̦ЦϦ�", "�̦ЦϦ�", 
			"�ͦɦ�", "�Φɦ�", "�ҦԦͦϦ̦Ǧ�", "�ЦŦӦ�", "�ЦɦӦ�", "�Цɦʦ��ͦ�", "�Ц˦ɦ��Ӧ�", "�ЦϦҦӦŦ˦�", "�ЦѦئӦϦ�", "�ҦŦѦ�", 
			"�ҦԦͦ���", "�ӦҦ���", "�ԦЦϦ�", "�զɦ˦Ϧ�", "�զԦ˦Ϧ�", "�֦���"
		}), false);
		exc7 = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"���ͦ���", "���ЦϦ�", "���ЦϦ�", "���ЦϦҦ�", "�¦ϦԦ�", "�ΦŦ�", "�ϦԦ�", "�ЦŦ�", "�Цɦʦ�", "�ЦϦ�", 
			"�Ҧɦ�", "��"
		}), false);
		exc8a = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"�Ӧ�", "�Ӧ�"
		}), false);
		exc8b = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"�¦ŦӦŦ�", "�¦ϦԦ˦�", "�¦Ѧ��֦�", "��", "�ĦѦ��ĦϦԦ�", "��", "�ʦ��˦ЦϦԦ�", "�ʦ��ҦӦŦ�", "�ʦϦѦ̦Ϧ�", "�˦��ϦЦ�", 
			"�̦ئ��̦Ŧ�", "��", "�̦ϦԦҦϦԦ˦�", "��", "�ϦԦ�", "��", "�ЦŦ˦Ŧ�", "�Ц�", "�ЦϦ˦ɦ�", "�ЦϦѦӦϦ�", 
			"�Ҧ��Ѧ��ʦ��Ӧ�", "�ҦϦԦ˦�", "�ӦҦ��Ѧ˦���", "�ϦѦ�", "�ӦҦɦæ�", "�ӦҦϦ�", "�զئӦϦҦӦŦ�", "��", "�צԦ֦ϦЦ�", "����", 
			"�ϦѦ�", "�æ���", "�æŦ�", "�ĦŦ�", "�ĦɦЦ�", "���̦ŦѦɦʦ���", "�ϦԦ�", "�Цɦ�", "�ЦϦԦѦɦ�", "��", 
			"�Ʀئͦ�", "�ɦ�", "�ʦ��Ҧ�", "�ʦϦ�", "�˦ɦ�", "�˦ϦԦȦǦ�", "�̦��ɦͦ�", "�̦Ŧ�", "�Ҧɦ�", "�Ҧ�", 
			"�ҦӦŦ�", "�ӦѦ���", "�ӦҦ���", "��", "�Ŧ�", "���Ħ���", "���Ȧɦæ�", "���̦Ǧ�", "���ͦɦ�", "���ͦϦѦ�", 
			"���ЦǦ�", "���Цɦ�", "���ӦҦɦæ�", "�¦���", "�¦��Ҧ�", "�¦��ȦԦæ���", "�¦ɦϦ̦Ǧ�", "�¦Ѧ��֦Ԧ�", "�Ħɦ���", "�Ħɦ���", 
			"�ŦͦϦѦ�", "�ȦԦ�", "�ʦ��ЦͦϦ¦ɦϦ̦Ǧ�", "�ʦ��Ӧ��æ���", "�ʦ˦ɦ�", "�ʦϦɦ˦��Ѧ�", "�˦ɦ�", "�̦Ŧæ˦Ϧ¦ɦϦ̦Ǧ�", "�̦ɦʦѦϦ¦ɦϦ̦Ǧ�", "�ͦӦ���", 
			"�ΦǦѦϦʦ˦ɦ�", "�Ϧ˦ɦæϦĦ���", "�Ϧ˦Ϧæ���", "�ЦŦͦӦ��Ѧ�", "�ЦŦѦǦ�", "�ЦŦѦɦӦ�", "�Ц˦���", "�ЦϦ˦ԦĦ���", "�ЦϦ˦Ԧ̦Ǧ�", "�ҦӦŦ�", 
			"�Ӧ���", "�ӦŦ�", "�ԦЦŦѦǦ�", "�ԦЦϦʦϦ�", "�֦��̦Ǧ˦ϦĦ���", "�צǦ˦ϦӦ���"
		}), false);
		exc9 = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"���¦���", "�¦Ŧ�", "�Ŧͦ���", "���¦�", "����", "����", "����", "���Ц�", "�¦��ѦϦ�", "�ͦӦ�", 
			"�Ҧ�", "�ʦϦ�", "�̦ЦϦ�", "�ͦɦ�", "�Ц���", "�Ц��Ѧ��ʦ���", "�ҦŦѦ�", "�ҦʦŦ�", "�ҦԦѦ�", "�ӦϦ�", 
			"��", "��", "�Ŧ�", "�Ȧ��Ѧ�", "��"
		}), false);
		exc12a = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"��", "����", "�ҦԦ̦�", "���ҦԦ̦�", "���ʦ��Ӧ���", "���̦ŦӦ��̦�"
		}), false);
		exc12b = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"����", "����", "�ŦʦӦŦ�", "��", "��", "��", "�Ц��Ѧ��ʦ���", "����", "�ЦѦ�", "�ͦɦ�"
		}), false);
		exc13 = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"�Ħɦ���", "��", "�Ц��Ѧ��ʦ��Ӧ���", "�ЦѦϦҦ�", "�ҦԦͦ�"
		}), false);
		exc14 = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"�զ��Ѧ̦���", "�֦���", "���æ�", "���ͦ��Ѧ�", "�¦ѦϦ�", "�Ŧʦ˦ɦ�", "�˦��̦Цɦ�", "�˦Ŧ�", "��", "�Ц���", 
			"��", "��", "�̦Ŧ�", "�̦ŦҦ���", "�ԦЦϦӦŦɦ�", "����", "���ɦ�", "���ͦǦ�", "�ĦŦҦЦϦ�", "�ŦͦĦɦ��զŦ�", 
			"�Ħ�", "�ĦŦԦӦŦѦŦ�", "�ʦ��Ȧ��ѦŦ�", "�Ц˦�", "�ӦҦ�"
		}), false);
		exc15a = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"���¦��Ҧ�", "�ЦϦ˦Ԧ�", "���ĦǦ�", "�Ц��̦�", "��", "���Ҧ�", "����", "���̦���", "���̦��˦˦�", "���ͦԦҦ�", 
			"���ЦŦ�", "���ҦЦ���", "���֦���", "�ĦŦѦ¦Ŧ�", "�ĦѦϦҦϦ�", "�ΦŦ�", "�ͦŦϦ�", "�ͦϦ̦Ϧ�", "�Ϧ˦Ϧ�", "�Ϧ̦Ϧ�", 
			"�ЦѦϦҦ�", "�ЦѦϦҦئЦϦ�", "�ҦԦ̦�", "�ҦԦͦ�", "��", "�ԦЦϦ�", "�֦���", "���Ŧɦ�", "���ɦ̦ϦҦ�", "���ͦԦ�", 
			"���ЦϦ�", "���ѦӦɦ�", "�Ħɦ���", "�Ŧ�", "�ŦЦɦ�", "�ʦѦϦʦ��˦Ϧ�", "�ҦɦĦǦѦϦ�", "��", "�ͦ���", "�ϦԦ˦���", 
			"�ϦԦ�", "��", "�Ӧ�", "��"
		}), false);
		exc15b = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"�צϦ�", "�ͦ��Ԧ˦Ϧ�"
		}), false);
		exc16 = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"��", "�֦ŦѦҦϦ�", "�ĦئĦŦʦ���", "�ŦѦǦ̦Ϧ�", "�̦Ŧæ��˦Ϧ�", "�ŦЦӦ���"
		}), false);
		exc17 = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"���Ҧ�", "�Ҧ�", "���֦�", "�֦�", "���Ц�", "���Ŧɦ̦�", "�ĦԦҦ֦�", "�ŦԦ֦�", "�ʦϦɦͦϦ֦�", "�Ц��˦ɦ̦�"
		}), false);
		exc18 = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"��", "��", "�ҦЦ�", "�ҦӦѦ��¦Ϧ̦ϦԦӦ�", "�ʦ��ʦϦ̦ϦԦӦ�", "�ŦΦئ�"
		}), false);
		exc19 = new CharArraySet(Version.LUCENE_31, Arrays.asList(new String[] {
			"�Ц��Ѧ��ҦϦԦ�", "��", "��", "�ئѦɦϦЦ�", "����", "���˦˦ϦҦϦԦ�", "���ҦϦԦ�"
		}), false);
	}
}
