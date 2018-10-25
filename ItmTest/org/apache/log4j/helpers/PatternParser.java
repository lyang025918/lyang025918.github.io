// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PatternParser.java

package org.apache.log4j.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.helpers:
//			FormattingInfo, ISO8601DateFormat, AbsoluteTimeDateFormat, DateTimeDateFormat, 
//			PatternConverter, LogLog, OptionConverter

public class PatternParser
{
	private class CategoryPatternConverter extends NamedPatternConverter
	{

		String getFullyQualifiedName(LoggingEvent event)
		{
			return event.getLoggerName();
		}

		CategoryPatternConverter(FormattingInfo formattingInfo, int precision)
		{
			super(formattingInfo, precision);
		}
	}

	private class ClassNamePatternConverter extends NamedPatternConverter
	{

		String getFullyQualifiedName(LoggingEvent event)
		{
			return event.getLocationInformation().getClassName();
		}

		ClassNamePatternConverter(FormattingInfo formattingInfo, int precision)
		{
			super(formattingInfo, precision);
		}
	}

	private static abstract class NamedPatternConverter extends PatternConverter
	{

		int precision;

		abstract String getFullyQualifiedName(LoggingEvent loggingevent);

		public String convert(LoggingEvent event)
		{
			String n = getFullyQualifiedName(event);
			if (precision <= 0)
				return n;
			int len = n.length();
			int end = len - 1;
			for (int i = precision; i > 0; i--)
			{
				end = n.lastIndexOf('.', end - 1);
				if (end == -1)
					return n;
			}

			return n.substring(end + 1, len);
		}

		NamedPatternConverter(FormattingInfo formattingInfo, int precision)
		{
			super(formattingInfo);
			this.precision = precision;
		}
	}

	private class LocationPatternConverter extends PatternConverter
	{

		int type;

		public String convert(LoggingEvent event)
		{
			LocationInfo locationInfo = event.getLocationInformation();
			switch (type)
			{
			case 1000: 
				return locationInfo.fullInfo;

			case 1001: 
				return locationInfo.getMethodName();

			case 1003: 
				return locationInfo.getLineNumber();

			case 1004: 
				return locationInfo.getFileName();

			case 1002: 
			default:
				return null;
			}
		}

		LocationPatternConverter(FormattingInfo formattingInfo, int type)
		{
			super(formattingInfo);
			this.type = type;
		}
	}

	private static class MDCPatternConverter extends PatternConverter
	{

		private String key;

		public String convert(LoggingEvent event)
		{
			if (key == null)
			{
				StringBuffer buf = new StringBuffer("{");
				Map properties = event.getProperties();
				if (properties.size() > 0)
				{
					Object keys[] = properties.keySet().toArray();
					Arrays.sort(keys);
					for (int i = 0; i < keys.length; i++)
					{
						buf.append('{');
						buf.append(keys[i]);
						buf.append(',');
						buf.append(properties.get(keys[i]));
						buf.append('}');
					}

				}
				buf.append('}');
				return buf.toString();
			}
			Object val = event.getMDC(key);
			if (val == null)
				return null;
			else
				return val.toString();
		}

		MDCPatternConverter(FormattingInfo formattingInfo, String key)
		{
			super(formattingInfo);
			this.key = key;
		}
	}

	private static class DatePatternConverter extends PatternConverter
	{

		private DateFormat df;
		private Date date;

		public String convert(LoggingEvent event)
		{
			date.setTime(event.timeStamp);
			String converted = null;
			try
			{
				converted = df.format(date);
			}
			catch (Exception ex)
			{
				LogLog.error("Error occured while converting date.", ex);
			}
			return converted;
		}

		DatePatternConverter(FormattingInfo formattingInfo, DateFormat df)
		{
			super(formattingInfo);
			date = new Date();
			this.df = df;
		}
	}

	private static class LiteralPatternConverter extends PatternConverter
	{

		private String literal;

		public final void format(StringBuffer sbuf, LoggingEvent event)
		{
			sbuf.append(literal);
		}

		public String convert(LoggingEvent event)
		{
			return literal;
		}

		LiteralPatternConverter(String value)
		{
			literal = value;
		}
	}

	private static class BasicPatternConverter extends PatternConverter
	{

		int type;

