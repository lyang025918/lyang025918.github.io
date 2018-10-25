// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SnowballProgram.java

package org.tartarus.snowball;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.lucene.util.ArrayUtil;

// Referenced classes of package org.tartarus.snowball:
//			Among

public abstract class SnowballProgram
{

	private static final Object EMPTY_ARGS[] = new Object[0];
	private char current[];
	protected int cursor;
	protected int limit;
	protected int limit_backward;
	protected int bra;
	protected int ket;

	protected SnowballProgram()
	{
		current = new char[8];
		setCurrent("");
	}

	public abstract boolean stem();

	public void setCurrent(String value)
	{
		current = value.toCharArray();
		cursor = 0;
		limit = value.length();
		limit_backward = 0;
		bra = cursor;
		ket = limit;
	}

	public String getCurrent()
	{
		return new String(current, 0, limit);
	}

	public void setCurrent(char text[], int length)
	{
		current = text;
		cursor = 0;
		limit = length;
		limit_backward = 0;
		bra = cursor;
		ket = limit;
	}

	public char[] getCurrentBuffer()
	{
		return current;
	}

	public int getCurrentBufferLength()
	{
		return limit;
	}

	protected void copy_from(SnowballProgram other)
	{
		current = other.current;
		cursor = other.cursor;
		limit = other.limit;
		limit_backward = other.limit_backward;
		bra = other.bra;
		ket = other.ket;
	}

	protected boolean in_grouping(char s[], int min, int max)
	{
		if (cursor >= limit)
			return false;
		char ch = current[cursor];
		if (ch > max || ch < min)
			return false;
		ch -= min;
		if ((s[ch >> 3] & 1 << (ch & 7)) == 0)
		{
			return false;
		} else
		{
			cursor++;
			return true;
		}
	}

	protected boolean in_grouping_b(char s[], int min, int max)
	{
		if (cursor <= limit_backward)
			return false;
		char ch = current[cursor - 1];
		if (ch > max || ch < min)
			return false;
		ch -= min;
		if ((s[ch >> 3] & 1 << (ch & 7)) == 0)
		{
			return false;
		} else
		{
			cursor--;
			return true;
		}
	}

	protected boolean out_grouping(char s[], int min, int max)
	{
		if (cursor >= limit)
			return false;
		char ch = current[cursor];
		if (ch > max || ch < min)
		{
			cursor++;
			return true;
		}
		ch -= min;
		if ((s[ch >> 3] & 1 << (ch & 7)) == 0)
		{
			cursor++;
			return true;
		} else
		{
			return false;
		}
	}

	protected boolean out_grouping_b(char s[], int min, int max)
	{
		if (cursor <= limit_backward)
			return false;
		char ch = current[cursor - 1];
		if (ch > max || ch < min)
		{
			cursor--;
			return true;
		}
		ch -= min;
		if ((s[ch >> 3] & 1 << (ch & 7)) == 0)
		{
			cursor--;
			return true;
		} else
		{
			return false;
		}
	}

	protected boolean in_range(int min, int max)
	{
		if (cursor >= limit)
			return false;
		char ch = current[cursor];
		if (ch > max || ch < min)
		{
			return false;
		} else
		{
			cursor++;
			return true;
		}
	}

	protected boolean in_range_b(int min, int max)
	{
		if (cursor <= limit_backward)
			return false;
		char ch = current[cursor - 1];
		if (ch > max || ch < min)
		{
			return false;
		} else
		{
			cursor--;
			return true;
		}
	}

	protected boolean out_range(int min, int max)
	{
		if (cursor >= limit)
			return false;
		char ch = current[cursor];
		if (ch <= max && ch >= min)
		{
			return false;
		} else
		{
			cursor++;
			return true;
		}
	}

	protected boolean out_range_b(int min, int max)
	{
		if (cursor <= limit_backward)
			return false;
		char ch = current[cursor - 1];
		if (ch <= max && ch >= min)
		{
			return false;
		} else
		{
			cursor--;
			return true;
		}
	}

	protected boolean eq_s(int s_size, CharSequence s)
	{
		if (limit - cursor < s_size)
			return false;
		for (int i = 0; i != s_size; i++)
			if (current[cursor + i] != s.charAt(i))
				return false;

		cursor += s_size;
		return true;
	}

	protected boolean eq_s_b(int s_size, CharSequence s)
	{
		if (cursor - limit_backward < s_size)
			return false;
		for (int i = 0; i != s_size; i++)
			if (current[(cursor - s_size) + i] != s.charAt(i))
				return false;

		cursor -= s_size;
		return true;
	}

	protected boolean eq_v(CharSequence s)
	{
		return eq_s(s.length(), s);
	}

	protected boolean eq_v_b(CharSequence s)
	{
		return eq_s_b(s.length(), s);
	}

