// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LogTableColumn.java

package org.apache.log4j.lf5.viewer;

import java.io.Serializable;
import java.util.*;

// Referenced classes of package org.apache.log4j.lf5.viewer:
//			LogTableColumnFormatException

public class LogTableColumn
	implements Serializable
{

	private static final long serialVersionUID = 0xc4a9354ff856ae1dL;
	public static final LogTableColumn DATE;
	public static final LogTableColumn THREAD;
	public static final LogTableColumn MESSAGE_NUM;
	public static final LogTableColumn LEVEL;
	public static final LogTableColumn NDC;
	public static final LogTableColumn CATEGORY;
	public static final LogTableColumn MESSAGE;
	public static final LogTableColumn LOCATION;
	public static final LogTableColumn THROWN;
	protected String _label;
	private static LogTableColumn _log4JColumns[];
	private static Map _logTableColumnMap;

	public LogTableColumn(String label)
	{
		_label = label;
	}

	public String getLabel()
	{
		return _label;
	}

	public static LogTableColumn valueOf(String column)
		throws LogTableColumnFormatException
	{
		LogTableColumn tableColumn = null;
		if (column != null)
		{
			column = column.trim();
			tableColumn = (LogTableColumn)_logTableColumnMap.get(column);
		}
		if (tableColumn == null)
		{
			StringBuffer buf = new StringBuffer();
			buf.append("Error while trying to parse (" + column + ") into");
			buf.append(" a LogTableColumn.");
			throw new LogTableColumnFormatException(buf.toString());
		} else
		{
			return tableColumn;
		}
	}

	public boolean equals(Object o)
	{
		boolean equals = false;
		if ((o instanceof LogTableColumn) && getLabel() == ((LogTableColumn)o).getLabel())
			equals = true;
		return equals;
	}

	public int hashCode()
	{
		return _label.hashCode();
	}

	public String toString()
	{
		return _label;
	}

	public static List getLogTableColumns()
	{
		return Arrays.asList(_log4JColumns);
	}

	public static LogTableColumn[] getLogTableColumnArray()
	{
		return _log4JColumns;
	}

	static 
	{
		DATE = new LogTableColumn("Date");
		THREAD = new LogTableColumn("Thread");
		MESSAGE_NUM = new LogTableColumn("Message #");
		LEVEL = new LogTableColumn("Level");
		NDC = new LogTableColumn("NDC");
		CATEGORY = new LogTableColumn("Category");
		MESSAGE = new LogTableColumn("Message");
		LOCATION = new LogTableColumn("Location");
		THROWN = new LogTableColumn("Thrown");
		_log4JColumns = (new LogTableColumn[] {
			DATE, THREAD, MESSAGE_NUM, LEVEL, NDC, CATEGORY, MESSAGE, LOCATION, THROWN
		});
		_logTableColumnMap = new HashMap();
		for (int i = 0; i < _log4JColumns.length; i++)
			_logTableColumnMap.put(_log4JColumns[i].getLabel(), _log4JColumns[i]);

	}
}