		public String convert(LoggingEvent event)
		{
			switch (type)
			{
			case 2000: 
				return Long.toString(event.timeStamp - LoggingEvent.getStartTime());

			case 2001: 
				return event.getThreadName();

			case 2002: 
				return event.getLevel().toString();

			case 2003: 
				return event.getNDC();

			case 2004: 
				return event.getRenderedMessage();
			}
			return null;
		}

		BasicPatternConverter(FormattingInfo formattingInfo, int type)
		{
			super(formattingInfo);
			this.type = type;
		}
	}


	private static final char ESCAPE_CHAR = 37;
	private static final int LITERAL_STATE = 0;
	private static final int CONVERTER_STATE = 1;
	private static final int DOT_STATE = 3;
	private static final int MIN_STATE = 4;
	private static final int MAX_STATE = 5;
	static final int FULL_LOCATION_CONVERTER = 1000;
	static final int METHOD_LOCATION_CONVERTER = 1001;
	static final int CLASS_LOCATION_CONVERTER = 1002;
	static final int LINE_LOCATION_CONVERTER = 1003;
	static final int FILE_LOCATION_CONVERTER = 1004;
	static final int RELATIVE_TIME_CONVERTER = 2000;
	static final int THREAD_CONVERTER = 2001;
	static final int LEVEL_CONVERTER = 2002;
	static final int NDC_CONVERTER = 2003;
	static final int MESSAGE_CONVERTER = 2004;
	int state;
	protected StringBuffer currentLiteral;
	protected int patternLength;
	protected int i;
	PatternConverter head;
	PatternConverter tail;
	protected FormattingInfo formattingInfo;
	protected String pattern;

	public PatternParser(String pattern)
	{
		currentLiteral = new StringBuffer(32);
		formattingInfo = new FormattingInfo();
		this.pattern = pattern;
		patternLength = pattern.length();
		state = 0;
	}

	private void addToList(PatternConverter pc)
	{
		if (head == null)
		{
			head = tail = pc;
		} else
		{
			tail.next = pc;
			tail = pc;
		}
	}

	protected String extractOption()
	{
		if (i < patternLength && pattern.charAt(i) == '{')
		{
			int end = pattern.indexOf('}', i);
			if (end > i)
			{
				String r = pattern.substring(i + 1, end);
				i = end + 1;
				return r;
			}
		}
		return null;
	}

	protected int extractPrecisionOption()
	{
		String opt = extractOption();
		int r = 0;
		if (opt != null)
			try
			{
				r = Integer.parseInt(opt);
				if (r <= 0)
				{
					LogLog.error("Precision option (" + opt + ") isn't a positive integer.");
					r = 0;
				}
			}
			catch (NumberFormatException e)
			{
				LogLog.error("Category option \"" + opt + "\" not a decimal integer.", e);
			}
		return r;
	}

	public PatternConverter parse()
	{
		i = 0;
		do
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

						case 110: // 'n'
							currentLiteral.append(Layout.LINE_SEP);
							i++;
							break;

						default:
							if (currentLiteral.length() != 0)
								addToList(new LiteralPatternConverter(currentLiteral.toString()));
							currentLiteral.setLength(0);
							currentLiteral.append(c);
							state = 1;
							formattingInfo.reset();
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
						formattingInfo.leftAlign = true;
						break;

					case 46: // '.'
						state = 3;
						break;

					default:
						if (c >= '0' && c <= '9')
						{
							formattingInfo.min = c - 48;
							state = 4;
						} else
						{
							finalizeConverter(c);
						}
						break;
					}
					break;

				case 4: // '\004'
					currentLiteral.append(c);
					if (c >= '0' && c <= '9')
						formattingInfo.min = formattingInfo.min * 10 + (c - 48);
					else
					if (c == '.')
						state = 3;
					else
						finalizeConverter(c);
					break;

