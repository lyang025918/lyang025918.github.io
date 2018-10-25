// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UnicodeUtil.java

package org.apache.lucene.util;


// Referenced classes of package org.apache.lucene.util:
//			BytesRef, IntsRef, ArrayUtil, CharsRef

public final class UnicodeUtil
{

	public static final BytesRef BIG_TERM = new BytesRef(new byte[] {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1
	});
	public static final int UNI_SUR_HIGH_START = 55296;
	public static final int UNI_SUR_HIGH_END = 56319;
	public static final int UNI_SUR_LOW_START = 56320;
	public static final int UNI_SUR_LOW_END = 57343;
	public static final int UNI_REPLACEMENT_CHAR = 65533;
	private static final long UNI_MAX_BMP = 65535L;
	private static final long HALF_SHIFT = 10L;
	private static final long HALF_MASK = 1023L;
	private static final int SURROGATE_OFFSET = 0xfca02400;
	static byte utf8CodeLength[] = {
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
		1, 1, 1, 1, 1, 1, 1, 1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
		-1, -1, 2, 2, 2, 2, 2, 2, 2, 2, 
		2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
		2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
		2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 
		3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
		4, 4, 4, 4, 4, 4, 4, 4
	};
	private static final int LEAD_SURROGATE_SHIFT_ = 10;
	private static final int TRAIL_SURROGATE_MASK_ = 1023;
	private static final int TRAIL_SURROGATE_MIN_VALUE = 56320;
	private static final int LEAD_SURROGATE_MIN_VALUE = 55296;
	private static final int SUPPLEMENTARY_MIN_VALUE = 0x10000;
	private static final int LEAD_SURROGATE_OFFSET_ = 55232;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/UnicodeUtil.desiredAssertionStatus();

	private UnicodeUtil()
	{
	}

	public static int UTF16toUTF8WithHash(char source[], int offset, int length, BytesRef result)
	{
		int hash = 0;
		int upto = 0;
		int i = offset;
		int end = offset + length;
		byte out[] = result.bytes;
		int maxLen = length * 4;
		if (out.length < maxLen)
			out = result.bytes = new byte[ArrayUtil.oversize(maxLen, 1)];
		result.offset = 0;
		do
		{
			if (i >= end)
				break;
			int code = source[i++];
			if (code < 128)
			{
				hash = 31 * hash + (out[upto++] = (byte)code);
				continue;
			}
			if (code < 2048)
			{
				hash = 31 * hash + (out[upto++] = (byte)(0xc0 | code >> 6));
				hash = 31 * hash + (out[upto++] = (byte)(0x80 | code & 0x3f));
				continue;
			}
			if (code < 55296 || code > 57343)
			{
				hash = 31 * hash + (out[upto++] = (byte)(0xe0 | code >> 12));
				hash = 31 * hash + (out[upto++] = (byte)(0x80 | code >> 6 & 0x3f));
				hash = 31 * hash + (out[upto++] = (byte)(0x80 | code & 0x3f));
				continue;
			}
			if (code < 56320 && i < end)
			{
				int utf32 = source[i];
				if (utf32 >= 56320 && utf32 <= 57343)
				{
					utf32 = (code << 10) + utf32 + 0xfca02400;
					i++;
					hash = 31 * hash + (out[upto++] = (byte)(0xf0 | utf32 >> 18));
					hash = 31 * hash + (out[upto++] = (byte)(0x80 | utf32 >> 12 & 0x3f));
					hash = 31 * hash + (out[upto++] = (byte)(0x80 | utf32 >> 6 & 0x3f));
					hash = 31 * hash + (out[upto++] = (byte)(0x80 | utf32 & 0x3f));
					continue;
				}
			}
			hash = 31 * hash + (out[upto++] = -17);
			hash = 31 * hash + (out[upto++] = -65);
			hash = 31 * hash + (out[upto++] = -67);
		} while (true);
		result.length = upto;
		return hash;
	}

