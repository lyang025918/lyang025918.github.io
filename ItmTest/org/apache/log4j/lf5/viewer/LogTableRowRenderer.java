// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LogTableRowRenderer.java

package org.apache.log4j.lf5.viewer;

import java.awt.Color;
import java.awt.Component;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.log4j.lf5.LogLevel;
import org.apache.log4j.lf5.LogRecord;

// Referenced classes of package org.apache.log4j.lf5.viewer:
//			FilteredLogTableModel

public class LogTableRowRenderer extends DefaultTableCellRenderer
{

	private static final long serialVersionUID = 0xc928f46617161e33L;
	protected boolean _highlightFatal;
	protected Color _color;

	public LogTableRowRenderer()
	{
		_highlightFatal = true;
		_color = new Color(230, 230, 230);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col)
	{
		if (row % 2 == 0)
			setBackground(_color);
		else
			setBackground(Color.white);
		FilteredLogTableModel model = (FilteredLogTableModel)table.getModel();
		LogRecord record = model.getFilteredRecord(row);
		setForeground(getLogLevelColor(record.getLevel()));
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
	}

	protected Color getLogLevelColor(LogLevel level)
	{
		return (Color)LogLevel.getLogLevelColorMap().get(level);
	}
}
