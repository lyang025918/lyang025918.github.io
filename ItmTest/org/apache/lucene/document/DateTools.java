// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DateTools.java

package org.apache.lucene.document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateTools
{
	public static final class Resolution extends Enum
	{

		public static final Resolution YEAR;
		public static final Resolution MONTH;
		public static final Resolution DAY;
		public static final Resolution HOUR;
		public static final Resolution MINUTE;
		public static final Resolution SECOND;
		public static final Resolution MILLISECOND;
		final int formatLen;
		final SimpleDateFormat format;
		private static final Resolution $VALUES[];

		public static Resolution[] values()
		{
			return (Resolution[])$VALUES.clone();
		}

		public static Resolution valueOf(String name)
		{
			return (Resolution)Enum.valueOf(org/apache/lucene/document/DateTools$Resolution, name);
		}

		public String toString()
		{
			return super.toString().toLowerCase(Locale.ROOT);
		}

		static 
		{
			YEAR = new Resolution("YEAR", 0, 4);
			MONTH = new Resolution("MONTH", 1, 6);
			DAY = new Resolution("DAY", 2, 8);
			HOUR = new Resolution("HOUR", 3, 10);
			MINUTE = new Resolution("MINUTE", 4, 12);
			SECOND = new Resolution("SECOND", 5, 14);
			MILLISECOND = new Resolution("MILLISECOND", 6, 17);
			$VALUES = (new Resolution[] {
				YEAR, MONTH, DAY, HOUR, MINUTE, SECOND, MILLISECOND
			});
		}

		private Resolution(String s, int i, int formatLen)
		{
			super(s, i);
			this.formatLen = formatLen;
			format = new SimpleDateFormat("yyyyMMddHHmmssSSS".substring(0, formatLen), Locale.ROOT);
			format.setTimeZone(DateTools.GMT);
		}
	}


	static final TimeZone GMT = TimeZone.getTimeZone("GMT");
	private static final ThreadLocal TL_CAL = new ThreadLocal() {

		protected Calendar initialValue()
		{
			return Calendar.getInstance(DateTools.GMT, Locale.ROOT);
		}

		protected volatile Object initialValue()
		{
			return initialValue();
		}

	};
	private static final ThreadLocal TL_FORMATS = new ThreadLocal() {

		protected SimpleDateFormat[] initialValue()
		{
			SimpleDateFormat arr[] = new SimpleDateFormat[Resolution.MILLISECOND.formatLen + 1];
			Resolution arr$[] = Resolution.values();
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Resolution resolution = arr$[i$];
				arr[resolution.formatLen] = (SimpleDateFormat)resolution.format.clone();
			}

			return arr;
		}

		protected volatile Object initialValue()
		{
			return initialValue();
		}

	};

	private DateTools()
	{
	}

	public static String dateToString(Date date, Resolution resolution)
	{
		return timeToString(date.getTime(), resolution);
	}

	public static String timeToString(long time, Resolution resolution)
	{
		Date date = new Date(round(time, resolution));
		return ((SimpleDateFormat[])TL_FORMATS.get())[resolution.formatLen].format(date);
	}

	public static long stringToTime(String dateString)
		throws ParseException
	{
		return stringToDate(dateString).getTime();
	}

	public static Date stringToDate(String dateString)
		throws ParseException
	{
		return ((SimpleDateFormat[])TL_FORMATS.get())[dateString.length()].parse(dateString);
		Exception e;
		e;
		throw new ParseException((new StringBuilder()).append("Input is not a valid date string: ").append(dateString).toString(), 0);
	}

	public static Date round(Date date, Resolution resolution)
	{
		return new Date(round(date.getTime(), resolution));
	}

	public static long round(long time, Resolution resolution)
	{
		Calendar calInstance = (Calendar)TL_CAL.get();
		calInstance.setTimeInMillis(time);
		static class 3
		{

			static final int $SwitchMap$org$apache$lucene$document$DateTools$Resolution[];

			static 
			{
				$SwitchMap$org$apache$lucene$document$DateTools$Resolution = new int[Resolution.values().length];
				try
				{
					$SwitchMap$org$apache$lucene$document$DateTools$Resolution[Resolution.YEAR.ordinal()] = 1;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$document$DateTools$Resolution[Resolution.MONTH.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$document$DateTools$Resolution[Resolution.DAY.ordinal()] = 3;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$document$DateTools$Resolution[Resolution.HOUR.ordinal()] = 4;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$document$DateTools$Resolution[Resolution.MINUTE.ordinal()] = 5;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$document$DateTools$Resolution[Resolution.SECOND.ordinal()] = 6;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$document$DateTools$Resolution[Resolution.MILLISECOND.ordinal()] = 7;
				}
				catch (NoSuchFieldError ex) { }
			}
		}

		switch (3..SwitchMap.org.apache.lucene.document.DateTools.Resolution[resolution.ordinal()])
		{
		default:
			throw new IllegalArgumentException((new StringBuilder()).append("unknown resolution ").append(resolution).toString());

		case 1: // '\001'
			calInstance.set(2, 0);
			// fall through

		case 2: // '\002'
			calInstance.set(5, 1);
			// fall through

		case 3: // '\003'
			calInstance.set(11, 0);
			// fall through

		case 4: // '\004'
			calInstance.set(12, 0);
			// fall through

		case 5: // '\005'
			calInstance.set(13, 0);
			// fall through

		case 6: // '\006'
			calInstance.set(14, 0);
			// fall through

		case 7: // '\007'
			return calInstance.getTimeInMillis();
		}
	}

}
