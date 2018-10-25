// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CharArrayMap.java

package org.apache.lucene.analysis.util;

import java.util.*;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.util:
//			CharacterUtils, CharArraySet

public class CharArrayMap extends AbstractMap
{
	private static final class EmptyCharArrayMap extends UnmodifiableCharArrayMap
	{

		public boolean containsKey(char text[], int off, int len)
		{
			if (text == null)
				throw new NullPointerException();
			else
				return false;
		}

		public boolean containsKey(CharSequence cs)
		{
			if (cs == null)
				throw new NullPointerException();
			else
				return false;
		}

		public boolean containsKey(Object o)
		{
			if (o == null)
				throw new NullPointerException();
			else
				return false;
		}

		public Object get(char text[], int off, int len)
		{
			if (text == null)
				throw new NullPointerException();
			else
				return null;
		}

		public Object get(CharSequence cs)
		{
			if (cs == null)
				throw new NullPointerException();
			else
				return null;
		}

		public Object get(Object o)
		{
			if (o == null)
				throw new NullPointerException();
			else
				return null;
		}

		EmptyCharArrayMap()
		{
			super(new CharArrayMap(Version.LUCENE_CURRENT, 0, false));
		}
	}

	static class UnmodifiableCharArrayMap extends CharArrayMap
	{

		public void clear()
		{
			throw new UnsupportedOperationException();
		}

		public Object put(Object o, Object val)
		{
			throw new UnsupportedOperationException();
		}

		public Object put(char text[], Object val)
		{
			throw new UnsupportedOperationException();
		}

		public Object put(CharSequence text, Object val)
		{
			throw new UnsupportedOperationException();
		}

		public Object put(String text, Object val)
		{
			throw new UnsupportedOperationException();
		}

		public Object remove(Object key)
		{
			throw new UnsupportedOperationException();
		}

		EntrySet createEntrySet()
		{
			return new EntrySet(false);
		}

		public volatile Set entrySet()
		{
			return entrySet();
		}

		public volatile Set keySet()
		{
			return keySet();
		}

		UnmodifiableCharArrayMap(CharArrayMap map)
		{
			super(map, null);
		}
	}

	public final class EntrySet extends AbstractSet
	{

		private final boolean allowModify;
		final CharArrayMap this$0;

		public EntryIterator iterator()
		{
			return new EntryIterator(allowModify);
		}

		public boolean contains(Object o)
		{
			if (!(o instanceof java.util.Map.Entry))
			{
				return false;
			} else
			{
				java.util.Map.Entry e = (java.util.Map.Entry)o;
				Object key = e.getKey();
				Object val = e.getValue();
				Object v = get(key);
				return v != null ? v.equals(val) : val == null;
			}
		}

		public boolean remove(Object o)
		{
			throw new UnsupportedOperationException();
		}

		public int size()
		{
			return count;
		}

		public void clear()
		{
			if (!allowModify)
			{
				throw new UnsupportedOperationException();
			} else
			{
				CharArrayMap.this.clear();
				return;
			}
		}

		public volatile Iterator iterator()
		{
			return iterator();
		}

		private EntrySet(boolean allowModify)
		{
			this$0 = CharArrayMap.this;
			super();
			this.allowModify = allowModify;
		}

	}

	private final class MapEntry
		implements java.util.Map.Entry
	{

		private final int pos;
		private final boolean allowModify;
		final CharArrayMap this$0;

		public Object getKey()
		{
			return keys[pos].clone();
		}

		public Object getValue()
		{
			return values[pos];
		}

		public Object setValue(Object value)
		{
			if (!allowModify)
			{
				throw new UnsupportedOperationException();
			} else
			{
				Object old = values[pos];
				values[pos] = value;
				return old;
			}
		}

		public String toString()
		{
			return (new StringBuilder()).append(keys[pos]).append('=').append(values[pos] != CharArrayMap.this ? values[pos] : "(this Map)").toString();
		}

		private MapEntry(int pos, boolean allowModify)
		{
			this$0 = CharArrayMap.this;
			super();
			this.pos = pos;
			this.allowModify = allowModify;
		}

	}

	public class EntryIterator
		implements Iterator
	{

		private int pos;
		private int lastPos;
		private final boolean allowModify;
		final CharArrayMap this$0;

		private void goNext()
		{
			lastPos = pos;
			for (pos++; pos < keys.length && keys[pos] == null; pos++);
		}

		public boolean hasNext()
		{
			return pos < keys.length;
		}

		public char[] nextKey()
		{
			goNext();
			return keys[lastPos];
		}

		public String nextKeyString()
		{
			return new String(nextKey());
		}

		public Object currentValue()
		{
			return values[lastPos];
		}

		public Object setValue(Object value)
		{
			if (!allowModify)
			{
				throw new UnsupportedOperationException();
			} else
			{
				Object old = values[lastPos];
				values[lastPos] = value;
				return old;
			}
		}

		public java.util.Map.Entry next()
		{
			goNext();
			return new MapEntry(lastPos, allowModify);
		}

		public void remove()
		{
			throw new UnsupportedOperationException();
		}

		public volatile Object next()
		{
			return next();
		}

		private EntryIterator(boolean allowModify)
		{
			this$0 = CharArrayMap.this;
			super();
			pos = -1;
			this.allowModify = allowModify;
			goNext();
		}

	}