	public static void UTF16toUTF8(char source[], int offset, int length, BytesRef result)
	{
		int upto = 0;
		int i = offset;
		int end = offset + length;
		byte out[] = result.bytes;
		int maxLen = length * 4;
		if (out.length < maxLen)
			out = result.bytes = new byte[maxLen];
		result.offset = 0;
		do
		{
			if (i >= end)
				break;
			int code = source[i++];
			if (code < 128)
			{
				out[upto++] = (byte)code;
				continue;
			}
			if (code < 2048)
			{
				out[upto++] = (byte)(0xc0 | code >> 6);
				out[upto++] = (byte)(0x80 | code & 0x3f);
				continue;
			}
			if (code < 55296 || code > 57343)
			{
				out[upto++] = (byte)(0xe0 | code >> 12);
				out[upto++] = (byte)(0x80 | code >> 6 & 0x3f);
				out[upto++] = (byte)(0x80 | code & 0x3f);
				continue;
			}
			if (code < 56320 && i < end)
			{
				int utf32 = source[i];
				if (utf32 >= 56320 && utf32 <= 57343)
				{
					utf32 = (code << 10) + utf32 + 0xfca02400;
					i++;
					out[upto++] = (byte)(0xf0 | utf32 >> 18);
					out[upto++] = (byte)(0x80 | utf32 >> 12 & 0x3f);
					out[upto++] = (byte)(0x80 | utf32 >> 6 & 0x3f);
					out[upto++] = (byte)(0x80 | utf32 & 0x3f);
					continue;
				}
			}
			out[upto++] = -17;
			out[upto++] = -65;
			out[upto++] = -67;
		} while (true);
		result.length = upto;
	}

	public static void UTF16toUTF8(CharSequence s, int offset, int length, BytesRef result)
	{
		int end = offset + length;
		byte out[] = result.bytes;
		result.offset = 0;
		int maxLen = length * 4;
		if (out.length < maxLen)
			out = result.bytes = new byte[maxLen];
		int upto = 0;
		for (int i = offset; i < end; i++)
		{
			int code = s.charAt(i);
			if (code < 128)
			{
				out[upto++] = (byte)code;
				continue;
			}
			if (code < 2048)
			{
				out[upto++] = (byte)(0xc0 | code >> 6);
				out[upto++] = (byte)(0x80 | code & 0x3f);
				continue;
			}
			if (code < 55296 || code > 57343)
			{
				out[upto++] = (byte)(0xe0 | code >> 12);
				out[upto++] = (byte)(0x80 | code >> 6 & 0x3f);
				out[upto++] = (byte)(0x80 | code & 0x3f);
				continue;
			}
			if (code < 56320 && i < end - 1)
			{
				int utf32 = s.charAt(i + 1);
				if (utf32 >= 56320 && utf32 <= 57343)
				{
					utf32 = (code << 10) + utf32 + 0xfca02400;
					i++;
					out[upto++] = (byte)(0xf0 | utf32 >> 18);
					out[upto++] = (byte)(0x80 | utf32 >> 12 & 0x3f);
					out[upto++] = (byte)(0x80 | utf32 >> 6 & 0x3f);
					out[upto++] = (byte)(0x80 | utf32 & 0x3f);
					continue;
				}
			}
			out[upto++] = -17;
			out[upto++] = -65;
			out[upto++] = -67;
		}

		result.length = upto;
	}

	public static boolean validUTF16String(CharSequence s)
	{
		int size = s.length();
		for (int i = 0; i < size; i++)
		{
			char ch = s.charAt(i);
			if (ch >= '\uD800' && ch <= '\uDBFF')
			{
				if (i < size - 1)
				{
					i++;
					char nextCH = s.charAt(i);
					if (nextCH < '\uDC00' || nextCH > '\uDFFF')
						return false;
				} else
				{
					return false;
				}
				continue;
			}
			if (ch >= '\uDC00' && ch <= '\uDFFF')
				return false;
		}

		return true;
	}

	public static boolean validUTF16String(char s[], int size)
	{
		for (int i = 0; i < size; i++)
		{
			char ch = s[i];
			if (ch >= '\uD800' && ch <= '\uDBFF')
			{
				if (i < size - 1)
				{
					i++;
					char nextCH = s[i];
					if (nextCH < '\uDC00' || nextCH > '\uDFFF')
						return false;
				} else
				{
					return false;
				}
				continue;
			}
			if (ch >= '\uDC00' && ch <= '\uDFFF')
				return false;
		}

		return true;
	}

	public static int codePointCount(BytesRef utf8)
	{
		int upto = utf8.offset;
		int limit = utf8.offset + utf8.length;
		byte bytes[] = utf8.bytes;
		int codePointCount = 0;
		for (; upto < limit; upto += utf8CodeLength[bytes[upto] & 0xff])
			codePointCount++;

		return codePointCount;
	}

