// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PatternParser.java

package org.apache.log4j.pattern;

import java.lang.reflect.Method;
import java.util.*;
import org.apache.log4j.helpers.Loader;
import org.apache.log4j.helpers.LogLog;

// Referenced classes of package org.apache.log4j.pattern:
//			LiteralPatternConverter, FormattingInfo, PatternConverter

public final class PatternParser
{
	private static class ReadOnlyMap
		implements Map
	{

		private final Map map;

		public void clear()
		{
			throw new UnsupportedOperationException();
		}

		public boolean containsKey(Object key)
		{
			return map.containsKey(key);
		}

		public boolean containsValue(Object value)
		{
			return map.containsValue(value);
		}

		public Set entrySet()
		{
			return map.entrySet();
		}

		public Object get(Object key)
		{
			return map.get(key);
		}

		public boolean isEmpty()
		{
			return map.isEmpty();
		}

		public Set keySet()
		{
			return map.keySet();
		}

		public Object put(Object key, Object value)
		{
			throw new UnsupportedOperationException();
		}

		public void putAll(Map t)
		{
			throw new UnsupportedOperationException();
		}

		public Object remove(Object key)
		{
			throw new UnsupportedOperationException();
		}

		public int size()
		{
			return map.size();
		}

		public Collection values()
		{
			return map.values();
		}

		public ReadOnlyMap(Map src)
		{
			map = src;
		}
	}


	private static final char ESCAPE_CHAR = 37;
	private static final int LITERAL_STATE = 0;
	private static final int CONVERTER_STATE = 1;
	private static final int DOT_STATE = 3;
	private static final int MIN_STATE = 4;
	private static final int MAX_STATE = 5;
	private static final Map PATTERN_LAYOUT_RULES;
	private static final Map FILENAME_PATTERN_RULES;

	private PatternParser()
	{
	}

	public static Map getPatternLayoutRules()
	{
		return PATTERN_LAYOUT_RULES;
	}

	public static Map getFileNamePatternRules()
	{
		return FILENAME_PATTERN_RULES;
	}

	private static int extractConverter(char lastChar, String pattern, int i, StringBuffer convBuf, StringBuffer currentLiteral)
	{
		convBuf.setLength(0);
		if (!Character.isUnicodeIdentifierStart(lastChar))
			return i;
		convBuf.append(lastChar);
		for (; i < pattern.length() && Character.isUnicodeIdentifierPart(pattern.charAt(i)); i++)
		{
			convBuf.append(pattern.charAt(i));
			currentLiteral.append(pattern.charAt(i));
		}

		return i;
	}

	private static int extractOptions(String pattern, int i, List options)
	{
		do
		{
			if (i >= pattern.length() || pattern.charAt(i) != '{')
				break;
			int end = pattern.indexOf('}', i);
			if (end == -1)
				break;
			String r = pattern.substring(i + 1, end);
			options.add(r);
			i = end + 1;
		} while (true);
		return i;
	}

	public static void parse(String pattern, List patternConverters, List formattingInfos, Map converterRegistry, Map rules)
	{
		if (pattern == null)
			throw new NullPointerException("pattern");
		StringBuffer currentLiteral = new StringBuffer(32);
		int patternLength = pattern.length();
		int state = 0;
		int i = 0;
		FormattingInfo formattingInfo = FormattingInfo.getDefault();
		do
		{
			if (i < patternLength)
			{
				char c = pattern.charAt(i++);
				switch (state)
				{
				case 0: // '\0'
					if (i == patternLength)
						currentLiteral.append(c);
					else
					if (c == '%')
						switch (pattern.charAt(i))
						{
						case 37: // '%'
							currentLiteral.append(c);
							i++;
							break;

						default:
							if (currentLiteral.length() != 0)
							{
								patternConverters.add(new LiteralPatternConverter(currentLiteral.toString()));
								formattingInfos.add(FormattingInfo.getDefault());
							}
							currentLiteral.setLength(0);
							currentLiteral.append(c);
							state = 1;
							formattingInfo = FormattingInfo.getDefault();
							break;
						}
					else
						currentLiteral.append(c);
					break;

				case 1: // '\001'
					currentLiteral.append(c);
					switch (c)
					{
					case 45: // '-'
						formattingInfo = new FormattingInfo(true, formattingInfo.getMinLength(), formattingInfo.getMaxLength());
						break;

					case 46: // '.'
						state = 3;
						break;

					default:
						if (c >= '0' && c <= '9')
						{
							formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), c - 48, formattingInfo.getMaxLength());
							state = 4;
						} else
						{
							i = finalizeConverter(c, pattern, i, currentLiteral, formattingInfo, converterRegistry, rules, patternConverters, formattingInfos);
							state = 0;
							formattingInfo = FormattingInfo.getDefault();
							currentLiteral.setLength(0);
						}
						break;
					}
					break;

				case 4: // '\004'
					currentLiteral.append(c);
					if (c >= '0' && c <= '9')
						formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), formattingInfo.getMinLength() * 10 + (c - 48), formattingInfo.getMaxLength());
					else
					if (c == '.')
					{
						state = 3;
					} else
					{
						i = finalizeConverter(c, pattern, i, currentLiteral, formattingInfo, converterRegistry, rules, patternConverters, formattingInfos);
						state = 0;
						formattingInfo = FormattingInfo.getDefault();
						currentLiteral.setLength(0);
					}
					break;