	private static final CharArrayMap EMPTY_MAP = new EmptyCharArrayMap();
	private static final int INIT_SIZE = 8;
	private final CharacterUtils charUtils;
	private boolean ignoreCase;
	private int count;
	final Version matchVersion;
	char keys[][];
	Object values[];
	private EntrySet entrySet;
	private CharArraySet keySet;
	static final boolean $assertionsDisabled = !org/apache/lucene/analysis/util/CharArrayMap.desiredAssertionStatus();

	public CharArrayMap(Version matchVersion, int startSize, boolean ignoreCase)
	{
		entrySet = null;
		keySet = null;
		this.ignoreCase = ignoreCase;
		int size;
		for (size = 8; startSize + (startSize >> 2) > size; size <<= 1);
		keys = new char[size][];
		values = (Object[])new Object[size];
		charUtils = CharacterUtils.getInstance(matchVersion);
		this.matchVersion = matchVersion;
	}

	public CharArrayMap(Version matchVersion, Map c, boolean ignoreCase)
	{
		this(matchVersion, c.size(), ignoreCase);
		putAll(c);
	}

	private CharArrayMap(CharArrayMap toCopy)
	{
		entrySet = null;
		keySet = null;
		keys = toCopy.keys;
		values = toCopy.values;
		ignoreCase = toCopy.ignoreCase;
		count = toCopy.count;
		charUtils = toCopy.charUtils;
		matchVersion = toCopy.matchVersion;
	}

	public void clear()
	{
		count = 0;
		Arrays.fill(keys, null);
		Arrays.fill(values, null);
	}

	public boolean containsKey(char text[], int off, int len)
	{
		return keys[getSlot(text, off, len)] != null;
	}

	public boolean containsKey(CharSequence cs)
	{
		return keys[getSlot(cs)] != null;
	}

	public boolean containsKey(Object o)
	{
		if (o instanceof char[])
		{
			char text[] = (char[])(char[])o;
			return containsKey(text, 0, text.length);
		} else
		{
			return containsKey(((CharSequence) (o.toString())));
		}
	}

	public Object get(char text[], int off, int len)
	{
		return values[getSlot(text, off, len)];
	}

	public Object get(CharSequence cs)
	{
		return values[getSlot(cs)];
	}

	public Object get(Object o)
	{
		if (o instanceof char[])
		{
			char text[] = (char[])(char[])o;
			return get(text, 0, text.length);
		} else
		{
			return get(((CharSequence) (o.toString())));
		}
	}

	private int getSlot(char text[], int off, int len)
	{
		int code = getHashCode(text, off, len);
		int pos = code & keys.length - 1;
		char text2[] = keys[pos];
		if (text2 != null && !equals(text, off, len, text2))
		{
			int inc = (code >> 8) + code | 1;
			do
			{
				code += inc;
				pos = code & keys.length - 1;
				text2 = keys[pos];
			} while (text2 != null && !equals(text, off, len, text2));
		}
		return pos;
	}

	private int getSlot(CharSequence text)
	{
		int code = getHashCode(text);
		int pos = code & keys.length - 1;
		char text2[] = keys[pos];
		if (text2 != null && !equals(text, text2))
		{
			int inc = (code >> 8) + code | 1;
			do
			{
				code += inc;
				pos = code & keys.length - 1;
				text2 = keys[pos];
			} while (text2 != null && !equals(text, text2));
		}
		return pos;
	}

	public Object put(CharSequence text, Object value)
	{
		return put(text.toString(), value);
	}

	public Object put(Object o, Object value)
	{
		if (o instanceof char[])
			return put((char[])(char[])o, value);
		else
			return put(o.toString(), value);
	}

	public Object put(String text, Object value)
	{
		return put(text.toCharArray(), value);
	}

	public Object put(char text[], Object value)
	{
		if (ignoreCase)
		{
			for (int i = 0; i < text.length; i += Character.toChars(Character.toLowerCase(charUtils.codePointAt(text, i)), text, i));
		}
		int slot = getSlot(text, 0, text.length);
		if (keys[slot] != null)
		{
			Object oldValue = values[slot];
			values[slot] = value;
			return oldValue;
		}
		keys[slot] = text;
		values[slot] = value;
		count++;
		if (count + (count >> 2) > keys.length)
			rehash();
		return null;
	}

	private void rehash()
	{
		if (!$assertionsDisabled && keys.length != values.length)
			throw new AssertionError();
		int newSize = 2 * keys.length;
		char oldkeys[][] = keys;
		Object oldvalues[] = values;
		keys = new char[newSize][];
		values = (Object[])new Object[newSize];
		for (int i = 0; i < oldkeys.length; i++)
		{
			char text[] = oldkeys[i];
			if (text != null)
			{
				int slot = getSlot(text, 0, text.length);
				keys[slot] = text;
				values[slot] = oldvalues[i];
			}
		}

	}

