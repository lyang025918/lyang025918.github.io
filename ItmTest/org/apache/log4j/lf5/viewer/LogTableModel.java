// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LogTableModel.java

package org.apache.log4j.lf5.viewer;

import javax.swing.table.DefaultTableModel;

public class LogTableModel extends DefaultTableModel
{

	private static final long serialVersionUID = 0x31ddf7ef885f14deL;

	public LogTableModel(Object colNames[], int numRows)
	{
		super(colNames, numRows);
	}

	public boolean isCellEditable(int row, int column)
	{
		return false;
	}
}