				case 3: // '\003'
					currentLiteral.append(c);
					if (c >= '0' && c <= '9')
					{
						formattingInfo.max = c - 48;
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
						formattingInfo.max = formattingInfo.max * 10 + (c - 48);
					} else
					{
						finalizeConverter(c);
						state = 0;
					}
					break;
				}
			} else
			{
				if (currentLiteral.length() != 0)
					addToList(new LiteralPatternConverter(currentLiteral.toString()));
				return head;
			}
		while (true);
	}

	protected void finalizeConverter(char c)
	{
		PatternConverter pc = null;
		switch (c)
		{
		case 99: // 'c'
			pc = new CategoryPatternConverter(formattingInfo, extractPrecisionOption());
			currentLiteral.setLength(0);
			break;

		case 67: // 'C'
			pc = new ClassNamePatternConverter(formattingInfo, extractPrecisionOption());
			currentLiteral.setLength(0);
			break;

		case 100: // 'd'
			String dateFormatStr = "ISO8601";
			String dOpt = extractOption();
			if (dOpt != null)
				dateFormatStr = dOpt;
			DateFormat df;
			if (dateFormatStr.equalsIgnoreCase("ISO8601"))
				df = new ISO8601DateFormat();
			else
			if (dateFormatStr.equalsIgnoreCase("ABSOLUTE"))
				df = new AbsoluteTimeDateFormat();
			else
			if (dateFormatStr.equalsIgnoreCase("DATE"))
				df = new DateTimeDateFormat();
			else
				try
				{
					df = new SimpleDateFormat(dateFormatStr);
				}
				catch (IllegalArgumentException e)
				{
					LogLog.error("Could not instantiate SimpleDateFormat with " + dateFormatStr, e);
					df = (DateFormat)OptionConverter.instantiateByClassName("org.apache.log4j.helpers.ISO8601DateFormat", java.text.DateFormat.class, null);
				}
			pc = new DatePatternConverter(formattingInfo, df);
			currentLiteral.setLength(0);
			break;

		case 70: // 'F'
			pc = new LocationPatternConverter(formattingInfo, 1004);
			currentLiteral.setLength(0);
			break;

		case 108: // 'l'
			pc = new LocationPatternConverter(formattingInfo, 1000);
			currentLiteral.setLength(0);
			break;

		case 76: // 'L'
			pc = new LocationPatternConverter(formattingInfo, 1003);
			currentLiteral.setLength(0);
			break;

		case 109: // 'm'
			pc = new BasicPatternConverter(formattingInfo, 2004);
			currentLiteral.setLength(0);
			break;

		case 77: // 'M'
			pc = new LocationPatternConverter(formattingInfo, 1001);
			currentLiteral.setLength(0);
			break;

		case 112: // 'p'
			pc = new BasicPatternConverter(formattingInfo, 2002);
			currentLiteral.setLength(0);
			break;

		case 114: // 'r'
			pc = new BasicPatternConverter(formattingInfo, 2000);
			currentLiteral.setLength(0);
			break;

		case 116: // 't'
			pc = new BasicPatternConverter(formattingInfo, 2001);
			currentLiteral.setLength(0);
			break;

		case 120: // 'x'
			pc = new BasicPatternConverter(formattingInfo, 2003);
			currentLiteral.setLength(0);
			break;

		case 88: // 'X'
			String xOpt = extractOption();
			pc = new MDCPatternConverter(formattingInfo, xOpt);
			currentLiteral.setLength(0);
			break;

		case 68: // 'D'
		case 69: // 'E'
		case 71: // 'G'
		case 72: // 'H'
		case 73: // 'I'
		case 74: // 'J'
		case 75: // 'K'
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
		case 89: // 'Y'
		case 90: // 'Z'
		case 91: // '['
		case 92: // '\\'
		case 93: // ']'
		case 94: // '^'
		case 95: // '_'
		case 96: // '`'
		case 97: // 'a'
		case 98: // 'b'
		case 101: // 'e'
		case 102: // 'f'
		case 103: // 'g'
		case 104: // 'h'
		case 105: // 'i'
		case 106: // 'j'
		case 107: // 'k'
		case 110: // 'n'
		case 111: // 'o'
		case 113: // 'q'
		case 115: // 's'
		case 117: // 'u'
		case 118: // 'v'
		case 119: // 'w'
		default:
			LogLog.error("Unexpected char [" + c + "] at position " + i + " in conversion patterrn.");
			pc = new LiteralPatternConverter(currentLiteral.toString());
			currentLiteral.setLength(0);
			break;
		}
		addConverter(pc);
	}

	protected void addConverter(PatternConverter pc)
	{
		currentLiteral.setLength(0);
		addToList(pc);
		state = 0;
		formattingInfo.reset();
	}
}
