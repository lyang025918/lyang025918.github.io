// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LogTable.java

package org.apache.log4j.lf5.viewer;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.apache.log4j.lf5.util.DateFormatManager;

// Referenced classes of package org.apache.log4j.lf5.viewer:
//			FilteredLogTableModel, LogTableRowRenderer, LogTableColumn

public class LogTable extends JTable
{
	class LogTableListSelectionListener
		implements ListSelectionListener
	{

		protected JTable _table;

		public void valueChanged(ListSelectionEvent e)
		{
			if (e.getValueIsAdjusting())
				return;
			ListSelectionModel lsm = (ListSelectionModel)e.getSource();
			if (!lsm.isSelectionEmpty())
			{
				StringBuffer buf = new StringBuffer();
				int selectedRow = lsm.getMinSelectionIndex();
				for (int i = 0; i < _numCols - 1; i++)
				{
					String value = "";
					Object obj = _table.getModel().getValueAt(selectedRow, i);
					if (obj != null)
						value = obj.toString();
					buf.append(_colNames[i] + ":");
					buf.append("\t");
					if (i == _colThread || i == _colMessage || i == _colLevel)
						buf.append("\t");
					if (i == _colDate || i == _colNDC)
						buf.append("\t\t");
					buf.append(value);
					buf.append("\n");
				}

				buf.append(_colNames[_numCols - 1] + ":\n");
				Object obj = _table.getModel().getValueAt(selectedRow, _numCols - 1);
				if (obj != null)
					buf.append(obj.toString());
				_detailTextArea.setText(buf.toString());
			}
		}

		public LogTableListSelectionListener(JTable table)
		{
			super();
			_table = table;
		}
	}


	private static final long serialVersionUID = 0x438b5c25e8ea02aaL;
	protected int _rowHeight;
	protected JTextArea _detailTextArea;
	protected int _numCols;
	protected TableColumn _tableColumns[];
	protected int _colWidths[] = {
		40, 40, 40, 70, 70, 360, 440, 200, 60
	};
	protected LogTableColumn _colNames[];
	protected int _colDate;
	protected int _colThread;
	protected int _colMessageNum;
	protected int _colLevel;
	protected int _colNDC;
	protected int _colCategory;
	protected int _colMessage;
	protected int _colLocation;
	protected int _colThrown;
	protected DateFormatManager _dateFormatManager;

	public LogTable(JTextArea detailTextArea)
	{
		_rowHeight = 30;
		_numCols = 9;
		_tableColumns = new TableColumn[_numCols];
		_colNames = LogTableColumn.getLogTableColumnArray();
		_colDate = 0;
		_colThread = 1;
		_colMessageNum = 2;
		_colLevel = 3;
		_colNDC = 4;
		_colCategory = 5;
		_colMessage = 6;
		_colLocation = 7;
		_colThrown = 8;
		_dateFormatManager = null;
		init();
		_detailTextArea = detailTextArea;
		setModel(new FilteredLogTableModel());
		Enumeration columns = getColumnModel().getColumns();
		for (int i = 0; columns.hasMoreElements(); i++)
		{
			TableColumn col = (TableColumn)columns.nextElement();
			col.setCellRenderer(new LogTableRowRenderer());
			col.setPreferredWidth(_colWidths[i]);
			_tableColumns[i] = col;
		}

		ListSelectionModel rowSM = getSelectionModel();
		rowSM.addListSelectionListener(new LogTableListSelectionListener(this));
	}

	public DateFormatManager getDateFormatManager()
	{
		return _dateFormatManager;
	}

	public void setDateFormatManager(DateFormatManager dfm)
	{
		_dateFormatManager = dfm;
	}

	public synchronized void clearLogRecords()
	{
		getFilteredLogTableModel().clear();
	}

	public FilteredLogTableModel getFilteredLogTableModel()
	{
		return (FilteredLogTableModel)getModel();
	}

	public void setDetailedView()
	{
		TableColumnModel model = getColumnModel();
		for (int f = 0; f < _numCols; f++)
			model.removeColumn(_tableColumns[f]);

		for (int i = 0; i < _numCols; i++)
			model.addColumn(_tableColumns[i]);

		sizeColumnsToFit(-1);
	}

	public void setView(List columns)
	{
		TableColumnModel model = getColumnModel();
		for (int f = 0; f < _numCols; f++)
			model.removeColumn(_tableColumns[f]);

		Iterator selectedColumns = columns.iterator();
		Vector columnNameAndNumber = getColumnNameAndNumber();
		for (; selectedColumns.hasNext(); model.addColumn(_tableColumns[columnNameAndNumber.indexOf(selectedColumns.next())]));
		sizeColumnsToFit(-1);
	}

	public void setFont(java.awt.Font font)
	{
		super.setFont(font);
		java.awt.Graphics g = getGraphics();
		if (g != null)
		{
			java.awt.FontMetrics fm = g.getFontMetrics(font);
			int height = fm.getHeight();
			_rowHeight = height + height / 3;
			setRowHeight(_rowHeight);
		}
	}

	protected void init()
	{
		setRowHeight(_rowHeight);
		setSelectionMode(0);
	}

	protected Vector getColumnNameAndNumber()
	{
		Vector columnNameAndNumber = new Vector();
		for (int i = 0; i < _colNames.length; i++)
			columnNameAndNumber.add(i, _colNames[i]);

		return columnNameAndNumber;
	}
}
