// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CategoryNode.java

package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;

public class CategoryNode extends DefaultMutableTreeNode
{

	private static final long serialVersionUID = 0x52b29a36eefed5e7L;
	protected boolean _selected;
	protected int _numberOfContainedRecords;
	protected int _numberOfRecordsFromChildren;
	protected boolean _hasFatalChildren;
	protected boolean _hasFatalRecords;

	public CategoryNode(String title)
	{
		_selected = true;
		_numberOfContainedRecords = 0;
		_numberOfRecordsFromChildren = 0;
		_hasFatalChildren = false;
		_hasFatalRecords = false;
		setUserObject(title);
	}

	public String getTitle()
	{
		return (String)getUserObject();
	}

	public void setSelected(boolean s)
	{
		if (s != _selected)
			_selected = s;
	}

	public boolean isSelected()
	{
		return _selected;
	}

	/**
	 * @deprecated Method setAllDescendantsSelected is deprecated
	 */

	public void setAllDescendantsSelected()
	{
		CategoryNode node;
		for (Enumeration children = children(); children.hasMoreElements(); node.setAllDescendantsSelected())
		{
			node = (CategoryNode)children.nextElement();
			node.setSelected(true);
		}

	}

	/**
	 * @deprecated Method setAllDescendantsDeSelected is deprecated
	 */

	public void setAllDescendantsDeSelected()
	{
		CategoryNode node;
		for (Enumeration children = children(); children.hasMoreElements(); node.setAllDescendantsDeSelected())
		{
			node = (CategoryNode)children.nextElement();
			node.setSelected(false);
		}

	}

	public String toString()
	{
		return getTitle();
	}

	public boolean equals(Object obj)
	{
		if (obj instanceof CategoryNode)
		{
			CategoryNode node = (CategoryNode)obj;
			String tit1 = getTitle().toLowerCase();
			String tit2 = node.getTitle().toLowerCase();
			if (tit1.equals(tit2))
				return true;
		}
		return false;
	}

	public int hashCode()
	{
		return getTitle().hashCode();
	}

	public void addRecord()
	{
		_numberOfContainedRecords++;
		addRecordToParent();
	}

	public int getNumberOfContainedRecords()
	{
		return _numberOfContainedRecords;
	}

	public void resetNumberOfContainedRecords()
	{
		_numberOfContainedRecords = 0;
		_numberOfRecordsFromChildren = 0;
		_hasFatalRecords = false;
		_hasFatalChildren = false;
	}

	public boolean hasFatalRecords()
	{
		return _hasFatalRecords;
	}

	public boolean hasFatalChildren()
	{
		return _hasFatalChildren;
	}

	public void setHasFatalRecords(boolean flag)
	{
		_hasFatalRecords = flag;
	}

	public void setHasFatalChildren(boolean flag)
	{
		_hasFatalChildren = flag;
	}

	protected int getTotalNumberOfRecords()
	{
		return getNumberOfRecordsFromChildren() + getNumberOfContainedRecords();
	}

	protected void addRecordFromChild()
	{
		_numberOfRecordsFromChildren++;
		addRecordToParent();
	}

	protected int getNumberOfRecordsFromChildren()
	{
		return _numberOfRecordsFromChildren;
	}

	protected void addRecordToParent()
	{
		javax.swing.tree.TreeNode parent = getParent();
		if (parent == null)
		{
			return;
		} else
		{
			((CategoryNode)parent).addRecordFromChild();
			return;
		}
	}
}
