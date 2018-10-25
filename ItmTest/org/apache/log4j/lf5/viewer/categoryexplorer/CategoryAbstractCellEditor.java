// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CategoryAbstractCellEditor.java

package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.*;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeCellEditor;

public class CategoryAbstractCellEditor
	implements TableCellEditor, TreeCellEditor
{

	protected EventListenerList _listenerList;
	protected Object _value;
	protected ChangeEvent _changeEvent;
	protected int _clickCountToStart;

	public CategoryAbstractCellEditor()
	{
		_listenerList = new EventListenerList();
		_changeEvent = null;
		_clickCountToStart = 1;
	}

	public Object getCellEditorValue()
	{
		return _value;
	}

	public void setCellEditorValue(Object value)
	{
		_value = value;
	}

	public void setClickCountToStart(int count)
	{
		_clickCountToStart = count;
	}

	public int getClickCountToStart()
	{
		return _clickCountToStart;
	}

	public boolean isCellEditable(EventObject anEvent)
	{
		return !(anEvent instanceof MouseEvent) || ((MouseEvent)anEvent).getClickCount() >= _clickCountToStart;
	}

	public boolean shouldSelectCell(EventObject anEvent)
	{
		return isCellEditable(anEvent) && (anEvent == null || ((MouseEvent)anEvent).getClickCount() >= _clickCountToStart);
	}

	public boolean stopCellEditing()
	{
		fireEditingStopped();
		return true;
	}

	public void cancelCellEditing()
	{
		fireEditingCanceled();
	}

	public void addCellEditorListener(CellEditorListener l)
	{
		_listenerList.add(javax.swing.event.CellEditorListener.class, l);
	}

	public void removeCellEditorListener(CellEditorListener l)
	{
		_listenerList.remove(javax.swing.event.CellEditorListener.class, l);
	}

	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean flag, boolean flag1, int i)
	{
		return null;
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int i, int j)
	{
		return null;
	}

	protected void fireEditingStopped()
	{
		Object listeners[] = _listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] != (javax.swing.event.CellEditorListener.class))
				continue;
			if (_changeEvent == null)
				_changeEvent = new ChangeEvent(this);
			((CellEditorListener)listeners[i + 1]).editingStopped(_changeEvent);
		}

	}

	protected void fireEditingCanceled()
	{
		Object listeners[] = _listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] != (javax.swing.event.CellEditorListener.class))
				continue;
			if (_changeEvent == null)
				_changeEvent = new ChangeEvent(this);
			((CellEditorListener)listeners[i + 1]).editingCanceled(_changeEvent);
		}

	}
}