	private boolean equals(char text1[], int off, int len, char text2[])
	{
		if (len != text2.length)
			return false;
		int limit = off + len;
		if (ignoreCase)
		{
			int codePointAt;
			for (int i = 0; i < len; i += Character.charCount(codePointAt))
			{
				codePointAt = charUtils.codePointAt(text1, off + i, limit);
				if (Character.toLowerCase(codePointAt) != charUtils.codePointAt(text2, i))
					return false;
			}

		} else
		{
			for (int i = 0; i < len; i++)
				if (text1[off + i] != text2[i])
					return false;

		}
		return true;
	}

	private boolean equals(CharSequence text1, char text2[])
	{
		int len = text1.length();
		if (len != text2.length)
			return false;
		if (ignoreCase)
		{
			int codePointAt;
			for (int i = 0; i < len; i += Character.charCount(codePointAt))
			{
				codePointAt = charUtils.codePointAt(text1, i);
				if (Character.toLowerCase(codePointAt) != charUtils.codePointAt(text2, i))
					return false;
			}

		} else
		{
			for (int i = 0; i < len; i++)
				if (text1.charAt(i) != text2[i])
					return false;

		}
		return true;
	}

	private int getHashCode(char text[], int offset, int len)
	{
		if (text == null)
			throw new NullPointerException();
		int code = 0;
		int stop = offset + len;
		if (ignoreCase)
		{
			int codePointAt;
			for (int i = offset; i < stop; i += Character.charCount(codePointAt))
			{
				codePointAt = charUtils.codePointAt(text, i, stop);
				code = code * 31 + Character.toLowerCase(codePointAt);
			}

		} else
		{
			for (int i = offset; i < stop; i++)
				code = code * 31 + text[i];

		}
		return code;
	}

	private int getHashCode(CharSequence text)
	{
		if (text == null)
			throw new NullPointerException();
		int code = 0;
		int len = text.length();
		if (ignoreCase)
		{
			int codePointAt;
			for (int i = 0; i < len; i += Character.charCount(codePointAt))
			{
				codePointAt = charUtils.codePointAt(text, i);
				code = code * 31 + Character.toLowerCase(codePointAt);
			}

		} else
		{
			for (int i = 0; i < len; i++)
				code = code * 31 + text.charAt(i);

		}
		return code;
	}

	public Object remove(Object key)
	{
		throw new UnsupportedOperationException();
	}

	public int size()
	{
		return count;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder("{");
		java.util.Map.Entry entry;
		for (Iterator i$ = entrySet().iterator(); i$.hasNext(); sb.append(entry))
		{
			entry = (java.util.Map.Entry)i$.next();
			if (sb.length() > 1)
				sb.append(", ");
		}

		return sb.append('}').toString();
	}

	EntrySet createEntrySet()
	{
		return new EntrySet(true);
	}

	public final EntrySet entrySet()
	{
		if (entrySet == null)
			entrySet = createEntrySet();
		return entrySet;
	}

	final Set originalKeySet()
	{
		return super.keySet();
	}

	public final CharArraySet keySet()
	{
		if (keySet == null)
			keySet = new CharArraySet(this) {

				final CharArrayMap this$0;

				public boolean add(Object o)
				{
					throw new UnsupportedOperationException();
				}

				public boolean add(CharSequence text)
				{
					throw new UnsupportedOperationException();
				}

				public boolean add(String text)
				{
					throw new UnsupportedOperationException();
				}

				public boolean add(char text[])
				{
					throw new UnsupportedOperationException();
				}

			
			{
				this$0 = CharArrayMap.this;
				super(x0);
			}
			};
		return keySet;
	}

	public static CharArrayMap unmodifiableMap(CharArrayMap map)
	{
		if (map == null)
			throw new NullPointerException("Given map is null");
		if (map == emptyMap() || map.isEmpty())
			return emptyMap();
		if (map instanceof UnmodifiableCharArrayMap)
			return map;
		else
			return new UnmodifiableCharArrayMap(map);
	}

	public static CharArrayMap copy(Version matchVersion, Map map)
	{
		if (map == EMPTY_MAP)
			return emptyMap();
		if (map instanceof CharArrayMap)
		{
			CharArrayMap m = (CharArrayMap)map;
			char keys[][] = new char[m.keys.length][];
			System.arraycopy(m.keys, 0, keys, 0, keys.length);
			Object values[] = (Object[])new Object[m.values.length];
			System.arraycopy(((Object) (m.values)), 0, ((Object) (values)), 0, values.length);
			m = new CharArrayMap(m);
			m.keys = keys;
			m.values = values;
			return m;
		} else
		{
			return new CharArrayMap(matchVersion, map, false);
		}
	}

	public static CharArrayMap emptyMap()
	{
		return EMPTY_MAP;
	}

	public volatile Set entrySet()
	{
		return entrySet();
	}

	public volatile Set keySet()
	{
		return keySet();
	}



}