	protected int find_among(Among v[], int v_size)
	{
		int i = 0;
		int j = v_size;
		int c = cursor;
		int l = limit;
		int common_i = 0;
		int common_j = 0;
		boolean first_key_inspected = false;
		do
		{
			do
			{
				int k = i + (j - i >> 1);
				int diff = 0;
				int common = common_i >= common_j ? common_j : common_i;
				Among w = v[k];
				int i2 = common;
				do
				{
					if (i2 >= w.s_size)
						break;
					if (c + common == l)
					{
						diff = -1;
						break;
					}
					diff = current[c + common] - w.s[i2];
					if (diff != 0)
						break;
					common++;
					i2++;
				} while (true);
				if (diff < 0)
				{
					j = k;
					common_j = common;
				} else
				{
					i = k;
					common_i = common;
				}
			} while (j - i > 1);
			if (i > 0 || j == i || first_key_inspected)
				break;
			first_key_inspected = true;
		} while (true);
		do
		{
			Among w = v[i];
			if (common_i >= w.s_size)
			{
				cursor = c + w.s_size;
				if (w.method == null)
					return w.result;
				boolean res;
				try
				{
					Object resobj = w.method.invoke(w.methodobject, EMPTY_ARGS);
					res = resobj.toString().equals("true");
				}
				catch (InvocationTargetException e)
				{
					res = false;
				}
				catch (IllegalAccessException e)
				{
					res = false;
				}
				cursor = c + w.s_size;
				if (res)
					return w.result;
			}
			i = w.substring_i;
		} while (i >= 0);
		return 0;
	}

	protected int find_among_b(Among v[], int v_size)
	{
		int i = 0;
		int j = v_size;
		int c = cursor;
		int lb = limit_backward;
		int common_i = 0;
		int common_j = 0;
		boolean first_key_inspected = false;
		do
		{
			do
			{
				int k = i + (j - i >> 1);
				int diff = 0;
				int common = common_i >= common_j ? common_j : common_i;
				Among w = v[k];
				int i2 = w.s_size - 1 - common;
				do
				{
					if (i2 < 0)
						break;
					if (c - common == lb)
					{
						diff = -1;
						break;
					}
					diff = current[c - 1 - common] - w.s[i2];
					if (diff != 0)
						break;
					common++;
					i2--;
				} while (true);
				if (diff < 0)
				{
					j = k;
					common_j = common;
				} else
				{
					i = k;
					common_i = common;
				}
			} while (j - i > 1);
			if (i > 0 || j == i || first_key_inspected)
				break;
			first_key_inspected = true;
		} while (true);
		do
		{
			Among w = v[i];
			if (common_i >= w.s_size)
			{
				cursor = c - w.s_size;
				if (w.method == null)
					return w.result;
				boolean res;
				try
				{
					Object resobj = w.method.invoke(w.methodobject, EMPTY_ARGS);
					res = resobj.toString().equals("true");
				}
				catch (InvocationTargetException e)
				{
					res = false;
				}
				catch (IllegalAccessException e)
				{
					res = false;
				}
				cursor = c - w.s_size;
				if (res)
					return w.result;
			}
			i = w.substring_i;
		} while (i >= 0);
		return 0;
	}

	protected int replace_s(int c_bra, int c_ket, CharSequence s)
	{
		int adjustment = s.length() - (c_ket - c_bra);
		int newLength = limit + adjustment;
		if (newLength > current.length)
		{
			char newBuffer[] = new char[ArrayUtil.oversize(newLength, 2)];
			System.arraycopy(current, 0, newBuffer, 0, limit);
			current = newBuffer;
		}
		if (adjustment != 0 && c_ket < limit)
			System.arraycopy(current, c_ket, current, c_bra + s.length(), limit - c_ket);
		for (int i = 0; i < s.length(); i++)
			current[c_bra + i] = s.charAt(i);

		limit += adjustment;
		if (cursor >= c_ket)
			cursor += adjustment;
		else
		if (cursor > c_bra)
			cursor = c_bra;
		return adjustment;
	}

	protected void slice_check()
	{
		if (bra < 0 || bra > ket || ket > limit)
			throw new IllegalArgumentException((new StringBuilder()).append("faulty slice operation: bra=").append(bra).append(",ket=").append(ket).append(",limit=").append(limit).toString());
		else
			return;
	}

	protected void slice_from(CharSequence s)
	{
		slice_check();
		replace_s(bra, ket, s);
	}

	protected void slice_del()
	{
		slice_from("");
	}

	protected void insert(int c_bra, int c_ket, CharSequence s)
	{
		int adjustment = replace_s(c_bra, c_ket, s);
		if (c_bra <= bra)
			bra += adjustment;
		if (c_bra <= ket)
			ket += adjustment;
	}

	protected StringBuilder slice_to(StringBuilder s)
	{
		slice_check();
		int len = ket - bra;
		s.setLength(0);
		s.append(current, bra, len);
		return s;
	}

	protected StringBuilder assign_to(StringBuilder s)
	{
		s.setLength(0);
		s.append(current, 0, limit);
		return s;
	}

}