	public static void UTF8toUTF32(BytesRef utf8, IntsRef utf32)
	{
		if (utf32.ints == null || utf32.ints.length < utf8.length)
			utf32.ints = new int[utf8.length];
		int utf32Count = 0;
		int utf8Upto = utf8.offset;
		int ints[] = utf32.ints;
		byte bytes[] = utf8.bytes;
		int utf8Limit = utf8.offset + utf8.length;
		do
			if (utf8Upto < utf8Limit)
			{
				int numBytes = utf8CodeLength[bytes[utf8Upto] & 0xff];
				int v = 0;
				switch (numBytes)
				{
				case 1: // '\001'
					ints[utf32Count++] = bytes[utf8Upto++];
					continue;

				case 2: // '\002'
					v = bytes[utf8Upto++] & 0x1f;
					break;

				case 3: // '\003'
					v = bytes[utf8Upto++] & 0xf;
					break;

				case 4: // '\004'
					v = bytes[utf8Upto++] & 7;
					break;

				default:
					throw new IllegalStateException("invalid utf8");
				}
				for (int limit = (utf8Upto + numBytes) - 1; utf8Upto < limit;)
					v = v << 6 | bytes[utf8Upto++] & 0x3f;

				ints[utf32Count++] = v;
			} else
			{
				utf32.offset = 0;
				utf32.length = utf32Count;
				return;
			}
		while (true);
	}

	public static String newString(int codePoints[], int offset, int count)
	{
		if (count < 0)
			throw new IllegalArgumentException();
		char chars[] = new char[count];
		int w = 0;
		int r = offset;
label0:
		for (int e = offset + count; r < e; r++)
		{
			int cp = codePoints[r];
			if (cp < 0 || cp > 0x10ffff)
				throw new IllegalArgumentException();
			do
				try
				{
					if (cp < 0x10000)
					{
						chars[w] = (char)cp;
						w++;
					} else
					{
						chars[w] = (char)(55232 + (cp >> 10));
						chars[w + 1] = (char)(56320 + (cp & 0x3ff));
						w += 2;
					}
					continue label0;
				}
				catch (IndexOutOfBoundsException ex)
				{
					int newlen = (int)Math.ceil(((double)codePoints.length * (double)(w + 2)) / (double)((r - offset) + 1));
					char temp[] = new char[newlen];
					System.arraycopy(chars, 0, temp, 0, w);
					chars = temp;
				}
			while (true);
		}

		return new String(chars, 0, w);
	}

	public static String toHexString(String s)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++)
		{
			char ch = s.charAt(i);
			if (i > 0)
				sb.append(' ');
			if (ch < '\200')
			{
				sb.append(ch);
				continue;
			}
			if (ch >= '\uD800' && ch <= '\uDBFF')
				sb.append("H:");
			else
			if (ch >= '\uDC00' && ch <= '\uDFFF')
				sb.append("L:");
			else
			if (ch > '\uDFFF')
				if (ch == '\uFFFF')
					sb.append("F:");
				else
					sb.append("E:");
			sb.append((new StringBuilder()).append("0x").append(Integer.toHexString(ch)).toString());
		}

		return sb.toString();
	}

	public static void UTF8toUTF16(byte utf8[], int offset, int length, CharsRef chars)
	{
		int out_offset = chars.offset = 0;
		char out[] = chars.chars = ArrayUtil.grow(chars.chars, length);
		for (int limit = offset + length; offset < limit;)
		{
			int b = utf8[offset++] & 0xff;
			if (b < 192)
			{
				if (!$assertionsDisabled && b >= 128)
					throw new AssertionError();
				out[out_offset++] = (char)b;
			} else
			if (b < 224)
				out[out_offset++] = (char)(((b & 0x1f) << 6) + (utf8[offset++] & 0x3f));
			else
			if (b < 240)
			{
				out[out_offset++] = (char)(((b & 0xf) << 12) + ((utf8[offset] & 0x3f) << 6) + (utf8[offset + 1] & 0x3f));
				offset += 2;
			} else
			{
				if (!$assertionsDisabled && b >= 248)
					throw new AssertionError((new StringBuilder()).append("b = 0x").append(Integer.toHexString(b)).toString());
				int ch = ((b & 7) << 18) + ((utf8[offset] & 0x3f) << 12) + ((utf8[offset + 1] & 0x3f) << 6) + (utf8[offset + 2] & 0x3f);
				offset += 3;
				if ((long)ch < 65535L)
				{
					out[out_offset++] = (char)ch;
				} else
				{
					int chHalf = ch - 0x10000;
					out[out_offset++] = (char)((chHalf >> 10) + 55296);
					out[out_offset++] = (char)(int)(((long)chHalf & 1023L) + 56320L);
				}
			}
		}

		chars.length = out_offset - chars.offset;
	}

	public static void UTF8toUTF16(BytesRef bytesRef, CharsRef chars)
	{
		UTF8toUTF16(bytesRef.bytes, bytesRef.offset, bytesRef.length, chars);
	}

}
