// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LF5SwingUtils.java

package org.apache.log4j.lf5.viewer;

import java.awt.Adjustable;
import javax.swing.*;
import javax.swing.table.TableModel;

// Referenced classes of package org.apache.log4j.lf5.viewer:
//			TrackingAdjustmentListener

public class LF5SwingUtils
{

	public LF5SwingUtils()
	{
	}

	public static void selectRow(int row, JTable table, JScrollPane pane)
	{
		if (table == null || pane == null)
			return;
		if (!contains(row, table.getModel()))
		{
			return;
		} else
		{
			moveAdjustable(row * table.getRowHeight(), pane.getVerticalScrollBar());
			selectRow(row, table.getSelectionModel());
			repaintLater(table);
			return;
		}
	}

	public static void makeScrollBarTrack(Adjustable scrollBar)
	{
		if (scrollBar == null)
		{
			return;
		} else
		{
			scrollBar.addAdjustmentListener(new TrackingAdjustmentListener());
			return;
		}
	}

	public static void makeVerticalScrollBarTrack(JScrollPane pane)
	{
		if (pane == null)
		{
			return;
		} else
		{
			makeScrollBarTrack(pane.getVerticalScrollBar());
			return;
		}
	}

	protected static boolean contains(int row, TableModel model)
	{
		if (model == null)
			return false;
		if (row < 0)
			return false;
		return row < model.getRowCount();
	}

	protected static void selectRow(int row, ListSelectionModel model)
	{
		if (model == null)
		{
			return;
		} else
		{
			model.setSelectionInterval(row, row);
			return;
		}
	}

	protected static void moveAdjustable(int location, Adjustable scrollBar)
	{
		if (scrollBar == null)
		{
			return;
		} else
		{
			scrollBar.setValue(location);
			return;
		}
	}

	protected static void repaintLater(JComponent component)
	{
		SwingUtilities.invokeLater(new Runnable(component) {

			public void run()
			{
				component.repaint();
			}

			
			{
				super();
			}
		});
	}
}
