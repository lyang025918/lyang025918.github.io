// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CategoryExplorerModel.java

package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.awt.AWTEventMulticaster;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.apache.log4j.lf5.LogRecord;

// Referenced classes of package org.apache.log4j.lf5.viewer.categoryexplorer:
//			CategoryPath, CategoryNode, CategoryElement

public class CategoryExplorerModel extends DefaultTreeModel
{

	private static final long serialVersionUID = 0xd09f6f82f2a80ae3L;
	protected boolean _renderFatal;
	protected ActionListener _listener;
	protected ActionEvent _event;

	public CategoryExplorerModel(CategoryNode node)
	{
		super(node);
		_renderFatal = true;
		_listener = null;
		_event = new ActionEvent(this, 1001, "Nodes Selection changed");
	}

	public void addLogRecord(LogRecord lr)
	{
		CategoryPath path = new CategoryPath(lr.getCategory());
		addCategory(path);
		CategoryNode node = getCategoryNode(path);
		node.addRecord();
		if (_renderFatal && lr.isFatal())
		{
			javax.swing.tree.TreeNode nodes[] = getPathToRoot(node);
			int len = nodes.length;
			for (int i = 1; i < len - 1; i++)
			{
				CategoryNode parent = (CategoryNode)nodes[i];
				parent.setHasFatalChildren(true);
				nodeChanged(parent);
			}

			node.setHasFatalRecords(true);
			nodeChanged(node);
		}
	}

	public CategoryNode getRootCategoryNode()
	{
		return (CategoryNode)getRoot();
	}

	public CategoryNode getCategoryNode(String category)
	{
		CategoryPath path = new CategoryPath(category);
		return getCategoryNode(path);
	}

	public CategoryNode getCategoryNode(CategoryPath path)
	{
		CategoryNode root = (CategoryNode)getRoot();
		CategoryNode parent = root;
		for (int i = 0; i < path.size(); i++)
		{
			CategoryElement element = path.categoryElementAt(i);
			Enumeration children = parent.children();
			boolean categoryAlreadyExists = false;
			do
			{
				if (!children.hasMoreElements())
					break;
				CategoryNode node = (CategoryNode)children.nextElement();
				String title = node.getTitle().toLowerCase();
				String pathLC = element.getTitle().toLowerCase();
				if (!title.equals(pathLC))
					continue;
				categoryAlreadyExists = true;
				parent = node;
				break;
			} while (true);
			if (!categoryAlreadyExists)
				return null;
		}

		return parent;
	}

	public boolean isCategoryPathActive(CategoryPath path)
	{
		CategoryNode root = (CategoryNode)getRoot();
		CategoryNode parent = root;
		boolean active = false;
		for (int i = 0; i < path.size(); i++)
		{
			CategoryElement element = path.categoryElementAt(i);
			Enumeration children = parent.children();
			boolean categoryAlreadyExists = false;
			active = false;
			do
			{
				if (!children.hasMoreElements())
					break;
				CategoryNode node = (CategoryNode)children.nextElement();
				String title = node.getTitle().toLowerCase();
				String pathLC = element.getTitle().toLowerCase();
				if (!title.equals(pathLC))
					continue;
				categoryAlreadyExists = true;
				parent = node;
				if (parent.isSelected())
					active = true;
				break;
			} while (true);
			if (!active || !categoryAlreadyExists)
				return false;
		}

		return active;
	}

	public CategoryNode addCategory(CategoryPath path)
	{
		CategoryNode root = (CategoryNode)getRoot();
		CategoryNode parent = root;
		for (int i = 0; i < path.size(); i++)
		{
			CategoryElement element = path.categoryElementAt(i);
			Enumeration children = parent.children();
			boolean categoryAlreadyExists = false;
			do
			{
				if (!children.hasMoreElements())
					break;
				CategoryNode node = (CategoryNode)children.nextElement();
				String title = node.getTitle().toLowerCase();
				String pathLC = element.getTitle().toLowerCase();
				if (!title.equals(pathLC))
					continue;
				categoryAlreadyExists = true;
				parent = node;
				break;
			} while (true);
			if (!categoryAlreadyExists)
			{
				CategoryNode newNode = new CategoryNode(element.getTitle());
				insertNodeInto(newNode, parent, parent.getChildCount());
				refresh(newNode);
				parent = newNode;
			}
		}

		return parent;
	}

	public void update(CategoryNode node, boolean selected)
	{
		if (node.isSelected() == selected)
			return;
		if (selected)
			setParentSelection(node, true);
		else
			setDescendantSelection(node, false);
	}

	public void setDescendantSelection(CategoryNode node, boolean selected)
	{
		Enumeration descendants = node.depthFirstEnumeration();
		do
		{
			if (!descendants.hasMoreElements())
				break;
			CategoryNode current = (CategoryNode)descendants.nextElement();
			if (current.isSelected() != selected)
			{
				current.setSelected(selected);
				nodeChanged(current);
			}
		} while (true);
		notifyActionListeners();
	}

	public void setParentSelection(CategoryNode node, boolean selected)
	{
		javax.swing.tree.TreeNode nodes[] = getPathToRoot(node);
		int len = nodes.length;
		for (int i = 1; i < len; i++)
		{
			CategoryNode parent = (CategoryNode)nodes[i];
			if (parent.isSelected() != selected)
			{
				parent.setSelected(selected);
				nodeChanged(parent);
			}
		}

		notifyActionListeners();
	}

	public synchronized void addActionListener(ActionListener l)
	{
		_listener = AWTEventMulticaster.add(_listener, l);
	}

	public synchronized void removeActionListener(ActionListener l)
	{
		_listener = AWTEventMulticaster.remove(_listener, l);
	}

	public void resetAllNodeCounts()
	{
		CategoryNode current;
		for (Enumeration nodes = getRootCategoryNode().depthFirstEnumeration(); nodes.hasMoreElements(); nodeChanged(current))
		{
			current = (CategoryNode)nodes.nextElement();
			current.resetNumberOfContainedRecords();
		}

	}

	public TreePath getTreePathToRoot(CategoryNode node)
	{
		if (node == null)
			return null;
		else
			return new TreePath(getPathToRoot(node));
	}

	protected void notifyActionListeners()
	{
		if (_listener != null)
			_listener.actionPerformed(_event);
	}

	protected void refresh(final CategoryNode node)
	{
		SwingUtilities.invokeLater(new Runnable() {

			public void run()
			{
				nodeChanged(node);
			}

			
			{
				super();
			}
		});
	}
}