				case 3: // '\003'
					currentLiteral.append(c);
					if (c >= '0' && c <= '9')
					{
						formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), formattingInfo.getMinLength(), c - 48);
						state = 5;
					} else
					{
						LogLog.error("Error occured in position " + i + ".\n Was expecting digit, instead got char \"" + c + "\".");
						state = 0;
					}
					break;

				case 5: // '\005'
					currentLiteral.append(c);
					if (c >= '0' && c <= '9')
					{
						formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), formattingInfo.getMinLength(), formattingInfo.getMaxLength() * 10 + (c - 48));
					} else
					{
						i = finalizeConverter(c, pattern, i, currentLiteral, formattingInfo, converterRegistry, rules, patternConverters, formattingInfos);
						state = 0;
						formattingInfo = FormattingInfo.getDefault();
						currentLiteral.setLength(0);
					}
					break;
				}
				continue;
			}
			if (currentLiteral.length() != 0)
			{
				patternConverters.add(new LiteralPatternConverter(currentLiteral.toString()));
				formattingInfos.add(FormattingInfo.getDefault());
			}
			break;
		} while (true);
	}

	private static PatternConverter createConverter(String converterId, StringBuffer currentLiteral, Map converterRegistry, Map rules, List options)
	{
		String converterName;
		Class converterClass;
		converterName = converterId;
		Object converterObj = null;
		for (int i = converterId.length(); i > 0 && converterObj == null; i--)
		{
			converterName = converterName.substring(0, i);
			if (converterRegistry != null)
				converterObj = converterRegistry.get(converterName);
			if (converterObj == null && rules != null)
				converterObj = rules.get(converterName);
		}

		if (converterObj == null)
		{
			LogLog.error("Unrecognized format specifier [" + converterId + "]");
			return null;
		}
		converterClass = null;
		if (converterObj instanceof Class)
			converterClass = (Class)converterObj;
		else
		if (converterObj instanceof String)
		{
			try
			{
				converterClass = Loader.loadClass((String)converterObj);
			}
			catch (ClassNotFoundException ex)
			{
				LogLog.warn("Class for conversion pattern %" + converterName + " not found", ex);
				return null;
			}
		} else
		{
			LogLog.warn("Bad map entry for conversion pattern %" + converterName + ".");
			return null;
		}
		Object newObj;
		Method factory = converterClass.getMethod("newInstance", new Class[] {
			Class.forName("[Ljava.lang.String;")
		});
		String optionsArray[] = new String[options.size()];
		optionsArray = (String[])(String[])options.toArray(optionsArray);
		newObj = factory.invoke(null, new Object[] {
			optionsArray
		});
		if (!(newObj instanceof PatternConverter))
			break MISSING_BLOCK_LABEL_313;
		currentLiteral.delete(0, currentLiteral.length() - (converterId.length() - converterName.length()));
		return (PatternConverter)newObj;
		try
		{
			LogLog.warn("Class " + converterClass.getName() + " does not extend PatternConverter.");
			break MISSING_BLOCK_LABEL_433;
		}
		catch (Exception ex)
		{
			LogLog.error("Error creating converter for " + converterId, ex);
		}
		PatternConverter pc;
		pc = (PatternConverter)converterClass.newInstance();
		currentLiteral.delete(0, currentLiteral.length() - (converterId.length() - converterName.length()));
		return pc;
		Exception ex2;
		ex2;
		LogLog.error("Error creating converter for " + converterId, ex2);
		return null;
	}

	private static int finalizeConverter(char c, String pattern, int i, StringBuffer currentLiteral, FormattingInfo formattingInfo, Map converterRegistry, Map rules, List patternConverters, 
			List formattingInfos)
	{
		StringBuffer convBuf = new StringBuffer();
		i = extractConverter(c, pattern, i, convBuf, currentLiteral);
		String converterId = convBuf.toString();
		List options = new ArrayList();
		i = extractOptions(pattern, i, options);
		PatternConverter pc = createConverter(converterId, currentLiteral, converterRegistry, rules, options);
		if (pc == null)
		{
			StringBuffer msg;
			if (converterId == null || converterId.length() == 0)
			{
				msg = new StringBuffer("Empty conversion specifier starting at position ");
			} else
			{
				msg = new StringBuffer("Unrecognized conversion specifier [");
				msg.append(converterId);
				msg.append("] starting at position ");
			}
			msg.append(Integer.toString(i));
			msg.append(" in conversion pattern.");
			LogLog.error(msg.toString());
			patternConverters.add(new LiteralPatternConverter(currentLiteral.toString()));
			formattingInfos.add(FormattingInfo.getDefault());
		} else
		{
			patternConverters.add(pc);
			formattingInfos.add(formattingInfo);
			if (currentLiteral.length() > 0)
			{
				patternConverters.add(new LiteralPatternConverter(currentLiteral.toString()));
				formattingInfos.add(FormattingInfo.getDefault());
			}
		}
		currentLiteral.setLength(0);
		return i;
	}

	static Class class$(String x0)
	{
		return Class.forName(x0);
		ClassNotFoundException x1;
		x1;
		throw (new NoClassDefFoundError()).initCause(x1);
	}

	static 
	{
		Map rules = new HashMap(17);
		rules.put("c", org.apache.log4j.pattern.LoggerPatternConverter.class);
		rules.put("logger", org.apache.log4j.pattern.LoggerPatternConverter.class);
		rules.put("C", org.apache.log4j.pattern.ClassNamePatternConverter.class);
		rules.put("class", org.apache.log4j.pattern.ClassNamePatternConverter.class);
		rules.put("d", org.apache.log4j.pattern.DatePatternConverter.class);
		rules.put("date", org.apache.log4j.pattern.DatePatternConverter.class);
		rules.put("F", org.apache.log4j.pattern.FileLocationPatternConverter.class);
		rules.put("file", org.apache.log4j.pattern.FileLocationPatternConverter.class);
		rules.put("l", org.apache.log4j.pattern.FullLocationPatternConverter.class);
		rules.put("L", org.apache.log4j.pattern.LineLocationPatternConverter.class);
		rules.put("line", org.apache.log4j.pattern.LineLocationPatternConverter.class);
		rules.put("m", org.apache.log4j.pattern.MessagePatternConverter.class);
		rules.put("message", org.apache.log4j.pattern.MessagePatternConverter.class);
		rules.put("n", org.apache.log4j.pattern.LineSeparatorPatternConverter.class);
		rules.put("M", org.apache.log4j.pattern.MethodLocationPatternConverter.class);
		rules.put("method", org.apache.log4j.pattern.MethodLocationPatternConverter.class);
		rules.put("p", org.apache.log4j.pattern.LevelPatternConverter.class);
		rules.put("level", org.apache.log4j.pattern.LevelPatternConverter.class);
		rules.put("r", org.apache.log4j.pattern.RelativeTimePatternConverter.class);
		rules.put("relative", org.apache.log4j.pattern.RelativeTimePatternConverter.class);
		rules.put("t", org.apache.log4j.pattern.ThreadPatternConverter.class);
		rules.put("thread", org.apache.log4j.pattern.ThreadPatternConverter.class);
		rules.put("x", org.apache.log4j.pattern.NDCPatternConverter.class);
		rules.put("ndc", org.apache.log4j.pattern.NDCPatternConverter.class);
		rules.put("X", org.apache.log4j.pattern.PropertiesPatternConverter.class);
		rules.put("properties", org.apache.log4j.pattern.PropertiesPatternConverter.class);
		rules.put("sn", org.apache.log4j.pattern.SequenceNumberPatternConverter.class);
		rules.put("sequenceNumber", org.apache.log4j.pattern.SequenceNumberPatternConverter.class);
		rules.put("throwable", org.apache.log4j.pattern.ThrowableInformationPatternConverter.class);
		PATTERN_LAYOUT_RULES = new ReadOnlyMap(rules);
		Map fnameRules = new HashMap(4);
		fnameRules.put("d", org.apache.log4j.pattern.FileDatePatternConverter.class);
		fnameRules.put("date", org.apache.log4j.pattern.FileDatePatternConverter.class);
		fnameRules.put("i", org.apache.log4j.pattern.IntegerPatternConverter.class);
		fnameRules.put("index", org.apache.log4j.pattern.IntegerPatternConverter.class);
		FILENAME_PATTERN_RULES = new ReadOnlyMap(fnameRules);
	}
}
